package org.computate.medicale.frFR.inscription;

import org.computate.medicale.frFR.cluster.ClusterPage;
import org.computate.medicale.frFR.page.MiseEnPage;
import org.computate.medicale.frFR.config.ConfigSite;
import org.computate.medicale.frFR.requete.RequeteSiteFrFR;
import org.computate.medicale.frFR.contexte.SiteContexteFrFR;
import org.computate.medicale.frFR.utilisateur.UtilisateurSite;
import java.io.IOException;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import org.computate.medicale.frFR.recherche.ListeRecherche;
import org.computate.medicale.frFR.couverture.Couverture;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.api.OperationRequest;
import io.vertx.core.json.JsonArray;
import java.net.URLDecoder;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import java.util.Map;
import java.util.List;
import java.util.Optional;
import org.apache.solr.common.util.SimpleOrderedMap;
import java.util.stream.Collectors;
import java.util.Arrays;
import org.apache.solr.client.solrj.response.QueryResponse;
import java.math.BigDecimal;
import java.math.MathContext;
import org.apache.commons.collections.CollectionUtils;
import java.util.Objects;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.computate.medicale.frFR.cluster.ClusterPage;
import org.computate.medicale.frFR.page.MiseEnPage;
import org.computate.medicale.frFR.config.ConfigSite;
import org.computate.medicale.frFR.requete.RequeteSiteFrFR;
import org.computate.medicale.frFR.contexte.SiteContexteFrFR;
import org.computate.medicale.frFR.utilisateur.UtilisateurSite;
import java.io.IOException;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import org.computate.medicale.frFR.recherche.ListeRecherche;
import org.computate.medicale.frFR.couverture.Couverture;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.api.OperationRequest;
import io.vertx.core.json.JsonArray;
import java.net.URLDecoder;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import java.util.Map;
import java.util.List;
import java.util.Optional;
import org.apache.solr.common.util.SimpleOrderedMap;
import java.util.stream.Collectors;
import java.util.Arrays;
import org.apache.solr.client.solrj.response.QueryResponse;
import java.math.BigDecimal;
import java.math.MathContext;
import org.apache.commons.collections.CollectionUtils;
import java.util.Objects;
import org.apache.solr.client.solrj.SolrQuery.SortClause;


/**
 * Traduire: false
 * NomCanonique.enUS: org.computate.medicale.enUS.enrollment.EnrollmentGenPage
 **/
public class InscriptionGenPage extends InscriptionGenPageGen<ClusterPage> {

	public static final List<String> ROLES = Arrays.asList("SiteAdmin");
	public static final List<String> ROLE_READS = Arrays.asList("");

	/**
	 * {@inheritDoc}
	 * 
	 **/
	protected void _listeInscriptionMedicale(Couverture<ListeRecherche<InscriptionMedicale>> c) {
	}

	protected void _inscriptionMedicale(Couverture<InscriptionMedicale> c) {
		if(listeInscriptionMedicale != null && listeInscriptionMedicale.size() == 1)
			c.o(listeInscriptionMedicale.get(0));
	}

	@Override protected void _pageH1(Couverture<String> c) {
			c.o("inscriptions");
	}

	@Override protected void _pageH2(Couverture<String> c) {
		if(inscriptionMedicale != null && inscriptionMedicale.getInscriptionNomComplet() != null)
			c.o(inscriptionMedicale.getInscriptionNomComplet());
	}

	@Override protected void _pageH3(Couverture<String> c) {
		c.o("");
	}

	@Override protected void _pageTitre(Couverture<String> c) {
		if(inscriptionMedicale != null && inscriptionMedicale.getInscriptionNomComplet() != null)
			c.o(inscriptionMedicale.getInscriptionNomComplet());
		else if(inscriptionMedicale != null)
			c.o("");
		else if(listeInscriptionMedicale == null || listeInscriptionMedicale.size() == 0)
			c.o("aucune inscription trouvée");
	}

	@Override protected void _pageUri(Couverture<String> c) {
		c.o("/inscription");
	}

	@Override protected void _pageImageUri(Couverture<String> c) {
			c.o("/png/inscription-999.png");
	}

	@Override protected void _contexteIconeGroupe(Couverture<String> c) {
			c.o("solid");
	}

	@Override protected void _contexteIconeNom(Couverture<String> c) {
			c.o("edit");
	}

	@Override public void initLoinInscriptionGenPage() {
		initInscriptionGenPage();
		super.initLoinMiseEnPage();
	}

	@Override public void htmlScriptsInscriptionGenPage() {
		e("script").a("src", statiqueUrlBase, "/js/frFR/InscriptionPage.js").f().g("script");
		e("script").a("src", statiqueUrlBase, "/js/frFR/EnfantPage.js").f().g("script");
		e("script").a("src", statiqueUrlBase, "/js/frFR/UtilisateurSitePage.js").f().g("script");
	}

	@Override public void htmlScriptInscriptionGenPage() {
		l("$(document).ready(function() {");
		tl(1, "document.onkeydown = function(evt) {");
		tl(2, "evt = evt || window.event;");
		tl(2, "var isEscape = false;");
		tl(2, "if ('key' in evt) {");
		tl(3, "isEscape = (evt.key === 'Escape' || evt.key === 'Esc');");
		tl(2, "} else {");
		tl(3, "isEscape = (evt.keyCode === 27);");
		tl(2, "}");
		tl(2, "if (isEscape) {");
		tl(3, "$('.w3-modal:visible').hide();");
		tl(2, "}");
		tl(1, "};");
		tl(1, "window.eventBus = new EventBus('/eventbus');");
		tl(1, "var pk = ", Optional.ofNullable(requeteSite_.getRequetePk()).map(l -> l.toString()).orElse("null"), ";");
		tl(1, "if(pk != null) {");
		if(
				CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
				) {
			tl(2, "suggereInscriptionMedicalePatientCle([{'name':'fq','value':'inscriptionCles:' + pk}], $('#listInscriptionMedicalePatientCle_Page'), pk, true); ");
		} else {
			tl(2, "suggereInscriptionMedicalePatientCle([{'name':'fq','value':'inscriptionCles:' + pk}], $('#listInscriptionMedicalePatientCle_Page'), pk, false); ");
		}
		if(
				CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
				) {
			tl(2, "suggereInscriptionMedicaleUtilisateurCles([{'name':'fq','value':'inscriptionCles:' + pk}], $('#listInscriptionMedicaleUtilisateurCles_Page'), pk, true); ");
		} else {
			tl(2, "suggereInscriptionMedicaleUtilisateurCles([{'name':'fq','value':'inscriptionCles:' + pk}], $('#listInscriptionMedicaleUtilisateurCles_Page'), pk, false); ");
		}
		tl(2, "$('#signatureInputInscriptionMedicale' + pk + 'inscriptionSignature1').jSignature({'height':200}).bind('change', function(e){ patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + pk }], 'setInscriptionSignature1', $('#signatureInputInscriptionMedicale' + pk + 'inscriptionSignature1').jSignature('getData', 'default')); }); ");
		tl(2, "$('#signatureInputInscriptionMedicale' + pk + 'inscriptionSignature2').jSignature({'height':200}).bind('change', function(e){ patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + pk }], 'setInscriptionSignature2', $('#signatureInputInscriptionMedicale' + pk + 'inscriptionSignature2').jSignature('getData', 'default')); }); ");
		tl(2, "$('#signatureInputInscriptionMedicale' + pk + 'inscriptionSignature3').jSignature({'height':200}).bind('change', function(e){ patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + pk }], 'setInscriptionSignature3', $('#signatureInputInscriptionMedicale' + pk + 'inscriptionSignature3').jSignature('getData', 'default')); }); ");
		tl(2, "$('#signatureInputInscriptionMedicale' + pk + 'inscriptionSignature4').jSignature({'height':200}).bind('change', function(e){ patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + pk }], 'setInscriptionSignature4', $('#signatureInputInscriptionMedicale' + pk + 'inscriptionSignature4').jSignature('getData', 'default')); }); ");
		tl(2, "$('#signatureInputInscriptionMedicale' + pk + 'inscriptionSignature5').jSignature({'height':200}).bind('change', function(e){ patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + pk }], 'setInscriptionSignature5', $('#signatureInputInscriptionMedicale' + pk + 'inscriptionSignature5').jSignature('getData', 'default')); }); ");
		tl(2, "$('#signatureInputInscriptionMedicale' + pk + 'inscriptionSignature6').jSignature({'height':200}).bind('change', function(e){ patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + pk }], 'setInscriptionSignature6', $('#signatureInputInscriptionMedicale' + pk + 'inscriptionSignature6').jSignature('getData', 'default')); }); ");
		tl(2, "$('#signatureInputInscriptionMedicale' + pk + 'inscriptionSignature7').jSignature({'height':200}).bind('change', function(e){ patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + pk }], 'setInscriptionSignature7', $('#signatureInputInscriptionMedicale' + pk + 'inscriptionSignature7').jSignature('getData', 'default')); }); ");
		tl(2, "$('#signatureInputInscriptionMedicale' + pk + 'inscriptionSignature8').jSignature({'height':200}).bind('change', function(e){ patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + pk }], 'setInscriptionSignature8', $('#signatureInputInscriptionMedicale' + pk + 'inscriptionSignature8').jSignature('getData', 'default')); }); ");
		tl(2, "$('#signatureInputInscriptionMedicale' + pk + 'inscriptionSignature9').jSignature({'height':200}).bind('change', function(e){ patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + pk }], 'setInscriptionSignature9', $('#signatureInputInscriptionMedicale' + pk + 'inscriptionSignature9').jSignature('getData', 'default')); }); ");
		tl(2, "$('#signatureInputInscriptionMedicale' + pk + 'inscriptionSignature10').jSignature({'height':200}).bind('change', function(e){ patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + pk }], 'setInscriptionSignature10', $('#signatureInputInscriptionMedicale' + pk + 'inscriptionSignature10').jSignature('getData', 'default')); }); ");
		tl(1, "}");
		tl(1, "websocketInscriptionMedicale(websocketInscriptionMedicaleInner);");
		l("});");
	}

	public void htmlFormPageInscriptionMedicale(InscriptionMedicale o) {
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPk("Page");
			o.htmCree("Page");
			o.htmModifie("Page");
			o.htmObjetId("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmArchive("Page");
			o.htmSupprime("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmInscriptionApprouve("Page");
			o.htmInscriptionImmunisations("Page");
			o.htmCustomerProfileId("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmFamilleAddresse("Page");
			o.htmInscriptionConsiderationsSpeciales("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientConditionsMedicales("Page");
			o.htmPatientCliniquesPrecedemmentFrequentees("Page");
			o.htmFamilleCommentVousConnaissezClinique("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientDescription("Page");
			o.htmPatientObjectifs("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientCle("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmUtilisateurCles("Page");
		} g("div");
	}

	public void htmlFormPOSTInscriptionMedicale(InscriptionMedicale o) {
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPk("POST");
			o.htmCree("POST");
			o.htmModifie("POST");
			o.htmObjetId("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmArchive("POST");
			o.htmSupprime("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmInscriptionApprouve("POST");
			o.htmInscriptionImmunisations("POST");
			o.htmCustomerProfileId("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmFamilleAddresse("POST");
			o.htmInscriptionConsiderationsSpeciales("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientConditionsMedicales("POST");
			o.htmPatientCliniquesPrecedemmentFrequentees("POST");
			o.htmFamilleCommentVousConnaissezClinique("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientDescription("POST");
			o.htmPatientObjectifs("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientCle("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmUtilisateurCles("POST");
		} g("div");
	}

	public void htmlFormPUTImportInscriptionMedicale(InscriptionMedicale o) {
		{ e("div").a("class", "w3-cell-row ").f();
			e("textarea")
				.a("class", "PUTImport_liste w3-input w3-border ")
				.a("style", "height: 400px; ")
				.a("placeholder", "{ \"liste\": [ { \"pk\": ... , \"sauvegardes\": [ ... ] }, ... ] }")
				;
				f();
			g("textarea");
		} g("div");
	}

	public void htmlFormPUTFusionInscriptionMedicale(InscriptionMedicale o) {
		{ e("div").a("class", "w3-cell-row ").f();
			e("textarea")
				.a("class", "PUTFusion_liste w3-input w3-border ")
				.a("style", "height: 400px; ")
				.a("placeholder", "{ \"liste\": [ { \"pk\": ... , \"sauvegardes\": [ ... ] }, ... ] }")
				;
				f();
			g("textarea");
		} g("div");
	}

	public void htmlFormPUTCopieInscriptionMedicale(InscriptionMedicale o) {
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmCree("PUTCopie");
			o.htmModifie("PUTCopie");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmArchive("PUTCopie");
			o.htmSupprime("PUTCopie");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmInscriptionApprouve("PUTCopie");
			o.htmInscriptionImmunisations("PUTCopie");
			o.htmCustomerProfileId("PUTCopie");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmFamilleAddresse("PUTCopie");
			o.htmInscriptionConsiderationsSpeciales("PUTCopie");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientConditionsMedicales("PUTCopie");
			o.htmPatientCliniquesPrecedemmentFrequentees("PUTCopie");
			o.htmFamilleCommentVousConnaissezClinique("PUTCopie");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientDescription("PUTCopie");
			o.htmPatientObjectifs("PUTCopie");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientCle("PUTCopie");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmUtilisateurCles("PUTCopie");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmInheritPk("PUTCopie");
			o.htmSessionId("PUTCopie");
			o.htmUtilisateurId("PUTCopie");
			o.htmUtilisateurCle("PUTCopie");
			o.htmPatientNomComplet("PUTCopie");
			o.htmPatientNomCompletPrefere("PUTCopie");
			o.htmPatientDateNaissance("PUTCopie");
			o.htmCliniqueAddresse("PUTCopie");
			o.htmInscriptionSignature1("PUTCopie");
			o.htmInscriptionSignature2("PUTCopie");
			o.htmInscriptionSignature3("PUTCopie");
			o.htmInscriptionSignature4("PUTCopie");
			o.htmInscriptionSignature5("PUTCopie");
			o.htmInscriptionSignature6("PUTCopie");
			o.htmInscriptionSignature7("PUTCopie");
			o.htmInscriptionSignature8("PUTCopie");
			o.htmInscriptionSignature9("PUTCopie");
			o.htmInscriptionSignature10("PUTCopie");
			o.htmInscriptionDate1("PUTCopie");
			o.htmInscriptionDate2("PUTCopie");
			o.htmInscriptionDate3("PUTCopie");
			o.htmInscriptionDate4("PUTCopie");
			o.htmInscriptionDate5("PUTCopie");
			o.htmInscriptionDate6("PUTCopie");
			o.htmInscriptionDate7("PUTCopie");
			o.htmInscriptionDate8("PUTCopie");
			o.htmInscriptionDate9("PUTCopie");
			o.htmInscriptionDate10("PUTCopie");
		} g("div");
	}

	public void htmlFormPATCHInscriptionMedicale(InscriptionMedicale o) {
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmCree("PATCH");
			o.htmModifie("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmArchive("PATCH");
			o.htmSupprime("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmInscriptionApprouve("PATCH");
			o.htmInscriptionImmunisations("PATCH");
			o.htmCustomerProfileId("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmFamilleAddresse("PATCH");
			o.htmInscriptionConsiderationsSpeciales("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientConditionsMedicales("PATCH");
			o.htmPatientCliniquesPrecedemmentFrequentees("PATCH");
			o.htmFamilleCommentVousConnaissezClinique("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientDescription("PATCH");
			o.htmPatientObjectifs("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientCle("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmUtilisateurCles("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmInheritPk("PATCH");
			o.htmSessionId("PATCH");
			o.htmUtilisateurId("PATCH");
			o.htmUtilisateurCle("PATCH");
			o.htmPatientNomComplet("PATCH");
			o.htmPatientNomCompletPrefere("PATCH");
			o.htmPatientDateNaissance("PATCH");
			o.htmCliniqueAddresse("PATCH");
			o.htmInscriptionSignature1("PATCH");
			o.htmInscriptionSignature2("PATCH");
			o.htmInscriptionSignature3("PATCH");
			o.htmInscriptionSignature4("PATCH");
			o.htmInscriptionSignature5("PATCH");
			o.htmInscriptionSignature6("PATCH");
			o.htmInscriptionSignature7("PATCH");
			o.htmInscriptionSignature8("PATCH");
			o.htmInscriptionSignature9("PATCH");
			o.htmInscriptionSignature10("PATCH");
			o.htmInscriptionDate1("PATCH");
			o.htmInscriptionDate2("PATCH");
			o.htmInscriptionDate3("PATCH");
			o.htmInscriptionDate4("PATCH");
			o.htmInscriptionDate5("PATCH");
			o.htmInscriptionDate6("PATCH");
			o.htmInscriptionDate7("PATCH");
			o.htmInscriptionDate8("PATCH");
			o.htmInscriptionDate9("PATCH");
			o.htmInscriptionDate10("PATCH");
		} g("div");
	}

	public void htmlFormRechercheInscriptionMedicale(InscriptionMedicale o) {
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPk("Recherche");
			o.htmCree("Recherche");
			o.htmModifie("Recherche");
			o.htmObjetId("Recherche");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmArchive("Recherche");
			o.htmSupprime("Recherche");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmInscriptionApprouve("Recherche");
			o.htmInscriptionImmunisations("Recherche");
			o.htmCustomerProfileId("Recherche");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmFamilleAddresse("Recherche");
			o.htmInscriptionConsiderationsSpeciales("Recherche");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientConditionsMedicales("Recherche");
			o.htmPatientCliniquesPrecedemmentFrequentees("Recherche");
			o.htmFamilleCommentVousConnaissezClinique("Recherche");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientDescription("Recherche");
			o.htmPatientObjectifs("Recherche");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientCle("Recherche");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmUtilisateurCles("Recherche");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmInheritPk("Recherche");
			o.htmSessionId("Recherche");
			o.htmUtilisateurId("Recherche");
			o.htmUtilisateurCle("Recherche");
			o.htmObjetTitre("Recherche");
			o.htmPatientNomComplet("Recherche");
			o.htmPatientNomCompletPrefere("Recherche");
			o.htmPatientDateNaissance("Recherche");
			o.htmCliniqueAddresse("Recherche");
			o.htmInscriptionDate1("Recherche");
			o.htmInscriptionDate2("Recherche");
			o.htmInscriptionDate3("Recherche");
			o.htmInscriptionDate4("Recherche");
			o.htmInscriptionDate5("Recherche");
			o.htmInscriptionDate6("Recherche");
			o.htmInscriptionDate7("Recherche");
			o.htmInscriptionDate8("Recherche");
			o.htmInscriptionDate9("Recherche");
			o.htmInscriptionDate10("Recherche");
		} g("div");
	}

	@Override public void htmlBodyInscriptionGenPage() {

		OperationRequest operationRequete = requeteSite_.getOperationRequete();
		JsonObject params = operationRequete.getParams();
		if(listeInscriptionMedicale == null || listeInscriptionMedicale.size() == 0) {

			{ e("h1").f();
				{ e("a").a("href", "/inscription").a("class", "w3-bar-item w3-btn w3-center w3-block w3-blue-gray w3-hover-blue-gray ").f();
					if(contexteIconeClassesCss != null)
						e("i").a("class", contexteIconeClassesCss + " site-menu-icon ").f().g("i");
					e("span").a("class", " ").f().sx("inscriptions").g("span");
				} g("a");
			} g("h1");
			e("div").a("class", "w3-padding-16 w3-card-4 w3-light-grey ").f();
			{ e("h2").f();
				{ e("span").a("class", "w3-bar-item w3-padding w3-center w3-block w3-blue-gray ").f();
					if(contexteIconeClassesCss != null)
						e("i").a("class", contexteIconeClassesCss + " site-menu-icon ").f().g("i");
					e("span").a("class", " ").f().sx("aucune inscription trouvée").g("span");
				} g("span");
			} g("h2");
		} else if(listeInscriptionMedicale != null && listeInscriptionMedicale.size() == 1 && params.getJsonObject("query").getString("q").equals("*:*")) {
			InscriptionMedicale o = listeInscriptionMedicale.get(0);
			requeteSite_.setRequetePk(o.getPk());
			if(StringUtils.isNotEmpty(pageH1)) {
				{ e("h1").f();
					{ e("a").a("href", "/inscription").a("class", "w3-bar-item w3-btn w3-center w3-block w3-blue-gray w3-hover-blue-gray ").f();
						if(contexteIconeClassesCss != null)
							e("i").a("class", contexteIconeClassesCss + " site-menu-icon ").f().g("i");
						e("span").a("class", " ").f().sx(pageH1).g("span");
					} g("a");
				} g("h1");
			}
			e("div").a("class", "w3-padding-16 w3-card-4 w3-light-grey ").f();
			if(StringUtils.isNotEmpty(pageH2)) {
				{ e("h2").f();
					{ e("span").a("class", "w3-bar-item w3-padding w3-center w3-block w3-blue-gray ").f();
						e("span").a("class", " ").f().sx(pageH2).g("span");
					} g("span");
				} g("h2");
			}
			if(StringUtils.isNotEmpty(pageH3)) {
				{ e("h3").f();
					{ e("span").a("class", "w3-bar-item w3-padding w3-center w3-block w3-blue-gray ").f();
						e("span").a("class", " ").f().sx(pageH3).g("span");
					} g("span");
				} g("h3");
			}
		} else {

			{ e("h1").f();
				{ e("a").a("href", "/inscription").a("class", "w3-bar-item w3-btn w3-center w3-block w3-blue-gray w3-hover-blue-gray ").f();
					if(contexteIconeClassesCss != null)
						e("i").a("class", contexteIconeClassesCss + " site-menu-icon ").f().g("i");
					e("span").a("class", " ").f().sx(pageH1).g("span");
				} g("a");
			} g("h1");
			e("div").a("class", "").f();
				{ e("div").f();
					JsonObject queryParams = Optional.ofNullable(operationRequete).map(OperationRequest::getParams).map(or -> or.getJsonObject("query")).orElse(new JsonObject());
					Long num = listeInscriptionMedicale.getQueryResponse().getResults().getNumFound();
					String q = "*:*";
					String query1 = "objetTexte";
					String query2 = "";
					String query = "*:*";
					for(String paramNom : queryParams.fieldNames()) {
						String entiteVar = null;
						String valeurIndexe = null;
						Object paramValeursObjet = queryParams.getValue(paramNom);
						JsonArray paramObjets = paramValeursObjet instanceof JsonArray ? (JsonArray)paramValeursObjet : new JsonArray().add(paramValeursObjet);

						try {
							for(Object paramObjet : paramObjets) {
								switch(paramNom) {
									case "q":
										q = (String)paramObjet;
										entiteVar = StringUtils.trim(StringUtils.substringBefore((String)paramObjet, ":"));
										valeurIndexe = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObjet, ":")), "UTF-8");
										query1 = entiteVar.equals("*") ? query1 : entiteVar;
										query2 = valeurIndexe;
										query = query1 + ":" + query2;
								}
							}
						} catch(Exception e) {
							ExceptionUtils.rethrow(e);
						}
					}

					Integer rows1 = Optional.ofNullable(listeInscriptionMedicale).map(l -> l.getRows()).orElse(10);
					Integer start1 = Optional.ofNullable(listeInscriptionMedicale).map(l -> l.getStart()).orElse(1);
					Integer start2 = start1 - rows1;
					Integer start3 = start1 + rows1;
					Integer rows2 = rows1 / 2;
					Integer rows3 = rows1 * 2;
					start2 = start2 < 0 ? 0 : start2;
					StringBuilder fqs = new StringBuilder();
					for(String fq : Optional.ofNullable(listeInscriptionMedicale).map(l -> l.getFilterQueries()).orElse(new String[0])) {
						if(!StringUtils.contains(fq, "(")) {
							String fq1 = StringUtils.substringBefore(fq, "_");
							String fq2 = StringUtils.substringAfter(fq, ":");
							if(!StringUtils.startsWithAny(fq, "classeNomsCanoniques_", "archive_", "supprime_", "sessionId", "utilisateurCles"))
								fqs.append("&fq=").append(fq1).append(":").append(fq2);
						}
					}
					StringBuilder sorts = new StringBuilder();
					for(SortClause sort : Optional.ofNullable(listeInscriptionMedicale).map(l -> l.getSorts()).orElse(Arrays.asList())) {
						sorts.append("&sort=").append(StringUtils.substringBefore(sort.getItem(), "_")).append(" ").append(sort.getOrder().name());
					}

					if(start1 == 0) {
						e("i").a("class", "fas fa-arrow-square-left w3-opacity ").f().g("i");
					} else {
						{ e("a").a("href", "/inscription?q=", query, fqs, sorts, "&start=", start2, "&rows=", rows1).f();
							e("i").a("class", "fas fa-arrow-square-left ").f().g("i");
						} g("a");
					}

					if(rows1 <= 1) {
						e("i").a("class", "fas fa-minus-square w3-opacity ").f().g("i");
					} else {
						{ e("a").a("href", "/inscription?q=", query, fqs, sorts, "&start=", start1, "&rows=", rows2).f();
							e("i").a("class", "fas fa-minus-square ").f().g("i");
						} g("a");
					}

					{ e("a").a("href", "/inscription?q=", query, fqs, sorts, "&start=", start1, "&rows=", rows3).f();
						e("i").a("class", "fas fa-plus-square ").f().g("i");
					} g("a");

					if(start3 >= num) {
						e("i").a("class", "fas fa-arrow-square-right w3-opacity ").f().g("i");
					} else {
						{ e("a").a("href", "/inscription?q=", query, fqs, sorts, "&start=", start3, "&rows=", rows1).f();
							e("i").a("class", "fas fa-arrow-square-right ").f().g("i");
						} g("a");
					}
						e("span").f().sx((start1 + 1), " - ", (start1 + rows1), " de ", num).g("span");
				} g("div");
				table1InscriptionGenPage();
		}

		if(listeInscriptionMedicale != null && listeInscriptionMedicale.size() == 1 && params.getJsonObject("query").getString("q").equals("*:*")) {
			InscriptionMedicale o = listeInscriptionMedicale.first();

			{ e("div").a("class", "").f();

				if(o.getPk() != null) {
					{ e("form").a("action", "").a("id", "InscriptionMedicaleForm").a("style", "display: inline-block; width: 100%; ").a("onsubmit", "event.preventDefault(); return false; ").f();
						e("input")
						.a("name", "pk")
						.a("class", "valeurPk")
						.a("type", "hidden")
						.a("value", o.getPk())
						.fg();
						e("input")
						.a("name", "focusId")
						.a("type", "hidden")
						.fg();
					} g("form");
					htmlFormPageInscriptionMedicale(o);
				}

			} g("div");

		}
		htmlBodyFormsInscriptionGenPage();
		g("div");
	}

	public void table1InscriptionGenPage() {
		{ e("table").a("class", "w3-table w3-bordered w3-striped w3-border w3-hoverable ").f();
			table2InscriptionGenPage();
		} g("table");
	}

	public void table2InscriptionGenPage() {
		thead1InscriptionGenPage();
		tbody1InscriptionGenPage();
		tfoot1InscriptionGenPage();
	}

	public void thead1InscriptionGenPage() {
		{ e("thead").a("class", "w3-blue-gray w3-hover-blue-gray ").f();
			thead2InscriptionGenPage();
		} g("thead");
	}

	public void thead2InscriptionGenPage() {
			{ e("tr").f();
			if(getColonneCree()) {
				e("th").f().sx("crée").g("th");
			}
			if(getColonneObjetTitre()) {
				e("th").f().sx("").g("th");
			}
			} g("tr");
	}

	public void tbody1InscriptionGenPage() {
		{ e("tbody").f();
			tbody2InscriptionGenPage();
		} g("tbody");
	}

	public void tbody2InscriptionGenPage() {
		Map<String, Map<String, List<String>>> highlighting = listeInscriptionMedicale.getQueryResponse().getHighlighting();
		for(int i = 0; i < listeInscriptionMedicale.size(); i++) {
			InscriptionMedicale o = listeInscriptionMedicale.getList().get(i);
			Map<String, List<String>> highlights = highlighting == null ? null : highlighting.get(o.getId());
			List<String> highlightList = highlights == null ? null : highlights.get(highlights.keySet().stream().findFirst().orElse(null));
			String uri = "/inscription/" + o.getPk();
			{ e("tr").f();
				if(getColonneCree()) {
					{ e("td").f();
						{ e("a").a("href", uri).f();
							{ e("span").f();
								sx(o.strCree());
							} g("span");
						} g("a");
					} g("td");
				}
				if(getColonneObjetTitre()) {
					{ e("td").f();
						{ e("a").a("href", uri).f();
							e("i").a("class", "fas fa-edit ").f().g("i");
							{ e("span").f();
								sx(o.strObjetTitre());
							} g("span");
						} g("a");
					} g("td");
				}
			} g("tr");
		}
	}

	public void tfoot1InscriptionGenPage() {
		{ e("tfoot").a("class", "w3-blue-gray w3-hover-blue-gray ").f();
			tfoot2InscriptionGenPage();
		} g("tfoot");
	}

	public void tfoot2InscriptionGenPage() {
		{ e("tr").f();
			SimpleOrderedMap facets = (SimpleOrderedMap)Optional.ofNullable(listeInscriptionMedicale.getQueryResponse()).map(QueryResponse::getResponse).map(r -> r.get("facets")).orElse(new SimpleOrderedMap());
			if(getColonneCree()) {
				e("td").f();
				g("td");
			}
			if(getColonneObjetTitre()) {
				e("td").f();
				g("td");
			}
		} g("tr");
	}

	public Boolean getColonneCree() {
		return true;
	}

	public Boolean getColonneObjetTitre() {
		return true;
	}

	public void htmlBodyFormsInscriptionGenPage() {
		if(
				CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
				) {
			e("div").a("class", "w3-margin-top ").f();

			if(listeInscriptionMedicale != null && listeInscriptionMedicale.size() == 1) {
				{ e("button")
					.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-blue-gray ")
						.a("id", "rechargerCetteInscriptionGenPage")
						.a("onclick", "patchInscriptionMedicaleVals( [ {name: 'fq', value: 'pk:' + " + requeteSite_.getRequetePk() + " } ], {}, function() { ajouterLueur($('#rechargerCetteInscriptionGenPage')); }, function() { ajouterErreur($('#rechargerCetteInscriptionGenPage')); }); return false; ").f();
						e("i").a("class", "fas fa-sync-alt ").f().g("i");
					sx("recharger cette inscription");
				} g("button");
			}

			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-blue-gray ")
				.a("onclick", "$('#postInscriptionMedicaleModale').show(); ")
				.f();
				e("i").a("class", "fas fa-file-plus ").f().g("i");
				sx("Créer une inscription");
			} g("button");
			{ e("div").a("id", "postInscriptionMedicaleModale").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-blue-gray ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#postInscriptionMedicaleModale').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Créer une inscription").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").f();
							InscriptionMedicale o = new InscriptionMedicale();
							o.setRequeteSite_(requeteSite_);

							// Form POST
							{ e("div").a("id", "postInscriptionMedicaleForm").f();
								htmlFormPOSTInscriptionMedicale(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-blue-gray ")
								.a("onclick", "postInscriptionMedicale($('#postInscriptionMedicaleForm')); ")
								.f().sx("Créer une inscription")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-blue-gray ")
				.a("onclick", "$('#putimportInscriptionMedicaleModale').show(); ")
				.f();
				e("i").a("class", "fas fa-file-import ").f().g("i");
				sx("Importer inscriptions");
			} g("button");
			{ e("div").a("id", "putimportInscriptionMedicaleModale").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-blue-gray ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#putimportInscriptionMedicaleModale').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Importer inscriptions").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").f();
							InscriptionMedicale o = new InscriptionMedicale();
							o.setRequeteSite_(requeteSite_);

							// Form PUT
							{ e("div").a("id", "putimportInscriptionMedicaleForm").f();
								htmlFormPUTImportInscriptionMedicale(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-blue-gray ")
								.a("onclick", "putimportInscriptionMedicale($('#putimportInscriptionMedicaleForm')); ")
								.f().sx("Importer inscriptions")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-blue-gray ")
				.a("onclick", "$('#putfusionInscriptionMedicaleModale').show(); ")
				.f();
				e("i").a("class", "fas fa-code-merge ").f().g("i");
				sx("Fusionner inscriptions");
			} g("button");
			{ e("div").a("id", "putfusionInscriptionMedicaleModale").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-blue-gray ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#putfusionInscriptionMedicaleModale').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Fusionner inscriptions").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").f();
							InscriptionMedicale o = new InscriptionMedicale();
							o.setRequeteSite_(requeteSite_);

							// Form PUT
							{ e("div").a("id", "putfusionInscriptionMedicaleForm").f();
								htmlFormPUTFusionInscriptionMedicale(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-blue-gray ")
								.a("onclick", "putfusionInscriptionMedicale($('#putfusionInscriptionMedicaleForm')); ")
								.f().sx("Fusionner inscriptions")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-blue-gray ")
				.a("onclick", "$('#putcopieInscriptionMedicaleModale').show(); ")
				.f();
				e("i").a("class", "fas fa-copy ").f().g("i");
				sx("Dupliquer inscriptions");
			} g("button");
			{ e("div").a("id", "putcopieInscriptionMedicaleModale").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-blue-gray ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#putcopieInscriptionMedicaleModale').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Dupliquer inscriptions").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").f();
							InscriptionMedicale o = new InscriptionMedicale();
							o.setRequeteSite_(requeteSite_);

							// Form PUT
							{ e("div").a("id", "putcopieInscriptionMedicaleForm").f();
								htmlFormPUTCopieInscriptionMedicale(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-blue-gray ")
								.a("onclick", "putcopieInscriptionMedicale($('#putcopieInscriptionMedicaleForm'), ", inscriptionMedicale == null ? "null" : inscriptionMedicale.getPk(), "); ")
								.f().sx("Dupliquer inscriptions")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-blue-gray ")
				.a("onclick", "$('#patchInscriptionMedicaleModale').show(); ")
				.f();
				e("i").a("class", "fas fa-edit ").f().g("i");
				sx("Modifier inscriptions");
			} g("button");
			{ e("div").a("id", "patchInscriptionMedicaleModale").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-blue-gray ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#patchInscriptionMedicaleModale').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Modifier inscriptions").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").f();
							InscriptionMedicale o = new InscriptionMedicale();
							o.setRequeteSite_(requeteSite_);

							// FormulaireValeurs PATCH
							{ e("form").a("action", "").a("id", "patchInscriptionMedicaleFormulaireValeurs").a("onsubmit", "event.preventDefault(); return false; ").f();
								htmlFormPATCHInscriptionMedicale(o);
							} g("form");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-blue-gray ")
								.a("onclick", "patchInscriptionMedicale(null, $('#patchInscriptionMedicaleFormulaireValeurs'), ", Optional.ofNullable(inscriptionMedicale).map(InscriptionMedicale::getPk).map(a -> a.toString()).orElse("null"), ", function() {}, function() {}); ")
								.f().sx("Modifier inscriptions")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");

			g("div");
		}
		htmlSuggereInscriptionGenPage(this, null, listeInscriptionMedicale);
	}

	/**
	 * Var.enUS: htmlSuggestedEnrollmentGenPage
	 * r: "/inscription"
	 * r.enUS: "/enrollment"
	 * r: "voir toutes les inscriptions"
	 * r.enUS: "see all the enrollments"
	 * r: "rechargerInscriptionGenPage"
	 * r.enUS: "refreshEnrollmentGenPage"
	 * r: "recharger toutes les inscriptions"
	 * r.enUS: "refresh all the enrollments"
	 * r: "rechercher inscriptions : "
	 * r.enUS: "search enrollments: "
	 * r: "suggereFormInscriptionMedicale"
	 * r.enUS: "suggestFormMedicalEnrollment"
	 * r: "rechercher inscriptions"
	 * r.enUS: "search enrollments"
	 * r: "suggereInscriptionMedicale w3-input w3-border w3-cell w3-cell-middle "
	 * r.enUS: "suggestMedicalEnrollment w3-input w3-border w3-cell w3-cell-middle "
	 * r: "suggereInscriptionMedicale"
	 * r.enUS: "suggestMedicalEnrollment"
	 * r: patchInscriptionMedicaleVals
	 * r.enUS: patchMedicalEnrollmentVals
	 * r: ajouterLueur
	 * r.enUS: addGlow
	 * r: rechargerInscriptionGenPage
	 * r.enUS: refreshEnrollmentGenPage
	 * r: ajouterErreur
	 * r.enUS: addError
	 * r: suggereInscriptionMedicaleObjetSuggere
	 * r.enUS: suggestMedicalEnrollmentObjectSuggest
	 * r: texteInscriptionMedicaleObjetTexte
	 * r.enUS: textMedicalEnrollmentObjectText
	 * r: 'objetSuggere:'
	 * r.enUS: 'objectSuggest:'
	 * r: 'objetTexte:'
	 * r.enUS: 'objectText:'
	 * r: '#suggereListInscriptionMedicale'
	 * r.enUS: '#suggestListMedicalEnrollment'
	 * r: "suggereListInscriptionMedicale"
	 * r.enUS: "suggestListMedicalEnrollment"
	**/
	public static void htmlSuggereInscriptionGenPage(MiseEnPage p, String id, ListeRecherche<InscriptionMedicale> listeInscriptionMedicale) {
		RequeteSiteFrFR requeteSite_ = p.getRequeteSite_();
		try {
			OperationRequest operationRequete = requeteSite_.getOperationRequete();
			JsonObject queryParams = Optional.ofNullable(operationRequete).map(OperationRequest::getParams).map(or -> or.getJsonObject("query")).orElse(new JsonObject());
			String q = "*:*";
			String query1 = "objetTexte";
			String query2 = "";
			for(String paramNom : queryParams.fieldNames()) {
				String entiteVar = null;
				String valeurIndexe = null;
				Object paramValeursObjet = queryParams.getValue(paramNom);
				JsonArray paramObjets = paramValeursObjet instanceof JsonArray ? (JsonArray)paramValeursObjet : new JsonArray().add(paramValeursObjet);

				try {
					for(Object paramObjet : paramObjets) {
						switch(paramNom) {
							case "q":
								q = (String)paramObjet;
								entiteVar = StringUtils.trim(StringUtils.substringBefore((String)paramObjet, ":"));
								valeurIndexe = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObjet, ":")), "UTF-8");
								query1 = entiteVar.equals("*") ? query1 : entiteVar;
								query2 = valeurIndexe.equals("*") ? "" : valeurIndexe;
						}
					}
				} catch(Exception e) {
					ExceptionUtils.rethrow(e);
				}
			}

			Integer rows1 = Optional.ofNullable(listeInscriptionMedicale).map(l -> l.getRows()).orElse(10);
			Integer start1 = Optional.ofNullable(listeInscriptionMedicale).map(l -> l.getStart()).orElse(1);
			Integer start2 = start1 - rows1;
			Integer start3 = start1 + rows1;
			Integer rows2 = rows1 / 2;
			Integer rows3 = rows1 * 2;
			start2 = start2 < 0 ? 0 : start2;
			StringBuilder fqs = new StringBuilder();
			for(String fq : Optional.ofNullable(listeInscriptionMedicale).map(l -> l.getFilterQueries()).orElse(new String[0])) {
				if(!StringUtils.contains(fq, "(")) {
					String fq1 = StringUtils.substringBefore(fq, "_");
					String fq2 = StringUtils.substringAfter(fq, ":");
					if(!StringUtils.startsWithAny(fq, "classeNomsCanoniques_", "archive_", "supprime_", "sessionId", "utilisateurCles"))
						fqs.append("&fq=").append(fq1).append(":").append(fq2);
				}
			}
			StringBuilder sorts = new StringBuilder();
			for(SortClause sort : Optional.ofNullable(listeInscriptionMedicale).map(l -> l.getSorts()).orElse(Arrays.asList())) {
				sorts.append("&sort=").append(StringUtils.substringBefore(sort.getItem(), "_")).append(" ").append(sort.getOrder().name());
			}

			if(
					CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), InscriptionGenPage.ROLES)
					|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), InscriptionGenPage.ROLES)
					) {
					{ p.e("div").a("class", "").f();
						{ p.e("button").a("id", "rechargerToutesInscriptionGenPage", id).a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-blue-gray ").a("onclick", "patchInscriptionMedicaleVals([], {}, function() { ajouterLueur($('#rechargerToutesInscriptionGenPage", id, "')); }, function() { ajouterErreur($('#rechargerToutesInscriptionGenPage", id, "')); }); ").f();
							p.e("i").a("class", "fas fa-sync-alt ").f().g("i");
							p.sx("recharger toutes les inscriptions");
						} p.g("button");
					} p.g("div");
			}
			{ p.e("div").a("class", "w3-cell-row ").f();
				{ p.e("div").a("class", "w3-cell ").f();
					{ p.e("span").f();
						p.sx("rechercher inscriptions : ");
					} p.g("span");
				} p.g("div");
			} p.g("div");
			{ p.e("div").a("class", "w3-bar ").f();

				p.e("input")
					.a("type", "text")
					.a("placeholder", "rechercher inscriptions")
					.a("class", "suggereInscriptionMedicale w3-input w3-border w3-bar-item ")
					.a("name", "suggereInscriptionMedicale")
					.a("id", "suggereInscriptionMedicale", id)
					.a("autocomplete", "off")
					.a("oninput", "suggereInscriptionMedicaleObjetSuggere( [ { 'name': 'q', 'value': 'objetSuggere:' + $(this).val() } ], $('#suggereListInscriptionMedicale", id, "'), ", p.getRequeteSite_().getRequetePk(), "); ")
					.a("onkeyup", "if (event.keyCode === 13) { event.preventDefault(); window.location.href = '/inscription?q=", query1, ":' + encodeURIComponent(this.value) + '", fqs, sorts, "&start=", start2, "&rows=", rows1, "'; }"); 
				if(listeInscriptionMedicale != null)
					p.a("value", query2);
				p.fg();
				{ p.e("button")
					.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
					.a("onclick", "window.location.href = '/inscription?q=", query1, ":' + encodeURIComponent(this.previousElementSibling.value) + '", fqs, sorts, "&start=", start2, "&rows=", rows1, "'; ") 
					.f();
					p.e("i").a("class", "fas fa-search ").f().g("i");
				} p.g("button");

			} p.g("div");
			{ p.e("div").a("class", "w3-cell-row ").f();
				{ p.e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
					{ p.e("ul").a("class", "w3-ul w3-hoverable ").a("id", "suggereListInscriptionMedicale", id).f();
					} p.g("ul");
				} p.g("div");
			} p.g("div");
			{ p.e("div").a("class", "").f();
				{ p.e("a").a("href", "/inscription").a("class", "").f();
					p.e("i").a("class", "fas fa-edit ").f().g("i");
					p.sx("voir toutes les inscriptions");
				} p.g("a");
			} p.g("div");
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

}
