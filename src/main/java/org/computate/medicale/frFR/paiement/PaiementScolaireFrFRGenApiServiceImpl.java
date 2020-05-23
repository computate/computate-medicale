package org.computate.medicale.frFR.paiement;

import org.computate.medicale.frFR.inscription.InscriptionmedicaleFrFRGenApiServiceImpl;
import org.computate.medicale.frFR.inscription.Inscriptionmedicale;
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
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;
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
 * NomCanonique.enUS: org.computate.medicale.enUS.payment.SchoolPaymentEnUSGenApiServiceImpl
 **/
public class PaiementmedicaleFrFRGenApiServiceImpl implements PaiementmedicaleFrFRGenApiService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(PaiementmedicaleFrFRGenApiServiceImpl.class);

	protected static final String SERVICE_ADDRESS = "PaiementmedicaleFrFRApiServiceImpl";

	protected SiteContexteFrFR siteContexte;

	public PaiementmedicaleFrFRGenApiServiceImpl(SiteContexteFrFR siteContexte) {
		this.siteContexte = siteContexte;
	}

	// POST //

	@Override
	public void postPaiementmedicale(JsonObject body, OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = genererRequeteSiteFrFRPourPaiementmedicale(siteContexte, operationRequete, body);
		try {
			LOGGER.info(String.format("postPaiementmedicale a démarré. "));
			sqlPaiementmedicale(requeteSite, a -> {
				if(a.succeeded()) {
					utilisateurPaiementmedicale(requeteSite, b -> {
						if(b.succeeded()) {
							RequeteApi requeteApi = new RequeteApi();
							requeteApi.setRows(1);
							requeteApi.setNumFound(1L);
							requeteApi.setNumPATCH(0L);
							requeteApi.initLoinRequeteApi(requeteSite);
							requeteSite.setRequeteApi_(requeteApi);
							requeteSite.getVertx().eventBus().publish("websocketPaiementmedicale", JsonObject.mapFrom(requeteApi).toString());
							postPaiementmedicaleFuture(requeteSite, false, c -> {
								if(c.succeeded()) {
									Paiementmedicale paiementmedicale = c.result();
									requeteApi.setPk(paiementmedicale.getPk());
									postPaiementmedicaleReponse(paiementmedicale, d -> {
										if(d.succeeded()) {
											gestionnaireEvenements.handle(Future.succeededFuture(d.result()));
											LOGGER.info(String.format("postPaiementmedicale a réussi. "));
										} else {
											LOGGER.error(String.format("postPaiementmedicale a échoué. ", d.cause()));
											erreurPaiementmedicale(requeteSite, gestionnaireEvenements, d);
										}
									});
								} else {
									LOGGER.error(String.format("postPaiementmedicale a échoué. ", c.cause()));
									erreurPaiementmedicale(requeteSite, gestionnaireEvenements, c);
								}
							});
						} else {
							LOGGER.error(String.format("postPaiementmedicale a échoué. ", b.cause()));
							erreurPaiementmedicale(requeteSite, gestionnaireEvenements, b);
						}
					});
				} else {
					LOGGER.error(String.format("postPaiementmedicale a échoué. ", a.cause()));
					erreurPaiementmedicale(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("postPaiementmedicale a échoué. ", ex));
			erreurPaiementmedicale(requeteSite, gestionnaireEvenements, Future.failedFuture(ex));
		}
	}


	public Future<Paiementmedicale> postPaiementmedicaleFuture(RequeteSiteFrFR requeteSite, Boolean inheritPk, Handler<AsyncResult<Paiementmedicale>> gestionnaireEvenements) {
		Promise<Paiementmedicale> promise = Promise.promise();
		try {
			creerPaiementmedicale(requeteSite, a -> {
				if(a.succeeded()) {
					Paiementmedicale paiementmedicale = a.result();
					sqlPOSTPaiementmedicale(paiementmedicale, inheritPk, b -> {
						if(b.succeeded()) {
							definirIndexerPaiementmedicale(paiementmedicale, c -> {
								if(c.succeeded()) {
									RequeteApi requeteApi = requeteSite.getRequeteApi_();
									if(requeteApi != null) {
										requeteApi.setNumPATCH(requeteApi.getNumPATCH() + 1);
										paiementmedicale.requeteApiPaiementmedicale();
										requeteSite.getVertx().eventBus().publish("websocketPaiementmedicale", JsonObject.mapFrom(requeteApi).toString());
									}
									SQLConnection connexionSql = requeteSite.getConnexionSql();
									connexionSql.commit(d -> {
										if(d.succeeded()) {
											gestionnaireEvenements.handle(Future.succeededFuture(paiementmedicale));
											promise.complete(paiementmedicale);
										} else {
											gestionnaireEvenements.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									gestionnaireEvenements.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							gestionnaireEvenements.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					gestionnaireEvenements.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			erreurPaiementmedicale(requeteSite, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPOSTPaiementmedicale(Paiementmedicale o, Boolean inheritPk, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = o.getRequeteSite_();
			RequeteApi requeteApi = requeteSite.getRequeteApi_();
			List<Long> pks = Optional.ofNullable(requeteApi).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(requeteApi).map(r -> r.getClasses()).orElse(new ArrayList<>());
			SQLConnection connexionSql = requeteSite.getConnexionSql();
			Long pk = o.getPk();
			JsonObject jsonObject = requeteSite.getObjetJson();
			StringBuilder postSql = new StringBuilder();
			List<Object> postSqlParams = new ArrayList<Object>();

			if(requeteSite.getSessionId() != null) {
				postSql.append(SiteContexteFrFR.SQL_setD);
				postSqlParams.addAll(Arrays.asList(pk, "sessionId", requeteSite.getSessionId()));
			}
			if(requeteSite.getUtilisateurId() != null) {
				postSql.append(SiteContexteFrFR.SQL_setD);
				postSqlParams.addAll(Arrays.asList(pk, "utilisateurId", requeteSite.getUtilisateurId()));
			}
			if(requeteSite.getUtilisateurCle() != null) {
				postSql.append(SiteContexteFrFR.SQL_setD);
				postSqlParams.addAll(Arrays.asList(pk, "utilisateurCle", requeteSite.getUtilisateurCle()));

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
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList(pk, "inheritPk", jsonObject.getString(entiteVar)));
						break;
					case "cree":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList(pk, "cree", jsonObject.getString(entiteVar)));
						break;
					case "modifie":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList(pk, "modifie", jsonObject.getString(entiteVar)));
						break;
					case "archive":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList(pk, "archive", jsonObject.getBoolean(entiteVar)));
						break;
					case "supprime":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList(pk, "supprime", jsonObject.getBoolean(entiteVar)));
						break;
					case "sessionId":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList(pk, "sessionId", jsonObject.getString(entiteVar)));
						break;
					case "utilisateurId":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList(pk, "utilisateurId", jsonObject.getString(entiteVar)));
						break;
					case "utilisateurCle":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList(pk, "utilisateurCle", jsonObject.getString(entiteVar)));
						break;
					case "inscriptionCle":
						{
							Long l = Long.parseLong(jsonObject.getString(entiteVar));
							if(l != null) {
								ListeRecherche<Inscriptionmedicale> listeRecherche = new ListeRecherche<Inscriptionmedicale>();
								listeRecherche.setQuery("*:*");
								listeRecherche.setStocker(true);
								listeRecherche.setC(Inscriptionmedicale.class);
								listeRecherche.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								listeRecherche.initLoinListeRecherche(requeteSite);
								l = Optional.ofNullable(listeRecherche.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l != null) {
									postSql.append(SiteContexteFrFR.SQL_addA);
									postSqlParams.addAll(Arrays.asList("inscriptionCle", pk, "paiementCles", l));
									if(!pks.contains(l)) {
										pks.add(l);
										classes.add("Inscriptionmedicale");
									}
								}
							}
						}
						break;
					case "enfantNomCompletPrefere":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList(pk, "enfantNomCompletPrefere", jsonObject.getString(entiteVar)));
						break;
					case "enfantDateNaissance":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList(pk, "enfantDateNaissance", DateTimeFormatter.ofPattern("MM/dd/yyyy").format(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(jsonObject.getString(entiteVar)))));
						break;
					case "mereNomCompletPrefere":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList(pk, "mereNomCompletPrefere", jsonObject.getString(entiteVar)));
						break;
					case "pereNomCompletPrefere":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList(pk, "pereNomCompletPrefere", jsonObject.getString(entiteVar)));
						break;
					case "inscriptionPaimentChaqueMois":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList(pk, "inscriptionPaimentChaqueMois", jsonObject.getBoolean(entiteVar)));
						break;
					case "inscriptionPaimentComplet":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList(pk, "inscriptionPaimentComplet", jsonObject.getBoolean(entiteVar)));
						break;
					case "paiementDescription":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList(pk, "paiementDescription", jsonObject.getString(entiteVar)));
						break;
					case "paiementDate":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList(pk, "paiementDate", DateTimeFormatter.ofPattern("MM/dd/yyyy").format(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(jsonObject.getString(entiteVar)))));
						break;
					case "paiementMontant":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList(pk, "paiementMontant", jsonObject.getString(entiteVar)));
						break;
					case "paiementEspeces":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList(pk, "paiementEspeces", jsonObject.getBoolean(entiteVar)));
						break;
					case "paiementCheque":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList(pk, "paiementCheque", jsonObject.getBoolean(entiteVar)));
						break;
					case "paiementSysteme":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList(pk, "paiementSysteme", jsonObject.getBoolean(entiteVar)));
						break;
					case "paiementPar":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList(pk, "paiementPar", jsonObject.getString(entiteVar)));
						break;
					case "transactionId":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList(pk, "transactionId", jsonObject.getString(entiteVar)));
						break;
					case "customerProfileId":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList(pk, "customerProfileId", jsonObject.getString(entiteVar)));
						break;
					case "transactionStatus":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList(pk, "transactionStatus", jsonObject.getString(entiteVar)));
						break;
					case "paiementRecu":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList(pk, "paiementRecu", jsonObject.getBoolean(entiteVar)));
						break;
					case "fraisMontant":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList(pk, "fraisMontant", jsonObject.getString(entiteVar)));
						break;
					case "fraisMontantDu":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList(pk, "fraisMontantDu", jsonObject.getString(entiteVar)));
						break;
					case "fraisMontantFuture":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList(pk, "fraisMontantFuture", jsonObject.getString(entiteVar)));
						break;
					case "fraisPremierDernier":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList(pk, "fraisPremierDernier", jsonObject.getBoolean(entiteVar)));
						break;
					case "fraisInscription":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList(pk, "fraisInscription", jsonObject.getBoolean(entiteVar)));
						break;
					case "fraisMois":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList(pk, "fraisMois", jsonObject.getBoolean(entiteVar)));
						break;
					case "fraisRetard":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList(pk, "fraisRetard", jsonObject.getBoolean(entiteVar)));
						break;
					case "paiementNomCourt":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList(pk, "paiementNomCourt", jsonObject.getString(entiteVar)));
						break;
					}
				}
			}
			connexionSql.queryWithParams(
					postSql.toString()
					, new JsonArray(postSqlParams)
					, postAsync
			-> {
				if(postAsync.succeeded()) {
					gestionnaireEvenements.handle(Future.succeededFuture());
				} else {
					gestionnaireEvenements.handle(Future.failedFuture(new Exception(postAsync.cause())));
				}
			});
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void postPaiementmedicaleReponse(Paiementmedicale paiementmedicale, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = paiementmedicale.getRequeteSite_();
		try {
			reponse200POSTPaiementmedicale(paiementmedicale, a -> {
				if(a.succeeded()) {
					SQLConnection connexionSql = requeteSite.getConnexionSql();
					connexionSql.commit(b -> {
						if(b.succeeded()) {
							connexionSql.close(c -> {
								if(c.succeeded()) {
									gestionnaireEvenements.handle(Future.succeededFuture(a.result()));
								} else {
									erreurPaiementmedicale(requeteSite, gestionnaireEvenements, c);
								}
							});
						} else {
							erreurPaiementmedicale(requeteSite, gestionnaireEvenements, b);
						}
					});
				} else {
					erreurPaiementmedicale(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("postPaiementmedicale a échoué. ", ex));
			erreurPaiementmedicale(requeteSite, null, Future.failedFuture(ex));
		}
	}
	public void reponse200POSTPaiementmedicale(Paiementmedicale o, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = o.getRequeteSite_();
			JsonObject json = JsonObject.mapFrom(o);
			gestionnaireEvenements.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	// PUTImport //

	@Override
	public void putimportPaiementmedicale(JsonObject body, OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = genererRequeteSiteFrFRPourPaiementmedicale(siteContexte, operationRequete, body);
		try {
			LOGGER.info(String.format("putimportPaiementmedicale a démarré. "));
			sqlPaiementmedicale(requeteSite, a -> {
				if(a.succeeded()) {
					utilisateurPaiementmedicale(requeteSite, b -> {
						if(b.succeeded()) {
							putimportPaiementmedicaleReponse(requeteSite, c -> {
								if(c.succeeded()) {
									gestionnaireEvenements.handle(Future.succeededFuture(c.result()));
									WorkerExecutor executeurTravailleur = siteContexte.getExecuteurTravailleur();
									executeurTravailleur.executeBlocking(
										blockingCodeHandler -> {
											RequeteSiteFrFR requeteSite2 = genererRequeteSiteFrFRPourPaiementmedicale(siteContexte, operationRequete, body);
											try {
												RequeteApi requeteApi = new RequeteApi();
												JsonArray jsonArray = Optional.ofNullable(requeteSite2.getObjetJson()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
												requeteApi.setRows(jsonArray.size());
												requeteApi.setNumFound(new Integer(jsonArray.size()).longValue());
												requeteApi.setNumPATCH(0L);
												requeteApi.initLoinRequeteApi(requeteSite2);
												requeteSite2.setRequeteApi_(requeteApi);
												requeteSite2.getVertx().eventBus().publish("websocketPaiementmedicale", JsonObject.mapFrom(requeteApi).toString());
												sqlPaiementmedicale(requeteSite2, d -> {
													if(d.succeeded()) {
														listePUTImportPaiementmedicale(requeteApi, requeteSite2, e -> {
															if(e.succeeded()) {
																putimportPaiementmedicaleReponse(requeteSite2, f -> {
																	if(f.succeeded()) {
																		LOGGER.info(String.format("putimportPaiementmedicale a réussi. "));
																		blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																	} else {
																		LOGGER.error(String.format("putimportPaiementmedicale a échoué. ", f.cause()));
																		erreurPaiementmedicale(requeteSite2, null, f);
																	}
																});
															} else {
																LOGGER.error(String.format("putimportPaiementmedicale a échoué. ", e.cause()));
																erreurPaiementmedicale(requeteSite2, null, e);
															}
														});
													} else {
														LOGGER.error(String.format("putimportPaiementmedicale a échoué. ", d.cause()));
														erreurPaiementmedicale(requeteSite2, null, d);
													}
												});
											} catch(Exception ex) {
												LOGGER.error(String.format("putimportPaiementmedicale a échoué. ", ex));
												erreurPaiementmedicale(requeteSite2, null, Future.failedFuture(ex));
											}
										}, resultHandler -> {
										}
									);
								} else {
									LOGGER.error(String.format("putimportPaiementmedicale a échoué. ", c.cause()));
									erreurPaiementmedicale(requeteSite, gestionnaireEvenements, c);
								}
							});
						} else {
							LOGGER.error(String.format("putimportPaiementmedicale a échoué. ", b.cause()));
							erreurPaiementmedicale(requeteSite, gestionnaireEvenements, b);
						}
					});
				} else {
					LOGGER.error(String.format("putimportPaiementmedicale a échoué. ", a.cause()));
					erreurPaiementmedicale(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putimportPaiementmedicale a échoué. ", ex));
			erreurPaiementmedicale(requeteSite, gestionnaireEvenements, Future.failedFuture(ex));
		}
	}


	public void listePUTImportPaiementmedicale(RequeteApi requeteApi, RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		List<Future> futures = new ArrayList<>();
		JsonArray jsonArray = Optional.ofNullable(requeteSite.getObjetJson()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
		try {
			jsonArray.forEach(obj -> {
				JsonObject json = (JsonObject)obj;

				json.put("inheritPk", json.getValue("pk"));

				RequeteSiteFrFR requeteSite2 = genererRequeteSiteFrFRPourPaiementmedicale(siteContexte, requeteSite.getOperationRequete(), json);
				requeteSite2.setConnexionSql(requeteSite.getConnexionSql());
				requeteSite2.setRequeteApi_(requeteApi);

				ListeRecherche<Paiementmedicale> listeRecherche = new ListeRecherche<Paiementmedicale>();
				listeRecherche.setStocker(true);
				listeRecherche.setQuery("*:*");
				listeRecherche.setC(Paiementmedicale.class);
				listeRecherche.addFilterQuery("inheritPk_indexed_long:" + json.getString("pk"));
				listeRecherche.initLoinPourClasse(requeteSite2);

				if(listeRecherche.size() == 1) {
					Paiementmedicale o = listeRecherche.getList().stream().findFirst().orElse(null);
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
							patchPaiementmedicaleFuture(o, true, a -> {
								if(a.succeeded()) {
								} else {
									erreurPaiementmedicale(requeteSite2, gestionnaireEvenements, a);
								}
							})
						);
					}
				} else {
					futures.add(
						postPaiementmedicaleFuture(requeteSite2, true, a -> {
							if(a.succeeded()) {
							} else {
								erreurPaiementmedicale(requeteSite2, gestionnaireEvenements, a);
							}
						})
					);
				}
			});
			CompositeFuture.all(futures).setHandler( a -> {
				if(a.succeeded()) {
					requeteApi.setNumPATCH(requeteApi.getNumPATCH() + 1);
					reponse200PUTImportPaiementmedicale(requeteSite, gestionnaireEvenements);
				} else {
					erreurPaiementmedicale(requeteApi.getRequeteSite_(), gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putimportPaiementmedicale a échoué. ", ex));
			erreurPaiementmedicale(requeteSite, null, Future.failedFuture(ex));
		}
	}

	public void putimportPaiementmedicaleReponse(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			reponse200PUTImportPaiementmedicale(requeteSite, a -> {
				if(a.succeeded()) {
					SQLConnection connexionSql = requeteSite.getConnexionSql();
					connexionSql.commit(b -> {
						if(b.succeeded()) {
							connexionSql.close(c -> {
								if(c.succeeded()) {
									gestionnaireEvenements.handle(Future.succeededFuture(a.result()));
								} else {
									erreurPaiementmedicale(requeteSite, gestionnaireEvenements, c);
								}
							});
						} else {
							erreurPaiementmedicale(requeteSite, gestionnaireEvenements, b);
						}
					});
				} else {
					erreurPaiementmedicale(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putimportPaiementmedicale a échoué. ", ex));
			erreurPaiementmedicale(requeteSite, null, Future.failedFuture(ex));
		}
	}
	public void reponse200PUTImportPaiementmedicale(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			JsonObject json = new JsonObject();
			gestionnaireEvenements.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	// PUTFusion //

	@Override
	public void putfusionPaiementmedicale(JsonObject body, OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = genererRequeteSiteFrFRPourPaiementmedicale(siteContexte, operationRequete, body);
		try {
			LOGGER.info(String.format("putfusionPaiementmedicale a démarré. "));
			sqlPaiementmedicale(requeteSite, a -> {
				if(a.succeeded()) {
					utilisateurPaiementmedicale(requeteSite, b -> {
						if(b.succeeded()) {
							putfusionPaiementmedicaleReponse(requeteSite, c -> {
								if(c.succeeded()) {
									gestionnaireEvenements.handle(Future.succeededFuture(c.result()));
									WorkerExecutor executeurTravailleur = siteContexte.getExecuteurTravailleur();
									executeurTravailleur.executeBlocking(
										blockingCodeHandler -> {
											RequeteSiteFrFR requeteSite2 = genererRequeteSiteFrFRPourPaiementmedicale(siteContexte, operationRequete, body);
											try {
												RequeteApi requeteApi = new RequeteApi();
												JsonArray jsonArray = Optional.ofNullable(requeteSite2.getObjetJson()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
												requeteApi.setRows(jsonArray.size());
												requeteApi.setNumFound(new Integer(jsonArray.size()).longValue());
												requeteApi.setNumPATCH(0L);
												requeteApi.initLoinRequeteApi(requeteSite2);
												requeteSite2.setRequeteApi_(requeteApi);
												requeteSite2.getVertx().eventBus().publish("websocketPaiementmedicale", JsonObject.mapFrom(requeteApi).toString());
												sqlPaiementmedicale(requeteSite2, d -> {
													if(d.succeeded()) {
														listePUTFusionPaiementmedicale(requeteApi, requeteSite2, e -> {
															if(e.succeeded()) {
																putfusionPaiementmedicaleReponse(requeteSite2, f -> {
																	if(f.succeeded()) {
																		LOGGER.info(String.format("putfusionPaiementmedicale a réussi. "));
																		blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																	} else {
																		LOGGER.error(String.format("putfusionPaiementmedicale a échoué. ", f.cause()));
																		erreurPaiementmedicale(requeteSite2, null, f);
																	}
																});
															} else {
																LOGGER.error(String.format("putfusionPaiementmedicale a échoué. ", e.cause()));
																erreurPaiementmedicale(requeteSite2, null, e);
															}
														});
													} else {
														LOGGER.error(String.format("putfusionPaiementmedicale a échoué. ", d.cause()));
														erreurPaiementmedicale(requeteSite2, null, d);
													}
												});
											} catch(Exception ex) {
												LOGGER.error(String.format("putfusionPaiementmedicale a échoué. ", ex));
												erreurPaiementmedicale(requeteSite2, null, Future.failedFuture(ex));
											}
										}, resultHandler -> {
										}
									);
								} else {
									LOGGER.error(String.format("putfusionPaiementmedicale a échoué. ", c.cause()));
									erreurPaiementmedicale(requeteSite, gestionnaireEvenements, c);
								}
							});
						} else {
							LOGGER.error(String.format("putfusionPaiementmedicale a échoué. ", b.cause()));
							erreurPaiementmedicale(requeteSite, gestionnaireEvenements, b);
						}
					});
				} else {
					LOGGER.error(String.format("putfusionPaiementmedicale a échoué. ", a.cause()));
					erreurPaiementmedicale(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putfusionPaiementmedicale a échoué. ", ex));
			erreurPaiementmedicale(requeteSite, gestionnaireEvenements, Future.failedFuture(ex));
		}
	}


	public void listePUTFusionPaiementmedicale(RequeteApi requeteApi, RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		List<Future> futures = new ArrayList<>();
		JsonArray jsonArray = Optional.ofNullable(requeteSite.getObjetJson()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
		try {
			jsonArray.forEach(obj -> {
				JsonObject json = (JsonObject)obj;

				json.put("inheritPk", json.getValue("pk"));

				RequeteSiteFrFR requeteSite2 = genererRequeteSiteFrFRPourPaiementmedicale(siteContexte, requeteSite.getOperationRequete(), json);
				requeteSite2.setConnexionSql(requeteSite.getConnexionSql());
				requeteSite2.setRequeteApi_(requeteApi);

				ListeRecherche<Paiementmedicale> listeRecherche = new ListeRecherche<Paiementmedicale>();
				listeRecherche.setStocker(true);
				listeRecherche.setQuery("*:*");
				listeRecherche.setC(Paiementmedicale.class);
				listeRecherche.addFilterQuery("pk_indexed_long:" + json.getString("pk"));
				listeRecherche.initLoinPourClasse(requeteSite2);

				if(listeRecherche.size() == 1) {
					Paiementmedicale o = listeRecherche.getList().stream().findFirst().orElse(null);
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
							patchPaiementmedicaleFuture(o, false, a -> {
								if(a.succeeded()) {
								} else {
									erreurPaiementmedicale(requeteSite2, gestionnaireEvenements, a);
								}
							})
						);
					}
				} else {
					futures.add(
						postPaiementmedicaleFuture(requeteSite2, false, a -> {
							if(a.succeeded()) {
							} else {
								erreurPaiementmedicale(requeteSite2, gestionnaireEvenements, a);
							}
						})
					);
				}
			});
			CompositeFuture.all(futures).setHandler( a -> {
				if(a.succeeded()) {
					requeteApi.setNumPATCH(requeteApi.getNumPATCH() + 1);
					reponse200PUTFusionPaiementmedicale(requeteSite, gestionnaireEvenements);
				} else {
					erreurPaiementmedicale(requeteApi.getRequeteSite_(), gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putfusionPaiementmedicale a échoué. ", ex));
			erreurPaiementmedicale(requeteSite, null, Future.failedFuture(ex));
		}
	}

	public void putfusionPaiementmedicaleReponse(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			reponse200PUTFusionPaiementmedicale(requeteSite, a -> {
				if(a.succeeded()) {
					SQLConnection connexionSql = requeteSite.getConnexionSql();
					connexionSql.commit(b -> {
						if(b.succeeded()) {
							connexionSql.close(c -> {
								if(c.succeeded()) {
									gestionnaireEvenements.handle(Future.succeededFuture(a.result()));
								} else {
									erreurPaiementmedicale(requeteSite, gestionnaireEvenements, c);
								}
							});
						} else {
							erreurPaiementmedicale(requeteSite, gestionnaireEvenements, b);
						}
					});
				} else {
					erreurPaiementmedicale(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putfusionPaiementmedicale a échoué. ", ex));
			erreurPaiementmedicale(requeteSite, null, Future.failedFuture(ex));
		}
	}
	public void reponse200PUTFusionPaiementmedicale(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			JsonObject json = new JsonObject();
			gestionnaireEvenements.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	// PUTCopie //

	@Override
	public void putcopiePaiementmedicale(JsonObject body, OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = genererRequeteSiteFrFRPourPaiementmedicale(siteContexte, operationRequete, body);
		try {
			LOGGER.info(String.format("putcopiePaiementmedicale a démarré. "));
			sqlPaiementmedicale(requeteSite, a -> {
				if(a.succeeded()) {
					utilisateurPaiementmedicale(requeteSite, b -> {
						if(b.succeeded()) {
							putcopiePaiementmedicaleReponse(requeteSite, c -> {
								if(c.succeeded()) {
									gestionnaireEvenements.handle(Future.succeededFuture(c.result()));
									WorkerExecutor executeurTravailleur = siteContexte.getExecuteurTravailleur();
									executeurTravailleur.executeBlocking(
										blockingCodeHandler -> {
											RequeteSiteFrFR requeteSite2 = genererRequeteSiteFrFRPourPaiementmedicale(siteContexte, operationRequete, body);
											try {
												recherchePaiementmedicale(requeteSite2, false, true, "/api/paiement/copie", "PUTCopie", d -> {
													if(d.succeeded()) {
														ListeRecherche<Paiementmedicale> listePaiementmedicale = d.result();
														RequeteApi requeteApi = new RequeteApi();
														requeteApi.setRows(listePaiementmedicale.getRows());
														requeteApi.setNumFound(listePaiementmedicale.getQueryResponse().getResults().getNumFound());
														requeteApi.setNumPATCH(0L);
														requeteApi.initLoinRequeteApi(requeteSite2);
														requeteSite2.setRequeteApi_(requeteApi);
														requeteSite2.getVertx().eventBus().publish("websocketPaiementmedicale", JsonObject.mapFrom(requeteApi).toString());
														sqlPaiementmedicale(requeteSite2, e -> {
															if(e.succeeded()) {
																try {
																	listePUTCopiePaiementmedicale(requeteApi, listePaiementmedicale, f -> {
																		if(f.succeeded()) {
																			putcopiePaiementmedicaleReponse(requeteSite2, g -> {
																				if(g.succeeded()) {
																					LOGGER.info(String.format("putcopiePaiementmedicale a réussi. "));
																					blockingCodeHandler.handle(Future.succeededFuture(g.result()));
																				} else {
																					LOGGER.error(String.format("putcopiePaiementmedicale a échoué. ", g.cause()));
																					erreurPaiementmedicale(requeteSite2, null, g);
																				}
																			});
																		} else {
																			LOGGER.error(String.format("putcopiePaiementmedicale a échoué. ", f.cause()));
																			erreurPaiementmedicale(requeteSite2, null, f);
																		}
																	});
																} catch(Exception ex) {
																	LOGGER.error(String.format("putcopiePaiementmedicale a échoué. ", ex));
																	erreurPaiementmedicale(requeteSite2, null, Future.failedFuture(ex));
																}
															} else {
																LOGGER.error(String.format("putcopiePaiementmedicale a échoué. ", e.cause()));
																erreurPaiementmedicale(requeteSite2, null, e);
															}
														});
													} else {
														LOGGER.error(String.format("putcopiePaiementmedicale a échoué. ", d.cause()));
														erreurPaiementmedicale(requeteSite2, null, d);
													}
												});
											} catch(Exception ex) {
												LOGGER.error(String.format("putcopiePaiementmedicale a échoué. ", ex));
												erreurPaiementmedicale(requeteSite2, null, Future.failedFuture(ex));
											}
										}, resultHandler -> {
										}
									);
								} else {
									LOGGER.error(String.format("putcopiePaiementmedicale a échoué. ", c.cause()));
									erreurPaiementmedicale(requeteSite, gestionnaireEvenements, c);
								}
							});
						} else {
							LOGGER.error(String.format("putcopiePaiementmedicale a échoué. ", b.cause()));
							erreurPaiementmedicale(requeteSite, gestionnaireEvenements, b);
						}
					});
				} else {
					LOGGER.error(String.format("putcopiePaiementmedicale a échoué. ", a.cause()));
					erreurPaiementmedicale(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putcopiePaiementmedicale a échoué. ", ex));
			erreurPaiementmedicale(requeteSite, gestionnaireEvenements, Future.failedFuture(ex));
		}
	}


	public void listePUTCopiePaiementmedicale(RequeteApi requeteApi, ListeRecherche<Paiementmedicale> listePaiementmedicale, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		List<Future> futures = new ArrayList<>();
		RequeteSiteFrFR requeteSite = listePaiementmedicale.getRequeteSite_();
		listePaiementmedicale.getList().forEach(o -> {
			futures.add(
				putcopiePaiementmedicaleFuture(requeteSite, JsonObject.mapFrom(o), a -> {
					if(a.succeeded()) {
						Paiementmedicale paiementmedicale = a.result();
					} else {
						erreurPaiementmedicale(requeteSite, gestionnaireEvenements, a);
					}
				})
			);
		});
		CompositeFuture.all(futures).setHandler( a -> {
			if(a.succeeded()) {
				requeteApi.setNumPATCH(requeteApi.getNumPATCH() + listePaiementmedicale.size());
				if(listePaiementmedicale.next()) {
					listePUTCopiePaiementmedicale(requeteApi, listePaiementmedicale, gestionnaireEvenements);
				} else {
					reponse200PUTCopiePaiementmedicale(requeteSite, gestionnaireEvenements);
				}
			} else {
				erreurPaiementmedicale(listePaiementmedicale.getRequeteSite_(), gestionnaireEvenements, a);
			}
		});
	}

	public Future<Paiementmedicale> putcopiePaiementmedicaleFuture(RequeteSiteFrFR requeteSite, JsonObject jsonObject, Handler<AsyncResult<Paiementmedicale>> gestionnaireEvenements) {
		Promise<Paiementmedicale> promise = Promise.promise();
		try {

			jsonObject.put("sauvegardes", Optional.ofNullable(jsonObject.getJsonArray("sauvegardes")).orElse(new JsonArray()));
			JsonObject jsonPatch = Optional.ofNullable(requeteSite.getObjetJson()).map(o -> o.getJsonObject("patch")).orElse(new JsonObject());
			jsonPatch.stream().forEach(o -> {
				jsonObject.put(o.getKey(), o.getValue());
				jsonObject.getJsonArray("sauvegardes").add(o.getKey());
			});

			creerPaiementmedicale(requeteSite, a -> {
				if(a.succeeded()) {
					Paiementmedicale paiementmedicale = a.result();
					sqlPUTCopiePaiementmedicale(paiementmedicale, jsonObject, b -> {
						if(b.succeeded()) {
							definirPaiementmedicale(paiementmedicale, c -> {
								if(c.succeeded()) {
									attribuerPaiementmedicale(paiementmedicale, d -> {
										if(d.succeeded()) {
											indexerPaiementmedicale(paiementmedicale, e -> {
												if(e.succeeded()) {
													RequeteApi requeteApi = requeteSite.getRequeteApi_();
													if(requeteApi != null) {
														requeteApi.setNumPATCH(requeteApi.getNumPATCH() + 1);
														if(requeteApi.getNumFound() == 1L) {
															paiementmedicale.requeteApiPaiementmedicale();
														}
														requeteSite.getVertx().eventBus().publish("websocketPaiementmedicale", JsonObject.mapFrom(requeteApi).toString());
													}
													SQLConnection connexionSql = requeteSite.getConnexionSql();
													connexionSql.commit(f -> {
														if(f.succeeded()) {
															gestionnaireEvenements.handle(Future.succeededFuture(paiementmedicale));
															promise.complete(paiementmedicale);
														} else {
															gestionnaireEvenements.handle(Future.failedFuture(f.cause()));
														}
													});
												} else {
													gestionnaireEvenements.handle(Future.failedFuture(e.cause()));
												}
											});
										} else {
											gestionnaireEvenements.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									gestionnaireEvenements.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							gestionnaireEvenements.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					gestionnaireEvenements.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			erreurPaiementmedicale(requeteSite, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPUTCopiePaiementmedicale(Paiementmedicale o, JsonObject jsonObject, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = o.getRequeteSite_();
			RequeteApi requeteApi = requeteSite.getRequeteApi_();
			List<Long> pks = Optional.ofNullable(requeteApi).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(requeteApi).map(r -> r.getClasses()).orElse(new ArrayList<>());
			SQLConnection connexionSql = requeteSite.getConnexionSql();
			Long pk = o.getPk();
			StringBuilder putSql = new StringBuilder();
			List<Object> putSqlParams = new ArrayList<Object>();

			if(jsonObject != null) {
				JsonArray entiteVars = jsonObject.getJsonArray("sauvegardes");
				for(Integer i = 0; i < entiteVars.size(); i++) {
					String entiteVar = entiteVars.getString(i);
					switch(entiteVar) {
					case "inheritPk":
						putSql.append(SiteContexteFrFR.SQL_setD);
						putSqlParams.addAll(Arrays.asList(pk, "inheritPk", jsonObject.getString(entiteVar)));
						break;
					case "cree":
						putSql.append(SiteContexteFrFR.SQL_setD);
						putSqlParams.addAll(Arrays.asList(pk, "cree", jsonObject.getString(entiteVar)));
						break;
					case "modifie":
						putSql.append(SiteContexteFrFR.SQL_setD);
						putSqlParams.addAll(Arrays.asList(pk, "modifie", jsonObject.getString(entiteVar)));
						break;
					case "archive":
						putSql.append(SiteContexteFrFR.SQL_setD);
						putSqlParams.addAll(Arrays.asList(pk, "archive", jsonObject.getBoolean(entiteVar)));
						break;
					case "supprime":
						putSql.append(SiteContexteFrFR.SQL_setD);
						putSqlParams.addAll(Arrays.asList(pk, "supprime", jsonObject.getBoolean(entiteVar)));
						break;
					case "sessionId":
						putSql.append(SiteContexteFrFR.SQL_setD);
						putSqlParams.addAll(Arrays.asList(pk, "sessionId", jsonObject.getString(entiteVar)));
						break;
					case "utilisateurId":
						putSql.append(SiteContexteFrFR.SQL_setD);
						putSqlParams.addAll(Arrays.asList(pk, "utilisateurId", jsonObject.getString(entiteVar)));
						break;
					case "utilisateurCle":
						putSql.append(SiteContexteFrFR.SQL_setD);
						putSqlParams.addAll(Arrays.asList(pk, "utilisateurCle", jsonObject.getString(entiteVar)));
						break;
					case "inscriptionCle":
							{
						Long l = Long.parseLong(jsonObject.getString(entiteVar));
						putSql.append(SiteContexteFrFR.SQL_addA);
						putSqlParams.addAll(Arrays.asList(pk, "inscriptionCle", l, "paiementCles"));
						}
						break;
					case "enfantNomCompletPrefere":
						putSql.append(SiteContexteFrFR.SQL_setD);
						putSqlParams.addAll(Arrays.asList(pk, "enfantNomCompletPrefere", jsonObject.getString(entiteVar)));
						break;
					case "enfantDateNaissance":
						putSql.append(SiteContexteFrFR.SQL_setD);
						putSqlParams.addAll(Arrays.asList(pk, "enfantDateNaissance", DateTimeFormatter.ofPattern("MM/dd/yyyy").format(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(jsonObject.getString(entiteVar)))));
						break;
					case "mereNomCompletPrefere":
						putSql.append(SiteContexteFrFR.SQL_setD);
						putSqlParams.addAll(Arrays.asList(pk, "mereNomCompletPrefere", jsonObject.getString(entiteVar)));
						break;
					case "pereNomCompletPrefere":
						putSql.append(SiteContexteFrFR.SQL_setD);
						putSqlParams.addAll(Arrays.asList(pk, "pereNomCompletPrefere", jsonObject.getString(entiteVar)));
						break;
					case "inscriptionPaimentChaqueMois":
						putSql.append(SiteContexteFrFR.SQL_setD);
						putSqlParams.addAll(Arrays.asList(pk, "inscriptionPaimentChaqueMois", jsonObject.getBoolean(entiteVar)));
						break;
					case "inscriptionPaimentComplet":
						putSql.append(SiteContexteFrFR.SQL_setD);
						putSqlParams.addAll(Arrays.asList(pk, "inscriptionPaimentComplet", jsonObject.getBoolean(entiteVar)));
						break;
					case "paiementDescription":
						putSql.append(SiteContexteFrFR.SQL_setD);
						putSqlParams.addAll(Arrays.asList(pk, "paiementDescription", jsonObject.getString(entiteVar)));
						break;
					case "paiementDate":
						putSql.append(SiteContexteFrFR.SQL_setD);
						putSqlParams.addAll(Arrays.asList(pk, "paiementDate", DateTimeFormatter.ofPattern("MM/dd/yyyy").format(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(jsonObject.getString(entiteVar)))));
						break;
					case "paiementMontant":
						putSql.append(SiteContexteFrFR.SQL_setD);
						putSqlParams.addAll(Arrays.asList(pk, "paiementMontant", jsonObject.getString(entiteVar)));
						break;
					case "paiementEspeces":
						putSql.append(SiteContexteFrFR.SQL_setD);
						putSqlParams.addAll(Arrays.asList(pk, "paiementEspeces", jsonObject.getBoolean(entiteVar)));
						break;
					case "paiementCheque":
						putSql.append(SiteContexteFrFR.SQL_setD);
						putSqlParams.addAll(Arrays.asList(pk, "paiementCheque", jsonObject.getBoolean(entiteVar)));
						break;
					case "paiementSysteme":
						putSql.append(SiteContexteFrFR.SQL_setD);
						putSqlParams.addAll(Arrays.asList(pk, "paiementSysteme", jsonObject.getBoolean(entiteVar)));
						break;
					case "paiementPar":
						putSql.append(SiteContexteFrFR.SQL_setD);
						putSqlParams.addAll(Arrays.asList(pk, "paiementPar", jsonObject.getString(entiteVar)));
						break;
					case "transactionId":
						putSql.append(SiteContexteFrFR.SQL_setD);
						putSqlParams.addAll(Arrays.asList(pk, "transactionId", jsonObject.getString(entiteVar)));
						break;
					case "customerProfileId":
						putSql.append(SiteContexteFrFR.SQL_setD);
						putSqlParams.addAll(Arrays.asList(pk, "customerProfileId", jsonObject.getString(entiteVar)));
						break;
					case "transactionStatus":
						putSql.append(SiteContexteFrFR.SQL_setD);
						putSqlParams.addAll(Arrays.asList(pk, "transactionStatus", jsonObject.getString(entiteVar)));
						break;
					case "paiementRecu":
						putSql.append(SiteContexteFrFR.SQL_setD);
						putSqlParams.addAll(Arrays.asList(pk, "paiementRecu", jsonObject.getBoolean(entiteVar)));
						break;
					case "fraisMontant":
						putSql.append(SiteContexteFrFR.SQL_setD);
						putSqlParams.addAll(Arrays.asList(pk, "fraisMontant", jsonObject.getString(entiteVar)));
						break;
					case "fraisMontantDu":
						putSql.append(SiteContexteFrFR.SQL_setD);
						putSqlParams.addAll(Arrays.asList(pk, "fraisMontantDu", jsonObject.getString(entiteVar)));
						break;
					case "fraisMontantFuture":
						putSql.append(SiteContexteFrFR.SQL_setD);
						putSqlParams.addAll(Arrays.asList(pk, "fraisMontantFuture", jsonObject.getString(entiteVar)));
						break;
					case "fraisPremierDernier":
						putSql.append(SiteContexteFrFR.SQL_setD);
						putSqlParams.addAll(Arrays.asList(pk, "fraisPremierDernier", jsonObject.getBoolean(entiteVar)));
						break;
					case "fraisInscription":
						putSql.append(SiteContexteFrFR.SQL_setD);
						putSqlParams.addAll(Arrays.asList(pk, "fraisInscription", jsonObject.getBoolean(entiteVar)));
						break;
					case "fraisMois":
						putSql.append(SiteContexteFrFR.SQL_setD);
						putSqlParams.addAll(Arrays.asList(pk, "fraisMois", jsonObject.getBoolean(entiteVar)));
						break;
					case "fraisRetard":
						putSql.append(SiteContexteFrFR.SQL_setD);
						putSqlParams.addAll(Arrays.asList(pk, "fraisRetard", jsonObject.getBoolean(entiteVar)));
						break;
					case "paiementNomCourt":
						putSql.append(SiteContexteFrFR.SQL_setD);
						putSqlParams.addAll(Arrays.asList(pk, "paiementNomCourt", jsonObject.getString(entiteVar)));
						break;
					}
				}
			}
			connexionSql.queryWithParams(
					putSql.toString()
					, new JsonArray(putSqlParams)
					, postAsync
			-> {
				if(postAsync.succeeded()) {
					gestionnaireEvenements.handle(Future.succeededFuture());
				} else {
					gestionnaireEvenements.handle(Future.failedFuture(new Exception(postAsync.cause())));
				}
			});
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void putcopiePaiementmedicaleReponse(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			reponse200PUTCopiePaiementmedicale(requeteSite, a -> {
				if(a.succeeded()) {
					SQLConnection connexionSql = requeteSite.getConnexionSql();
					connexionSql.commit(b -> {
						if(b.succeeded()) {
							connexionSql.close(c -> {
								if(c.succeeded()) {
									gestionnaireEvenements.handle(Future.succeededFuture(a.result()));
								} else {
									erreurPaiementmedicale(requeteSite, gestionnaireEvenements, c);
								}
							});
						} else {
							erreurPaiementmedicale(requeteSite, gestionnaireEvenements, b);
						}
					});
				} else {
					erreurPaiementmedicale(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putcopiePaiementmedicale a échoué. ", ex));
			erreurPaiementmedicale(requeteSite, null, Future.failedFuture(ex));
		}
	}
	public void reponse200PUTCopiePaiementmedicale(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			JsonObject json = new JsonObject();
			gestionnaireEvenements.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	// PATCH //

	@Override
	public void patchPaiementmedicale(JsonObject body, OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = genererRequeteSiteFrFRPourPaiementmedicale(siteContexte, operationRequete, body);
		try {
			LOGGER.info(String.format("patchPaiementmedicale a démarré. "));
			sqlPaiementmedicale(requeteSite, a -> {
				if(a.succeeded()) {
					utilisateurPaiementmedicale(requeteSite, b -> {
						if(b.succeeded()) {
							patchPaiementmedicaleReponse(requeteSite, c -> {
								if(c.succeeded()) {
									gestionnaireEvenements.handle(Future.succeededFuture(c.result()));
									WorkerExecutor executeurTravailleur = siteContexte.getExecuteurTravailleur();
									executeurTravailleur.executeBlocking(
										blockingCodeHandler -> {
											RequeteSiteFrFR requeteSite2 = genererRequeteSiteFrFRPourPaiementmedicale(siteContexte, operationRequete, body);
											try {
												recherchePaiementmedicale(requeteSite2, false, true, "/api/paiement", "PATCH", d -> {
													if(d.succeeded()) {
														ListeRecherche<Paiementmedicale> listePaiementmedicale = d.result();
														RequeteApi requeteApi = new RequeteApi();
														requeteApi.setRows(listePaiementmedicale.getRows());
														requeteApi.setNumFound(listePaiementmedicale.getQueryResponse().getResults().getNumFound());
														requeteApi.setNumPATCH(0L);
														requeteApi.initLoinRequeteApi(requeteSite2);
														requeteSite2.setRequeteApi_(requeteApi);
														requeteSite2.getVertx().eventBus().publish("websocketPaiementmedicale", JsonObject.mapFrom(requeteApi).toString());
														SimpleOrderedMap facets = (SimpleOrderedMap)Optional.ofNullable(listePaiementmedicale.getQueryResponse()).map(QueryResponse::getResponse).map(r -> r.get("facets")).orElse(null);
														Date date = null;
														if(facets != null)
															date = (Date)facets.get("max_modifie");
														String dt;
														if(date == null)
															dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(ZonedDateTime.now().toInstant(), ZoneId.of("UTC")).minusNanos(1000));
														else
															dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC")));
														listePaiementmedicale.addFilterQuery(String.format("modifie_indexed_date:[* TO %s]", dt));

														Paiementmedicale o = listePaiementmedicale.getList().stream().findFirst().orElse(null);
														sqlPaiementmedicale(requeteSite2, e -> {
															if(e.succeeded()) {
																try {
																	listePATCHPaiementmedicale(requeteApi, listePaiementmedicale, dt, f -> {
																		if(f.succeeded()) {
																			patchPaiementmedicaleReponse(requeteSite2, g -> {
																				if(g.succeeded()) {
																					LOGGER.info(String.format("patchPaiementmedicale a réussi. "));
																					blockingCodeHandler.handle(Future.succeededFuture(g.result()));
																				} else {
																					LOGGER.error(String.format("patchPaiementmedicale a échoué. ", g.cause()));
																					erreurPaiementmedicale(requeteSite2, null, g);
																				}
																			});
																		} else {
																			LOGGER.error(String.format("patchPaiementmedicale a échoué. ", f.cause()));
																			erreurPaiementmedicale(requeteSite2, null, f);
																		}
																	});
																} catch(Exception ex) {
																	LOGGER.error(String.format("patchPaiementmedicale a échoué. ", ex));
																	erreurPaiementmedicale(requeteSite2, null, Future.failedFuture(ex));
																}
															} else {
																LOGGER.error(String.format("patchPaiementmedicale a échoué. ", e.cause()));
																erreurPaiementmedicale(requeteSite2, null, e);
															}
														});
													} else {
														LOGGER.error(String.format("patchPaiementmedicale a échoué. ", d.cause()));
														erreurPaiementmedicale(requeteSite2, null, d);
													}
												});
											} catch(Exception ex) {
												LOGGER.error(String.format("patchPaiementmedicale a échoué. ", ex));
												erreurPaiementmedicale(requeteSite2, null, Future.failedFuture(ex));
											}
										}, resultHandler -> {
										}
									);
								} else {
									LOGGER.error(String.format("patchPaiementmedicale a échoué. ", c.cause()));
									erreurPaiementmedicale(requeteSite, gestionnaireEvenements, c);
								}
							});
						} else {
							LOGGER.error(String.format("patchPaiementmedicale a échoué. ", b.cause()));
							erreurPaiementmedicale(requeteSite, gestionnaireEvenements, b);
						}
					});
				} else {
					LOGGER.error(String.format("patchPaiementmedicale a échoué. ", a.cause()));
					erreurPaiementmedicale(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("patchPaiementmedicale a échoué. ", ex));
			erreurPaiementmedicale(requeteSite, gestionnaireEvenements, Future.failedFuture(ex));
		}
	}


	public void listePATCHPaiementmedicale(RequeteApi requeteApi, ListeRecherche<Paiementmedicale> listePaiementmedicale, String dt, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		List<Future> futures = new ArrayList<>();
		RequeteSiteFrFR requeteSite = listePaiementmedicale.getRequeteSite_();
		listePaiementmedicale.getList().forEach(o -> {
			futures.add(
				patchPaiementmedicaleFuture(o, false, a -> {
					if(a.succeeded()) {
					} else {
						erreurPaiementmedicale(requeteSite, gestionnaireEvenements, a);
					}
				})
			);
		});
		CompositeFuture.all(futures).setHandler( a -> {
			if(a.succeeded()) {
				if(listePaiementmedicale.next(dt)) {
					listePATCHPaiementmedicale(requeteApi, listePaiementmedicale, dt, gestionnaireEvenements);
				} else {
					reponse200PATCHPaiementmedicale(requeteSite, gestionnaireEvenements);
				}
			} else {
				erreurPaiementmedicale(listePaiementmedicale.getRequeteSite_(), gestionnaireEvenements, a);
			}
		});
	}

	public Future<Paiementmedicale> patchPaiementmedicaleFuture(Paiementmedicale o, Boolean inheritPk, Handler<AsyncResult<Paiementmedicale>> gestionnaireEvenements) {
		Promise<Paiementmedicale> promise = Promise.promise();
		RequeteSiteFrFR requeteSite = o.getRequeteSite_();
		try {
			RequeteApi requeteApi = requeteSite.getRequeteApi_();
			if(requeteApi != null && requeteApi.getNumFound() == 1L) {
				requeteApi.setOriginal(o);
				requeteApi.setPk(o.getPk());
			}
			sqlPATCHPaiementmedicale(o, inheritPk, a -> {
				if(a.succeeded()) {
					Paiementmedicale paiementmedicale = a.result();
					definirPaiementmedicale(paiementmedicale, b -> {
						if(b.succeeded()) {
							attribuerPaiementmedicale(paiementmedicale, c -> {
								if(c.succeeded()) {
									indexerPaiementmedicale(paiementmedicale, d -> {
										if(d.succeeded()) {
											if(requeteApi != null) {
												requeteApi.setNumPATCH(requeteApi.getNumPATCH() + 1);
												if(requeteApi.getNumFound() == 1L) {
													paiementmedicale.requeteApiPaiementmedicale();
												}
												requeteSite.getVertx().eventBus().publish("websocketPaiementmedicale", JsonObject.mapFrom(requeteApi).toString());
											}
											SQLConnection connexionSql = requeteSite.getConnexionSql();
											connexionSql.commit(e -> {
												if(e.succeeded()) {
													gestionnaireEvenements.handle(Future.succeededFuture(paiementmedicale));
													promise.complete(paiementmedicale);
												} else {
													gestionnaireEvenements.handle(Future.failedFuture(e.cause()));
												}
											});
										} else {
											gestionnaireEvenements.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									gestionnaireEvenements.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							gestionnaireEvenements.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					gestionnaireEvenements.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			erreurPaiementmedicale(requeteSite, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPATCHPaiementmedicale(Paiementmedicale o, Boolean inheritPk, Handler<AsyncResult<Paiementmedicale>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = o.getRequeteSite_();
			RequeteApi requeteApi = requeteSite.getRequeteApi_();
			List<Long> pks = Optional.ofNullable(requeteApi).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(requeteApi).map(r -> r.getClasses()).orElse(new ArrayList<>());
			SQLConnection connexionSql = requeteSite.getConnexionSql();
			Long pk = o.getPk();
			JsonObject jsonObject = requeteSite.getObjetJson();
			StringBuilder patchSql = new StringBuilder();
			List<Object> patchSqlParams = new ArrayList<Object>();
			Set<String> methodeNoms = jsonObject.fieldNames();
			Paiementmedicale o2 = new Paiementmedicale();

			if(o.getUtilisateurId() == null && requeteSite.getUtilisateurId() != null) {
				patchSql.append(SiteContexteFrFR.SQL_setD);
				patchSqlParams.addAll(Arrays.asList(pk, "utilisateurId", requeteSite.getUtilisateurId()));
			}
			if(o.getUtilisateurCle() == null && requeteSite.getUtilisateurCle() != null) {
				patchSql.append(SiteContexteFrFR.SQL_setD);
				patchSqlParams.addAll(Arrays.asList(pk, "utilisateurCle", requeteSite.getUtilisateurCle()));

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
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "inheritPk"));
						} else {
							o2.setInheritPk(jsonObject.getString(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList(pk, "inheritPk", o2.jsonInheritPk()));
						}
						break;
					case "setCree":
						if(jsonObject.getString(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "cree"));
						} else {
							o2.setCree(jsonObject.getString(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList(pk, "cree", o2.jsonCree()));
						}
						break;
					case "setModifie":
						if(jsonObject.getString(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "modifie"));
						} else {
							o2.setModifie(jsonObject.getString(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList(pk, "modifie", o2.jsonModifie()));
						}
						break;
					case "setArchive":
						if(jsonObject.getBoolean(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "archive"));
						} else {
							o2.setArchive(jsonObject.getBoolean(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList(pk, "archive", o2.jsonArchive()));
						}
						break;
					case "setSupprime":
						if(jsonObject.getBoolean(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "supprime"));
						} else {
							o2.setSupprime(jsonObject.getBoolean(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList(pk, "supprime", o2.jsonSupprime()));
						}
						break;
					case "setSessionId":
						if(jsonObject.getString(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "sessionId"));
						} else {
							o2.setSessionId(jsonObject.getString(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList(pk, "sessionId", o2.jsonSessionId()));
						}
						break;
					case "setUtilisateurId":
						if(jsonObject.getString(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "utilisateurId"));
						} else {
							o2.setUtilisateurId(jsonObject.getString(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList(pk, "utilisateurId", o2.jsonUtilisateurId()));
						}
						break;
					case "setUtilisateurCle":
						if(jsonObject.getString(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "utilisateurCle"));
						} else {
							o2.setUtilisateurCle(jsonObject.getString(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList(pk, "utilisateurCle", o2.jsonUtilisateurCle()));
						}
						break;
					case "setInscriptionCle":
						{
							Long l = o2.getInscriptionCle();
							if(l != null) {
								ListeRecherche<Inscriptionmedicale> listeRecherche = new ListeRecherche<Inscriptionmedicale>();
								listeRecherche.setQuery("*:*");
								listeRecherche.setStocker(true);
								listeRecherche.setC(Inscriptionmedicale.class);
								listeRecherche.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								listeRecherche.initLoinListeRecherche(requeteSite);
								l = Optional.ofNullable(listeRecherche.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l != null && !l.equals(o.getInscriptionCle())) {
									o2.setInscriptionCle(jsonObject.getString(methodeNom));
									patchSql.append(SiteContexteFrFR.SQL_addA);
									patchSqlParams.addAll(Arrays.asList(pk, "inscriptionCle", l, "paiementCles"));
									if(!pks.contains(l)) {
										pks.add(l);
										classes.add("Inscriptionmedicale");
									}
								}
							}
						}
						break;
					case "removeInscriptionCle":
						{
							Long l = o2.getInscriptionCle();
							if(l != null) {
								ListeRecherche<Inscriptionmedicale> listeRecherche = new ListeRecherche<Inscriptionmedicale>();
								listeRecherche.setQuery("*:*");
								listeRecherche.setStocker(true);
								listeRecherche.setC(Inscriptionmedicale.class);
								listeRecherche.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								listeRecherche.initLoinListeRecherche(requeteSite);
								l = Optional.ofNullable(listeRecherche.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l != null && l.equals(o.getInscriptionCle())) {
									o2.setInscriptionCle(jsonObject.getString(methodeNom));
									patchSql.append(SiteContexteFrFR.SQL_removeA);
									patchSqlParams.addAll(Arrays.asList("inscriptionCle", pk, "paiementCles", l));
									if(!pks.contains(l)) {
										pks.add(l);
										classes.add("Inscriptionmedicale");
									}
								}
							}
						}
						break;
					case "setEnfantNomCompletPrefere":
						if(jsonObject.getString(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enfantNomCompletPrefere"));
						} else {
							o2.setEnfantNomCompletPrefere(jsonObject.getString(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList(pk, "enfantNomCompletPrefere", o2.jsonEnfantNomCompletPrefere()));
						}
						break;
					case "setEnfantDateNaissance":
						if(jsonObject.getString(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enfantDateNaissance"));
						} else {
							o2.setEnfantDateNaissance(jsonObject.getString(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList(pk, "enfantDateNaissance", o2.jsonEnfantDateNaissance()));
						}
						break;
					case "setMereNomCompletPrefere":
						if(jsonObject.getString(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "mereNomCompletPrefere"));
						} else {
							o2.setMereNomCompletPrefere(jsonObject.getString(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList(pk, "mereNomCompletPrefere", o2.jsonMereNomCompletPrefere()));
						}
						break;
					case "setPereNomCompletPrefere":
						if(jsonObject.getString(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "pereNomCompletPrefere"));
						} else {
							o2.setPereNomCompletPrefere(jsonObject.getString(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList(pk, "pereNomCompletPrefere", o2.jsonPereNomCompletPrefere()));
						}
						break;
					case "setInscriptionPaimentChaqueMois":
						if(jsonObject.getBoolean(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "inscriptionPaimentChaqueMois"));
						} else {
							o2.setInscriptionPaimentChaqueMois(jsonObject.getBoolean(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList(pk, "inscriptionPaimentChaqueMois", o2.jsonInscriptionPaimentChaqueMois()));
						}
						break;
					case "setInscriptionPaimentComplet":
						if(jsonObject.getBoolean(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "inscriptionPaimentComplet"));
						} else {
							o2.setInscriptionPaimentComplet(jsonObject.getBoolean(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList(pk, "inscriptionPaimentComplet", o2.jsonInscriptionPaimentComplet()));
						}
						break;
					case "setPaiementDescription":
						if(jsonObject.getString(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "paiementDescription"));
						} else {
							o2.setPaiementDescription(jsonObject.getString(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList(pk, "paiementDescription", o2.jsonPaiementDescription()));
						}
						break;
					case "setPaiementDate":
						if(jsonObject.getString(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "paiementDate"));
						} else {
							o2.setPaiementDate(jsonObject.getString(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList(pk, "paiementDate", o2.jsonPaiementDate()));
						}
						break;
					case "setPaiementMontant":
						if(jsonObject.getString(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "paiementMontant"));
						} else {
							o2.setPaiementMontant(jsonObject.getString(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList(pk, "paiementMontant", o2.jsonPaiementMontant()));
						}
						break;
					case "setPaiementEspeces":
						if(jsonObject.getBoolean(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "paiementEspeces"));
						} else {
							o2.setPaiementEspeces(jsonObject.getBoolean(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList(pk, "paiementEspeces", o2.jsonPaiementEspeces()));
						}
						break;
					case "setPaiementCheque":
						if(jsonObject.getBoolean(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "paiementCheque"));
						} else {
							o2.setPaiementCheque(jsonObject.getBoolean(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList(pk, "paiementCheque", o2.jsonPaiementCheque()));
						}
						break;
					case "setPaiementSysteme":
						if(jsonObject.getBoolean(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "paiementSysteme"));
						} else {
							o2.setPaiementSysteme(jsonObject.getBoolean(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList(pk, "paiementSysteme", o2.jsonPaiementSysteme()));
						}
						break;
					case "setPaiementPar":
						if(jsonObject.getString(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "paiementPar"));
						} else {
							o2.setPaiementPar(jsonObject.getString(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList(pk, "paiementPar", o2.jsonPaiementPar()));
						}
						break;
					case "setTransactionId":
						if(jsonObject.getString(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "transactionId"));
						} else {
							o2.setTransactionId(jsonObject.getString(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList(pk, "transactionId", o2.jsonTransactionId()));
						}
						break;
					case "setCustomerProfileId":
						if(jsonObject.getString(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "customerProfileId"));
						} else {
							o2.setCustomerProfileId(jsonObject.getString(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList(pk, "customerProfileId", o2.jsonCustomerProfileId()));
						}
						break;
					case "setTransactionStatus":
						if(jsonObject.getString(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "transactionStatus"));
						} else {
							o2.setTransactionStatus(jsonObject.getString(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList(pk, "transactionStatus", o2.jsonTransactionStatus()));
						}
						break;
					case "setPaiementRecu":
						if(jsonObject.getBoolean(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "paiementRecu"));
						} else {
							o2.setPaiementRecu(jsonObject.getBoolean(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList(pk, "paiementRecu", o2.jsonPaiementRecu()));
						}
						break;
					case "setFraisMontant":
						if(jsonObject.getString(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "fraisMontant"));
						} else {
							o2.setFraisMontant(jsonObject.getString(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList(pk, "fraisMontant", o2.jsonFraisMontant()));
						}
						break;
					case "setFraisMontantDu":
						if(jsonObject.getString(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "fraisMontantDu"));
						} else {
							o2.setFraisMontantDu(jsonObject.getString(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList(pk, "fraisMontantDu", o2.jsonFraisMontantDu()));
						}
						break;
					case "setFraisMontantFuture":
						if(jsonObject.getString(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "fraisMontantFuture"));
						} else {
							o2.setFraisMontantFuture(jsonObject.getString(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList(pk, "fraisMontantFuture", o2.jsonFraisMontantFuture()));
						}
						break;
					case "setFraisPremierDernier":
						if(jsonObject.getBoolean(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "fraisPremierDernier"));
						} else {
							o2.setFraisPremierDernier(jsonObject.getBoolean(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList(pk, "fraisPremierDernier", o2.jsonFraisPremierDernier()));
						}
						break;
					case "setFraisInscription":
						if(jsonObject.getBoolean(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "fraisInscription"));
						} else {
							o2.setFraisInscription(jsonObject.getBoolean(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList(pk, "fraisInscription", o2.jsonFraisInscription()));
						}
						break;
					case "setFraisMois":
						if(jsonObject.getBoolean(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "fraisMois"));
						} else {
							o2.setFraisMois(jsonObject.getBoolean(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList(pk, "fraisMois", o2.jsonFraisMois()));
						}
						break;
					case "setFraisRetard":
						if(jsonObject.getBoolean(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "fraisRetard"));
						} else {
							o2.setFraisRetard(jsonObject.getBoolean(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList(pk, "fraisRetard", o2.jsonFraisRetard()));
						}
						break;
					case "setPaiementNomCourt":
						if(jsonObject.getString(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "paiementNomCourt"));
						} else {
							o2.setPaiementNomCourt(jsonObject.getString(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList(pk, "paiementNomCourt", o2.jsonPaiementNomCourt()));
						}
						break;
				}
			}
			connexionSql.queryWithParams(
					patchSql.toString()
					, new JsonArray(patchSqlParams)
					, patchAsync
			-> {
				if(patchAsync.succeeded()) {
					Paiementmedicale o3 = new Paiementmedicale();
					o3.setRequeteSite_(o.getRequeteSite_());
					o3.setPk(pk);
					gestionnaireEvenements.handle(Future.succeededFuture(o3));
				} else {
					gestionnaireEvenements.handle(Future.failedFuture(new Exception(patchAsync.cause())));
				}
			});
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void patchPaiementmedicaleReponse(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			reponse200PATCHPaiementmedicale(requeteSite, a -> {
				if(a.succeeded()) {
					SQLConnection connexionSql = requeteSite.getConnexionSql();
					connexionSql.commit(b -> {
						if(b.succeeded()) {
							connexionSql.close(c -> {
								if(c.succeeded()) {
									gestionnaireEvenements.handle(Future.succeededFuture(a.result()));
								} else {
									erreurPaiementmedicale(requeteSite, gestionnaireEvenements, c);
								}
							});
						} else {
							erreurPaiementmedicale(requeteSite, gestionnaireEvenements, b);
						}
					});
				} else {
					erreurPaiementmedicale(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("patchPaiementmedicale a échoué. ", ex));
			erreurPaiementmedicale(requeteSite, null, Future.failedFuture(ex));
		}
	}
	public void reponse200PATCHPaiementmedicale(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			JsonObject json = new JsonObject();
			gestionnaireEvenements.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	// GET //

	@Override
	public void getPaiementmedicale(OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = genererRequeteSiteFrFRPourPaiementmedicale(siteContexte, operationRequete);
		try {
			sqlPaiementmedicale(requeteSite, a -> {
				if(a.succeeded()) {
					utilisateurPaiementmedicale(requeteSite, b -> {
						if(b.succeeded()) {
							recherchePaiementmedicale(requeteSite, false, true, "/api/paiement/{id}", "GET", c -> {
								if(c.succeeded()) {
									ListeRecherche<Paiementmedicale> listePaiementmedicale = c.result();
									getPaiementmedicaleReponse(listePaiementmedicale, d -> {
										if(d.succeeded()) {
											gestionnaireEvenements.handle(Future.succeededFuture(d.result()));
											LOGGER.info(String.format("getPaiementmedicale a réussi. "));
										} else {
											LOGGER.error(String.format("getPaiementmedicale a échoué. ", d.cause()));
											erreurPaiementmedicale(requeteSite, gestionnaireEvenements, d);
										}
									});
								} else {
									LOGGER.error(String.format("getPaiementmedicale a échoué. ", c.cause()));
									erreurPaiementmedicale(requeteSite, gestionnaireEvenements, c);
								}
							});
						} else {
							LOGGER.error(String.format("getPaiementmedicale a échoué. ", b.cause()));
							erreurPaiementmedicale(requeteSite, gestionnaireEvenements, b);
						}
					});
				} else {
					LOGGER.error(String.format("getPaiementmedicale a échoué. ", a.cause()));
					erreurPaiementmedicale(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("getPaiementmedicale a échoué. ", ex));
			erreurPaiementmedicale(requeteSite, gestionnaireEvenements, Future.failedFuture(ex));
		}
	}


	public void getPaiementmedicaleReponse(ListeRecherche<Paiementmedicale> listePaiementmedicale, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = listePaiementmedicale.getRequeteSite_();
		try {
			reponse200GETPaiementmedicale(listePaiementmedicale, a -> {
				if(a.succeeded()) {
					SQLConnection connexionSql = requeteSite.getConnexionSql();
					connexionSql.commit(b -> {
						if(b.succeeded()) {
							connexionSql.close(c -> {
								if(c.succeeded()) {
									gestionnaireEvenements.handle(Future.succeededFuture(a.result()));
								} else {
									erreurPaiementmedicale(requeteSite, gestionnaireEvenements, c);
								}
							});
						} else {
							erreurPaiementmedicale(requeteSite, gestionnaireEvenements, b);
						}
					});
				} else {
					erreurPaiementmedicale(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("getPaiementmedicale a échoué. ", ex));
			erreurPaiementmedicale(requeteSite, null, Future.failedFuture(ex));
		}
	}
	public void reponse200GETPaiementmedicale(ListeRecherche<Paiementmedicale> listePaiementmedicale, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = listePaiementmedicale.getRequeteSite_();
			SolrDocumentList documentsSolr = listePaiementmedicale.getSolrDocumentList();

			JsonObject json = JsonObject.mapFrom(listePaiementmedicale.getList().stream().findFirst().orElse(null));
			gestionnaireEvenements.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	// Recherche //

	@Override
	public void recherchePaiementmedicale(OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = genererRequeteSiteFrFRPourPaiementmedicale(siteContexte, operationRequete);
		try {
			sqlPaiementmedicale(requeteSite, a -> {
				if(a.succeeded()) {
					utilisateurPaiementmedicale(requeteSite, b -> {
						if(b.succeeded()) {
							recherchePaiementmedicale(requeteSite, false, true, "/api/paiement", "Recherche", c -> {
								if(c.succeeded()) {
									ListeRecherche<Paiementmedicale> listePaiementmedicale = c.result();
									recherchePaiementmedicaleReponse(listePaiementmedicale, d -> {
										if(d.succeeded()) {
											gestionnaireEvenements.handle(Future.succeededFuture(d.result()));
											LOGGER.info(String.format("recherchePaiementmedicale a réussi. "));
										} else {
											LOGGER.error(String.format("recherchePaiementmedicale a échoué. ", d.cause()));
											erreurPaiementmedicale(requeteSite, gestionnaireEvenements, d);
										}
									});
								} else {
									LOGGER.error(String.format("recherchePaiementmedicale a échoué. ", c.cause()));
									erreurPaiementmedicale(requeteSite, gestionnaireEvenements, c);
								}
							});
						} else {
							LOGGER.error(String.format("recherchePaiementmedicale a échoué. ", b.cause()));
							erreurPaiementmedicale(requeteSite, gestionnaireEvenements, b);
						}
					});
				} else {
					LOGGER.error(String.format("recherchePaiementmedicale a échoué. ", a.cause()));
					erreurPaiementmedicale(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("recherchePaiementmedicale a échoué. ", ex));
			erreurPaiementmedicale(requeteSite, gestionnaireEvenements, Future.failedFuture(ex));
		}
	}


	public void recherchePaiementmedicaleReponse(ListeRecherche<Paiementmedicale> listePaiementmedicale, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = listePaiementmedicale.getRequeteSite_();
		try {
			reponse200RecherchePaiementmedicale(listePaiementmedicale, a -> {
				if(a.succeeded()) {
					SQLConnection connexionSql = requeteSite.getConnexionSql();
					connexionSql.commit(b -> {
						if(b.succeeded()) {
							connexionSql.close(c -> {
								if(c.succeeded()) {
									gestionnaireEvenements.handle(Future.succeededFuture(a.result()));
								} else {
									erreurPaiementmedicale(requeteSite, gestionnaireEvenements, c);
								}
							});
						} else {
							erreurPaiementmedicale(requeteSite, gestionnaireEvenements, b);
						}
					});
				} else {
					erreurPaiementmedicale(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("recherchePaiementmedicale a échoué. ", ex));
			erreurPaiementmedicale(requeteSite, null, Future.failedFuture(ex));
		}
	}
	public void reponse200RecherchePaiementmedicale(ListeRecherche<Paiementmedicale> listePaiementmedicale, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = listePaiementmedicale.getRequeteSite_();
			QueryResponse reponseRecherche = listePaiementmedicale.getQueryResponse();
			SolrDocumentList documentsSolr = listePaiementmedicale.getSolrDocumentList();
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
			listePaiementmedicale.getList().stream().forEach(o -> {
				JsonObject json2 = JsonObject.mapFrom(o);
				List<String> fls = listePaiementmedicale.getFields();
				if(fls.size() > 0) {
					Set<String> fieldNames = new HashSet<String>();
					fieldNames.addAll(json2.fieldNames());
					if(fls.size() == 1 && fls.stream().findFirst().orElse(null).equals("sauvegardes")) {
						fieldNames.removeAll(Optional.ofNullable(json2.getJsonArray("sauvegardes")).orElse(new JsonArray()).stream().map(s -> s.toString()).collect(Collectors.toList()));
						fieldNames.remove("pk");
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
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	// PageRecherche //

	@Override
	public void pagerecherchePaiementmedicaleId(OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		pagerecherchePaiementmedicale(operationRequete, gestionnaireEvenements);
	}

	@Override
	public void pagerecherchePaiementmedicale(OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = genererRequeteSiteFrFRPourPaiementmedicale(siteContexte, operationRequete);
		try {
			sqlPaiementmedicale(requeteSite, a -> {
				if(a.succeeded()) {
					utilisateurPaiementmedicale(requeteSite, b -> {
						if(b.succeeded()) {
							recherchePaiementmedicale(requeteSite, false, true, "/paiement", "PageRecherche", c -> {
								if(c.succeeded()) {
									ListeRecherche<Paiementmedicale> listePaiementmedicale = c.result();
									pagerecherchePaiementmedicaleReponse(listePaiementmedicale, d -> {
										if(d.succeeded()) {
											gestionnaireEvenements.handle(Future.succeededFuture(d.result()));
											LOGGER.info(String.format("pagerecherchePaiementmedicale a réussi. "));
										} else {
											LOGGER.error(String.format("pagerecherchePaiementmedicale a échoué. ", d.cause()));
											erreurPaiementmedicale(requeteSite, gestionnaireEvenements, d);
										}
									});
								} else {
									LOGGER.error(String.format("pagerecherchePaiementmedicale a échoué. ", c.cause()));
									erreurPaiementmedicale(requeteSite, gestionnaireEvenements, c);
								}
							});
						} else {
							LOGGER.error(String.format("pagerecherchePaiementmedicale a échoué. ", b.cause()));
							erreurPaiementmedicale(requeteSite, gestionnaireEvenements, b);
						}
					});
				} else {
					LOGGER.error(String.format("pagerecherchePaiementmedicale a échoué. ", a.cause()));
					erreurPaiementmedicale(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("pagerecherchePaiementmedicale a échoué. ", ex));
			erreurPaiementmedicale(requeteSite, gestionnaireEvenements, Future.failedFuture(ex));
		}
	}


	public void pagerecherchePaiementmedicaleReponse(ListeRecherche<Paiementmedicale> listePaiementmedicale, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = listePaiementmedicale.getRequeteSite_();
		try {
			Buffer buffer = Buffer.buffer();
			ToutEcrivain w = ToutEcrivain.creer(requeteSite, buffer);
			requeteSite.setW(w);
			reponse200PageRecherchePaiementmedicale(listePaiementmedicale, a -> {
				if(a.succeeded()) {
					SQLConnection connexionSql = requeteSite.getConnexionSql();
					connexionSql.commit(b -> {
						if(b.succeeded()) {
							connexionSql.close(c -> {
								if(c.succeeded()) {
									gestionnaireEvenements.handle(Future.succeededFuture(a.result()));
								} else {
									erreurPaiementmedicale(requeteSite, gestionnaireEvenements, c);
								}
							});
						} else {
							erreurPaiementmedicale(requeteSite, gestionnaireEvenements, b);
						}
					});
				} else {
					erreurPaiementmedicale(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("pagerecherchePaiementmedicale a échoué. ", ex));
			erreurPaiementmedicale(requeteSite, null, Future.failedFuture(ex));
		}
	}
	public void reponse200PageRecherchePaiementmedicale(ListeRecherche<Paiementmedicale> listePaiementmedicale, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = listePaiementmedicale.getRequeteSite_();
			Buffer buffer = Buffer.buffer();
			ToutEcrivain w = ToutEcrivain.creer(listePaiementmedicale.getRequeteSite_(), buffer);
			PaiementPage page = new PaiementPage();
			SolrDocument pageDocumentSolr = new SolrDocument();
			CaseInsensitiveHeaders requeteEnTetes = new CaseInsensitiveHeaders();
			requeteSite.setRequeteEnTetes(requeteEnTetes);

			pageDocumentSolr.setField("pageUri_frFR_stored_string", "/paiement");
			page.setPageDocumentSolr(pageDocumentSolr);
			page.setW(w);
			if(listePaiementmedicale.size() == 1)
				requeteSite.setRequetePk(listePaiementmedicale.get(0).getPk());
			requeteSite.setW(w);
			page.setListePaiementmedicale(listePaiementmedicale);
			page.setRequeteSite_(requeteSite);
			page.initLoinPaiementPage(requeteSite);
			page.html();
			gestionnaireEvenements.handle(Future.succeededFuture(new OperationResponse(200, "OK", buffer, requeteEnTetes)));
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	// General //

	public Future<Paiementmedicale> definirIndexerPaiementmedicale(Paiementmedicale paiementmedicale, Handler<AsyncResult<Paiementmedicale>> gestionnaireEvenements) {
		Promise<Paiementmedicale> promise = Promise.promise();
		RequeteSiteFrFR requeteSite = paiementmedicale.getRequeteSite_();
		definirPaiementmedicale(paiementmedicale, c -> {
			if(c.succeeded()) {
				attribuerPaiementmedicale(paiementmedicale, d -> {
					if(d.succeeded()) {
						indexerPaiementmedicale(paiementmedicale, e -> {
							if(e.succeeded()) {
								gestionnaireEvenements.handle(Future.succeededFuture(paiementmedicale));
								promise.complete(paiementmedicale);
							} else {
								erreurPaiementmedicale(requeteSite, null, e);
							}
						});
					} else {
						erreurPaiementmedicale(requeteSite, null, d);
					}
				});
			} else {
				erreurPaiementmedicale(requeteSite, null, c);
			}
		});
		return promise.future();
	}

	public void creerPaiementmedicale(RequeteSiteFrFR requeteSite, Handler<AsyncResult<Paiementmedicale>> gestionnaireEvenements) {
		try {
			SQLConnection connexionSql = requeteSite.getConnexionSql();
			String utilisateurId = requeteSite.getUtilisateurId();

			connexionSql.queryWithParams(
					SiteContexteFrFR.SQL_creer
					, new JsonArray(Arrays.asList(Paiementmedicale.class.getCanonicalName(), utilisateurId))
					, creerAsync
			-> {
				JsonArray creerLigne = creerAsync.result().getResults().stream().findFirst().orElseGet(() -> null);
				Long pk = creerLigne.getLong(0);
				Paiementmedicale o = new Paiementmedicale();
				o.setPk(pk);
				o.setRequeteSite_(requeteSite);
				gestionnaireEvenements.handle(Future.succeededFuture(o));
			});
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void erreurPaiementmedicale(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements, AsyncResult<?> resultatAsync) {
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
		if(requeteSite != null) {
			SQLConnection connexionSql = requeteSite.getConnexionSql();
			if(connexionSql != null) {
				connexionSql.rollback(a -> {
					if(a.succeeded()) {
						LOGGER.info(String.format("sql rollback. "));
						connexionSql.close(b -> {
							if(a.succeeded()) {
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
			} else {
				if(gestionnaireEvenements != null)
					gestionnaireEvenements.handle(Future.succeededFuture(reponseOperation));
			}
		}
	}

	public void sqlPaiementmedicale(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			SQLClient clientSql = requeteSite.getSiteContexte_().getClientSql();

			if(clientSql == null) {
				gestionnaireEvenements.handle(Future.succeededFuture());
			} else {
				clientSql.getConnection(sqlAsync -> {
					if(sqlAsync.succeeded()) {
						SQLConnection connexionSql = sqlAsync.result();
						connexionSql.setAutoCommit(false, a -> {
							if(a.succeeded()) {
								requeteSite.setConnexionSql(connexionSql);
								gestionnaireEvenements.handle(Future.succeededFuture());
							} else {
								gestionnaireEvenements.handle(Future.failedFuture(a.cause()));
							}
						});
					} else {
						gestionnaireEvenements.handle(Future.failedFuture(new Exception(sqlAsync.cause())));
					}
				});
			}
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public RequeteSiteFrFR genererRequeteSiteFrFRPourPaiementmedicale(SiteContexteFrFR siteContexte, OperationRequest operationRequete) {
		return genererRequeteSiteFrFRPourPaiementmedicale(siteContexte, operationRequete, null);
	}

	public RequeteSiteFrFR genererRequeteSiteFrFRPourPaiementmedicale(SiteContexteFrFR siteContexte, OperationRequest operationRequete, JsonObject body) {
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

	public void utilisateurPaiementmedicale(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			SQLConnection connexionSql = requeteSite.getConnexionSql();
			String utilisateurId = requeteSite.getUtilisateurId();
			if(utilisateurId == null) {
				gestionnaireEvenements.handle(Future.succeededFuture());
			} else {
				connexionSql.queryWithParams(
						SiteContexteFrFR.SQL_selectC
						, new JsonArray(Arrays.asList("org.computate.medicale.frFR.utilisateur.UtilisateurSite", utilisateurId))
						, selectCAsync
				-> {
					if(selectCAsync.succeeded()) {
						try {
							JsonArray utilisateurValeurs = selectCAsync.result().getResults().stream().findFirst().orElse(null);
							UtilisateurSiteFrFRApiServiceImpl utilisateurService = new UtilisateurSiteFrFRApiServiceImpl(siteContexte);
							if(utilisateurValeurs == null) {
								JsonObject utilisateurVertx = requeteSite.getOperationRequete().getUser();
								JsonObject principalJson = KeycloakHelper.parseToken(utilisateurVertx.getString("access_token"));

								JsonObject jsonObject = new JsonObject();
								jsonObject.put("utilisateurNom", principalJson.getString("preferred_username"));
								jsonObject.put("utilisateurPrenom", principalJson.getString("given_name"));
								jsonObject.put("utilisateurNomFamille", principalJson.getString("family_name"));
								jsonObject.put("utilisateurId", principalJson.getString("sub"));
								utilisateurPaiementmedicaleDefinir(requeteSite, jsonObject, false);

								RequeteSiteFrFR requeteSite2 = new RequeteSiteFrFR();
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

								utilisateurService.creerUtilisateurSite(requeteSite2, b -> {
									if(b.succeeded()) {
										UtilisateurSite utilisateurSite = b.result();
										utilisateurService.sqlPOSTUtilisateurSite(utilisateurSite, false, c -> {
											if(c.succeeded()) {
												utilisateurService.definirIndexerUtilisateurSite(utilisateurSite, d -> {
													if(d.succeeded()) {
														requeteSite.setUtilisateurSite(utilisateurSite);
														requeteSite.setUtilisateurNom(principalJson.getString("preferred_username"));
														requeteSite.setUtilisateurPrenom(principalJson.getString("given_name"));
														requeteSite.setUtilisateurNomFamille(principalJson.getString("family_name"));
														requeteSite.setUtilisateurId(principalJson.getString("sub"));
														requeteSite.setUtilisateurCle(utilisateurSite.getPk());
														gestionnaireEvenements.handle(Future.succeededFuture());
													} else {
														erreurPaiementmedicale(requeteSite, gestionnaireEvenements, d);
													}
												});
											} else {
												erreurPaiementmedicale(requeteSite, gestionnaireEvenements, c);
											}
										});
									} else {
										erreurPaiementmedicale(requeteSite, gestionnaireEvenements, b);
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
								Boolean definir = utilisateurPaiementmedicaleDefinir(requeteSite, jsonObject, true);
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

									utilisateurService.sqlPATCHUtilisateurSite(utilisateurSite, false, c -> {
										if(c.succeeded()) {
											UtilisateurSite utilisateurSite2 = c.result();
											utilisateurService.definirIndexerUtilisateurSite(utilisateurSite2, d -> {
												if(d.succeeded()) {
													requeteSite.setUtilisateurSite(utilisateurSite2);
													requeteSite.setUtilisateurNom(utilisateurSite2.getUtilisateurNom());
													requeteSite.setUtilisateurPrenom(utilisateurSite2.getUtilisateurPrenom());
													requeteSite.setUtilisateurNomFamille(utilisateurSite2.getUtilisateurNomFamille());
													requeteSite.setUtilisateurId(utilisateurSite2.getUtilisateurId());
													requeteSite.setUtilisateurCle(utilisateurSite2.getPk());
													gestionnaireEvenements.handle(Future.succeededFuture());
												} else {
													erreurPaiementmedicale(requeteSite, gestionnaireEvenements, d);
												}
											});
										} else {
											erreurPaiementmedicale(requeteSite, gestionnaireEvenements, c);
										}
									});
								} else {
									requeteSite.setUtilisateurSite(utilisateurSite1);
									requeteSite.setUtilisateurNom(utilisateurSite1.getUtilisateurNom());
									requeteSite.setUtilisateurPrenom(utilisateurSite1.getUtilisateurPrenom());
									requeteSite.setUtilisateurNomFamille(utilisateurSite1.getUtilisateurNomFamille());
									requeteSite.setUtilisateurId(utilisateurSite1.getUtilisateurId());
									requeteSite.setUtilisateurCle(utilisateurSite1.getPk());
									gestionnaireEvenements.handle(Future.succeededFuture());
								}
							}
						} catch(Exception e) {
							gestionnaireEvenements.handle(Future.failedFuture(e));
						}
					} else {
						gestionnaireEvenements.handle(Future.failedFuture(new Exception(selectCAsync.cause())));
					}
				});
			}
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public Boolean utilisateurPaiementmedicaleDefinir(RequeteSiteFrFR requeteSite, JsonObject jsonObject, Boolean patch) {
		if(patch) {
			return jsonObject.getString("setCustomerProfileId") == null;
		} else {
			return jsonObject.getString("customerProfileId") == null;
		}
	}

	public void recherchePaiementmedicaleQ(String uri, String apiMethode, ListeRecherche<Paiementmedicale> listeRecherche, String entiteVar, String valeurIndexe, String varIndexe) {
		listeRecherche.setQuery(varIndexe + ":" + ("*".equals(valeurIndexe) ? valeurIndexe : ClientUtils.escapeQueryChars(valeurIndexe)));
		if(!"*".equals(entiteVar)) {
			listeRecherche.setHighlight(true);
			listeRecherche.setHighlightSnippets(3);
			listeRecherche.addHighlightField(varIndexe);
			listeRecherche.setParam("hl.encoder", "html");
		}
	}

	public void recherchePaiementmedicaleFq(String uri, String apiMethode, ListeRecherche<Paiementmedicale> listeRecherche, String entiteVar, String valeurIndexe, String varIndexe) {
		if(varIndexe == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entiteVar));
		listeRecherche.addFilterQuery(varIndexe + ":" + ClientUtils.escapeQueryChars(valeurIndexe));
	}

	public void recherchePaiementmedicaleSort(String uri, String apiMethode, ListeRecherche<Paiementmedicale> listeRecherche, String entiteVar, String valeurIndexe, String varIndexe) {
		if(varIndexe == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entiteVar));
		listeRecherche.addSort(varIndexe, ORDER.valueOf(valeurIndexe));
	}

	public void recherchePaiementmedicaleRows(String uri, String apiMethode, ListeRecherche<Paiementmedicale> listeRecherche, Integer valeurRows) {
			listeRecherche.setRows(apiMethode != null && apiMethode.contains("Recherche") ? valeurRows : 10);
	}

	public void recherchePaiementmedicaleStart(String uri, String apiMethode, ListeRecherche<Paiementmedicale> listeRecherche, Integer valeurStart) {
		listeRecherche.setStart(valeurStart);
	}

	public void recherchePaiementmedicaleVar(String uri, String apiMethode, ListeRecherche<Paiementmedicale> listeRecherche, String var, String valeur) {
		listeRecherche.getRequeteSite_().getRequeteVars().put(var, valeur);
	}

	public void recherchePaiementmedicaleUri(String uri, String apiMethode, ListeRecherche<Paiementmedicale> listeRecherche) {
	}

	public void recherchePaiementmedicale(RequeteSiteFrFR requeteSite, Boolean peupler, Boolean stocker, String uri, String apiMethode, Handler<AsyncResult<ListeRecherche<Paiementmedicale>>> gestionnaireEvenements) {
		try {
			OperationRequest operationRequete = requeteSite.getOperationRequete();
			String entiteListeStr = requeteSite.getOperationRequete().getParams().getJsonObject("query").getString("fl");
			String[] entiteListe = entiteListeStr == null ? null : entiteListeStr.split(",\\s*");
			ListeRecherche<Paiementmedicale> listeRecherche = new ListeRecherche<Paiementmedicale>();
			listeRecherche.setPeupler(peupler);
			listeRecherche.setStocker(stocker);
			listeRecherche.setQuery("*:*");
			listeRecherche.setC(Paiementmedicale.class);
			listeRecherche.setRequeteSite_(requeteSite);
			if(entiteListe != null)
				listeRecherche.addFields(entiteListe);
			listeRecherche.add("json.facet", "{max_modifie:'max(modifie_indexed_date)'}");
			listeRecherche.add("json.facet", "{terms_enfantNomCompletPrefere:{terms:{field:enfantNomCompletPrefere_indexed_string}}}");
			listeRecherche.add("json.facet", "{sum_paiementMontant:'sum(paiementMontant_indexed_double)'}");
			listeRecherche.add("json.facet", "{sum_fraisMontant:'sum(fraisMontant_indexed_double)'}");
			listeRecherche.add("json.facet", "{sum_fraisMontantDu:'sum(fraisMontantDu_indexed_double)'}");
			listeRecherche.add("json.facet", "{sum_fraisMontantFuture:'sum(fraisMontantFuture_indexed_double)'}");

			String id = operationRequete.getParams().getJsonObject("path").getString("id");
			if(id != null) {
				listeRecherche.addFilterQuery("(id:" + ClientUtils.escapeQueryChars(id) + " OR objetId_indexed_string:" + ClientUtils.escapeQueryChars(id) + ")");
			}

			List<String> roles = Arrays.asList("SiteAdmin");
			if(
					!CollectionUtils.containsAny(requeteSite.getUtilisateurRolesRessource(), roles)
					&& !CollectionUtils.containsAny(requeteSite.getUtilisateurRolesRoyaume(), roles)
					) {
				listeRecherche.addFilterQuery("sessionId_indexed_string:" + ClientUtils.escapeQueryChars(Optional.ofNullable(requeteSite.getSessionId()).orElse("-----"))
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
								varIndexe = "*".equals(entiteVar) ? entiteVar : Paiementmedicale.varRecherchePaiementmedicale(entiteVar);
								valeurIndexe = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObjet, ":")), "UTF-8");
								valeurIndexe = StringUtils.isEmpty(valeurIndexe) ? "*" : valeurIndexe;
								recherchePaiementmedicaleQ(uri, apiMethode, listeRecherche, entiteVar, valeurIndexe, varIndexe);
								break;
							case "fq":
								entiteVar = StringUtils.trim(StringUtils.substringBefore((String)paramObjet, ":"));
								valeurIndexe = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObjet, ":")), "UTF-8");
								varIndexe = Paiementmedicale.varIndexePaiementmedicale(entiteVar);
								recherchePaiementmedicaleFq(uri, apiMethode, listeRecherche, entiteVar, valeurIndexe, varIndexe);
								break;
							case "sort":
								entiteVar = StringUtils.trim(StringUtils.substringBefore((String)paramObjet, " "));
								valeurIndexe = StringUtils.trim(StringUtils.substringAfter((String)paramObjet, " "));
								varIndexe = Paiementmedicale.varIndexePaiementmedicale(entiteVar);
								recherchePaiementmedicaleSort(uri, apiMethode, listeRecherche, entiteVar, valeurIndexe, varIndexe);
								break;
							case "start":
								valeurStart = (Integer)paramObjet;
								recherchePaiementmedicaleStart(uri, apiMethode, listeRecherche, valeurStart);
								break;
							case "rows":
								valeurRows = (Integer)paramObjet;
								recherchePaiementmedicaleRows(uri, apiMethode, listeRecherche, valeurRows);
								break;
							case "var":
								entiteVar = StringUtils.trim(StringUtils.substringBefore((String)paramObjet, ":"));
								valeurIndexe = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObjet, ":")), "UTF-8");
								recherchePaiementmedicaleVar(uri, apiMethode, listeRecherche, entiteVar, valeurIndexe);
								break;
						}
					}
					recherchePaiementmedicaleUri(uri, apiMethode, listeRecherche);
				} catch(Exception e) {
					gestionnaireEvenements.handle(Future.failedFuture(e));
				}
			});
			if(listeRecherche.getSorts().size() == 0) {
				listeRecherche.addSort("paiementDate_indexed_date", ORDER.desc);
				listeRecherche.addSort("paiementPar_indexed_string", ORDER.desc);
			}
			listeRecherche.initLoinPourClasse(requeteSite);
			gestionnaireEvenements.handle(Future.succeededFuture(listeRecherche));
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void definirPaiementmedicale(Paiementmedicale o, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = o.getRequeteSite_();
			SQLConnection connexionSql = requeteSite.getConnexionSql();
			Long pk = o.getPk();
			connexionSql.queryWithParams(
					SiteContexteFrFR.SQL_definir
					, new JsonArray(Arrays.asList(pk, pk))
					, definirAsync
			-> {
				if(definirAsync.succeeded()) {
					try {
						for(JsonArray definition : definirAsync.result().getResults()) {
							try {
								o.definirPourClasse(definition.getString(0), definition.getString(1));
							} catch(Exception e) {
								LOGGER.error(e);
							}
						}
						gestionnaireEvenements.handle(Future.succeededFuture());
					} catch(Exception e) {
						gestionnaireEvenements.handle(Future.failedFuture(e));
					}
				} else {
					gestionnaireEvenements.handle(Future.failedFuture(new Exception(definirAsync.cause())));
				}
			});
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void attribuerPaiementmedicale(Paiementmedicale o, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = o.getRequeteSite_();
			SQLConnection connexionSql = requeteSite.getConnexionSql();
			Long pk = o.getPk();
			connexionSql.queryWithParams(
					SiteContexteFrFR.SQL_attribuer
					, new JsonArray(Arrays.asList(pk, pk))
					, attribuerAsync
			-> {
				try {
					if(attribuerAsync.succeeded()) {
						if(attribuerAsync.result() != null) {
							for(JsonArray definition : attribuerAsync.result().getResults()) {
								if(pk.equals(definition.getLong(0)))
									o.attribuerPourClasse(definition.getString(2), definition.getLong(1));
								else
									o.attribuerPourClasse(definition.getString(3), definition.getLong(0));
							}
						}
						gestionnaireEvenements.handle(Future.succeededFuture());
					} else {
						gestionnaireEvenements.handle(Future.failedFuture(new Exception(attribuerAsync.cause())));
					}
				} catch(Exception e) {
					gestionnaireEvenements.handle(Future.failedFuture(e));
				}
			});
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void indexerPaiementmedicale(Paiementmedicale o, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = o.getRequeteSite_();
		try {
			RequeteApi requeteApi = requeteSite.getRequeteApi_();
			List<Long> pks = Optional.ofNullable(requeteApi).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(requeteApi).map(r -> r.getClasses()).orElse(new ArrayList<>());
			o.initLoinPourClasse(requeteSite);
			o.indexerPourClasse();
			if(BooleanUtils.isFalse(Optional.ofNullable(requeteSite.getRequeteApi_()).map(RequeteApi::getEmpty).orElse(true))) {
				RequeteSiteFrFR requeteSite2 = genererRequeteSiteFrFRPourPaiementmedicale(siteContexte, requeteSite.getOperationRequete(), new JsonObject());
				requeteSite2.setConnexionSql(requeteSite.getConnexionSql());
				ListeRecherche<Paiementmedicale> listeRecherche = new ListeRecherche<Paiementmedicale>();
				listeRecherche.setStocker(true);
				listeRecherche.setQuery("*:*");
				listeRecherche.setC(Paiementmedicale.class);
				listeRecherche.addFilterQuery("modifie_indexed_date:[" + DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(requeteSite.getRequeteApi_().getCree().toInstant(), ZoneId.of("UTC"))) + " TO *]");
				listeRecherche.add("json.facet", "{inscriptionCle:{terms:{field:inscriptionCle_indexed_longs, limit:1000}}}");
				listeRecherche.setRows(1000);
				listeRecherche.initLoinListeRecherche(requeteSite2);
				List<Future> futures = new ArrayList<>();

				for(int i=0; i < pks.size(); i++) {
					Long pk2 = pks.get(i);
					String classeNomSimple2 = classes.get(i);

					if("Inscriptionmedicale".equals(classeNomSimple2) && pk2 != null) {
						ListeRecherche<Inscriptionmedicale> listeRecherche2 = new ListeRecherche<Inscriptionmedicale>();
						listeRecherche2.setStocker(true);
						listeRecherche2.setQuery("*:*");
						listeRecherche2.setC(Inscriptionmedicale.class);
						listeRecherche2.addFilterQuery("pk_indexed_long:" + pk2);
						listeRecherche2.setRows(1);
						listeRecherche2.initLoinListeRecherche(requeteSite2);
						Inscriptionmedicale o2 = listeRecherche2.getList().stream().findFirst().orElse(null);

						if(o2 != null) {
							InscriptionmedicaleFrFRGenApiServiceImpl service = new InscriptionmedicaleFrFRGenApiServiceImpl(requeteSite2.getSiteContexte_());

							RequeteApi requeteApi2 = new RequeteApi();
							requeteApi2.setRows(1);
							requeteApi2.setNumFound(1L);
							requeteApi2.setNumPATCH(0L);
							requeteApi2.initLoinRequeteApi(requeteSite2);
							requeteSite2.setRequeteApi_(requeteApi2);
							requeteSite2.getVertx().eventBus().publish("websocketInscriptionmedicale", JsonObject.mapFrom(requeteApi2).toString());

							if(pk2 != null) {
								o2.setPk(pk2);
								o2.setRequeteSite_(requeteSite2);
								futures.add(
									service.patchInscriptionmedicaleFuture(o2, false, a -> {
										if(a.succeeded()) {
											LOGGER.info(String.format("Inscriptionmedicale %s rechargé. ", pk2));
										} else {
											LOGGER.info(String.format("Inscriptionmedicale %s a échoué. ", pk2));
											gestionnaireEvenements.handle(Future.failedFuture(a.cause()));
										}
									})
								);
							}
						}
					}
				}

				CompositeFuture.all(futures).setHandler(a -> {
					if(a.succeeded()) {
						LOGGER.info("Recharger relations a réussi. ");
						PaiementmedicaleFrFRApiServiceImpl service = new PaiementmedicaleFrFRApiServiceImpl(requeteSite2.getSiteContexte_());
						List<Future> futures2 = new ArrayList<>();
						for(Paiementmedicale o2 : listeRecherche.getList()) {
							futures2.add(
								service.patchPaiementmedicaleFuture(o2, false, b -> {
									if(b.succeeded()) {
										LOGGER.info(String.format("Paiementmedicale %s rechargé. ", o2.getPk()));
									} else {
										LOGGER.info(String.format("Paiementmedicale %s a échoué. ", o2.getPk()));
										gestionnaireEvenements.handle(Future.failedFuture(b.cause()));
									}
								})
							);
						}

						CompositeFuture.all(futures2).setHandler(b -> {
							if(b.succeeded()) {
								LOGGER.info("Recharger Paiementmedicale a réussi. ");
								gestionnaireEvenements.handle(Future.succeededFuture());
							} else {
								LOGGER.error("Recharger relations a échoué. ", b.cause());
								erreurPaiementmedicale(requeteSite2, gestionnaireEvenements, b);
							}
						});
					} else {
						LOGGER.error("Recharger relations a échoué. ", a.cause());
						erreurPaiementmedicale(requeteSite2, gestionnaireEvenements, a);
					}
				});
			} else {
				gestionnaireEvenements.handle(Future.succeededFuture());
			}
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}
}