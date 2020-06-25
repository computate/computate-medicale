package org.computate.medicale.enUS.clinic;

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
 * CanonicalName.frFR: org.computate.medicale.frFR.clinique.CliniqueMedicaleFrFRGenApiServiceImpl
 **/
public class MedicalClinicEnUSGenApiServiceImpl implements MedicalClinicEnUSGenApiService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(MedicalClinicEnUSGenApiServiceImpl.class);

	protected static final String SERVICE_ADDRESS = "MedicalClinicEnUSApiServiceImpl";

	protected SiteContextEnUS siteContext;

	public MedicalClinicEnUSGenApiServiceImpl(SiteContextEnUS siteContext) {
		this.siteContext = siteContext;
	}

	// POST //

	@Override
	public void postMedicalClinic(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForMedicalClinic(siteContext, operationRequest, body);
		try {
			LOGGER.info(String.format("postMedicalClinic started. "));

			List<String> roles = Arrays.asList("SiteAdmin");
			if(
					!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles)
					&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles)
					) {
				eventHandler.handle(Future.succeededFuture(
					new OperationResponse(401, "UNAUTHORIZED", 
						Buffer.buffer().appendString(
							new JsonObject()
								.put("errorCode", "401")
								.put("errorMessage", "roles required: " + String.join(", ", roles))
								.encodePrettily()
							), new CaseInsensitiveHeaders()
					)
				));
			}

			userMedicalClinic(siteRequest, b -> {
				if(b.succeeded()) {
					ApiRequest apiRequest = new ApiRequest();
					apiRequest.setRows(1);
					apiRequest.setNumFound(1L);
					apiRequest.setNumPATCH(0L);
					apiRequest.initDeepApiRequest(siteRequest);
					siteRequest.setApiRequest_(apiRequest);
					siteRequest.getVertx().eventBus().publish("websocketMedicalClinic", JsonObject.mapFrom(apiRequest).toString());
					postMedicalClinicFuture(siteRequest, false, c -> {
						if(c.succeeded()) {
							MedicalClinic medicalClinic = c.result();
							apiRequest.setPk(medicalClinic.getPk());
							postMedicalClinicResponse(medicalClinic, d -> {
									if(d.succeeded()) {
									eventHandler.handle(Future.succeededFuture(d.result()));
									LOGGER.info(String.format("postMedicalClinic succeeded. "));
								} else {
									LOGGER.error(String.format("postMedicalClinic failed. ", d.cause()));
									errorMedicalClinic(siteRequest, eventHandler, d);
								}
							});
						} else {
							LOGGER.error(String.format("postMedicalClinic failed. ", c.cause()));
							errorMedicalClinic(siteRequest, eventHandler, c);
						}
					});
				} else {
					LOGGER.error(String.format("postMedicalClinic failed. ", b.cause()));
					errorMedicalClinic(siteRequest, eventHandler, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("postMedicalClinic failed. ", ex));
			errorMedicalClinic(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public Future<MedicalClinic> postMedicalClinicFuture(SiteRequestEnUS siteRequest, Boolean inheritPk, Handler<AsyncResult<MedicalClinic>> eventHandler) {
		Promise<MedicalClinic> promise = Promise.promise();
		try {
			sqlConnectionMedicalClinic(siteRequest, a -> {
				if(a.succeeded()) {
					sqlTransactionMedicalClinic(siteRequest, b -> {
						if(b.succeeded()) {
							createMedicalClinic(siteRequest, c -> {
								if(c.succeeded()) {
									MedicalClinic medicalClinic = c.result();
									sqlPOSTMedicalClinic(medicalClinic, inheritPk, d -> {
										if(d.succeeded()) {
											defineIndexMedicalClinic(medicalClinic, e -> {
												if(e.succeeded()) {
													ApiRequest apiRequest = siteRequest.getApiRequest_();
													if(apiRequest != null) {
														apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
														medicalClinic.apiRequestMedicalClinic();
														siteRequest.getVertx().eventBus().publish("websocketMedicalClinic", JsonObject.mapFrom(apiRequest).toString());
													}
													eventHandler.handle(Future.succeededFuture(medicalClinic));
													promise.complete(medicalClinic);
												} else {
													LOGGER.error(String.format("postMedicalClinicFuture failed. ", e.cause()));
													eventHandler.handle(Future.failedFuture(e.cause()));
												}
											});
										} else {
											LOGGER.error(String.format("postMedicalClinicFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOGGER.error(String.format("postMedicalClinicFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOGGER.error(String.format("postMedicalClinicFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOGGER.error(String.format("postMedicalClinicFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("postMedicalClinicFuture failed. ", e));
			errorMedicalClinic(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPOSTMedicalClinic(MedicalClinic o, Boolean inheritPk, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
									a.handle(Future.failedFuture(new Exception("value MedicalClinic.inheritPk failed", b.cause())));
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
									a.handle(Future.failedFuture(new Exception("value MedicalClinic.archived failed", b.cause())));
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
									a.handle(Future.failedFuture(new Exception("value MedicalClinic.deleted failed", b.cause())));
							});
						}));
						break;
					case "clinicName":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "clinicName", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalClinic.clinicName failed", b.cause())));
							});
						}));
						break;
					case "clinicPhoneNumber":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "clinicPhoneNumber", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalClinic.clinicPhoneNumber failed", b.cause())));
							});
						}));
						break;
					case "clinicAdministratorName":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "clinicAdministratorName", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalClinic.clinicAdministratorName failed", b.cause())));
							});
						}));
						break;
					case "clinicEmail":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "clinicEmail", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalClinic.clinicEmail failed", b.cause())));
							});
						}));
						break;
					case "clinicEmailFrom":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "clinicEmailFrom", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalClinic.clinicEmailFrom failed", b.cause())));
							});
						}));
						break;
					case "clinicEmailTo":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "clinicEmailTo", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalClinic.clinicEmailTo failed", b.cause())));
							});
						}));
						break;
					case "clinicLocation":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "clinicLocation", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalClinic.clinicLocation failed", b.cause())));
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
									a.handle(Future.failedFuture(new Exception("value MedicalClinic.clinicAddress failed", b.cause())));
							});
						}));
						break;
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
												, Tuple.of(l2, "clinicKey", pk, "enrollmentKeys")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value MedicalClinic.enrollmentKeys failed", b.cause())));
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
					}
				}
			}
			CompositeFuture.all(futures).setHandler( a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture());
				} else {
					LOGGER.error(String.format("sqlPOSTMedicalClinic failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPOSTMedicalClinic failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void postMedicalClinicResponse(MedicalClinic medicalClinic, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = medicalClinic.getSiteRequest_();
		try {
			response200POSTMedicalClinic(medicalClinic, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("postMedicalClinicResponse failed. ", a.cause()));
					errorMedicalClinic(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("postMedicalClinicResponse failed. ", ex));
			errorMedicalClinic(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200POSTMedicalClinic(MedicalClinic o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			JsonObject json = JsonObject.mapFrom(o);
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200POSTMedicalClinic failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PATCH //

	@Override
	public void patchMedicalClinic(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForMedicalClinic(siteContext, operationRequest, body);
		try {
			LOGGER.info(String.format("patchMedicalClinic started. "));

			List<String> roles = Arrays.asList("SiteAdmin");
			if(
					!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles)
					&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles)
					) {
				eventHandler.handle(Future.succeededFuture(
					new OperationResponse(401, "UNAUTHORIZED", 
						Buffer.buffer().appendString(
							new JsonObject()
								.put("errorCode", "401")
								.put("errorMessage", "roles required: " + String.join(", ", roles))
								.encodePrettily()
							), new CaseInsensitiveHeaders()
					)
				));
			}

			userMedicalClinic(siteRequest, b -> {
				if(b.succeeded()) {
					patchMedicalClinicResponse(siteRequest, c -> {
						if(c.succeeded()) {
							eventHandler.handle(Future.succeededFuture(c.result()));
							WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
							workerExecutor.executeBlocking(
								blockingCodeHandler -> {
									try {
										aSearchMedicalClinic(siteRequest, false, true, "/api/clinic", "PATCH", d -> {
											if(d.succeeded()) {
												SearchList<MedicalClinic> listMedicalClinic = d.result();
												ApiRequest apiRequest = new ApiRequest();
												apiRequest.setRows(listMedicalClinic.getRows());
												apiRequest.setNumFound(listMedicalClinic.getQueryResponse().getResults().getNumFound());
												apiRequest.setNumPATCH(0L);
												apiRequest.initDeepApiRequest(siteRequest);
												siteRequest.setApiRequest_(apiRequest);
												siteRequest.getVertx().eventBus().publish("websocketMedicalClinic", JsonObject.mapFrom(apiRequest).toString());
												SimpleOrderedMap facets = (SimpleOrderedMap)Optional.ofNullable(listMedicalClinic.getQueryResponse()).map(QueryResponse::getResponse).map(r -> r.get("facets")).orElse(null);
												Date date = null;
												if(facets != null)
													date = (Date)facets.get("max_modified");
												String dt;
												if(date == null)
													dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(ZonedDateTime.now().toInstant(), ZoneId.of("UTC")).minusNanos(1000));
												else
													dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC")));
												listMedicalClinic.addFilterQuery(String.format("modified_indexed_date:[* TO %s]", dt));

												try {
													listPATCHMedicalClinic(apiRequest, listMedicalClinic, dt, e -> {
														if(e.succeeded()) {
															patchMedicalClinicResponse(siteRequest, f -> {
																if(f.succeeded()) {
																	LOGGER.info(String.format("patchMedicalClinic succeeded. "));
																	blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																} else {
																	LOGGER.error(String.format("patchMedicalClinic failed. ", f.cause()));
																	errorMedicalClinic(siteRequest, null, f);
																}
															});
														} else {
															LOGGER.error(String.format("patchMedicalClinic failed. ", e.cause()));
															errorMedicalClinic(siteRequest, null, e);
														}
													});
												} catch(Exception ex) {
													LOGGER.error(String.format("patchMedicalClinic failed. ", ex));
													errorMedicalClinic(siteRequest, null, Future.failedFuture(ex));
												}
											} else {
												LOGGER.error(String.format("patchMedicalClinic failed. ", d.cause()));
												errorMedicalClinic(siteRequest, null, d);
											}
										});
									} catch(Exception ex) {
										LOGGER.error(String.format("patchMedicalClinic failed. ", ex));
										errorMedicalClinic(siteRequest, null, Future.failedFuture(ex));
									}
								}, resultHandler -> {
								}
							);
						} else {
							LOGGER.error(String.format("patchMedicalClinic failed. ", c.cause()));
							errorMedicalClinic(siteRequest, eventHandler, c);
						}
					});
				} else {
					LOGGER.error(String.format("patchMedicalClinic failed. ", b.cause()));
					errorMedicalClinic(siteRequest, eventHandler, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("patchMedicalClinic failed. ", ex));
			errorMedicalClinic(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void listPATCHMedicalClinic(ApiRequest apiRequest, SearchList<MedicalClinic> listMedicalClinic, String dt, Handler<AsyncResult<OperationResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listMedicalClinic.getSiteRequest_();
		listMedicalClinic.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForMedicalClinic(siteContext, siteRequest.getOperationRequest(), siteRequest.getJsonObject());
			siteRequest2.setApiRequest_(siteRequest.getApiRequest_());
			o.setSiteRequest_(siteRequest2);
			futures.add(
				patchMedicalClinicFuture(o, false, a -> {
					if(a.succeeded()) {
					} else {
						errorMedicalClinic(siteRequest2, eventHandler, a);
					}
				})
			);
		});
		CompositeFuture.all(futures).setHandler( a -> {
			if(a.succeeded()) {
				if(listMedicalClinic.next(dt)) {
					listPATCHMedicalClinic(apiRequest, listMedicalClinic, dt, eventHandler);
				} else {
					response200PATCHMedicalClinic(siteRequest, eventHandler);
				}
			} else {
				LOGGER.error(String.format("listPATCHMedicalClinic failed. ", a.cause()));
				errorMedicalClinic(listMedicalClinic.getSiteRequest_(), eventHandler, a);
			}
		});
	}

	public Future<MedicalClinic> patchMedicalClinicFuture(MedicalClinic o, Boolean inheritPk, Handler<AsyncResult<MedicalClinic>> eventHandler) {
		Promise<MedicalClinic> promise = Promise.promise();
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			if(apiRequest != null && apiRequest.getNumFound() == 1L) {
				apiRequest.setOriginal(o);
				apiRequest.setPk(o.getPk());
			}
			sqlConnectionMedicalClinic(siteRequest, a -> {
				if(a.succeeded()) {
					sqlTransactionMedicalClinic(siteRequest, b -> {
						if(b.succeeded()) {
							sqlPATCHMedicalClinic(o, inheritPk, c -> {
								if(c.succeeded()) {
									MedicalClinic medicalClinic = c.result();
									defineIndexMedicalClinic(medicalClinic, d -> {
										if(d.succeeded()) {
											if(apiRequest != null) {
												apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
												if(apiRequest.getNumFound() == 1L) {
													medicalClinic.apiRequestMedicalClinic();
												}
												siteRequest.getVertx().eventBus().publish("websocketMedicalClinic", JsonObject.mapFrom(apiRequest).toString());
											}
											eventHandler.handle(Future.succeededFuture(medicalClinic));
											promise.complete(medicalClinic);
										} else {
											LOGGER.error(String.format("patchMedicalClinicFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOGGER.error(String.format("patchMedicalClinicFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOGGER.error(String.format("patchMedicalClinicFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOGGER.error(String.format("patchMedicalClinicFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("patchMedicalClinicFuture failed. ", e));
			errorMedicalClinic(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPATCHMedicalClinic(MedicalClinic o, Boolean inheritPk, Handler<AsyncResult<MedicalClinic>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Transaction tx = siteRequest.getTx();
			Long pk = o.getPk();
			JsonObject jsonObject = siteRequest.getJsonObject();
			Set<String> methodNames = jsonObject.fieldNames();
			MedicalClinic o2 = new MedicalClinic();
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
										a.handle(Future.failedFuture(new Exception("value MedicalClinic.inheritPk failed", b.cause())));
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
										a.handle(Future.failedFuture(new Exception("value MedicalClinic.inheritPk failed", b.cause())));
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
										a.handle(Future.failedFuture(new Exception("value MedicalClinic.archived failed", b.cause())));
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
										a.handle(Future.failedFuture(new Exception("value MedicalClinic.archived failed", b.cause())));
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
										a.handle(Future.failedFuture(new Exception("value MedicalClinic.deleted failed", b.cause())));
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
										a.handle(Future.failedFuture(new Exception("value MedicalClinic.deleted failed", b.cause())));
								});
							}));
						}
						break;
					case "setClinicName":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "clinicName")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalClinic.clinicName failed", b.cause())));
								});
							}));
						} else {
							o2.setClinicName(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "clinicName", o2.jsonClinicName())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalClinic.clinicName failed", b.cause())));
								});
							}));
						}
						break;
					case "setClinicPhoneNumber":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "clinicPhoneNumber")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalClinic.clinicPhoneNumber failed", b.cause())));
								});
							}));
						} else {
							o2.setClinicPhoneNumber(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "clinicPhoneNumber", o2.jsonClinicPhoneNumber())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalClinic.clinicPhoneNumber failed", b.cause())));
								});
							}));
						}
						break;
					case "setClinicAdministratorName":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "clinicAdministratorName")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalClinic.clinicAdministratorName failed", b.cause())));
								});
							}));
						} else {
							o2.setClinicAdministratorName(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "clinicAdministratorName", o2.jsonClinicAdministratorName())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalClinic.clinicAdministratorName failed", b.cause())));
								});
							}));
						}
						break;
					case "setClinicEmail":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "clinicEmail")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalClinic.clinicEmail failed", b.cause())));
								});
							}));
						} else {
							o2.setClinicEmail(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "clinicEmail", o2.jsonClinicEmail())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalClinic.clinicEmail failed", b.cause())));
								});
							}));
						}
						break;
					case "setClinicEmailFrom":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "clinicEmailFrom")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalClinic.clinicEmailFrom failed", b.cause())));
								});
							}));
						} else {
							o2.setClinicEmailFrom(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "clinicEmailFrom", o2.jsonClinicEmailFrom())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalClinic.clinicEmailFrom failed", b.cause())));
								});
							}));
						}
						break;
					case "setClinicEmailTo":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "clinicEmailTo")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalClinic.clinicEmailTo failed", b.cause())));
								});
							}));
						} else {
							o2.setClinicEmailTo(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "clinicEmailTo", o2.jsonClinicEmailTo())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalClinic.clinicEmailTo failed", b.cause())));
								});
							}));
						}
						break;
					case "setClinicLocation":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "clinicLocation")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalClinic.clinicLocation failed", b.cause())));
								});
							}));
						} else {
							o2.setClinicLocation(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "clinicLocation", o2.jsonClinicLocation())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalClinic.clinicLocation failed", b.cause())));
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
										a.handle(Future.failedFuture(new Exception("value MedicalClinic.clinicAddress failed", b.cause())));
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
										a.handle(Future.failedFuture(new Exception("value MedicalClinic.clinicAddress failed", b.cause())));
								});
							}));
						}
						break;
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
												, Tuple.of(l2, "clinicKey", pk, "enrollmentKeys")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value MedicalClinic.enrollmentKeys failed", b.cause())));
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
												, Tuple.of(l2, "clinicKey", pk, "enrollmentKeys")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value MedicalClinic.enrollmentKeys failed", b.cause())));
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
												, Tuple.of(l2, "clinicKey", pk, "enrollmentKeys")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value MedicalClinic.enrollmentKeys failed", b.cause())));
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
												, Tuple.of(l, "clinicKey", pk, "enrollmentKeys")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value MedicalClinic.enrollmentKeys failed", b.cause())));
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
												, Tuple.of(l2, "clinicKey", pk, "enrollmentKeys")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value MedicalClinic.enrollmentKeys failed", b.cause())));
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
				}
			}
			CompositeFuture.all(futures).setHandler( a -> {
				if(a.succeeded()) {
					MedicalClinic o3 = new MedicalClinic();
					o3.setSiteRequest_(o.getSiteRequest_());
					o3.setPk(pk);
					eventHandler.handle(Future.succeededFuture(o3));
				} else {
					LOGGER.error(String.format("sqlPATCHMedicalClinic failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPATCHMedicalClinic failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void patchMedicalClinicResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PATCHMedicalClinic(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("patchMedicalClinicResponse failed. ", a.cause()));
					errorMedicalClinic(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("patchMedicalClinicResponse failed. ", ex));
			errorMedicalClinic(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PATCHMedicalClinic(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PATCHMedicalClinic failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// GET //

	@Override
	public void getMedicalClinic(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForMedicalClinic(siteContext, operationRequest);
		try {

			List<String> roles = Arrays.asList("SiteAdmin");
			List<String> roleLires = Arrays.asList("");
			if(
					!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles)
					&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles)
					&& !CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roleLires)
					&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roleLires)
					) {
				eventHandler.handle(Future.succeededFuture(
					new OperationResponse(401, "UNAUTHORIZED", 
						Buffer.buffer().appendString(
							new JsonObject()
								.put("errorCode", "401")
								.put("errorMessage", "roles required: " + String.join(", ", roles))
								.encodePrettily()
							), new CaseInsensitiveHeaders()
					)
				));
			}

			userMedicalClinic(siteRequest, b -> {
				if(b.succeeded()) {
					aSearchMedicalClinic(siteRequest, false, true, "/api/clinic/{id}", "GET", c -> {
						if(c.succeeded()) {
							SearchList<MedicalClinic> listMedicalClinic = c.result();
							getMedicalClinicResponse(listMedicalClinic, d -> {
								if(d.succeeded()) {
									eventHandler.handle(Future.succeededFuture(d.result()));
									LOGGER.info(String.format("getMedicalClinic succeeded. "));
								} else {
									LOGGER.error(String.format("getMedicalClinic failed. ", d.cause()));
									errorMedicalClinic(siteRequest, eventHandler, d);
								}
							});
						} else {
							LOGGER.error(String.format("getMedicalClinic failed. ", c.cause()));
							errorMedicalClinic(siteRequest, eventHandler, c);
						}
					});
				} else {
					LOGGER.error(String.format("getMedicalClinic failed. ", b.cause()));
					errorMedicalClinic(siteRequest, eventHandler, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("getMedicalClinic failed. ", ex));
			errorMedicalClinic(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void getMedicalClinicResponse(SearchList<MedicalClinic> listMedicalClinic, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listMedicalClinic.getSiteRequest_();
		try {
			response200GETMedicalClinic(listMedicalClinic, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("getMedicalClinicResponse failed. ", a.cause()));
					errorMedicalClinic(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("getMedicalClinicResponse failed. ", ex));
			errorMedicalClinic(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200GETMedicalClinic(SearchList<MedicalClinic> listMedicalClinic, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listMedicalClinic.getSiteRequest_();
			SolrDocumentList solrDocuments = listMedicalClinic.getSolrDocumentList();

			JsonObject json = JsonObject.mapFrom(listMedicalClinic.getList().stream().findFirst().orElse(null));
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200GETMedicalClinic failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// Search //

	@Override
	public void searchMedicalClinic(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForMedicalClinic(siteContext, operationRequest);
		try {

			List<String> roles = Arrays.asList("SiteAdmin");
			List<String> roleLires = Arrays.asList("");
			if(
					!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles)
					&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles)
					&& !CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roleLires)
					&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roleLires)
					) {
				eventHandler.handle(Future.succeededFuture(
					new OperationResponse(401, "UNAUTHORIZED", 
						Buffer.buffer().appendString(
							new JsonObject()
								.put("errorCode", "401")
								.put("errorMessage", "roles required: " + String.join(", ", roles))
								.encodePrettily()
							), new CaseInsensitiveHeaders()
					)
				));
			}

			userMedicalClinic(siteRequest, b -> {
				if(b.succeeded()) {
					aSearchMedicalClinic(siteRequest, false, true, "/api/clinic", "Search", c -> {
						if(c.succeeded()) {
							SearchList<MedicalClinic> listMedicalClinic = c.result();
							searchMedicalClinicResponse(listMedicalClinic, d -> {
								if(d.succeeded()) {
									eventHandler.handle(Future.succeededFuture(d.result()));
									LOGGER.info(String.format("searchMedicalClinic succeeded. "));
								} else {
									LOGGER.error(String.format("searchMedicalClinic failed. ", d.cause()));
									errorMedicalClinic(siteRequest, eventHandler, d);
								}
							});
						} else {
							LOGGER.error(String.format("searchMedicalClinic failed. ", c.cause()));
							errorMedicalClinic(siteRequest, eventHandler, c);
						}
					});
				} else {
					LOGGER.error(String.format("searchMedicalClinic failed. ", b.cause()));
					errorMedicalClinic(siteRequest, eventHandler, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("searchMedicalClinic failed. ", ex));
			errorMedicalClinic(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void searchMedicalClinicResponse(SearchList<MedicalClinic> listMedicalClinic, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listMedicalClinic.getSiteRequest_();
		try {
			response200SearchMedicalClinic(listMedicalClinic, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("searchMedicalClinicResponse failed. ", a.cause()));
					errorMedicalClinic(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("searchMedicalClinicResponse failed. ", ex));
			errorMedicalClinic(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200SearchMedicalClinic(SearchList<MedicalClinic> listMedicalClinic, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listMedicalClinic.getSiteRequest_();
			QueryResponse responseSearch = listMedicalClinic.getQueryResponse();
			SolrDocumentList solrDocuments = listMedicalClinic.getSolrDocumentList();
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
			listMedicalClinic.getList().stream().forEach(o -> {
				JsonObject json2 = JsonObject.mapFrom(o);
				List<String> fls = listMedicalClinic.getFields();
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
			LOGGER.error(String.format("response200SearchMedicalClinic failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTImport //

	@Override
	public void putimportMedicalClinic(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForMedicalClinic(siteContext, operationRequest, body);
		try {
			LOGGER.info(String.format("putimportMedicalClinic started. "));

			List<String> roles = Arrays.asList("SiteAdmin");
			if(
					!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles)
					&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles)
					) {
				eventHandler.handle(Future.succeededFuture(
					new OperationResponse(401, "UNAUTHORIZED", 
						Buffer.buffer().appendString(
							new JsonObject()
								.put("errorCode", "401")
								.put("errorMessage", "roles required: " + String.join(", ", roles))
								.encodePrettily()
							), new CaseInsensitiveHeaders()
					)
				));
			}

			userMedicalClinic(siteRequest, b -> {
				if(b.succeeded()) {
					putimportMedicalClinicResponse(siteRequest, c -> {
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
										siteRequest.getVertx().eventBus().publish("websocketMedicalClinic", JsonObject.mapFrom(apiRequest).toString());
										varsMedicalClinic(siteRequest, d -> {
											if(d.succeeded()) {
												listPUTImportMedicalClinic(apiRequest, siteRequest, e -> {
													if(e.succeeded()) {
														putimportMedicalClinicResponse(siteRequest, f -> {
															if(e.succeeded()) {
																LOGGER.info(String.format("putimportMedicalClinic succeeded. "));
																blockingCodeHandler.handle(Future.succeededFuture(e.result()));
															} else {
																LOGGER.error(String.format("putimportMedicalClinic failed. ", f.cause()));
																errorMedicalClinic(siteRequest, null, f);
															}
														});
													} else {
														LOGGER.error(String.format("putimportMedicalClinic failed. ", e.cause()));
														errorMedicalClinic(siteRequest, null, e);
													}
												});
											} else {
												LOGGER.error(String.format("putimportMedicalClinic failed. ", d.cause()));
												errorMedicalClinic(siteRequest, null, d);
											}
										});
									} catch(Exception ex) {
										LOGGER.error(String.format("putimportMedicalClinic failed. ", ex));
										errorMedicalClinic(siteRequest, null, Future.failedFuture(ex));
									}
								}, resultHandler -> {
								}
							);
						} else {
							LOGGER.error(String.format("putimportMedicalClinic failed. ", c.cause()));
							errorMedicalClinic(siteRequest, eventHandler, c);
						}
					});
				} else {
					LOGGER.error(String.format("putimportMedicalClinic failed. ", b.cause()));
					errorMedicalClinic(siteRequest, eventHandler, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putimportMedicalClinic failed. ", ex));
			errorMedicalClinic(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void listPUTImportMedicalClinic(ApiRequest apiRequest, SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
		try {
			jsonArray.forEach(obj -> {
				JsonObject json = (JsonObject)obj;

				json.put("inheritPk", json.getValue("pk"));

				json.put("created", json.getValue("created"));

				SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForMedicalClinic(siteContext, siteRequest.getOperationRequest(), json);
				siteRequest2.setApiRequest_(apiRequest);
				siteRequest2.setRequestVars(siteRequest.getRequestVars());

				SearchList<MedicalClinic> searchList = new SearchList<MedicalClinic>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(MedicalClinic.class);
				searchList.addFilterQuery("inheritPk_indexed_long:" + json.getString("pk"));
				searchList.initDeepForClass(siteRequest2);

				if(searchList.size() == 1) {
					MedicalClinic o = searchList.getList().stream().findFirst().orElse(null);
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
							patchMedicalClinicFuture(o, true, a -> {
								if(a.succeeded()) {
								} else {
									LOGGER.error(String.format("listPUTImportMedicalClinic failed. ", a.cause()));
									errorMedicalClinic(siteRequest2, eventHandler, a);
								}
							})
						);
					}
				} else {
					futures.add(
						postMedicalClinicFuture(siteRequest2, true, a -> {
							if(a.succeeded()) {
							} else {
								LOGGER.error(String.format("listPUTImportMedicalClinic failed. ", a.cause()));
								errorMedicalClinic(siteRequest2, eventHandler, a);
							}
						})
					);
				}
			});
			CompositeFuture.all(futures).setHandler( a -> {
				if(a.succeeded()) {
					apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
					response200PUTImportMedicalClinic(siteRequest, eventHandler);
				} else {
					LOGGER.error(String.format("listPUTImportMedicalClinic failed. ", a.cause()));
					errorMedicalClinic(apiRequest.getSiteRequest_(), eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("listPUTImportMedicalClinic failed. ", ex));
			errorMedicalClinic(siteRequest, null, Future.failedFuture(ex));
		}
	}

	public void putimportMedicalClinicResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PUTImportMedicalClinic(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("putimportMedicalClinicResponse failed. ", a.cause()));
					errorMedicalClinic(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putimportMedicalClinicResponse failed. ", ex));
			errorMedicalClinic(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTImportMedicalClinic(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PUTImportMedicalClinic failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTMerge //

	@Override
	public void putmergeMedicalClinic(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForMedicalClinic(siteContext, operationRequest, body);
		try {
			LOGGER.info(String.format("putmergeMedicalClinic started. "));

			List<String> roles = Arrays.asList("SiteAdmin");
			if(
					!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles)
					&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles)
					) {
				eventHandler.handle(Future.succeededFuture(
					new OperationResponse(401, "UNAUTHORIZED", 
						Buffer.buffer().appendString(
							new JsonObject()
								.put("errorCode", "401")
								.put("errorMessage", "roles required: " + String.join(", ", roles))
								.encodePrettily()
							), new CaseInsensitiveHeaders()
					)
				));
			}

			userMedicalClinic(siteRequest, b -> {
				if(b.succeeded()) {
					putmergeMedicalClinicResponse(siteRequest, c -> {
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
										siteRequest.getVertx().eventBus().publish("websocketMedicalClinic", JsonObject.mapFrom(apiRequest).toString());
										varsMedicalClinic(siteRequest, d -> {
											if(d.succeeded()) {
												listPUTMergeMedicalClinic(apiRequest, siteRequest, e -> {
													if(e.succeeded()) {
														putmergeMedicalClinicResponse(siteRequest, f -> {
															if(e.succeeded()) {
																LOGGER.info(String.format("putmergeMedicalClinic succeeded. "));
																blockingCodeHandler.handle(Future.succeededFuture(e.result()));
															} else {
																LOGGER.error(String.format("putmergeMedicalClinic failed. ", f.cause()));
																errorMedicalClinic(siteRequest, null, f);
															}
														});
													} else {
														LOGGER.error(String.format("putmergeMedicalClinic failed. ", e.cause()));
														errorMedicalClinic(siteRequest, null, e);
													}
												});
											} else {
												LOGGER.error(String.format("putmergeMedicalClinic failed. ", d.cause()));
												errorMedicalClinic(siteRequest, null, d);
											}
										});
									} catch(Exception ex) {
										LOGGER.error(String.format("putmergeMedicalClinic failed. ", ex));
										errorMedicalClinic(siteRequest, null, Future.failedFuture(ex));
									}
								}, resultHandler -> {
								}
							);
						} else {
							LOGGER.error(String.format("putmergeMedicalClinic failed. ", c.cause()));
							errorMedicalClinic(siteRequest, eventHandler, c);
						}
					});
				} else {
					LOGGER.error(String.format("putmergeMedicalClinic failed. ", b.cause()));
					errorMedicalClinic(siteRequest, eventHandler, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putmergeMedicalClinic failed. ", ex));
			errorMedicalClinic(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void listPUTMergeMedicalClinic(ApiRequest apiRequest, SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
		try {
			jsonArray.forEach(obj -> {
				JsonObject json = (JsonObject)obj;

				json.put("inheritPk", json.getValue("pk"));

				SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForMedicalClinic(siteContext, siteRequest.getOperationRequest(), json);
				siteRequest2.setApiRequest_(apiRequest);
				siteRequest2.setRequestVars(siteRequest.getRequestVars());

				SearchList<MedicalClinic> searchList = new SearchList<MedicalClinic>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(MedicalClinic.class);
				searchList.addFilterQuery("pk_indexed_long:" + json.getString("pk"));
				searchList.initDeepForClass(siteRequest2);

				if(searchList.size() == 1) {
					MedicalClinic o = searchList.getList().stream().findFirst().orElse(null);
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
							patchMedicalClinicFuture(o, false, a -> {
								if(a.succeeded()) {
								} else {
									LOGGER.error(String.format("listPUTMergeMedicalClinic failed. ", a.cause()));
									errorMedicalClinic(siteRequest2, eventHandler, a);
								}
							})
						);
					}
				} else {
					futures.add(
						postMedicalClinicFuture(siteRequest2, false, a -> {
							if(a.succeeded()) {
							} else {
								LOGGER.error(String.format("listPUTMergeMedicalClinic failed. ", a.cause()));
								errorMedicalClinic(siteRequest2, eventHandler, a);
							}
						})
					);
				}
			});
			CompositeFuture.all(futures).setHandler( a -> {
				if(a.succeeded()) {
					apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
					response200PUTMergeMedicalClinic(siteRequest, eventHandler);
				} else {
					LOGGER.error(String.format("listPUTMergeMedicalClinic failed. ", a.cause()));
					errorMedicalClinic(apiRequest.getSiteRequest_(), eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("listPUTMergeMedicalClinic failed. ", ex));
			errorMedicalClinic(siteRequest, null, Future.failedFuture(ex));
		}
	}

	public void putmergeMedicalClinicResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PUTMergeMedicalClinic(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("putmergeMedicalClinicResponse failed. ", a.cause()));
					errorMedicalClinic(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putmergeMedicalClinicResponse failed. ", ex));
			errorMedicalClinic(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTMergeMedicalClinic(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PUTMergeMedicalClinic failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTCopy //

	@Override
	public void putcopyMedicalClinic(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForMedicalClinic(siteContext, operationRequest, body);
		try {
			LOGGER.info(String.format("putcopyMedicalClinic started. "));

			List<String> roles = Arrays.asList("SiteAdmin");
			if(
					!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles)
					&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles)
					) {
				eventHandler.handle(Future.succeededFuture(
					new OperationResponse(401, "UNAUTHORIZED", 
						Buffer.buffer().appendString(
							new JsonObject()
								.put("errorCode", "401")
								.put("errorMessage", "roles required: " + String.join(", ", roles))
								.encodePrettily()
							), new CaseInsensitiveHeaders()
					)
				));
			}

			userMedicalClinic(siteRequest, b -> {
				if(b.succeeded()) {
					putcopyMedicalClinicResponse(siteRequest, c -> {
						if(c.succeeded()) {
							eventHandler.handle(Future.succeededFuture(c.result()));
							WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
							workerExecutor.executeBlocking(
								blockingCodeHandler -> {
									try {
										aSearchMedicalClinic(siteRequest, false, true, "/api/clinic/copy", "PUTCopy", d -> {
											if(d.succeeded()) {
												SearchList<MedicalClinic> listMedicalClinic = d.result();
												ApiRequest apiRequest = new ApiRequest();
												apiRequest.setRows(listMedicalClinic.getRows());
												apiRequest.setNumFound(listMedicalClinic.getQueryResponse().getResults().getNumFound());
												apiRequest.setNumPATCH(0L);
												apiRequest.initDeepApiRequest(siteRequest);
												siteRequest.setApiRequest_(apiRequest);
												siteRequest.getVertx().eventBus().publish("websocketMedicalClinic", JsonObject.mapFrom(apiRequest).toString());
												try {
													listPUTCopyMedicalClinic(apiRequest, listMedicalClinic, e -> {
														if(e.succeeded()) {
															putcopyMedicalClinicResponse(siteRequest, f -> {
																if(f.succeeded()) {
																	LOGGER.info(String.format("putcopyMedicalClinic succeeded. "));
																	blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																} else {
																	LOGGER.error(String.format("putcopyMedicalClinic failed. ", f.cause()));
																	errorMedicalClinic(siteRequest, null, f);
																}
															});
														} else {
															LOGGER.error(String.format("putcopyMedicalClinic failed. ", e.cause()));
															errorMedicalClinic(siteRequest, null, e);
														}
													});
												} catch(Exception ex) {
													LOGGER.error(String.format("putcopyMedicalClinic failed. ", ex));
													errorMedicalClinic(siteRequest, null, Future.failedFuture(ex));
												}
											} else {
												LOGGER.error(String.format("putcopyMedicalClinic failed. ", d.cause()));
												errorMedicalClinic(siteRequest, null, d);
											}
										});
									} catch(Exception ex) {
										LOGGER.error(String.format("putcopyMedicalClinic failed. ", ex));
										errorMedicalClinic(siteRequest, null, Future.failedFuture(ex));
									}
								}, resultHandler -> {
								}
							);
						} else {
							LOGGER.error(String.format("putcopyMedicalClinic failed. ", c.cause()));
							errorMedicalClinic(siteRequest, eventHandler, c);
						}
					});
				} else {
					LOGGER.error(String.format("putcopyMedicalClinic failed. ", b.cause()));
					errorMedicalClinic(siteRequest, eventHandler, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putcopyMedicalClinic failed. ", ex));
			errorMedicalClinic(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void listPUTCopyMedicalClinic(ApiRequest apiRequest, SearchList<MedicalClinic> listMedicalClinic, Handler<AsyncResult<OperationResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listMedicalClinic.getSiteRequest_();
		listMedicalClinic.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForMedicalClinic(siteContext, siteRequest.getOperationRequest(), siteRequest.getJsonObject());
			siteRequest2.setApiRequest_(siteRequest.getApiRequest_());
			o.setSiteRequest_(siteRequest2);
			futures.add(
				putcopyMedicalClinicFuture(siteRequest, JsonObject.mapFrom(o), a -> {
					if(a.succeeded()) {
					} else {
						LOGGER.error(String.format("listPUTCopyMedicalClinic failed. ", a.cause()));
						errorMedicalClinic(siteRequest, eventHandler, a);
					}
				})
			);
		});
		CompositeFuture.all(futures).setHandler( a -> {
			if(a.succeeded()) {
				apiRequest.setNumPATCH(apiRequest.getNumPATCH() + listMedicalClinic.size());
				if(listMedicalClinic.next()) {
					listPUTCopyMedicalClinic(apiRequest, listMedicalClinic, eventHandler);
				} else {
					response200PUTCopyMedicalClinic(siteRequest, eventHandler);
				}
			} else {
				LOGGER.error(String.format("listPUTCopyMedicalClinic failed. ", a.cause()));
				errorMedicalClinic(listMedicalClinic.getSiteRequest_(), eventHandler, a);
			}
		});
	}

	public Future<MedicalClinic> putcopyMedicalClinicFuture(SiteRequestEnUS siteRequest, JsonObject jsonObject, Handler<AsyncResult<MedicalClinic>> eventHandler) {
		Promise<MedicalClinic> promise = Promise.promise();
		try {

			jsonObject.put("saves", Optional.ofNullable(jsonObject.getJsonArray("saves")).orElse(new JsonArray()));
			JsonObject jsonPatch = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonObject("patch")).orElse(new JsonObject());
			jsonPatch.stream().forEach(o -> {
				jsonObject.put(o.getKey(), o.getValue());
				jsonObject.getJsonArray("saves").add(o.getKey());
			});

			sqlConnectionMedicalClinic(siteRequest, a -> {
				if(a.succeeded()) {
					sqlTransactionMedicalClinic(siteRequest, b -> {
						if(b.succeeded()) {
							createMedicalClinic(siteRequest, c -> {
								if(c.succeeded()) {
									MedicalClinic medicalClinic = c.result();
									sqlPUTCopyMedicalClinic(medicalClinic, jsonObject, d -> {
										if(d.succeeded()) {
											defineIndexMedicalClinic(medicalClinic, e -> {
												if(e.succeeded()) {
													ApiRequest apiRequest = siteRequest.getApiRequest_();
													if(apiRequest != null) {
														apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
														if(apiRequest.getNumFound() == 1L) {
															medicalClinic.apiRequestMedicalClinic();
														}
														siteRequest.getVertx().eventBus().publish("websocketMedicalClinic", JsonObject.mapFrom(apiRequest).toString());
													}
													eventHandler.handle(Future.succeededFuture(medicalClinic));
													promise.complete(medicalClinic);
												} else {
													LOGGER.error(String.format("putcopyMedicalClinicFuture failed. ", e.cause()));
													eventHandler.handle(Future.failedFuture(e.cause()));
												}
											});
										} else {
											LOGGER.error(String.format("putcopyMedicalClinicFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOGGER.error(String.format("putcopyMedicalClinicFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOGGER.error(String.format("putcopyMedicalClinicFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOGGER.error(String.format("putcopyMedicalClinicFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("putcopyMedicalClinicFuture failed. ", e));
			errorMedicalClinic(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPUTCopyMedicalClinic(MedicalClinic o, JsonObject jsonObject, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
									a.handle(Future.failedFuture(new Exception("value MedicalClinic.inheritPk failed", b.cause())));
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
									a.handle(Future.failedFuture(new Exception("value MedicalClinic.archived failed", b.cause())));
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
									a.handle(Future.failedFuture(new Exception("value MedicalClinic.deleted failed", b.cause())));
							});
						}));
						break;
					case "clinicName":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "clinicName", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalClinic.clinicName failed", b.cause())));
							});
						}));
						break;
					case "clinicPhoneNumber":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "clinicPhoneNumber", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalClinic.clinicPhoneNumber failed", b.cause())));
							});
						}));
						break;
					case "clinicAdministratorName":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "clinicAdministratorName", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalClinic.clinicAdministratorName failed", b.cause())));
							});
						}));
						break;
					case "clinicEmail":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "clinicEmail", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalClinic.clinicEmail failed", b.cause())));
							});
						}));
						break;
					case "clinicEmailFrom":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "clinicEmailFrom", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalClinic.clinicEmailFrom failed", b.cause())));
							});
						}));
						break;
					case "clinicEmailTo":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "clinicEmailTo", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalClinic.clinicEmailTo failed", b.cause())));
							});
						}));
						break;
					case "clinicLocation":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "clinicLocation", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value MedicalClinic.clinicLocation failed", b.cause())));
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
									a.handle(Future.failedFuture(new Exception("value MedicalClinic.clinicAddress failed", b.cause())));
							});
						}));
						break;
					case "enrollmentKeys":
						for(Long l : jsonObject.getJsonArray(entityVar).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_addA
										, Tuple.of(l, "clinicKey", pk, "enrollmentKeys")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value MedicalClinic.enrollmentKeys failed", b.cause())));
								});
							}));
							if(!pks.contains(l)) {
								pks.add(l);
								classes.add("MedicalEnrollment");
							}
						}
						break;
					}
				}
			}
			CompositeFuture.all(futures).setHandler( a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture());
				} else {
					LOGGER.error(String.format("sqlPUTCopyMedicalClinic failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPUTCopyMedicalClinic failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void putcopyMedicalClinicResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PUTCopyMedicalClinic(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("putcopyMedicalClinicResponse failed. ", a.cause()));
					errorMedicalClinic(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putcopyMedicalClinicResponse failed. ", ex));
			errorMedicalClinic(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTCopyMedicalClinic(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PUTCopyMedicalClinic failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// SearchPage //

	@Override
	public void searchpageMedicalClinicId(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		searchpageMedicalClinic(operationRequest, eventHandler);
	}

	@Override
	public void searchpageMedicalClinic(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForMedicalClinic(siteContext, operationRequest);
		try {

			List<String> roles = Arrays.asList("SiteAdmin");
			List<String> roleLires = Arrays.asList("");
			if(
					!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles)
					&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles)
					&& !CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roleLires)
					&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roleLires)
					) {
				eventHandler.handle(Future.succeededFuture(
					new OperationResponse(401, "UNAUTHORIZED", 
						Buffer.buffer().appendString(
							new JsonObject()
								.put("errorCode", "401")
								.put("errorMessage", "roles required: " + String.join(", ", roles))
								.encodePrettily()
							), new CaseInsensitiveHeaders()
					)
				));
			}

			userMedicalClinic(siteRequest, b -> {
				if(b.succeeded()) {
					aSearchMedicalClinic(siteRequest, false, true, "/clinic", "SearchPage", c -> {
						if(c.succeeded()) {
							SearchList<MedicalClinic> listMedicalClinic = c.result();
							searchpageMedicalClinicResponse(listMedicalClinic, d -> {
								if(d.succeeded()) {
									eventHandler.handle(Future.succeededFuture(d.result()));
									LOGGER.info(String.format("searchpageMedicalClinic succeeded. "));
								} else {
									LOGGER.error(String.format("searchpageMedicalClinic failed. ", d.cause()));
									errorMedicalClinic(siteRequest, eventHandler, d);
								}
							});
						} else {
							LOGGER.error(String.format("searchpageMedicalClinic failed. ", c.cause()));
							errorMedicalClinic(siteRequest, eventHandler, c);
						}
					});
				} else {
					LOGGER.error(String.format("searchpageMedicalClinic failed. ", b.cause()));
					errorMedicalClinic(siteRequest, eventHandler, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("searchpageMedicalClinic failed. ", ex));
			errorMedicalClinic(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void searchpageMedicalClinicResponse(SearchList<MedicalClinic> listMedicalClinic, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listMedicalClinic.getSiteRequest_();
		try {
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(siteRequest, buffer);
			siteRequest.setW(w);
			response200SearchPageMedicalClinic(listMedicalClinic, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("searchpageMedicalClinicResponse failed. ", a.cause()));
					errorMedicalClinic(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("searchpageMedicalClinicResponse failed. ", ex));
			errorMedicalClinic(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200SearchPageMedicalClinic(SearchList<MedicalClinic> listMedicalClinic, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listMedicalClinic.getSiteRequest_();
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(listMedicalClinic.getSiteRequest_(), buffer);
			ClinicPage page = new ClinicPage();
			SolrDocument pageSolrDocument = new SolrDocument();
			CaseInsensitiveHeaders requestHeaders = new CaseInsensitiveHeaders();
			siteRequest.setRequestHeaders(requestHeaders);

			pageSolrDocument.setField("pageUri_frFR_stored_string", "/clinic");
			page.setPageSolrDocument(pageSolrDocument);
			page.setW(w);
			if(listMedicalClinic.size() == 1)
				siteRequest.setRequestPk(listMedicalClinic.get(0).getPk());
			siteRequest.setW(w);
			page.setListMedicalClinic(listMedicalClinic);
			page.setSiteRequest_(siteRequest);
			page.initDeepClinicPage(siteRequest);
			page.html();
			eventHandler.handle(Future.succeededFuture(new OperationResponse(200, "OK", buffer, requestHeaders)));
		} catch(Exception e) {
			LOGGER.error(String.format("response200SearchPageMedicalClinic failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// General //

	public Future<MedicalClinic> defineIndexMedicalClinic(MedicalClinic medicalClinic, Handler<AsyncResult<MedicalClinic>> eventHandler) {
		Promise<MedicalClinic> promise = Promise.promise();
		SiteRequestEnUS siteRequest = medicalClinic.getSiteRequest_();
		defineMedicalClinic(medicalClinic, c -> {
			if(c.succeeded()) {
				attributeMedicalClinic(medicalClinic, d -> {
					if(d.succeeded()) {
						indexMedicalClinic(medicalClinic, e -> {
							if(e.succeeded()) {
								sqlCommitMedicalClinic(siteRequest, f -> {
									if(f.succeeded()) {
										sqlCloseMedicalClinic(siteRequest, g -> {
											if(g.succeeded()) {
												refreshMedicalClinic(medicalClinic, h -> {
													if(h.succeeded()) {
														eventHandler.handle(Future.succeededFuture(medicalClinic));
														promise.complete(medicalClinic);
													} else {
														LOGGER.error(String.format("refreshMedicalClinic failed. ", h.cause()));
														errorMedicalClinic(siteRequest, null, h);
													}
												});
											} else {
												LOGGER.error(String.format("defineIndexMedicalClinic failed. ", g.cause()));
												errorMedicalClinic(siteRequest, null, g);
											}
										});
									} else {
										LOGGER.error(String.format("defineIndexMedicalClinic failed. ", f.cause()));
										errorMedicalClinic(siteRequest, null, f);
									}
								});
							} else {
								LOGGER.error(String.format("defineIndexMedicalClinic failed. ", e.cause()));
								errorMedicalClinic(siteRequest, null, e);
							}
						});
					} else {
						LOGGER.error(String.format("defineIndexMedicalClinic failed. ", d.cause()));
						errorMedicalClinic(siteRequest, null, d);
					}
				});
			} else {
				LOGGER.error(String.format("defineIndexMedicalClinic failed. ", c.cause()));
				errorMedicalClinic(siteRequest, null, c);
			}
		});
		return promise.future();
	}

	public void createMedicalClinic(SiteRequestEnUS siteRequest, Handler<AsyncResult<MedicalClinic>> eventHandler) {
		try {
			Transaction tx = siteRequest.getTx();
			String userId = siteRequest.getUserId();
			ZonedDateTime created = Optional.ofNullable(siteRequest.getJsonObject()).map(j -> j.getString("created")).map(s -> ZonedDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of(siteRequest.getSiteConfig_().getSiteZone())))).orElse(ZonedDateTime.now(ZoneId.of(siteRequest.getSiteConfig_().getSiteZone())));

			tx.preparedQuery(
					SiteContextEnUS.SQL_create
					, Tuple.of(MedicalClinic.class.getCanonicalName(), userId, created.toOffsetDateTime())
					, Collectors.toList()
					, createAsync
			-> {
				if(createAsync.succeeded()) {
					Row createLine = createAsync.result().value().stream().findFirst().orElseGet(() -> null);
					Long pk = createLine.getLong(0);
					MedicalClinic o = new MedicalClinic();
					o.setPk(pk);
					o.setSiteRequest_(siteRequest);
					eventHandler.handle(Future.succeededFuture(o));
				} else {
					LOGGER.error(String.format("createMedicalClinic failed. ", createAsync.cause()));
					eventHandler.handle(Future.failedFuture(createAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("createMedicalClinic failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void errorMedicalClinic(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler, AsyncResult<?> resultAsync) {
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
		sqlRollbackMedicalClinic(siteRequest, a -> {
			if(a.succeeded()) {
				LOGGER.info(String.format("sql rollback. "));
				sqlCloseMedicalClinic(siteRequest, b -> {
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

	public void sqlConnectionMedicalClinic(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
						LOGGER.error(String.format("sqlConnectionMedicalClinic failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlMedicalClinic failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlTransactionMedicalClinic(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
			LOGGER.error(String.format("sqlTransactionMedicalClinic failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlCommitMedicalClinic(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
						LOGGER.error(String.format("sqlCommitMedicalClinic failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlMedicalClinic failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlRollbackMedicalClinic(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
						LOGGER.error(String.format("sqlRollbackMedicalClinic failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlMedicalClinic failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlCloseMedicalClinic(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
			LOGGER.error(String.format("sqlCloseMedicalClinic failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public SiteRequestEnUS generateSiteRequestEnUSForMedicalClinic(SiteContextEnUS siteContext, OperationRequest operationRequest) {
		return generateSiteRequestEnUSForMedicalClinic(siteContext, operationRequest, null);
	}

	public SiteRequestEnUS generateSiteRequestEnUSForMedicalClinic(SiteContextEnUS siteContext, OperationRequest operationRequest, JsonObject body) {
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

	public void userMedicalClinic(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			String userId = siteRequest.getUserId();
			if(userId == null) {
				eventHandler.handle(Future.succeededFuture());
			} else {
				sqlConnectionMedicalClinic(siteRequest, a -> {
					if(a.succeeded()) {
						sqlTransactionMedicalClinic(siteRequest, b -> {
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
												userMedicalClinicDefine(siteRequest, jsonObject, false);

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
																		errorMedicalClinic(siteRequest, eventHandler, e);
																	}
																});
															} else {
																errorMedicalClinic(siteRequest, eventHandler, d);
															}
														});
													} else {
														errorMedicalClinic(siteRequest, eventHandler, c);
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
												Boolean define = userMedicalClinicDefine(siteRequest, jsonObject, true);
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
																	errorMedicalClinic(siteRequest, eventHandler, e);
																}
															});
														} else {
															errorMedicalClinic(siteRequest, eventHandler, d);
														}
													});
												} else {
													siteRequest.setSiteUser(siteUser1);
													siteRequest.setUserName(siteUser1.getUserName());
													siteRequest.setUserFirstName(siteUser1.getUserFirstName());
													siteRequest.setUserLastName(siteUser1.getUserLastName());
													siteRequest.setUserId(siteUser1.getUserId());
													siteRequest.setUserKey(siteUser1.getPk());
													sqlRollbackMedicalClinic(siteRequest, c -> {
														if(c.succeeded()) {
															eventHandler.handle(Future.succeededFuture());
														} else {
															eventHandler.handle(Future.failedFuture(c.cause()));
															errorMedicalClinic(siteRequest, eventHandler, c);
														}
													});
												}
											}
										} catch(Exception e) {
											LOGGER.error(String.format("userMedicalClinic failed. ", e));
											eventHandler.handle(Future.failedFuture(e));
										}
									} else {
										LOGGER.error(String.format("userMedicalClinic failed. ", selectCAsync.cause()));
										eventHandler.handle(Future.failedFuture(selectCAsync.cause()));
									}
								});
							} else {
								LOGGER.error(String.format("userMedicalClinic failed. ", b.cause()));
								eventHandler.handle(Future.failedFuture(b.cause()));
							}
						});
					} else {
						LOGGER.error(String.format("userMedicalClinic failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("userMedicalClinic failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public Boolean userMedicalClinicDefine(SiteRequestEnUS siteRequest, JsonObject jsonObject, Boolean patch) {
		if(patch) {
			return jsonObject.getString("setCustomerProfileId") == null;
		} else {
			return jsonObject.getString("customerProfileId") == null;
		}
	}

	public void aSearchMedicalClinicQ(String uri, String apiMethod, SearchList<MedicalClinic> searchList, String entityVar, String valueIndexed, String varIndexed) {
		searchList.setQuery(varIndexed + ":" + ("*".equals(valueIndexed) ? valueIndexed : ClientUtils.escapeQueryChars(valueIndexed)));
		if(!"*".equals(entityVar)) {
			searchList.setHighlight(true);
			searchList.setHighlightSnippets(3);
			searchList.addHighlightField(varIndexed);
			searchList.setParam("hl.encoder", "html");
		}
	}

	public void aSearchMedicalClinicFq(String uri, String apiMethod, SearchList<MedicalClinic> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		searchList.addFilterQuery(varIndexed + ":" + ClientUtils.escapeQueryChars(valueIndexed));
	}

	public void aSearchMedicalClinicSort(String uri, String apiMethod, SearchList<MedicalClinic> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		searchList.addSort(varIndexed, ORDER.valueOf(valueIndexed));
	}

	public void aSearchMedicalClinicRows(String uri, String apiMethod, SearchList<MedicalClinic> searchList, Integer valueRows) {
			searchList.setRows(apiMethod != null && apiMethod.contains("Search") ? valueRows : 10);
	}

	public void aSearchMedicalClinicStart(String uri, String apiMethod, SearchList<MedicalClinic> searchList, Integer valueStart) {
		searchList.setStart(valueStart);
	}

	public void aSearchMedicalClinicVar(String uri, String apiMethod, SearchList<MedicalClinic> searchList, String var, String value) {
		searchList.getSiteRequest_().getRequestVars().put(var, value);
	}

	public void aSearchMedicalClinicUri(String uri, String apiMethod, SearchList<MedicalClinic> searchList) {
	}

	public void varsMedicalClinic(SiteRequestEnUS siteRequest, Handler<AsyncResult<SearchList<OperationResponse>>> eventHandler) {
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
					LOGGER.error(String.format("aSearchMedicalClinic failed. ", e));
					eventHandler.handle(Future.failedFuture(e));
				}
			});
			eventHandler.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOGGER.error(String.format("aSearchMedicalClinic failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void aSearchMedicalClinic(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, String uri, String apiMethod, Handler<AsyncResult<SearchList<MedicalClinic>>> eventHandler) {
		try {
			OperationRequest operationRequest = siteRequest.getOperationRequest();
			String entityListStr = siteRequest.getOperationRequest().getParams().getJsonObject("query").getString("fl");
			String[] entityList = entityListStr == null ? null : entityListStr.split(",\\s*");
			SearchList<MedicalClinic> searchList = new SearchList<MedicalClinic>();
			searchList.setPopulate(populate);
			searchList.setStore(store);
			searchList.setQuery("*:*");
			searchList.setC(MedicalClinic.class);
			searchList.setSiteRequest_(siteRequest);
			if(entityList != null)
				searchList.addFields(entityList);
			searchList.add("json.facet", "{max_modified:'max(modified_indexed_date)'}");

			String id = operationRequest.getParams().getJsonObject("path").getString("id");
			if(id != null) {
				searchList.addFilterQuery("(id:" + ClientUtils.escapeQueryChars(id) + " OR objectId_indexed_string:" + ClientUtils.escapeQueryChars(id) + ")");
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
								varIndexed = "*".equals(entityVar) ? entityVar : MedicalClinic.varSearchMedicalClinic(entityVar);
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								valueIndexed = StringUtils.isEmpty(valueIndexed) ? "*" : valueIndexed;
								aSearchMedicalClinicQ(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "fq":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								varIndexed = MedicalClinic.varIndexedMedicalClinic(entityVar);
								aSearchMedicalClinicFq(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "sort":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, " "));
								valueIndexed = StringUtils.trim(StringUtils.substringAfter((String)paramObject, " "));
								varIndexed = MedicalClinic.varIndexedMedicalClinic(entityVar);
								aSearchMedicalClinicSort(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "start":
								valueStart = (Integer)paramObject;
								aSearchMedicalClinicStart(uri, apiMethod, searchList, valueStart);
								break;
							case "rows":
								valueRows = (Integer)paramObject;
								aSearchMedicalClinicRows(uri, apiMethod, searchList, valueRows);
								break;
							case "var":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								aSearchMedicalClinicVar(uri, apiMethod, searchList, entityVar, valueIndexed);
								break;
						}
					}
					aSearchMedicalClinicUri(uri, apiMethod, searchList);
				} catch(Exception e) {
					LOGGER.error(String.format("aSearchMedicalClinic failed. ", e));
					eventHandler.handle(Future.failedFuture(e));
				}
			});
			if(searchList.getSorts().size() == 0) {
				searchList.addSort("created_indexed_date", ORDER.desc);
			}
			searchList.initDeepForClass(siteRequest);
			eventHandler.handle(Future.succeededFuture(searchList));
		} catch(Exception e) {
			LOGGER.error(String.format("aSearchMedicalClinic failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void defineMedicalClinic(MedicalClinic o, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
						LOGGER.error(String.format("defineMedicalClinic failed. ", e));
						eventHandler.handle(Future.failedFuture(e));
					}
				} else {
					LOGGER.error(String.format("defineMedicalClinic failed. ", defineAsync.cause()));
					eventHandler.handle(Future.failedFuture(defineAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("defineMedicalClinic failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void attributeMedicalClinic(MedicalClinic o, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
						LOGGER.error(String.format("attributeMedicalClinic failed. ", attributeAsync.cause()));
						eventHandler.handle(Future.failedFuture(attributeAsync.cause()));
					}
				} catch(Exception e) {
					LOGGER.error(String.format("attributeMedicalClinic failed. ", e));
					eventHandler.handle(Future.failedFuture(e));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("attributeMedicalClinic failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void indexMedicalClinic(MedicalClinic o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			o.initDeepForClass(siteRequest);
			o.indexForClass();
			eventHandler.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOGGER.error(String.format("indexMedicalClinic failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void refreshMedicalClinic(MedicalClinic o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Boolean refresh = !"false".equals(siteRequest.getRequestVars().get("refresh"));
			if(refresh && BooleanUtils.isFalse(Optional.ofNullable(siteRequest.getApiRequest_()).map(ApiRequest::getEmpty).orElse(true))) {
				SearchList<MedicalClinic> searchList = new SearchList<MedicalClinic>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(MedicalClinic.class);
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
							SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForMedicalClinic(siteContext, siteRequest.getOperationRequest(), new JsonObject());
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
						MedicalClinicEnUSApiServiceImpl service = new MedicalClinicEnUSApiServiceImpl(siteRequest.getSiteContext_());
						List<Future> futures2 = new ArrayList<>();
						for(MedicalClinic o2 : searchList.getList()) {
							SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForMedicalClinic(siteContext, siteRequest.getOperationRequest(), new JsonObject());
							o2.setSiteRequest_(siteRequest2);
							futures2.add(
								service.patchMedicalClinicFuture(o2, false, b -> {
									if(b.succeeded()) {
									} else {
										LOGGER.info(String.format("MedicalClinic %s failed. ", o2.getPk()));
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
								errorMedicalClinic(siteRequest, eventHandler, b);
							}
						});
					} else {
						LOGGER.error("Refresh relations failed. ", a.cause());
						errorMedicalClinic(siteRequest, eventHandler, a);
					}
				});
			} else {
				eventHandler.handle(Future.succeededFuture());
			}
		} catch(Exception e) {
			LOGGER.error(String.format("refreshMedicalClinic failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}
}
