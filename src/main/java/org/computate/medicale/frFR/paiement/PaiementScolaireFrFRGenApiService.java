package org.computate.medicale.frFR.paiement;

import org.computate.medicale.frFR.contexte.SiteContexteFrFR;
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
 * Traduire: false
 * NomCanonique.enUS: org.computate.medicale.enUS.payment.SchoolPaymentEnUSGenApiService
 * Gen: false
 **/
@WebApiServiceGen
@ProxyGen
public interface PaiementmedicaleFrFRGenApiService {
	static void enregistrerService(SiteContexteFrFR siteContexte, Vertx vertx) {
		new ServiceBinder(vertx).setAddress("computate-medicale-frFR-Paiementmedicale").register(PaiementmedicaleFrFRGenApiService.class, new PaiementmedicaleFrFRApiServiceImpl(siteContexte));
	}

	static PaiementmedicaleFrFRGenApiService creer(SiteContexteFrFR siteContexte, Vertx vertx) {
		return new PaiementmedicaleFrFRApiServiceImpl(siteContexte);
	}

	// Une méthode d'usine pour créer une instance et un proxy. 
	static PaiementmedicaleFrFRGenApiService creerProxy(Vertx vertx, String addresse) {
		return new PaiementmedicaleFrFRGenApiServiceVertxEBProxy(vertx, addresse);
	}

	public void postPaiementmedicale(JsonObject body, OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements);
	public void putimportPaiementmedicale(JsonObject body, OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements);
	public void putfusionPaiementmedicale(JsonObject body, OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements);
	public void putcopiePaiementmedicale(JsonObject body, OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements);
	public void patchPaiementmedicale(JsonObject body, OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements);
	public void getPaiementmedicale(OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements);
	public void recherchePaiementmedicale(OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements);
	public void pagerecherchePaiementmedicaleId(OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements);
	public void pagerecherchePaiementmedicale(OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements);
}
