package org.computate.medicale.frFR.vertx;          

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.computate.medicale.frFR.clinique.CliniqueMedicaleFrFRGenApiService;
import org.computate.medicale.frFR.cluster.ClusterFrFRGenApiService;
import org.computate.medicale.frFR.config.ConfigSite;
import org.computate.medicale.frFR.contexte.SiteContexteFrFR;
import org.computate.medicale.frFR.design.DesignPageFrFRGenApiService;
import org.computate.medicale.frFR.html.part.PartHtmlFrFRGenApiService;
import org.computate.medicale.frFR.java.LocalDateSerializer;
import org.computate.medicale.frFR.java.LocalTimeSerializer;
import org.computate.medicale.frFR.java.ZonedDateTimeSerializer;
import org.computate.medicale.frFR.requete.RequeteSiteFrFR;
import org.computate.medicale.frFR.utilisateur.UtilisateurSiteFrFRGenApiService;

import com.fasterxml.jackson.databind.module.SimpleModule;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.WorkerExecutor;
import io.vertx.core.http.Cookie;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.JksOptions;
import io.vertx.core.shareddata.AsyncMap;
import io.vertx.core.shareddata.SharedData;
import io.vertx.ext.auth.oauth2.AccessToken;
import io.vertx.ext.auth.oauth2.OAuth2Auth;
import io.vertx.ext.auth.oauth2.OAuth2ClientOptions;
import io.vertx.ext.auth.oauth2.OAuth2FlowType;
import io.vertx.ext.auth.oauth2.providers.OpenIDConnectAuth;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.healthchecks.HealthCheckHandler;
import io.vertx.ext.healthchecks.Status;
import io.vertx.ext.mail.MailClient;
import io.vertx.ext.mail.MailConfig;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.api.contract.openapi3.OpenAPI3RouterFactory;
import io.vertx.ext.web.handler.OAuth2AuthHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.SqlConnection;
import io.vertx.sqlclient.Transaction;

/**
 * NomCanonique.enUS: org.computate.medicale.enUS.vertx.AppVertx
 * enUS: A Java class to start the Vert.x application as a main method. 
 */      
public class AppliVertx extends AppliVertxGen<AbstractVerticle> {

	public final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

	/**
	 * enUS: A SQL query for creating a database table "c" to store any type of object in the application. 
	 * r: nom_canonique
	 * r.enUS: canonical_name
	 * r: ajour
	 * r.enUS: current
	 * r: nom_canonique
	 * r.enUS: canonical_name
	 * r: cree
	 * r.enUS: created
	 * r: id_utilisateur
	 * r.enUS: user_id
	 */
	public static final String SQL_createTableC = "create table if not exists c(pk bigserial primary key, ajour boolean, nom_canonique text, cree timestamp with time zone, id_utilisateur text); ";

	/**
	 * enUS: A SQL query for creating a unique index on the "c" table based on the pk, canonical_name, and user_id fields for faster lookup. 
	 * r: nom_canonique
	 * r.enUS: canonical_name
	 * r: ajour
	 * r.enUS: current
	 * r: nom_canonique
	 * r.enUS: canonical_name
	 * r: cree
	 * r.enUS: created
	 * r: id_utilisateur
	 * r.enUS: user_id
	 * r: utilisateur
	 * r.enUS: user
	 */
	public static final String SQL_uniqueIndexC = "create unique index if not exists c_index_utilisateur on c(pk, nom_canonique, id_utilisateur); ";

	/**
	 * enUS: A SQL query for creating a database table "a" to store relations (like entity relations) between one other record in the "c" table with another record in the "c" table. 
	 * r: entite
	 * r.enUS: entity
	 * r: actuel
	 * r.enUS: current
	 * r: cree
	 * r.enUS: created
	 */
	public static final String SQL_createTableA = "create table if not exists a(pk bigserial primary key, pk1 bigint, entite1 text, pk2 bigint, entite2 text, actuel boolean, cree timestamp with time zone, constraint a_constraint unique (pk1, entite1, pk2, entite2)); ";

	/**
	 * enUS: A SQL query for creating an index on the "a" table based on fields for faster lookup. 
	 * r: entite
	 * r.enUS: entity
	 * r: actuel
	 * r.enUS: current
	 * r: cree
	 * r.enUS: created
	 */
	public static final String SQL_uniqueIndexA = "create index if not exists a_index on a(pk1, pk2, current); ";

	/**
	 * enUS: A SQL query for creating a database table "d" to store String values to define fields in an instance of a class based on a record in the "c" table. 
	 * r: actuel
	 * r.enUS: current
	 * r: cree
	 * r.enUS: created
	 * r: valeur
	 * r.enUS: value
	 * r: chemin
	 * r.enUS: path
	 */
	public static final String SQL_createTableD = "create table if not exists d(pk bigserial primary key, pk_c bigint, chemin text, valeur text, actuel boolean, cree timestamp with time zone, constraint d_constraint unique (pk_c, chemin)); ";

	/**
	 * enUS: A SQL query for creating an index on the "d" table based on fields for faster lookup. 
	 * r: actuel
	 * r.enUS: current
	 * r: cree
	 * r.enUS: created
	 * r: valeur
	 * r.enUS: value
	 * r: chemin
	 * r.enUS: path
	 */
	public static final String SQL_uniqueIndexD = "create index if not exists d_index on d(pk_c, current); ";

	/**
	 * enUS: Concatenate all of the SQL together to execute when the server starts. 
	 * Var.enUS: SQL_initAll
	 */
	public static final String SQL_initTout = SQL_createTableC + SQL_uniqueIndexC + SQL_createTableA + SQL_uniqueIndexA + SQL_createTableD + SQL_uniqueIndexD;

	/**
	 * enUS: A io.vertx.ext.jdbc.JDBCClient for connecting to the relational database PostgreSQL. 
	 */
	private PgPool pgPool;

	/**
	 * enUS: A site context object for storing information about the entire site in English. 
	 * Var.enUS: siteContextEnUS
	 */
	SiteContexteFrFR siteContexteFrFR;

	/**
	 * enUS: For logging information and errors in the application. 
	 * r: AppliVertx
	 * r.enUS: AppVertx
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AppliVertx.class);

	/**
	 * r: CoureurVertx
	 * r.enUS: RunnerVertx
	 * r: AppliVertx
	 * r.enUS: AppVertx
	 * enUS: The main method for the Vert.x application that runs the Vert.x Runner class
	 */
	public static void main(String[] args) {
		CoureurVertx.run(AppliVertx.class);
	}

	/**
	 * Param1.var.enUS: startPromise
	 * r: SiteContexteFrFR
	 * r.enUS: SiteContextEnUS
	 * r: siteContexteFrFR
	 * r.enUS: siteContextEnUS
	 * r: initLoin
	 * r.enUS: initDeep
	 * r: demarrerPromise
	 * r.enUS: startPromise
	 * r: configurerDonnees
	 * r.enUS: configureData
	 * r: configurerCluster
	 * r.enUS: configureCluster
	 * r: configurerOpenApi
	 * r.enUS: configureOpenApi
	 * r: configurerControlesSante
	 * r.enUS: configureHealthChecks
	 * r: configurerExecuteurTravailleurPartage
	 * r.enUS: configureSharedWorkerExecutor
	 * r: configurerWebsockets
	 * r.enUS: configureWebsockets
	 * r: configurerMail
	 * r.enUS: configureEmail
	 * r: demarrerServeur
	 * r.enUS: startServer
	 * r: etapesPromises
	 * r.enUS: promiseSteps
	 * r: configurerAuthorizeNetPaiements
	 * r.enUS: configureAuthorizeNetPayments
	 * r: configurerAuthorizeNetFrais
	 * r.enUS: configureAuthorizeNetCharges
	 * 
	 * enUS: This is called by Vert.x when the verticle instance is deployed. 
	 * enUS: Initialize a new site context object for storing information about the entire site in English. 
	 * enUS: Setup the startPromise to handle the configuration steps and starting the server. 
	 */
	@Override
	public void start(Promise<Void> demarrerPromise) throws Exception {

		siteContexteFrFR = new SiteContexteFrFR();
		siteContexteFrFR.setVertx(vertx);
		siteContexteFrFR.initLoinSiteContexteFrFR();

		Future<Void> etapesPromises = configurerDonnees().future().compose(a -> 
			configurerCluster().future().compose(b -> 
				configurerOpenApi().future().compose(c -> 
					configurerControlesSante().future().compose(d -> 
						configurerExecuteurTravailleurPartage().future().compose(e -> 
							configurerWebsockets().future().compose(f -> 
								configurerMail().future().compose(g -> 
									demarrerServeur().future()
								)
							)
						)
					)
				)
			)
		);
		etapesPromises.setHandler(demarrerPromise);
	}

	/**
	 * Var.enUS: configureData
	 * 
	 * Val.ConnectionError.enUS:Could not open the database client connection. 
	 * Val.ErreurConnexion.frFR:Impossible d'ouvrir la connexion du client de base de données. 
	 * Val.ConnectionSuccess.enUS:The database client connection was successful. 
	 * Val.SuccesConnexion.frFR:La connexion du client de base de données a réussi. 
	 * 
	 * Val.InitError.enUS:Could not initialize the database tables. 
	 * Val.ErreurInit.frFR:Impossible d'initialiser les tables de la base de données. 
	 * Val.InitSuccess.enUS:The database tables were created successfully. 
	 * Val.SuccesInit.frFR:Les tables de base de données ont été créées avec succès. 
	 * 
	 * enUS: Configure shared database connections across the cluster for massive scaling of the application. 
	 * enUS: Return a promise that configures a shared database client connection. 
	 * enUS: Load the database configuration into a shared io.vertx.ext.jdbc.JDBCClient for a scalable, clustered datasource connection pool. 
	 * enUS: Initialize the database tables if not already created for the first time. 
	 * 
	 * r: configurerDonnees
	 * r.enUS: configureData
	 * r: ErreurConnexion
	 * r.enUS: ConnectionError
	 * r: SuccesConnexion
	 * r.enUS: ConnectionSuccess
	 * r: ErreurInit
	 * r.enUS: InitError
	 * r: SuccesInit
	 * r.enUS: InitSuccess
	 * r: initTout
	 * r.enUS: initAll
	 * r: siteContexteFrFR
	 * r.enUS: siteContextEnUS
	 * r: ConfigSite
	 * r.enUS: SiteConfig
	 * r: configSite
	 * r.enUS: siteConfig
	 * r: getJdbcClassePilote
	 * r.enUS: getJdbcDriverClass
	 * r: getJdbcUtilisateur
	 * r.enUS: getJdbcUsername
	 * r: getJdbcMotDePasse
	 * r.enUS: getJdbcPassword
	 * r: getJdbcTailleMaxPiscine
	 * r.enUS: getJdbcMaxPoolSize
	 * r: getJdbcTailleInitialePiscine
	 * r.enUS: getJdbcInitialPoolSize
	 * r: getJdbcTailleMinPiscine
	 * r.enUS: getJdbcMinPoolSize
	 * r: getJdbcMaxDeclarationsParConnexion
	 * r.enUS: getJdbcMaxStatementsPerConnection
	 * r: getJdbcMaxDeclarations
	 * r.enUS: getJdbcMaxStatements
	 * r: getJdbcTempsInactiviteMax
	 * r.enUS: getJdbcMaxIdleTime
	 * r: getJdbcHote
	 * r.enUS: getJdbcHost
	 * r: getJdbcBaseDeDonnees
	 * r.enUS: getJdbcDatabase
	 * r: JdbcDelaiConnexion
	 * r.enUS: JdbcConnectTimeout
	 * r: JdbcMaxFileAttente
	 * r.enUS: JdbcMaxWaitQueueSize
	 */
	private Promise<Void> configurerDonnees() {
		ConfigSite configSite = siteContexteFrFR.getConfigSite();
		Promise<Void> promise = Promise.promise();

		PgConnectOptions pgOptions = new PgConnectOptions();
		pgOptions.setPort(configSite.getJdbcPort());
		pgOptions.setHost(configSite.getJdbcHote());
		pgOptions.setDatabase(configSite.getJdbcBaseDeDonnees());
		pgOptions.setUser(configSite.getJdbcUtilisateur());
		pgOptions.setPassword(configSite.getJdbcMotDePasse());
		pgOptions.setIdleTimeout(configSite.getJdbcTempsInactiviteMax());
		pgOptions.setIdleTimeoutUnit(TimeUnit.SECONDS);
		pgOptions.setConnectTimeout(configSite.getJdbcDelaiConnexion());

		PoolOptions poolOptions = new PoolOptions();
		poolOptions.setMaxSize(configSite.getJdbcTailleMaxPiscine());
		poolOptions.setMaxWaitQueueSize(configSite.getJdbcMaxFileAttente());

		pgPool = PgPool.pool(vertx, pgOptions, poolOptions);

		siteContexteFrFR.setPgPool(pgPool);


		pgPool.preparedQuery(SQL_createTableC, a -> {
			if (a.succeeded()) {
				pgPool.preparedQuery(SQL_uniqueIndexC, b -> {
					if (b.succeeded()) {
						pgPool.preparedQuery(SQL_createTableA, c -> {
							if (c.succeeded()) {
								pgPool.preparedQuery(SQL_uniqueIndexA, d -> {
									if (d.succeeded()) {
										pgPool.preparedQuery(SQL_createTableD, e -> {
											if (e.succeeded()) {
												pgPool.preparedQuery(SQL_uniqueIndexD, f -> {
													if (f.succeeded()) {
														LOGGER.info(configurerDonneesSuccesInit);
														promise.complete();
													} else {
														LOGGER.error(configurerDonneesErreurInit, f.cause());
														promise.fail(f.cause());
													}
												});
											} else {
												LOGGER.error(configurerDonneesErreurInit, e.cause());
												promise.fail(e.cause());
											}
										});
									} else {
										LOGGER.error(configurerDonneesErreurInit, d.cause());
										promise.fail(d.cause());
									}
								});
							} else {
								LOGGER.error(configurerDonneesErreurInit, c.cause());
								promise.fail(c.cause());
							}
						});
					} else {
						LOGGER.error(configurerDonneesErreurInit, b.cause());
						promise.fail(b.cause());
					}
				});
			} else {
				LOGGER.error(configurerDonneesErreurInit, a.cause());
				promise.fail(a.cause());
			}
		});

		return promise;
	}

	/**  
	 * Var.enUS: configureCluster
	 * 
	 * Val.DataError.enUS:Could not configure the shared cluster data. 
	 * Val.ErreurDonnees.frFR:Impossible de configurer les données du cluster partagé.
	 * Val.DataSuccess.enUS:The shared cluster data was configured successfully. 
	 * Val.SuccesDonnees.frFR:Les données du cluster partagé ont été configurées avec succès. 
	 * 
	 * r: siteContexteFrFR
	 * r.enUS: siteContextEnUS
	 * r: ConfigSite
	 * r.enUS: SiteConfig
	 * r: configSite
	 * r.enUS: siteConfig
	 * r: donneesPartagees
	 * r.enUS: sharedData
	 * r: donneesCluster
	 * r.enUS: clusterData
	 * r: configurerCluster
	 * r.enUS: configureCluster
	 * r: ErreurDonnees
	 * r.enUS: DataError
	 * r: SuccesDonnees
	 * r.enUS: DataSuccess
	 * 
	 * enUS: Configure shared data across the cluster for massive scaling of the application. 
	 * enUS: Return a promise that configures a shared cluster data. 
	 */
	private Promise<Void> configurerCluster() {
		ConfigSite configSite = siteContexteFrFR.getConfigSite();
		Promise<Void> promise = Promise.promise();
		SharedData donneesPartagees = vertx.sharedData();
		donneesPartagees.getClusterWideMap("donneesCluster", res -> {
			if (res.succeeded()) {
				AsyncMap<Object, Object> donneesCluster = res.result();
				donneesCluster.put("configSite", configSite, resPut -> {
					if (resPut.succeeded()) {
						LOGGER.info(configurerClusterSuccesDonnees);
						promise.complete();
					} else {
						LOGGER.error(configurerClusterErreurDonnees, res.cause());
						promise.fail(res.cause());
					}
				});
			} else {
				LOGGER.error(configurerClusterErreurDonnees, res.cause());
				promise.fail(res.cause());
			}
		});
		return promise;
	}

	/**
	 * Var.enUS: configureOpenApi
	 * 
	 * enUS: Configure the connection to the auth server and setup the routes based on the OpenAPI definition. 
	 * enUS: Setup a callback route when returning from the auth server after successful authentication. 
	 * enUS: Setup a logout route for logging out completely of the application. 
	 * enUS: Return a promise that configures the authentication server and OpenAPI. 
	 * 
	 * Val.Error.enUS:Could not configure the auth server and API. 
	 * Val.Erreur.frFR:Impossible de configurer le serveur auth et le API.
	 * Val.Success.enUS:The auth server and API was configured successfully. 
	 * Val.Succes.frFR:Le serveur auth et le API ont été configurées avec succès. 
	 * 
	 * r: siteContexteFrFR
	 * r.enUS: siteContextEnUS
	 * r: ConfigSite
	 * r.enUS: SiteConfig
	 * r: configSite
	 * r.enUS: siteConfig
	 * r: siteNomHote
	 * r.enUS: siteHostName
	 * r: routeur
	 * r.enUS: router
	 * r: usineRouteur
	 * r.enUS: routerFactory
	 * r: UsineRouteur
	 * r.enUS: RouterFactory
	 * r: authFournisseur
	 * r.enUS: authProvider
	 * r: getAuthRoyaume
	 * r.enUS: getAuthRealm
	 * r: getAuthRessource
	 * r.enUS: getAuthResource
	 * r: getAuthSslRequis
	 * r.enUS: getAuthSslRequired
	 * r: getSiteNomHote
	 * r.enUS: getSiteHostName
	 * r: gestionnaireAuth
	 * r.enUS: authHandler
	 * r: getSiteUrlBase
	 * r.enUS: getSiteBaseUrl
	 * r: sessionIdAvant
	 * r.enUS: sessionIdBefore
	 * r: setRouteur
	 * r.enUS: setRouter
	 * r: deconnexion
	 * r.enUS: logout
	 * r: Erreur
	 * r.enUS: Error
	 * r: Succes
	 * r.enUS: Success
	 * r: configurerOpenApi
	 * r.enUS: configureOpenApi
	 * r: openapi3-frFR.yaml
	 * r.enUS: openapi3-enUS.yaml
	 * r: /ecole
	 * r.enUS: /school
	 * r: "computate-medicale-frFR-session"
	 * r.enUS: "computate-medicale-enUS-session"
	 */
	private Promise<Void> configurerOpenApi() {
		ConfigSite configSite = siteContexteFrFR.getConfigSite();
		Promise<Void> promise = Promise.promise();
		String siteUrlBase = configSite.getSiteUrlBase();

		OAuth2ClientOptions oauth2ClientOptions = new OAuth2ClientOptions();
		oauth2ClientOptions.setSite(configSite.getAuthUrl() + "/realms/" + configSite.getAuthRoyaume());
		oauth2ClientOptions.setClientID(configSite.getAuthRessource());
		oauth2ClientOptions.setClientSecret(configSite.getAuthSecret());
		oauth2ClientOptions.setFlow(OAuth2FlowType.AUTH_CODE);
		JsonObject extraParams = new JsonObject();
		extraParams.put("scope", "openid DefaultAuthScope SiteAdminScope");
		oauth2ClientOptions.setExtraParameters(extraParams);

		OpenIDConnectAuth.discover(vertx, oauth2ClientOptions, a -> {
			if(a.succeeded()) {
				OAuth2Auth authFournisseur = a.result();
	
				OAuth2AuthHandler gestionnaireAuth = OAuth2AuthHandler.create(authFournisseur, siteUrlBase + "/callback");
				gestionnaireAuth.addAuthority("DefaultAuthScope");
				gestionnaireAuth.addAuthority("SiteAdminScope");
				gestionnaireAuth.addAuthority("openid");
				{
					Router tempRouter = Router.router(vertx);
					gestionnaireAuth.setupCallback(tempRouter.get("/callback"));
				}
		
		//		ClusteredSessionStore sessionStore = ClusteredSessionStore.create(vertx);
				LocalSessionStore sessionStore = LocalSessionStore.create(vertx, "computate-medicale-sessions");
				SessionHandler sessionHandler = SessionHandler.create(sessionStore);
				sessionHandler.setAuthProvider(authFournisseur);
		
				OpenAPI3RouterFactory.create(vertx, "openapi3-frFR.yaml", b -> {
					if (b.succeeded()) {
						OpenAPI3RouterFactory usineRouteur = b.result();
						usineRouteur.mountServicesFromExtensions();
						siteContexteFrFR.setUsineRouteur(usineRouteur);
		
						usineRouteur.addGlobalHandler(sessionHandler);
						usineRouteur.addHandlerByOperationId("callback", ctx -> {
		
							// Handle the callback of the flow
							final String code = ctx.request().getParam("code");
		
							// code is a require value
							if (code == null) {
								ctx.fail(400);
								return;
							}
		
							final String state = ctx.request().getParam("state");
		
							final JsonObject config = new JsonObject().put("code", code);
		
							config.put("redirect_uri", siteUrlBase + "/callback");
		
							authFournisseur.authenticate(config, res -> {
								if (res.failed()) {
									ctx.fail(res.cause());
								} else {
									AccessToken token = (AccessToken) res.result();
//									token.isAuthorized("SiteAdminScope", r -> {
//										if(r.succeeded()) {
											ctx.setUser(res.result());
											Session session = ctx.session();
											if (session != null) {
												// the user has upgraded from unauthenticated to authenticated
												// session should be upgraded as recommended by owasp
												ctx.addCookie(Cookie.cookie("sessionIdAvant", session.id()));
												session.regenerateId();
												// we should redirect the UA so this link becomes invalid
												ctx.response()
														// disable all caching
														.putHeader(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
														.putHeader("Pragma", "no-cache").putHeader(HttpHeaders.EXPIRES, "0")
														// redirect (when there is no state, redirect to home
														.putHeader(HttpHeaders.LOCATION, state != null ? state : "/").setStatusCode(302)
														.end("Redirecting to " + (state != null ? state : "/") + ".");
											} else {
												// there is no session object so we cannot keep state
												ctx.reroute(state != null ? state : "/");
											}
//										} else {
//											String str = new JsonObject()
//													.put("error", new JsonObject())
//													.put("message", "Unauthorized").encodePrettily();
//											Buffer buffer = Buffer.buffer().appendString(str);
//											ctx.response().putHeader("Content-Length", Integer.toString(buffer.length()));
//											ctx.response().write(buffer);
//											ctx.response().setStatusCode(403);
//											ctx.response().end();
//										}
//									});
								}
							});
						});
						usineRouteur.addFailureHandlerByOperationId("callback", c -> {});
		
						usineRouteur.addHandlerByOperationId("deconnexion", rc -> {
							Session session = rc.session();
//							if (session != null) {
//								session.destroy();
//							}
							rc.clearUser();
							rc.reroute("/");
						});
						usineRouteur.addFailureHandlerByOperationId("deconnexion", c -> {});
		
//						usineRouteur.addSecurityHandler("openIdConnect", gestionnaireAuth);
						usineRouteur.addSecuritySchemaScopeValidator("openIdConnect", "DefaultAuthScope", gestionnaireAuth);
						usineRouteur.addSecuritySchemaScopeValidator("openIdConnect", "SiteAdminScope", gestionnaireAuth);
						usineRouteur.addSecuritySchemaScopeValidator("openIdConnect", "openid", gestionnaireAuth);
						Router routeur = usineRouteur.getRouter();
						siteContexteFrFR.setRouteur(routeur);
		
						LOGGER.info(configurerOpenApiSucces);
						promise.complete();
					} else {
						LOGGER.error(configurerOpenApiErreur, b.cause());
						promise.fail(b.cause());
					}
				});
			} else {
				LOGGER.error(configurerOpenApiErreur, a.cause());
				promise.fail(a.cause());
			}
		});
		return promise;
	}

	/**
	 * Var.enUS: configureSharedWorkerExecutor
	 * 
	 * enUS: Configure a shared worker executor for running blocking tasks in the background. 
	 * enUS: Return a promise that configures the shared worker executor. 
	 * 
	 * r: executeurTravailleur
	 * r.enUS: workerExecutor
	 * r: ExecuteurTravailleur
	 * r.enUS: WorkerExecutor
	 * r: siteContexteFrFR
	 * r.enUS: siteContextEnUS
	 */
	private Promise<Void> configurerExecuteurTravailleurPartage() {
		Promise<Void> promise = Promise.promise();

		WorkerExecutor executeurTravailleur = vertx.createSharedWorkerExecutor("WorkerExecutor");
		siteContexteFrFR.setExecuteurTravailleur(executeurTravailleur);
		promise.complete();
		return promise;
	}

	/**
	 * Var.enUS: configureHealthChecks
	 * 
	 * Val.ErrorDatabase.enUS:The database is not configured properly. 
	 * Val.ErreurBaseDeDonnees.frFR:La base de données n'est pas configurée correctement. 
	 * 
	 * Val.EmptySolr.enUS:The Solr search engine is empty. 
	 * Val.VideSolr.frFR:Le moteur de recherche Solr est vide. 
	 * 
	 * Val.ErrorSolr.enUS:The Solr search engine is not configured properly. 
	 * Val.ErreurSolr.frFR:Le moteur de recherche Solr n'est pas configuré correctement. 
	 * 
	 * Val.ErrorVertx.enUS:The Vert.x application is not configured properly. 
	 * Val.ErreurVertx.frFR:L'application Vert.x n'est pas configuré correctement. 
	 * 
	 * enUS: Configure health checks for the status of the website and it's dependent services. 
	 * enUS: Return a promise that configures the health checks. 
	 * 
	 * r: siteContexteFrFR
	 * r.enUS: siteContextEnUS
	 * r: UsineRouteur
	 * r.enUS: RouterFactory
	 * r: gestionnaireControlesSante
	 * r.enUS: healthCheckHandler
	 * r: baseDeDonnees
	 * r.enUS: database
	 * r: configurerControlesSanteErreurBaseDeDonnees
	 * r.enUS: configureHealthChecksErrorDatabase
	 * r: configurerControlesSanteErreurSolr
	 * r.enUS: configureHealthChecksErrorSolr
	 * r: configurerControlesSanteVideSolr
	 * r.enUS: configureHealthChecksEmptySolr
	 * r: configurerControlesSanteErreurVertx
	 * r.enUS: configureHealthChecksErrorVertx
	 * r: ClientSolr
	 * r.enUS: SolrClient
	 * r: getRouteur
	 * r.enUS: getRouter
	 */
	private Promise<Void> configurerControlesSante() {
		Promise<Void> promise = Promise.promise();
		Router siteRouteur = siteContexteFrFR.getRouteur();
		HealthCheckHandler gestionnaireControlesSante = HealthCheckHandler.create(vertx);

		gestionnaireControlesSante.register("baseDeDonnees", 2000, a -> {
			siteContexteFrFR.getPgPool().preparedQuery("select current_timestamp" , selectCAsync -> {
				if(selectCAsync.succeeded()) {
					a.complete(Status.OK());
				} else {
					LOGGER.error(configurerControlesSanteErreurBaseDeDonnees, a.future().cause());
					promise.fail(a.future().cause());
				}
			});
		});
		gestionnaireControlesSante.register("solr", 2000, a -> {
			SolrQuery query = new SolrQuery();
			query.setQuery("*:*");
			try {
				QueryResponse r = siteContexteFrFR.getClientSolr().query(query);
				if(r.getResults().size() > 0)
					a.complete(Status.OK());
				else {
					LOGGER.error(configurerControlesSanteVideSolr, a.future().cause());
					promise.fail(a.future().cause());
				}
			} catch (SolrServerException | IOException e) {
				LOGGER.error(configurerControlesSanteErreurSolr, a.future().cause());
				promise.fail(a.future().cause());
			}
		});
		siteRouteur.get("/health").handler(gestionnaireControlesSante);
		promise.complete();
		return promise;
	}

	/**
	 * Var.enUS: configureWebsockets
	 * 
	 * enUS: Configure websockets for realtime messages. 
	 * 
	 * r: siteContexteFrFR
	 * r.enUS: siteContextEnUS
	 * r: gestionnaireSockJs
	 * r.enUS: sockJsHandler
	 * r: GestionnaireSockJs
	 * r.enUS: SockJsHandler
	 * r: siteRouteur
	 * r.enUS: siteRouter
	 * r: usineRouteur
	 * r.enUS: routerFactory
	 * r: UsineRouteur
	 * r.enUS: RouterFactory
	 * r: getRouteur
	 * r.enUS: getRouter
	 */
	private Promise<Void> configurerWebsockets() {
		Promise<Void> promise = Promise.promise();
		Router siteRouteur = siteContexteFrFR.getRouteur();
		BridgeOptions options = new BridgeOptions()
				.addOutboundPermitted(new PermittedOptions().setAddressRegex("websocket.*"));
		SockJSHandler gestionnaireSockJs = SockJSHandler.create(vertx);
		gestionnaireSockJs.bridge(options);
		siteRouteur.route("/eventbus/*").handler(gestionnaireSockJs);
		promise.complete();
		return promise;
	}

	/**
	 * Var.enUS: configureEmail
	 * 
	 * enUS: Configure sending email. 
	 * 
	 * r: siteContexteFrFR
	 * r.enUS: siteContextEnUS
	 * r: ConfigSite
	 * r.enUS: SiteConfig
	 * r: configSite
	 * r.enUS: siteConfig
	 * r: MailHote
	 * r.enUS: EmailHost
	 * r: MailPort
	 * r.enUS: EmailPort
	 * r: MailSsl
	 * r.enUS: EmailSsl
	 * r: MailUtilisateur
	 * r.enUS: EmailUsername
	 * r: MailMotDePasse
	 * r.enUS: EmailPassword
	 */
	private Promise<Void> configurerMail() {
		ConfigSite configSite = siteContexteFrFR.getConfigSite();
		Promise<Void> promise = Promise.promise();
		MailConfig config = new MailConfig();
		config.setHostname(configSite.getMailHote());
		config.setPort(configSite.getMailPort());
		config.setSsl(configSite.getMailSsl());
		config.setUsername(configSite.getMailUtilisateur());
		config.setPassword(configSite.getMailMotDePasse());
		MailClient mailClient = MailClient.createShared(vertx, config);
		siteContexteFrFR.setMailClient(mailClient);
		promise.complete();
		return promise;
	}

	/**
	 * Var.enUS: errorAppVertx
	 * Param1.var.enUS: siteRequest
	 * 
	 * r: "Rollback la connexion SQL a réussi. "
	 * r.enUS: "Rollback the SQL connection succeded. "
	 * r: "Fermer la connexion SQL a réussi. "
	 * r.enUS: "Close the SQL connection succeded. "
	 * r: "Rollback la connexion SQL a échoué. "
	 * r.enUS: "Rollback the SQL connection failed. "
	 * r: "Fermer la connexion SQL a échoué. "
	 * r.enUS: "Close the SQL connection failed. "
	 * r: ConnexionSql
	 * r.enUS: SqlConnection
	 * r: requeteSite
	 * r.enUS: siteRequest
	 */
	public void erreurAppliVertx(RequeteSiteFrFR requeteSite, AsyncResult<?> a) {
		Throwable e = a.cause();
		if(e != null)
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		if(requeteSite != null) {
			Transaction tx = requeteSite.getTx();
			if(tx != null) {
				tx.rollback(b -> {
					if(b.succeeded()) {
						LOGGER.info("Rollback la connexion SQL a réussi. ");
						try {
							SqlConnection connexionSql = requeteSite.getConnexionSql();
				
							if(connexionSql == null) {
								LOGGER.info("Fermer la connexion SQL a réussi. ");
							} else {
								connexionSql.close();
								requeteSite.setConnexionSql(null);
								LOGGER.info("Fermer la connexion SQL a réussi. ");
							}
						} catch(Exception ex) {
							LOGGER.error(String.format("sqlFermerEcole a échoué. ", ex));
						}
					} else {
						LOGGER.error("Rollback la connexion SQL a échoué. ", b.cause());
					}
				});
			}
		}
	}

	/**
	 * Var.enUS: startServer
	 * 
	 * Val.ErrorServer.enUS:The server is not configured properly. 
	 * Val.ErreurServeur.frFR:Le serveur n'est pas configurée correctement. 
	 * Val.SuccessServer.enUS:The HTTP server is running: %s:%s
	 * Val.SuccesServeur.frFR:Le serveur HTTP est démarré : %s:%s
	 * Val.BeforeServer.enUS:HTTP server starting: %s://%s:%s
	 * Val.AvantServeur.frFR:Le serveur HTTP est démarré : %s:%s
	 * Val.Ssl.enUS:Configuring SSL: %s
	 * Val.Ssl.frFR:Configurer SSL : %s
	 * 
	 * enUS: Start the Vert.x server. 
	 * enUS: Démarrer le serveur Vert.x. 
	 * 
	 * r: siteContexteFrFR
	 * r.enUS: siteContextEnUS
	 * r: ConfigSite
	 * r.enUS: SiteConfig
	 * r: configSite
	 * r.enUS: siteConfig
	 * r: UsineRouteur
	 * r.enUS: RouterFactory
	 * r: siteRouteur
	 * r.enUS: siteRouter
	 * r: gestionnaireStatic
	 * r.enUS: staticHandler
	 * r: siteNomHote
	 * r.enUS: siteHostName
	 * r: demarrerServeurErreurServeur
	 * r.enUS: startServerErrorServer
	 * r: demarrerServeurSuccesServeur
	 * r.enUS: startServerSuccessServer
	 * r: demarrerServeurAvantServeur
	 * r.enUS: startServerBeforeServer
	 * r: demarrerServeurSsl
	 * r.enUS: startServerSsl
	 * r: getSiteNomHote
	 * r.enUS: getSiteHostName
	 * r: getSslJksChemin
	 * r.enUS: getSslJksPath
	 * r: getSslJksMotDePasse
	 * r.enUS: getSslJksPassword
	 * r: ClusterFrFRGenApiService
	 * r.enUS: ClusterEnUSGenApiService
	 * r: EcoleFrFRGenApiService
	 * r.enUS: SchoolEnUSGenApiService
	 * r: UtilisateurSiteFrFRGenApiService
	 * r.enUS: SiteUserEnUSGenApiService
	 * r: CliniqueMedicaleFrFRGenApiService
	 * r.enUS: MedicalClinicEnUSGenApiService
	 * r: SaisonmedicaleFrFRGenApiService
	 * r.enUS: SchoolSeasonEnUSGenApiService
	 * r: SessionmedicaleFrFRGenApiService
	 * r.enUS: SchoolSessionEnUSGenApiService
	 * r: AgemedicaleFrFRGenApiService
	 * r.enUS: SchoolAgeEnUSGenApiService
	 * r: BlocmedicaleFrFRGenApiService
	 * r.enUS: SchoolBlockEnUSGenApiService
	 * r: InscriptionmedicaleFrFRGenApiService
	 * r.enUS: SchoolEnrollmentEnUSGenApiService
	 * r: EnfantmedicaleFrFRGenApiService
	 * r.enUS: SchoolChildEnUSGenApiService
	 * r: MeremedicaleFrFRGenApiService
	 * r.enUS: SchoolMomEnUSGenApiService
	 * r: PeremedicaleFrFRGenApiService
	 * r.enUS: SchoolDadEnUSGenApiService
	 * r: ContactmedicaleFrFRGenApiService
	 * r.enUS: SchoolContactEnUSGenApiService
	 * r: GardienmedicaleFrFRGenApiService
	 * r.enUS: SchoolGuardianEnUSGenApiService
	 * r: PaiementmedicaleFrFRGenApiService
	 * r.enUS: SchoolPaymentEnUSGenApiService
	 * r: DesignInscriptionFrFRGenApiService
	 * r.enUS: EnrollmentDesignEnUSGenApiService
	 * r: DesignPageFrFRGenApiService
	 * r.enUS: PageDesignEnUSGenApiService
	 * r: PartHtmlFrFRGenApiService
	 * r.enUS: HtmlPartEnUSGenApiService
	 * r: enregistrerService
	 * r.enUS: registerService
	 * r: getRouteur
	 * r.enUS: getRouter
	 */   
	private Promise<Void> demarrerServeur() {
		ConfigSite configSite = siteContexteFrFR.getConfigSite();
		Promise<Void> promise = Promise.promise();

		ClusterFrFRGenApiService.enregistrerService(siteContexteFrFR, vertx);
		CliniqueMedicaleFrFRGenApiService.enregistrerService(siteContexteFrFR, vertx);
		UtilisateurSiteFrFRGenApiService.enregistrerService(siteContexteFrFR, vertx);
		DesignPageFrFRGenApiService.enregistrerService(siteContexteFrFR, vertx);
		PartHtmlFrFRGenApiService.enregistrerService(siteContexteFrFR, vertx);

		Router siteRouteur = siteContexteFrFR.getRouteur();

		StaticHandler gestionnaireStatic = StaticHandler.create().setCachingEnabled(false).setFilesReadOnly(true);
		if("medicale-dev.computate.org".equals(configSite.getSiteNomHote())) {
			gestionnaireStatic.setAllowRootFileSystemAccess(true);
			gestionnaireStatic.setWebRoot("/usr/local/src/computate-medicale-static");
		}
		siteRouteur.route("/static/*").handler(gestionnaireStatic);

		SimpleModule module = new SimpleModule();
		module.addSerializer(ZonedDateTime.class, new ZonedDateTimeSerializer());
		module.addSerializer(LocalDate.class, new LocalDateSerializer());
		module.addSerializer(LocalTime.class, new LocalTimeSerializer());
		Json.mapper.registerModule(module);

		String siteNomHote = configSite.getSiteNomHote();
		Integer sitePort = configSite.getSitePort();
		HttpServerOptions options = new HttpServerOptions();
		if(configSite.getSslJksChemin() != null && new File(configSite.getSslJksChemin()).exists()) {
			options.setKeyStoreOptions(new JksOptions().setPath(configSite.getSslJksChemin()).setPassword(configSite.getSslJksMotDePasse()));
			options.setSsl(true);
			LOGGER.info(String.format(demarrerServeurSsl, configSite.getSslJksChemin()));
		}
		options.setPort(sitePort);

		LOGGER.info(String.format(demarrerServeurAvantServeur, "https", siteNomHote, sitePort));
		vertx.createHttpServer(options).requestHandler(siteRouteur).listen(ar -> {
			if (ar.succeeded()) {
				LOGGER.info(String.format(demarrerServeurSuccesServeur, "*", sitePort));
				promise.complete();
			} else {
				LOGGER.error(demarrerServeurErreurServeur, ar.cause());
				promise.fail(ar.cause());
			}
		});

		return promise;
	}

	/**
	 * Param1.var.enUS: stopPromise
	 * r: SiteContexteFrFR
	 * r.enUS: SiteContextEnUS
	 * r: siteContexteFrFR
	 * r.enUS: siteContextEnUS
	 * r: initLoin
	 * r.enUS: initDeep
	 * r: arreterPromise
	 * r.enUS: stopPromise
	 * r: fermerDonnees
	 * r.enUS: closeData
	 * r: etapesPromises
	 * r.enUS: promiseSteps
	 * 
	 * enUS: This is called by Vert.x when the verticle instance is undeployed. 
	 * enUS: Setup the stopPromise to handle tearing down the server. 
	 */
	@Override
	public void stop(Promise<Void> arreterPromise) throws Exception {
		Promise<Void> etapesPromises = fermerDonnees();
		etapesPromises.future().setHandler(arreterPromise);
	}

	/**
	 * Var.enUS: closeData
	 * Val.Error.enUS:Could not close the database client connection. 
	 * Val.Erreur.frFR:Impossible de fermer la connexion du client de base de données. 
	 * Val.Success.enUS:The database client connextion was closed. 
	 * Val.Succes.frFR:La connexion client de la base de données a été fermée.
	 * r: fermerDonneesErreur
	 * r.enUS: closeDataError
	 * r: fermerDonneesSucces
	 * r.enUS: closeDataSuccess
	 * r: siteContexteFrFR
	 * r.enUS: siteContextEnUS
	 * 
	 * enUS: Return a promise to close the database client connection. 
	 */        
	private Promise<Void> fermerDonnees() {
		Promise<Void> promise = Promise.promise();
		PgPool pgPool = siteContexteFrFR.getPgPool();

		if(pgPool != null) {
			pgPool.close();
			LOGGER.info(fermerDonneesSucces);
			promise.complete();
		}
		return promise;
	}
}
