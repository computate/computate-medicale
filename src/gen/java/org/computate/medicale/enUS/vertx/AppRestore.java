package org.computate.medicale.enUS.vertx;

import java.security.MessageDigest;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.computate.medicale.enUS.cluster.Cluster;
import org.computate.medicale.enUS.config.SiteConfig;
import org.computate.medicale.enUS.context.SiteContextEnUS;
import org.computate.medicale.enUS.context.SiteContextEnUSGen;
import org.computate.medicale.enUS.request.SiteRequestEnUS;
import org.computate.medicale.enUS.search.SearchList;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.CaseInsensitiveHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.api.OperationResponse;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.Tuple;

public class AppRestore extends AbstractVerticle {

	Base64.Decoder decoder;
	Cipher cipher;

	Boolean scramble = false;

	public static void main(String[] args) {

		final Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new AppRestore());
	}

	/**	
	 *	This is called by Vert.x when the verticle instance is deployed. 
	 *	Initialize a new site context object for storing information about the entire site in English. 
	 *	Setup the startFuture to handle the configuration steps and starting the server. 
	 **/
	@Override()
	public void  start(Promise<Void> startPromise) throws Exception, Exception {
		SiteRequestEnUS siteRequest = new SiteRequestEnUS();
		SiteContextEnUS siteContext = new SiteContextEnUS();
		siteRequest.setSiteContext_(siteContext);
		siteRequest.initDeepSiteRequestEnUS();
		siteContext.getSiteConfig()
				.setConfigPath("/usr/local/src/computate-medicale/config/computate-medicale-enUS.config");
		siteContext.initDeepSiteContextEnUS();
		siteRequest.setSiteConfig_(siteContext.getSiteConfig());

		start(siteRequest, startPromise);
	}

	public void  start(SiteRequestEnUS siteRequest, Promise<Void> startPromise) throws Exception, Exception {
		SiteContextEnUS siteContext = siteRequest.getSiteContext_();
		SiteRequestEnUS siteRequest2 = new SiteRequestEnUS();
		siteRequest2.setSiteContext_(siteContext);
		siteRequest2.initDeepSiteRequestEnUS();
		siteRequest2.setSiteConfig_(siteContext.getSiteConfig());
		siteRequest = siteRequest2;
		start2(siteRequest2, startPromise);
	}

	public void  start2(SiteRequestEnUS siteRequest, Promise<Void> startPromise) throws Exception, Exception {

		try {
			ZonedDateTime dateTime = ZonedDateTime.now();
			configureData(siteRequest);

			decoder = Base64.getDecoder();
			cipher = Cipher.getInstance("AES");
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
			byte[] key = Arrays.copyOf(messageDigest.digest((siteRequest.getSiteConfig_().getEncryptionPassword()).getBytes("UTF-8")), 16);
			SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

			sqlCluster(siteRequest, a -> {
				if(a.succeeded()) {
					nextDefinitions(siteRequest, dateTime, b -> {
						if(b.succeeded()) {
							List<Row> rows = b.result();
							listDefinitions(siteRequest, rows, dateTime, d -> {
								if(d.succeeded()) {
									nextClusters(siteRequest, dateTime, e -> {
										if(e.succeeded()) {
											SearchList<Cluster> clusters = e.result();
											listPATCHCluster(clusters, dateTime, f -> {
												if(f.succeeded()) {
													startPromise.complete();
												} else {
													startPromise.fail(f.cause());
				//									errorCluster(siteRequest, eventHandler, d);
												}
											});
										} else {
											startPromise.fail(e.cause());
				//							errorCluster(siteRequest, eventHandler, b);
										}
									});
								} else {
									startPromise.fail(d.cause());
				//					errorCluster(siteRequest, eventHandler, a);
								}
							});
						} else {
							startPromise.fail(b.cause());
//							errorCluster(siteRequest, eventHandler, b);
						}
					});
				} else {
					startPromise.fail(a.cause());
//					errorCluster(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception e) {
			startPromise.fail(e);
//			errorCluster(null, eventHandler, Future.failedFuture(e));
		}
	}

	/**	
	 *	Configure shared database connections across the cluster for massive scaling of the application. 
	 *	Return a future that configures a shared database client connection. 
	 *	Load the database configuration into a shared io.vertx.ext.jdbc.JDBCClient for a scalable, clustered datasource connection pool. 
	 *	Initialize the database tables if not already created for the first time. 
	 **/
	private Future<Void> configureData(SiteRequestEnUS siteRequest) {
		SiteContextEnUSGen<Object> siteContextEnUS = siteRequest.getSiteContext_();
		SiteConfig siteConfig = siteContextEnUS.getSiteConfig();
		Promise<Void> promise = Promise.promise();

		PgConnectOptions pgOptions = new PgConnectOptions();
		pgOptions.setPort(siteConfig.getJdbcPort());
		pgOptions.setHost(siteConfig.getJdbcHost());
		pgOptions.setDatabase(siteConfig.getJdbcDatabase());
		pgOptions.setUser(siteConfig.getJdbcUsername());
		pgOptions.setPassword(siteConfig.getJdbcPassword());
		pgOptions.setIdleTimeout(siteConfig.getJdbcMaxIdleTime());

		PoolOptions poolOptions = new PoolOptions();
		poolOptions.setMaxSize(siteConfig.getJdbcMaxPoolSize());

		PgPool pgPool = PgPool.pool(vertx, pgOptions, poolOptions);

		siteContextEnUS.setPgPool(pgPool);
		promise.complete();

		return promise.future();
	}

	public void nextDefinitions(SiteRequestEnUS siteRequest, ZonedDateTime dateTime, Handler<AsyncResult<List<Row>>> eventHandler) {
		try {
			PgPool pgPool = siteRequest.getSiteContext_().getPgPool();
			pgPool.preparedQuery(
					"update d set modified=now() where pk in (select pk from d where not current and modified < $1 order by pk limit 10) returning pk, pk_c, path, value, current, created, modified;"
//					"update d set modified=now() where pk in (select pk from d where not current and modified < $1 order by pk limit 0) returning pk, pk_c, path, value, current, created, modified;"
					, Tuple.of(dateTime.toOffsetDateTime())
					, Collectors.toList()
					, updateDAsync
			-> {
				if(updateDAsync.succeeded()) {
					List<Row> rows = updateDAsync.result().value();
					System.out.println("d " + rows.stream().map(row -> row.getLong(0)).collect(Collectors.toList()));
					eventHandler.handle(Future.succeededFuture(rows));
				} else {
					eventHandler.handle(Future.failedFuture(new Exception(updateDAsync.cause())));
				}
			});
		} catch(Exception e) {
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void nextClusters(SiteRequestEnUS siteRequest, ZonedDateTime dateTime, Handler<AsyncResult<SearchList<Cluster>>> eventHandler) {
		try {
			PgPool pgPool = siteRequest.getSiteContext_().getPgPool();
			pgPool.preparedQuery(
					"update c set modified=now() where pk in (select pk from c where canonical_name is not null and modified < $1 order by pk limit 10) returning pk, current, canonical_name, created, modified, user_id;"
					, Tuple.of(dateTime.toOffsetDateTime())
					, Collectors.toList()
					, selectCAsync
			-> {
				if(selectCAsync.succeeded()) {
					SearchList<Cluster> searchList = new SearchList<Cluster>();
					searchList.setSiteRequest_(siteRequest);
					System.out.println("c " + selectCAsync.result().value().stream().map(row -> row.getLong(0)).collect(Collectors.toList()));
					selectCAsync.result().value().forEach(clusterValues -> {
						try {
							Long pk = clusterValues.getLong(0);
							String canonicalName = clusterValues.getString(2);
							Cluster cluster = (Cluster)Class.forName(canonicalName).newInstance();
							cluster.setPk(pk);
							cluster.setCreated(clusterValues.getOffsetDateTime(3).atZoneSameInstant(ZoneId.systemDefault()));
							cluster.setSiteRequest_(siteRequest);
							searchList.getList().add(cluster);
						} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
							eventHandler.handle(Future.failedFuture(e));
						}
					});
					eventHandler.handle(Future.succeededFuture(searchList));
				} else {
					eventHandler.handle(Future.failedFuture(new Exception(selectCAsync.cause())));
				}
			});
		} catch(Exception e) {
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlCluster(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			PgPool pgPool = siteRequest.getSiteContext_().getPgPool();

			if(pgPool == null) {
				eventHandler.handle(Future.succeededFuture());
			} else {
				eventHandler.handle(Future.succeededFuture());
			}
		} catch(Exception e) {
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void listDefinitions(SiteRequestEnUS siteRequest, List<Row> rows, ZonedDateTime dateTime, Handler<AsyncResult<List<Row>>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		if(rows.size() == 0) {
			eventHandler.handle(Future.succeededFuture(rows));
		}
		else {
			rows.forEach(o -> {
				futures.add(
					futureDefinition(siteRequest, rows, dateTime, o, a -> {
						if(a.succeeded()) {
							a.toString();
						} else {
							eventHandler.handle(Future.failedFuture(a.cause()));
						}
					})
				);
			});
			CompositeFuture.all(futures).setHandler( a -> {
				if(a.succeeded()) {
					nextDefinitions(siteRequest, dateTime, b -> {
						if(b.succeeded()) {
							List<Row> clusters = b.result();
							listDefinitions(siteRequest, clusters, dateTime, eventHandler);
						} else {
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		}
	}

	public Future<List<Row>> futureDefinition(SiteRequestEnUS siteRequest, List<Row> rows, ZonedDateTime dateTime, Row o,  Handler<AsyncResult<List<Row>>> eventHandler) {

		Promise<List<Row>> promise = Promise.promise();
		try {
			Long pk = o.getLong(0);
			String path = o.getString(2);
			String value = o.getString(3);
			try {
				value = new String(cipher.doFinal(decoder.decode(value)));
			} catch (Exception e1) {
				System.err.println(o);
			}
			PgPool pgPool = siteRequest.getSiteContext_().getPgPool();
			pgPool.preparedQuery(
					"update d set modified=now(), value=$1, current=true where pk=$2;"
					, Tuple.of(value, pk)
					, selectCAsync
			-> {
				if(selectCAsync.succeeded()) {
					promise.complete(rows);
					eventHandler.handle(Future.succeededFuture(rows));
				} else {
					eventHandler.handle(Future.failedFuture(selectCAsync.cause()));
				}
			});
			return promise.future();
		} catch(Exception e) {
			return Future.failedFuture(e);
		}
	}

	public void listPATCHCluster(SearchList<Cluster> listCluster, ZonedDateTime dateTime, Handler<AsyncResult<OperationResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listCluster.getSiteRequest_();
		if(listCluster.size() == 0) {
			response200PATCHCluster(listCluster, eventHandler);
		}
		else {
			listCluster.getList().forEach(o -> {
				futures.add(
					futurePATCHCluster(o, a -> {
						if(a.succeeded()) {
						} else {
							errorCluster(siteRequest, eventHandler, a);
						}
					})
				);
			});
			CompositeFuture.all(futures).setHandler( a -> {
				if(a.succeeded()) {
					nextClusters(siteRequest, dateTime, b -> {
						if(b.succeeded()) {
							SearchList<Cluster> clusters = b.result();
							listPATCHCluster(clusters, dateTime, eventHandler);
						} else {
							errorCluster(listCluster.getSiteRequest_(), eventHandler, a);
						}
					});
				} else {
					errorCluster(listCluster.getSiteRequest_(), eventHandler, a);
				}
			});
		}
	}

	public Future<Cluster> futurePATCHCluster(Cluster o,  Handler<AsyncResult<OperationResponse>> eventHandler) {
		Promise<Cluster> promise = Promise.promise();
		try {
			defineCluster(o, b -> {
				if(b.succeeded()) {
					attributeCluster(o, c -> {
						if(c.succeeded()) {
							indexCluster(o, d -> {
								if(d.succeeded()) {
									promise.complete(o);
//									eventHandler.handle(Future.succeededFuture(d.result()));
								} else {
									eventHandler.handle(Future.failedFuture(d.cause()));
								}
							});
						} else {
							eventHandler.handle(Future.failedFuture(c.cause()));
						}
					});
				} else {
					eventHandler.handle(Future.failedFuture(b.cause()));
				}
			});
			return promise.future();
		} catch(Exception e) {
			return Future.failedFuture(e);
		}
	}

	public void defineCluster(Cluster o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			PgPool pgPool = siteRequest.getSiteContext_().getPgPool();
			Long pk = o.getPk();
			pgPool.preparedQuery(
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
							} catch (Exception e) {
								// TODO Auto-generated catch block
								System.out.println(definition);
								e.printStackTrace();
							}
						}
						eventHandler.handle(Future.succeededFuture());
					} catch(Exception e) {
						eventHandler.handle(Future.failedFuture(e));
					}
				} else {
					eventHandler.handle(Future.failedFuture(defineAsync.cause()));
				}
			});
		} catch(Exception e) {
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void attributeCluster(Cluster o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			PgPool pgPool = siteRequest.getSiteContext_().getPgPool();
			Long pk = o.getPk();
			pgPool.preparedQuery(
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
						eventHandler.handle(Future.failedFuture(new Exception(attributeAsync.cause())));
					}
				} catch(Exception e) {
					eventHandler.handle(Future.failedFuture(e));
				}
			});
		} catch(Exception e) {
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void indexCluster(Cluster o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			o.initDeepForClass(siteRequest);
			o.indexForClass();
			eventHandler.handle(Future.succeededFuture());
		} catch(Exception e) {
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void response200PATCHCluster(SearchList<Cluster> listCluster, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listCluster.getSiteRequest_();
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(json)));
		} catch(Exception e) {
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void errorCluster(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler, AsyncResult<?> resultAsync) {
		Throwable e = resultAsync.cause();
		ExceptionUtils.printRootCauseStackTrace(e);
		OperationResponse responseOperation = new OperationResponse(400, "BAD REQUEST", 
			Buffer.buffer().appendString(
				new JsonObject() {{
					put("error", new JsonObject() {{
					put("message", e.getMessage());
					}});
				}}.encodePrettily()
			)
			, new CaseInsensitiveHeaders()
		);
		if(siteRequest != null) {
			PgPool pgPool = siteRequest.getSiteContext_().getPgPool();
			eventHandler.handle(Future.succeededFuture(responseOperation));
		} else {
			eventHandler.handle(Future.succeededFuture(responseOperation));
		}
	}
}
