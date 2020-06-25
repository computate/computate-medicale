package org.computate.medicale.frFR.patient;

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
 * NomCanonique.enUS: org.computate.medicale.enUS.patient.PatientGenPage
 **/
public class PatientGenPage extends PatientGenPageGen<ClusterPage> {

	public static final List<String> ROLES = Arrays.asList("SiteAdmin");
	public static final List<String> ROLE_READS = Arrays.asList("");

	/**
	 * {@inheritDoc}
	 * 
	 **/
	protected void _listePatientMedicale(Couverture<ListeRecherche<PatientMedicale>> c) {
	}

	protected void _patientMedicale(Couverture<PatientMedicale> c) {
		if(listePatientMedicale != null && listePatientMedicale.size() == 1)
			c.o(listePatientMedicale.get(0));
	}

	@Override protected void _pageH1(Couverture<String> c) {
			c.o("patients");
	}

	@Override protected void _pageH2(Couverture<String> c) {
		if(patientMedicale != null && patientMedicale.getPatientNomComplet() != null)
			c.o(patientMedicale.getPatientNomComplet());
	}

	@Override protected void _pageH3(Couverture<String> c) {
		c.o("");
	}

	@Override protected void _pageTitre(Couverture<String> c) {
		if(patientMedicale != null && patientMedicale.getPatientNomComplet() != null)
			c.o(patientMedicale.getPatientNomComplet());
		else if(patientMedicale != null)
			c.o("");
		else if(listePatientMedicale == null || listePatientMedicale.size() == 0)
			c.o("aucun patient trouvé");
	}

	@Override protected void _pageUri(Couverture<String> c) {
		c.o("/patient");
	}

	@Override protected void _pageImageUri(Couverture<String> c) {
			c.o("/png/patient-999.png");
	}

	@Override protected void _contexteIconeGroupe(Couverture<String> c) {
			c.o("regular");
	}

	@Override protected void _contexteIconeNom(Couverture<String> c) {
			c.o("hospital-user");
	}

	@Override public void initLoinPatientGenPage() {
		initPatientGenPage();
		super.initLoinMiseEnPage();
	}

	@Override public void htmlScriptsPatientGenPage() {
		e("script").a("src", statiqueUrlBase, "/js/frFR/PatientPage.js").f().g("script");
		e("script").a("src", statiqueUrlBase, "/js/frFR/InscriptionPage.js").f().g("script");
	}

	@Override public void htmlScriptPatientGenPage() {
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
			tl(2, "suggerePatientMedicaleInscriptionCles([{'name':'fq','value':'patientCle:' + pk}], $('#listPatientMedicaleInscriptionCles_Page'), pk, true); ");
		} else {
			tl(2, "suggerePatientMedicaleInscriptionCles([{'name':'fq','value':'patientCle:' + pk}], $('#listPatientMedicaleInscriptionCles_Page'), pk, false); ");
		}
		tl(1, "}");
		tl(1, "websocketPatientMedicale(websocketPatientMedicaleInner);");
		l("});");
	}

	public void htmlFormPagePatientMedicale(PatientMedicale o) {
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
			o.htmPatientPrenom("Page");
			o.htmFamilleNom("Page");
			o.htmPatientPrenomPrefere("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientDateNaissance("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmInscriptionCles("Page");
		} g("div");
	}

	public void htmlFormPOSTPatientMedicale(PatientMedicale o) {
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
			o.htmPatientPrenom("POST");
			o.htmFamilleNom("POST");
			o.htmPatientPrenomPrefere("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientDateNaissance("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmInscriptionCles("POST");
		} g("div");
	}

	public void htmlFormPUTImportPatientMedicale(PatientMedicale o) {
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

	public void htmlFormPUTFusionPatientMedicale(PatientMedicale o) {
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

	public void htmlFormPUTCopiePatientMedicale(PatientMedicale o) {
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmCree("PUTCopie");
			o.htmModifie("PUTCopie");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmArchive("PUTCopie");
			o.htmSupprime("PUTCopie");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientPrenom("PUTCopie");
			o.htmFamilleNom("PUTCopie");
			o.htmPatientPrenomPrefere("PUTCopie");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientDateNaissance("PUTCopie");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmInscriptionCles("PUTCopie");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmInheritPk("PUTCopie");
			o.htmSessionId("PUTCopie");
			o.htmUtilisateurId("PUTCopie");
			o.htmUtilisateurCle("PUTCopie");
		} g("div");
	}

	public void htmlFormPATCHPatientMedicale(PatientMedicale o) {
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmCree("PATCH");
			o.htmModifie("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmArchive("PATCH");
			o.htmSupprime("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientPrenom("PATCH");
			o.htmFamilleNom("PATCH");
			o.htmPatientPrenomPrefere("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientDateNaissance("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmInscriptionCles("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmInheritPk("PATCH");
			o.htmSessionId("PATCH");
			o.htmUtilisateurId("PATCH");
			o.htmUtilisateurCle("PATCH");
		} g("div");
	}

	public void htmlFormRecherchePatientMedicale(PatientMedicale o) {
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
			o.htmPatientPrenom("Recherche");
			o.htmFamilleNom("Recherche");
			o.htmPatientPrenomPrefere("Recherche");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientDateNaissance("Recherche");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmInscriptionCles("Recherche");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmInheritPk("Recherche");
			o.htmSessionId("Recherche");
			o.htmUtilisateurId("Recherche");
			o.htmUtilisateurCle("Recherche");
			o.htmObjetTitre("Recherche");
		} g("div");
	}

	@Override public void htmlBodyPatientGenPage() {

		OperationRequest operationRequete = requeteSite_.getOperationRequete();
		JsonObject params = operationRequete.getParams();
		if(listePatientMedicale == null || listePatientMedicale.size() == 0) {

			{ e("h1").f();
				{ e("a").a("href", "/patient").a("class", "w3-bar-item w3-btn w3-center w3-block w3-orange w3-hover-orange ").f();
					if(contexteIconeClassesCss != null)
						e("i").a("class", contexteIconeClassesCss + " site-menu-icon ").f().g("i");
					e("span").a("class", " ").f().sx("patients").g("span");
				} g("a");
			} g("h1");
			e("div").a("class", "w3-padding-16 w3-card-4 w3-light-grey ").f();
			{ e("h2").f();
				{ e("span").a("class", "w3-bar-item w3-padding w3-center w3-block w3-orange ").f();
					if(contexteIconeClassesCss != null)
						e("i").a("class", contexteIconeClassesCss + " site-menu-icon ").f().g("i");
					e("span").a("class", " ").f().sx("aucun patient trouvé").g("span");
				} g("span");
			} g("h2");
		} else if(listePatientMedicale != null && listePatientMedicale.size() == 1 && params.getJsonObject("query").getString("q").equals("*:*")) {
			PatientMedicale o = listePatientMedicale.get(0);
			requeteSite_.setRequetePk(o.getPk());
			if(StringUtils.isNotEmpty(pageH1)) {
				{ e("h1").f();
					{ e("a").a("href", "/patient").a("class", "w3-bar-item w3-btn w3-center w3-block w3-orange w3-hover-orange ").f();
						if(contexteIconeClassesCss != null)
							e("i").a("class", contexteIconeClassesCss + " site-menu-icon ").f().g("i");
						e("span").a("class", " ").f().sx(pageH1).g("span");
					} g("a");
				} g("h1");
			}
			e("div").a("class", "w3-padding-16 w3-card-4 w3-light-grey ").f();
			if(StringUtils.isNotEmpty(pageH2)) {
				{ e("h2").f();
					{ e("span").a("class", "w3-bar-item w3-padding w3-center w3-block w3-orange ").f();
						e("span").a("class", " ").f().sx(pageH2).g("span");
					} g("span");
				} g("h2");
			}
			if(StringUtils.isNotEmpty(pageH3)) {
				{ e("h3").f();
					{ e("span").a("class", "w3-bar-item w3-padding w3-center w3-block w3-orange ").f();
						e("span").a("class", " ").f().sx(pageH3).g("span");
					} g("span");
				} g("h3");
			}
		} else {

			{ e("h1").f();
				{ e("a").a("href", "/patient").a("class", "w3-bar-item w3-btn w3-center w3-block w3-orange w3-hover-orange ").f();
					if(contexteIconeClassesCss != null)
						e("i").a("class", contexteIconeClassesCss + " site-menu-icon ").f().g("i");
					e("span").a("class", " ").f().sx(pageH1).g("span");
				} g("a");
			} g("h1");
			e("div").a("class", "").f();
				{ e("div").f();
					JsonObject queryParams = Optional.ofNullable(operationRequete).map(OperationRequest::getParams).map(or -> or.getJsonObject("query")).orElse(new JsonObject());
					Long num = listePatientMedicale.getQueryResponse().getResults().getNumFound();
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

					Integer rows1 = Optional.ofNullable(listePatientMedicale).map(l -> l.getRows()).orElse(10);
					Integer start1 = Optional.ofNullable(listePatientMedicale).map(l -> l.getStart()).orElse(1);
					Integer start2 = start1 - rows1;
					Integer start3 = start1 + rows1;
					Integer rows2 = rows1 / 2;
					Integer rows3 = rows1 * 2;
					start2 = start2 < 0 ? 0 : start2;
					StringBuilder fqs = new StringBuilder();
					for(String fq : Optional.ofNullable(listePatientMedicale).map(l -> l.getFilterQueries()).orElse(new String[0])) {
						if(!StringUtils.contains(fq, "(")) {
							String fq1 = StringUtils.substringBefore(fq, "_");
							String fq2 = StringUtils.substringAfter(fq, ":");
							if(!StringUtils.startsWithAny(fq, "classeNomsCanoniques_", "archive_", "supprime_", "sessionId", "utilisateurCles"))
								fqs.append("&fq=").append(fq1).append(":").append(fq2);
						}
					}
					StringBuilder sorts = new StringBuilder();
					for(SortClause sort : Optional.ofNullable(listePatientMedicale).map(l -> l.getSorts()).orElse(Arrays.asList())) {
						sorts.append("&sort=").append(StringUtils.substringBefore(sort.getItem(), "_")).append(" ").append(sort.getOrder().name());
					}

					if(start1 == 0) {
						e("i").a("class", "fas fa-arrow-square-left w3-opacity ").f().g("i");
					} else {
						{ e("a").a("href", "/patient?q=", query, fqs, sorts, "&start=", start2, "&rows=", rows1).f();
							e("i").a("class", "fas fa-arrow-square-left ").f().g("i");
						} g("a");
					}

					if(rows1 <= 1) {
						e("i").a("class", "fas fa-minus-square w3-opacity ").f().g("i");
					} else {
						{ e("a").a("href", "/patient?q=", query, fqs, sorts, "&start=", start1, "&rows=", rows2).f();
							e("i").a("class", "fas fa-minus-square ").f().g("i");
						} g("a");
					}

					{ e("a").a("href", "/patient?q=", query, fqs, sorts, "&start=", start1, "&rows=", rows3).f();
						e("i").a("class", "fas fa-plus-square ").f().g("i");
					} g("a");

					if(start3 >= num) {
						e("i").a("class", "fas fa-arrow-square-right w3-opacity ").f().g("i");
					} else {
						{ e("a").a("href", "/patient?q=", query, fqs, sorts, "&start=", start3, "&rows=", rows1).f();
							e("i").a("class", "fas fa-arrow-square-right ").f().g("i");
						} g("a");
					}
						e("span").f().sx((start1 + 1), " - ", (start1 + rows1), " de ", num).g("span");
				} g("div");
				table1PatientGenPage();
		}

		if(listePatientMedicale != null && listePatientMedicale.size() == 1 && params.getJsonObject("query").getString("q").equals("*:*")) {
			PatientMedicale o = listePatientMedicale.first();

			{ e("div").a("class", "").f();

				if(o.getPk() != null) {
					{ e("form").a("action", "").a("id", "PatientMedicaleForm").a("style", "display: inline-block; width: 100%; ").a("onsubmit", "event.preventDefault(); return false; ").f();
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
					htmlFormPagePatientMedicale(o);
				}

			} g("div");

		}
		htmlBodyFormsPatientGenPage();
		g("div");
	}

	public void table1PatientGenPage() {
		{ e("table").a("class", "w3-table w3-bordered w3-striped w3-border w3-hoverable ").f();
			table2PatientGenPage();
		} g("table");
	}

	public void table2PatientGenPage() {
		thead1PatientGenPage();
		tbody1PatientGenPage();
		tfoot1PatientGenPage();
	}

	public void thead1PatientGenPage() {
		{ e("thead").a("class", "w3-orange w3-hover-orange ").f();
			thead2PatientGenPage();
		} g("thead");
	}

	public void thead2PatientGenPage() {
			{ e("tr").f();
			if(getColonneCree()) {
				e("th").f().sx("crée").g("th");
			}
			if(getColonneObjetTitre()) {
				e("th").f().sx("").g("th");
			}
			} g("tr");
	}

	public void tbody1PatientGenPage() {
		{ e("tbody").f();
			tbody2PatientGenPage();
		} g("tbody");
	}

	public void tbody2PatientGenPage() {
		Map<String, Map<String, List<String>>> highlighting = listePatientMedicale.getQueryResponse().getHighlighting();
		for(int i = 0; i < listePatientMedicale.size(); i++) {
			PatientMedicale o = listePatientMedicale.getList().get(i);
			Map<String, List<String>> highlights = highlighting == null ? null : highlighting.get(o.getId());
			List<String> highlightList = highlights == null ? null : highlights.get(highlights.keySet().stream().findFirst().orElse(null));
			String uri = "/patient/" + o.getPk();
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
							e("i").a("class", "far fa-hospital-user ").f().g("i");
							{ e("span").f();
								sx(o.strObjetTitre());
							} g("span");
						} g("a");
					} g("td");
				}
			} g("tr");
		}
	}

	public void tfoot1PatientGenPage() {
		{ e("tfoot").a("class", "w3-orange w3-hover-orange ").f();
			tfoot2PatientGenPage();
		} g("tfoot");
	}

	public void tfoot2PatientGenPage() {
		{ e("tr").f();
			SimpleOrderedMap facets = (SimpleOrderedMap)Optional.ofNullable(listePatientMedicale.getQueryResponse()).map(QueryResponse::getResponse).map(r -> r.get("facets")).orElse(new SimpleOrderedMap());
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

	public void htmlBodyFormsPatientGenPage() {
		if(
				CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
				) {
			e("div").a("class", "w3-margin-top ").f();

			if(listePatientMedicale != null && listePatientMedicale.size() == 1) {
				{ e("button")
					.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-orange ")
						.a("id", "rechargerCePatientGenPage")
						.a("onclick", "patchPatientMedicaleVals( [ {name: 'fq', value: 'pk:' + " + requeteSite_.getRequetePk() + " } ], {}, function() { ajouterLueur($('#rechargerCePatientGenPage')); }, function() { ajouterErreur($('#rechargerCePatientGenPage')); }); return false; ").f();
						e("i").a("class", "fas fa-sync-alt ").f().g("i");
					sx("recharger ce patient");
				} g("button");
			}

			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-orange ")
				.a("onclick", "$('#postPatientMedicaleModale').show(); ")
				.f();
				e("i").a("class", "fas fa-file-plus ").f().g("i");
				sx("Créer un patient");
			} g("button");
			{ e("div").a("id", "postPatientMedicaleModale").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-orange ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#postPatientMedicaleModale').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Créer un patient").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").f();
							PatientMedicale o = new PatientMedicale();
							o.setRequeteSite_(requeteSite_);

							// Form POST
							{ e("div").a("id", "postPatientMedicaleForm").f();
								htmlFormPOSTPatientMedicale(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-orange ")
								.a("onclick", "postPatientMedicale($('#postPatientMedicaleForm')); ")
								.f().sx("Créer un patient")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-orange ")
				.a("onclick", "$('#putimportPatientMedicaleModale').show(); ")
				.f();
				e("i").a("class", "fas fa-file-import ").f().g("i");
				sx("Importer patients");
			} g("button");
			{ e("div").a("id", "putimportPatientMedicaleModale").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-orange ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#putimportPatientMedicaleModale').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Importer patients").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").f();
							PatientMedicale o = new PatientMedicale();
							o.setRequeteSite_(requeteSite_);

							// Form PUT
							{ e("div").a("id", "putimportPatientMedicaleForm").f();
								htmlFormPUTImportPatientMedicale(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-orange ")
								.a("onclick", "putimportPatientMedicale($('#putimportPatientMedicaleForm')); ")
								.f().sx("Importer patients")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-orange ")
				.a("onclick", "$('#putfusionPatientMedicaleModale').show(); ")
				.f();
				e("i").a("class", "fas fa-code-merge ").f().g("i");
				sx("Fusionner patients");
			} g("button");
			{ e("div").a("id", "putfusionPatientMedicaleModale").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-orange ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#putfusionPatientMedicaleModale').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Fusionner patients").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").f();
							PatientMedicale o = new PatientMedicale();
							o.setRequeteSite_(requeteSite_);

							// Form PUT
							{ e("div").a("id", "putfusionPatientMedicaleForm").f();
								htmlFormPUTFusionPatientMedicale(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-orange ")
								.a("onclick", "putfusionPatientMedicale($('#putfusionPatientMedicaleForm')); ")
								.f().sx("Fusionner patients")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-orange ")
				.a("onclick", "$('#putcopiePatientMedicaleModale').show(); ")
				.f();
				e("i").a("class", "fas fa-copy ").f().g("i");
				sx("Dupliquer patients");
			} g("button");
			{ e("div").a("id", "putcopiePatientMedicaleModale").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-orange ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#putcopiePatientMedicaleModale').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Dupliquer patients").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").f();
							PatientMedicale o = new PatientMedicale();
							o.setRequeteSite_(requeteSite_);

							// Form PUT
							{ e("div").a("id", "putcopiePatientMedicaleForm").f();
								htmlFormPUTCopiePatientMedicale(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-orange ")
								.a("onclick", "putcopiePatientMedicale($('#putcopiePatientMedicaleForm'), ", patientMedicale == null ? "null" : patientMedicale.getPk(), "); ")
								.f().sx("Dupliquer patients")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-orange ")
				.a("onclick", "$('#patchPatientMedicaleModale').show(); ")
				.f();
				e("i").a("class", "fas fa-edit ").f().g("i");
				sx("Modifier patients");
			} g("button");
			{ e("div").a("id", "patchPatientMedicaleModale").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-orange ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#patchPatientMedicaleModale').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Modifier patients").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").f();
							PatientMedicale o = new PatientMedicale();
							o.setRequeteSite_(requeteSite_);

							// FormulaireValeurs PATCH
							{ e("form").a("action", "").a("id", "patchPatientMedicaleFormulaireValeurs").a("onsubmit", "event.preventDefault(); return false; ").f();
								htmlFormPATCHPatientMedicale(o);
							} g("form");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-orange ")
								.a("onclick", "patchPatientMedicale(null, $('#patchPatientMedicaleFormulaireValeurs'), ", Optional.ofNullable(patientMedicale).map(PatientMedicale::getPk).map(a -> a.toString()).orElse("null"), ", function() {}, function() {}); ")
								.f().sx("Modifier patients")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");

			g("div");
		}
		htmlSuggerePatientGenPage(this, null, listePatientMedicale);
	}

	/**
	 * Var.enUS: htmlSuggestedPatientGenPage
	 * r: "/patient"
	 * r.enUS: "/patient"
	 * r: "voir tous les patients"
	 * r.enUS: "see all the patients"
	 * r: "rechargerPatientGenPage"
	 * r.enUS: "refreshPatientGenPage"
	 * r: "recharger tous les patients"
	 * r.enUS: "refresh all the patients"
	 * r: "rechercher patients : "
	 * r.enUS: "search patients: "
	 * r: "suggereFormPatientMedicale"
	 * r.enUS: "suggestFormMedicalPatient"
	 * r: "rechercher patients"
	 * r.enUS: "search patients"
	 * r: "suggerePatientMedicale w3-input w3-border w3-cell w3-cell-middle "
	 * r.enUS: "suggestMedicalPatient w3-input w3-border w3-cell w3-cell-middle "
	 * r: "suggerePatientMedicale"
	 * r.enUS: "suggestMedicalPatient"
	 * r: patchPatientMedicaleVals
	 * r.enUS: patchMedicalPatientVals
	 * r: ajouterLueur
	 * r.enUS: addGlow
	 * r: rechargerPatientGenPage
	 * r.enUS: refreshPatientGenPage
	 * r: ajouterErreur
	 * r.enUS: addError
	 * r: suggerePatientMedicaleObjetSuggere
	 * r.enUS: suggestMedicalPatientObjectSuggest
	 * r: textePatientMedicaleObjetTexte
	 * r.enUS: textMedicalPatientObjectText
	 * r: 'objetSuggere:'
	 * r.enUS: 'objectSuggest:'
	 * r: 'objetTexte:'
	 * r.enUS: 'objectText:'
	 * r: '#suggereListPatientMedicale'
	 * r.enUS: '#suggestListMedicalPatient'
	 * r: "suggereListPatientMedicale"
	 * r.enUS: "suggestListMedicalPatient"
	**/
	public static void htmlSuggerePatientGenPage(MiseEnPage p, String id, ListeRecherche<PatientMedicale> listePatientMedicale) {
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

			Integer rows1 = Optional.ofNullable(listePatientMedicale).map(l -> l.getRows()).orElse(10);
			Integer start1 = Optional.ofNullable(listePatientMedicale).map(l -> l.getStart()).orElse(1);
			Integer start2 = start1 - rows1;
			Integer start3 = start1 + rows1;
			Integer rows2 = rows1 / 2;
			Integer rows3 = rows1 * 2;
			start2 = start2 < 0 ? 0 : start2;
			StringBuilder fqs = new StringBuilder();
			for(String fq : Optional.ofNullable(listePatientMedicale).map(l -> l.getFilterQueries()).orElse(new String[0])) {
				if(!StringUtils.contains(fq, "(")) {
					String fq1 = StringUtils.substringBefore(fq, "_");
					String fq2 = StringUtils.substringAfter(fq, ":");
					if(!StringUtils.startsWithAny(fq, "classeNomsCanoniques_", "archive_", "supprime_", "sessionId", "utilisateurCles"))
						fqs.append("&fq=").append(fq1).append(":").append(fq2);
				}
			}
			StringBuilder sorts = new StringBuilder();
			for(SortClause sort : Optional.ofNullable(listePatientMedicale).map(l -> l.getSorts()).orElse(Arrays.asList())) {
				sorts.append("&sort=").append(StringUtils.substringBefore(sort.getItem(), "_")).append(" ").append(sort.getOrder().name());
			}

			if(
					CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), PatientGenPage.ROLES)
					|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), PatientGenPage.ROLES)
					) {
					{ p.e("div").a("class", "").f();
						{ p.e("button").a("id", "rechargerTousPatientGenPage", id).a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-orange ").a("onclick", "patchPatientMedicaleVals([], {}, function() { ajouterLueur($('#rechargerTousPatientGenPage", id, "')); }, function() { ajouterErreur($('#rechargerTousPatientGenPage", id, "')); }); ").f();
							p.e("i").a("class", "fas fa-sync-alt ").f().g("i");
							p.sx("recharger tous les patients");
						} p.g("button");
					} p.g("div");
			}
			{ p.e("div").a("class", "w3-cell-row ").f();
				{ p.e("div").a("class", "w3-cell ").f();
					{ p.e("span").f();
						p.sx("rechercher patients : ");
					} p.g("span");
				} p.g("div");
			} p.g("div");
			{ p.e("div").a("class", "w3-bar ").f();

				p.e("input")
					.a("type", "text")
					.a("placeholder", "rechercher patients")
					.a("class", "suggerePatientMedicale w3-input w3-border w3-bar-item ")
					.a("name", "suggerePatientMedicale")
					.a("id", "suggerePatientMedicale", id)
					.a("autocomplete", "off")
					.a("oninput", "suggerePatientMedicaleObjetSuggere( [ { 'name': 'q', 'value': 'objetSuggere:' + $(this).val() } ], $('#suggereListPatientMedicale", id, "'), ", p.getRequeteSite_().getRequetePk(), "); ")
					.a("onkeyup", "if (event.keyCode === 13) { event.preventDefault(); window.location.href = '/patient?q=", query1, ":' + encodeURIComponent(this.value) + '", fqs, sorts, "&start=", start2, "&rows=", rows1, "'; }"); 
				if(listePatientMedicale != null)
					p.a("value", query2);
				p.fg();
				{ p.e("button")
					.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-orange ")
					.a("onclick", "window.location.href = '/patient?q=", query1, ":' + encodeURIComponent(this.previousElementSibling.value) + '", fqs, sorts, "&start=", start2, "&rows=", rows1, "'; ") 
					.f();
					p.e("i").a("class", "fas fa-search ").f().g("i");
				} p.g("button");

			} p.g("div");
			{ p.e("div").a("class", "w3-cell-row ").f();
				{ p.e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
					{ p.e("ul").a("class", "w3-ul w3-hoverable ").a("id", "suggereListPatientMedicale", id).f();
					} p.g("ul");
				} p.g("div");
			} p.g("div");
			{ p.e("div").a("class", "").f();
				{ p.e("a").a("href", "/patient").a("class", "").f();
					p.e("i").a("class", "far fa-hospital-user ").f().g("i");
					p.sx("voir tous les patients");
				} p.g("a");
			} p.g("div");
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

}
