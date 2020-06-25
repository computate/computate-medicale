package org.computate.medicale.frFR.clinique;

import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.computate.medicale.frFR.cluster.Cluster;
import java.util.Date;
import org.computate.medicale.frFR.requete.api.RequeteApi;
import java.util.HashMap;
import org.computate.medicale.frFR.inscription.InscriptionMedicale;
import org.computate.medicale.frFR.contexte.SiteContexteFrFR;
import org.apache.commons.lang3.StringUtils;
import java.lang.Integer;
import io.vertx.core.logging.LoggerFactory;
import java.text.NumberFormat;
import java.util.ArrayList;
import org.apache.commons.collections.CollectionUtils;
import java.lang.Long;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.vertx.core.json.JsonObject;
import java.lang.String;
import io.vertx.core.logging.Logger;
import org.computate.medicale.frFR.couverture.Couverture;
import java.math.MathContext;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.computate.medicale.frFR.ecrivain.ToutEcrivain;
import java.util.Set;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.solr.client.solrj.SolrClient;
import java.util.Objects;
import io.vertx.core.json.JsonArray;
import org.apache.solr.common.SolrDocument;
import java.util.List;
import org.computate.medicale.frFR.requete.RequeteSiteFrFR;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.commons.lang3.math.NumberUtils;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.solr.client.solrj.util.ClientUtils;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import org.apache.solr.common.SolrInputDocument;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**	
 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.clinique.CliniqueMedicale&fq=classeEtendGen_indexed_boolean:true">Trouver la classe clinicCompleteName dans Solr</a>
 * <br/>
 **/
public abstract class CliniqueMedicaleGen<DEV> extends Cluster {
	protected static final Logger LOGGER = LoggerFactory.getLogger(CliniqueMedicale.class);

	public static final List<String> ROLES = Arrays.asList("SiteAdmin");
	public static final List<String> ROLE_READS = Arrays.asList("");

	public static final String CliniqueMedicale_UnNom = "une école";
	public static final String CliniqueMedicale_Ce = "cette ";
	public static final String CliniqueMedicale_CeNom = "cette école";
	public static final String CliniqueMedicale_Un = "une ";
	public static final String CliniqueMedicale_LeNom = "l'école";
	public static final String CliniqueMedicale_NomSingulier = "école";
	public static final String CliniqueMedicale_NomPluriel = "écoles";
	public static final String CliniqueMedicale_NomActuel = "école actuelle";
	public static final String CliniqueMedicale_Tous = "all ";
	public static final String CliniqueMedicale_TousNom = "toutes les écoles";
	public static final String CliniqueMedicale_RechercherTousNomPar = "rechercher écoles par ";
	public static final String CliniqueMedicale_RechercherTousNom = "rechercher écoles";
	public static final String CliniqueMedicale_LesNom = "les écoles";
	public static final String CliniqueMedicale_AucunNomTrouve = "aucune école trouvée";
	public static final String CliniqueMedicale_NomVar = "école";
	public static final String CliniqueMedicale_DeNom = "d'école";
	public static final String CliniqueMedicale_NomAdjectifSingulier = "école";
	public static final String CliniqueMedicale_NomAdjectifPluriel = "écoles";
	public static final String CliniqueMedicale_Couleur = "pink";
	public static final String CliniqueMedicale_IconeGroupe = "regular";
	public static final String CliniqueMedicale_IconeNom = "clinic-medical";

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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.clinique.CliniqueMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:cliniqueCle">Trouver l'entité cliniqueCle dans Solr</a>
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
	public CliniqueMedicale setCliniqueCle(String o) {
		if(NumberUtils.isParsable(o))
			this.cliniqueCle = Long.parseLong(o);
		this.cliniqueCleCouverture.dejaInitialise = true;
		return (CliniqueMedicale)this;
	}
	protected CliniqueMedicale cliniqueCleInit() {
		if(!cliniqueCleCouverture.dejaInitialise) {
			_cliniqueCle(cliniqueCleCouverture);
			if(cliniqueCle == null)
				setCliniqueCle(cliniqueCleCouverture.o);
		}
		cliniqueCleCouverture.dejaInitialise(true);
		return (CliniqueMedicale)this;
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
		return "clé";
	}

	public String htmTooltipCliniqueCle() {
		return null;
	}

	public String htmCliniqueCle() {
		return cliniqueCle == null ? "" : StringEscapeUtils.escapeHtml4(strCliniqueCle());
	}

	/////////////////
	// patientCles //
	/////////////////

	/**	L'entité « patientCles »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> patientCles = new ArrayList<Long>();
	@JsonIgnore
	public Couverture<List<Long>> patientClesCouverture = new Couverture<List<Long>>().p(this).c(List.class).var("patientCles").o(patientCles);

	/**	<br/>L'entité « patientCles »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.clinique.CliniqueMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:patientCles">Trouver l'entité patientCles dans Solr</a>
	 * <br/>
	 * @param patientCles est l'entité déjà construit. 
	 **/
	protected abstract void _patientCles(List<Long> o);

	public List<Long> getPatientCles() {
		return patientCles;
	}

	public void setPatientCles(List<Long> patientCles) {
		this.patientCles = patientCles;
		this.patientClesCouverture.dejaInitialise = true;
	}
	public CliniqueMedicale addPatientCles(Long...objets) {
		for(Long o : objets) {
			addPatientCles(o);
		}
		return (CliniqueMedicale)this;
	}
	public CliniqueMedicale addPatientCles(Long o) {
		if(o != null && !patientCles.contains(o))
			this.patientCles.add(o);
		return (CliniqueMedicale)this;
	}
	public CliniqueMedicale setPatientCles(JsonArray objets) {
		patientCles.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addPatientCles(o);
		}
		return (CliniqueMedicale)this;
	}
	public CliniqueMedicale addPatientCles(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addPatientCles(p);
		}
		return (CliniqueMedicale)this;
	}
	protected CliniqueMedicale patientClesInit() {
		if(!patientClesCouverture.dejaInitialise) {
			_patientCles(patientCles);
		}
		patientClesCouverture.dejaInitialise(true);
		return (CliniqueMedicale)this;
	}

	public List<Long> solrPatientCles() {
		return patientCles;
	}

	public String strPatientCles() {
		return patientCles == null ? "" : patientCles.toString();
	}

	public String jsonPatientCles() {
		return patientCles == null ? "" : patientCles.toString();
	}

	public String nomAffichagePatientCles() {
		return "NomAffichage.enUS: ";
	}

	public String htmTooltipPatientCles() {
		return null;
	}

	public String htmPatientCles() {
		return patientCles == null ? "" : StringEscapeUtils.escapeHtml4(strPatientCles());
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.clinique.CliniqueMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:medicaleTri">Trouver l'entité medicaleTri dans Solr</a>
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
	public CliniqueMedicale setMedicaleTri(String o) {
		if(NumberUtils.isParsable(o))
			this.medicaleTri = Integer.parseInt(o);
		this.medicaleTriCouverture.dejaInitialise = true;
		return (CliniqueMedicale)this;
	}
	protected CliniqueMedicale medicaleTriInit() {
		if(!medicaleTriCouverture.dejaInitialise) {
			_medicaleTri(medicaleTriCouverture);
			if(medicaleTri == null)
				setMedicaleTri(medicaleTriCouverture.o);
		}
		medicaleTriCouverture.dejaInitialise(true);
		return (CliniqueMedicale)this;
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
		return "NomAffichage.enUS: ";
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.clinique.CliniqueMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:cliniqueTri">Trouver l'entité cliniqueTri dans Solr</a>
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
	public CliniqueMedicale setCliniqueTri(String o) {
		if(NumberUtils.isParsable(o))
			this.cliniqueTri = Integer.parseInt(o);
		this.cliniqueTriCouverture.dejaInitialise = true;
		return (CliniqueMedicale)this;
	}
	protected CliniqueMedicale cliniqueTriInit() {
		if(!cliniqueTriCouverture.dejaInitialise) {
			_cliniqueTri(cliniqueTriCouverture);
			if(cliniqueTri == null)
				setCliniqueTri(cliniqueTriCouverture.o);
		}
		cliniqueTriCouverture.dejaInitialise(true);
		return (CliniqueMedicale)this;
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
		return "NomAffichage.enUS: ";
	}

	public String htmTooltipCliniqueTri() {
		return null;
	}

	public String htmCliniqueTri() {
		return cliniqueTri == null ? "" : StringEscapeUtils.escapeHtml4(strCliniqueTri());
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.clinique.CliniqueMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:cliniqueNom">Trouver l'entité cliniqueNom dans Solr</a>
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
	protected CliniqueMedicale cliniqueNomInit() {
		if(!cliniqueNomCouverture.dejaInitialise) {
			_cliniqueNom(cliniqueNomCouverture);
			if(cliniqueNom == null)
				setCliniqueNom(cliniqueNomCouverture.o);
		}
		cliniqueNomCouverture.dejaInitialise(true);
		return (CliniqueMedicale)this;
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
		return "nom de l'école";
	}

	public String htmTooltipCliniqueNom() {
		return null;
	}

	public String htmCliniqueNom() {
		return cliniqueNom == null ? "" : StringEscapeUtils.escapeHtml4(strCliniqueNom());
	}

	public void inputCliniqueNom(String classeApiMethodeMethode) {
		CliniqueMedicale s = (CliniqueMedicale)this;
		{
			e("input")
				.a("type", "text")
				.a("placeholder", "nom de l'école")
				.a("id", classeApiMethodeMethode, "_cliniqueNom");
				if("Page".equals(classeApiMethodeMethode) || "PATCH".equals(classeApiMethodeMethode)) {
					a("class", "setCliniqueNom classCliniqueMedicale inputCliniqueMedicale", pk, "CliniqueNom w3-input w3-border ");
					a("name", "setCliniqueNom");
				} else {
					a("class", "valeurCliniqueNom w3-input w3-border classCliniqueMedicale inputCliniqueMedicale", pk, "CliniqueNom w3-input w3-border ");
					a("name", "cliniqueNom");
				}
				if("Page".equals(classeApiMethodeMethode)) {
					a("onclick", "enleverLueur($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setCliniqueNom', $(this).val(), function() { ajouterLueur($('#", classeApiMethodeMethode, "_cliniqueNom')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_cliniqueNom')); }); ");
				}
				a("value", strCliniqueNom())
			.fg();

		}
	}

	public void htmCliniqueNom(String classeApiMethodeMethode) {
		CliniqueMedicale s = (CliniqueMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "CliniqueMedicaleCliniqueNom").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pink ").f();
							e("label").a("for", classeApiMethodeMethode, "_cliniqueNom").a("class", "").f().sx("nom de l'école").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputCliniqueNom(classeApiMethodeMethode);
							} g("div");
							{
								if("Page".equals(classeApiMethodeMethode)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pink ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_cliniqueNom')); $('#", classeApiMethodeMethode, "_cliniqueNom').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#CliniqueMedicaleForm :input[name=pk]').val() }], 'setCliniqueNom', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_cliniqueNom')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_cliniqueNom')); }); ")
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.clinique.CliniqueMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:cliniqueNumeroTelephone">Trouver l'entité cliniqueNumeroTelephone dans Solr</a>
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
	protected CliniqueMedicale cliniqueNumeroTelephoneInit() {
		if(!cliniqueNumeroTelephoneCouverture.dejaInitialise) {
			_cliniqueNumeroTelephone(cliniqueNumeroTelephoneCouverture);
			if(cliniqueNumeroTelephone == null)
				setCliniqueNumeroTelephone(cliniqueNumeroTelephoneCouverture.o);
		}
		cliniqueNumeroTelephoneCouverture.dejaInitialise(true);
		return (CliniqueMedicale)this;
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

	public void inputCliniqueNumeroTelephone(String classeApiMethodeMethode) {
		CliniqueMedicale s = (CliniqueMedicale)this;
		{
			e("input")
				.a("type", "text")
				.a("placeholder", "numéro de téléphone")
				.a("id", classeApiMethodeMethode, "_cliniqueNumeroTelephone");
				if("Page".equals(classeApiMethodeMethode) || "PATCH".equals(classeApiMethodeMethode)) {
					a("class", "setCliniqueNumeroTelephone classCliniqueMedicale inputCliniqueMedicale", pk, "CliniqueNumeroTelephone w3-input w3-border ");
					a("name", "setCliniqueNumeroTelephone");
				} else {
					a("class", "valeurCliniqueNumeroTelephone w3-input w3-border classCliniqueMedicale inputCliniqueMedicale", pk, "CliniqueNumeroTelephone w3-input w3-border ");
					a("name", "cliniqueNumeroTelephone");
				}
				if("Page".equals(classeApiMethodeMethode)) {
					a("onclick", "enleverLueur($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setCliniqueNumeroTelephone', $(this).val(), function() { ajouterLueur($('#", classeApiMethodeMethode, "_cliniqueNumeroTelephone')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_cliniqueNumeroTelephone')); }); ");
				}
				a("value", strCliniqueNumeroTelephone())
			.fg();

		}
	}

	public void htmCliniqueNumeroTelephone(String classeApiMethodeMethode) {
		CliniqueMedicale s = (CliniqueMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "CliniqueMedicaleCliniqueNumeroTelephone").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pink ").f();
							e("label").a("for", classeApiMethodeMethode, "_cliniqueNumeroTelephone").a("class", "").f().sx("numéro de téléphone").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputCliniqueNumeroTelephone(classeApiMethodeMethode);
							} g("div");
							{
								if("Page".equals(classeApiMethodeMethode)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pink ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_cliniqueNumeroTelephone')); $('#", classeApiMethodeMethode, "_cliniqueNumeroTelephone').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#CliniqueMedicaleForm :input[name=pk]').val() }], 'setCliniqueNumeroTelephone', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_cliniqueNumeroTelephone')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_cliniqueNumeroTelephone')); }); ")
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.clinique.CliniqueMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:cliniqueAdministrateurNom">Trouver l'entité cliniqueAdministrateurNom dans Solr</a>
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
	protected CliniqueMedicale cliniqueAdministrateurNomInit() {
		if(!cliniqueAdministrateurNomCouverture.dejaInitialise) {
			_cliniqueAdministrateurNom(cliniqueAdministrateurNomCouverture);
			if(cliniqueAdministrateurNom == null)
				setCliniqueAdministrateurNom(cliniqueAdministrateurNomCouverture.o);
		}
		cliniqueAdministrateurNomCouverture.dejaInitialise(true);
		return (CliniqueMedicale)this;
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

	public void inputCliniqueAdministrateurNom(String classeApiMethodeMethode) {
		CliniqueMedicale s = (CliniqueMedicale)this;
		{
			e("input")
				.a("type", "text")
				.a("placeholder", "administrateur de l'école")
				.a("id", classeApiMethodeMethode, "_cliniqueAdministrateurNom");
				if("Page".equals(classeApiMethodeMethode) || "PATCH".equals(classeApiMethodeMethode)) {
					a("class", "setCliniqueAdministrateurNom classCliniqueMedicale inputCliniqueMedicale", pk, "CliniqueAdministrateurNom w3-input w3-border ");
					a("name", "setCliniqueAdministrateurNom");
				} else {
					a("class", "valeurCliniqueAdministrateurNom w3-input w3-border classCliniqueMedicale inputCliniqueMedicale", pk, "CliniqueAdministrateurNom w3-input w3-border ");
					a("name", "cliniqueAdministrateurNom");
				}
				if("Page".equals(classeApiMethodeMethode)) {
					a("onclick", "enleverLueur($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setCliniqueAdministrateurNom', $(this).val(), function() { ajouterLueur($('#", classeApiMethodeMethode, "_cliniqueAdministrateurNom')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_cliniqueAdministrateurNom')); }); ");
				}
				a("value", strCliniqueAdministrateurNom())
			.fg();

		}
	}

	public void htmCliniqueAdministrateurNom(String classeApiMethodeMethode) {
		CliniqueMedicale s = (CliniqueMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "CliniqueMedicaleCliniqueAdministrateurNom").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pink ").f();
							e("label").a("for", classeApiMethodeMethode, "_cliniqueAdministrateurNom").a("class", "").f().sx("administrateur de l'école").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputCliniqueAdministrateurNom(classeApiMethodeMethode);
							} g("div");
							{
								if("Page".equals(classeApiMethodeMethode)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pink ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_cliniqueAdministrateurNom')); $('#", classeApiMethodeMethode, "_cliniqueAdministrateurNom').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#CliniqueMedicaleForm :input[name=pk]').val() }], 'setCliniqueAdministrateurNom', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_cliniqueAdministrateurNom')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_cliniqueAdministrateurNom')); }); ")
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

	//////////////////
	// cliniqueMail //
	//////////////////

	/**	L'entité « cliniqueMail »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String cliniqueMail;
	@JsonIgnore
	public Couverture<String> cliniqueMailCouverture = new Couverture<String>().p(this).c(String.class).var("cliniqueMail").o(cliniqueMail);

	/**	<br/>L'entité « cliniqueMail »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.clinique.CliniqueMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:cliniqueMail">Trouver l'entité cliniqueMail dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _cliniqueMail(Couverture<String> c);

	public String getCliniqueMail() {
		return cliniqueMail;
	}

	public void setCliniqueMail(String cliniqueMail) {
		this.cliniqueMail = cliniqueMail;
		this.cliniqueMailCouverture.dejaInitialise = true;
	}
	protected CliniqueMedicale cliniqueMailInit() {
		if(!cliniqueMailCouverture.dejaInitialise) {
			_cliniqueMail(cliniqueMailCouverture);
			if(cliniqueMail == null)
				setCliniqueMail(cliniqueMailCouverture.o);
		}
		cliniqueMailCouverture.dejaInitialise(true);
		return (CliniqueMedicale)this;
	}

	public String solrCliniqueMail() {
		return cliniqueMail;
	}

	public String strCliniqueMail() {
		return cliniqueMail == null ? "" : cliniqueMail;
	}

	public String jsonCliniqueMail() {
		return cliniqueMail == null ? "" : cliniqueMail;
	}

	public String nomAffichageCliniqueMail() {
		return "mail";
	}

	public String htmTooltipCliniqueMail() {
		return null;
	}

	public String htmCliniqueMail() {
		return cliniqueMail == null ? "" : StringEscapeUtils.escapeHtml4(strCliniqueMail());
	}

	public void inputCliniqueMail(String classeApiMethodeMethode) {
		CliniqueMedicale s = (CliniqueMedicale)this;
		{
			e("input")
				.a("type", "text")
				.a("placeholder", "mail")
				.a("id", classeApiMethodeMethode, "_cliniqueMail");
				if("Page".equals(classeApiMethodeMethode) || "PATCH".equals(classeApiMethodeMethode)) {
					a("class", "setCliniqueMail classCliniqueMedicale inputCliniqueMedicale", pk, "CliniqueMail w3-input w3-border ");
					a("name", "setCliniqueMail");
				} else {
					a("class", "valeurCliniqueMail w3-input w3-border classCliniqueMedicale inputCliniqueMedicale", pk, "CliniqueMail w3-input w3-border ");
					a("name", "cliniqueMail");
				}
				if("Page".equals(classeApiMethodeMethode)) {
					a("onclick", "enleverLueur($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setCliniqueMail', $(this).val(), function() { ajouterLueur($('#", classeApiMethodeMethode, "_cliniqueMail')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_cliniqueMail')); }); ");
				}
				a("value", strCliniqueMail())
			.fg();

		}
	}

	public void htmCliniqueMail(String classeApiMethodeMethode) {
		CliniqueMedicale s = (CliniqueMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "CliniqueMedicaleCliniqueMail").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pink ").f();
							e("label").a("for", classeApiMethodeMethode, "_cliniqueMail").a("class", "").f().sx("mail").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputCliniqueMail(classeApiMethodeMethode);
							} g("div");
							{
								if("Page".equals(classeApiMethodeMethode)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pink ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_cliniqueMail')); $('#", classeApiMethodeMethode, "_cliniqueMail').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#CliniqueMedicaleForm :input[name=pk]').val() }], 'setCliniqueMail', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_cliniqueMail')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_cliniqueMail')); }); ")
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

	////////////////////
	// cliniqueMailDe //
	////////////////////

	/**	L'entité « cliniqueMailDe »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String cliniqueMailDe;
	@JsonIgnore
	public Couverture<String> cliniqueMailDeCouverture = new Couverture<String>().p(this).c(String.class).var("cliniqueMailDe").o(cliniqueMailDe);

	/**	<br/>L'entité « cliniqueMailDe »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.clinique.CliniqueMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:cliniqueMailDe">Trouver l'entité cliniqueMailDe dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _cliniqueMailDe(Couverture<String> c);

	public String getCliniqueMailDe() {
		return cliniqueMailDe;
	}

	public void setCliniqueMailDe(String cliniqueMailDe) {
		this.cliniqueMailDe = cliniqueMailDe;
		this.cliniqueMailDeCouverture.dejaInitialise = true;
	}
	protected CliniqueMedicale cliniqueMailDeInit() {
		if(!cliniqueMailDeCouverture.dejaInitialise) {
			_cliniqueMailDe(cliniqueMailDeCouverture);
			if(cliniqueMailDe == null)
				setCliniqueMailDe(cliniqueMailDeCouverture.o);
		}
		cliniqueMailDeCouverture.dejaInitialise(true);
		return (CliniqueMedicale)this;
	}

	public String solrCliniqueMailDe() {
		return cliniqueMailDe;
	}

	public String strCliniqueMailDe() {
		return cliniqueMailDe == null ? "" : cliniqueMailDe;
	}

	public String jsonCliniqueMailDe() {
		return cliniqueMailDe == null ? "" : cliniqueMailDe;
	}

	public String nomAffichageCliniqueMailDe() {
		return "mail de l'école de";
	}

	public String htmTooltipCliniqueMailDe() {
		return null;
	}

	public String htmCliniqueMailDe() {
		return cliniqueMailDe == null ? "" : StringEscapeUtils.escapeHtml4(strCliniqueMailDe());
	}

	public void inputCliniqueMailDe(String classeApiMethodeMethode) {
		CliniqueMedicale s = (CliniqueMedicale)this;
		{
			e("input")
				.a("type", "text")
				.a("placeholder", "mail de l'école de")
				.a("id", classeApiMethodeMethode, "_cliniqueMailDe");
				if("Page".equals(classeApiMethodeMethode) || "PATCH".equals(classeApiMethodeMethode)) {
					a("class", "setCliniqueMailDe classCliniqueMedicale inputCliniqueMedicale", pk, "CliniqueMailDe w3-input w3-border ");
					a("name", "setCliniqueMailDe");
				} else {
					a("class", "valeurCliniqueMailDe w3-input w3-border classCliniqueMedicale inputCliniqueMedicale", pk, "CliniqueMailDe w3-input w3-border ");
					a("name", "cliniqueMailDe");
				}
				if("Page".equals(classeApiMethodeMethode)) {
					a("onclick", "enleverLueur($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setCliniqueMailDe', $(this).val(), function() { ajouterLueur($('#", classeApiMethodeMethode, "_cliniqueMailDe')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_cliniqueMailDe')); }); ");
				}
				a("value", strCliniqueMailDe())
			.fg();

		}
	}

	public void htmCliniqueMailDe(String classeApiMethodeMethode) {
		CliniqueMedicale s = (CliniqueMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "CliniqueMedicaleCliniqueMailDe").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pink ").f();
							e("label").a("for", classeApiMethodeMethode, "_cliniqueMailDe").a("class", "").f().sx("mail de l'école de").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputCliniqueMailDe(classeApiMethodeMethode);
							} g("div");
							{
								if("Page".equals(classeApiMethodeMethode)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pink ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_cliniqueMailDe')); $('#", classeApiMethodeMethode, "_cliniqueMailDe').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#CliniqueMedicaleForm :input[name=pk]').val() }], 'setCliniqueMailDe', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_cliniqueMailDe')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_cliniqueMailDe')); }); ")
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

	///////////////////
	// cliniqueMailA //
	///////////////////

	/**	L'entité « cliniqueMailA »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String cliniqueMailA;
	@JsonIgnore
	public Couverture<String> cliniqueMailACouverture = new Couverture<String>().p(this).c(String.class).var("cliniqueMailA").o(cliniqueMailA);

	/**	<br/>L'entité « cliniqueMailA »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.clinique.CliniqueMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:cliniqueMailA">Trouver l'entité cliniqueMailA dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _cliniqueMailA(Couverture<String> c);

	public String getCliniqueMailA() {
		return cliniqueMailA;
	}

	public void setCliniqueMailA(String cliniqueMailA) {
		this.cliniqueMailA = cliniqueMailA;
		this.cliniqueMailACouverture.dejaInitialise = true;
	}
	protected CliniqueMedicale cliniqueMailAInit() {
		if(!cliniqueMailACouverture.dejaInitialise) {
			_cliniqueMailA(cliniqueMailACouverture);
			if(cliniqueMailA == null)
				setCliniqueMailA(cliniqueMailACouverture.o);
		}
		cliniqueMailACouverture.dejaInitialise(true);
		return (CliniqueMedicale)this;
	}

	public String solrCliniqueMailA() {
		return cliniqueMailA;
	}

	public String strCliniqueMailA() {
		return cliniqueMailA == null ? "" : cliniqueMailA;
	}

	public String jsonCliniqueMailA() {
		return cliniqueMailA == null ? "" : cliniqueMailA;
	}

	public String nomAffichageCliniqueMailA() {
		return "mail de l'école à";
	}

	public String htmTooltipCliniqueMailA() {
		return null;
	}

	public String htmCliniqueMailA() {
		return cliniqueMailA == null ? "" : StringEscapeUtils.escapeHtml4(strCliniqueMailA());
	}

	public void inputCliniqueMailA(String classeApiMethodeMethode) {
		CliniqueMedicale s = (CliniqueMedicale)this;
		{
			e("input")
				.a("type", "text")
				.a("placeholder", "mail de l'école à")
				.a("id", classeApiMethodeMethode, "_cliniqueMailA");
				if("Page".equals(classeApiMethodeMethode) || "PATCH".equals(classeApiMethodeMethode)) {
					a("class", "setCliniqueMailA classCliniqueMedicale inputCliniqueMedicale", pk, "CliniqueMailA w3-input w3-border ");
					a("name", "setCliniqueMailA");
				} else {
					a("class", "valeurCliniqueMailA w3-input w3-border classCliniqueMedicale inputCliniqueMedicale", pk, "CliniqueMailA w3-input w3-border ");
					a("name", "cliniqueMailA");
				}
				if("Page".equals(classeApiMethodeMethode)) {
					a("onclick", "enleverLueur($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setCliniqueMailA', $(this).val(), function() { ajouterLueur($('#", classeApiMethodeMethode, "_cliniqueMailA')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_cliniqueMailA')); }); ");
				}
				a("value", strCliniqueMailA())
			.fg();

		}
	}

	public void htmCliniqueMailA(String classeApiMethodeMethode) {
		CliniqueMedicale s = (CliniqueMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "CliniqueMedicaleCliniqueMailA").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pink ").f();
							e("label").a("for", classeApiMethodeMethode, "_cliniqueMailA").a("class", "").f().sx("mail de l'école à").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputCliniqueMailA(classeApiMethodeMethode);
							} g("div");
							{
								if("Page".equals(classeApiMethodeMethode)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pink ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_cliniqueMailA')); $('#", classeApiMethodeMethode, "_cliniqueMailA').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#CliniqueMedicaleForm :input[name=pk]').val() }], 'setCliniqueMailA', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_cliniqueMailA')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_cliniqueMailA')); }); ")
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.clinique.CliniqueMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:cliniqueEmplacement">Trouver l'entité cliniqueEmplacement dans Solr</a>
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
	protected CliniqueMedicale cliniqueEmplacementInit() {
		if(!cliniqueEmplacementCouverture.dejaInitialise) {
			_cliniqueEmplacement(cliniqueEmplacementCouverture);
			if(cliniqueEmplacement == null)
				setCliniqueEmplacement(cliniqueEmplacementCouverture.o);
		}
		cliniqueEmplacementCouverture.dejaInitialise(true);
		return (CliniqueMedicale)this;
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

	public void inputCliniqueEmplacement(String classeApiMethodeMethode) {
		CliniqueMedicale s = (CliniqueMedicale)this;
		{
			e("input")
				.a("type", "text")
				.a("placeholder", "l'emplacement")
				.a("id", classeApiMethodeMethode, "_cliniqueEmplacement");
				if("Page".equals(classeApiMethodeMethode) || "PATCH".equals(classeApiMethodeMethode)) {
					a("class", "setCliniqueEmplacement classCliniqueMedicale inputCliniqueMedicale", pk, "CliniqueEmplacement w3-input w3-border ");
					a("name", "setCliniqueEmplacement");
				} else {
					a("class", "valeurCliniqueEmplacement w3-input w3-border classCliniqueMedicale inputCliniqueMedicale", pk, "CliniqueEmplacement w3-input w3-border ");
					a("name", "cliniqueEmplacement");
				}
				if("Page".equals(classeApiMethodeMethode)) {
					a("onclick", "enleverLueur($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setCliniqueEmplacement', $(this).val(), function() { ajouterLueur($('#", classeApiMethodeMethode, "_cliniqueEmplacement')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_cliniqueEmplacement')); }); ");
				}
				a("value", strCliniqueEmplacement())
			.fg();

		}
	}

	public void htmCliniqueEmplacement(String classeApiMethodeMethode) {
		CliniqueMedicale s = (CliniqueMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "CliniqueMedicaleCliniqueEmplacement").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pink ").f();
							e("label").a("for", classeApiMethodeMethode, "_cliniqueEmplacement").a("class", "").f().sx("l'emplacement").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputCliniqueEmplacement(classeApiMethodeMethode);
							} g("div");
							{
								if("Page".equals(classeApiMethodeMethode)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pink ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_cliniqueEmplacement')); $('#", classeApiMethodeMethode, "_cliniqueEmplacement').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#CliniqueMedicaleForm :input[name=pk]').val() }], 'setCliniqueEmplacement', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_cliniqueEmplacement')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_cliniqueEmplacement')); }); ")
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.clinique.CliniqueMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:cliniqueAddresse">Trouver l'entité cliniqueAddresse dans Solr</a>
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
	protected CliniqueMedicale cliniqueAddresseInit() {
		if(!cliniqueAddresseCouverture.dejaInitialise) {
			_cliniqueAddresse(cliniqueAddresseCouverture);
			if(cliniqueAddresse == null)
				setCliniqueAddresse(cliniqueAddresseCouverture.o);
		}
		cliniqueAddresseCouverture.dejaInitialise(true);
		return (CliniqueMedicale)this;
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
		CliniqueMedicale s = (CliniqueMedicale)this;
		{
			e("textarea")
				.a("placeholder", "addresse")
				.a("id", classeApiMethodeMethode, "_cliniqueAddresse");
				if("Page".equals(classeApiMethodeMethode) || "PATCH".equals(classeApiMethodeMethode)) {
					a("class", "setCliniqueAddresse classCliniqueMedicale inputCliniqueMedicale", pk, "CliniqueAddresse w3-input w3-border ");
					a("name", "setCliniqueAddresse");
				} else {
					a("class", "valeurCliniqueAddresse w3-input w3-border classCliniqueMedicale inputCliniqueMedicale", pk, "CliniqueAddresse w3-input w3-border ");
					a("name", "cliniqueAddresse");
				}
				if("Page".equals(classeApiMethodeMethode)) {
					a("onclick", "enleverLueur($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setCliniqueAddresse', $(this).val(), function() { ajouterLueur($('#", classeApiMethodeMethode, "_cliniqueAddresse')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_cliniqueAddresse')); }); ");
				}
			f().sx(strCliniqueAddresse()).g("textarea");

		}
	}

	public void htmCliniqueAddresse(String classeApiMethodeMethode) {
		CliniqueMedicale s = (CliniqueMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "CliniqueMedicaleCliniqueAddresse").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pink ").f();
							e("label").a("for", classeApiMethodeMethode, "_cliniqueAddresse").a("class", "").f().sx("addresse").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputCliniqueAddresse(classeApiMethodeMethode);
							} g("div");
							{
								if("Page".equals(classeApiMethodeMethode)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pink ")
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_cliniqueAddresse')); $('#", classeApiMethodeMethode, "_cliniqueAddresse').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#CliniqueMedicaleForm :input[name=pk]').val() }], 'setCliniqueAddresse', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_cliniqueAddresse')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_cliniqueAddresse')); }); ")
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

	/////////////////////
	// inscriptionCles //
	/////////////////////

	/**	L'entité « inscriptionCles »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> inscriptionCles = new ArrayList<Long>();
	@JsonIgnore
	public Couverture<List<Long>> inscriptionClesCouverture = new Couverture<List<Long>>().p(this).c(List.class).var("inscriptionCles").o(inscriptionCles);

	/**	<br/>L'entité « inscriptionCles »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.clinique.CliniqueMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptionCles">Trouver l'entité inscriptionCles dans Solr</a>
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
	public CliniqueMedicale addInscriptionCles(Long...objets) {
		for(Long o : objets) {
			addInscriptionCles(o);
		}
		return (CliniqueMedicale)this;
	}
	public CliniqueMedicale addInscriptionCles(Long o) {
		if(o != null && !inscriptionCles.contains(o))
			this.inscriptionCles.add(o);
		return (CliniqueMedicale)this;
	}
	public CliniqueMedicale setInscriptionCles(JsonArray objets) {
		inscriptionCles.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addInscriptionCles(o);
		}
		return (CliniqueMedicale)this;
	}
	public CliniqueMedicale addInscriptionCles(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addInscriptionCles(p);
		}
		return (CliniqueMedicale)this;
	}
	protected CliniqueMedicale inscriptionClesInit() {
		if(!inscriptionClesCouverture.dejaInitialise) {
			_inscriptionCles(inscriptionCles);
		}
		inscriptionClesCouverture.dejaInitialise(true);
		return (CliniqueMedicale)this;
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
		CliniqueMedicale s = (CliniqueMedicale)this;
		{
			e("i").a("class", "far fa-search w3-xxlarge w3-cell w3-cell-middle ").f().g("i");
				e("input")
					.a("type", "text")
					.a("placeholder", "inscriptions")
					.a("class", "valeur suggereInscriptionCles w3-input w3-border w3-cell w3-cell-middle ")
					.a("name", "setInscriptionCles")
					.a("id", classeApiMethodeMethode, "_inscriptionCles")
					.a("autocomplete", "off")
					.a("oninput", "suggereCliniqueMedicaleInscriptionCles($(this).val() ? rechercherInscriptionMedicaleFiltres($(this.parentElement)) : [", pk == null ? "" : "{'name':'fq','value':'cliniqueCle:" + pk + "'}", "], $('#listCliniqueMedicaleInscriptionCles_", classeApiMethodeMethode, "'), ", pk, "); ")
				.fg();

		}
	}

	public void htmInscriptionCles(String classeApiMethodeMethode) {
		CliniqueMedicale s = (CliniqueMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggere", classeApiMethodeMethode, "CliniqueMedicaleInscriptionCles").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row ").f();
							{ e("a").a("href", "?fq=cliniqueCle:", pk).a("class", "w3-cell w3-btn w3-center h4 w3-block h4 w3-blue-gray w3-hover-blue-gray ").f();
								e("i").a("class", "fas fa-notes-medical ").f().g("i");
								sx("inscriptions");
							} g("a");
						} g("div");
						{ e("div").a("class", "w3-cell-row ").f();
							{ e("h5").a("class", "w3-cell ").f();
								sx("relier  a cette école");
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
								{ e("ul").a("class", "w3-ul w3-hoverable ").a("id", "listCliniqueMedicaleInscriptionCles_", classeApiMethodeMethode).f();
								} g("ul");
								{
									{ e("div").a("class", "w3-cell-row ").f();
										e("button")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-blue-gray ")
											.a("id", classeApiMethodeMethode, "_inscriptionCles_ajouter")
											.a("onclick", "$(this).addClass('w3-disabled'); this.disabled = true; this.innerHTML = 'Envoi…'; postInscriptionMedicaleVals({ cliniqueCle: \"", pk, "\" }, function() {}, function() { ajouterErreur($('#", classeApiMethodeMethode, "inscriptionCles')); });")
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

	//////////////////////
	// cliniqueNomCourt //
	//////////////////////

	/**	L'entité « cliniqueNomCourt »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String cliniqueNomCourt;
	@JsonIgnore
	public Couverture<String> cliniqueNomCourtCouverture = new Couverture<String>().p(this).c(String.class).var("cliniqueNomCourt").o(cliniqueNomCourt);

	/**	<br/>L'entité « cliniqueNomCourt »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.clinique.CliniqueMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:cliniqueNomCourt">Trouver l'entité cliniqueNomCourt dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _cliniqueNomCourt(Couverture<String> c);

	public String getCliniqueNomCourt() {
		return cliniqueNomCourt;
	}

	public void setCliniqueNomCourt(String cliniqueNomCourt) {
		this.cliniqueNomCourt = cliniqueNomCourt;
		this.cliniqueNomCourtCouverture.dejaInitialise = true;
	}
	protected CliniqueMedicale cliniqueNomCourtInit() {
		if(!cliniqueNomCourtCouverture.dejaInitialise) {
			_cliniqueNomCourt(cliniqueNomCourtCouverture);
			if(cliniqueNomCourt == null)
				setCliniqueNomCourt(cliniqueNomCourtCouverture.o);
		}
		cliniqueNomCourtCouverture.dejaInitialise(true);
		return (CliniqueMedicale)this;
	}

	public String solrCliniqueNomCourt() {
		return cliniqueNomCourt;
	}

	public String strCliniqueNomCourt() {
		return cliniqueNomCourt == null ? "" : cliniqueNomCourt;
	}

	public String jsonCliniqueNomCourt() {
		return cliniqueNomCourt == null ? "" : cliniqueNomCourt;
	}

	public String nomAffichageCliniqueNomCourt() {
		return "NomAffichage.enUS: ";
	}

	public String htmTooltipCliniqueNomCourt() {
		return null;
	}

	public String htmCliniqueNomCourt() {
		return cliniqueNomCourt == null ? "" : StringEscapeUtils.escapeHtml4(strCliniqueNomCourt());
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.clinique.CliniqueMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:cliniqueNomComplet">Trouver l'entité cliniqueNomComplet dans Solr</a>
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
	protected CliniqueMedicale cliniqueNomCompletInit() {
		if(!cliniqueNomCompletCouverture.dejaInitialise) {
			_cliniqueNomComplet(cliniqueNomCompletCouverture);
			if(cliniqueNomComplet == null)
				setCliniqueNomComplet(cliniqueNomCompletCouverture.o);
		}
		cliniqueNomCompletCouverture.dejaInitialise(true);
		return (CliniqueMedicale)this;
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
		return "nom";
	}

	public String htmTooltipCliniqueNomComplet() {
		return null;
	}

	public String htmCliniqueNomComplet() {
		return cliniqueNomComplet == null ? "" : StringEscapeUtils.escapeHtml4(strCliniqueNomComplet());
	}

	//////////////
	// initLoin //
	//////////////

	protected boolean dejaInitialiseCliniqueMedicale = false;

	public CliniqueMedicale initLoinCliniqueMedicale(RequeteSiteFrFR requeteSite_) {
		setRequeteSite_(requeteSite_);
		if(!dejaInitialiseCliniqueMedicale) {
			dejaInitialiseCliniqueMedicale = true;
			initLoinCliniqueMedicale();
		}
		return (CliniqueMedicale)this;
	}

	public void initLoinCliniqueMedicale() {
		initCliniqueMedicale();
		super.initLoinCluster(requeteSite_);
	}

	public void initCliniqueMedicale() {
		cliniqueCleInit();
		patientClesInit();
		medicaleTriInit();
		cliniqueTriInit();
		cliniqueNomInit();
		cliniqueNumeroTelephoneInit();
		cliniqueAdministrateurNomInit();
		cliniqueMailInit();
		cliniqueMailDeInit();
		cliniqueMailAInit();
		cliniqueEmplacementInit();
		cliniqueAddresseInit();
		inscriptionClesInit();
		cliniqueNomCourtInit();
		cliniqueNomCompletInit();
	}

	@Override public void initLoinPourClasse(RequeteSiteFrFR requeteSite_) {
		initLoinCliniqueMedicale(requeteSite_);
	}

	/////////////////
	// requeteSite //
	/////////////////

	public void requeteSiteCliniqueMedicale(RequeteSiteFrFR requeteSite_) {
			super.requeteSiteCluster(requeteSite_);
	}

	public void requeteSitePourClasse(RequeteSiteFrFR requeteSite_) {
		requeteSiteCliniqueMedicale(requeteSite_);
	}

	/////////////
	// obtenir //
	/////////////

	@Override public Object obtenirPourClasse(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtenirCliniqueMedicale(v);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.obtenirPourClasse(v);
			}
		}
		return o;
	}
	public Object obtenirCliniqueMedicale(String var) {
		CliniqueMedicale oCliniqueMedicale = (CliniqueMedicale)this;
		switch(var) {
			case "cliniqueCle":
				return oCliniqueMedicale.cliniqueCle;
			case "patientCles":
				return oCliniqueMedicale.patientCles;
			case "medicaleTri":
				return oCliniqueMedicale.medicaleTri;
			case "cliniqueTri":
				return oCliniqueMedicale.cliniqueTri;
			case "cliniqueNom":
				return oCliniqueMedicale.cliniqueNom;
			case "cliniqueNumeroTelephone":
				return oCliniqueMedicale.cliniqueNumeroTelephone;
			case "cliniqueAdministrateurNom":
				return oCliniqueMedicale.cliniqueAdministrateurNom;
			case "cliniqueMail":
				return oCliniqueMedicale.cliniqueMail;
			case "cliniqueMailDe":
				return oCliniqueMedicale.cliniqueMailDe;
			case "cliniqueMailA":
				return oCliniqueMedicale.cliniqueMailA;
			case "cliniqueEmplacement":
				return oCliniqueMedicale.cliniqueEmplacement;
			case "cliniqueAddresse":
				return oCliniqueMedicale.cliniqueAddresse;
			case "inscriptionCles":
				return oCliniqueMedicale.inscriptionCles;
			case "cliniqueNomCourt":
				return oCliniqueMedicale.cliniqueNomCourt;
			case "cliniqueNomComplet":
				return oCliniqueMedicale.cliniqueNomComplet;
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
				o = attribuerCliniqueMedicale(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attribuerPourClasse(v, val);
			}
		}
		return o != null;
	}
	public Object attribuerCliniqueMedicale(String var, Object val) {
		CliniqueMedicale oCliniqueMedicale = (CliniqueMedicale)this;
		switch(var) {
			case "inscriptionCles":
				oCliniqueMedicale.addInscriptionCles((Long)val);
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
					o = definirCliniqueMedicale(v, val);
				else if(o instanceof Cluster) {
					Cluster cluster = (Cluster)o;
					o = cluster.definirPourClasse(v, val);
				}
			}
		}
		return o != null;
	}
	public Object definirCliniqueMedicale(String var, String val) {
		switch(var) {
			case "cliniqueNom":
				if(val != null)
					setCliniqueNom(val);
				sauvegardes.add(var);
				return val;
			case "cliniqueNumeroTelephone":
				if(val != null)
					setCliniqueNumeroTelephone(val);
				sauvegardes.add(var);
				return val;
			case "cliniqueAdministrateurNom":
				if(val != null)
					setCliniqueAdministrateurNom(val);
				sauvegardes.add(var);
				return val;
			case "cliniqueMail":
				if(val != null)
					setCliniqueMail(val);
				sauvegardes.add(var);
				return val;
			case "cliniqueMailDe":
				if(val != null)
					setCliniqueMailDe(val);
				sauvegardes.add(var);
				return val;
			case "cliniqueMailA":
				if(val != null)
					setCliniqueMailA(val);
				sauvegardes.add(var);
				return val;
			case "cliniqueEmplacement":
				if(val != null)
					setCliniqueEmplacement(val);
				sauvegardes.add(var);
				return val;
			case "cliniqueAddresse":
				if(val != null)
					setCliniqueAddresse(val);
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
		peuplerCliniqueMedicale(solrDocument);
	}
	public void peuplerCliniqueMedicale(SolrDocument solrDocument) {
		CliniqueMedicale oCliniqueMedicale = (CliniqueMedicale)this;
		sauvegardes = (List<String>)solrDocument.get("sauvegardes_stored_strings");
		if(sauvegardes != null) {

			if(sauvegardes.contains("cliniqueCle")) {
				Long cliniqueCle = (Long)solrDocument.get("cliniqueCle_stored_long");
				if(cliniqueCle != null)
					oCliniqueMedicale.setCliniqueCle(cliniqueCle);
			}

			if(sauvegardes.contains("patientCles")) {
				List<Long> patientCles = (List<Long>)solrDocument.get("patientCles_stored_longs");
				if(patientCles != null)
					oCliniqueMedicale.patientCles.addAll(patientCles);
			}

			if(sauvegardes.contains("medicaleTri")) {
				Integer medicaleTri = (Integer)solrDocument.get("medicaleTri_stored_int");
				if(medicaleTri != null)
					oCliniqueMedicale.setMedicaleTri(medicaleTri);
			}

			if(sauvegardes.contains("cliniqueTri")) {
				Integer cliniqueTri = (Integer)solrDocument.get("cliniqueTri_stored_int");
				if(cliniqueTri != null)
					oCliniqueMedicale.setCliniqueTri(cliniqueTri);
			}

			if(sauvegardes.contains("cliniqueNom")) {
				String cliniqueNom = (String)solrDocument.get("cliniqueNom_stored_string");
				if(cliniqueNom != null)
					oCliniqueMedicale.setCliniqueNom(cliniqueNom);
			}

			if(sauvegardes.contains("cliniqueNumeroTelephone")) {
				String cliniqueNumeroTelephone = (String)solrDocument.get("cliniqueNumeroTelephone_stored_string");
				if(cliniqueNumeroTelephone != null)
					oCliniqueMedicale.setCliniqueNumeroTelephone(cliniqueNumeroTelephone);
			}

			if(sauvegardes.contains("cliniqueAdministrateurNom")) {
				String cliniqueAdministrateurNom = (String)solrDocument.get("cliniqueAdministrateurNom_stored_string");
				if(cliniqueAdministrateurNom != null)
					oCliniqueMedicale.setCliniqueAdministrateurNom(cliniqueAdministrateurNom);
			}

			if(sauvegardes.contains("cliniqueMail")) {
				String cliniqueMail = (String)solrDocument.get("cliniqueMail_stored_string");
				if(cliniqueMail != null)
					oCliniqueMedicale.setCliniqueMail(cliniqueMail);
			}

			if(sauvegardes.contains("cliniqueMailDe")) {
				String cliniqueMailDe = (String)solrDocument.get("cliniqueMailDe_stored_string");
				if(cliniqueMailDe != null)
					oCliniqueMedicale.setCliniqueMailDe(cliniqueMailDe);
			}

			if(sauvegardes.contains("cliniqueMailA")) {
				String cliniqueMailA = (String)solrDocument.get("cliniqueMailA_stored_string");
				if(cliniqueMailA != null)
					oCliniqueMedicale.setCliniqueMailA(cliniqueMailA);
			}

			if(sauvegardes.contains("cliniqueEmplacement")) {
				String cliniqueEmplacement = (String)solrDocument.get("cliniqueEmplacement_stored_string");
				if(cliniqueEmplacement != null)
					oCliniqueMedicale.setCliniqueEmplacement(cliniqueEmplacement);
			}

			if(sauvegardes.contains("cliniqueAddresse")) {
				String cliniqueAddresse = (String)solrDocument.get("cliniqueAddresse_stored_string");
				if(cliniqueAddresse != null)
					oCliniqueMedicale.setCliniqueAddresse(cliniqueAddresse);
			}

			List<Long> inscriptionCles = (List<Long>)solrDocument.get("inscriptionCles_stored_longs");
			if(inscriptionCles != null)
				oCliniqueMedicale.inscriptionCles.addAll(inscriptionCles);

			if(sauvegardes.contains("cliniqueNomCourt")) {
				String cliniqueNomCourt = (String)solrDocument.get("cliniqueNomCourt_stored_string");
				if(cliniqueNomCourt != null)
					oCliniqueMedicale.setCliniqueNomCourt(cliniqueNomCourt);
			}

			if(sauvegardes.contains("cliniqueNomComplet")) {
				String cliniqueNomComplet = (String)solrDocument.get("cliniqueNomComplet_stored_string");
				if(cliniqueNomComplet != null)
					oCliniqueMedicale.setCliniqueNomComplet(cliniqueNomComplet);
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
			rechercheSolr.addFilterQuery("id:" + ClientUtils.escapeQueryChars("org.computate.medicale.frFR.clinique.CliniqueMedicale"));
			QueryResponse reponseRecherche = requeteSite.getSiteContexte_().getClientSolr().query(rechercheSolr);
			if(reponseRecherche.getResults().size() > 0)
				requeteSite.setDocumentSolr(reponseRecherche.getResults().get(0));
			CliniqueMedicale o = new CliniqueMedicale();
			o.requeteSiteCliniqueMedicale(requeteSite);
			o.initLoinCliniqueMedicale(requeteSite);
			o.indexerCliniqueMedicale();
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}


	@Override public void indexerPourClasse() {
		indexerCliniqueMedicale();
	}

	@Override public void indexerPourClasse(SolrInputDocument document) {
		indexerCliniqueMedicale(document);
	}

	public void indexerCliniqueMedicale(SolrClient clientSolr) {
		try {
			SolrInputDocument document = new SolrInputDocument();
			indexerCliniqueMedicale(document);
			clientSolr.add(document);
			clientSolr.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public void indexerCliniqueMedicale() {
		try {
			SolrInputDocument document = new SolrInputDocument();
			indexerCliniqueMedicale(document);
			SolrClient clientSolr = requeteSite_.getSiteContexte_().getClientSolr();
			clientSolr.add(document);
			clientSolr.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public void indexerCliniqueMedicale(SolrInputDocument document) {
		if(cliniqueCle != null) {
			document.addField("cliniqueCle_indexed_long", cliniqueCle);
			document.addField("cliniqueCle_stored_long", cliniqueCle);
		}
		if(patientCles != null) {
			for(java.lang.Long o : patientCles) {
				document.addField("patientCles_indexed_longs", o);
			}
			for(java.lang.Long o : patientCles) {
				document.addField("patientCles_stored_longs", o);
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
		if(cliniqueNom != null) {
			document.addField("cliniqueNom_indexed_string", cliniqueNom);
			document.addField("cliniqueNom_stored_string", cliniqueNom);
		}
		if(cliniqueNumeroTelephone != null) {
			document.addField("cliniqueNumeroTelephone_indexed_string", cliniqueNumeroTelephone);
			document.addField("cliniqueNumeroTelephone_stored_string", cliniqueNumeroTelephone);
		}
		if(cliniqueAdministrateurNom != null) {
			document.addField("cliniqueAdministrateurNom_indexed_string", cliniqueAdministrateurNom);
			document.addField("cliniqueAdministrateurNom_stored_string", cliniqueAdministrateurNom);
		}
		if(cliniqueMail != null) {
			document.addField("cliniqueMail_indexed_string", cliniqueMail);
			document.addField("cliniqueMail_stored_string", cliniqueMail);
		}
		if(cliniqueMailDe != null) {
			document.addField("cliniqueMailDe_indexed_string", cliniqueMailDe);
			document.addField("cliniqueMailDe_stored_string", cliniqueMailDe);
		}
		if(cliniqueMailA != null) {
			document.addField("cliniqueMailA_indexed_string", cliniqueMailA);
			document.addField("cliniqueMailA_stored_string", cliniqueMailA);
		}
		if(cliniqueEmplacement != null) {
			document.addField("cliniqueEmplacement_indexed_string", cliniqueEmplacement);
			document.addField("cliniqueEmplacement_stored_string", cliniqueEmplacement);
		}
		if(cliniqueAddresse != null) {
			document.addField("cliniqueAddresse_indexed_string", cliniqueAddresse);
			document.addField("cliniqueAddresse_stored_string", cliniqueAddresse);
		}
		if(inscriptionCles != null) {
			for(java.lang.Long o : inscriptionCles) {
				document.addField("inscriptionCles_indexed_longs", o);
			}
			for(java.lang.Long o : inscriptionCles) {
				document.addField("inscriptionCles_stored_longs", o);
			}
		}
		if(cliniqueNomCourt != null) {
			document.addField("cliniqueNomCourt_indexed_string", cliniqueNomCourt);
			document.addField("cliniqueNomCourt_stored_string", cliniqueNomCourt);
		}
		if(cliniqueNomComplet != null) {
			document.addField("cliniqueNomComplet_indexed_string", cliniqueNomComplet);
			document.addField("cliniqueNomComplet_stored_string", cliniqueNomComplet);
		}
		super.indexerCluster(document);

	}

	public void desindexerCliniqueMedicale() {
		try {
		RequeteSiteFrFR requeteSite = new RequeteSiteFrFR();
			requeteSite.initLoinRequeteSiteFrFR();
			SiteContexteFrFR siteContexte = new SiteContexteFrFR();
			siteContexte.initLoinSiteContexteFrFR();
			requeteSite.setSiteContexte_(siteContexte);
			requeteSite.setConfigSite_(siteContexte.getConfigSite());
			initLoinCliniqueMedicale(requeteSite);
			SolrClient clientSolr = siteContexte.getClientSolr();
			clientSolr.deleteById(id.toString());
			clientSolr.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public static String varIndexeCliniqueMedicale(String entiteVar) {
		switch(entiteVar) {
			case "cliniqueCle":
				return "cliniqueCle_indexed_long";
			case "patientCles":
				return "patientCles_indexed_longs";
			case "medicaleTri":
				return "medicaleTri_indexed_int";
			case "cliniqueTri":
				return "cliniqueTri_indexed_int";
			case "cliniqueNom":
				return "cliniqueNom_indexed_string";
			case "cliniqueNumeroTelephone":
				return "cliniqueNumeroTelephone_indexed_string";
			case "cliniqueAdministrateurNom":
				return "cliniqueAdministrateurNom_indexed_string";
			case "cliniqueMail":
				return "cliniqueMail_indexed_string";
			case "cliniqueMailDe":
				return "cliniqueMailDe_indexed_string";
			case "cliniqueMailA":
				return "cliniqueMailA_indexed_string";
			case "cliniqueEmplacement":
				return "cliniqueEmplacement_indexed_string";
			case "cliniqueAddresse":
				return "cliniqueAddresse_indexed_string";
			case "inscriptionCles":
				return "inscriptionCles_indexed_longs";
			case "cliniqueNomCourt":
				return "cliniqueNomCourt_indexed_string";
			case "cliniqueNomComplet":
				return "cliniqueNomComplet_indexed_string";
			default:
				return Cluster.varIndexeCluster(entiteVar);
		}
	}

	public static String varRechercheCliniqueMedicale(String entiteVar) {
		switch(entiteVar) {
			default:
				return Cluster.varRechercheCluster(entiteVar);
		}
	}

	public static String varSuggereCliniqueMedicale(String entiteVar) {
		switch(entiteVar) {
			default:
				return Cluster.varSuggereCluster(entiteVar);
		}
	}

	/////////////
	// stocker //
	/////////////

	@Override public void stockerPourClasse(SolrDocument solrDocument) {
		stockerCliniqueMedicale(solrDocument);
	}
	public void stockerCliniqueMedicale(SolrDocument solrDocument) {
		CliniqueMedicale oCliniqueMedicale = (CliniqueMedicale)this;

		Long cliniqueCle = (Long)solrDocument.get("cliniqueCle_stored_long");
		if(cliniqueCle != null)
			oCliniqueMedicale.setCliniqueCle(cliniqueCle);

		List<Long> patientCles = (List<Long>)solrDocument.get("patientCles_stored_longs");
		if(patientCles != null)
			oCliniqueMedicale.patientCles.addAll(patientCles);

		Integer medicaleTri = (Integer)solrDocument.get("medicaleTri_stored_int");
		if(medicaleTri != null)
			oCliniqueMedicale.setMedicaleTri(medicaleTri);

		Integer cliniqueTri = (Integer)solrDocument.get("cliniqueTri_stored_int");
		if(cliniqueTri != null)
			oCliniqueMedicale.setCliniqueTri(cliniqueTri);

		String cliniqueNom = (String)solrDocument.get("cliniqueNom_stored_string");
		if(cliniqueNom != null)
			oCliniqueMedicale.setCliniqueNom(cliniqueNom);

		String cliniqueNumeroTelephone = (String)solrDocument.get("cliniqueNumeroTelephone_stored_string");
		if(cliniqueNumeroTelephone != null)
			oCliniqueMedicale.setCliniqueNumeroTelephone(cliniqueNumeroTelephone);

		String cliniqueAdministrateurNom = (String)solrDocument.get("cliniqueAdministrateurNom_stored_string");
		if(cliniqueAdministrateurNom != null)
			oCliniqueMedicale.setCliniqueAdministrateurNom(cliniqueAdministrateurNom);

		String cliniqueMail = (String)solrDocument.get("cliniqueMail_stored_string");
		if(cliniqueMail != null)
			oCliniqueMedicale.setCliniqueMail(cliniqueMail);

		String cliniqueMailDe = (String)solrDocument.get("cliniqueMailDe_stored_string");
		if(cliniqueMailDe != null)
			oCliniqueMedicale.setCliniqueMailDe(cliniqueMailDe);

		String cliniqueMailA = (String)solrDocument.get("cliniqueMailA_stored_string");
		if(cliniqueMailA != null)
			oCliniqueMedicale.setCliniqueMailA(cliniqueMailA);

		String cliniqueEmplacement = (String)solrDocument.get("cliniqueEmplacement_stored_string");
		if(cliniqueEmplacement != null)
			oCliniqueMedicale.setCliniqueEmplacement(cliniqueEmplacement);

		String cliniqueAddresse = (String)solrDocument.get("cliniqueAddresse_stored_string");
		if(cliniqueAddresse != null)
			oCliniqueMedicale.setCliniqueAddresse(cliniqueAddresse);

		List<Long> inscriptionCles = (List<Long>)solrDocument.get("inscriptionCles_stored_longs");
		if(inscriptionCles != null)
			oCliniqueMedicale.inscriptionCles.addAll(inscriptionCles);

		String cliniqueNomCourt = (String)solrDocument.get("cliniqueNomCourt_stored_string");
		if(cliniqueNomCourt != null)
			oCliniqueMedicale.setCliniqueNomCourt(cliniqueNomCourt);

		String cliniqueNomComplet = (String)solrDocument.get("cliniqueNomComplet_stored_string");
		if(cliniqueNomComplet != null)
			oCliniqueMedicale.setCliniqueNomComplet(cliniqueNomComplet);

		super.stockerCluster(solrDocument);
	}

	//////////////////
	// requeteApi //
	//////////////////

	public void requeteApiCliniqueMedicale() {
		RequeteApi requeteApi = Optional.ofNullable(requeteSite_).map(RequeteSiteFrFR::getRequeteApi_).orElse(null);
		Object o = Optional.ofNullable(requeteApi).map(RequeteApi::getOriginal).orElse(null);
		if(o != null && o instanceof CliniqueMedicale) {
			CliniqueMedicale original = (CliniqueMedicale)o;
			if(!Objects.equals(cliniqueNom, original.getCliniqueNom()))
				requeteApi.addVars("cliniqueNom");
			if(!Objects.equals(cliniqueNumeroTelephone, original.getCliniqueNumeroTelephone()))
				requeteApi.addVars("cliniqueNumeroTelephone");
			if(!Objects.equals(cliniqueAdministrateurNom, original.getCliniqueAdministrateurNom()))
				requeteApi.addVars("cliniqueAdministrateurNom");
			if(!Objects.equals(cliniqueMail, original.getCliniqueMail()))
				requeteApi.addVars("cliniqueMail");
			if(!Objects.equals(cliniqueMailDe, original.getCliniqueMailDe()))
				requeteApi.addVars("cliniqueMailDe");
			if(!Objects.equals(cliniqueMailA, original.getCliniqueMailA()))
				requeteApi.addVars("cliniqueMailA");
			if(!Objects.equals(cliniqueEmplacement, original.getCliniqueEmplacement()))
				requeteApi.addVars("cliniqueEmplacement");
			if(!Objects.equals(cliniqueAddresse, original.getCliniqueAddresse()))
				requeteApi.addVars("cliniqueAddresse");
			if(!Objects.equals(inscriptionCles, original.getInscriptionCles()))
				requeteApi.addVars("inscriptionCles");
			super.requeteApiCluster();
		}
	}

	//////////////
	// hashCode //
	//////////////

	@Override public int hashCode() {
		return Objects.hash(super.hashCode(), cliniqueNom, cliniqueNumeroTelephone, cliniqueAdministrateurNom, cliniqueMail, cliniqueMailDe, cliniqueMailA, cliniqueEmplacement, cliniqueAddresse, inscriptionCles);
	}

	////////////
	// equals //
	////////////

	@Override public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof CliniqueMedicale))
			return false;
		CliniqueMedicale that = (CliniqueMedicale)o;
		return super.equals(o)
				&& Objects.equals( cliniqueNom, that.cliniqueNom )
				&& Objects.equals( cliniqueNumeroTelephone, that.cliniqueNumeroTelephone )
				&& Objects.equals( cliniqueAdministrateurNom, that.cliniqueAdministrateurNom )
				&& Objects.equals( cliniqueMail, that.cliniqueMail )
				&& Objects.equals( cliniqueMailDe, that.cliniqueMailDe )
				&& Objects.equals( cliniqueMailA, that.cliniqueMailA )
				&& Objects.equals( cliniqueEmplacement, that.cliniqueEmplacement )
				&& Objects.equals( cliniqueAddresse, that.cliniqueAddresse )
				&& Objects.equals( inscriptionCles, that.inscriptionCles );
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("CliniqueMedicale { ");
		sb.append( "cliniqueNom: \"" ).append(cliniqueNom).append( "\"" );
		sb.append( ", cliniqueNumeroTelephone: \"" ).append(cliniqueNumeroTelephone).append( "\"" );
		sb.append( ", cliniqueAdministrateurNom: \"" ).append(cliniqueAdministrateurNom).append( "\"" );
		sb.append( ", cliniqueMail: \"" ).append(cliniqueMail).append( "\"" );
		sb.append( ", cliniqueMailDe: \"" ).append(cliniqueMailDe).append( "\"" );
		sb.append( ", cliniqueMailA: \"" ).append(cliniqueMailA).append( "\"" );
		sb.append( ", cliniqueEmplacement: \"" ).append(cliniqueEmplacement).append( "\"" );
		sb.append( ", cliniqueAddresse: \"" ).append(cliniqueAddresse).append( "\"" );
		sb.append( ", inscriptionCles: " ).append(inscriptionCles);
		sb.append(" }");
		return sb.toString();
	}
}
