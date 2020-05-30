package org.computate.medicale.frFR.clinique;

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
 * NomCanonique.enUS: org.computate.medicale.enUS.clinic.ClinicGenPage
 **/
public class CliniqueGenPage extends CliniqueGenPageGen<ClusterPage> {

	public static final List<String> ROLES = Arrays.asList("SiteAdmin");
	public static final List<String> ROLE_READS = Arrays.asList("");

	/**
	 * {@inheritDoc}
	 * 
	 **/
	protected void _listeCliniqueMedicale(Couverture<ListeRecherche<CliniqueMedicale>> c) {
	}

	protected void _cliniqueMedicale(Couverture<CliniqueMedicale> c) {
		if(listeCliniqueMedicale != null && listeCliniqueMedicale.size() == 1)
			c.o(listeCliniqueMedicale.get(0));
	}

	@Override protected void _pageH1(Couverture<String> c) {
			c.o("écoles");
	}

	@Override protected void _pageH2(Couverture<String> c) {
		if(cliniqueMedicale != null && cliniqueMedicale.getCliniqueNomComplet() != null)
			c.o(cliniqueMedicale.getCliniqueNomComplet());
	}

	@Override protected void _pageH3(Couverture<String> c) {
		c.o("");
	}

	@Override protected void _pageTitre(Couverture<String> c) {
		if(cliniqueMedicale != null && cliniqueMedicale.getCliniqueNomComplet() != null)
			c.o(cliniqueMedicale.getCliniqueNomComplet());
		else if(cliniqueMedicale != null)
			c.o("");
		else if(listeCliniqueMedicale == null || listeCliniqueMedicale.size() == 0)
			c.o("aucune école trouvée");
	}

	@Override protected void _pageUri(Couverture<String> c) {
		c.o("/clinique");
	}

	@Override protected void _pageImageUri(Couverture<String> c) {
			c.o("/png/clinique-999.png");
	}

	@Override protected void _contexteIconeGroupe(Couverture<String> c) {
			c.o("regular");
	}

	@Override protected void _contexteIconeNom(Couverture<String> c) {
			c.o("clinic");
	}

	@Override public void initLoinCliniqueGenPage() {
		initCliniqueGenPage();
		super.initLoinMiseEnPage();
	}

	@Override public void htmlScriptsCliniqueGenPage() {
		e("script").a("src", statiqueUrlBase, "/js/frFR/CliniquePage.js").f().g("script");
	}

	@Override public void htmlScriptCliniqueGenPage() {
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
		tl(1, "}");
		tl(1, "websocketCliniqueMedicale(websocketCliniqueMedicaleInner);");
		l("});");
	}

	public void htmlFormPageCliniqueMedicale(CliniqueMedicale o) {
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
			o.htmCliniqueNom("Page");
			o.htmCliniqueEmplacement("Page");
			o.htmCliniqueAdministrateurNom("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmCliniqueMailDe("Page");
			o.htmCliniqueMailA("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmCliniqueNumeroTelephone("Page");
			o.htmCliniqueAddresse("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmAnneeCles("Page");
		} g("div");
	}

	public void htmlFormPOSTCliniqueMedicale(CliniqueMedicale o) {
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
			o.htmCliniqueNom("POST");
			o.htmCliniqueEmplacement("POST");
			o.htmCliniqueAdministrateurNom("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmCliniqueMailDe("POST");
			o.htmCliniqueMailA("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmCliniqueNumeroTelephone("POST");
			o.htmCliniqueAddresse("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmAnneeCles("POST");
		} g("div");
	}

	public void htmlFormPUTImportCliniqueMedicale(CliniqueMedicale o) {
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

	public void htmlFormPUTFusionCliniqueMedicale(CliniqueMedicale o) {
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

	public void htmlFormPUTCopieCliniqueMedicale(CliniqueMedicale o) {
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmCree("PUTCopie");
			o.htmModifie("PUTCopie");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmArchive("PUTCopie");
			o.htmSupprime("PUTCopie");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmCliniqueNom("PUTCopie");
			o.htmCliniqueEmplacement("PUTCopie");
			o.htmCliniqueAdministrateurNom("PUTCopie");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmCliniqueMailDe("PUTCopie");
			o.htmCliniqueMailA("PUTCopie");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmCliniqueNumeroTelephone("PUTCopie");
			o.htmCliniqueAddresse("PUTCopie");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmInheritPk("PUTCopie");
			o.htmSessionId("PUTCopie");
			o.htmUtilisateurId("PUTCopie");
			o.htmUtilisateurCle("PUTCopie");
		} g("div");
	}

	public void htmlFormPATCHCliniqueMedicale(CliniqueMedicale o) {
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmCree("PATCH");
			o.htmModifie("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmArchive("PATCH");
			o.htmSupprime("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmCliniqueNom("PATCH");
			o.htmCliniqueEmplacement("PATCH");
			o.htmCliniqueAdministrateurNom("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmCliniqueMailDe("PATCH");
			o.htmCliniqueMailA("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmCliniqueNumeroTelephone("PATCH");
			o.htmCliniqueAddresse("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmInheritPk("PATCH");
			o.htmSessionId("PATCH");
			o.htmUtilisateurId("PATCH");
			o.htmUtilisateurCle("PATCH");
		} g("div");
	}

	public void htmlFormRechercheCliniqueMedicale(CliniqueMedicale o) {
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
			o.htmCliniqueNom("Recherche");
			o.htmCliniqueEmplacement("Recherche");
			o.htmCliniqueAdministrateurNom("Recherche");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmCliniqueMailDe("Recherche");
			o.htmCliniqueMailA("Recherche");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmCliniqueNumeroTelephone("Recherche");
			o.htmCliniqueAddresse("Recherche");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmAnneeCles("Recherche");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmInheritPk("Recherche");
			o.htmSessionId("Recherche");
			o.htmUtilisateurId("Recherche");
			o.htmUtilisateurCle("Recherche");
			o.htmObjetTitre("Recherche");
		} g("div");
	}

	@Override public void htmlBodyCliniqueGenPage() {

		OperationRequest operationRequete = requeteSite_.getOperationRequete();
		JsonObject params = operationRequete.getParams();
		if(listeCliniqueMedicale == null || listeCliniqueMedicale.size() == 0) {

			{ e("h1").f();
				{ e("a").a("href", "/clinique").a("class", "w3-bar-item w3-btn w3-center w3-block w3-pink w3-hover-pink ").f();
					if(contexteIconeClassesCss != null)
						e("i").a("class", contexteIconeClassesCss + " site-menu-icon ").f().g("i");
					e("span").a("class", " ").f().sx("écoles").g("span");
				} g("a");
			} g("h1");
			e("div").a("class", "w3-padding-16 w3-card-4 w3-light-grey ").f();
			{ e("h2").f();
				{ e("span").a("class", "w3-bar-item w3-padding w3-center w3-block w3-pink ").f();
					if(contexteIconeClassesCss != null)
						e("i").a("class", contexteIconeClassesCss + " site-menu-icon ").f().g("i");
					e("span").a("class", " ").f().sx("aucune école trouvée").g("span");
				} g("span");
			} g("h2");
		} else if(listeCliniqueMedicale != null && listeCliniqueMedicale.size() == 1 && params.getJsonObject("query").getString("q").equals("*:*")) {
			CliniqueMedicale o = listeCliniqueMedicale.get(0);
			requeteSite_.setRequetePk(o.getPk());
			if(StringUtils.isNotEmpty(pageH1)) {
				{ e("h1").f();
					{ e("a").a("href", "/clinique").a("class", "w3-bar-item w3-btn w3-center w3-block w3-pink w3-hover-pink ").f();
						if(contexteIconeClassesCss != null)
							e("i").a("class", contexteIconeClassesCss + " site-menu-icon ").f().g("i");
						e("span").a("class", " ").f().sx(pageH1).g("span");
					} g("a");
				} g("h1");
			}
			e("div").a("class", "w3-padding-16 w3-card-4 w3-light-grey ").f();
			if(StringUtils.isNotEmpty(pageH2)) {
				{ e("h2").f();
					{ e("span").a("class", "w3-bar-item w3-padding w3-center w3-block w3-pink ").f();
						e("span").a("class", " ").f().sx(pageH2).g("span");
					} g("span");
				} g("h2");
			}
			if(StringUtils.isNotEmpty(pageH3)) {
				{ e("h3").f();
					{ e("span").a("class", "w3-bar-item w3-padding w3-center w3-block w3-pink ").f();
						e("span").a("class", " ").f().sx(pageH3).g("span");
					} g("span");
				} g("h3");
			}
		} else {

			{ e("h1").f();
				{ e("a").a("href", "/clinique").a("class", "w3-bar-item w3-btn w3-center w3-block w3-pink w3-hover-pink ").f();
					if(contexteIconeClassesCss != null)
						e("i").a("class", contexteIconeClassesCss + " site-menu-icon ").f().g("i");
					e("span").a("class", " ").f().sx(pageH1).g("span");
				} g("a");
			} g("h1");
			e("div").a("class", "").f();
				{ e("div").f();
					JsonObject queryParams = Optional.ofNullable(operationRequete).map(OperationRequest::getParams).map(or -> or.getJsonObject("query")).orElse(new JsonObject());
					Long num = listeCliniqueMedicale.getQueryResponse().getResults().getNumFound();
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

					Integer rows1 = Optional.ofNullable(listeCliniqueMedicale).map(l -> l.getRows()).orElse(10);
					Integer start1 = Optional.ofNullable(listeCliniqueMedicale).map(l -> l.getStart()).orElse(1);
					Integer start2 = start1 - rows1;
					Integer start3 = start1 + rows1;
					Integer rows2 = rows1 / 2;
					Integer rows3 = rows1 * 2;
					start2 = start2 < 0 ? 0 : start2;
					StringBuilder fqs = new StringBuilder();
					for(String fq : Optional.ofNullable(listeCliniqueMedicale).map(l -> l.getFilterQueries()).orElse(new String[0])) {
						if(!StringUtils.contains(fq, "(")) {
							String fq1 = StringUtils.substringBefore(fq, "_");
							String fq2 = StringUtils.substringAfter(fq, ":");
							if(!StringUtils.startsWithAny(fq, "classeNomsCanoniques_", "archive_", "supprime_", "sessionId", "utilisateurCles"))
								fqs.append("&fq=").append(fq1).append(":").append(fq2);
						}
					}
					StringBuilder sorts = new StringBuilder();
					for(SortClause sort : Optional.ofNullable(listeCliniqueMedicale).map(l -> l.getSorts()).orElse(Arrays.asList())) {
						sorts.append("&sort=").append(StringUtils.substringBefore(sort.getItem(), "_")).append(" ").append(sort.getOrder().name());
					}

					if(start1 == 0) {
						e("i").a("class", "fas fa-arrow-square-left w3-opacity ").f().g("i");
					} else {
						{ e("a").a("href", "/clinique?q=", query, fqs, sorts, "&start=", start2, "&rows=", rows1).f();
							e("i").a("class", "fas fa-arrow-square-left ").f().g("i");
						} g("a");
					}

					if(rows1 <= 1) {
						e("i").a("class", "fas fa-minus-square w3-opacity ").f().g("i");
					} else {
						{ e("a").a("href", "/clinique?q=", query, fqs, sorts, "&start=", start1, "&rows=", rows2).f();
							e("i").a("class", "fas fa-minus-square ").f().g("i");
						} g("a");
					}

					{ e("a").a("href", "/clinique?q=", query, fqs, sorts, "&start=", start1, "&rows=", rows3).f();
						e("i").a("class", "fas fa-plus-square ").f().g("i");
					} g("a");

					if(start3 >= num) {
						e("i").a("class", "fas fa-arrow-square-right w3-opacity ").f().g("i");
					} else {
						{ e("a").a("href", "/clinique?q=", query, fqs, sorts, "&start=", start3, "&rows=", rows1).f();
							e("i").a("class", "fas fa-arrow-square-right ").f().g("i");
						} g("a");
					}
						e("span").f().sx((start1 + 1), " - ", (start1 + rows1), " de ", num).g("span");
				} g("div");
				table1CliniqueGenPage();
		}

		if(listeCliniqueMedicale != null && listeCliniqueMedicale.size() == 1 && params.getJsonObject("query").getString("q").equals("*:*")) {
			CliniqueMedicale o = listeCliniqueMedicale.first();

			{ e("div").a("class", "").f();

				if(o.getPk() != null) {
					{ e("form").a("action", "").a("id", "CliniqueMedicaleForm").a("style", "display: inline-block; width: 100%; ").a("onsubmit", "event.preventDefault(); return false; ").f();
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
					htmlFormPageCliniqueMedicale(o);
				}

			} g("div");

		}
		htmlBodyFormsCliniqueGenPage();
		g("div");
	}

	public void table1CliniqueGenPage() {
		{ e("table").a("class", "w3-table w3-bordered w3-striped w3-border w3-hoverable ").f();
			table2CliniqueGenPage();
		} g("table");
	}

	public void table2CliniqueGenPage() {
		thead1CliniqueGenPage();
		tbody1CliniqueGenPage();
		tfoot1CliniqueGenPage();
	}

	public void thead1CliniqueGenPage() {
		{ e("thead").a("class", "w3-pink w3-hover-pink ").f();
			thead2CliniqueGenPage();
		} g("thead");
	}

	public void thead2CliniqueGenPage() {
			{ e("tr").f();
			if(getColonneCree()) {
				e("th").f().sx("crée").g("th");
			}
			if(getColonneObjetTitre()) {
				e("th").f().sx("").g("th");
			}
			} g("tr");
	}

	public void tbody1CliniqueGenPage() {
		{ e("tbody").f();
			tbody2CliniqueGenPage();
		} g("tbody");
	}

	public void tbody2CliniqueGenPage() {
		Map<String, Map<String, List<String>>> highlighting = listeCliniqueMedicale.getQueryResponse().getHighlighting();
		for(int i = 0; i < listeCliniqueMedicale.size(); i++) {
			CliniqueMedicale o = listeCliniqueMedicale.getList().get(i);
			Map<String, List<String>> highlights = highlighting == null ? null : highlighting.get(o.getId());
			List<String> highlightList = highlights == null ? null : highlights.get(highlights.keySet().stream().findFirst().orElse(null));
			String uri = "/clinique/" + o.getPk();
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
							e("i").a("class", "far fa-clinic ").f().g("i");
							{ e("span").f();
								sx(o.strObjetTitre());
							} g("span");
						} g("a");
					} g("td");
				}
			} g("tr");
		}
	}

	public void tfoot1CliniqueGenPage() {
		{ e("tfoot").a("class", "w3-pink w3-hover-pink ").f();
			tfoot2CliniqueGenPage();
		} g("tfoot");
	}

	public void tfoot2CliniqueGenPage() {
		{ e("tr").f();
			SimpleOrderedMap facets = (SimpleOrderedMap)Optional.ofNullable(listeCliniqueMedicale.getQueryResponse()).map(QueryResponse::getResponse).map(r -> r.get("facets")).orElse(new SimpleOrderedMap());
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

	public void htmlBodyFormsCliniqueGenPage() {
		if(
				CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
				) {
			e("div").a("class", "w3-margin-top ").f();

			if(listeCliniqueMedicale != null && listeCliniqueMedicale.size() == 1) {
				{ e("button")
					.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-pink ")
						.a("id", "rechargerCetteCliniqueGenPage")
						.a("onclick", "patchCliniqueMedicaleVals( [ {name: 'fq', value: 'pk:' + " + requeteSite_.getRequetePk() + " } ], {}, function() { ajouterLueur($('#rechargerCetteCliniqueGenPage')); }, function() { ajouterErreur($('#rechargerCetteCliniqueGenPage')); }); return false; ").f();
						e("i").a("class", "fas fa-sync-alt ").f().g("i");
					sx("recharger cette école");
				} g("button");
			}

			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-pink ")
				.a("onclick", "$('#postCliniqueMedicaleModale').show(); ")
				.f();
				e("i").a("class", "fas fa-file-plus ").f().g("i");
				sx("Créer une école");
			} g("button");
			{ e("div").a("id", "postCliniqueMedicaleModale").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-pink ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#postCliniqueMedicaleModale').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Créer une école").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").f();
							CliniqueMedicale o = new CliniqueMedicale();
							o.setRequeteSite_(requeteSite_);

							// Form POST
							{ e("div").a("id", "postCliniqueMedicaleForm").f();
								htmlFormPOSTCliniqueMedicale(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-pink ")
								.a("onclick", "postCliniqueMedicale($('#postCliniqueMedicaleForm')); ")
								.f().sx("Créer une école")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-pink ")
				.a("onclick", "$('#patchCliniqueMedicaleModale').show(); ")
				.f();
				e("i").a("class", "fas fa-edit ").f().g("i");
				sx("Modifier écoles");
			} g("button");
			{ e("div").a("id", "patchCliniqueMedicaleModale").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-pink ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#patchCliniqueMedicaleModale').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Modifier écoles").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").f();
							CliniqueMedicale o = new CliniqueMedicale();
							o.setRequeteSite_(requeteSite_);

							// FormulaireValeurs PATCH
							{ e("form").a("action", "").a("id", "patchCliniqueMedicaleFormulaireValeurs").a("onsubmit", "event.preventDefault(); return false; ").f();
								htmlFormPATCHCliniqueMedicale(o);
							} g("form");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-pink ")
								.a("onclick", "patchCliniqueMedicale(null, $('#patchCliniqueMedicaleFormulaireValeurs'), ", Optional.ofNullable(cliniqueMedicale).map(CliniqueMedicale::getPk).map(a -> a.toString()).orElse("null"), ", function() {}, function() {}); ")
								.f().sx("Modifier écoles")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-pink ")
				.a("onclick", "$('#putimportCliniqueMedicaleModale').show(); ")
				.f();
				e("i").a("class", "fas fa-file-import ").f().g("i");
				sx("Importer écoles");
			} g("button");
			{ e("div").a("id", "putimportCliniqueMedicaleModale").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-pink ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#putimportCliniqueMedicaleModale').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Importer écoles").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").f();
							CliniqueMedicale o = new CliniqueMedicale();
							o.setRequeteSite_(requeteSite_);

							// Form PUT
							{ e("div").a("id", "putimportCliniqueMedicaleForm").f();
								htmlFormPUTImportCliniqueMedicale(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-pink ")
								.a("onclick", "putimportCliniqueMedicale($('#putimportCliniqueMedicaleForm')); ")
								.f().sx("Importer écoles")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-pink ")
				.a("onclick", "$('#putfusionCliniqueMedicaleModale').show(); ")
				.f();
				e("i").a("class", "fas fa-code-merge ").f().g("i");
				sx("Fusionner écoles");
			} g("button");
			{ e("div").a("id", "putfusionCliniqueMedicaleModale").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-pink ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#putfusionCliniqueMedicaleModale').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Fusionner écoles").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").f();
							CliniqueMedicale o = new CliniqueMedicale();
							o.setRequeteSite_(requeteSite_);

							// Form PUT
							{ e("div").a("id", "putfusionCliniqueMedicaleForm").f();
								htmlFormPUTFusionCliniqueMedicale(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-pink ")
								.a("onclick", "putfusionCliniqueMedicale($('#putfusionCliniqueMedicaleForm')); ")
								.f().sx("Fusionner écoles")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-pink ")
				.a("onclick", "$('#putcopieCliniqueMedicaleModale').show(); ")
				.f();
				e("i").a("class", "fas fa-copy ").f().g("i");
				sx("Dupliquer écoles");
			} g("button");
			{ e("div").a("id", "putcopieCliniqueMedicaleModale").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-pink ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#putcopieCliniqueMedicaleModale').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Dupliquer écoles").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").f();
							CliniqueMedicale o = new CliniqueMedicale();
							o.setRequeteSite_(requeteSite_);

							// Form PUT
							{ e("div").a("id", "putcopieCliniqueMedicaleForm").f();
								htmlFormPUTCopieCliniqueMedicale(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-pink ")
								.a("onclick", "putcopieCliniqueMedicale($('#putcopieCliniqueMedicaleForm'), ", cliniqueMedicale == null ? "null" : cliniqueMedicale.getPk(), "); ")
								.f().sx("Dupliquer écoles")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");

			g("div");
		}
		htmlSuggereCliniqueGenPage(this, null, listeCliniqueMedicale);
	}

	/**
	 * Var.enUS: htmlSuggestedClinicGenPage
	 * r: "/clinique"
	 * r.enUS: "/clinic"
	 * r: "voir toutes les écoles"
	 * r.enUS: "see all the clinics"
	 * r: "rechargerCliniqueGenPage"
	 * r.enUS: "refreshClinicGenPage"
	 * r: "recharger toutes les écoles"
	 * r.enUS: "refresh all the clinics"
	 * r: "rechercher écoles : "
	 * r.enUS: "search clinics: "
	 * r: "suggereFormCliniqueMedicale"
	 * r.enUS: "suggestFormMedicalClinic"
	 * r: "rechercher écoles"
	 * r.enUS: "search clinics"
	 * r: "suggereCliniqueMedicale w3-input w3-border w3-cell w3-cell-middle "
	 * r.enUS: "suggestMedicalClinic w3-input w3-border w3-cell w3-cell-middle "
	 * r: "suggereCliniqueMedicale"
	 * r.enUS: "suggestMedicalClinic"
	 * r: patchCliniqueMedicaleVals
	 * r.enUS: patchMedicalClinicVals
	 * r: ajouterLueur
	 * r.enUS: addGlow
	 * r: rechargerCliniqueGenPage
	 * r.enUS: refreshClinicGenPage
	 * r: ajouterErreur
	 * r.enUS: addError
	 * r: suggereCliniqueMedicaleObjetSuggere
	 * r.enUS: suggestMedicalClinicObjectSuggest
	 * r: texteCliniqueMedicaleObjetTexte
	 * r.enUS: textMedicalClinicObjectText
	 * r: 'objetSuggere:'
	 * r.enUS: 'objectSuggest:'
	 * r: 'objetTexte:'
	 * r.enUS: 'objectText:'
	 * r: '#suggereListCliniqueMedicale'
	 * r.enUS: '#suggestListMedicalClinic'
	 * r: "suggereListCliniqueMedicale"
	 * r.enUS: "suggestListMedicalClinic"
	**/
	public static void htmlSuggereCliniqueGenPage(MiseEnPage p, String id, ListeRecherche<CliniqueMedicale> listeCliniqueMedicale) {
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

			Integer rows1 = Optional.ofNullable(listeCliniqueMedicale).map(l -> l.getRows()).orElse(10);
			Integer start1 = Optional.ofNullable(listeCliniqueMedicale).map(l -> l.getStart()).orElse(1);
			Integer start2 = start1 - rows1;
			Integer start3 = start1 + rows1;
			Integer rows2 = rows1 / 2;
			Integer rows3 = rows1 * 2;
			start2 = start2 < 0 ? 0 : start2;
			StringBuilder fqs = new StringBuilder();
			for(String fq : Optional.ofNullable(listeCliniqueMedicale).map(l -> l.getFilterQueries()).orElse(new String[0])) {
				if(!StringUtils.contains(fq, "(")) {
					String fq1 = StringUtils.substringBefore(fq, "_");
					String fq2 = StringUtils.substringAfter(fq, ":");
					if(!StringUtils.startsWithAny(fq, "classeNomsCanoniques_", "archive_", "supprime_", "sessionId", "utilisateurCles"))
						fqs.append("&fq=").append(fq1).append(":").append(fq2);
				}
			}
			StringBuilder sorts = new StringBuilder();
			for(SortClause sort : Optional.ofNullable(listeCliniqueMedicale).map(l -> l.getSorts()).orElse(Arrays.asList())) {
				sorts.append("&sort=").append(StringUtils.substringBefore(sort.getItem(), "_")).append(" ").append(sort.getOrder().name());
			}

			if(
					CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), CliniqueGenPage.ROLES)
					|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), CliniqueGenPage.ROLES)
					) {
					{ p.e("div").a("class", "").f();
						{ p.e("button").a("id", "rechargerToutesCliniqueGenPage", id).a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-pink ").a("onclick", "patchCliniqueMedicaleVals([], {}, function() { ajouterLueur($('#rechargerToutesCliniqueGenPage", id, "')); }, function() { ajouterErreur($('#rechargerToutesCliniqueGenPage", id, "')); }); ").f();
							p.e("i").a("class", "fas fa-sync-alt ").f().g("i");
							p.sx("recharger toutes les écoles");
						} p.g("button");
					} p.g("div");
			}
			{ p.e("div").a("class", "w3-cell-row ").f();
				{ p.e("div").a("class", "w3-cell ").f();
					{ p.e("span").f();
						p.sx("rechercher écoles : ");
					} p.g("span");
				} p.g("div");
			} p.g("div");
			{ p.e("div").a("class", "w3-bar ").f();

				p.e("input")
					.a("type", "text")
					.a("placeholder", "rechercher écoles")
					.a("class", "suggereCliniqueMedicale w3-input w3-border w3-bar-item ")
					.a("name", "suggereCliniqueMedicale")
					.a("id", "suggereCliniqueMedicale", id)
					.a("autocomplete", "off")
					.a("oninput", "suggereCliniqueMedicaleObjetSuggere( [ { 'name': 'q', 'value': 'objetSuggere:' + $(this).val() } ], $('#suggereListCliniqueMedicale", id, "'), ", p.getRequeteSite_().getRequetePk(), "); ")
					.a("onkeyup", "if (event.keyCode === 13) { event.preventDefault(); window.location.href = '/clinique?q=", query1, ":' + encodeURIComponent(this.value) + '", fqs, sorts, "&start=", start2, "&rows=", rows1, "'; }"); 
				if(listeCliniqueMedicale != null)
					p.a("value", query2);
				p.fg();
				{ p.e("button")
					.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pink ")
					.a("onclick", "window.location.href = '/clinique?q=", query1, ":' + encodeURIComponent(this.previousElementSibling.value) + '", fqs, sorts, "&start=", start2, "&rows=", rows1, "'; ") 
					.f();
					p.e("i").a("class", "fas fa-search ").f().g("i");
				} p.g("button");

			} p.g("div");
			{ p.e("div").a("class", "w3-cell-row ").f();
				{ p.e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
					{ p.e("ul").a("class", "w3-ul w3-hoverable ").a("id", "suggereListCliniqueMedicale", id).f();
					} p.g("ul");
				} p.g("div");
			} p.g("div");
			{ p.e("div").a("class", "").f();
				{ p.e("a").a("href", "/clinique").a("class", "").f();
					p.e("i").a("class", "far fa-clinic ").f().g("i");
					p.sx("voir toutes les écoles");
				} p.g("a");
			} p.g("div");
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

}
