package org.computate.medicale.enUS.vertx;

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
import org.computate.medicale.enUS.clinic.MedicalClinicEnUSGenApiService;
import org.computate.medicale.enUS.cluster.ClusterEnUSGenApiService;
import org.computate.medicale.enUS.config.SiteConfig;
import org.computate.medicale.enUS.context.SiteContextEnUS;
import org.computate.medicale.enUS.design.PageDesignEnUSGenApiService;
import org.computate.medicale.enUS.html.part.HtmlPartEnUSGenApiService;
import org.computate.medicale.frFR.java.LocalDateSerializer;
import org.computate.medicale.frFR.java.LocalTimeSerializer;
import org.computate.medicale.frFR.java.ZonedDateTimeSerializer;
import org.computate.medicale.enUS.request.SiteRequestEnUS;
import org.computate.medicale.enUS.user.SiteUserEnUSGenApiService;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
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
 * A Java class to start the Vert.x application as a main method. 
 * CanonicalName: org.computate.medicale.frFR.vertx.AppliVertx
 **/
public class AppVertx extends AppVertxGen<AbstractVerticle> {

	public final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

	/**
	 * A SQL query for creating a database table "c" to store any type of object in the application. 
	 **/
	public static final String SQL_createTableC = "create table if not exists c(pk bigserial primary key, current boolean, canonical_name text, created timestamp with time zone, user_id text); ";

	/**
	 * A SQL query for creating a unique index on the "c" table based on the pk, canonical_name, and user_id fields for faster lookup. 
	 **/
	public static final String SQL_uniqueIndexC = "create unique index if not exists c_index_user on c(pk, canonical_name, user_id); ";

	/**
	 * A SQL query for creating a database table "a" to store relations (like entity relations) between one other record in the "c" table with another record in the "c" table. 
	 **/
	public static final String SQL_createTableA = "create table if not exists a(pk bigserial primary key, pk1 bigint, entity1 text, pk2 bigint, entity2 text, current boolean, created timestamp with time zone, constraint a_constraint unique (pk1, entity1, pk2, entity2)); ";

	/**
	 * A SQL query for creating an index on the "a" table based on fields for faster lookup. 
	 **/
	public static final String SQL_uniqueIndexA = "create index if not exists a_index on a(pk1, pk2, current); ";

	/**
	 * A SQL query for creating a database table "d" to store String values to define fields in an instance of a class based on a record in the "c" table. 
	 **/
	public static final String SQL_createTableD = "create table if not exists d(pk bigserial primary key, pk_c bigint, path text, value text, current boolean, created timestamp with time zone, constraint d_constraint unique (pk_c, path)); ";

	/**
	 * A SQL query for creating an index on the "d" table based on fields for faster lookup. 
	 **/
	public static final String SQL_uniqueIndexD = "create index if not exists d_index on d(pk_c, current); ";

	/**
	 * Concatenate all of the SQL together to execute when the server starts. 
	 **/
	public static final String SQL_initAll = SQL_createTableC + SQL_uniqueIndexC + SQL_createTableA + SQL_uniqueIndexA + SQL_createTableD + SQL_uniqueIndexD;

	/**
	 * A io.vertx.ext.jdbc.JDBCClient for connecting to the relational database PostgreSQL. 
	 **/
	private PgPool pgPool;

	/**
	 * A site context object for storing information about the entire site in English. 
	 **/
	SiteContextEnUS siteContextEnUS;

	/**
	 * For logging information and errors in the application. 
	 **/
	private static final Logger LOGGER = LoggerFactory.getLogger(AppVertx.class);

	/**
	 * The main method for the Vert.x application that runs the Vert.x Runner class
	 **/
	public static void  main(String[] args) {
		RunnerVertx.run(AppVertx.class);
	}

	/**
	 * This is called by Vert.x when the verticle instance is deployed. 
	 * Initialize a new site context object for storing information about the entire site in English. 
	 * Setup the startPromise to handle the configuration steps and starting the server. 
	 **/
	@Override()
	public void  start(Promise<Void> startPromise) throws Exception, Exception {

		siteContextEnUS = new SiteContextEnUS();
		siteContextEnUS.setVertx(vertx);
		siteContextEnUS.initDeepSiteContextEnUS();

		Future<Void> promiseSteps = configureData().future().compose(a -> 
			configureCluster().future().compose(b -> 
				configureOpenApi().future().compose(c -> 
					configureHealthChecks().future().compose(d -> 
						configureSharedWorkerExecutor().future().compose(e -> 
							configureWebsockets().future().compose(f -> 
								configureEmail().future().compose(g -> 
									startServer().future()
								)
							)
						)
					)
				)
			)
		);
		promiseSteps.setHandler(startPromise);
	}

	/**
	 * Configure shared database connections across the cluster for massive scaling of the application. 
	 * Return a promise that configures a shared database client connection. 
	 * Load the database configuration into a shared io.vertx.ext.jdbc.JDBCClient for a scalable, clustered datasource connection pool. 
	 * Initialize the database tables if not already created for the first time. 
	 **/
	private Promise<Void> configureData() {
		SiteConfig siteConfig = siteContextEnUS.getSiteConfig();
		Promise<Void> promise = Promise.promise();

		PgConnectOptions pgOptions = new PgConnectOptions();
		pgOptions.setPort(siteConfig.getJdbcPort());
		pgOptions.setHost(siteConfig.getJdbcHost());
		pgOptions.setDatabase(siteConfig.getJdbcDatabase());
		pgOptions.setUser(siteConfig.getJdbcUsername());
		pgOptions.setPassword(siteConfig.getJdbcPassword());
		pgOptions.setIdleTimeout(siteConfig.getJdbcMaxIdleTime());
		pgOptions.setIdleTimeoutUnit(TimeUnit.SECONDS);
		pgOptions.setConnectTimeout(siteConfig.getJdbcConnectTimeout());

		PoolOptions poolOptions = new PoolOptions();
		poolOptions.setMaxSize(siteConfig.getJdbcMaxPoolSize());
		poolOptions.setMaxWaitQueueSize(siteConfig.getJdbcMaxWaitQueueSize());

		pgPool = PgPool.pool(vertx, pgOptions, poolOptions);

		siteContextEnUS.setPgPool(pgPool);


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
														LOGGER.info(configureDataInitSuccess);
														promise.complete();
													} else {
														LOGGER.error(configureDataInitError, f.cause());
														promise.fail(f.cause());
													}
												});
											} else {
												LOGGER.error(configureDataInitError, e.cause());
												promise.fail(e.cause());
											}
										});
									} else {
										LOGGER.error(configureDataInitError, d.cause());
										promise.fail(d.cause());
									}
								});
							} else {
								LOGGER.error(configureDataInitError, c.cause());
								promise.fail(c.cause());
							}
						});
					} else {
						LOGGER.error(configureDataInitError, b.cause());
						promise.fail(b.cause());
					}
				});
			} else {
				LOGGER.error(configureDataInitError, a.cause());
				promise.fail(a.cause());
			}
		});

		return promise;
	}

	/**
	 * Configure shared data across the cluster for massive scaling of the application. 
	 * Return a promise that configures a shared cluster data. 
	 **/
	private Promise<Void> configureCluster() {
		SiteConfig siteConfig = siteContextEnUS.getSiteConfig();
		Promise<Void> promise = Promise.promise();
		SharedData sharedData = vertx.sharedData();
		sharedData.getClusterWideMap("clusterData", res -> {
			if (res.succeeded()) {
				AsyncMap<Object, Object> clusterData = res.result();
				clusterData.put("siteConfig", siteConfig, resPut -> {
					if (resPut.succeeded()) {
						LOGGER.info(configureClusterDataSuccess);
						promise.complete();
					} else {
						LOGGER.error(configureClusterDataError, res.cause());
						promise.fail(res.cause());
					}
				});
			} else {
				LOGGER.error(configureClusterDataError, res.cause());
				promise.fail(res.cause());
			}
		});
		return promise;
	}

	/**
	 * Configure the connection to the auth server and setup the routes based on the OpenAPI definition. 
	 * Setup a callback route when returning from the auth server after successful authentication. 
	 * Setup a logout route for logging out completely of the application. 
	 * Return a promise that configures the authentication server and OpenAPI. 
	 **/
	private Promise<Void> configureOpenApi() {
		SiteConfig siteConfig = siteContextEnUS.getSiteConfig();
		Promise<Void> promise = Promise.promise();
		String siteUrlBase = siteConfig.getSiteBaseUrl();

		OAuth2ClientOptions oauth2ClientOptions = new OAuth2ClientOptions();
		oauth2ClientOptions.setSite(siteConfig.getAuthUrl() + "/realms/" + siteConfig.getAuthRealm());
		oauth2ClientOptions.setClientID(siteConfig.getAuthResource());
		oauth2ClientOptions.setClientSecret(siteConfig.getAuthSecret());
		oauth2ClientOptions.setFlow(OAuth2FlowType.AUTH_CODE);
		JsonObject extraParams = new JsonObject();
		extraParams.put("scope", "openid DefaultAuthScope SiteAdminScope");
		oauth2ClientOptions.setExtraParameters(extraParams);

		OpenIDConnectAuth.discover(vertx, oauth2ClientOptions, a -> {
			if(a.succeeded()) {
				OAuth2Auth authProvider = a.result();
	
				OAuth2AuthHandler authHandler = OAuth2AuthHandler.create(authProvider, siteUrlBase + "/callback");
				authHandler.addAuthority("DefaultAuthScope");
				authHandler.addAuthority("SiteAdminScope");
				authHandler.addAuthority("openid");
				{
					Router tempRouter = Router.router(vertx);
					authHandler.setupCallback(tempRouter.get("/callback"));
				}
		
		//		ClusteredSessionStore sessionStore = ClusteredSessionStore.create(vertx);
				LocalSessionStore sessionStore = LocalSessionStore.create(vertx, "computate-medicale-sessions");
				SessionHandler sessionHandler = SessionHandler.create(sessionStore);
				sessionHandler.setAuthProvider(authProvider);
		
				OpenAPI3RouterFactory.create(vertx, "openapi3-enUS.yaml", b -> {
					if (b.succeeded()) {
						OpenAPI3RouterFactory routerFactory = b.result();
						routerFactory.mountServicesFromExtensions();
						siteContextEnUS.setRouterFactory(routerFactory);
		
						routerFactory.addGlobalHandler(sessionHandler);
						routerFactory.addHandlerByOperationId("callback", ctx -> {
		
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
		
							authProvider.authenticate(config, res -> {
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
												ctx.addCookie(Cookie.cookie("sessionIdBefore", session.id()));
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
						routerFactory.addFailureHandlerByOperationId("callback", c -> {});
		
						routerFactory.addHandlerByOperationId("logout", rc -> {
							Session session = rc.session();
//							if (session != null) {
//								session.destroy();
//							}
							rc.clearUser();
							rc.reroute("/");
						});
						routerFactory.addFailureHandlerByOperationId("logout", c -> {});
		
//						routerFactory.addSecurityHandler("openIdConnect", authHandler);
						routerFactory.addSecuritySchemaScopeValidator("openIdConnect", "DefaultAuthScope", authHandler);
						routerFactory.addSecuritySchemaScopeValidator("openIdConnect", "SiteAdminScope", authHandler);
						routerFactory.addSecuritySchemaScopeValidator("openIdConnect", "openid", authHandler);
						Router router = routerFactory.getRouter();
						siteContextEnUS.setRouter(router);
		
						LOGGER.info(configureOpenApiSuccess);
						promise.complete();
					} else {
						LOGGER.error(configureOpenApiError, b.cause());
						promise.fail(b.cause());
					}
				});
			} else {
				LOGGER.error(configureOpenApiError, a.cause());
				promise.fail(a.cause());
			}
		});
		return promise;
	}

	/**
	 * Configure a shared worker executor for running blocking tasks in the background. 
	 * Return a promise that configures the shared worker executor. 
	 **/
	private Promise<Void> configureSharedWorkerExecutor() {
		Promise<Void> promise = Promise.promise();

		WorkerExecutor workerExecutor = vertx.createSharedWorkerExecutor("WorkerExecutor");
		siteContextEnUS.setWorkerExecutor(workerExecutor);
		promise.complete();
		return promise;
	}

	/**
	 * Configure health checks for the status of the website and it's dependent services. 
	 * Return a promise that configures the health checks. 
	 **/
	private Promise<Void> configureHealthChecks() {
		Promise<Void> promise = Promise.promise();
		Router siteRouteur = siteContextEnUS.getRouter();
		HealthCheckHandler healthCheckHandler = HealthCheckHandler.create(vertx);

		healthCheckHandler.register("database", 2000, a -> {
			siteContextEnUS.getPgPool().preparedQuery("select current_timestamp" , selectCAsync -> {
				if(selectCAsync.succeeded()) {
					a.complete(Status.OK());
				} else {
					LOGGER.error(configureHealthChecksErrorDatabase, a.future().cause());
					promise.fail(a.future().cause());
				}
			});
		});
		healthCheckHandler.register("solr", 2000, a -> {
			SolrQuery query = new SolrQuery();
			query.setQuery("*:*");
			try {
				QueryResponse r = siteContextEnUS.getSolrClient().query(query);
				if(r.getResults().size() > 0)
					a.complete(Status.OK());
				else {
					LOGGER.error(configureHealthChecksEmptySolr, a.future().cause());
					promise.fail(a.future().cause());
				}
			} catch (SolrServerException | IOException e) {
				LOGGER.error(configureHealthChecksErrorSolr, a.future().cause());
				promise.fail(a.future().cause());
			}
		});
		siteRouteur.get("/health").handler(healthCheckHandler);
		promise.complete();
		return promise;
	}

	/**
	 * Configure websockets for realtime messages. 
	 **/
	private Promise<Void> configureWebsockets() {
		Promise<Void> promise = Promise.promise();
		Router siteRouter = siteContextEnUS.getRouter();
		BridgeOptions options = new BridgeOptions()
				.addOutboundPermitted(new PermittedOptions().setAddressRegex("websocket.*"));
		SockJSHandler sockJsHandler = SockJSHandler.create(vertx);
		sockJsHandler.bridge(options);
		siteRouter.route("/eventbus/*").handler(sockJsHandler);
		promise.complete();
		return promise;
	}

	/**
	 * Configure sending email. 
	 **/
	private Promise<Void> configureEmail() {
		SiteConfig siteConfig = siteContextEnUS.getSiteConfig();
		Promise<Void> promise = Promise.promise();
		MailConfig config = new MailConfig();
		config.setHostname(siteConfig.getEmailHost());
		config.setPort(siteConfig.getEmailPort());
		config.setSsl(siteConfig.getEmailSsl());
		config.setUsername(siteConfig.getEmailUsername());
		config.setPassword(siteConfig.getEmailPassword());
		MailClient mailClient = MailClient.createShared(vertx, config);
		siteContextEnUS.setMailClient(mailClient);
		promise.complete();
		return promise;
	}

	public void  errorAppVertx(SiteRequestEnUS siteRequest, AsyncResult<?> a) {
		Throwable e = a.cause();
		if(e != null)
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		if(siteRequest != null) {
			Transaction tx = siteRequest.getTx();
			if(tx != null) {
				tx.rollback(b -> {
					if(b.succeeded()) {
						LOGGER.info("Rollback the SQL connection succeded. ");
						try {
							SqlConnection connexionSql = siteRequest.getSqlConnection();
				
							if(connexionSql == null) {
								LOGGER.info("Close the SQL connection succeded. ");
							} else {
								connexionSql.close();
								siteRequest.setSqlConnection(null);
								LOGGER.info("Close the SQL connection succeded. ");
							}
						} catch(Exception ex) {
							LOGGER.error(String.format("sqlFermerEcole a échoué. ", ex));
						}
					} else {
						LOGGER.error("Rollback the SQL connection failed. ", b.cause());
					}
				});
			}
		}
	}

	/**
	 * Start the Vert.x server. 
	 * Démarrer le serveur Vert.x. 
	 **/
	private Promise<Void> startServer() {
		SiteConfig siteConfig = siteContextEnUS.getSiteConfig();
		Promise<Void> promise = Promise.promise();

		ClusterEnUSGenApiService.registerService(siteContextEnUS, vertx);
		MedicalClinicEnUSGenApiService.registerService(siteContextEnUS, vertx);
		SiteUserEnUSGenApiService.registerService(siteContextEnUS, vertx);
		PageDesignEnUSGenApiService.registerService(siteContextEnUS, vertx);
		HtmlPartEnUSGenApiService.registerService(siteContextEnUS, vertx);

		Router siteRouter = siteContextEnUS.getRouter();

		StaticHandler staticHandler = StaticHandler.create().setCachingEnabled(false).setFilesReadOnly(true);
		if("medicale-dev.computate.org".equals(siteConfig.getSiteHostName())) {
			staticHandler.setAllowRootFileSystemAccess(true);
			staticHandler.setWebRoot("/usr/local/src/computate-medicale-static");
		}
		siteRouter.route("/static/*").handler(staticHandler);

		SimpleModule module = new SimpleModule();
		module.addSerializer(ZonedDateTime.class, new ZonedDateTimeSerializer());
		module.addSerializer(LocalDate.class, new LocalDateSerializer());
		module.addSerializer(LocalTime.class, new LocalTimeSerializer());
		Json.mapper.registerModule(module);

		String siteHostName = siteConfig.getSiteHostName();
		Integer sitePort = siteConfig.getSitePort();
		HttpServerOptions options = new HttpServerOptions();
		if(siteConfig.getSslJksPath() != null && new File(siteConfig.getSslJksPath()).exists()) {
			options.setKeyStoreOptions(new JksOptions().setPath(siteConfig.getSslJksPath()).setPassword(siteConfig.getSslJksPassword()));
			options.setSsl(true);
			LOGGER.info(String.format(startServerSsl, siteConfig.getSslJksPath()));
		}
		options.setPort(sitePort);

		LOGGER.info(String.format(startServerBeforeServer, "https", siteHostName, sitePort));
		vertx.createHttpServer(options).requestHandler(siteRouter).listen(ar -> {
			if (ar.succeeded()) {
				LOGGER.info(String.format(startServerSuccessServer, "*", sitePort));
				promise.complete();
			} else {
				LOGGER.error(startServerErrorServer, ar.cause());
				promise.fail(ar.cause());
			}
		});

		return promise;
	}

	/**
	 * This is called by Vert.x when the verticle instance is undeployed. 
	 * Setup the stopPromise to handle tearing down the server. 
	 **/
	@Override()
	public void  stop(Promise<Void> stopPromise) throws Exception, Exception {
		Promise<Void> promiseSteps = closeData();
		promiseSteps.future().setHandler(stopPromise);
	}

	/**
	 * Return a promise to close the database client connection. 
	 **/
	private Promise<Void> closeData() {
		Promise<Void> promise = Promise.promise();
		PgPool pgPool = siteContextEnUS.getPgPool();

		if(pgPool != null) {
			pgPool.close();
			LOGGER.info(closeDataSuccess);
			promise.complete();
		}
		return promise;
	}
}
