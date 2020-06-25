package org.computate.medicale.enUS.patient;

import org.computate.medicale.enUS.enrollment.MedicalEnrollmentEnUSGenApiServiceImpl;
import org.computate.medicale.enUS.enrollment.MedicalEnrollment;
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
 * CanonicalName.frFR: org.computate.medicale.frFR.patient.PatientMedicaleFrFRGenApiServiceImpl
 **/
public class MedicalPatientEnUSGenApiServiceImpl implements MedicalPatientEnUSGenApiService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(MedicalPatientEnUSGenApiServiceImpl.class);

	protected static final String SERVICE_ADDRESS = "MedicalPatientEnUSApiServiceImpl";

	protected SiteContextEnUS siteContext;

	public MedicalPatientEnUSGenApiServiceImpl(SiteContextEnUS siteContext) {
		this.siteContext = siteContext;
	}

	// POST //

	@Override
	public void postMedicalPatient(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForMedicalPatient(siteContext, operationRequest, body);
		try {
			LOGGER.info(String.format("postMedicalPatient started. "));
			userMedicalPatient(siteRequest, b -> {
				if(b.succeeded()) {
					ApiRequest apiRequest = new ApiRequest();
					apiRequest.setRows(1);
					apiRequest.setNumFound(1L);
					apiRequest.setNumPATCH(0L);
					apiRequest.initDeepApiRequest(siteRequest);
					siteRequest.setApiRequest_(apiRequest);
					siteRequest.getVertx().eventBus().publish("websocketMedicalPatient", JsonObject.mapFrom(apiRequest).toString());
					postMedicalPatientFuture(siteRequest, false, c -> {
						if(c.succeeded()) {
							MedicalPatient medicalPatient = c.result();
							apiRequest.setPk(medicalPatient.getPk());
							postMedicalPatientResponse(medicalPatient, d -> {
									if(d.succeeded()) {
									eventHandler.handle(Future.succeededFuture(d.result()));
									LOGGER.info(String.format("postMedicalPatient succeeded. "));
								} else {
									LOGGER.error(String.format("postMedicalPatient failed. ", d.cause()));
									errorMedicalPatient(siteRequest, eventHandler, d);
								}
							});
						} else {
							LOGGER.error(String.format("postMedicalPatient failed. ", c.cause()));
							errorMedicalPatient(siteRequest, eventHandler, c);
						}
					});
				} else {
					LOGGER.error(String.format("postMedicalPatient failed. ", b.cause()));
					errorMedicalPatient(siteRequest, eventHandler, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("postMedicalPatient failed. ", ex));
			errorMedicalPatient(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public Future<MedicalPatient> postMedicalPatientFuture(SiteRequestEnUS siteRequest, Boolean inheritPk, Handler<AsyncResult<MedicalPatient>> eventHandler) {
		Promise<MedicalPatient> promise = Promise.promise();
		try {
			sqlConnectionMedicalPatient(siteRequest, a -> {
				if(a.succeeded()) {
					sqlTransactionMedicalPatient(siteRequest, b -> {
						if(b.succeeded()) {
							createMedicalPatient(siteRequest, c -> {
								if(c.succeeded()) {
									MedicalPatient medicalPatient = c.result();
									sqlPOSTMedicalPatient(medicalPatient, inheritPk, d -> {
										if(d.succeeded()) {
											defineIndexMedicalPatient(medicalPatient, e -> {
												if(e.succeeded()) {
													ApiRequest apiRequest = siteRequest.getApiRequest_();
													if(apiRequest != null) {
														apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
														medicalPatient.apiRequestMedicalPatient();
														siteRequest.getVertx().eventBus().publish("websocketMedicalPatient", JsonObject.mapFrom(apiRequest).toString());
													}
													eventHandler.handle(Future.succeededFuture(medicalPatient));
													promise.complete(medicalPatient);
												} else {
													LOGGER.error(String.format("postMedicalPatientFuture failed. ", e.cause()));
													eventHandler.handle(Future.failedFuture(e.cause()));
												}
											});
										} else {
											LOGGER.error(String.format("postMedicalPatientFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOGGER.error(String.format("postMedicalPatientFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOGGER.error(String.format("postMedicalPatientFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOGGER.error(String.format("postMedicalPatientFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("postMedicalPatientFuture failed. ", e));
			errorMedicalPatient(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPOSTMedicalPatient(MedicalPatient o, Boolean inheritPk, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
					case "enrollmentKeys":
						for(Long l : jsonObject.getJsonArray(entityVar).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
							if(l != null) {
								SearchList<MedicalEnrollment> searchList = new SearchList<MedicalEnrollment>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(MedicalEnrollment.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_addA
												, Tuple.of(pk, "enrollmentKeys", l2, "patientKey")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value MedicalPatient.enrollmentKeys failed", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("MedicalEnrollment");
									}
								}
							}
						}
						break;
					case "patientFirstName":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "patientFirstName", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalPatient.patientFirstName failed", b.cause())));
							});
						}));
						break;
					case "patientFirstNamePreferred":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "patientFirstNamePreferred", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalPatient.patientFirstNamePreferred failed", b.cause())));
							});
						}));
						break;
					case "familyName":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "familyName", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalPatient.familyName failed", b.cause())));
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
									a.handle(Future.failedFuture(new Exception("value MedicalPatient.patientBirthDate failed", b.cause())));
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
					LOGGER.error(String.format("sqlPOSTMedicalPatient failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPOSTMedicalPatient failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void postMedicalPatientResponse(MedicalPatient medicalPatient, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = medicalPatient.getSiteRequest_();
		try {
			response200POSTMedicalPatient(medicalPatient, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("postMedicalPatientResponse failed. ", a.cause()));
					errorMedicalPatient(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("postMedicalPatientResponse failed. ", ex));
			errorMedicalPatient(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200POSTMedicalPatient(MedicalPatient o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			JsonObject json = JsonObject.mapFrom(o);
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200POSTMedicalPatient failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTImport //

	@Override
	public void putimportMedicalPatient(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForMedicalPatient(siteContext, operationRequest, body);
		try {
			LOGGER.info(String.format("putimportMedicalPatient started. "));
			userMedicalPatient(siteRequest, b -> {
				if(b.succeeded()) {
					putimportMedicalPatientResponse(siteRequest, c -> {
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
										siteRequest.getVertx().eventBus().publish("websocketMedicalPatient", JsonObject.mapFrom(apiRequest).toString());
										varsMedicalPatient(siteRequest, d -> {
											if(d.succeeded()) {
												listPUTImportMedicalPatient(apiRequest, siteRequest, e -> {
													if(e.succeeded()) {
														putimportMedicalPatientResponse(siteRequest, f -> {
															if(e.succeeded()) {
																LOGGER.info(String.format("putimportMedicalPatient succeeded. "));
																blockingCodeHandler.handle(Future.succeededFuture(e.result()));
															} else {
																LOGGER.error(String.format("putimportMedicalPatient failed. ", f.cause()));
																errorMedicalPatient(siteRequest, null, f);
															}
														});
													} else {
														LOGGER.error(String.format("putimportMedicalPatient failed. ", e.cause()));
														errorMedicalPatient(siteRequest, null, e);
													}
												});
											} else {
												LOGGER.error(String.format("putimportMedicalPatient failed. ", d.cause()));
												errorMedicalPatient(siteRequest, null, d);
											}
										});
									} catch(Exception ex) {
										LOGGER.error(String.format("putimportMedicalPatient failed. ", ex));
										errorMedicalPatient(siteRequest, null, Future.failedFuture(ex));
									}
								}, resultHandler -> {
								}
							);
						} else {
							LOGGER.error(String.format("putimportMedicalPatient failed. ", c.cause()));
							errorMedicalPatient(siteRequest, eventHandler, c);
						}
					});
				} else {
					LOGGER.error(String.format("putimportMedicalPatient failed. ", b.cause()));
					errorMedicalPatient(siteRequest, eventHandler, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putimportMedicalPatient failed. ", ex));
			errorMedicalPatient(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void listPUTImportMedicalPatient(ApiRequest apiRequest, SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
		try {
			jsonArray.forEach(obj -> {
				JsonObject json = (JsonObject)obj;

				json.put("inheritPk", json.getValue("pk"));

				json.put("created", json.getValue("created"));

				SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForMedicalPatient(siteContext, siteRequest.getOperationRequest(), json);
				siteRequest2.setApiRequest_(apiRequest);
				siteRequest2.setRequestVars(siteRequest.getRequestVars());

				SearchList<MedicalPatient> searchList = new SearchList<MedicalPatient>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(MedicalPatient.class);
				searchList.addFilterQuery("inheritPk_indexed_long:" + json.getString("pk"));
				searchList.initDeepForClass(siteRequest2);

				if(searchList.size() == 1) {
					MedicalPatient o = searchList.getList().stream().findFirst().orElse(null);
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
							patchMedicalPatientFuture(o, true, a -> {
								if(a.succeeded()) {
								} else {
									LOGGER.error(String.format("listPUTImportMedicalPatient failed. ", a.cause()));
									errorMedicalPatient(siteRequest2, eventHandler, a);
								}
							})
						);
					}
				} else {
					futures.add(
						postMedicalPatientFuture(siteRequest2, true, a -> {
							if(a.succeeded()) {
							} else {
								LOGGER.error(String.format("listPUTImportMedicalPatient failed. ", a.cause()));
								errorMedicalPatient(siteRequest2, eventHandler, a);
							}
						})
					);
				}
			});
			CompositeFuture.all(futures).setHandler( a -> {
				if(a.succeeded()) {
					apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
					response200PUTImportMedicalPatient(siteRequest, eventHandler);
				} else {
					LOGGER.error(String.format("listPUTImportMedicalPatient failed. ", a.cause()));
					errorMedicalPatient(apiRequest.getSiteRequest_(), eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("listPUTImportMedicalPatient failed. ", ex));
			errorMedicalPatient(siteRequest, null, Future.failedFuture(ex));
		}
	}

	public void putimportMedicalPatientResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PUTImportMedicalPatient(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("putimportMedicalPatientResponse failed. ", a.cause()));
					errorMedicalPatient(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putimportMedicalPatientResponse failed. ", ex));
			errorMedicalPatient(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTImportMedicalPatient(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PUTImportMedicalPatient failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTMerge //

	@Override
	public void putmergeMedicalPatient(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForMedicalPatient(siteContext, operationRequest, body);
		try {
			LOGGER.info(String.format("putmergeMedicalPatient started. "));
			userMedicalPatient(siteRequest, b -> {
				if(b.succeeded()) {
					putmergeMedicalPatientResponse(siteRequest, c -> {
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
										siteRequest.getVertx().eventBus().publish("websocketMedicalPatient", JsonObject.mapFrom(apiRequest).toString());
										varsMedicalPatient(siteRequest, d -> {
											if(d.succeeded()) {
												listPUTMergeMedicalPatient(apiRequest, siteRequest, e -> {
													if(e.succeeded()) {
														putmergeMedicalPatientResponse(siteRequest, f -> {
															if(e.succeeded()) {
																LOGGER.info(String.format("putmergeMedicalPatient succeeded. "));
																blockingCodeHandler.handle(Future.succeededFuture(e.result()));
															} else {
																LOGGER.error(String.format("putmergeMedicalPatient failed. ", f.cause()));
																errorMedicalPatient(siteRequest, null, f);
															}
														});
													} else {
														LOGGER.error(String.format("putmergeMedicalPatient failed. ", e.cause()));
														errorMedicalPatient(siteRequest, null, e);
													}
												});
											} else {
												LOGGER.error(String.format("putmergeMedicalPatient failed. ", d.cause()));
												errorMedicalPatient(siteRequest, null, d);
											}
										});
									} catch(Exception ex) {
										LOGGER.error(String.format("putmergeMedicalPatient failed. ", ex));
										errorMedicalPatient(siteRequest, null, Future.failedFuture(ex));
									}
								}, resultHandler -> {
								}
							);
						} else {
							LOGGER.error(String.format("putmergeMedicalPatient failed. ", c.cause()));
							errorMedicalPatient(siteRequest, eventHandler, c);
						}
					});
				} else {
					LOGGER.error(String.format("putmergeMedicalPatient failed. ", b.cause()));
					errorMedicalPatient(siteRequest, eventHandler, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putmergeMedicalPatient failed. ", ex));
			errorMedicalPatient(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void listPUTMergeMedicalPatient(ApiRequest apiRequest, SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
		try {
			jsonArray.forEach(obj -> {
				JsonObject json = (JsonObject)obj;

				json.put("inheritPk", json.getValue("pk"));

				SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForMedicalPatient(siteContext, siteRequest.getOperationRequest(), json);
				siteRequest2.setApiRequest_(apiRequest);
				siteRequest2.setRequestVars(siteRequest.getRequestVars());

				SearchList<MedicalPatient> searchList = new SearchList<MedicalPatient>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(MedicalPatient.class);
				searchList.addFilterQuery("pk_indexed_long:" + json.getString("pk"));
				searchList.initDeepForClass(siteRequest2);

				if(searchList.size() == 1) {
					MedicalPatient o = searchList.getList().stream().findFirst().orElse(null);
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
							patchMedicalPatientFuture(o, false, a -> {
								if(a.succeeded()) {
								} else {
									LOGGER.error(String.format("listPUTMergeMedicalPatient failed. ", a.cause()));
									errorMedicalPatient(siteRequest2, eventHandler, a);
								}
							})
						);
					}
				} else {
					futures.add(
						postMedicalPatientFuture(siteRequest2, false, a -> {
							if(a.succeeded()) {
							} else {
								LOGGER.error(String.format("listPUTMergeMedicalPatient failed. ", a.cause()));
								errorMedicalPatient(siteRequest2, eventHandler, a);
							}
						})
					);
				}
			});
			CompositeFuture.all(futures).setHandler( a -> {
				if(a.succeeded()) {
					apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
					response200PUTMergeMedicalPatient(siteRequest, eventHandler);
				} else {
					LOGGER.error(String.format("listPUTMergeMedicalPatient failed. ", a.cause()));
					errorMedicalPatient(apiRequest.getSiteRequest_(), eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("listPUTMergeMedicalPatient failed. ", ex));
			errorMedicalPatient(siteRequest, null, Future.failedFuture(ex));
		}
	}

	public void putmergeMedicalPatientResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PUTMergeMedicalPatient(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("putmergeMedicalPatientResponse failed. ", a.cause()));
					errorMedicalPatient(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putmergeMedicalPatientResponse failed. ", ex));
			errorMedicalPatient(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTMergeMedicalPatient(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PUTMergeMedicalPatient failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTCopy //

	@Override
	public void putcopyMedicalPatient(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForMedicalPatient(siteContext, operationRequest, body);
		try {
			LOGGER.info(String.format("putcopyMedicalPatient started. "));
			userMedicalPatient(siteRequest, b -> {
				if(b.succeeded()) {
					putcopyMedicalPatientResponse(siteRequest, c -> {
						if(c.succeeded()) {
							eventHandler.handle(Future.succeededFuture(c.result()));
							WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
							workerExecutor.executeBlocking(
								blockingCodeHandler -> {
									try {
										aSearchMedicalPatient(siteRequest, false, true, "/api/patient/copy", "PUTCopy", d -> {
											if(d.succeeded()) {
												SearchList<MedicalPatient> listMedicalPatient = d.result();
												ApiRequest apiRequest = new ApiRequest();
												apiRequest.setRows(listMedicalPatient.getRows());
												apiRequest.setNumFound(listMedicalPatient.getQueryResponse().getResults().getNumFound());
												apiRequest.setNumPATCH(0L);
												apiRequest.initDeepApiRequest(siteRequest);
												siteRequest.setApiRequest_(apiRequest);
												siteRequest.getVertx().eventBus().publish("websocketMedicalPatient", JsonObject.mapFrom(apiRequest).toString());
												try {
													listPUTCopyMedicalPatient(apiRequest, listMedicalPatient, e -> {
														if(e.succeeded()) {
															putcopyMedicalPatientResponse(siteRequest, f -> {
																if(f.succeeded()) {
																	LOGGER.info(String.format("putcopyMedicalPatient succeeded. "));
																	blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																} else {
																	LOGGER.error(String.format("putcopyMedicalPatient failed. ", f.cause()));
																	errorMedicalPatient(siteRequest, null, f);
																}
															});
														} else {
															LOGGER.error(String.format("putcopyMedicalPatient failed. ", e.cause()));
															errorMedicalPatient(siteRequest, null, e);
														}
													});
												} catch(Exception ex) {
													LOGGER.error(String.format("putcopyMedicalPatient failed. ", ex));
													errorMedicalPatient(siteRequest, null, Future.failedFuture(ex));
												}
											} else {
												LOGGER.error(String.format("putcopyMedicalPatient failed. ", d.cause()));
												errorMedicalPatient(siteRequest, null, d);
											}
										});
									} catch(Exception ex) {
										LOGGER.error(String.format("putcopyMedicalPatient failed. ", ex));
										errorMedicalPatient(siteRequest, null, Future.failedFuture(ex));
									}
								}, resultHandler -> {
								}
							);
						} else {
							LOGGER.error(String.format("putcopyMedicalPatient failed. ", c.cause()));
							errorMedicalPatient(siteRequest, eventHandler, c);
						}
					});
				} else {
					LOGGER.error(String.format("putcopyMedicalPatient failed. ", b.cause()));
					errorMedicalPatient(siteRequest, eventHandler, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putcopyMedicalPatient failed. ", ex));
			errorMedicalPatient(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void listPUTCopyMedicalPatient(ApiRequest apiRequest, SearchList<MedicalPatient> listMedicalPatient, Handler<AsyncResult<OperationResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listMedicalPatient.getSiteRequest_();
		listMedicalPatient.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForMedicalPatient(siteContext, siteRequest.getOperationRequest(), siteRequest.getJsonObject());
			siteRequest2.setApiRequest_(siteRequest.getApiRequest_());
			o.setSiteRequest_(siteRequest2);
			futures.add(
				putcopyMedicalPatientFuture(siteRequest, JsonObject.mapFrom(o), a -> {
					if(a.succeeded()) {
					} else {
						LOGGER.error(String.format("listPUTCopyMedicalPatient failed. ", a.cause()));
						errorMedicalPatient(siteRequest, eventHandler, a);
					}
				})
			);
		});
		CompositeFuture.all(futures).setHandler( a -> {
			if(a.succeeded()) {
				apiRequest.setNumPATCH(apiRequest.getNumPATCH() + listMedicalPatient.size());
				if(listMedicalPatient.next()) {
					listPUTCopyMedicalPatient(apiRequest, listMedicalPatient, eventHandler);
				} else {
					response200PUTCopyMedicalPatient(siteRequest, eventHandler);
				}
			} else {
				LOGGER.error(String.format("listPUTCopyMedicalPatient failed. ", a.cause()));
				errorMedicalPatient(listMedicalPatient.getSiteRequest_(), eventHandler, a);
			}
		});
	}

	public Future<MedicalPatient> putcopyMedicalPatientFuture(SiteRequestEnUS siteRequest, JsonObject jsonObject, Handler<AsyncResult<MedicalPatient>> eventHandler) {
		Promise<MedicalPatient> promise = Promise.promise();
		try {

			jsonObject.put("saves", Optional.ofNullable(jsonObject.getJsonArray("saves")).orElse(new JsonArray()));
			JsonObject jsonPatch = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonObject("patch")).orElse(new JsonObject());
			jsonPatch.stream().forEach(o -> {
				jsonObject.put(o.getKey(), o.getValue());
				jsonObject.getJsonArray("saves").add(o.getKey());
			});

			sqlConnectionMedicalPatient(siteRequest, a -> {
				if(a.succeeded()) {
					sqlTransactionMedicalPatient(siteRequest, b -> {
						if(b.succeeded()) {
							createMedicalPatient(siteRequest, c -> {
								if(c.succeeded()) {
									MedicalPatient medicalPatient = c.result();
									sqlPUTCopyMedicalPatient(medicalPatient, jsonObject, d -> {
										if(d.succeeded()) {
											defineIndexMedicalPatient(medicalPatient, e -> {
												if(e.succeeded()) {
													ApiRequest apiRequest = siteRequest.getApiRequest_();
													if(apiRequest != null) {
														apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
														if(apiRequest.getNumFound() == 1L) {
															medicalPatient.apiRequestMedicalPatient();
														}
														siteRequest.getVertx().eventBus().publish("websocketMedicalPatient", JsonObject.mapFrom(apiRequest).toString());
													}
													eventHandler.handle(Future.succeededFuture(medicalPatient));
													promise.complete(medicalPatient);
												} else {
													LOGGER.error(String.format("putcopyMedicalPatientFuture failed. ", e.cause()));
													eventHandler.handle(Future.failedFuture(e.cause()));
												}
											});
										} else {
											LOGGER.error(String.format("putcopyMedicalPatientFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOGGER.error(String.format("putcopyMedicalPatientFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOGGER.error(String.format("putcopyMedicalPatientFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOGGER.error(String.format("putcopyMedicalPatientFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("putcopyMedicalPatientFuture failed. ", e));
			errorMedicalPatient(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPUTCopyMedicalPatient(MedicalPatient o, JsonObject jsonObject, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
					case "enrollmentKeys":
						for(Long l : jsonObject.getJsonArray(entityVar).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_addA
										, Tuple.of(pk, "enrollmentKeys", l, "patientKey")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalPatient.enrollmentKeys failed", b.cause())));
								});
							}));
							if(!pks.contains(l)) {
								pks.add(l);
								classes.add("MedicalEnrollment");
							}
						}
						break;
					case "patientFirstName":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "patientFirstName", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalPatient.patientFirstName failed", b.cause())));
							});
						}));
						break;
					case "patientFirstNamePreferred":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "patientFirstNamePreferred", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalPatient.patientFirstNamePreferred failed", b.cause())));
							});
						}));
						break;
					case "familyName":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "familyName", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalPatient.familyName failed", b.cause())));
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
									a.handle(Future.failedFuture(new Exception("value MedicalPatient.patientBirthDate failed", b.cause())));
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
					LOGGER.error(String.format("sqlPUTCopyMedicalPatient failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPUTCopyMedicalPatient failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void putcopyMedicalPatientResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PUTCopyMedicalPatient(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("putcopyMedicalPatientResponse failed. ", a.cause()));
					errorMedicalPatient(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putcopyMedicalPatientResponse failed. ", ex));
			errorMedicalPatient(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTCopyMedicalPatient(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PUTCopyMedicalPatient failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PATCH //

	@Override
	public void patchMedicalPatient(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForMedicalPatient(siteContext, operationRequest, body);
		try {
			LOGGER.info(String.format("patchMedicalPatient started. "));
			userMedicalPatient(siteRequest, b -> {
				if(b.succeeded()) {
					patchMedicalPatientResponse(siteRequest, c -> {
						if(c.succeeded()) {
							eventHandler.handle(Future.succeededFuture(c.result()));
							WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
							workerExecutor.executeBlocking(
								blockingCodeHandler -> {
									try {
										aSearchMedicalPatient(siteRequest, false, true, "/api/patient", "PATCH", d -> {
											if(d.succeeded()) {
												SearchList<MedicalPatient> listMedicalPatient = d.result();
												ApiRequest apiRequest = new ApiRequest();
												apiRequest.setRows(listMedicalPatient.getRows());
												apiRequest.setNumFound(listMedicalPatient.getQueryResponse().getResults().getNumFound());
												apiRequest.setNumPATCH(0L);
												apiRequest.initDeepApiRequest(siteRequest);
												siteRequest.setApiRequest_(apiRequest);
												siteRequest.getVertx().eventBus().publish("websocketMedicalPatient", JsonObject.mapFrom(apiRequest).toString());
												SimpleOrderedMap facets = (SimpleOrderedMap)Optional.ofNullable(listMedicalPatient.getQueryResponse()).map(QueryResponse::getResponse).map(r -> r.get("facets")).orElse(null);
												Date date = null;
												if(facets != null)
													date = (Date)facets.get("max_modified");
												String dt;
												if(date == null)
													dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(ZonedDateTime.now().toInstant(), ZoneId.of("UTC")).minusNanos(1000));
												else
													dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC")));
												listMedicalPatient.addFilterQuery(String.format("modified_indexed_date:[* TO %s]", dt));

												try {
													listPATCHMedicalPatient(apiRequest, listMedicalPatient, dt, e -> {
														if(e.succeeded()) {
															patchMedicalPatientResponse(siteRequest, f -> {
																if(f.succeeded()) {
																	LOGGER.info(String.format("patchMedicalPatient succeeded. "));
																	blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																} else {
																	LOGGER.error(String.format("patchMedicalPatient failed. ", f.cause()));
																	errorMedicalPatient(siteRequest, null, f);
																}
															});
														} else {
															LOGGER.error(String.format("patchMedicalPatient failed. ", e.cause()));
															errorMedicalPatient(siteRequest, null, e);
														}
													});
												} catch(Exception ex) {
													LOGGER.error(String.format("patchMedicalPatient failed. ", ex));
													errorMedicalPatient(siteRequest, null, Future.failedFuture(ex));
												}
											} else {
												LOGGER.error(String.format("patchMedicalPatient failed. ", d.cause()));
												errorMedicalPatient(siteRequest, null, d);
											}
										});
									} catch(Exception ex) {
										LOGGER.error(String.format("patchMedicalPatient failed. ", ex));
										errorMedicalPatient(siteRequest, null, Future.failedFuture(ex));
									}
								}, resultHandler -> {
								}
							);
						} else {
							LOGGER.error(String.format("patchMedicalPatient failed. ", c.cause()));
							errorMedicalPatient(siteRequest, eventHandler, c);
						}
					});
				} else {
					LOGGER.error(String.format("patchMedicalPatient failed. ", b.cause()));
					errorMedicalPatient(siteRequest, eventHandler, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("patchMedicalPatient failed. ", ex));
			errorMedicalPatient(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void listPATCHMedicalPatient(ApiRequest apiRequest, SearchList<MedicalPatient> listMedicalPatient, String dt, Handler<AsyncResult<OperationResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listMedicalPatient.getSiteRequest_();
		listMedicalPatient.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForMedicalPatient(siteContext, siteRequest.getOperationRequest(), siteRequest.getJsonObject());
			siteRequest2.setApiRequest_(siteRequest.getApiRequest_());
			o.setSiteRequest_(siteRequest2);
			futures.add(
				patchMedicalPatientFuture(o, false, a -> {
					if(a.succeeded()) {
					} else {
						errorMedicalPatient(siteRequest2, eventHandler, a);
					}
				})
			);
		});
		CompositeFuture.all(futures).setHandler( a -> {
			if(a.succeeded()) {
				if(listMedicalPatient.next(dt)) {
					listPATCHMedicalPatient(apiRequest, listMedicalPatient, dt, eventHandler);
				} else {
					response200PATCHMedicalPatient(siteRequest, eventHandler);
				}
			} else {
				LOGGER.error(String.format("listPATCHMedicalPatient failed. ", a.cause()));
				errorMedicalPatient(listMedicalPatient.getSiteRequest_(), eventHandler, a);
			}
		});
	}

	public Future<MedicalPatient> patchMedicalPatientFuture(MedicalPatient o, Boolean inheritPk, Handler<AsyncResult<MedicalPatient>> eventHandler) {
		Promise<MedicalPatient> promise = Promise.promise();
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			if(apiRequest != null && apiRequest.getNumFound() == 1L) {
				apiRequest.setOriginal(o);
				apiRequest.setPk(o.getPk());
			}
			sqlConnectionMedicalPatient(siteRequest, a -> {
				if(a.succeeded()) {
					sqlTransactionMedicalPatient(siteRequest, b -> {
						if(b.succeeded()) {
							sqlPATCHMedicalPatient(o, inheritPk, c -> {
								if(c.succeeded()) {
									MedicalPatient medicalPatient = c.result();
									defineIndexMedicalPatient(medicalPatient, d -> {
										if(d.succeeded()) {
											if(apiRequest != null) {
												apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
												if(apiRequest.getNumFound() == 1L) {
													medicalPatient.apiRequestMedicalPatient();
												}
												siteRequest.getVertx().eventBus().publish("websocketMedicalPatient", JsonObject.mapFrom(apiRequest).toString());
											}
											eventHandler.handle(Future.succeededFuture(medicalPatient));
											promise.complete(medicalPatient);
										} else {
											LOGGER.error(String.format("patchMedicalPatientFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOGGER.error(String.format("patchMedicalPatientFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOGGER.error(String.format("patchMedicalPatientFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOGGER.error(String.format("patchMedicalPatientFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("patchMedicalPatientFuture failed. ", e));
			errorMedicalPatient(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPATCHMedicalPatient(MedicalPatient o, Boolean inheritPk, Handler<AsyncResult<MedicalPatient>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Transaction tx = siteRequest.getTx();
			Long pk = o.getPk();
			JsonObject jsonObject = siteRequest.getJsonObject();
			Set<String> methodNames = jsonObject.fieldNames();
			MedicalPatient o2 = new MedicalPatient();
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
					case "addEnrollmentKeys":
						{
							Long l = Long.parseLong(jsonObject.getString(methodName));
							if(l != null) {
								SearchList<MedicalEnrollment> searchList = new SearchList<MedicalEnrollment>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(MedicalEnrollment.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null && !o.getEnrollmentKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_addA
												, Tuple.of(pk, "enrollmentKeys", l2, "patientKey")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value MedicalPatient.enrollmentKeys failed", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("MedicalEnrollment");
									}
								}
							}
						}
						break;
					case "addAllEnrollmentKeys":
						JsonArray addAllEnrollmentKeysValues = jsonObject.getJsonArray(methodName);
						if(addAllEnrollmentKeysValues != null) {
							for(Integer i = 0; i <  addAllEnrollmentKeysValues.size(); i++) {
								Long l = Long.parseLong(addAllEnrollmentKeysValues.getString(i));
								if(l != null) {
									SearchList<MedicalEnrollment> searchList = new SearchList<MedicalEnrollment>();
									searchList.setQuery("*:*");
									searchList.setStore(true);
									searchList.setC(MedicalEnrollment.class);
									searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
									searchList.initDeepSearchList(siteRequest);
									Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
									if(l2 != null && !o.getEnrollmentKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_addA
												, Tuple.of(pk, "enrollmentKeys", l2, "patientKey")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value MedicalPatient.enrollmentKeys failed", b.cause())));
										});
									}));
										if(!pks.contains(l2)) {
											pks.add(l2);
											classes.add("MedicalEnrollment");
										}
									}
								}
							}
						}
						break;
					case "setEnrollmentKeys":
						JsonArray setEnrollmentKeysValues = jsonObject.getJsonArray(methodName);
						if(setEnrollmentKeysValues != null) {
							for(Integer i = 0; i <  setEnrollmentKeysValues.size(); i++) {
								Long l = Long.parseLong(setEnrollmentKeysValues.getString(i));
								if(l != null) {
									SearchList<MedicalEnrollment> searchList = new SearchList<MedicalEnrollment>();
									searchList.setQuery("*:*");
									searchList.setStore(true);
									searchList.setC(MedicalEnrollment.class);
									searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
									searchList.initDeepSearchList(siteRequest);
									Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
									if(l2 != null && !o.getEnrollmentKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_addA
												, Tuple.of(pk, "enrollmentKeys", l2, "patientKey")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value MedicalPatient.enrollmentKeys failed", b.cause())));
										});
									}));
										if(!pks.contains(l2)) {
											pks.add(l2);
											classes.add("MedicalEnrollment");
										}
									}
								}
							}
						}
						if(o.getEnrollmentKeys() != null) {
							for(Long l :  o.getEnrollmentKeys()) {
								if(l != null && (setEnrollmentKeysValues == null || !setEnrollmentKeysValues.contains(l))) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_removeA
												, Tuple.of(pk, "enrollmentKeys", l, "patientKey")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value MedicalPatient.enrollmentKeys failed", b.cause())));
										});
									}));
								}
							}
						}
						break;
					case "removeEnrollmentKeys":
						{
							Long l = Long.parseLong(jsonObject.getString(methodName));
							if(l != null) {
								SearchList<MedicalEnrollment> searchList = new SearchList<MedicalEnrollment>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(MedicalEnrollment.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null && o.getEnrollmentKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_removeA
												, Tuple.of(pk, "enrollmentKeys", l2, "patientKey")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value MedicalPatient.enrollmentKeys failed", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("MedicalEnrollment");
									}
								}
							}
						}
						break;
					case "setPatientFirstName":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "patientFirstName")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalPatient.patientFirstName failed", b.cause())));
								});
							}));
						} else {
							o2.setPatientFirstName(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "patientFirstName", o2.jsonPatientFirstName())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalPatient.patientFirstName failed", b.cause())));
								});
							}));
						}
						break;
					case "setPatientFirstNamePreferred":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "patientFirstNamePreferred")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalPatient.patientFirstNamePreferred failed", b.cause())));
								});
							}));
						} else {
							o2.setPatientFirstNamePreferred(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "patientFirstNamePreferred", o2.jsonPatientFirstNamePreferred())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalPatient.patientFirstNamePreferred failed", b.cause())));
								});
							}));
						}
						break;
					case "setFamilyName":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "familyName")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalPatient.familyName failed", b.cause())));
								});
							}));
						} else {
							o2.setFamilyName(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "familyName", o2.jsonFamilyName())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalPatient.familyName failed", b.cause())));
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
										a.handle(Future.failedFuture(new Exception("value MedicalPatient.patientBirthDate failed", b.cause())));
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
										a.handle(Future.failedFuture(new Exception("value MedicalPatient.patientBirthDate failed", b.cause())));
								});
							}));
						}
						break;
				}
			}
			CompositeFuture.all(futures).setHandler( a -> {
				if(a.succeeded()) {
					MedicalPatient o3 = new MedicalPatient();
					o3.setSiteRequest_(o.getSiteRequest_());
					o3.setPk(pk);
					eventHandler.handle(Future.succeededFuture(o3));
				} else {
					LOGGER.error(String.format("sqlPATCHMedicalPatient failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPATCHMedicalPatient failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void patchMedicalPatientResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PATCHMedicalPatient(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("patchMedicalPatientResponse failed. ", a.cause()));
					errorMedicalPatient(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("patchMedicalPatientResponse failed. ", ex));
			errorMedicalPatient(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PATCHMedicalPatient(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PATCHMedicalPatient failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// GET //

	@Override
	public void getMedicalPatient(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForMedicalPatient(siteContext, operationRequest);
		try {
			userMedicalPatient(siteRequest, b -> {
				if(b.succeeded()) {
					aSearchMedicalPatient(siteRequest, false, true, "/api/patient/{id}", "GET", c -> {
						if(c.succeeded()) {
							SearchList<MedicalPatient> listMedicalPatient = c.result();
							getMedicalPatientResponse(listMedicalPatient, d -> {
								if(d.succeeded()) {
									eventHandler.handle(Future.succeededFuture(d.result()));
									LOGGER.info(String.format("getMedicalPatient succeeded. "));
								} else {
									LOGGER.error(String.format("getMedicalPatient failed. ", d.cause()));
									errorMedicalPatient(siteRequest, eventHandler, d);
								}
							});
						} else {
							LOGGER.error(String.format("getMedicalPatient failed. ", c.cause()));
							errorMedicalPatient(siteRequest, eventHandler, c);
						}
					});
				} else {
					LOGGER.error(String.format("getMedicalPatient failed. ", b.cause()));
					errorMedicalPatient(siteRequest, eventHandler, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("getMedicalPatient failed. ", ex));
			errorMedicalPatient(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void getMedicalPatientResponse(SearchList<MedicalPatient> listMedicalPatient, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listMedicalPatient.getSiteRequest_();
		try {
			response200GETMedicalPatient(listMedicalPatient, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("getMedicalPatientResponse failed. ", a.cause()));
					errorMedicalPatient(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("getMedicalPatientResponse failed. ", ex));
			errorMedicalPatient(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200GETMedicalPatient(SearchList<MedicalPatient> listMedicalPatient, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listMedicalPatient.getSiteRequest_();
			SolrDocumentList solrDocuments = listMedicalPatient.getSolrDocumentList();

			JsonObject json = JsonObject.mapFrom(listMedicalPatient.getList().stream().findFirst().orElse(null));
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200GETMedicalPatient failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// Search //

	@Override
	public void searchMedicalPatient(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForMedicalPatient(siteContext, operationRequest);
		try {
			userMedicalPatient(siteRequest, b -> {
				if(b.succeeded()) {
					aSearchMedicalPatient(siteRequest, false, true, "/api/patient", "Search", c -> {
						if(c.succeeded()) {
							SearchList<MedicalPatient> listMedicalPatient = c.result();
							searchMedicalPatientResponse(listMedicalPatient, d -> {
								if(d.succeeded()) {
									eventHandler.handle(Future.succeededFuture(d.result()));
									LOGGER.info(String.format("searchMedicalPatient succeeded. "));
								} else {
									LOGGER.error(String.format("searchMedicalPatient failed. ", d.cause()));
									errorMedicalPatient(siteRequest, eventHandler, d);
								}
							});
						} else {
							LOGGER.error(String.format("searchMedicalPatient failed. ", c.cause()));
							errorMedicalPatient(siteRequest, eventHandler, c);
						}
					});
				} else {
					LOGGER.error(String.format("searchMedicalPatient failed. ", b.cause()));
					errorMedicalPatient(siteRequest, eventHandler, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("searchMedicalPatient failed. ", ex));
			errorMedicalPatient(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void searchMedicalPatientResponse(SearchList<MedicalPatient> listMedicalPatient, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listMedicalPatient.getSiteRequest_();
		try {
			response200SearchMedicalPatient(listMedicalPatient, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("searchMedicalPatientResponse failed. ", a.cause()));
					errorMedicalPatient(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("searchMedicalPatientResponse failed. ", ex));
			errorMedicalPatient(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200SearchMedicalPatient(SearchList<MedicalPatient> listMedicalPatient, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listMedicalPatient.getSiteRequest_();
			QueryResponse responseSearch = listMedicalPatient.getQueryResponse();
			SolrDocumentList solrDocuments = listMedicalPatient.getSolrDocumentList();
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
			listMedicalPatient.getList().stream().forEach(o -> {
				JsonObject json2 = JsonObject.mapFrom(o);
				List<String> fls = listMedicalPatient.getFields();
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
			LOGGER.error(String.format("response200SearchMedicalPatient failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// AdminSearch //

	@Override
	public void adminsearchMedicalPatient(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForMedicalPatient(siteContext, operationRequest);
		try {
			userMedicalPatient(siteRequest, b -> {
				if(b.succeeded()) {
					aSearchMedicalPatient(siteRequest, false, true, "/api/admin/patient", "AdminSearch", c -> {
						if(c.succeeded()) {
							SearchList<MedicalPatient> listMedicalPatient = c.result();
							adminsearchMedicalPatientResponse(listMedicalPatient, d -> {
								if(d.succeeded()) {
									eventHandler.handle(Future.succeededFuture(d.result()));
									LOGGER.info(String.format("adminsearchMedicalPatient succeeded. "));
								} else {
									LOGGER.error(String.format("adminsearchMedicalPatient failed. ", d.cause()));
									errorMedicalPatient(siteRequest, eventHandler, d);
								}
							});
						} else {
							LOGGER.error(String.format("adminsearchMedicalPatient failed. ", c.cause()));
							errorMedicalPatient(siteRequest, eventHandler, c);
						}
					});
				} else {
					LOGGER.error(String.format("adminsearchMedicalPatient failed. ", b.cause()));
					errorMedicalPatient(siteRequest, eventHandler, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("adminsearchMedicalPatient failed. ", ex));
			errorMedicalPatient(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void adminsearchMedicalPatientResponse(SearchList<MedicalPatient> listMedicalPatient, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listMedicalPatient.getSiteRequest_();
		try {
			response200AdminSearchMedicalPatient(listMedicalPatient, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("adminsearchMedicalPatientResponse failed. ", a.cause()));
					errorMedicalPatient(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("adminsearchMedicalPatientResponse failed. ", ex));
			errorMedicalPatient(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200AdminSearchMedicalPatient(SearchList<MedicalPatient> listMedicalPatient, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listMedicalPatient.getSiteRequest_();
			QueryResponse responseSearch = listMedicalPatient.getQueryResponse();
			SolrDocumentList solrDocuments = listMedicalPatient.getSolrDocumentList();
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
			listMedicalPatient.getList().stream().forEach(o -> {
				JsonObject json2 = JsonObject.mapFrom(o);
				List<String> fls = listMedicalPatient.getFields();
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
			LOGGER.error(String.format("response200AdminSearchMedicalPatient failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// SearchPage //

	@Override
	public void searchpageMedicalPatientId(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		searchpageMedicalPatient(operationRequest, eventHandler);
	}

	@Override
	public void searchpageMedicalPatient(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForMedicalPatient(siteContext, operationRequest);
		try {
			userMedicalPatient(siteRequest, b -> {
				if(b.succeeded()) {
					aSearchMedicalPatient(siteRequest, false, true, "/patient", "SearchPage", c -> {
						if(c.succeeded()) {
							SearchList<MedicalPatient> listMedicalPatient = c.result();
							searchpageMedicalPatientResponse(listMedicalPatient, d -> {
								if(d.succeeded()) {
									eventHandler.handle(Future.succeededFuture(d.result()));
									LOGGER.info(String.format("searchpageMedicalPatient succeeded. "));
								} else {
									LOGGER.error(String.format("searchpageMedicalPatient failed. ", d.cause()));
									errorMedicalPatient(siteRequest, eventHandler, d);
								}
							});
						} else {
							LOGGER.error(String.format("searchpageMedicalPatient failed. ", c.cause()));
							errorMedicalPatient(siteRequest, eventHandler, c);
						}
					});
				} else {
					LOGGER.error(String.format("searchpageMedicalPatient failed. ", b.cause()));
					errorMedicalPatient(siteRequest, eventHandler, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("searchpageMedicalPatient failed. ", ex));
			errorMedicalPatient(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void searchpageMedicalPatientResponse(SearchList<MedicalPatient> listMedicalPatient, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listMedicalPatient.getSiteRequest_();
		try {
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(siteRequest, buffer);
			siteRequest.setW(w);
			response200SearchPageMedicalPatient(listMedicalPatient, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("searchpageMedicalPatientResponse failed. ", a.cause()));
					errorMedicalPatient(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("searchpageMedicalPatientResponse failed. ", ex));
			errorMedicalPatient(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200SearchPageMedicalPatient(SearchList<MedicalPatient> listMedicalPatient, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listMedicalPatient.getSiteRequest_();
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(listMedicalPatient.getSiteRequest_(), buffer);
			PatientPage page = new PatientPage();
			SolrDocument pageSolrDocument = new SolrDocument();
			CaseInsensitiveHeaders requestHeaders = new CaseInsensitiveHeaders();
			siteRequest.setRequestHeaders(requestHeaders);

			pageSolrDocument.setField("pageUri_frFR_stored_string", "/patient");
			page.setPageSolrDocument(pageSolrDocument);
			page.setW(w);
			if(listMedicalPatient.size() == 1)
				siteRequest.setRequestPk(listMedicalPatient.get(0).getPk());
			siteRequest.setW(w);
			page.setListMedicalPatient(listMedicalPatient);
			page.setSiteRequest_(siteRequest);
			page.initDeepPatientPage(siteRequest);
			page.html();
			eventHandler.handle(Future.succeededFuture(new OperationResponse(200, "OK", buffer, requestHeaders)));
		} catch(Exception e) {
			LOGGER.error(String.format("response200SearchPageMedicalPatient failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// General //

	public Future<MedicalPatient> defineIndexMedicalPatient(MedicalPatient medicalPatient, Handler<AsyncResult<MedicalPatient>> eventHandler) {
		Promise<MedicalPatient> promise = Promise.promise();
		SiteRequestEnUS siteRequest = medicalPatient.getSiteRequest_();
		defineMedicalPatient(medicalPatient, c -> {
			if(c.succeeded()) {
				attributeMedicalPatient(medicalPatient, d -> {
					if(d.succeeded()) {
						indexMedicalPatient(medicalPatient, e -> {
							if(e.succeeded()) {
								sqlCommitMedicalPatient(siteRequest, f -> {
									if(f.succeeded()) {
										sqlCloseMedicalPatient(siteRequest, g -> {
											if(g.succeeded()) {
												refreshMedicalPatient(medicalPatient, h -> {
													if(h.succeeded()) {
														eventHandler.handle(Future.succeededFuture(medicalPatient));
														promise.complete(medicalPatient);
													} else {
														LOGGER.error(String.format("refreshMedicalPatient failed. ", h.cause()));
														errorMedicalPatient(siteRequest, null, h);
													}
												});
											} else {
												LOGGER.error(String.format("defineIndexMedicalPatient failed. ", g.cause()));
												errorMedicalPatient(siteRequest, null, g);
											}
										});
									} else {
										LOGGER.error(String.format("defineIndexMedicalPatient failed. ", f.cause()));
										errorMedicalPatient(siteRequest, null, f);
									}
								});
							} else {
								LOGGER.error(String.format("defineIndexMedicalPatient failed. ", e.cause()));
								errorMedicalPatient(siteRequest, null, e);
							}
						});
					} else {
						LOGGER.error(String.format("defineIndexMedicalPatient failed. ", d.cause()));
						errorMedicalPatient(siteRequest, null, d);
					}
				});
			} else {
				LOGGER.error(String.format("defineIndexMedicalPatient failed. ", c.cause()));
				errorMedicalPatient(siteRequest, null, c);
			}
		});
		return promise.future();
	}

	public void createMedicalPatient(SiteRequestEnUS siteRequest, Handler<AsyncResult<MedicalPatient>> eventHandler) {
		try {
			Transaction tx = siteRequest.getTx();
			String userId = siteRequest.getUserId();
			ZonedDateTime created = Optional.ofNullable(siteRequest.getJsonObject()).map(j -> j.getString("created")).map(s -> ZonedDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of(siteRequest.getSiteConfig_().getSiteZone())))).orElse(ZonedDateTime.now(ZoneId.of(siteRequest.getSiteConfig_().getSiteZone())));

			tx.preparedQuery(
					SiteContextEnUS.SQL_create
					, Tuple.of(MedicalPatient.class.getCanonicalName(), userId, created.toOffsetDateTime())
					, Collectors.toList()
					, createAsync
			-> {
				if(createAsync.succeeded()) {
					Row createLine = createAsync.result().value().stream().findFirst().orElseGet(() -> null);
					Long pk = createLine.getLong(0);
					MedicalPatient o = new MedicalPatient();
					o.setPk(pk);
					o.setSiteRequest_(siteRequest);
					eventHandler.handle(Future.succeededFuture(o));
				} else {
					LOGGER.error(String.format("createMedicalPatient failed. ", createAsync.cause()));
					eventHandler.handle(Future.failedFuture(createAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("createMedicalPatient failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void errorMedicalPatient(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler, AsyncResult<?> resultAsync) {
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
		sqlRollbackMedicalPatient(siteRequest, a -> {
			if(a.succeeded()) {
				LOGGER.info(String.format("sql rollback. "));
				sqlCloseMedicalPatient(siteRequest, b -> {
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

	public void sqlConnectionMedicalPatient(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
						LOGGER.error(String.format("sqlConnectionMedicalPatient failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlMedicalPatient failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlTransactionMedicalPatient(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
			LOGGER.error(String.format("sqlTransactionMedicalPatient failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlCommitMedicalPatient(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
						LOGGER.error(String.format("sqlCommitMedicalPatient failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlMedicalPatient failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlRollbackMedicalPatient(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
						LOGGER.error(String.format("sqlRollbackMedicalPatient failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlMedicalPatient failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlCloseMedicalPatient(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
			LOGGER.error(String.format("sqlCloseMedicalPatient failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public SiteRequestEnUS generateSiteRequestEnUSForMedicalPatient(SiteContextEnUS siteContext, OperationRequest operationRequest) {
		return generateSiteRequestEnUSForMedicalPatient(siteContext, operationRequest, null);
	}

	public SiteRequestEnUS generateSiteRequestEnUSForMedicalPatient(SiteContextEnUS siteContext, OperationRequest operationRequest, JsonObject body) {
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

	public void userMedicalPatient(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			String userId = siteRequest.getUserId();
			if(userId == null) {
				eventHandler.handle(Future.succeededFuture());
			} else {
				sqlConnectionMedicalPatient(siteRequest, a -> {
					if(a.succeeded()) {
						sqlTransactionMedicalPatient(siteRequest, b -> {
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
												userMedicalPatientDefine(siteRequest, jsonObject, false);

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
																		errorMedicalPatient(siteRequest, eventHandler, e);
																	}
																});
															} else {
																errorMedicalPatient(siteRequest, eventHandler, d);
															}
														});
													} else {
														errorMedicalPatient(siteRequest, eventHandler, c);
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
												Boolean define = userMedicalPatientDefine(siteRequest, jsonObject, true);
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
																	errorMedicalPatient(siteRequest, eventHandler, e);
																}
															});
														} else {
															errorMedicalPatient(siteRequest, eventHandler, d);
														}
													});
												} else {
													siteRequest.setSiteUser(siteUser1);
													siteRequest.setUserName(siteUser1.getUserName());
													siteRequest.setUserFirstName(siteUser1.getUserFirstName());
													siteRequest.setUserLastName(siteUser1.getUserLastName());
													siteRequest.setUserId(siteUser1.getUserId());
													siteRequest.setUserKey(siteUser1.getPk());
													sqlRollbackMedicalPatient(siteRequest, c -> {
														if(c.succeeded()) {
															eventHandler.handle(Future.succeededFuture());
														} else {
															eventHandler.handle(Future.failedFuture(c.cause()));
															errorMedicalPatient(siteRequest, eventHandler, c);
														}
													});
												}
											}
										} catch(Exception e) {
											LOGGER.error(String.format("userMedicalPatient failed. ", e));
											eventHandler.handle(Future.failedFuture(e));
										}
									} else {
										LOGGER.error(String.format("userMedicalPatient failed. ", selectCAsync.cause()));
										eventHandler.handle(Future.failedFuture(selectCAsync.cause()));
									}
								});
							} else {
								LOGGER.error(String.format("userMedicalPatient failed. ", b.cause()));
								eventHandler.handle(Future.failedFuture(b.cause()));
							}
						});
					} else {
						LOGGER.error(String.format("userMedicalPatient failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("userMedicalPatient failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public Boolean userMedicalPatientDefine(SiteRequestEnUS siteRequest, JsonObject jsonObject, Boolean patch) {
		if(patch) {
			return jsonObject.getString("setCustomerProfileId") == null;
		} else {
			return jsonObject.getString("customerProfileId") == null;
		}
	}

	public void aSearchMedicalPatientQ(String uri, String apiMethod, SearchList<MedicalPatient> searchList, String entityVar, String valueIndexed, String varIndexed) {
		searchList.setQuery(varIndexed + ":" + ("*".equals(valueIndexed) ? valueIndexed : ClientUtils.escapeQueryChars(valueIndexed)));
		if(!"*".equals(entityVar)) {
			searchList.setHighlight(true);
			searchList.setHighlightSnippets(3);
			searchList.addHighlightField(varIndexed);
			searchList.setParam("hl.encoder", "html");
		}
	}

	public void aSearchMedicalPatientFq(String uri, String apiMethod, SearchList<MedicalPatient> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		searchList.addFilterQuery(varIndexed + ":" + ClientUtils.escapeQueryChars(valueIndexed));
	}

	public void aSearchMedicalPatientSort(String uri, String apiMethod, SearchList<MedicalPatient> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		searchList.addSort(varIndexed, ORDER.valueOf(valueIndexed));
	}

	public void aSearchMedicalPatientRows(String uri, String apiMethod, SearchList<MedicalPatient> searchList, Integer valueRows) {
			searchList.setRows(apiMethod != null && apiMethod.contains("Search") ? valueRows : 10);
	}

	public void aSearchMedicalPatientStart(String uri, String apiMethod, SearchList<MedicalPatient> searchList, Integer valueStart) {
		searchList.setStart(valueStart);
	}

	public void aSearchMedicalPatientVar(String uri, String apiMethod, SearchList<MedicalPatient> searchList, String var, String value) {
		searchList.getSiteRequest_().getRequestVars().put(var, value);
	}

	public void aSearchMedicalPatientUri(String uri, String apiMethod, SearchList<MedicalPatient> searchList) {
	}

	public void varsMedicalPatient(SiteRequestEnUS siteRequest, Handler<AsyncResult<SearchList<OperationResponse>>> eventHandler) {
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
					LOGGER.error(String.format("aSearchMedicalPatient failed. ", e));
					eventHandler.handle(Future.failedFuture(e));
				}
			});
			eventHandler.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOGGER.error(String.format("aSearchMedicalPatient failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void aSearchMedicalPatient(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, String uri, String apiMethod, Handler<AsyncResult<SearchList<MedicalPatient>>> eventHandler) {
		try {
			OperationRequest operationRequest = siteRequest.getOperationRequest();
			String entityListStr = siteRequest.getOperationRequest().getParams().getJsonObject("query").getString("fl");
			String[] entityList = entityListStr == null ? null : entityListStr.split(",\\s*");
			SearchList<MedicalPatient> searchList = new SearchList<MedicalPatient>();
			searchList.setPopulate(populate);
			searchList.setStore(store);
			searchList.setQuery("*:*");
			searchList.setC(MedicalPatient.class);
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
								varIndexed = "*".equals(entityVar) ? entityVar : MedicalPatient.varSearchMedicalPatient(entityVar);
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								valueIndexed = StringUtils.isEmpty(valueIndexed) ? "*" : valueIndexed;
								aSearchMedicalPatientQ(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "fq":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								varIndexed = MedicalPatient.varIndexedMedicalPatient(entityVar);
								aSearchMedicalPatientFq(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "sort":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, " "));
								valueIndexed = StringUtils.trim(StringUtils.substringAfter((String)paramObject, " "));
								varIndexed = MedicalPatient.varIndexedMedicalPatient(entityVar);
								aSearchMedicalPatientSort(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "start":
								valueStart = (Integer)paramObject;
								aSearchMedicalPatientStart(uri, apiMethod, searchList, valueStart);
								break;
							case "rows":
								valueRows = (Integer)paramObject;
								aSearchMedicalPatientRows(uri, apiMethod, searchList, valueRows);
								break;
							case "var":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								aSearchMedicalPatientVar(uri, apiMethod, searchList, entityVar, valueIndexed);
								break;
						}
					}
					aSearchMedicalPatientUri(uri, apiMethod, searchList);
				} catch(Exception e) {
					LOGGER.error(String.format("aSearchMedicalPatient failed. ", e));
					eventHandler.handle(Future.failedFuture(e));
				}
			});
			if(searchList.getSorts().size() == 0) {
				searchList.addSort("created_indexed_date", ORDER.desc);
			}
			searchList.initDeepForClass(siteRequest);
			eventHandler.handle(Future.succeededFuture(searchList));
		} catch(Exception e) {
			LOGGER.error(String.format("aSearchMedicalPatient failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void defineMedicalPatient(MedicalPatient o, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
						LOGGER.error(String.format("defineMedicalPatient failed. ", e));
						eventHandler.handle(Future.failedFuture(e));
					}
				} else {
					LOGGER.error(String.format("defineMedicalPatient failed. ", defineAsync.cause()));
					eventHandler.handle(Future.failedFuture(defineAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("defineMedicalPatient failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void attributeMedicalPatient(MedicalPatient o, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
						LOGGER.error(String.format("attributeMedicalPatient failed. ", attributeAsync.cause()));
						eventHandler.handle(Future.failedFuture(attributeAsync.cause()));
					}
				} catch(Exception e) {
					LOGGER.error(String.format("attributeMedicalPatient failed. ", e));
					eventHandler.handle(Future.failedFuture(e));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("attributeMedicalPatient failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void indexMedicalPatient(MedicalPatient o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			o.initDeepForClass(siteRequest);
			o.indexForClass();
			eventHandler.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOGGER.error(String.format("indexMedicalPatient failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void refreshMedicalPatient(MedicalPatient o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Boolean refresh = !"false".equals(siteRequest.getRequestVars().get("refresh"));
			if(refresh && BooleanUtils.isFalse(Optional.ofNullable(siteRequest.getApiRequest_()).map(ApiRequest::getEmpty).orElse(true))) {
				SearchList<MedicalPatient> searchList = new SearchList<MedicalPatient>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(MedicalPatient.class);
				searchList.addFilterQuery("modified_indexed_date:[" + DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(siteRequest.getApiRequest_().getCreated().toInstant(), ZoneId.of("UTC"))) + " TO *]");
				searchList.add("json.facet", "{enrollmentKeys:{terms:{field:enrollmentKeys_indexed_longs, limit:1000}}}");
				searchList.setRows(1000);
				searchList.initDeepSearchList(siteRequest);
				List<Future> futures = new ArrayList<>();

				for(int i=0; i < pks.size(); i++) {
					Long pk2 = pks.get(i);
					String classSimpleName2 = classes.get(i);

					if("MedicalEnrollment".equals(classSimpleName2) && pk2 != null) {
						SearchList<MedicalEnrollment> searchList2 = new SearchList<MedicalEnrollment>();
						searchList2.setStore(true);
						searchList2.setQuery("*:*");
						searchList2.setC(MedicalEnrollment.class);
						searchList2.addFilterQuery("pk_indexed_long:" + pk2);
						searchList2.setRows(1);
						searchList2.initDeepSearchList(siteRequest);
						MedicalEnrollment o2 = searchList2.getList().stream().findFirst().orElse(null);

						if(o2 != null) {
							MedicalEnrollmentEnUSGenApiServiceImpl service = new MedicalEnrollmentEnUSGenApiServiceImpl(siteRequest.getSiteContext_());
							SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForMedicalPatient(siteContext, siteRequest.getOperationRequest(), new JsonObject());
							ApiRequest apiRequest2 = new ApiRequest();
							apiRequest2.setRows(1);
							apiRequest2.setNumFound(1l);
							apiRequest2.setNumPATCH(0L);
							apiRequest2.initDeepApiRequest(siteRequest2);
							siteRequest2.setApiRequest_(apiRequest2);
							siteRequest2.getVertx().eventBus().publish("websocketMedicalEnrollment", JsonObject.mapFrom(apiRequest2).toString());

							o2.setPk(pk2);
							o2.setSiteRequest_(siteRequest2);
							futures.add(
								service.patchMedicalEnrollmentFuture(o2, false, a -> {
									if(a.succeeded()) {
									} else {
										LOGGER.info(String.format("MedicalEnrollment %s failed. ", pk2));
										eventHandler.handle(Future.failedFuture(a.cause()));
									}
								})
							);
						}
					}
				}

				CompositeFuture.all(futures).setHandler(a -> {
					if(a.succeeded()) {
						MedicalPatientEnUSApiServiceImpl service = new MedicalPatientEnUSApiServiceImpl(siteRequest.getSiteContext_());
						List<Future> futures2 = new ArrayList<>();
						for(MedicalPatient o2 : searchList.getList()) {
							SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForMedicalPatient(siteContext, siteRequest.getOperationRequest(), new JsonObject());
							o2.setSiteRequest_(siteRequest2);
							futures2.add(
								service.patchMedicalPatientFuture(o2, false, b -> {
									if(b.succeeded()) {
									} else {
										LOGGER.info(String.format("MedicalPatient %s failed. ", o2.getPk()));
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
								errorMedicalPatient(siteRequest, eventHandler, b);
							}
						});
					} else {
						LOGGER.error("Refresh relations failed. ", a.cause());
						errorMedicalPatient(siteRequest, eventHandler, a);
					}
				});
			} else {
				eventHandler.handle(Future.succeededFuture());
			}
		} catch(Exception e) {
			LOGGER.error(String.format("refreshMedicalPatient failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}
}
