package org.computate.medicale.enUS.cluster;

import org.computate.medicale.enUS.context.SiteContextEnUS;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.ext.web.api.generator.WebApiServiceGen;
import io.vertx.serviceproxy.ServiceBinder;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.api.OperationRequest;
import io.vertx.ext.web.api.OperationResponse;

/**
 * Translate: false
 * CanonicalName.frFR: org.computate.medicale.frFR.cluster.ClusterFrFRGenApiService
 * Gen: false
 **/
@WebApiServiceGen
@ProxyGen
public interface ClusterEnUSGenApiService {
	static void registerService(SiteContextEnUS siteContext, Vertx vertx) {
		new ServiceBinder(vertx).setAddress("computate-medicale-enUS-Cluster").register(ClusterEnUSGenApiService.class, new ClusterEnUSApiServiceImpl(siteContext));
	}

	static ClusterEnUSGenApiService create(SiteContextEnUS siteContext, Vertx vertx) {
		return new ClusterEnUSApiServiceImpl(siteContext);
	}

	// Une méthode d'usine pour créer une instance et un proxy. 
	static ClusterEnUSGenApiService createProxy(Vertx vertx, String address) {
		return new ClusterEnUSGenApiServiceVertxEBProxy(vertx, address);
	}

	public void postCluster(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void putimportCluster(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void putmergeCluster(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void putcopyCluster(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void patchCluster(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void getCluster(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void searchCluster(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void searchpageClusterId(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void searchpageCluster(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
}
