package org.computate.medicale.frFR.clinique;

import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.computate.medicale.frFR.cluster.Cluster;
import java.util.Date;
import org.computate.medicale.frFR.requete.api.RequeteApi;
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
	public static final String CliniqueMedicale_IconeNom = "clinic";

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

	///////////////
	// anneeCles //
	///////////////

	/**	L'entité « anneeCles »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> anneeCles = new java.util.ArrayList<java.lang.Long>();
	@JsonIgnore
	public Couverture<List<Long>> anneeClesCouverture = new Couverture<List<Long>>().p(this).c(List.class).var("anneeCles").o(anneeCles);

	/**	<br/>L'entité « anneeCles »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.clinique.CliniqueMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:anneeCles">Trouver l'entité anneeCles dans Solr</a>
	 * <br/>
	 * @param anneeCles est l'entité déjà construit. 
	 **/
	protected abstract void _anneeCles(List<Long> o);

	public List<Long> getAnneeCles() {
		return anneeCles;
	}

	public void setAnneeCles(List<Long> anneeCles) {
		this.anneeCles = anneeCles;
		this.anneeClesCouverture.dejaInitialise = true;
	}
	public CliniqueMedicale addAnneeCles(Long...objets) {
		for(Long o : objets) {
			addAnneeCles(o);
		}
		return (CliniqueMedicale)this;
	}
	public CliniqueMedicale addAnneeCles(Long o) {
		if(o != null && !anneeCles.contains(o))
			this.anneeCles.add(o);
		return (CliniqueMedicale)this;
	}
	public CliniqueMedicale setAnneeCles(JsonArray objets) {
		anneeCles.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addAnneeCles(o);
		}
		return (CliniqueMedicale)this;
	}
	public CliniqueMedicale addAnneeCles(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addAnneeCles(p);
		}
		return (CliniqueMedicale)this;
	}
	protected CliniqueMedicale anneeClesInit() {
		if(!anneeClesCouverture.dejaInitialise) {
			_anneeCles(anneeCles);
		}
		anneeClesCouverture.dejaInitialise(true);
		return (CliniqueMedicale)this;
	}

	public List<Long> solrAnneeCles() {
		return anneeCles;
	}

	public String strAnneeCles() {
		return anneeCles == null ? "" : anneeCles.toString();
	}

	public String jsonAnneeCles() {
		return anneeCles == null ? "" : anneeCles.toString();
	}

	public String nomAffichageAnneeCles() {
		return "années";
	}

	public String htmTooltipAnneeCles() {
		return null;
	}

	public String htmAnneeCles() {
		return anneeCles == null ? "" : StringEscapeUtils.escapeHtml4(strAnneeCles());
	}

	public void inputAnneeCles(String classeApiMethodeMethode) {
		CliniqueMedicale s = (CliniqueMedicale)this;
	}

	public void htmAnneeCles(String classeApiMethodeMethode) {
		CliniqueMedicale s = (CliniqueMedicale)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			if("Page".equals(classeApiMethodeMethode)) {
				{ e("div").a("class", "w3-padding ").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pink ").f();
							e("label").a("class", "").f().sx("années").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row  ").f();
							{ e("div").a("class", "w3-cell ").f();
								{ e("div").a("class", "w3-rest ").f();
									e("span").f().sx(strAnneeCles()).g("span");
								} g("div");
							} g("div");
						} g("div");
					} g("div");
				} g("div");
			}
		} g("div");
	}

	////////////////
	// saisonCles //
	////////////////

	/**	L'entité « saisonCles »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> saisonCles = new java.util.ArrayList<java.lang.Long>();
	@JsonIgnore
	public Couverture<List<Long>> saisonClesCouverture = new Couverture<List<Long>>().p(this).c(List.class).var("saisonCles").o(saisonCles);

	/**	<br/>L'entité « saisonCles »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.clinique.CliniqueMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:saisonCles">Trouver l'entité saisonCles dans Solr</a>
	 * <br/>
	 * @param saisonCles est l'entité déjà construit. 
	 **/
	protected abstract void _saisonCles(List<Long> o);

	public List<Long> getSaisonCles() {
		return saisonCles;
	}

	public void setSaisonCles(List<Long> saisonCles) {
		this.saisonCles = saisonCles;
		this.saisonClesCouverture.dejaInitialise = true;
	}
	public CliniqueMedicale addSaisonCles(Long...objets) {
		for(Long o : objets) {
			addSaisonCles(o);
		}
		return (CliniqueMedicale)this;
	}
	public CliniqueMedicale addSaisonCles(Long o) {
		if(o != null && !saisonCles.contains(o))
			this.saisonCles.add(o);
		return (CliniqueMedicale)this;
	}
	public CliniqueMedicale setSaisonCles(JsonArray objets) {
		saisonCles.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addSaisonCles(o);
		}
		return (CliniqueMedicale)this;
	}
	public CliniqueMedicale addSaisonCles(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addSaisonCles(p);
		}
		return (CliniqueMedicale)this;
	}
	protected CliniqueMedicale saisonClesInit() {
		if(!saisonClesCouverture.dejaInitialise) {
			_saisonCles(saisonCles);
		}
		saisonClesCouverture.dejaInitialise(true);
		return (CliniqueMedicale)this;
	}

	public List<Long> solrSaisonCles() {
		return saisonCles;
	}

	public String strSaisonCles() {
		return saisonCles == null ? "" : saisonCles.toString();
	}

	public String jsonSaisonCles() {
		return saisonCles == null ? "" : saisonCles.toString();
	}

	public String nomAffichageSaisonCles() {
		return "NomAffichage.enUS: ";
	}

	public String htmTooltipSaisonCles() {
		return null;
	}

	public String htmSaisonCles() {
		return saisonCles == null ? "" : StringEscapeUtils.escapeHtml4(strSaisonCles());
	}

	/////////////////
	// sessionCles //
	/////////////////

	/**	L'entité « sessionCles »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> sessionCles = new java.util.ArrayList<java.lang.Long>();
	@JsonIgnore
	public Couverture<List<Long>> sessionClesCouverture = new Couverture<List<Long>>().p(this).c(List.class).var("sessionCles").o(sessionCles);

	/**	<br/>L'entité « sessionCles »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.clinique.CliniqueMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:sessionCles">Trouver l'entité sessionCles dans Solr</a>
	 * <br/>
	 * @param sessionCles est l'entité déjà construit. 
	 **/
	protected abstract void _sessionCles(List<Long> o);

	public List<Long> getSessionCles() {
		return sessionCles;
	}

	public void setSessionCles(List<Long> sessionCles) {
		this.sessionCles = sessionCles;
		this.sessionClesCouverture.dejaInitialise = true;
	}
	public CliniqueMedicale addSessionCles(Long...objets) {
		for(Long o : objets) {
			addSessionCles(o);
		}
		return (CliniqueMedicale)this;
	}
	public CliniqueMedicale addSessionCles(Long o) {
		if(o != null && !sessionCles.contains(o))
			this.sessionCles.add(o);
		return (CliniqueMedicale)this;
	}
	public CliniqueMedicale setSessionCles(JsonArray objets) {
		sessionCles.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addSessionCles(o);
		}
		return (CliniqueMedicale)this;
	}
	public CliniqueMedicale addSessionCles(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addSessionCles(p);
		}
		return (CliniqueMedicale)this;
	}
	protected CliniqueMedicale sessionClesInit() {
		if(!sessionClesCouverture.dejaInitialise) {
			_sessionCles(sessionCles);
		}
		sessionClesCouverture.dejaInitialise(true);
		return (CliniqueMedicale)this;
	}

	public List<Long> solrSessionCles() {
		return sessionCles;
	}

	public String strSessionCles() {
		return sessionCles == null ? "" : sessionCles.toString();
	}

	public String jsonSessionCles() {
		return sessionCles == null ? "" : sessionCles.toString();
	}

	public String nomAffichageSessionCles() {
		return "NomAffichage.enUS: ";
	}

	public String htmTooltipSessionCles() {
		return null;
	}

	public String htmSessionCles() {
		return sessionCles == null ? "" : StringEscapeUtils.escapeHtml4(strSessionCles());
	}

	///////////////////
	// groupeAgeCles //
	///////////////////

	/**	L'entité « groupeAgeCles »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> groupeAgeCles = new java.util.ArrayList<java.lang.Long>();
	@JsonIgnore
	public Couverture<List<Long>> groupeAgeClesCouverture = new Couverture<List<Long>>().p(this).c(List.class).var("groupeAgeCles").o(groupeAgeCles);

	/**	<br/>L'entité « groupeAgeCles »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.clinique.CliniqueMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:groupeAgeCles">Trouver l'entité groupeAgeCles dans Solr</a>
	 * <br/>
	 * @param groupeAgeCles est l'entité déjà construit. 
	 **/
	protected abstract void _groupeAgeCles(List<Long> o);

	public List<Long> getGroupeAgeCles() {
		return groupeAgeCles;
	}

	public void setGroupeAgeCles(List<Long> groupeAgeCles) {
		this.groupeAgeCles = groupeAgeCles;
		this.groupeAgeClesCouverture.dejaInitialise = true;
	}
	public CliniqueMedicale addGroupeAgeCles(Long...objets) {
		for(Long o : objets) {
			addGroupeAgeCles(o);
		}
		return (CliniqueMedicale)this;
	}
	public CliniqueMedicale addGroupeAgeCles(Long o) {
		if(o != null && !groupeAgeCles.contains(o))
			this.groupeAgeCles.add(o);
		return (CliniqueMedicale)this;
	}
	public CliniqueMedicale setGroupeAgeCles(JsonArray objets) {
		groupeAgeCles.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addGroupeAgeCles(o);
		}
		return (CliniqueMedicale)this;
	}
	public CliniqueMedicale addGroupeAgeCles(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addGroupeAgeCles(p);
		}
		return (CliniqueMedicale)this;
	}
	protected CliniqueMedicale groupeAgeClesInit() {
		if(!groupeAgeClesCouverture.dejaInitialise) {
			_groupeAgeCles(groupeAgeCles);
		}
		groupeAgeClesCouverture.dejaInitialise(true);
		return (CliniqueMedicale)this;
	}

	public List<Long> solrGroupeAgeCles() {
		return groupeAgeCles;
	}

	public String strGroupeAgeCles() {
		return groupeAgeCles == null ? "" : groupeAgeCles.toString();
	}

	public String jsonGroupeAgeCles() {
		return groupeAgeCles == null ? "" : groupeAgeCles.toString();
	}

	public String nomAffichageGroupeAgeCles() {
		return "NomAffichage.enUS: ";
	}

	public String htmTooltipGroupeAgeCles() {
		return null;
	}

	public String htmGroupeAgeCles() {
		return groupeAgeCles == null ? "" : StringEscapeUtils.escapeHtml4(strGroupeAgeCles());
	}

	//////////////
	// blocCles //
	//////////////

	/**	L'entité « blocCles »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> blocCles = new java.util.ArrayList<java.lang.Long>();
	@JsonIgnore
	public Couverture<List<Long>> blocClesCouverture = new Couverture<List<Long>>().p(this).c(List.class).var("blocCles").o(blocCles);

	/**	<br/>L'entité « blocCles »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.clinique.CliniqueMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:blocCles">Trouver l'entité blocCles dans Solr</a>
	 * <br/>
	 * @param blocCles est l'entité déjà construit. 
	 **/
	protected abstract void _blocCles(List<Long> o);

	public List<Long> getBlocCles() {
		return blocCles;
	}

	public void setBlocCles(List<Long> blocCles) {
		this.blocCles = blocCles;
		this.blocClesCouverture.dejaInitialise = true;
	}
	public CliniqueMedicale addBlocCles(Long...objets) {
		for(Long o : objets) {
			addBlocCles(o);
		}
		return (CliniqueMedicale)this;
	}
	public CliniqueMedicale addBlocCles(Long o) {
		if(o != null && !blocCles.contains(o))
			this.blocCles.add(o);
		return (CliniqueMedicale)this;
	}
	public CliniqueMedicale setBlocCles(JsonArray objets) {
		blocCles.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addBlocCles(o);
		}
		return (CliniqueMedicale)this;
	}
	public CliniqueMedicale addBlocCles(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addBlocCles(p);
		}
		return (CliniqueMedicale)this;
	}
	protected CliniqueMedicale blocClesInit() {
		if(!blocClesCouverture.dejaInitialise) {
			_blocCles(blocCles);
		}
		blocClesCouverture.dejaInitialise(true);
		return (CliniqueMedicale)this;
	}

	public List<Long> solrBlocCles() {
		return blocCles;
	}

	public String strBlocCles() {
		return blocCles == null ? "" : blocCles.toString();
	}

	public String jsonBlocCles() {
		return blocCles == null ? "" : blocCles.toString();
	}

	public String nomAffichageBlocCles() {
		return "NomAffichage.enUS: ";
	}

	public String htmTooltipBlocCles() {
		return null;
	}

	public String htmBlocCles() {
		return blocCles == null ? "" : StringEscapeUtils.escapeHtml4(strBlocCles());
	}

	////////////////
	// enfantCles //
	////////////////

	/**	L'entité « enfantCles »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> enfantCles = new java.util.ArrayList<java.lang.Long>();
	@JsonIgnore
	public Couverture<List<Long>> enfantClesCouverture = new Couverture<List<Long>>().p(this).c(List.class).var("enfantCles").o(enfantCles);

	/**	<br/>L'entité « enfantCles »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.clinique.CliniqueMedicale&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:enfantCles">Trouver l'entité enfantCles dans Solr</a>
	 * <br/>
	 * @param enfantCles est l'entité déjà construit. 
	 **/
	protected abstract void _enfantCles(List<Long> o);

	public List<Long> getEnfantCles() {
		return enfantCles;
	}

	public void setEnfantCles(List<Long> enfantCles) {
		this.enfantCles = enfantCles;
		this.enfantClesCouverture.dejaInitialise = true;
	}
	public CliniqueMedicale addEnfantCles(Long...objets) {
		for(Long o : objets) {
			addEnfantCles(o);
		}
		return (CliniqueMedicale)this;
	}
	public CliniqueMedicale addEnfantCles(Long o) {
		if(o != null && !enfantCles.contains(o))
			this.enfantCles.add(o);
		return (CliniqueMedicale)this;
	}
	public CliniqueMedicale setEnfantCles(JsonArray objets) {
		enfantCles.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addEnfantCles(o);
		}
		return (CliniqueMedicale)this;
	}
	public CliniqueMedicale addEnfantCles(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addEnfantCles(p);
		}
		return (CliniqueMedicale)this;
	}
	protected CliniqueMedicale enfantClesInit() {
		if(!enfantClesCouverture.dejaInitialise) {
			_enfantCles(enfantCles);
		}
		enfantClesCouverture.dejaInitialise(true);
		return (CliniqueMedicale)this;
	}

	public List<Long> solrEnfantCles() {
		return enfantCles;
	}

	public String strEnfantCles() {
		return enfantCles == null ? "" : enfantCles.toString();
	}

	public String jsonEnfantCles() {
		return enfantCles == null ? "" : enfantCles.toString();
	}

	public String nomAffichageEnfantCles() {
		return "NomAffichage.enUS: ";
	}

	public String htmTooltipEnfantCles() {
		return null;
	}

	public String htmEnfantCles() {
		return enfantCles == null ? "" : StringEscapeUtils.escapeHtml4(strEnfantCles());
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
					a("onchange", "patchCliniqueMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setCliniqueNom', $(this).val(), function() { ajouterLueur($('#", classeApiMethodeMethode, "_cliniqueNom')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_cliniqueNom')); }); ");
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
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_cliniqueNom')); $('#", classeApiMethodeMethode, "_cliniqueNom').val(null); patchCliniqueMedicaleVal([{ name: 'fq', value: 'pk:' + $('#CliniqueMedicaleForm :input[name=pk]').val() }], 'setCliniqueNom', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_cliniqueNom')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_cliniqueNom')); }); ")
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
					a("onchange", "patchCliniqueMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setCliniqueNumeroTelephone', $(this).val(), function() { ajouterLueur($('#", classeApiMethodeMethode, "_cliniqueNumeroTelephone')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_cliniqueNumeroTelephone')); }); ");
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
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_cliniqueNumeroTelephone')); $('#", classeApiMethodeMethode, "_cliniqueNumeroTelephone').val(null); patchCliniqueMedicaleVal([{ name: 'fq', value: 'pk:' + $('#CliniqueMedicaleForm :input[name=pk]').val() }], 'setCliniqueNumeroTelephone', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_cliniqueNumeroTelephone')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_cliniqueNumeroTelephone')); }); ")
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
					a("onchange", "patchCliniqueMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setCliniqueAdministrateurNom', $(this).val(), function() { ajouterLueur($('#", classeApiMethodeMethode, "_cliniqueAdministrateurNom')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_cliniqueAdministrateurNom')); }); ");
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
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_cliniqueAdministrateurNom')); $('#", classeApiMethodeMethode, "_cliniqueAdministrateurNom').val(null); patchCliniqueMedicaleVal([{ name: 'fq', value: 'pk:' + $('#CliniqueMedicaleForm :input[name=pk]').val() }], 'setCliniqueAdministrateurNom', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_cliniqueAdministrateurNom')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_cliniqueAdministrateurNom')); }); ")
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
					a("onchange", "patchCliniqueMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setCliniqueMailDe', $(this).val(), function() { ajouterLueur($('#", classeApiMethodeMethode, "_cliniqueMailDe')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_cliniqueMailDe')); }); ");
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
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_cliniqueMailDe')); $('#", classeApiMethodeMethode, "_cliniqueMailDe').val(null); patchCliniqueMedicaleVal([{ name: 'fq', value: 'pk:' + $('#CliniqueMedicaleForm :input[name=pk]').val() }], 'setCliniqueMailDe', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_cliniqueMailDe')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_cliniqueMailDe')); }); ")
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
					a("onchange", "patchCliniqueMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setCliniqueMailA', $(this).val(), function() { ajouterLueur($('#", classeApiMethodeMethode, "_cliniqueMailA')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_cliniqueMailA')); }); ");
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
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_cliniqueMailA')); $('#", classeApiMethodeMethode, "_cliniqueMailA').val(null); patchCliniqueMedicaleVal([{ name: 'fq', value: 'pk:' + $('#CliniqueMedicaleForm :input[name=pk]').val() }], 'setCliniqueMailA', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_cliniqueMailA')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_cliniqueMailA')); }); ")
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
					a("onchange", "patchCliniqueMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setCliniqueEmplacement', $(this).val(), function() { ajouterLueur($('#", classeApiMethodeMethode, "_cliniqueEmplacement')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_cliniqueEmplacement')); }); ");
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
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_cliniqueEmplacement')); $('#", classeApiMethodeMethode, "_cliniqueEmplacement').val(null); patchCliniqueMedicaleVal([{ name: 'fq', value: 'pk:' + $('#CliniqueMedicaleForm :input[name=pk]').val() }], 'setCliniqueEmplacement', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_cliniqueEmplacement')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_cliniqueEmplacement')); }); ")
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
					a("onchange", "patchCliniqueMedicaleVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setCliniqueAddresse', $(this).val(), function() { ajouterLueur($('#", classeApiMethodeMethode, "_cliniqueAddresse')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_cliniqueAddresse')); }); ");
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
										.a("onclick", "enleverLueur($('#", classeApiMethodeMethode, "_cliniqueAddresse')); $('#", classeApiMethodeMethode, "_cliniqueAddresse').val(null); patchCliniqueMedicaleVal([{ name: 'fq', value: 'pk:' + $('#CliniqueMedicaleForm :input[name=pk]').val() }], 'setCliniqueAddresse', null, function() { ajouterLueur($('#", classeApiMethodeMethode, "_cliniqueAddresse')); }, function() { ajouterErreur($('#", classeApiMethodeMethode, "_cliniqueAddresse')); }); ")
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
		anneeClesInit();
		saisonClesInit();
		sessionClesInit();
		groupeAgeClesInit();
		blocClesInit();
		enfantClesInit();
		medicaleTriInit();
		cliniqueTriInit();
		cliniqueNomInit();
		cliniqueNumeroTelephoneInit();
		cliniqueAdministrateurNomInit();
		cliniqueMailDeInit();
		cliniqueMailAInit();
		cliniqueEmplacementInit();
		cliniqueAddresseInit();
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
			case "anneeCles":
				return oCliniqueMedicale.anneeCles;
			case "saisonCles":
				return oCliniqueMedicale.saisonCles;
			case "sessionCles":
				return oCliniqueMedicale.sessionCles;
			case "groupeAgeCles":
				return oCliniqueMedicale.groupeAgeCles;
			case "blocCles":
				return oCliniqueMedicale.blocCles;
			case "enfantCles":
				return oCliniqueMedicale.enfantCles;
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
			case "cliniqueMailDe":
				return oCliniqueMedicale.cliniqueMailDe;
			case "cliniqueMailA":
				return oCliniqueMedicale.cliniqueMailA;
			case "cliniqueEmplacement":
				return oCliniqueMedicale.cliniqueEmplacement;
			case "cliniqueAddresse":
				return oCliniqueMedicale.cliniqueAddresse;
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
				sauvegardesCliniqueMedicale.add(var);
				return val;
			case "cliniqueNumeroTelephone":
				if(val != null)
					setCliniqueNumeroTelephone(val);
				sauvegardesCliniqueMedicale.add(var);
				return val;
			case "cliniqueAdministrateurNom":
				if(val != null)
					setCliniqueAdministrateurNom(val);
				sauvegardesCliniqueMedicale.add(var);
				return val;
			case "cliniqueMailDe":
				if(val != null)
					setCliniqueMailDe(val);
				sauvegardesCliniqueMedicale.add(var);
				return val;
			case "cliniqueMailA":
				if(val != null)
					setCliniqueMailA(val);
				sauvegardesCliniqueMedicale.add(var);
				return val;
			case "cliniqueEmplacement":
				if(val != null)
					setCliniqueEmplacement(val);
				sauvegardesCliniqueMedicale.add(var);
				return val;
			case "cliniqueAddresse":
				if(val != null)
					setCliniqueAddresse(val);
				sauvegardesCliniqueMedicale.add(var);
				return val;
			default:
				return super.definirCluster(var, val);
		}
	}

	/////////////////
	// sauvegardes //
	/////////////////

	protected List<String> sauvegardesCliniqueMedicale = new ArrayList<String>();

	/////////////
	// peupler //
	/////////////

	@Override public void peuplerPourClasse(SolrDocument solrDocument) {
		peuplerCliniqueMedicale(solrDocument);
	}
	public void peuplerCliniqueMedicale(SolrDocument solrDocument) {
		CliniqueMedicale oCliniqueMedicale = (CliniqueMedicale)this;
		sauvegardesCliniqueMedicale = (List<String>)solrDocument.get("sauvegardesCliniqueMedicale_stored_strings");
		if(sauvegardesCliniqueMedicale != null) {

			if(sauvegardesCliniqueMedicale.contains("cliniqueCle")) {
				Long cliniqueCle = (Long)solrDocument.get("cliniqueCle_stored_long");
				if(cliniqueCle != null)
					oCliniqueMedicale.setCliniqueCle(cliniqueCle);
			}

			if(sauvegardesCliniqueMedicale.contains("anneeCles")) {
				List<Long> anneeCles = (List<Long>)solrDocument.get("anneeCles_stored_longs");
				if(anneeCles != null)
					oCliniqueMedicale.anneeCles.addAll(anneeCles);
			}

			if(sauvegardesCliniqueMedicale.contains("saisonCles")) {
				List<Long> saisonCles = (List<Long>)solrDocument.get("saisonCles_stored_longs");
				if(saisonCles != null)
					oCliniqueMedicale.saisonCles.addAll(saisonCles);
			}

			if(sauvegardesCliniqueMedicale.contains("sessionCles")) {
				List<Long> sessionCles = (List<Long>)solrDocument.get("sessionCles_stored_longs");
				if(sessionCles != null)
					oCliniqueMedicale.sessionCles.addAll(sessionCles);
			}

			if(sauvegardesCliniqueMedicale.contains("groupeAgeCles")) {
				List<Long> groupeAgeCles = (List<Long>)solrDocument.get("groupeAgeCles_stored_longs");
				if(groupeAgeCles != null)
					oCliniqueMedicale.groupeAgeCles.addAll(groupeAgeCles);
			}

			if(sauvegardesCliniqueMedicale.contains("blocCles")) {
				List<Long> blocCles = (List<Long>)solrDocument.get("blocCles_stored_longs");
				if(blocCles != null)
					oCliniqueMedicale.blocCles.addAll(blocCles);
			}

			if(sauvegardesCliniqueMedicale.contains("enfantCles")) {
				List<Long> enfantCles = (List<Long>)solrDocument.get("enfantCles_stored_longs");
				if(enfantCles != null)
					oCliniqueMedicale.enfantCles.addAll(enfantCles);
			}

			if(sauvegardesCliniqueMedicale.contains("medicaleTri")) {
				Integer medicaleTri = (Integer)solrDocument.get("medicaleTri_stored_int");
				if(medicaleTri != null)
					oCliniqueMedicale.setMedicaleTri(medicaleTri);
			}

			if(sauvegardesCliniqueMedicale.contains("cliniqueTri")) {
				Integer cliniqueTri = (Integer)solrDocument.get("cliniqueTri_stored_int");
				if(cliniqueTri != null)
					oCliniqueMedicale.setCliniqueTri(cliniqueTri);
			}

			if(sauvegardesCliniqueMedicale.contains("cliniqueNom")) {
				String cliniqueNom = (String)solrDocument.get("cliniqueNom_stored_string");
				if(cliniqueNom != null)
					oCliniqueMedicale.setCliniqueNom(cliniqueNom);
			}

			if(sauvegardesCliniqueMedicale.contains("cliniqueNumeroTelephone")) {
				String cliniqueNumeroTelephone = (String)solrDocument.get("cliniqueNumeroTelephone_stored_string");
				if(cliniqueNumeroTelephone != null)
					oCliniqueMedicale.setCliniqueNumeroTelephone(cliniqueNumeroTelephone);
			}

			if(sauvegardesCliniqueMedicale.contains("cliniqueAdministrateurNom")) {
				String cliniqueAdministrateurNom = (String)solrDocument.get("cliniqueAdministrateurNom_stored_string");
				if(cliniqueAdministrateurNom != null)
					oCliniqueMedicale.setCliniqueAdministrateurNom(cliniqueAdministrateurNom);
			}

			if(sauvegardesCliniqueMedicale.contains("cliniqueMailDe")) {
				String cliniqueMailDe = (String)solrDocument.get("cliniqueMailDe_stored_string");
				if(cliniqueMailDe != null)
					oCliniqueMedicale.setCliniqueMailDe(cliniqueMailDe);
			}

			if(sauvegardesCliniqueMedicale.contains("cliniqueMailA")) {
				String cliniqueMailA = (String)solrDocument.get("cliniqueMailA_stored_string");
				if(cliniqueMailA != null)
					oCliniqueMedicale.setCliniqueMailA(cliniqueMailA);
			}

			if(sauvegardesCliniqueMedicale.contains("cliniqueEmplacement")) {
				String cliniqueEmplacement = (String)solrDocument.get("cliniqueEmplacement_stored_string");
				if(cliniqueEmplacement != null)
					oCliniqueMedicale.setCliniqueEmplacement(cliniqueEmplacement);
			}

			if(sauvegardesCliniqueMedicale.contains("cliniqueAddresse")) {
				String cliniqueAddresse = (String)solrDocument.get("cliniqueAddresse_stored_string");
				if(cliniqueAddresse != null)
					oCliniqueMedicale.setCliniqueAddresse(cliniqueAddresse);
			}

			if(sauvegardesCliniqueMedicale.contains("cliniqueNomCourt")) {
				String cliniqueNomCourt = (String)solrDocument.get("cliniqueNomCourt_stored_string");
				if(cliniqueNomCourt != null)
					oCliniqueMedicale.setCliniqueNomCourt(cliniqueNomCourt);
			}

			if(sauvegardesCliniqueMedicale.contains("cliniqueNomComplet")) {
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
		if(sauvegardesCliniqueMedicale != null)
			document.addField("sauvegardesCliniqueMedicale_stored_strings", sauvegardesCliniqueMedicale);

		if(cliniqueCle != null) {
			document.addField("cliniqueCle_indexed_long", cliniqueCle);
			document.addField("cliniqueCle_stored_long", cliniqueCle);
		}
		if(anneeCles != null) {
			for(java.lang.Long o : anneeCles) {
				document.addField("anneeCles_indexed_longs", o);
			}
			for(java.lang.Long o : anneeCles) {
				document.addField("anneeCles_stored_longs", o);
			}
		}
		if(saisonCles != null) {
			for(java.lang.Long o : saisonCles) {
				document.addField("saisonCles_indexed_longs", o);
			}
			for(java.lang.Long o : saisonCles) {
				document.addField("saisonCles_stored_longs", o);
			}
		}
		if(sessionCles != null) {
			for(java.lang.Long o : sessionCles) {
				document.addField("sessionCles_indexed_longs", o);
			}
			for(java.lang.Long o : sessionCles) {
				document.addField("sessionCles_stored_longs", o);
			}
		}
		if(groupeAgeCles != null) {
			for(java.lang.Long o : groupeAgeCles) {
				document.addField("groupeAgeCles_indexed_longs", o);
			}
			for(java.lang.Long o : groupeAgeCles) {
				document.addField("groupeAgeCles_stored_longs", o);
			}
		}
		if(blocCles != null) {
			for(java.lang.Long o : blocCles) {
				document.addField("blocCles_indexed_longs", o);
			}
			for(java.lang.Long o : blocCles) {
				document.addField("blocCles_stored_longs", o);
			}
		}
		if(enfantCles != null) {
			for(java.lang.Long o : enfantCles) {
				document.addField("enfantCles_indexed_longs", o);
			}
			for(java.lang.Long o : enfantCles) {
				document.addField("enfantCles_stored_longs", o);
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
			case "anneeCles":
				return "anneeCles_indexed_longs";
			case "saisonCles":
				return "saisonCles_indexed_longs";
			case "sessionCles":
				return "sessionCles_indexed_longs";
			case "groupeAgeCles":
				return "groupeAgeCles_indexed_longs";
			case "blocCles":
				return "blocCles_indexed_longs";
			case "enfantCles":
				return "enfantCles_indexed_longs";
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
			case "cliniqueMailDe":
				return "cliniqueMailDe_indexed_string";
			case "cliniqueMailA":
				return "cliniqueMailA_indexed_string";
			case "cliniqueEmplacement":
				return "cliniqueEmplacement_indexed_string";
			case "cliniqueAddresse":
				return "cliniqueAddresse_indexed_string";
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

		List<Long> anneeCles = (List<Long>)solrDocument.get("anneeCles_stored_longs");
		if(anneeCles != null)
			oCliniqueMedicale.anneeCles.addAll(anneeCles);

		List<Long> saisonCles = (List<Long>)solrDocument.get("saisonCles_stored_longs");
		if(saisonCles != null)
			oCliniqueMedicale.saisonCles.addAll(saisonCles);

		List<Long> sessionCles = (List<Long>)solrDocument.get("sessionCles_stored_longs");
		if(sessionCles != null)
			oCliniqueMedicale.sessionCles.addAll(sessionCles);

		List<Long> groupeAgeCles = (List<Long>)solrDocument.get("groupeAgeCles_stored_longs");
		if(groupeAgeCles != null)
			oCliniqueMedicale.groupeAgeCles.addAll(groupeAgeCles);

		List<Long> blocCles = (List<Long>)solrDocument.get("blocCles_stored_longs");
		if(blocCles != null)
			oCliniqueMedicale.blocCles.addAll(blocCles);

		List<Long> enfantCles = (List<Long>)solrDocument.get("enfantCles_stored_longs");
		if(enfantCles != null)
			oCliniqueMedicale.enfantCles.addAll(enfantCles);

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
			if(!Objects.equals(cliniqueMailDe, original.getCliniqueMailDe()))
				requeteApi.addVars("cliniqueMailDe");
			if(!Objects.equals(cliniqueMailA, original.getCliniqueMailA()))
				requeteApi.addVars("cliniqueMailA");
			if(!Objects.equals(cliniqueEmplacement, original.getCliniqueEmplacement()))
				requeteApi.addVars("cliniqueEmplacement");
			if(!Objects.equals(cliniqueAddresse, original.getCliniqueAddresse()))
				requeteApi.addVars("cliniqueAddresse");
			super.requeteApiCluster();
		}
	}

	//////////////
	// hashCode //
	//////////////

	@Override public int hashCode() {
		return Objects.hash(super.hashCode(), cliniqueNom, cliniqueNumeroTelephone, cliniqueAdministrateurNom, cliniqueMailDe, cliniqueMailA, cliniqueEmplacement, cliniqueAddresse);
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
				&& Objects.equals( cliniqueMailDe, that.cliniqueMailDe )
				&& Objects.equals( cliniqueMailA, that.cliniqueMailA )
				&& Objects.equals( cliniqueEmplacement, that.cliniqueEmplacement )
				&& Objects.equals( cliniqueAddresse, that.cliniqueAddresse );
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
		sb.append( ", cliniqueMailDe: \"" ).append(cliniqueMailDe).append( "\"" );
		sb.append( ", cliniqueMailA: \"" ).append(cliniqueMailA).append( "\"" );
		sb.append( ", cliniqueEmplacement: \"" ).append(cliniqueEmplacement).append( "\"" );
		sb.append( ", cliniqueAddresse: \"" ).append(cliniqueAddresse).append( "\"" );
		sb.append(" }");
		return sb.toString();
	}
}
