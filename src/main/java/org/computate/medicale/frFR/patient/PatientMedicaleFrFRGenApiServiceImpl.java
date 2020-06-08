package org.computate.medicale.frFR.patient;

import org.computate.medicale.frFR.inscription.InscriptionMedicaleFrFRGenApiServiceImpl;
import org.computate.medicale.frFR.inscription.InscriptionMedicale;
import org.computate.medicale.frFR.config.ConfigSite;
import org.computate.medicale.frFR.requete.RequeteSiteFrFR;
import org.computate.medicale.frFR.contexte.SiteContexteFrFR;
import org.computate.medicale.frFR.utilisateur.UtilisateurSite;
import org.computate.medicale.frFR.requete.api.RequeteApi;
import org.computate.medicale.frFR.recherche.ResultatRecherche;
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
import org.computate.medicale.frFR.utilisateur.UtilisateurSiteFrFRApiServiceImpl;
import org.computate.medicale.frFR.recherche.ListeRecherche;
import org.computate.medicale.frFR.ecrivain.ToutEcrivain;


/**
 * Traduire: false
 * NomCanonique.enUS: org.computate.medicale.enUS.patient.MedicalPatientEnUSGenApiServiceImpl
 **/
public class PatientMedicaleFrFRGenApiServiceImpl implements PatientMedicaleFrFRGenApiService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(PatientMedicaleFrFRGenApiServiceImpl.class);

	protected static final String SERVICE_ADDRESS = "PatientMedicaleFrFRApiServiceImpl";

	protected SiteContexteFrFR siteContexte;

	public PatientMedicaleFrFRGenApiServiceImpl(SiteContexteFrFR siteContexte) {
		this.siteContexte = siteContexte;
	}

	// POST //

	@Override
	public void postPatientMedicale(JsonObject body, OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = genererRequeteSiteFrFRPourPatientMedicale(siteContexte, operationRequete, body);
		try {
			LOGGER.info(String.format("postPatientMedicale a démarré. "));
			utilisateurPatientMedicale(requeteSite, b -> {
				if(b.succeeded()) {
					RequeteApi requeteApi = new RequeteApi();
					requeteApi.setRows(1);
					requeteApi.setNumFound(1L);
					requeteApi.setNumPATCH(0L);
					requeteApi.initLoinRequeteApi(requeteSite);
					requeteSite.setRequeteApi_(requeteApi);
					requeteSite.getVertx().eventBus().publish("websocketPatientMedicale", JsonObject.mapFrom(requeteApi).toString());
					postPatientMedicaleFuture(requeteSite, false, c -> {
						if(c.succeeded()) {
							PatientMedicale patientMedicale = c.result();
							requeteApi.setPk(patientMedicale.getPk());
							postPatientMedicaleReponse(patientMedicale, d -> {
									if(d.succeeded()) {
									gestionnaireEvenements.handle(Future.succeededFuture(d.result()));
									LOGGER.info(String.format("postPatientMedicale a réussi. "));
								} else {
									LOGGER.error(String.format("postPatientMedicale a échoué. ", d.cause()));
									erreurPatientMedicale(requeteSite, gestionnaireEvenements, d);
								}
							});
						} else {
							LOGGER.error(String.format("postPatientMedicale a échoué. ", c.cause()));
							erreurPatientMedicale(requeteSite, gestionnaireEvenements, c);
						}
					});
				} else {
					LOGGER.error(String.format("postPatientMedicale a échoué. ", b.cause()));
					erreurPatientMedicale(requeteSite, gestionnaireEvenements, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("postPatientMedicale a échoué. ", ex));
			erreurPatientMedicale(requeteSite, gestionnaireEvenements, Future.failedFuture(ex));
		}
	}


	public Future<PatientMedicale> postPatientMedicaleFuture(RequeteSiteFrFR requeteSite, Boolean inheritPk, Handler<AsyncResult<PatientMedicale>> gestionnaireEvenements) {
		Promise<PatientMedicale> promise = Promise.promise();
		try {
			sqlConnexionPatientMedicale(requeteSite, a -> {
				if(a.succeeded()) {
					sqlTransactionPatientMedicale(requeteSite, b -> {
						if(b.succeeded()) {
							creerPatientMedicale(requeteSite, c -> {
								if(c.succeeded()) {
									PatientMedicale patientMedicale = c.result();
									sqlPOSTPatientMedicale(patientMedicale, inheritPk, d -> {
										if(d.succeeded()) {
											definirIndexerPatientMedicale(patientMedicale, e -> {
												if(e.succeeded()) {
													RequeteApi requeteApi = requeteSite.getRequeteApi_();
													if(requeteApi != null) {
														requeteApi.setNumPATCH(requeteApi.getNumPATCH() + 1);
														patientMedicale.requeteApiPatientMedicale();
														requeteSite.getVertx().eventBus().publish("websocketPatientMedicale", JsonObject.mapFrom(requeteApi).toString());
													}
													gestionnaireEvenements.handle(Future.succeededFuture(patientMedicale));
													promise.complete(patientMedicale);
												} else {
													LOGGER.error(String.format("postPatientMedicaleFuture a échoué. ", e.cause()));
													gestionnaireEvenements.handle(Future.failedFuture(e.cause()));
												}
											});
										} else {
											LOGGER.error(String.format("postPatientMedicaleFuture a échoué. ", d.cause()));
											gestionnaireEvenements.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOGGER.error(String.format("postPatientMedicaleFuture a échoué. ", c.cause()));
									gestionnaireEvenements.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOGGER.error(String.format("postPatientMedicaleFuture a échoué. ", b.cause()));
							gestionnaireEvenements.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOGGER.error(String.format("postPatientMedicaleFuture a échoué. ", a.cause()));
					gestionnaireEvenements.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("postPatientMedicaleFuture a échoué. ", e));
			erreurPatientMedicale(requeteSite, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPOSTPatientMedicale(PatientMedicale o, Boolean inheritPk, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = o.getRequeteSite_();
			RequeteApi requeteApi = requeteSite.getRequeteApi_();
			List<Long> pks = Optional.ofNullable(requeteApi).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(requeteApi).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Transaction tx = requeteSite.getTx();
			Long pk = o.getPk();
			JsonObject jsonObject = requeteSite.getObjetJson();
			List<Future> futures = new ArrayList<>();

			if(requeteSite.getSessionId() != null) {
				futures.add(Future.future(a -> {
					tx.preparedQuery(SiteContexteFrFR.SQL_setD
				, Tuple.of(pk, "sessionId", requeteSite.getSessionId())
							, b
					-> {
						if(b.succeeded())
							a.handle(Future.succeededFuture());
						else
							a.handle(Future.failedFuture(b.cause()));
					});
				}));
			}
			if(requeteSite.getUtilisateurId() != null) {
				futures.add(Future.future(a -> {
					tx.preparedQuery(SiteContexteFrFR.SQL_setD
				, Tuple.of(pk, "utilisateurId", requeteSite.getUtilisateurId())
							, b
					-> {
						if(b.succeeded())
							a.handle(Future.succeededFuture());
						else
							a.handle(Future.failedFuture(b.cause()));
					});
				}));
			}
			if(requeteSite.getUtilisateurCle() != null) {
				futures.add(Future.future(a -> {
					tx.preparedQuery(SiteContexteFrFR.SQL_setD
				, Tuple.of(pk, "utilisateurCle", requeteSite.getUtilisateurCle().toString())
							, b
					-> {
						if(b.succeeded())
							a.handle(Future.succeededFuture());
						else
							a.handle(Future.failedFuture(b.cause()));
					});
				}));

				JsonArray utilisateurCles = Optional.ofNullable(jsonObject.getJsonArray("utilisateurCles")).orElse(null);
				if(utilisateurCles != null && !utilisateurCles.contains(requeteSite.getUtilisateurCle()))
					utilisateurCles.add(requeteSite.getUtilisateurCle().toString());
				else
					jsonObject.put("utilisateurCles", new JsonArray().add(requeteSite.getUtilisateurCle().toString()));
			}

			if(jsonObject != null) {
				Set<String> entiteVars = jsonObject.fieldNames();
				for(String entiteVar : entiteVars) {
					switch(entiteVar) {
					case "inheritPk":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContexteFrFR.SQL_setD
									, Tuple.of(pk, "inheritPk", Optional.ofNullable(jsonObject.getValue(entiteVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("valeur PatientMedicale.inheritPk a échoué", b.cause())));
							});
						}));
						break;
					case "archive":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContexteFrFR.SQL_setD
									, Tuple.of(pk, "archive", Optional.ofNullable(jsonObject.getValue(entiteVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("valeur PatientMedicale.archive a échoué", b.cause())));
							});
						}));
						break;
					case "supprime":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContexteFrFR.SQL_setD
									, Tuple.of(pk, "supprime", Optional.ofNullable(jsonObject.getValue(entiteVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("valeur PatientMedicale.supprime a échoué", b.cause())));
							});
						}));
						break;
					case "inscriptionCles":
						for(Long l : jsonObject.getJsonArray(entiteVar).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
							if(l != null) {
								ListeRecherche<InscriptionMedicale> listeRecherche = new ListeRecherche<InscriptionMedicale>();
								listeRecherche.setQuery("*:*");
								listeRecherche.setStocker(true);
								listeRecherche.setC(InscriptionMedicale.class);
								listeRecherche.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								listeRecherche.initLoinListeRecherche(requeteSite);
								Long l2 = Optional.ofNullable(listeRecherche.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContexteFrFR.SQL_addA
												, Tuple.of(pk, "inscriptionCles", l2, "patientCle")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("valeur PatientMedicale.inscriptionCles a échoué", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("InscriptionMedicale");
									}
								}
							}
						}
						break;
					case "patientPrenom":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContexteFrFR.SQL_setD
									, Tuple.of(pk, "patientPrenom", Optional.ofNullable(jsonObject.getValue(entiteVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("valeur PatientMedicale.patientPrenom a échoué", b.cause())));
							});
						}));
						break;
					case "patientPrenomPrefere":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContexteFrFR.SQL_setD
									, Tuple.of(pk, "patientPrenomPrefere", Optional.ofNullable(jsonObject.getValue(entiteVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("valeur PatientMedicale.patientPrenomPrefere a échoué", b.cause())));
							});
						}));
						break;
					case "familleNom":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContexteFrFR.SQL_setD
									, Tuple.of(pk, "familleNom", Optional.ofNullable(jsonObject.getValue(entiteVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("valeur PatientMedicale.familleNom a échoué", b.cause())));
							});
						}));
						break;
					case "patientDateNaissance":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContexteFrFR.SQL_setD
									, Tuple.of(pk, "patientDateNaissance", Optional.ofNullable(jsonObject.getValue(entiteVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("valeur PatientMedicale.patientDateNaissance a échoué", b.cause())));
							});
						}));
						break;
					}
				}
			}
			CompositeFuture.all(futures).setHandler( a -> {
				if(a.succeeded()) {
					gestionnaireEvenements.handle(Future.succeededFuture());
				} else {
					LOGGER.error(String.format("sqlPOSTPatientMedicale a échoué. ", a.cause()));
					gestionnaireEvenements.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPOSTPatientMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void postPatientMedicaleReponse(PatientMedicale patientMedicale, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = patientMedicale.getRequeteSite_();
		try {
			reponse200POSTPatientMedicale(patientMedicale, a -> {
				if(a.succeeded()) {
					gestionnaireEvenements.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("postPatientMedicaleReponse a échoué. ", a.cause()));
					erreurPatientMedicale(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("postPatientMedicaleReponse a échoué. ", ex));
			erreurPatientMedicale(requeteSite, null, Future.failedFuture(ex));
		}
	}
	public void reponse200POSTPatientMedicale(PatientMedicale o, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = o.getRequeteSite_();
			JsonObject json = JsonObject.mapFrom(o);
			gestionnaireEvenements.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("reponse200POSTPatientMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	// PUTImport //

	@Override
	public void putimportPatientMedicale(JsonObject body, OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = genererRequeteSiteFrFRPourPatientMedicale(siteContexte, operationRequete, body);
		try {
			LOGGER.info(String.format("putimportPatientMedicale a démarré. "));
			utilisateurPatientMedicale(requeteSite, b -> {
				if(b.succeeded()) {
					putimportPatientMedicaleReponse(requeteSite, c -> {
						if(c.succeeded()) {
							gestionnaireEvenements.handle(Future.succeededFuture(c.result()));
							WorkerExecutor executeurTravailleur = siteContexte.getExecuteurTravailleur();
							executeurTravailleur.executeBlocking(
								blockingCodeHandler -> {
									try {
										RequeteApi requeteApi = new RequeteApi();
										JsonArray jsonArray = Optional.ofNullable(requeteSite.getObjetJson()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
										requeteApi.setRows(jsonArray.size());
										requeteApi.setNumFound(new Integer(jsonArray.size()).longValue());
										requeteApi.setNumPATCH(0L);
										requeteApi.initLoinRequeteApi(requeteSite);
										requeteSite.setRequeteApi_(requeteApi);
										requeteSite.getVertx().eventBus().publish("websocketPatientMedicale", JsonObject.mapFrom(requeteApi).toString());
										varsPatientMedicale(requeteSite, d -> {
											if(d.succeeded()) {
												listePUTImportPatientMedicale(requeteApi, requeteSite, e -> {
													if(e.succeeded()) {
														putimportPatientMedicaleReponse(requeteSite, f -> {
															if(e.succeeded()) {
																LOGGER.info(String.format("putimportPatientMedicale a réussi. "));
																blockingCodeHandler.handle(Future.succeededFuture(e.result()));
															} else {
																LOGGER.error(String.format("putimportPatientMedicale a échoué. ", f.cause()));
																erreurPatientMedicale(requeteSite, null, f);
															}
														});
													} else {
														LOGGER.error(String.format("putimportPatientMedicale a échoué. ", e.cause()));
														erreurPatientMedicale(requeteSite, null, e);
													}
												});
											} else {
												LOGGER.error(String.format("putimportPatientMedicale a échoué. ", d.cause()));
												erreurPatientMedicale(requeteSite, null, d);
											}
										});
									} catch(Exception ex) {
										LOGGER.error(String.format("putimportPatientMedicale a échoué. ", ex));
										erreurPatientMedicale(requeteSite, null, Future.failedFuture(ex));
									}
								}, resultHandler -> {
								}
							);
						} else {
							LOGGER.error(String.format("putimportPatientMedicale a échoué. ", c.cause()));
							erreurPatientMedicale(requeteSite, gestionnaireEvenements, c);
						}
					});
				} else {
					LOGGER.error(String.format("putimportPatientMedicale a échoué. ", b.cause()));
					erreurPatientMedicale(requeteSite, gestionnaireEvenements, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putimportPatientMedicale a échoué. ", ex));
			erreurPatientMedicale(requeteSite, gestionnaireEvenements, Future.failedFuture(ex));
		}
	}


	public void listePUTImportPatientMedicale(RequeteApi requeteApi, RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		List<Future> futures = new ArrayList<>();
		JsonArray jsonArray = Optional.ofNullable(requeteSite.getObjetJson()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
		try {
			jsonArray.forEach(obj -> {
				JsonObject json = (JsonObject)obj;

				json.put("inheritPk", json.getValue("pk"));

				json.put("cree", json.getValue("cree"));

				RequeteSiteFrFR requeteSite2 = genererRequeteSiteFrFRPourPatientMedicale(siteContexte, requeteSite.getOperationRequete(), json);
				requeteSite2.setRequeteApi_(requeteApi);
				requeteSite2.setRequeteVars(requeteSite.getRequeteVars());

				ListeRecherche<PatientMedicale> listeRecherche = new ListeRecherche<PatientMedicale>();
				listeRecherche.setStocker(true);
				listeRecherche.setQuery("*:*");
				listeRecherche.setC(PatientMedicale.class);
				listeRecherche.addFilterQuery("inheritPk_indexed_long:" + json.getString("pk"));
				listeRecherche.initLoinPourClasse(requeteSite2);

				if(listeRecherche.size() == 1) {
					PatientMedicale o = listeRecherche.getList().stream().findFirst().orElse(null);
					JsonObject json2 = new JsonObject();
					for(String f : json.fieldNames()) {
						json2.put("set" + StringUtils.capitalize(f), json.getValue(f));
					}
					if(o != null) {
						for(String f : Optional.ofNullable(o.getSauvegardes()).orElse(new ArrayList<>())) {
							if(!json.fieldNames().contains(f))
								json2.putNull("set" + StringUtils.capitalize(f));
						}
						requeteSite2.setObjetJson(json2);
						futures.add(
							patchPatientMedicaleFuture(o, true, a -> {
								if(a.succeeded()) {
								} else {
									LOGGER.error(String.format("listePUTImportPatientMedicale a échoué. ", a.cause()));
									erreurPatientMedicale(requeteSite2, gestionnaireEvenements, a);
								}
							})
						);
					}
				} else {
					futures.add(
						postPatientMedicaleFuture(requeteSite2, true, a -> {
							if(a.succeeded()) {
							} else {
								LOGGER.error(String.format("listePUTImportPatientMedicale a échoué. ", a.cause()));
								erreurPatientMedicale(requeteSite2, gestionnaireEvenements, a);
							}
						})
					);
				}
			});
			CompositeFuture.all(futures).setHandler( a -> {
				if(a.succeeded()) {
					requeteApi.setNumPATCH(requeteApi.getNumPATCH() + 1);
					reponse200PUTImportPatientMedicale(requeteSite, gestionnaireEvenements);
				} else {
					LOGGER.error(String.format("listePUTImportPatientMedicale a échoué. ", a.cause()));
					erreurPatientMedicale(requeteApi.getRequeteSite_(), gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("listePUTImportPatientMedicale a échoué. ", ex));
			erreurPatientMedicale(requeteSite, null, Future.failedFuture(ex));
		}
	}

	public void putimportPatientMedicaleReponse(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			reponse200PUTImportPatientMedicale(requeteSite, a -> {
				if(a.succeeded()) {
					gestionnaireEvenements.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("putimportPatientMedicaleReponse a échoué. ", a.cause()));
					erreurPatientMedicale(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putimportPatientMedicaleReponse a échoué. ", ex));
			erreurPatientMedicale(requeteSite, null, Future.failedFuture(ex));
		}
	}
	public void reponse200PUTImportPatientMedicale(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			JsonObject json = new JsonObject();
			gestionnaireEvenements.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("reponse200PUTImportPatientMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	// PUTFusion //

	@Override
	public void putfusionPatientMedicale(JsonObject body, OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = genererRequeteSiteFrFRPourPatientMedicale(siteContexte, operationRequete, body);
		try {
			LOGGER.info(String.format("putfusionPatientMedicale a démarré. "));
			utilisateurPatientMedicale(requeteSite, b -> {
				if(b.succeeded()) {
					putfusionPatientMedicaleReponse(requeteSite, c -> {
						if(c.succeeded()) {
							gestionnaireEvenements.handle(Future.succeededFuture(c.result()));
							WorkerExecutor executeurTravailleur = siteContexte.getExecuteurTravailleur();
							executeurTravailleur.executeBlocking(
								blockingCodeHandler -> {
									try {
										RequeteApi requeteApi = new RequeteApi();
										JsonArray jsonArray = Optional.ofNullable(requeteSite.getObjetJson()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
										requeteApi.setRows(jsonArray.size());
										requeteApi.setNumFound(new Integer(jsonArray.size()).longValue());
										requeteApi.setNumPATCH(0L);
										requeteApi.initLoinRequeteApi(requeteSite);
										requeteSite.setRequeteApi_(requeteApi);
										requeteSite.getVertx().eventBus().publish("websocketPatientMedicale", JsonObject.mapFrom(requeteApi).toString());
										varsPatientMedicale(requeteSite, d -> {
											if(d.succeeded()) {
												listePUTFusionPatientMedicale(requeteApi, requeteSite, e -> {
													if(e.succeeded()) {
														putfusionPatientMedicaleReponse(requeteSite, f -> {
															if(e.succeeded()) {
																LOGGER.info(String.format("putfusionPatientMedicale a réussi. "));
																blockingCodeHandler.handle(Future.succeededFuture(e.result()));
															} else {
																LOGGER.error(String.format("putfusionPatientMedicale a échoué. ", f.cause()));
																erreurPatientMedicale(requeteSite, null, f);
															}
														});
													} else {
														LOGGER.error(String.format("putfusionPatientMedicale a échoué. ", e.cause()));
														erreurPatientMedicale(requeteSite, null, e);
													}
												});
											} else {
												LOGGER.error(String.format("putfusionPatientMedicale a échoué. ", d.cause()));
												erreurPatientMedicale(requeteSite, null, d);
											}
										});
									} catch(Exception ex) {
										LOGGER.error(String.format("putfusionPatientMedicale a échoué. ", ex));
										erreurPatientMedicale(requeteSite, null, Future.failedFuture(ex));
									}
								}, resultHandler -> {
								}
							);
						} else {
							LOGGER.error(String.format("putfusionPatientMedicale a échoué. ", c.cause()));
							erreurPatientMedicale(requeteSite, gestionnaireEvenements, c);
						}
					});
				} else {
					LOGGER.error(String.format("putfusionPatientMedicale a échoué. ", b.cause()));
					erreurPatientMedicale(requeteSite, gestionnaireEvenements, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putfusionPatientMedicale a échoué. ", ex));
			erreurPatientMedicale(requeteSite, gestionnaireEvenements, Future.failedFuture(ex));
		}
	}


	public void listePUTFusionPatientMedicale(RequeteApi requeteApi, RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		List<Future> futures = new ArrayList<>();
		JsonArray jsonArray = Optional.ofNullable(requeteSite.getObjetJson()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
		try {
			jsonArray.forEach(obj -> {
				JsonObject json = (JsonObject)obj;

				json.put("inheritPk", json.getValue("pk"));

				RequeteSiteFrFR requeteSite2 = genererRequeteSiteFrFRPourPatientMedicale(siteContexte, requeteSite.getOperationRequete(), json);
				requeteSite2.setRequeteApi_(requeteApi);
				requeteSite2.setRequeteVars(requeteSite.getRequeteVars());

				ListeRecherche<PatientMedicale> listeRecherche = new ListeRecherche<PatientMedicale>();
				listeRecherche.setStocker(true);
				listeRecherche.setQuery("*:*");
				listeRecherche.setC(PatientMedicale.class);
				listeRecherche.addFilterQuery("pk_indexed_long:" + json.getString("pk"));
				listeRecherche.initLoinPourClasse(requeteSite2);

				if(listeRecherche.size() == 1) {
					PatientMedicale o = listeRecherche.getList().stream().findFirst().orElse(null);
					JsonObject json2 = new JsonObject();
					for(String f : json.fieldNames()) {
						json2.put("set" + StringUtils.capitalize(f), json.getValue(f));
					}
					if(o != null) {
						for(String f : Optional.ofNullable(o.getSauvegardes()).orElse(new ArrayList<>())) {
							if(!json.fieldNames().contains(f))
								json2.putNull("set" + StringUtils.capitalize(f));
						}
						requeteSite2.setObjetJson(json2);
						futures.add(
							patchPatientMedicaleFuture(o, false, a -> {
								if(a.succeeded()) {
								} else {
									LOGGER.error(String.format("listePUTFusionPatientMedicale a échoué. ", a.cause()));
									erreurPatientMedicale(requeteSite2, gestionnaireEvenements, a);
								}
							})
						);
					}
				} else {
					futures.add(
						postPatientMedicaleFuture(requeteSite2, false, a -> {
							if(a.succeeded()) {
							} else {
								LOGGER.error(String.format("listePUTFusionPatientMedicale a échoué. ", a.cause()));
								erreurPatientMedicale(requeteSite2, gestionnaireEvenements, a);
							}
						})
					);
				}
			});
			CompositeFuture.all(futures).setHandler( a -> {
				if(a.succeeded()) {
					requeteApi.setNumPATCH(requeteApi.getNumPATCH() + 1);
					reponse200PUTFusionPatientMedicale(requeteSite, gestionnaireEvenements);
				} else {
					LOGGER.error(String.format("listePUTFusionPatientMedicale a échoué. ", a.cause()));
					erreurPatientMedicale(requeteApi.getRequeteSite_(), gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("listePUTFusionPatientMedicale a échoué. ", ex));
			erreurPatientMedicale(requeteSite, null, Future.failedFuture(ex));
		}
	}

	public void putfusionPatientMedicaleReponse(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			reponse200PUTFusionPatientMedicale(requeteSite, a -> {
				if(a.succeeded()) {
					gestionnaireEvenements.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("putfusionPatientMedicaleReponse a échoué. ", a.cause()));
					erreurPatientMedicale(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putfusionPatientMedicaleReponse a échoué. ", ex));
			erreurPatientMedicale(requeteSite, null, Future.failedFuture(ex));
		}
	}
	public void reponse200PUTFusionPatientMedicale(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			JsonObject json = new JsonObject();
			gestionnaireEvenements.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("reponse200PUTFusionPatientMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	// PUTCopie //

	@Override
	public void putcopiePatientMedicale(JsonObject body, OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = genererRequeteSiteFrFRPourPatientMedicale(siteContexte, operationRequete, body);
		try {
			LOGGER.info(String.format("putcopiePatientMedicale a démarré. "));
			utilisateurPatientMedicale(requeteSite, b -> {
				if(b.succeeded()) {
					putcopiePatientMedicaleReponse(requeteSite, c -> {
						if(c.succeeded()) {
							gestionnaireEvenements.handle(Future.succeededFuture(c.result()));
							WorkerExecutor executeurTravailleur = siteContexte.getExecuteurTravailleur();
							executeurTravailleur.executeBlocking(
								blockingCodeHandler -> {
									try {
										recherchePatientMedicale(requeteSite, false, true, "/api/patient/copie", "PUTCopie", d -> {
											if(d.succeeded()) {
												ListeRecherche<PatientMedicale> listePatientMedicale = d.result();
												RequeteApi requeteApi = new RequeteApi();
												requeteApi.setRows(listePatientMedicale.getRows());
												requeteApi.setNumFound(listePatientMedicale.getQueryResponse().getResults().getNumFound());
												requeteApi.setNumPATCH(0L);
												requeteApi.initLoinRequeteApi(requeteSite);
												requeteSite.setRequeteApi_(requeteApi);
												requeteSite.getVertx().eventBus().publish("websocketPatientMedicale", JsonObject.mapFrom(requeteApi).toString());
												try {
													listePUTCopiePatientMedicale(requeteApi, listePatientMedicale, e -> {
														if(e.succeeded()) {
															putcopiePatientMedicaleReponse(requeteSite, f -> {
																if(f.succeeded()) {
																	LOGGER.info(String.format("putcopiePatientMedicale a réussi. "));
																	blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																} else {
																	LOGGER.error(String.format("putcopiePatientMedicale a échoué. ", f.cause()));
																	erreurPatientMedicale(requeteSite, null, f);
																}
															});
														} else {
															LOGGER.error(String.format("putcopiePatientMedicale a échoué. ", e.cause()));
															erreurPatientMedicale(requeteSite, null, e);
														}
													});
												} catch(Exception ex) {
													LOGGER.error(String.format("putcopiePatientMedicale a échoué. ", ex));
													erreurPatientMedicale(requeteSite, null, Future.failedFuture(ex));
												}
											} else {
												LOGGER.error(String.format("putcopiePatientMedicale a échoué. ", d.cause()));
												erreurPatientMedicale(requeteSite, null, d);
											}
										});
									} catch(Exception ex) {
										LOGGER.error(String.format("putcopiePatientMedicale a échoué. ", ex));
										erreurPatientMedicale(requeteSite, null, Future.failedFuture(ex));
									}
								}, resultHandler -> {
								}
							);
						} else {
							LOGGER.error(String.format("putcopiePatientMedicale a échoué. ", c.cause()));
							erreurPatientMedicale(requeteSite, gestionnaireEvenements, c);
						}
					});
				} else {
					LOGGER.error(String.format("putcopiePatientMedicale a échoué. ", b.cause()));
					erreurPatientMedicale(requeteSite, gestionnaireEvenements, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putcopiePatientMedicale a échoué. ", ex));
			erreurPatientMedicale(requeteSite, gestionnaireEvenements, Future.failedFuture(ex));
		}
	}


	public void listePUTCopiePatientMedicale(RequeteApi requeteApi, ListeRecherche<PatientMedicale> listePatientMedicale, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		List<Future> futures = new ArrayList<>();
		RequeteSiteFrFR requeteSite = listePatientMedicale.getRequeteSite_();
		listePatientMedicale.getList().forEach(o -> {
			RequeteSiteFrFR requeteSite2 = genererRequeteSiteFrFRPourPatientMedicale(siteContexte, requeteSite.getOperationRequete(), requeteSite.getObjetJson());
			requeteSite2.setRequeteApi_(requeteSite.getRequeteApi_());
			o.setRequeteSite_(requeteSite2);
			futures.add(
				putcopiePatientMedicaleFuture(requeteSite, JsonObject.mapFrom(o), a -> {
					if(a.succeeded()) {
					} else {
						LOGGER.error(String.format("listePUTCopiePatientMedicale a échoué. ", a.cause()));
						erreurPatientMedicale(requeteSite, gestionnaireEvenements, a);
					}
				})
			);
		});
		CompositeFuture.all(futures).setHandler( a -> {
			if(a.succeeded()) {
				requeteApi.setNumPATCH(requeteApi.getNumPATCH() + listePatientMedicale.size());
				if(listePatientMedicale.next()) {
					listePUTCopiePatientMedicale(requeteApi, listePatientMedicale, gestionnaireEvenements);
				} else {
					reponse200PUTCopiePatientMedicale(requeteSite, gestionnaireEvenements);
				}
			} else {
				LOGGER.error(String.format("listePUTCopiePatientMedicale a échoué. ", a.cause()));
				erreurPatientMedicale(listePatientMedicale.getRequeteSite_(), gestionnaireEvenements, a);
			}
		});
	}

	public Future<PatientMedicale> putcopiePatientMedicaleFuture(RequeteSiteFrFR requeteSite, JsonObject jsonObject, Handler<AsyncResult<PatientMedicale>> gestionnaireEvenements) {
		Promise<PatientMedicale> promise = Promise.promise();
		try {

			jsonObject.put("sauvegardes", Optional.ofNullable(jsonObject.getJsonArray("sauvegardes")).orElse(new JsonArray()));
			JsonObject jsonPatch = Optional.ofNullable(requeteSite.getObjetJson()).map(o -> o.getJsonObject("patch")).orElse(new JsonObject());
			jsonPatch.stream().forEach(o -> {
				jsonObject.put(o.getKey(), o.getValue());
				jsonObject.getJsonArray("sauvegardes").add(o.getKey());
			});

			sqlConnexionPatientMedicale(requeteSite, a -> {
				if(a.succeeded()) {
					sqlTransactionPatientMedicale(requeteSite, b -> {
						if(b.succeeded()) {
							creerPatientMedicale(requeteSite, c -> {
								if(c.succeeded()) {
									PatientMedicale patientMedicale = c.result();
									sqlPUTCopiePatientMedicale(patientMedicale, jsonObject, d -> {
										if(d.succeeded()) {
											definirIndexerPatientMedicale(patientMedicale, e -> {
												if(e.succeeded()) {
													RequeteApi requeteApi = requeteSite.getRequeteApi_();
													if(requeteApi != null) {
														requeteApi.setNumPATCH(requeteApi.getNumPATCH() + 1);
														if(requeteApi.getNumFound() == 1L) {
															patientMedicale.requeteApiPatientMedicale();
														}
														requeteSite.getVertx().eventBus().publish("websocketPatientMedicale", JsonObject.mapFrom(requeteApi).toString());
													}
													gestionnaireEvenements.handle(Future.succeededFuture(patientMedicale));
													promise.complete(patientMedicale);
												} else {
													LOGGER.error(String.format("putcopiePatientMedicaleFuture a échoué. ", e.cause()));
													gestionnaireEvenements.handle(Future.failedFuture(e.cause()));
												}
											});
										} else {
											LOGGER.error(String.format("putcopiePatientMedicaleFuture a échoué. ", d.cause()));
											gestionnaireEvenements.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOGGER.error(String.format("putcopiePatientMedicaleFuture a échoué. ", c.cause()));
									gestionnaireEvenements.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOGGER.error(String.format("putcopiePatientMedicaleFuture a échoué. ", b.cause()));
							gestionnaireEvenements.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOGGER.error(String.format("putcopiePatientMedicaleFuture a échoué. ", a.cause()));
					gestionnaireEvenements.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("putcopiePatientMedicaleFuture a échoué. ", e));
			erreurPatientMedicale(requeteSite, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPUTCopiePatientMedicale(PatientMedicale o, JsonObject jsonObject, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = o.getRequeteSite_();
			RequeteApi requeteApi = requeteSite.getRequeteApi_();
			List<Long> pks = Optional.ofNullable(requeteApi).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(requeteApi).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Transaction tx = requeteSite.getTx();
			Long pk = o.getPk();
			List<Future> futures = new ArrayList<>();

			if(jsonObject != null) {
				JsonArray entiteVars = jsonObject.getJsonArray("sauvegardes");
				for(Integer i = 0; i < entiteVars.size(); i++) {
					String entiteVar = entiteVars.getString(i);
					switch(entiteVar) {
					case "inheritPk":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContexteFrFR.SQL_setD
									, Tuple.of(pk, "inheritPk", Optional.ofNullable(jsonObject.getValue(entiteVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("valeur PatientMedicale.inheritPk a échoué", b.cause())));
							});
						}));
						break;
					case "archive":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContexteFrFR.SQL_setD
									, Tuple.of(pk, "archive", Optional.ofNullable(jsonObject.getValue(entiteVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("valeur PatientMedicale.archive a échoué", b.cause())));
							});
						}));
						break;
					case "supprime":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContexteFrFR.SQL_setD
									, Tuple.of(pk, "supprime", Optional.ofNullable(jsonObject.getValue(entiteVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("valeur PatientMedicale.supprime a échoué", b.cause())));
							});
						}));
						break;
					case "inscriptionCles":
						for(Long l : jsonObject.getJsonArray(entiteVar).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContexteFrFR.SQL_addA
										, Tuple.of(pk, "inscriptionCles", l, "patientCle")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("valeur PatientMedicale.inscriptionCles a échoué", b.cause())));
								});
							}));
							if(!pks.contains(l)) {
								pks.add(l);
								classes.add("InscriptionMedicale");
							}
						}
						break;
					case "patientPrenom":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContexteFrFR.SQL_setD
									, Tuple.of(pk, "patientPrenom", Optional.ofNullable(jsonObject.getValue(entiteVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("valeur PatientMedicale.patientPrenom a échoué", b.cause())));
							});
						}));
						break;
					case "patientPrenomPrefere":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContexteFrFR.SQL_setD
									, Tuple.of(pk, "patientPrenomPrefere", Optional.ofNullable(jsonObject.getValue(entiteVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("valeur PatientMedicale.patientPrenomPrefere a échoué", b.cause())));
							});
						}));
						break;
					case "familleNom":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContexteFrFR.SQL_setD
									, Tuple.of(pk, "familleNom", Optional.ofNullable(jsonObject.getValue(entiteVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("valeur PatientMedicale.familleNom a échoué", b.cause())));
							});
						}));
						break;
					case "patientDateNaissance":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContexteFrFR.SQL_setD
									, Tuple.of(pk, "patientDateNaissance", Optional.ofNullable(jsonObject.getValue(entiteVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("valeur PatientMedicale.patientDateNaissance a échoué", b.cause())));
							});
						}));
						break;
					}
				}
			}
			CompositeFuture.all(futures).setHandler( a -> {
				if(a.succeeded()) {
					gestionnaireEvenements.handle(Future.succeededFuture());
				} else {
					LOGGER.error(String.format("sqlPUTCopiePatientMedicale a échoué. ", a.cause()));
					gestionnaireEvenements.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPUTCopiePatientMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void putcopiePatientMedicaleReponse(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			reponse200PUTCopiePatientMedicale(requeteSite, a -> {
				if(a.succeeded()) {
					gestionnaireEvenements.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("putcopiePatientMedicaleReponse a échoué. ", a.cause()));
					erreurPatientMedicale(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putcopiePatientMedicaleReponse a échoué. ", ex));
			erreurPatientMedicale(requeteSite, null, Future.failedFuture(ex));
		}
	}
	public void reponse200PUTCopiePatientMedicale(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			JsonObject json = new JsonObject();
			gestionnaireEvenements.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("reponse200PUTCopiePatientMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	// PATCH //

	@Override
	public void patchPatientMedicale(JsonObject body, OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = genererRequeteSiteFrFRPourPatientMedicale(siteContexte, operationRequete, body);
		try {
			LOGGER.info(String.format("patchPatientMedicale a démarré. "));
			utilisateurPatientMedicale(requeteSite, b -> {
				if(b.succeeded()) {
					patchPatientMedicaleReponse(requeteSite, c -> {
						if(c.succeeded()) {
							gestionnaireEvenements.handle(Future.succeededFuture(c.result()));
							WorkerExecutor executeurTravailleur = siteContexte.getExecuteurTravailleur();
							executeurTravailleur.executeBlocking(
								blockingCodeHandler -> {
									try {
										recherchePatientMedicale(requeteSite, false, true, "/api/patient", "PATCH", d -> {
											if(d.succeeded()) {
												ListeRecherche<PatientMedicale> listePatientMedicale = d.result();
												RequeteApi requeteApi = new RequeteApi();
												requeteApi.setRows(listePatientMedicale.getRows());
												requeteApi.setNumFound(listePatientMedicale.getQueryResponse().getResults().getNumFound());
												requeteApi.setNumPATCH(0L);
												requeteApi.initLoinRequeteApi(requeteSite);
												requeteSite.setRequeteApi_(requeteApi);
												requeteSite.getVertx().eventBus().publish("websocketPatientMedicale", JsonObject.mapFrom(requeteApi).toString());
												SimpleOrderedMap facets = (SimpleOrderedMap)Optional.ofNullable(listePatientMedicale.getQueryResponse()).map(QueryResponse::getResponse).map(r -> r.get("facets")).orElse(null);
												Date date = null;
												if(facets != null)
													date = (Date)facets.get("max_modifie");
												String dt;
												if(date == null)
													dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(ZonedDateTime.now().toInstant(), ZoneId.of("UTC")).minusNanos(1000));
												else
													dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC")));
												listePatientMedicale.addFilterQuery(String.format("modifie_indexed_date:[* TO %s]", dt));

												try {
													listePATCHPatientMedicale(requeteApi, listePatientMedicale, dt, e -> {
														if(e.succeeded()) {
															patchPatientMedicaleReponse(requeteSite, f -> {
																if(f.succeeded()) {
																	LOGGER.info(String.format("patchPatientMedicale a réussi. "));
																	blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																} else {
																	LOGGER.error(String.format("patchPatientMedicale a échoué. ", f.cause()));
																	erreurPatientMedicale(requeteSite, null, f);
																}
															});
														} else {
															LOGGER.error(String.format("patchPatientMedicale a échoué. ", e.cause()));
															erreurPatientMedicale(requeteSite, null, e);
														}
													});
												} catch(Exception ex) {
													LOGGER.error(String.format("patchPatientMedicale a échoué. ", ex));
													erreurPatientMedicale(requeteSite, null, Future.failedFuture(ex));
												}
											} else {
												LOGGER.error(String.format("patchPatientMedicale a échoué. ", d.cause()));
												erreurPatientMedicale(requeteSite, null, d);
											}
										});
									} catch(Exception ex) {
										LOGGER.error(String.format("patchPatientMedicale a échoué. ", ex));
										erreurPatientMedicale(requeteSite, null, Future.failedFuture(ex));
									}
								}, resultHandler -> {
								}
							);
						} else {
							LOGGER.error(String.format("patchPatientMedicale a échoué. ", c.cause()));
							erreurPatientMedicale(requeteSite, gestionnaireEvenements, c);
						}
					});
				} else {
					LOGGER.error(String.format("patchPatientMedicale a échoué. ", b.cause()));
					erreurPatientMedicale(requeteSite, gestionnaireEvenements, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("patchPatientMedicale a échoué. ", ex));
			erreurPatientMedicale(requeteSite, gestionnaireEvenements, Future.failedFuture(ex));
		}
	}


	public void listePATCHPatientMedicale(RequeteApi requeteApi, ListeRecherche<PatientMedicale> listePatientMedicale, String dt, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		List<Future> futures = new ArrayList<>();
		RequeteSiteFrFR requeteSite = listePatientMedicale.getRequeteSite_();
		listePatientMedicale.getList().forEach(o -> {
			RequeteSiteFrFR requeteSite2 = genererRequeteSiteFrFRPourPatientMedicale(siteContexte, requeteSite.getOperationRequete(), requeteSite.getObjetJson());
			requeteSite2.setRequeteApi_(requeteSite.getRequeteApi_());
			o.setRequeteSite_(requeteSite2);
			futures.add(
				patchPatientMedicaleFuture(o, false, a -> {
					if(a.succeeded()) {
					} else {
						erreurPatientMedicale(requeteSite2, gestionnaireEvenements, a);
					}
				})
			);
		});
		CompositeFuture.all(futures).setHandler( a -> {
			if(a.succeeded()) {
				if(listePatientMedicale.next(dt)) {
					listePATCHPatientMedicale(requeteApi, listePatientMedicale, dt, gestionnaireEvenements);
				} else {
					reponse200PATCHPatientMedicale(requeteSite, gestionnaireEvenements);
				}
			} else {
				LOGGER.error(String.format("listePATCHPatientMedicale a échoué. ", a.cause()));
				erreurPatientMedicale(listePatientMedicale.getRequeteSite_(), gestionnaireEvenements, a);
			}
		});
	}

	public Future<PatientMedicale> patchPatientMedicaleFuture(PatientMedicale o, Boolean inheritPk, Handler<AsyncResult<PatientMedicale>> gestionnaireEvenements) {
		Promise<PatientMedicale> promise = Promise.promise();
		RequeteSiteFrFR requeteSite = o.getRequeteSite_();
		try {
			RequeteApi requeteApi = requeteSite.getRequeteApi_();
			if(requeteApi != null && requeteApi.getNumFound() == 1L) {
				requeteApi.setOriginal(o);
				requeteApi.setPk(o.getPk());
			}
			sqlConnexionPatientMedicale(requeteSite, a -> {
				if(a.succeeded()) {
					sqlTransactionPatientMedicale(requeteSite, b -> {
						if(b.succeeded()) {
							sqlPATCHPatientMedicale(o, inheritPk, c -> {
								if(c.succeeded()) {
									PatientMedicale patientMedicale = c.result();
									definirIndexerPatientMedicale(patientMedicale, d -> {
										if(d.succeeded()) {
											if(requeteApi != null) {
												requeteApi.setNumPATCH(requeteApi.getNumPATCH() + 1);
												if(requeteApi.getNumFound() == 1L) {
													patientMedicale.requeteApiPatientMedicale();
												}
												requeteSite.getVertx().eventBus().publish("websocketPatientMedicale", JsonObject.mapFrom(requeteApi).toString());
											}
											gestionnaireEvenements.handle(Future.succeededFuture(patientMedicale));
											promise.complete(patientMedicale);
										} else {
											LOGGER.error(String.format("patchPatientMedicaleFuture a échoué. ", d.cause()));
											gestionnaireEvenements.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOGGER.error(String.format("patchPatientMedicaleFuture a échoué. ", c.cause()));
									gestionnaireEvenements.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOGGER.error(String.format("patchPatientMedicaleFuture a échoué. ", b.cause()));
							gestionnaireEvenements.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOGGER.error(String.format("patchPatientMedicaleFuture a échoué. ", a.cause()));
					gestionnaireEvenements.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("patchPatientMedicaleFuture a échoué. ", e));
			erreurPatientMedicale(requeteSite, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPATCHPatientMedicale(PatientMedicale o, Boolean inheritPk, Handler<AsyncResult<PatientMedicale>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = o.getRequeteSite_();
			RequeteApi requeteApi = requeteSite.getRequeteApi_();
			List<Long> pks = Optional.ofNullable(requeteApi).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(requeteApi).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Transaction tx = requeteSite.getTx();
			Long pk = o.getPk();
			JsonObject jsonObject = requeteSite.getObjetJson();
			Set<String> methodeNoms = jsonObject.fieldNames();
			PatientMedicale o2 = new PatientMedicale();
			List<Future> futures = new ArrayList<>();

			if(o.getUtilisateurId() == null && requeteSite.getUtilisateurId() != null) {
				futures.add(Future.future(a -> {
					tx.preparedQuery(SiteContexteFrFR.SQL_setD
							, Tuple.of(pk, "utilisateurId", requeteSite.getUtilisateurId())
							, b
					-> {
						if(b.succeeded())
							a.handle(Future.succeededFuture());
						else
							a.handle(Future.failedFuture(b.cause()));
					});
				}));
			}
			if(o.getUtilisateurCle() == null && requeteSite.getUtilisateurCle() != null) {
				futures.add(Future.future(a -> {
					tx.preparedQuery(SiteContexteFrFR.SQL_setD
				, Tuple.of(pk, "utilisateurCle", requeteSite.getUtilisateurCle().toString())
							, b
					-> {
						if(b.succeeded())
							a.handle(Future.succeededFuture());
						else
							a.handle(Future.failedFuture(b.cause()));
					});
				}));

				JsonArray utilisateurCles = Optional.ofNullable(jsonObject.getJsonArray("addUtilisateurCles")).orElse(null);
				if(utilisateurCles != null && !utilisateurCles.contains(requeteSite.getUtilisateurCle()))
					utilisateurCles.add(requeteSite.getUtilisateurCle().toString());
				else
					jsonObject.put("addUtilisateurCles", new JsonArray().add(requeteSite.getUtilisateurCle().toString()));
			}

			for(String methodeNom : methodeNoms) {
				switch(methodeNom) {
					case "setInheritPk":
						if(jsonObject.getString(methodeNom) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContexteFrFR.SQL_removeD
										, Tuple.of(pk, "inheritPk")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("valeur PatientMedicale.inheritPk a échoué", b.cause())));
								});
							}));
						} else {
							o2.setInheritPk(jsonObject.getString(methodeNom));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContexteFrFR.SQL_setD
										, Tuple.of(pk, "inheritPk", o2.jsonInheritPk())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("valeur PatientMedicale.inheritPk a échoué", b.cause())));
								});
							}));
						}
						break;
					case "setArchive":
						if(jsonObject.getBoolean(methodeNom) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContexteFrFR.SQL_removeD
										, Tuple.of(pk, "archive")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("valeur PatientMedicale.archive a échoué", b.cause())));
								});
							}));
						} else {
							o2.setArchive(jsonObject.getBoolean(methodeNom));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContexteFrFR.SQL_setD
										, Tuple.of(pk, "archive", o2.jsonArchive())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("valeur PatientMedicale.archive a échoué", b.cause())));
								});
							}));
						}
						break;
					case "setSupprime":
						if(jsonObject.getBoolean(methodeNom) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContexteFrFR.SQL_removeD
										, Tuple.of(pk, "supprime")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("valeur PatientMedicale.supprime a échoué", b.cause())));
								});
							}));
						} else {
							o2.setSupprime(jsonObject.getBoolean(methodeNom));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContexteFrFR.SQL_setD
										, Tuple.of(pk, "supprime", o2.jsonSupprime())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("valeur PatientMedicale.supprime a échoué", b.cause())));
								});
							}));
						}
						break;
					case "addInscriptionCles":
						{
							Long l = Long.parseLong(jsonObject.getString(methodeNom));
							if(l != null) {
								ListeRecherche<InscriptionMedicale> listeRecherche = new ListeRecherche<InscriptionMedicale>();
								listeRecherche.setQuery("*:*");
								listeRecherche.setStocker(true);
								listeRecherche.setC(InscriptionMedicale.class);
								listeRecherche.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								listeRecherche.initLoinListeRecherche(requeteSite);
								Long l2 = Optional.ofNullable(listeRecherche.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null && !o.getInscriptionCles().contains(l2)) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContexteFrFR.SQL_addA
												, Tuple.of(pk, "inscriptionCles", l2, "patientCle")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("valeur PatientMedicale.inscriptionCles a échoué", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("InscriptionMedicale");
									}
								}
							}
						}
						break;
					case "addAllInscriptionCles":
						JsonArray addAllInscriptionClesValeurs = jsonObject.getJsonArray(methodeNom);
						if(addAllInscriptionClesValeurs != null) {
							for(Integer i = 0; i <  addAllInscriptionClesValeurs.size(); i++) {
								Long l = Long.parseLong(addAllInscriptionClesValeurs.getString(i));
								if(l != null) {
									ListeRecherche<InscriptionMedicale> listeRecherche = new ListeRecherche<InscriptionMedicale>();
									listeRecherche.setQuery("*:*");
									listeRecherche.setStocker(true);
									listeRecherche.setC(InscriptionMedicale.class);
									listeRecherche.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
									listeRecherche.initLoinListeRecherche(requeteSite);
									Long l2 = Optional.ofNullable(listeRecherche.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
									if(l2 != null && !o.getInscriptionCles().contains(l2)) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContexteFrFR.SQL_addA
												, Tuple.of(pk, "inscriptionCles", l2, "patientCle")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("valeur PatientMedicale.inscriptionCles a échoué", b.cause())));
										});
									}));
										if(!pks.contains(l2)) {
											pks.add(l2);
											classes.add("InscriptionMedicale");
										}
									}
								}
							}
						}
						break;
					case "setInscriptionCles":
						JsonArray setInscriptionClesValeurs = jsonObject.getJsonArray(methodeNom);
						if(setInscriptionClesValeurs != null) {
							for(Integer i = 0; i <  setInscriptionClesValeurs.size(); i++) {
								Long l = Long.parseLong(setInscriptionClesValeurs.getString(i));
								if(l != null) {
									ListeRecherche<InscriptionMedicale> listeRecherche = new ListeRecherche<InscriptionMedicale>();
									listeRecherche.setQuery("*:*");
									listeRecherche.setStocker(true);
									listeRecherche.setC(InscriptionMedicale.class);
									listeRecherche.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
									listeRecherche.initLoinListeRecherche(requeteSite);
									Long l2 = Optional.ofNullable(listeRecherche.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
									if(l2 != null && !o.getInscriptionCles().contains(l2)) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContexteFrFR.SQL_addA
												, Tuple.of(pk, "inscriptionCles", l2, "patientCle")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("valeur PatientMedicale.inscriptionCles a échoué", b.cause())));
										});
									}));
										if(!pks.contains(l2)) {
											pks.add(l2);
											classes.add("InscriptionMedicale");
										}
									}
								}
							}
						}
						if(o.getInscriptionCles() != null) {
							for(Long l :  o.getInscriptionCles()) {
								if(l != null && (setInscriptionClesValeurs == null || !setInscriptionClesValeurs.contains(l))) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContexteFrFR.SQL_removeA
												, Tuple.of(pk, "inscriptionCles", l, "patientCle")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("valeur PatientMedicale.inscriptionCles a échoué", b.cause())));
										});
									}));
								}
							}
						}
						break;
					case "removeInscriptionCles":
						{
							Long l = Long.parseLong(jsonObject.getString(methodeNom));
							if(l != null) {
								ListeRecherche<InscriptionMedicale> listeRecherche = new ListeRecherche<InscriptionMedicale>();
								listeRecherche.setQuery("*:*");
								listeRecherche.setStocker(true);
								listeRecherche.setC(InscriptionMedicale.class);
								listeRecherche.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								listeRecherche.initLoinListeRecherche(requeteSite);
								Long l2 = Optional.ofNullable(listeRecherche.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null && o.getInscriptionCles().contains(l2)) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContexteFrFR.SQL_removeA
												, Tuple.of(pk, "inscriptionCles", l2, "patientCle")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("valeur PatientMedicale.inscriptionCles a échoué", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("InscriptionMedicale");
									}
								}
							}
						}
						break;
					case "setPatientPrenom":
						if(jsonObject.getString(methodeNom) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContexteFrFR.SQL_removeD
										, Tuple.of(pk, "patientPrenom")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("valeur PatientMedicale.patientPrenom a échoué", b.cause())));
								});
							}));
						} else {
							o2.setPatientPrenom(jsonObject.getString(methodeNom));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContexteFrFR.SQL_setD
										, Tuple.of(pk, "patientPrenom", o2.jsonPatientPrenom())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("valeur PatientMedicale.patientPrenom a échoué", b.cause())));
								});
							}));
						}
						break;
					case "setPatientPrenomPrefere":
						if(jsonObject.getString(methodeNom) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContexteFrFR.SQL_removeD
										, Tuple.of(pk, "patientPrenomPrefere")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("valeur PatientMedicale.patientPrenomPrefere a échoué", b.cause())));
								});
							}));
						} else {
							o2.setPatientPrenomPrefere(jsonObject.getString(methodeNom));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContexteFrFR.SQL_setD
										, Tuple.of(pk, "patientPrenomPrefere", o2.jsonPatientPrenomPrefere())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("valeur PatientMedicale.patientPrenomPrefere a échoué", b.cause())));
								});
							}));
						}
						break;
					case "setFamilleNom":
						if(jsonObject.getString(methodeNom) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContexteFrFR.SQL_removeD
										, Tuple.of(pk, "familleNom")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("valeur PatientMedicale.familleNom a échoué", b.cause())));
								});
							}));
						} else {
							o2.setFamilleNom(jsonObject.getString(methodeNom));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContexteFrFR.SQL_setD
										, Tuple.of(pk, "familleNom", o2.jsonFamilleNom())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("valeur PatientMedicale.familleNom a échoué", b.cause())));
								});
							}));
						}
						break;
					case "setPatientDateNaissance":
						if(jsonObject.getString(methodeNom) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContexteFrFR.SQL_removeD
										, Tuple.of(pk, "patientDateNaissance")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("valeur PatientMedicale.patientDateNaissance a échoué", b.cause())));
								});
							}));
						} else {
							o2.setPatientDateNaissance(jsonObject.getString(methodeNom));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContexteFrFR.SQL_setD
										, Tuple.of(pk, "patientDateNaissance", o2.jsonPatientDateNaissance())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("valeur PatientMedicale.patientDateNaissance a échoué", b.cause())));
								});
							}));
						}
						break;
				}
			}
			CompositeFuture.all(futures).setHandler( a -> {
				if(a.succeeded()) {
					PatientMedicale o3 = new PatientMedicale();
					o3.setRequeteSite_(o.getRequeteSite_());
					o3.setPk(pk);
					gestionnaireEvenements.handle(Future.succeededFuture(o3));
				} else {
					LOGGER.error(String.format("sqlPATCHPatientMedicale a échoué. ", a.cause()));
					gestionnaireEvenements.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPATCHPatientMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void patchPatientMedicaleReponse(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			reponse200PATCHPatientMedicale(requeteSite, a -> {
				if(a.succeeded()) {
					gestionnaireEvenements.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("patchPatientMedicaleReponse a échoué. ", a.cause()));
					erreurPatientMedicale(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("patchPatientMedicaleReponse a échoué. ", ex));
			erreurPatientMedicale(requeteSite, null, Future.failedFuture(ex));
		}
	}
	public void reponse200PATCHPatientMedicale(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			JsonObject json = new JsonObject();
			gestionnaireEvenements.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("reponse200PATCHPatientMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	// GET //

	@Override
	public void getPatientMedicale(OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = genererRequeteSiteFrFRPourPatientMedicale(siteContexte, operationRequete);
		try {
			utilisateurPatientMedicale(requeteSite, b -> {
				if(b.succeeded()) {
					recherchePatientMedicale(requeteSite, false, true, "/api/patient/{id}", "GET", c -> {
						if(c.succeeded()) {
							ListeRecherche<PatientMedicale> listePatientMedicale = c.result();
							getPatientMedicaleReponse(listePatientMedicale, d -> {
								if(d.succeeded()) {
									gestionnaireEvenements.handle(Future.succeededFuture(d.result()));
									LOGGER.info(String.format("getPatientMedicale a réussi. "));
								} else {
									LOGGER.error(String.format("getPatientMedicale a échoué. ", d.cause()));
									erreurPatientMedicale(requeteSite, gestionnaireEvenements, d);
								}
							});
						} else {
							LOGGER.error(String.format("getPatientMedicale a échoué. ", c.cause()));
							erreurPatientMedicale(requeteSite, gestionnaireEvenements, c);
						}
					});
				} else {
					LOGGER.error(String.format("getPatientMedicale a échoué. ", b.cause()));
					erreurPatientMedicale(requeteSite, gestionnaireEvenements, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("getPatientMedicale a échoué. ", ex));
			erreurPatientMedicale(requeteSite, gestionnaireEvenements, Future.failedFuture(ex));
		}
	}


	public void getPatientMedicaleReponse(ListeRecherche<PatientMedicale> listePatientMedicale, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = listePatientMedicale.getRequeteSite_();
		try {
			reponse200GETPatientMedicale(listePatientMedicale, a -> {
				if(a.succeeded()) {
					gestionnaireEvenements.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("getPatientMedicaleReponse a échoué. ", a.cause()));
					erreurPatientMedicale(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("getPatientMedicaleReponse a échoué. ", ex));
			erreurPatientMedicale(requeteSite, null, Future.failedFuture(ex));
		}
	}
	public void reponse200GETPatientMedicale(ListeRecherche<PatientMedicale> listePatientMedicale, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = listePatientMedicale.getRequeteSite_();
			SolrDocumentList documentsSolr = listePatientMedicale.getSolrDocumentList();

			JsonObject json = JsonObject.mapFrom(listePatientMedicale.getList().stream().findFirst().orElse(null));
			gestionnaireEvenements.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("reponse200GETPatientMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	// Recherche //

	@Override
	public void recherchePatientMedicale(OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = genererRequeteSiteFrFRPourPatientMedicale(siteContexte, operationRequete);
		try {
			utilisateurPatientMedicale(requeteSite, b -> {
				if(b.succeeded()) {
					recherchePatientMedicale(requeteSite, false, true, "/api/patient", "Recherche", c -> {
						if(c.succeeded()) {
							ListeRecherche<PatientMedicale> listePatientMedicale = c.result();
							recherchePatientMedicaleReponse(listePatientMedicale, d -> {
								if(d.succeeded()) {
									gestionnaireEvenements.handle(Future.succeededFuture(d.result()));
									LOGGER.info(String.format("recherchePatientMedicale a réussi. "));
								} else {
									LOGGER.error(String.format("recherchePatientMedicale a échoué. ", d.cause()));
									erreurPatientMedicale(requeteSite, gestionnaireEvenements, d);
								}
							});
						} else {
							LOGGER.error(String.format("recherchePatientMedicale a échoué. ", c.cause()));
							erreurPatientMedicale(requeteSite, gestionnaireEvenements, c);
						}
					});
				} else {
					LOGGER.error(String.format("recherchePatientMedicale a échoué. ", b.cause()));
					erreurPatientMedicale(requeteSite, gestionnaireEvenements, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("recherchePatientMedicale a échoué. ", ex));
			erreurPatientMedicale(requeteSite, gestionnaireEvenements, Future.failedFuture(ex));
		}
	}


	public void recherchePatientMedicaleReponse(ListeRecherche<PatientMedicale> listePatientMedicale, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = listePatientMedicale.getRequeteSite_();
		try {
			reponse200RecherchePatientMedicale(listePatientMedicale, a -> {
				if(a.succeeded()) {
					gestionnaireEvenements.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("recherchePatientMedicaleReponse a échoué. ", a.cause()));
					erreurPatientMedicale(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("recherchePatientMedicaleReponse a échoué. ", ex));
			erreurPatientMedicale(requeteSite, null, Future.failedFuture(ex));
		}
	}
	public void reponse200RecherchePatientMedicale(ListeRecherche<PatientMedicale> listePatientMedicale, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = listePatientMedicale.getRequeteSite_();
			QueryResponse reponseRecherche = listePatientMedicale.getQueryResponse();
			SolrDocumentList documentsSolr = listePatientMedicale.getSolrDocumentList();
			Long millisRecherche = Long.valueOf(reponseRecherche.getQTime());
			Long millisTransmission = reponseRecherche.getElapsedTime();
			Long numCommence = reponseRecherche.getResults().getStart();
			Long numTrouve = reponseRecherche.getResults().getNumFound();
			Integer numRetourne = reponseRecherche.getResults().size();
			String tempsRecherche = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(millisRecherche), TimeUnit.MILLISECONDS.toMillis(millisRecherche) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(millisRecherche)));
			String tempsTransmission = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(millisTransmission), TimeUnit.MILLISECONDS.toMillis(millisTransmission) - TimeUnit.SECONDS.toSeconds(TimeUnit.MILLISECONDS.toSeconds(millisTransmission)));
			Exception exceptionRecherche = reponseRecherche.getException();

			JsonObject json = new JsonObject();
			json.put("numCommence", numCommence);
			json.put("numTrouve", numTrouve);
			json.put("numRetourne", numRetourne);
			json.put("tempsRecherche", tempsRecherche);
			json.put("tempsTransmission", tempsTransmission);
			JsonArray l = new JsonArray();
			listePatientMedicale.getList().stream().forEach(o -> {
				JsonObject json2 = JsonObject.mapFrom(o);
				List<String> fls = listePatientMedicale.getFields();
				if(fls.size() > 0) {
					Set<String> fieldNames = new HashSet<String>();
					fieldNames.addAll(json2.fieldNames());
					if(fls.size() == 1 && fls.stream().findFirst().orElse(null).equals("sauvegardes")) {
						fieldNames.removeAll(Optional.ofNullable(json2.getJsonArray("sauvegardes")).orElse(new JsonArray()).stream().map(s -> s.toString()).collect(Collectors.toList()));
						fieldNames.remove("pk");
						fieldNames.remove("cree");
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
			json.put("liste", l);
			if(exceptionRecherche != null) {
				json.put("exceptionRecherche", exceptionRecherche.getMessage());
			}
			gestionnaireEvenements.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("reponse200RecherchePatientMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	// RechercheAdmin //

	@Override
	public void rechercheadminPatientMedicale(OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = genererRequeteSiteFrFRPourPatientMedicale(siteContexte, operationRequete);
		try {
			utilisateurPatientMedicale(requeteSite, b -> {
				if(b.succeeded()) {
					recherchePatientMedicale(requeteSite, false, true, "/api/admin/patient", "RechercheAdmin", c -> {
						if(c.succeeded()) {
							ListeRecherche<PatientMedicale> listePatientMedicale = c.result();
							rechercheadminPatientMedicaleReponse(listePatientMedicale, d -> {
								if(d.succeeded()) {
									gestionnaireEvenements.handle(Future.succeededFuture(d.result()));
									LOGGER.info(String.format("rechercheadminPatientMedicale a réussi. "));
								} else {
									LOGGER.error(String.format("rechercheadminPatientMedicale a échoué. ", d.cause()));
									erreurPatientMedicale(requeteSite, gestionnaireEvenements, d);
								}
							});
						} else {
							LOGGER.error(String.format("rechercheadminPatientMedicale a échoué. ", c.cause()));
							erreurPatientMedicale(requeteSite, gestionnaireEvenements, c);
						}
					});
				} else {
					LOGGER.error(String.format("rechercheadminPatientMedicale a échoué. ", b.cause()));
					erreurPatientMedicale(requeteSite, gestionnaireEvenements, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("rechercheadminPatientMedicale a échoué. ", ex));
			erreurPatientMedicale(requeteSite, gestionnaireEvenements, Future.failedFuture(ex));
		}
	}


	public void rechercheadminPatientMedicaleReponse(ListeRecherche<PatientMedicale> listePatientMedicale, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = listePatientMedicale.getRequeteSite_();
		try {
			reponse200RechercheAdminPatientMedicale(listePatientMedicale, a -> {
				if(a.succeeded()) {
					gestionnaireEvenements.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("rechercheadminPatientMedicaleReponse a échoué. ", a.cause()));
					erreurPatientMedicale(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("rechercheadminPatientMedicaleReponse a échoué. ", ex));
			erreurPatientMedicale(requeteSite, null, Future.failedFuture(ex));
		}
	}
	public void reponse200RechercheAdminPatientMedicale(ListeRecherche<PatientMedicale> listePatientMedicale, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = listePatientMedicale.getRequeteSite_();
			QueryResponse reponseRecherche = listePatientMedicale.getQueryResponse();
			SolrDocumentList documentsSolr = listePatientMedicale.getSolrDocumentList();
			Long millisRecherche = Long.valueOf(reponseRecherche.getQTime());
			Long millisTransmission = reponseRecherche.getElapsedTime();
			Long numCommence = reponseRecherche.getResults().getStart();
			Long numTrouve = reponseRecherche.getResults().getNumFound();
			Integer numRetourne = reponseRecherche.getResults().size();
			String tempsRecherche = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(millisRecherche), TimeUnit.MILLISECONDS.toMillis(millisRecherche) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(millisRecherche)));
			String tempsTransmission = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(millisTransmission), TimeUnit.MILLISECONDS.toMillis(millisTransmission) - TimeUnit.SECONDS.toSeconds(TimeUnit.MILLISECONDS.toSeconds(millisTransmission)));
			Exception exceptionRecherche = reponseRecherche.getException();

			JsonObject json = new JsonObject();
			json.put("numCommence", numCommence);
			json.put("numTrouve", numTrouve);
			json.put("numRetourne", numRetourne);
			json.put("tempsRecherche", tempsRecherche);
			json.put("tempsTransmission", tempsTransmission);
			JsonArray l = new JsonArray();
			listePatientMedicale.getList().stream().forEach(o -> {
				JsonObject json2 = JsonObject.mapFrom(o);
				List<String> fls = listePatientMedicale.getFields();
				if(fls.size() > 0) {
					Set<String> fieldNames = new HashSet<String>();
					fieldNames.addAll(json2.fieldNames());
					if(fls.size() == 1 && fls.stream().findFirst().orElse(null).equals("sauvegardes")) {
						fieldNames.removeAll(Optional.ofNullable(json2.getJsonArray("sauvegardes")).orElse(new JsonArray()).stream().map(s -> s.toString()).collect(Collectors.toList()));
						fieldNames.remove("pk");
						fieldNames.remove("cree");
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
			json.put("liste", l);
			if(exceptionRecherche != null) {
				json.put("exceptionRecherche", exceptionRecherche.getMessage());
			}
			gestionnaireEvenements.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("reponse200RechercheAdminPatientMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	// PageRecherche //

	@Override
	public void pagerecherchePatientMedicaleId(OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		pagerecherchePatientMedicale(operationRequete, gestionnaireEvenements);
	}

	@Override
	public void pagerecherchePatientMedicale(OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = genererRequeteSiteFrFRPourPatientMedicale(siteContexte, operationRequete);
		try {
			utilisateurPatientMedicale(requeteSite, b -> {
				if(b.succeeded()) {
					recherchePatientMedicale(requeteSite, false, true, "/patient", "PageRecherche", c -> {
						if(c.succeeded()) {
							ListeRecherche<PatientMedicale> listePatientMedicale = c.result();
							pagerecherchePatientMedicaleReponse(listePatientMedicale, d -> {
								if(d.succeeded()) {
									gestionnaireEvenements.handle(Future.succeededFuture(d.result()));
									LOGGER.info(String.format("pagerecherchePatientMedicale a réussi. "));
								} else {
									LOGGER.error(String.format("pagerecherchePatientMedicale a échoué. ", d.cause()));
									erreurPatientMedicale(requeteSite, gestionnaireEvenements, d);
								}
							});
						} else {
							LOGGER.error(String.format("pagerecherchePatientMedicale a échoué. ", c.cause()));
							erreurPatientMedicale(requeteSite, gestionnaireEvenements, c);
						}
					});
				} else {
					LOGGER.error(String.format("pagerecherchePatientMedicale a échoué. ", b.cause()));
					erreurPatientMedicale(requeteSite, gestionnaireEvenements, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("pagerecherchePatientMedicale a échoué. ", ex));
			erreurPatientMedicale(requeteSite, gestionnaireEvenements, Future.failedFuture(ex));
		}
	}


	public void pagerecherchePatientMedicaleReponse(ListeRecherche<PatientMedicale> listePatientMedicale, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = listePatientMedicale.getRequeteSite_();
		try {
			Buffer buffer = Buffer.buffer();
			ToutEcrivain w = ToutEcrivain.creer(requeteSite, buffer);
			requeteSite.setW(w);
			reponse200PageRecherchePatientMedicale(listePatientMedicale, a -> {
				if(a.succeeded()) {
					gestionnaireEvenements.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("pagerecherchePatientMedicaleReponse a échoué. ", a.cause()));
					erreurPatientMedicale(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("pagerecherchePatientMedicaleReponse a échoué. ", ex));
			erreurPatientMedicale(requeteSite, null, Future.failedFuture(ex));
		}
	}
	public void reponse200PageRecherchePatientMedicale(ListeRecherche<PatientMedicale> listePatientMedicale, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = listePatientMedicale.getRequeteSite_();
			Buffer buffer = Buffer.buffer();
			ToutEcrivain w = ToutEcrivain.creer(listePatientMedicale.getRequeteSite_(), buffer);
			EnfantPage page = new EnfantPage();
			SolrDocument pageDocumentSolr = new SolrDocument();
			CaseInsensitiveHeaders requeteEnTetes = new CaseInsensitiveHeaders();
			requeteSite.setRequeteEnTetes(requeteEnTetes);

			pageDocumentSolr.setField("pageUri_frFR_stored_string", "/patient");
			page.setPageDocumentSolr(pageDocumentSolr);
			page.setW(w);
			if(listePatientMedicale.size() == 1)
				requeteSite.setRequetePk(listePatientMedicale.get(0).getPk());
			requeteSite.setW(w);
			page.setListePatientMedicale(listePatientMedicale);
			page.setRequeteSite_(requeteSite);
			page.initLoinEnfantPage(requeteSite);
			page.html();
			gestionnaireEvenements.handle(Future.succeededFuture(new OperationResponse(200, "OK", buffer, requeteEnTetes)));
		} catch(Exception e) {
			LOGGER.error(String.format("reponse200PageRecherchePatientMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	// General //

	public Future<PatientMedicale> definirIndexerPatientMedicale(PatientMedicale patientMedicale, Handler<AsyncResult<PatientMedicale>> gestionnaireEvenements) {
		Promise<PatientMedicale> promise = Promise.promise();
		RequeteSiteFrFR requeteSite = patientMedicale.getRequeteSite_();
		definirPatientMedicale(patientMedicale, c -> {
			if(c.succeeded()) {
				attribuerPatientMedicale(patientMedicale, d -> {
					if(d.succeeded()) {
						indexerPatientMedicale(patientMedicale, e -> {
							if(e.succeeded()) {
								sqlCommitPatientMedicale(requeteSite, f -> {
									if(f.succeeded()) {
										sqlFermerPatientMedicale(requeteSite, g -> {
											if(g.succeeded()) {
												rechargerPatientMedicale(patientMedicale, h -> {
													if(h.succeeded()) {
														gestionnaireEvenements.handle(Future.succeededFuture(patientMedicale));
														promise.complete(patientMedicale);
													} else {
														LOGGER.error(String.format("rechargerPatientMedicale a échoué. ", h.cause()));
														erreurPatientMedicale(requeteSite, null, h);
													}
												});
											} else {
												LOGGER.error(String.format("definirIndexerPatientMedicale a échoué. ", g.cause()));
												erreurPatientMedicale(requeteSite, null, g);
											}
										});
									} else {
										LOGGER.error(String.format("definirIndexerPatientMedicale a échoué. ", f.cause()));
										erreurPatientMedicale(requeteSite, null, f);
									}
								});
							} else {
								LOGGER.error(String.format("definirIndexerPatientMedicale a échoué. ", e.cause()));
								erreurPatientMedicale(requeteSite, null, e);
							}
						});
					} else {
						LOGGER.error(String.format("definirIndexerPatientMedicale a échoué. ", d.cause()));
						erreurPatientMedicale(requeteSite, null, d);
					}
				});
			} else {
				LOGGER.error(String.format("definirIndexerPatientMedicale a échoué. ", c.cause()));
				erreurPatientMedicale(requeteSite, null, c);
			}
		});
		return promise.future();
	}

	public void creerPatientMedicale(RequeteSiteFrFR requeteSite, Handler<AsyncResult<PatientMedicale>> gestionnaireEvenements) {
		try {
			Transaction tx = requeteSite.getTx();
			String utilisateurId = requeteSite.getUtilisateurId();
			ZonedDateTime cree = Optional.ofNullable(requeteSite.getObjetJson()).map(j -> j.getString("cree")).map(s -> ZonedDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of(requeteSite.getConfigSite_().getSiteZone())))).orElse(ZonedDateTime.now(ZoneId.of(requeteSite.getConfigSite_().getSiteZone())));

			tx.preparedQuery(
					SiteContexteFrFR.SQL_creer
					, Tuple.of(PatientMedicale.class.getCanonicalName(), utilisateurId)
					, Collectors.toList()
					, creerAsync
			-> {
				if(creerAsync.succeeded()) {
					Row creerLigne = creerAsync.result().value().stream().findFirst().orElseGet(() -> null);
					Long pk = creerLigne.getLong(0);
					PatientMedicale o = new PatientMedicale();
					o.setPk(pk);
					o.setRequeteSite_(requeteSite);
					gestionnaireEvenements.handle(Future.succeededFuture(o));
				} else {
					LOGGER.error(String.format("creerPatientMedicale a échoué. ", creerAsync.cause()));
					gestionnaireEvenements.handle(Future.failedFuture(creerAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("creerPatientMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void erreurPatientMedicale(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements, AsyncResult<?> resultatAsync) {
		Throwable e = resultatAsync.cause();
		ExceptionUtils.printRootCauseStackTrace(e);
		OperationResponse reponseOperation = new OperationResponse(400, "BAD REQUEST", 
			Buffer.buffer().appendString(
				new JsonObject() {{
					put("erreur", new JsonObject()
						.put("message", Optional.ofNullable(e).map(Throwable::getMessage).orElse(null))
					);
				}}.encodePrettily()
			)
			, new CaseInsensitiveHeaders()
		);
		ConfigSite configSite = requeteSite.getConfigSite_();
		SiteContexteFrFR siteContexte = requeteSite.getSiteContexte_();
		MailClient mailClient = siteContexte.getMailClient();
		MailMessage message = new MailMessage();
		message.setFrom(configSite.getMailDe());
		message.setTo(configSite.getMailAdmin());
		if(e != null)
			message.setText(ExceptionUtils.getStackTrace(e));
		message.setSubject(String.format(configSite.getSiteUrlBase() + " " + Optional.ofNullable(e).map(Throwable::getMessage).orElse(null)));
		WorkerExecutor workerExecutor = siteContexte.getExecuteurTravailleur();
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
		sqlRollbackPatientMedicale(requeteSite, a -> {
			if(a.succeeded()) {
				LOGGER.info(String.format("sql rollback. "));
				sqlFermerPatientMedicale(requeteSite, b -> {
					if(b.succeeded()) {
						LOGGER.info(String.format("sql close. "));
						if(gestionnaireEvenements != null)
							gestionnaireEvenements.handle(Future.succeededFuture(reponseOperation));
					} else {
						if(gestionnaireEvenements != null)
							gestionnaireEvenements.handle(Future.succeededFuture(reponseOperation));
					}
				});
			} else {
				if(gestionnaireEvenements != null)
					gestionnaireEvenements.handle(Future.succeededFuture(reponseOperation));
			}
		});
	}

	public void sqlConnexionPatientMedicale(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			PgPool pgPool = requeteSite.getSiteContexte_().getPgPool();

			if(pgPool == null) {
				gestionnaireEvenements.handle(Future.succeededFuture());
			} else {
				pgPool.getConnection(a -> {
					if(a.succeeded()) {
						SqlConnection connexionSql = a.result();
						requeteSite.setConnexionSql(connexionSql);
						gestionnaireEvenements.handle(Future.succeededFuture());
					} else {
						LOGGER.error(String.format("sqlConnexionPatientMedicale a échoué. ", a.cause()));
						gestionnaireEvenements.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPatientMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void sqlTransactionPatientMedicale(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			SqlConnection connexionSql = requeteSite.getConnexionSql();

			if(connexionSql == null) {
				gestionnaireEvenements.handle(Future.succeededFuture());
			} else {
				Transaction tx = connexionSql.begin();
				requeteSite.setTx(tx);
				gestionnaireEvenements.handle(Future.succeededFuture());
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlTransactionPatientMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void sqlCommitPatientMedicale(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			Transaction tx = requeteSite.getTx();

			if(tx == null) {
				gestionnaireEvenements.handle(Future.succeededFuture());
			} else {
				tx.commit(a -> {
					if(a.succeeded()) {
						requeteSite.setTx(null);
						gestionnaireEvenements.handle(Future.succeededFuture());
					} else if("Transaction already completed".equals(a.cause().getMessage())) {
						requeteSite.setTx(null);
						gestionnaireEvenements.handle(Future.succeededFuture());
					} else {
						LOGGER.error(String.format("sqlCommitPatientMedicale a échoué. ", a.cause()));
						gestionnaireEvenements.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPatientMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void sqlRollbackPatientMedicale(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			Transaction tx = requeteSite.getTx();

			if(tx == null) {
				gestionnaireEvenements.handle(Future.succeededFuture());
			} else {
				tx.rollback(a -> {
					if(a.succeeded()) {
						requeteSite.setTx(null);
						gestionnaireEvenements.handle(Future.succeededFuture());
					} else if("Transaction already completed".equals(a.cause().getMessage())) {
						requeteSite.setTx(null);
						gestionnaireEvenements.handle(Future.succeededFuture());
					} else {
						LOGGER.error(String.format("sqlRollbackPatientMedicale a échoué. ", a.cause()));
						gestionnaireEvenements.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPatientMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void sqlFermerPatientMedicale(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			SqlConnection connexionSql = requeteSite.getConnexionSql();

			if(connexionSql == null) {
				gestionnaireEvenements.handle(Future.succeededFuture());
			} else {
				connexionSql.close();
				requeteSite.setConnexionSql(null);
				gestionnaireEvenements.handle(Future.succeededFuture());
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlFermerPatientMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public RequeteSiteFrFR genererRequeteSiteFrFRPourPatientMedicale(SiteContexteFrFR siteContexte, OperationRequest operationRequete) {
		return genererRequeteSiteFrFRPourPatientMedicale(siteContexte, operationRequete, null);
	}

	public RequeteSiteFrFR genererRequeteSiteFrFRPourPatientMedicale(SiteContexteFrFR siteContexte, OperationRequest operationRequete, JsonObject body) {
		Vertx vertx = siteContexte.getVertx();
		RequeteSiteFrFR requeteSite = new RequeteSiteFrFR();
		requeteSite.setObjetJson(body);
		requeteSite.setVertx(vertx);
		requeteSite.setSiteContexte_(siteContexte);
		requeteSite.setConfigSite_(siteContexte.getConfigSite());
		requeteSite.setOperationRequete(operationRequete);
		requeteSite.initLoinRequeteSiteFrFR(requeteSite);

		return requeteSite;
	}

	public void utilisateurPatientMedicale(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			String utilisateurId = requeteSite.getUtilisateurId();
			if(utilisateurId == null) {
				gestionnaireEvenements.handle(Future.succeededFuture());
			} else {
				sqlConnexionPatientMedicale(requeteSite, a -> {
					if(a.succeeded()) {
						sqlTransactionPatientMedicale(requeteSite, b -> {
							if(b.succeeded()) {
								Transaction tx = requeteSite.getTx();
								tx.preparedQuery(
										SiteContexteFrFR.SQL_selectC
										, Tuple.of("org.computate.medicale.frFR.utilisateur.UtilisateurSite", utilisateurId)
										, Collectors.toList()
										, selectCAsync
								-> {
									if(selectCAsync.succeeded()) {
										try {
											Row utilisateurValeurs = selectCAsync.result().value().stream().findFirst().orElse(null);
											UtilisateurSiteFrFRApiServiceImpl utilisateurService = new UtilisateurSiteFrFRApiServiceImpl(siteContexte);
											if(utilisateurValeurs == null) {
												JsonObject utilisateurVertx = requeteSite.getOperationRequete().getUser();
												JsonObject principalJson = KeycloakHelper.parseToken(utilisateurVertx.getString("access_token"));

												JsonObject jsonObject = new JsonObject();
												jsonObject.put("utilisateurNom", principalJson.getString("preferred_username"));
												jsonObject.put("utilisateurPrenom", principalJson.getString("given_name"));
												jsonObject.put("utilisateurNomFamille", principalJson.getString("family_name"));
												jsonObject.put("utilisateurId", principalJson.getString("sub"));
												utilisateurPatientMedicaleDefinir(requeteSite, jsonObject, false);

												RequeteSiteFrFR requeteSite2 = new RequeteSiteFrFR();
												requeteSite2.setTx(requeteSite.getTx());
												requeteSite2.setConnexionSql(requeteSite.getConnexionSql());
												requeteSite2.setObjetJson(jsonObject);
												requeteSite2.setVertx(requeteSite.getVertx());
												requeteSite2.setSiteContexte_(siteContexte);
												requeteSite2.setConfigSite_(siteContexte.getConfigSite());
												requeteSite2.setUtilisateurId(requeteSite.getUtilisateurId());
												requeteSite2.initLoinRequeteSiteFrFR(requeteSite);

												RequeteApi requeteApi = new RequeteApi();
												requeteApi.setRows(1);
												requeteApi.setNumFound(1L);
												requeteApi.setNumPATCH(0L);
												requeteApi.initLoinRequeteApi(requeteSite2);
												requeteSite2.setRequeteApi_(requeteApi);

												utilisateurService.creerUtilisateurSite(requeteSite2, c -> {
													if(c.succeeded()) {
														UtilisateurSite utilisateurSite = c.result();
														utilisateurService.sqlPOSTUtilisateurSite(utilisateurSite, false, d -> {
															if(d.succeeded()) {
																utilisateurService.definirIndexerUtilisateurSite(utilisateurSite, e -> {
																	if(e.succeeded()) {
																		requeteSite.setUtilisateurSite(utilisateurSite);
																		requeteSite.setUtilisateurNom(principalJson.getString("preferred_username"));
																		requeteSite.setUtilisateurPrenom(principalJson.getString("given_name"));
																		requeteSite.setUtilisateurNomFamille(principalJson.getString("family_name"));
																		requeteSite.setUtilisateurId(principalJson.getString("sub"));
																		requeteSite.setUtilisateurCle(utilisateurSite.getPk());
																		gestionnaireEvenements.handle(Future.succeededFuture());
																	} else {
																		erreurPatientMedicale(requeteSite, gestionnaireEvenements, e);
																	}
																});
															} else {
																erreurPatientMedicale(requeteSite, gestionnaireEvenements, d);
															}
														});
													} else {
														erreurPatientMedicale(requeteSite, gestionnaireEvenements, c);
													}
												});
											} else {
												Long pkUtilisateur = utilisateurValeurs.getLong(0);
												ListeRecherche<UtilisateurSite> listeRecherche = new ListeRecherche<UtilisateurSite>();
												listeRecherche.setQuery("*:*");
												listeRecherche.setStocker(true);
												listeRecherche.setC(UtilisateurSite.class);
												listeRecherche.addFilterQuery("utilisateurId_indexed_string:" + ClientUtils.escapeQueryChars(utilisateurId));
												listeRecherche.addFilterQuery("pk_indexed_long:" + pkUtilisateur);
												listeRecherche.initLoinListeRecherche(requeteSite);
												UtilisateurSite utilisateurSite1 = listeRecherche.getList().stream().findFirst().orElse(null);

												JsonObject utilisateurVertx = requeteSite.getOperationRequete().getUser();
												JsonObject principalJson = KeycloakHelper.parseToken(utilisateurVertx.getString("access_token"));

												JsonObject jsonObject = new JsonObject();
												jsonObject.put("setUtilisateurNom", principalJson.getString("preferred_username"));
												jsonObject.put("setUtilisateurPrenom", principalJson.getString("given_name"));
												jsonObject.put("setUtilisateurNomFamille", principalJson.getString("family_name"));
												jsonObject.put("setUtilisateurNomComplet", principalJson.getString("name"));
												jsonObject.put("setCustomerProfileId", Optional.ofNullable(utilisateurSite1).map(u -> u.getCustomerProfileId()).orElse(null));
												jsonObject.put("setUtilisateurId", principalJson.getString("sub"));
												jsonObject.put("setUtilisateurMail", principalJson.getString("email"));
												Boolean definir = utilisateurPatientMedicaleDefinir(requeteSite, jsonObject, true);
												if(definir) {
													UtilisateurSite utilisateurSite;
													if(utilisateurSite1 == null) {
														utilisateurSite = new UtilisateurSite();
														utilisateurSite.setPk(pkUtilisateur);
														utilisateurSite.setRequeteSite_(requeteSite);
													} else {
														utilisateurSite = utilisateurSite1;
													}

													RequeteSiteFrFR requeteSite2 = new RequeteSiteFrFR();
													requeteSite2.setTx(requeteSite.getTx());
													requeteSite2.setConnexionSql(requeteSite.getConnexionSql());
													requeteSite2.setObjetJson(jsonObject);
													requeteSite2.setVertx(requeteSite.getVertx());
													requeteSite2.setSiteContexte_(siteContexte);
													requeteSite2.setConfigSite_(siteContexte.getConfigSite());
													requeteSite2.setUtilisateurId(requeteSite.getUtilisateurId());
													requeteSite2.setUtilisateurCle(requeteSite.getUtilisateurCle());
													requeteSite2.initLoinRequeteSiteFrFR(requeteSite);
													utilisateurSite.setRequeteSite_(requeteSite2);

													RequeteApi requeteApi = new RequeteApi();
													requeteApi.setRows(1);
													requeteApi.setNumFound(1L);
													requeteApi.setNumPATCH(0L);
													requeteApi.initLoinRequeteApi(requeteSite2);
													requeteSite2.setRequeteApi_(requeteApi);

													utilisateurService.sqlPATCHUtilisateurSite(utilisateurSite, false, d -> {
														if(d.succeeded()) {
															UtilisateurSite utilisateurSite2 = d.result();
															utilisateurService.definirIndexerUtilisateurSite(utilisateurSite2, e -> {
																if(e.succeeded()) {
																	requeteSite.setUtilisateurSite(utilisateurSite2);
																	requeteSite.setUtilisateurNom(utilisateurSite2.getUtilisateurNom());
																	requeteSite.setUtilisateurPrenom(utilisateurSite2.getUtilisateurPrenom());
																	requeteSite.setUtilisateurNomFamille(utilisateurSite2.getUtilisateurNomFamille());
																	requeteSite.setUtilisateurId(utilisateurSite2.getUtilisateurId());
																	requeteSite.setUtilisateurCle(utilisateurSite2.getPk());
																	gestionnaireEvenements.handle(Future.succeededFuture());
																} else {
																	erreurPatientMedicale(requeteSite, gestionnaireEvenements, e);
																}
															});
														} else {
															erreurPatientMedicale(requeteSite, gestionnaireEvenements, d);
														}
													});
												} else {
													requeteSite.setUtilisateurSite(utilisateurSite1);
													requeteSite.setUtilisateurNom(utilisateurSite1.getUtilisateurNom());
													requeteSite.setUtilisateurPrenom(utilisateurSite1.getUtilisateurPrenom());
													requeteSite.setUtilisateurNomFamille(utilisateurSite1.getUtilisateurNomFamille());
													requeteSite.setUtilisateurId(utilisateurSite1.getUtilisateurId());
													requeteSite.setUtilisateurCle(utilisateurSite1.getPk());
													sqlRollbackPatientMedicale(requeteSite, c -> {
														if(c.succeeded()) {
															gestionnaireEvenements.handle(Future.succeededFuture());
														} else {
															gestionnaireEvenements.handle(Future.failedFuture(c.cause()));
															erreurPatientMedicale(requeteSite, gestionnaireEvenements, c);
														}
													});
												}
											}
										} catch(Exception e) {
											LOGGER.error(String.format("utilisateurPatientMedicale a échoué. ", e));
											gestionnaireEvenements.handle(Future.failedFuture(e));
										}
									} else {
										LOGGER.error(String.format("utilisateurPatientMedicale a échoué. ", selectCAsync.cause()));
										gestionnaireEvenements.handle(Future.failedFuture(selectCAsync.cause()));
									}
								});
							} else {
								LOGGER.error(String.format("utilisateurPatientMedicale a échoué. ", b.cause()));
								gestionnaireEvenements.handle(Future.failedFuture(b.cause()));
							}
						});
					} else {
						LOGGER.error(String.format("utilisateurPatientMedicale a échoué. ", a.cause()));
						gestionnaireEvenements.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("utilisateurPatientMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public Boolean utilisateurPatientMedicaleDefinir(RequeteSiteFrFR requeteSite, JsonObject jsonObject, Boolean patch) {
		if(patch) {
			return jsonObject.getString("setCustomerProfileId") == null;
		} else {
			return jsonObject.getString("customerProfileId") == null;
		}
	}

	public void recherchePatientMedicaleQ(String uri, String apiMethode, ListeRecherche<PatientMedicale> listeRecherche, String entiteVar, String valeurIndexe, String varIndexe) {
		listeRecherche.setQuery(varIndexe + ":" + ("*".equals(valeurIndexe) ? valeurIndexe : ClientUtils.escapeQueryChars(valeurIndexe)));
		if(!"*".equals(entiteVar)) {
			listeRecherche.setHighlight(true);
			listeRecherche.setHighlightSnippets(3);
			listeRecherche.addHighlightField(varIndexe);
			listeRecherche.setParam("hl.encoder", "html");
		}
	}

	public void recherchePatientMedicaleFq(String uri, String apiMethode, ListeRecherche<PatientMedicale> listeRecherche, String entiteVar, String valeurIndexe, String varIndexe) {
		if(varIndexe == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entiteVar));
		listeRecherche.addFilterQuery(varIndexe + ":" + ClientUtils.escapeQueryChars(valeurIndexe));
	}

	public void recherchePatientMedicaleSort(String uri, String apiMethode, ListeRecherche<PatientMedicale> listeRecherche, String entiteVar, String valeurIndexe, String varIndexe) {
		if(varIndexe == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entiteVar));
		listeRecherche.addSort(varIndexe, ORDER.valueOf(valeurIndexe));
	}

	public void recherchePatientMedicaleRows(String uri, String apiMethode, ListeRecherche<PatientMedicale> listeRecherche, Integer valeurRows) {
			listeRecherche.setRows(apiMethode != null && apiMethode.contains("Recherche") ? valeurRows : 10);
	}

	public void recherchePatientMedicaleStart(String uri, String apiMethode, ListeRecherche<PatientMedicale> listeRecherche, Integer valeurStart) {
		listeRecherche.setStart(valeurStart);
	}

	public void recherchePatientMedicaleVar(String uri, String apiMethode, ListeRecherche<PatientMedicale> listeRecherche, String var, String valeur) {
		listeRecherche.getRequeteSite_().getRequeteVars().put(var, valeur);
	}

	public void recherchePatientMedicaleUri(String uri, String apiMethode, ListeRecherche<PatientMedicale> listeRecherche) {
	}

	public void varsPatientMedicale(RequeteSiteFrFR requeteSite, Handler<AsyncResult<ListeRecherche<OperationResponse>>> gestionnaireEvenements) {
		try {
			OperationRequest operationRequete = requeteSite.getOperationRequete();

			operationRequete.getParams().getJsonObject("query").forEach(paramRequete -> {
				String entiteVar = null;
				String valeurIndexe = null;
				String paramNom = paramRequete.getKey();
				Object paramValeursObjet = paramRequete.getValue();
				JsonArray paramObjets = paramValeursObjet instanceof JsonArray ? (JsonArray)paramValeursObjet : new JsonArray().add(paramValeursObjet);

				try {
					for(Object paramObjet : paramObjets) {
						switch(paramNom) {
							case "var":
								entiteVar = StringUtils.trim(StringUtils.substringBefore((String)paramObjet, ":"));
								valeurIndexe = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObjet, ":")), "UTF-8");
								requeteSite.getRequeteVars().put(entiteVar, valeurIndexe);
								break;
						}
					}
				} catch(Exception e) {
					LOGGER.error(String.format("recherchePatientMedicale a échoué. ", e));
					gestionnaireEvenements.handle(Future.failedFuture(e));
				}
			});
			gestionnaireEvenements.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOGGER.error(String.format("recherchePatientMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void recherchePatientMedicale(RequeteSiteFrFR requeteSite, Boolean peupler, Boolean stocker, String uri, String apiMethode, Handler<AsyncResult<ListeRecherche<PatientMedicale>>> gestionnaireEvenements) {
		try {
			OperationRequest operationRequete = requeteSite.getOperationRequete();
			String entiteListeStr = requeteSite.getOperationRequete().getParams().getJsonObject("query").getString("fl");
			String[] entiteListe = entiteListeStr == null ? null : entiteListeStr.split(",\\s*");
			ListeRecherche<PatientMedicale> listeRecherche = new ListeRecherche<PatientMedicale>();
			listeRecherche.setPeupler(peupler);
			listeRecherche.setStocker(stocker);
			listeRecherche.setQuery("*:*");
			listeRecherche.setC(PatientMedicale.class);
			listeRecherche.setRequeteSite_(requeteSite);
			if(entiteListe != null)
				listeRecherche.addFields(entiteListe);
			listeRecherche.add("json.facet", "{max_modifie:'max(modifie_indexed_date)'}");

			String id = operationRequete.getParams().getJsonObject("path").getString("id");
			if(id != null) {
				listeRecherche.addFilterQuery("(id:" + ClientUtils.escapeQueryChars(id) + " OR objetId_indexed_string:" + ClientUtils.escapeQueryChars(id) + ")");
			}

			List<String> roles = Arrays.asList("SiteAdmin");
			if(
					!CollectionUtils.containsAny(requeteSite.getUtilisateurRolesRessource(), roles)
					&& !CollectionUtils.containsAny(requeteSite.getUtilisateurRolesRoyaume(), roles)
					) {
				listeRecherche.addFilterQuery("sessionId_indexed_string:" + ClientUtils.escapeQueryChars(Optional.ofNullable(requeteSite.getSessionId()).orElse("-----")) + " OR " + "sessionId_indexed_string:" + ClientUtils.escapeQueryChars(Optional.ofNullable(requeteSite.getSessionIdAvant()).orElse("-----"))
						+ " OR utilisateurCles_indexed_longs:" + Optional.ofNullable(requeteSite.getUtilisateurCle()).orElse(0L));
			}

			operationRequete.getParams().getJsonObject("query").forEach(paramRequete -> {
				String entiteVar = null;
				String valeurIndexe = null;
				String varIndexe = null;
				String valeurTri = null;
				Integer valeurStart = null;
				Integer valeurRows = null;
				String paramNom = paramRequete.getKey();
				Object paramValeursObjet = paramRequete.getValue();
				JsonArray paramObjets = paramValeursObjet instanceof JsonArray ? (JsonArray)paramValeursObjet : new JsonArray().add(paramValeursObjet);

				try {
					for(Object paramObjet : paramObjets) {
						switch(paramNom) {
							case "q":
								entiteVar = StringUtils.trim(StringUtils.substringBefore((String)paramObjet, ":"));
								varIndexe = "*".equals(entiteVar) ? entiteVar : PatientMedicale.varRecherchePatientMedicale(entiteVar);
								valeurIndexe = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObjet, ":")), "UTF-8");
								valeurIndexe = StringUtils.isEmpty(valeurIndexe) ? "*" : valeurIndexe;
								recherchePatientMedicaleQ(uri, apiMethode, listeRecherche, entiteVar, valeurIndexe, varIndexe);
								break;
							case "fq":
								entiteVar = StringUtils.trim(StringUtils.substringBefore((String)paramObjet, ":"));
								valeurIndexe = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObjet, ":")), "UTF-8");
								varIndexe = PatientMedicale.varIndexePatientMedicale(entiteVar);
								recherchePatientMedicaleFq(uri, apiMethode, listeRecherche, entiteVar, valeurIndexe, varIndexe);
								break;
							case "sort":
								entiteVar = StringUtils.trim(StringUtils.substringBefore((String)paramObjet, " "));
								valeurIndexe = StringUtils.trim(StringUtils.substringAfter((String)paramObjet, " "));
								varIndexe = PatientMedicale.varIndexePatientMedicale(entiteVar);
								recherchePatientMedicaleSort(uri, apiMethode, listeRecherche, entiteVar, valeurIndexe, varIndexe);
								break;
							case "start":
								valeurStart = (Integer)paramObjet;
								recherchePatientMedicaleStart(uri, apiMethode, listeRecherche, valeurStart);
								break;
							case "rows":
								valeurRows = (Integer)paramObjet;
								recherchePatientMedicaleRows(uri, apiMethode, listeRecherche, valeurRows);
								break;
							case "var":
								entiteVar = StringUtils.trim(StringUtils.substringBefore((String)paramObjet, ":"));
								valeurIndexe = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObjet, ":")), "UTF-8");
								recherchePatientMedicaleVar(uri, apiMethode, listeRecherche, entiteVar, valeurIndexe);
								break;
						}
					}
					recherchePatientMedicaleUri(uri, apiMethode, listeRecherche);
				} catch(Exception e) {
					LOGGER.error(String.format("recherchePatientMedicale a échoué. ", e));
					gestionnaireEvenements.handle(Future.failedFuture(e));
				}
			});
			if(listeRecherche.getSorts().size() == 0) {
				listeRecherche.addSort("cree_indexed_date", ORDER.desc);
			}
			listeRecherche.initLoinPourClasse(requeteSite);
			gestionnaireEvenements.handle(Future.succeededFuture(listeRecherche));
		} catch(Exception e) {
			LOGGER.error(String.format("recherchePatientMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void definirPatientMedicale(PatientMedicale o, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = o.getRequeteSite_();
			Transaction tx = requeteSite.getTx();
			Long pk = o.getPk();
			tx.preparedQuery(
					SiteContexteFrFR.SQL_definir
					, Tuple.of(pk)
					, Collectors.toList()
					, definirAsync
			-> {
				if(definirAsync.succeeded()) {
					try {
						for(Row definition : definirAsync.result().value()) {
							try {
								o.definirPourClasse(definition.getString(0), definition.getString(1));
							} catch(Exception e) {
								LOGGER.error(e);
							}
						}
						gestionnaireEvenements.handle(Future.succeededFuture());
					} catch(Exception e) {
						LOGGER.error(String.format("definirPatientMedicale a échoué. ", e));
						gestionnaireEvenements.handle(Future.failedFuture(e));
					}
				} else {
					LOGGER.error(String.format("definirPatientMedicale a échoué. ", definirAsync.cause()));
					gestionnaireEvenements.handle(Future.failedFuture(definirAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("definirPatientMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void attribuerPatientMedicale(PatientMedicale o, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = o.getRequeteSite_();
			Transaction tx = requeteSite.getTx();
			Long pk = o.getPk();
			tx.preparedQuery(
					SiteContexteFrFR.SQL_attribuer
					, Tuple.of(pk, pk)
					, Collectors.toList()
					, attribuerAsync
			-> {
				try {
					if(attribuerAsync.succeeded()) {
						if(attribuerAsync.result() != null) {
							for(Row definition : attribuerAsync.result().value()) {
								if(pk.equals(definition.getLong(0)))
									o.attribuerPourClasse(definition.getString(2), definition.getLong(1));
								else
									o.attribuerPourClasse(definition.getString(3), definition.getLong(0));
							}
						}
						gestionnaireEvenements.handle(Future.succeededFuture());
					} else {
						LOGGER.error(String.format("attribuerPatientMedicale a échoué. ", attribuerAsync.cause()));
						gestionnaireEvenements.handle(Future.failedFuture(attribuerAsync.cause()));
					}
				} catch(Exception e) {
					LOGGER.error(String.format("attribuerPatientMedicale a échoué. ", e));
					gestionnaireEvenements.handle(Future.failedFuture(e));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("attribuerPatientMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void indexerPatientMedicale(PatientMedicale o, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = o.getRequeteSite_();
		try {
			RequeteApi requeteApi = requeteSite.getRequeteApi_();
			List<Long> pks = Optional.ofNullable(requeteApi).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(requeteApi).map(r -> r.getClasses()).orElse(new ArrayList<>());
			o.initLoinPourClasse(requeteSite);
			o.indexerPourClasse();
			gestionnaireEvenements.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOGGER.error(String.format("indexerPatientMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void rechargerPatientMedicale(PatientMedicale o, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = o.getRequeteSite_();
		try {
			RequeteApi requeteApi = requeteSite.getRequeteApi_();
			List<Long> pks = Optional.ofNullable(requeteApi).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(requeteApi).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Boolean recharger = !"false".equals(requeteSite.getRequeteVars().get("recharger"));
			if(recharger && BooleanUtils.isFalse(Optional.ofNullable(requeteSite.getRequeteApi_()).map(RequeteApi::getEmpty).orElse(true))) {
				ListeRecherche<PatientMedicale> listeRecherche = new ListeRecherche<PatientMedicale>();
				listeRecherche.setStocker(true);
				listeRecherche.setQuery("*:*");
				listeRecherche.setC(PatientMedicale.class);
				listeRecherche.addFilterQuery("modifie_indexed_date:[" + DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(requeteSite.getRequeteApi_().getCree().toInstant(), ZoneId.of("UTC"))) + " TO *]");
				listeRecherche.add("json.facet", "{inscriptionCles:{terms:{field:inscriptionCles_indexed_longs, limit:1000}}}");
				listeRecherche.setRows(1000);
				listeRecherche.initLoinListeRecherche(requeteSite);
				List<Future> futures = new ArrayList<>();

				for(int i=0; i < pks.size(); i++) {
					Long pk2 = pks.get(i);
					String classeNomSimple2 = classes.get(i);

					if("InscriptionMedicale".equals(classeNomSimple2) && pk2 != null) {
						ListeRecherche<InscriptionMedicale> listeRecherche2 = new ListeRecherche<InscriptionMedicale>();
						listeRecherche2.setStocker(true);
						listeRecherche2.setQuery("*:*");
						listeRecherche2.setC(InscriptionMedicale.class);
						listeRecherche2.addFilterQuery("pk_indexed_long:" + pk2);
						listeRecherche2.setRows(1);
						listeRecherche2.initLoinListeRecherche(requeteSite);
						InscriptionMedicale o2 = listeRecherche2.getList().stream().findFirst().orElse(null);

						if(o2 != null) {
							InscriptionMedicaleFrFRGenApiServiceImpl service = new InscriptionMedicaleFrFRGenApiServiceImpl(requeteSite.getSiteContexte_());
							RequeteSiteFrFR requeteSite2 = genererRequeteSiteFrFRPourPatientMedicale(siteContexte, requeteSite.getOperationRequete(), new JsonObject());
							RequeteApi requeteApi2 = new RequeteApi();
							requeteApi2.setRows(1);
							requeteApi2.setNumFound(1l);
							requeteApi2.setNumPATCH(0L);
							requeteApi2.initLoinRequeteApi(requeteSite2);
							requeteSite2.setRequeteApi_(requeteApi2);
							requeteSite2.getVertx().eventBus().publish("websocketInscriptionMedicale", JsonObject.mapFrom(requeteApi2).toString());

							o2.setPk(pk2);
							o2.setRequeteSite_(requeteSite2);
							futures.add(
								service.patchInscriptionMedicaleFuture(o2, false, a -> {
									if(a.succeeded()) {
									} else {
										LOGGER.info(String.format("InscriptionMedicale %s a échoué. ", pk2));
										gestionnaireEvenements.handle(Future.failedFuture(a.cause()));
									}
								})
							);
						}
					}
				}

				CompositeFuture.all(futures).setHandler(a -> {
					if(a.succeeded()) {
						PatientMedicaleFrFRApiServiceImpl service = new PatientMedicaleFrFRApiServiceImpl(requeteSite.getSiteContexte_());
						List<Future> futures2 = new ArrayList<>();
						for(PatientMedicale o2 : listeRecherche.getList()) {
							RequeteSiteFrFR requeteSite2 = genererRequeteSiteFrFRPourPatientMedicale(siteContexte, requeteSite.getOperationRequete(), new JsonObject());
							o2.setRequeteSite_(requeteSite2);
							futures2.add(
								service.patchPatientMedicaleFuture(o2, false, b -> {
									if(b.succeeded()) {
									} else {
										LOGGER.info(String.format("PatientMedicale %s a échoué. ", o2.getPk()));
										gestionnaireEvenements.handle(Future.failedFuture(b.cause()));
									}
								})
							);
						}

						CompositeFuture.all(futures2).setHandler(b -> {
							if(b.succeeded()) {
								gestionnaireEvenements.handle(Future.succeededFuture());
							} else {
								LOGGER.error("Recharger relations a échoué. ", b.cause());
								erreurPatientMedicale(requeteSite, gestionnaireEvenements, b);
							}
						});
					} else {
						LOGGER.error("Recharger relations a échoué. ", a.cause());
						erreurPatientMedicale(requeteSite, gestionnaireEvenements, a);
					}
				});
			} else {
				gestionnaireEvenements.handle(Future.succeededFuture());
			}
		} catch(Exception e) {
			LOGGER.error(String.format("rechargerPatientMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}
}
