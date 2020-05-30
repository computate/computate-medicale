package org.computate.medicale.enUS.clinic;

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
 * CanonicalName.frFR: org.computate.medicale.frFR.clinique.CliniqueMedicaleFrFRGenApiService
 * Gen: false
 **/
@WebApiServiceGen
@ProxyGen
public interface MedicalClinicEnUSGenApiService {
	static void registerService(SiteContextEnUS siteContext, Vertx vertx) {
		new ServiceBinder(vertx).setAddress("computate-medicale-enUS-MedicalClinic").register(MedicalClinicEnUSGenApiService.class, new MedicalClinicEnUSApiServiceImpl(siteContext));
	}

	static MedicalClinicEnUSGenApiService create(SiteContextEnUS siteContext, Vertx vertx) {
		return new MedicalClinicEnUSApiServiceImpl(siteContext);
	}

	// Une méthode d'usine pour créer une instance et un proxy. 
	static MedicalClinicEnUSGenApiService createProxy(Vertx vertx, String address) {
		return new MedicalClinicEnUSGenApiServiceVertxEBProxy(vertx, address);
	}

	public void postMedicalClinic(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void patchMedicalClinic(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void getMedicalClinic(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void searchMedicalClinic(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void putimportMedicalClinic(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void putmergeMedicalClinic(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void putcopyMedicalClinic(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void searchpageMedicalClinicId(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void searchpageMedicalClinic(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
}
