package org.computate.medicale.frFR.patient;

import java.util.Arrays;
import java.util.Date;
import org.computate.medicale.frFR.requete.api.RequeteApi;
import org.apache.commons.lang3.StringUtils;
import java.lang.Integer;
import java.lang.Long;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.util.Locale;
import io.vertx.core.json.JsonObject;
import java.time.ZoneOffset;
import io.vertx.core.logging.Logger;
import org.computate.medicale.frFR.couverture.Couverture;
import java.math.MathContext;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Objects;
import java.util.List;
import org.computate.medicale.frFR.recherche.ListeRecherche;
import org.computate.medicale.frFR.requete.RequeteSiteFrFR;
import java.time.LocalDate;
import org.apache.solr.client.solrj.SolrQuery;
import java.util.Optional;
import org.apache.solr.client.solrj.util.ClientUtils;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.solr.common.SolrInputDocument;
import org.apache.commons.lang3.exception.ExceptionUtils;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.computate.medicale.frFR.cluster.Cluster;
import org.computate.medicale.frFR.inscription.InscriptionMedicale;
import org.computate.medicale.frFR.contexte.SiteContexteFrFR;
import io.vertx.core.logging.LoggerFactory;
import java.text.NumberFormat;
import java.util.ArrayList;
import org.apache.commons.collections.CollectionUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.lang.String;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.computate.medicale.frFR.ecrivain.ToutEcrivain;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.apache.solr.client.solrj.SolrClient;
import io.vertx.core.json.JsonArray;
import org.apache.solr.common.SolrDocument;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.math.NumberUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

/**	
 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.patient.PatientMedicale&fq=classeEtendGen_indexed_boolean:true">Trouver la classe patientCompleteName dans Solr</a>
 * <br/>
 **/
public abstract class PatientMedicaleGen<DEV> extends Cluster {
	protected static final Logger LOGGER = LoggerFactory.getLogger(PatientMedicale.class);

	public static final List<String> ROLES = Arrays.asList("SiteAdmin");
	public static final List<String> ROLE_READS = Arrays.asList("");

	public static final String PatientMedicale_UnNom = "un patient";
	public static final String PatientMedicale_Ce = "ce ";
	public static final String PatientMedicale_CeNom = "ce patient";
	public static final String PatientMedicale_Un = "un ";
	public static final String PatientMedicale_LeNom = "le patient";
	public static final String PatientMedicale_NomSingulier = "patient";
	public static final String PatientMedicale_NomPluriel = "patients";
	public static final String PatientMedicale_NomActuel = "patient actuel";
	public static final String PatientMedicale_Tous = "all ";
	public static final String PatientMedicale_TousNom = "tous les patients";
	public static final String PatientMedicale_RechercherTousNomPar = "rechercher patients par ";
	public static final String PatientMedicale_RechercherTousNom = "rechercher patients";
	public static final String PatientMedicale_LesNom = "les patients";
	public static final String PatientMedicale_AucunNomTrouve = "aucun patient trouvé";
	public static final String PatientMedicale_NomVar = "patient";
	public static final String PatientMedicale_DeNom = "de patient";
	public static final String PatientMedicale_NomAdjectifSingulier = "patient";
	public static final String PatientMedicale_NomAdjectifPluriel = "patients";
	public static final String PatientMedicale_Couleur = "orange";
	public static final String PatientMedicale_IconeGroupe = "regular";
	public static final String PatientMedicale_IconeNom = "patient";

	////////////////
	// patientCle //
	////////////////

	/**	L'entité « patientCle »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long patientCle;
	@JsonIgnore
	public Couverture<Long> patientCleCouverture = new Couverture<Long>().p(this).c(Long.class).var("patientCle").o(patientCle);

	/**	<br/>L'entité « patientCle »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.patient.PatientMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:patientCle">Trouver l'entité patientCle dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patientCle(Couverture<Long> c);

	public Long getPatientCle() {
		return patientCle;
	}

	public void setPatientCle(Long patientCle) {
		this.patientCle = patientCle;
		this.patientCleCouverture.dejaInitialise = true;
	}
	public PatientMedicale setPatientCle(String o) {
		if(NumberUtils.isParsable(o))
			this.patientCle = Long.parseLong(o);
		this.patientCleCouverture.dejaInitialise = true;
		return (PatientMedicale)this;
	}
	protected PatientMedicale patientCleInit() {
		if(!patientCleCouverture.dejaInitialise) {
			_patientCle(patientCleCouverture);
			if(patientCle == null)
				setPatientCle(patientCleCouverture.o);
		}
		patientCleCouverture.dejaInitialise(true);
		return (PatientMedicale)this;
	}

	public Long solrPatientCle() {
		return patientCle;
	}

	public String strPatientCle() {
		return patientCle == null ? "" : patientCle.toString();
	}

	public String jsonPatientCle() {
		return patientCle == null ? "" : patientCle.toString();
	}

	public String nomAffichagePatientCle() {
		return "clé";
	}

	public String htmTooltipPatientCle() {
		return null;
	}

	public String htmPatientCle() {
		return patientCle == null ? "" : StringEscapeUtils.escapeHtml4(strPatientCle());
	}

	/////////////////////
	// inscriptionCles //
	/////////////////////

	/**	L'entité « inscriptionCles »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> inscriptionCles = new java.util.ArrayList<java.lang.Long>();
	@JsonIgnore
	public Couverture<List<Long>> inscriptionClesCouverture = new Couverture<List<Long>>().p(this).c(List.class).var("inscriptionCles").o(inscriptionCles);

	/**	<br/>L'entité « inscriptionCles »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.patient.PatientMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptionCles">Trouver l'entité inscriptionCles dans Solr</a>
	 * <br/>
	 * @param inscriptionCles est l'entité déjà construit. 
	 **/
	protected abstract void _inscriptionCles(List<Long> o);

	public List<Long> getInscriptionCles() {
		return inscriptionCles;
	}

	public void setInscriptionCles(List<Long> inscriptionCles) {
		this.inscriptionCles = inscriptionCles;
		this.inscriptionClesCouverture.dejaInitialise = true;
	}
	public PatientMedicale addInscriptionCles(Long...objets) {
		for(Long o : objets) {
			addInscriptionCles(o);
		}
		return (PatientMedicale)this;
	}
	public PatientMedicale addInscriptionCles(Long o) {
		if(o != null && !inscriptionCles.contains(o))
			this.inscriptionCles.add(o);
		return (PatientMedicale)this;
	}
	public PatientMedicale setInscriptionCles(JsonArray objets) {
		inscriptionCles.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addInscriptionCles(o);
		}
		return (PatientMedicale)this;
	}
	public PatientMedicale addInscriptionCles(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addInscriptionCles(p);
		}
		return (PatientMedicale)this;
	}
	protected PatientMedicale inscriptionClesInit() {
		if(!inscriptionClesCouverture.dejaInitialise) {
			_inscriptionCles(inscriptionCles);
		}
		inscriptionClesCouverture.dejaInitialise(true);
		return (PatientMedicale)this;
	}

	public List<Long> solrInscriptionCles() {
		return inscriptionCles;
	}

	public String strInscriptionCles() {
		return inscriptionCles == null ? "" : inscriptionCles.toString();
	}

	public String jsonInscriptionCles() {
		return inscriptionCles == null ? "" : inscriptionCles.toString();
	}

	public String nomAffichageInscriptionCles() {
		return "inscriptions";
	}

	public String htmTooltipInscriptionCles() {
		return null;
	}

	public String htmInscriptionCles() {
		return inscriptionCles == null ? "" : StringEscapeUtils.escapeHtml4(strInscriptionCles());
	}

	public void inputInscriptionCles(String classeApiMethodeMethode) {
		PatientMedicale s = (PatientMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("i").a("class", "far fa-search w3-xxlarge w3-cell w3-cell-middle ").f().g("i");
				e("input")
					.a("type", "text")
					.a("placeholder", "inscriptions")
					.a("class", "valeur suggereInscriptionCles w3-input w3-border w3-cell w3-cell-middle ")
					.a("name", "setInscriptionCles")
					.a("id", classeApiMethodeMethode, "_inscriptionCles")
					.a("autocomplete", "off")
					.a("oninput", "suggerePatientMedicaleInscriptionCles($(this).val() ? rechercherInscriptionMedicaleFiltres($(this.parentElement)) : [", pk == null ? "" : "{'name':'fq','value':'patientCle:" + pk + "'}", "], $('#listPatientMedicaleInscriptionCles_", classeApiMethodeMethode, "'), ", pk, "); ")
				.fg();

		} else {
			sx(htmInscriptionCles());
		}
	}

	public void htmInscriptionCles(String classeApiMethodeMethode) {
		PatientMedicale s = (PatientMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "PatientMedicaleInscriptionCles").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row ").f();
							{ e("a").a("href", "?fq=patientCle:", pk).a("class", "w3-cell w3-btn w3-center h4 w3-block h4 w3-blue-gray w3-hover-blue-gray ").f();
								e("i").a("class", "fas fa-edit ").f().g("i");
								sx("inscriptions");
							} g("a");
						} g("div");
						{ e("div").a("class", "w3-cell-row ").f();
							{ e("h5").a("class", "w3-cell ").f();
								sx("relier  a ce patient");
							} g("h5");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();
								{ e("div").a("class", "w3-cell-row ").f();

								inputInscriptionCles(classeApiMethodeMethode);
								} g("div");
							} g("div");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
								{ e("ul").a("class", "w3-ul w3-hoverable ").a("id", "listPatientMedicaleInscriptionCles_", classeApiMethodeMethode).f();
								} g("ul");
								if(
										utilisateurCles.contains(requeteSite_.getUtilisateurCle())
										|| Objects.equals(sessionId, requeteSite_.getSessionId())
										|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
										|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
								) {
									{ e("div").a("class", "w3-cell-row ").f();
										e("button")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-blue-gray ")
											.a("id", classeApiMethodeMethode, "_inscriptionCles_ajouter")
											.a("onclick", "$(this).addClass('w3-disabled'); this.disabled = true; this.innerHTML = 'Envoi…'; postInscriptionMedicaleVals({ patientCle: \"", pk, "\" }, function() {}, function() { ajouterErreur($('#", classeApiMethodeMethode, "inscriptionCles')); });")
											.f().sx("ajouter une inscription")
										.g("button");
									} g("div");
								}
							} g("div");
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	////////////////
	// patientTri //
	////////////////

	/**	L'entité « patientTri »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer patientTri;
	@JsonIgnore
	public Couverture<Integer> patientTriCouverture = new Couverture<Integer>().p(this).c(Integer.class).var("patientTri").o(patientTri);

	/**	<br/>L'entité « patientTri »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.patient.PatientMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:patientTri">Trouver l'entité patientTri dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patientTri(Couverture<Integer> c);

	public Integer getPatientTri() {
		return patientTri;
	}

	public void setPatientTri(Integer patientTri) {
		this.patientTri = patientTri;
		this.patientTriCouverture.dejaInitialise = true;
	}
	public PatientMedicale setPatientTri(String o) {
		if(NumberUtils.isParsable(o))
			this.patientTri = Integer.parseInt(o);
		this.patientTriCouverture.dejaInitialise = true;
		return (PatientMedicale)this;
	}
	protected PatientMedicale patientTriInit() {
		if(!patientTriCouverture.dejaInitialise) {
			_patientTri(patientTriCouverture);
			if(patientTri == null)
				setPatientTri(patientTriCouverture.o);
		}
		patientTriCouverture.dejaInitialise(true);
		return (PatientMedicale)this;
	}

	public Integer solrPatientTri() {
		return patientTri;
	}

	public String strPatientTri() {
		return patientTri == null ? "" : patientTri.toString();
	}

	public String jsonPatientTri() {
		return patientTri == null ? "" : patientTri.toString();
	}

	public String nomAffichagePatientTri() {
		return null;
	}

	public String htmTooltipPatientTri() {
		return null;
	}

	public String htmPatientTri() {
		return patientTri == null ? "" : StringEscapeUtils.escapeHtml4(strPatientTri());
	}

	//////////////////////////
	// inscriptionRecherche //
	//////////////////////////

	/**	L'entité « inscriptionRecherche »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut ListeRecherche<InscriptionMedicale>(). 
	 */
	@JsonIgnore
	@JsonInclude(Include.NON_NULL)
	protected ListeRecherche<InscriptionMedicale> inscriptionRecherche = new ListeRecherche<InscriptionMedicale>();
	@JsonIgnore
	public Couverture<ListeRecherche<InscriptionMedicale>> inscriptionRechercheCouverture = new Couverture<ListeRecherche<InscriptionMedicale>>().p(this).c(ListeRecherche.class).var("inscriptionRecherche").o(inscriptionRecherche);

	/**	<br/>L'entité « inscriptionRecherche »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut ListeRecherche<InscriptionMedicale>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.patient.PatientMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptionRecherche">Trouver l'entité inscriptionRecherche dans Solr</a>
	 * <br/>
	 * @param inscriptionRecherche est l'entité déjà construit. 
	 **/
	protected abstract void _inscriptionRecherche(ListeRecherche<InscriptionMedicale> l);

	public ListeRecherche<InscriptionMedicale> getInscriptionRecherche() {
		return inscriptionRecherche;
	}

	public void setInscriptionRecherche(ListeRecherche<InscriptionMedicale> inscriptionRecherche) {
		this.inscriptionRecherche = inscriptionRecherche;
		this.inscriptionRechercheCouverture.dejaInitialise = true;
	}
	protected PatientMedicale inscriptionRechercheInit() {
		if(!inscriptionRechercheCouverture.dejaInitialise) {
			_inscriptionRecherche(inscriptionRecherche);
		}
		inscriptionRecherche.initLoinPourClasse(requeteSite_);
		inscriptionRechercheCouverture.dejaInitialise(true);
		return (PatientMedicale)this;
	}

	//////////////////
	// inscriptions //
	//////////////////

	/**	L'entité « inscriptions »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<InscriptionMedicale>(). 
	 */
	@JsonIgnore
	@JsonInclude(Include.NON_NULL)
	protected List<InscriptionMedicale> inscriptions = new java.util.ArrayList<org.computate.medicale.frFR.inscription.InscriptionMedicale>();
	@JsonIgnore
	public Couverture<List<InscriptionMedicale>> inscriptionsCouverture = new Couverture<List<InscriptionMedicale>>().p(this).c(List.class).var("inscriptions").o(inscriptions);

	/**	<br/>L'entité « inscriptions »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<InscriptionMedicale>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.patient.PatientMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptions">Trouver l'entité inscriptions dans Solr</a>
	 * <br/>
	 * @param inscriptions est l'entité déjà construit. 
	 **/
	protected abstract void _inscriptions(List<InscriptionMedicale> l);

	public List<InscriptionMedicale> getInscriptions() {
		return inscriptions;
	}

	public void setInscriptions(List<InscriptionMedicale> inscriptions) {
		this.inscriptions = inscriptions;
		this.inscriptionsCouverture.dejaInitialise = true;
	}
	public PatientMedicale addInscriptions(InscriptionMedicale...objets) {
		for(InscriptionMedicale o : objets) {
			addInscriptions(o);
		}
		return (PatientMedicale)this;
	}
	public PatientMedicale addInscriptions(InscriptionMedicale o) {
		if(o != null && !inscriptions.contains(o))
			this.inscriptions.add(o);
		return (PatientMedicale)this;
	}
	protected PatientMedicale inscriptionsInit() {
		if(!inscriptionsCouverture.dejaInitialise) {
			_inscriptions(inscriptions);
		}
		inscriptionsCouverture.dejaInitialise(true);
		return (PatientMedicale)this;
	}

	/////////////////////
	// utilisateurCles //
	/////////////////////

	/**	L'entité « utilisateurCles »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> utilisateurCles = new java.util.ArrayList<java.lang.Long>();
	@JsonIgnore
	public Couverture<List<Long>> utilisateurClesCouverture = new Couverture<List<Long>>().p(this).c(List.class).var("utilisateurCles").o(utilisateurCles);

	/**	<br/>L'entité « utilisateurCles »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.patient.PatientMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:utilisateurCles">Trouver l'entité utilisateurCles dans Solr</a>
	 * <br/>
	 * @param utilisateurCles est l'entité déjà construit. 
	 **/
	protected abstract void _utilisateurCles(List<Long> l);

	public List<Long> getUtilisateurCles() {
		return utilisateurCles;
	}

	public void setUtilisateurCles(List<Long> utilisateurCles) {
		this.utilisateurCles = utilisateurCles;
		this.utilisateurClesCouverture.dejaInitialise = true;
	}
	public PatientMedicale addUtilisateurCles(Long...objets) {
		for(Long o : objets) {
			addUtilisateurCles(o);
		}
		return (PatientMedicale)this;
	}
	public PatientMedicale addUtilisateurCles(Long o) {
		if(o != null && !utilisateurCles.contains(o))
			this.utilisateurCles.add(o);
		return (PatientMedicale)this;
	}
	public PatientMedicale setUtilisateurCles(JsonArray objets) {
		utilisateurCles.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addUtilisateurCles(o);
		}
		return (PatientMedicale)this;
	}
	public PatientMedicale addUtilisateurCles(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addUtilisateurCles(p);
		}
		return (PatientMedicale)this;
	}
	protected PatientMedicale utilisateurClesInit() {
		if(!utilisateurClesCouverture.dejaInitialise) {
			_utilisateurCles(utilisateurCles);
		}
		utilisateurClesCouverture.dejaInitialise(true);
		return (PatientMedicale)this;
	}

	public List<Long> solrUtilisateurCles() {
		return utilisateurCles;
	}

	public String strUtilisateurCles() {
		return utilisateurCles == null ? "" : utilisateurCles.toString();
	}

	public String jsonUtilisateurCles() {
		return utilisateurCles == null ? "" : utilisateurCles.toString();
	}

	public String nomAffichageUtilisateurCles() {
		return null;
	}

	public String htmTooltipUtilisateurCles() {
		return null;
	}

	public String htmUtilisateurCles() {
		return utilisateurCles == null ? "" : StringEscapeUtils.escapeHtml4(strUtilisateurCles());
	}

	//////////////////
	// cliniqueCles //
	//////////////////

	/**	L'entité « cliniqueCles »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> cliniqueCles = new java.util.ArrayList<java.lang.Long>();
	@JsonIgnore
	public Couverture<List<Long>> cliniqueClesCouverture = new Couverture<List<Long>>().p(this).c(List.class).var("cliniqueCles").o(cliniqueCles);

	/**	<br/>L'entité « cliniqueCles »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.patient.PatientMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:cliniqueCles">Trouver l'entité cliniqueCles dans Solr</a>
	 * <br/>
	 * @param cliniqueCles est l'entité déjà construit. 
	 **/
	protected abstract void _cliniqueCles(List<Long> l);

	public List<Long> getCliniqueCles() {
		return cliniqueCles;
	}

	public void setCliniqueCles(List<Long> cliniqueCles) {
		this.cliniqueCles = cliniqueCles;
		this.cliniqueClesCouverture.dejaInitialise = true;
	}
	public PatientMedicale addCliniqueCles(Long...objets) {
		for(Long o : objets) {
			addCliniqueCles(o);
		}
		return (PatientMedicale)this;
	}
	public PatientMedicale addCliniqueCles(Long o) {
		if(o != null && !cliniqueCles.contains(o))
			this.cliniqueCles.add(o);
		return (PatientMedicale)this;
	}
	public PatientMedicale setCliniqueCles(JsonArray objets) {
		cliniqueCles.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addCliniqueCles(o);
		}
		return (PatientMedicale)this;
	}
	public PatientMedicale addCliniqueCles(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addCliniqueCles(p);
		}
		return (PatientMedicale)this;
	}
	protected PatientMedicale cliniqueClesInit() {
		if(!cliniqueClesCouverture.dejaInitialise) {
			_cliniqueCles(cliniqueCles);
		}
		cliniqueClesCouverture.dejaInitialise(true);
		return (PatientMedicale)this;
	}

	public List<Long> solrCliniqueCles() {
		return cliniqueCles;
	}

	public String strCliniqueCles() {
		return cliniqueCles == null ? "" : cliniqueCles.toString();
	}

	public String jsonCliniqueCles() {
		return cliniqueCles == null ? "" : cliniqueCles.toString();
	}

	public String nomAffichageCliniqueCles() {
		return "écoles";
	}

	public String htmTooltipCliniqueCles() {
		return null;
	}

	public String htmCliniqueCles() {
		return cliniqueCles == null ? "" : StringEscapeUtils.escapeHtml4(strCliniqueCles());
	}

	////////////////////
	// personnePrenom //
	////////////////////

	/**	L'entité « personnePrenom »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String personnePrenom;
	@JsonIgnore
	public Couverture<String> personnePrenomCouverture = new Couverture<String>().p(this).c(String.class).var("personnePrenom").o(personnePrenom);

	/**	<br/>L'entité « personnePrenom »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.patient.PatientMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:personnePrenom">Trouver l'entité personnePrenom dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _personnePrenom(Couverture<String> c);

	public String getPersonnePrenom() {
		return personnePrenom;
	}

	public void setPersonnePrenom(String personnePrenom) {
		this.personnePrenom = personnePrenom;
		this.personnePrenomCouverture.dejaInitialise = true;
	}
	protected PatientMedicale personnePrenomInit() {
		if(!personnePrenomCouverture.dejaInitialise) {
			_personnePrenom(personnePrenomCouverture);
			if(personnePrenom == null)
				setPersonnePrenom(personnePrenomCouverture.o);
		}
		personnePrenomCouverture.dejaInitialise(true);
		return (PatientMedicale)this;
	}

	public String solrPersonnePrenom() {
		return personnePrenom;
	}

	public String strPersonnePrenom() {
		return personnePrenom == null ? "" : personnePrenom;
	}

	public String jsonPersonnePrenom() {
		return personnePrenom == null ? "" : personnePrenom;
	}

	public String nomAffichagePersonnePrenom() {
		return "prénom";
	}

	public String htmTooltipPersonnePrenom() {
		return null;
	}

	public String htmPersonnePrenom() {
		return personnePrenom == null ? "" : StringEscapeUtils.escapeHtml4(strPersonnePrenom());
	}

	public void inputPersonnePrenom(String classeApiMethodeMethode) {
		PatientMedicale s = (PatientMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("placeholder", "prénom")
				.a("id", classeApiMethodeMethode, "_personnePrenom");
				if("Page".equals(classeApiMethodeMethode) || "PATCH".equals(classeApiMethodeMethode)) {
					a("class", "setPersonnePrenom classPatientMedicale inputPatientMedicale", pk, "PersonnePrenom w3-input w3-border ");
					a("name", "setPersonnePrenom");
				} else {
					a("class", "valeurPersonnePrenom w3-input w3-border classPatientMedicale inputPatientMedicale", pk, "PersonnePrenom w3-input w3-border ");
					a("name", "personnePrenom");
				}
				if("Page".equals(classeApiMethodeMethode)) {
					a("onclick", "enleverLueur($(this)); ");
					a("onchange", "patchPatientMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setPersonnePrenom', $(this).val(), function() { ajouterLueur($('#", classeApiMethodeMethode, "_personnePrenom')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_personnePrenom')); }); ");
				}
				a("value", strPersonnePrenom())
			.fg();

		} else {
			sx(htmPersonnePrenom());
		}
	}

	public void htmPersonnePrenom(String classeApiMethodeMethode) {
		PatientMedicale s = (PatientMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "PatientMedicalePersonnePrenom").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-orange ").f();
							e("label").a("for", classeApiMethodeMethode, "_personnePrenom").a("class", "").f().sx("prénom").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputPersonnePrenom(classeApiMethodeMethode);
							} g("div");
							if(
									utilisateurCles.contains(requeteSite_.getUtilisateurCle())
									|| Objects.equals(sessionId, requeteSite_.getSessionId())
									|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
									|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
							) {
								if("Page".equals(classeApiMethodeMethode)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-orange ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_personnePrenom')); $('#", classeApiMethodeMethode, "_personnePrenom').val(null); patchPatientMedicaleVal([{ name: 'fq', value: 'pk:' + $('#PatientMedicaleForm :input[name=pk]').val() }], 'setPersonnePrenom', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_personnePrenom')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_personnePrenom')); }); ")
											.f();
											e("i").a("class", "far fa-eraser ").f().g("i");
										} g("button");
									} g("div");
								}
							}
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	///////////////////////////
	// personnePrenomPrefere //
	///////////////////////////

	/**	L'entité « personnePrenomPrefere »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String personnePrenomPrefere;
	@JsonIgnore
	public Couverture<String> personnePrenomPrefereCouverture = new Couverture<String>().p(this).c(String.class).var("personnePrenomPrefere").o(personnePrenomPrefere);

	/**	<br/>L'entité « personnePrenomPrefere »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.patient.PatientMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:personnePrenomPrefere">Trouver l'entité personnePrenomPrefere dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _personnePrenomPrefere(Couverture<String> c);

	public String getPersonnePrenomPrefere() {
		return personnePrenomPrefere;
	}

	public void setPersonnePrenomPrefere(String personnePrenomPrefere) {
		this.personnePrenomPrefere = personnePrenomPrefere;
		this.personnePrenomPrefereCouverture.dejaInitialise = true;
	}
	protected PatientMedicale personnePrenomPrefereInit() {
		if(!personnePrenomPrefereCouverture.dejaInitialise) {
			_personnePrenomPrefere(personnePrenomPrefereCouverture);
			if(personnePrenomPrefere == null)
				setPersonnePrenomPrefere(personnePrenomPrefereCouverture.o);
		}
		personnePrenomPrefereCouverture.dejaInitialise(true);
		return (PatientMedicale)this;
	}

	public String solrPersonnePrenomPrefere() {
		return personnePrenomPrefere;
	}

	public String strPersonnePrenomPrefere() {
		return personnePrenomPrefere == null ? "" : personnePrenomPrefere;
	}

	public String jsonPersonnePrenomPrefere() {
		return personnePrenomPrefere == null ? "" : personnePrenomPrefere;
	}

	public String nomAffichagePersonnePrenomPrefere() {
		return "prénom préferé";
	}

	public String htmTooltipPersonnePrenomPrefere() {
		return null;
	}

	public String htmPersonnePrenomPrefere() {
		return personnePrenomPrefere == null ? "" : StringEscapeUtils.escapeHtml4(strPersonnePrenomPrefere());
	}

	public void inputPersonnePrenomPrefere(String classeApiMethodeMethode) {
		PatientMedicale s = (PatientMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("placeholder", "prénom préferé")
				.a("id", classeApiMethodeMethode, "_personnePrenomPrefere");
				if("Page".equals(classeApiMethodeMethode) || "PATCH".equals(classeApiMethodeMethode)) {
					a("class", "setPersonnePrenomPrefere classPatientMedicale inputPatientMedicale", pk, "PersonnePrenomPrefere w3-input w3-border ");
					a("name", "setPersonnePrenomPrefere");
				} else {
					a("class", "valeurPersonnePrenomPrefere w3-input w3-border classPatientMedicale inputPatientMedicale", pk, "PersonnePrenomPrefere w3-input w3-border ");
					a("name", "personnePrenomPrefere");
				}
				if("Page".equals(classeApiMethodeMethode)) {
					a("onclick", "enleverLueur($(this)); ");
					a("onchange", "patchPatientMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setPersonnePrenomPrefere', $(this).val(), function() { ajouterLueur($('#", classeApiMethodeMethode, "_personnePrenomPrefere')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_personnePrenomPrefere')); }); ");
				}
				a("value", strPersonnePrenomPrefere())
			.fg();

		} else {
			sx(htmPersonnePrenomPrefere());
		}
	}

	public void htmPersonnePrenomPrefere(String classeApiMethodeMethode) {
		PatientMedicale s = (PatientMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "PatientMedicalePersonnePrenomPrefere").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-orange ").f();
							e("label").a("for", classeApiMethodeMethode, "_personnePrenomPrefere").a("class", "").f().sx("prénom préferé").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputPersonnePrenomPrefere(classeApiMethodeMethode);
							} g("div");
							if(
									utilisateurCles.contains(requeteSite_.getUtilisateurCle())
									|| Objects.equals(sessionId, requeteSite_.getSessionId())
									|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
									|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
							) {
								if("Page".equals(classeApiMethodeMethode)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-orange ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_personnePrenomPrefere')); $('#", classeApiMethodeMethode, "_personnePrenomPrefere').val(null); patchPatientMedicaleVal([{ name: 'fq', value: 'pk:' + $('#PatientMedicaleForm :input[name=pk]').val() }], 'setPersonnePrenomPrefere', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_personnePrenomPrefere')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_personnePrenomPrefere')); }); ")
											.f();
											e("i").a("class", "far fa-eraser ").f().g("i");
										} g("button");
									} g("div");
								}
							}
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	////////////////
	// familleNom //
	////////////////

	/**	L'entité « familleNom »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String familleNom;
	@JsonIgnore
	public Couverture<String> familleNomCouverture = new Couverture<String>().p(this).c(String.class).var("familleNom").o(familleNom);

	/**	<br/>L'entité « familleNom »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.patient.PatientMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:familleNom">Trouver l'entité familleNom dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _familleNom(Couverture<String> c);

	public String getFamilleNom() {
		return familleNom;
	}

	public void setFamilleNom(String familleNom) {
		this.familleNom = familleNom;
		this.familleNomCouverture.dejaInitialise = true;
	}
	protected PatientMedicale familleNomInit() {
		if(!familleNomCouverture.dejaInitialise) {
			_familleNom(familleNomCouverture);
			if(familleNom == null)
				setFamilleNom(familleNomCouverture.o);
		}
		familleNomCouverture.dejaInitialise(true);
		return (PatientMedicale)this;
	}

	public String solrFamilleNom() {
		return familleNom;
	}

	public String strFamilleNom() {
		return familleNom == null ? "" : familleNom;
	}

	public String jsonFamilleNom() {
		return familleNom == null ? "" : familleNom;
	}

	public String nomAffichageFamilleNom() {
		return "nom de famille";
	}

	public String htmTooltipFamilleNom() {
		return null;
	}

	public String htmFamilleNom() {
		return familleNom == null ? "" : StringEscapeUtils.escapeHtml4(strFamilleNom());
	}

	public void inputFamilleNom(String classeApiMethodeMethode) {
		PatientMedicale s = (PatientMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("placeholder", "nom de famille")
				.a("id", classeApiMethodeMethode, "_familleNom");
				if("Page".equals(classeApiMethodeMethode) || "PATCH".equals(classeApiMethodeMethode)) {
					a("class", "setFamilleNom classPatientMedicale inputPatientMedicale", pk, "FamilleNom w3-input w3-border ");
					a("name", "setFamilleNom");
				} else {
					a("class", "valeurFamilleNom w3-input w3-border classPatientMedicale inputPatientMedicale", pk, "FamilleNom w3-input w3-border ");
					a("name", "familleNom");
				}
				if("Page".equals(classeApiMethodeMethode)) {
					a("onclick", "enleverLueur($(this)); ");
					a("onchange", "patchPatientMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setFamilleNom', $(this).val(), function() { ajouterLueur($('#", classeApiMethodeMethode, "_familleNom')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_familleNom')); }); ");
				}
				a("value", strFamilleNom())
			.fg();

		} else {
			sx(htmFamilleNom());
		}
	}

	public void htmFamilleNom(String classeApiMethodeMethode) {
		PatientMedicale s = (PatientMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "PatientMedicaleFamilleNom").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-orange ").f();
							e("label").a("for", classeApiMethodeMethode, "_familleNom").a("class", "").f().sx("nom de famille").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputFamilleNom(classeApiMethodeMethode);
							} g("div");
							if(
									utilisateurCles.contains(requeteSite_.getUtilisateurCle())
									|| Objects.equals(sessionId, requeteSite_.getSessionId())
									|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
									|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
							) {
								if("Page".equals(classeApiMethodeMethode)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-orange ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_familleNom')); $('#", classeApiMethodeMethode, "_familleNom').val(null); patchPatientMedicaleVal([{ name: 'fq', value: 'pk:' + $('#PatientMedicaleForm :input[name=pk]').val() }], 'setFamilleNom', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_familleNom')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_familleNom')); }); ")
											.f();
											e("i").a("class", "far fa-eraser ").f().g("i");
										} g("button");
									} g("div");
								}
							}
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	////////////////////////
	// personneNomComplet //
	////////////////////////

	/**	L'entité « personneNomComplet »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String personneNomComplet;
	@JsonIgnore
	public Couverture<String> personneNomCompletCouverture = new Couverture<String>().p(this).c(String.class).var("personneNomComplet").o(personneNomComplet);

	/**	<br/>L'entité « personneNomComplet »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.patient.PatientMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:personneNomComplet">Trouver l'entité personneNomComplet dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _personneNomComplet(Couverture<String> c);

	public String getPersonneNomComplet() {
		return personneNomComplet;
	}

	public void setPersonneNomComplet(String personneNomComplet) {
		this.personneNomComplet = personneNomComplet;
		this.personneNomCompletCouverture.dejaInitialise = true;
	}
	protected PatientMedicale personneNomCompletInit() {
		if(!personneNomCompletCouverture.dejaInitialise) {
			_personneNomComplet(personneNomCompletCouverture);
			if(personneNomComplet == null)
				setPersonneNomComplet(personneNomCompletCouverture.o);
		}
		personneNomCompletCouverture.dejaInitialise(true);
		return (PatientMedicale)this;
	}

	public String solrPersonneNomComplet() {
		return personneNomComplet;
	}

	public String strPersonneNomComplet() {
		return personneNomComplet == null ? "" : personneNomComplet;
	}

	public String jsonPersonneNomComplet() {
		return personneNomComplet == null ? "" : personneNomComplet;
	}

	public String nomAffichagePersonneNomComplet() {
		return "nom complèt";
	}

	public String htmTooltipPersonneNomComplet() {
		return null;
	}

	public String htmPersonneNomComplet() {
		return personneNomComplet == null ? "" : StringEscapeUtils.escapeHtml4(strPersonneNomComplet());
	}

	///////////////////////////////
	// personneNomCompletPrefere //
	///////////////////////////////

	/**	L'entité « personneNomCompletPrefere »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String personneNomCompletPrefere;
	@JsonIgnore
	public Couverture<String> personneNomCompletPrefereCouverture = new Couverture<String>().p(this).c(String.class).var("personneNomCompletPrefere").o(personneNomCompletPrefere);

	/**	<br/>L'entité « personneNomCompletPrefere »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.patient.PatientMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:personneNomCompletPrefere">Trouver l'entité personneNomCompletPrefere dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _personneNomCompletPrefere(Couverture<String> c);

	public String getPersonneNomCompletPrefere() {
		return personneNomCompletPrefere;
	}

	public void setPersonneNomCompletPrefere(String personneNomCompletPrefere) {
		this.personneNomCompletPrefere = personneNomCompletPrefere;
		this.personneNomCompletPrefereCouverture.dejaInitialise = true;
	}
	protected PatientMedicale personneNomCompletPrefereInit() {
		if(!personneNomCompletPrefereCouverture.dejaInitialise) {
			_personneNomCompletPrefere(personneNomCompletPrefereCouverture);
			if(personneNomCompletPrefere == null)
				setPersonneNomCompletPrefere(personneNomCompletPrefereCouverture.o);
		}
		personneNomCompletPrefereCouverture.dejaInitialise(true);
		return (PatientMedicale)this;
	}

	public String solrPersonneNomCompletPrefere() {
		return personneNomCompletPrefere;
	}

	public String strPersonneNomCompletPrefere() {
		return personneNomCompletPrefere == null ? "" : personneNomCompletPrefere;
	}

	public String jsonPersonneNomCompletPrefere() {
		return personneNomCompletPrefere == null ? "" : personneNomCompletPrefere;
	}

	public String nomAffichagePersonneNomCompletPrefere() {
		return "nom complèt préferé";
	}

	public String htmTooltipPersonneNomCompletPrefere() {
		return null;
	}

	public String htmPersonneNomCompletPrefere() {
		return personneNomCompletPrefere == null ? "" : StringEscapeUtils.escapeHtml4(strPersonneNomCompletPrefere());
	}

	///////////////////////
	// personneNomFormel //
	///////////////////////

	/**	L'entité « personneNomFormel »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String personneNomFormel;
	@JsonIgnore
	public Couverture<String> personneNomFormelCouverture = new Couverture<String>().p(this).c(String.class).var("personneNomFormel").o(personneNomFormel);

	/**	<br/>L'entité « personneNomFormel »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.patient.PatientMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:personneNomFormel">Trouver l'entité personneNomFormel dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _personneNomFormel(Couverture<String> c);

	public String getPersonneNomFormel() {
		return personneNomFormel;
	}

	public void setPersonneNomFormel(String personneNomFormel) {
		this.personneNomFormel = personneNomFormel;
		this.personneNomFormelCouverture.dejaInitialise = true;
	}
	protected PatientMedicale personneNomFormelInit() {
		if(!personneNomFormelCouverture.dejaInitialise) {
			_personneNomFormel(personneNomFormelCouverture);
			if(personneNomFormel == null)
				setPersonneNomFormel(personneNomFormelCouverture.o);
		}
		personneNomFormelCouverture.dejaInitialise(true);
		return (PatientMedicale)this;
	}

	public String solrPersonneNomFormel() {
		return personneNomFormel;
	}

	public String strPersonneNomFormel() {
		return personneNomFormel == null ? "" : personneNomFormel;
	}

	public String jsonPersonneNomFormel() {
		return personneNomFormel == null ? "" : personneNomFormel;
	}

	public String nomAffichagePersonneNomFormel() {
		return "nom formel";
	}

	public String htmTooltipPersonneNomFormel() {
		return null;
	}

	public String htmPersonneNomFormel() {
		return personneNomFormel == null ? "" : StringEscapeUtils.escapeHtml4(strPersonneNomFormel());
	}

	///////////////////////////
	// personneDateNaissance //
	///////////////////////////

	/**	L'entité « personneDateNaissance »
	 *	 is defined as null before being initialized. 
	 */
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	protected LocalDate personneDateNaissance;
	@JsonIgnore
	public Couverture<LocalDate> personneDateNaissanceCouverture = new Couverture<LocalDate>().p(this).c(LocalDate.class).var("personneDateNaissance").o(personneDateNaissance);

	/**	<br/>L'entité « personneDateNaissance »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.patient.PatientMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:personneDateNaissance">Trouver l'entité personneDateNaissance dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _personneDateNaissance(Couverture<LocalDate> c);

	public LocalDate getPersonneDateNaissance() {
		return personneDateNaissance;
	}

	public void setPersonneDateNaissance(LocalDate personneDateNaissance) {
		this.personneDateNaissance = personneDateNaissance;
		this.personneDateNaissanceCouverture.dejaInitialise = true;
	}
	public PatientMedicale setPersonneDateNaissance(Instant o) {
		this.personneDateNaissance = o == null ? null : LocalDate.from(o);
		this.personneDateNaissanceCouverture.dejaInitialise = true;
		return (PatientMedicale)this;
	}
	/** Example: 2011-12-03+01:00 **/
	public PatientMedicale setPersonneDateNaissance(String o) {
		this.personneDateNaissance = o == null ? null : LocalDate.parse(o, DateTimeFormatter.ISO_DATE);
		this.personneDateNaissanceCouverture.dejaInitialise = true;
		return (PatientMedicale)this;
	}
	public PatientMedicale setPersonneDateNaissance(Date o) {
		this.personneDateNaissance = o == null ? null : o.toInstant().atZone(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toLocalDate();
		this.personneDateNaissanceCouverture.dejaInitialise = true;
		return (PatientMedicale)this;
	}
	protected PatientMedicale personneDateNaissanceInit() {
		if(!personneDateNaissanceCouverture.dejaInitialise) {
			_personneDateNaissance(personneDateNaissanceCouverture);
			if(personneDateNaissance == null)
				setPersonneDateNaissance(personneDateNaissanceCouverture.o);
		}
		personneDateNaissanceCouverture.dejaInitialise(true);
		return (PatientMedicale)this;
	}

	public Date solrPersonneDateNaissance() {
		return personneDateNaissance == null ? null : Date.from(personneDateNaissance.atStartOfDay(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toInstant().atZone(ZoneId.of("Z")).toInstant());
	}

	public String strPersonneDateNaissance() {
		return personneDateNaissance == null ? "" : personneDateNaissance.format(DateTimeFormatter.ofPattern("EEE d MMM yyyy", Locale.forLanguageTag("fr-FR")));
	}

	public String jsonPersonneDateNaissance() {
		return personneDateNaissance == null ? "" : personneDateNaissance.format(DateTimeFormatter.ISO_DATE);
	}

	public String nomAffichagePersonneDateNaissance() {
		return "date de naissance";
	}

	public String htmTooltipPersonneDateNaissance() {
		return null;
	}

	public String htmPersonneDateNaissance() {
		return personneDateNaissance == null ? "" : StringEscapeUtils.escapeHtml4(strPersonneDateNaissance());
	}

	public void inputPersonneDateNaissance(String classeApiMethodeMethode) {
		PatientMedicale s = (PatientMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("class", "w3-input w3-border datepicker setPersonneDateNaissance classPatientMedicale inputPatientMedicale", pk, "PersonneDateNaissance w3-input w3-border ")
				.a("placeholder", "DD-MM-YYYY")
				.a("data-timeformat", "dd-MM-yyyy")
				.a("id", classeApiMethodeMethode, "_personneDateNaissance")
				.a("onclick", "enleverLueur($(this)); ")
				.a("value", personneDateNaissance == null ? "" : DateTimeFormatter.ofPattern("dd-MM-yyyy").format(personneDateNaissance))
				.a("onchange", "var t = moment(this.value, 'DD-MM-YYYY'); if(t) { var s = t.format('YYYY-MM-DD'); patchPatientMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setPersonneDateNaissance', s, function() { ajouterLueur($('#", classeApiMethodeMethode, "_personneDateNaissance')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_personneDateNaissance')); }); } ")
				.fg();
		} else {
			sx(htmPersonneDateNaissance());
		}
	}

	public void htmPersonneDateNaissance(String classeApiMethodeMethode) {
		PatientMedicale s = (PatientMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "PatientMedicalePersonneDateNaissance").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-orange ").f();
							e("label").a("for", classeApiMethodeMethode, "_personneDateNaissance").a("class", "").f().sx("date de naissance").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row  ").f();
							{ e("div").a("class", "w3-cell ").f();
								inputPersonneDateNaissance(classeApiMethodeMethode);
							} g("div");
							if(
									utilisateurCles.contains(requeteSite_.getUtilisateurCle())
									|| Objects.equals(sessionId, requeteSite_.getSessionId())
									|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
									|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
							) {
								if("Page".equals(classeApiMethodeMethode)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-orange ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_personneDateNaissance')); $('#", classeApiMethodeMethode, "_personneDateNaissance').val(null); patchPatientMedicaleVal([{ name: 'fq', value: 'pk:' + $('#PatientMedicaleForm :input[name=pk]').val() }], 'setPersonneDateNaissance', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_personneDateNaissance')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_personneDateNaissance')); }); ")
											.f();
											e("i").a("class", "far fa-eraser ").f().g("i");
										} g("button");
									} g("div");
								}
							}
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	/////////////////////////////////
	// personneDateNaissanceDAnnee //
	/////////////////////////////////

	/**	L'entité « personneDateNaissanceDAnnee »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer personneDateNaissanceDAnnee;
	@JsonIgnore
	public Couverture<Integer> personneDateNaissanceDAnneeCouverture = new Couverture<Integer>().p(this).c(Integer.class).var("personneDateNaissanceDAnnee").o(personneDateNaissanceDAnnee);

	/**	<br/>L'entité « personneDateNaissanceDAnnee »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.patient.PatientMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:personneDateNaissanceDAnnee">Trouver l'entité personneDateNaissanceDAnnee dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _personneDateNaissanceDAnnee(Couverture<Integer> c);

	public Integer getPersonneDateNaissanceDAnnee() {
		return personneDateNaissanceDAnnee;
	}

	public void setPersonneDateNaissanceDAnnee(Integer personneDateNaissanceDAnnee) {
		this.personneDateNaissanceDAnnee = personneDateNaissanceDAnnee;
		this.personneDateNaissanceDAnneeCouverture.dejaInitialise = true;
	}
	public PatientMedicale setPersonneDateNaissanceDAnnee(String o) {
		if(NumberUtils.isParsable(o))
			this.personneDateNaissanceDAnnee = Integer.parseInt(o);
		this.personneDateNaissanceDAnneeCouverture.dejaInitialise = true;
		return (PatientMedicale)this;
	}
	protected PatientMedicale personneDateNaissanceDAnneeInit() {
		if(!personneDateNaissanceDAnneeCouverture.dejaInitialise) {
			_personneDateNaissanceDAnnee(personneDateNaissanceDAnneeCouverture);
			if(personneDateNaissanceDAnnee == null)
				setPersonneDateNaissanceDAnnee(personneDateNaissanceDAnneeCouverture.o);
		}
		personneDateNaissanceDAnneeCouverture.dejaInitialise(true);
		return (PatientMedicale)this;
	}

	public Integer solrPersonneDateNaissanceDAnnee() {
		return personneDateNaissanceDAnnee;
	}

	public String strPersonneDateNaissanceDAnnee() {
		return personneDateNaissanceDAnnee == null ? "" : personneDateNaissanceDAnnee.toString();
	}

	public String jsonPersonneDateNaissanceDAnnee() {
		return personneDateNaissanceDAnnee == null ? "" : personneDateNaissanceDAnnee.toString();
	}

	public String nomAffichagePersonneDateNaissanceDAnnee() {
		return null;
	}

	public String htmTooltipPersonneDateNaissanceDAnnee() {
		return null;
	}

	public String htmPersonneDateNaissanceDAnnee() {
		return personneDateNaissanceDAnnee == null ? "" : StringEscapeUtils.escapeHtml4(strPersonneDateNaissanceDAnnee());
	}

	/////////////////////////////////////
	// personneDateNaissanceMoisDAnnee //
	/////////////////////////////////////

	/**	L'entité « personneDateNaissanceMoisDAnnee »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String personneDateNaissanceMoisDAnnee;
	@JsonIgnore
	public Couverture<String> personneDateNaissanceMoisDAnneeCouverture = new Couverture<String>().p(this).c(String.class).var("personneDateNaissanceMoisDAnnee").o(personneDateNaissanceMoisDAnnee);

	/**	<br/>L'entité « personneDateNaissanceMoisDAnnee »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.patient.PatientMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:personneDateNaissanceMoisDAnnee">Trouver l'entité personneDateNaissanceMoisDAnnee dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _personneDateNaissanceMoisDAnnee(Couverture<String> c);

	public String getPersonneDateNaissanceMoisDAnnee() {
		return personneDateNaissanceMoisDAnnee;
	}

	public void setPersonneDateNaissanceMoisDAnnee(String personneDateNaissanceMoisDAnnee) {
		this.personneDateNaissanceMoisDAnnee = personneDateNaissanceMoisDAnnee;
		this.personneDateNaissanceMoisDAnneeCouverture.dejaInitialise = true;
	}
	protected PatientMedicale personneDateNaissanceMoisDAnneeInit() {
		if(!personneDateNaissanceMoisDAnneeCouverture.dejaInitialise) {
			_personneDateNaissanceMoisDAnnee(personneDateNaissanceMoisDAnneeCouverture);
			if(personneDateNaissanceMoisDAnnee == null)
				setPersonneDateNaissanceMoisDAnnee(personneDateNaissanceMoisDAnneeCouverture.o);
		}
		personneDateNaissanceMoisDAnneeCouverture.dejaInitialise(true);
		return (PatientMedicale)this;
	}

	public String solrPersonneDateNaissanceMoisDAnnee() {
		return personneDateNaissanceMoisDAnnee;
	}

	public String strPersonneDateNaissanceMoisDAnnee() {
		return personneDateNaissanceMoisDAnnee == null ? "" : personneDateNaissanceMoisDAnnee;
	}

	public String jsonPersonneDateNaissanceMoisDAnnee() {
		return personneDateNaissanceMoisDAnnee == null ? "" : personneDateNaissanceMoisDAnnee;
	}

	public String nomAffichagePersonneDateNaissanceMoisDAnnee() {
		return null;
	}

	public String htmTooltipPersonneDateNaissanceMoisDAnnee() {
		return null;
	}

	public String htmPersonneDateNaissanceMoisDAnnee() {
		return personneDateNaissanceMoisDAnnee == null ? "" : StringEscapeUtils.escapeHtml4(strPersonneDateNaissanceMoisDAnnee());
	}

	////////////////////////////////////////
	// personneDateNaissanceJourDeSemaine //
	////////////////////////////////////////

	/**	L'entité « personneDateNaissanceJourDeSemaine »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String personneDateNaissanceJourDeSemaine;
	@JsonIgnore
	public Couverture<String> personneDateNaissanceJourDeSemaineCouverture = new Couverture<String>().p(this).c(String.class).var("personneDateNaissanceJourDeSemaine").o(personneDateNaissanceJourDeSemaine);

	/**	<br/>L'entité « personneDateNaissanceJourDeSemaine »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.patient.PatientMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:personneDateNaissanceJourDeSemaine">Trouver l'entité personneDateNaissanceJourDeSemaine dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _personneDateNaissanceJourDeSemaine(Couverture<String> c);

	public String getPersonneDateNaissanceJourDeSemaine() {
		return personneDateNaissanceJourDeSemaine;
	}

	public void setPersonneDateNaissanceJourDeSemaine(String personneDateNaissanceJourDeSemaine) {
		this.personneDateNaissanceJourDeSemaine = personneDateNaissanceJourDeSemaine;
		this.personneDateNaissanceJourDeSemaineCouverture.dejaInitialise = true;
	}
	protected PatientMedicale personneDateNaissanceJourDeSemaineInit() {
		if(!personneDateNaissanceJourDeSemaineCouverture.dejaInitialise) {
			_personneDateNaissanceJourDeSemaine(personneDateNaissanceJourDeSemaineCouverture);
			if(personneDateNaissanceJourDeSemaine == null)
				setPersonneDateNaissanceJourDeSemaine(personneDateNaissanceJourDeSemaineCouverture.o);
		}
		personneDateNaissanceJourDeSemaineCouverture.dejaInitialise(true);
		return (PatientMedicale)this;
	}

	public String solrPersonneDateNaissanceJourDeSemaine() {
		return personneDateNaissanceJourDeSemaine;
	}

	public String strPersonneDateNaissanceJourDeSemaine() {
		return personneDateNaissanceJourDeSemaine == null ? "" : personneDateNaissanceJourDeSemaine;
	}

	public String jsonPersonneDateNaissanceJourDeSemaine() {
		return personneDateNaissanceJourDeSemaine == null ? "" : personneDateNaissanceJourDeSemaine;
	}

	public String nomAffichagePersonneDateNaissanceJourDeSemaine() {
		return null;
	}

	public String htmTooltipPersonneDateNaissanceJourDeSemaine() {
		return null;
	}

	public String htmPersonneDateNaissanceJourDeSemaine() {
		return personneDateNaissanceJourDeSemaine == null ? "" : StringEscapeUtils.escapeHtml4(strPersonneDateNaissanceJourDeSemaine());
	}

	///////////////////////
	// patientNomComplet //
	///////////////////////

	/**	L'entité « patientNomComplet »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String patientNomComplet;
	@JsonIgnore
	public Couverture<String> patientNomCompletCouverture = new Couverture<String>().p(this).c(String.class).var("patientNomComplet").o(patientNomComplet);

	/**	<br/>L'entité « patientNomComplet »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.patient.PatientMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:patientNomComplet">Trouver l'entité patientNomComplet dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patientNomComplet(Couverture<String> c);

	public String getPatientNomComplet() {
		return patientNomComplet;
	}

	public void setPatientNomComplet(String patientNomComplet) {
		this.patientNomComplet = patientNomComplet;
		this.patientNomCompletCouverture.dejaInitialise = true;
	}
	protected PatientMedicale patientNomCompletInit() {
		if(!patientNomCompletCouverture.dejaInitialise) {
			_patientNomComplet(patientNomCompletCouverture);
			if(patientNomComplet == null)
				setPatientNomComplet(patientNomCompletCouverture.o);
		}
		patientNomCompletCouverture.dejaInitialise(true);
		return (PatientMedicale)this;
	}

	public String solrPatientNomComplet() {
		return patientNomComplet;
	}

	public String strPatientNomComplet() {
		return patientNomComplet == null ? "" : patientNomComplet;
	}

	public String jsonPatientNomComplet() {
		return patientNomComplet == null ? "" : patientNomComplet;
	}

	public String nomAffichagePatientNomComplet() {
		return "nom";
	}

	public String htmTooltipPatientNomComplet() {
		return null;
	}

	public String htmPatientNomComplet() {
		return patientNomComplet == null ? "" : StringEscapeUtils.escapeHtml4(strPatientNomComplet());
	}

	//////////////
	// initLoin //
	//////////////

	protected boolean dejaInitialisePatientMedicale = false;

	public PatientMedicale initLoinPatientMedicale(RequeteSiteFrFR requeteSite_) {
		setRequeteSite_(requeteSite_);
		if(!dejaInitialisePatientMedicale) {
			dejaInitialisePatientMedicale = true;
			initLoinPatientMedicale();
		}
		return (PatientMedicale)this;
	}

	public void initLoinPatientMedicale() {
		initPatientMedicale();
		super.initLoinCluster(requeteSite_);
	}

	public void initPatientMedicale() {
		patientCleInit();
		inscriptionClesInit();
		patientTriInit();
		inscriptionRechercheInit();
		inscriptionsInit();
		utilisateurClesInit();
		cliniqueClesInit();
		personnePrenomInit();
		personnePrenomPrefereInit();
		familleNomInit();
		personneNomCompletInit();
		personneNomCompletPrefereInit();
		personneNomFormelInit();
		personneDateNaissanceInit();
		personneDateNaissanceDAnneeInit();
		personneDateNaissanceMoisDAnneeInit();
		personneDateNaissanceJourDeSemaineInit();
		patientNomCompletInit();
	}

	@Override public void initLoinPourClasse(RequeteSiteFrFR requeteSite_) {
		initLoinPatientMedicale(requeteSite_);
	}

	/////////////////
	// requeteSite //
	/////////////////

	public void requeteSitePatientMedicale(RequeteSiteFrFR requeteSite_) {
			super.requeteSiteCluster(requeteSite_);
		if(inscriptionRecherche != null)
			inscriptionRecherche.setRequeteSite_(requeteSite_);
	}

	public void requeteSitePourClasse(RequeteSiteFrFR requeteSite_) {
		requeteSitePatientMedicale(requeteSite_);
	}

	/////////////
	// obtenir //
	/////////////

	@Override public Object obtenirPourClasse(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtenirPatientMedicale(v);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.obtenirPourClasse(v);
			}
		}
		return o;
	}
	public Object obtenirPatientMedicale(String var) {
		PatientMedicale oPatientMedicale = (PatientMedicale)this;
		switch(var) {
			case "patientCle":
				return oPatientMedicale.patientCle;
			case "inscriptionCles":
				return oPatientMedicale.inscriptionCles;
			case "patientTri":
				return oPatientMedicale.patientTri;
			case "inscriptionRecherche":
				return oPatientMedicale.inscriptionRecherche;
			case "inscriptions":
				return oPatientMedicale.inscriptions;
			case "utilisateurCles":
				return oPatientMedicale.utilisateurCles;
			case "cliniqueCles":
				return oPatientMedicale.cliniqueCles;
			case "personnePrenom":
				return oPatientMedicale.personnePrenom;
			case "personnePrenomPrefere":
				return oPatientMedicale.personnePrenomPrefere;
			case "familleNom":
				return oPatientMedicale.familleNom;
			case "personneNomComplet":
				return oPatientMedicale.personneNomComplet;
			case "personneNomCompletPrefere":
				return oPatientMedicale.personneNomCompletPrefere;
			case "personneNomFormel":
				return oPatientMedicale.personneNomFormel;
			case "personneDateNaissance":
				return oPatientMedicale.personneDateNaissance;
			case "personneDateNaissanceDAnnee":
				return oPatientMedicale.personneDateNaissanceDAnnee;
			case "personneDateNaissanceMoisDAnnee":
				return oPatientMedicale.personneDateNaissanceMoisDAnnee;
			case "personneDateNaissanceJourDeSemaine":
				return oPatientMedicale.personneDateNaissanceJourDeSemaine;
			case "patientNomComplet":
				return oPatientMedicale.patientNomComplet;
			default:
				return super.obtenirCluster(var);
		}
	}

	///////////////
	// attribuer //
	///////////////

	@Override public boolean attribuerPourClasse(String var, Object val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = attribuerPatientMedicale(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attribuerPourClasse(v, val);
			}
		}
		return o != null;
	}
	public Object attribuerPatientMedicale(String var, Object val) {
		PatientMedicale oPatientMedicale = (PatientMedicale)this;
		switch(var) {
			case "inscriptionCles":
				oPatientMedicale.addInscriptionCles((Long)val);
				if(!sauvegardesPatientMedicale.contains(var))
					sauvegardesPatientMedicale.add(var);
				return val;
			default:
				return super.attribuerCluster(var, val);
		}
	}

	/////////////
	// definir //
	/////////////

	@Override public boolean definirPourClasse(String var, String val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		if(val != null) {
			for(String v : vars) {
				if(o == null)
					o = definirPatientMedicale(v, val);
				else if(o instanceof Cluster) {
					Cluster cluster = (Cluster)o;
					o = cluster.definirPourClasse(v, val);
				}
			}
		}
		return o != null;
	}
	public Object definirPatientMedicale(String var, String val) {
		switch(var) {
			case "personnePrenom":
				if(val != null)
					setPersonnePrenom(val);
				sauvegardesPatientMedicale.add(var);
				return val;
			case "personnePrenomPrefere":
				if(val != null)
					setPersonnePrenomPrefere(val);
				sauvegardesPatientMedicale.add(var);
				return val;
			case "familleNom":
				if(val != null)
					setFamilleNom(val);
				sauvegardesPatientMedicale.add(var);
				return val;
			case "personneDateNaissance":
				if(val != null)
					setPersonneDateNaissance(val);
				sauvegardesPatientMedicale.add(var);
				return val;
			default:
				return super.definirCluster(var, val);
		}
	}

	/////////////////
	// sauvegardes //
	/////////////////

	protected List<String> sauvegardesPatientMedicale = new ArrayList<String>();

	/////////////
	// peupler //
	/////////////

	@Override public void peuplerPourClasse(SolrDocument solrDocument) {
		peuplerPatientMedicale(solrDocument);
	}
	public void peuplerPatientMedicale(SolrDocument solrDocument) {
		PatientMedicale oPatientMedicale = (PatientMedicale)this;
		sauvegardesPatientMedicale = (List<String>)solrDocument.get("sauvegardesPatientMedicale_stored_strings");
		if(sauvegardesPatientMedicale != null) {

			if(sauvegardesPatientMedicale.contains("patientCle")) {
				Long patientCle = (Long)solrDocument.get("patientCle_stored_long");
				if(patientCle != null)
					oPatientMedicale.setPatientCle(patientCle);
			}

			List<Long> inscriptionCles = (List<Long>)solrDocument.get("inscriptionCles_stored_longs");
			if(inscriptionCles != null)
				oPatientMedicale.inscriptionCles.addAll(inscriptionCles);

			if(sauvegardesPatientMedicale.contains("patientTri")) {
				Integer patientTri = (Integer)solrDocument.get("patientTri_stored_int");
				if(patientTri != null)
					oPatientMedicale.setPatientTri(patientTri);
			}

			if(sauvegardesPatientMedicale.contains("utilisateurCles")) {
				List<Long> utilisateurCles = (List<Long>)solrDocument.get("utilisateurCles_stored_longs");
				if(utilisateurCles != null)
					oPatientMedicale.utilisateurCles.addAll(utilisateurCles);
			}

			if(sauvegardesPatientMedicale.contains("cliniqueCles")) {
				List<Long> cliniqueCles = (List<Long>)solrDocument.get("cliniqueCles_stored_longs");
				if(cliniqueCles != null)
					oPatientMedicale.cliniqueCles.addAll(cliniqueCles);
			}

			if(sauvegardesPatientMedicale.contains("personnePrenom")) {
				String personnePrenom = (String)solrDocument.get("personnePrenom_stored_string");
				if(personnePrenom != null)
					oPatientMedicale.setPersonnePrenom(personnePrenom);
			}

			if(sauvegardesPatientMedicale.contains("personnePrenomPrefere")) {
				String personnePrenomPrefere = (String)solrDocument.get("personnePrenomPrefere_stored_string");
				if(personnePrenomPrefere != null)
					oPatientMedicale.setPersonnePrenomPrefere(personnePrenomPrefere);
			}

			if(sauvegardesPatientMedicale.contains("familleNom")) {
				String familleNom = (String)solrDocument.get("familleNom_stored_string");
				if(familleNom != null)
					oPatientMedicale.setFamilleNom(familleNom);
			}

			if(sauvegardesPatientMedicale.contains("personneNomComplet")) {
				String personneNomComplet = (String)solrDocument.get("personneNomComplet_stored_string");
				if(personneNomComplet != null)
					oPatientMedicale.setPersonneNomComplet(personneNomComplet);
			}

			if(sauvegardesPatientMedicale.contains("personneNomCompletPrefere")) {
				String personneNomCompletPrefere = (String)solrDocument.get("personneNomCompletPrefere_stored_string");
				if(personneNomCompletPrefere != null)
					oPatientMedicale.setPersonneNomCompletPrefere(personneNomCompletPrefere);
			}

			if(sauvegardesPatientMedicale.contains("personneNomFormel")) {
				String personneNomFormel = (String)solrDocument.get("personneNomFormel_stored_string");
				if(personneNomFormel != null)
					oPatientMedicale.setPersonneNomFormel(personneNomFormel);
			}

			if(sauvegardesPatientMedicale.contains("personneDateNaissance")) {
				Date personneDateNaissance = (Date)solrDocument.get("personneDateNaissance_stored_date");
				if(personneDateNaissance != null)
					oPatientMedicale.setPersonneDateNaissance(personneDateNaissance);
			}

			if(sauvegardesPatientMedicale.contains("personneDateNaissanceDAnnee")) {
				Integer personneDateNaissanceDAnnee = (Integer)solrDocument.get("personneDateNaissanceDAnnee_stored_int");
				if(personneDateNaissanceDAnnee != null)
					oPatientMedicale.setPersonneDateNaissanceDAnnee(personneDateNaissanceDAnnee);
			}

			if(sauvegardesPatientMedicale.contains("personneDateNaissanceMoisDAnnee")) {
				String personneDateNaissanceMoisDAnnee = (String)solrDocument.get("personneDateNaissanceMoisDAnnee_stored_string");
				if(personneDateNaissanceMoisDAnnee != null)
					oPatientMedicale.setPersonneDateNaissanceMoisDAnnee(personneDateNaissanceMoisDAnnee);
			}

			if(sauvegardesPatientMedicale.contains("personneDateNaissanceJourDeSemaine")) {
				String personneDateNaissanceJourDeSemaine = (String)solrDocument.get("personneDateNaissanceJourDeSemaine_stored_string");
				if(personneDateNaissanceJourDeSemaine != null)
					oPatientMedicale.setPersonneDateNaissanceJourDeSemaine(personneDateNaissanceJourDeSemaine);
			}

			if(sauvegardesPatientMedicale.contains("patientNomComplet")) {
				String patientNomComplet = (String)solrDocument.get("patientNomComplet_stored_string");
				if(patientNomComplet != null)
					oPatientMedicale.setPatientNomComplet(patientNomComplet);
			}
		}

		super.peuplerCluster(solrDocument);
	}

	/////////////
	// indexer //
	/////////////

	public static void indexer() {
		try {
			RequeteSiteFrFR requeteSite = new RequeteSiteFrFR();
			requeteSite.initLoinRequeteSiteFrFR();
			SiteContexteFrFR siteContexte = new SiteContexteFrFR();
			siteContexte.getConfigSite().setConfigChemin("/usr/local/src/computate-medicale/config/computate-medicale.config");
			siteContexte.initLoinSiteContexteFrFR();
			requeteSite.setSiteContexte_(siteContexte);
			requeteSite.setConfigSite_(siteContexte.getConfigSite());
			SolrQuery rechercheSolr = new SolrQuery();
			rechercheSolr.setQuery("*:*");
			rechercheSolr.setRows(1);
			rechercheSolr.addFilterQuery("id:" + ClientUtils.escapeQueryChars("org.computate.medicale.frFR.patient.PatientMedicale"));
			QueryResponse reponseRecherche = requeteSite.getSiteContexte_().getClientSolr().query(rechercheSolr);
			if(reponseRecherche.getResults().size() > 0)
				requeteSite.setDocumentSolr(reponseRecherche.getResults().get(0));
			PatientMedicale o = new PatientMedicale();
			o.requeteSitePatientMedicale(requeteSite);
			o.initLoinPatientMedicale(requeteSite);
			o.indexerPatientMedicale();
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}


	@Override public void indexerPourClasse() {
		indexerPatientMedicale();
	}

	@Override public void indexerPourClasse(SolrInputDocument document) {
		indexerPatientMedicale(document);
	}

	public void indexerPatientMedicale(SolrClient clientSolr) {
		try {
			SolrInputDocument document = new SolrInputDocument();
			indexerPatientMedicale(document);
			clientSolr.add(document);
			clientSolr.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public void indexerPatientMedicale() {
		try {
			SolrInputDocument document = new SolrInputDocument();
			indexerPatientMedicale(document);
			SolrClient clientSolr = requeteSite_.getSiteContexte_().getClientSolr();
			clientSolr.add(document);
			clientSolr.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public void indexerPatientMedicale(SolrInputDocument document) {
		if(sauvegardesPatientMedicale != null)
			document.addField("sauvegardesPatientMedicale_stored_strings", sauvegardesPatientMedicale);

		if(patientCle != null) {
			document.addField("patientCle_indexed_long", patientCle);
			document.addField("patientCle_stored_long", patientCle);
		}
		if(inscriptionCles != null) {
			for(java.lang.Long o : inscriptionCles) {
				document.addField("inscriptionCles_indexed_longs", o);
			}
			for(java.lang.Long o : inscriptionCles) {
				document.addField("inscriptionCles_stored_longs", o);
			}
		}
		if(patientTri != null) {
			document.addField("patientTri_indexed_int", patientTri);
			document.addField("patientTri_stored_int", patientTri);
		}
		if(utilisateurCles != null) {
			for(java.lang.Long o : utilisateurCles) {
				document.addField("utilisateurCles_indexed_longs", o);
			}
			for(java.lang.Long o : utilisateurCles) {
				document.addField("utilisateurCles_stored_longs", o);
			}
		}
		if(cliniqueCles != null) {
			for(java.lang.Long o : cliniqueCles) {
				document.addField("cliniqueCles_indexed_longs", o);
			}
			for(java.lang.Long o : cliniqueCles) {
				document.addField("cliniqueCles_stored_longs", o);
			}
		}
		if(personnePrenom != null) {
			document.addField("personnePrenom_indexed_string", personnePrenom);
			document.addField("personnePrenom_stored_string", personnePrenom);
		}
		if(personnePrenomPrefere != null) {
			document.addField("personnePrenomPrefere_indexed_string", personnePrenomPrefere);
			document.addField("personnePrenomPrefere_stored_string", personnePrenomPrefere);
		}
		if(familleNom != null) {
			document.addField("familleNom_indexed_string", familleNom);
			document.addField("familleNom_stored_string", familleNom);
		}
		if(personneNomComplet != null) {
			document.addField("personneNomComplet_indexed_string", personneNomComplet);
			document.addField("personneNomComplet_stored_string", personneNomComplet);
		}
		if(personneNomCompletPrefere != null) {
			document.addField("personneNomCompletPrefere_indexed_string", personneNomCompletPrefere);
			document.addField("personneNomCompletPrefere_stored_string", personneNomCompletPrefere);
		}
		if(personneNomFormel != null) {
			document.addField("personneNomFormel_indexed_string", personneNomFormel);
			document.addField("personneNomFormel_stored_string", personneNomFormel);
		}
		if(personneDateNaissance != null) {
			document.addField("personneDateNaissance_indexed_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(personneDateNaissance.atStartOfDay(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
			document.addField("personneDateNaissance_stored_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(personneDateNaissance.atStartOfDay(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
		}
		if(personneDateNaissanceDAnnee != null) {
			document.addField("personneDateNaissanceDAnnee_indexed_int", personneDateNaissanceDAnnee);
			document.addField("personneDateNaissanceDAnnee_stored_int", personneDateNaissanceDAnnee);
		}
		if(personneDateNaissanceMoisDAnnee != null) {
			document.addField("personneDateNaissanceMoisDAnnee_indexed_string", personneDateNaissanceMoisDAnnee);
			document.addField("personneDateNaissanceMoisDAnnee_stored_string", personneDateNaissanceMoisDAnnee);
		}
		if(personneDateNaissanceJourDeSemaine != null) {
			document.addField("personneDateNaissanceJourDeSemaine_indexed_string", personneDateNaissanceJourDeSemaine);
			document.addField("personneDateNaissanceJourDeSemaine_stored_string", personneDateNaissanceJourDeSemaine);
		}
		if(patientNomComplet != null) {
			document.addField("patientNomComplet_indexed_string", patientNomComplet);
			document.addField("patientNomComplet_stored_string", patientNomComplet);
		}
		super.indexerCluster(document);

	}

	public void desindexerPatientMedicale() {
		try {
		RequeteSiteFrFR requeteSite = new RequeteSiteFrFR();
			requeteSite.initLoinRequeteSiteFrFR();
			SiteContexteFrFR siteContexte = new SiteContexteFrFR();
			siteContexte.initLoinSiteContexteFrFR();
			requeteSite.setSiteContexte_(siteContexte);
			requeteSite.setConfigSite_(siteContexte.getConfigSite());
			initLoinPatientMedicale(requeteSite);
			SolrClient clientSolr = siteContexte.getClientSolr();
			clientSolr.deleteById(id.toString());
			clientSolr.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public static String varIndexePatientMedicale(String entiteVar) {
		switch(entiteVar) {
			case "patientCle":
				return "patientCle_indexed_long";
			case "inscriptionCles":
				return "inscriptionCles_indexed_longs";
			case "patientTri":
				return "patientTri_indexed_int";
			case "utilisateurCles":
				return "utilisateurCles_indexed_longs";
			case "cliniqueCles":
				return "cliniqueCles_indexed_longs";
			case "personnePrenom":
				return "personnePrenom_indexed_string";
			case "personnePrenomPrefere":
				return "personnePrenomPrefere_indexed_string";
			case "familleNom":
				return "familleNom_indexed_string";
			case "personneNomComplet":
				return "personneNomComplet_indexed_string";
			case "personneNomCompletPrefere":
				return "personneNomCompletPrefere_indexed_string";
			case "personneNomFormel":
				return "personneNomFormel_indexed_string";
			case "personneDateNaissance":
				return "personneDateNaissance_indexed_date";
			case "personneDateNaissanceDAnnee":
				return "personneDateNaissanceDAnnee_indexed_int";
			case "personneDateNaissanceMoisDAnnee":
				return "personneDateNaissanceMoisDAnnee_indexed_string";
			case "personneDateNaissanceJourDeSemaine":
				return "personneDateNaissanceJourDeSemaine_indexed_string";
			case "patientNomComplet":
				return "patientNomComplet_indexed_string";
			default:
				return Cluster.varIndexeCluster(entiteVar);
		}
	}

	public static String varRecherchePatientMedicale(String entiteVar) {
		switch(entiteVar) {
			default:
				return Cluster.varRechercheCluster(entiteVar);
		}
	}

	public static String varSuggerePatientMedicale(String entiteVar) {
		switch(entiteVar) {
			default:
				return Cluster.varSuggereCluster(entiteVar);
		}
	}

	/////////////
	// stocker //
	/////////////

	@Override public void stockerPourClasse(SolrDocument solrDocument) {
		stockerPatientMedicale(solrDocument);
	}
	public void stockerPatientMedicale(SolrDocument solrDocument) {
		PatientMedicale oPatientMedicale = (PatientMedicale)this;

		Long patientCle = (Long)solrDocument.get("patientCle_stored_long");
		if(patientCle != null)
			oPatientMedicale.setPatientCle(patientCle);

		List<Long> inscriptionCles = (List<Long>)solrDocument.get("inscriptionCles_stored_longs");
		if(inscriptionCles != null)
			oPatientMedicale.inscriptionCles.addAll(inscriptionCles);

		Integer patientTri = (Integer)solrDocument.get("patientTri_stored_int");
		if(patientTri != null)
			oPatientMedicale.setPatientTri(patientTri);

		List<Long> utilisateurCles = (List<Long>)solrDocument.get("utilisateurCles_stored_longs");
		if(utilisateurCles != null)
			oPatientMedicale.utilisateurCles.addAll(utilisateurCles);

		List<Long> cliniqueCles = (List<Long>)solrDocument.get("cliniqueCles_stored_longs");
		if(cliniqueCles != null)
			oPatientMedicale.cliniqueCles.addAll(cliniqueCles);

		String personnePrenom = (String)solrDocument.get("personnePrenom_stored_string");
		if(personnePrenom != null)
			oPatientMedicale.setPersonnePrenom(personnePrenom);

		String personnePrenomPrefere = (String)solrDocument.get("personnePrenomPrefere_stored_string");
		if(personnePrenomPrefere != null)
			oPatientMedicale.setPersonnePrenomPrefere(personnePrenomPrefere);

		String familleNom = (String)solrDocument.get("familleNom_stored_string");
		if(familleNom != null)
			oPatientMedicale.setFamilleNom(familleNom);

		String personneNomComplet = (String)solrDocument.get("personneNomComplet_stored_string");
		if(personneNomComplet != null)
			oPatientMedicale.setPersonneNomComplet(personneNomComplet);

		String personneNomCompletPrefere = (String)solrDocument.get("personneNomCompletPrefere_stored_string");
		if(personneNomCompletPrefere != null)
			oPatientMedicale.setPersonneNomCompletPrefere(personneNomCompletPrefere);

		String personneNomFormel = (String)solrDocument.get("personneNomFormel_stored_string");
		if(personneNomFormel != null)
			oPatientMedicale.setPersonneNomFormel(personneNomFormel);

		Date personneDateNaissance = (Date)solrDocument.get("personneDateNaissance_stored_date");
		if(personneDateNaissance != null)
			oPatientMedicale.setPersonneDateNaissance(personneDateNaissance);

		Integer personneDateNaissanceDAnnee = (Integer)solrDocument.get("personneDateNaissanceDAnnee_stored_int");
		if(personneDateNaissanceDAnnee != null)
			oPatientMedicale.setPersonneDateNaissanceDAnnee(personneDateNaissanceDAnnee);

		String personneDateNaissanceMoisDAnnee = (String)solrDocument.get("personneDateNaissanceMoisDAnnee_stored_string");
		if(personneDateNaissanceMoisDAnnee != null)
			oPatientMedicale.setPersonneDateNaissanceMoisDAnnee(personneDateNaissanceMoisDAnnee);

		String personneDateNaissanceJourDeSemaine = (String)solrDocument.get("personneDateNaissanceJourDeSemaine_stored_string");
		if(personneDateNaissanceJourDeSemaine != null)
			oPatientMedicale.setPersonneDateNaissanceJourDeSemaine(personneDateNaissanceJourDeSemaine);

		String patientNomComplet = (String)solrDocument.get("patientNomComplet_stored_string");
		if(patientNomComplet != null)
			oPatientMedicale.setPatientNomComplet(patientNomComplet);

		super.stockerCluster(solrDocument);
	}

	//////////////////
	// requeteApi //
	//////////////////

	public void requeteApiPatientMedicale() {
		RequeteApi requeteApi = Optional.ofNullable(requeteSite_).map(RequeteSiteFrFR::getRequeteApi_).orElse(null);
		Object o = Optional.ofNullable(requeteApi).map(RequeteApi::getOriginal).orElse(null);
		if(o != null && o instanceof PatientMedicale) {
			PatientMedicale original = (PatientMedicale)o;
			if(!Objects.equals(inscriptionCles, original.getInscriptionCles()))
				requeteApi.addVars("inscriptionCles");
			if(!Objects.equals(personnePrenom, original.getPersonnePrenom()))
				requeteApi.addVars("personnePrenom");
			if(!Objects.equals(personnePrenomPrefere, original.getPersonnePrenomPrefere()))
				requeteApi.addVars("personnePrenomPrefere");
			if(!Objects.equals(familleNom, original.getFamilleNom()))
				requeteApi.addVars("familleNom");
			if(!Objects.equals(personneDateNaissance, original.getPersonneDateNaissance()))
				requeteApi.addVars("personneDateNaissance");
			super.requeteApiCluster();
		}
	}

	//////////////
	// hashCode //
	//////////////

	@Override public int hashCode() {
		return Objects.hash(super.hashCode(), inscriptionCles, personnePrenom, personnePrenomPrefere, familleNom, personneDateNaissance);
	}

	////////////
	// equals //
	////////////

	@Override public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof PatientMedicale))
			return false;
		PatientMedicale that = (PatientMedicale)o;
		return super.equals(o)
				&& Objects.equals( inscriptionCles, that.inscriptionCles )
				&& Objects.equals( personnePrenom, that.personnePrenom )
				&& Objects.equals( personnePrenomPrefere, that.personnePrenomPrefere )
				&& Objects.equals( familleNom, that.familleNom )
				&& Objects.equals( personneDateNaissance, that.personneDateNaissance );
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("PatientMedicale { ");
		sb.append( "inscriptionCles: " ).append(inscriptionCles);
		sb.append( ", personnePrenom: \"" ).append(personnePrenom).append( "\"" );
		sb.append( ", personnePrenomPrefere: \"" ).append(personnePrenomPrefere).append( "\"" );
		sb.append( ", familleNom: \"" ).append(familleNom).append( "\"" );
		sb.append( ", personneDateNaissance: " ).append(personneDateNaissance);
		sb.append(" }");
		return sb.toString();
	}
}
