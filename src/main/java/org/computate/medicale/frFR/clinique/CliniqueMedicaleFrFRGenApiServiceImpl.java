package org.computate.medicale.frFR.clinique;

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
 * NomCanonique.enUS: org.computate.medicale.enUS.clinic.MedicalClinicEnUSGenApiServiceImpl
 **/
public class CliniqueMedicaleFrFRGenApiServiceImpl implements CliniqueMedicaleFrFRGenApiService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(CliniqueMedicaleFrFRGenApiServiceImpl.class);

	protected static final String SERVICE_ADDRESS = "CliniqueMedicaleFrFRApiServiceImpl";

	protected SiteContexteFrFR siteContexte;

	public CliniqueMedicaleFrFRGenApiServiceImpl(SiteContexteFrFR siteContexte) {
		this.siteContexte = siteContexte;
	}

	// POST //

	@Override
	public void postCliniqueMedicale(JsonObject body, OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = genererRequeteSiteFrFRPourCliniqueMedicale(siteContexte, operationRequete, body);
		try {
			LOGGER.info(String.format("postCliniqueMedicale a démarré. "));

			List<String> roles = Arrays.asList("SiteAdmin");
			if(
					!CollectionUtils.containsAny(requeteSite.getUtilisateurRolesRessource(), roles)
					&& !CollectionUtils.containsAny(requeteSite.getUtilisateurRolesRoyaume(), roles)
					) {
				gestionnaireEvenements.handle(Future.succeededFuture(
					new OperationResponse(401, "UNAUTHORIZED", 
						Buffer.buffer().appendString(
							new JsonObject()
								.put("errorCode", "401")
								.put("errorMessage", "rôles requis : " + String.join(", ", roles))
								.encodePrettily()
							), new CaseInsensitiveHeaders()
					)
				));
			}

			utilisateurCliniqueMedicale(requeteSite, b -> {
				if(b.succeeded()) {
					RequeteApi requeteApi = new RequeteApi();
					requeteApi.setRows(1);
					requeteApi.setNumFound(1L);
					requeteApi.setNumPATCH(0L);
					requeteApi.initLoinRequeteApi(requeteSite);
					requeteSite.setRequeteApi_(requeteApi);
					requeteSite.getVertx().eventBus().publish("websocketCliniqueMedicale", JsonObject.mapFrom(requeteApi).toString());
					postCliniqueMedicaleFuture(requeteSite, false, c -> {
						if(c.succeeded()) {
							CliniqueMedicale cliniqueMedicale = c.result();
							requeteApi.setPk(cliniqueMedicale.getPk());
							postCliniqueMedicaleReponse(cliniqueMedicale, d -> {
									if(d.succeeded()) {
									gestionnaireEvenements.handle(Future.succeededFuture(d.result()));
									LOGGER.info(String.format("postCliniqueMedicale a réussi. "));
								} else {
									LOGGER.error(String.format("postCliniqueMedicale a échoué. ", d.cause()));
									erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, d);
								}
							});
						} else {
							LOGGER.error(String.format("postCliniqueMedicale a échoué. ", c.cause()));
							erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, c);
						}
					});
				} else {
					LOGGER.error(String.format("postCliniqueMedicale a échoué. ", b.cause()));
					erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("postCliniqueMedicale a échoué. ", ex));
			erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, Future.failedFuture(ex));
		}
	}


	public Future<CliniqueMedicale> postCliniqueMedicaleFuture(RequeteSiteFrFR requeteSite, Boolean inheritPk, Handler<AsyncResult<CliniqueMedicale>> gestionnaireEvenements) {
		Promise<CliniqueMedicale> promise = Promise.promise();
		try {
			sqlConnexionCliniqueMedicale(requeteSite, a -> {
				if(a.succeeded()) {
					sqlTransactionCliniqueMedicale(requeteSite, b -> {
						if(b.succeeded()) {
							creerCliniqueMedicale(requeteSite, c -> {
								if(c.succeeded()) {
									CliniqueMedicale cliniqueMedicale = c.result();
									sqlPOSTCliniqueMedicale(cliniqueMedicale, inheritPk, d -> {
										if(d.succeeded()) {
											definirIndexerCliniqueMedicale(cliniqueMedicale, e -> {
												if(e.succeeded()) {
													RequeteApi requeteApi = requeteSite.getRequeteApi_();
													if(requeteApi != null) {
														requeteApi.setNumPATCH(requeteApi.getNumPATCH() + 1);
														cliniqueMedicale.requeteApiCliniqueMedicale();
														requeteSite.getVertx().eventBus().publish("websocketCliniqueMedicale", JsonObject.mapFrom(requeteApi).toString());
													}
													gestionnaireEvenements.handle(Future.succeededFuture(cliniqueMedicale));
													promise.complete(cliniqueMedicale);
												} else {
													LOGGER.error(String.format("postCliniqueMedicaleFuture a échoué. ", e.cause()));
													gestionnaireEvenements.handle(Future.failedFuture(e.cause()));
												}
											});
										} else {
											LOGGER.error(String.format("postCliniqueMedicaleFuture a échoué. ", d.cause()));
											gestionnaireEvenements.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOGGER.error(String.format("postCliniqueMedicaleFuture a échoué. ", c.cause()));
									gestionnaireEvenements.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOGGER.error(String.format("postCliniqueMedicaleFuture a échoué. ", b.cause()));
							gestionnaireEvenements.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOGGER.error(String.format("postCliniqueMedicaleFuture a échoué. ", a.cause()));
					gestionnaireEvenements.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("postCliniqueMedicaleFuture a échoué. ", e));
			erreurCliniqueMedicale(requeteSite, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPOSTCliniqueMedicale(CliniqueMedicale o, Boolean inheritPk, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
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
									a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.inheritPk a échoué", b.cause())));
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
									a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.archive a échoué", b.cause())));
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
									a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.supprime a échoué", b.cause())));
							});
						}));
						break;
					case "cliniqueNom":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContexteFrFR.SQL_setD
									, Tuple.of(pk, "cliniqueNom", Optional.ofNullable(jsonObject.getValue(entiteVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.cliniqueNom a échoué", b.cause())));
							});
						}));
						break;
					case "cliniqueNumeroTelephone":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContexteFrFR.SQL_setD
									, Tuple.of(pk, "cliniqueNumeroTelephone", Optional.ofNullable(jsonObject.getValue(entiteVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.cliniqueNumeroTelephone a échoué", b.cause())));
							});
						}));
						break;
					case "cliniqueAdministrateurNom":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContexteFrFR.SQL_setD
									, Tuple.of(pk, "cliniqueAdministrateurNom", Optional.ofNullable(jsonObject.getValue(entiteVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.cliniqueAdministrateurNom a échoué", b.cause())));
							});
						}));
						break;
					case "cliniqueMail":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContexteFrFR.SQL_setD
									, Tuple.of(pk, "cliniqueMail", Optional.ofNullable(jsonObject.getValue(entiteVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.cliniqueMail a échoué", b.cause())));
							});
						}));
						break;
					case "cliniqueMailDe":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContexteFrFR.SQL_setD
									, Tuple.of(pk, "cliniqueMailDe", Optional.ofNullable(jsonObject.getValue(entiteVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.cliniqueMailDe a échoué", b.cause())));
							});
						}));
						break;
					case "cliniqueMailA":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContexteFrFR.SQL_setD
									, Tuple.of(pk, "cliniqueMailA", Optional.ofNullable(jsonObject.getValue(entiteVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.cliniqueMailA a échoué", b.cause())));
							});
						}));
						break;
					case "cliniqueEmplacement":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContexteFrFR.SQL_setD
									, Tuple.of(pk, "cliniqueEmplacement", Optional.ofNullable(jsonObject.getValue(entiteVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.cliniqueEmplacement a échoué", b.cause())));
							});
						}));
						break;
					case "cliniqueAddresse":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContexteFrFR.SQL_setD
									, Tuple.of(pk, "cliniqueAddresse", Optional.ofNullable(jsonObject.getValue(entiteVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.cliniqueAddresse a échoué", b.cause())));
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
												, Tuple.of(l2, "cliniqueCle", pk, "inscriptionCles")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.inscriptionCles a échoué", b.cause())));
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
					}
				}
			}
			CompositeFuture.all(futures).setHandler( a -> {
				if(a.succeeded()) {
					gestionnaireEvenements.handle(Future.succeededFuture());
				} else {
					LOGGER.error(String.format("sqlPOSTCliniqueMedicale a échoué. ", a.cause()));
					gestionnaireEvenements.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPOSTCliniqueMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void postCliniqueMedicaleReponse(CliniqueMedicale cliniqueMedicale, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = cliniqueMedicale.getRequeteSite_();
		try {
			reponse200POSTCliniqueMedicale(cliniqueMedicale, a -> {
				if(a.succeeded()) {
					gestionnaireEvenements.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("postCliniqueMedicaleReponse a échoué. ", a.cause()));
					erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("postCliniqueMedicaleReponse a échoué. ", ex));
			erreurCliniqueMedicale(requeteSite, null, Future.failedFuture(ex));
		}
	}
	public void reponse200POSTCliniqueMedicale(CliniqueMedicale o, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = o.getRequeteSite_();
			JsonObject json = JsonObject.mapFrom(o);
			gestionnaireEvenements.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("reponse200POSTCliniqueMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	// PATCH //

	@Override
	public void patchCliniqueMedicale(JsonObject body, OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = genererRequeteSiteFrFRPourCliniqueMedicale(siteContexte, operationRequete, body);
		try {
			LOGGER.info(String.format("patchCliniqueMedicale a démarré. "));

			List<String> roles = Arrays.asList("SiteAdmin");
			if(
					!CollectionUtils.containsAny(requeteSite.getUtilisateurRolesRessource(), roles)
					&& !CollectionUtils.containsAny(requeteSite.getUtilisateurRolesRoyaume(), roles)
					) {
				gestionnaireEvenements.handle(Future.succeededFuture(
					new OperationResponse(401, "UNAUTHORIZED", 
						Buffer.buffer().appendString(
							new JsonObject()
								.put("errorCode", "401")
								.put("errorMessage", "rôles requis : " + String.join(", ", roles))
								.encodePrettily()
							), new CaseInsensitiveHeaders()
					)
				));
			}

			utilisateurCliniqueMedicale(requeteSite, b -> {
				if(b.succeeded()) {
					patchCliniqueMedicaleReponse(requeteSite, c -> {
						if(c.succeeded()) {
							gestionnaireEvenements.handle(Future.succeededFuture(c.result()));
							WorkerExecutor executeurTravailleur = siteContexte.getExecuteurTravailleur();
							executeurTravailleur.executeBlocking(
								blockingCodeHandler -> {
									try {
										rechercheCliniqueMedicale(requeteSite, false, true, "/api/clinique", "PATCH", d -> {
											if(d.succeeded()) {
												ListeRecherche<CliniqueMedicale> listeCliniqueMedicale = d.result();
												RequeteApi requeteApi = new RequeteApi();
												requeteApi.setRows(listeCliniqueMedicale.getRows());
												requeteApi.setNumFound(listeCliniqueMedicale.getQueryResponse().getResults().getNumFound());
												requeteApi.setNumPATCH(0L);
												requeteApi.initLoinRequeteApi(requeteSite);
												requeteSite.setRequeteApi_(requeteApi);
												requeteSite.getVertx().eventBus().publish("websocketCliniqueMedicale", JsonObject.mapFrom(requeteApi).toString());
												SimpleOrderedMap facets = (SimpleOrderedMap)Optional.ofNullable(listeCliniqueMedicale.getQueryResponse()).map(QueryResponse::getResponse).map(r -> r.get("facets")).orElse(null);
												Date date = null;
												if(facets != null)
													date = (Date)facets.get("max_modifie");
												String dt;
												if(date == null)
													dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(ZonedDateTime.now().toInstant(), ZoneId.of("UTC")).minusNanos(1000));
												else
													dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC")));
												listeCliniqueMedicale.addFilterQuery(String.format("modifie_indexed_date:[* TO %s]", dt));

												try {
													listePATCHCliniqueMedicale(requeteApi, listeCliniqueMedicale, dt, e -> {
														if(e.succeeded()) {
															patchCliniqueMedicaleReponse(requeteSite, f -> {
																if(f.succeeded()) {
																	LOGGER.info(String.format("patchCliniqueMedicale a réussi. "));
																	blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																} else {
																	LOGGER.error(String.format("patchCliniqueMedicale a échoué. ", f.cause()));
																	erreurCliniqueMedicale(requeteSite, null, f);
																}
															});
														} else {
															LOGGER.error(String.format("patchCliniqueMedicale a échoué. ", e.cause()));
															erreurCliniqueMedicale(requeteSite, null, e);
														}
													});
												} catch(Exception ex) {
													LOGGER.error(String.format("patchCliniqueMedicale a échoué. ", ex));
													erreurCliniqueMedicale(requeteSite, null, Future.failedFuture(ex));
												}
											} else {
												LOGGER.error(String.format("patchCliniqueMedicale a échoué. ", d.cause()));
												erreurCliniqueMedicale(requeteSite, null, d);
											}
										});
									} catch(Exception ex) {
										LOGGER.error(String.format("patchCliniqueMedicale a échoué. ", ex));
										erreurCliniqueMedicale(requeteSite, null, Future.failedFuture(ex));
									}
								}, resultHandler -> {
								}
							);
						} else {
							LOGGER.error(String.format("patchCliniqueMedicale a échoué. ", c.cause()));
							erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, c);
						}
					});
				} else {
					LOGGER.error(String.format("patchCliniqueMedicale a échoué. ", b.cause()));
					erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("patchCliniqueMedicale a échoué. ", ex));
			erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, Future.failedFuture(ex));
		}
	}


	public void listePATCHCliniqueMedicale(RequeteApi requeteApi, ListeRecherche<CliniqueMedicale> listeCliniqueMedicale, String dt, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		List<Future> futures = new ArrayList<>();
		RequeteSiteFrFR requeteSite = listeCliniqueMedicale.getRequeteSite_();
		listeCliniqueMedicale.getList().forEach(o -> {
			RequeteSiteFrFR requeteSite2 = genererRequeteSiteFrFRPourCliniqueMedicale(siteContexte, requeteSite.getOperationRequete(), requeteSite.getObjetJson());
			requeteSite2.setRequeteApi_(requeteSite.getRequeteApi_());
			o.setRequeteSite_(requeteSite2);
			futures.add(
				patchCliniqueMedicaleFuture(o, false, a -> {
					if(a.succeeded()) {
					} else {
						erreurCliniqueMedicale(requeteSite2, gestionnaireEvenements, a);
					}
				})
			);
		});
		CompositeFuture.all(futures).setHandler( a -> {
			if(a.succeeded()) {
				if(listeCliniqueMedicale.next(dt)) {
					listePATCHCliniqueMedicale(requeteApi, listeCliniqueMedicale, dt, gestionnaireEvenements);
				} else {
					reponse200PATCHCliniqueMedicale(requeteSite, gestionnaireEvenements);
				}
			} else {
				LOGGER.error(String.format("listePATCHCliniqueMedicale a échoué. ", a.cause()));
				erreurCliniqueMedicale(listeCliniqueMedicale.getRequeteSite_(), gestionnaireEvenements, a);
			}
		});
	}

	public Future<CliniqueMedicale> patchCliniqueMedicaleFuture(CliniqueMedicale o, Boolean inheritPk, Handler<AsyncResult<CliniqueMedicale>> gestionnaireEvenements) {
		Promise<CliniqueMedicale> promise = Promise.promise();
		RequeteSiteFrFR requeteSite = o.getRequeteSite_();
		try {
			RequeteApi requeteApi = requeteSite.getRequeteApi_();
			if(requeteApi != null && requeteApi.getNumFound() == 1L) {
				requeteApi.setOriginal(o);
				requeteApi.setPk(o.getPk());
			}
			sqlConnexionCliniqueMedicale(requeteSite, a -> {
				if(a.succeeded()) {
					sqlTransactionCliniqueMedicale(requeteSite, b -> {
						if(b.succeeded()) {
							sqlPATCHCliniqueMedicale(o, inheritPk, c -> {
								if(c.succeeded()) {
									CliniqueMedicale cliniqueMedicale = c.result();
									definirIndexerCliniqueMedicale(cliniqueMedicale, d -> {
										if(d.succeeded()) {
											if(requeteApi != null) {
												requeteApi.setNumPATCH(requeteApi.getNumPATCH() + 1);
												if(requeteApi.getNumFound() == 1L) {
													cliniqueMedicale.requeteApiCliniqueMedicale();
												}
												requeteSite.getVertx().eventBus().publish("websocketCliniqueMedicale", JsonObject.mapFrom(requeteApi).toString());
											}
											gestionnaireEvenements.handle(Future.succeededFuture(cliniqueMedicale));
											promise.complete(cliniqueMedicale);
										} else {
											LOGGER.error(String.format("patchCliniqueMedicaleFuture a échoué. ", d.cause()));
											gestionnaireEvenements.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOGGER.error(String.format("patchCliniqueMedicaleFuture a échoué. ", c.cause()));
									gestionnaireEvenements.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOGGER.error(String.format("patchCliniqueMedicaleFuture a échoué. ", b.cause()));
							gestionnaireEvenements.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOGGER.error(String.format("patchCliniqueMedicaleFuture a échoué. ", a.cause()));
					gestionnaireEvenements.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("patchCliniqueMedicaleFuture a échoué. ", e));
			erreurCliniqueMedicale(requeteSite, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPATCHCliniqueMedicale(CliniqueMedicale o, Boolean inheritPk, Handler<AsyncResult<CliniqueMedicale>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = o.getRequeteSite_();
			RequeteApi requeteApi = requeteSite.getRequeteApi_();
			List<Long> pks = Optional.ofNullable(requeteApi).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(requeteApi).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Transaction tx = requeteSite.getTx();
			Long pk = o.getPk();
			JsonObject jsonObject = requeteSite.getObjetJson();
			Set<String> methodeNoms = jsonObject.fieldNames();
			CliniqueMedicale o2 = new CliniqueMedicale();
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
										a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.inheritPk a échoué", b.cause())));
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
										a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.inheritPk a échoué", b.cause())));
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
										a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.archive a échoué", b.cause())));
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
										a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.archive a échoué", b.cause())));
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
										a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.supprime a échoué", b.cause())));
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
										a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.supprime a échoué", b.cause())));
								});
							}));
						}
						break;
					case "setCliniqueNom":
						if(jsonObject.getString(methodeNom) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContexteFrFR.SQL_removeD
										, Tuple.of(pk, "cliniqueNom")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.cliniqueNom a échoué", b.cause())));
								});
							}));
						} else {
							o2.setCliniqueNom(jsonObject.getString(methodeNom));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContexteFrFR.SQL_setD
										, Tuple.of(pk, "cliniqueNom", o2.jsonCliniqueNom())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.cliniqueNom a échoué", b.cause())));
								});
							}));
						}
						break;
					case "setCliniqueNumeroTelephone":
						if(jsonObject.getString(methodeNom) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContexteFrFR.SQL_removeD
										, Tuple.of(pk, "cliniqueNumeroTelephone")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.cliniqueNumeroTelephone a échoué", b.cause())));
								});
							}));
						} else {
							o2.setCliniqueNumeroTelephone(jsonObject.getString(methodeNom));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContexteFrFR.SQL_setD
										, Tuple.of(pk, "cliniqueNumeroTelephone", o2.jsonCliniqueNumeroTelephone())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.cliniqueNumeroTelephone a échoué", b.cause())));
								});
							}));
						}
						break;
					case "setCliniqueAdministrateurNom":
						if(jsonObject.getString(methodeNom) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContexteFrFR.SQL_removeD
										, Tuple.of(pk, "cliniqueAdministrateurNom")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.cliniqueAdministrateurNom a échoué", b.cause())));
								});
							}));
						} else {
							o2.setCliniqueAdministrateurNom(jsonObject.getString(methodeNom));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContexteFrFR.SQL_setD
										, Tuple.of(pk, "cliniqueAdministrateurNom", o2.jsonCliniqueAdministrateurNom())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.cliniqueAdministrateurNom a échoué", b.cause())));
								});
							}));
						}
						break;
					case "setCliniqueMail":
						if(jsonObject.getString(methodeNom) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContexteFrFR.SQL_removeD
										, Tuple.of(pk, "cliniqueMail")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.cliniqueMail a échoué", b.cause())));
								});
							}));
						} else {
							o2.setCliniqueMail(jsonObject.getString(methodeNom));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContexteFrFR.SQL_setD
										, Tuple.of(pk, "cliniqueMail", o2.jsonCliniqueMail())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.cliniqueMail a échoué", b.cause())));
								});
							}));
						}
						break;
					case "setCliniqueMailDe":
						if(jsonObject.getString(methodeNom) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContexteFrFR.SQL_removeD
										, Tuple.of(pk, "cliniqueMailDe")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.cliniqueMailDe a échoué", b.cause())));
								});
							}));
						} else {
							o2.setCliniqueMailDe(jsonObject.getString(methodeNom));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContexteFrFR.SQL_setD
										, Tuple.of(pk, "cliniqueMailDe", o2.jsonCliniqueMailDe())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.cliniqueMailDe a échoué", b.cause())));
								});
							}));
						}
						break;
					case "setCliniqueMailA":
						if(jsonObject.getString(methodeNom) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContexteFrFR.SQL_removeD
										, Tuple.of(pk, "cliniqueMailA")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.cliniqueMailA a échoué", b.cause())));
								});
							}));
						} else {
							o2.setCliniqueMailA(jsonObject.getString(methodeNom));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContexteFrFR.SQL_setD
										, Tuple.of(pk, "cliniqueMailA", o2.jsonCliniqueMailA())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.cliniqueMailA a échoué", b.cause())));
								});
							}));
						}
						break;
					case "setCliniqueEmplacement":
						if(jsonObject.getString(methodeNom) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContexteFrFR.SQL_removeD
										, Tuple.of(pk, "cliniqueEmplacement")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.cliniqueEmplacement a échoué", b.cause())));
								});
							}));
						} else {
							o2.setCliniqueEmplacement(jsonObject.getString(methodeNom));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContexteFrFR.SQL_setD
										, Tuple.of(pk, "cliniqueEmplacement", o2.jsonCliniqueEmplacement())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.cliniqueEmplacement a échoué", b.cause())));
								});
							}));
						}
						break;
					case "setCliniqueAddresse":
						if(jsonObject.getString(methodeNom) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContexteFrFR.SQL_removeD
										, Tuple.of(pk, "cliniqueAddresse")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.cliniqueAddresse a échoué", b.cause())));
								});
							}));
						} else {
							o2.setCliniqueAddresse(jsonObject.getString(methodeNom));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContexteFrFR.SQL_setD
										, Tuple.of(pk, "cliniqueAddresse", o2.jsonCliniqueAddresse())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.cliniqueAddresse a échoué", b.cause())));
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
												, Tuple.of(l2, "cliniqueCle", pk, "inscriptionCles")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.inscriptionCles a échoué", b.cause())));
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
												, Tuple.of(l2, "cliniqueCle", pk, "inscriptionCles")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.inscriptionCles a échoué", b.cause())));
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
												, Tuple.of(l2, "cliniqueCle", pk, "inscriptionCles")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.inscriptionCles a échoué", b.cause())));
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
												, Tuple.of(l, "cliniqueCle", pk, "inscriptionCles")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.inscriptionCles a échoué", b.cause())));
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
												, Tuple.of(l2, "cliniqueCle", pk, "inscriptionCles")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.inscriptionCles a échoué", b.cause())));
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
				}
			}
			CompositeFuture.all(futures).setHandler( a -> {
				if(a.succeeded()) {
					CliniqueMedicale o3 = new CliniqueMedicale();
					o3.setRequeteSite_(o.getRequeteSite_());
					o3.setPk(pk);
					gestionnaireEvenements.handle(Future.succeededFuture(o3));
				} else {
					LOGGER.error(String.format("sqlPATCHCliniqueMedicale a échoué. ", a.cause()));
					gestionnaireEvenements.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPATCHCliniqueMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void patchCliniqueMedicaleReponse(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			reponse200PATCHCliniqueMedicale(requeteSite, a -> {
				if(a.succeeded()) {
					gestionnaireEvenements.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("patchCliniqueMedicaleReponse a échoué. ", a.cause()));
					erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("patchCliniqueMedicaleReponse a échoué. ", ex));
			erreurCliniqueMedicale(requeteSite, null, Future.failedFuture(ex));
		}
	}
	public void reponse200PATCHCliniqueMedicale(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			JsonObject json = new JsonObject();
			gestionnaireEvenements.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("reponse200PATCHCliniqueMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	// GET //

	@Override
	public void getCliniqueMedicale(OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = genererRequeteSiteFrFRPourCliniqueMedicale(siteContexte, operationRequete);
		try {

			List<String> roles = Arrays.asList("SiteAdmin");
			List<String> roleLires = Arrays.asList("");
			if(
					!CollectionUtils.containsAny(requeteSite.getUtilisateurRolesRessource(), roles)
					&& !CollectionUtils.containsAny(requeteSite.getUtilisateurRolesRoyaume(), roles)
					&& !CollectionUtils.containsAny(requeteSite.getUtilisateurRolesRessource(), roleLires)
					&& !CollectionUtils.containsAny(requeteSite.getUtilisateurRolesRoyaume(), roleLires)
					) {
				gestionnaireEvenements.handle(Future.succeededFuture(
					new OperationResponse(401, "UNAUTHORIZED", 
						Buffer.buffer().appendString(
							new JsonObject()
								.put("errorCode", "401")
								.put("errorMessage", "rôles requis : " + String.join(", ", roles))
								.encodePrettily()
							), new CaseInsensitiveHeaders()
					)
				));
			}

			utilisateurCliniqueMedicale(requeteSite, b -> {
				if(b.succeeded()) {
					rechercheCliniqueMedicale(requeteSite, false, true, "/api/clinique/{id}", "GET", c -> {
						if(c.succeeded()) {
							ListeRecherche<CliniqueMedicale> listeCliniqueMedicale = c.result();
							getCliniqueMedicaleReponse(listeCliniqueMedicale, d -> {
								if(d.succeeded()) {
									gestionnaireEvenements.handle(Future.succeededFuture(d.result()));
									LOGGER.info(String.format("getCliniqueMedicale a réussi. "));
								} else {
									LOGGER.error(String.format("getCliniqueMedicale a échoué. ", d.cause()));
									erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, d);
								}
							});
						} else {
							LOGGER.error(String.format("getCliniqueMedicale a échoué. ", c.cause()));
							erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, c);
						}
					});
				} else {
					LOGGER.error(String.format("getCliniqueMedicale a échoué. ", b.cause()));
					erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("getCliniqueMedicale a échoué. ", ex));
			erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, Future.failedFuture(ex));
		}
	}


	public void getCliniqueMedicaleReponse(ListeRecherche<CliniqueMedicale> listeCliniqueMedicale, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = listeCliniqueMedicale.getRequeteSite_();
		try {
			reponse200GETCliniqueMedicale(listeCliniqueMedicale, a -> {
				if(a.succeeded()) {
					gestionnaireEvenements.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("getCliniqueMedicaleReponse a échoué. ", a.cause()));
					erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("getCliniqueMedicaleReponse a échoué. ", ex));
			erreurCliniqueMedicale(requeteSite, null, Future.failedFuture(ex));
		}
	}
	public void reponse200GETCliniqueMedicale(ListeRecherche<CliniqueMedicale> listeCliniqueMedicale, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = listeCliniqueMedicale.getRequeteSite_();
			SolrDocumentList documentsSolr = listeCliniqueMedicale.getSolrDocumentList();

			JsonObject json = JsonObject.mapFrom(listeCliniqueMedicale.getList().stream().findFirst().orElse(null));
			gestionnaireEvenements.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("reponse200GETCliniqueMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	// Recherche //

	@Override
	public void rechercheCliniqueMedicale(OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = genererRequeteSiteFrFRPourCliniqueMedicale(siteContexte, operationRequete);
		try {

			List<String> roles = Arrays.asList("SiteAdmin");
			List<String> roleLires = Arrays.asList("");
			if(
					!CollectionUtils.containsAny(requeteSite.getUtilisateurRolesRessource(), roles)
					&& !CollectionUtils.containsAny(requeteSite.getUtilisateurRolesRoyaume(), roles)
					&& !CollectionUtils.containsAny(requeteSite.getUtilisateurRolesRessource(), roleLires)
					&& !CollectionUtils.containsAny(requeteSite.getUtilisateurRolesRoyaume(), roleLires)
					) {
				gestionnaireEvenements.handle(Future.succeededFuture(
					new OperationResponse(401, "UNAUTHORIZED", 
						Buffer.buffer().appendString(
							new JsonObject()
								.put("errorCode", "401")
								.put("errorMessage", "rôles requis : " + String.join(", ", roles))
								.encodePrettily()
							), new CaseInsensitiveHeaders()
					)
				));
			}

			utilisateurCliniqueMedicale(requeteSite, b -> {
				if(b.succeeded()) {
					rechercheCliniqueMedicale(requeteSite, false, true, "/api/clinique", "Recherche", c -> {
						if(c.succeeded()) {
							ListeRecherche<CliniqueMedicale> listeCliniqueMedicale = c.result();
							rechercheCliniqueMedicaleReponse(listeCliniqueMedicale, d -> {
								if(d.succeeded()) {
									gestionnaireEvenements.handle(Future.succeededFuture(d.result()));
									LOGGER.info(String.format("rechercheCliniqueMedicale a réussi. "));
								} else {
									LOGGER.error(String.format("rechercheCliniqueMedicale a échoué. ", d.cause()));
									erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, d);
								}
							});
						} else {
							LOGGER.error(String.format("rechercheCliniqueMedicale a échoué. ", c.cause()));
							erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, c);
						}
					});
				} else {
					LOGGER.error(String.format("rechercheCliniqueMedicale a échoué. ", b.cause()));
					erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("rechercheCliniqueMedicale a échoué. ", ex));
			erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, Future.failedFuture(ex));
		}
	}


	public void rechercheCliniqueMedicaleReponse(ListeRecherche<CliniqueMedicale> listeCliniqueMedicale, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = listeCliniqueMedicale.getRequeteSite_();
		try {
			reponse200RechercheCliniqueMedicale(listeCliniqueMedicale, a -> {
				if(a.succeeded()) {
					gestionnaireEvenements.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("rechercheCliniqueMedicaleReponse a échoué. ", a.cause()));
					erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("rechercheCliniqueMedicaleReponse a échoué. ", ex));
			erreurCliniqueMedicale(requeteSite, null, Future.failedFuture(ex));
		}
	}
	public void reponse200RechercheCliniqueMedicale(ListeRecherche<CliniqueMedicale> listeCliniqueMedicale, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = listeCliniqueMedicale.getRequeteSite_();
			QueryResponse reponseRecherche = listeCliniqueMedicale.getQueryResponse();
			SolrDocumentList documentsSolr = listeCliniqueMedicale.getSolrDocumentList();
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
			listeCliniqueMedicale.getList().stream().forEach(o -> {
				JsonObject json2 = JsonObject.mapFrom(o);
				List<String> fls = listeCliniqueMedicale.getFields();
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
			LOGGER.error(String.format("reponse200RechercheCliniqueMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	// PUTImport //

	@Override
	public void putimportCliniqueMedicale(JsonObject body, OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = genererRequeteSiteFrFRPourCliniqueMedicale(siteContexte, operationRequete, body);
		try {
			LOGGER.info(String.format("putimportCliniqueMedicale a démarré. "));

			List<String> roles = Arrays.asList("SiteAdmin");
			if(
					!CollectionUtils.containsAny(requeteSite.getUtilisateurRolesRessource(), roles)
					&& !CollectionUtils.containsAny(requeteSite.getUtilisateurRolesRoyaume(), roles)
					) {
				gestionnaireEvenements.handle(Future.succeededFuture(
					new OperationResponse(401, "UNAUTHORIZED", 
						Buffer.buffer().appendString(
							new JsonObject()
								.put("errorCode", "401")
								.put("errorMessage", "rôles requis : " + String.join(", ", roles))
								.encodePrettily()
							), new CaseInsensitiveHeaders()
					)
				));
			}

			utilisateurCliniqueMedicale(requeteSite, b -> {
				if(b.succeeded()) {
					putimportCliniqueMedicaleReponse(requeteSite, c -> {
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
										requeteSite.getVertx().eventBus().publish("websocketCliniqueMedicale", JsonObject.mapFrom(requeteApi).toString());
										varsCliniqueMedicale(requeteSite, d -> {
											if(d.succeeded()) {
												listePUTImportCliniqueMedicale(requeteApi, requeteSite, e -> {
													if(e.succeeded()) {
														putimportCliniqueMedicaleReponse(requeteSite, f -> {
															if(e.succeeded()) {
																LOGGER.info(String.format("putimportCliniqueMedicale a réussi. "));
																blockingCodeHandler.handle(Future.succeededFuture(e.result()));
															} else {
																LOGGER.error(String.format("putimportCliniqueMedicale a échoué. ", f.cause()));
																erreurCliniqueMedicale(requeteSite, null, f);
															}
														});
													} else {
														LOGGER.error(String.format("putimportCliniqueMedicale a échoué. ", e.cause()));
														erreurCliniqueMedicale(requeteSite, null, e);
													}
												});
											} else {
												LOGGER.error(String.format("putimportCliniqueMedicale a échoué. ", d.cause()));
												erreurCliniqueMedicale(requeteSite, null, d);
											}
										});
									} catch(Exception ex) {
										LOGGER.error(String.format("putimportCliniqueMedicale a échoué. ", ex));
										erreurCliniqueMedicale(requeteSite, null, Future.failedFuture(ex));
									}
								}, resultHandler -> {
								}
							);
						} else {
							LOGGER.error(String.format("putimportCliniqueMedicale a échoué. ", c.cause()));
							erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, c);
						}
					});
				} else {
					LOGGER.error(String.format("putimportCliniqueMedicale a échoué. ", b.cause()));
					erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putimportCliniqueMedicale a échoué. ", ex));
			erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, Future.failedFuture(ex));
		}
	}


	public void listePUTImportCliniqueMedicale(RequeteApi requeteApi, RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		List<Future> futures = new ArrayList<>();
		JsonArray jsonArray = Optional.ofNullable(requeteSite.getObjetJson()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
		try {
			jsonArray.forEach(obj -> {
				JsonObject json = (JsonObject)obj;

				json.put("inheritPk", json.getValue("pk"));

				json.put("cree", json.getValue("cree"));

				RequeteSiteFrFR requeteSite2 = genererRequeteSiteFrFRPourCliniqueMedicale(siteContexte, requeteSite.getOperationRequete(), json);
				requeteSite2.setRequeteApi_(requeteApi);
				requeteSite2.setRequeteVars(requeteSite.getRequeteVars());

				ListeRecherche<CliniqueMedicale> listeRecherche = new ListeRecherche<CliniqueMedicale>();
				listeRecherche.setStocker(true);
				listeRecherche.setQuery("*:*");
				listeRecherche.setC(CliniqueMedicale.class);
				listeRecherche.addFilterQuery("inheritPk_indexed_long:" + json.getString("pk"));
				listeRecherche.initLoinPourClasse(requeteSite2);

				if(listeRecherche.size() == 1) {
					CliniqueMedicale o = listeRecherche.getList().stream().findFirst().orElse(null);
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
							patchCliniqueMedicaleFuture(o, true, a -> {
								if(a.succeeded()) {
								} else {
									LOGGER.error(String.format("listePUTImportCliniqueMedicale a échoué. ", a.cause()));
									erreurCliniqueMedicale(requeteSite2, gestionnaireEvenements, a);
								}
							})
						);
					}
				} else {
					futures.add(
						postCliniqueMedicaleFuture(requeteSite2, true, a -> {
							if(a.succeeded()) {
							} else {
								LOGGER.error(String.format("listePUTImportCliniqueMedicale a échoué. ", a.cause()));
								erreurCliniqueMedicale(requeteSite2, gestionnaireEvenements, a);
							}
						})
					);
				}
			});
			CompositeFuture.all(futures).setHandler( a -> {
				if(a.succeeded()) {
					requeteApi.setNumPATCH(requeteApi.getNumPATCH() + 1);
					reponse200PUTImportCliniqueMedicale(requeteSite, gestionnaireEvenements);
				} else {
					LOGGER.error(String.format("listePUTImportCliniqueMedicale a échoué. ", a.cause()));
					erreurCliniqueMedicale(requeteApi.getRequeteSite_(), gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("listePUTImportCliniqueMedicale a échoué. ", ex));
			erreurCliniqueMedicale(requeteSite, null, Future.failedFuture(ex));
		}
	}

	public void putimportCliniqueMedicaleReponse(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			reponse200PUTImportCliniqueMedicale(requeteSite, a -> {
				if(a.succeeded()) {
					gestionnaireEvenements.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("putimportCliniqueMedicaleReponse a échoué. ", a.cause()));
					erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putimportCliniqueMedicaleReponse a échoué. ", ex));
			erreurCliniqueMedicale(requeteSite, null, Future.failedFuture(ex));
		}
	}
	public void reponse200PUTImportCliniqueMedicale(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			JsonObject json = new JsonObject();
			gestionnaireEvenements.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("reponse200PUTImportCliniqueMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	// PUTFusion //

	@Override
	public void putfusionCliniqueMedicale(JsonObject body, OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = genererRequeteSiteFrFRPourCliniqueMedicale(siteContexte, operationRequete, body);
		try {
			LOGGER.info(String.format("putfusionCliniqueMedicale a démarré. "));

			List<String> roles = Arrays.asList("SiteAdmin");
			if(
					!CollectionUtils.containsAny(requeteSite.getUtilisateurRolesRessource(), roles)
					&& !CollectionUtils.containsAny(requeteSite.getUtilisateurRolesRoyaume(), roles)
					) {
				gestionnaireEvenements.handle(Future.succeededFuture(
					new OperationResponse(401, "UNAUTHORIZED", 
						Buffer.buffer().appendString(
							new JsonObject()
								.put("errorCode", "401")
								.put("errorMessage", "rôles requis : " + String.join(", ", roles))
								.encodePrettily()
							), new CaseInsensitiveHeaders()
					)
				));
			}

			utilisateurCliniqueMedicale(requeteSite, b -> {
				if(b.succeeded()) {
					putfusionCliniqueMedicaleReponse(requeteSite, c -> {
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
										requeteSite.getVertx().eventBus().publish("websocketCliniqueMedicale", JsonObject.mapFrom(requeteApi).toString());
										varsCliniqueMedicale(requeteSite, d -> {
											if(d.succeeded()) {
												listePUTFusionCliniqueMedicale(requeteApi, requeteSite, e -> {
													if(e.succeeded()) {
														putfusionCliniqueMedicaleReponse(requeteSite, f -> {
															if(e.succeeded()) {
																LOGGER.info(String.format("putfusionCliniqueMedicale a réussi. "));
																blockingCodeHandler.handle(Future.succeededFuture(e.result()));
															} else {
																LOGGER.error(String.format("putfusionCliniqueMedicale a échoué. ", f.cause()));
																erreurCliniqueMedicale(requeteSite, null, f);
															}
														});
													} else {
														LOGGER.error(String.format("putfusionCliniqueMedicale a échoué. ", e.cause()));
														erreurCliniqueMedicale(requeteSite, null, e);
													}
												});
											} else {
												LOGGER.error(String.format("putfusionCliniqueMedicale a échoué. ", d.cause()));
												erreurCliniqueMedicale(requeteSite, null, d);
											}
										});
									} catch(Exception ex) {
										LOGGER.error(String.format("putfusionCliniqueMedicale a échoué. ", ex));
										erreurCliniqueMedicale(requeteSite, null, Future.failedFuture(ex));
									}
								}, resultHandler -> {
								}
							);
						} else {
							LOGGER.error(String.format("putfusionCliniqueMedicale a échoué. ", c.cause()));
							erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, c);
						}
					});
				} else {
					LOGGER.error(String.format("putfusionCliniqueMedicale a échoué. ", b.cause()));
					erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putfusionCliniqueMedicale a échoué. ", ex));
			erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, Future.failedFuture(ex));
		}
	}


	public void listePUTFusionCliniqueMedicale(RequeteApi requeteApi, RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		List<Future> futures = new ArrayList<>();
		JsonArray jsonArray = Optional.ofNullable(requeteSite.getObjetJson()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
		try {
			jsonArray.forEach(obj -> {
				JsonObject json = (JsonObject)obj;

				json.put("inheritPk", json.getValue("pk"));

				RequeteSiteFrFR requeteSite2 = genererRequeteSiteFrFRPourCliniqueMedicale(siteContexte, requeteSite.getOperationRequete(), json);
				requeteSite2.setRequeteApi_(requeteApi);
				requeteSite2.setRequeteVars(requeteSite.getRequeteVars());

				ListeRecherche<CliniqueMedicale> listeRecherche = new ListeRecherche<CliniqueMedicale>();
				listeRecherche.setStocker(true);
				listeRecherche.setQuery("*:*");
				listeRecherche.setC(CliniqueMedicale.class);
				listeRecherche.addFilterQuery("pk_indexed_long:" + json.getString("pk"));
				listeRecherche.initLoinPourClasse(requeteSite2);

				if(listeRecherche.size() == 1) {
					CliniqueMedicale o = listeRecherche.getList().stream().findFirst().orElse(null);
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
							patchCliniqueMedicaleFuture(o, false, a -> {
								if(a.succeeded()) {
								} else {
									LOGGER.error(String.format("listePUTFusionCliniqueMedicale a échoué. ", a.cause()));
									erreurCliniqueMedicale(requeteSite2, gestionnaireEvenements, a);
								}
							})
						);
					}
				} else {
					futures.add(
						postCliniqueMedicaleFuture(requeteSite2, false, a -> {
							if(a.succeeded()) {
							} else {
								LOGGER.error(String.format("listePUTFusionCliniqueMedicale a échoué. ", a.cause()));
								erreurCliniqueMedicale(requeteSite2, gestionnaireEvenements, a);
							}
						})
					);
				}
			});
			CompositeFuture.all(futures).setHandler( a -> {
				if(a.succeeded()) {
					requeteApi.setNumPATCH(requeteApi.getNumPATCH() + 1);
					reponse200PUTFusionCliniqueMedicale(requeteSite, gestionnaireEvenements);
				} else {
					LOGGER.error(String.format("listePUTFusionCliniqueMedicale a échoué. ", a.cause()));
					erreurCliniqueMedicale(requeteApi.getRequeteSite_(), gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("listePUTFusionCliniqueMedicale a échoué. ", ex));
			erreurCliniqueMedicale(requeteSite, null, Future.failedFuture(ex));
		}
	}

	public void putfusionCliniqueMedicaleReponse(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			reponse200PUTFusionCliniqueMedicale(requeteSite, a -> {
				if(a.succeeded()) {
					gestionnaireEvenements.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("putfusionCliniqueMedicaleReponse a échoué. ", a.cause()));
					erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putfusionCliniqueMedicaleReponse a échoué. ", ex));
			erreurCliniqueMedicale(requeteSite, null, Future.failedFuture(ex));
		}
	}
	public void reponse200PUTFusionCliniqueMedicale(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			JsonObject json = new JsonObject();
			gestionnaireEvenements.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("reponse200PUTFusionCliniqueMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	// PUTCopie //

	@Override
	public void putcopieCliniqueMedicale(JsonObject body, OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = genererRequeteSiteFrFRPourCliniqueMedicale(siteContexte, operationRequete, body);
		try {
			LOGGER.info(String.format("putcopieCliniqueMedicale a démarré. "));

			List<String> roles = Arrays.asList("SiteAdmin");
			if(
					!CollectionUtils.containsAny(requeteSite.getUtilisateurRolesRessource(), roles)
					&& !CollectionUtils.containsAny(requeteSite.getUtilisateurRolesRoyaume(), roles)
					) {
				gestionnaireEvenements.handle(Future.succeededFuture(
					new OperationResponse(401, "UNAUTHORIZED", 
						Buffer.buffer().appendString(
							new JsonObject()
								.put("errorCode", "401")
								.put("errorMessage", "rôles requis : " + String.join(", ", roles))
								.encodePrettily()
							), new CaseInsensitiveHeaders()
					)
				));
			}

			utilisateurCliniqueMedicale(requeteSite, b -> {
				if(b.succeeded()) {
					putcopieCliniqueMedicaleReponse(requeteSite, c -> {
						if(c.succeeded()) {
							gestionnaireEvenements.handle(Future.succeededFuture(c.result()));
							WorkerExecutor executeurTravailleur = siteContexte.getExecuteurTravailleur();
							executeurTravailleur.executeBlocking(
								blockingCodeHandler -> {
									try {
										rechercheCliniqueMedicale(requeteSite, false, true, "/api/clinique/copie", "PUTCopie", d -> {
											if(d.succeeded()) {
												ListeRecherche<CliniqueMedicale> listeCliniqueMedicale = d.result();
												RequeteApi requeteApi = new RequeteApi();
												requeteApi.setRows(listeCliniqueMedicale.getRows());
												requeteApi.setNumFound(listeCliniqueMedicale.getQueryResponse().getResults().getNumFound());
												requeteApi.setNumPATCH(0L);
												requeteApi.initLoinRequeteApi(requeteSite);
												requeteSite.setRequeteApi_(requeteApi);
												requeteSite.getVertx().eventBus().publish("websocketCliniqueMedicale", JsonObject.mapFrom(requeteApi).toString());
												try {
													listePUTCopieCliniqueMedicale(requeteApi, listeCliniqueMedicale, e -> {
														if(e.succeeded()) {
															putcopieCliniqueMedicaleReponse(requeteSite, f -> {
																if(f.succeeded()) {
																	LOGGER.info(String.format("putcopieCliniqueMedicale a réussi. "));
																	blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																} else {
																	LOGGER.error(String.format("putcopieCliniqueMedicale a échoué. ", f.cause()));
																	erreurCliniqueMedicale(requeteSite, null, f);
																}
															});
														} else {
															LOGGER.error(String.format("putcopieCliniqueMedicale a échoué. ", e.cause()));
															erreurCliniqueMedicale(requeteSite, null, e);
														}
													});
												} catch(Exception ex) {
													LOGGER.error(String.format("putcopieCliniqueMedicale a échoué. ", ex));
													erreurCliniqueMedicale(requeteSite, null, Future.failedFuture(ex));
												}
											} else {
												LOGGER.error(String.format("putcopieCliniqueMedicale a échoué. ", d.cause()));
												erreurCliniqueMedicale(requeteSite, null, d);
											}
										});
									} catch(Exception ex) {
										LOGGER.error(String.format("putcopieCliniqueMedicale a échoué. ", ex));
										erreurCliniqueMedicale(requeteSite, null, Future.failedFuture(ex));
									}
								}, resultHandler -> {
								}
							);
						} else {
							LOGGER.error(String.format("putcopieCliniqueMedicale a échoué. ", c.cause()));
							erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, c);
						}
					});
				} else {
					LOGGER.error(String.format("putcopieCliniqueMedicale a échoué. ", b.cause()));
					erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putcopieCliniqueMedicale a échoué. ", ex));
			erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, Future.failedFuture(ex));
		}
	}


	public void listePUTCopieCliniqueMedicale(RequeteApi requeteApi, ListeRecherche<CliniqueMedicale> listeCliniqueMedicale, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		List<Future> futures = new ArrayList<>();
		RequeteSiteFrFR requeteSite = listeCliniqueMedicale.getRequeteSite_();
		listeCliniqueMedicale.getList().forEach(o -> {
			RequeteSiteFrFR requeteSite2 = genererRequeteSiteFrFRPourCliniqueMedicale(siteContexte, requeteSite.getOperationRequete(), requeteSite.getObjetJson());
			requeteSite2.setRequeteApi_(requeteSite.getRequeteApi_());
			o.setRequeteSite_(requeteSite2);
			futures.add(
				putcopieCliniqueMedicaleFuture(requeteSite, JsonObject.mapFrom(o), a -> {
					if(a.succeeded()) {
					} else {
						LOGGER.error(String.format("listePUTCopieCliniqueMedicale a échoué. ", a.cause()));
						erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, a);
					}
				})
			);
		});
		CompositeFuture.all(futures).setHandler( a -> {
			if(a.succeeded()) {
				requeteApi.setNumPATCH(requeteApi.getNumPATCH() + listeCliniqueMedicale.size());
				if(listeCliniqueMedicale.next()) {
					listePUTCopieCliniqueMedicale(requeteApi, listeCliniqueMedicale, gestionnaireEvenements);
				} else {
					reponse200PUTCopieCliniqueMedicale(requeteSite, gestionnaireEvenements);
				}
			} else {
				LOGGER.error(String.format("listePUTCopieCliniqueMedicale a échoué. ", a.cause()));
				erreurCliniqueMedicale(listeCliniqueMedicale.getRequeteSite_(), gestionnaireEvenements, a);
			}
		});
	}

	public Future<CliniqueMedicale> putcopieCliniqueMedicaleFuture(RequeteSiteFrFR requeteSite, JsonObject jsonObject, Handler<AsyncResult<CliniqueMedicale>> gestionnaireEvenements) {
		Promise<CliniqueMedicale> promise = Promise.promise();
		try {

			jsonObject.put("sauvegardes", Optional.ofNullable(jsonObject.getJsonArray("sauvegardes")).orElse(new JsonArray()));
			JsonObject jsonPatch = Optional.ofNullable(requeteSite.getObjetJson()).map(o -> o.getJsonObject("patch")).orElse(new JsonObject());
			jsonPatch.stream().forEach(o -> {
				jsonObject.put(o.getKey(), o.getValue());
				jsonObject.getJsonArray("sauvegardes").add(o.getKey());
			});

			sqlConnexionCliniqueMedicale(requeteSite, a -> {
				if(a.succeeded()) {
					sqlTransactionCliniqueMedicale(requeteSite, b -> {
						if(b.succeeded()) {
							creerCliniqueMedicale(requeteSite, c -> {
								if(c.succeeded()) {
									CliniqueMedicale cliniqueMedicale = c.result();
									sqlPUTCopieCliniqueMedicale(cliniqueMedicale, jsonObject, d -> {
										if(d.succeeded()) {
											definirIndexerCliniqueMedicale(cliniqueMedicale, e -> {
												if(e.succeeded()) {
													RequeteApi requeteApi = requeteSite.getRequeteApi_();
													if(requeteApi != null) {
														requeteApi.setNumPATCH(requeteApi.getNumPATCH() + 1);
														if(requeteApi.getNumFound() == 1L) {
															cliniqueMedicale.requeteApiCliniqueMedicale();
														}
														requeteSite.getVertx().eventBus().publish("websocketCliniqueMedicale", JsonObject.mapFrom(requeteApi).toString());
													}
													gestionnaireEvenements.handle(Future.succeededFuture(cliniqueMedicale));
													promise.complete(cliniqueMedicale);
												} else {
													LOGGER.error(String.format("putcopieCliniqueMedicaleFuture a échoué. ", e.cause()));
													gestionnaireEvenements.handle(Future.failedFuture(e.cause()));
												}
											});
										} else {
											LOGGER.error(String.format("putcopieCliniqueMedicaleFuture a échoué. ", d.cause()));
											gestionnaireEvenements.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOGGER.error(String.format("putcopieCliniqueMedicaleFuture a échoué. ", c.cause()));
									gestionnaireEvenements.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOGGER.error(String.format("putcopieCliniqueMedicaleFuture a échoué. ", b.cause()));
							gestionnaireEvenements.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOGGER.error(String.format("putcopieCliniqueMedicaleFuture a échoué. ", a.cause()));
					gestionnaireEvenements.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("putcopieCliniqueMedicaleFuture a échoué. ", e));
			erreurCliniqueMedicale(requeteSite, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPUTCopieCliniqueMedicale(CliniqueMedicale o, JsonObject jsonObject, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
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
									a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.inheritPk a échoué", b.cause())));
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
									a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.archive a échoué", b.cause())));
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
									a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.supprime a échoué", b.cause())));
							});
						}));
						break;
					case "cliniqueNom":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContexteFrFR.SQL_setD
									, Tuple.of(pk, "cliniqueNom", Optional.ofNullable(jsonObject.getValue(entiteVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.cliniqueNom a échoué", b.cause())));
							});
						}));
						break;
					case "cliniqueNumeroTelephone":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContexteFrFR.SQL_setD
									, Tuple.of(pk, "cliniqueNumeroTelephone", Optional.ofNullable(jsonObject.getValue(entiteVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.cliniqueNumeroTelephone a échoué", b.cause())));
							});
						}));
						break;
					case "cliniqueAdministrateurNom":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContexteFrFR.SQL_setD
									, Tuple.of(pk, "cliniqueAdministrateurNom", Optional.ofNullable(jsonObject.getValue(entiteVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.cliniqueAdministrateurNom a échoué", b.cause())));
							});
						}));
						break;
					case "cliniqueMail":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContexteFrFR.SQL_setD
									, Tuple.of(pk, "cliniqueMail", Optional.ofNullable(jsonObject.getValue(entiteVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.cliniqueMail a échoué", b.cause())));
							});
						}));
						break;
					case "cliniqueMailDe":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContexteFrFR.SQL_setD
									, Tuple.of(pk, "cliniqueMailDe", Optional.ofNullable(jsonObject.getValue(entiteVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.cliniqueMailDe a échoué", b.cause())));
							});
						}));
						break;
					case "cliniqueMailA":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContexteFrFR.SQL_setD
									, Tuple.of(pk, "cliniqueMailA", Optional.ofNullable(jsonObject.getValue(entiteVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.cliniqueMailA a échoué", b.cause())));
							});
						}));
						break;
					case "cliniqueEmplacement":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContexteFrFR.SQL_setD
									, Tuple.of(pk, "cliniqueEmplacement", Optional.ofNullable(jsonObject.getValue(entiteVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.cliniqueEmplacement a échoué", b.cause())));
							});
						}));
						break;
					case "cliniqueAddresse":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContexteFrFR.SQL_setD
									, Tuple.of(pk, "cliniqueAddresse", Optional.ofNullable(jsonObject.getValue(entiteVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.cliniqueAddresse a échoué", b.cause())));
							});
						}));
						break;
					case "inscriptionCles":
						for(Long l : jsonObject.getJsonArray(entiteVar).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContexteFrFR.SQL_addA
										, Tuple.of(l, "cliniqueCle", pk, "inscriptionCles")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("valeur CliniqueMedicale.inscriptionCles a échoué", b.cause())));
								});
							}));
							if(!pks.contains(l)) {
								pks.add(l);
								classes.add("InscriptionMedicale");
							}
						}
						break;
					}
				}
			}
			CompositeFuture.all(futures).setHandler( a -> {
				if(a.succeeded()) {
					gestionnaireEvenements.handle(Future.succeededFuture());
				} else {
					LOGGER.error(String.format("sqlPUTCopieCliniqueMedicale a échoué. ", a.cause()));
					gestionnaireEvenements.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPUTCopieCliniqueMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void putcopieCliniqueMedicaleReponse(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			reponse200PUTCopieCliniqueMedicale(requeteSite, a -> {
				if(a.succeeded()) {
					gestionnaireEvenements.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("putcopieCliniqueMedicaleReponse a échoué. ", a.cause()));
					erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putcopieCliniqueMedicaleReponse a échoué. ", ex));
			erreurCliniqueMedicale(requeteSite, null, Future.failedFuture(ex));
		}
	}
	public void reponse200PUTCopieCliniqueMedicale(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			JsonObject json = new JsonObject();
			gestionnaireEvenements.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("reponse200PUTCopieCliniqueMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	// PageRecherche //

	@Override
	public void pagerechercheCliniqueMedicaleId(OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		pagerechercheCliniqueMedicale(operationRequete, gestionnaireEvenements);
	}

	@Override
	public void pagerechercheCliniqueMedicale(OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = genererRequeteSiteFrFRPourCliniqueMedicale(siteContexte, operationRequete);
		try {

			List<String> roles = Arrays.asList("SiteAdmin");
			List<String> roleLires = Arrays.asList("");
			if(
					!CollectionUtils.containsAny(requeteSite.getUtilisateurRolesRessource(), roles)
					&& !CollectionUtils.containsAny(requeteSite.getUtilisateurRolesRoyaume(), roles)
					&& !CollectionUtils.containsAny(requeteSite.getUtilisateurRolesRessource(), roleLires)
					&& !CollectionUtils.containsAny(requeteSite.getUtilisateurRolesRoyaume(), roleLires)
					) {
				gestionnaireEvenements.handle(Future.succeededFuture(
					new OperationResponse(401, "UNAUTHORIZED", 
						Buffer.buffer().appendString(
							new JsonObject()
								.put("errorCode", "401")
								.put("errorMessage", "rôles requis : " + String.join(", ", roles))
								.encodePrettily()
							), new CaseInsensitiveHeaders()
					)
				));
			}

			utilisateurCliniqueMedicale(requeteSite, b -> {
				if(b.succeeded()) {
					rechercheCliniqueMedicale(requeteSite, false, true, "/clinique", "PageRecherche", c -> {
						if(c.succeeded()) {
							ListeRecherche<CliniqueMedicale> listeCliniqueMedicale = c.result();
							pagerechercheCliniqueMedicaleReponse(listeCliniqueMedicale, d -> {
								if(d.succeeded()) {
									gestionnaireEvenements.handle(Future.succeededFuture(d.result()));
									LOGGER.info(String.format("pagerechercheCliniqueMedicale a réussi. "));
								} else {
									LOGGER.error(String.format("pagerechercheCliniqueMedicale a échoué. ", d.cause()));
									erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, d);
								}
							});
						} else {
							LOGGER.error(String.format("pagerechercheCliniqueMedicale a échoué. ", c.cause()));
							erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, c);
						}
					});
				} else {
					LOGGER.error(String.format("pagerechercheCliniqueMedicale a échoué. ", b.cause()));
					erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, b);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("pagerechercheCliniqueMedicale a échoué. ", ex));
			erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, Future.failedFuture(ex));
		}
	}


	public void pagerechercheCliniqueMedicaleReponse(ListeRecherche<CliniqueMedicale> listeCliniqueMedicale, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = listeCliniqueMedicale.getRequeteSite_();
		try {
			Buffer buffer = Buffer.buffer();
			ToutEcrivain w = ToutEcrivain.creer(requeteSite, buffer);
			requeteSite.setW(w);
			reponse200PageRechercheCliniqueMedicale(listeCliniqueMedicale, a -> {
				if(a.succeeded()) {
					gestionnaireEvenements.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("pagerechercheCliniqueMedicaleReponse a échoué. ", a.cause()));
					erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("pagerechercheCliniqueMedicaleReponse a échoué. ", ex));
			erreurCliniqueMedicale(requeteSite, null, Future.failedFuture(ex));
		}
	}
	public void reponse200PageRechercheCliniqueMedicale(ListeRecherche<CliniqueMedicale> listeCliniqueMedicale, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = listeCliniqueMedicale.getRequeteSite_();
			Buffer buffer = Buffer.buffer();
			ToutEcrivain w = ToutEcrivain.creer(listeCliniqueMedicale.getRequeteSite_(), buffer);
			CliniquePage page = new CliniquePage();
			SolrDocument pageDocumentSolr = new SolrDocument();
			CaseInsensitiveHeaders requeteEnTetes = new CaseInsensitiveHeaders();
			requeteSite.setRequeteEnTetes(requeteEnTetes);

			pageDocumentSolr.setField("pageUri_frFR_stored_string", "/clinique");
			page.setPageDocumentSolr(pageDocumentSolr);
			page.setW(w);
			if(listeCliniqueMedicale.size() == 1)
				requeteSite.setRequetePk(listeCliniqueMedicale.get(0).getPk());
			requeteSite.setW(w);
			page.setListeCliniqueMedicale(listeCliniqueMedicale);
			page.setRequeteSite_(requeteSite);
			page.initLoinCliniquePage(requeteSite);
			page.html();
			gestionnaireEvenements.handle(Future.succeededFuture(new OperationResponse(200, "OK", buffer, requeteEnTetes)));
		} catch(Exception e) {
			LOGGER.error(String.format("reponse200PageRechercheCliniqueMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	// General //

	public Future<CliniqueMedicale> definirIndexerCliniqueMedicale(CliniqueMedicale cliniqueMedicale, Handler<AsyncResult<CliniqueMedicale>> gestionnaireEvenements) {
		Promise<CliniqueMedicale> promise = Promise.promise();
		RequeteSiteFrFR requeteSite = cliniqueMedicale.getRequeteSite_();
		definirCliniqueMedicale(cliniqueMedicale, c -> {
			if(c.succeeded()) {
				attribuerCliniqueMedicale(cliniqueMedicale, d -> {
					if(d.succeeded()) {
						indexerCliniqueMedicale(cliniqueMedicale, e -> {
							if(e.succeeded()) {
								sqlCommitCliniqueMedicale(requeteSite, f -> {
									if(f.succeeded()) {
										sqlFermerCliniqueMedicale(requeteSite, g -> {
											if(g.succeeded()) {
												rechargerCliniqueMedicale(cliniqueMedicale, h -> {
													if(h.succeeded()) {
														gestionnaireEvenements.handle(Future.succeededFuture(cliniqueMedicale));
														promise.complete(cliniqueMedicale);
													} else {
														LOGGER.error(String.format("rechargerCliniqueMedicale a échoué. ", h.cause()));
														erreurCliniqueMedicale(requeteSite, null, h);
													}
												});
											} else {
												LOGGER.error(String.format("definirIndexerCliniqueMedicale a échoué. ", g.cause()));
												erreurCliniqueMedicale(requeteSite, null, g);
											}
										});
									} else {
										LOGGER.error(String.format("definirIndexerCliniqueMedicale a échoué. ", f.cause()));
										erreurCliniqueMedicale(requeteSite, null, f);
									}
								});
							} else {
								LOGGER.error(String.format("definirIndexerCliniqueMedicale a échoué. ", e.cause()));
								erreurCliniqueMedicale(requeteSite, null, e);
							}
						});
					} else {
						LOGGER.error(String.format("definirIndexerCliniqueMedicale a échoué. ", d.cause()));
						erreurCliniqueMedicale(requeteSite, null, d);
					}
				});
			} else {
				LOGGER.error(String.format("definirIndexerCliniqueMedicale a échoué. ", c.cause()));
				erreurCliniqueMedicale(requeteSite, null, c);
			}
		});
		return promise.future();
	}

	public void creerCliniqueMedicale(RequeteSiteFrFR requeteSite, Handler<AsyncResult<CliniqueMedicale>> gestionnaireEvenements) {
		try {
			Transaction tx = requeteSite.getTx();
			String utilisateurId = requeteSite.getUtilisateurId();
			ZonedDateTime cree = Optional.ofNullable(requeteSite.getObjetJson()).map(j -> j.getString("cree")).map(s -> ZonedDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of(requeteSite.getConfigSite_().getSiteZone())))).orElse(ZonedDateTime.now(ZoneId.of(requeteSite.getConfigSite_().getSiteZone())));

			tx.preparedQuery(
					SiteContexteFrFR.SQL_creer
					, Tuple.of(CliniqueMedicale.class.getCanonicalName(), utilisateurId, cree.toOffsetDateTime())
					, Collectors.toList()
					, creerAsync
			-> {
				if(creerAsync.succeeded()) {
					Row creerLigne = creerAsync.result().value().stream().findFirst().orElseGet(() -> null);
					Long pk = creerLigne.getLong(0);
					CliniqueMedicale o = new CliniqueMedicale();
					o.setPk(pk);
					o.setRequeteSite_(requeteSite);
					gestionnaireEvenements.handle(Future.succeededFuture(o));
				} else {
					LOGGER.error(String.format("creerCliniqueMedicale a échoué. ", creerAsync.cause()));
					gestionnaireEvenements.handle(Future.failedFuture(creerAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("creerCliniqueMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void erreurCliniqueMedicale(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements, AsyncResult<?> resultatAsync) {
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
		sqlRollbackCliniqueMedicale(requeteSite, a -> {
			if(a.succeeded()) {
				LOGGER.info(String.format("sql rollback. "));
				sqlFermerCliniqueMedicale(requeteSite, b -> {
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

	public void sqlConnexionCliniqueMedicale(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
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
						LOGGER.error(String.format("sqlConnexionCliniqueMedicale a échoué. ", a.cause()));
						gestionnaireEvenements.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlCliniqueMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void sqlTransactionCliniqueMedicale(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
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
			LOGGER.error(String.format("sqlTransactionCliniqueMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void sqlCommitCliniqueMedicale(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
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
						LOGGER.error(String.format("sqlCommitCliniqueMedicale a échoué. ", a.cause()));
						gestionnaireEvenements.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlCliniqueMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void sqlRollbackCliniqueMedicale(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
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
						LOGGER.error(String.format("sqlRollbackCliniqueMedicale a échoué. ", a.cause()));
						gestionnaireEvenements.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlCliniqueMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void sqlFermerCliniqueMedicale(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
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
			LOGGER.error(String.format("sqlFermerCliniqueMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public RequeteSiteFrFR genererRequeteSiteFrFRPourCliniqueMedicale(SiteContexteFrFR siteContexte, OperationRequest operationRequete) {
		return genererRequeteSiteFrFRPourCliniqueMedicale(siteContexte, operationRequete, null);
	}

	public RequeteSiteFrFR genererRequeteSiteFrFRPourCliniqueMedicale(SiteContexteFrFR siteContexte, OperationRequest operationRequete, JsonObject body) {
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

	public void utilisateurCliniqueMedicale(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			String utilisateurId = requeteSite.getUtilisateurId();
			if(utilisateurId == null) {
				gestionnaireEvenements.handle(Future.succeededFuture());
			} else {
				sqlConnexionCliniqueMedicale(requeteSite, a -> {
					if(a.succeeded()) {
						sqlTransactionCliniqueMedicale(requeteSite, b -> {
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
												utilisateurCliniqueMedicaleDefinir(requeteSite, jsonObject, false);

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
																		erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, e);
																	}
																});
															} else {
																erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, d);
															}
														});
													} else {
														erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, c);
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
												Boolean definir = utilisateurCliniqueMedicaleDefinir(requeteSite, jsonObject, true);
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
																	erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, e);
																}
															});
														} else {
															erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, d);
														}
													});
												} else {
													requeteSite.setUtilisateurSite(utilisateurSite1);
													requeteSite.setUtilisateurNom(utilisateurSite1.getUtilisateurNom());
													requeteSite.setUtilisateurPrenom(utilisateurSite1.getUtilisateurPrenom());
													requeteSite.setUtilisateurNomFamille(utilisateurSite1.getUtilisateurNomFamille());
													requeteSite.setUtilisateurId(utilisateurSite1.getUtilisateurId());
													requeteSite.setUtilisateurCle(utilisateurSite1.getPk());
													sqlRollbackCliniqueMedicale(requeteSite, c -> {
														if(c.succeeded()) {
															gestionnaireEvenements.handle(Future.succeededFuture());
														} else {
															gestionnaireEvenements.handle(Future.failedFuture(c.cause()));
															erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, c);
														}
													});
												}
											}
										} catch(Exception e) {
											LOGGER.error(String.format("utilisateurCliniqueMedicale a échoué. ", e));
											gestionnaireEvenements.handle(Future.failedFuture(e));
										}
									} else {
										LOGGER.error(String.format("utilisateurCliniqueMedicale a échoué. ", selectCAsync.cause()));
										gestionnaireEvenements.handle(Future.failedFuture(selectCAsync.cause()));
									}
								});
							} else {
								LOGGER.error(String.format("utilisateurCliniqueMedicale a échoué. ", b.cause()));
								gestionnaireEvenements.handle(Future.failedFuture(b.cause()));
							}
						});
					} else {
						LOGGER.error(String.format("utilisateurCliniqueMedicale a échoué. ", a.cause()));
						gestionnaireEvenements.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("utilisateurCliniqueMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public Boolean utilisateurCliniqueMedicaleDefinir(RequeteSiteFrFR requeteSite, JsonObject jsonObject, Boolean patch) {
		if(patch) {
			return jsonObject.getString("setCustomerProfileId") == null;
		} else {
			return jsonObject.getString("customerProfileId") == null;
		}
	}

	public void rechercheCliniqueMedicaleQ(String uri, String apiMethode, ListeRecherche<CliniqueMedicale> listeRecherche, String entiteVar, String valeurIndexe, String varIndexe) {
		listeRecherche.setQuery(varIndexe + ":" + ("*".equals(valeurIndexe) ? valeurIndexe : ClientUtils.escapeQueryChars(valeurIndexe)));
		if(!"*".equals(entiteVar)) {
			listeRecherche.setHighlight(true);
			listeRecherche.setHighlightSnippets(3);
			listeRecherche.addHighlightField(varIndexe);
			listeRecherche.setParam("hl.encoder", "html");
		}
	}

	public void rechercheCliniqueMedicaleFq(String uri, String apiMethode, ListeRecherche<CliniqueMedicale> listeRecherche, String entiteVar, String valeurIndexe, String varIndexe) {
		if(varIndexe == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entiteVar));
		listeRecherche.addFilterQuery(varIndexe + ":" + ClientUtils.escapeQueryChars(valeurIndexe));
	}

	public void rechercheCliniqueMedicaleSort(String uri, String apiMethode, ListeRecherche<CliniqueMedicale> listeRecherche, String entiteVar, String valeurIndexe, String varIndexe) {
		if(varIndexe == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entiteVar));
		listeRecherche.addSort(varIndexe, ORDER.valueOf(valeurIndexe));
	}

	public void rechercheCliniqueMedicaleRows(String uri, String apiMethode, ListeRecherche<CliniqueMedicale> listeRecherche, Integer valeurRows) {
			listeRecherche.setRows(apiMethode != null && apiMethode.contains("Recherche") ? valeurRows : 10);
	}

	public void rechercheCliniqueMedicaleStart(String uri, String apiMethode, ListeRecherche<CliniqueMedicale> listeRecherche, Integer valeurStart) {
		listeRecherche.setStart(valeurStart);
	}

	public void rechercheCliniqueMedicaleVar(String uri, String apiMethode, ListeRecherche<CliniqueMedicale> listeRecherche, String var, String valeur) {
		listeRecherche.getRequeteSite_().getRequeteVars().put(var, valeur);
	}

	public void rechercheCliniqueMedicaleUri(String uri, String apiMethode, ListeRecherche<CliniqueMedicale> listeRecherche) {
	}

	public void varsCliniqueMedicale(RequeteSiteFrFR requeteSite, Handler<AsyncResult<ListeRecherche<OperationResponse>>> gestionnaireEvenements) {
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
					LOGGER.error(String.format("rechercheCliniqueMedicale a échoué. ", e));
					gestionnaireEvenements.handle(Future.failedFuture(e));
				}
			});
			gestionnaireEvenements.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOGGER.error(String.format("rechercheCliniqueMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void rechercheCliniqueMedicale(RequeteSiteFrFR requeteSite, Boolean peupler, Boolean stocker, String uri, String apiMethode, Handler<AsyncResult<ListeRecherche<CliniqueMedicale>>> gestionnaireEvenements) {
		try {
			OperationRequest operationRequete = requeteSite.getOperationRequete();
			String entiteListeStr = requeteSite.getOperationRequete().getParams().getJsonObject("query").getString("fl");
			String[] entiteListe = entiteListeStr == null ? null : entiteListeStr.split(",\\s*");
			ListeRecherche<CliniqueMedicale> listeRecherche = new ListeRecherche<CliniqueMedicale>();
			listeRecherche.setPeupler(peupler);
			listeRecherche.setStocker(stocker);
			listeRecherche.setQuery("*:*");
			listeRecherche.setC(CliniqueMedicale.class);
			listeRecherche.setRequeteSite_(requeteSite);
			if(entiteListe != null)
				listeRecherche.addFields(entiteListe);
			listeRecherche.add("json.facet", "{max_modifie:'max(modifie_indexed_date)'}");

			String id = operationRequete.getParams().getJsonObject("path").getString("id");
			if(id != null) {
				listeRecherche.addFilterQuery("(id:" + ClientUtils.escapeQueryChars(id) + " OR objetId_indexed_string:" + ClientUtils.escapeQueryChars(id) + ")");
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
								varIndexe = "*".equals(entiteVar) ? entiteVar : CliniqueMedicale.varRechercheCliniqueMedicale(entiteVar);
								valeurIndexe = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObjet, ":")), "UTF-8");
								valeurIndexe = StringUtils.isEmpty(valeurIndexe) ? "*" : valeurIndexe;
								rechercheCliniqueMedicaleQ(uri, apiMethode, listeRecherche, entiteVar, valeurIndexe, varIndexe);
								break;
							case "fq":
								entiteVar = StringUtils.trim(StringUtils.substringBefore((String)paramObjet, ":"));
								valeurIndexe = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObjet, ":")), "UTF-8");
								varIndexe = CliniqueMedicale.varIndexeCliniqueMedicale(entiteVar);
								rechercheCliniqueMedicaleFq(uri, apiMethode, listeRecherche, entiteVar, valeurIndexe, varIndexe);
								break;
							case "sort":
								entiteVar = StringUtils.trim(StringUtils.substringBefore((String)paramObjet, " "));
								valeurIndexe = StringUtils.trim(StringUtils.substringAfter((String)paramObjet, " "));
								varIndexe = CliniqueMedicale.varIndexeCliniqueMedicale(entiteVar);
								rechercheCliniqueMedicaleSort(uri, apiMethode, listeRecherche, entiteVar, valeurIndexe, varIndexe);
								break;
							case "start":
								valeurStart = (Integer)paramObjet;
								rechercheCliniqueMedicaleStart(uri, apiMethode, listeRecherche, valeurStart);
								break;
							case "rows":
								valeurRows = (Integer)paramObjet;
								rechercheCliniqueMedicaleRows(uri, apiMethode, listeRecherche, valeurRows);
								break;
							case "var":
								entiteVar = StringUtils.trim(StringUtils.substringBefore((String)paramObjet, ":"));
								valeurIndexe = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObjet, ":")), "UTF-8");
								rechercheCliniqueMedicaleVar(uri, apiMethode, listeRecherche, entiteVar, valeurIndexe);
								break;
						}
					}
					rechercheCliniqueMedicaleUri(uri, apiMethode, listeRecherche);
				} catch(Exception e) {
					LOGGER.error(String.format("rechercheCliniqueMedicale a échoué. ", e));
					gestionnaireEvenements.handle(Future.failedFuture(e));
				}
			});
			if(listeRecherche.getSorts().size() == 0) {
				listeRecherche.addSort("cree_indexed_date", ORDER.desc);
			}
			listeRecherche.initLoinPourClasse(requeteSite);
			gestionnaireEvenements.handle(Future.succeededFuture(listeRecherche));
		} catch(Exception e) {
			LOGGER.error(String.format("rechercheCliniqueMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void definirCliniqueMedicale(CliniqueMedicale o, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
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
						LOGGER.error(String.format("definirCliniqueMedicale a échoué. ", e));
						gestionnaireEvenements.handle(Future.failedFuture(e));
					}
				} else {
					LOGGER.error(String.format("definirCliniqueMedicale a échoué. ", definirAsync.cause()));
					gestionnaireEvenements.handle(Future.failedFuture(definirAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("definirCliniqueMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void attribuerCliniqueMedicale(CliniqueMedicale o, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
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
						LOGGER.error(String.format("attribuerCliniqueMedicale a échoué. ", attribuerAsync.cause()));
						gestionnaireEvenements.handle(Future.failedFuture(attribuerAsync.cause()));
					}
				} catch(Exception e) {
					LOGGER.error(String.format("attribuerCliniqueMedicale a échoué. ", e));
					gestionnaireEvenements.handle(Future.failedFuture(e));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("attribuerCliniqueMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void indexerCliniqueMedicale(CliniqueMedicale o, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = o.getRequeteSite_();
		try {
			RequeteApi requeteApi = requeteSite.getRequeteApi_();
			List<Long> pks = Optional.ofNullable(requeteApi).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(requeteApi).map(r -> r.getClasses()).orElse(new ArrayList<>());
			o.initLoinPourClasse(requeteSite);
			o.indexerPourClasse();
			gestionnaireEvenements.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOGGER.error(String.format("indexerCliniqueMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void rechargerCliniqueMedicale(CliniqueMedicale o, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = o.getRequeteSite_();
		try {
			RequeteApi requeteApi = requeteSite.getRequeteApi_();
			List<Long> pks = Optional.ofNullable(requeteApi).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(requeteApi).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Boolean recharger = !"false".equals(requeteSite.getRequeteVars().get("recharger"));
			if(recharger && BooleanUtils.isFalse(Optional.ofNullable(requeteSite.getRequeteApi_()).map(RequeteApi::getEmpty).orElse(true))) {
				ListeRecherche<CliniqueMedicale> listeRecherche = new ListeRecherche<CliniqueMedicale>();
				listeRecherche.setStocker(true);
				listeRecherche.setQuery("*:*");
				listeRecherche.setC(CliniqueMedicale.class);
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
							RequeteSiteFrFR requeteSite2 = genererRequeteSiteFrFRPourCliniqueMedicale(siteContexte, requeteSite.getOperationRequete(), new JsonObject());
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
						CliniqueMedicaleFrFRApiServiceImpl service = new CliniqueMedicaleFrFRApiServiceImpl(requeteSite.getSiteContexte_());
						List<Future> futures2 = new ArrayList<>();
						for(CliniqueMedicale o2 : listeRecherche.getList()) {
							RequeteSiteFrFR requeteSite2 = genererRequeteSiteFrFRPourCliniqueMedicale(siteContexte, requeteSite.getOperationRequete(), new JsonObject());
							o2.setRequeteSite_(requeteSite2);
							futures2.add(
								service.patchCliniqueMedicaleFuture(o2, false, b -> {
									if(b.succeeded()) {
									} else {
										LOGGER.info(String.format("CliniqueMedicale %s a échoué. ", o2.getPk()));
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
								erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, b);
							}
						});
					} else {
						LOGGER.error("Recharger relations a échoué. ", a.cause());
						erreurCliniqueMedicale(requeteSite, gestionnaireEvenements, a);
					}
				});
			} else {
				gestionnaireEvenements.handle(Future.succeededFuture());
			}
		} catch(Exception e) {
			LOGGER.error(String.format("rechargerCliniqueMedicale a échoué. ", e));
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}
}
