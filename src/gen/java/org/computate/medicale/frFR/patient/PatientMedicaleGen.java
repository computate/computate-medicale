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
 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.patient.PatientMedicale&fq=classeEtendGen_indexed_boolean:true">Trouver la classe patientBirthDateDayOfWeek dans Solr</a>
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
	public static final String PatientMedicale_IconeNom = "hospital-user";

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
								e("i").a("class", "fas fa-notes-medical ").f().g("i");
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

	///////////////////
	// patientPrenom //
	///////////////////

	/**	L'entité « patientPrenom »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String patientPrenom;
	@JsonIgnore
	public Couverture<String> patientPrenomCouverture = new Couverture<String>().p(this).c(String.class).var("patientPrenom").o(patientPrenom);

	/**	<br/>L'entité « patientPrenom »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.patient.PatientMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:patientPrenom">Trouver l'entité patientPrenom dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patientPrenom(Couverture<String> c);

	public String getPatientPrenom() {
		return patientPrenom;
	}

	public void setPatientPrenom(String patientPrenom) {
		this.patientPrenom = patientPrenom;
		this.patientPrenomCouverture.dejaInitialise = true;
	}
	protected PatientMedicale patientPrenomInit() {
		if(!patientPrenomCouverture.dejaInitialise) {
			_patientPrenom(patientPrenomCouverture);
			if(patientPrenom == null)
				setPatientPrenom(patientPrenomCouverture.o);
		}
		patientPrenomCouverture.dejaInitialise(true);
		return (PatientMedicale)this;
	}

	public String solrPatientPrenom() {
		return patientPrenom;
	}

	public String strPatientPrenom() {
		return patientPrenom == null ? "" : patientPrenom;
	}

	public String jsonPatientPrenom() {
		return patientPrenom == null ? "" : patientPrenom;
	}

	public String nomAffichagePatientPrenom() {
		return "prénom";
	}

	public String htmTooltipPatientPrenom() {
		return null;
	}

	public String htmPatientPrenom() {
		return patientPrenom == null ? "" : StringEscapeUtils.escapeHtml4(strPatientPrenom());
	}

	public void inputPatientPrenom(String classeApiMethodeMethode) {
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
				.a("id", classeApiMethodeMethode, "_patientPrenom");
				if("Page".equals(classeApiMethodeMethode) || "PATCH".equals(classeApiMethodeMethode)) {
					a("class", "setPatientPrenom classPatientMedicale inputPatientMedicale", pk, "PatientPrenom w3-input w3-border ");
					a("name", "setPatientPrenom");
				} else {
					a("class", "valeurPatientPrenom w3-input w3-border classPatientMedicale inputPatientMedicale", pk, "PatientPrenom w3-input w3-border ");
					a("name", "patientPrenom");
				}
				if("Page".equals(classeApiMethodeMethode)) {
					a("onclick", "enleverLueur($(this)); ");
					a("onchange", "patchPatientMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setPatientPrenom', $(this).val(), function() { ajouterLueur($('#", classeApiMethodeMethode, "_patientPrenom')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_patientPrenom')); }); ");
				}
				a("value", strPatientPrenom())
			.fg();

		} else {
			sx(htmPatientPrenom());
		}
	}

	public void htmPatientPrenom(String classeApiMethodeMethode) {
		PatientMedicale s = (PatientMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "PatientMedicalePatientPrenom").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-orange ").f();
							e("label").a("for", classeApiMethodeMethode, "_patientPrenom").a("class", "").f().sx("prénom").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputPatientPrenom(classeApiMethodeMethode);
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
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_patientPrenom')); $('#", classeApiMethodeMethode, "_patientPrenom').val(null); patchPatientMedicaleVal([{ name: 'fq', value: 'pk:' + $('#PatientMedicaleForm :input[name=pk]').val() }], 'setPatientPrenom', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_patientPrenom')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_patientPrenom')); }); ")
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

	//////////////////////////
	// patientPrenomPrefere //
	//////////////////////////

	/**	L'entité « patientPrenomPrefere »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String patientPrenomPrefere;
	@JsonIgnore
	public Couverture<String> patientPrenomPrefereCouverture = new Couverture<String>().p(this).c(String.class).var("patientPrenomPrefere").o(patientPrenomPrefere);

	/**	<br/>L'entité « patientPrenomPrefere »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.patient.PatientMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:patientPrenomPrefere">Trouver l'entité patientPrenomPrefere dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patientPrenomPrefere(Couverture<String> c);

	public String getPatientPrenomPrefere() {
		return patientPrenomPrefere;
	}

	public void setPatientPrenomPrefere(String patientPrenomPrefere) {
		this.patientPrenomPrefere = patientPrenomPrefere;
		this.patientPrenomPrefereCouverture.dejaInitialise = true;
	}
	protected PatientMedicale patientPrenomPrefereInit() {
		if(!patientPrenomPrefereCouverture.dejaInitialise) {
			_patientPrenomPrefere(patientPrenomPrefereCouverture);
			if(patientPrenomPrefere == null)
				setPatientPrenomPrefere(patientPrenomPrefereCouverture.o);
		}
		patientPrenomPrefereCouverture.dejaInitialise(true);
		return (PatientMedicale)this;
	}

	public String solrPatientPrenomPrefere() {
		return patientPrenomPrefere;
	}

	public String strPatientPrenomPrefere() {
		return patientPrenomPrefere == null ? "" : patientPrenomPrefere;
	}

	public String jsonPatientPrenomPrefere() {
		return patientPrenomPrefere == null ? "" : patientPrenomPrefere;
	}

	public String nomAffichagePatientPrenomPrefere() {
		return "prénom préferé";
	}

	public String htmTooltipPatientPrenomPrefere() {
		return null;
	}

	public String htmPatientPrenomPrefere() {
		return patientPrenomPrefere == null ? "" : StringEscapeUtils.escapeHtml4(strPatientPrenomPrefere());
	}

	public void inputPatientPrenomPrefere(String classeApiMethodeMethode) {
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
				.a("id", classeApiMethodeMethode, "_patientPrenomPrefere");
				if("Page".equals(classeApiMethodeMethode) || "PATCH".equals(classeApiMethodeMethode)) {
					a("class", "setPatientPrenomPrefere classPatientMedicale inputPatientMedicale", pk, "PatientPrenomPrefere w3-input w3-border ");
					a("name", "setPatientPrenomPrefere");
				} else {
					a("class", "valeurPatientPrenomPrefere w3-input w3-border classPatientMedicale inputPatientMedicale", pk, "PatientPrenomPrefere w3-input w3-border ");
					a("name", "patientPrenomPrefere");
				}
				if("Page".equals(classeApiMethodeMethode)) {
					a("onclick", "enleverLueur($(this)); ");
					a("onchange", "patchPatientMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setPatientPrenomPrefere', $(this).val(), function() { ajouterLueur($('#", classeApiMethodeMethode, "_patientPrenomPrefere')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_patientPrenomPrefere')); }); ");
				}
				a("value", strPatientPrenomPrefere())
			.fg();

		} else {
			sx(htmPatientPrenomPrefere());
		}
	}

	public void htmPatientPrenomPrefere(String classeApiMethodeMethode) {
		PatientMedicale s = (PatientMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "PatientMedicalePatientPrenomPrefere").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-orange ").f();
							e("label").a("for", classeApiMethodeMethode, "_patientPrenomPrefere").a("class", "").f().sx("prénom préferé").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputPatientPrenomPrefere(classeApiMethodeMethode);
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
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_patientPrenomPrefere')); $('#", classeApiMethodeMethode, "_patientPrenomPrefere').val(null); patchPatientMedicaleVal([{ name: 'fq', value: 'pk:' + $('#PatientMedicaleForm :input[name=pk]').val() }], 'setPatientPrenomPrefere', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_patientPrenomPrefere')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_patientPrenomPrefere')); }); ")
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
		return "nom complèt";
	}

	public String htmTooltipPatientNomComplet() {
		return null;
	}

	public String htmPatientNomComplet() {
		return patientNomComplet == null ? "" : StringEscapeUtils.escapeHtml4(strPatientNomComplet());
	}

	//////////////////////////////
	// patientNomCompletPrefere //
	//////////////////////////////

	/**	L'entité « patientNomCompletPrefere »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String patientNomCompletPrefere;
	@JsonIgnore
	public Couverture<String> patientNomCompletPrefereCouverture = new Couverture<String>().p(this).c(String.class).var("patientNomCompletPrefere").o(patientNomCompletPrefere);

	/**	<br/>L'entité « patientNomCompletPrefere »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.patient.PatientMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:patientNomCompletPrefere">Trouver l'entité patientNomCompletPrefere dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patientNomCompletPrefere(Couverture<String> c);

	public String getPatientNomCompletPrefere() {
		return patientNomCompletPrefere;
	}

	public void setPatientNomCompletPrefere(String patientNomCompletPrefere) {
		this.patientNomCompletPrefere = patientNomCompletPrefere;
		this.patientNomCompletPrefereCouverture.dejaInitialise = true;
	}
	protected PatientMedicale patientNomCompletPrefereInit() {
		if(!patientNomCompletPrefereCouverture.dejaInitialise) {
			_patientNomCompletPrefere(patientNomCompletPrefereCouverture);
			if(patientNomCompletPrefere == null)
				setPatientNomCompletPrefere(patientNomCompletPrefereCouverture.o);
		}
		patientNomCompletPrefereCouverture.dejaInitialise(true);
		return (PatientMedicale)this;
	}

	public String solrPatientNomCompletPrefere() {
		return patientNomCompletPrefere;
	}

	public String strPatientNomCompletPrefere() {
		return patientNomCompletPrefere == null ? "" : patientNomCompletPrefere;
	}

	public String jsonPatientNomCompletPrefere() {
		return patientNomCompletPrefere == null ? "" : patientNomCompletPrefere;
	}

	public String nomAffichagePatientNomCompletPrefere() {
		return "nom complèt préferé";
	}

	public String htmTooltipPatientNomCompletPrefere() {
		return null;
	}

	public String htmPatientNomCompletPrefere() {
		return patientNomCompletPrefere == null ? "" : StringEscapeUtils.escapeHtml4(strPatientNomCompletPrefere());
	}

	//////////////////////
	// patientNomFormel //
	//////////////////////

	/**	L'entité « patientNomFormel »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String patientNomFormel;
	@JsonIgnore
	public Couverture<String> patientNomFormelCouverture = new Couverture<String>().p(this).c(String.class).var("patientNomFormel").o(patientNomFormel);

	/**	<br/>L'entité « patientNomFormel »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.patient.PatientMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:patientNomFormel">Trouver l'entité patientNomFormel dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patientNomFormel(Couverture<String> c);

	public String getPatientNomFormel() {
		return patientNomFormel;
	}

	public void setPatientNomFormel(String patientNomFormel) {
		this.patientNomFormel = patientNomFormel;
		this.patientNomFormelCouverture.dejaInitialise = true;
	}
	protected PatientMedicale patientNomFormelInit() {
		if(!patientNomFormelCouverture.dejaInitialise) {
			_patientNomFormel(patientNomFormelCouverture);
			if(patientNomFormel == null)
				setPatientNomFormel(patientNomFormelCouverture.o);
		}
		patientNomFormelCouverture.dejaInitialise(true);
		return (PatientMedicale)this;
	}

	public String solrPatientNomFormel() {
		return patientNomFormel;
	}

	public String strPatientNomFormel() {
		return patientNomFormel == null ? "" : patientNomFormel;
	}

	public String jsonPatientNomFormel() {
		return patientNomFormel == null ? "" : patientNomFormel;
	}

	public String nomAffichagePatientNomFormel() {
		return "nom formel";
	}

	public String htmTooltipPatientNomFormel() {
		return null;
	}

	public String htmPatientNomFormel() {
		return patientNomFormel == null ? "" : StringEscapeUtils.escapeHtml4(strPatientNomFormel());
	}

	//////////////////////////
	// patientDateNaissance //
	//////////////////////////

	/**	L'entité « patientDateNaissance »
	 *	 is defined as null before being initialized. 
	 */
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	protected LocalDate patientDateNaissance;
	@JsonIgnore
	public Couverture<LocalDate> patientDateNaissanceCouverture = new Couverture<LocalDate>().p(this).c(LocalDate.class).var("patientDateNaissance").o(patientDateNaissance);

	/**	<br/>L'entité « patientDateNaissance »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.patient.PatientMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:patientDateNaissance">Trouver l'entité patientDateNaissance dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patientDateNaissance(Couverture<LocalDate> c);

	public LocalDate getPatientDateNaissance() {
		return patientDateNaissance;
	}

	public void setPatientDateNaissance(LocalDate patientDateNaissance) {
		this.patientDateNaissance = patientDateNaissance;
		this.patientDateNaissanceCouverture.dejaInitialise = true;
	}
	public PatientMedicale setPatientDateNaissance(Instant o) {
		this.patientDateNaissance = o == null ? null : LocalDate.from(o);
		this.patientDateNaissanceCouverture.dejaInitialise = true;
		return (PatientMedicale)this;
	}
	/** Example: 2011-12-03+01:00 **/
	public PatientMedicale setPatientDateNaissance(String o) {
		this.patientDateNaissance = o == null ? null : LocalDate.parse(o, DateTimeFormatter.ISO_DATE);
		this.patientDateNaissanceCouverture.dejaInitialise = true;
		return (PatientMedicale)this;
	}
	public PatientMedicale setPatientDateNaissance(Date o) {
		this.patientDateNaissance = o == null ? null : o.toInstant().atZone(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toLocalDate();
		this.patientDateNaissanceCouverture.dejaInitialise = true;
		return (PatientMedicale)this;
	}
	protected PatientMedicale patientDateNaissanceInit() {
		if(!patientDateNaissanceCouverture.dejaInitialise) {
			_patientDateNaissance(patientDateNaissanceCouverture);
			if(patientDateNaissance == null)
				setPatientDateNaissance(patientDateNaissanceCouverture.o);
		}
		patientDateNaissanceCouverture.dejaInitialise(true);
		return (PatientMedicale)this;
	}

	public Date solrPatientDateNaissance() {
		return patientDateNaissance == null ? null : Date.from(patientDateNaissance.atStartOfDay(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toInstant().atZone(ZoneId.of("Z")).toInstant());
	}

	public String strPatientDateNaissance() {
		return patientDateNaissance == null ? "" : patientDateNaissance.format(DateTimeFormatter.ofPattern("EEE d MMM yyyy", Locale.forLanguageTag("fr-FR")));
	}

	public String jsonPatientDateNaissance() {
		return patientDateNaissance == null ? "" : patientDateNaissance.format(DateTimeFormatter.ISO_DATE);
	}

	public String nomAffichagePatientDateNaissance() {
		return "date de naissance";
	}

	public String htmTooltipPatientDateNaissance() {
		return null;
	}

	public String htmPatientDateNaissance() {
		return patientDateNaissance == null ? "" : StringEscapeUtils.escapeHtml4(strPatientDateNaissance());
	}

	public void inputPatientDateNaissance(String classeApiMethodeMethode) {
		PatientMedicale s = (PatientMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("class", "w3-input w3-border datepicker setPatientDateNaissance classPatientMedicale inputPatientMedicale", pk, "PatientDateNaissance w3-input w3-border ")
				.a("placeholder", "DD-MM-YYYY")
				.a("data-timeformat", "dd-MM-yyyy")
				.a("id", classeApiMethodeMethode, "_patientDateNaissance")
				.a("onclick", "enleverLueur($(this)); ")
				.a("value", patientDateNaissance == null ? "" : DateTimeFormatter.ofPattern("dd-MM-yyyy").format(patientDateNaissance))
				.a("onchange", "var t = moment(this.value, 'DD-MM-YYYY'); if(t) { var s = t.format('YYYY-MM-DD'); patchPatientMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setPatientDateNaissance', s, function() { ajouterLueur($('#", classeApiMethodeMethode, "_patientDateNaissance')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_patientDateNaissance')); }); } ")
				.fg();
		} else {
			sx(htmPatientDateNaissance());
		}
	}

	public void htmPatientDateNaissance(String classeApiMethodeMethode) {
		PatientMedicale s = (PatientMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "PatientMedicalePatientDateNaissance").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-orange ").f();
							e("label").a("for", classeApiMethodeMethode, "_patientDateNaissance").a("class", "").f().sx("date de naissance").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row  ").f();
							{ e("div").a("class", "w3-cell ").f();
								inputPatientDateNaissance(classeApiMethodeMethode);
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
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_patientDateNaissance')); $('#", classeApiMethodeMethode, "_patientDateNaissance').val(null); patchPatientMedicaleVal([{ name: 'fq', value: 'pk:' + $('#PatientMedicaleForm :input[name=pk]').val() }], 'setPatientDateNaissance', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_patientDateNaissance')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_patientDateNaissance')); }); ")
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

	////////////////////////////////
	// patientDateNaissanceDAnnee //
	////////////////////////////////

	/**	L'entité « patientDateNaissanceDAnnee »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer patientDateNaissanceDAnnee;
	@JsonIgnore
	public Couverture<Integer> patientDateNaissanceDAnneeCouverture = new Couverture<Integer>().p(this).c(Integer.class).var("patientDateNaissanceDAnnee").o(patientDateNaissanceDAnnee);

	/**	<br/>L'entité « patientDateNaissanceDAnnee »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.patient.PatientMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:patientDateNaissanceDAnnee">Trouver l'entité patientDateNaissanceDAnnee dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patientDateNaissanceDAnnee(Couverture<Integer> c);

	public Integer getPatientDateNaissanceDAnnee() {
		return patientDateNaissanceDAnnee;
	}

	public void setPatientDateNaissanceDAnnee(Integer patientDateNaissanceDAnnee) {
		this.patientDateNaissanceDAnnee = patientDateNaissanceDAnnee;
		this.patientDateNaissanceDAnneeCouverture.dejaInitialise = true;
	}
	public PatientMedicale setPatientDateNaissanceDAnnee(String o) {
		if(NumberUtils.isParsable(o))
			this.patientDateNaissanceDAnnee = Integer.parseInt(o);
		this.patientDateNaissanceDAnneeCouverture.dejaInitialise = true;
		return (PatientMedicale)this;
	}
	protected PatientMedicale patientDateNaissanceDAnneeInit() {
		if(!patientDateNaissanceDAnneeCouverture.dejaInitialise) {
			_patientDateNaissanceDAnnee(patientDateNaissanceDAnneeCouverture);
			if(patientDateNaissanceDAnnee == null)
				setPatientDateNaissanceDAnnee(patientDateNaissanceDAnneeCouverture.o);
		}
		patientDateNaissanceDAnneeCouverture.dejaInitialise(true);
		return (PatientMedicale)this;
	}

	public Integer solrPatientDateNaissanceDAnnee() {
		return patientDateNaissanceDAnnee;
	}

	public String strPatientDateNaissanceDAnnee() {
		return patientDateNaissanceDAnnee == null ? "" : patientDateNaissanceDAnnee.toString();
	}

	public String jsonPatientDateNaissanceDAnnee() {
		return patientDateNaissanceDAnnee == null ? "" : patientDateNaissanceDAnnee.toString();
	}

	public String nomAffichagePatientDateNaissanceDAnnee() {
		return null;
	}

	public String htmTooltipPatientDateNaissanceDAnnee() {
		return null;
	}

	public String htmPatientDateNaissanceDAnnee() {
		return patientDateNaissanceDAnnee == null ? "" : StringEscapeUtils.escapeHtml4(strPatientDateNaissanceDAnnee());
	}

	////////////////////////////////////
	// patientDateNaissanceMoisDAnnee //
	////////////////////////////////////

	/**	L'entité « patientDateNaissanceMoisDAnnee »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String patientDateNaissanceMoisDAnnee;
	@JsonIgnore
	public Couverture<String> patientDateNaissanceMoisDAnneeCouverture = new Couverture<String>().p(this).c(String.class).var("patientDateNaissanceMoisDAnnee").o(patientDateNaissanceMoisDAnnee);

	/**	<br/>L'entité « patientDateNaissanceMoisDAnnee »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.patient.PatientMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:patientDateNaissanceMoisDAnnee">Trouver l'entité patientDateNaissanceMoisDAnnee dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patientDateNaissanceMoisDAnnee(Couverture<String> c);

	public String getPatientDateNaissanceMoisDAnnee() {
		return patientDateNaissanceMoisDAnnee;
	}

	public void setPatientDateNaissanceMoisDAnnee(String patientDateNaissanceMoisDAnnee) {
		this.patientDateNaissanceMoisDAnnee = patientDateNaissanceMoisDAnnee;
		this.patientDateNaissanceMoisDAnneeCouverture.dejaInitialise = true;
	}
	protected PatientMedicale patientDateNaissanceMoisDAnneeInit() {
		if(!patientDateNaissanceMoisDAnneeCouverture.dejaInitialise) {
			_patientDateNaissanceMoisDAnnee(patientDateNaissanceMoisDAnneeCouverture);
			if(patientDateNaissanceMoisDAnnee == null)
				setPatientDateNaissanceMoisDAnnee(patientDateNaissanceMoisDAnneeCouverture.o);
		}
		patientDateNaissanceMoisDAnneeCouverture.dejaInitialise(true);
		return (PatientMedicale)this;
	}

	public String solrPatientDateNaissanceMoisDAnnee() {
		return patientDateNaissanceMoisDAnnee;
	}

	public String strPatientDateNaissanceMoisDAnnee() {
		return patientDateNaissanceMoisDAnnee == null ? "" : patientDateNaissanceMoisDAnnee;
	}

	public String jsonPatientDateNaissanceMoisDAnnee() {
		return patientDateNaissanceMoisDAnnee == null ? "" : patientDateNaissanceMoisDAnnee;
	}

	public String nomAffichagePatientDateNaissanceMoisDAnnee() {
		return null;
	}

	public String htmTooltipPatientDateNaissanceMoisDAnnee() {
		return null;
	}

	public String htmPatientDateNaissanceMoisDAnnee() {
		return patientDateNaissanceMoisDAnnee == null ? "" : StringEscapeUtils.escapeHtml4(strPatientDateNaissanceMoisDAnnee());
	}

	///////////////////////////////////////
	// patientDateNaissanceJourDeSemaine //
	///////////////////////////////////////

	/**	L'entité « patientDateNaissanceJourDeSemaine »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String patientDateNaissanceJourDeSemaine;
	@JsonIgnore
	public Couverture<String> patientDateNaissanceJourDeSemaineCouverture = new Couverture<String>().p(this).c(String.class).var("patientDateNaissanceJourDeSemaine").o(patientDateNaissanceJourDeSemaine);

	/**	<br/>L'entité « patientDateNaissanceJourDeSemaine »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.patient.PatientMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:patientDateNaissanceJourDeSemaine">Trouver l'entité patientDateNaissanceJourDeSemaine dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patientDateNaissanceJourDeSemaine(Couverture<String> c);

	public String getPatientDateNaissanceJourDeSemaine() {
		return patientDateNaissanceJourDeSemaine;
	}

	public void setPatientDateNaissanceJourDeSemaine(String patientDateNaissanceJourDeSemaine) {
		this.patientDateNaissanceJourDeSemaine = patientDateNaissanceJourDeSemaine;
		this.patientDateNaissanceJourDeSemaineCouverture.dejaInitialise = true;
	}
	protected PatientMedicale patientDateNaissanceJourDeSemaineInit() {
		if(!patientDateNaissanceJourDeSemaineCouverture.dejaInitialise) {
			_patientDateNaissanceJourDeSemaine(patientDateNaissanceJourDeSemaineCouverture);
			if(patientDateNaissanceJourDeSemaine == null)
				setPatientDateNaissanceJourDeSemaine(patientDateNaissanceJourDeSemaineCouverture.o);
		}
		patientDateNaissanceJourDeSemaineCouverture.dejaInitialise(true);
		return (PatientMedicale)this;
	}

	public String solrPatientDateNaissanceJourDeSemaine() {
		return patientDateNaissanceJourDeSemaine;
	}

	public String strPatientDateNaissanceJourDeSemaine() {
		return patientDateNaissanceJourDeSemaine == null ? "" : patientDateNaissanceJourDeSemaine;
	}

	public String jsonPatientDateNaissanceJourDeSemaine() {
		return patientDateNaissanceJourDeSemaine == null ? "" : patientDateNaissanceJourDeSemaine;
	}

	public String nomAffichagePatientDateNaissanceJourDeSemaine() {
		return null;
	}

	public String htmTooltipPatientDateNaissanceJourDeSemaine() {
		return null;
	}

	public String htmPatientDateNaissanceJourDeSemaine() {
		return patientDateNaissanceJourDeSemaine == null ? "" : StringEscapeUtils.escapeHtml4(strPatientDateNaissanceJourDeSemaine());
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
		patientPrenomInit();
		patientPrenomPrefereInit();
		familleNomInit();
		patientNomCompletInit();
		patientNomCompletPrefereInit();
		patientNomFormelInit();
		patientDateNaissanceInit();
		patientDateNaissanceDAnneeInit();
		patientDateNaissanceMoisDAnneeInit();
		patientDateNaissanceJourDeSemaineInit();
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
			case "patientPrenom":
				return oPatientMedicale.patientPrenom;
			case "patientPrenomPrefere":
				return oPatientMedicale.patientPrenomPrefere;
			case "familleNom":
				return oPatientMedicale.familleNom;
			case "patientNomComplet":
				return oPatientMedicale.patientNomComplet;
			case "patientNomCompletPrefere":
				return oPatientMedicale.patientNomCompletPrefere;
			case "patientNomFormel":
				return oPatientMedicale.patientNomFormel;
			case "patientDateNaissance":
				return oPatientMedicale.patientDateNaissance;
			case "patientDateNaissanceDAnnee":
				return oPatientMedicale.patientDateNaissanceDAnnee;
			case "patientDateNaissanceMoisDAnnee":
				return oPatientMedicale.patientDateNaissanceMoisDAnnee;
			case "patientDateNaissanceJourDeSemaine":
				return oPatientMedicale.patientDateNaissanceJourDeSemaine;
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
			case "patientPrenom":
				if(val != null)
					setPatientPrenom(val);
				sauvegardesPatientMedicale.add(var);
				return val;
			case "patientPrenomPrefere":
				if(val != null)
					setPatientPrenomPrefere(val);
				sauvegardesPatientMedicale.add(var);
				return val;
			case "familleNom":
				if(val != null)
					setFamilleNom(val);
				sauvegardesPatientMedicale.add(var);
				return val;
			case "patientDateNaissance":
				if(val != null)
					setPatientDateNaissance(val);
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

			if(sauvegardesPatientMedicale.contains("patientPrenom")) {
				String patientPrenom = (String)solrDocument.get("patientPrenom_stored_string");
				if(patientPrenom != null)
					oPatientMedicale.setPatientPrenom(patientPrenom);
			}

			if(sauvegardesPatientMedicale.contains("patientPrenomPrefere")) {
				String patientPrenomPrefere = (String)solrDocument.get("patientPrenomPrefere_stored_string");
				if(patientPrenomPrefere != null)
					oPatientMedicale.setPatientPrenomPrefere(patientPrenomPrefere);
			}

			if(sauvegardesPatientMedicale.contains("familleNom")) {
				String familleNom = (String)solrDocument.get("familleNom_stored_string");
				if(familleNom != null)
					oPatientMedicale.setFamilleNom(familleNom);
			}

			if(sauvegardesPatientMedicale.contains("patientNomComplet")) {
				String patientNomComplet = (String)solrDocument.get("patientNomComplet_stored_string");
				if(patientNomComplet != null)
					oPatientMedicale.setPatientNomComplet(patientNomComplet);
			}

			if(sauvegardesPatientMedicale.contains("patientNomCompletPrefere")) {
				String patientNomCompletPrefere = (String)solrDocument.get("patientNomCompletPrefere_stored_string");
				if(patientNomCompletPrefere != null)
					oPatientMedicale.setPatientNomCompletPrefere(patientNomCompletPrefere);
			}

			if(sauvegardesPatientMedicale.contains("patientNomFormel")) {
				String patientNomFormel = (String)solrDocument.get("patientNomFormel_stored_string");
				if(patientNomFormel != null)
					oPatientMedicale.setPatientNomFormel(patientNomFormel);
			}

			if(sauvegardesPatientMedicale.contains("patientDateNaissance")) {
				Date patientDateNaissance = (Date)solrDocument.get("patientDateNaissance_stored_date");
				if(patientDateNaissance != null)
					oPatientMedicale.setPatientDateNaissance(patientDateNaissance);
			}

			if(sauvegardesPatientMedicale.contains("patientDateNaissanceDAnnee")) {
				Integer patientDateNaissanceDAnnee = (Integer)solrDocument.get("patientDateNaissanceDAnnee_stored_int");
				if(patientDateNaissanceDAnnee != null)
					oPatientMedicale.setPatientDateNaissanceDAnnee(patientDateNaissanceDAnnee);
			}

			if(sauvegardesPatientMedicale.contains("patientDateNaissanceMoisDAnnee")) {
				String patientDateNaissanceMoisDAnnee = (String)solrDocument.get("patientDateNaissanceMoisDAnnee_stored_string");
				if(patientDateNaissanceMoisDAnnee != null)
					oPatientMedicale.setPatientDateNaissanceMoisDAnnee(patientDateNaissanceMoisDAnnee);
			}

			if(sauvegardesPatientMedicale.contains("patientDateNaissanceJourDeSemaine")) {
				String patientDateNaissanceJourDeSemaine = (String)solrDocument.get("patientDateNaissanceJourDeSemaine_stored_string");
				if(patientDateNaissanceJourDeSemaine != null)
					oPatientMedicale.setPatientDateNaissanceJourDeSemaine(patientDateNaissanceJourDeSemaine);
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
		if(patientPrenom != null) {
			document.addField("patientPrenom_indexed_string", patientPrenom);
			document.addField("patientPrenom_stored_string", patientPrenom);
		}
		if(patientPrenomPrefere != null) {
			document.addField("patientPrenomPrefere_indexed_string", patientPrenomPrefere);
			document.addField("patientPrenomPrefere_stored_string", patientPrenomPrefere);
		}
		if(familleNom != null) {
			document.addField("familleNom_indexed_string", familleNom);
			document.addField("familleNom_stored_string", familleNom);
		}
		if(patientNomComplet != null) {
			document.addField("patientNomComplet_indexed_string", patientNomComplet);
			document.addField("patientNomComplet_stored_string", patientNomComplet);
		}
		if(patientNomCompletPrefere != null) {
			document.addField("patientNomCompletPrefere_indexed_string", patientNomCompletPrefere);
			document.addField("patientNomCompletPrefere_stored_string", patientNomCompletPrefere);
		}
		if(patientNomFormel != null) {
			document.addField("patientNomFormel_indexed_string", patientNomFormel);
			document.addField("patientNomFormel_stored_string", patientNomFormel);
		}
		if(patientDateNaissance != null) {
			document.addField("patientDateNaissance_indexed_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(patientDateNaissance.atStartOfDay(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
			document.addField("patientDateNaissance_stored_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(patientDateNaissance.atStartOfDay(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
		}
		if(patientDateNaissanceDAnnee != null) {
			document.addField("patientDateNaissanceDAnnee_indexed_int", patientDateNaissanceDAnnee);
			document.addField("patientDateNaissanceDAnnee_stored_int", patientDateNaissanceDAnnee);
		}
		if(patientDateNaissanceMoisDAnnee != null) {
			document.addField("patientDateNaissanceMoisDAnnee_indexed_string", patientDateNaissanceMoisDAnnee);
			document.addField("patientDateNaissanceMoisDAnnee_stored_string", patientDateNaissanceMoisDAnnee);
		}
		if(patientDateNaissanceJourDeSemaine != null) {
			document.addField("patientDateNaissanceJourDeSemaine_indexed_string", patientDateNaissanceJourDeSemaine);
			document.addField("patientDateNaissanceJourDeSemaine_stored_string", patientDateNaissanceJourDeSemaine);
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
			case "patientPrenom":
				return "patientPrenom_indexed_string";
			case "patientPrenomPrefere":
				return "patientPrenomPrefere_indexed_string";
			case "familleNom":
				return "familleNom_indexed_string";
			case "patientNomComplet":
				return "patientNomComplet_indexed_string";
			case "patientNomCompletPrefere":
				return "patientNomCompletPrefere_indexed_string";
			case "patientNomFormel":
				return "patientNomFormel_indexed_string";
			case "patientDateNaissance":
				return "patientDateNaissance_indexed_date";
			case "patientDateNaissanceDAnnee":
				return "patientDateNaissanceDAnnee_indexed_int";
			case "patientDateNaissanceMoisDAnnee":
				return "patientDateNaissanceMoisDAnnee_indexed_string";
			case "patientDateNaissanceJourDeSemaine":
				return "patientDateNaissanceJourDeSemaine_indexed_string";
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

		String patientPrenom = (String)solrDocument.get("patientPrenom_stored_string");
		if(patientPrenom != null)
			oPatientMedicale.setPatientPrenom(patientPrenom);

		String patientPrenomPrefere = (String)solrDocument.get("patientPrenomPrefere_stored_string");
		if(patientPrenomPrefere != null)
			oPatientMedicale.setPatientPrenomPrefere(patientPrenomPrefere);

		String familleNom = (String)solrDocument.get("familleNom_stored_string");
		if(familleNom != null)
			oPatientMedicale.setFamilleNom(familleNom);

		String patientNomComplet = (String)solrDocument.get("patientNomComplet_stored_string");
		if(patientNomComplet != null)
			oPatientMedicale.setPatientNomComplet(patientNomComplet);

		String patientNomCompletPrefere = (String)solrDocument.get("patientNomCompletPrefere_stored_string");
		if(patientNomCompletPrefere != null)
			oPatientMedicale.setPatientNomCompletPrefere(patientNomCompletPrefere);

		String patientNomFormel = (String)solrDocument.get("patientNomFormel_stored_string");
		if(patientNomFormel != null)
			oPatientMedicale.setPatientNomFormel(patientNomFormel);

		Date patientDateNaissance = (Date)solrDocument.get("patientDateNaissance_stored_date");
		if(patientDateNaissance != null)
			oPatientMedicale.setPatientDateNaissance(patientDateNaissance);

		Integer patientDateNaissanceDAnnee = (Integer)solrDocument.get("patientDateNaissanceDAnnee_stored_int");
		if(patientDateNaissanceDAnnee != null)
			oPatientMedicale.setPatientDateNaissanceDAnnee(patientDateNaissanceDAnnee);

		String patientDateNaissanceMoisDAnnee = (String)solrDocument.get("patientDateNaissanceMoisDAnnee_stored_string");
		if(patientDateNaissanceMoisDAnnee != null)
			oPatientMedicale.setPatientDateNaissanceMoisDAnnee(patientDateNaissanceMoisDAnnee);

		String patientDateNaissanceJourDeSemaine = (String)solrDocument.get("patientDateNaissanceJourDeSemaine_stored_string");
		if(patientDateNaissanceJourDeSemaine != null)
			oPatientMedicale.setPatientDateNaissanceJourDeSemaine(patientDateNaissanceJourDeSemaine);

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
			if(!Objects.equals(patientPrenom, original.getPatientPrenom()))
				requeteApi.addVars("patientPrenom");
			if(!Objects.equals(patientPrenomPrefere, original.getPatientPrenomPrefere()))
				requeteApi.addVars("patientPrenomPrefere");
			if(!Objects.equals(familleNom, original.getFamilleNom()))
				requeteApi.addVars("familleNom");
			if(!Objects.equals(patientDateNaissance, original.getPatientDateNaissance()))
				requeteApi.addVars("patientDateNaissance");
			super.requeteApiCluster();
		}
	}

	//////////////
	// hashCode //
	//////////////

	@Override public int hashCode() {
		return Objects.hash(super.hashCode(), inscriptionCles, patientPrenom, patientPrenomPrefere, familleNom, patientDateNaissance);
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
				&& Objects.equals( patientPrenom, that.patientPrenom )
				&& Objects.equals( patientPrenomPrefere, that.patientPrenomPrefere )
				&& Objects.equals( familleNom, that.familleNom )
				&& Objects.equals( patientDateNaissance, that.patientDateNaissance );
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("PatientMedicale { ");
		sb.append( "inscriptionCles: " ).append(inscriptionCles);
		sb.append( ", patientPrenom: \"" ).append(patientPrenom).append( "\"" );
		sb.append( ", patientPrenomPrefere: \"" ).append(patientPrenomPrefere).append( "\"" );
		sb.append( ", familleNom: \"" ).append(familleNom).append( "\"" );
		sb.append( ", patientDateNaissance: " ).append(patientDateNaissance);
		sb.append(" }");
		return sb.toString();
	}
}
