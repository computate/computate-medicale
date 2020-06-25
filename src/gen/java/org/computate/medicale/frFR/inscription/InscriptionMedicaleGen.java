package org.computate.medicale.frFR.inscription;

import java.util.Arrays;
import java.util.Date;
import org.computate.medicale.frFR.requete.api.RequeteApi;
import org.apache.commons.lang3.StringUtils;
import java.lang.Integer;
import org.computate.medicale.frFR.utilisateur.UtilisateurSite;
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
import org.computate.medicale.frFR.patient.PatientMedicale;
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
import java.util.HashMap;
import org.computate.medicale.frFR.inscription.InscriptionMedicale;
import org.computate.medicale.frFR.contexte.SiteContexteFrFR;
import io.vertx.core.logging.LoggerFactory;
import java.text.NumberFormat;
import java.util.ArrayList;
import org.apache.commons.collections.CollectionUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.lang.Boolean;
import org.computate.medicale.frFR.clinique.CliniqueMedicale;
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
 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true">Trouver la classe enrollmentCompleteName dans Solr</a>
 * <br/>
 **/
public abstract class InscriptionMedicaleGen<DEV> extends Cluster {
	protected static final Logger LOGGER = LoggerFactory.getLogger(InscriptionMedicale.class);

	public static final List<String> ROLES = Arrays.asList("SiteAdmin");
	public static final List<String> ROLE_READS = Arrays.asList("");

	public static final String InscriptionMedicale_UnNom = "une inscription";
	public static final String InscriptionMedicale_Ce = "cette ";
	public static final String InscriptionMedicale_CeNom = "cette inscription";
	public static final String InscriptionMedicale_Un = "une ";
	public static final String InscriptionMedicale_LeNom = "l'inscription";
	public static final String InscriptionMedicale_NomSingulier = "inscription";
	public static final String InscriptionMedicale_NomPluriel = "inscriptions";
	public static final String InscriptionMedicale_NomActuel = "inscription actuelle";
	public static final String InscriptionMedicale_Tous = "all ";
	public static final String InscriptionMedicale_TousNom = "toutes les inscriptions";
	public static final String InscriptionMedicale_RechercherTousNomPar = "rechercher inscriptions par ";
	public static final String InscriptionMedicale_RechercherTousNom = "rechercher inscriptions";
	public static final String InscriptionMedicale_LesNom = "les inscriptions";
	public static final String InscriptionMedicale_AucunNomTrouve = "aucune inscription trouvée";
	public static final String InscriptionMedicale_NomVar = "inscription";
	public static final String InscriptionMedicale_DeNom = "d'inscription";
	public static final String InscriptionMedicale_NomAdjectifSingulier = "inscription";
	public static final String InscriptionMedicale_NomAdjectifPluriel = "inscriptions";
	public static final String InscriptionMedicale_Couleur = "blue-gray";
	public static final String InscriptionMedicale_IconeGroupe = "solid";
	public static final String InscriptionMedicale_IconeNom = "notes-medical";

	////////////////////
	// inscriptionCle //
	////////////////////

	/**	L'entité « inscriptionCle »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long inscriptionCle;
	@JsonIgnore
	public Couverture<Long> inscriptionCleCouverture = new Couverture<Long>().p(this).c(Long.class).var("inscriptionCle").o(inscriptionCle);

	/**	<br/>L'entité « inscriptionCle »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptionCle">Trouver l'entité inscriptionCle dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _inscriptionCle(Couverture<Long> c);

	public Long getInscriptionCle() {
		return inscriptionCle;
	}

	public void setInscriptionCle(Long inscriptionCle) {
		this.inscriptionCle = inscriptionCle;
		this.inscriptionCleCouverture.dejaInitialise = true;
	}
	public InscriptionMedicale setInscriptionCle(String o) {
		if(NumberUtils.isParsable(o))
			this.inscriptionCle = Long.parseLong(o);
		this.inscriptionCleCouverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	protected InscriptionMedicale inscriptionCleInit() {
		if(!inscriptionCleCouverture.dejaInitialise) {
			_inscriptionCle(inscriptionCleCouverture);
			if(inscriptionCle == null)
				setInscriptionCle(inscriptionCleCouverture.o);
		}
		inscriptionCleCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public Long solrInscriptionCle() {
		return inscriptionCle;
	}

	public String strInscriptionCle() {
		return inscriptionCle == null ? "" : inscriptionCle.toString();
	}

	public String jsonInscriptionCle() {
		return inscriptionCle == null ? "" : inscriptionCle.toString();
	}

	public String nomAffichageInscriptionCle() {
		return "clé";
	}

	public String htmTooltipInscriptionCle() {
		return null;
	}

	public String htmInscriptionCle() {
		return inscriptionCle == null ? "" : StringEscapeUtils.escapeHtml4(strInscriptionCle());
	}

	/////////////////
	// cliniqueCle //
	/////////////////

	/**	L'entité « cliniqueCle »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long cliniqueCle;
	@JsonIgnore
	public Couverture<Long> cliniqueCleCouverture = new Couverture<Long>().p(this).c(Long.class).var("cliniqueCle").o(cliniqueCle);

	/**	<br/>L'entité « cliniqueCle »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:cliniqueCle">Trouver l'entité cliniqueCle dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _cliniqueCle(Couverture<Long> c);

	public Long getCliniqueCle() {
		return cliniqueCle;
	}

	public void setCliniqueCle(Long cliniqueCle) {
		this.cliniqueCle = cliniqueCle;
		this.cliniqueCleCouverture.dejaInitialise = true;
	}
	public InscriptionMedicale setCliniqueCle(String o) {
		if(NumberUtils.isParsable(o))
			this.cliniqueCle = Long.parseLong(o);
		this.cliniqueCleCouverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	protected InscriptionMedicale cliniqueCleInit() {
		if(!cliniqueCleCouverture.dejaInitialise) {
			_cliniqueCle(cliniqueCleCouverture);
			if(cliniqueCle == null)
				setCliniqueCle(cliniqueCleCouverture.o);
		}
		cliniqueCleCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public Long solrCliniqueCle() {
		return cliniqueCle;
	}

	public String strCliniqueCle() {
		return cliniqueCle == null ? "" : cliniqueCle.toString();
	}

	public String jsonCliniqueCle() {
		return cliniqueCle == null ? "" : cliniqueCle.toString();
	}

	public String nomAffichageCliniqueCle() {
		return "clinique";
	}

	public String htmTooltipCliniqueCle() {
		return null;
	}

	public String htmCliniqueCle() {
		return cliniqueCle == null ? "" : StringEscapeUtils.escapeHtml4(strCliniqueCle());
	}

	///////////////////////
	// cliniqueRecherche //
	///////////////////////

	/**	L'entité « cliniqueRecherche »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut ListeRecherche<CliniqueMedicale>(). 
	 */
	@JsonIgnore
	@JsonInclude(Include.NON_NULL)
	protected ListeRecherche<CliniqueMedicale> cliniqueRecherche = new ListeRecherche<CliniqueMedicale>();
	@JsonIgnore
	public Couverture<ListeRecherche<CliniqueMedicale>> cliniqueRechercheCouverture = new Couverture<ListeRecherche<CliniqueMedicale>>().p(this).c(ListeRecherche.class).var("cliniqueRecherche").o(cliniqueRecherche);

	/**	<br/>L'entité « cliniqueRecherche »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut ListeRecherche<CliniqueMedicale>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:cliniqueRecherche">Trouver l'entité cliniqueRecherche dans Solr</a>
	 * <br/>
	 * @param cliniqueRecherche est l'entité déjà construit. 
	 **/
	protected abstract void _cliniqueRecherche(ListeRecherche<CliniqueMedicale> l);

	public ListeRecherche<CliniqueMedicale> getCliniqueRecherche() {
		return cliniqueRecherche;
	}

	public void setCliniqueRecherche(ListeRecherche<CliniqueMedicale> cliniqueRecherche) {
		this.cliniqueRecherche = cliniqueRecherche;
		this.cliniqueRechercheCouverture.dejaInitialise = true;
	}
	protected InscriptionMedicale cliniqueRechercheInit() {
		if(!cliniqueRechercheCouverture.dejaInitialise) {
			_cliniqueRecherche(cliniqueRecherche);
		}
		cliniqueRecherche.initLoinPourClasse(requeteSite_);
		cliniqueRechercheCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	///////////////
	// clinique_ //
	///////////////

	/**	L'entité « clinique_ »
	 *	 is defined as null before being initialized. 
	 */
	@JsonIgnore
	@JsonInclude(Include.NON_NULL)
	protected CliniqueMedicale clinique_;
	@JsonIgnore
	public Couverture<CliniqueMedicale> clinique_Couverture = new Couverture<CliniqueMedicale>().p(this).c(CliniqueMedicale.class).var("clinique_").o(clinique_);

	/**	<br/>L'entité « clinique_ »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:clinique_">Trouver l'entité clinique_ dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _clinique_(Couverture<CliniqueMedicale> c);

	public CliniqueMedicale getClinique_() {
		return clinique_;
	}

	public void setClinique_(CliniqueMedicale clinique_) {
		this.clinique_ = clinique_;
		this.clinique_Couverture.dejaInitialise = true;
	}
	protected InscriptionMedicale clinique_Init() {
		if(!clinique_Couverture.dejaInitialise) {
			_clinique_(clinique_Couverture);
			if(clinique_ == null)
				setClinique_(clinique_Couverture.o);
		}
		clinique_Couverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:patientCle">Trouver l'entité patientCle dans Solr</a>
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
	public InscriptionMedicale setPatientCle(String o) {
		if(NumberUtils.isParsable(o))
			this.patientCle = Long.parseLong(o);
		this.patientCleCouverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	protected InscriptionMedicale patientCleInit() {
		if(!patientCleCouverture.dejaInitialise) {
			_patientCle(patientCleCouverture);
			if(patientCle == null)
				setPatientCle(patientCleCouverture.o);
		}
		patientCleCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
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
		return "patients";
	}

	public String htmTooltipPatientCle() {
		return null;
	}

	public String htmPatientCle() {
		return patientCle == null ? "" : StringEscapeUtils.escapeHtml4(strPatientCle());
	}

	public void inputPatientCle(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("i").a("class", "far fa-search w3-xxlarge w3-cell w3-cell-middle ").f().g("i");
				e("input")
					.a("type", "text")
					.a("placeholder", "patients")
					.a("title", "La clé primaire des utilisateurs dans la base de données. ")
					.a("class", "valeur suggerePatientCle w3-input w3-border w3-cell w3-cell-middle ")
					.a("name", "setPatientCle")
					.a("id", classeApiMethodeMethode, "_patientCle")
					.a("autocomplete", "off")
					.a("oninput", "suggereInscriptionMedicalePatientCle($(this).val() ? rechercherPatientMedicaleFiltres($(this.parentElement)) : [", pk == null ? "" : "{'name':'fq','value':'inscriptionCles:" + pk + "'}", "], $('#listInscriptionMedicalePatientCle_", classeApiMethodeMethode, "'), ", pk, "); ")
				.fg();

		} else {
			sx(htmPatientCle());
		}
	}

	public void htmPatientCle(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "InscriptionMedicalePatientCle").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row ").f();
							{ e("a").a("href", "?fq=inscriptionCles:", pk).a("class", "w3-cell w3-btn w3-center h4 w3-block h4 w3-orange w3-hover-orange ").f();
								e("i").a("class", "far fa-hospital-user ").f().g("i");
								sx("patients");
							} g("a");
						} g("div");
						{ e("div").a("class", "w3-cell-row ").f();
							{ e("h5").a("class", "w3-cell ").f();
								sx("relier un patient a cette inscription");
							} g("h5");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();
								{ e("div").a("class", "w3-cell-row ").f();

								inputPatientCle(classeApiMethodeMethode);
								} g("div");
							} g("div");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
								{ e("ul").a("class", "w3-ul w3-hoverable ").a("id", "listInscriptionMedicalePatientCle_", classeApiMethodeMethode).f();
								} g("ul");
								{
									{ e("div").a("class", "w3-cell-row ").f();
										e("button")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-orange ")
											.a("id", classeApiMethodeMethode, "_patientCle_ajouter")
											.a("onclick", "$(this).addClass('w3-disabled'); this.disabled = true; this.innerHTML = 'Envoi…'; postPatientMedicaleVals({ inscriptionCles: [ \"", pk, "\" ] }, function() {}, function() { ajouterErreur($('#", classeApiMethodeMethode, "patientCle')); });")
											.f().sx("ajouter un patient")
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

	////////////////////////
	// formInscriptionCle //
	////////////////////////

	/**	L'entité « formInscriptionCle »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long formInscriptionCle;
	@JsonIgnore
	public Couverture<Long> formInscriptionCleCouverture = new Couverture<Long>().p(this).c(Long.class).var("formInscriptionCle").o(formInscriptionCle);

	/**	<br/>L'entité « formInscriptionCle »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:formInscriptionCle">Trouver l'entité formInscriptionCle dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _formInscriptionCle(Couverture<Long> c);

	public Long getFormInscriptionCle() {
		return formInscriptionCle;
	}

	public void setFormInscriptionCle(Long formInscriptionCle) {
		this.formInscriptionCle = formInscriptionCle;
		this.formInscriptionCleCouverture.dejaInitialise = true;
	}
	public InscriptionMedicale setFormInscriptionCle(String o) {
		if(NumberUtils.isParsable(o))
			this.formInscriptionCle = Long.parseLong(o);
		this.formInscriptionCleCouverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	protected InscriptionMedicale formInscriptionCleInit() {
		if(!formInscriptionCleCouverture.dejaInitialise) {
			_formInscriptionCle(formInscriptionCleCouverture);
			if(formInscriptionCle == null)
				setFormInscriptionCle(formInscriptionCleCouverture.o);
		}
		formInscriptionCleCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public Long solrFormInscriptionCle() {
		return formInscriptionCle;
	}

	public String strFormInscriptionCle() {
		return formInscriptionCle == null ? "" : formInscriptionCle.toString();
	}

	public String jsonFormInscriptionCle() {
		return formInscriptionCle == null ? "" : formInscriptionCle.toString();
	}

	public String nomAffichageFormInscriptionCle() {
		return "formulaire d'inscription";
	}

	public String htmTooltipFormInscriptionCle() {
		return null;
	}

	public String htmFormInscriptionCle() {
		return formInscriptionCle == null ? "" : StringEscapeUtils.escapeHtml4(strFormInscriptionCle());
	}

	/////////////////////
	// utilisateurCles //
	/////////////////////

	/**	L'entité « utilisateurCles »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> utilisateurCles = new ArrayList<Long>();
	@JsonIgnore
	public Couverture<List<Long>> utilisateurClesCouverture = new Couverture<List<Long>>().p(this).c(List.class).var("utilisateurCles").o(utilisateurCles);

	/**	<br/>L'entité « utilisateurCles »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:utilisateurCles">Trouver l'entité utilisateurCles dans Solr</a>
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
	public InscriptionMedicale addUtilisateurCles(Long...objets) {
		for(Long o : objets) {
			addUtilisateurCles(o);
		}
		return (InscriptionMedicale)this;
	}
	public InscriptionMedicale addUtilisateurCles(Long o) {
		if(o != null && !utilisateurCles.contains(o))
			this.utilisateurCles.add(o);
		return (InscriptionMedicale)this;
	}
	public InscriptionMedicale setUtilisateurCles(JsonArray objets) {
		utilisateurCles.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addUtilisateurCles(o);
		}
		return (InscriptionMedicale)this;
	}
	public InscriptionMedicale addUtilisateurCles(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addUtilisateurCles(p);
		}
		return (InscriptionMedicale)this;
	}
	protected InscriptionMedicale utilisateurClesInit() {
		if(!utilisateurClesCouverture.dejaInitialise) {
			_utilisateurCles(utilisateurCles);
		}
		utilisateurClesCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
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
		return "utilisateurs";
	}

	public String htmTooltipUtilisateurCles() {
		return null;
	}

	public String htmUtilisateurCles() {
		return utilisateurCles == null ? "" : StringEscapeUtils.escapeHtml4(strUtilisateurCles());
	}

	public void inputUtilisateurCles(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("i").a("class", "far fa-search w3-xxlarge w3-cell w3-cell-middle ").f().g("i");
				e("input")
					.a("type", "text")
					.a("placeholder", "utilisateurs")
					.a("title", "La clé primaire des utilisateurs dans la base de données. ")
					.a("class", "valeur suggereUtilisateurCles w3-input w3-border w3-cell w3-cell-middle ")
					.a("name", "setUtilisateurCles")
					.a("id", classeApiMethodeMethode, "_utilisateurCles")
					.a("autocomplete", "off")
					.a("oninput", "suggereInscriptionMedicaleUtilisateurCles($(this).val() ? rechercherUtilisateurSiteFiltres($(this.parentElement)) : [", pk == null ? "" : "{'name':'fq','value':'inscriptionCles:" + pk + "'}", "], $('#listInscriptionMedicaleUtilisateurCles_", classeApiMethodeMethode, "'), ", pk, "); ")
				.fg();

		} else {
			sx(htmUtilisateurCles());
		}
	}

	public void htmUtilisateurCles(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "InscriptionMedicaleUtilisateurCles").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row ").f();
							{ e("a").a("href", "?fq=inscriptionCles:", pk).a("class", "w3-cell w3-btn w3-center h4 w3-block h4 w3-gray w3-hover-gray ").f();
								e("i").a("class", "far fa-user-cog ").f().g("i");
								sx("utilisateurs");
							} g("a");
						} g("div");
						{ e("div").a("class", "w3-cell-row ").f();
							{ e("h5").a("class", "w3-cell ").f();
								sx("relier  a cette inscription");
							} g("h5");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();
								{ e("div").a("class", "w3-cell-row ").f();

								inputUtilisateurCles(classeApiMethodeMethode);
								} g("div");
							} g("div");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
								{ e("ul").a("class", "w3-ul w3-hoverable ").a("id", "listInscriptionMedicaleUtilisateurCles_", classeApiMethodeMethode).f();
								} g("ul");
								if(
										CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), UtilisateurSite.ROLES)
										|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), UtilisateurSite.ROLES)
										) {
									{ e("div").a("class", "w3-cell-row ").f();
										e("button")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-gray ")
											.a("id", classeApiMethodeMethode, "_utilisateurCles_ajouter")
											.a("onclick", "$(this).addClass('w3-disabled'); this.disabled = true; this.innerHTML = 'Envoi…'; postUtilisateurSiteVals({ inscriptionCles: [ \"", pk, "\" ] }, function() {}, function() { ajouterErreur($('#", classeApiMethodeMethode, "utilisateurCles')); });")
											.f().sx("ajouter un utilisateur du site")
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

	/////////////////
	// medicaleTri //
	/////////////////

	/**	L'entité « medicaleTri »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer medicaleTri;
	@JsonIgnore
	public Couverture<Integer> medicaleTriCouverture = new Couverture<Integer>().p(this).c(Integer.class).var("medicaleTri").o(medicaleTri);

	/**	<br/>L'entité « medicaleTri »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:medicaleTri">Trouver l'entité medicaleTri dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _medicaleTri(Couverture<Integer> c);

	public Integer getMedicaleTri() {
		return medicaleTri;
	}

	public void setMedicaleTri(Integer medicaleTri) {
		this.medicaleTri = medicaleTri;
		this.medicaleTriCouverture.dejaInitialise = true;
	}
	public InscriptionMedicale setMedicaleTri(String o) {
		if(NumberUtils.isParsable(o))
			this.medicaleTri = Integer.parseInt(o);
		this.medicaleTriCouverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	protected InscriptionMedicale medicaleTriInit() {
		if(!medicaleTriCouverture.dejaInitialise) {
			_medicaleTri(medicaleTriCouverture);
			if(medicaleTri == null)
				setMedicaleTri(medicaleTriCouverture.o);
		}
		medicaleTriCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public Integer solrMedicaleTri() {
		return medicaleTri;
	}

	public String strMedicaleTri() {
		return medicaleTri == null ? "" : medicaleTri.toString();
	}

	public String jsonMedicaleTri() {
		return medicaleTri == null ? "" : medicaleTri.toString();
	}

	public String nomAffichageMedicaleTri() {
		return null;
	}

	public String htmTooltipMedicaleTri() {
		return null;
	}

	public String htmMedicaleTri() {
		return medicaleTri == null ? "" : StringEscapeUtils.escapeHtml4(strMedicaleTri());
	}

	/////////////////
	// cliniqueTri //
	/////////////////

	/**	L'entité « cliniqueTri »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer cliniqueTri;
	@JsonIgnore
	public Couverture<Integer> cliniqueTriCouverture = new Couverture<Integer>().p(this).c(Integer.class).var("cliniqueTri").o(cliniqueTri);

	/**	<br/>L'entité « cliniqueTri »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:cliniqueTri">Trouver l'entité cliniqueTri dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _cliniqueTri(Couverture<Integer> c);

	public Integer getCliniqueTri() {
		return cliniqueTri;
	}

	public void setCliniqueTri(Integer cliniqueTri) {
		this.cliniqueTri = cliniqueTri;
		this.cliniqueTriCouverture.dejaInitialise = true;
	}
	public InscriptionMedicale setCliniqueTri(String o) {
		if(NumberUtils.isParsable(o))
			this.cliniqueTri = Integer.parseInt(o);
		this.cliniqueTriCouverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	protected InscriptionMedicale cliniqueTriInit() {
		if(!cliniqueTriCouverture.dejaInitialise) {
			_cliniqueTri(cliniqueTriCouverture);
			if(cliniqueTri == null)
				setCliniqueTri(cliniqueTriCouverture.o);
		}
		cliniqueTriCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public Integer solrCliniqueTri() {
		return cliniqueTri;
	}

	public String strCliniqueTri() {
		return cliniqueTri == null ? "" : cliniqueTri.toString();
	}

	public String jsonCliniqueTri() {
		return cliniqueTri == null ? "" : cliniqueTri.toString();
	}

	public String nomAffichageCliniqueTri() {
		return null;
	}

	public String htmTooltipCliniqueTri() {
		return null;
	}

	public String htmCliniqueTri() {
		return cliniqueTri == null ? "" : StringEscapeUtils.escapeHtml4(strCliniqueTri());
	}

	//////////////////////
	// patientRecherche //
	//////////////////////

	/**	L'entité « patientRecherche »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut ListeRecherche<PatientMedicale>(). 
	 */
	@JsonIgnore
	@JsonInclude(Include.NON_NULL)
	protected ListeRecherche<PatientMedicale> patientRecherche = new ListeRecherche<PatientMedicale>();
	@JsonIgnore
	public Couverture<ListeRecherche<PatientMedicale>> patientRechercheCouverture = new Couverture<ListeRecherche<PatientMedicale>>().p(this).c(ListeRecherche.class).var("patientRecherche").o(patientRecherche);

	/**	<br/>L'entité « patientRecherche »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut ListeRecherche<PatientMedicale>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:patientRecherche">Trouver l'entité patientRecherche dans Solr</a>
	 * <br/>
	 * @param patientRecherche est l'entité déjà construit. 
	 **/
	protected abstract void _patientRecherche(ListeRecherche<PatientMedicale> l);

	public ListeRecherche<PatientMedicale> getPatientRecherche() {
		return patientRecherche;
	}

	public void setPatientRecherche(ListeRecherche<PatientMedicale> patientRecherche) {
		this.patientRecherche = patientRecherche;
		this.patientRechercheCouverture.dejaInitialise = true;
	}
	protected InscriptionMedicale patientRechercheInit() {
		if(!patientRechercheCouverture.dejaInitialise) {
			_patientRecherche(patientRecherche);
		}
		patientRecherche.initLoinPourClasse(requeteSite_);
		patientRechercheCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	//////////////
	// patient_ //
	//////////////

	/**	L'entité « patient_ »
	 *	 is defined as null before being initialized. 
	 */
	@JsonIgnore
	@JsonInclude(Include.NON_NULL)
	protected PatientMedicale patient_;
	@JsonIgnore
	public Couverture<PatientMedicale> patient_Couverture = new Couverture<PatientMedicale>().p(this).c(PatientMedicale.class).var("patient_").o(patient_);

	/**	<br/>L'entité « patient_ »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:patient_">Trouver l'entité patient_ dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patient_(Couverture<PatientMedicale> c);

	public PatientMedicale getPatient_() {
		return patient_;
	}

	public void setPatient_(PatientMedicale patient_) {
		this.patient_ = patient_;
		this.patient_Couverture.dejaInitialise = true;
	}
	protected InscriptionMedicale patient_Init() {
		if(!patient_Couverture.dejaInitialise) {
			_patient_(patient_Couverture);
			if(patient_ == null)
				setPatient_(patient_Couverture.o);
		}
		patient_Couverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:patientPrenom">Trouver l'entité patientPrenom dans Solr</a>
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
	protected InscriptionMedicale patientPrenomInit() {
		if(!patientPrenomCouverture.dejaInitialise) {
			_patientPrenom(patientPrenomCouverture);
			if(patientPrenom == null)
				setPatientPrenom(patientPrenomCouverture.o);
		}
		patientPrenomCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
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
		return null;
	}

	public String htmTooltipPatientPrenom() {
		return null;
	}

	public String htmPatientPrenom() {
		return patientPrenom == null ? "" : StringEscapeUtils.escapeHtml4(strPatientPrenom());
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:patientPrenomPrefere">Trouver l'entité patientPrenomPrefere dans Solr</a>
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
	protected InscriptionMedicale patientPrenomPrefereInit() {
		if(!patientPrenomPrefereCouverture.dejaInitialise) {
			_patientPrenomPrefere(patientPrenomPrefereCouverture);
			if(patientPrenomPrefere == null)
				setPatientPrenomPrefere(patientPrenomPrefereCouverture.o);
		}
		patientPrenomPrefereCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
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
		return null;
	}

	public String htmTooltipPatientPrenomPrefere() {
		return null;
	}

	public String htmPatientPrenomPrefere() {
		return patientPrenomPrefere == null ? "" : StringEscapeUtils.escapeHtml4(strPatientPrenomPrefere());
	}

	///////////////////////
	// patientFamilleNom //
	///////////////////////

	/**	L'entité « patientFamilleNom »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String patientFamilleNom;
	@JsonIgnore
	public Couverture<String> patientFamilleNomCouverture = new Couverture<String>().p(this).c(String.class).var("patientFamilleNom").o(patientFamilleNom);

	/**	<br/>L'entité « patientFamilleNom »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:patientFamilleNom">Trouver l'entité patientFamilleNom dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patientFamilleNom(Couverture<String> c);

	public String getPatientFamilleNom() {
		return patientFamilleNom;
	}

	public void setPatientFamilleNom(String patientFamilleNom) {
		this.patientFamilleNom = patientFamilleNom;
		this.patientFamilleNomCouverture.dejaInitialise = true;
	}
	protected InscriptionMedicale patientFamilleNomInit() {
		if(!patientFamilleNomCouverture.dejaInitialise) {
			_patientFamilleNom(patientFamilleNomCouverture);
			if(patientFamilleNom == null)
				setPatientFamilleNom(patientFamilleNomCouverture.o);
		}
		patientFamilleNomCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public String solrPatientFamilleNom() {
		return patientFamilleNom;
	}

	public String strPatientFamilleNom() {
		return patientFamilleNom == null ? "" : patientFamilleNom;
	}

	public String jsonPatientFamilleNom() {
		return patientFamilleNom == null ? "" : patientFamilleNom;
	}

	public String nomAffichagePatientFamilleNom() {
		return null;
	}

	public String htmTooltipPatientFamilleNom() {
		return null;
	}

	public String htmPatientFamilleNom() {
		return patientFamilleNom == null ? "" : StringEscapeUtils.escapeHtml4(strPatientFamilleNom());
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:patientNomComplet">Trouver l'entité patientNomComplet dans Solr</a>
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
	protected InscriptionMedicale patientNomCompletInit() {
		if(!patientNomCompletCouverture.dejaInitialise) {
			_patientNomComplet(patientNomCompletCouverture);
			if(patientNomComplet == null)
				setPatientNomComplet(patientNomCompletCouverture.o);
		}
		patientNomCompletCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
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
		return "NomAffichage.enUS: ";
	}

	public String htmTooltipPatientNomComplet() {
		return null;
	}

	public String htmPatientNomComplet() {
		return patientNomComplet == null ? "" : StringEscapeUtils.escapeHtml4(strPatientNomComplet());
	}

	public void inputPatientNomComplet(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("placeholder", "NomAffichage.enUS: ")
				.a("title", "La clé primaire des utilisateurs dans la base de données. ")
				.a("id", classeApiMethodeMethode, "_patientNomComplet");
				if("Page".equals(classeApiMethodeMethode) || "PATCH".equals(classeApiMethodeMethode)) {
					a("class", "setPatientNomComplet classInscriptionMedicale inputInscriptionMedicale", pk, "PatientNomComplet w3-input w3-border ");
					a("name", "setPatientNomComplet");
				} else {
					a("class", "valeurPatientNomComplet w3-input w3-border classInscriptionMedicale inputInscriptionMedicale", pk, "PatientNomComplet w3-input w3-border ");
					a("name", "patientNomComplet");
				}
				if("Page".equals(classeApiMethodeMethode)) {
					a("onclick", "enleverLueur($(this)); ");
					a("onchange", "patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setPatientNomComplet', $(this).val(), function() { ajouterLueur($('#", classeApiMethodeMethode, "_patientNomComplet')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_patientNomComplet')); }); ");
				}
				a("value", strPatientNomComplet())
			.fg();

		} else {
			sx(htmPatientNomComplet());
		}
	}

	public void htmPatientNomComplet(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "InscriptionMedicalePatientNomComplet").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-blue-gray ").f();
							e("label").a("for", classeApiMethodeMethode, "_patientNomComplet").a("class", "").f().sx("NomAffichage.enUS: ").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputPatientNomComplet(classeApiMethodeMethode);
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
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_patientNomComplet')); $('#", classeApiMethodeMethode, "_patientNomComplet').val(null); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + $('#InscriptionMedicaleForm :input[name=pk]').val() }], 'setPatientNomComplet', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_patientNomComplet')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_patientNomComplet')); }); ")
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:patientNomCompletPrefere">Trouver l'entité patientNomCompletPrefere dans Solr</a>
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
	protected InscriptionMedicale patientNomCompletPrefereInit() {
		if(!patientNomCompletPrefereCouverture.dejaInitialise) {
			_patientNomCompletPrefere(patientNomCompletPrefereCouverture);
			if(patientNomCompletPrefere == null)
				setPatientNomCompletPrefere(patientNomCompletPrefereCouverture.o);
		}
		patientNomCompletPrefereCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
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
		return "NomAffichage.enUS: ";
	}

	public String htmTooltipPatientNomCompletPrefere() {
		return null;
	}

	public String htmPatientNomCompletPrefere() {
		return patientNomCompletPrefere == null ? "" : StringEscapeUtils.escapeHtml4(strPatientNomCompletPrefere());
	}

	public void inputPatientNomCompletPrefere(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("placeholder", "NomAffichage.enUS: ")
				.a("title", "La clé primaire des utilisateurs dans la base de données. ")
				.a("id", classeApiMethodeMethode, "_patientNomCompletPrefere");
				if("Page".equals(classeApiMethodeMethode) || "PATCH".equals(classeApiMethodeMethode)) {
					a("class", "setPatientNomCompletPrefere classInscriptionMedicale inputInscriptionMedicale", pk, "PatientNomCompletPrefere w3-input w3-border ");
					a("name", "setPatientNomCompletPrefere");
				} else {
					a("class", "valeurPatientNomCompletPrefere w3-input w3-border classInscriptionMedicale inputInscriptionMedicale", pk, "PatientNomCompletPrefere w3-input w3-border ");
					a("name", "patientNomCompletPrefere");
				}
				if("Page".equals(classeApiMethodeMethode)) {
					a("onclick", "enleverLueur($(this)); ");
					a("onchange", "patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setPatientNomCompletPrefere', $(this).val(), function() { ajouterLueur($('#", classeApiMethodeMethode, "_patientNomCompletPrefere')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_patientNomCompletPrefere')); }); ");
				}
				a("value", strPatientNomCompletPrefere())
			.fg();

		} else {
			sx(htmPatientNomCompletPrefere());
		}
	}

	public void htmPatientNomCompletPrefere(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "InscriptionMedicalePatientNomCompletPrefere").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-blue-gray ").f();
							e("label").a("for", classeApiMethodeMethode, "_patientNomCompletPrefere").a("class", "").f().sx("NomAffichage.enUS: ").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputPatientNomCompletPrefere(classeApiMethodeMethode);
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
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_patientNomCompletPrefere')); $('#", classeApiMethodeMethode, "_patientNomCompletPrefere').val(null); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + $('#InscriptionMedicaleForm :input[name=pk]').val() }], 'setPatientNomCompletPrefere', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_patientNomCompletPrefere')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_patientNomCompletPrefere')); }); ")
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:patientDateNaissance">Trouver l'entité patientDateNaissance dans Solr</a>
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
	public InscriptionMedicale setPatientDateNaissance(Instant o) {
		this.patientDateNaissance = o == null ? null : LocalDate.from(o);
		this.patientDateNaissanceCouverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	/** Example: 2011-12-03+01:00 **/
	public InscriptionMedicale setPatientDateNaissance(String o) {
		this.patientDateNaissance = o == null ? null : LocalDate.parse(o, DateTimeFormatter.ISO_DATE);
		this.patientDateNaissanceCouverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	public InscriptionMedicale setPatientDateNaissance(Date o) {
		this.patientDateNaissance = o == null ? null : o.toInstant().atZone(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toLocalDate();
		this.patientDateNaissanceCouverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	protected InscriptionMedicale patientDateNaissanceInit() {
		if(!patientDateNaissanceCouverture.dejaInitialise) {
			_patientDateNaissance(patientDateNaissanceCouverture);
			if(patientDateNaissance == null)
				setPatientDateNaissance(patientDateNaissanceCouverture.o);
		}
		patientDateNaissanceCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
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
		return "NomAffichage.enUS: ";
	}

	public String htmTooltipPatientDateNaissance() {
		return null;
	}

	public String htmPatientDateNaissance() {
		return patientDateNaissance == null ? "" : StringEscapeUtils.escapeHtml4(strPatientDateNaissance());
	}

	public void inputPatientDateNaissance(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("class", "w3-input w3-border datepicker setPatientDateNaissance classInscriptionMedicale inputInscriptionMedicale", pk, "PatientDateNaissance w3-input w3-border ")
				.a("placeholder", "DD-MM-YYYY")
				.a("data-timeformat", "dd-MM-yyyy")
				.a("id", classeApiMethodeMethode, "_patientDateNaissance")
				.a("onclick", "enleverLueur($(this)); ")
				.a("title", "La clé primaire des utilisateurs dans la base de données.  (DD-MM-YYYY)")
				.a("value", patientDateNaissance == null ? "" : DateTimeFormatter.ofPattern("dd-MM-yyyy").format(patientDateNaissance))
				.a("onchange", "var t = moment(this.value, 'DD-MM-YYYY'); if(t) { var s = t.format('YYYY-MM-DD'); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setPatientDateNaissance', s, function() { ajouterLueur($('#", classeApiMethodeMethode, "_patientDateNaissance')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_patientDateNaissance')); }); } ")
				.fg();
		} else {
			sx(htmPatientDateNaissance());
		}
	}

	public void htmPatientDateNaissance(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "InscriptionMedicalePatientDateNaissance").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-blue-gray ").f();
							e("label").a("for", classeApiMethodeMethode, "_patientDateNaissance").a("class", "").f().sx("NomAffichage.enUS: ").g("label");
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
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_patientDateNaissance')); $('#", classeApiMethodeMethode, "_patientDateNaissance').val(null); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + $('#InscriptionMedicaleForm :input[name=pk]').val() }], 'setPatientDateNaissance', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_patientDateNaissance')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_patientDateNaissance')); }); ")
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:patientDateNaissanceDAnnee">Trouver l'entité patientDateNaissanceDAnnee dans Solr</a>
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
	public InscriptionMedicale setPatientDateNaissanceDAnnee(String o) {
		if(NumberUtils.isParsable(o))
			this.patientDateNaissanceDAnnee = Integer.parseInt(o);
		this.patientDateNaissanceDAnneeCouverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	protected InscriptionMedicale patientDateNaissanceDAnneeInit() {
		if(!patientDateNaissanceDAnneeCouverture.dejaInitialise) {
			_patientDateNaissanceDAnnee(patientDateNaissanceDAnneeCouverture);
			if(patientDateNaissanceDAnnee == null)
				setPatientDateNaissanceDAnnee(patientDateNaissanceDAnneeCouverture.o);
		}
		patientDateNaissanceDAnneeCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:patientDateNaissanceMoisDAnnee">Trouver l'entité patientDateNaissanceMoisDAnnee dans Solr</a>
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
	protected InscriptionMedicale patientDateNaissanceMoisDAnneeInit() {
		if(!patientDateNaissanceMoisDAnneeCouverture.dejaInitialise) {
			_patientDateNaissanceMoisDAnnee(patientDateNaissanceMoisDAnneeCouverture);
			if(patientDateNaissanceMoisDAnnee == null)
				setPatientDateNaissanceMoisDAnnee(patientDateNaissanceMoisDAnneeCouverture.o);
		}
		patientDateNaissanceMoisDAnneeCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:patientDateNaissanceJourDeSemaine">Trouver l'entité patientDateNaissanceJourDeSemaine dans Solr</a>
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
	protected InscriptionMedicale patientDateNaissanceJourDeSemaineInit() {
		if(!patientDateNaissanceJourDeSemaineCouverture.dejaInitialise) {
			_patientDateNaissanceJourDeSemaine(patientDateNaissanceJourDeSemaineCouverture);
			if(patientDateNaissanceJourDeSemaine == null)
				setPatientDateNaissanceJourDeSemaine(patientDateNaissanceJourDeSemaineCouverture.o);
		}
		patientDateNaissanceJourDeSemaineCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
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

	//////////////////////////
	// patientMoisNaissance //
	//////////////////////////

	/**	L'entité « patientMoisNaissance »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer patientMoisNaissance;
	@JsonIgnore
	public Couverture<Integer> patientMoisNaissanceCouverture = new Couverture<Integer>().p(this).c(Integer.class).var("patientMoisNaissance").o(patientMoisNaissance);

	/**	<br/>L'entité « patientMoisNaissance »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:patientMoisNaissance">Trouver l'entité patientMoisNaissance dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patientMoisNaissance(Couverture<Integer> c);

	public Integer getPatientMoisNaissance() {
		return patientMoisNaissance;
	}

	public void setPatientMoisNaissance(Integer patientMoisNaissance) {
		this.patientMoisNaissance = patientMoisNaissance;
		this.patientMoisNaissanceCouverture.dejaInitialise = true;
	}
	public InscriptionMedicale setPatientMoisNaissance(String o) {
		if(NumberUtils.isParsable(o))
			this.patientMoisNaissance = Integer.parseInt(o);
		this.patientMoisNaissanceCouverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	protected InscriptionMedicale patientMoisNaissanceInit() {
		if(!patientMoisNaissanceCouverture.dejaInitialise) {
			_patientMoisNaissance(patientMoisNaissanceCouverture);
			if(patientMoisNaissance == null)
				setPatientMoisNaissance(patientMoisNaissanceCouverture.o);
		}
		patientMoisNaissanceCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public Integer solrPatientMoisNaissance() {
		return patientMoisNaissance;
	}

	public String strPatientMoisNaissance() {
		return patientMoisNaissance == null ? "" : patientMoisNaissance.toString();
	}

	public String jsonPatientMoisNaissance() {
		return patientMoisNaissance == null ? "" : patientMoisNaissance.toString();
	}

	public String nomAffichagePatientMoisNaissance() {
		return null;
	}

	public String htmTooltipPatientMoisNaissance() {
		return null;
	}

	public String htmPatientMoisNaissance() {
		return patientMoisNaissance == null ? "" : StringEscapeUtils.escapeHtml4(strPatientMoisNaissance());
	}

	//////////////////////////
	// patientJourNaissance //
	//////////////////////////

	/**	L'entité « patientJourNaissance »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer patientJourNaissance;
	@JsonIgnore
	public Couverture<Integer> patientJourNaissanceCouverture = new Couverture<Integer>().p(this).c(Integer.class).var("patientJourNaissance").o(patientJourNaissance);

	/**	<br/>L'entité « patientJourNaissance »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:patientJourNaissance">Trouver l'entité patientJourNaissance dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patientJourNaissance(Couverture<Integer> c);

	public Integer getPatientJourNaissance() {
		return patientJourNaissance;
	}

	public void setPatientJourNaissance(Integer patientJourNaissance) {
		this.patientJourNaissance = patientJourNaissance;
		this.patientJourNaissanceCouverture.dejaInitialise = true;
	}
	public InscriptionMedicale setPatientJourNaissance(String o) {
		if(NumberUtils.isParsable(o))
			this.patientJourNaissance = Integer.parseInt(o);
		this.patientJourNaissanceCouverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	protected InscriptionMedicale patientJourNaissanceInit() {
		if(!patientJourNaissanceCouverture.dejaInitialise) {
			_patientJourNaissance(patientJourNaissanceCouverture);
			if(patientJourNaissance == null)
				setPatientJourNaissance(patientJourNaissanceCouverture.o);
		}
		patientJourNaissanceCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public Integer solrPatientJourNaissance() {
		return patientJourNaissance;
	}

	public String strPatientJourNaissance() {
		return patientJourNaissance == null ? "" : patientJourNaissance.toString();
	}

	public String jsonPatientJourNaissance() {
		return patientJourNaissance == null ? "" : patientJourNaissance.toString();
	}

	public String nomAffichagePatientJourNaissance() {
		return null;
	}

	public String htmTooltipPatientJourNaissance() {
		return null;
	}

	public String htmPatientJourNaissance() {
		return patientJourNaissance == null ? "" : StringEscapeUtils.escapeHtml4(strPatientJourNaissance());
	}

	/////////////////
	// cliniqueNom //
	/////////////////

	/**	L'entité « cliniqueNom »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String cliniqueNom;
	@JsonIgnore
	public Couverture<String> cliniqueNomCouverture = new Couverture<String>().p(this).c(String.class).var("cliniqueNom").o(cliniqueNom);

	/**	<br/>L'entité « cliniqueNom »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:cliniqueNom">Trouver l'entité cliniqueNom dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _cliniqueNom(Couverture<String> c);

	public String getCliniqueNom() {
		return cliniqueNom;
	}

	public void setCliniqueNom(String cliniqueNom) {
		this.cliniqueNom = cliniqueNom;
		this.cliniqueNomCouverture.dejaInitialise = true;
	}
	protected InscriptionMedicale cliniqueNomInit() {
		if(!cliniqueNomCouverture.dejaInitialise) {
			_cliniqueNom(cliniqueNomCouverture);
			if(cliniqueNom == null)
				setCliniqueNom(cliniqueNomCouverture.o);
		}
		cliniqueNomCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public String solrCliniqueNom() {
		return cliniqueNom;
	}

	public String strCliniqueNom() {
		return cliniqueNom == null ? "" : cliniqueNom;
	}

	public String jsonCliniqueNom() {
		return cliniqueNom == null ? "" : cliniqueNom;
	}

	public String nomAffichageCliniqueNom() {
		return "NomAffichage.enUS: ";
	}

	public String htmTooltipCliniqueNom() {
		return null;
	}

	public String htmCliniqueNom() {
		return cliniqueNom == null ? "" : StringEscapeUtils.escapeHtml4(strCliniqueNom());
	}

	////////////////////////
	// cliniqueNomComplet //
	////////////////////////

	/**	L'entité « cliniqueNomComplet »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String cliniqueNomComplet;
	@JsonIgnore
	public Couverture<String> cliniqueNomCompletCouverture = new Couverture<String>().p(this).c(String.class).var("cliniqueNomComplet").o(cliniqueNomComplet);

	/**	<br/>L'entité « cliniqueNomComplet »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:cliniqueNomComplet">Trouver l'entité cliniqueNomComplet dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _cliniqueNomComplet(Couverture<String> c);

	public String getCliniqueNomComplet() {
		return cliniqueNomComplet;
	}

	public void setCliniqueNomComplet(String cliniqueNomComplet) {
		this.cliniqueNomComplet = cliniqueNomComplet;
		this.cliniqueNomCompletCouverture.dejaInitialise = true;
	}
	protected InscriptionMedicale cliniqueNomCompletInit() {
		if(!cliniqueNomCompletCouverture.dejaInitialise) {
			_cliniqueNomComplet(cliniqueNomCompletCouverture);
			if(cliniqueNomComplet == null)
				setCliniqueNomComplet(cliniqueNomCompletCouverture.o);
		}
		cliniqueNomCompletCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public String solrCliniqueNomComplet() {
		return cliniqueNomComplet;
	}

	public String strCliniqueNomComplet() {
		return cliniqueNomComplet == null ? "" : cliniqueNomComplet;
	}

	public String jsonCliniqueNomComplet() {
		return cliniqueNomComplet == null ? "" : cliniqueNomComplet;
	}

	public String nomAffichageCliniqueNomComplet() {
		return "NomAffichage.enUS: ";
	}

	public String htmTooltipCliniqueNomComplet() {
		return null;
	}

	public String htmCliniqueNomComplet() {
		return cliniqueNomComplet == null ? "" : StringEscapeUtils.escapeHtml4(strCliniqueNomComplet());
	}

	/////////////////////////
	// cliniqueEmplacement //
	/////////////////////////

	/**	L'entité « cliniqueEmplacement »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String cliniqueEmplacement;
	@JsonIgnore
	public Couverture<String> cliniqueEmplacementCouverture = new Couverture<String>().p(this).c(String.class).var("cliniqueEmplacement").o(cliniqueEmplacement);

	/**	<br/>L'entité « cliniqueEmplacement »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:cliniqueEmplacement">Trouver l'entité cliniqueEmplacement dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _cliniqueEmplacement(Couverture<String> c);

	public String getCliniqueEmplacement() {
		return cliniqueEmplacement;
	}

	public void setCliniqueEmplacement(String cliniqueEmplacement) {
		this.cliniqueEmplacement = cliniqueEmplacement;
		this.cliniqueEmplacementCouverture.dejaInitialise = true;
	}
	protected InscriptionMedicale cliniqueEmplacementInit() {
		if(!cliniqueEmplacementCouverture.dejaInitialise) {
			_cliniqueEmplacement(cliniqueEmplacementCouverture);
			if(cliniqueEmplacement == null)
				setCliniqueEmplacement(cliniqueEmplacementCouverture.o);
		}
		cliniqueEmplacementCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public String solrCliniqueEmplacement() {
		return cliniqueEmplacement;
	}

	public String strCliniqueEmplacement() {
		return cliniqueEmplacement == null ? "" : cliniqueEmplacement;
	}

	public String jsonCliniqueEmplacement() {
		return cliniqueEmplacement == null ? "" : cliniqueEmplacement;
	}

	public String nomAffichageCliniqueEmplacement() {
		return "l'emplacement";
	}

	public String htmTooltipCliniqueEmplacement() {
		return null;
	}

	public String htmCliniqueEmplacement() {
		return cliniqueEmplacement == null ? "" : StringEscapeUtils.escapeHtml4(strCliniqueEmplacement());
	}

	//////////////////////
	// cliniqueAddresse //
	//////////////////////

	/**	L'entité « cliniqueAddresse »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String cliniqueAddresse;
	@JsonIgnore
	public Couverture<String> cliniqueAddresseCouverture = new Couverture<String>().p(this).c(String.class).var("cliniqueAddresse").o(cliniqueAddresse);

	/**	<br/>L'entité « cliniqueAddresse »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:cliniqueAddresse">Trouver l'entité cliniqueAddresse dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _cliniqueAddresse(Couverture<String> c);

	public String getCliniqueAddresse() {
		return cliniqueAddresse;
	}

	public void setCliniqueAddresse(String cliniqueAddresse) {
		this.cliniqueAddresse = cliniqueAddresse;
		this.cliniqueAddresseCouverture.dejaInitialise = true;
	}
	protected InscriptionMedicale cliniqueAddresseInit() {
		if(!cliniqueAddresseCouverture.dejaInitialise) {
			_cliniqueAddresse(cliniqueAddresseCouverture);
			if(cliniqueAddresse == null)
				setCliniqueAddresse(cliniqueAddresseCouverture.o);
		}
		cliniqueAddresseCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public String solrCliniqueAddresse() {
		return cliniqueAddresse;
	}

	public String strCliniqueAddresse() {
		return cliniqueAddresse == null ? "" : cliniqueAddresse;
	}

	public String jsonCliniqueAddresse() {
		return cliniqueAddresse == null ? "" : cliniqueAddresse;
	}

	public String nomAffichageCliniqueAddresse() {
		return "addresse";
	}

	public String htmTooltipCliniqueAddresse() {
		return null;
	}

	public String htmCliniqueAddresse() {
		return cliniqueAddresse == null ? "" : StringEscapeUtils.escapeHtml4(strCliniqueAddresse());
	}

	public void inputCliniqueAddresse(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("placeholder", "addresse")
				.a("title", "La clé primaire des utilisateurs dans la base de données. ")
				.a("id", classeApiMethodeMethode, "_cliniqueAddresse");
				if("Page".equals(classeApiMethodeMethode) || "PATCH".equals(classeApiMethodeMethode)) {
					a("class", "setCliniqueAddresse classInscriptionMedicale inputInscriptionMedicale", pk, "CliniqueAddresse w3-input w3-border ");
					a("name", "setCliniqueAddresse");
				} else {
					a("class", "valeurCliniqueAddresse w3-input w3-border classInscriptionMedicale inputInscriptionMedicale", pk, "CliniqueAddresse w3-input w3-border ");
					a("name", "cliniqueAddresse");
				}
				if("Page".equals(classeApiMethodeMethode)) {
					a("onclick", "enleverLueur($(this)); ");
					a("onchange", "patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setCliniqueAddresse', $(this).val(), function() { ajouterLueur($('#", classeApiMethodeMethode, "_cliniqueAddresse')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_cliniqueAddresse')); }); ");
				}
				a("value", strCliniqueAddresse())
			.fg();

		} else {
			sx(htmCliniqueAddresse());
		}
	}

	public void htmCliniqueAddresse(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "InscriptionMedicaleCliniqueAddresse").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-blue-gray ").f();
							e("label").a("for", classeApiMethodeMethode, "_cliniqueAddresse").a("class", "").f().sx("addresse").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputCliniqueAddresse(classeApiMethodeMethode);
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
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_cliniqueAddresse')); $('#", classeApiMethodeMethode, "_cliniqueAddresse').val(null); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + $('#InscriptionMedicaleForm :input[name=pk]').val() }], 'setCliniqueAddresse', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_cliniqueAddresse')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_cliniqueAddresse')); }); ")
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

	/////////////////////////////
	// cliniqueNumeroTelephone //
	/////////////////////////////

	/**	L'entité « cliniqueNumeroTelephone »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String cliniqueNumeroTelephone;
	@JsonIgnore
	public Couverture<String> cliniqueNumeroTelephoneCouverture = new Couverture<String>().p(this).c(String.class).var("cliniqueNumeroTelephone").o(cliniqueNumeroTelephone);

	/**	<br/>L'entité « cliniqueNumeroTelephone »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:cliniqueNumeroTelephone">Trouver l'entité cliniqueNumeroTelephone dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _cliniqueNumeroTelephone(Couverture<String> c);

	public String getCliniqueNumeroTelephone() {
		return cliniqueNumeroTelephone;
	}

	public void setCliniqueNumeroTelephone(String cliniqueNumeroTelephone) {
		this.cliniqueNumeroTelephone = cliniqueNumeroTelephone;
		this.cliniqueNumeroTelephoneCouverture.dejaInitialise = true;
	}
	protected InscriptionMedicale cliniqueNumeroTelephoneInit() {
		if(!cliniqueNumeroTelephoneCouverture.dejaInitialise) {
			_cliniqueNumeroTelephone(cliniqueNumeroTelephoneCouverture);
			if(cliniqueNumeroTelephone == null)
				setCliniqueNumeroTelephone(cliniqueNumeroTelephoneCouverture.o);
		}
		cliniqueNumeroTelephoneCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public String solrCliniqueNumeroTelephone() {
		return cliniqueNumeroTelephone;
	}

	public String strCliniqueNumeroTelephone() {
		return cliniqueNumeroTelephone == null ? "" : cliniqueNumeroTelephone;
	}

	public String jsonCliniqueNumeroTelephone() {
		return cliniqueNumeroTelephone == null ? "" : cliniqueNumeroTelephone;
	}

	public String nomAffichageCliniqueNumeroTelephone() {
		return "numéro de téléphone";
	}

	public String htmTooltipCliniqueNumeroTelephone() {
		return null;
	}

	public String htmCliniqueNumeroTelephone() {
		return cliniqueNumeroTelephone == null ? "" : StringEscapeUtils.escapeHtml4(strCliniqueNumeroTelephone());
	}

	///////////////////////////////
	// cliniqueAdministrateurNom //
	///////////////////////////////

	/**	L'entité « cliniqueAdministrateurNom »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String cliniqueAdministrateurNom;
	@JsonIgnore
	public Couverture<String> cliniqueAdministrateurNomCouverture = new Couverture<String>().p(this).c(String.class).var("cliniqueAdministrateurNom").o(cliniqueAdministrateurNom);

	/**	<br/>L'entité « cliniqueAdministrateurNom »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:cliniqueAdministrateurNom">Trouver l'entité cliniqueAdministrateurNom dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _cliniqueAdministrateurNom(Couverture<String> c);

	public String getCliniqueAdministrateurNom() {
		return cliniqueAdministrateurNom;
	}

	public void setCliniqueAdministrateurNom(String cliniqueAdministrateurNom) {
		this.cliniqueAdministrateurNom = cliniqueAdministrateurNom;
		this.cliniqueAdministrateurNomCouverture.dejaInitialise = true;
	}
	protected InscriptionMedicale cliniqueAdministrateurNomInit() {
		if(!cliniqueAdministrateurNomCouverture.dejaInitialise) {
			_cliniqueAdministrateurNom(cliniqueAdministrateurNomCouverture);
			if(cliniqueAdministrateurNom == null)
				setCliniqueAdministrateurNom(cliniqueAdministrateurNomCouverture.o);
		}
		cliniqueAdministrateurNomCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public String solrCliniqueAdministrateurNom() {
		return cliniqueAdministrateurNom;
	}

	public String strCliniqueAdministrateurNom() {
		return cliniqueAdministrateurNom == null ? "" : cliniqueAdministrateurNom;
	}

	public String jsonCliniqueAdministrateurNom() {
		return cliniqueAdministrateurNom == null ? "" : cliniqueAdministrateurNom;
	}

	public String nomAffichageCliniqueAdministrateurNom() {
		return "administrateur de l'école";
	}

	public String htmTooltipCliniqueAdministrateurNom() {
		return null;
	}

	public String htmCliniqueAdministrateurNom() {
		return cliniqueAdministrateurNom == null ? "" : StringEscapeUtils.escapeHtml4(strCliniqueAdministrateurNom());
	}

	/////////////////////////
	// inscriptionApprouve //
	/////////////////////////

	/**	L'entité « inscriptionApprouve »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean inscriptionApprouve;
	@JsonIgnore
	public Couverture<Boolean> inscriptionApprouveCouverture = new Couverture<Boolean>().p(this).c(Boolean.class).var("inscriptionApprouve").o(inscriptionApprouve);

	/**	<br/>L'entité « inscriptionApprouve »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptionApprouve">Trouver l'entité inscriptionApprouve dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _inscriptionApprouve(Couverture<Boolean> c);

	public Boolean getInscriptionApprouve() {
		return inscriptionApprouve;
	}

	public void setInscriptionApprouve(Boolean inscriptionApprouve) {
		this.inscriptionApprouve = inscriptionApprouve;
		this.inscriptionApprouveCouverture.dejaInitialise = true;
	}
	public InscriptionMedicale setInscriptionApprouve(String o) {
		this.inscriptionApprouve = Boolean.parseBoolean(o);
		this.inscriptionApprouveCouverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	protected InscriptionMedicale inscriptionApprouveInit() {
		if(!inscriptionApprouveCouverture.dejaInitialise) {
			_inscriptionApprouve(inscriptionApprouveCouverture);
			if(inscriptionApprouve == null)
				setInscriptionApprouve(inscriptionApprouveCouverture.o);
		}
		inscriptionApprouveCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public Boolean solrInscriptionApprouve() {
		return inscriptionApprouve;
	}

	public String strInscriptionApprouve() {
		return inscriptionApprouve == null ? "" : inscriptionApprouve.toString();
	}

	public String jsonInscriptionApprouve() {
		return inscriptionApprouve == null ? "" : inscriptionApprouve.toString();
	}

	public String nomAffichageInscriptionApprouve() {
		return "approuvé";
	}

	public String htmTooltipInscriptionApprouve() {
		return null;
	}

	public String htmInscriptionApprouve() {
		return inscriptionApprouve == null ? "" : StringEscapeUtils.escapeHtml4(strInscriptionApprouve());
	}

	public void inputInscriptionApprouve(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			if("Page".equals(classeApiMethodeMethode)) {
				e("input")
					.a("type", "checkbox")
					.a("id", classeApiMethodeMethode, "_inscriptionApprouve")
					.a("value", "true");
			} else {
				e("select")
					.a("id", classeApiMethodeMethode, "_inscriptionApprouve");
			}
			if("Page".equals(classeApiMethodeMethode) || "PATCH".equals(classeApiMethodeMethode)) {
				a("class", "setInscriptionApprouve classInscriptionMedicale inputInscriptionMedicale", pk, "InscriptionApprouve w3-input w3-border ");
				a("name", "setInscriptionApprouve");
			} else {
				a("class", "valeurInscriptionApprouve classInscriptionMedicale inputInscriptionMedicale", pk, "InscriptionApprouve w3-input w3-border ");
				a("name", "inscriptionApprouve");
			}
			if("Page".equals(classeApiMethodeMethode)) {
				a("onchange", "patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setInscriptionApprouve', $(this).prop('checked'), function() { ajouterLueur($('#", classeApiMethodeMethode, "_inscriptionApprouve')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_inscriptionApprouve')); }); ");
			}
			if("Page".equals(classeApiMethodeMethode)) {
				if(getInscriptionApprouve() != null && getInscriptionApprouve())
					a("checked", "checked");
				fg();
			} else {
				f();
				e("option").a("value", "").a("selected", "selected").f().g("option");
				e("option").a("value", "true").f().sx("true").g("option");
				e("option").a("value", "false").f().sx("false").g("option");
				g("select");
			}

		} else {
			sx(htmInscriptionApprouve());
		}
	}

	public void htmInscriptionApprouve(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "InscriptionMedicaleInscriptionApprouve").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-blue-gray ").f();
							e("label").a("for", classeApiMethodeMethode, "_inscriptionApprouve").a("class", "").f().sx("approuvé").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputInscriptionApprouve(classeApiMethodeMethode);
							} g("div");
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	//////////////////////////////
	// inscriptionImmunisations //
	//////////////////////////////

	/**	L'entité « inscriptionImmunisations »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean inscriptionImmunisations;
	@JsonIgnore
	public Couverture<Boolean> inscriptionImmunisationsCouverture = new Couverture<Boolean>().p(this).c(Boolean.class).var("inscriptionImmunisations").o(inscriptionImmunisations);

	/**	<br/>L'entité « inscriptionImmunisations »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptionImmunisations">Trouver l'entité inscriptionImmunisations dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _inscriptionImmunisations(Couverture<Boolean> c);

	public Boolean getInscriptionImmunisations() {
		return inscriptionImmunisations;
	}

	public void setInscriptionImmunisations(Boolean inscriptionImmunisations) {
		this.inscriptionImmunisations = inscriptionImmunisations;
		this.inscriptionImmunisationsCouverture.dejaInitialise = true;
	}
	public InscriptionMedicale setInscriptionImmunisations(String o) {
		this.inscriptionImmunisations = Boolean.parseBoolean(o);
		this.inscriptionImmunisationsCouverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	protected InscriptionMedicale inscriptionImmunisationsInit() {
		if(!inscriptionImmunisationsCouverture.dejaInitialise) {
			_inscriptionImmunisations(inscriptionImmunisationsCouverture);
			if(inscriptionImmunisations == null)
				setInscriptionImmunisations(inscriptionImmunisationsCouverture.o);
		}
		inscriptionImmunisationsCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public Boolean solrInscriptionImmunisations() {
		return inscriptionImmunisations;
	}

	public String strInscriptionImmunisations() {
		return inscriptionImmunisations == null ? "" : inscriptionImmunisations.toString();
	}

	public String jsonInscriptionImmunisations() {
		return inscriptionImmunisations == null ? "" : inscriptionImmunisations.toString();
	}

	public String nomAffichageInscriptionImmunisations() {
		return "vacciné";
	}

	public String htmTooltipInscriptionImmunisations() {
		return null;
	}

	public String htmInscriptionImmunisations() {
		return inscriptionImmunisations == null ? "" : StringEscapeUtils.escapeHtml4(strInscriptionImmunisations());
	}

	public void inputInscriptionImmunisations(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			if("Page".equals(classeApiMethodeMethode)) {
				e("input")
					.a("type", "checkbox")
					.a("id", classeApiMethodeMethode, "_inscriptionImmunisations")
					.a("value", "true");
			} else {
				e("select")
					.a("id", classeApiMethodeMethode, "_inscriptionImmunisations");
			}
			if("Page".equals(classeApiMethodeMethode) || "PATCH".equals(classeApiMethodeMethode)) {
				a("class", "setInscriptionImmunisations classInscriptionMedicale inputInscriptionMedicale", pk, "InscriptionImmunisations w3-input w3-border ");
				a("name", "setInscriptionImmunisations");
			} else {
				a("class", "valeurInscriptionImmunisations classInscriptionMedicale inputInscriptionMedicale", pk, "InscriptionImmunisations w3-input w3-border ");
				a("name", "inscriptionImmunisations");
			}
			if("Page".equals(classeApiMethodeMethode)) {
				a("onchange", "patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setInscriptionImmunisations', $(this).prop('checked'), function() { ajouterLueur($('#", classeApiMethodeMethode, "_inscriptionImmunisations')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_inscriptionImmunisations')); }); ");
			}
			if("Page".equals(classeApiMethodeMethode)) {
				if(getInscriptionImmunisations() != null && getInscriptionImmunisations())
					a("checked", "checked");
				fg();
			} else {
				f();
				e("option").a("value", "").a("selected", "selected").f().g("option");
				e("option").a("value", "true").f().sx("true").g("option");
				e("option").a("value", "false").f().sx("false").g("option");
				g("select");
			}

		} else {
			sx(htmInscriptionImmunisations());
		}
	}

	public void htmInscriptionImmunisations(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "InscriptionMedicaleInscriptionImmunisations").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-blue-gray ").f();
							e("label").a("for", classeApiMethodeMethode, "_inscriptionImmunisations").a("class", "").f().sx("vacciné").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputInscriptionImmunisations(classeApiMethodeMethode);
							} g("div");
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	/////////////////////
	// familleAddresse //
	/////////////////////

	/**	L'entité « familleAddresse »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String familleAddresse;
	@JsonIgnore
	public Couverture<String> familleAddresseCouverture = new Couverture<String>().p(this).c(String.class).var("familleAddresse").o(familleAddresse);

	/**	<br/>L'entité « familleAddresse »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:familleAddresse">Trouver l'entité familleAddresse dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _familleAddresse(Couverture<String> c);

	public String getFamilleAddresse() {
		return familleAddresse;
	}

	public void setFamilleAddresse(String familleAddresse) {
		this.familleAddresse = familleAddresse;
		this.familleAddresseCouverture.dejaInitialise = true;
	}
	protected InscriptionMedicale familleAddresseInit() {
		if(!familleAddresseCouverture.dejaInitialise) {
			_familleAddresse(familleAddresseCouverture);
			if(familleAddresse == null)
				setFamilleAddresse(familleAddresseCouverture.o);
		}
		familleAddresseCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public String solrFamilleAddresse() {
		return familleAddresse;
	}

	public String strFamilleAddresse() {
		return familleAddresse == null ? "" : familleAddresse;
	}

	public String jsonFamilleAddresse() {
		return familleAddresse == null ? "" : familleAddresse;
	}

	public String nomAffichageFamilleAddresse() {
		return "addresse de la famille";
	}

	public String htmTooltipFamilleAddresse() {
		return null;
	}

	public String htmFamilleAddresse() {
		return familleAddresse == null ? "" : StringEscapeUtils.escapeHtml4(strFamilleAddresse());
	}

	public void inputFamilleAddresse(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("textarea")
				.a("placeholder", "addresse de la famille")
				.a("title", "La clé primaire des utilisateurs dans la base de données. ")
				.a("id", classeApiMethodeMethode, "_familleAddresse");
				if("Page".equals(classeApiMethodeMethode) || "PATCH".equals(classeApiMethodeMethode)) {
					a("class", "setFamilleAddresse classInscriptionMedicale inputInscriptionMedicale", pk, "FamilleAddresse w3-input w3-border ");
					a("name", "setFamilleAddresse");
				} else {
					a("class", "valeurFamilleAddresse w3-input w3-border classInscriptionMedicale inputInscriptionMedicale", pk, "FamilleAddresse w3-input w3-border ");
					a("name", "familleAddresse");
				}
				if("Page".equals(classeApiMethodeMethode)) {
					a("onclick", "enleverLueur($(this)); ");
					a("onchange", "patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setFamilleAddresse', $(this).val(), function() { ajouterLueur($('#", classeApiMethodeMethode, "_familleAddresse')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_familleAddresse')); }); ");
				}
			f().sx(strFamilleAddresse()).g("textarea");

		} else {
			sx(htmFamilleAddresse());
		}
	}

	public void htmFamilleAddresse(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "InscriptionMedicaleFamilleAddresse").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-blue-gray ").f();
							e("label").a("for", classeApiMethodeMethode, "_familleAddresse").a("class", "").f().sx("addresse de la famille").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputFamilleAddresse(classeApiMethodeMethode);
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
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_familleAddresse')); $('#", classeApiMethodeMethode, "_familleAddresse').val(null); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + $('#InscriptionMedicaleForm :input[name=pk]').val() }], 'setFamilleAddresse', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_familleAddresse')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_familleAddresse')); }); ")
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

	//////////////////////////////////////////
	// familleCommentVousConnaissezClinique //
	//////////////////////////////////////////

	/**	L'entité « familleCommentVousConnaissezClinique »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String familleCommentVousConnaissezClinique;
	@JsonIgnore
	public Couverture<String> familleCommentVousConnaissezCliniqueCouverture = new Couverture<String>().p(this).c(String.class).var("familleCommentVousConnaissezClinique").o(familleCommentVousConnaissezClinique);

	/**	<br/>L'entité « familleCommentVousConnaissezClinique »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:familleCommentVousConnaissezClinique">Trouver l'entité familleCommentVousConnaissezClinique dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _familleCommentVousConnaissezClinique(Couverture<String> c);

	public String getFamilleCommentVousConnaissezClinique() {
		return familleCommentVousConnaissezClinique;
	}

	public void setFamilleCommentVousConnaissezClinique(String familleCommentVousConnaissezClinique) {
		this.familleCommentVousConnaissezClinique = familleCommentVousConnaissezClinique;
		this.familleCommentVousConnaissezCliniqueCouverture.dejaInitialise = true;
	}
	protected InscriptionMedicale familleCommentVousConnaissezCliniqueInit() {
		if(!familleCommentVousConnaissezCliniqueCouverture.dejaInitialise) {
			_familleCommentVousConnaissezClinique(familleCommentVousConnaissezCliniqueCouverture);
			if(familleCommentVousConnaissezClinique == null)
				setFamilleCommentVousConnaissezClinique(familleCommentVousConnaissezCliniqueCouverture.o);
		}
		familleCommentVousConnaissezCliniqueCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public String solrFamilleCommentVousConnaissezClinique() {
		return familleCommentVousConnaissezClinique;
	}

	public String strFamilleCommentVousConnaissezClinique() {
		return familleCommentVousConnaissezClinique == null ? "" : familleCommentVousConnaissezClinique;
	}

	public String jsonFamilleCommentVousConnaissezClinique() {
		return familleCommentVousConnaissezClinique == null ? "" : familleCommentVousConnaissezClinique;
	}

	public String nomAffichageFamilleCommentVousConnaissezClinique() {
		return "comment vous connaissez l'école ? ";
	}

	public String htmTooltipFamilleCommentVousConnaissezClinique() {
		return null;
	}

	public String htmFamilleCommentVousConnaissezClinique() {
		return familleCommentVousConnaissezClinique == null ? "" : StringEscapeUtils.escapeHtml4(strFamilleCommentVousConnaissezClinique());
	}

	public void inputFamilleCommentVousConnaissezClinique(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("textarea")
				.a("placeholder", "comment vous connaissez l'école ? ")
				.a("title", "La clé primaire des utilisateurs dans la base de données. ")
				.a("id", classeApiMethodeMethode, "_familleCommentVousConnaissezClinique");
				if("Page".equals(classeApiMethodeMethode) || "PATCH".equals(classeApiMethodeMethode)) {
					a("class", "setFamilleCommentVousConnaissezClinique classInscriptionMedicale inputInscriptionMedicale", pk, "FamilleCommentVousConnaissezClinique w3-input w3-border ");
					a("name", "setFamilleCommentVousConnaissezClinique");
				} else {
					a("class", "valeurFamilleCommentVousConnaissezClinique w3-input w3-border classInscriptionMedicale inputInscriptionMedicale", pk, "FamilleCommentVousConnaissezClinique w3-input w3-border ");
					a("name", "familleCommentVousConnaissezClinique");
				}
				if("Page".equals(classeApiMethodeMethode)) {
					a("onclick", "enleverLueur($(this)); ");
					a("onchange", "patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setFamilleCommentVousConnaissezClinique', $(this).val(), function() { ajouterLueur($('#", classeApiMethodeMethode, "_familleCommentVousConnaissezClinique')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_familleCommentVousConnaissezClinique')); }); ");
				}
			f().sx(strFamilleCommentVousConnaissezClinique()).g("textarea");

		} else {
			sx(htmFamilleCommentVousConnaissezClinique());
		}
	}

	public void htmFamilleCommentVousConnaissezClinique(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "InscriptionMedicaleFamilleCommentVousConnaissezClinique").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-blue-gray ").f();
							e("label").a("for", classeApiMethodeMethode, "_familleCommentVousConnaissezClinique").a("class", "").f().sx("comment vous connaissez l'école ? ").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputFamilleCommentVousConnaissezClinique(classeApiMethodeMethode);
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
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_familleCommentVousConnaissezClinique')); $('#", classeApiMethodeMethode, "_familleCommentVousConnaissezClinique').val(null); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + $('#InscriptionMedicaleForm :input[name=pk]').val() }], 'setFamilleCommentVousConnaissezClinique', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_familleCommentVousConnaissezClinique')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_familleCommentVousConnaissezClinique')); }); ")
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

	////////////////////////////////////////
	// inscriptionConsiderationsSpeciales //
	////////////////////////////////////////

	/**	L'entité « inscriptionConsiderationsSpeciales »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String inscriptionConsiderationsSpeciales;
	@JsonIgnore
	public Couverture<String> inscriptionConsiderationsSpecialesCouverture = new Couverture<String>().p(this).c(String.class).var("inscriptionConsiderationsSpeciales").o(inscriptionConsiderationsSpeciales);

	/**	<br/>L'entité « inscriptionConsiderationsSpeciales »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptionConsiderationsSpeciales">Trouver l'entité inscriptionConsiderationsSpeciales dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _inscriptionConsiderationsSpeciales(Couverture<String> c);

	public String getInscriptionConsiderationsSpeciales() {
		return inscriptionConsiderationsSpeciales;
	}

	public void setInscriptionConsiderationsSpeciales(String inscriptionConsiderationsSpeciales) {
		this.inscriptionConsiderationsSpeciales = inscriptionConsiderationsSpeciales;
		this.inscriptionConsiderationsSpecialesCouverture.dejaInitialise = true;
	}
	protected InscriptionMedicale inscriptionConsiderationsSpecialesInit() {
		if(!inscriptionConsiderationsSpecialesCouverture.dejaInitialise) {
			_inscriptionConsiderationsSpeciales(inscriptionConsiderationsSpecialesCouverture);
			if(inscriptionConsiderationsSpeciales == null)
				setInscriptionConsiderationsSpeciales(inscriptionConsiderationsSpecialesCouverture.o);
		}
		inscriptionConsiderationsSpecialesCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public String solrInscriptionConsiderationsSpeciales() {
		return inscriptionConsiderationsSpeciales;
	}

	public String strInscriptionConsiderationsSpeciales() {
		return inscriptionConsiderationsSpeciales == null ? "" : inscriptionConsiderationsSpeciales;
	}

	public String jsonInscriptionConsiderationsSpeciales() {
		return inscriptionConsiderationsSpeciales == null ? "" : inscriptionConsiderationsSpeciales;
	}

	public String nomAffichageInscriptionConsiderationsSpeciales() {
		return "considérations spéciale";
	}

	public String htmTooltipInscriptionConsiderationsSpeciales() {
		return null;
	}

	public String htmInscriptionConsiderationsSpeciales() {
		return inscriptionConsiderationsSpeciales == null ? "" : StringEscapeUtils.escapeHtml4(strInscriptionConsiderationsSpeciales());
	}

	public void inputInscriptionConsiderationsSpeciales(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("textarea")
				.a("placeholder", "considérations spéciale")
				.a("title", "La clé primaire des utilisateurs dans la base de données. ")
				.a("id", classeApiMethodeMethode, "_inscriptionConsiderationsSpeciales");
				if("Page".equals(classeApiMethodeMethode) || "PATCH".equals(classeApiMethodeMethode)) {
					a("class", "setInscriptionConsiderationsSpeciales classInscriptionMedicale inputInscriptionMedicale", pk, "InscriptionConsiderationsSpeciales w3-input w3-border ");
					a("name", "setInscriptionConsiderationsSpeciales");
				} else {
					a("class", "valeurInscriptionConsiderationsSpeciales w3-input w3-border classInscriptionMedicale inputInscriptionMedicale", pk, "InscriptionConsiderationsSpeciales w3-input w3-border ");
					a("name", "inscriptionConsiderationsSpeciales");
				}
				if("Page".equals(classeApiMethodeMethode)) {
					a("onclick", "enleverLueur($(this)); ");
					a("onchange", "patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setInscriptionConsiderationsSpeciales', $(this).val(), function() { ajouterLueur($('#", classeApiMethodeMethode, "_inscriptionConsiderationsSpeciales')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_inscriptionConsiderationsSpeciales')); }); ");
				}
			f().sx(strInscriptionConsiderationsSpeciales()).g("textarea");

		} else {
			sx(htmInscriptionConsiderationsSpeciales());
		}
	}

	public void htmInscriptionConsiderationsSpeciales(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "InscriptionMedicaleInscriptionConsiderationsSpeciales").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-blue-gray ").f();
							e("label").a("for", classeApiMethodeMethode, "_inscriptionConsiderationsSpeciales").a("class", "").f().sx("considérations spéciale").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputInscriptionConsiderationsSpeciales(classeApiMethodeMethode);
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
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_inscriptionConsiderationsSpeciales')); $('#", classeApiMethodeMethode, "_inscriptionConsiderationsSpeciales').val(null); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + $('#InscriptionMedicaleForm :input[name=pk]').val() }], 'setInscriptionConsiderationsSpeciales', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_inscriptionConsiderationsSpeciales')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_inscriptionConsiderationsSpeciales')); }); ")
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
	// patientConditionsMedicales //
	////////////////////////////////

	/**	L'entité « patientConditionsMedicales »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String patientConditionsMedicales;
	@JsonIgnore
	public Couverture<String> patientConditionsMedicalesCouverture = new Couverture<String>().p(this).c(String.class).var("patientConditionsMedicales").o(patientConditionsMedicales);

	/**	<br/>L'entité « patientConditionsMedicales »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:patientConditionsMedicales">Trouver l'entité patientConditionsMedicales dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patientConditionsMedicales(Couverture<String> c);

	public String getPatientConditionsMedicales() {
		return patientConditionsMedicales;
	}

	public void setPatientConditionsMedicales(String patientConditionsMedicales) {
		this.patientConditionsMedicales = patientConditionsMedicales;
		this.patientConditionsMedicalesCouverture.dejaInitialise = true;
	}
	protected InscriptionMedicale patientConditionsMedicalesInit() {
		if(!patientConditionsMedicalesCouverture.dejaInitialise) {
			_patientConditionsMedicales(patientConditionsMedicalesCouverture);
			if(patientConditionsMedicales == null)
				setPatientConditionsMedicales(patientConditionsMedicalesCouverture.o);
		}
		patientConditionsMedicalesCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public String solrPatientConditionsMedicales() {
		return patientConditionsMedicales;
	}

	public String strPatientConditionsMedicales() {
		return patientConditionsMedicales == null ? "" : patientConditionsMedicales;
	}

	public String jsonPatientConditionsMedicales() {
		return patientConditionsMedicales == null ? "" : patientConditionsMedicales;
	}

	public String nomAffichagePatientConditionsMedicales() {
		return "conditions médicales";
	}

	public String htmTooltipPatientConditionsMedicales() {
		return null;
	}

	public String htmPatientConditionsMedicales() {
		return patientConditionsMedicales == null ? "" : StringEscapeUtils.escapeHtml4(strPatientConditionsMedicales());
	}

	public void inputPatientConditionsMedicales(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("textarea")
				.a("placeholder", "conditions médicales")
				.a("title", "La clé primaire des utilisateurs dans la base de données. ")
				.a("id", classeApiMethodeMethode, "_patientConditionsMedicales");
				if("Page".equals(classeApiMethodeMethode) || "PATCH".equals(classeApiMethodeMethode)) {
					a("class", "setPatientConditionsMedicales classInscriptionMedicale inputInscriptionMedicale", pk, "PatientConditionsMedicales w3-input w3-border ");
					a("name", "setPatientConditionsMedicales");
				} else {
					a("class", "valeurPatientConditionsMedicales w3-input w3-border classInscriptionMedicale inputInscriptionMedicale", pk, "PatientConditionsMedicales w3-input w3-border ");
					a("name", "patientConditionsMedicales");
				}
				if("Page".equals(classeApiMethodeMethode)) {
					a("onclick", "enleverLueur($(this)); ");
					a("onchange", "patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setPatientConditionsMedicales', $(this).val(), function() { ajouterLueur($('#", classeApiMethodeMethode, "_patientConditionsMedicales')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_patientConditionsMedicales')); }); ");
				}
			f().sx(strPatientConditionsMedicales()).g("textarea");

		} else {
			sx(htmPatientConditionsMedicales());
		}
	}

	public void htmPatientConditionsMedicales(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "InscriptionMedicalePatientConditionsMedicales").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-blue-gray ").f();
							e("label").a("for", classeApiMethodeMethode, "_patientConditionsMedicales").a("class", "").f().sx("conditions médicales").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputPatientConditionsMedicales(classeApiMethodeMethode);
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
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_patientConditionsMedicales')); $('#", classeApiMethodeMethode, "_patientConditionsMedicales').val(null); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + $('#InscriptionMedicaleForm :input[name=pk]').val() }], 'setPatientConditionsMedicales', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_patientConditionsMedicales')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_patientConditionsMedicales')); }); ")
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

	/////////////////////////////////////////////
	// patientCliniquesPrecedemmentFrequentees //
	/////////////////////////////////////////////

	/**	L'entité « patientCliniquesPrecedemmentFrequentees »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String patientCliniquesPrecedemmentFrequentees;
	@JsonIgnore
	public Couverture<String> patientCliniquesPrecedemmentFrequenteesCouverture = new Couverture<String>().p(this).c(String.class).var("patientCliniquesPrecedemmentFrequentees").o(patientCliniquesPrecedemmentFrequentees);

	/**	<br/>L'entité « patientCliniquesPrecedemmentFrequentees »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:patientCliniquesPrecedemmentFrequentees">Trouver l'entité patientCliniquesPrecedemmentFrequentees dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patientCliniquesPrecedemmentFrequentees(Couverture<String> c);

	public String getPatientCliniquesPrecedemmentFrequentees() {
		return patientCliniquesPrecedemmentFrequentees;
	}

	public void setPatientCliniquesPrecedemmentFrequentees(String patientCliniquesPrecedemmentFrequentees) {
		this.patientCliniquesPrecedemmentFrequentees = patientCliniquesPrecedemmentFrequentees;
		this.patientCliniquesPrecedemmentFrequenteesCouverture.dejaInitialise = true;
	}
	protected InscriptionMedicale patientCliniquesPrecedemmentFrequenteesInit() {
		if(!patientCliniquesPrecedemmentFrequenteesCouverture.dejaInitialise) {
			_patientCliniquesPrecedemmentFrequentees(patientCliniquesPrecedemmentFrequenteesCouverture);
			if(patientCliniquesPrecedemmentFrequentees == null)
				setPatientCliniquesPrecedemmentFrequentees(patientCliniquesPrecedemmentFrequenteesCouverture.o);
		}
		patientCliniquesPrecedemmentFrequenteesCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public String solrPatientCliniquesPrecedemmentFrequentees() {
		return patientCliniquesPrecedemmentFrequentees;
	}

	public String strPatientCliniquesPrecedemmentFrequentees() {
		return patientCliniquesPrecedemmentFrequentees == null ? "" : patientCliniquesPrecedemmentFrequentees;
	}

	public String jsonPatientCliniquesPrecedemmentFrequentees() {
		return patientCliniquesPrecedemmentFrequentees == null ? "" : patientCliniquesPrecedemmentFrequentees;
	}

	public String nomAffichagePatientCliniquesPrecedemmentFrequentees() {
		return "écoles précedemment fréqentées";
	}

	public String htmTooltipPatientCliniquesPrecedemmentFrequentees() {
		return null;
	}

	public String htmPatientCliniquesPrecedemmentFrequentees() {
		return patientCliniquesPrecedemmentFrequentees == null ? "" : StringEscapeUtils.escapeHtml4(strPatientCliniquesPrecedemmentFrequentees());
	}

	public void inputPatientCliniquesPrecedemmentFrequentees(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("textarea")
				.a("placeholder", "écoles précedemment fréqentées")
				.a("title", "La clé primaire des utilisateurs dans la base de données. ")
				.a("id", classeApiMethodeMethode, "_patientCliniquesPrecedemmentFrequentees");
				if("Page".equals(classeApiMethodeMethode) || "PATCH".equals(classeApiMethodeMethode)) {
					a("class", "setPatientCliniquesPrecedemmentFrequentees classInscriptionMedicale inputInscriptionMedicale", pk, "PatientCliniquesPrecedemmentFrequentees w3-input w3-border ");
					a("name", "setPatientCliniquesPrecedemmentFrequentees");
				} else {
					a("class", "valeurPatientCliniquesPrecedemmentFrequentees w3-input w3-border classInscriptionMedicale inputInscriptionMedicale", pk, "PatientCliniquesPrecedemmentFrequentees w3-input w3-border ");
					a("name", "patientCliniquesPrecedemmentFrequentees");
				}
				if("Page".equals(classeApiMethodeMethode)) {
					a("onclick", "enleverLueur($(this)); ");
					a("onchange", "patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setPatientCliniquesPrecedemmentFrequentees', $(this).val(), function() { ajouterLueur($('#", classeApiMethodeMethode, "_patientCliniquesPrecedemmentFrequentees')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_patientCliniquesPrecedemmentFrequentees')); }); ");
				}
			f().sx(strPatientCliniquesPrecedemmentFrequentees()).g("textarea");

		} else {
			sx(htmPatientCliniquesPrecedemmentFrequentees());
		}
	}

	public void htmPatientCliniquesPrecedemmentFrequentees(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "InscriptionMedicalePatientCliniquesPrecedemmentFrequentees").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-blue-gray ").f();
							e("label").a("for", classeApiMethodeMethode, "_patientCliniquesPrecedemmentFrequentees").a("class", "").f().sx("écoles précedemment fréqentées").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputPatientCliniquesPrecedemmentFrequentees(classeApiMethodeMethode);
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
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_patientCliniquesPrecedemmentFrequentees')); $('#", classeApiMethodeMethode, "_patientCliniquesPrecedemmentFrequentees').val(null); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + $('#InscriptionMedicaleForm :input[name=pk]').val() }], 'setPatientCliniquesPrecedemmentFrequentees', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_patientCliniquesPrecedemmentFrequentees')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_patientCliniquesPrecedemmentFrequentees')); }); ")
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
	// patientDescription //
	////////////////////////

	/**	L'entité « patientDescription »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String patientDescription;
	@JsonIgnore
	public Couverture<String> patientDescriptionCouverture = new Couverture<String>().p(this).c(String.class).var("patientDescription").o(patientDescription);

	/**	<br/>L'entité « patientDescription »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:patientDescription">Trouver l'entité patientDescription dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patientDescription(Couverture<String> c);

	public String getPatientDescription() {
		return patientDescription;
	}

	public void setPatientDescription(String patientDescription) {
		this.patientDescription = patientDescription;
		this.patientDescriptionCouverture.dejaInitialise = true;
	}
	protected InscriptionMedicale patientDescriptionInit() {
		if(!patientDescriptionCouverture.dejaInitialise) {
			_patientDescription(patientDescriptionCouverture);
			if(patientDescription == null)
				setPatientDescription(patientDescriptionCouverture.o);
		}
		patientDescriptionCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public String solrPatientDescription() {
		return patientDescription;
	}

	public String strPatientDescription() {
		return patientDescription == null ? "" : patientDescription;
	}

	public String jsonPatientDescription() {
		return patientDescription == null ? "" : patientDescription;
	}

	public String nomAffichagePatientDescription() {
		return "description";
	}

	public String htmTooltipPatientDescription() {
		return null;
	}

	public String htmPatientDescription() {
		return patientDescription == null ? "" : StringEscapeUtils.escapeHtml4(strPatientDescription());
	}

	public void inputPatientDescription(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("textarea")
				.a("placeholder", "description")
				.a("title", "La clé primaire des utilisateurs dans la base de données. ")
				.a("id", classeApiMethodeMethode, "_patientDescription");
				if("Page".equals(classeApiMethodeMethode) || "PATCH".equals(classeApiMethodeMethode)) {
					a("class", "setPatientDescription classInscriptionMedicale inputInscriptionMedicale", pk, "PatientDescription w3-input w3-border ");
					a("name", "setPatientDescription");
				} else {
					a("class", "valeurPatientDescription w3-input w3-border classInscriptionMedicale inputInscriptionMedicale", pk, "PatientDescription w3-input w3-border ");
					a("name", "patientDescription");
				}
				if("Page".equals(classeApiMethodeMethode)) {
					a("onclick", "enleverLueur($(this)); ");
					a("onchange", "patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setPatientDescription', $(this).val(), function() { ajouterLueur($('#", classeApiMethodeMethode, "_patientDescription')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_patientDescription')); }); ");
				}
			f().sx(strPatientDescription()).g("textarea");

		} else {
			sx(htmPatientDescription());
		}
	}

	public void htmPatientDescription(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "InscriptionMedicalePatientDescription").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-blue-gray ").f();
							e("label").a("for", classeApiMethodeMethode, "_patientDescription").a("class", "").f().sx("description").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputPatientDescription(classeApiMethodeMethode);
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
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_patientDescription')); $('#", classeApiMethodeMethode, "_patientDescription').val(null); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + $('#InscriptionMedicaleForm :input[name=pk]').val() }], 'setPatientDescription', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_patientDescription')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_patientDescription')); }); ")
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

	//////////////////////
	// patientObjectifs //
	//////////////////////

	/**	L'entité « patientObjectifs »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String patientObjectifs;
	@JsonIgnore
	public Couverture<String> patientObjectifsCouverture = new Couverture<String>().p(this).c(String.class).var("patientObjectifs").o(patientObjectifs);

	/**	<br/>L'entité « patientObjectifs »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:patientObjectifs">Trouver l'entité patientObjectifs dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patientObjectifs(Couverture<String> c);

	public String getPatientObjectifs() {
		return patientObjectifs;
	}

	public void setPatientObjectifs(String patientObjectifs) {
		this.patientObjectifs = patientObjectifs;
		this.patientObjectifsCouverture.dejaInitialise = true;
	}
	protected InscriptionMedicale patientObjectifsInit() {
		if(!patientObjectifsCouverture.dejaInitialise) {
			_patientObjectifs(patientObjectifsCouverture);
			if(patientObjectifs == null)
				setPatientObjectifs(patientObjectifsCouverture.o);
		}
		patientObjectifsCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public String solrPatientObjectifs() {
		return patientObjectifs;
	}

	public String strPatientObjectifs() {
		return patientObjectifs == null ? "" : patientObjectifs;
	}

	public String jsonPatientObjectifs() {
		return patientObjectifs == null ? "" : patientObjectifs;
	}

	public String nomAffichagePatientObjectifs() {
		return "objectifs";
	}

	public String htmTooltipPatientObjectifs() {
		return null;
	}

	public String htmPatientObjectifs() {
		return patientObjectifs == null ? "" : StringEscapeUtils.escapeHtml4(strPatientObjectifs());
	}

	public void inputPatientObjectifs(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("textarea")
				.a("placeholder", "objectifs")
				.a("title", "La clé primaire des utilisateurs dans la base de données. ")
				.a("id", classeApiMethodeMethode, "_patientObjectifs");
				if("Page".equals(classeApiMethodeMethode) || "PATCH".equals(classeApiMethodeMethode)) {
					a("class", "setPatientObjectifs classInscriptionMedicale inputInscriptionMedicale", pk, "PatientObjectifs w3-input w3-border ");
					a("name", "setPatientObjectifs");
				} else {
					a("class", "valeurPatientObjectifs w3-input w3-border classInscriptionMedicale inputInscriptionMedicale", pk, "PatientObjectifs w3-input w3-border ");
					a("name", "patientObjectifs");
				}
				if("Page".equals(classeApiMethodeMethode)) {
					a("onclick", "enleverLueur($(this)); ");
					a("onchange", "patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setPatientObjectifs', $(this).val(), function() { ajouterLueur($('#", classeApiMethodeMethode, "_patientObjectifs')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_patientObjectifs')); }); ");
				}
			f().sx(strPatientObjectifs()).g("textarea");

		} else {
			sx(htmPatientObjectifs());
		}
	}

	public void htmPatientObjectifs(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "InscriptionMedicalePatientObjectifs").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-blue-gray ").f();
							e("label").a("for", classeApiMethodeMethode, "_patientObjectifs").a("class", "").f().sx("objectifs").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputPatientObjectifs(classeApiMethodeMethode);
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
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_patientObjectifs')); $('#", classeApiMethodeMethode, "_patientObjectifs').val(null); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + $('#InscriptionMedicaleForm :input[name=pk]').val() }], 'setPatientObjectifs', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_patientObjectifs')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_patientObjectifs')); }); ")
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
	// customerProfileId //
	///////////////////////

	/**	L'entité « customerProfileId »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String customerProfileId;
	@JsonIgnore
	public Couverture<String> customerProfileIdCouverture = new Couverture<String>().p(this).c(String.class).var("customerProfileId").o(customerProfileId);

	/**	<br/>L'entité « customerProfileId »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:customerProfileId">Trouver l'entité customerProfileId dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _customerProfileId(Couverture<String> c);

	public String getCustomerProfileId() {
		return customerProfileId;
	}

	public void setCustomerProfileId(String customerProfileId) {
		this.customerProfileId = customerProfileId;
		this.customerProfileIdCouverture.dejaInitialise = true;
	}
	protected InscriptionMedicale customerProfileIdInit() {
		if(!customerProfileIdCouverture.dejaInitialise) {
			_customerProfileId(customerProfileIdCouverture);
			if(customerProfileId == null)
				setCustomerProfileId(customerProfileIdCouverture.o);
		}
		customerProfileIdCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public String solrCustomerProfileId() {
		return customerProfileId;
	}

	public String strCustomerProfileId() {
		return customerProfileId == null ? "" : customerProfileId;
	}

	public String jsonCustomerProfileId() {
		return customerProfileId == null ? "" : customerProfileId;
	}

	public String nomAffichageCustomerProfileId() {
		return "customer profile ID";
	}

	public String htmTooltipCustomerProfileId() {
		return null;
	}

	public String htmCustomerProfileId() {
		return customerProfileId == null ? "" : StringEscapeUtils.escapeHtml4(strCustomerProfileId());
	}

	public void inputCustomerProfileId(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("placeholder", "customer profile ID")
				.a("title", "La clé primaire des utilisateurs dans la base de données. ")
				.a("id", classeApiMethodeMethode, "_customerProfileId");
				if("Page".equals(classeApiMethodeMethode) || "PATCH".equals(classeApiMethodeMethode)) {
					a("class", "setCustomerProfileId classInscriptionMedicale inputInscriptionMedicale", pk, "CustomerProfileId w3-input w3-border ");
					a("name", "setCustomerProfileId");
				} else {
					a("class", "valeurCustomerProfileId w3-input w3-border classInscriptionMedicale inputInscriptionMedicale", pk, "CustomerProfileId w3-input w3-border ");
					a("name", "customerProfileId");
				}
				if("Page".equals(classeApiMethodeMethode)) {
					a("onclick", "enleverLueur($(this)); ");
					a("onchange", "patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setCustomerProfileId', $(this).val(), function() { ajouterLueur($('#", classeApiMethodeMethode, "_customerProfileId')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_customerProfileId')); }); ");
				}
				a("value", strCustomerProfileId())
			.fg();

		} else {
			sx(htmCustomerProfileId());
		}
	}

	public void htmCustomerProfileId(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "InscriptionMedicaleCustomerProfileId").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-blue-gray ").f();
							e("label").a("for", classeApiMethodeMethode, "_customerProfileId").a("class", "").f().sx("customer profile ID").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputCustomerProfileId(classeApiMethodeMethode);
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
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_customerProfileId')); $('#", classeApiMethodeMethode, "_customerProfileId').val(null); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + $('#InscriptionMedicaleForm :input[name=pk]').val() }], 'setCustomerProfileId', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_customerProfileId')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_customerProfileId')); }); ")
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
	// creeDAnnee //
	////////////////

	/**	L'entité « creeDAnnee »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer creeDAnnee;
	@JsonIgnore
	public Couverture<Integer> creeDAnneeCouverture = new Couverture<Integer>().p(this).c(Integer.class).var("creeDAnnee").o(creeDAnnee);

	/**	<br/>L'entité « creeDAnnee »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:creeDAnnee">Trouver l'entité creeDAnnee dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _creeDAnnee(Couverture<Integer> c);

	public Integer getCreeDAnnee() {
		return creeDAnnee;
	}

	public void setCreeDAnnee(Integer creeDAnnee) {
		this.creeDAnnee = creeDAnnee;
		this.creeDAnneeCouverture.dejaInitialise = true;
	}
	public InscriptionMedicale setCreeDAnnee(String o) {
		if(NumberUtils.isParsable(o))
			this.creeDAnnee = Integer.parseInt(o);
		this.creeDAnneeCouverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	protected InscriptionMedicale creeDAnneeInit() {
		if(!creeDAnneeCouverture.dejaInitialise) {
			_creeDAnnee(creeDAnneeCouverture);
			if(creeDAnnee == null)
				setCreeDAnnee(creeDAnneeCouverture.o);
		}
		creeDAnneeCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public Integer solrCreeDAnnee() {
		return creeDAnnee;
	}

	public String strCreeDAnnee() {
		return creeDAnnee == null ? "" : creeDAnnee.toString();
	}

	public String jsonCreeDAnnee() {
		return creeDAnnee == null ? "" : creeDAnnee.toString();
	}

	public String nomAffichageCreeDAnnee() {
		return "crée l'année";
	}

	public String htmTooltipCreeDAnnee() {
		return null;
	}

	public String htmCreeDAnnee() {
		return creeDAnnee == null ? "" : StringEscapeUtils.escapeHtml4(strCreeDAnnee());
	}

	///////////////////////
	// creeJourDeSemaine //
	///////////////////////

	/**	L'entité « creeJourDeSemaine »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String creeJourDeSemaine;
	@JsonIgnore
	public Couverture<String> creeJourDeSemaineCouverture = new Couverture<String>().p(this).c(String.class).var("creeJourDeSemaine").o(creeJourDeSemaine);

	/**	<br/>L'entité « creeJourDeSemaine »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:creeJourDeSemaine">Trouver l'entité creeJourDeSemaine dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _creeJourDeSemaine(Couverture<String> c);

	public String getCreeJourDeSemaine() {
		return creeJourDeSemaine;
	}

	public void setCreeJourDeSemaine(String creeJourDeSemaine) {
		this.creeJourDeSemaine = creeJourDeSemaine;
		this.creeJourDeSemaineCouverture.dejaInitialise = true;
	}
	protected InscriptionMedicale creeJourDeSemaineInit() {
		if(!creeJourDeSemaineCouverture.dejaInitialise) {
			_creeJourDeSemaine(creeJourDeSemaineCouverture);
			if(creeJourDeSemaine == null)
				setCreeJourDeSemaine(creeJourDeSemaineCouverture.o);
		}
		creeJourDeSemaineCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public String solrCreeJourDeSemaine() {
		return creeJourDeSemaine;
	}

	public String strCreeJourDeSemaine() {
		return creeJourDeSemaine == null ? "" : creeJourDeSemaine;
	}

	public String jsonCreeJourDeSemaine() {
		return creeJourDeSemaine == null ? "" : creeJourDeSemaine;
	}

	public String nomAffichageCreeJourDeSemaine() {
		return "crée jour de la semaine";
	}

	public String htmTooltipCreeJourDeSemaine() {
		return null;
	}

	public String htmCreeJourDeSemaine() {
		return creeJourDeSemaine == null ? "" : StringEscapeUtils.escapeHtml4(strCreeJourDeSemaine());
	}

	////////////////////
	// creeMoisDAnnee //
	////////////////////

	/**	L'entité « creeMoisDAnnee »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String creeMoisDAnnee;
	@JsonIgnore
	public Couverture<String> creeMoisDAnneeCouverture = new Couverture<String>().p(this).c(String.class).var("creeMoisDAnnee").o(creeMoisDAnnee);

	/**	<br/>L'entité « creeMoisDAnnee »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:creeMoisDAnnee">Trouver l'entité creeMoisDAnnee dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _creeMoisDAnnee(Couverture<String> c);

	public String getCreeMoisDAnnee() {
		return creeMoisDAnnee;
	}

	public void setCreeMoisDAnnee(String creeMoisDAnnee) {
		this.creeMoisDAnnee = creeMoisDAnnee;
		this.creeMoisDAnneeCouverture.dejaInitialise = true;
	}
	protected InscriptionMedicale creeMoisDAnneeInit() {
		if(!creeMoisDAnneeCouverture.dejaInitialise) {
			_creeMoisDAnnee(creeMoisDAnneeCouverture);
			if(creeMoisDAnnee == null)
				setCreeMoisDAnnee(creeMoisDAnneeCouverture.o);
		}
		creeMoisDAnneeCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public String solrCreeMoisDAnnee() {
		return creeMoisDAnnee;
	}

	public String strCreeMoisDAnnee() {
		return creeMoisDAnnee == null ? "" : creeMoisDAnnee;
	}

	public String jsonCreeMoisDAnnee() {
		return creeMoisDAnnee == null ? "" : creeMoisDAnnee;
	}

	public String nomAffichageCreeMoisDAnnee() {
		return "crée mois de l'année";
	}

	public String htmTooltipCreeMoisDAnnee() {
		return null;
	}

	public String htmCreeMoisDAnnee() {
		return creeMoisDAnnee == null ? "" : StringEscapeUtils.escapeHtml4(strCreeMoisDAnnee());
	}

	/////////////////////
	// creeHeureDuJour //
	/////////////////////

	/**	L'entité « creeHeureDuJour »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String creeHeureDuJour;
	@JsonIgnore
	public Couverture<String> creeHeureDuJourCouverture = new Couverture<String>().p(this).c(String.class).var("creeHeureDuJour").o(creeHeureDuJour);

	/**	<br/>L'entité « creeHeureDuJour »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:creeHeureDuJour">Trouver l'entité creeHeureDuJour dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _creeHeureDuJour(Couverture<String> c);

	public String getCreeHeureDuJour() {
		return creeHeureDuJour;
	}

	public void setCreeHeureDuJour(String creeHeureDuJour) {
		this.creeHeureDuJour = creeHeureDuJour;
		this.creeHeureDuJourCouverture.dejaInitialise = true;
	}
	protected InscriptionMedicale creeHeureDuJourInit() {
		if(!creeHeureDuJourCouverture.dejaInitialise) {
			_creeHeureDuJour(creeHeureDuJourCouverture);
			if(creeHeureDuJour == null)
				setCreeHeureDuJour(creeHeureDuJourCouverture.o);
		}
		creeHeureDuJourCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public String solrCreeHeureDuJour() {
		return creeHeureDuJour;
	}

	public String strCreeHeureDuJour() {
		return creeHeureDuJour == null ? "" : creeHeureDuJour;
	}

	public String jsonCreeHeureDuJour() {
		return creeHeureDuJour == null ? "" : creeHeureDuJour;
	}

	public String nomAffichageCreeHeureDuJour() {
		return "heure du jour";
	}

	public String htmTooltipCreeHeureDuJour() {
		return null;
	}

	public String htmCreeHeureDuJour() {
		return creeHeureDuJour == null ? "" : StringEscapeUtils.escapeHtml4(strCreeHeureDuJour());
	}

	///////////////////////////
	// inscriptionSignature1 //
	///////////////////////////

	/**	L'entité « inscriptionSignature1 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String inscriptionSignature1;
	@JsonIgnore
	public Couverture<String> inscriptionSignature1Couverture = new Couverture<String>().p(this).c(String.class).var("inscriptionSignature1").o(inscriptionSignature1);

	/**	<br/>L'entité « inscriptionSignature1 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptionSignature1">Trouver l'entité inscriptionSignature1 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _inscriptionSignature1(Couverture<String> c);

	public String getInscriptionSignature1() {
		return inscriptionSignature1;
	}

	public void setInscriptionSignature1(String inscriptionSignature1) {
		this.inscriptionSignature1 = inscriptionSignature1;
		this.inscriptionSignature1Couverture.dejaInitialise = true;
	}
	protected InscriptionMedicale inscriptionSignature1Init() {
		if(!inscriptionSignature1Couverture.dejaInitialise) {
			_inscriptionSignature1(inscriptionSignature1Couverture);
			if(inscriptionSignature1 == null)
				setInscriptionSignature1(inscriptionSignature1Couverture.o);
		}
		inscriptionSignature1Couverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public String solrInscriptionSignature1() {
		return inscriptionSignature1;
	}

	public String strInscriptionSignature1() {
		return inscriptionSignature1 == null ? "" : inscriptionSignature1;
	}

	public String jsonInscriptionSignature1() {
		return inscriptionSignature1 == null ? "" : inscriptionSignature1;
	}

	public String nomAffichageInscriptionSignature1() {
		return null;
	}

	public String htmTooltipInscriptionSignature1() {
		return null;
	}

	public String htmInscriptionSignature1() {
		return inscriptionSignature1 == null ? "" : StringEscapeUtils.escapeHtml4(strInscriptionSignature1());
	}

	public void inputInscriptionSignature1(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("div").a("class", "signatureDiv1InscriptionMedicale_inscriptionSignature1 signatureInputInscriptionMedicale", pk, "InscriptionSignature1").a("id", "signatureDiv1InscriptionMedicale", pk, "inscriptionSignature1").f();
				e("div").a("id", "signatureInputInscriptionMedicale", pk, "inscriptionSignature1");
					a("style", "display: ", StringUtils.isBlank(inscriptionSignature1) ? "block" : "none", "; ");
				f().g("div");
				e("img").a("id", "signatureImgInscriptionMedicale", pk, "inscriptionSignature1");
					a("class", "signatureImgInscriptionMedicale", pk, "InscriptionSignature1 ");
					a("src", StringUtils.isBlank(inscriptionSignature1) ? "data:image/png;base64," : inscriptionSignature1).a("alt", "");
					a("style", "padding: 10px; display: ", StringUtils.isBlank(inscriptionSignature1) ? "none" : "block", "; ");
				fg();
			g("div");
			e("div").a("id", "signatureDiv2InscriptionMedicale", pk, "inscriptionSignature1").f();
				e("button").a("id", "signatureButtonViderInscriptionMedicale", pk, "inscriptionSignature1");
					a("class", "w3-btn w3-round w3-border w3-border-black w3-section w3-ripple w3-padding w3-margin ");
					s(" onclick=", "\"");
						s("$('#signatureInputInscriptionMedicale", pk, "inscriptionSignature1').show(); ");
						s("$('#signatureImgInscriptionMedicale", pk, "inscriptionSignature1').hide(); ");
						s("enleverLueur($('#signatureInputInscriptionMedicale", pk, "inscriptionSignature1')); ");
						s("patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setInscriptionSignature1', null); ");
						s("if($('#signatureInputInscriptionMedicale", pk, "inscriptionSignature1')) { ");
						s("$('#signatureInputInscriptionMedicale", pk, "inscriptionSignature1').jSignature('reset'); ");
						s(" } else { ");
						s("$('#signatureInputInscriptionMedicale", pk, "inscriptionSignature1').jSignature({'height':200}); ");
						s(" } ");
					s("\"");
					f().sx("Vider");
				g("button");
			g("div");
		} else {
			sx(htmInscriptionSignature1());
		}
	}

	public void htmInscriptionSignature1(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "InscriptionMedicaleInscriptionSignature1").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputInscriptionSignature1(classeApiMethodeMethode);
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
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_inscriptionSignature1')); $('#", classeApiMethodeMethode, "_inscriptionSignature1').val(null); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + $('#InscriptionMedicaleForm :input[name=pk]').val() }], 'setInscriptionSignature1', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_inscriptionSignature1')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_inscriptionSignature1')); }); ")
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
	// inscriptionSignature2 //
	///////////////////////////

	/**	L'entité « inscriptionSignature2 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String inscriptionSignature2;
	@JsonIgnore
	public Couverture<String> inscriptionSignature2Couverture = new Couverture<String>().p(this).c(String.class).var("inscriptionSignature2").o(inscriptionSignature2);

	/**	<br/>L'entité « inscriptionSignature2 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptionSignature2">Trouver l'entité inscriptionSignature2 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _inscriptionSignature2(Couverture<String> c);

	public String getInscriptionSignature2() {
		return inscriptionSignature2;
	}

	public void setInscriptionSignature2(String inscriptionSignature2) {
		this.inscriptionSignature2 = inscriptionSignature2;
		this.inscriptionSignature2Couverture.dejaInitialise = true;
	}
	protected InscriptionMedicale inscriptionSignature2Init() {
		if(!inscriptionSignature2Couverture.dejaInitialise) {
			_inscriptionSignature2(inscriptionSignature2Couverture);
			if(inscriptionSignature2 == null)
				setInscriptionSignature2(inscriptionSignature2Couverture.o);
		}
		inscriptionSignature2Couverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public String solrInscriptionSignature2() {
		return inscriptionSignature2;
	}

	public String strInscriptionSignature2() {
		return inscriptionSignature2 == null ? "" : inscriptionSignature2;
	}

	public String jsonInscriptionSignature2() {
		return inscriptionSignature2 == null ? "" : inscriptionSignature2;
	}

	public String nomAffichageInscriptionSignature2() {
		return null;
	}

	public String htmTooltipInscriptionSignature2() {
		return null;
	}

	public String htmInscriptionSignature2() {
		return inscriptionSignature2 == null ? "" : StringEscapeUtils.escapeHtml4(strInscriptionSignature2());
	}

	public void inputInscriptionSignature2(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("div").a("class", "signatureDiv1InscriptionMedicale_inscriptionSignature2 signatureInputInscriptionMedicale", pk, "InscriptionSignature2").a("id", "signatureDiv1InscriptionMedicale", pk, "inscriptionSignature2").f();
				e("div").a("id", "signatureInputInscriptionMedicale", pk, "inscriptionSignature2");
					a("style", "display: ", StringUtils.isBlank(inscriptionSignature2) ? "block" : "none", "; ");
				f().g("div");
				e("img").a("id", "signatureImgInscriptionMedicale", pk, "inscriptionSignature2");
					a("class", "signatureImgInscriptionMedicale", pk, "InscriptionSignature2 ");
					a("src", StringUtils.isBlank(inscriptionSignature2) ? "data:image/png;base64," : inscriptionSignature2).a("alt", "");
					a("style", "padding: 10px; display: ", StringUtils.isBlank(inscriptionSignature2) ? "none" : "block", "; ");
				fg();
			g("div");
			e("div").a("id", "signatureDiv2InscriptionMedicale", pk, "inscriptionSignature2").f();
				e("button").a("id", "signatureButtonViderInscriptionMedicale", pk, "inscriptionSignature2");
					a("class", "w3-btn w3-round w3-border w3-border-black w3-section w3-ripple w3-padding w3-margin ");
					s(" onclick=", "\"");
						s("$('#signatureInputInscriptionMedicale", pk, "inscriptionSignature2').show(); ");
						s("$('#signatureImgInscriptionMedicale", pk, "inscriptionSignature2').hide(); ");
						s("enleverLueur($('#signatureInputInscriptionMedicale", pk, "inscriptionSignature2')); ");
						s("patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setInscriptionSignature2', null); ");
						s("if($('#signatureInputInscriptionMedicale", pk, "inscriptionSignature2')) { ");
						s("$('#signatureInputInscriptionMedicale", pk, "inscriptionSignature2').jSignature('reset'); ");
						s(" } else { ");
						s("$('#signatureInputInscriptionMedicale", pk, "inscriptionSignature2').jSignature({'height':200}); ");
						s(" } ");
					s("\"");
					f().sx("Vider");
				g("button");
			g("div");
		} else {
			sx(htmInscriptionSignature2());
		}
	}

	public void htmInscriptionSignature2(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "InscriptionMedicaleInscriptionSignature2").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputInscriptionSignature2(classeApiMethodeMethode);
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
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_inscriptionSignature2')); $('#", classeApiMethodeMethode, "_inscriptionSignature2').val(null); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + $('#InscriptionMedicaleForm :input[name=pk]').val() }], 'setInscriptionSignature2', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_inscriptionSignature2')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_inscriptionSignature2')); }); ")
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
	// inscriptionSignature3 //
	///////////////////////////

	/**	L'entité « inscriptionSignature3 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String inscriptionSignature3;
	@JsonIgnore
	public Couverture<String> inscriptionSignature3Couverture = new Couverture<String>().p(this).c(String.class).var("inscriptionSignature3").o(inscriptionSignature3);

	/**	<br/>L'entité « inscriptionSignature3 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptionSignature3">Trouver l'entité inscriptionSignature3 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _inscriptionSignature3(Couverture<String> c);

	public String getInscriptionSignature3() {
		return inscriptionSignature3;
	}

	public void setInscriptionSignature3(String inscriptionSignature3) {
		this.inscriptionSignature3 = inscriptionSignature3;
		this.inscriptionSignature3Couverture.dejaInitialise = true;
	}
	protected InscriptionMedicale inscriptionSignature3Init() {
		if(!inscriptionSignature3Couverture.dejaInitialise) {
			_inscriptionSignature3(inscriptionSignature3Couverture);
			if(inscriptionSignature3 == null)
				setInscriptionSignature3(inscriptionSignature3Couverture.o);
		}
		inscriptionSignature3Couverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public String solrInscriptionSignature3() {
		return inscriptionSignature3;
	}

	public String strInscriptionSignature3() {
		return inscriptionSignature3 == null ? "" : inscriptionSignature3;
	}

	public String jsonInscriptionSignature3() {
		return inscriptionSignature3 == null ? "" : inscriptionSignature3;
	}

	public String nomAffichageInscriptionSignature3() {
		return null;
	}

	public String htmTooltipInscriptionSignature3() {
		return null;
	}

	public String htmInscriptionSignature3() {
		return inscriptionSignature3 == null ? "" : StringEscapeUtils.escapeHtml4(strInscriptionSignature3());
	}

	public void inputInscriptionSignature3(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("div").a("class", "signatureDiv1InscriptionMedicale_inscriptionSignature3 signatureInputInscriptionMedicale", pk, "InscriptionSignature3").a("id", "signatureDiv1InscriptionMedicale", pk, "inscriptionSignature3").f();
				e("div").a("id", "signatureInputInscriptionMedicale", pk, "inscriptionSignature3");
					a("style", "display: ", StringUtils.isBlank(inscriptionSignature3) ? "block" : "none", "; ");
				f().g("div");
				e("img").a("id", "signatureImgInscriptionMedicale", pk, "inscriptionSignature3");
					a("class", "signatureImgInscriptionMedicale", pk, "InscriptionSignature3 ");
					a("src", StringUtils.isBlank(inscriptionSignature3) ? "data:image/png;base64," : inscriptionSignature3).a("alt", "");
					a("style", "padding: 10px; display: ", StringUtils.isBlank(inscriptionSignature3) ? "none" : "block", "; ");
				fg();
			g("div");
			e("div").a("id", "signatureDiv2InscriptionMedicale", pk, "inscriptionSignature3").f();
				e("button").a("id", "signatureButtonViderInscriptionMedicale", pk, "inscriptionSignature3");
					a("class", "w3-btn w3-round w3-border w3-border-black w3-section w3-ripple w3-padding w3-margin ");
					s(" onclick=", "\"");
						s("$('#signatureInputInscriptionMedicale", pk, "inscriptionSignature3').show(); ");
						s("$('#signatureImgInscriptionMedicale", pk, "inscriptionSignature3').hide(); ");
						s("enleverLueur($('#signatureInputInscriptionMedicale", pk, "inscriptionSignature3')); ");
						s("patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setInscriptionSignature3', null); ");
						s("if($('#signatureInputInscriptionMedicale", pk, "inscriptionSignature3')) { ");
						s("$('#signatureInputInscriptionMedicale", pk, "inscriptionSignature3').jSignature('reset'); ");
						s(" } else { ");
						s("$('#signatureInputInscriptionMedicale", pk, "inscriptionSignature3').jSignature({'height':200}); ");
						s(" } ");
					s("\"");
					f().sx("Vider");
				g("button");
			g("div");
		} else {
			sx(htmInscriptionSignature3());
		}
	}

	public void htmInscriptionSignature3(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "InscriptionMedicaleInscriptionSignature3").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputInscriptionSignature3(classeApiMethodeMethode);
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
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_inscriptionSignature3')); $('#", classeApiMethodeMethode, "_inscriptionSignature3').val(null); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + $('#InscriptionMedicaleForm :input[name=pk]').val() }], 'setInscriptionSignature3', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_inscriptionSignature3')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_inscriptionSignature3')); }); ")
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
	// inscriptionSignature4 //
	///////////////////////////

	/**	L'entité « inscriptionSignature4 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String inscriptionSignature4;
	@JsonIgnore
	public Couverture<String> inscriptionSignature4Couverture = new Couverture<String>().p(this).c(String.class).var("inscriptionSignature4").o(inscriptionSignature4);

	/**	<br/>L'entité « inscriptionSignature4 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptionSignature4">Trouver l'entité inscriptionSignature4 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _inscriptionSignature4(Couverture<String> c);

	public String getInscriptionSignature4() {
		return inscriptionSignature4;
	}

	public void setInscriptionSignature4(String inscriptionSignature4) {
		this.inscriptionSignature4 = inscriptionSignature4;
		this.inscriptionSignature4Couverture.dejaInitialise = true;
	}
	protected InscriptionMedicale inscriptionSignature4Init() {
		if(!inscriptionSignature4Couverture.dejaInitialise) {
			_inscriptionSignature4(inscriptionSignature4Couverture);
			if(inscriptionSignature4 == null)
				setInscriptionSignature4(inscriptionSignature4Couverture.o);
		}
		inscriptionSignature4Couverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public String solrInscriptionSignature4() {
		return inscriptionSignature4;
	}

	public String strInscriptionSignature4() {
		return inscriptionSignature4 == null ? "" : inscriptionSignature4;
	}

	public String jsonInscriptionSignature4() {
		return inscriptionSignature4 == null ? "" : inscriptionSignature4;
	}

	public String nomAffichageInscriptionSignature4() {
		return null;
	}

	public String htmTooltipInscriptionSignature4() {
		return null;
	}

	public String htmInscriptionSignature4() {
		return inscriptionSignature4 == null ? "" : StringEscapeUtils.escapeHtml4(strInscriptionSignature4());
	}

	public void inputInscriptionSignature4(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("div").a("class", "signatureDiv1InscriptionMedicale_inscriptionSignature4 signatureInputInscriptionMedicale", pk, "InscriptionSignature4").a("id", "signatureDiv1InscriptionMedicale", pk, "inscriptionSignature4").f();
				e("div").a("id", "signatureInputInscriptionMedicale", pk, "inscriptionSignature4");
					a("style", "display: ", StringUtils.isBlank(inscriptionSignature4) ? "block" : "none", "; ");
				f().g("div");
				e("img").a("id", "signatureImgInscriptionMedicale", pk, "inscriptionSignature4");
					a("class", "signatureImgInscriptionMedicale", pk, "InscriptionSignature4 ");
					a("src", StringUtils.isBlank(inscriptionSignature4) ? "data:image/png;base64," : inscriptionSignature4).a("alt", "");
					a("style", "padding: 10px; display: ", StringUtils.isBlank(inscriptionSignature4) ? "none" : "block", "; ");
				fg();
			g("div");
			e("div").a("id", "signatureDiv2InscriptionMedicale", pk, "inscriptionSignature4").f();
				e("button").a("id", "signatureButtonViderInscriptionMedicale", pk, "inscriptionSignature4");
					a("class", "w3-btn w3-round w3-border w3-border-black w3-section w3-ripple w3-padding w3-margin ");
					s(" onclick=", "\"");
						s("$('#signatureInputInscriptionMedicale", pk, "inscriptionSignature4').show(); ");
						s("$('#signatureImgInscriptionMedicale", pk, "inscriptionSignature4').hide(); ");
						s("enleverLueur($('#signatureInputInscriptionMedicale", pk, "inscriptionSignature4')); ");
						s("patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setInscriptionSignature4', null); ");
						s("if($('#signatureInputInscriptionMedicale", pk, "inscriptionSignature4')) { ");
						s("$('#signatureInputInscriptionMedicale", pk, "inscriptionSignature4').jSignature('reset'); ");
						s(" } else { ");
						s("$('#signatureInputInscriptionMedicale", pk, "inscriptionSignature4').jSignature({'height':200}); ");
						s(" } ");
					s("\"");
					f().sx("Vider");
				g("button");
			g("div");
		} else {
			sx(htmInscriptionSignature4());
		}
	}

	public void htmInscriptionSignature4(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "InscriptionMedicaleInscriptionSignature4").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputInscriptionSignature4(classeApiMethodeMethode);
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
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_inscriptionSignature4')); $('#", classeApiMethodeMethode, "_inscriptionSignature4').val(null); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + $('#InscriptionMedicaleForm :input[name=pk]').val() }], 'setInscriptionSignature4', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_inscriptionSignature4')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_inscriptionSignature4')); }); ")
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
	// inscriptionSignature5 //
	///////////////////////////

	/**	L'entité « inscriptionSignature5 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String inscriptionSignature5;
	@JsonIgnore
	public Couverture<String> inscriptionSignature5Couverture = new Couverture<String>().p(this).c(String.class).var("inscriptionSignature5").o(inscriptionSignature5);

	/**	<br/>L'entité « inscriptionSignature5 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptionSignature5">Trouver l'entité inscriptionSignature5 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _inscriptionSignature5(Couverture<String> c);

	public String getInscriptionSignature5() {
		return inscriptionSignature5;
	}

	public void setInscriptionSignature5(String inscriptionSignature5) {
		this.inscriptionSignature5 = inscriptionSignature5;
		this.inscriptionSignature5Couverture.dejaInitialise = true;
	}
	protected InscriptionMedicale inscriptionSignature5Init() {
		if(!inscriptionSignature5Couverture.dejaInitialise) {
			_inscriptionSignature5(inscriptionSignature5Couverture);
			if(inscriptionSignature5 == null)
				setInscriptionSignature5(inscriptionSignature5Couverture.o);
		}
		inscriptionSignature5Couverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public String solrInscriptionSignature5() {
		return inscriptionSignature5;
	}

	public String strInscriptionSignature5() {
		return inscriptionSignature5 == null ? "" : inscriptionSignature5;
	}

	public String jsonInscriptionSignature5() {
		return inscriptionSignature5 == null ? "" : inscriptionSignature5;
	}

	public String nomAffichageInscriptionSignature5() {
		return null;
	}

	public String htmTooltipInscriptionSignature5() {
		return null;
	}

	public String htmInscriptionSignature5() {
		return inscriptionSignature5 == null ? "" : StringEscapeUtils.escapeHtml4(strInscriptionSignature5());
	}

	public void inputInscriptionSignature5(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("div").a("class", "signatureDiv1InscriptionMedicale_inscriptionSignature5 signatureInputInscriptionMedicale", pk, "InscriptionSignature5").a("id", "signatureDiv1InscriptionMedicale", pk, "inscriptionSignature5").f();
				e("div").a("id", "signatureInputInscriptionMedicale", pk, "inscriptionSignature5");
					a("style", "display: ", StringUtils.isBlank(inscriptionSignature5) ? "block" : "none", "; ");
				f().g("div");
				e("img").a("id", "signatureImgInscriptionMedicale", pk, "inscriptionSignature5");
					a("class", "signatureImgInscriptionMedicale", pk, "InscriptionSignature5 ");
					a("src", StringUtils.isBlank(inscriptionSignature5) ? "data:image/png;base64," : inscriptionSignature5).a("alt", "");
					a("style", "padding: 10px; display: ", StringUtils.isBlank(inscriptionSignature5) ? "none" : "block", "; ");
				fg();
			g("div");
			e("div").a("id", "signatureDiv2InscriptionMedicale", pk, "inscriptionSignature5").f();
				e("button").a("id", "signatureButtonViderInscriptionMedicale", pk, "inscriptionSignature5");
					a("class", "w3-btn w3-round w3-border w3-border-black w3-section w3-ripple w3-padding w3-margin ");
					s(" onclick=", "\"");
						s("$('#signatureInputInscriptionMedicale", pk, "inscriptionSignature5').show(); ");
						s("$('#signatureImgInscriptionMedicale", pk, "inscriptionSignature5').hide(); ");
						s("enleverLueur($('#signatureInputInscriptionMedicale", pk, "inscriptionSignature5')); ");
						s("patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setInscriptionSignature5', null); ");
						s("if($('#signatureInputInscriptionMedicale", pk, "inscriptionSignature5')) { ");
						s("$('#signatureInputInscriptionMedicale", pk, "inscriptionSignature5').jSignature('reset'); ");
						s(" } else { ");
						s("$('#signatureInputInscriptionMedicale", pk, "inscriptionSignature5').jSignature({'height':200}); ");
						s(" } ");
					s("\"");
					f().sx("Vider");
				g("button");
			g("div");
		} else {
			sx(htmInscriptionSignature5());
		}
	}

	public void htmInscriptionSignature5(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "InscriptionMedicaleInscriptionSignature5").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputInscriptionSignature5(classeApiMethodeMethode);
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
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_inscriptionSignature5')); $('#", classeApiMethodeMethode, "_inscriptionSignature5').val(null); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + $('#InscriptionMedicaleForm :input[name=pk]').val() }], 'setInscriptionSignature5', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_inscriptionSignature5')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_inscriptionSignature5')); }); ")
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
	// inscriptionSignature6 //
	///////////////////////////

	/**	L'entité « inscriptionSignature6 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String inscriptionSignature6;
	@JsonIgnore
	public Couverture<String> inscriptionSignature6Couverture = new Couverture<String>().p(this).c(String.class).var("inscriptionSignature6").o(inscriptionSignature6);

	/**	<br/>L'entité « inscriptionSignature6 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptionSignature6">Trouver l'entité inscriptionSignature6 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _inscriptionSignature6(Couverture<String> c);

	public String getInscriptionSignature6() {
		return inscriptionSignature6;
	}

	public void setInscriptionSignature6(String inscriptionSignature6) {
		this.inscriptionSignature6 = inscriptionSignature6;
		this.inscriptionSignature6Couverture.dejaInitialise = true;
	}
	protected InscriptionMedicale inscriptionSignature6Init() {
		if(!inscriptionSignature6Couverture.dejaInitialise) {
			_inscriptionSignature6(inscriptionSignature6Couverture);
			if(inscriptionSignature6 == null)
				setInscriptionSignature6(inscriptionSignature6Couverture.o);
		}
		inscriptionSignature6Couverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public String solrInscriptionSignature6() {
		return inscriptionSignature6;
	}

	public String strInscriptionSignature6() {
		return inscriptionSignature6 == null ? "" : inscriptionSignature6;
	}

	public String jsonInscriptionSignature6() {
		return inscriptionSignature6 == null ? "" : inscriptionSignature6;
	}

	public String nomAffichageInscriptionSignature6() {
		return null;
	}

	public String htmTooltipInscriptionSignature6() {
		return null;
	}

	public String htmInscriptionSignature6() {
		return inscriptionSignature6 == null ? "" : StringEscapeUtils.escapeHtml4(strInscriptionSignature6());
	}

	public void inputInscriptionSignature6(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("div").a("class", "signatureDiv1InscriptionMedicale_inscriptionSignature6 signatureInputInscriptionMedicale", pk, "InscriptionSignature6").a("id", "signatureDiv1InscriptionMedicale", pk, "inscriptionSignature6").f();
				e("div").a("id", "signatureInputInscriptionMedicale", pk, "inscriptionSignature6");
					a("style", "display: ", StringUtils.isBlank(inscriptionSignature6) ? "block" : "none", "; ");
				f().g("div");
				e("img").a("id", "signatureImgInscriptionMedicale", pk, "inscriptionSignature6");
					a("class", "signatureImgInscriptionMedicale", pk, "InscriptionSignature6 ");
					a("src", StringUtils.isBlank(inscriptionSignature6) ? "data:image/png;base64," : inscriptionSignature6).a("alt", "");
					a("style", "padding: 10px; display: ", StringUtils.isBlank(inscriptionSignature6) ? "none" : "block", "; ");
				fg();
			g("div");
			e("div").a("id", "signatureDiv2InscriptionMedicale", pk, "inscriptionSignature6").f();
				e("button").a("id", "signatureButtonViderInscriptionMedicale", pk, "inscriptionSignature6");
					a("class", "w3-btn w3-round w3-border w3-border-black w3-section w3-ripple w3-padding w3-margin ");
					s(" onclick=", "\"");
						s("$('#signatureInputInscriptionMedicale", pk, "inscriptionSignature6').show(); ");
						s("$('#signatureImgInscriptionMedicale", pk, "inscriptionSignature6').hide(); ");
						s("enleverLueur($('#signatureInputInscriptionMedicale", pk, "inscriptionSignature6')); ");
						s("patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setInscriptionSignature6', null); ");
						s("if($('#signatureInputInscriptionMedicale", pk, "inscriptionSignature6')) { ");
						s("$('#signatureInputInscriptionMedicale", pk, "inscriptionSignature6').jSignature('reset'); ");
						s(" } else { ");
						s("$('#signatureInputInscriptionMedicale", pk, "inscriptionSignature6').jSignature({'height':200}); ");
						s(" } ");
					s("\"");
					f().sx("Vider");
				g("button");
			g("div");
		} else {
			sx(htmInscriptionSignature6());
		}
	}

	public void htmInscriptionSignature6(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "InscriptionMedicaleInscriptionSignature6").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputInscriptionSignature6(classeApiMethodeMethode);
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
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_inscriptionSignature6')); $('#", classeApiMethodeMethode, "_inscriptionSignature6').val(null); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + $('#InscriptionMedicaleForm :input[name=pk]').val() }], 'setInscriptionSignature6', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_inscriptionSignature6')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_inscriptionSignature6')); }); ")
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
	// inscriptionSignature7 //
	///////////////////////////

	/**	L'entité « inscriptionSignature7 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String inscriptionSignature7;
	@JsonIgnore
	public Couverture<String> inscriptionSignature7Couverture = new Couverture<String>().p(this).c(String.class).var("inscriptionSignature7").o(inscriptionSignature7);

	/**	<br/>L'entité « inscriptionSignature7 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptionSignature7">Trouver l'entité inscriptionSignature7 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _inscriptionSignature7(Couverture<String> c);

	public String getInscriptionSignature7() {
		return inscriptionSignature7;
	}

	public void setInscriptionSignature7(String inscriptionSignature7) {
		this.inscriptionSignature7 = inscriptionSignature7;
		this.inscriptionSignature7Couverture.dejaInitialise = true;
	}
	protected InscriptionMedicale inscriptionSignature7Init() {
		if(!inscriptionSignature7Couverture.dejaInitialise) {
			_inscriptionSignature7(inscriptionSignature7Couverture);
			if(inscriptionSignature7 == null)
				setInscriptionSignature7(inscriptionSignature7Couverture.o);
		}
		inscriptionSignature7Couverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public String solrInscriptionSignature7() {
		return inscriptionSignature7;
	}

	public String strInscriptionSignature7() {
		return inscriptionSignature7 == null ? "" : inscriptionSignature7;
	}

	public String jsonInscriptionSignature7() {
		return inscriptionSignature7 == null ? "" : inscriptionSignature7;
	}

	public String nomAffichageInscriptionSignature7() {
		return null;
	}

	public String htmTooltipInscriptionSignature7() {
		return null;
	}

	public String htmInscriptionSignature7() {
		return inscriptionSignature7 == null ? "" : StringEscapeUtils.escapeHtml4(strInscriptionSignature7());
	}

	public void inputInscriptionSignature7(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("div").a("class", "signatureDiv1InscriptionMedicale_inscriptionSignature7 signatureInputInscriptionMedicale", pk, "InscriptionSignature7").a("id", "signatureDiv1InscriptionMedicale", pk, "inscriptionSignature7").f();
				e("div").a("id", "signatureInputInscriptionMedicale", pk, "inscriptionSignature7");
					a("style", "display: ", StringUtils.isBlank(inscriptionSignature7) ? "block" : "none", "; ");
				f().g("div");
				e("img").a("id", "signatureImgInscriptionMedicale", pk, "inscriptionSignature7");
					a("class", "signatureImgInscriptionMedicale", pk, "InscriptionSignature7 ");
					a("src", StringUtils.isBlank(inscriptionSignature7) ? "data:image/png;base64," : inscriptionSignature7).a("alt", "");
					a("style", "padding: 10px; display: ", StringUtils.isBlank(inscriptionSignature7) ? "none" : "block", "; ");
				fg();
			g("div");
			e("div").a("id", "signatureDiv2InscriptionMedicale", pk, "inscriptionSignature7").f();
				e("button").a("id", "signatureButtonViderInscriptionMedicale", pk, "inscriptionSignature7");
					a("class", "w3-btn w3-round w3-border w3-border-black w3-section w3-ripple w3-padding w3-margin ");
					s(" onclick=", "\"");
						s("$('#signatureInputInscriptionMedicale", pk, "inscriptionSignature7').show(); ");
						s("$('#signatureImgInscriptionMedicale", pk, "inscriptionSignature7').hide(); ");
						s("enleverLueur($('#signatureInputInscriptionMedicale", pk, "inscriptionSignature7')); ");
						s("patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setInscriptionSignature7', null); ");
						s("if($('#signatureInputInscriptionMedicale", pk, "inscriptionSignature7')) { ");
						s("$('#signatureInputInscriptionMedicale", pk, "inscriptionSignature7').jSignature('reset'); ");
						s(" } else { ");
						s("$('#signatureInputInscriptionMedicale", pk, "inscriptionSignature7').jSignature({'height':200}); ");
						s(" } ");
					s("\"");
					f().sx("Vider");
				g("button");
			g("div");
		} else {
			sx(htmInscriptionSignature7());
		}
	}

	public void htmInscriptionSignature7(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "InscriptionMedicaleInscriptionSignature7").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputInscriptionSignature7(classeApiMethodeMethode);
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
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_inscriptionSignature7')); $('#", classeApiMethodeMethode, "_inscriptionSignature7').val(null); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + $('#InscriptionMedicaleForm :input[name=pk]').val() }], 'setInscriptionSignature7', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_inscriptionSignature7')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_inscriptionSignature7')); }); ")
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
	// inscriptionSignature8 //
	///////////////////////////

	/**	L'entité « inscriptionSignature8 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String inscriptionSignature8;
	@JsonIgnore
	public Couverture<String> inscriptionSignature8Couverture = new Couverture<String>().p(this).c(String.class).var("inscriptionSignature8").o(inscriptionSignature8);

	/**	<br/>L'entité « inscriptionSignature8 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptionSignature8">Trouver l'entité inscriptionSignature8 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _inscriptionSignature8(Couverture<String> c);

	public String getInscriptionSignature8() {
		return inscriptionSignature8;
	}

	public void setInscriptionSignature8(String inscriptionSignature8) {
		this.inscriptionSignature8 = inscriptionSignature8;
		this.inscriptionSignature8Couverture.dejaInitialise = true;
	}
	protected InscriptionMedicale inscriptionSignature8Init() {
		if(!inscriptionSignature8Couverture.dejaInitialise) {
			_inscriptionSignature8(inscriptionSignature8Couverture);
			if(inscriptionSignature8 == null)
				setInscriptionSignature8(inscriptionSignature8Couverture.o);
		}
		inscriptionSignature8Couverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public String solrInscriptionSignature8() {
		return inscriptionSignature8;
	}

	public String strInscriptionSignature8() {
		return inscriptionSignature8 == null ? "" : inscriptionSignature8;
	}

	public String jsonInscriptionSignature8() {
		return inscriptionSignature8 == null ? "" : inscriptionSignature8;
	}

	public String nomAffichageInscriptionSignature8() {
		return null;
	}

	public String htmTooltipInscriptionSignature8() {
		return null;
	}

	public String htmInscriptionSignature8() {
		return inscriptionSignature8 == null ? "" : StringEscapeUtils.escapeHtml4(strInscriptionSignature8());
	}

	public void inputInscriptionSignature8(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("div").a("class", "signatureDiv1InscriptionMedicale_inscriptionSignature8 signatureInputInscriptionMedicale", pk, "InscriptionSignature8").a("id", "signatureDiv1InscriptionMedicale", pk, "inscriptionSignature8").f();
				e("div").a("id", "signatureInputInscriptionMedicale", pk, "inscriptionSignature8");
					a("style", "display: ", StringUtils.isBlank(inscriptionSignature8) ? "block" : "none", "; ");
				f().g("div");
				e("img").a("id", "signatureImgInscriptionMedicale", pk, "inscriptionSignature8");
					a("class", "signatureImgInscriptionMedicale", pk, "InscriptionSignature8 ");
					a("src", StringUtils.isBlank(inscriptionSignature8) ? "data:image/png;base64," : inscriptionSignature8).a("alt", "");
					a("style", "padding: 10px; display: ", StringUtils.isBlank(inscriptionSignature8) ? "none" : "block", "; ");
				fg();
			g("div");
			e("div").a("id", "signatureDiv2InscriptionMedicale", pk, "inscriptionSignature8").f();
				e("button").a("id", "signatureButtonViderInscriptionMedicale", pk, "inscriptionSignature8");
					a("class", "w3-btn w3-round w3-border w3-border-black w3-section w3-ripple w3-padding w3-margin ");
					s(" onclick=", "\"");
						s("$('#signatureInputInscriptionMedicale", pk, "inscriptionSignature8').show(); ");
						s("$('#signatureImgInscriptionMedicale", pk, "inscriptionSignature8').hide(); ");
						s("enleverLueur($('#signatureInputInscriptionMedicale", pk, "inscriptionSignature8')); ");
						s("patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setInscriptionSignature8', null); ");
						s("if($('#signatureInputInscriptionMedicale", pk, "inscriptionSignature8')) { ");
						s("$('#signatureInputInscriptionMedicale", pk, "inscriptionSignature8').jSignature('reset'); ");
						s(" } else { ");
						s("$('#signatureInputInscriptionMedicale", pk, "inscriptionSignature8').jSignature({'height':200}); ");
						s(" } ");
					s("\"");
					f().sx("Vider");
				g("button");
			g("div");
		} else {
			sx(htmInscriptionSignature8());
		}
	}

	public void htmInscriptionSignature8(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "InscriptionMedicaleInscriptionSignature8").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputInscriptionSignature8(classeApiMethodeMethode);
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
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_inscriptionSignature8')); $('#", classeApiMethodeMethode, "_inscriptionSignature8').val(null); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + $('#InscriptionMedicaleForm :input[name=pk]').val() }], 'setInscriptionSignature8', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_inscriptionSignature8')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_inscriptionSignature8')); }); ")
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
	// inscriptionSignature9 //
	///////////////////////////

	/**	L'entité « inscriptionSignature9 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String inscriptionSignature9;
	@JsonIgnore
	public Couverture<String> inscriptionSignature9Couverture = new Couverture<String>().p(this).c(String.class).var("inscriptionSignature9").o(inscriptionSignature9);

	/**	<br/>L'entité « inscriptionSignature9 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptionSignature9">Trouver l'entité inscriptionSignature9 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _inscriptionSignature9(Couverture<String> c);

	public String getInscriptionSignature9() {
		return inscriptionSignature9;
	}

	public void setInscriptionSignature9(String inscriptionSignature9) {
		this.inscriptionSignature9 = inscriptionSignature9;
		this.inscriptionSignature9Couverture.dejaInitialise = true;
	}
	protected InscriptionMedicale inscriptionSignature9Init() {
		if(!inscriptionSignature9Couverture.dejaInitialise) {
			_inscriptionSignature9(inscriptionSignature9Couverture);
			if(inscriptionSignature9 == null)
				setInscriptionSignature9(inscriptionSignature9Couverture.o);
		}
		inscriptionSignature9Couverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public String solrInscriptionSignature9() {
		return inscriptionSignature9;
	}

	public String strInscriptionSignature9() {
		return inscriptionSignature9 == null ? "" : inscriptionSignature9;
	}

	public String jsonInscriptionSignature9() {
		return inscriptionSignature9 == null ? "" : inscriptionSignature9;
	}

	public String nomAffichageInscriptionSignature9() {
		return null;
	}

	public String htmTooltipInscriptionSignature9() {
		return null;
	}

	public String htmInscriptionSignature9() {
		return inscriptionSignature9 == null ? "" : StringEscapeUtils.escapeHtml4(strInscriptionSignature9());
	}

	public void inputInscriptionSignature9(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("div").a("class", "signatureDiv1InscriptionMedicale_inscriptionSignature9 signatureInputInscriptionMedicale", pk, "InscriptionSignature9").a("id", "signatureDiv1InscriptionMedicale", pk, "inscriptionSignature9").f();
				e("div").a("id", "signatureInputInscriptionMedicale", pk, "inscriptionSignature9");
					a("style", "display: ", StringUtils.isBlank(inscriptionSignature9) ? "block" : "none", "; ");
				f().g("div");
				e("img").a("id", "signatureImgInscriptionMedicale", pk, "inscriptionSignature9");
					a("class", "signatureImgInscriptionMedicale", pk, "InscriptionSignature9 ");
					a("src", StringUtils.isBlank(inscriptionSignature9) ? "data:image/png;base64," : inscriptionSignature9).a("alt", "");
					a("style", "padding: 10px; display: ", StringUtils.isBlank(inscriptionSignature9) ? "none" : "block", "; ");
				fg();
			g("div");
			e("div").a("id", "signatureDiv2InscriptionMedicale", pk, "inscriptionSignature9").f();
				e("button").a("id", "signatureButtonViderInscriptionMedicale", pk, "inscriptionSignature9");
					a("class", "w3-btn w3-round w3-border w3-border-black w3-section w3-ripple w3-padding w3-margin ");
					s(" onclick=", "\"");
						s("$('#signatureInputInscriptionMedicale", pk, "inscriptionSignature9').show(); ");
						s("$('#signatureImgInscriptionMedicale", pk, "inscriptionSignature9').hide(); ");
						s("enleverLueur($('#signatureInputInscriptionMedicale", pk, "inscriptionSignature9')); ");
						s("patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setInscriptionSignature9', null); ");
						s("if($('#signatureInputInscriptionMedicale", pk, "inscriptionSignature9')) { ");
						s("$('#signatureInputInscriptionMedicale", pk, "inscriptionSignature9').jSignature('reset'); ");
						s(" } else { ");
						s("$('#signatureInputInscriptionMedicale", pk, "inscriptionSignature9').jSignature({'height':200}); ");
						s(" } ");
					s("\"");
					f().sx("Vider");
				g("button");
			g("div");
		} else {
			sx(htmInscriptionSignature9());
		}
	}

	public void htmInscriptionSignature9(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "InscriptionMedicaleInscriptionSignature9").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputInscriptionSignature9(classeApiMethodeMethode);
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
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_inscriptionSignature9')); $('#", classeApiMethodeMethode, "_inscriptionSignature9').val(null); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + $('#InscriptionMedicaleForm :input[name=pk]').val() }], 'setInscriptionSignature9', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_inscriptionSignature9')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_inscriptionSignature9')); }); ")
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

	////////////////////////////
	// inscriptionSignature10 //
	////////////////////////////

	/**	L'entité « inscriptionSignature10 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String inscriptionSignature10;
	@JsonIgnore
	public Couverture<String> inscriptionSignature10Couverture = new Couverture<String>().p(this).c(String.class).var("inscriptionSignature10").o(inscriptionSignature10);

	/**	<br/>L'entité « inscriptionSignature10 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptionSignature10">Trouver l'entité inscriptionSignature10 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _inscriptionSignature10(Couverture<String> c);

	public String getInscriptionSignature10() {
		return inscriptionSignature10;
	}

	public void setInscriptionSignature10(String inscriptionSignature10) {
		this.inscriptionSignature10 = inscriptionSignature10;
		this.inscriptionSignature10Couverture.dejaInitialise = true;
	}
	protected InscriptionMedicale inscriptionSignature10Init() {
		if(!inscriptionSignature10Couverture.dejaInitialise) {
			_inscriptionSignature10(inscriptionSignature10Couverture);
			if(inscriptionSignature10 == null)
				setInscriptionSignature10(inscriptionSignature10Couverture.o);
		}
		inscriptionSignature10Couverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public String solrInscriptionSignature10() {
		return inscriptionSignature10;
	}

	public String strInscriptionSignature10() {
		return inscriptionSignature10 == null ? "" : inscriptionSignature10;
	}

	public String jsonInscriptionSignature10() {
		return inscriptionSignature10 == null ? "" : inscriptionSignature10;
	}

	public String nomAffichageInscriptionSignature10() {
		return null;
	}

	public String htmTooltipInscriptionSignature10() {
		return null;
	}

	public String htmInscriptionSignature10() {
		return inscriptionSignature10 == null ? "" : StringEscapeUtils.escapeHtml4(strInscriptionSignature10());
	}

	public void inputInscriptionSignature10(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("div").a("class", "signatureDiv1InscriptionMedicale_inscriptionSignature10 signatureInputInscriptionMedicale", pk, "InscriptionSignature10").a("id", "signatureDiv1InscriptionMedicale", pk, "inscriptionSignature10").f();
				e("div").a("id", "signatureInputInscriptionMedicale", pk, "inscriptionSignature10");
					a("style", "display: ", StringUtils.isBlank(inscriptionSignature10) ? "block" : "none", "; ");
				f().g("div");
				e("img").a("id", "signatureImgInscriptionMedicale", pk, "inscriptionSignature10");
					a("class", "signatureImgInscriptionMedicale", pk, "InscriptionSignature10 ");
					a("src", StringUtils.isBlank(inscriptionSignature10) ? "data:image/png;base64," : inscriptionSignature10).a("alt", "");
					a("style", "padding: 10px; display: ", StringUtils.isBlank(inscriptionSignature10) ? "none" : "block", "; ");
				fg();
			g("div");
			e("div").a("id", "signatureDiv2InscriptionMedicale", pk, "inscriptionSignature10").f();
				e("button").a("id", "signatureButtonViderInscriptionMedicale", pk, "inscriptionSignature10");
					a("class", "w3-btn w3-round w3-border w3-border-black w3-section w3-ripple w3-padding w3-margin ");
					s(" onclick=", "\"");
						s("$('#signatureInputInscriptionMedicale", pk, "inscriptionSignature10').show(); ");
						s("$('#signatureImgInscriptionMedicale", pk, "inscriptionSignature10').hide(); ");
						s("enleverLueur($('#signatureInputInscriptionMedicale", pk, "inscriptionSignature10')); ");
						s("patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setInscriptionSignature10', null); ");
						s("if($('#signatureInputInscriptionMedicale", pk, "inscriptionSignature10')) { ");
						s("$('#signatureInputInscriptionMedicale", pk, "inscriptionSignature10').jSignature('reset'); ");
						s(" } else { ");
						s("$('#signatureInputInscriptionMedicale", pk, "inscriptionSignature10').jSignature({'height':200}); ");
						s(" } ");
					s("\"");
					f().sx("Vider");
				g("button");
			g("div");
		} else {
			sx(htmInscriptionSignature10());
		}
	}

	public void htmInscriptionSignature10(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "InscriptionMedicaleInscriptionSignature10").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputInscriptionSignature10(classeApiMethodeMethode);
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
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_inscriptionSignature10')); $('#", classeApiMethodeMethode, "_inscriptionSignature10').val(null); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + $('#InscriptionMedicaleForm :input[name=pk]').val() }], 'setInscriptionSignature10', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_inscriptionSignature10')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_inscriptionSignature10')); }); ")
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

	//////////////////////
	// inscriptionDate1 //
	//////////////////////

	/**	L'entité « inscriptionDate1 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	protected LocalDate inscriptionDate1;
	@JsonIgnore
	public Couverture<LocalDate> inscriptionDate1Couverture = new Couverture<LocalDate>().p(this).c(LocalDate.class).var("inscriptionDate1").o(inscriptionDate1);

	/**	<br/>L'entité « inscriptionDate1 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptionDate1">Trouver l'entité inscriptionDate1 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _inscriptionDate1(Couverture<LocalDate> c);

	public LocalDate getInscriptionDate1() {
		return inscriptionDate1;
	}

	public void setInscriptionDate1(LocalDate inscriptionDate1) {
		this.inscriptionDate1 = inscriptionDate1;
		this.inscriptionDate1Couverture.dejaInitialise = true;
	}
	public InscriptionMedicale setInscriptionDate1(Instant o) {
		this.inscriptionDate1 = o == null ? null : LocalDate.from(o);
		this.inscriptionDate1Couverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	/** Example: 2011-12-03+01:00 **/
	public InscriptionMedicale setInscriptionDate1(String o) {
		this.inscriptionDate1 = o == null ? null : LocalDate.parse(o, DateTimeFormatter.ISO_DATE);
		this.inscriptionDate1Couverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	public InscriptionMedicale setInscriptionDate1(Date o) {
		this.inscriptionDate1 = o == null ? null : o.toInstant().atZone(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toLocalDate();
		this.inscriptionDate1Couverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	protected InscriptionMedicale inscriptionDate1Init() {
		if(!inscriptionDate1Couverture.dejaInitialise) {
			_inscriptionDate1(inscriptionDate1Couverture);
			if(inscriptionDate1 == null)
				setInscriptionDate1(inscriptionDate1Couverture.o);
		}
		inscriptionDate1Couverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public Date solrInscriptionDate1() {
		return inscriptionDate1 == null ? null : Date.from(inscriptionDate1.atStartOfDay(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toInstant().atZone(ZoneId.of("Z")).toInstant());
	}

	public String strInscriptionDate1() {
		return inscriptionDate1 == null ? "" : inscriptionDate1.format(DateTimeFormatter.ofPattern("EEE d MMM yyyy", Locale.forLanguageTag("fr-FR")));
	}

	public String jsonInscriptionDate1() {
		return inscriptionDate1 == null ? "" : inscriptionDate1.format(DateTimeFormatter.ISO_DATE);
	}

	public String nomAffichageInscriptionDate1() {
		return null;
	}

	public String htmTooltipInscriptionDate1() {
		return null;
	}

	public String htmInscriptionDate1() {
		return inscriptionDate1 == null ? "" : StringEscapeUtils.escapeHtml4(strInscriptionDate1());
	}

	public void inputInscriptionDate1(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("class", "w3-input w3-border datepicker setInscriptionDate1 classInscriptionMedicale inputInscriptionMedicale", pk, "InscriptionDate1 w3-input w3-border ")
				.a("placeholder", "DD-MM-YYYY")
				.a("data-timeformat", "dd-MM-yyyy")
				.a("id", classeApiMethodeMethode, "_inscriptionDate1")
				.a("onclick", "enleverLueur($(this)); ")
				.a("title", "La clé primaire des utilisateurs dans la base de données.  (DD-MM-YYYY)")
				.a("value", inscriptionDate1 == null ? "" : DateTimeFormatter.ofPattern("dd-MM-yyyy").format(inscriptionDate1))
				.a("onchange", "var t = moment(this.value, 'DD-MM-YYYY'); if(t) { var s = t.format('YYYY-MM-DD'); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setInscriptionDate1', s, function() { ajouterLueur($('#", classeApiMethodeMethode, "_inscriptionDate1')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_inscriptionDate1')); }); } ")
				.fg();
		} else {
			sx(htmInscriptionDate1());
		}
	}

	public void htmInscriptionDate1(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "InscriptionMedicaleInscriptionDate1").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row  ").f();
							{ e("div").a("class", "w3-cell ").f();
								inputInscriptionDate1(classeApiMethodeMethode);
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
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_inscriptionDate1')); $('#", classeApiMethodeMethode, "_inscriptionDate1').val(null); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + $('#InscriptionMedicaleForm :input[name=pk]').val() }], 'setInscriptionDate1', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_inscriptionDate1')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_inscriptionDate1')); }); ")
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

	//////////////////////
	// inscriptionDate2 //
	//////////////////////

	/**	L'entité « inscriptionDate2 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	protected LocalDate inscriptionDate2;
	@JsonIgnore
	public Couverture<LocalDate> inscriptionDate2Couverture = new Couverture<LocalDate>().p(this).c(LocalDate.class).var("inscriptionDate2").o(inscriptionDate2);

	/**	<br/>L'entité « inscriptionDate2 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptionDate2">Trouver l'entité inscriptionDate2 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _inscriptionDate2(Couverture<LocalDate> c);

	public LocalDate getInscriptionDate2() {
		return inscriptionDate2;
	}

	public void setInscriptionDate2(LocalDate inscriptionDate2) {
		this.inscriptionDate2 = inscriptionDate2;
		this.inscriptionDate2Couverture.dejaInitialise = true;
	}
	public InscriptionMedicale setInscriptionDate2(Instant o) {
		this.inscriptionDate2 = o == null ? null : LocalDate.from(o);
		this.inscriptionDate2Couverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	/** Example: 2011-12-03+01:00 **/
	public InscriptionMedicale setInscriptionDate2(String o) {
		this.inscriptionDate2 = o == null ? null : LocalDate.parse(o, DateTimeFormatter.ISO_DATE);
		this.inscriptionDate2Couverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	public InscriptionMedicale setInscriptionDate2(Date o) {
		this.inscriptionDate2 = o == null ? null : o.toInstant().atZone(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toLocalDate();
		this.inscriptionDate2Couverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	protected InscriptionMedicale inscriptionDate2Init() {
		if(!inscriptionDate2Couverture.dejaInitialise) {
			_inscriptionDate2(inscriptionDate2Couverture);
			if(inscriptionDate2 == null)
				setInscriptionDate2(inscriptionDate2Couverture.o);
		}
		inscriptionDate2Couverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public Date solrInscriptionDate2() {
		return inscriptionDate2 == null ? null : Date.from(inscriptionDate2.atStartOfDay(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toInstant().atZone(ZoneId.of("Z")).toInstant());
	}

	public String strInscriptionDate2() {
		return inscriptionDate2 == null ? "" : inscriptionDate2.format(DateTimeFormatter.ofPattern("EEE d MMM yyyy", Locale.forLanguageTag("fr-FR")));
	}

	public String jsonInscriptionDate2() {
		return inscriptionDate2 == null ? "" : inscriptionDate2.format(DateTimeFormatter.ISO_DATE);
	}

	public String nomAffichageInscriptionDate2() {
		return null;
	}

	public String htmTooltipInscriptionDate2() {
		return null;
	}

	public String htmInscriptionDate2() {
		return inscriptionDate2 == null ? "" : StringEscapeUtils.escapeHtml4(strInscriptionDate2());
	}

	public void inputInscriptionDate2(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("class", "w3-input w3-border datepicker setInscriptionDate2 classInscriptionMedicale inputInscriptionMedicale", pk, "InscriptionDate2 w3-input w3-border ")
				.a("placeholder", "DD-MM-YYYY")
				.a("data-timeformat", "dd-MM-yyyy")
				.a("id", classeApiMethodeMethode, "_inscriptionDate2")
				.a("onclick", "enleverLueur($(this)); ")
				.a("title", "La clé primaire des utilisateurs dans la base de données.  (DD-MM-YYYY)")
				.a("value", inscriptionDate2 == null ? "" : DateTimeFormatter.ofPattern("dd-MM-yyyy").format(inscriptionDate2))
				.a("onchange", "var t = moment(this.value, 'DD-MM-YYYY'); if(t) { var s = t.format('YYYY-MM-DD'); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setInscriptionDate2', s, function() { ajouterLueur($('#", classeApiMethodeMethode, "_inscriptionDate2')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_inscriptionDate2')); }); } ")
				.fg();
		} else {
			sx(htmInscriptionDate2());
		}
	}

	public void htmInscriptionDate2(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "InscriptionMedicaleInscriptionDate2").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row  ").f();
							{ e("div").a("class", "w3-cell ").f();
								inputInscriptionDate2(classeApiMethodeMethode);
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
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_inscriptionDate2')); $('#", classeApiMethodeMethode, "_inscriptionDate2').val(null); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + $('#InscriptionMedicaleForm :input[name=pk]').val() }], 'setInscriptionDate2', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_inscriptionDate2')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_inscriptionDate2')); }); ")
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

	//////////////////////
	// inscriptionDate3 //
	//////////////////////

	/**	L'entité « inscriptionDate3 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	protected LocalDate inscriptionDate3;
	@JsonIgnore
	public Couverture<LocalDate> inscriptionDate3Couverture = new Couverture<LocalDate>().p(this).c(LocalDate.class).var("inscriptionDate3").o(inscriptionDate3);

	/**	<br/>L'entité « inscriptionDate3 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptionDate3">Trouver l'entité inscriptionDate3 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _inscriptionDate3(Couverture<LocalDate> c);

	public LocalDate getInscriptionDate3() {
		return inscriptionDate3;
	}

	public void setInscriptionDate3(LocalDate inscriptionDate3) {
		this.inscriptionDate3 = inscriptionDate3;
		this.inscriptionDate3Couverture.dejaInitialise = true;
	}
	public InscriptionMedicale setInscriptionDate3(Instant o) {
		this.inscriptionDate3 = o == null ? null : LocalDate.from(o);
		this.inscriptionDate3Couverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	/** Example: 2011-12-03+01:00 **/
	public InscriptionMedicale setInscriptionDate3(String o) {
		this.inscriptionDate3 = o == null ? null : LocalDate.parse(o, DateTimeFormatter.ISO_DATE);
		this.inscriptionDate3Couverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	public InscriptionMedicale setInscriptionDate3(Date o) {
		this.inscriptionDate3 = o == null ? null : o.toInstant().atZone(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toLocalDate();
		this.inscriptionDate3Couverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	protected InscriptionMedicale inscriptionDate3Init() {
		if(!inscriptionDate3Couverture.dejaInitialise) {
			_inscriptionDate3(inscriptionDate3Couverture);
			if(inscriptionDate3 == null)
				setInscriptionDate3(inscriptionDate3Couverture.o);
		}
		inscriptionDate3Couverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public Date solrInscriptionDate3() {
		return inscriptionDate3 == null ? null : Date.from(inscriptionDate3.atStartOfDay(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toInstant().atZone(ZoneId.of("Z")).toInstant());
	}

	public String strInscriptionDate3() {
		return inscriptionDate3 == null ? "" : inscriptionDate3.format(DateTimeFormatter.ofPattern("EEE d MMM yyyy", Locale.forLanguageTag("fr-FR")));
	}

	public String jsonInscriptionDate3() {
		return inscriptionDate3 == null ? "" : inscriptionDate3.format(DateTimeFormatter.ISO_DATE);
	}

	public String nomAffichageInscriptionDate3() {
		return null;
	}

	public String htmTooltipInscriptionDate3() {
		return null;
	}

	public String htmInscriptionDate3() {
		return inscriptionDate3 == null ? "" : StringEscapeUtils.escapeHtml4(strInscriptionDate3());
	}

	public void inputInscriptionDate3(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("class", "w3-input w3-border datepicker setInscriptionDate3 classInscriptionMedicale inputInscriptionMedicale", pk, "InscriptionDate3 w3-input w3-border ")
				.a("placeholder", "DD-MM-YYYY")
				.a("data-timeformat", "dd-MM-yyyy")
				.a("id", classeApiMethodeMethode, "_inscriptionDate3")
				.a("onclick", "enleverLueur($(this)); ")
				.a("title", "La clé primaire des utilisateurs dans la base de données.  (DD-MM-YYYY)")
				.a("value", inscriptionDate3 == null ? "" : DateTimeFormatter.ofPattern("dd-MM-yyyy").format(inscriptionDate3))
				.a("onchange", "var t = moment(this.value, 'DD-MM-YYYY'); if(t) { var s = t.format('YYYY-MM-DD'); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setInscriptionDate3', s, function() { ajouterLueur($('#", classeApiMethodeMethode, "_inscriptionDate3')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_inscriptionDate3')); }); } ")
				.fg();
		} else {
			sx(htmInscriptionDate3());
		}
	}

	public void htmInscriptionDate3(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "InscriptionMedicaleInscriptionDate3").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row  ").f();
							{ e("div").a("class", "w3-cell ").f();
								inputInscriptionDate3(classeApiMethodeMethode);
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
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_inscriptionDate3')); $('#", classeApiMethodeMethode, "_inscriptionDate3').val(null); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + $('#InscriptionMedicaleForm :input[name=pk]').val() }], 'setInscriptionDate3', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_inscriptionDate3')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_inscriptionDate3')); }); ")
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

	//////////////////////
	// inscriptionDate4 //
	//////////////////////

	/**	L'entité « inscriptionDate4 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	protected LocalDate inscriptionDate4;
	@JsonIgnore
	public Couverture<LocalDate> inscriptionDate4Couverture = new Couverture<LocalDate>().p(this).c(LocalDate.class).var("inscriptionDate4").o(inscriptionDate4);

	/**	<br/>L'entité « inscriptionDate4 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptionDate4">Trouver l'entité inscriptionDate4 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _inscriptionDate4(Couverture<LocalDate> c);

	public LocalDate getInscriptionDate4() {
		return inscriptionDate4;
	}

	public void setInscriptionDate4(LocalDate inscriptionDate4) {
		this.inscriptionDate4 = inscriptionDate4;
		this.inscriptionDate4Couverture.dejaInitialise = true;
	}
	public InscriptionMedicale setInscriptionDate4(Instant o) {
		this.inscriptionDate4 = o == null ? null : LocalDate.from(o);
		this.inscriptionDate4Couverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	/** Example: 2011-12-03+01:00 **/
	public InscriptionMedicale setInscriptionDate4(String o) {
		this.inscriptionDate4 = o == null ? null : LocalDate.parse(o, DateTimeFormatter.ISO_DATE);
		this.inscriptionDate4Couverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	public InscriptionMedicale setInscriptionDate4(Date o) {
		this.inscriptionDate4 = o == null ? null : o.toInstant().atZone(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toLocalDate();
		this.inscriptionDate4Couverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	protected InscriptionMedicale inscriptionDate4Init() {
		if(!inscriptionDate4Couverture.dejaInitialise) {
			_inscriptionDate4(inscriptionDate4Couverture);
			if(inscriptionDate4 == null)
				setInscriptionDate4(inscriptionDate4Couverture.o);
		}
		inscriptionDate4Couverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public Date solrInscriptionDate4() {
		return inscriptionDate4 == null ? null : Date.from(inscriptionDate4.atStartOfDay(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toInstant().atZone(ZoneId.of("Z")).toInstant());
	}

	public String strInscriptionDate4() {
		return inscriptionDate4 == null ? "" : inscriptionDate4.format(DateTimeFormatter.ofPattern("EEE d MMM yyyy", Locale.forLanguageTag("fr-FR")));
	}

	public String jsonInscriptionDate4() {
		return inscriptionDate4 == null ? "" : inscriptionDate4.format(DateTimeFormatter.ISO_DATE);
	}

	public String nomAffichageInscriptionDate4() {
		return null;
	}

	public String htmTooltipInscriptionDate4() {
		return null;
	}

	public String htmInscriptionDate4() {
		return inscriptionDate4 == null ? "" : StringEscapeUtils.escapeHtml4(strInscriptionDate4());
	}

	public void inputInscriptionDate4(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("class", "w3-input w3-border datepicker setInscriptionDate4 classInscriptionMedicale inputInscriptionMedicale", pk, "InscriptionDate4 w3-input w3-border ")
				.a("placeholder", "DD-MM-YYYY")
				.a("data-timeformat", "dd-MM-yyyy")
				.a("id", classeApiMethodeMethode, "_inscriptionDate4")
				.a("onclick", "enleverLueur($(this)); ")
				.a("title", "La clé primaire des utilisateurs dans la base de données.  (DD-MM-YYYY)")
				.a("value", inscriptionDate4 == null ? "" : DateTimeFormatter.ofPattern("dd-MM-yyyy").format(inscriptionDate4))
				.a("onchange", "var t = moment(this.value, 'DD-MM-YYYY'); if(t) { var s = t.format('YYYY-MM-DD'); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setInscriptionDate4', s, function() { ajouterLueur($('#", classeApiMethodeMethode, "_inscriptionDate4')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_inscriptionDate4')); }); } ")
				.fg();
		} else {
			sx(htmInscriptionDate4());
		}
	}

	public void htmInscriptionDate4(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "InscriptionMedicaleInscriptionDate4").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row  ").f();
							{ e("div").a("class", "w3-cell ").f();
								inputInscriptionDate4(classeApiMethodeMethode);
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
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_inscriptionDate4')); $('#", classeApiMethodeMethode, "_inscriptionDate4').val(null); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + $('#InscriptionMedicaleForm :input[name=pk]').val() }], 'setInscriptionDate4', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_inscriptionDate4')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_inscriptionDate4')); }); ")
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

	//////////////////////
	// inscriptionDate5 //
	//////////////////////

	/**	L'entité « inscriptionDate5 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	protected LocalDate inscriptionDate5;
	@JsonIgnore
	public Couverture<LocalDate> inscriptionDate5Couverture = new Couverture<LocalDate>().p(this).c(LocalDate.class).var("inscriptionDate5").o(inscriptionDate5);

	/**	<br/>L'entité « inscriptionDate5 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptionDate5">Trouver l'entité inscriptionDate5 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _inscriptionDate5(Couverture<LocalDate> c);

	public LocalDate getInscriptionDate5() {
		return inscriptionDate5;
	}

	public void setInscriptionDate5(LocalDate inscriptionDate5) {
		this.inscriptionDate5 = inscriptionDate5;
		this.inscriptionDate5Couverture.dejaInitialise = true;
	}
	public InscriptionMedicale setInscriptionDate5(Instant o) {
		this.inscriptionDate5 = o == null ? null : LocalDate.from(o);
		this.inscriptionDate5Couverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	/** Example: 2011-12-03+01:00 **/
	public InscriptionMedicale setInscriptionDate5(String o) {
		this.inscriptionDate5 = o == null ? null : LocalDate.parse(o, DateTimeFormatter.ISO_DATE);
		this.inscriptionDate5Couverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	public InscriptionMedicale setInscriptionDate5(Date o) {
		this.inscriptionDate5 = o == null ? null : o.toInstant().atZone(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toLocalDate();
		this.inscriptionDate5Couverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	protected InscriptionMedicale inscriptionDate5Init() {
		if(!inscriptionDate5Couverture.dejaInitialise) {
			_inscriptionDate5(inscriptionDate5Couverture);
			if(inscriptionDate5 == null)
				setInscriptionDate5(inscriptionDate5Couverture.o);
		}
		inscriptionDate5Couverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public Date solrInscriptionDate5() {
		return inscriptionDate5 == null ? null : Date.from(inscriptionDate5.atStartOfDay(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toInstant().atZone(ZoneId.of("Z")).toInstant());
	}

	public String strInscriptionDate5() {
		return inscriptionDate5 == null ? "" : inscriptionDate5.format(DateTimeFormatter.ofPattern("EEE d MMM yyyy", Locale.forLanguageTag("fr-FR")));
	}

	public String jsonInscriptionDate5() {
		return inscriptionDate5 == null ? "" : inscriptionDate5.format(DateTimeFormatter.ISO_DATE);
	}

	public String nomAffichageInscriptionDate5() {
		return null;
	}

	public String htmTooltipInscriptionDate5() {
		return null;
	}

	public String htmInscriptionDate5() {
		return inscriptionDate5 == null ? "" : StringEscapeUtils.escapeHtml4(strInscriptionDate5());
	}

	public void inputInscriptionDate5(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("class", "w3-input w3-border datepicker setInscriptionDate5 classInscriptionMedicale inputInscriptionMedicale", pk, "InscriptionDate5 w3-input w3-border ")
				.a("placeholder", "DD-MM-YYYY")
				.a("data-timeformat", "dd-MM-yyyy")
				.a("id", classeApiMethodeMethode, "_inscriptionDate5")
				.a("onclick", "enleverLueur($(this)); ")
				.a("title", "La clé primaire des utilisateurs dans la base de données.  (DD-MM-YYYY)")
				.a("value", inscriptionDate5 == null ? "" : DateTimeFormatter.ofPattern("dd-MM-yyyy").format(inscriptionDate5))
				.a("onchange", "var t = moment(this.value, 'DD-MM-YYYY'); if(t) { var s = t.format('YYYY-MM-DD'); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setInscriptionDate5', s, function() { ajouterLueur($('#", classeApiMethodeMethode, "_inscriptionDate5')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_inscriptionDate5')); }); } ")
				.fg();
		} else {
			sx(htmInscriptionDate5());
		}
	}

	public void htmInscriptionDate5(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "InscriptionMedicaleInscriptionDate5").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row  ").f();
							{ e("div").a("class", "w3-cell ").f();
								inputInscriptionDate5(classeApiMethodeMethode);
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
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_inscriptionDate5')); $('#", classeApiMethodeMethode, "_inscriptionDate5').val(null); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + $('#InscriptionMedicaleForm :input[name=pk]').val() }], 'setInscriptionDate5', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_inscriptionDate5')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_inscriptionDate5')); }); ")
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

	//////////////////////
	// inscriptionDate6 //
	//////////////////////

	/**	L'entité « inscriptionDate6 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	protected LocalDate inscriptionDate6;
	@JsonIgnore
	public Couverture<LocalDate> inscriptionDate6Couverture = new Couverture<LocalDate>().p(this).c(LocalDate.class).var("inscriptionDate6").o(inscriptionDate6);

	/**	<br/>L'entité « inscriptionDate6 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptionDate6">Trouver l'entité inscriptionDate6 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _inscriptionDate6(Couverture<LocalDate> c);

	public LocalDate getInscriptionDate6() {
		return inscriptionDate6;
	}

	public void setInscriptionDate6(LocalDate inscriptionDate6) {
		this.inscriptionDate6 = inscriptionDate6;
		this.inscriptionDate6Couverture.dejaInitialise = true;
	}
	public InscriptionMedicale setInscriptionDate6(Instant o) {
		this.inscriptionDate6 = o == null ? null : LocalDate.from(o);
		this.inscriptionDate6Couverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	/** Example: 2011-12-03+01:00 **/
	public InscriptionMedicale setInscriptionDate6(String o) {
		this.inscriptionDate6 = o == null ? null : LocalDate.parse(o, DateTimeFormatter.ISO_DATE);
		this.inscriptionDate6Couverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	public InscriptionMedicale setInscriptionDate6(Date o) {
		this.inscriptionDate6 = o == null ? null : o.toInstant().atZone(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toLocalDate();
		this.inscriptionDate6Couverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	protected InscriptionMedicale inscriptionDate6Init() {
		if(!inscriptionDate6Couverture.dejaInitialise) {
			_inscriptionDate6(inscriptionDate6Couverture);
			if(inscriptionDate6 == null)
				setInscriptionDate6(inscriptionDate6Couverture.o);
		}
		inscriptionDate6Couverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public Date solrInscriptionDate6() {
		return inscriptionDate6 == null ? null : Date.from(inscriptionDate6.atStartOfDay(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toInstant().atZone(ZoneId.of("Z")).toInstant());
	}

	public String strInscriptionDate6() {
		return inscriptionDate6 == null ? "" : inscriptionDate6.format(DateTimeFormatter.ofPattern("EEE d MMM yyyy", Locale.forLanguageTag("fr-FR")));
	}

	public String jsonInscriptionDate6() {
		return inscriptionDate6 == null ? "" : inscriptionDate6.format(DateTimeFormatter.ISO_DATE);
	}

	public String nomAffichageInscriptionDate6() {
		return null;
	}

	public String htmTooltipInscriptionDate6() {
		return null;
	}

	public String htmInscriptionDate6() {
		return inscriptionDate6 == null ? "" : StringEscapeUtils.escapeHtml4(strInscriptionDate6());
	}

	public void inputInscriptionDate6(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("class", "w3-input w3-border datepicker setInscriptionDate6 classInscriptionMedicale inputInscriptionMedicale", pk, "InscriptionDate6 w3-input w3-border ")
				.a("placeholder", "DD-MM-YYYY")
				.a("data-timeformat", "dd-MM-yyyy")
				.a("id", classeApiMethodeMethode, "_inscriptionDate6")
				.a("onclick", "enleverLueur($(this)); ")
				.a("title", "La clé primaire des utilisateurs dans la base de données.  (DD-MM-YYYY)")
				.a("value", inscriptionDate6 == null ? "" : DateTimeFormatter.ofPattern("dd-MM-yyyy").format(inscriptionDate6))
				.a("onchange", "var t = moment(this.value, 'DD-MM-YYYY'); if(t) { var s = t.format('YYYY-MM-DD'); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setInscriptionDate6', s, function() { ajouterLueur($('#", classeApiMethodeMethode, "_inscriptionDate6')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_inscriptionDate6')); }); } ")
				.fg();
		} else {
			sx(htmInscriptionDate6());
		}
	}

	public void htmInscriptionDate6(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "InscriptionMedicaleInscriptionDate6").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row  ").f();
							{ e("div").a("class", "w3-cell ").f();
								inputInscriptionDate6(classeApiMethodeMethode);
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
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_inscriptionDate6')); $('#", classeApiMethodeMethode, "_inscriptionDate6').val(null); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + $('#InscriptionMedicaleForm :input[name=pk]').val() }], 'setInscriptionDate6', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_inscriptionDate6')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_inscriptionDate6')); }); ")
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

	//////////////////////
	// inscriptionDate7 //
	//////////////////////

	/**	L'entité « inscriptionDate7 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	protected LocalDate inscriptionDate7;
	@JsonIgnore
	public Couverture<LocalDate> inscriptionDate7Couverture = new Couverture<LocalDate>().p(this).c(LocalDate.class).var("inscriptionDate7").o(inscriptionDate7);

	/**	<br/>L'entité « inscriptionDate7 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptionDate7">Trouver l'entité inscriptionDate7 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _inscriptionDate7(Couverture<LocalDate> c);

	public LocalDate getInscriptionDate7() {
		return inscriptionDate7;
	}

	public void setInscriptionDate7(LocalDate inscriptionDate7) {
		this.inscriptionDate7 = inscriptionDate7;
		this.inscriptionDate7Couverture.dejaInitialise = true;
	}
	public InscriptionMedicale setInscriptionDate7(Instant o) {
		this.inscriptionDate7 = o == null ? null : LocalDate.from(o);
		this.inscriptionDate7Couverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	/** Example: 2011-12-03+01:00 **/
	public InscriptionMedicale setInscriptionDate7(String o) {
		this.inscriptionDate7 = o == null ? null : LocalDate.parse(o, DateTimeFormatter.ISO_DATE);
		this.inscriptionDate7Couverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	public InscriptionMedicale setInscriptionDate7(Date o) {
		this.inscriptionDate7 = o == null ? null : o.toInstant().atZone(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toLocalDate();
		this.inscriptionDate7Couverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	protected InscriptionMedicale inscriptionDate7Init() {
		if(!inscriptionDate7Couverture.dejaInitialise) {
			_inscriptionDate7(inscriptionDate7Couverture);
			if(inscriptionDate7 == null)
				setInscriptionDate7(inscriptionDate7Couverture.o);
		}
		inscriptionDate7Couverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public Date solrInscriptionDate7() {
		return inscriptionDate7 == null ? null : Date.from(inscriptionDate7.atStartOfDay(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toInstant().atZone(ZoneId.of("Z")).toInstant());
	}

	public String strInscriptionDate7() {
		return inscriptionDate7 == null ? "" : inscriptionDate7.format(DateTimeFormatter.ofPattern("EEE d MMM yyyy", Locale.forLanguageTag("fr-FR")));
	}

	public String jsonInscriptionDate7() {
		return inscriptionDate7 == null ? "" : inscriptionDate7.format(DateTimeFormatter.ISO_DATE);
	}

	public String nomAffichageInscriptionDate7() {
		return null;
	}

	public String htmTooltipInscriptionDate7() {
		return null;
	}

	public String htmInscriptionDate7() {
		return inscriptionDate7 == null ? "" : StringEscapeUtils.escapeHtml4(strInscriptionDate7());
	}

	public void inputInscriptionDate7(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("class", "w3-input w3-border datepicker setInscriptionDate7 classInscriptionMedicale inputInscriptionMedicale", pk, "InscriptionDate7 w3-input w3-border ")
				.a("placeholder", "DD-MM-YYYY")
				.a("data-timeformat", "dd-MM-yyyy")
				.a("id", classeApiMethodeMethode, "_inscriptionDate7")
				.a("onclick", "enleverLueur($(this)); ")
				.a("title", "La clé primaire des utilisateurs dans la base de données.  (DD-MM-YYYY)")
				.a("value", inscriptionDate7 == null ? "" : DateTimeFormatter.ofPattern("dd-MM-yyyy").format(inscriptionDate7))
				.a("onchange", "var t = moment(this.value, 'DD-MM-YYYY'); if(t) { var s = t.format('YYYY-MM-DD'); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setInscriptionDate7', s, function() { ajouterLueur($('#", classeApiMethodeMethode, "_inscriptionDate7')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_inscriptionDate7')); }); } ")
				.fg();
		} else {
			sx(htmInscriptionDate7());
		}
	}

	public void htmInscriptionDate7(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "InscriptionMedicaleInscriptionDate7").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row  ").f();
							{ e("div").a("class", "w3-cell ").f();
								inputInscriptionDate7(classeApiMethodeMethode);
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
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_inscriptionDate7')); $('#", classeApiMethodeMethode, "_inscriptionDate7').val(null); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + $('#InscriptionMedicaleForm :input[name=pk]').val() }], 'setInscriptionDate7', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_inscriptionDate7')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_inscriptionDate7')); }); ")
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

	//////////////////////
	// inscriptionDate8 //
	//////////////////////

	/**	L'entité « inscriptionDate8 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	protected LocalDate inscriptionDate8;
	@JsonIgnore
	public Couverture<LocalDate> inscriptionDate8Couverture = new Couverture<LocalDate>().p(this).c(LocalDate.class).var("inscriptionDate8").o(inscriptionDate8);

	/**	<br/>L'entité « inscriptionDate8 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptionDate8">Trouver l'entité inscriptionDate8 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _inscriptionDate8(Couverture<LocalDate> c);

	public LocalDate getInscriptionDate8() {
		return inscriptionDate8;
	}

	public void setInscriptionDate8(LocalDate inscriptionDate8) {
		this.inscriptionDate8 = inscriptionDate8;
		this.inscriptionDate8Couverture.dejaInitialise = true;
	}
	public InscriptionMedicale setInscriptionDate8(Instant o) {
		this.inscriptionDate8 = o == null ? null : LocalDate.from(o);
		this.inscriptionDate8Couverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	/** Example: 2011-12-03+01:00 **/
	public InscriptionMedicale setInscriptionDate8(String o) {
		this.inscriptionDate8 = o == null ? null : LocalDate.parse(o, DateTimeFormatter.ISO_DATE);
		this.inscriptionDate8Couverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	public InscriptionMedicale setInscriptionDate8(Date o) {
		this.inscriptionDate8 = o == null ? null : o.toInstant().atZone(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toLocalDate();
		this.inscriptionDate8Couverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	protected InscriptionMedicale inscriptionDate8Init() {
		if(!inscriptionDate8Couverture.dejaInitialise) {
			_inscriptionDate8(inscriptionDate8Couverture);
			if(inscriptionDate8 == null)
				setInscriptionDate8(inscriptionDate8Couverture.o);
		}
		inscriptionDate8Couverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public Date solrInscriptionDate8() {
		return inscriptionDate8 == null ? null : Date.from(inscriptionDate8.atStartOfDay(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toInstant().atZone(ZoneId.of("Z")).toInstant());
	}

	public String strInscriptionDate8() {
		return inscriptionDate8 == null ? "" : inscriptionDate8.format(DateTimeFormatter.ofPattern("EEE d MMM yyyy", Locale.forLanguageTag("fr-FR")));
	}

	public String jsonInscriptionDate8() {
		return inscriptionDate8 == null ? "" : inscriptionDate8.format(DateTimeFormatter.ISO_DATE);
	}

	public String nomAffichageInscriptionDate8() {
		return null;
	}

	public String htmTooltipInscriptionDate8() {
		return null;
	}

	public String htmInscriptionDate8() {
		return inscriptionDate8 == null ? "" : StringEscapeUtils.escapeHtml4(strInscriptionDate8());
	}

	public void inputInscriptionDate8(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("class", "w3-input w3-border datepicker setInscriptionDate8 classInscriptionMedicale inputInscriptionMedicale", pk, "InscriptionDate8 w3-input w3-border ")
				.a("placeholder", "DD-MM-YYYY")
				.a("data-timeformat", "dd-MM-yyyy")
				.a("id", classeApiMethodeMethode, "_inscriptionDate8")
				.a("onclick", "enleverLueur($(this)); ")
				.a("title", "La clé primaire des utilisateurs dans la base de données.  (DD-MM-YYYY)")
				.a("value", inscriptionDate8 == null ? "" : DateTimeFormatter.ofPattern("dd-MM-yyyy").format(inscriptionDate8))
				.a("onchange", "var t = moment(this.value, 'DD-MM-YYYY'); if(t) { var s = t.format('YYYY-MM-DD'); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setInscriptionDate8', s, function() { ajouterLueur($('#", classeApiMethodeMethode, "_inscriptionDate8')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_inscriptionDate8')); }); } ")
				.fg();
		} else {
			sx(htmInscriptionDate8());
		}
	}

	public void htmInscriptionDate8(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "InscriptionMedicaleInscriptionDate8").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row  ").f();
							{ e("div").a("class", "w3-cell ").f();
								inputInscriptionDate8(classeApiMethodeMethode);
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
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_inscriptionDate8')); $('#", classeApiMethodeMethode, "_inscriptionDate8').val(null); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + $('#InscriptionMedicaleForm :input[name=pk]').val() }], 'setInscriptionDate8', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_inscriptionDate8')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_inscriptionDate8')); }); ")
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

	//////////////////////
	// inscriptionDate9 //
	//////////////////////

	/**	L'entité « inscriptionDate9 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	protected LocalDate inscriptionDate9;
	@JsonIgnore
	public Couverture<LocalDate> inscriptionDate9Couverture = new Couverture<LocalDate>().p(this).c(LocalDate.class).var("inscriptionDate9").o(inscriptionDate9);

	/**	<br/>L'entité « inscriptionDate9 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptionDate9">Trouver l'entité inscriptionDate9 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _inscriptionDate9(Couverture<LocalDate> c);

	public LocalDate getInscriptionDate9() {
		return inscriptionDate9;
	}

	public void setInscriptionDate9(LocalDate inscriptionDate9) {
		this.inscriptionDate9 = inscriptionDate9;
		this.inscriptionDate9Couverture.dejaInitialise = true;
	}
	public InscriptionMedicale setInscriptionDate9(Instant o) {
		this.inscriptionDate9 = o == null ? null : LocalDate.from(o);
		this.inscriptionDate9Couverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	/** Example: 2011-12-03+01:00 **/
	public InscriptionMedicale setInscriptionDate9(String o) {
		this.inscriptionDate9 = o == null ? null : LocalDate.parse(o, DateTimeFormatter.ISO_DATE);
		this.inscriptionDate9Couverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	public InscriptionMedicale setInscriptionDate9(Date o) {
		this.inscriptionDate9 = o == null ? null : o.toInstant().atZone(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toLocalDate();
		this.inscriptionDate9Couverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	protected InscriptionMedicale inscriptionDate9Init() {
		if(!inscriptionDate9Couverture.dejaInitialise) {
			_inscriptionDate9(inscriptionDate9Couverture);
			if(inscriptionDate9 == null)
				setInscriptionDate9(inscriptionDate9Couverture.o);
		}
		inscriptionDate9Couverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public Date solrInscriptionDate9() {
		return inscriptionDate9 == null ? null : Date.from(inscriptionDate9.atStartOfDay(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toInstant().atZone(ZoneId.of("Z")).toInstant());
	}

	public String strInscriptionDate9() {
		return inscriptionDate9 == null ? "" : inscriptionDate9.format(DateTimeFormatter.ofPattern("EEE d MMM yyyy", Locale.forLanguageTag("fr-FR")));
	}

	public String jsonInscriptionDate9() {
		return inscriptionDate9 == null ? "" : inscriptionDate9.format(DateTimeFormatter.ISO_DATE);
	}

	public String nomAffichageInscriptionDate9() {
		return null;
	}

	public String htmTooltipInscriptionDate9() {
		return null;
	}

	public String htmInscriptionDate9() {
		return inscriptionDate9 == null ? "" : StringEscapeUtils.escapeHtml4(strInscriptionDate9());
	}

	public void inputInscriptionDate9(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("class", "w3-input w3-border datepicker setInscriptionDate9 classInscriptionMedicale inputInscriptionMedicale", pk, "InscriptionDate9 w3-input w3-border ")
				.a("placeholder", "DD-MM-YYYY")
				.a("data-timeformat", "dd-MM-yyyy")
				.a("id", classeApiMethodeMethode, "_inscriptionDate9")
				.a("onclick", "enleverLueur($(this)); ")
				.a("title", "La clé primaire des utilisateurs dans la base de données.  (DD-MM-YYYY)")
				.a("value", inscriptionDate9 == null ? "" : DateTimeFormatter.ofPattern("dd-MM-yyyy").format(inscriptionDate9))
				.a("onchange", "var t = moment(this.value, 'DD-MM-YYYY'); if(t) { var s = t.format('YYYY-MM-DD'); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setInscriptionDate9', s, function() { ajouterLueur($('#", classeApiMethodeMethode, "_inscriptionDate9')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_inscriptionDate9')); }); } ")
				.fg();
		} else {
			sx(htmInscriptionDate9());
		}
	}

	public void htmInscriptionDate9(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "InscriptionMedicaleInscriptionDate9").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row  ").f();
							{ e("div").a("class", "w3-cell ").f();
								inputInscriptionDate9(classeApiMethodeMethode);
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
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_inscriptionDate9')); $('#", classeApiMethodeMethode, "_inscriptionDate9').val(null); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + $('#InscriptionMedicaleForm :input[name=pk]').val() }], 'setInscriptionDate9', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_inscriptionDate9')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_inscriptionDate9')); }); ")
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
	// inscriptionDate10 //
	///////////////////////

	/**	L'entité « inscriptionDate10 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	protected LocalDate inscriptionDate10;
	@JsonIgnore
	public Couverture<LocalDate> inscriptionDate10Couverture = new Couverture<LocalDate>().p(this).c(LocalDate.class).var("inscriptionDate10").o(inscriptionDate10);

	/**	<br/>L'entité « inscriptionDate10 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptionDate10">Trouver l'entité inscriptionDate10 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _inscriptionDate10(Couverture<LocalDate> c);

	public LocalDate getInscriptionDate10() {
		return inscriptionDate10;
	}

	public void setInscriptionDate10(LocalDate inscriptionDate10) {
		this.inscriptionDate10 = inscriptionDate10;
		this.inscriptionDate10Couverture.dejaInitialise = true;
	}
	public InscriptionMedicale setInscriptionDate10(Instant o) {
		this.inscriptionDate10 = o == null ? null : LocalDate.from(o);
		this.inscriptionDate10Couverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	/** Example: 2011-12-03+01:00 **/
	public InscriptionMedicale setInscriptionDate10(String o) {
		this.inscriptionDate10 = o == null ? null : LocalDate.parse(o, DateTimeFormatter.ISO_DATE);
		this.inscriptionDate10Couverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	public InscriptionMedicale setInscriptionDate10(Date o) {
		this.inscriptionDate10 = o == null ? null : o.toInstant().atZone(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toLocalDate();
		this.inscriptionDate10Couverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	protected InscriptionMedicale inscriptionDate10Init() {
		if(!inscriptionDate10Couverture.dejaInitialise) {
			_inscriptionDate10(inscriptionDate10Couverture);
			if(inscriptionDate10 == null)
				setInscriptionDate10(inscriptionDate10Couverture.o);
		}
		inscriptionDate10Couverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public Date solrInscriptionDate10() {
		return inscriptionDate10 == null ? null : Date.from(inscriptionDate10.atStartOfDay(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toInstant().atZone(ZoneId.of("Z")).toInstant());
	}

	public String strInscriptionDate10() {
		return inscriptionDate10 == null ? "" : inscriptionDate10.format(DateTimeFormatter.ofPattern("EEE d MMM yyyy", Locale.forLanguageTag("fr-FR")));
	}

	public String jsonInscriptionDate10() {
		return inscriptionDate10 == null ? "" : inscriptionDate10.format(DateTimeFormatter.ISO_DATE);
	}

	public String nomAffichageInscriptionDate10() {
		return null;
	}

	public String htmTooltipInscriptionDate10() {
		return null;
	}

	public String htmInscriptionDate10() {
		return inscriptionDate10 == null ? "" : StringEscapeUtils.escapeHtml4(strInscriptionDate10());
	}

	public void inputInscriptionDate10(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		if(
				utilisateurCles.contains(requeteSite_.getUtilisateurCle())
				|| Objects.equals(sessionId, requeteSite_.getSessionId())
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRessource(), ROLES)
				|| CollectionUtils.containsAny(requeteSite_.getUtilisateurRolesRoyaume(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("class", "w3-input w3-border datepicker setInscriptionDate10 classInscriptionMedicale inputInscriptionMedicale", pk, "InscriptionDate10 w3-input w3-border ")
				.a("placeholder", "DD-MM-YYYY")
				.a("data-timeformat", "dd-MM-yyyy")
				.a("id", classeApiMethodeMethode, "_inscriptionDate10")
				.a("onclick", "enleverLueur($(this)); ")
				.a("title", "La clé primaire des utilisateurs dans la base de données.  (DD-MM-YYYY)")
				.a("value", inscriptionDate10 == null ? "" : DateTimeFormatter.ofPattern("dd-MM-yyyy").format(inscriptionDate10))
				.a("onchange", "var t = moment(this.value, 'DD-MM-YYYY'); if(t) { var s = t.format('YYYY-MM-DD'); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setInscriptionDate10', s, function() { ajouterLueur($('#", classeApiMethodeMethode, "_inscriptionDate10')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_inscriptionDate10')); }); } ")
				.fg();
		} else {
			sx(htmInscriptionDate10());
		}
	}

	public void htmInscriptionDate10(String classeApiMethodeMethode) {
		InscriptionMedicale s = (InscriptionMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "InscriptionMedicaleInscriptionDate10").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row  ").f();
							{ e("div").a("class", "w3-cell ").f();
								inputInscriptionDate10(classeApiMethodeMethode);
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
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_inscriptionDate10')); $('#", classeApiMethodeMethode, "_inscriptionDate10').val(null); patchInscriptionMedicaleVal([{ name: 'fq', value: 'pk:' + $('#InscriptionMedicaleForm :input[name=pk]').val() }], 'setInscriptionDate10', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_inscriptionDate10')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_inscriptionDate10')); }); ")
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

	/////////////////////////////
	// inscriptionsInscription //
	/////////////////////////////

	/**	L'entité « inscriptionsInscription »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<InscriptionMedicale>(). 
	 */
	@JsonInclude(Include.NON_NULL)
	protected List<InscriptionMedicale> inscriptionsInscription = new ArrayList<InscriptionMedicale>();
	@JsonIgnore
	public Couverture<List<InscriptionMedicale>> inscriptionsInscriptionCouverture = new Couverture<List<InscriptionMedicale>>().p(this).c(List.class).var("inscriptionsInscription").o(inscriptionsInscription);

	/**	<br/>L'entité « inscriptionsInscription »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<InscriptionMedicale>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptionsInscription">Trouver l'entité inscriptionsInscription dans Solr</a>
	 * <br/>
	 * @param inscriptionsInscription est l'entité déjà construit. 
	 **/
	protected abstract void _inscriptionsInscription(List<InscriptionMedicale> l);

	public List<InscriptionMedicale> getInscriptionsInscription() {
		return inscriptionsInscription;
	}

	public void setInscriptionsInscription(List<InscriptionMedicale> inscriptionsInscription) {
		this.inscriptionsInscription = inscriptionsInscription;
		this.inscriptionsInscriptionCouverture.dejaInitialise = true;
	}
	public InscriptionMedicale addInscriptionsInscription(InscriptionMedicale...objets) {
		for(InscriptionMedicale o : objets) {
			addInscriptionsInscription(o);
		}
		return (InscriptionMedicale)this;
	}
	public InscriptionMedicale addInscriptionsInscription(InscriptionMedicale o) {
		if(o != null && !inscriptionsInscription.contains(o))
			this.inscriptionsInscription.add(o);
		return (InscriptionMedicale)this;
	}
	protected InscriptionMedicale inscriptionsInscriptionInit() {
		if(!inscriptionsInscriptionCouverture.dejaInitialise) {
			_inscriptionsInscription(inscriptionsInscription);
		}
		inscriptionsInscriptionCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	///////////////////////
	// inscriptionNumero //
	///////////////////////

	/**	L'entité « inscriptionNumero »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer inscriptionNumero;
	@JsonIgnore
	public Couverture<Integer> inscriptionNumeroCouverture = new Couverture<Integer>().p(this).c(Integer.class).var("inscriptionNumero").o(inscriptionNumero);

	/**	<br/>L'entité « inscriptionNumero »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptionNumero">Trouver l'entité inscriptionNumero dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _inscriptionNumero(Couverture<Integer> c);

	public Integer getInscriptionNumero() {
		return inscriptionNumero;
	}

	public void setInscriptionNumero(Integer inscriptionNumero) {
		this.inscriptionNumero = inscriptionNumero;
		this.inscriptionNumeroCouverture.dejaInitialise = true;
	}
	public InscriptionMedicale setInscriptionNumero(String o) {
		if(NumberUtils.isParsable(o))
			this.inscriptionNumero = Integer.parseInt(o);
		this.inscriptionNumeroCouverture.dejaInitialise = true;
		return (InscriptionMedicale)this;
	}
	protected InscriptionMedicale inscriptionNumeroInit() {
		if(!inscriptionNumeroCouverture.dejaInitialise) {
			_inscriptionNumero(inscriptionNumeroCouverture);
			if(inscriptionNumero == null)
				setInscriptionNumero(inscriptionNumeroCouverture.o);
		}
		inscriptionNumeroCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public Integer solrInscriptionNumero() {
		return inscriptionNumero;
	}

	public String strInscriptionNumero() {
		return inscriptionNumero == null ? "" : inscriptionNumero.toString();
	}

	public String jsonInscriptionNumero() {
		return inscriptionNumero == null ? "" : inscriptionNumero.toString();
	}

	public String nomAffichageInscriptionNumero() {
		return null;
	}

	public String htmTooltipInscriptionNumero() {
		return null;
	}

	public String htmInscriptionNumero() {
		return inscriptionNumero == null ? "" : StringEscapeUtils.escapeHtml4(strInscriptionNumero());
	}

	///////////////////////////
	// inscriptionNomComplet //
	///////////////////////////

	/**	L'entité « inscriptionNomComplet »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String inscriptionNomComplet;
	@JsonIgnore
	public Couverture<String> inscriptionNomCompletCouverture = new Couverture<String>().p(this).c(String.class).var("inscriptionNomComplet").o(inscriptionNomComplet);

	/**	<br/>L'entité « inscriptionNomComplet »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptionNomComplet">Trouver l'entité inscriptionNomComplet dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _inscriptionNomComplet(Couverture<String> c);

	public String getInscriptionNomComplet() {
		return inscriptionNomComplet;
	}

	public void setInscriptionNomComplet(String inscriptionNomComplet) {
		this.inscriptionNomComplet = inscriptionNomComplet;
		this.inscriptionNomCompletCouverture.dejaInitialise = true;
	}
	protected InscriptionMedicale inscriptionNomCompletInit() {
		if(!inscriptionNomCompletCouverture.dejaInitialise) {
			_inscriptionNomComplet(inscriptionNomCompletCouverture);
			if(inscriptionNomComplet == null)
				setInscriptionNomComplet(inscriptionNomCompletCouverture.o);
		}
		inscriptionNomCompletCouverture.dejaInitialise(true);
		return (InscriptionMedicale)this;
	}

	public String solrInscriptionNomComplet() {
		return inscriptionNomComplet;
	}

	public String strInscriptionNomComplet() {
		return inscriptionNomComplet == null ? "" : inscriptionNomComplet;
	}

	public String jsonInscriptionNomComplet() {
		return inscriptionNomComplet == null ? "" : inscriptionNomComplet;
	}

	public String nomAffichageInscriptionNomComplet() {
		return "nom";
	}

	public String htmTooltipInscriptionNomComplet() {
		return null;
	}

	public String htmInscriptionNomComplet() {
		return inscriptionNomComplet == null ? "" : StringEscapeUtils.escapeHtml4(strInscriptionNomComplet());
	}

	//////////////
	// initLoin //
	//////////////

	protected boolean dejaInitialiseInscriptionMedicale = false;

	public InscriptionMedicale initLoinInscriptionMedicale(RequeteSiteFrFR requeteSite_) {
		setRequeteSite_(requeteSite_);
		if(!dejaInitialiseInscriptionMedicale) {
			dejaInitialiseInscriptionMedicale = true;
			initLoinInscriptionMedicale();
		}
		return (InscriptionMedicale)this;
	}

	public void initLoinInscriptionMedicale() {
		initInscriptionMedicale();
		super.initLoinCluster(requeteSite_);
	}

	public void initInscriptionMedicale() {
		inscriptionCleInit();
		cliniqueCleInit();
		cliniqueRechercheInit();
		clinique_Init();
		patientCleInit();
		formInscriptionCleInit();
		utilisateurClesInit();
		medicaleTriInit();
		cliniqueTriInit();
		patientRechercheInit();
		patient_Init();
		patientPrenomInit();
		patientPrenomPrefereInit();
		patientFamilleNomInit();
		patientNomCompletInit();
		patientNomCompletPrefereInit();
		patientDateNaissanceInit();
		patientDateNaissanceDAnneeInit();
		patientDateNaissanceMoisDAnneeInit();
		patientDateNaissanceJourDeSemaineInit();
		patientMoisNaissanceInit();
		patientJourNaissanceInit();
		cliniqueNomInit();
		cliniqueNomCompletInit();
		cliniqueEmplacementInit();
		cliniqueAddresseInit();
		cliniqueNumeroTelephoneInit();
		cliniqueAdministrateurNomInit();
		inscriptionApprouveInit();
		inscriptionImmunisationsInit();
		familleAddresseInit();
		familleCommentVousConnaissezCliniqueInit();
		inscriptionConsiderationsSpecialesInit();
		patientConditionsMedicalesInit();
		patientCliniquesPrecedemmentFrequenteesInit();
		patientDescriptionInit();
		patientObjectifsInit();
		customerProfileIdInit();
		creeDAnneeInit();
		creeJourDeSemaineInit();
		creeMoisDAnneeInit();
		creeHeureDuJourInit();
		inscriptionSignature1Init();
		inscriptionSignature2Init();
		inscriptionSignature3Init();
		inscriptionSignature4Init();
		inscriptionSignature5Init();
		inscriptionSignature6Init();
		inscriptionSignature7Init();
		inscriptionSignature8Init();
		inscriptionSignature9Init();
		inscriptionSignature10Init();
		inscriptionDate1Init();
		inscriptionDate2Init();
		inscriptionDate3Init();
		inscriptionDate4Init();
		inscriptionDate5Init();
		inscriptionDate6Init();
		inscriptionDate7Init();
		inscriptionDate8Init();
		inscriptionDate9Init();
		inscriptionDate10Init();
		inscriptionsInscriptionInit();
		inscriptionNumeroInit();
		inscriptionNomCompletInit();
	}

	@Override public void initLoinPourClasse(RequeteSiteFrFR requeteSite_) {
		initLoinInscriptionMedicale(requeteSite_);
	}

	/////////////////
	// requeteSite //
	/////////////////

	public void requeteSiteInscriptionMedicale(RequeteSiteFrFR requeteSite_) {
			super.requeteSiteCluster(requeteSite_);
		if(cliniqueRecherche != null)
			cliniqueRecherche.setRequeteSite_(requeteSite_);
		if(patientRecherche != null)
			patientRecherche.setRequeteSite_(requeteSite_);
	}

	public void requeteSitePourClasse(RequeteSiteFrFR requeteSite_) {
		requeteSiteInscriptionMedicale(requeteSite_);
	}

	/////////////
	// obtenir //
	/////////////

	@Override public Object obtenirPourClasse(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtenirInscriptionMedicale(v);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.obtenirPourClasse(v);
			}
		}
		return o;
	}
	public Object obtenirInscriptionMedicale(String var) {
		InscriptionMedicale oInscriptionMedicale = (InscriptionMedicale)this;
		switch(var) {
			case "inscriptionCle":
				return oInscriptionMedicale.inscriptionCle;
			case "cliniqueCle":
				return oInscriptionMedicale.cliniqueCle;
			case "cliniqueRecherche":
				return oInscriptionMedicale.cliniqueRecherche;
			case "clinique_":
				return oInscriptionMedicale.clinique_;
			case "patientCle":
				return oInscriptionMedicale.patientCle;
			case "formInscriptionCle":
				return oInscriptionMedicale.formInscriptionCle;
			case "utilisateurCles":
				return oInscriptionMedicale.utilisateurCles;
			case "medicaleTri":
				return oInscriptionMedicale.medicaleTri;
			case "cliniqueTri":
				return oInscriptionMedicale.cliniqueTri;
			case "patientRecherche":
				return oInscriptionMedicale.patientRecherche;
			case "patient_":
				return oInscriptionMedicale.patient_;
			case "patientPrenom":
				return oInscriptionMedicale.patientPrenom;
			case "patientPrenomPrefere":
				return oInscriptionMedicale.patientPrenomPrefere;
			case "patientFamilleNom":
				return oInscriptionMedicale.patientFamilleNom;
			case "patientNomComplet":
				return oInscriptionMedicale.patientNomComplet;
			case "patientNomCompletPrefere":
				return oInscriptionMedicale.patientNomCompletPrefere;
			case "patientDateNaissance":
				return oInscriptionMedicale.patientDateNaissance;
			case "patientDateNaissanceDAnnee":
				return oInscriptionMedicale.patientDateNaissanceDAnnee;
			case "patientDateNaissanceMoisDAnnee":
				return oInscriptionMedicale.patientDateNaissanceMoisDAnnee;
			case "patientDateNaissanceJourDeSemaine":
				return oInscriptionMedicale.patientDateNaissanceJourDeSemaine;
			case "patientMoisNaissance":
				return oInscriptionMedicale.patientMoisNaissance;
			case "patientJourNaissance":
				return oInscriptionMedicale.patientJourNaissance;
			case "cliniqueNom":
				return oInscriptionMedicale.cliniqueNom;
			case "cliniqueNomComplet":
				return oInscriptionMedicale.cliniqueNomComplet;
			case "cliniqueEmplacement":
				return oInscriptionMedicale.cliniqueEmplacement;
			case "cliniqueAddresse":
				return oInscriptionMedicale.cliniqueAddresse;
			case "cliniqueNumeroTelephone":
				return oInscriptionMedicale.cliniqueNumeroTelephone;
			case "cliniqueAdministrateurNom":
				return oInscriptionMedicale.cliniqueAdministrateurNom;
			case "inscriptionApprouve":
				return oInscriptionMedicale.inscriptionApprouve;
			case "inscriptionImmunisations":
				return oInscriptionMedicale.inscriptionImmunisations;
			case "familleAddresse":
				return oInscriptionMedicale.familleAddresse;
			case "familleCommentVousConnaissezClinique":
				return oInscriptionMedicale.familleCommentVousConnaissezClinique;
			case "inscriptionConsiderationsSpeciales":
				return oInscriptionMedicale.inscriptionConsiderationsSpeciales;
			case "patientConditionsMedicales":
				return oInscriptionMedicale.patientConditionsMedicales;
			case "patientCliniquesPrecedemmentFrequentees":
				return oInscriptionMedicale.patientCliniquesPrecedemmentFrequentees;
			case "patientDescription":
				return oInscriptionMedicale.patientDescription;
			case "patientObjectifs":
				return oInscriptionMedicale.patientObjectifs;
			case "customerProfileId":
				return oInscriptionMedicale.customerProfileId;
			case "creeDAnnee":
				return oInscriptionMedicale.creeDAnnee;
			case "creeJourDeSemaine":
				return oInscriptionMedicale.creeJourDeSemaine;
			case "creeMoisDAnnee":
				return oInscriptionMedicale.creeMoisDAnnee;
			case "creeHeureDuJour":
				return oInscriptionMedicale.creeHeureDuJour;
			case "inscriptionSignature1":
				return oInscriptionMedicale.inscriptionSignature1;
			case "inscriptionSignature2":
				return oInscriptionMedicale.inscriptionSignature2;
			case "inscriptionSignature3":
				return oInscriptionMedicale.inscriptionSignature3;
			case "inscriptionSignature4":
				return oInscriptionMedicale.inscriptionSignature4;
			case "inscriptionSignature5":
				return oInscriptionMedicale.inscriptionSignature5;
			case "inscriptionSignature6":
				return oInscriptionMedicale.inscriptionSignature6;
			case "inscriptionSignature7":
				return oInscriptionMedicale.inscriptionSignature7;
			case "inscriptionSignature8":
				return oInscriptionMedicale.inscriptionSignature8;
			case "inscriptionSignature9":
				return oInscriptionMedicale.inscriptionSignature9;
			case "inscriptionSignature10":
				return oInscriptionMedicale.inscriptionSignature10;
			case "inscriptionDate1":
				return oInscriptionMedicale.inscriptionDate1;
			case "inscriptionDate2":
				return oInscriptionMedicale.inscriptionDate2;
			case "inscriptionDate3":
				return oInscriptionMedicale.inscriptionDate3;
			case "inscriptionDate4":
				return oInscriptionMedicale.inscriptionDate4;
			case "inscriptionDate5":
				return oInscriptionMedicale.inscriptionDate5;
			case "inscriptionDate6":
				return oInscriptionMedicale.inscriptionDate6;
			case "inscriptionDate7":
				return oInscriptionMedicale.inscriptionDate7;
			case "inscriptionDate8":
				return oInscriptionMedicale.inscriptionDate8;
			case "inscriptionDate9":
				return oInscriptionMedicale.inscriptionDate9;
			case "inscriptionDate10":
				return oInscriptionMedicale.inscriptionDate10;
			case "inscriptionsInscription":
				return oInscriptionMedicale.inscriptionsInscription;
			case "inscriptionNumero":
				return oInscriptionMedicale.inscriptionNumero;
			case "inscriptionNomComplet":
				return oInscriptionMedicale.inscriptionNomComplet;
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
				o = attribuerInscriptionMedicale(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attribuerPourClasse(v, val);
			}
		}
		return o != null;
	}
	public Object attribuerInscriptionMedicale(String var, Object val) {
		InscriptionMedicale oInscriptionMedicale = (InscriptionMedicale)this;
		switch(var) {
			case "cliniqueCle":
				oInscriptionMedicale.setCliniqueCle((Long)val);
				if(!sauvegardes.contains(var))
					sauvegardes.add(var);
				return val;
			case "patientCle":
				oInscriptionMedicale.setPatientCle((Long)val);
				if(!sauvegardes.contains(var))
					sauvegardes.add(var);
				return val;
			case "utilisateurCles":
				oInscriptionMedicale.addUtilisateurCles((Long)val);
				if(!sauvegardes.contains(var))
					sauvegardes.add(var);
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
					o = definirInscriptionMedicale(v, val);
				else if(o instanceof Cluster) {
					Cluster cluster = (Cluster)o;
					o = cluster.definirPourClasse(v, val);
				}
			}
		}
		return o != null;
	}
	public Object definirInscriptionMedicale(String var, String val) {
		switch(var) {
			case "patientNomComplet":
				if(val != null)
					setPatientNomComplet(val);
				sauvegardes.add(var);
				return val;
			case "patientNomCompletPrefere":
				if(val != null)
					setPatientNomCompletPrefere(val);
				sauvegardes.add(var);
				return val;
			case "patientDateNaissance":
				if(val != null)
					setPatientDateNaissance(val);
				sauvegardes.add(var);
				return val;
			case "cliniqueAddresse":
				if(val != null)
					setCliniqueAddresse(val);
				sauvegardes.add(var);
				return val;
			case "inscriptionApprouve":
				if(val != null)
					setInscriptionApprouve(val);
				sauvegardes.add(var);
				return val;
			case "inscriptionImmunisations":
				if(val != null)
					setInscriptionImmunisations(val);
				sauvegardes.add(var);
				return val;
			case "familleAddresse":
				if(val != null)
					setFamilleAddresse(val);
				sauvegardes.add(var);
				return val;
			case "familleCommentVousConnaissezClinique":
				if(val != null)
					setFamilleCommentVousConnaissezClinique(val);
				sauvegardes.add(var);
				return val;
			case "inscriptionConsiderationsSpeciales":
				if(val != null)
					setInscriptionConsiderationsSpeciales(val);
				sauvegardes.add(var);
				return val;
			case "patientConditionsMedicales":
				if(val != null)
					setPatientConditionsMedicales(val);
				sauvegardes.add(var);
				return val;
			case "patientCliniquesPrecedemmentFrequentees":
				if(val != null)
					setPatientCliniquesPrecedemmentFrequentees(val);
				sauvegardes.add(var);
				return val;
			case "patientDescription":
				if(val != null)
					setPatientDescription(val);
				sauvegardes.add(var);
				return val;
			case "patientObjectifs":
				if(val != null)
					setPatientObjectifs(val);
				sauvegardes.add(var);
				return val;
			case "customerProfileId":
				if(val != null)
					setCustomerProfileId(val);
				sauvegardes.add(var);
				return val;
			case "inscriptionSignature1":
				if(val != null)
					setInscriptionSignature1(val);
				sauvegardes.add(var);
				return val;
			case "inscriptionSignature2":
				if(val != null)
					setInscriptionSignature2(val);
				sauvegardes.add(var);
				return val;
			case "inscriptionSignature3":
				if(val != null)
					setInscriptionSignature3(val);
				sauvegardes.add(var);
				return val;
			case "inscriptionSignature4":
				if(val != null)
					setInscriptionSignature4(val);
				sauvegardes.add(var);
				return val;
			case "inscriptionSignature5":
				if(val != null)
					setInscriptionSignature5(val);
				sauvegardes.add(var);
				return val;
			case "inscriptionSignature6":
				if(val != null)
					setInscriptionSignature6(val);
				sauvegardes.add(var);
				return val;
			case "inscriptionSignature7":
				if(val != null)
					setInscriptionSignature7(val);
				sauvegardes.add(var);
				return val;
			case "inscriptionSignature8":
				if(val != null)
					setInscriptionSignature8(val);
				sauvegardes.add(var);
				return val;
			case "inscriptionSignature9":
				if(val != null)
					setInscriptionSignature9(val);
				sauvegardes.add(var);
				return val;
			case "inscriptionSignature10":
				if(val != null)
					setInscriptionSignature10(val);
				sauvegardes.add(var);
				return val;
			case "inscriptionDate1":
				if(val != null)
					setInscriptionDate1(val);
				sauvegardes.add(var);
				return val;
			case "inscriptionDate2":
				if(val != null)
					setInscriptionDate2(val);
				sauvegardes.add(var);
				return val;
			case "inscriptionDate3":
				if(val != null)
					setInscriptionDate3(val);
				sauvegardes.add(var);
				return val;
			case "inscriptionDate4":
				if(val != null)
					setInscriptionDate4(val);
				sauvegardes.add(var);
				return val;
			case "inscriptionDate5":
				if(val != null)
					setInscriptionDate5(val);
				sauvegardes.add(var);
				return val;
			case "inscriptionDate6":
				if(val != null)
					setInscriptionDate6(val);
				sauvegardes.add(var);
				return val;
			case "inscriptionDate7":
				if(val != null)
					setInscriptionDate7(val);
				sauvegardes.add(var);
				return val;
			case "inscriptionDate8":
				if(val != null)
					setInscriptionDate8(val);
				sauvegardes.add(var);
				return val;
			case "inscriptionDate9":
				if(val != null)
					setInscriptionDate9(val);
				sauvegardes.add(var);
				return val;
			case "inscriptionDate10":
				if(val != null)
					setInscriptionDate10(val);
				sauvegardes.add(var);
				return val;
			default:
				return super.definirCluster(var, val);
		}
	}

	/////////////
	// peupler //
	/////////////

	@Override public void peuplerPourClasse(SolrDocument solrDocument) {
		peuplerInscriptionMedicale(solrDocument);
	}
	public void peuplerInscriptionMedicale(SolrDocument solrDocument) {
		InscriptionMedicale oInscriptionMedicale = (InscriptionMedicale)this;
		sauvegardes = (List<String>)solrDocument.get("sauvegardes_stored_strings");
		if(sauvegardes != null) {

			if(sauvegardes.contains("inscriptionCle")) {
				Long inscriptionCle = (Long)solrDocument.get("inscriptionCle_stored_long");
				if(inscriptionCle != null)
					oInscriptionMedicale.setInscriptionCle(inscriptionCle);
			}

			Long cliniqueCle = (Long)solrDocument.get("cliniqueCle_stored_long");
			if(cliniqueCle != null)
				oInscriptionMedicale.setCliniqueCle(cliniqueCle);

			Long patientCle = (Long)solrDocument.get("patientCle_stored_long");
			if(patientCle != null)
				oInscriptionMedicale.setPatientCle(patientCle);

			if(sauvegardes.contains("formInscriptionCle")) {
				Long formInscriptionCle = (Long)solrDocument.get("formInscriptionCle_stored_long");
				if(formInscriptionCle != null)
					oInscriptionMedicale.setFormInscriptionCle(formInscriptionCle);
			}

			List<Long> utilisateurCles = (List<Long>)solrDocument.get("utilisateurCles_stored_longs");
			if(utilisateurCles != null)
				oInscriptionMedicale.utilisateurCles.addAll(utilisateurCles);

			if(sauvegardes.contains("medicaleTri")) {
				Integer medicaleTri = (Integer)solrDocument.get("medicaleTri_stored_int");
				if(medicaleTri != null)
					oInscriptionMedicale.setMedicaleTri(medicaleTri);
			}

			if(sauvegardes.contains("cliniqueTri")) {
				Integer cliniqueTri = (Integer)solrDocument.get("cliniqueTri_stored_int");
				if(cliniqueTri != null)
					oInscriptionMedicale.setCliniqueTri(cliniqueTri);
			}

			if(sauvegardes.contains("patientPrenom")) {
				String patientPrenom = (String)solrDocument.get("patientPrenom_stored_string");
				if(patientPrenom != null)
					oInscriptionMedicale.setPatientPrenom(patientPrenom);
			}

			if(sauvegardes.contains("patientPrenomPrefere")) {
				String patientPrenomPrefere = (String)solrDocument.get("patientPrenomPrefere_stored_string");
				if(patientPrenomPrefere != null)
					oInscriptionMedicale.setPatientPrenomPrefere(patientPrenomPrefere);
			}

			if(sauvegardes.contains("patientFamilleNom")) {
				String patientFamilleNom = (String)solrDocument.get("patientFamilleNom_stored_string");
				if(patientFamilleNom != null)
					oInscriptionMedicale.setPatientFamilleNom(patientFamilleNom);
			}

			if(sauvegardes.contains("patientNomComplet")) {
				String patientNomComplet = (String)solrDocument.get("patientNomComplet_stored_string");
				if(patientNomComplet != null)
					oInscriptionMedicale.setPatientNomComplet(patientNomComplet);
			}

			if(sauvegardes.contains("patientNomCompletPrefere")) {
				String patientNomCompletPrefere = (String)solrDocument.get("patientNomCompletPrefere_stored_string");
				if(patientNomCompletPrefere != null)
					oInscriptionMedicale.setPatientNomCompletPrefere(patientNomCompletPrefere);
			}

			if(sauvegardes.contains("patientDateNaissance")) {
				Date patientDateNaissance = (Date)solrDocument.get("patientDateNaissance_stored_date");
				if(patientDateNaissance != null)
					oInscriptionMedicale.setPatientDateNaissance(patientDateNaissance);
			}

			if(sauvegardes.contains("patientDateNaissanceDAnnee")) {
				Integer patientDateNaissanceDAnnee = (Integer)solrDocument.get("patientDateNaissanceDAnnee_stored_int");
				if(patientDateNaissanceDAnnee != null)
					oInscriptionMedicale.setPatientDateNaissanceDAnnee(patientDateNaissanceDAnnee);
			}

			if(sauvegardes.contains("patientDateNaissanceMoisDAnnee")) {
				String patientDateNaissanceMoisDAnnee = (String)solrDocument.get("patientDateNaissanceMoisDAnnee_stored_string");
				if(patientDateNaissanceMoisDAnnee != null)
					oInscriptionMedicale.setPatientDateNaissanceMoisDAnnee(patientDateNaissanceMoisDAnnee);
			}

			if(sauvegardes.contains("patientDateNaissanceJourDeSemaine")) {
				String patientDateNaissanceJourDeSemaine = (String)solrDocument.get("patientDateNaissanceJourDeSemaine_stored_string");
				if(patientDateNaissanceJourDeSemaine != null)
					oInscriptionMedicale.setPatientDateNaissanceJourDeSemaine(patientDateNaissanceJourDeSemaine);
			}

			if(sauvegardes.contains("patientMoisNaissance")) {
				Integer patientMoisNaissance = (Integer)solrDocument.get("patientMoisNaissance_stored_int");
				if(patientMoisNaissance != null)
					oInscriptionMedicale.setPatientMoisNaissance(patientMoisNaissance);
			}

			if(sauvegardes.contains("patientJourNaissance")) {
				Integer patientJourNaissance = (Integer)solrDocument.get("patientJourNaissance_stored_int");
				if(patientJourNaissance != null)
					oInscriptionMedicale.setPatientJourNaissance(patientJourNaissance);
			}

			if(sauvegardes.contains("cliniqueNom")) {
				String cliniqueNom = (String)solrDocument.get("cliniqueNom_stored_string");
				if(cliniqueNom != null)
					oInscriptionMedicale.setCliniqueNom(cliniqueNom);
			}

			if(sauvegardes.contains("cliniqueNomComplet")) {
				String cliniqueNomComplet = (String)solrDocument.get("cliniqueNomComplet_stored_string");
				if(cliniqueNomComplet != null)
					oInscriptionMedicale.setCliniqueNomComplet(cliniqueNomComplet);
			}

			if(sauvegardes.contains("cliniqueEmplacement")) {
				String cliniqueEmplacement = (String)solrDocument.get("cliniqueEmplacement_stored_string");
				if(cliniqueEmplacement != null)
					oInscriptionMedicale.setCliniqueEmplacement(cliniqueEmplacement);
			}

			if(sauvegardes.contains("cliniqueAddresse")) {
				String cliniqueAddresse = (String)solrDocument.get("cliniqueAddresse_stored_string");
				if(cliniqueAddresse != null)
					oInscriptionMedicale.setCliniqueAddresse(cliniqueAddresse);
			}

			if(sauvegardes.contains("cliniqueNumeroTelephone")) {
				String cliniqueNumeroTelephone = (String)solrDocument.get("cliniqueNumeroTelephone_stored_string");
				if(cliniqueNumeroTelephone != null)
					oInscriptionMedicale.setCliniqueNumeroTelephone(cliniqueNumeroTelephone);
			}

			if(sauvegardes.contains("cliniqueAdministrateurNom")) {
				String cliniqueAdministrateurNom = (String)solrDocument.get("cliniqueAdministrateurNom_stored_string");
				if(cliniqueAdministrateurNom != null)
					oInscriptionMedicale.setCliniqueAdministrateurNom(cliniqueAdministrateurNom);
			}

			if(sauvegardes.contains("inscriptionApprouve")) {
				Boolean inscriptionApprouve = (Boolean)solrDocument.get("inscriptionApprouve_stored_boolean");
				if(inscriptionApprouve != null)
					oInscriptionMedicale.setInscriptionApprouve(inscriptionApprouve);
			}

			if(sauvegardes.contains("inscriptionImmunisations")) {
				Boolean inscriptionImmunisations = (Boolean)solrDocument.get("inscriptionImmunisations_stored_boolean");
				if(inscriptionImmunisations != null)
					oInscriptionMedicale.setInscriptionImmunisations(inscriptionImmunisations);
			}

			if(sauvegardes.contains("familleAddresse")) {
				String familleAddresse = (String)solrDocument.get("familleAddresse_stored_string");
				if(familleAddresse != null)
					oInscriptionMedicale.setFamilleAddresse(familleAddresse);
			}

			if(sauvegardes.contains("familleCommentVousConnaissezClinique")) {
				String familleCommentVousConnaissezClinique = (String)solrDocument.get("familleCommentVousConnaissezClinique_stored_string");
				if(familleCommentVousConnaissezClinique != null)
					oInscriptionMedicale.setFamilleCommentVousConnaissezClinique(familleCommentVousConnaissezClinique);
			}

			if(sauvegardes.contains("inscriptionConsiderationsSpeciales")) {
				String inscriptionConsiderationsSpeciales = (String)solrDocument.get("inscriptionConsiderationsSpeciales_stored_string");
				if(inscriptionConsiderationsSpeciales != null)
					oInscriptionMedicale.setInscriptionConsiderationsSpeciales(inscriptionConsiderationsSpeciales);
			}

			if(sauvegardes.contains("patientConditionsMedicales")) {
				String patientConditionsMedicales = (String)solrDocument.get("patientConditionsMedicales_stored_string");
				if(patientConditionsMedicales != null)
					oInscriptionMedicale.setPatientConditionsMedicales(patientConditionsMedicales);
			}

			if(sauvegardes.contains("patientCliniquesPrecedemmentFrequentees")) {
				String patientCliniquesPrecedemmentFrequentees = (String)solrDocument.get("patientCliniquesPrecedemmentFrequentees_stored_string");
				if(patientCliniquesPrecedemmentFrequentees != null)
					oInscriptionMedicale.setPatientCliniquesPrecedemmentFrequentees(patientCliniquesPrecedemmentFrequentees);
			}

			if(sauvegardes.contains("patientDescription")) {
				String patientDescription = (String)solrDocument.get("patientDescription_stored_string");
				if(patientDescription != null)
					oInscriptionMedicale.setPatientDescription(patientDescription);
			}

			if(sauvegardes.contains("patientObjectifs")) {
				String patientObjectifs = (String)solrDocument.get("patientObjectifs_stored_string");
				if(patientObjectifs != null)
					oInscriptionMedicale.setPatientObjectifs(patientObjectifs);
			}

			if(sauvegardes.contains("customerProfileId")) {
				String customerProfileId = (String)solrDocument.get("customerProfileId_stored_string");
				if(customerProfileId != null)
					oInscriptionMedicale.setCustomerProfileId(customerProfileId);
			}

			if(sauvegardes.contains("creeDAnnee")) {
				Integer creeDAnnee = (Integer)solrDocument.get("creeDAnnee_stored_int");
				if(creeDAnnee != null)
					oInscriptionMedicale.setCreeDAnnee(creeDAnnee);
			}

			if(sauvegardes.contains("creeJourDeSemaine")) {
				String creeJourDeSemaine = (String)solrDocument.get("creeJourDeSemaine_stored_string");
				if(creeJourDeSemaine != null)
					oInscriptionMedicale.setCreeJourDeSemaine(creeJourDeSemaine);
			}

			if(sauvegardes.contains("creeMoisDAnnee")) {
				String creeMoisDAnnee = (String)solrDocument.get("creeMoisDAnnee_stored_string");
				if(creeMoisDAnnee != null)
					oInscriptionMedicale.setCreeMoisDAnnee(creeMoisDAnnee);
			}

			if(sauvegardes.contains("creeHeureDuJour")) {
				String creeHeureDuJour = (String)solrDocument.get("creeHeureDuJour_stored_string");
				if(creeHeureDuJour != null)
					oInscriptionMedicale.setCreeHeureDuJour(creeHeureDuJour);
			}

			if(sauvegardes.contains("inscriptionSignature1")) {
				String inscriptionSignature1 = (String)solrDocument.get("inscriptionSignature1_stored_string");
				if(inscriptionSignature1 != null)
					oInscriptionMedicale.setInscriptionSignature1(inscriptionSignature1);
			}

			if(sauvegardes.contains("inscriptionSignature2")) {
				String inscriptionSignature2 = (String)solrDocument.get("inscriptionSignature2_stored_string");
				if(inscriptionSignature2 != null)
					oInscriptionMedicale.setInscriptionSignature2(inscriptionSignature2);
			}

			if(sauvegardes.contains("inscriptionSignature3")) {
				String inscriptionSignature3 = (String)solrDocument.get("inscriptionSignature3_stored_string");
				if(inscriptionSignature3 != null)
					oInscriptionMedicale.setInscriptionSignature3(inscriptionSignature3);
			}

			if(sauvegardes.contains("inscriptionSignature4")) {
				String inscriptionSignature4 = (String)solrDocument.get("inscriptionSignature4_stored_string");
				if(inscriptionSignature4 != null)
					oInscriptionMedicale.setInscriptionSignature4(inscriptionSignature4);
			}

			if(sauvegardes.contains("inscriptionSignature5")) {
				String inscriptionSignature5 = (String)solrDocument.get("inscriptionSignature5_stored_string");
				if(inscriptionSignature5 != null)
					oInscriptionMedicale.setInscriptionSignature5(inscriptionSignature5);
			}

			if(sauvegardes.contains("inscriptionSignature6")) {
				String inscriptionSignature6 = (String)solrDocument.get("inscriptionSignature6_stored_string");
				if(inscriptionSignature6 != null)
					oInscriptionMedicale.setInscriptionSignature6(inscriptionSignature6);
			}

			if(sauvegardes.contains("inscriptionSignature7")) {
				String inscriptionSignature7 = (String)solrDocument.get("inscriptionSignature7_stored_string");
				if(inscriptionSignature7 != null)
					oInscriptionMedicale.setInscriptionSignature7(inscriptionSignature7);
			}

			if(sauvegardes.contains("inscriptionSignature8")) {
				String inscriptionSignature8 = (String)solrDocument.get("inscriptionSignature8_stored_string");
				if(inscriptionSignature8 != null)
					oInscriptionMedicale.setInscriptionSignature8(inscriptionSignature8);
			}

			if(sauvegardes.contains("inscriptionSignature9")) {
				String inscriptionSignature9 = (String)solrDocument.get("inscriptionSignature9_stored_string");
				if(inscriptionSignature9 != null)
					oInscriptionMedicale.setInscriptionSignature9(inscriptionSignature9);
			}

			if(sauvegardes.contains("inscriptionSignature10")) {
				String inscriptionSignature10 = (String)solrDocument.get("inscriptionSignature10_stored_string");
				if(inscriptionSignature10 != null)
					oInscriptionMedicale.setInscriptionSignature10(inscriptionSignature10);
			}

			if(sauvegardes.contains("inscriptionDate1")) {
				Date inscriptionDate1 = (Date)solrDocument.get("inscriptionDate1_stored_date");
				if(inscriptionDate1 != null)
					oInscriptionMedicale.setInscriptionDate1(inscriptionDate1);
			}

			if(sauvegardes.contains("inscriptionDate2")) {
				Date inscriptionDate2 = (Date)solrDocument.get("inscriptionDate2_stored_date");
				if(inscriptionDate2 != null)
					oInscriptionMedicale.setInscriptionDate2(inscriptionDate2);
			}

			if(sauvegardes.contains("inscriptionDate3")) {
				Date inscriptionDate3 = (Date)solrDocument.get("inscriptionDate3_stored_date");
				if(inscriptionDate3 != null)
					oInscriptionMedicale.setInscriptionDate3(inscriptionDate3);
			}

			if(sauvegardes.contains("inscriptionDate4")) {
				Date inscriptionDate4 = (Date)solrDocument.get("inscriptionDate4_stored_date");
				if(inscriptionDate4 != null)
					oInscriptionMedicale.setInscriptionDate4(inscriptionDate4);
			}

			if(sauvegardes.contains("inscriptionDate5")) {
				Date inscriptionDate5 = (Date)solrDocument.get("inscriptionDate5_stored_date");
				if(inscriptionDate5 != null)
					oInscriptionMedicale.setInscriptionDate5(inscriptionDate5);
			}

			if(sauvegardes.contains("inscriptionDate6")) {
				Date inscriptionDate6 = (Date)solrDocument.get("inscriptionDate6_stored_date");
				if(inscriptionDate6 != null)
					oInscriptionMedicale.setInscriptionDate6(inscriptionDate6);
			}

			if(sauvegardes.contains("inscriptionDate7")) {
				Date inscriptionDate7 = (Date)solrDocument.get("inscriptionDate7_stored_date");
				if(inscriptionDate7 != null)
					oInscriptionMedicale.setInscriptionDate7(inscriptionDate7);
			}

			if(sauvegardes.contains("inscriptionDate8")) {
				Date inscriptionDate8 = (Date)solrDocument.get("inscriptionDate8_stored_date");
				if(inscriptionDate8 != null)
					oInscriptionMedicale.setInscriptionDate8(inscriptionDate8);
			}

			if(sauvegardes.contains("inscriptionDate9")) {
				Date inscriptionDate9 = (Date)solrDocument.get("inscriptionDate9_stored_date");
				if(inscriptionDate9 != null)
					oInscriptionMedicale.setInscriptionDate9(inscriptionDate9);
			}

			if(sauvegardes.contains("inscriptionDate10")) {
				Date inscriptionDate10 = (Date)solrDocument.get("inscriptionDate10_stored_date");
				if(inscriptionDate10 != null)
					oInscriptionMedicale.setInscriptionDate10(inscriptionDate10);
			}

			if(sauvegardes.contains("inscriptionNomComplet")) {
				String inscriptionNomComplet = (String)solrDocument.get("inscriptionNomComplet_stored_string");
				if(inscriptionNomComplet != null)
					oInscriptionMedicale.setInscriptionNomComplet(inscriptionNomComplet);
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
			rechercheSolr.addFilterQuery("id:" + ClientUtils.escapeQueryChars("org.computate.medicale.frFR.inscription.InscriptionMedicale"));
			QueryResponse reponseRecherche = requeteSite.getSiteContexte_().getClientSolr().query(rechercheSolr);
			if(reponseRecherche.getResults().size() > 0)
				requeteSite.setDocumentSolr(reponseRecherche.getResults().get(0));
			InscriptionMedicale o = new InscriptionMedicale();
			o.requeteSiteInscriptionMedicale(requeteSite);
			o.initLoinInscriptionMedicale(requeteSite);
			o.indexerInscriptionMedicale();
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}


	@Override public void indexerPourClasse() {
		indexerInscriptionMedicale();
	}

	@Override public void indexerPourClasse(SolrInputDocument document) {
		indexerInscriptionMedicale(document);
	}

	public void indexerInscriptionMedicale(SolrClient clientSolr) {
		try {
			SolrInputDocument document = new SolrInputDocument();
			indexerInscriptionMedicale(document);
			clientSolr.add(document);
			clientSolr.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public void indexerInscriptionMedicale() {
		try {
			SolrInputDocument document = new SolrInputDocument();
			indexerInscriptionMedicale(document);
			SolrClient clientSolr = requeteSite_.getSiteContexte_().getClientSolr();
			clientSolr.add(document);
			clientSolr.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public void indexerInscriptionMedicale(SolrInputDocument document) {
		if(inscriptionCle != null) {
			document.addField("inscriptionCle_indexed_long", inscriptionCle);
			document.addField("inscriptionCle_stored_long", inscriptionCle);
		}
		if(cliniqueCle != null) {
			document.addField("cliniqueCle_indexed_long", cliniqueCle);
			document.addField("cliniqueCle_stored_long", cliniqueCle);
		}
		if(patientCle != null) {
			document.addField("patientCle_indexed_long", patientCle);
			document.addField("patientCle_stored_long", patientCle);
		}
		if(formInscriptionCle != null) {
			document.addField("formInscriptionCle_indexed_long", formInscriptionCle);
			document.addField("formInscriptionCle_stored_long", formInscriptionCle);
		}
		if(utilisateurCles != null) {
			for(java.lang.Long o : utilisateurCles) {
				document.addField("utilisateurCles_indexed_longs", o);
			}
			for(java.lang.Long o : utilisateurCles) {
				document.addField("utilisateurCles_stored_longs", o);
			}
		}
		if(medicaleTri != null) {
			document.addField("medicaleTri_indexed_int", medicaleTri);
			document.addField("medicaleTri_stored_int", medicaleTri);
		}
		if(cliniqueTri != null) {
			document.addField("cliniqueTri_indexed_int", cliniqueTri);
			document.addField("cliniqueTri_stored_int", cliniqueTri);
		}
		if(patientPrenom != null) {
			document.addField("patientPrenom_indexed_string", patientPrenom);
			document.addField("patientPrenom_stored_string", patientPrenom);
		}
		if(patientPrenomPrefere != null) {
			document.addField("patientPrenomPrefere_indexed_string", patientPrenomPrefere);
			document.addField("patientPrenomPrefere_stored_string", patientPrenomPrefere);
		}
		if(patientFamilleNom != null) {
			document.addField("patientFamilleNom_indexed_string", patientFamilleNom);
			document.addField("patientFamilleNom_stored_string", patientFamilleNom);
		}
		if(patientNomComplet != null) {
			document.addField("patientNomComplet_indexed_string", patientNomComplet);
			document.addField("patientNomComplet_stored_string", patientNomComplet);
		}
		if(patientNomCompletPrefere != null) {
			document.addField("patientNomCompletPrefere_indexed_string", patientNomCompletPrefere);
			document.addField("patientNomCompletPrefere_stored_string", patientNomCompletPrefere);
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
		if(patientMoisNaissance != null) {
			document.addField("patientMoisNaissance_indexed_int", patientMoisNaissance);
			document.addField("patientMoisNaissance_stored_int", patientMoisNaissance);
		}
		if(patientJourNaissance != null) {
			document.addField("patientJourNaissance_indexed_int", patientJourNaissance);
			document.addField("patientJourNaissance_stored_int", patientJourNaissance);
		}
		if(cliniqueNom != null) {
			document.addField("cliniqueNom_indexed_string", cliniqueNom);
			document.addField("cliniqueNom_stored_string", cliniqueNom);
		}
		if(cliniqueNomComplet != null) {
			document.addField("cliniqueNomComplet_indexed_string", cliniqueNomComplet);
			document.addField("cliniqueNomComplet_stored_string", cliniqueNomComplet);
		}
		if(cliniqueEmplacement != null) {
			document.addField("cliniqueEmplacement_indexed_string", cliniqueEmplacement);
			document.addField("cliniqueEmplacement_stored_string", cliniqueEmplacement);
		}
		if(cliniqueAddresse != null) {
			document.addField("cliniqueAddresse_indexed_string", cliniqueAddresse);
			document.addField("cliniqueAddresse_stored_string", cliniqueAddresse);
		}
		if(cliniqueNumeroTelephone != null) {
			document.addField("cliniqueNumeroTelephone_indexed_string", cliniqueNumeroTelephone);
			document.addField("cliniqueNumeroTelephone_stored_string", cliniqueNumeroTelephone);
		}
		if(cliniqueAdministrateurNom != null) {
			document.addField("cliniqueAdministrateurNom_indexed_string", cliniqueAdministrateurNom);
			document.addField("cliniqueAdministrateurNom_stored_string", cliniqueAdministrateurNom);
		}
		if(inscriptionApprouve != null) {
			document.addField("inscriptionApprouve_indexed_boolean", inscriptionApprouve);
			document.addField("inscriptionApprouve_stored_boolean", inscriptionApprouve);
		}
		if(inscriptionImmunisations != null) {
			document.addField("inscriptionImmunisations_indexed_boolean", inscriptionImmunisations);
			document.addField("inscriptionImmunisations_stored_boolean", inscriptionImmunisations);
		}
		if(familleAddresse != null) {
			document.addField("familleAddresse_indexed_string", familleAddresse);
			document.addField("familleAddresse_stored_string", familleAddresse);
		}
		if(familleCommentVousConnaissezClinique != null) {
			document.addField("familleCommentVousConnaissezClinique_indexed_string", familleCommentVousConnaissezClinique);
			document.addField("familleCommentVousConnaissezClinique_stored_string", familleCommentVousConnaissezClinique);
		}
		if(inscriptionConsiderationsSpeciales != null) {
			document.addField("inscriptionConsiderationsSpeciales_indexed_string", inscriptionConsiderationsSpeciales);
			document.addField("inscriptionConsiderationsSpeciales_stored_string", inscriptionConsiderationsSpeciales);
		}
		if(patientConditionsMedicales != null) {
			document.addField("patientConditionsMedicales_text_frFR", patientConditionsMedicales.toString());
			document.addField("patientConditionsMedicales_indexed_string", patientConditionsMedicales);
			document.addField("patientConditionsMedicales_stored_string", patientConditionsMedicales);
		}
		if(patientCliniquesPrecedemmentFrequentees != null) {
			document.addField("patientCliniquesPrecedemmentFrequentees_indexed_string", patientCliniquesPrecedemmentFrequentees);
			document.addField("patientCliniquesPrecedemmentFrequentees_stored_string", patientCliniquesPrecedemmentFrequentees);
		}
		if(patientDescription != null) {
			document.addField("patientDescription_indexed_string", patientDescription);
			document.addField("patientDescription_stored_string", patientDescription);
		}
		if(patientObjectifs != null) {
			document.addField("patientObjectifs_indexed_string", patientObjectifs);
			document.addField("patientObjectifs_stored_string", patientObjectifs);
		}
		if(customerProfileId != null) {
			document.addField("customerProfileId_indexed_string", customerProfileId);
			document.addField("customerProfileId_stored_string", customerProfileId);
		}
		if(creeDAnnee != null) {
			document.addField("creeDAnnee_indexed_int", creeDAnnee);
			document.addField("creeDAnnee_stored_int", creeDAnnee);
		}
		if(creeJourDeSemaine != null) {
			document.addField("creeJourDeSemaine_indexed_string", creeJourDeSemaine);
			document.addField("creeJourDeSemaine_stored_string", creeJourDeSemaine);
		}
		if(creeMoisDAnnee != null) {
			document.addField("creeMoisDAnnee_indexed_string", creeMoisDAnnee);
			document.addField("creeMoisDAnnee_stored_string", creeMoisDAnnee);
		}
		if(creeHeureDuJour != null) {
			document.addField("creeHeureDuJour_indexed_string", creeHeureDuJour);
			document.addField("creeHeureDuJour_stored_string", creeHeureDuJour);
		}
		if(inscriptionSignature1 != null) {
			document.addField("inscriptionSignature1_stored_string", inscriptionSignature1);
		}
		if(inscriptionSignature2 != null) {
			document.addField("inscriptionSignature2_stored_string", inscriptionSignature2);
		}
		if(inscriptionSignature3 != null) {
			document.addField("inscriptionSignature3_stored_string", inscriptionSignature3);
		}
		if(inscriptionSignature4 != null) {
			document.addField("inscriptionSignature4_stored_string", inscriptionSignature4);
		}
		if(inscriptionSignature5 != null) {
			document.addField("inscriptionSignature5_stored_string", inscriptionSignature5);
		}
		if(inscriptionSignature6 != null) {
			document.addField("inscriptionSignature6_stored_string", inscriptionSignature6);
		}
		if(inscriptionSignature7 != null) {
			document.addField("inscriptionSignature7_stored_string", inscriptionSignature7);
		}
		if(inscriptionSignature8 != null) {
			document.addField("inscriptionSignature8_stored_string", inscriptionSignature8);
		}
		if(inscriptionSignature9 != null) {
			document.addField("inscriptionSignature9_stored_string", inscriptionSignature9);
		}
		if(inscriptionSignature10 != null) {
			document.addField("inscriptionSignature10_stored_string", inscriptionSignature10);
		}
		if(inscriptionDate1 != null) {
			document.addField("inscriptionDate1_indexed_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(inscriptionDate1.atStartOfDay(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
			document.addField("inscriptionDate1_stored_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(inscriptionDate1.atStartOfDay(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
		}
		if(inscriptionDate2 != null) {
			document.addField("inscriptionDate2_indexed_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(inscriptionDate2.atStartOfDay(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
			document.addField("inscriptionDate2_stored_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(inscriptionDate2.atStartOfDay(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
		}
		if(inscriptionDate3 != null) {
			document.addField("inscriptionDate3_indexed_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(inscriptionDate3.atStartOfDay(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
			document.addField("inscriptionDate3_stored_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(inscriptionDate3.atStartOfDay(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
		}
		if(inscriptionDate4 != null) {
			document.addField("inscriptionDate4_indexed_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(inscriptionDate4.atStartOfDay(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
			document.addField("inscriptionDate4_stored_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(inscriptionDate4.atStartOfDay(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
		}
		if(inscriptionDate5 != null) {
			document.addField("inscriptionDate5_indexed_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(inscriptionDate5.atStartOfDay(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
			document.addField("inscriptionDate5_stored_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(inscriptionDate5.atStartOfDay(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
		}
		if(inscriptionDate6 != null) {
			document.addField("inscriptionDate6_indexed_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(inscriptionDate6.atStartOfDay(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
			document.addField("inscriptionDate6_stored_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(inscriptionDate6.atStartOfDay(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
		}
		if(inscriptionDate7 != null) {
			document.addField("inscriptionDate7_indexed_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(inscriptionDate7.atStartOfDay(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
			document.addField("inscriptionDate7_stored_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(inscriptionDate7.atStartOfDay(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
		}
		if(inscriptionDate8 != null) {
			document.addField("inscriptionDate8_indexed_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(inscriptionDate8.atStartOfDay(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
			document.addField("inscriptionDate8_stored_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(inscriptionDate8.atStartOfDay(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
		}
		if(inscriptionDate9 != null) {
			document.addField("inscriptionDate9_indexed_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(inscriptionDate9.atStartOfDay(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
			document.addField("inscriptionDate9_stored_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(inscriptionDate9.atStartOfDay(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
		}
		if(inscriptionDate10 != null) {
			document.addField("inscriptionDate10_indexed_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(inscriptionDate10.atStartOfDay(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
			document.addField("inscriptionDate10_stored_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(inscriptionDate10.atStartOfDay(ZoneId.of(requeteSite_.getConfigSite_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
		}
		if(inscriptionNomComplet != null) {
			document.addField("inscriptionNomComplet_indexed_string", inscriptionNomComplet);
			document.addField("inscriptionNomComplet_stored_string", inscriptionNomComplet);
		}
		super.indexerCluster(document);

	}

	public void desindexerInscriptionMedicale() {
		try {
		RequeteSiteFrFR requeteSite = new RequeteSiteFrFR();
			requeteSite.initLoinRequeteSiteFrFR();
			SiteContexteFrFR siteContexte = new SiteContexteFrFR();
			siteContexte.initLoinSiteContexteFrFR();
			requeteSite.setSiteContexte_(siteContexte);
			requeteSite.setConfigSite_(siteContexte.getConfigSite());
			initLoinInscriptionMedicale(requeteSite);
			SolrClient clientSolr = siteContexte.getClientSolr();
			clientSolr.deleteById(id.toString());
			clientSolr.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public static String varIndexeInscriptionMedicale(String entiteVar) {
		switch(entiteVar) {
			case "inscriptionCle":
				return "inscriptionCle_indexed_long";
			case "cliniqueCle":
				return "cliniqueCle_indexed_long";
			case "patientCle":
				return "patientCle_indexed_long";
			case "formInscriptionCle":
				return "formInscriptionCle_indexed_long";
			case "utilisateurCles":
				return "utilisateurCles_indexed_longs";
			case "medicaleTri":
				return "medicaleTri_indexed_int";
			case "cliniqueTri":
				return "cliniqueTri_indexed_int";
			case "patientPrenom":
				return "patientPrenom_indexed_string";
			case "patientPrenomPrefere":
				return "patientPrenomPrefere_indexed_string";
			case "patientFamilleNom":
				return "patientFamilleNom_indexed_string";
			case "patientNomComplet":
				return "patientNomComplet_indexed_string";
			case "patientNomCompletPrefere":
				return "patientNomCompletPrefere_indexed_string";
			case "patientDateNaissance":
				return "patientDateNaissance_indexed_date";
			case "patientDateNaissanceDAnnee":
				return "patientDateNaissanceDAnnee_indexed_int";
			case "patientDateNaissanceMoisDAnnee":
				return "patientDateNaissanceMoisDAnnee_indexed_string";
			case "patientDateNaissanceJourDeSemaine":
				return "patientDateNaissanceJourDeSemaine_indexed_string";
			case "patientMoisNaissance":
				return "patientMoisNaissance_indexed_int";
			case "patientJourNaissance":
				return "patientJourNaissance_indexed_int";
			case "cliniqueNom":
				return "cliniqueNom_indexed_string";
			case "cliniqueNomComplet":
				return "cliniqueNomComplet_indexed_string";
			case "cliniqueEmplacement":
				return "cliniqueEmplacement_indexed_string";
			case "cliniqueAddresse":
				return "cliniqueAddresse_indexed_string";
			case "cliniqueNumeroTelephone":
				return "cliniqueNumeroTelephone_indexed_string";
			case "cliniqueAdministrateurNom":
				return "cliniqueAdministrateurNom_indexed_string";
			case "inscriptionApprouve":
				return "inscriptionApprouve_indexed_boolean";
			case "inscriptionImmunisations":
				return "inscriptionImmunisations_indexed_boolean";
			case "familleAddresse":
				return "familleAddresse_indexed_string";
			case "familleCommentVousConnaissezClinique":
				return "familleCommentVousConnaissezClinique_indexed_string";
			case "inscriptionConsiderationsSpeciales":
				return "inscriptionConsiderationsSpeciales_indexed_string";
			case "patientConditionsMedicales":
				return "patientConditionsMedicales_indexed_string";
			case "patientCliniquesPrecedemmentFrequentees":
				return "patientCliniquesPrecedemmentFrequentees_indexed_string";
			case "patientDescription":
				return "patientDescription_indexed_string";
			case "patientObjectifs":
				return "patientObjectifs_indexed_string";
			case "customerProfileId":
				return "customerProfileId_indexed_string";
			case "creeDAnnee":
				return "creeDAnnee_indexed_int";
			case "creeJourDeSemaine":
				return "creeJourDeSemaine_indexed_string";
			case "creeMoisDAnnee":
				return "creeMoisDAnnee_indexed_string";
			case "creeHeureDuJour":
				return "creeHeureDuJour_indexed_string";
			case "inscriptionDate1":
				return "inscriptionDate1_indexed_date";
			case "inscriptionDate2":
				return "inscriptionDate2_indexed_date";
			case "inscriptionDate3":
				return "inscriptionDate3_indexed_date";
			case "inscriptionDate4":
				return "inscriptionDate4_indexed_date";
			case "inscriptionDate5":
				return "inscriptionDate5_indexed_date";
			case "inscriptionDate6":
				return "inscriptionDate6_indexed_date";
			case "inscriptionDate7":
				return "inscriptionDate7_indexed_date";
			case "inscriptionDate8":
				return "inscriptionDate8_indexed_date";
			case "inscriptionDate9":
				return "inscriptionDate9_indexed_date";
			case "inscriptionDate10":
				return "inscriptionDate10_indexed_date";
			case "inscriptionNomComplet":
				return "inscriptionNomComplet_indexed_string";
			default:
				return Cluster.varIndexeCluster(entiteVar);
		}
	}

	public static String varRechercheInscriptionMedicale(String entiteVar) {
		switch(entiteVar) {
			case "patientConditionsMedicales":
				return "patientConditionsMedicales_text_frFR";
			default:
				return Cluster.varRechercheCluster(entiteVar);
		}
	}

	public static String varSuggereInscriptionMedicale(String entiteVar) {
		switch(entiteVar) {
			default:
				return Cluster.varSuggereCluster(entiteVar);
		}
	}

	/////////////
	// stocker //
	/////////////

	@Override public void stockerPourClasse(SolrDocument solrDocument) {
		stockerInscriptionMedicale(solrDocument);
	}
	public void stockerInscriptionMedicale(SolrDocument solrDocument) {
		InscriptionMedicale oInscriptionMedicale = (InscriptionMedicale)this;

		Long inscriptionCle = (Long)solrDocument.get("inscriptionCle_stored_long");
		if(inscriptionCle != null)
			oInscriptionMedicale.setInscriptionCle(inscriptionCle);

		Long cliniqueCle = (Long)solrDocument.get("cliniqueCle_stored_long");
		if(cliniqueCle != null)
			oInscriptionMedicale.setCliniqueCle(cliniqueCle);

		Long patientCle = (Long)solrDocument.get("patientCle_stored_long");
		if(patientCle != null)
			oInscriptionMedicale.setPatientCle(patientCle);

		Long formInscriptionCle = (Long)solrDocument.get("formInscriptionCle_stored_long");
		if(formInscriptionCle != null)
			oInscriptionMedicale.setFormInscriptionCle(formInscriptionCle);

		List<Long> utilisateurCles = (List<Long>)solrDocument.get("utilisateurCles_stored_longs");
		if(utilisateurCles != null)
			oInscriptionMedicale.utilisateurCles.addAll(utilisateurCles);

		Integer medicaleTri = (Integer)solrDocument.get("medicaleTri_stored_int");
		if(medicaleTri != null)
			oInscriptionMedicale.setMedicaleTri(medicaleTri);

		Integer cliniqueTri = (Integer)solrDocument.get("cliniqueTri_stored_int");
		if(cliniqueTri != null)
			oInscriptionMedicale.setCliniqueTri(cliniqueTri);

		String patientPrenom = (String)solrDocument.get("patientPrenom_stored_string");
		if(patientPrenom != null)
			oInscriptionMedicale.setPatientPrenom(patientPrenom);

		String patientPrenomPrefere = (String)solrDocument.get("patientPrenomPrefere_stored_string");
		if(patientPrenomPrefere != null)
			oInscriptionMedicale.setPatientPrenomPrefere(patientPrenomPrefere);

		String patientFamilleNom = (String)solrDocument.get("patientFamilleNom_stored_string");
		if(patientFamilleNom != null)
			oInscriptionMedicale.setPatientFamilleNom(patientFamilleNom);

		String patientNomComplet = (String)solrDocument.get("patientNomComplet_stored_string");
		if(patientNomComplet != null)
			oInscriptionMedicale.setPatientNomComplet(patientNomComplet);

		String patientNomCompletPrefere = (String)solrDocument.get("patientNomCompletPrefere_stored_string");
		if(patientNomCompletPrefere != null)
			oInscriptionMedicale.setPatientNomCompletPrefere(patientNomCompletPrefere);

		Date patientDateNaissance = (Date)solrDocument.get("patientDateNaissance_stored_date");
		if(patientDateNaissance != null)
			oInscriptionMedicale.setPatientDateNaissance(patientDateNaissance);

		Integer patientDateNaissanceDAnnee = (Integer)solrDocument.get("patientDateNaissanceDAnnee_stored_int");
		if(patientDateNaissanceDAnnee != null)
			oInscriptionMedicale.setPatientDateNaissanceDAnnee(patientDateNaissanceDAnnee);

		String patientDateNaissanceMoisDAnnee = (String)solrDocument.get("patientDateNaissanceMoisDAnnee_stored_string");
		if(patientDateNaissanceMoisDAnnee != null)
			oInscriptionMedicale.setPatientDateNaissanceMoisDAnnee(patientDateNaissanceMoisDAnnee);

		String patientDateNaissanceJourDeSemaine = (String)solrDocument.get("patientDateNaissanceJourDeSemaine_stored_string");
		if(patientDateNaissanceJourDeSemaine != null)
			oInscriptionMedicale.setPatientDateNaissanceJourDeSemaine(patientDateNaissanceJourDeSemaine);

		Integer patientMoisNaissance = (Integer)solrDocument.get("patientMoisNaissance_stored_int");
		if(patientMoisNaissance != null)
			oInscriptionMedicale.setPatientMoisNaissance(patientMoisNaissance);

		Integer patientJourNaissance = (Integer)solrDocument.get("patientJourNaissance_stored_int");
		if(patientJourNaissance != null)
			oInscriptionMedicale.setPatientJourNaissance(patientJourNaissance);

		String cliniqueNom = (String)solrDocument.get("cliniqueNom_stored_string");
		if(cliniqueNom != null)
			oInscriptionMedicale.setCliniqueNom(cliniqueNom);

		String cliniqueNomComplet = (String)solrDocument.get("cliniqueNomComplet_stored_string");
		if(cliniqueNomComplet != null)
			oInscriptionMedicale.setCliniqueNomComplet(cliniqueNomComplet);

		String cliniqueEmplacement = (String)solrDocument.get("cliniqueEmplacement_stored_string");
		if(cliniqueEmplacement != null)
			oInscriptionMedicale.setCliniqueEmplacement(cliniqueEmplacement);

		String cliniqueAddresse = (String)solrDocument.get("cliniqueAddresse_stored_string");
		if(cliniqueAddresse != null)
			oInscriptionMedicale.setCliniqueAddresse(cliniqueAddresse);

		String cliniqueNumeroTelephone = (String)solrDocument.get("cliniqueNumeroTelephone_stored_string");
		if(cliniqueNumeroTelephone != null)
			oInscriptionMedicale.setCliniqueNumeroTelephone(cliniqueNumeroTelephone);

		String cliniqueAdministrateurNom = (String)solrDocument.get("cliniqueAdministrateurNom_stored_string");
		if(cliniqueAdministrateurNom != null)
			oInscriptionMedicale.setCliniqueAdministrateurNom(cliniqueAdministrateurNom);

		Boolean inscriptionApprouve = (Boolean)solrDocument.get("inscriptionApprouve_stored_boolean");
		if(inscriptionApprouve != null)
			oInscriptionMedicale.setInscriptionApprouve(inscriptionApprouve);

		Boolean inscriptionImmunisations = (Boolean)solrDocument.get("inscriptionImmunisations_stored_boolean");
		if(inscriptionImmunisations != null)
			oInscriptionMedicale.setInscriptionImmunisations(inscriptionImmunisations);

		String familleAddresse = (String)solrDocument.get("familleAddresse_stored_string");
		if(familleAddresse != null)
			oInscriptionMedicale.setFamilleAddresse(familleAddresse);

		String familleCommentVousConnaissezClinique = (String)solrDocument.get("familleCommentVousConnaissezClinique_stored_string");
		if(familleCommentVousConnaissezClinique != null)
			oInscriptionMedicale.setFamilleCommentVousConnaissezClinique(familleCommentVousConnaissezClinique);

		String inscriptionConsiderationsSpeciales = (String)solrDocument.get("inscriptionConsiderationsSpeciales_stored_string");
		if(inscriptionConsiderationsSpeciales != null)
			oInscriptionMedicale.setInscriptionConsiderationsSpeciales(inscriptionConsiderationsSpeciales);

		String patientConditionsMedicales = (String)solrDocument.get("patientConditionsMedicales_stored_string");
		if(patientConditionsMedicales != null)
			oInscriptionMedicale.setPatientConditionsMedicales(patientConditionsMedicales);

		String patientCliniquesPrecedemmentFrequentees = (String)solrDocument.get("patientCliniquesPrecedemmentFrequentees_stored_string");
		if(patientCliniquesPrecedemmentFrequentees != null)
			oInscriptionMedicale.setPatientCliniquesPrecedemmentFrequentees(patientCliniquesPrecedemmentFrequentees);

		String patientDescription = (String)solrDocument.get("patientDescription_stored_string");
		if(patientDescription != null)
			oInscriptionMedicale.setPatientDescription(patientDescription);

		String patientObjectifs = (String)solrDocument.get("patientObjectifs_stored_string");
		if(patientObjectifs != null)
			oInscriptionMedicale.setPatientObjectifs(patientObjectifs);

		String customerProfileId = (String)solrDocument.get("customerProfileId_stored_string");
		if(customerProfileId != null)
			oInscriptionMedicale.setCustomerProfileId(customerProfileId);

		Integer creeDAnnee = (Integer)solrDocument.get("creeDAnnee_stored_int");
		if(creeDAnnee != null)
			oInscriptionMedicale.setCreeDAnnee(creeDAnnee);

		String creeJourDeSemaine = (String)solrDocument.get("creeJourDeSemaine_stored_string");
		if(creeJourDeSemaine != null)
			oInscriptionMedicale.setCreeJourDeSemaine(creeJourDeSemaine);

		String creeMoisDAnnee = (String)solrDocument.get("creeMoisDAnnee_stored_string");
		if(creeMoisDAnnee != null)
			oInscriptionMedicale.setCreeMoisDAnnee(creeMoisDAnnee);

		String creeHeureDuJour = (String)solrDocument.get("creeHeureDuJour_stored_string");
		if(creeHeureDuJour != null)
			oInscriptionMedicale.setCreeHeureDuJour(creeHeureDuJour);

		String inscriptionSignature1 = (String)solrDocument.get("inscriptionSignature1_stored_string");
		if(inscriptionSignature1 != null)
			oInscriptionMedicale.setInscriptionSignature1(inscriptionSignature1);

		String inscriptionSignature2 = (String)solrDocument.get("inscriptionSignature2_stored_string");
		if(inscriptionSignature2 != null)
			oInscriptionMedicale.setInscriptionSignature2(inscriptionSignature2);

		String inscriptionSignature3 = (String)solrDocument.get("inscriptionSignature3_stored_string");
		if(inscriptionSignature3 != null)
			oInscriptionMedicale.setInscriptionSignature3(inscriptionSignature3);

		String inscriptionSignature4 = (String)solrDocument.get("inscriptionSignature4_stored_string");
		if(inscriptionSignature4 != null)
			oInscriptionMedicale.setInscriptionSignature4(inscriptionSignature4);

		String inscriptionSignature5 = (String)solrDocument.get("inscriptionSignature5_stored_string");
		if(inscriptionSignature5 != null)
			oInscriptionMedicale.setInscriptionSignature5(inscriptionSignature5);

		String inscriptionSignature6 = (String)solrDocument.get("inscriptionSignature6_stored_string");
		if(inscriptionSignature6 != null)
			oInscriptionMedicale.setInscriptionSignature6(inscriptionSignature6);

		String inscriptionSignature7 = (String)solrDocument.get("inscriptionSignature7_stored_string");
		if(inscriptionSignature7 != null)
			oInscriptionMedicale.setInscriptionSignature7(inscriptionSignature7);

		String inscriptionSignature8 = (String)solrDocument.get("inscriptionSignature8_stored_string");
		if(inscriptionSignature8 != null)
			oInscriptionMedicale.setInscriptionSignature8(inscriptionSignature8);

		String inscriptionSignature9 = (String)solrDocument.get("inscriptionSignature9_stored_string");
		if(inscriptionSignature9 != null)
			oInscriptionMedicale.setInscriptionSignature9(inscriptionSignature9);

		String inscriptionSignature10 = (String)solrDocument.get("inscriptionSignature10_stored_string");
		if(inscriptionSignature10 != null)
			oInscriptionMedicale.setInscriptionSignature10(inscriptionSignature10);

		Date inscriptionDate1 = (Date)solrDocument.get("inscriptionDate1_stored_date");
		if(inscriptionDate1 != null)
			oInscriptionMedicale.setInscriptionDate1(inscriptionDate1);

		Date inscriptionDate2 = (Date)solrDocument.get("inscriptionDate2_stored_date");
		if(inscriptionDate2 != null)
			oInscriptionMedicale.setInscriptionDate2(inscriptionDate2);

		Date inscriptionDate3 = (Date)solrDocument.get("inscriptionDate3_stored_date");
		if(inscriptionDate3 != null)
			oInscriptionMedicale.setInscriptionDate3(inscriptionDate3);

		Date inscriptionDate4 = (Date)solrDocument.get("inscriptionDate4_stored_date");
		if(inscriptionDate4 != null)
			oInscriptionMedicale.setInscriptionDate4(inscriptionDate4);

		Date inscriptionDate5 = (Date)solrDocument.get("inscriptionDate5_stored_date");
		if(inscriptionDate5 != null)
			oInscriptionMedicale.setInscriptionDate5(inscriptionDate5);

		Date inscriptionDate6 = (Date)solrDocument.get("inscriptionDate6_stored_date");
		if(inscriptionDate6 != null)
			oInscriptionMedicale.setInscriptionDate6(inscriptionDate6);

		Date inscriptionDate7 = (Date)solrDocument.get("inscriptionDate7_stored_date");
		if(inscriptionDate7 != null)
			oInscriptionMedicale.setInscriptionDate7(inscriptionDate7);

		Date inscriptionDate8 = (Date)solrDocument.get("inscriptionDate8_stored_date");
		if(inscriptionDate8 != null)
			oInscriptionMedicale.setInscriptionDate8(inscriptionDate8);

		Date inscriptionDate9 = (Date)solrDocument.get("inscriptionDate9_stored_date");
		if(inscriptionDate9 != null)
			oInscriptionMedicale.setInscriptionDate9(inscriptionDate9);

		Date inscriptionDate10 = (Date)solrDocument.get("inscriptionDate10_stored_date");
		if(inscriptionDate10 != null)
			oInscriptionMedicale.setInscriptionDate10(inscriptionDate10);

		String inscriptionNomComplet = (String)solrDocument.get("inscriptionNomComplet_stored_string");
		if(inscriptionNomComplet != null)
			oInscriptionMedicale.setInscriptionNomComplet(inscriptionNomComplet);

		super.stockerCluster(solrDocument);
	}

	//////////////////
	// requeteApi //
	//////////////////

	public void requeteApiInscriptionMedicale() {
		RequeteApi requeteApi = Optional.ofNullable(requeteSite_).map(RequeteSiteFrFR::getRequeteApi_).orElse(null);
		Object o = Optional.ofNullable(requeteApi).map(RequeteApi::getOriginal).orElse(null);
		if(o != null && o instanceof InscriptionMedicale) {
			InscriptionMedicale original = (InscriptionMedicale)o;
			if(!Objects.equals(cliniqueCle, original.getCliniqueCle()))
				requeteApi.addVars("cliniqueCle");
			if(!Objects.equals(patientCle, original.getPatientCle()))
				requeteApi.addVars("patientCle");
			if(!Objects.equals(utilisateurCles, original.getUtilisateurCles()))
				requeteApi.addVars("utilisateurCles");
			if(!Objects.equals(patientNomComplet, original.getPatientNomComplet()))
				requeteApi.addVars("patientNomComplet");
			if(!Objects.equals(patientNomCompletPrefere, original.getPatientNomCompletPrefere()))
				requeteApi.addVars("patientNomCompletPrefere");
			if(!Objects.equals(patientDateNaissance, original.getPatientDateNaissance()))
				requeteApi.addVars("patientDateNaissance");
			if(!Objects.equals(cliniqueAddresse, original.getCliniqueAddresse()))
				requeteApi.addVars("cliniqueAddresse");
			if(!Objects.equals(inscriptionApprouve, original.getInscriptionApprouve()))
				requeteApi.addVars("inscriptionApprouve");
			if(!Objects.equals(inscriptionImmunisations, original.getInscriptionImmunisations()))
				requeteApi.addVars("inscriptionImmunisations");
			if(!Objects.equals(familleAddresse, original.getFamilleAddresse()))
				requeteApi.addVars("familleAddresse");
			if(!Objects.equals(familleCommentVousConnaissezClinique, original.getFamilleCommentVousConnaissezClinique()))
				requeteApi.addVars("familleCommentVousConnaissezClinique");
			if(!Objects.equals(inscriptionConsiderationsSpeciales, original.getInscriptionConsiderationsSpeciales()))
				requeteApi.addVars("inscriptionConsiderationsSpeciales");
			if(!Objects.equals(patientConditionsMedicales, original.getPatientConditionsMedicales()))
				requeteApi.addVars("patientConditionsMedicales");
			if(!Objects.equals(patientCliniquesPrecedemmentFrequentees, original.getPatientCliniquesPrecedemmentFrequentees()))
				requeteApi.addVars("patientCliniquesPrecedemmentFrequentees");
			if(!Objects.equals(patientDescription, original.getPatientDescription()))
				requeteApi.addVars("patientDescription");
			if(!Objects.equals(patientObjectifs, original.getPatientObjectifs()))
				requeteApi.addVars("patientObjectifs");
			if(!Objects.equals(customerProfileId, original.getCustomerProfileId()))
				requeteApi.addVars("customerProfileId");
			if(!Objects.equals(inscriptionSignature1, original.getInscriptionSignature1()))
				requeteApi.addVars("inscriptionSignature1");
			if(!Objects.equals(inscriptionSignature2, original.getInscriptionSignature2()))
				requeteApi.addVars("inscriptionSignature2");
			if(!Objects.equals(inscriptionSignature3, original.getInscriptionSignature3()))
				requeteApi.addVars("inscriptionSignature3");
			if(!Objects.equals(inscriptionSignature4, original.getInscriptionSignature4()))
				requeteApi.addVars("inscriptionSignature4");
			if(!Objects.equals(inscriptionSignature5, original.getInscriptionSignature5()))
				requeteApi.addVars("inscriptionSignature5");
			if(!Objects.equals(inscriptionSignature6, original.getInscriptionSignature6()))
				requeteApi.addVars("inscriptionSignature6");
			if(!Objects.equals(inscriptionSignature7, original.getInscriptionSignature7()))
				requeteApi.addVars("inscriptionSignature7");
			if(!Objects.equals(inscriptionSignature8, original.getInscriptionSignature8()))
				requeteApi.addVars("inscriptionSignature8");
			if(!Objects.equals(inscriptionSignature9, original.getInscriptionSignature9()))
				requeteApi.addVars("inscriptionSignature9");
			if(!Objects.equals(inscriptionSignature10, original.getInscriptionSignature10()))
				requeteApi.addVars("inscriptionSignature10");
			if(!Objects.equals(inscriptionDate1, original.getInscriptionDate1()))
				requeteApi.addVars("inscriptionDate1");
			if(!Objects.equals(inscriptionDate2, original.getInscriptionDate2()))
				requeteApi.addVars("inscriptionDate2");
			if(!Objects.equals(inscriptionDate3, original.getInscriptionDate3()))
				requeteApi.addVars("inscriptionDate3");
			if(!Objects.equals(inscriptionDate4, original.getInscriptionDate4()))
				requeteApi.addVars("inscriptionDate4");
			if(!Objects.equals(inscriptionDate5, original.getInscriptionDate5()))
				requeteApi.addVars("inscriptionDate5");
			if(!Objects.equals(inscriptionDate6, original.getInscriptionDate6()))
				requeteApi.addVars("inscriptionDate6");
			if(!Objects.equals(inscriptionDate7, original.getInscriptionDate7()))
				requeteApi.addVars("inscriptionDate7");
			if(!Objects.equals(inscriptionDate8, original.getInscriptionDate8()))
				requeteApi.addVars("inscriptionDate8");
			if(!Objects.equals(inscriptionDate9, original.getInscriptionDate9()))
				requeteApi.addVars("inscriptionDate9");
			if(!Objects.equals(inscriptionDate10, original.getInscriptionDate10()))
				requeteApi.addVars("inscriptionDate10");
			super.requeteApiCluster();
		}
	}

	//////////////
	// hashCode //
	//////////////

	@Override public int hashCode() {
		return Objects.hash(super.hashCode(), cliniqueCle, patientCle, utilisateurCles, patientNomComplet, patientNomCompletPrefere, patientDateNaissance, cliniqueAddresse, inscriptionApprouve, inscriptionImmunisations, familleAddresse, familleCommentVousConnaissezClinique, inscriptionConsiderationsSpeciales, patientConditionsMedicales, patientCliniquesPrecedemmentFrequentees, patientDescription, patientObjectifs, customerProfileId, inscriptionSignature1, inscriptionSignature2, inscriptionSignature3, inscriptionSignature4, inscriptionSignature5, inscriptionSignature6, inscriptionSignature7, inscriptionSignature8, inscriptionSignature9, inscriptionSignature10, inscriptionDate1, inscriptionDate2, inscriptionDate3, inscriptionDate4, inscriptionDate5, inscriptionDate6, inscriptionDate7, inscriptionDate8, inscriptionDate9, inscriptionDate10);
	}

	////////////
	// equals //
	////////////

	@Override public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof InscriptionMedicale))
			return false;
		InscriptionMedicale that = (InscriptionMedicale)o;
		return super.equals(o)
				&& Objects.equals( cliniqueCle, that.cliniqueCle )
				&& Objects.equals( patientCle, that.patientCle )
				&& Objects.equals( utilisateurCles, that.utilisateurCles )
				&& Objects.equals( patientNomComplet, that.patientNomComplet )
				&& Objects.equals( patientNomCompletPrefere, that.patientNomCompletPrefere )
				&& Objects.equals( patientDateNaissance, that.patientDateNaissance )
				&& Objects.equals( cliniqueAddresse, that.cliniqueAddresse )
				&& Objects.equals( inscriptionApprouve, that.inscriptionApprouve )
				&& Objects.equals( inscriptionImmunisations, that.inscriptionImmunisations )
				&& Objects.equals( familleAddresse, that.familleAddresse )
				&& Objects.equals( familleCommentVousConnaissezClinique, that.familleCommentVousConnaissezClinique )
				&& Objects.equals( inscriptionConsiderationsSpeciales, that.inscriptionConsiderationsSpeciales )
				&& Objects.equals( patientConditionsMedicales, that.patientConditionsMedicales )
				&& Objects.equals( patientCliniquesPrecedemmentFrequentees, that.patientCliniquesPrecedemmentFrequentees )
				&& Objects.equals( patientDescription, that.patientDescription )
				&& Objects.equals( patientObjectifs, that.patientObjectifs )
				&& Objects.equals( customerProfileId, that.customerProfileId )
				&& Objects.equals( inscriptionSignature1, that.inscriptionSignature1 )
				&& Objects.equals( inscriptionSignature2, that.inscriptionSignature2 )
				&& Objects.equals( inscriptionSignature3, that.inscriptionSignature3 )
				&& Objects.equals( inscriptionSignature4, that.inscriptionSignature4 )
				&& Objects.equals( inscriptionSignature5, that.inscriptionSignature5 )
				&& Objects.equals( inscriptionSignature6, that.inscriptionSignature6 )
				&& Objects.equals( inscriptionSignature7, that.inscriptionSignature7 )
				&& Objects.equals( inscriptionSignature8, that.inscriptionSignature8 )
				&& Objects.equals( inscriptionSignature9, that.inscriptionSignature9 )
				&& Objects.equals( inscriptionSignature10, that.inscriptionSignature10 )
				&& Objects.equals( inscriptionDate1, that.inscriptionDate1 )
				&& Objects.equals( inscriptionDate2, that.inscriptionDate2 )
				&& Objects.equals( inscriptionDate3, that.inscriptionDate3 )
				&& Objects.equals( inscriptionDate4, that.inscriptionDate4 )
				&& Objects.equals( inscriptionDate5, that.inscriptionDate5 )
				&& Objects.equals( inscriptionDate6, that.inscriptionDate6 )
				&& Objects.equals( inscriptionDate7, that.inscriptionDate7 )
				&& Objects.equals( inscriptionDate8, that.inscriptionDate8 )
				&& Objects.equals( inscriptionDate9, that.inscriptionDate9 )
				&& Objects.equals( inscriptionDate10, that.inscriptionDate10 );
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("InscriptionMedicale { ");
		sb.append( "cliniqueCle: " ).append(cliniqueCle);
		sb.append( ", patientCle: " ).append(patientCle);
		sb.append( ", utilisateurCles: " ).append(utilisateurCles);
		sb.append( ", patientNomComplet: \"" ).append(patientNomComplet).append( "\"" );
		sb.append( ", patientNomCompletPrefere: \"" ).append(patientNomCompletPrefere).append( "\"" );
		sb.append( ", patientDateNaissance: " ).append(patientDateNaissance);
		sb.append( ", cliniqueAddresse: \"" ).append(cliniqueAddresse).append( "\"" );
		sb.append( ", inscriptionApprouve: " ).append(inscriptionApprouve);
		sb.append( ", inscriptionImmunisations: " ).append(inscriptionImmunisations);
		sb.append( ", familleAddresse: \"" ).append(familleAddresse).append( "\"" );
		sb.append( ", familleCommentVousConnaissezClinique: \"" ).append(familleCommentVousConnaissezClinique).append( "\"" );
		sb.append( ", inscriptionConsiderationsSpeciales: \"" ).append(inscriptionConsiderationsSpeciales).append( "\"" );
		sb.append( ", patientConditionsMedicales: \"" ).append(patientConditionsMedicales).append( "\"" );
		sb.append( ", patientCliniquesPrecedemmentFrequentees: \"" ).append(patientCliniquesPrecedemmentFrequentees).append( "\"" );
		sb.append( ", patientDescription: \"" ).append(patientDescription).append( "\"" );
		sb.append( ", patientObjectifs: \"" ).append(patientObjectifs).append( "\"" );
		sb.append( ", customerProfileId: \"" ).append(customerProfileId).append( "\"" );
		sb.append( ", inscriptionSignature1: \"" ).append(inscriptionSignature1).append( "\"" );
		sb.append( ", inscriptionSignature2: \"" ).append(inscriptionSignature2).append( "\"" );
		sb.append( ", inscriptionSignature3: \"" ).append(inscriptionSignature3).append( "\"" );
		sb.append( ", inscriptionSignature4: \"" ).append(inscriptionSignature4).append( "\"" );
		sb.append( ", inscriptionSignature5: \"" ).append(inscriptionSignature5).append( "\"" );
		sb.append( ", inscriptionSignature6: \"" ).append(inscriptionSignature6).append( "\"" );
		sb.append( ", inscriptionSignature7: \"" ).append(inscriptionSignature7).append( "\"" );
		sb.append( ", inscriptionSignature8: \"" ).append(inscriptionSignature8).append( "\"" );
		sb.append( ", inscriptionSignature9: \"" ).append(inscriptionSignature9).append( "\"" );
		sb.append( ", inscriptionSignature10: \"" ).append(inscriptionSignature10).append( "\"" );
		sb.append( ", inscriptionDate1: " ).append(inscriptionDate1);
		sb.append( ", inscriptionDate2: " ).append(inscriptionDate2);
		sb.append( ", inscriptionDate3: " ).append(inscriptionDate3);
		sb.append( ", inscriptionDate4: " ).append(inscriptionDate4);
		sb.append( ", inscriptionDate5: " ).append(inscriptionDate5);
		sb.append( ", inscriptionDate6: " ).append(inscriptionDate6);
		sb.append( ", inscriptionDate7: " ).append(inscriptionDate7);
		sb.append( ", inscriptionDate8: " ).append(inscriptionDate8);
		sb.append( ", inscriptionDate9: " ).append(inscriptionDate9);
		sb.append( ", inscriptionDate10: " ).append(inscriptionDate10);
		sb.append(" }");
		return sb.toString();
	}
}
