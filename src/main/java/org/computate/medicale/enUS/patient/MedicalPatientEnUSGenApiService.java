package org.computate.medicale.enUS.patient;

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
 * CanonicalName.frFR: org.computate.medicale.frFR.patient.PatientMedicaleFrFRGenApiService
 * Gen: false
 **/
@WebApiServiceGen
@ProxyGen
public interface MedicalPatientEnUSGenApiService {
	static void registerService(SiteContextEnUS siteContext, Vertx vertx) {
		new ServiceBinder(vertx).setAddress("computate-medicale-enUS-MedicalPatient").register(MedicalPatientEnUSGenApiService.class, new MedicalPatientEnUSApiServiceImpl(siteContext));
	}

	static MedicalPatientEnUSGenApiService create(SiteContextEnUS siteContext, Vertx vertx) {
		return new MedicalPatientEnUSApiServiceImpl(siteContext);
	}

	// Une méthode d'usine pour créer une instance et un proxy. 
	static MedicalPatientEnUSGenApiService createProxy(Vertx vertx, String address) {
		return new MedicalPatientEnUSGenApiServiceVertxEBProxy(vertx, address);
	}

	public void postMedicalPatient(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void putimportMedicalPatient(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void putmergeMedicalPatient(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void putcopyMedicalPatient(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void patchMedicalPatient(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void getMedicalPatient(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void searchMedicalPatient(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void adminsearchMedicalPatient(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void searchpageMedicalPatientId(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void searchpageMedicalPatient(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
}
