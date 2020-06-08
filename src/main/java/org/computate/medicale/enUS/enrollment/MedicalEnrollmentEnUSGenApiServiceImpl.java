package org.computate.medicale.enUS.enrollment;

import org.computate.medicale.enUS.patient.MedicalPatientEnUSGenApiServiceImpl;
import org.computate.medicale.enUS.patient.MedicalPatient;
import org.computate.medicale.enUS.user.SiteUserEnUSGenApiServiceImpl;
import org.computate.medicale.enUS.user.SiteUser;
import org.computate.medicale.enUS.config.SiteConfig;
import org.computate.medicale.enUS.request.SiteRequestEnUS;
import org.computate.medicale.enUS.context.SiteContextEnUS;
import org.computate.medicale.enUS.user.SiteUser;
import org.computate.medicale.enUS.request.api.ApiRequest;
import org.computate.medicale.enUS.search.SearchResult;
import io.vertx.core.WorkerExecutor;
import io.vertx.ext.mail.MailClient;
import io.vertx.ext.mail.MailMessage;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import io.vertx.core.json.Json;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.commons.lang3.StringUtils;
import java.security.Principal;
import org.apache.commons.lang3.exception.ExceptionUtils;
import java.io.PrintWriter;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrDocument;
import java.util.Collection;
import java.math.BigDecimal;
import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Router;
import io.vertx.core.Vertx;
import io.vertx.ext.reactivestreams.ReactiveReadStream;
import io.vertx.ext.reactivestreams.ReactiveWriteStream;
import io.vertx.core.MultiMap;
import io.vertx.ext.auth.oauth2.OAuth2Auth;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.api.validation.HTTPRequestValidationHandler;
import io.vertx.ext.web.api.validation.ParameterTypeValidator;
import io.vertx.ext.web.api.validation.ValidationException;
import io.vertx.ext.web.api.contract.openapi3.OpenAPI3RouterFactory;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.Transaction;
import io.vertx.sqlclient.SqlConnection;
import io.vertx.sqlclient.Tuple;
import io.vertx.sqlclient.Row;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.sql.Timestamp;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.CaseInsensitiveHeaders;
import io.vertx.core.AsyncResult;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.api.OperationResponse;
import io.vertx.core.CompositeFuture;
import org.apache.http.client.utils.URLEncodedUtils;
import java.nio.charset.Charset;
import org.apache.http.NameValuePair;
import io.vertx.ext.web.api.OperationRequest;
import io.vertx.ext.auth.oauth2.KeycloakHelper;
import java.util.Optional;
import java.util.stream.Stream;
import java.net.URLDecoder;
import java.time.ZonedDateTime;
import org.apache.solr.common.util.SimpleOrderedMap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.computate.medicale.enUS.user.SiteUserEnUSApiServiceImpl;
import org.computate.medicale.enUS.search.SearchList;
import org.computate.medicale.enUS.writer.AllWriter;


/**
 * Translate: false
 * CanonicalName.frFR: org.computate.medicale.frFR.inscription.InscriptionMedicaleFrFRGenApiServiceImpl
 **/
public class MedicalEnrollmentEnUSGenApiServiceImpl implements MedicalEnrollmentEnUSGenApiService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(MedicalEnrollmentEnUSGenApiServiceImpl.class);

	protected static final String SERVICE_ADDRESS = "MedicalEnrollmentEnUSApiServiceImpl";

	protected SiteContextEnUS siteContext;

	public MedicalEnrollmentEnUSGenApiServiceImpl(SiteContextEnUS siteContext) {
		this.siteContext = siteContext;
	}

	// POST //

	@Override
	public void postMedicalEnrollment(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForMedicalEnrollment(siteContext, operationRequest, body);
		try {
			LOGGER.info(String.format("postMedicalEnrollment started. "));
			userMedicalEnrollment(siteRequest, b -> {
				if(b.succeeded()) {
					ApiRequest apiRequest = new ApiRequest();
					apiRequest.setRows(1);
					apiRequest.setNumFound(1L);
					apiRequest.setNumPATCH(0L);
					apiRequest.initDeepApiRequest(siteRequest);
					siteRequest.setApiRequest_(apiRequest);
					siteRequest.getVertx().eventBus().publish("websocketMedicalEnrollment", JsonObject.mapFrom(apiRequest).toString());
					postMedicalEnrollmentFuture(siteRequest, false, c -> {
						if(c.succeeded()) {
							MedicalEnrollment medicalEnrollment = c.result();
							apiRequest.setPk(medicalEnrollment.getPk());
							postMedicalEnrollmentResponse(medicalEnrollment, d -> {
									if(d.succeeded()) {
									eventHandler.handle(Future.succeededFuture(d.result()));
									LOGGER.info(String.format("postMedicalEnrollment succeeded. "));
								} else {
									LOGGER.error(String.format("postMedicalEnrollment failed. ", d.cause()));
									errorMedicalEnrollment(siteRequest, eventHandler, d);
								}
							});
						} else {
							LOGGER.error(String.format("postMedicalEnrollment failed. ", c.cause()));
							errorMedicalEnrollment(siteRequest, eventHandler, c);
						}
					});
				} else {
					LOGGER.error(String.format("postMedicalEnrollment failed. ", b.cause()));
					errorMedicalEnrollment(siteRequest, eventHandler, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("postMedicalEnrollment failed. ", ex));
			errorMedicalEnrollment(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public Future<MedicalEnrollment> postMedicalEnrollmentFuture(SiteRequestEnUS siteRequest, Boolean inheritPk, Handler<AsyncResult<MedicalEnrollment>> eventHandler) {
		Promise<MedicalEnrollment> promise = Promise.promise();
		try {
			sqlConnectionMedicalEnrollment(siteRequest, a -> {
				if(a.succeeded()) {
					sqlTransactionMedicalEnrollment(siteRequest, b -> {
						if(b.succeeded()) {
							createMedicalEnrollment(siteRequest, c -> {
								if(c.succeeded()) {
									MedicalEnrollment medicalEnrollment = c.result();
									sqlPOSTMedicalEnrollment(medicalEnrollment, inheritPk, d -> {
										if(d.succeeded()) {
											defineIndexMedicalEnrollment(medicalEnrollment, e -> {
												if(e.succeeded()) {
													ApiRequest apiRequest = siteRequest.getApiRequest_();
													if(apiRequest != null) {
														apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
														medicalEnrollment.apiRequestMedicalEnrollment();
														siteRequest.getVertx().eventBus().publish("websocketMedicalEnrollment", JsonObject.mapFrom(apiRequest).toString());
													}
													eventHandler.handle(Future.succeededFuture(medicalEnrollment));
													promise.complete(medicalEnrollment);
												} else {
													LOGGER.error(String.format("postMedicalEnrollmentFuture failed. ", e.cause()));
													eventHandler.handle(Future.failedFuture(e.cause()));
												}
											});
										} else {
											LOGGER.error(String.format("postMedicalEnrollmentFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOGGER.error(String.format("postMedicalEnrollmentFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOGGER.error(String.format("postMedicalEnrollmentFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOGGER.error(String.format("postMedicalEnrollmentFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("postMedicalEnrollmentFuture failed. ", e));
			errorMedicalEnrollment(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPOSTMedicalEnrollment(MedicalEnrollment o, Boolean inheritPk, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Transaction tx = siteRequest.getTx();
			Long pk = o.getPk();
			JsonObject jsonObject = siteRequest.getJsonObject();
			List<Future> futures = new ArrayList<>();

			if(siteRequest.getSessionId() != null) {
				futures.add(Future.future(a -> {
					tx.preparedQuery(SiteContextEnUS.SQL_setD
				, Tuple.of(pk, "sessionId", siteRequest.getSessionId())
							, b
					-> {
						if(b.succeeded())
							a.handle(Future.succeededFuture());
						else
							a.handle(Future.failedFuture(b.cause()));
					});
				}));
			}
			if(siteRequest.getUserId() != null) {
				futures.add(Future.future(a -> {
					tx.preparedQuery(SiteContextEnUS.SQL_setD
				, Tuple.of(pk, "userId", siteRequest.getUserId())
							, b
					-> {
						if(b.succeeded())
							a.handle(Future.succeededFuture());
						else
							a.handle(Future.failedFuture(b.cause()));
					});
				}));
			}
			if(siteRequest.getUserKey() != null) {
				futures.add(Future.future(a -> {
					tx.preparedQuery(SiteContextEnUS.SQL_setD
				, Tuple.of(pk, "userKey", siteRequest.getUserKey().toString())
							, b
					-> {
						if(b.succeeded())
							a.handle(Future.succeededFuture());
						else
							a.handle(Future.failedFuture(b.cause()));
					});
				}));

				JsonArray userKeys = Optional.ofNullable(jsonObject.getJsonArray("userKeys")).orElse(null);
				if(userKeys != null && !userKeys.contains(siteRequest.getUserKey()))
					userKeys.add(siteRequest.getUserKey().toString());
				else
					jsonObject.put("userKeys", new JsonArray().add(siteRequest.getUserKey().toString()));
			}

			if(jsonObject != null) {
				Set<String> entityVars = jsonObject.fieldNames();
				for(String entityVar : entityVars) {
					switch(entityVar) {
					case "inheritPk":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "inheritPk", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.inheritPk failed", b.cause())));
							});
						}));
						break;
					case "archived":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "archived", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.archived failed", b.cause())));
							});
						}));
						break;
					case "deleted":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "deleted", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.deleted failed", b.cause())));
							});
						}));
						break;
					case "patientKey":
						{
							Long l = Long.parseLong(jsonObject.getString(entityVar));
							if(l != null) {
								SearchList<MedicalPatient> searchList = new SearchList<MedicalPatient>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(MedicalPatient.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_addA
												, Tuple.of(l2, "enrollmentKeys", pk, "patientKey")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientKey failed", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("MedicalPatient");
									}
								}
							}
						}
						break;
					case "userKeys":
						for(Long l : jsonObject.getJsonArray(entityVar).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
							if(l != null) {
								SearchList<SiteUser> searchList = new SearchList<SiteUser>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(SiteUser.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_addA
												, Tuple.of(l2, "enrollmentKeys", pk, "userKeys")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.userKeys failed", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("SiteUser");
									}
								}
							}
						}
						break;
					case "patientCompleteName":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "patientCompleteName", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientCompleteName failed", b.cause())));
							});
						}));
						break;
					case "patientCompleteNamePreferred":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "patientCompleteNamePreferred", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientCompleteNamePreferred failed", b.cause())));
							});
						}));
						break;
					case "patientBirthDate":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "patientBirthDate", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientBirthDate failed", b.cause())));
							});
						}));
						break;
					case "clinicAddress":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "clinicAddress", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.clinicAddress failed", b.cause())));
							});
						}));
						break;
					case "enrollmentApproved":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentApproved", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentApproved failed", b.cause())));
							});
						}));
						break;
					case "enrollmentImmunizations":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentImmunizations", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentImmunizations failed", b.cause())));
							});
						}));
						break;
					case "familyAddress":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "familyAddress", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.familyAddress failed", b.cause())));
							});
						}));
						break;
					case "familyHowDoYouKnowTheClinic":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "familyHowDoYouKnowTheClinic", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.familyHowDoYouKnowTheClinic failed", b.cause())));
							});
						}));
						break;
					case "enrollmentSpecialConsiderations":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentSpecialConsiderations", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSpecialConsiderations failed", b.cause())));
							});
						}));
						break;
					case "patientMedicalConditions":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "patientMedicalConditions", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientMedicalConditions failed", b.cause())));
							});
						}));
						break;
					case "patientPreviousClinicsAttended":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "patientPreviousClinicsAttended", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientPreviousClinicsAttended failed", b.cause())));
							});
						}));
						break;
					case "patientDescription":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "patientDescription", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientDescription failed", b.cause())));
							});
						}));
						break;
					case "patientObjectives":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "patientObjectives", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientObjectives failed", b.cause())));
							});
						}));
						break;
					case "customerProfileId":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "customerProfileId", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.customerProfileId failed", b.cause())));
							});
						}));
						break;
					case "enrollmentSignature1":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentSignature1", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature1 failed", b.cause())));
							});
						}));
						break;
					case "enrollmentSignature2":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentSignature2", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature2 failed", b.cause())));
							});
						}));
						break;
					case "enrollmentSignature3":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentSignature3", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature3 failed", b.cause())));
							});
						}));
						break;
					case "enrollmentSignature4":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentSignature4", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature4 failed", b.cause())));
							});
						}));
						break;
					case "enrollmentSignature5":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentSignature5", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature5 failed", b.cause())));
							});
						}));
						break;
					case "enrollmentSignature6":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentSignature6", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature6 failed", b.cause())));
							});
						}));
						break;
					case "enrollmentSignature7":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentSignature7", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature7 failed", b.cause())));
							});
						}));
						break;
					case "enrollmentSignature8":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentSignature8", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature8 failed", b.cause())));
							});
						}));
						break;
					case "enrollmentSignature9":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentSignature9", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature9 failed", b.cause())));
							});
						}));
						break;
					case "enrollmentSignature10":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentSignature10", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature10 failed", b.cause())));
							});
						}));
						break;
					case "enrollmentDate1":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentDate1", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate1 failed", b.cause())));
							});
						}));
						break;
					case "enrollmentDate2":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentDate2", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate2 failed", b.cause())));
							});
						}));
						break;
					case "enrollmentDate3":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentDate3", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate3 failed", b.cause())));
							});
						}));
						break;
					case "enrollmentDate4":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentDate4", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate4 failed", b.cause())));
							});
						}));
						break;
					case "enrollmentDate5":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentDate5", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate5 failed", b.cause())));
							});
						}));
						break;
					case "enrollmentDate6":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentDate6", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate6 failed", b.cause())));
							});
						}));
						break;
					case "enrollmentDate7":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentDate7", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate7 failed", b.cause())));
							});
						}));
						break;
					case "enrollmentDate8":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentDate8", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate8 failed", b.cause())));
							});
						}));
						break;
					case "enrollmentDate9":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentDate9", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate9 failed", b.cause())));
							});
						}));
						break;
					case "enrollmentDate10":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentDate10", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate10 failed", b.cause())));
							});
						}));
						break;
					}
				}
			}
			CompositeFuture.all(futures).setHandler( a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture());
				} else {
					LOGGER.error(String.format("sqlPOSTMedicalEnrollment failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPOSTMedicalEnrollment failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void postMedicalEnrollmentResponse(MedicalEnrollment medicalEnrollment, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = medicalEnrollment.getSiteRequest_();
		try {
			response200POSTMedicalEnrollment(medicalEnrollment, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("postMedicalEnrollmentResponse failed. ", a.cause()));
					errorMedicalEnrollment(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("postMedicalEnrollmentResponse failed. ", ex));
			errorMedicalEnrollment(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200POSTMedicalEnrollment(MedicalEnrollment o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			JsonObject json = JsonObject.mapFrom(o);
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200POSTMedicalEnrollment failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTImport //

	@Override
	public void putimportMedicalEnrollment(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForMedicalEnrollment(siteContext, operationRequest, body);
		try {
			LOGGER.info(String.format("putimportMedicalEnrollment started. "));
			userMedicalEnrollment(siteRequest, b -> {
				if(b.succeeded()) {
					putimportMedicalEnrollmentResponse(siteRequest, c -> {
						if(c.succeeded()) {
							eventHandler.handle(Future.succeededFuture(c.result()));
							WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
							workerExecutor.executeBlocking(
								blockingCodeHandler -> {
									try {
										ApiRequest apiRequest = new ApiRequest();
										JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
										apiRequest.setRows(jsonArray.size());
										apiRequest.setNumFound(new Integer(jsonArray.size()).longValue());
										apiRequest.setNumPATCH(0L);
										apiRequest.initDeepApiRequest(siteRequest);
										siteRequest.setApiRequest_(apiRequest);
										siteRequest.getVertx().eventBus().publish("websocketMedicalEnrollment", JsonObject.mapFrom(apiRequest).toString());
										varsMedicalEnrollment(siteRequest, d -> {
											if(d.succeeded()) {
												listPUTImportMedicalEnrollment(apiRequest, siteRequest, e -> {
													if(e.succeeded()) {
														putimportMedicalEnrollmentResponse(siteRequest, f -> {
															if(e.succeeded()) {
																LOGGER.info(String.format("putimportMedicalEnrollment succeeded. "));
																blockingCodeHandler.handle(Future.succeededFuture(e.result()));
															} else {
																LOGGER.error(String.format("putimportMedicalEnrollment failed. ", f.cause()));
																errorMedicalEnrollment(siteRequest, null, f);
															}
														});
													} else {
														LOGGER.error(String.format("putimportMedicalEnrollment failed. ", e.cause()));
														errorMedicalEnrollment(siteRequest, null, e);
													}
												});
											} else {
												LOGGER.error(String.format("putimportMedicalEnrollment failed. ", d.cause()));
												errorMedicalEnrollment(siteRequest, null, d);
											}
										});
									} catch(Exception ex) {
										LOGGER.error(String.format("putimportMedicalEnrollment failed. ", ex));
										errorMedicalEnrollment(siteRequest, null, Future.failedFuture(ex));
									}
								}, resultHandler -> {
								}
							);
						} else {
							LOGGER.error(String.format("putimportMedicalEnrollment failed. ", c.cause()));
							errorMedicalEnrollment(siteRequest, eventHandler, c);
						}
					});
				} else {
					LOGGER.error(String.format("putimportMedicalEnrollment failed. ", b.cause()));
					errorMedicalEnrollment(siteRequest, eventHandler, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putimportMedicalEnrollment failed. ", ex));
			errorMedicalEnrollment(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void listPUTImportMedicalEnrollment(ApiRequest apiRequest, SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
		try {
			jsonArray.forEach(obj -> {
				JsonObject json = (JsonObject)obj;

				json.put("inheritPk", json.getValue("pk"));

				json.put("created", json.getValue("created"));

				SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForMedicalEnrollment(siteContext, siteRequest.getOperationRequest(), json);
				siteRequest2.setApiRequest_(apiRequest);
				siteRequest2.setRequestVars(siteRequest.getRequestVars());

				SearchList<MedicalEnrollment> searchList = new SearchList<MedicalEnrollment>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(MedicalEnrollment.class);
				searchList.addFilterQuery("inheritPk_indexed_long:" + json.getString("pk"));
				searchList.initDeepForClass(siteRequest2);

				if(searchList.size() == 1) {
					MedicalEnrollment o = searchList.getList().stream().findFirst().orElse(null);
					JsonObject json2 = new JsonObject();
					for(String f : json.fieldNames()) {
						json2.put("set" + StringUtils.capitalize(f), json.getValue(f));
					}
					if(o != null) {
						for(String f : Optional.ofNullable(o.getSaves()).orElse(new ArrayList<>())) {
							if(!json.fieldNames().contains(f))
								json2.putNull("set" + StringUtils.capitalize(f));
						}
						siteRequest2.setJsonObject(json2);
						futures.add(
							patchMedicalEnrollmentFuture(o, true, a -> {
								if(a.succeeded()) {
								} else {
									LOGGER.error(String.format("listPUTImportMedicalEnrollment failed. ", a.cause()));
									errorMedicalEnrollment(siteRequest2, eventHandler, a);
								}
							})
						);
					}
				} else {
					futures.add(
						postMedicalEnrollmentFuture(siteRequest2, true, a -> {
							if(a.succeeded()) {
							} else {
								LOGGER.error(String.format("listPUTImportMedicalEnrollment failed. ", a.cause()));
								errorMedicalEnrollment(siteRequest2, eventHandler, a);
							}
						})
					);
				}
			});
			CompositeFuture.all(futures).setHandler( a -> {
				if(a.succeeded()) {
					apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
					response200PUTImportMedicalEnrollment(siteRequest, eventHandler);
				} else {
					LOGGER.error(String.format("listPUTImportMedicalEnrollment failed. ", a.cause()));
					errorMedicalEnrollment(apiRequest.getSiteRequest_(), eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("listPUTImportMedicalEnrollment failed. ", ex));
			errorMedicalEnrollment(siteRequest, null, Future.failedFuture(ex));
		}
	}

	public void putimportMedicalEnrollmentResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PUTImportMedicalEnrollment(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("putimportMedicalEnrollmentResponse failed. ", a.cause()));
					errorMedicalEnrollment(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putimportMedicalEnrollmentResponse failed. ", ex));
			errorMedicalEnrollment(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTImportMedicalEnrollment(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PUTImportMedicalEnrollment failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTMerge //

	@Override
	public void putmergeMedicalEnrollment(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForMedicalEnrollment(siteContext, operationRequest, body);
		try {
			LOGGER.info(String.format("putmergeMedicalEnrollment started. "));
			userMedicalEnrollment(siteRequest, b -> {
				if(b.succeeded()) {
					putmergeMedicalEnrollmentResponse(siteRequest, c -> {
						if(c.succeeded()) {
							eventHandler.handle(Future.succeededFuture(c.result()));
							WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
							workerExecutor.executeBlocking(
								blockingCodeHandler -> {
									try {
										ApiRequest apiRequest = new ApiRequest();
										JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
										apiRequest.setRows(jsonArray.size());
										apiRequest.setNumFound(new Integer(jsonArray.size()).longValue());
										apiRequest.setNumPATCH(0L);
										apiRequest.initDeepApiRequest(siteRequest);
										siteRequest.setApiRequest_(apiRequest);
										siteRequest.getVertx().eventBus().publish("websocketMedicalEnrollment", JsonObject.mapFrom(apiRequest).toString());
										varsMedicalEnrollment(siteRequest, d -> {
											if(d.succeeded()) {
												listPUTMergeMedicalEnrollment(apiRequest, siteRequest, e -> {
													if(e.succeeded()) {
														putmergeMedicalEnrollmentResponse(siteRequest, f -> {
															if(e.succeeded()) {
																LOGGER.info(String.format("putmergeMedicalEnrollment succeeded. "));
																blockingCodeHandler.handle(Future.succeededFuture(e.result()));
															} else {
																LOGGER.error(String.format("putmergeMedicalEnrollment failed. ", f.cause()));
																errorMedicalEnrollment(siteRequest, null, f);
															}
														});
													} else {
														LOGGER.error(String.format("putmergeMedicalEnrollment failed. ", e.cause()));
														errorMedicalEnrollment(siteRequest, null, e);
													}
												});
											} else {
												LOGGER.error(String.format("putmergeMedicalEnrollment failed. ", d.cause()));
												errorMedicalEnrollment(siteRequest, null, d);
											}
										});
									} catch(Exception ex) {
										LOGGER.error(String.format("putmergeMedicalEnrollment failed. ", ex));
										errorMedicalEnrollment(siteRequest, null, Future.failedFuture(ex));
									}
								}, resultHandler -> {
								}
							);
						} else {
							LOGGER.error(String.format("putmergeMedicalEnrollment failed. ", c.cause()));
							errorMedicalEnrollment(siteRequest, eventHandler, c);
						}
					});
				} else {
					LOGGER.error(String.format("putmergeMedicalEnrollment failed. ", b.cause()));
					errorMedicalEnrollment(siteRequest, eventHandler, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putmergeMedicalEnrollment failed. ", ex));
			errorMedicalEnrollment(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void listPUTMergeMedicalEnrollment(ApiRequest apiRequest, SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
		try {
			jsonArray.forEach(obj -> {
				JsonObject json = (JsonObject)obj;

				json.put("inheritPk", json.getValue("pk"));

				SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForMedicalEnrollment(siteContext, siteRequest.getOperationRequest(), json);
				siteRequest2.setApiRequest_(apiRequest);
				siteRequest2.setRequestVars(siteRequest.getRequestVars());

				SearchList<MedicalEnrollment> searchList = new SearchList<MedicalEnrollment>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(MedicalEnrollment.class);
				searchList.addFilterQuery("pk_indexed_long:" + json.getString("pk"));
				searchList.initDeepForClass(siteRequest2);

				if(searchList.size() == 1) {
					MedicalEnrollment o = searchList.getList().stream().findFirst().orElse(null);
					JsonObject json2 = new JsonObject();
					for(String f : json.fieldNames()) {
						json2.put("set" + StringUtils.capitalize(f), json.getValue(f));
					}
					if(o != null) {
						for(String f : Optional.ofNullable(o.getSaves()).orElse(new ArrayList<>())) {
							if(!json.fieldNames().contains(f))
								json2.putNull("set" + StringUtils.capitalize(f));
						}
						siteRequest2.setJsonObject(json2);
						futures.add(
							patchMedicalEnrollmentFuture(o, false, a -> {
								if(a.succeeded()) {
								} else {
									LOGGER.error(String.format("listPUTMergeMedicalEnrollment failed. ", a.cause()));
									errorMedicalEnrollment(siteRequest2, eventHandler, a);
								}
							})
						);
					}
				} else {
					futures.add(
						postMedicalEnrollmentFuture(siteRequest2, false, a -> {
							if(a.succeeded()) {
							} else {
								LOGGER.error(String.format("listPUTMergeMedicalEnrollment failed. ", a.cause()));
								errorMedicalEnrollment(siteRequest2, eventHandler, a);
							}
						})
					);
				}
			});
			CompositeFuture.all(futures).setHandler( a -> {
				if(a.succeeded()) {
					apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
					response200PUTMergeMedicalEnrollment(siteRequest, eventHandler);
				} else {
					LOGGER.error(String.format("listPUTMergeMedicalEnrollment failed. ", a.cause()));
					errorMedicalEnrollment(apiRequest.getSiteRequest_(), eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("listPUTMergeMedicalEnrollment failed. ", ex));
			errorMedicalEnrollment(siteRequest, null, Future.failedFuture(ex));
		}
	}

	public void putmergeMedicalEnrollmentResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PUTMergeMedicalEnrollment(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("putmergeMedicalEnrollmentResponse failed. ", a.cause()));
					errorMedicalEnrollment(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putmergeMedicalEnrollmentResponse failed. ", ex));
			errorMedicalEnrollment(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTMergeMedicalEnrollment(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PUTMergeMedicalEnrollment failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTCopy //

	@Override
	public void putcopyMedicalEnrollment(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForMedicalEnrollment(siteContext, operationRequest, body);
		try {
			LOGGER.info(String.format("putcopyMedicalEnrollment started. "));
			userMedicalEnrollment(siteRequest, b -> {
				if(b.succeeded()) {
					putcopyMedicalEnrollmentResponse(siteRequest, c -> {
						if(c.succeeded()) {
							eventHandler.handle(Future.succeededFuture(c.result()));
							WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
							workerExecutor.executeBlocking(
								blockingCodeHandler -> {
									try {
										aSearchMedicalEnrollment(siteRequest, false, true, "/api/enrollment/copy", "PUTCopy", d -> {
											if(d.succeeded()) {
												SearchList<MedicalEnrollment> listMedicalEnrollment = d.result();
												ApiRequest apiRequest = new ApiRequest();
												apiRequest.setRows(listMedicalEnrollment.getRows());
												apiRequest.setNumFound(listMedicalEnrollment.getQueryResponse().getResults().getNumFound());
												apiRequest.setNumPATCH(0L);
												apiRequest.initDeepApiRequest(siteRequest);
												siteRequest.setApiRequest_(apiRequest);
												siteRequest.getVertx().eventBus().publish("websocketMedicalEnrollment", JsonObject.mapFrom(apiRequest).toString());
												try {
													listPUTCopyMedicalEnrollment(apiRequest, listMedicalEnrollment, e -> {
														if(e.succeeded()) {
															putcopyMedicalEnrollmentResponse(siteRequest, f -> {
																if(f.succeeded()) {
																	LOGGER.info(String.format("putcopyMedicalEnrollment succeeded. "));
																	blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																} else {
																	LOGGER.error(String.format("putcopyMedicalEnrollment failed. ", f.cause()));
																	errorMedicalEnrollment(siteRequest, null, f);
																}
															});
														} else {
															LOGGER.error(String.format("putcopyMedicalEnrollment failed. ", e.cause()));
															errorMedicalEnrollment(siteRequest, null, e);
														}
													});
												} catch(Exception ex) {
													LOGGER.error(String.format("putcopyMedicalEnrollment failed. ", ex));
													errorMedicalEnrollment(siteRequest, null, Future.failedFuture(ex));
												}
											} else {
												LOGGER.error(String.format("putcopyMedicalEnrollment failed. ", d.cause()));
												errorMedicalEnrollment(siteRequest, null, d);
											}
										});
									} catch(Exception ex) {
										LOGGER.error(String.format("putcopyMedicalEnrollment failed. ", ex));
										errorMedicalEnrollment(siteRequest, null, Future.failedFuture(ex));
									}
								}, resultHandler -> {
								}
							);
						} else {
							LOGGER.error(String.format("putcopyMedicalEnrollment failed. ", c.cause()));
							errorMedicalEnrollment(siteRequest, eventHandler, c);
						}
					});
				} else {
					LOGGER.error(String.format("putcopyMedicalEnrollment failed. ", b.cause()));
					errorMedicalEnrollment(siteRequest, eventHandler, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putcopyMedicalEnrollment failed. ", ex));
			errorMedicalEnrollment(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void listPUTCopyMedicalEnrollment(ApiRequest apiRequest, SearchList<MedicalEnrollment> listMedicalEnrollment, Handler<AsyncResult<OperationResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listMedicalEnrollment.getSiteRequest_();
		listMedicalEnrollment.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForMedicalEnrollment(siteContext, siteRequest.getOperationRequest(), siteRequest.getJsonObject());
			siteRequest2.setApiRequest_(siteRequest.getApiRequest_());
			o.setSiteRequest_(siteRequest2);
			futures.add(
				putcopyMedicalEnrollmentFuture(siteRequest, JsonObject.mapFrom(o), a -> {
					if(a.succeeded()) {
					} else {
						LOGGER.error(String.format("listPUTCopyMedicalEnrollment failed. ", a.cause()));
						errorMedicalEnrollment(siteRequest, eventHandler, a);
					}
				})
			);
		});
		CompositeFuture.all(futures).setHandler( a -> {
			if(a.succeeded()) {
				apiRequest.setNumPATCH(apiRequest.getNumPATCH() + listMedicalEnrollment.size());
				if(listMedicalEnrollment.next()) {
					listPUTCopyMedicalEnrollment(apiRequest, listMedicalEnrollment, eventHandler);
				} else {
					response200PUTCopyMedicalEnrollment(siteRequest, eventHandler);
				}
			} else {
				LOGGER.error(String.format("listPUTCopyMedicalEnrollment failed. ", a.cause()));
				errorMedicalEnrollment(listMedicalEnrollment.getSiteRequest_(), eventHandler, a);
			}
		});
	}

	public Future<MedicalEnrollment> putcopyMedicalEnrollmentFuture(SiteRequestEnUS siteRequest, JsonObject jsonObject, Handler<AsyncResult<MedicalEnrollment>> eventHandler) {
		Promise<MedicalEnrollment> promise = Promise.promise();
		try {

			jsonObject.put("saves", Optional.ofNullable(jsonObject.getJsonArray("saves")).orElse(new JsonArray()));
			JsonObject jsonPatch = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonObject("patch")).orElse(new JsonObject());
			jsonPatch.stream().forEach(o -> {
				jsonObject.put(o.getKey(), o.getValue());
				jsonObject.getJsonArray("saves").add(o.getKey());
			});

			sqlConnectionMedicalEnrollment(siteRequest, a -> {
				if(a.succeeded()) {
					sqlTransactionMedicalEnrollment(siteRequest, b -> {
						if(b.succeeded()) {
							createMedicalEnrollment(siteRequest, c -> {
								if(c.succeeded()) {
									MedicalEnrollment medicalEnrollment = c.result();
									sqlPUTCopyMedicalEnrollment(medicalEnrollment, jsonObject, d -> {
										if(d.succeeded()) {
											defineIndexMedicalEnrollment(medicalEnrollment, e -> {
												if(e.succeeded()) {
													ApiRequest apiRequest = siteRequest.getApiRequest_();
													if(apiRequest != null) {
														apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
														if(apiRequest.getNumFound() == 1L) {
															medicalEnrollment.apiRequestMedicalEnrollment();
														}
														siteRequest.getVertx().eventBus().publish("websocketMedicalEnrollment", JsonObject.mapFrom(apiRequest).toString());
													}
													eventHandler.handle(Future.succeededFuture(medicalEnrollment));
													promise.complete(medicalEnrollment);
												} else {
													LOGGER.error(String.format("putcopyMedicalEnrollmentFuture failed. ", e.cause()));
													eventHandler.handle(Future.failedFuture(e.cause()));
												}
											});
										} else {
											LOGGER.error(String.format("putcopyMedicalEnrollmentFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOGGER.error(String.format("putcopyMedicalEnrollmentFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOGGER.error(String.format("putcopyMedicalEnrollmentFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOGGER.error(String.format("putcopyMedicalEnrollmentFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("putcopyMedicalEnrollmentFuture failed. ", e));
			errorMedicalEnrollment(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPUTCopyMedicalEnrollment(MedicalEnrollment o, JsonObject jsonObject, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Transaction tx = siteRequest.getTx();
			Long pk = o.getPk();
			List<Future> futures = new ArrayList<>();

			if(jsonObject != null) {
				JsonArray entityVars = jsonObject.getJsonArray("saves");
				for(Integer i = 0; i < entityVars.size(); i++) {
					String entityVar = entityVars.getString(i);
					switch(entityVar) {
					case "inheritPk":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "inheritPk", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.inheritPk failed", b.cause())));
							});
						}));
						break;
					case "archived":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "archived", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.archived failed", b.cause())));
							});
						}));
						break;
					case "deleted":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "deleted", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.deleted failed", b.cause())));
							});
						}));
						break;
					case "patientKey":
							{
						Long l = Long.parseLong(jsonObject.getString(entityVar));
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_addA
									, Tuple.of(l, "enrollmentKeys", pk, "patientKey")
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientKey failed", b.cause())));
							});
						}));
						}
						break;
					case "userKeys":
						for(Long l : jsonObject.getJsonArray(entityVar).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_addA
										, Tuple.of(l, "enrollmentKeys", pk, "userKeys")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.userKeys failed", b.cause())));
								});
							}));
							if(!pks.contains(l)) {
								pks.add(l);
								classes.add("SiteUser");
							}
						}
						break;
					case "patientCompleteName":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "patientCompleteName", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientCompleteName failed", b.cause())));
							});
						}));
						break;
					case "patientCompleteNamePreferred":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "patientCompleteNamePreferred", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientCompleteNamePreferred failed", b.cause())));
							});
						}));
						break;
					case "patientBirthDate":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "patientBirthDate", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientBirthDate failed", b.cause())));
							});
						}));
						break;
					case "clinicAddress":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "clinicAddress", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.clinicAddress failed", b.cause())));
							});
						}));
						break;
					case "enrollmentApproved":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentApproved", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentApproved failed", b.cause())));
							});
						}));
						break;
					case "enrollmentImmunizations":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentImmunizations", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentImmunizations failed", b.cause())));
							});
						}));
						break;
					case "familyAddress":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "familyAddress", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.familyAddress failed", b.cause())));
							});
						}));
						break;
					case "familyHowDoYouKnowTheClinic":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "familyHowDoYouKnowTheClinic", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.familyHowDoYouKnowTheClinic failed", b.cause())));
							});
						}));
						break;
					case "enrollmentSpecialConsiderations":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentSpecialConsiderations", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSpecialConsiderations failed", b.cause())));
							});
						}));
						break;
					case "patientMedicalConditions":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "patientMedicalConditions", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientMedicalConditions failed", b.cause())));
							});
						}));
						break;
					case "patientPreviousClinicsAttended":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "patientPreviousClinicsAttended", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientPreviousClinicsAttended failed", b.cause())));
							});
						}));
						break;
					case "patientDescription":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "patientDescription", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientDescription failed", b.cause())));
							});
						}));
						break;
					case "patientObjectives":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "patientObjectives", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientObjectives failed", b.cause())));
							});
						}));
						break;
					case "customerProfileId":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "customerProfileId", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.customerProfileId failed", b.cause())));
							});
						}));
						break;
					case "enrollmentSignature1":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentSignature1", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature1 failed", b.cause())));
							});
						}));
						break;
					case "enrollmentSignature2":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentSignature2", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature2 failed", b.cause())));
							});
						}));
						break;
					case "enrollmentSignature3":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentSignature3", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature3 failed", b.cause())));
							});
						}));
						break;
					case "enrollmentSignature4":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentSignature4", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature4 failed", b.cause())));
							});
						}));
						break;
					case "enrollmentSignature5":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentSignature5", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature5 failed", b.cause())));
							});
						}));
						break;
					case "enrollmentSignature6":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentSignature6", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature6 failed", b.cause())));
							});
						}));
						break;
					case "enrollmentSignature7":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentSignature7", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature7 failed", b.cause())));
							});
						}));
						break;
					case "enrollmentSignature8":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentSignature8", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature8 failed", b.cause())));
							});
						}));
						break;
					case "enrollmentSignature9":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentSignature9", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature9 failed", b.cause())));
							});
						}));
						break;
					case "enrollmentSignature10":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentSignature10", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature10 failed", b.cause())));
							});
						}));
						break;
					case "enrollmentDate1":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentDate1", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate1 failed", b.cause())));
							});
						}));
						break;
					case "enrollmentDate2":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentDate2", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate2 failed", b.cause())));
							});
						}));
						break;
					case "enrollmentDate3":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentDate3", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate3 failed", b.cause())));
							});
						}));
						break;
					case "enrollmentDate4":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentDate4", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate4 failed", b.cause())));
							});
						}));
						break;
					case "enrollmentDate5":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentDate5", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate5 failed", b.cause())));
							});
						}));
						break;
					case "enrollmentDate6":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentDate6", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate6 failed", b.cause())));
							});
						}));
						break;
					case "enrollmentDate7":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentDate7", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate7 failed", b.cause())));
							});
						}));
						break;
					case "enrollmentDate8":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentDate8", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate8 failed", b.cause())));
							});
						}));
						break;
					case "enrollmentDate9":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentDate9", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate9 failed", b.cause())));
							});
						}));
						break;
					case "enrollmentDate10":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "enrollmentDate10", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate10 failed", b.cause())));
							});
						}));
						break;
					}
				}
			}
			CompositeFuture.all(futures).setHandler( a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture());
				} else {
					LOGGER.error(String.format("sqlPUTCopyMedicalEnrollment failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPUTCopyMedicalEnrollment failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void putcopyMedicalEnrollmentResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PUTCopyMedicalEnrollment(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("putcopyMedicalEnrollmentResponse failed. ", a.cause()));
					errorMedicalEnrollment(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putcopyMedicalEnrollmentResponse failed. ", ex));
			errorMedicalEnrollment(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTCopyMedicalEnrollment(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PUTCopyMedicalEnrollment failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PATCH //

	@Override
	public void patchMedicalEnrollment(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForMedicalEnrollment(siteContext, operationRequest, body);
		try {
			LOGGER.info(String.format("patchMedicalEnrollment started. "));
			userMedicalEnrollment(siteRequest, b -> {
				if(b.succeeded()) {
					patchMedicalEnrollmentResponse(siteRequest, c -> {
						if(c.succeeded()) {
							eventHandler.handle(Future.succeededFuture(c.result()));
							WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
							workerExecutor.executeBlocking(
								blockingCodeHandler -> {
									try {
										aSearchMedicalEnrollment(siteRequest, false, true, "/api/enrollment", "PATCH", d -> {
											if(d.succeeded()) {
												SearchList<MedicalEnrollment> listMedicalEnrollment = d.result();
												ApiRequest apiRequest = new ApiRequest();
												apiRequest.setRows(listMedicalEnrollment.getRows());
												apiRequest.setNumFound(listMedicalEnrollment.getQueryResponse().getResults().getNumFound());
												apiRequest.setNumPATCH(0L);
												apiRequest.initDeepApiRequest(siteRequest);
												siteRequest.setApiRequest_(apiRequest);
												siteRequest.getVertx().eventBus().publish("websocketMedicalEnrollment", JsonObject.mapFrom(apiRequest).toString());
												SimpleOrderedMap facets = (SimpleOrderedMap)Optional.ofNullable(listMedicalEnrollment.getQueryResponse()).map(QueryResponse::getResponse).map(r -> r.get("facets")).orElse(null);
												Date date = null;
												if(facets != null)
													date = (Date)facets.get("max_modified");
												String dt;
												if(date == null)
													dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(ZonedDateTime.now().toInstant(), ZoneId.of("UTC")).minusNanos(1000));
												else
													dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC")));
												listMedicalEnrollment.addFilterQuery(String.format("modified_indexed_date:[* TO %s]", dt));

												try {
													listPATCHMedicalEnrollment(apiRequest, listMedicalEnrollment, dt, e -> {
														if(e.succeeded()) {
															patchMedicalEnrollmentResponse(siteRequest, f -> {
																if(f.succeeded()) {
																	LOGGER.info(String.format("patchMedicalEnrollment succeeded. "));
																	blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																} else {
																	LOGGER.error(String.format("patchMedicalEnrollment failed. ", f.cause()));
																	errorMedicalEnrollment(siteRequest, null, f);
																}
															});
														} else {
															LOGGER.error(String.format("patchMedicalEnrollment failed. ", e.cause()));
															errorMedicalEnrollment(siteRequest, null, e);
														}
													});
												} catch(Exception ex) {
													LOGGER.error(String.format("patchMedicalEnrollment failed. ", ex));
													errorMedicalEnrollment(siteRequest, null, Future.failedFuture(ex));
												}
											} else {
												LOGGER.error(String.format("patchMedicalEnrollment failed. ", d.cause()));
												errorMedicalEnrollment(siteRequest, null, d);
											}
										});
									} catch(Exception ex) {
										LOGGER.error(String.format("patchMedicalEnrollment failed. ", ex));
										errorMedicalEnrollment(siteRequest, null, Future.failedFuture(ex));
									}
								}, resultHandler -> {
								}
							);
						} else {
							LOGGER.error(String.format("patchMedicalEnrollment failed. ", c.cause()));
							errorMedicalEnrollment(siteRequest, eventHandler, c);
						}
					});
				} else {
					LOGGER.error(String.format("patchMedicalEnrollment failed. ", b.cause()));
					errorMedicalEnrollment(siteRequest, eventHandler, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("patchMedicalEnrollment failed. ", ex));
			errorMedicalEnrollment(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void listPATCHMedicalEnrollment(ApiRequest apiRequest, SearchList<MedicalEnrollment> listMedicalEnrollment, String dt, Handler<AsyncResult<OperationResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listMedicalEnrollment.getSiteRequest_();
		listMedicalEnrollment.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForMedicalEnrollment(siteContext, siteRequest.getOperationRequest(), siteRequest.getJsonObject());
			siteRequest2.setApiRequest_(siteRequest.getApiRequest_());
			o.setSiteRequest_(siteRequest2);
			futures.add(
				patchMedicalEnrollmentFuture(o, false, a -> {
					if(a.succeeded()) {
					} else {
						errorMedicalEnrollment(siteRequest2, eventHandler, a);
					}
				})
			);
		});
		CompositeFuture.all(futures).setHandler( a -> {
			if(a.succeeded()) {
				if(listMedicalEnrollment.next(dt)) {
					listPATCHMedicalEnrollment(apiRequest, listMedicalEnrollment, dt, eventHandler);
				} else {
					response200PATCHMedicalEnrollment(siteRequest, eventHandler);
				}
			} else {
				LOGGER.error(String.format("listPATCHMedicalEnrollment failed. ", a.cause()));
				errorMedicalEnrollment(listMedicalEnrollment.getSiteRequest_(), eventHandler, a);
			}
		});
	}

	public Future<MedicalEnrollment> patchMedicalEnrollmentFuture(MedicalEnrollment o, Boolean inheritPk, Handler<AsyncResult<MedicalEnrollment>> eventHandler) {
		Promise<MedicalEnrollment> promise = Promise.promise();
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			if(apiRequest != null && apiRequest.getNumFound() == 1L) {
				apiRequest.setOriginal(o);
				apiRequest.setPk(o.getPk());
			}
			sqlConnectionMedicalEnrollment(siteRequest, a -> {
				if(a.succeeded()) {
					sqlTransactionMedicalEnrollment(siteRequest, b -> {
						if(b.succeeded()) {
							sqlPATCHMedicalEnrollment(o, inheritPk, c -> {
								if(c.succeeded()) {
									MedicalEnrollment medicalEnrollment = c.result();
									defineIndexMedicalEnrollment(medicalEnrollment, d -> {
										if(d.succeeded()) {
											if(apiRequest != null) {
												apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
												if(apiRequest.getNumFound() == 1L) {
													medicalEnrollment.apiRequestMedicalEnrollment();
												}
												siteRequest.getVertx().eventBus().publish("websocketMedicalEnrollment", JsonObject.mapFrom(apiRequest).toString());
											}
											eventHandler.handle(Future.succeededFuture(medicalEnrollment));
											promise.complete(medicalEnrollment);
										} else {
											LOGGER.error(String.format("patchMedicalEnrollmentFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOGGER.error(String.format("patchMedicalEnrollmentFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOGGER.error(String.format("patchMedicalEnrollmentFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOGGER.error(String.format("patchMedicalEnrollmentFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("patchMedicalEnrollmentFuture failed. ", e));
			errorMedicalEnrollment(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPATCHMedicalEnrollment(MedicalEnrollment o, Boolean inheritPk, Handler<AsyncResult<MedicalEnrollment>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Transaction tx = siteRequest.getTx();
			Long pk = o.getPk();
			JsonObject jsonObject = siteRequest.getJsonObject();
			Set<String> methodNames = jsonObject.fieldNames();
			MedicalEnrollment o2 = new MedicalEnrollment();
			List<Future> futures = new ArrayList<>();

			if(o.getUserId() == null && siteRequest.getUserId() != null) {
				futures.add(Future.future(a -> {
					tx.preparedQuery(SiteContextEnUS.SQL_setD
							, Tuple.of(pk, "userId", siteRequest.getUserId())
							, b
					-> {
						if(b.succeeded())
							a.handle(Future.succeededFuture());
						else
							a.handle(Future.failedFuture(b.cause()));
					});
				}));
			}
			if(o.getUserKey() == null && siteRequest.getUserKey() != null) {
				futures.add(Future.future(a -> {
					tx.preparedQuery(SiteContextEnUS.SQL_setD
				, Tuple.of(pk, "userKey", siteRequest.getUserKey().toString())
							, b
					-> {
						if(b.succeeded())
							a.handle(Future.succeededFuture());
						else
							a.handle(Future.failedFuture(b.cause()));
					});
				}));

				JsonArray userKeys = Optional.ofNullable(jsonObject.getJsonArray("addUserKeys")).orElse(null);
				if(userKeys != null && !userKeys.contains(siteRequest.getUserKey()))
					userKeys.add(siteRequest.getUserKey().toString());
				else
					jsonObject.put("addUserKeys", new JsonArray().add(siteRequest.getUserKey().toString()));
			}

			for(String methodName : methodNames) {
				switch(methodName) {
					case "setInheritPk":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "inheritPk")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.inheritPk failed", b.cause())));
								});
							}));
						} else {
							o2.setInheritPk(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "inheritPk", o2.jsonInheritPk())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.inheritPk failed", b.cause())));
								});
							}));
						}
						break;
					case "setArchived":
						if(jsonObject.getBoolean(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "archived")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.archived failed", b.cause())));
								});
							}));
						} else {
							o2.setArchived(jsonObject.getBoolean(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "archived", o2.jsonArchived())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.archived failed", b.cause())));
								});
							}));
						}
						break;
					case "setDeleted":
						if(jsonObject.getBoolean(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "deleted")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.deleted failed", b.cause())));
								});
							}));
						} else {
							o2.setDeleted(jsonObject.getBoolean(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "deleted", o2.jsonDeleted())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.deleted failed", b.cause())));
								});
							}));
						}
						break;
					case "setPatientKey":
						{
							Long l = o2.getPatientKey();
							if(l != null && !l.equals(o.getPatientKey())) {
								SearchList<MedicalPatient> searchList = new SearchList<MedicalPatient>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(MedicalPatient.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null) {
									o2.setPatientKey(jsonObject.getString(methodName));
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_addA
												, Tuple.of(l2, "enrollmentKeys", pk, "patientKey")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientKey failed", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("MedicalPatient");
									}
								}
							}
						}
						break;
					case "removePatientKey":
						{
							Long l = o2.getPatientKey();
							if(l != null) {
								SearchList<MedicalPatient> searchList = new SearchList<MedicalPatient>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(MedicalPatient.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null && l2.equals(o.getPatientKey())) {
									o2.setPatientKey(jsonObject.getString(methodName));
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_removeA
												, Tuple.of(l2, "enrollmentKeys", pk, "patientKey")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientKey failed", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("MedicalPatient");
									}
								}
							}
						}
						break;
					case "addUserKeys":
						{
							Long l = Long.parseLong(jsonObject.getString(methodName));
							if(l != null) {
								SearchList<SiteUser> searchList = new SearchList<SiteUser>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(SiteUser.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null && !o.getUserKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_addA
												, Tuple.of(l2, "enrollmentKeys", pk, "userKeys")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.userKeys failed", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("SiteUser");
									}
								}
							}
						}
						break;
					case "addAllUserKeys":
						JsonArray addAllUserKeysValues = jsonObject.getJsonArray(methodName);
						if(addAllUserKeysValues != null) {
							for(Integer i = 0; i <  addAllUserKeysValues.size(); i++) {
								Long l = Long.parseLong(addAllUserKeysValues.getString(i));
								if(l != null) {
									SearchList<SiteUser> searchList = new SearchList<SiteUser>();
									searchList.setQuery("*:*");
									searchList.setStore(true);
									searchList.setC(SiteUser.class);
									searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
									searchList.initDeepSearchList(siteRequest);
									Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
									if(l2 != null && !o.getUserKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_addA
												, Tuple.of(l2, "enrollmentKeys", pk, "userKeys")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.userKeys failed", b.cause())));
										});
									}));
										if(!pks.contains(l2)) {
											pks.add(l2);
											classes.add("SiteUser");
										}
									}
								}
							}
						}
						break;
					case "setUserKeys":
						JsonArray setUserKeysValues = jsonObject.getJsonArray(methodName);
						if(setUserKeysValues != null) {
							for(Integer i = 0; i <  setUserKeysValues.size(); i++) {
								Long l = Long.parseLong(setUserKeysValues.getString(i));
								if(l != null) {
									SearchList<SiteUser> searchList = new SearchList<SiteUser>();
									searchList.setQuery("*:*");
									searchList.setStore(true);
									searchList.setC(SiteUser.class);
									searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
									searchList.initDeepSearchList(siteRequest);
									Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
									if(l2 != null && !o.getUserKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_addA
												, Tuple.of(l2, "enrollmentKeys", pk, "userKeys")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.userKeys failed", b.cause())));
										});
									}));
										if(!pks.contains(l2)) {
											pks.add(l2);
											classes.add("SiteUser");
										}
									}
								}
							}
						}
						if(o.getUserKeys() != null) {
							for(Long l :  o.getUserKeys()) {
								if(l != null && (setUserKeysValues == null || !setUserKeysValues.contains(l))) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_removeA
												, Tuple.of(l, "enrollmentKeys", pk, "userKeys")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.userKeys failed", b.cause())));
										});
									}));
								}
							}
						}
						break;
					case "removeUserKeys":
						{
							Long l = Long.parseLong(jsonObject.getString(methodName));
							if(l != null) {
								SearchList<SiteUser> searchList = new SearchList<SiteUser>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(SiteUser.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null && o.getUserKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_removeA
												, Tuple.of(l2, "enrollmentKeys", pk, "userKeys")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.userKeys failed", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("SiteUser");
									}
								}
							}
						}
						break;
					case "setPatientCompleteName":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "patientCompleteName")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientCompleteName failed", b.cause())));
								});
							}));
						} else {
							o2.setPatientCompleteName(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "patientCompleteName", o2.jsonPatientCompleteName())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientCompleteName failed", b.cause())));
								});
							}));
						}
						break;
					case "setPatientCompleteNamePreferred":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "patientCompleteNamePreferred")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientCompleteNamePreferred failed", b.cause())));
								});
							}));
						} else {
							o2.setPatientCompleteNamePreferred(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "patientCompleteNamePreferred", o2.jsonPatientCompleteNamePreferred())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientCompleteNamePreferred failed", b.cause())));
								});
							}));
						}
						break;
					case "setPatientBirthDate":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "patientBirthDate")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientBirthDate failed", b.cause())));
								});
							}));
						} else {
							o2.setPatientBirthDate(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "patientBirthDate", o2.jsonPatientBirthDate())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientBirthDate failed", b.cause())));
								});
							}));
						}
						break;
					case "setClinicAddress":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "clinicAddress")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.clinicAddress failed", b.cause())));
								});
							}));
						} else {
							o2.setClinicAddress(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "clinicAddress", o2.jsonClinicAddress())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.clinicAddress failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentApproved":
						if(jsonObject.getBoolean(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentApproved")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentApproved failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentApproved(jsonObject.getBoolean(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentApproved", o2.jsonEnrollmentApproved())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentApproved failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentImmunizations":
						if(jsonObject.getBoolean(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentImmunizations")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentImmunizations failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentImmunizations(jsonObject.getBoolean(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentImmunizations", o2.jsonEnrollmentImmunizations())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentImmunizations failed", b.cause())));
								});
							}));
						}
						break;
					case "setFamilyAddress":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "familyAddress")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.familyAddress failed", b.cause())));
								});
							}));
						} else {
							o2.setFamilyAddress(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "familyAddress", o2.jsonFamilyAddress())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.familyAddress failed", b.cause())));
								});
							}));
						}
						break;
					case "setFamilyHowDoYouKnowTheClinic":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "familyHowDoYouKnowTheClinic")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.familyHowDoYouKnowTheClinic failed", b.cause())));
								});
							}));
						} else {
							o2.setFamilyHowDoYouKnowTheClinic(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "familyHowDoYouKnowTheClinic", o2.jsonFamilyHowDoYouKnowTheClinic())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.familyHowDoYouKnowTheClinic failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentSpecialConsiderations":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentSpecialConsiderations")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSpecialConsiderations failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentSpecialConsiderations(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentSpecialConsiderations", o2.jsonEnrollmentSpecialConsiderations())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSpecialConsiderations failed", b.cause())));
								});
							}));
						}
						break;
					case "setPatientMedicalConditions":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "patientMedicalConditions")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientMedicalConditions failed", b.cause())));
								});
							}));
						} else {
							o2.setPatientMedicalConditions(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "patientMedicalConditions", o2.jsonPatientMedicalConditions())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientMedicalConditions failed", b.cause())));
								});
							}));
						}
						break;
					case "setPatientPreviousClinicsAttended":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "patientPreviousClinicsAttended")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientPreviousClinicsAttended failed", b.cause())));
								});
							}));
						} else {
							o2.setPatientPreviousClinicsAttended(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "patientPreviousClinicsAttended", o2.jsonPatientPreviousClinicsAttended())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientPreviousClinicsAttended failed", b.cause())));
								});
							}));
						}
						break;
					case "setPatientDescription":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "patientDescription")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientDescription failed", b.cause())));
								});
							}));
						} else {
							o2.setPatientDescription(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "patientDescription", o2.jsonPatientDescription())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientDescription failed", b.cause())));
								});
							}));
						}
						break;
					case "setPatientObjectives":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "patientObjectives")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientObjectives failed", b.cause())));
								});
							}));
						} else {
							o2.setPatientObjectives(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "patientObjectives", o2.jsonPatientObjectives())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientObjectives failed", b.cause())));
								});
							}));
						}
						break;
					case "setCustomerProfileId":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "customerProfileId")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.customerProfileId failed", b.cause())));
								});
							}));
						} else {
							o2.setCustomerProfileId(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "customerProfileId", o2.jsonCustomerProfileId())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.customerProfileId failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentSignature1":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentSignature1")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature1 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentSignature1(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentSignature1", o2.jsonEnrollmentSignature1())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature1 failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentSignature2":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentSignature2")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature2 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentSignature2(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentSignature2", o2.jsonEnrollmentSignature2())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature2 failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentSignature3":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentSignature3")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature3 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentSignature3(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentSignature3", o2.jsonEnrollmentSignature3())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature3 failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentSignature4":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentSignature4")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature4 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentSignature4(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentSignature4", o2.jsonEnrollmentSignature4())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature4 failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentSignature5":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentSignature5")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature5 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentSignature5(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentSignature5", o2.jsonEnrollmentSignature5())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature5 failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentSignature6":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentSignature6")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature6 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentSignature6(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentSignature6", o2.jsonEnrollmentSignature6())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature6 failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentSignature7":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentSignature7")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature7 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentSignature7(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentSignature7", o2.jsonEnrollmentSignature7())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature7 failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentSignature8":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentSignature8")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature8 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentSignature8(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentSignature8", o2.jsonEnrollmentSignature8())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature8 failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentSignature9":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentSignature9")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature9 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentSignature9(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentSignature9", o2.jsonEnrollmentSignature9())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature9 failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentSignature10":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentSignature10")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature10 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentSignature10(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentSignature10", o2.jsonEnrollmentSignature10())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature10 failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentDate1":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentDate1")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate1 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentDate1(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentDate1", o2.jsonEnrollmentDate1())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate1 failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentDate2":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentDate2")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate2 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentDate2(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentDate2", o2.jsonEnrollmentDate2())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate2 failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentDate3":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentDate3")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate3 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentDate3(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentDate3", o2.jsonEnrollmentDate3())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate3 failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentDate4":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentDate4")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate4 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentDate4(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentDate4", o2.jsonEnrollmentDate4())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate4 failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentDate5":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentDate5")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate5 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentDate5(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentDate5", o2.jsonEnrollmentDate5())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate5 failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentDate6":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentDate6")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate6 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentDate6(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentDate6", o2.jsonEnrollmentDate6())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate6 failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentDate7":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentDate7")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate7 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentDate7(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentDate7", o2.jsonEnrollmentDate7())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate7 failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentDate8":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentDate8")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate8 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentDate8(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentDate8", o2.jsonEnrollmentDate8())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate8 failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentDate9":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentDate9")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate9 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentDate9(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentDate9", o2.jsonEnrollmentDate9())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate9 failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentDate10":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentDate10")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate10 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentDate10(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentDate10", o2.jsonEnrollmentDate10())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate10 failed", b.cause())));
								});
							}));
						}
						break;
				}
			}
			CompositeFuture.all(futures).setHandler( a -> {
				if(a.succeeded()) {
					MedicalEnrollment o3 = new MedicalEnrollment();
					o3.setSiteRequest_(o.getSiteRequest_());
					o3.setPk(pk);
					eventHandler.handle(Future.succeededFuture(o3));
				} else {
					LOGGER.error(String.format("sqlPATCHMedicalEnrollment failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPATCHMedicalEnrollment failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void patchMedicalEnrollmentResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PATCHMedicalEnrollment(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("patchMedicalEnrollmentResponse failed. ", a.cause()));
					errorMedicalEnrollment(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("patchMedicalEnrollmentResponse failed. ", ex));
			errorMedicalEnrollment(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PATCHMedicalEnrollment(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PATCHMedicalEnrollment failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// GET //

	@Override
	public void getMedicalEnrollment(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForMedicalEnrollment(siteContext, operationRequest);
		try {
			userMedicalEnrollment(siteRequest, b -> {
				if(b.succeeded()) {
					aSearchMedicalEnrollment(siteRequest, false, true, "/api/enrollment/{id}", "GET", c -> {
						if(c.succeeded()) {
							SearchList<MedicalEnrollment> listMedicalEnrollment = c.result();
							getMedicalEnrollmentResponse(listMedicalEnrollment, d -> {
								if(d.succeeded()) {
									eventHandler.handle(Future.succeededFuture(d.result()));
									LOGGER.info(String.format("getMedicalEnrollment succeeded. "));
								} else {
									LOGGER.error(String.format("getMedicalEnrollment failed. ", d.cause()));
									errorMedicalEnrollment(siteRequest, eventHandler, d);
								}
							});
						} else {
							LOGGER.error(String.format("getMedicalEnrollment failed. ", c.cause()));
							errorMedicalEnrollment(siteRequest, eventHandler, c);
						}
					});
				} else {
					LOGGER.error(String.format("getMedicalEnrollment failed. ", b.cause()));
					errorMedicalEnrollment(siteRequest, eventHandler, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("getMedicalEnrollment failed. ", ex));
			errorMedicalEnrollment(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void getMedicalEnrollmentResponse(SearchList<MedicalEnrollment> listMedicalEnrollment, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listMedicalEnrollment.getSiteRequest_();
		try {
			response200GETMedicalEnrollment(listMedicalEnrollment, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("getMedicalEnrollmentResponse failed. ", a.cause()));
					errorMedicalEnrollment(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("getMedicalEnrollmentResponse failed. ", ex));
			errorMedicalEnrollment(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200GETMedicalEnrollment(SearchList<MedicalEnrollment> listMedicalEnrollment, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listMedicalEnrollment.getSiteRequest_();
			SolrDocumentList solrDocuments = listMedicalEnrollment.getSolrDocumentList();

			JsonObject json = JsonObject.mapFrom(listMedicalEnrollment.getList().stream().findFirst().orElse(null));
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200GETMedicalEnrollment failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// Search //

	@Override
	public void searchMedicalEnrollment(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForMedicalEnrollment(siteContext, operationRequest);
		try {
			userMedicalEnrollment(siteRequest, b -> {
				if(b.succeeded()) {
					aSearchMedicalEnrollment(siteRequest, false, true, "/api/enrollment", "Search", c -> {
						if(c.succeeded()) {
							SearchList<MedicalEnrollment> listMedicalEnrollment = c.result();
							searchMedicalEnrollmentResponse(listMedicalEnrollment, d -> {
								if(d.succeeded()) {
									eventHandler.handle(Future.succeededFuture(d.result()));
									LOGGER.info(String.format("searchMedicalEnrollment succeeded. "));
								} else {
									LOGGER.error(String.format("searchMedicalEnrollment failed. ", d.cause()));
									errorMedicalEnrollment(siteRequest, eventHandler, d);
								}
							});
						} else {
							LOGGER.error(String.format("searchMedicalEnrollment failed. ", c.cause()));
							errorMedicalEnrollment(siteRequest, eventHandler, c);
						}
					});
				} else {
					LOGGER.error(String.format("searchMedicalEnrollment failed. ", b.cause()));
					errorMedicalEnrollment(siteRequest, eventHandler, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("searchMedicalEnrollment failed. ", ex));
			errorMedicalEnrollment(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void searchMedicalEnrollmentResponse(SearchList<MedicalEnrollment> listMedicalEnrollment, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listMedicalEnrollment.getSiteRequest_();
		try {
			response200SearchMedicalEnrollment(listMedicalEnrollment, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("searchMedicalEnrollmentResponse failed. ", a.cause()));
					errorMedicalEnrollment(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("searchMedicalEnrollmentResponse failed. ", ex));
			errorMedicalEnrollment(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200SearchMedicalEnrollment(SearchList<MedicalEnrollment> listMedicalEnrollment, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listMedicalEnrollment.getSiteRequest_();
			QueryResponse responseSearch = listMedicalEnrollment.getQueryResponse();
			SolrDocumentList solrDocuments = listMedicalEnrollment.getSolrDocumentList();
			Long searchInMillis = Long.valueOf(responseSearch.getQTime());
			Long transmissionInMillis = responseSearch.getElapsedTime();
			Long startNum = responseSearch.getResults().getStart();
			Long foundNum = responseSearch.getResults().getNumFound();
			Integer returnedNum = responseSearch.getResults().size();
			String searchTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(searchInMillis), TimeUnit.MILLISECONDS.toMillis(searchInMillis) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(searchInMillis)));
			String transmissionTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis), TimeUnit.MILLISECONDS.toMillis(transmissionInMillis) - TimeUnit.SECONDS.toSeconds(TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis)));
			Exception exceptionSearch = responseSearch.getException();

			JsonObject json = new JsonObject();
			json.put("startNum", startNum);
			json.put("foundNum", foundNum);
			json.put("returnedNum", returnedNum);
			json.put("searchTime", searchTime);
			json.put("transmissionTime", transmissionTime);
			JsonArray l = new JsonArray();
			listMedicalEnrollment.getList().stream().forEach(o -> {
				JsonObject json2 = JsonObject.mapFrom(o);
				List<String> fls = listMedicalEnrollment.getFields();
				if(fls.size() > 0) {
					Set<String> fieldNames = new HashSet<String>();
					fieldNames.addAll(json2.fieldNames());
					if(fls.size() == 1 && fls.stream().findFirst().orElse(null).equals("saves")) {
						fieldNames.removeAll(Optional.ofNullable(json2.getJsonArray("saves")).orElse(new JsonArray()).stream().map(s -> s.toString()).collect(Collectors.toList()));
						fieldNames.remove("pk");
						fieldNames.remove("created");
					}
					else if(fls.size() >= 1) {
						fieldNames.removeAll(fls);
					}
					for(String fieldName : fieldNames) {
						if(!fls.contains(fieldName))
							json2.remove(fieldName);
					}
				}
				l.add(json2);
			});
			json.put("list", l);
			if(exceptionSearch != null) {
				json.put("exceptionSearch", exceptionSearch.getMessage());
			}
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200SearchMedicalEnrollment failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// AdminSearch //

	@Override
	public void adminsearchMedicalEnrollment(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForMedicalEnrollment(siteContext, operationRequest);
		try {
			userMedicalEnrollment(siteRequest, b -> {
				if(b.succeeded()) {
					aSearchMedicalEnrollment(siteRequest, false, true, "/api/admin/enrollment", "AdminSearch", c -> {
						if(c.succeeded()) {
							SearchList<MedicalEnrollment> listMedicalEnrollment = c.result();
							adminsearchMedicalEnrollmentResponse(listMedicalEnrollment, d -> {
								if(d.succeeded()) {
									eventHandler.handle(Future.succeededFuture(d.result()));
									LOGGER.info(String.format("adminsearchMedicalEnrollment succeeded. "));
								} else {
									LOGGER.error(String.format("adminsearchMedicalEnrollment failed. ", d.cause()));
									errorMedicalEnrollment(siteRequest, eventHandler, d);
								}
							});
						} else {
							LOGGER.error(String.format("adminsearchMedicalEnrollment failed. ", c.cause()));
							errorMedicalEnrollment(siteRequest, eventHandler, c);
						}
					});
				} else {
					LOGGER.error(String.format("adminsearchMedicalEnrollment failed. ", b.cause()));
					errorMedicalEnrollment(siteRequest, eventHandler, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("adminsearchMedicalEnrollment failed. ", ex));
			errorMedicalEnrollment(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void adminsearchMedicalEnrollmentResponse(SearchList<MedicalEnrollment> listMedicalEnrollment, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listMedicalEnrollment.getSiteRequest_();
		try {
			response200AdminSearchMedicalEnrollment(listMedicalEnrollment, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("adminsearchMedicalEnrollmentResponse failed. ", a.cause()));
					errorMedicalEnrollment(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("adminsearchMedicalEnrollmentResponse failed. ", ex));
			errorMedicalEnrollment(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200AdminSearchMedicalEnrollment(SearchList<MedicalEnrollment> listMedicalEnrollment, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listMedicalEnrollment.getSiteRequest_();
			QueryResponse responseSearch = listMedicalEnrollment.getQueryResponse();
			SolrDocumentList solrDocuments = listMedicalEnrollment.getSolrDocumentList();
			Long searchInMillis = Long.valueOf(responseSearch.getQTime());
			Long transmissionInMillis = responseSearch.getElapsedTime();
			Long startNum = responseSearch.getResults().getStart();
			Long foundNum = responseSearch.getResults().getNumFound();
			Integer returnedNum = responseSearch.getResults().size();
			String searchTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(searchInMillis), TimeUnit.MILLISECONDS.toMillis(searchInMillis) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(searchInMillis)));
			String transmissionTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis), TimeUnit.MILLISECONDS.toMillis(transmissionInMillis) - TimeUnit.SECONDS.toSeconds(TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis)));
			Exception exceptionSearch = responseSearch.getException();

			JsonObject json = new JsonObject();
			json.put("startNum", startNum);
			json.put("foundNum", foundNum);
			json.put("returnedNum", returnedNum);
			json.put("searchTime", searchTime);
			json.put("transmissionTime", transmissionTime);
			JsonArray l = new JsonArray();
			listMedicalEnrollment.getList().stream().forEach(o -> {
				JsonObject json2 = JsonObject.mapFrom(o);
				List<String> fls = listMedicalEnrollment.getFields();
				if(fls.size() > 0) {
					Set<String> fieldNames = new HashSet<String>();
					fieldNames.addAll(json2.fieldNames());
					if(fls.size() == 1 && fls.stream().findFirst().orElse(null).equals("saves")) {
						fieldNames.removeAll(Optional.ofNullable(json2.getJsonArray("saves")).orElse(new JsonArray()).stream().map(s -> s.toString()).collect(Collectors.toList()));
						fieldNames.remove("pk");
						fieldNames.remove("created");
					}
					else if(fls.size() >= 1) {
						fieldNames.removeAll(fls);
					}
					for(String fieldName : fieldNames) {
						if(!fls.contains(fieldName))
							json2.remove(fieldName);
					}
				}
				l.add(json2);
			});
			json.put("list", l);
			if(exceptionSearch != null) {
				json.put("exceptionSearch", exceptionSearch.getMessage());
			}
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200AdminSearchMedicalEnrollment failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PATCHPayments //

	@Override
	public void patchpaymentsMedicalEnrollment(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForMedicalEnrollment(siteContext, operationRequest, body);
		try {
			LOGGER.info(String.format("patchpaymentsMedicalEnrollment started. "));
			userMedicalEnrollment(siteRequest, b -> {
				if(b.succeeded()) {
					patchpaymentsMedicalEnrollmentResponse(siteRequest, c -> {
						if(c.succeeded()) {
							eventHandler.handle(Future.succeededFuture(c.result()));
							WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
							workerExecutor.executeBlocking(
								blockingCodeHandler -> {
									try {
										aSearchMedicalEnrollment(siteRequest, false, true, "/enrollment/payments", "PATCHPayments", d -> {
											if(d.succeeded()) {
												SearchList<MedicalEnrollment> listMedicalEnrollment = d.result();
												ApiRequest apiRequest = new ApiRequest();
												apiRequest.setRows(listMedicalEnrollment.getRows());
												apiRequest.setNumFound(listMedicalEnrollment.getQueryResponse().getResults().getNumFound());
												apiRequest.setNumPATCH(0L);
												apiRequest.initDeepApiRequest(siteRequest);
												siteRequest.setApiRequest_(apiRequest);
												siteRequest.getVertx().eventBus().publish("websocketMedicalEnrollment", JsonObject.mapFrom(apiRequest).toString());
												SimpleOrderedMap facets = (SimpleOrderedMap)Optional.ofNullable(listMedicalEnrollment.getQueryResponse()).map(QueryResponse::getResponse).map(r -> r.get("facets")).orElse(null);
												Date date = null;
												if(facets != null)
													date = (Date)facets.get("max_modified");
												String dt;
												if(date == null)
													dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(ZonedDateTime.now().toInstant(), ZoneId.of("UTC")).minusNanos(1000));
												else
													dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC")));
												listMedicalEnrollment.addFilterQuery(String.format("modified_indexed_date:[* TO %s]", dt));

												try {
													listPATCHPaymentsMedicalEnrollment(apiRequest, listMedicalEnrollment, dt, e -> {
														if(e.succeeded()) {
															patchpaymentsMedicalEnrollmentResponse(siteRequest, f -> {
																if(f.succeeded()) {
																	LOGGER.info(String.format("patchpaymentsMedicalEnrollment succeeded. "));
																	blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																} else {
																	LOGGER.error(String.format("patchpaymentsMedicalEnrollment failed. ", f.cause()));
																	errorMedicalEnrollment(siteRequest, null, f);
																}
															});
														} else {
															LOGGER.error(String.format("patchpaymentsMedicalEnrollment failed. ", e.cause()));
															errorMedicalEnrollment(siteRequest, null, e);
														}
													});
												} catch(Exception ex) {
													LOGGER.error(String.format("patchpaymentsMedicalEnrollment failed. ", ex));
													errorMedicalEnrollment(siteRequest, null, Future.failedFuture(ex));
												}
											} else {
												LOGGER.error(String.format("patchpaymentsMedicalEnrollment failed. ", d.cause()));
												errorMedicalEnrollment(siteRequest, null, d);
											}
										});
									} catch(Exception ex) {
										LOGGER.error(String.format("patchpaymentsMedicalEnrollment failed. ", ex));
										errorMedicalEnrollment(siteRequest, null, Future.failedFuture(ex));
									}
								}, resultHandler -> {
								}
							);
						} else {
							LOGGER.error(String.format("patchpaymentsMedicalEnrollment failed. ", c.cause()));
							errorMedicalEnrollment(siteRequest, eventHandler, c);
						}
					});
				} else {
					LOGGER.error(String.format("patchpaymentsMedicalEnrollment failed. ", b.cause()));
					errorMedicalEnrollment(siteRequest, eventHandler, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("patchpaymentsMedicalEnrollment failed. ", ex));
			errorMedicalEnrollment(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void listPATCHPaymentsMedicalEnrollment(ApiRequest apiRequest, SearchList<MedicalEnrollment> listMedicalEnrollment, String dt, Handler<AsyncResult<OperationResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listMedicalEnrollment.getSiteRequest_();
		listMedicalEnrollment.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForMedicalEnrollment(siteContext, siteRequest.getOperationRequest(), siteRequest.getJsonObject());
			siteRequest2.setApiRequest_(siteRequest.getApiRequest_());
			o.setSiteRequest_(siteRequest2);
			futures.add(
				patchpaymentsMedicalEnrollmentFuture(o, false, a -> {
					if(a.succeeded()) {
					} else {
						errorMedicalEnrollment(siteRequest2, eventHandler, a);
					}
				})
			);
		});
		CompositeFuture.all(futures).setHandler( a -> {
			if(a.succeeded()) {
				if(listMedicalEnrollment.next(dt)) {
					listPATCHPaymentsMedicalEnrollment(apiRequest, listMedicalEnrollment, dt, eventHandler);
				} else {
					response200PATCHPaymentsMedicalEnrollment(siteRequest, eventHandler);
				}
			} else {
				LOGGER.error(String.format("listPATCHPaymentsMedicalEnrollment failed. ", a.cause()));
				errorMedicalEnrollment(listMedicalEnrollment.getSiteRequest_(), eventHandler, a);
			}
		});
	}

	public Future<MedicalEnrollment> patchpaymentsMedicalEnrollmentFuture(MedicalEnrollment o, Boolean inheritPk, Handler<AsyncResult<MedicalEnrollment>> eventHandler) {
		Promise<MedicalEnrollment> promise = Promise.promise();
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			if(apiRequest != null && apiRequest.getNumFound() == 1L) {
				apiRequest.setOriginal(o);
				apiRequest.setPk(o.getPk());
			}
			sqlConnectionMedicalEnrollment(siteRequest, a -> {
				if(a.succeeded()) {
					sqlTransactionMedicalEnrollment(siteRequest, b -> {
						if(b.succeeded()) {
							sqlPATCHPaymentsMedicalEnrollment(o, inheritPk, c -> {
								if(c.succeeded()) {
									MedicalEnrollment medicalEnrollment = c.result();
									defineIndexMedicalEnrollment(medicalEnrollment, d -> {
										if(d.succeeded()) {
											if(apiRequest != null) {
												apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
												if(apiRequest.getNumFound() == 1L) {
													medicalEnrollment.apiRequestMedicalEnrollment();
												}
												siteRequest.getVertx().eventBus().publish("websocketMedicalEnrollment", JsonObject.mapFrom(apiRequest).toString());
											}
											eventHandler.handle(Future.succeededFuture(medicalEnrollment));
											promise.complete(medicalEnrollment);
										} else {
											LOGGER.error(String.format("patchpaymentsMedicalEnrollmentFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOGGER.error(String.format("patchpaymentsMedicalEnrollmentFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOGGER.error(String.format("patchpaymentsMedicalEnrollmentFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOGGER.error(String.format("patchpaymentsMedicalEnrollmentFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("patchpaymentsMedicalEnrollmentFuture failed. ", e));
			errorMedicalEnrollment(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPATCHPaymentsMedicalEnrollment(MedicalEnrollment o, Boolean inheritPk, Handler<AsyncResult<MedicalEnrollment>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Transaction tx = siteRequest.getTx();
			Long pk = o.getPk();
			JsonObject jsonObject = siteRequest.getJsonObject();
			Set<String> methodNames = jsonObject.fieldNames();
			MedicalEnrollment o2 = new MedicalEnrollment();
			List<Future> futures = new ArrayList<>();

			if(o.getUserId() == null && siteRequest.getUserId() != null) {
				futures.add(Future.future(a -> {
					tx.preparedQuery(SiteContextEnUS.SQL_setD
							, Tuple.of(pk, "userId", siteRequest.getUserId())
							, b
					-> {
						if(b.succeeded())
							a.handle(Future.succeededFuture());
						else
							a.handle(Future.failedFuture(b.cause()));
					});
				}));
			}
			if(o.getUserKey() == null && siteRequest.getUserKey() != null) {
				futures.add(Future.future(a -> {
					tx.preparedQuery(SiteContextEnUS.SQL_setD
				, Tuple.of(pk, "userKey", siteRequest.getUserKey().toString())
							, b
					-> {
						if(b.succeeded())
							a.handle(Future.succeededFuture());
						else
							a.handle(Future.failedFuture(b.cause()));
					});
				}));

				JsonArray userKeys = Optional.ofNullable(jsonObject.getJsonArray("addUserKeys")).orElse(null);
				if(userKeys != null && !userKeys.contains(siteRequest.getUserKey()))
					userKeys.add(siteRequest.getUserKey().toString());
				else
					jsonObject.put("addUserKeys", new JsonArray().add(siteRequest.getUserKey().toString()));
			}

			for(String methodName : methodNames) {
				switch(methodName) {
					case "setInheritPk":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "inheritPk")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.inheritPk failed", b.cause())));
								});
							}));
						} else {
							o2.setInheritPk(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "inheritPk", o2.jsonInheritPk())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.inheritPk failed", b.cause())));
								});
							}));
						}
						break;
					case "setArchived":
						if(jsonObject.getBoolean(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "archived")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.archived failed", b.cause())));
								});
							}));
						} else {
							o2.setArchived(jsonObject.getBoolean(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "archived", o2.jsonArchived())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.archived failed", b.cause())));
								});
							}));
						}
						break;
					case "setDeleted":
						if(jsonObject.getBoolean(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "deleted")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.deleted failed", b.cause())));
								});
							}));
						} else {
							o2.setDeleted(jsonObject.getBoolean(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "deleted", o2.jsonDeleted())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.deleted failed", b.cause())));
								});
							}));
						}
						break;
					case "setPatientKey":
						{
							Long l = o2.getPatientKey();
							if(l != null && !l.equals(o.getPatientKey())) {
								SearchList<MedicalPatient> searchList = new SearchList<MedicalPatient>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(MedicalPatient.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null) {
									o2.setPatientKey(jsonObject.getString(methodName));
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_addA
												, Tuple.of(l2, "enrollmentKeys", pk, "patientKey")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientKey failed", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("MedicalPatient");
									}
								}
							}
						}
						break;
					case "removePatientKey":
						{
							Long l = o2.getPatientKey();
							if(l != null) {
								SearchList<MedicalPatient> searchList = new SearchList<MedicalPatient>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(MedicalPatient.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null && l2.equals(o.getPatientKey())) {
									o2.setPatientKey(jsonObject.getString(methodName));
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_removeA
												, Tuple.of(l2, "enrollmentKeys", pk, "patientKey")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientKey failed", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("MedicalPatient");
									}
								}
							}
						}
						break;
					case "addUserKeys":
						{
							Long l = Long.parseLong(jsonObject.getString(methodName));
							if(l != null) {
								SearchList<SiteUser> searchList = new SearchList<SiteUser>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(SiteUser.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null && !o.getUserKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_addA
												, Tuple.of(l2, "enrollmentKeys", pk, "userKeys")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.userKeys failed", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("SiteUser");
									}
								}
							}
						}
						break;
					case "addAllUserKeys":
						JsonArray addAllUserKeysValues = jsonObject.getJsonArray(methodName);
						if(addAllUserKeysValues != null) {
							for(Integer i = 0; i <  addAllUserKeysValues.size(); i++) {
								Long l = Long.parseLong(addAllUserKeysValues.getString(i));
								if(l != null) {
									SearchList<SiteUser> searchList = new SearchList<SiteUser>();
									searchList.setQuery("*:*");
									searchList.setStore(true);
									searchList.setC(SiteUser.class);
									searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
									searchList.initDeepSearchList(siteRequest);
									Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
									if(l2 != null && !o.getUserKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_addA
												, Tuple.of(l2, "enrollmentKeys", pk, "userKeys")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.userKeys failed", b.cause())));
										});
									}));
										if(!pks.contains(l2)) {
											pks.add(l2);
											classes.add("SiteUser");
										}
									}
								}
							}
						}
						break;
					case "setUserKeys":
						JsonArray setUserKeysValues = jsonObject.getJsonArray(methodName);
						if(setUserKeysValues != null) {
							for(Integer i = 0; i <  setUserKeysValues.size(); i++) {
								Long l = Long.parseLong(setUserKeysValues.getString(i));
								if(l != null) {
									SearchList<SiteUser> searchList = new SearchList<SiteUser>();
									searchList.setQuery("*:*");
									searchList.setStore(true);
									searchList.setC(SiteUser.class);
									searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
									searchList.initDeepSearchList(siteRequest);
									Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
									if(l2 != null && !o.getUserKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_addA
												, Tuple.of(l2, "enrollmentKeys", pk, "userKeys")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.userKeys failed", b.cause())));
										});
									}));
										if(!pks.contains(l2)) {
											pks.add(l2);
											classes.add("SiteUser");
										}
									}
								}
							}
						}
						if(o.getUserKeys() != null) {
							for(Long l :  o.getUserKeys()) {
								if(l != null && (setUserKeysValues == null || !setUserKeysValues.contains(l))) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_removeA
												, Tuple.of(l, "enrollmentKeys", pk, "userKeys")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.userKeys failed", b.cause())));
										});
									}));
								}
							}
						}
						break;
					case "removeUserKeys":
						{
							Long l = Long.parseLong(jsonObject.getString(methodName));
							if(l != null) {
								SearchList<SiteUser> searchList = new SearchList<SiteUser>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(SiteUser.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null && o.getUserKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_removeA
												, Tuple.of(l2, "enrollmentKeys", pk, "userKeys")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.userKeys failed", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("SiteUser");
									}
								}
							}
						}
						break;
					case "setPatientCompleteName":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "patientCompleteName")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientCompleteName failed", b.cause())));
								});
							}));
						} else {
							o2.setPatientCompleteName(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "patientCompleteName", o2.jsonPatientCompleteName())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientCompleteName failed", b.cause())));
								});
							}));
						}
						break;
					case "setPatientCompleteNamePreferred":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "patientCompleteNamePreferred")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientCompleteNamePreferred failed", b.cause())));
								});
							}));
						} else {
							o2.setPatientCompleteNamePreferred(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "patientCompleteNamePreferred", o2.jsonPatientCompleteNamePreferred())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientCompleteNamePreferred failed", b.cause())));
								});
							}));
						}
						break;
					case "setPatientBirthDate":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "patientBirthDate")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientBirthDate failed", b.cause())));
								});
							}));
						} else {
							o2.setPatientBirthDate(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "patientBirthDate", o2.jsonPatientBirthDate())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientBirthDate failed", b.cause())));
								});
							}));
						}
						break;
					case "setClinicAddress":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "clinicAddress")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.clinicAddress failed", b.cause())));
								});
							}));
						} else {
							o2.setClinicAddress(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "clinicAddress", o2.jsonClinicAddress())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.clinicAddress failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentApproved":
						if(jsonObject.getBoolean(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentApproved")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentApproved failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentApproved(jsonObject.getBoolean(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentApproved", o2.jsonEnrollmentApproved())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentApproved failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentImmunizations":
						if(jsonObject.getBoolean(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentImmunizations")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentImmunizations failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentImmunizations(jsonObject.getBoolean(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentImmunizations", o2.jsonEnrollmentImmunizations())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentImmunizations failed", b.cause())));
								});
							}));
						}
						break;
					case "setFamilyAddress":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "familyAddress")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.familyAddress failed", b.cause())));
								});
							}));
						} else {
							o2.setFamilyAddress(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "familyAddress", o2.jsonFamilyAddress())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.familyAddress failed", b.cause())));
								});
							}));
						}
						break;
					case "setFamilyHowDoYouKnowTheClinic":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "familyHowDoYouKnowTheClinic")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.familyHowDoYouKnowTheClinic failed", b.cause())));
								});
							}));
						} else {
							o2.setFamilyHowDoYouKnowTheClinic(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "familyHowDoYouKnowTheClinic", o2.jsonFamilyHowDoYouKnowTheClinic())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.familyHowDoYouKnowTheClinic failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentSpecialConsiderations":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentSpecialConsiderations")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSpecialConsiderations failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentSpecialConsiderations(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentSpecialConsiderations", o2.jsonEnrollmentSpecialConsiderations())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSpecialConsiderations failed", b.cause())));
								});
							}));
						}
						break;
					case "setPatientMedicalConditions":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "patientMedicalConditions")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientMedicalConditions failed", b.cause())));
								});
							}));
						} else {
							o2.setPatientMedicalConditions(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "patientMedicalConditions", o2.jsonPatientMedicalConditions())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientMedicalConditions failed", b.cause())));
								});
							}));
						}
						break;
					case "setPatientPreviousClinicsAttended":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "patientPreviousClinicsAttended")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientPreviousClinicsAttended failed", b.cause())));
								});
							}));
						} else {
							o2.setPatientPreviousClinicsAttended(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "patientPreviousClinicsAttended", o2.jsonPatientPreviousClinicsAttended())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientPreviousClinicsAttended failed", b.cause())));
								});
							}));
						}
						break;
					case "setPatientDescription":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "patientDescription")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientDescription failed", b.cause())));
								});
							}));
						} else {
							o2.setPatientDescription(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "patientDescription", o2.jsonPatientDescription())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientDescription failed", b.cause())));
								});
							}));
						}
						break;
					case "setPatientObjectives":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "patientObjectives")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientObjectives failed", b.cause())));
								});
							}));
						} else {
							o2.setPatientObjectives(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "patientObjectives", o2.jsonPatientObjectives())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.patientObjectives failed", b.cause())));
								});
							}));
						}
						break;
					case "setCustomerProfileId":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "customerProfileId")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.customerProfileId failed", b.cause())));
								});
							}));
						} else {
							o2.setCustomerProfileId(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "customerProfileId", o2.jsonCustomerProfileId())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.customerProfileId failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentSignature1":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentSignature1")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature1 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentSignature1(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentSignature1", o2.jsonEnrollmentSignature1())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature1 failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentSignature2":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentSignature2")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature2 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentSignature2(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentSignature2", o2.jsonEnrollmentSignature2())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature2 failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentSignature3":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentSignature3")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature3 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentSignature3(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentSignature3", o2.jsonEnrollmentSignature3())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature3 failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentSignature4":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentSignature4")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature4 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentSignature4(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentSignature4", o2.jsonEnrollmentSignature4())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature4 failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentSignature5":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentSignature5")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature5 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentSignature5(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentSignature5", o2.jsonEnrollmentSignature5())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature5 failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentSignature6":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentSignature6")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature6 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentSignature6(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentSignature6", o2.jsonEnrollmentSignature6())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature6 failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentSignature7":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentSignature7")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature7 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentSignature7(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentSignature7", o2.jsonEnrollmentSignature7())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature7 failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentSignature8":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentSignature8")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature8 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentSignature8(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentSignature8", o2.jsonEnrollmentSignature8())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature8 failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentSignature9":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentSignature9")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature9 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentSignature9(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentSignature9", o2.jsonEnrollmentSignature9())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature9 failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentSignature10":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentSignature10")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature10 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentSignature10(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentSignature10", o2.jsonEnrollmentSignature10())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentSignature10 failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentDate1":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentDate1")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate1 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentDate1(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentDate1", o2.jsonEnrollmentDate1())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate1 failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentDate2":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentDate2")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate2 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentDate2(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentDate2", o2.jsonEnrollmentDate2())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate2 failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentDate3":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentDate3")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate3 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentDate3(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentDate3", o2.jsonEnrollmentDate3())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate3 failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentDate4":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentDate4")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate4 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentDate4(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentDate4", o2.jsonEnrollmentDate4())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate4 failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentDate5":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentDate5")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate5 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentDate5(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentDate5", o2.jsonEnrollmentDate5())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate5 failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentDate6":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentDate6")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate6 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentDate6(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentDate6", o2.jsonEnrollmentDate6())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate6 failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentDate7":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentDate7")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate7 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentDate7(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentDate7", o2.jsonEnrollmentDate7())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate7 failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentDate8":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentDate8")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate8 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentDate8(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentDate8", o2.jsonEnrollmentDate8())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate8 failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentDate9":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentDate9")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate9 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentDate9(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentDate9", o2.jsonEnrollmentDate9())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate9 failed", b.cause())));
								});
							}));
						}
						break;
					case "setEnrollmentDate10":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "enrollmentDate10")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate10 failed", b.cause())));
								});
							}));
						} else {
							o2.setEnrollmentDate10(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "enrollmentDate10", o2.jsonEnrollmentDate10())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalEnrollment.enrollmentDate10 failed", b.cause())));
								});
							}));
						}
						break;
				}
			}
			CompositeFuture.all(futures).setHandler( a -> {
				if(a.succeeded()) {
					MedicalEnrollment o3 = new MedicalEnrollment();
					o3.setSiteRequest_(o.getSiteRequest_());
					o3.setPk(pk);
					eventHandler.handle(Future.succeededFuture(o3));
				} else {
					LOGGER.error(String.format("sqlPATCHPaymentsMedicalEnrollment failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPATCHPaymentsMedicalEnrollment failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void patchpaymentsMedicalEnrollmentResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PATCHPaymentsMedicalEnrollment(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("patchpaymentsMedicalEnrollmentResponse failed. ", a.cause()));
					errorMedicalEnrollment(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("patchpaymentsMedicalEnrollmentResponse failed. ", ex));
			errorMedicalEnrollment(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PATCHPaymentsMedicalEnrollment(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PATCHPaymentsMedicalEnrollment failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// SearchPage //

	@Override
	public void searchpageMedicalEnrollmentId(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		searchpageMedicalEnrollment(operationRequest, eventHandler);
	}

	@Override
	public void searchpageMedicalEnrollment(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForMedicalEnrollment(siteContext, operationRequest);
		try {
			userMedicalEnrollment(siteRequest, b -> {
				if(b.succeeded()) {
					aSearchMedicalEnrollment(siteRequest, false, true, "/enrollment", "SearchPage", c -> {
						if(c.succeeded()) {
							SearchList<MedicalEnrollment> listMedicalEnrollment = c.result();
							searchpageMedicalEnrollmentResponse(listMedicalEnrollment, d -> {
								if(d.succeeded()) {
									eventHandler.handle(Future.succeededFuture(d.result()));
									LOGGER.info(String.format("searchpageMedicalEnrollment succeeded. "));
								} else {
									LOGGER.error(String.format("searchpageMedicalEnrollment failed. ", d.cause()));
									errorMedicalEnrollment(siteRequest, eventHandler, d);
								}
							});
						} else {
							LOGGER.error(String.format("searchpageMedicalEnrollment failed. ", c.cause()));
							errorMedicalEnrollment(siteRequest, eventHandler, c);
						}
					});
				} else {
					LOGGER.error(String.format("searchpageMedicalEnrollment failed. ", b.cause()));
					errorMedicalEnrollment(siteRequest, eventHandler, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("searchpageMedicalEnrollment failed. ", ex));
			errorMedicalEnrollment(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void searchpageMedicalEnrollmentResponse(SearchList<MedicalEnrollment> listMedicalEnrollment, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listMedicalEnrollment.getSiteRequest_();
		try {
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(siteRequest, buffer);
			siteRequest.setW(w);
			response200SearchPageMedicalEnrollment(listMedicalEnrollment, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("searchpageMedicalEnrollmentResponse failed. ", a.cause()));
					errorMedicalEnrollment(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("searchpageMedicalEnrollmentResponse failed. ", ex));
			errorMedicalEnrollment(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200SearchPageMedicalEnrollment(SearchList<MedicalEnrollment> listMedicalEnrollment, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listMedicalEnrollment.getSiteRequest_();
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(listMedicalEnrollment.getSiteRequest_(), buffer);
			EnrollmentPage page = new EnrollmentPage();
			SolrDocument pageSolrDocument = new SolrDocument();
			CaseInsensitiveHeaders requestHeaders = new CaseInsensitiveHeaders();
			siteRequest.setRequestHeaders(requestHeaders);

			pageSolrDocument.setField("pageUri_frFR_stored_string", "/enrollment");
			page.setPageSolrDocument(pageSolrDocument);
			page.setW(w);
			if(listMedicalEnrollment.size() == 1)
				siteRequest.setRequestPk(listMedicalEnrollment.get(0).getPk());
			siteRequest.setW(w);
			page.setListMedicalEnrollment(listMedicalEnrollment);
			page.setSiteRequest_(siteRequest);
			page.initDeepEnrollmentPage(siteRequest);
			page.html();
			eventHandler.handle(Future.succeededFuture(new OperationResponse(200, "OK", buffer, requestHeaders)));
		} catch(Exception e) {
			LOGGER.error(String.format("response200SearchPageMedicalEnrollment failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// RefreshSearchPage //

	@Override
	public void refreshsearchpageMedicalEnrollmentId(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		refreshsearchpageMedicalEnrollment(operationRequest, eventHandler);
	}

	@Override
	public void refreshsearchpageMedicalEnrollment(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForMedicalEnrollment(siteContext, operationRequest);
		try {
			userMedicalEnrollment(siteRequest, b -> {
				if(b.succeeded()) {
					aSearchMedicalEnrollment(siteRequest, false, true, "/refresh-enrollment", "RefreshSearchPage", c -> {
						if(c.succeeded()) {
							SearchList<MedicalEnrollment> listMedicalEnrollment = c.result();
							refreshsearchpageMedicalEnrollmentResponse(listMedicalEnrollment, d -> {
								if(d.succeeded()) {
									eventHandler.handle(Future.succeededFuture(d.result()));
									LOGGER.info(String.format("refreshsearchpageMedicalEnrollment succeeded. "));
								} else {
									LOGGER.error(String.format("refreshsearchpageMedicalEnrollment failed. ", d.cause()));
									errorMedicalEnrollment(siteRequest, eventHandler, d);
								}
							});
						} else {
							LOGGER.error(String.format("refreshsearchpageMedicalEnrollment failed. ", c.cause()));
							errorMedicalEnrollment(siteRequest, eventHandler, c);
						}
					});
				} else {
					LOGGER.error(String.format("refreshsearchpageMedicalEnrollment failed. ", b.cause()));
					errorMedicalEnrollment(siteRequest, eventHandler, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("refreshsearchpageMedicalEnrollment failed. ", ex));
			errorMedicalEnrollment(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void refreshsearchpageMedicalEnrollmentResponse(SearchList<MedicalEnrollment> listMedicalEnrollment, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listMedicalEnrollment.getSiteRequest_();
		try {
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(siteRequest, buffer);
			siteRequest.setW(w);
			response200RefreshSearchPageMedicalEnrollment(listMedicalEnrollment, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("refreshsearchpageMedicalEnrollmentResponse failed. ", a.cause()));
					errorMedicalEnrollment(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("refreshsearchpageMedicalEnrollmentResponse failed. ", ex));
			errorMedicalEnrollment(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200RefreshSearchPageMedicalEnrollment(SearchList<MedicalEnrollment> listMedicalEnrollment, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listMedicalEnrollment.getSiteRequest_();
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(listMedicalEnrollment.getSiteRequest_(), buffer);
			EnrollmentPage page = new EnrollmentPage();
			SolrDocument pageSolrDocument = new SolrDocument();
			CaseInsensitiveHeaders requestHeaders = new CaseInsensitiveHeaders();
			siteRequest.setRequestHeaders(requestHeaders);

			pageSolrDocument.setField("pageUri_frFR_stored_string", "/refresh-enrollment");
			page.setPageSolrDocument(pageSolrDocument);
			page.setW(w);
			if(listMedicalEnrollment.size() == 1)
				siteRequest.setRequestPk(listMedicalEnrollment.get(0).getPk());
			siteRequest.setW(w);
			page.setListMedicalEnrollment(listMedicalEnrollment);
			page.setSiteRequest_(siteRequest);
			page.initDeepEnrollmentPage(siteRequest);
			page.html();
			eventHandler.handle(Future.succeededFuture(new OperationResponse(200, "OK", buffer, requestHeaders)));
		} catch(Exception e) {
			LOGGER.error(String.format("response200RefreshSearchPageMedicalEnrollment failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// General //

	public Future<MedicalEnrollment> defineIndexMedicalEnrollment(MedicalEnrollment medicalEnrollment, Handler<AsyncResult<MedicalEnrollment>> eventHandler) {
		Promise<MedicalEnrollment> promise = Promise.promise();
		SiteRequestEnUS siteRequest = medicalEnrollment.getSiteRequest_();
		defineMedicalEnrollment(medicalEnrollment, c -> {
			if(c.succeeded()) {
				attributeMedicalEnrollment(medicalEnrollment, d -> {
					if(d.succeeded()) {
						indexMedicalEnrollment(medicalEnrollment, e -> {
							if(e.succeeded()) {
								sqlCommitMedicalEnrollment(siteRequest, f -> {
									if(f.succeeded()) {
										sqlCloseMedicalEnrollment(siteRequest, g -> {
											if(g.succeeded()) {
												refreshMedicalEnrollment(medicalEnrollment, h -> {
													if(h.succeeded()) {
														eventHandler.handle(Future.succeededFuture(medicalEnrollment));
														promise.complete(medicalEnrollment);
													} else {
														LOGGER.error(String.format("refreshMedicalEnrollment failed. ", h.cause()));
														errorMedicalEnrollment(siteRequest, null, h);
													}
												});
											} else {
												LOGGER.error(String.format("defineIndexMedicalEnrollment failed. ", g.cause()));
												errorMedicalEnrollment(siteRequest, null, g);
											}
										});
									} else {
										LOGGER.error(String.format("defineIndexMedicalEnrollment failed. ", f.cause()));
										errorMedicalEnrollment(siteRequest, null, f);
									}
								});
							} else {
								LOGGER.error(String.format("defineIndexMedicalEnrollment failed. ", e.cause()));
								errorMedicalEnrollment(siteRequest, null, e);
							}
						});
					} else {
						LOGGER.error(String.format("defineIndexMedicalEnrollment failed. ", d.cause()));
						errorMedicalEnrollment(siteRequest, null, d);
					}
				});
			} else {
				LOGGER.error(String.format("defineIndexMedicalEnrollment failed. ", c.cause()));
				errorMedicalEnrollment(siteRequest, null, c);
			}
		});
		return promise.future();
	}

	public void createMedicalEnrollment(SiteRequestEnUS siteRequest, Handler<AsyncResult<MedicalEnrollment>> eventHandler) {
		try {
			Transaction tx = siteRequest.getTx();
			String userId = siteRequest.getUserId();
			ZonedDateTime created = Optional.ofNullable(siteRequest.getJsonObject()).map(j -> j.getString("created")).map(s -> ZonedDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of(siteRequest.getSiteConfig_().getSiteZone())))).orElse(ZonedDateTime.now(ZoneId.of(siteRequest.getSiteConfig_().getSiteZone())));

			tx.preparedQuery(
					SiteContextEnUS.SQL_create
					, Tuple.of(MedicalEnrollment.class.getCanonicalName(), userId)
					, Collectors.toList()
					, createAsync
			-> {
				if(createAsync.succeeded()) {
					Row createLine = createAsync.result().value().stream().findFirst().orElseGet(() -> null);
					Long pk = createLine.getLong(0);
					MedicalEnrollment o = new MedicalEnrollment();
					o.setPk(pk);
					o.setSiteRequest_(siteRequest);
					eventHandler.handle(Future.succeededFuture(o));
				} else {
					LOGGER.error(String.format("createMedicalEnrollment failed. ", createAsync.cause()));
					eventHandler.handle(Future.failedFuture(createAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("createMedicalEnrollment failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void errorMedicalEnrollment(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler, AsyncResult<?> resultAsync) {
		Throwable e = resultAsync.cause();
		ExceptionUtils.printRootCauseStackTrace(e);
		OperationResponse responseOperation = new OperationResponse(400, "BAD REQUEST", 
			Buffer.buffer().appendString(
				new JsonObject() {{
					put("error", new JsonObject()
						.put("message", Optional.ofNullable(e).map(Throwable::getMessage).orElse(null))
					);
				}}.encodePrettily()
			)
			, new CaseInsensitiveHeaders()
		);
		SiteConfig siteConfig = siteRequest.getSiteConfig_();
		SiteContextEnUS siteContext = siteRequest.getSiteContext_();
		MailClient mailClient = siteContext.getMailClient();
		MailMessage message = new MailMessage();
		message.setFrom(siteConfig.getEmailFrom());
		message.setTo(siteConfig.getEmailAdmin());
		if(e != null)
			message.setText(ExceptionUtils.getStackTrace(e));
		message.setSubject(String.format(siteConfig.getSiteBaseUrl() + " " + Optional.ofNullable(e).map(Throwable::getMessage).orElse(null)));
		WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
		workerExecutor.executeBlocking(
			blockingCodeHandler -> {
				mailClient.sendMail(message, result -> {
					if (result.succeeded()) {
						LOGGER.info(result.result());
					} else {
						LOGGER.error(result.cause());
					}
				});
			}, resultHandler -> {
			}
		);
		sqlRollbackMedicalEnrollment(siteRequest, a -> {
			if(a.succeeded()) {
				LOGGER.info(String.format("sql rollback. "));
				sqlCloseMedicalEnrollment(siteRequest, b -> {
					if(b.succeeded()) {
						LOGGER.info(String.format("sql close. "));
						if(eventHandler != null)
							eventHandler.handle(Future.succeededFuture(responseOperation));
					} else {
						if(eventHandler != null)
							eventHandler.handle(Future.succeededFuture(responseOperation));
					}
				});
			} else {
				if(eventHandler != null)
					eventHandler.handle(Future.succeededFuture(responseOperation));
			}
		});
	}

	public void sqlConnectionMedicalEnrollment(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			PgPool pgPool = siteRequest.getSiteContext_().getPgPool();

			if(pgPool == null) {
				eventHandler.handle(Future.succeededFuture());
			} else {
				pgPool.getConnection(a -> {
					if(a.succeeded()) {
						SqlConnection sqlConnection = a.result();
						siteRequest.setSqlConnection(sqlConnection);
						eventHandler.handle(Future.succeededFuture());
					} else {
						LOGGER.error(String.format("sqlConnectionMedicalEnrollment failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlMedicalEnrollment failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlTransactionMedicalEnrollment(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SqlConnection sqlConnection = siteRequest.getSqlConnection();

			if(sqlConnection == null) {
				eventHandler.handle(Future.succeededFuture());
			} else {
				Transaction tx = sqlConnection.begin();
				siteRequest.setTx(tx);
				eventHandler.handle(Future.succeededFuture());
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlTransactionMedicalEnrollment failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlCommitMedicalEnrollment(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			Transaction tx = siteRequest.getTx();

			if(tx == null) {
				eventHandler.handle(Future.succeededFuture());
			} else {
				tx.commit(a -> {
					if(a.succeeded()) {
						siteRequest.setTx(null);
						eventHandler.handle(Future.succeededFuture());
					} else if("Transaction already completed".equals(a.cause().getMessage())) {
						siteRequest.setTx(null);
						eventHandler.handle(Future.succeededFuture());
					} else {
						LOGGER.error(String.format("sqlCommitMedicalEnrollment failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlMedicalEnrollment failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlRollbackMedicalEnrollment(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			Transaction tx = siteRequest.getTx();

			if(tx == null) {
				eventHandler.handle(Future.succeededFuture());
			} else {
				tx.rollback(a -> {
					if(a.succeeded()) {
						siteRequest.setTx(null);
						eventHandler.handle(Future.succeededFuture());
					} else if("Transaction already completed".equals(a.cause().getMessage())) {
						siteRequest.setTx(null);
						eventHandler.handle(Future.succeededFuture());
					} else {
						LOGGER.error(String.format("sqlRollbackMedicalEnrollment failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlMedicalEnrollment failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlCloseMedicalEnrollment(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SqlConnection sqlConnection = siteRequest.getSqlConnection();

			if(sqlConnection == null) {
				eventHandler.handle(Future.succeededFuture());
			} else {
				sqlConnection.close();
				siteRequest.setSqlConnection(null);
				eventHandler.handle(Future.succeededFuture());
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlCloseMedicalEnrollment failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public SiteRequestEnUS generateSiteRequestEnUSForMedicalEnrollment(SiteContextEnUS siteContext, OperationRequest operationRequest) {
		return generateSiteRequestEnUSForMedicalEnrollment(siteContext, operationRequest, null);
	}

	public SiteRequestEnUS generateSiteRequestEnUSForMedicalEnrollment(SiteContextEnUS siteContext, OperationRequest operationRequest, JsonObject body) {
		Vertx vertx = siteContext.getVertx();
		SiteRequestEnUS siteRequest = new SiteRequestEnUS();
		siteRequest.setJsonObject(body);
		siteRequest.setVertx(vertx);
		siteRequest.setSiteContext_(siteContext);
		siteRequest.setSiteConfig_(siteContext.getSiteConfig());
		siteRequest.setOperationRequest(operationRequest);
		siteRequest.initDeepSiteRequestEnUS(siteRequest);

		return siteRequest;
	}

	public void userMedicalEnrollment(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			String userId = siteRequest.getUserId();
			if(userId == null) {
				eventHandler.handle(Future.succeededFuture());
			} else {
				sqlConnectionMedicalEnrollment(siteRequest, a -> {
					if(a.succeeded()) {
						sqlTransactionMedicalEnrollment(siteRequest, b -> {
							if(b.succeeded()) {
								Transaction tx = siteRequest.getTx();
								tx.preparedQuery(
										SiteContextEnUS.SQL_selectC
										, Tuple.of("org.computate.medicale.enUS.user.SiteUser", userId)
										, Collectors.toList()
										, selectCAsync
								-> {
									if(selectCAsync.succeeded()) {
										try {
											Row userValues = selectCAsync.result().value().stream().findFirst().orElse(null);
											SiteUserEnUSApiServiceImpl userService = new SiteUserEnUSApiServiceImpl(siteContext);
											if(userValues == null) {
												JsonObject userVertx = siteRequest.getOperationRequest().getUser();
												JsonObject jsonPrincipal = KeycloakHelper.parseToken(userVertx.getString("access_token"));

												JsonObject jsonObject = new JsonObject();
												jsonObject.put("userName", jsonPrincipal.getString("preferred_username"));
												jsonObject.put("userFirstName", jsonPrincipal.getString("given_name"));
												jsonObject.put("userLastName", jsonPrincipal.getString("family_name"));
												jsonObject.put("userId", jsonPrincipal.getString("sub"));
												userMedicalEnrollmentDefine(siteRequest, jsonObject, false);

												SiteRequestEnUS siteRequest2 = new SiteRequestEnUS();
												siteRequest2.setTx(siteRequest.getTx());
												siteRequest2.setSqlConnection(siteRequest.getSqlConnection());
												siteRequest2.setJsonObject(jsonObject);
												siteRequest2.setVertx(siteRequest.getVertx());
												siteRequest2.setSiteContext_(siteContext);
												siteRequest2.setSiteConfig_(siteContext.getSiteConfig());
												siteRequest2.setUserId(siteRequest.getUserId());
												siteRequest2.initDeepSiteRequestEnUS(siteRequest);

												ApiRequest apiRequest = new ApiRequest();
												apiRequest.setRows(1);
												apiRequest.setNumFound(1L);
												apiRequest.setNumPATCH(0L);
												apiRequest.initDeepApiRequest(siteRequest2);
												siteRequest2.setApiRequest_(apiRequest);

												userService.createSiteUser(siteRequest2, c -> {
													if(c.succeeded()) {
														SiteUser siteUser = c.result();
														userService.sqlPOSTSiteUser(siteUser, false, d -> {
															if(d.succeeded()) {
																userService.defineIndexSiteUser(siteUser, e -> {
																	if(e.succeeded()) {
																		siteRequest.setSiteUser(siteUser);
																		siteRequest.setUserName(jsonPrincipal.getString("preferred_username"));
																		siteRequest.setUserFirstName(jsonPrincipal.getString("given_name"));
																		siteRequest.setUserLastName(jsonPrincipal.getString("family_name"));
																		siteRequest.setUserId(jsonPrincipal.getString("sub"));
																		siteRequest.setUserKey(siteUser.getPk());
																		eventHandler.handle(Future.succeededFuture());
																	} else {
																		errorMedicalEnrollment(siteRequest, eventHandler, e);
																	}
																});
															} else {
																errorMedicalEnrollment(siteRequest, eventHandler, d);
															}
														});
													} else {
														errorMedicalEnrollment(siteRequest, eventHandler, c);
													}
												});
											} else {
												Long pkUser = userValues.getLong(0);
												SearchList<SiteUser> searchList = new SearchList<SiteUser>();
												searchList.setQuery("*:*");
												searchList.setStore(true);
												searchList.setC(SiteUser.class);
												searchList.addFilterQuery("userId_indexed_string:" + ClientUtils.escapeQueryChars(userId));
												searchList.addFilterQuery("pk_indexed_long:" + pkUser);
												searchList.initDeepSearchList(siteRequest);
												SiteUser siteUser1 = searchList.getList().stream().findFirst().orElse(null);

												JsonObject userVertx = siteRequest.getOperationRequest().getUser();
												JsonObject jsonPrincipal = KeycloakHelper.parseToken(userVertx.getString("access_token"));

												JsonObject jsonObject = new JsonObject();
												jsonObject.put("setUserName", jsonPrincipal.getString("preferred_username"));
												jsonObject.put("setUserFirstName", jsonPrincipal.getString("given_name"));
												jsonObject.put("setUserLastName", jsonPrincipal.getString("family_name"));
												jsonObject.put("setUserCompleteName", jsonPrincipal.getString("name"));
												jsonObject.put("setCustomerProfileId", Optional.ofNullable(siteUser1).map(u -> u.getCustomerProfileId()).orElse(null));
												jsonObject.put("setUserId", jsonPrincipal.getString("sub"));
												jsonObject.put("setUserEmail", jsonPrincipal.getString("email"));
												Boolean define = userMedicalEnrollmentDefine(siteRequest, jsonObject, true);
												if(define) {
													SiteUser siteUser;
													if(siteUser1 == null) {
														siteUser = new SiteUser();
														siteUser.setPk(pkUser);
														siteUser.setSiteRequest_(siteRequest);
													} else {
														siteUser = siteUser1;
													}

													SiteRequestEnUS siteRequest2 = new SiteRequestEnUS();
													siteRequest2.setTx(siteRequest.getTx());
													siteRequest2.setSqlConnection(siteRequest.getSqlConnection());
													siteRequest2.setJsonObject(jsonObject);
													siteRequest2.setVertx(siteRequest.getVertx());
													siteRequest2.setSiteContext_(siteContext);
													siteRequest2.setSiteConfig_(siteContext.getSiteConfig());
													siteRequest2.setUserId(siteRequest.getUserId());
													siteRequest2.setUserKey(siteRequest.getUserKey());
													siteRequest2.initDeepSiteRequestEnUS(siteRequest);
													siteUser.setSiteRequest_(siteRequest2);

													ApiRequest apiRequest = new ApiRequest();
													apiRequest.setRows(1);
													apiRequest.setNumFound(1L);
													apiRequest.setNumPATCH(0L);
													apiRequest.initDeepApiRequest(siteRequest2);
													siteRequest2.setApiRequest_(apiRequest);

													userService.sqlPATCHSiteUser(siteUser, false, d -> {
														if(d.succeeded()) {
															SiteUser siteUser2 = d.result();
															userService.defineIndexSiteUser(siteUser2, e -> {
																if(e.succeeded()) {
																	siteRequest.setSiteUser(siteUser2);
																	siteRequest.setUserName(siteUser2.getUserName());
																	siteRequest.setUserFirstName(siteUser2.getUserFirstName());
																	siteRequest.setUserLastName(siteUser2.getUserLastName());
																	siteRequest.setUserId(siteUser2.getUserId());
																	siteRequest.setUserKey(siteUser2.getPk());
																	eventHandler.handle(Future.succeededFuture());
																} else {
																	errorMedicalEnrollment(siteRequest, eventHandler, e);
																}
															});
														} else {
															errorMedicalEnrollment(siteRequest, eventHandler, d);
														}
													});
												} else {
													siteRequest.setSiteUser(siteUser1);
													siteRequest.setUserName(siteUser1.getUserName());
													siteRequest.setUserFirstName(siteUser1.getUserFirstName());
													siteRequest.setUserLastName(siteUser1.getUserLastName());
													siteRequest.setUserId(siteUser1.getUserId());
													siteRequest.setUserKey(siteUser1.getPk());
													sqlRollbackMedicalEnrollment(siteRequest, c -> {
														if(c.succeeded()) {
															eventHandler.handle(Future.succeededFuture());
														} else {
															eventHandler.handle(Future.failedFuture(c.cause()));
															errorMedicalEnrollment(siteRequest, eventHandler, c);
														}
													});
												}
											}
										} catch(Exception e) {
											LOGGER.error(String.format("userMedicalEnrollment failed. ", e));
											eventHandler.handle(Future.failedFuture(e));
										}
									} else {
										LOGGER.error(String.format("userMedicalEnrollment failed. ", selectCAsync.cause()));
										eventHandler.handle(Future.failedFuture(selectCAsync.cause()));
									}
								});
							} else {
								LOGGER.error(String.format("userMedicalEnrollment failed. ", b.cause()));
								eventHandler.handle(Future.failedFuture(b.cause()));
							}
						});
					} else {
						LOGGER.error(String.format("userMedicalEnrollment failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("userMedicalEnrollment failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public Boolean userMedicalEnrollmentDefine(SiteRequestEnUS siteRequest, JsonObject jsonObject, Boolean patch) {
		if(patch) {
			return jsonObject.getString("setCustomerProfileId") == null;
		} else {
			return jsonObject.getString("customerProfileId") == null;
		}
	}

	public void aSearchMedicalEnrollmentQ(String uri, String apiMethod, SearchList<MedicalEnrollment> searchList, String entityVar, String valueIndexed, String varIndexed) {
		searchList.setQuery(varIndexed + ":" + ("*".equals(valueIndexed) ? valueIndexed : ClientUtils.escapeQueryChars(valueIndexed)));
		if(!"*".equals(entityVar)) {
			searchList.setHighlight(true);
			searchList.setHighlightSnippets(3);
			searchList.addHighlightField(varIndexed);
			searchList.setParam("hl.encoder", "html");
		}
	}

	public void aSearchMedicalEnrollmentFq(String uri, String apiMethod, SearchList<MedicalEnrollment> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		searchList.addFilterQuery(varIndexed + ":" + ClientUtils.escapeQueryChars(valueIndexed));
	}

	public void aSearchMedicalEnrollmentSort(String uri, String apiMethod, SearchList<MedicalEnrollment> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		searchList.addSort(varIndexed, ORDER.valueOf(valueIndexed));
	}

	public void aSearchMedicalEnrollmentRows(String uri, String apiMethod, SearchList<MedicalEnrollment> searchList, Integer valueRows) {
			searchList.setRows(apiMethod != null && apiMethod.contains("Search") ? valueRows : 10);
	}

	public void aSearchMedicalEnrollmentStart(String uri, String apiMethod, SearchList<MedicalEnrollment> searchList, Integer valueStart) {
		searchList.setStart(valueStart);
	}

	public void aSearchMedicalEnrollmentVar(String uri, String apiMethod, SearchList<MedicalEnrollment> searchList, String var, String value) {
		searchList.getSiteRequest_().getRequestVars().put(var, value);
	}

	public void aSearchMedicalEnrollmentUri(String uri, String apiMethod, SearchList<MedicalEnrollment> searchList) {
	}

	public void varsMedicalEnrollment(SiteRequestEnUS siteRequest, Handler<AsyncResult<SearchList<OperationResponse>>> eventHandler) {
		try {
			OperationRequest operationRequest = siteRequest.getOperationRequest();

			operationRequest.getParams().getJsonObject("query").forEach(paramRequest -> {
				String entityVar = null;
				String valueIndexed = null;
				String paramName = paramRequest.getKey();
				Object paramValuesObject = paramRequest.getValue();
				JsonArray paramObjects = paramValuesObject instanceof JsonArray ? (JsonArray)paramValuesObject : new JsonArray().add(paramValuesObject);

				try {
					for(Object paramObject : paramObjects) {
						switch(paramName) {
							case "var":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								siteRequest.getRequestVars().put(entityVar, valueIndexed);
								break;
						}
					}
				} catch(Exception e) {
					LOGGER.error(String.format("aSearchMedicalEnrollment failed. ", e));
					eventHandler.handle(Future.failedFuture(e));
				}
			});
			eventHandler.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOGGER.error(String.format("aSearchMedicalEnrollment failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void aSearchMedicalEnrollment(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, String uri, String apiMethod, Handler<AsyncResult<SearchList<MedicalEnrollment>>> eventHandler) {
		try {
			OperationRequest operationRequest = siteRequest.getOperationRequest();
			String entityListStr = siteRequest.getOperationRequest().getParams().getJsonObject("query").getString("fl");
			String[] entityList = entityListStr == null ? null : entityListStr.split(",\\s*");
			SearchList<MedicalEnrollment> searchList = new SearchList<MedicalEnrollment>();
			searchList.setPopulate(populate);
			searchList.setStore(store);
			searchList.setQuery("*:*");
			searchList.setC(MedicalEnrollment.class);
			searchList.setSiteRequest_(siteRequest);
			if(entityList != null)
				searchList.addFields(entityList);
			searchList.add("json.facet", "{max_modified:'max(modified_indexed_date)'}");

			String id = operationRequest.getParams().getJsonObject("path").getString("id");
			if(id != null) {
				searchList.addFilterQuery("(id:" + ClientUtils.escapeQueryChars(id) + " OR objectId_indexed_string:" + ClientUtils.escapeQueryChars(id) + ")");
			}

			List<String> roles = Arrays.asList("SiteAdmin");
			if(
					!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles)
					&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles)
					) {
				searchList.addFilterQuery("sessionId_indexed_string:" + ClientUtils.escapeQueryChars(Optional.ofNullable(siteRequest.getSessionId()).orElse("-----")) + " OR " + "sessionId_indexed_string:" + ClientUtils.escapeQueryChars(Optional.ofNullable(siteRequest.getSessionIdBefore()).orElse("-----"))
						+ " OR userKeys_indexed_longs:" + Optional.ofNullable(siteRequest.getUserKey()).orElse(0L));
			}

			operationRequest.getParams().getJsonObject("query").forEach(paramRequest -> {
				String entityVar = null;
				String valueIndexed = null;
				String varIndexed = null;
				String valueSort = null;
				Integer valueStart = null;
				Integer valueRows = null;
				String paramName = paramRequest.getKey();
				Object paramValuesObject = paramRequest.getValue();
				JsonArray paramObjects = paramValuesObject instanceof JsonArray ? (JsonArray)paramValuesObject : new JsonArray().add(paramValuesObject);

				try {
					for(Object paramObject : paramObjects) {
						switch(paramName) {
							case "q":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								varIndexed = "*".equals(entityVar) ? entityVar : MedicalEnrollment.varSearchMedicalEnrollment(entityVar);
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								valueIndexed = StringUtils.isEmpty(valueIndexed) ? "*" : valueIndexed;
								aSearchMedicalEnrollmentQ(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "fq":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								varIndexed = MedicalEnrollment.varIndexedMedicalEnrollment(entityVar);
								aSearchMedicalEnrollmentFq(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "sort":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, " "));
								valueIndexed = StringUtils.trim(StringUtils.substringAfter((String)paramObject, " "));
								varIndexed = MedicalEnrollment.varIndexedMedicalEnrollment(entityVar);
								aSearchMedicalEnrollmentSort(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "start":
								valueStart = (Integer)paramObject;
								aSearchMedicalEnrollmentStart(uri, apiMethod, searchList, valueStart);
								break;
							case "rows":
								valueRows = (Integer)paramObject;
								aSearchMedicalEnrollmentRows(uri, apiMethod, searchList, valueRows);
								break;
							case "var":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								aSearchMedicalEnrollmentVar(uri, apiMethod, searchList, entityVar, valueIndexed);
								break;
						}
					}
					aSearchMedicalEnrollmentUri(uri, apiMethod, searchList);
				} catch(Exception e) {
					LOGGER.error(String.format("aSearchMedicalEnrollment failed. ", e));
					eventHandler.handle(Future.failedFuture(e));
				}
			});
			if(searchList.getSorts().size() == 0) {
				searchList.addSort("created_indexed_date", ORDER.desc);
			}
			searchList.initDeepForClass(siteRequest);
			eventHandler.handle(Future.succeededFuture(searchList));
		} catch(Exception e) {
			LOGGER.error(String.format("aSearchMedicalEnrollment failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void defineMedicalEnrollment(MedicalEnrollment o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			Transaction tx = siteRequest.getTx();
			Long pk = o.getPk();
			tx.preparedQuery(
					SiteContextEnUS.SQL_define
					, Tuple.of(pk)
					, Collectors.toList()
					, defineAsync
			-> {
				if(defineAsync.succeeded()) {
					try {
						for(Row definition : defineAsync.result().value()) {
							try {
								o.defineForClass(definition.getString(0), definition.getString(1));
							} catch(Exception e) {
								LOGGER.error(e);
							}
						}
						eventHandler.handle(Future.succeededFuture());
					} catch(Exception e) {
						LOGGER.error(String.format("defineMedicalEnrollment failed. ", e));
						eventHandler.handle(Future.failedFuture(e));
					}
				} else {
					LOGGER.error(String.format("defineMedicalEnrollment failed. ", defineAsync.cause()));
					eventHandler.handle(Future.failedFuture(defineAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("defineMedicalEnrollment failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void attributeMedicalEnrollment(MedicalEnrollment o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			Transaction tx = siteRequest.getTx();
			Long pk = o.getPk();
			tx.preparedQuery(
					SiteContextEnUS.SQL_attribute
					, Tuple.of(pk, pk)
					, Collectors.toList()
					, attributeAsync
			-> {
				try {
					if(attributeAsync.succeeded()) {
						if(attributeAsync.result() != null) {
							for(Row definition : attributeAsync.result().value()) {
								if(pk.equals(definition.getLong(0)))
									o.attributeForClass(definition.getString(2), definition.getLong(1));
								else
									o.attributeForClass(definition.getString(3), definition.getLong(0));
							}
						}
						eventHandler.handle(Future.succeededFuture());
					} else {
						LOGGER.error(String.format("attributeMedicalEnrollment failed. ", attributeAsync.cause()));
						eventHandler.handle(Future.failedFuture(attributeAsync.cause()));
					}
				} catch(Exception e) {
					LOGGER.error(String.format("attributeMedicalEnrollment failed. ", e));
					eventHandler.handle(Future.failedFuture(e));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("attributeMedicalEnrollment failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void indexMedicalEnrollment(MedicalEnrollment o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			o.initDeepForClass(siteRequest);
			o.indexForClass();
			eventHandler.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOGGER.error(String.format("indexMedicalEnrollment failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void refreshMedicalEnrollment(MedicalEnrollment o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Boolean refresh = !"false".equals(siteRequest.getRequestVars().get("refresh"));
			if(refresh && BooleanUtils.isFalse(Optional.ofNullable(siteRequest.getApiRequest_()).map(ApiRequest::getEmpty).orElse(true))) {
				SearchList<MedicalEnrollment> searchList = new SearchList<MedicalEnrollment>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(MedicalEnrollment.class);
				searchList.addFilterQuery("modified_indexed_date:[" + DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(siteRequest.getApiRequest_().getCreated().toInstant(), ZoneId.of("UTC"))) + " TO *]");
				searchList.add("json.facet", "{patientKey:{terms:{field:patientKey_indexed_longs, limit:1000}}}");
				searchList.add("json.facet", "{userKeys:{terms:{field:userKeys_indexed_longs, limit:1000}}}");
				searchList.setRows(1000);
				searchList.initDeepSearchList(siteRequest);
				List<Future> futures = new ArrayList<>();

				for(int i=0; i < pks.size(); i++) {
					Long pk2 = pks.get(i);
					String classSimpleName2 = classes.get(i);

					if("MedicalPatient".equals(classSimpleName2) && pk2 != null) {
						SearchList<MedicalPatient> searchList2 = new SearchList<MedicalPatient>();
						searchList2.setStore(true);
						searchList2.setQuery("*:*");
						searchList2.setC(MedicalPatient.class);
						searchList2.addFilterQuery("pk_indexed_long:" + pk2);
						searchList2.setRows(1);
						searchList2.initDeepSearchList(siteRequest);
						MedicalPatient o2 = searchList2.getList().stream().findFirst().orElse(null);

						if(o2 != null) {
							MedicalPatientEnUSGenApiServiceImpl service = new MedicalPatientEnUSGenApiServiceImpl(siteRequest.getSiteContext_());
							SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForMedicalEnrollment(siteContext, siteRequest.getOperationRequest(), new JsonObject());
							ApiRequest apiRequest2 = new ApiRequest();
							apiRequest2.setRows(1);
							apiRequest2.setNumFound(1l);
							apiRequest2.setNumPATCH(0L);
							apiRequest2.initDeepApiRequest(siteRequest2);
							siteRequest2.setApiRequest_(apiRequest2);
							siteRequest2.getVertx().eventBus().publish("websocketMedicalPatient", JsonObject.mapFrom(apiRequest2).toString());

							o2.setPk(pk2);
							o2.setSiteRequest_(siteRequest2);
							futures.add(
								service.patchMedicalPatientFuture(o2, false, a -> {
									if(a.succeeded()) {
									} else {
										LOGGER.info(String.format("MedicalPatient %s failed. ", pk2));
										eventHandler.handle(Future.failedFuture(a.cause()));
									}
								})
							);
						}
					}

					if("SiteUser".equals(classSimpleName2) && pk2 != null) {
						SearchList<SiteUser> searchList2 = new SearchList<SiteUser>();
						searchList2.setStore(true);
						searchList2.setQuery("*:*");
						searchList2.setC(SiteUser.class);
						searchList2.addFilterQuery("pk_indexed_long:" + pk2);
						searchList2.setRows(1);
						searchList2.initDeepSearchList(siteRequest);
						SiteUser o2 = searchList2.getList().stream().findFirst().orElse(null);

						if(o2 != null) {
							SiteUserEnUSGenApiServiceImpl service = new SiteUserEnUSGenApiServiceImpl(siteRequest.getSiteContext_());
							SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForMedicalEnrollment(siteContext, siteRequest.getOperationRequest(), new JsonObject());
							ApiRequest apiRequest2 = new ApiRequest();
							apiRequest2.setRows(1);
							apiRequest2.setNumFound(1l);
							apiRequest2.setNumPATCH(0L);
							apiRequest2.initDeepApiRequest(siteRequest2);
							siteRequest2.setApiRequest_(apiRequest2);
							siteRequest2.getVertx().eventBus().publish("websocketSiteUser", JsonObject.mapFrom(apiRequest2).toString());

							o2.setPk(pk2);
							o2.setSiteRequest_(siteRequest2);
							futures.add(
								service.patchSiteUserFuture(o2, false, a -> {
									if(a.succeeded()) {
									} else {
										LOGGER.info(String.format("SiteUser %s failed. ", pk2));
										eventHandler.handle(Future.failedFuture(a.cause()));
									}
								})
							);
						}
					}
				}

				CompositeFuture.all(futures).setHandler(a -> {
					if(a.succeeded()) {
						MedicalEnrollmentEnUSApiServiceImpl service = new MedicalEnrollmentEnUSApiServiceImpl(siteRequest.getSiteContext_());
						List<Future> futures2 = new ArrayList<>();
						for(MedicalEnrollment o2 : searchList.getList()) {
							SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForMedicalEnrollment(siteContext, siteRequest.getOperationRequest(), new JsonObject());
							o2.setSiteRequest_(siteRequest2);
							futures2.add(
								service.patchMedicalEnrollmentFuture(o2, false, b -> {
									if(b.succeeded()) {
									} else {
										LOGGER.info(String.format("MedicalEnrollment %s failed. ", o2.getPk()));
										eventHandler.handle(Future.failedFuture(b.cause()));
									}
								})
							);
						}

						CompositeFuture.all(futures2).setHandler(b -> {
							if(b.succeeded()) {
								eventHandler.handle(Future.succeededFuture());
							} else {
								LOGGER.error("Refresh relations failed. ", b.cause());
								errorMedicalEnrollment(siteRequest, eventHandler, b);
							}
						});
					} else {
						LOGGER.error("Refresh relations failed. ", a.cause());
						errorMedicalEnrollment(siteRequest, eventHandler, a);
					}
				});
			} else {
				eventHandler.handle(Future.succeededFuture());
			}
		} catch(Exception e) {
			LOGGER.error(String.format("refreshMedicalEnrollment failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}
}
