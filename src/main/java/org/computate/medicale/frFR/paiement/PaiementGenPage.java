package org.computate.medicale.frFR.paiement;

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
 * NomCanonique.enUS: org.computate.medicale.enUS.payment.PaymentGenPage
 **/
public class PaiementGenPage extends PaiementGenPageGen<ClusterPage> {

	public static final List<String> ROLES = Arrays.asList("SiteAdmin");
	public static final List<String> ROLE_READS = Arrays.asList("User");

	/**
	 * {@inheritDoc}
	 * 
	 **/
	protected void _listePaiementmedicale(Couverture<ListeRecherche<Paiementmedicale>> c) {
	}

	protected void _paiementmedicale(Couverture<Paiementmedicale> c) {
		if(listePaiementmedicale != null && listePaiementmedicale.size() == 1)
			c.o(listePaiementmedicale.get(0));
	}

	@Override protected void _pageH1(Couverture<String> c) {
			c.o("paiements");
	}

	@Override protected void _pageH2(Couverture<String> c) {
		if(paiementmedicale != null && paiementmedicale.getPaiementNomComplet() != null)
			c.o(paiementmedicale.getPaiementNomComplet());
	}

	@Override protected void _pageH3(Couverture<String> c) {
		c.o("");
	}

	@Override protected void _pageTitre(Couverture<String> c) {
		if(paiementmedicale != null && paiementmedicale.getPaiementNomComplet() != null)
			c.o(paiementmedicale.getPaiementNomComplet());
		else if(paiementmedicale != null)
			c.o("");
		else if(listePaiementmedicale == null || listePaiementmedicale.size() == 0)
			c.o("aucun paiement trouvé");
	}

	@Override protected void _pageUri(Couverture<String> c) {
		c.o("/paiement");
	}

	@Override protected void _pageImageUri(Couverture<String> c) {
			c.o("/png/paiement-999.png");
	}

	@Override protected void _contexteIconeGroupe(Couverture<String> c) {
			c.o("solid");
	}

	@Override protected void _contexteIconeNom(Couverture<String> c) {
			c.o("search-dollar");
	}

	@Override public void initLoinPaiementGenPage() {
		initPaiementGenPage();
		super.initLoinMiseEnPage();
	}

	@Override public void htmlScriptsPaiementGenPage() {
		e("script").a("src", statiqueUrlBase, "/js/frFR/PaiementPage.js").f().g("script");
		e("script").a("src", statiqueUrlBase, "/js/frFR/InscriptionPage.js").f().g("script");
	}

	@Override public void htmlScriptPaiementGenPage() {
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
			tl(2, "suggerePaiementmedicaleInscriptionCle([{'name':'fq','value':'paiementCles:' + pk}], $('#listPaiementmedicaleInscriptionCle_Page'), pk, true); ");
		} else {
			tl(2, "suggerePaiementmedicaleInscriptionCle([{'name':'fq','value':'paiementCles:' + pk}], $('#listPaiementmedicaleInscriptionCle_Page'), pk, false); ");
		}
		tl(1, "}");
		tl(1, "websocketPaiementmedicale(websocketPaiementmedicaleInner);");
		l("});");
	}

	public void htmlFormPagePaiementmedicale(Paiementmedicale o) {
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
			o.htmPaiementDate("Page");
			o.htmPaiementMontant("Page");
			o.htmPaiementEspeces("Page");
			o.htmPaiementCheque("Page");
			o.htmPaiementSysteme("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPaiementDescription("Page");
			o.htmPaiementPar("Page");
			o.htmInscriptionPaimentComplet("Page");
			o.htmInscriptionPaimentChaqueMois("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmTransactionId("Page");
			o.htmCustomerProfileId("Page");
			o.htmTransactionStatus("Page");
			o.htmPaiementRecu("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmFraisMontant("Page");
			o.htmFraisPremierDernier("Page");
			o.htmFraisInscription("Page");
			o.htmFraisMois("Page");
			o.htmFraisRetard("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmInscriptionCle("Page");
		} g("div");
	}

	public void htmlFormPOSTPaiementmedicale(Paiementmedicale o) {
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
			o.htmPaiementDate("POST");
			o.htmPaiementMontant("POST");
			o.htmPaiementEspeces("POST");
			o.htmPaiementCheque("POST");
			o.htmPaiementSysteme("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPaiementDescription("POST");
			o.htmPaiementPar("POST");
			o.htmInscriptionPaimentComplet("POST");
			o.htmInscriptionPaimentChaqueMois("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmTransactionId("POST");
			o.htmCustomerProfileId("POST");
			o.htmTransactionStatus("POST");
			o.htmPaiementRecu("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmFraisMontant("POST");
			o.htmFraisPremierDernier("POST");
			o.htmFraisInscription("POST");
			o.htmFraisMois("POST");
			o.htmFraisRetard("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmInscriptionCle("POST");
		} g("div");
	}

	public void htmlFormPUTImportPaiementmedicale(Paiementmedicale o) {
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

	public void htmlFormPUTFusionPaiementmedicale(Paiementmedicale o) {
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

	public void htmlFormPUTCopiePaiementmedicale(Paiementmedicale o) {
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmCree("PUTCopie");
			o.htmModifie("PUTCopie");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmArchive("PUTCopie");
			o.htmSupprime("PUTCopie");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPaiementDate("PUTCopie");
			o.htmPaiementMontant("PUTCopie");
			o.htmPaiementEspeces("PUTCopie");
			o.htmPaiementCheque("PUTCopie");
			o.htmPaiementSysteme("PUTCopie");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPaiementDescription("PUTCopie");
			o.htmPaiementPar("PUTCopie");
			o.htmInscriptionPaimentComplet("PUTCopie");
			o.htmInscriptionPaimentChaqueMois("PUTCopie");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmTransactionId("PUTCopie");
			o.htmCustomerProfileId("PUTCopie");
			o.htmTransactionStatus("PUTCopie");
			o.htmPaiementRecu("PUTCopie");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmFraisMontant("PUTCopie");
			o.htmFraisPremierDernier("PUTCopie");
			o.htmFraisInscription("PUTCopie");
			o.htmFraisMois("PUTCopie");
			o.htmFraisRetard("PUTCopie");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmInscriptionCle("PUTCopie");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmInheritPk("PUTCopie");
			o.htmSessionId("PUTCopie");
			o.htmUtilisateurId("PUTCopie");
			o.htmUtilisateurCle("PUTCopie");
			o.htmEnfantNomCompletPrefere("PUTCopie");
			o.htmEnfantDateNaissance("PUTCopie");
			o.htmMereNomCompletPrefere("PUTCopie");
			o.htmPereNomCompletPrefere("PUTCopie");
			o.htmFraisMontantDu("PUTCopie");
			o.htmFraisMontantFuture("PUTCopie");
			o.htmPaiementNomCourt("PUTCopie");
		} g("div");
	}

	public void htmlFormPATCHPaiementmedicale(Paiementmedicale o) {
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmCree("PATCH");
			o.htmModifie("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmArchive("PATCH");
			o.htmSupprime("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPaiementDate("PATCH");
			o.htmPaiementMontant("PATCH");
			o.htmPaiementEspeces("PATCH");
			o.htmPaiementCheque("PATCH");
			o.htmPaiementSysteme("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPaiementDescription("PATCH");
			o.htmPaiementPar("PATCH");
			o.htmInscriptionPaimentComplet("PATCH");
			o.htmInscriptionPaimentChaqueMois("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmTransactionId("PATCH");
			o.htmCustomerProfileId("PATCH");
			o.htmTransactionStatus("PATCH");
			o.htmPaiementRecu("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmFraisMontant("PATCH");
			o.htmFraisPremierDernier("PATCH");
			o.htmFraisInscription("PATCH");
			o.htmFraisMois("PATCH");
			o.htmFraisRetard("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmInscriptionCle("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmInheritPk("PATCH");
			o.htmSessionId("PATCH");
			o.htmUtilisateurId("PATCH");
			o.htmUtilisateurCle("PATCH");
			o.htmEnfantNomCompletPrefere("PATCH");
			o.htmEnfantDateNaissance("PATCH");
			o.htmMereNomCompletPrefere("PATCH");
			o.htmPereNomCompletPrefere("PATCH");
			o.htmFraisMontantDu("PATCH");
			o.htmFraisMontantFuture("PATCH");
			o.htmPaiementNomCourt("PATCH");
		} g("div");
	}

	public void htmlFormRecherchePaiementmedicale(Paiementmedicale o) {
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
			o.htmPaiementDate("Recherche");
			o.htmPaiementMontant("Recherche");
			o.htmPaiementEspeces("Recherche");
			o.htmPaiementCheque("Recherche");
			o.htmPaiementSysteme("Recherche");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPaiementDescription("Recherche");
			o.htmPaiementPar("Recherche");
			o.htmInscriptionPaimentComplet("Recherche");
			o.htmInscriptionPaimentChaqueMois("Recherche");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmTransactionId("Recherche");
			o.htmCustomerProfileId("Recherche");
			o.htmTransactionStatus("Recherche");
			o.htmPaiementRecu("Recherche");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmFraisMontant("Recherche");
			o.htmFraisPremierDernier("Recherche");
			o.htmFraisInscription("Recherche");
			o.htmFraisMois("Recherche");
			o.htmFraisRetard("Recherche");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmInscriptionCle("Recherche");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmInheritPk("Recherche");
			o.htmSessionId("Recherche");
			o.htmUtilisateurId("Recherche");
			o.htmUtilisateurCle("Recherche");
			o.htmObjetTitre("Recherche");
			o.htmEnfantNomCompletPrefere("Recherche");
			o.htmEnfantDateNaissance("Recherche");
			o.htmMereNomCompletPrefere("Recherche");
			o.htmPereNomCompletPrefere("Recherche");
			o.htmFraisMontantDu("Recherche");
			o.htmFraisMontantFuture("Recherche");
			o.htmPaiementNomCourt("Recherche");
		} g("div");
	}

	@Override public void htmlBodyPaiementGenPage() {

		OperationRequest operationRequete = requeteSite_.getOperationRequete();
		JsonObject params = operationRequete.getParams();
		if(listePaiementmedicale == null || listePaiementmedicale.size() == 0) {

			{ e("h1").f();
				{ e("a").a("href", "/paiement").a("class", "w3-bar-item w3-btn w3-center w3-block w3-green w3-hover-green ").f();
					if(contexteIconeClassesCss != null)
						e("i").a("class", contexteIconeClassesCss + " site-menu-icon ").f().g("i");
					e("span").a("class", " ").f().sx("paiements").g("span");
				} g("a");
			} g("h1");
			e("div").a("class", "w3-padding-16 w3-card-4 w3-light-grey ").f();
			{ e("h2").f();
				{ e("span").a("class", "w3-bar-item w3-padding w3-center w3-block w3-green ").f();
					if(contexteIconeClassesCss != null)
						e("i").a("class", contexteIconeClassesCss + " site-menu-icon ").f().g("i");
					e("span").a("class", " ").f().sx("aucun paiement trouvé").g("span");
				} g("span");
			} g("h2");
		} else if(listePaiementmedicale != null && listePaiementmedicale.size() == 1 && params.getJsonObject("query").getString("q").equals("*:*")) {
			Paiementmedicale o = listePaiementmedicale.get(0);
			requeteSite_.setRequetePk(o.getPk());
			if(StringUtils.isNotEmpty(pageH1)) {
				{ e("h1").f();
					{ e("a").a("href", "/paiement").a("class", "w3-bar-item w3-btn w3-center w3-block w3-green w3-hover-green ").f();
						if(contexteIconeClassesCss != null)
							e("i").a("class", contexteIconeClassesCss + " site-menu-icon ").f().g("i");
						e("span").a("class", " ").f().sx(pageH1).g("span");
					} g("a");
				} g("h1");
			}
			e("div").a("class", "w3-padding-16 w3-card-4 w3-light-grey ").f();
			if(StringUtils.isNotEmpty(pageH2)) {
				{ e("h2").f();
					{ e("span").a("class", "w3-bar-item w3-padding w3-center w3-block w3-green ").f();
						e("span").a("class", " ").f().sx(pageH2).g("span");
					} g("span");
				} g("h2");
			}
			if(StringUtils.isNotEmpty(pageH3)) {
				{ e("h3").f();
					{ e("span").a("class", "w3-bar-item w3-padding w3-center w3-block w3-green ").f();
						e("span").a("class", " ").f().sx(pageH3).g("span");
					} g("span");
				} g("h3");
			}
		} else {

			{ e("h1").f();
				{ e("a").a("href", "/paiement").a("class", "w3-bar-item w3-btn w3-center w3-block w3-green w3-hover-green ").f();
					if(contexteIconeClassesCss != null)
						e("i").a("class", contexteIconeClassesCss + " site-menu-icon ").f().g("i");
					e("span").a("class", " ").f().sx(pageH1).g("span");
				} g("a");
			} g("h1");
			e("div").a("class", "").f();
				{ e("div").f();
					JsonObject queryParams = Optional.ofNullable(operationRequete).map(OperationRequest::getParams).map(or -> or.getJsonObject("query")).orElse(new JsonObject());
					Long num = listePaiementmedicale.getQueryResponse().getResults().getNumFound();
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

					Integer rows1 = Optional.ofNullable(listePaiementmedicale).map(l -> l.getRows()).orElse(10);
					Integer start1 = Optional.ofNullable(listePaiementmedicale).map(l -> l.getStart()).orElse(1);
					Integer start2 = start1 - rows1;
					Integer start3 = start1 + rows1;
					Integer rows2 = rows1 / 2;
					Integer rows3 = rows1 * 2;
					start2 = start2 < 0 ? 0 : start2;
					StringBuilder fqs = new StringBuilder();
					for(String fq : Optional.ofNullable(listePaiementmedicale).map(l -> l.getFilterQueries()).orElse(new String[0])) {
						if(!StringUtils.contains(fq, "(")) {
							String fq1 = StringUtils.substringBefore(fq, "_");
							String fq2 = StringUtils.substringAfter(fq, ":");
							if(!StringUtils.startsWithAny(fq, "classeNomsCanoniques_", "archive_", "supprime_", "sessionId", "utilisateurCles"))
								fqs.append("&fq=").append(fq1).append(":").append(fq2);
						}
					}
					StringBuilder sorts = new StringBuilder();
					for(SortClause sort : Optional.ofNullable(listePaiementmedicale).map(l -> l.getSorts()).orElse(Arrays.asList())) {
						sorts.append("&sort=").append(StringUtils.substringBefore(sort.getItem(), "_")).append(" ").append(sort.getOrder().name());
					}

					if(start1 == 0) {
						e("i").a("class", "fas fa-arrow-square-left w3-opacity ").f().g("i");
					} else {
						{ e("a").a("href", "/paiement?q=", query, fqs, sorts, "&start=", start2, "&rows=", rows1).f();
							e("i").a("class", "fas fa-arrow-square-left ").f().g("i");
						} g("a");
					}

					if(rows1 <= 1) {
						e("i").a("class", "fas fa-minus-square w3-opacity ").f().g("i");
					} else {
						{ e("a").a("href", "/paiement?q=", query, fqs, sorts, "&start=", start1, "&rows=", rows2).f();
							e("i").a("class", "fas fa-minus-square ").f().g("i");
						} g("a");
					}

					{ e("a").a("href", "/paiement?q=", query, fqs, sorts, "&start=", start1, "&rows=", rows3).f();
						e("i").a("class", "fas fa-plus-square ").f().g("i");
					} g("a");

					if(start3 >= num) {
						e("i").a("class", "fas fa-arrow-square-right w3-opacity ").f().g("i");
					} else {
						{ e("a").a("href", "/paiement?q=", query, fqs, sorts, "&start=", start3, "&rows=", rows1).f();
							e("i").a("class", "fas fa-arrow-square-right ").f().g("i");
						} g("a");
					}
						e("span").f().sx((start1 + 1), " - ", (start1 + rows1), " de ", num).g("span");
				} g("div");
				table1PaiementGenPage();
		}

		if(listePaiementmedicale != null && listePaiementmedicale.size() == 1 && params.getJsonObject("query").getString("q").equals("*:*")) {
			Paiementmedicale o = listePaiementmedicale.first();

			{ e("div").a("class", "").f();

				if(o.getPk() != null) {
					{ e("form").a("action", "").a("id", "PaiementmedicaleForm").a("style", "display: inline-block; width: 100%; ").a("onsubmit", "event.preventDefault(); return false; ").f();
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
					htmlFormPagePaiementmedicale(o);
				}

			} g("div");

		}
		htmlBodyFormsPaiementGenPage();
		g("div");
	}

	public void table1PaiementGenPage() {
		{ e("table").a("class", "w3-table w3-bordered w3-striped w3-border w3-hoverable ").f();
			table2PaiementGenPage();
		} g("table");
	}

	public void table2PaiementGenPage() {
		thead1PaiementGenPage();
		tbody1PaiementGenPage();
		tfoot1PaiementGenPage();
	}

	public void thead1PaiementGenPage() {
		{ e("thead").a("class", "w3-green w3-hover-green ").f();
			thead2PaiementGenPage();
		} g("thead");
	}

	public void thead2PaiementGenPage() {
			{ e("tr").f();
			if(getColonneCree()) {
				e("th").f().sx("crée").g("th");
			}
			if(getColonneObjetTitre()) {
				e("th").f().sx("").g("th");
			}
			if(getColonnePaiementNomCourt()) {
				e("th").f().sx("nom").g("th");
			}
			if(getColonnePaiementDate()) {
				e("th").f().sx("date de paiement").g("th");
			}
			if(getColonnePaiementMontant()) {
				e("th").f().sx("paiement montant").g("th");
			}
			if(getColonneFraisMontant()) {
				e("th").f().sx("frais montant").g("th");
			}
			} g("tr");
	}

	public void tbody1PaiementGenPage() {
		{ e("tbody").f();
			tbody2PaiementGenPage();
		} g("tbody");
	}

	public void tbody2PaiementGenPage() {
		Map<String, Map<String, List<String>>> highlighting = listePaiementmedicale.getQueryResponse().getHighlighting();
		for(int i = 0; i < listePaiementmedicale.size(); i++) {
			Paiementmedicale o = listePaiementmedicale.getList().get(i);
			Map<String, List<String>> highlights = highlighting == null ? null : highlighting.get(o.getId());
			List<String> highlightList = highlights == null ? null : highlights.get(highlights.keySet().stream().findFirst().orElse(null));
			String uri = "/paiement/" + o.getPk();
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
							e("i").a("class", "fas fa-search-dollar ").f().g("i");
							{ e("span").f();
								sx(o.strObjetTitre());
							} g("span");
						} g("a");
					} g("td");
				}
				if(getColonnePaiementNomCourt()) {
					{ e("td").f();
						{ e("a").a("href", uri).f();
							{ e("span").f();
								sx(o.strPaiementNomCourt());
							} g("span");
						} g("a");
					} g("td");
				}
				if(getColonnePaiementDate()) {
					{ e("td").f();
						{ e("a").a("href", uri).f();
							{ e("span").f();
								sx(o.strPaiementDate());
							} g("span");
						} g("a");
					} g("td");
				}
				if(getColonnePaiementMontant()) {
					{ e("td").f();
						{ e("a").a("href", uri).f();
							{ e("span").f();
								sx(o.strPaiementMontant());
							} g("span");
						} g("a");
					} g("td");
				}
				if(getColonneFraisMontant()) {
					{ e("td").f();
						{ e("a").a("href", uri).f();
							{ e("span").f();
								sx(o.strFraisMontant());
							} g("span");
						} g("a");
					} g("td");
				}
			} g("tr");
		}
	}

	public void tfoot1PaiementGenPage() {
		{ e("tfoot").a("class", "w3-green w3-hover-green ").f();
			tfoot2PaiementGenPage();
		} g("tfoot");
	}

	public void tfoot2PaiementGenPage() {
		{ e("tr").f();
			SimpleOrderedMap facets = (SimpleOrderedMap)Optional.ofNullable(listePaiementmedicale.getQueryResponse()).map(QueryResponse::getResponse).map(r -> r.get("facets")).orElse(new SimpleOrderedMap());
			if(getColonneCree()) {
				e("td").f();
				g("td");
			}
			if(getColonneObjetTitre()) {
				e("td").f();
				g("td");
			}
			if(getColonnePaiementNomCourt()) {
				e("td").f();
				g("td");
			}
			if(getColonnePaiementDate()) {
				e("td").f();
				g("td");
			}
			if(getColonnePaiementMontant()) {
				e("td").f();
				BigDecimal sum_paiementMontant = Optional.ofNullable((Double)facets.get("sum_paiementMontant")).map(d -> new BigDecimal(d, MathContext.DECIMAL64).setScale(2)).orElse(new BigDecimal(0, MathContext.DECIMAL64).setScale(2));
				e("span").a("class", "font-weight-bold ").f().sx(sum_paiementMontant).g("span");
				g("td");
			}
			if(getColonneFraisMontant()) {
				e("td").f();
				BigDecimal sum_fraisMontant = Optional.ofNullable((Double)facets.get("sum_fraisMontant")).map(d -> new BigDecimal(d, MathContext.DECIMAL64).setScale(2)).orElse(new BigDecimal(0, MathContext.DECIMAL64).setScale(2));
				e("span").a("class", "font-weight-bold ").f().sx(sum_fraisMontant).g("span");
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

	public Boolean getColonnePaiementNomCourt() {
		return true;
	}

	public Boolean getColonnePaiementDate() {
		return true;
	}

	public Boolean getColonnePaiementMontant() {
		return true;
	}

	public Boolean getColonneFraisMontant() {
		return true;
	}

	public void htmlBodyFormsPaiementGenPage() {
		if(
				CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
				) {
			e("div").a("class", "w3-margin-top ").f();

			if(listePaiementmedicale != null && listePaiementmedicale.size() == 1) {
				{ e("button")
					.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-green ")
						.a("id", "rechargerCePaiementGenPage")
						.a("onclick", "patchPaiementmedicaleVals( [ {name: 'fq', value: 'pk:' + " + requeteSite_.getRequetePk() + " } ], {}, function() { ajouterLueur($('#rechargerCePaiementGenPage')); }, function() { ajouterErreur($('#rechargerCePaiementGenPage')); }); return false; ").f();
						e("i").a("class", "fas fa-sync-alt ").f().g("i");
					sx("recharger ce paiement");
				} g("button");
			}

			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-green ")
				.a("onclick", "$('#postPaiementmedicaleModale').show(); ")
				.f();
				e("i").a("class", "fas fa-file-plus ").f().g("i");
				sx("Créer un paiement");
			} g("button");
			{ e("div").a("id", "postPaiementmedicaleModale").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-green ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#postPaiementmedicaleModale').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Créer un paiement").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").f();
							Paiementmedicale o = new Paiementmedicale();
							o.setRequeteSite_(requeteSite_);

							// Form POST
							{ e("div").a("id", "postPaiementmedicaleForm").f();
								htmlFormPOSTPaiementmedicale(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-green ")
								.a("onclick", "postPaiementmedicale($('#postPaiementmedicaleForm')); ")
								.f().sx("Créer un paiement")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-green ")
				.a("onclick", "$('#putimportPaiementmedicaleModale').show(); ")
				.f();
				e("i").a("class", "fas fa-file-import ").f().g("i");
				sx("Importer paiements");
			} g("button");
			{ e("div").a("id", "putimportPaiementmedicaleModale").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-green ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#putimportPaiementmedicaleModale').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Importer paiements").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").f();
							Paiementmedicale o = new Paiementmedicale();
							o.setRequeteSite_(requeteSite_);

							// Form PUT
							{ e("div").a("id", "putimportPaiementmedicaleForm").f();
								htmlFormPUTImportPaiementmedicale(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-green ")
								.a("onclick", "putimportPaiementmedicale($('#putimportPaiementmedicaleForm')); ")
								.f().sx("Importer paiements")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-green ")
				.a("onclick", "$('#putfusionPaiementmedicaleModale').show(); ")
				.f();
				e("i").a("class", "fas fa-code-merge ").f().g("i");
				sx("Fusionner paiements");
			} g("button");
			{ e("div").a("id", "putfusionPaiementmedicaleModale").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-green ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#putfusionPaiementmedicaleModale').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Fusionner paiements").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").f();
							Paiementmedicale o = new Paiementmedicale();
							o.setRequeteSite_(requeteSite_);

							// Form PUT
							{ e("div").a("id", "putfusionPaiementmedicaleForm").f();
								htmlFormPUTFusionPaiementmedicale(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-green ")
								.a("onclick", "putfusionPaiementmedicale($('#putfusionPaiementmedicaleForm')); ")
								.f().sx("Fusionner paiements")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-green ")
				.a("onclick", "$('#putcopiePaiementmedicaleModale').show(); ")
				.f();
				e("i").a("class", "fas fa-copy ").f().g("i");
				sx("Dupliquer paiements");
			} g("button");
			{ e("div").a("id", "putcopiePaiementmedicaleModale").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-green ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#putcopiePaiementmedicaleModale').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Dupliquer paiements").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").f();
							Paiementmedicale o = new Paiementmedicale();
							o.setRequeteSite_(requeteSite_);

							// Form PUT
							{ e("div").a("id", "putcopiePaiementmedicaleForm").f();
								htmlFormPUTCopiePaiementmedicale(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-green ")
								.a("onclick", "putcopiePaiementmedicale($('#putcopiePaiementmedicaleForm'), ", paiementmedicale == null ? "null" : paiementmedicale.getPk(), "); ")
								.f().sx("Dupliquer paiements")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-green ")
				.a("onclick", "$('#patchPaiementmedicaleModale').show(); ")
				.f();
				e("i").a("class", "fas fa-edit ").f().g("i");
				sx("Modifier paiements");
			} g("button");
			{ e("div").a("id", "patchPaiementmedicaleModale").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-green ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#patchPaiementmedicaleModale').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Modifier paiements").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").f();
							Paiementmedicale o = new Paiementmedicale();
							o.setRequeteSite_(requeteSite_);

							// FormulaireValeurs PATCH
							{ e("form").a("action", "").a("id", "patchPaiementmedicaleFormulaireValeurs").a("onsubmit", "event.preventDefault(); return false; ").f();
								htmlFormPATCHPaiementmedicale(o);
							} g("form");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-green ")
								.a("onclick", "patchPaiementmedicale(null, $('#patchPaiementmedicaleFormulaireValeurs'), ", Optional.ofNullable(paiementmedicale).map(Paiementmedicale::getPk).map(a -> a.toString()).orElse("null"), ", function() {}, function() {}); ")
								.f().sx("Modifier paiements")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");

			g("div");
		}
		htmlSuggerePaiementGenPage(this, null, listePaiementmedicale);
	}

	/**
	 * Var.enUS: htmlSuggestedPaymentGenPage
	 * r: "/paiement"
	 * r.enUS: "/payment"
	 * r: "voir tous les paiements"
	 * r.enUS: "see all the payments"
	 * r: "rechargerPaiementGenPage"
	 * r.enUS: "refreshPaymentGenPage"
	 * r: "recharger tous les paiements"
	 * r.enUS: "refresh all the payments"
	 * r: "rechercher paiements : "
	 * r.enUS: "search payments: "
	 * r: "suggereFormPaiementmedicale"
	 * r.enUS: "suggestFormSchoolPayment"
	 * r: "rechercher paiements"
	 * r.enUS: "search payments"
	 * r: "suggerePaiementmedicale w3-input w3-border w3-cell w3-cell-middle "
	 * r.enUS: "suggestSchoolPayment w3-input w3-border w3-cell w3-cell-middle "
	 * r: "suggerePaiementmedicale"
	 * r.enUS: "suggestSchoolPayment"
	 * r: patchPaiementmedicaleVals
	 * r.enUS: patchSchoolPaymentVals
	 * r: ajouterLueur
	 * r.enUS: addGlow
	 * r: rechargerPaiementGenPage
	 * r.enUS: refreshPaymentGenPage
	 * r: ajouterErreur
	 * r.enUS: addError
	 * r: suggerePaiementmedicaleObjetSuggere
	 * r.enUS: suggestSchoolPaymentObjectSuggest
	 * r: textePaiementmedicaleObjetTexte
	 * r.enUS: textSchoolPaymentObjectText
	 * r: 'objetSuggere:'
	 * r.enUS: 'objectSuggest:'
	 * r: 'objetTexte:'
	 * r.enUS: 'objectText:'
	 * r: '#suggereListPaiementmedicale'
	 * r.enUS: '#suggestListSchoolPayment'
	 * r: "suggereListPaiementmedicale"
	 * r.enUS: "suggestListSchoolPayment"
	**/
	public static void htmlSuggerePaiementGenPage(MiseEnPage p, String id, ListeRecherche<Paiementmedicale> listePaiementmedicale) {
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

			Integer rows1 = Optional.ofNullable(listePaiementmedicale).map(l -> l.getRows()).orElse(10);
			Integer start1 = Optional.ofNullable(listePaiementmedicale).map(l -> l.getStart()).orElse(1);
			Integer start2 = start1 - rows1;
			Integer start3 = start1 + rows1;
			Integer rows2 = rows1 / 2;
			Integer rows3 = rows1 * 2;
			start2 = start2 < 0 ? 0 : start2;
			StringBuilder fqs = new StringBuilder();
			for(String fq : Optional.ofNullable(listePaiementmedicale).map(l -> l.getFilterQueries()).orElse(new String[0])) {
				if(!StringUtils.contains(fq, "(")) {
					String fq1 = StringUtils.substringBefore(fq, "_");
					String fq2 = StringUtils.substringAfter(fq, ":");
					if(!StringUtils.startsWithAny(fq, "classeNomsCanoniques_", "archive_", "supprime_", "sessionId", "utilisateurCles"))
						fqs.append("&fq=").append(fq1).append(":").append(fq2);
				}
			}
			StringBuilder sorts = new StringBuilder();
			for(SortClause sort : Optional.ofNullable(listePaiementmedicale).map(l -> l.getSorts()).orElse(Arrays.asList())) {
				sorts.append("&sort=").append(StringUtils.substringBefore(sort.getItem(), "_")).append(" ").append(sort.getOrder().name());
			}

			if(
					CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), PaiementGenPage.ROLES)
					|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), PaiementGenPage.ROLES)
					) {
					{ p.e("div").a("class", "").f();
						{ p.e("button").a("id", "rechargerTousPaiementGenPage", id).a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-green ").a("onclick", "patchPaiementmedicaleVals([], {}, function() { ajouterLueur($('#rechargerTousPaiementGenPage", id, "')); }, function() { ajouterErreur($('#rechargerTousPaiementGenPage", id, "')); }); ").f();
							p.e("i").a("class", "fas fa-sync-alt ").f().g("i");
							p.sx("recharger tous les paiements");
						} p.g("button");
					} p.g("div");
			}
			{ p.e("div").a("class", "w3-cell-row ").f();
				{ p.e("div").a("class", "w3-cell ").f();
					{ p.e("span").f();
						p.sx("rechercher paiements : ");
					} p.g("span");
				} p.g("div");
			} p.g("div");
			{ p.e("div").a("class", "w3-bar ").f();

				p.e("input")
					.a("type", "text")
					.a("placeholder", "rechercher paiements")
					.a("class", "suggerePaiementmedicale w3-input w3-border w3-bar-item ")
					.a("name", "suggerePaiementmedicale")
					.a("id", "suggerePaiementmedicale", id)
					.a("autocomplete", "off")
					.a("oninput", "suggerePaiementmedicaleObjetSuggere( [ { 'name': 'q', 'value': 'objetSuggere:' + $(this).val() } ], $('#suggereListPaiementmedicale", id, "'), ", p.getRequeteSite_().getRequetePk(), "); ")
					.a("onkeyup", "if (event.keyCode === 13) { event.preventDefault(); window.location.href = '/paiement?q=", query1, ":' + encodeURIComponent(this.value) + '", fqs, sorts, "&start=", start2, "&rows=", rows1, "'; }"); 
				if(listePaiementmedicale != null)
					p.a("value", query2);
				p.fg();
				{ p.e("button")
					.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-green ")
					.a("onclick", "window.location.href = '/paiement?q=", query1, ":' + encodeURIComponent(this.previousElementSibling.value) + '", fqs, sorts, "&start=", start2, "&rows=", rows1, "'; ") 
					.f();
					p.e("i").a("class", "fas fa-search ").f().g("i");
				} p.g("button");

			} p.g("div");
			{ p.e("div").a("class", "w3-cell-row ").f();
				{ p.e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
					{ p.e("ul").a("class", "w3-ul w3-hoverable ").a("id", "suggereListPaiementmedicale", id).f();
					} p.g("ul");
				} p.g("div");
			} p.g("div");
			{ p.e("div").a("class", "").f();
				{ p.e("a").a("href", "/paiement").a("class", "").f();
					p.e("i").a("class", "fas fa-search-dollar ").f().g("i");
					p.sx("voir tous les paiements");
				} p.g("a");
			} p.g("div");
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

}