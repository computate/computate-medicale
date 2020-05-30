package org.computate.medicale.enUS.enrollment;

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
 * CanonicalName.frFR: org.computate.medicale.frFR.inscription.InscriptionMedicaleFrFRGenApiService
 * Gen: false
 **/
@WebApiServiceGen
@ProxyGen
public interface MedicalEnrollmentEnUSGenApiService {
	static void registerService(SiteContextEnUS siteContext, Vertx vertx) {
		new ServiceBinder(vertx).setAddress("computate-medicale-enUS-MedicalEnrollment").register(MedicalEnrollmentEnUSGenApiService.class, new MedicalEnrollmentEnUSApiServiceImpl(siteContext));
	}

	static MedicalEnrollmentEnUSGenApiService create(SiteContextEnUS siteContext, Vertx vertx) {
		return new MedicalEnrollmentEnUSApiServiceImpl(siteContext);
	}

	// Une méthode d'usine pour créer une instance et un proxy. 
	static MedicalEnrollmentEnUSGenApiService createProxy(Vertx vertx, String address) {
		return new MedicalEnrollmentEnUSGenApiServiceVertxEBProxy(vertx, address);
	}

	public void postMedicalEnrollment(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void putimportMedicalEnrollment(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void putmergeMedicalEnrollment(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void putcopyMedicalEnrollment(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void patchMedicalEnrollment(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void getMedicalEnrollment(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void searchMedicalEnrollment(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void adminsearchMedicalEnrollment(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void patchpaymentsMedicalEnrollment(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void searchpageMedicalEnrollmentId(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void searchpageMedicalEnrollment(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void refreshsearchpageMedicalEnrollmentId(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void refreshsearchpageMedicalEnrollment(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
}
