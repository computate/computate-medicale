package org.computate.medicale.enUS.clinic;

import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.computate.medicale.enUS.cluster.Cluster;
import java.util.Date;
import org.computate.medicale.enUS.request.api.ApiRequest;
import org.computate.medicale.enUS.context.SiteContextEnUS;
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
import org.computate.medicale.enUS.wrap.Wrap;
import java.math.MathContext;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.computate.medicale.enUS.writer.AllWriter;
import java.util.Set;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.solr.client.solrj.SolrClient;
import java.util.Objects;
import io.vertx.core.json.JsonArray;
import org.apache.solr.common.SolrDocument;
import java.util.List;
import org.computate.medicale.enUS.request.SiteRequestEnUS;
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
 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.clinic.MedicalClinic&fq=classeEtendGen_indexed_boolean:true">Trouver la classe  dans Solr</a>
 * <br/>
 **/
public abstract class MedicalClinicGen<DEV> extends Cluster {
	protected static final Logger LOGGER = LoggerFactory.getLogger(MedicalClinic.class);

	public static final List<String> ROLES = Arrays.asList("SiteAdmin");
	public static final List<String> ROLE_READS = Arrays.asList("");

	public static final String MedicalClinic_AName = "a clinic";
	public static final String MedicalClinic_This = "this ";
	public static final String MedicalClinic_ThisName = "this clinic";
	public static final String MedicalClinic_A = "a ";
	public static final String MedicalClinic_TheName = "the clinic";
	public static final String MedicalClinic_NameSingular = "clinic";
	public static final String MedicalClinic_NamePlural = "clinics";
	public static final String MedicalClinic_NameActual = "current clinic";
	public static final String MedicalClinic_AllName = "all the clinics";
	public static final String MedicalClinic_SearchAllNameBy = "search clinics by ";
	public static final String MedicalClinic_ThePluralName = "the clinics";
	public static final String MedicalClinic_NoNameFound = "no clinic found";
	public static final String MedicalClinic_NameVar = "clinic";
	public static final String MedicalClinic_OfName = "of clinic";
	public static final String MedicalClinic_ANameAdjective = "a clinic";
	public static final String MedicalClinic_NameAdjectiveSingular = "clinic";
	public static final String MedicalClinic_NameAdjectivePlural = "clinics";
	public static final String MedicalClinic_Color = "pink";
	public static final String MedicalClinic_IconGroup = "regular";
	public static final String MedicalClinic_IconName = "clinic";

	///////////////
	// clinicKey //
	///////////////

	/**	L'entité « clinicKey »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long clinicKey;
	@JsonIgnore
	public Wrap<Long> clinicKeyWrap = new Wrap<Long>().p(this).c(Long.class).var("clinicKey").o(clinicKey);

	/**	<br/>L'entité « clinicKey »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.clinic.MedicalClinic&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinicKey">Trouver l'entité clinicKey dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _clinicKey(Wrap<Long> c);

	public Long getClinicKey() {
		return clinicKey;
	}

	public void setClinicKey(Long clinicKey) {
		this.clinicKey = clinicKey;
		this.clinicKeyWrap.alreadyInitialized = true;
	}
	public MedicalClinic setClinicKey(String o) {
		if(NumberUtils.isParsable(o))
			this.clinicKey = Long.parseLong(o);
		this.clinicKeyWrap.alreadyInitialized = true;
		return (MedicalClinic)this;
	}
	protected MedicalClinic clinicKeyInit() {
		if(!clinicKeyWrap.alreadyInitialized) {
			_clinicKey(clinicKeyWrap);
			if(clinicKey == null)
				setClinicKey(clinicKeyWrap.o);
		}
		clinicKeyWrap.alreadyInitialized(true);
		return (MedicalClinic)this;
	}

	public Long solrClinicKey() {
		return clinicKey;
	}

	public String strClinicKey() {
		return clinicKey == null ? "" : clinicKey.toString();
	}

	public String jsonClinicKey() {
		return clinicKey == null ? "" : clinicKey.toString();
	}

	public String nomAffichageClinicKey() {
		return "key";
	}

	public String htmTooltipClinicKey() {
		return null;
	}

	public String htmClinicKey() {
		return clinicKey == null ? "" : StringEscapeUtils.escapeHtml4(strClinicKey());
	}

	//////////////
	// yearKeys //
	//////////////

	/**	L'entité « yearKeys »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> yearKeys = new java.util.ArrayList<java.lang.Long>();
	@JsonIgnore
	public Wrap<List<Long>> yearKeysWrap = new Wrap<List<Long>>().p(this).c(List.class).var("yearKeys").o(yearKeys);

	/**	<br/>L'entité « yearKeys »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.clinic.MedicalClinic&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:yearKeys">Trouver l'entité yearKeys dans Solr</a>
	 * <br/>
	 * @param yearKeys est l'entité déjà construit. 
	 **/
	protected abstract void _yearKeys(List<Long> o);

	public List<Long> getYearKeys() {
		return yearKeys;
	}

	public void setYearKeys(List<Long> yearKeys) {
		this.yearKeys = yearKeys;
		this.yearKeysWrap.alreadyInitialized = true;
	}
	public MedicalClinic addYearKeys(Long...objets) {
		for(Long o : objets) {
			addYearKeys(o);
		}
		return (MedicalClinic)this;
	}
	public MedicalClinic addYearKeys(Long o) {
		if(o != null && !yearKeys.contains(o))
			this.yearKeys.add(o);
		return (MedicalClinic)this;
	}
	public MedicalClinic setYearKeys(JsonArray objets) {
		yearKeys.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addYearKeys(o);
		}
		return (MedicalClinic)this;
	}
	public MedicalClinic addYearKeys(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addYearKeys(p);
		}
		return (MedicalClinic)this;
	}
	protected MedicalClinic yearKeysInit() {
		if(!yearKeysWrap.alreadyInitialized) {
			_yearKeys(yearKeys);
		}
		yearKeysWrap.alreadyInitialized(true);
		return (MedicalClinic)this;
	}

	public List<Long> solrYearKeys() {
		return yearKeys;
	}

	public String strYearKeys() {
		return yearKeys == null ? "" : yearKeys.toString();
	}

	public String jsonYearKeys() {
		return yearKeys == null ? "" : yearKeys.toString();
	}

	public String nomAffichageYearKeys() {
		return "years";
	}

	public String htmTooltipYearKeys() {
		return null;
	}

	public String htmYearKeys() {
		return yearKeys == null ? "" : StringEscapeUtils.escapeHtml4(strYearKeys());
	}

	public void inputYearKeys(String classApiMethodMethod) {
		MedicalClinic s = (MedicalClinic)this;
	}

	public void htmYearKeys(String classApiMethodMethod) {
		MedicalClinic s = (MedicalClinic)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			if("Page".equals(classApiMethodMethod)) {
				{ e("div").a("class", "w3-padding ").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pink ").f();
							e("label").a("class", "").f().sx("years").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row  ").f();
							{ e("div").a("class", "w3-cell ").f();
								{ e("div").a("class", "w3-rest ").f();
									e("span").f().sx(strYearKeys()).g("span");
								} g("div");
							} g("div");
						} g("div");
					} g("div");
				} g("div");
			}
		} g("div");
	}

	////////////////
	// seasonKeys //
	////////////////

	/**	L'entité « seasonKeys »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> seasonKeys = new java.util.ArrayList<java.lang.Long>();
	@JsonIgnore
	public Wrap<List<Long>> seasonKeysWrap = new Wrap<List<Long>>().p(this).c(List.class).var("seasonKeys").o(seasonKeys);

	/**	<br/>L'entité « seasonKeys »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.clinic.MedicalClinic&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:seasonKeys">Trouver l'entité seasonKeys dans Solr</a>
	 * <br/>
	 * @param seasonKeys est l'entité déjà construit. 
	 **/
	protected abstract void _seasonKeys(List<Long> o);

	public List<Long> getSeasonKeys() {
		return seasonKeys;
	}

	public void setSeasonKeys(List<Long> seasonKeys) {
		this.seasonKeys = seasonKeys;
		this.seasonKeysWrap.alreadyInitialized = true;
	}
	public MedicalClinic addSeasonKeys(Long...objets) {
		for(Long o : objets) {
			addSeasonKeys(o);
		}
		return (MedicalClinic)this;
	}
	public MedicalClinic addSeasonKeys(Long o) {
		if(o != null && !seasonKeys.contains(o))
			this.seasonKeys.add(o);
		return (MedicalClinic)this;
	}
	public MedicalClinic setSeasonKeys(JsonArray objets) {
		seasonKeys.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addSeasonKeys(o);
		}
		return (MedicalClinic)this;
	}
	public MedicalClinic addSeasonKeys(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addSeasonKeys(p);
		}
		return (MedicalClinic)this;
	}
	protected MedicalClinic seasonKeysInit() {
		if(!seasonKeysWrap.alreadyInitialized) {
			_seasonKeys(seasonKeys);
		}
		seasonKeysWrap.alreadyInitialized(true);
		return (MedicalClinic)this;
	}

	public List<Long> solrSeasonKeys() {
		return seasonKeys;
	}

	public String strSeasonKeys() {
		return seasonKeys == null ? "" : seasonKeys.toString();
	}

	public String jsonSeasonKeys() {
		return seasonKeys == null ? "" : seasonKeys.toString();
	}

	public String nomAffichageSeasonKeys() {
		return "";
	}

	public String htmTooltipSeasonKeys() {
		return null;
	}

	public String htmSeasonKeys() {
		return seasonKeys == null ? "" : StringEscapeUtils.escapeHtml4(strSeasonKeys());
	}

	/////////////////
	// sessionKeys //
	/////////////////

	/**	L'entité « sessionKeys »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> sessionKeys = new java.util.ArrayList<java.lang.Long>();
	@JsonIgnore
	public Wrap<List<Long>> sessionKeysWrap = new Wrap<List<Long>>().p(this).c(List.class).var("sessionKeys").o(sessionKeys);

	/**	<br/>L'entité « sessionKeys »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.clinic.MedicalClinic&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:sessionKeys">Trouver l'entité sessionKeys dans Solr</a>
	 * <br/>
	 * @param sessionKeys est l'entité déjà construit. 
	 **/
	protected abstract void _sessionKeys(List<Long> o);

	public List<Long> getSessionKeys() {
		return sessionKeys;
	}

	public void setSessionKeys(List<Long> sessionKeys) {
		this.sessionKeys = sessionKeys;
		this.sessionKeysWrap.alreadyInitialized = true;
	}
	public MedicalClinic addSessionKeys(Long...objets) {
		for(Long o : objets) {
			addSessionKeys(o);
		}
		return (MedicalClinic)this;
	}
	public MedicalClinic addSessionKeys(Long o) {
		if(o != null && !sessionKeys.contains(o))
			this.sessionKeys.add(o);
		return (MedicalClinic)this;
	}
	public MedicalClinic setSessionKeys(JsonArray objets) {
		sessionKeys.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addSessionKeys(o);
		}
		return (MedicalClinic)this;
	}
	public MedicalClinic addSessionKeys(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addSessionKeys(p);
		}
		return (MedicalClinic)this;
	}
	protected MedicalClinic sessionKeysInit() {
		if(!sessionKeysWrap.alreadyInitialized) {
			_sessionKeys(sessionKeys);
		}
		sessionKeysWrap.alreadyInitialized(true);
		return (MedicalClinic)this;
	}

	public List<Long> solrSessionKeys() {
		return sessionKeys;
	}

	public String strSessionKeys() {
		return sessionKeys == null ? "" : sessionKeys.toString();
	}

	public String jsonSessionKeys() {
		return sessionKeys == null ? "" : sessionKeys.toString();
	}

	public String nomAffichageSessionKeys() {
		return "";
	}

	public String htmTooltipSessionKeys() {
		return null;
	}

	public String htmSessionKeys() {
		return sessionKeys == null ? "" : StringEscapeUtils.escapeHtml4(strSessionKeys());
	}

	//////////////////
	// ageGroupKeys //
	//////////////////

	/**	L'entité « ageGroupKeys »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> ageGroupKeys = new java.util.ArrayList<java.lang.Long>();
	@JsonIgnore
	public Wrap<List<Long>> ageGroupKeysWrap = new Wrap<List<Long>>().p(this).c(List.class).var("ageGroupKeys").o(ageGroupKeys);

	/**	<br/>L'entité « ageGroupKeys »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.clinic.MedicalClinic&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:ageGroupKeys">Trouver l'entité ageGroupKeys dans Solr</a>
	 * <br/>
	 * @param ageGroupKeys est l'entité déjà construit. 
	 **/
	protected abstract void _ageGroupKeys(List<Long> o);

	public List<Long> getAgeGroupKeys() {
		return ageGroupKeys;
	}

	public void setAgeGroupKeys(List<Long> ageGroupKeys) {
		this.ageGroupKeys = ageGroupKeys;
		this.ageGroupKeysWrap.alreadyInitialized = true;
	}
	public MedicalClinic addAgeGroupKeys(Long...objets) {
		for(Long o : objets) {
			addAgeGroupKeys(o);
		}
		return (MedicalClinic)this;
	}
	public MedicalClinic addAgeGroupKeys(Long o) {
		if(o != null && !ageGroupKeys.contains(o))
			this.ageGroupKeys.add(o);
		return (MedicalClinic)this;
	}
	public MedicalClinic setAgeGroupKeys(JsonArray objets) {
		ageGroupKeys.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addAgeGroupKeys(o);
		}
		return (MedicalClinic)this;
	}
	public MedicalClinic addAgeGroupKeys(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addAgeGroupKeys(p);
		}
		return (MedicalClinic)this;
	}
	protected MedicalClinic ageGroupKeysInit() {
		if(!ageGroupKeysWrap.alreadyInitialized) {
			_ageGroupKeys(ageGroupKeys);
		}
		ageGroupKeysWrap.alreadyInitialized(true);
		return (MedicalClinic)this;
	}

	public List<Long> solrAgeGroupKeys() {
		return ageGroupKeys;
	}

	public String strAgeGroupKeys() {
		return ageGroupKeys == null ? "" : ageGroupKeys.toString();
	}

	public String jsonAgeGroupKeys() {
		return ageGroupKeys == null ? "" : ageGroupKeys.toString();
	}

	public String nomAffichageAgeGroupKeys() {
		return "";
	}

	public String htmTooltipAgeGroupKeys() {
		return null;
	}

	public String htmAgeGroupKeys() {
		return ageGroupKeys == null ? "" : StringEscapeUtils.escapeHtml4(strAgeGroupKeys());
	}

	///////////////
	// blockKeys //
	///////////////

	/**	L'entité « blockKeys »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> blockKeys = new java.util.ArrayList<java.lang.Long>();
	@JsonIgnore
	public Wrap<List<Long>> blockKeysWrap = new Wrap<List<Long>>().p(this).c(List.class).var("blockKeys").o(blockKeys);

	/**	<br/>L'entité « blockKeys »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.clinic.MedicalClinic&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:blockKeys">Trouver l'entité blockKeys dans Solr</a>
	 * <br/>
	 * @param blockKeys est l'entité déjà construit. 
	 **/
	protected abstract void _blockKeys(List<Long> o);

	public List<Long> getBlockKeys() {
		return blockKeys;
	}

	public void setBlockKeys(List<Long> blockKeys) {
		this.blockKeys = blockKeys;
		this.blockKeysWrap.alreadyInitialized = true;
	}
	public MedicalClinic addBlockKeys(Long...objets) {
		for(Long o : objets) {
			addBlockKeys(o);
		}
		return (MedicalClinic)this;
	}
	public MedicalClinic addBlockKeys(Long o) {
		if(o != null && !blockKeys.contains(o))
			this.blockKeys.add(o);
		return (MedicalClinic)this;
	}
	public MedicalClinic setBlockKeys(JsonArray objets) {
		blockKeys.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addBlockKeys(o);
		}
		return (MedicalClinic)this;
	}
	public MedicalClinic addBlockKeys(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addBlockKeys(p);
		}
		return (MedicalClinic)this;
	}
	protected MedicalClinic blockKeysInit() {
		if(!blockKeysWrap.alreadyInitialized) {
			_blockKeys(blockKeys);
		}
		blockKeysWrap.alreadyInitialized(true);
		return (MedicalClinic)this;
	}

	public List<Long> solrBlockKeys() {
		return blockKeys;
	}

	public String strBlockKeys() {
		return blockKeys == null ? "" : blockKeys.toString();
	}

	public String jsonBlockKeys() {
		return blockKeys == null ? "" : blockKeys.toString();
	}

	public String nomAffichageBlockKeys() {
		return "";
	}

	public String htmTooltipBlockKeys() {
		return null;
	}

	public String htmBlockKeys() {
		return blockKeys == null ? "" : StringEscapeUtils.escapeHtml4(strBlockKeys());
	}

	///////////////
	// childKeys //
	///////////////

	/**	L'entité « childKeys »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> childKeys = new java.util.ArrayList<java.lang.Long>();
	@JsonIgnore
	public Wrap<List<Long>> childKeysWrap = new Wrap<List<Long>>().p(this).c(List.class).var("childKeys").o(childKeys);

	/**	<br/>L'entité « childKeys »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.clinic.MedicalClinic&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:childKeys">Trouver l'entité childKeys dans Solr</a>
	 * <br/>
	 * @param childKeys est l'entité déjà construit. 
	 **/
	protected abstract void _childKeys(List<Long> o);

	public List<Long> getChildKeys() {
		return childKeys;
	}

	public void setChildKeys(List<Long> childKeys) {
		this.childKeys = childKeys;
		this.childKeysWrap.alreadyInitialized = true;
	}
	public MedicalClinic addChildKeys(Long...objets) {
		for(Long o : objets) {
			addChildKeys(o);
		}
		return (MedicalClinic)this;
	}
	public MedicalClinic addChildKeys(Long o) {
		if(o != null && !childKeys.contains(o))
			this.childKeys.add(o);
		return (MedicalClinic)this;
	}
	public MedicalClinic setChildKeys(JsonArray objets) {
		childKeys.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addChildKeys(o);
		}
		return (MedicalClinic)this;
	}
	public MedicalClinic addChildKeys(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addChildKeys(p);
		}
		return (MedicalClinic)this;
	}
	protected MedicalClinic childKeysInit() {
		if(!childKeysWrap.alreadyInitialized) {
			_childKeys(childKeys);
		}
		childKeysWrap.alreadyInitialized(true);
		return (MedicalClinic)this;
	}

	public List<Long> solrChildKeys() {
		return childKeys;
	}

	public String strChildKeys() {
		return childKeys == null ? "" : childKeys.toString();
	}

	public String jsonChildKeys() {
		return childKeys == null ? "" : childKeys.toString();
	}

	public String nomAffichageChildKeys() {
		return "";
	}

	public String htmTooltipChildKeys() {
		return null;
	}

	public String htmChildKeys() {
		return childKeys == null ? "" : StringEscapeUtils.escapeHtml4(strChildKeys());
	}

	///////////////////
	// educationSort //
	///////////////////

	/**	L'entité « educationSort »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer educationSort;
	@JsonIgnore
	public Wrap<Integer> educationSortWrap = new Wrap<Integer>().p(this).c(Integer.class).var("educationSort").o(educationSort);

	/**	<br/>L'entité « educationSort »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.clinic.MedicalClinic&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:educationSort">Trouver l'entité educationSort dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _educationSort(Wrap<Integer> c);

	public Integer getEducationSort() {
		return educationSort;
	}

	public void setEducationSort(Integer educationSort) {
		this.educationSort = educationSort;
		this.educationSortWrap.alreadyInitialized = true;
	}
	public MedicalClinic setEducationSort(String o) {
		if(NumberUtils.isParsable(o))
			this.educationSort = Integer.parseInt(o);
		this.educationSortWrap.alreadyInitialized = true;
		return (MedicalClinic)this;
	}
	protected MedicalClinic educationSortInit() {
		if(!educationSortWrap.alreadyInitialized) {
			_educationSort(educationSortWrap);
			if(educationSort == null)
				setEducationSort(educationSortWrap.o);
		}
		educationSortWrap.alreadyInitialized(true);
		return (MedicalClinic)this;
	}

	public Integer solrEducationSort() {
		return educationSort;
	}

	public String strEducationSort() {
		return educationSort == null ? "" : educationSort.toString();
	}

	public String jsonEducationSort() {
		return educationSort == null ? "" : educationSort.toString();
	}

	public String nomAffichageEducationSort() {
		return "";
	}

	public String htmTooltipEducationSort() {
		return null;
	}

	public String htmEducationSort() {
		return educationSort == null ? "" : StringEscapeUtils.escapeHtml4(strEducationSort());
	}

	////////////////
	// clinicSort //
	////////////////

	/**	L'entité « clinicSort »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer clinicSort;
	@JsonIgnore
	public Wrap<Integer> clinicSortWrap = new Wrap<Integer>().p(this).c(Integer.class).var("clinicSort").o(clinicSort);

	/**	<br/>L'entité « clinicSort »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.clinic.MedicalClinic&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinicSort">Trouver l'entité clinicSort dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _clinicSort(Wrap<Integer> c);

	public Integer getClinicSort() {
		return clinicSort;
	}

	public void setClinicSort(Integer clinicSort) {
		this.clinicSort = clinicSort;
		this.clinicSortWrap.alreadyInitialized = true;
	}
	public MedicalClinic setClinicSort(String o) {
		if(NumberUtils.isParsable(o))
			this.clinicSort = Integer.parseInt(o);
		this.clinicSortWrap.alreadyInitialized = true;
		return (MedicalClinic)this;
	}
	protected MedicalClinic clinicSortInit() {
		if(!clinicSortWrap.alreadyInitialized) {
			_clinicSort(clinicSortWrap);
			if(clinicSort == null)
				setClinicSort(clinicSortWrap.o);
		}
		clinicSortWrap.alreadyInitialized(true);
		return (MedicalClinic)this;
	}

	public Integer solrClinicSort() {
		return clinicSort;
	}

	public String strClinicSort() {
		return clinicSort == null ? "" : clinicSort.toString();
	}

	public String jsonClinicSort() {
		return clinicSort == null ? "" : clinicSort.toString();
	}

	public String nomAffichageClinicSort() {
		return "";
	}

	public String htmTooltipClinicSort() {
		return null;
	}

	public String htmClinicSort() {
		return clinicSort == null ? "" : StringEscapeUtils.escapeHtml4(strClinicSort());
	}

	////////////////
	// clinicName //
	////////////////

	/**	L'entité « clinicName »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String clinicName;
	@JsonIgnore
	public Wrap<String> clinicNameWrap = new Wrap<String>().p(this).c(String.class).var("clinicName").o(clinicName);

	/**	<br/>L'entité « clinicName »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.clinic.MedicalClinic&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinicName">Trouver l'entité clinicName dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _clinicName(Wrap<String> c);

	public String getClinicName() {
		return clinicName;
	}

	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
		this.clinicNameWrap.alreadyInitialized = true;
	}
	protected MedicalClinic clinicNameInit() {
		if(!clinicNameWrap.alreadyInitialized) {
			_clinicName(clinicNameWrap);
			if(clinicName == null)
				setClinicName(clinicNameWrap.o);
		}
		clinicNameWrap.alreadyInitialized(true);
		return (MedicalClinic)this;
	}

	public String solrClinicName() {
		return clinicName;
	}

	public String strClinicName() {
		return clinicName == null ? "" : clinicName;
	}

	public String jsonClinicName() {
		return clinicName == null ? "" : clinicName;
	}

	public String nomAffichageClinicName() {
		return "name of the clinic";
	}

	public String htmTooltipClinicName() {
		return null;
	}

	public String htmClinicName() {
		return clinicName == null ? "" : StringEscapeUtils.escapeHtml4(strClinicName());
	}

	public void inputClinicName(String classApiMethodMethod) {
		MedicalClinic s = (MedicalClinic)this;
		{
			e("input")
				.a("type", "text")
				.a("placeholder", "name of the clinic")
				.a("id", classApiMethodMethod, "_clinicName");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setClinicName classMedicalClinic inputMedicalClinic", pk, "ClinicName w3-input w3-border ");
					a("name", "setClinicName");
				} else {
					a("class", "valueClinicName w3-input w3-border classMedicalClinic inputMedicalClinic", pk, "ClinicName w3-input w3-border ");
					a("name", "clinicName");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patchMedicalClinicVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setClinicName', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_clinicName')); }, function() { addError($('#", classApiMethodMethod, "_clinicName')); }); ");
				}
				a("value", strClinicName())
			.fg();

		}
	}

	public void htmClinicName(String classApiMethodMethod) {
		MedicalClinic s = (MedicalClinic)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalClinicClinicName").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pink ").f();
							e("label").a("for", classApiMethodMethod, "_clinicName").a("class", "").f().sx("name of the clinic").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputClinicName(classApiMethodMethod);
							} g("div");
							{
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pink ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_clinicName')); $('#", classApiMethodMethod, "_clinicName').val(null); patchMedicalClinicVal([{ name: 'fq', value: 'pk:' + $('#MedicalClinicForm :input[name=pk]').val() }], 'setClinicName', null, function() { addGlow($('#", classApiMethodMethod, "_clinicName')); }, function() { addError($('#", classApiMethodMethod, "_clinicName')); }); ")
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
	// clinicPhoneNumber //
	///////////////////////

	/**	L'entité « clinicPhoneNumber »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String clinicPhoneNumber;
	@JsonIgnore
	public Wrap<String> clinicPhoneNumberWrap = new Wrap<String>().p(this).c(String.class).var("clinicPhoneNumber").o(clinicPhoneNumber);

	/**	<br/>L'entité « clinicPhoneNumber »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.clinic.MedicalClinic&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinicPhoneNumber">Trouver l'entité clinicPhoneNumber dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _clinicPhoneNumber(Wrap<String> c);

	public String getClinicPhoneNumber() {
		return clinicPhoneNumber;
	}

	public void setClinicPhoneNumber(String clinicPhoneNumber) {
		this.clinicPhoneNumber = clinicPhoneNumber;
		this.clinicPhoneNumberWrap.alreadyInitialized = true;
	}
	protected MedicalClinic clinicPhoneNumberInit() {
		if(!clinicPhoneNumberWrap.alreadyInitialized) {
			_clinicPhoneNumber(clinicPhoneNumberWrap);
			if(clinicPhoneNumber == null)
				setClinicPhoneNumber(clinicPhoneNumberWrap.o);
		}
		clinicPhoneNumberWrap.alreadyInitialized(true);
		return (MedicalClinic)this;
	}

	public String solrClinicPhoneNumber() {
		return clinicPhoneNumber;
	}

	public String strClinicPhoneNumber() {
		return clinicPhoneNumber == null ? "" : clinicPhoneNumber;
	}

	public String jsonClinicPhoneNumber() {
		return clinicPhoneNumber == null ? "" : clinicPhoneNumber;
	}

	public String nomAffichageClinicPhoneNumber() {
		return "phone number";
	}

	public String htmTooltipClinicPhoneNumber() {
		return null;
	}

	public String htmClinicPhoneNumber() {
		return clinicPhoneNumber == null ? "" : StringEscapeUtils.escapeHtml4(strClinicPhoneNumber());
	}

	public void inputClinicPhoneNumber(String classApiMethodMethod) {
		MedicalClinic s = (MedicalClinic)this;
		{
			e("input")
				.a("type", "text")
				.a("placeholder", "phone number")
				.a("id", classApiMethodMethod, "_clinicPhoneNumber");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setClinicPhoneNumber classMedicalClinic inputMedicalClinic", pk, "ClinicPhoneNumber w3-input w3-border ");
					a("name", "setClinicPhoneNumber");
				} else {
					a("class", "valueClinicPhoneNumber w3-input w3-border classMedicalClinic inputMedicalClinic", pk, "ClinicPhoneNumber w3-input w3-border ");
					a("name", "clinicPhoneNumber");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patchMedicalClinicVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setClinicPhoneNumber', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_clinicPhoneNumber')); }, function() { addError($('#", classApiMethodMethod, "_clinicPhoneNumber')); }); ");
				}
				a("value", strClinicPhoneNumber())
			.fg();

		}
	}

	public void htmClinicPhoneNumber(String classApiMethodMethod) {
		MedicalClinic s = (MedicalClinic)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalClinicClinicPhoneNumber").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pink ").f();
							e("label").a("for", classApiMethodMethod, "_clinicPhoneNumber").a("class", "").f().sx("phone number").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputClinicPhoneNumber(classApiMethodMethod);
							} g("div");
							{
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pink ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_clinicPhoneNumber')); $('#", classApiMethodMethod, "_clinicPhoneNumber').val(null); patchMedicalClinicVal([{ name: 'fq', value: 'pk:' + $('#MedicalClinicForm :input[name=pk]').val() }], 'setClinicPhoneNumber', null, function() { addGlow($('#", classApiMethodMethod, "_clinicPhoneNumber')); }, function() { addError($('#", classApiMethodMethod, "_clinicPhoneNumber')); }); ")
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
	// clinicAdministratorName //
	/////////////////////////////

	/**	L'entité « clinicAdministratorName »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String clinicAdministratorName;
	@JsonIgnore
	public Wrap<String> clinicAdministratorNameWrap = new Wrap<String>().p(this).c(String.class).var("clinicAdministratorName").o(clinicAdministratorName);

	/**	<br/>L'entité « clinicAdministratorName »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.clinic.MedicalClinic&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinicAdministratorName">Trouver l'entité clinicAdministratorName dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _clinicAdministratorName(Wrap<String> c);

	public String getClinicAdministratorName() {
		return clinicAdministratorName;
	}

	public void setClinicAdministratorName(String clinicAdministratorName) {
		this.clinicAdministratorName = clinicAdministratorName;
		this.clinicAdministratorNameWrap.alreadyInitialized = true;
	}
	protected MedicalClinic clinicAdministratorNameInit() {
		if(!clinicAdministratorNameWrap.alreadyInitialized) {
			_clinicAdministratorName(clinicAdministratorNameWrap);
			if(clinicAdministratorName == null)
				setClinicAdministratorName(clinicAdministratorNameWrap.o);
		}
		clinicAdministratorNameWrap.alreadyInitialized(true);
		return (MedicalClinic)this;
	}

	public String solrClinicAdministratorName() {
		return clinicAdministratorName;
	}

	public String strClinicAdministratorName() {
		return clinicAdministratorName == null ? "" : clinicAdministratorName;
	}

	public String jsonClinicAdministratorName() {
		return clinicAdministratorName == null ? "" : clinicAdministratorName;
	}

	public String nomAffichageClinicAdministratorName() {
		return "administrator of the clinic";
	}

	public String htmTooltipClinicAdministratorName() {
		return null;
	}

	public String htmClinicAdministratorName() {
		return clinicAdministratorName == null ? "" : StringEscapeUtils.escapeHtml4(strClinicAdministratorName());
	}

	public void inputClinicAdministratorName(String classApiMethodMethod) {
		MedicalClinic s = (MedicalClinic)this;
		{
			e("input")
				.a("type", "text")
				.a("placeholder", "administrator of the clinic")
				.a("id", classApiMethodMethod, "_clinicAdministratorName");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setClinicAdministratorName classMedicalClinic inputMedicalClinic", pk, "ClinicAdministratorName w3-input w3-border ");
					a("name", "setClinicAdministratorName");
				} else {
					a("class", "valueClinicAdministratorName w3-input w3-border classMedicalClinic inputMedicalClinic", pk, "ClinicAdministratorName w3-input w3-border ");
					a("name", "clinicAdministratorName");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patchMedicalClinicVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setClinicAdministratorName', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_clinicAdministratorName')); }, function() { addError($('#", classApiMethodMethod, "_clinicAdministratorName')); }); ");
				}
				a("value", strClinicAdministratorName())
			.fg();

		}
	}

	public void htmClinicAdministratorName(String classApiMethodMethod) {
		MedicalClinic s = (MedicalClinic)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalClinicClinicAdministratorName").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pink ").f();
							e("label").a("for", classApiMethodMethod, "_clinicAdministratorName").a("class", "").f().sx("administrator of the clinic").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputClinicAdministratorName(classApiMethodMethod);
							} g("div");
							{
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pink ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_clinicAdministratorName')); $('#", classApiMethodMethod, "_clinicAdministratorName').val(null); patchMedicalClinicVal([{ name: 'fq', value: 'pk:' + $('#MedicalClinicForm :input[name=pk]').val() }], 'setClinicAdministratorName', null, function() { addGlow($('#", classApiMethodMethod, "_clinicAdministratorName')); }, function() { addError($('#", classApiMethodMethod, "_clinicAdministratorName')); }); ")
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
	// clinicEmailFrom //
	/////////////////////

	/**	L'entité « clinicEmailFrom »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String clinicEmailFrom;
	@JsonIgnore
	public Wrap<String> clinicEmailFromWrap = new Wrap<String>().p(this).c(String.class).var("clinicEmailFrom").o(clinicEmailFrom);

	/**	<br/>L'entité « clinicEmailFrom »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.clinic.MedicalClinic&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinicEmailFrom">Trouver l'entité clinicEmailFrom dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _clinicEmailFrom(Wrap<String> c);

	public String getClinicEmailFrom() {
		return clinicEmailFrom;
	}

	public void setClinicEmailFrom(String clinicEmailFrom) {
		this.clinicEmailFrom = clinicEmailFrom;
		this.clinicEmailFromWrap.alreadyInitialized = true;
	}
	protected MedicalClinic clinicEmailFromInit() {
		if(!clinicEmailFromWrap.alreadyInitialized) {
			_clinicEmailFrom(clinicEmailFromWrap);
			if(clinicEmailFrom == null)
				setClinicEmailFrom(clinicEmailFromWrap.o);
		}
		clinicEmailFromWrap.alreadyInitialized(true);
		return (MedicalClinic)this;
	}

	public String solrClinicEmailFrom() {
		return clinicEmailFrom;
	}

	public String strClinicEmailFrom() {
		return clinicEmailFrom == null ? "" : clinicEmailFrom;
	}

	public String jsonClinicEmailFrom() {
		return clinicEmailFrom == null ? "" : clinicEmailFrom;
	}

	public String nomAffichageClinicEmailFrom() {
		return "emails from (1 only)";
	}

	public String htmTooltipClinicEmailFrom() {
		return null;
	}

	public String htmClinicEmailFrom() {
		return clinicEmailFrom == null ? "" : StringEscapeUtils.escapeHtml4(strClinicEmailFrom());
	}

	public void inputClinicEmailFrom(String classApiMethodMethod) {
		MedicalClinic s = (MedicalClinic)this;
		{
			e("input")
				.a("type", "text")
				.a("placeholder", "emails from (1 only)")
				.a("id", classApiMethodMethod, "_clinicEmailFrom");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setClinicEmailFrom classMedicalClinic inputMedicalClinic", pk, "ClinicEmailFrom w3-input w3-border ");
					a("name", "setClinicEmailFrom");
				} else {
					a("class", "valueClinicEmailFrom w3-input w3-border classMedicalClinic inputMedicalClinic", pk, "ClinicEmailFrom w3-input w3-border ");
					a("name", "clinicEmailFrom");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patchMedicalClinicVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setClinicEmailFrom', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_clinicEmailFrom')); }, function() { addError($('#", classApiMethodMethod, "_clinicEmailFrom')); }); ");
				}
				a("value", strClinicEmailFrom())
			.fg();

		}
	}

	public void htmClinicEmailFrom(String classApiMethodMethod) {
		MedicalClinic s = (MedicalClinic)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalClinicClinicEmailFrom").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pink ").f();
							e("label").a("for", classApiMethodMethod, "_clinicEmailFrom").a("class", "").f().sx("emails from (1 only)").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputClinicEmailFrom(classApiMethodMethod);
							} g("div");
							{
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pink ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_clinicEmailFrom')); $('#", classApiMethodMethod, "_clinicEmailFrom').val(null); patchMedicalClinicVal([{ name: 'fq', value: 'pk:' + $('#MedicalClinicForm :input[name=pk]').val() }], 'setClinicEmailFrom', null, function() { addGlow($('#", classApiMethodMethod, "_clinicEmailFrom')); }, function() { addError($('#", classApiMethodMethod, "_clinicEmailFrom')); }); ")
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
	// clinicEmailTo //
	///////////////////

	/**	L'entité « clinicEmailTo »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String clinicEmailTo;
	@JsonIgnore
	public Wrap<String> clinicEmailToWrap = new Wrap<String>().p(this).c(String.class).var("clinicEmailTo").o(clinicEmailTo);

	/**	<br/>L'entité « clinicEmailTo »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.clinic.MedicalClinic&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinicEmailTo">Trouver l'entité clinicEmailTo dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _clinicEmailTo(Wrap<String> c);

	public String getClinicEmailTo() {
		return clinicEmailTo;
	}

	public void setClinicEmailTo(String clinicEmailTo) {
		this.clinicEmailTo = clinicEmailTo;
		this.clinicEmailToWrap.alreadyInitialized = true;
	}
	protected MedicalClinic clinicEmailToInit() {
		if(!clinicEmailToWrap.alreadyInitialized) {
			_clinicEmailTo(clinicEmailToWrap);
			if(clinicEmailTo == null)
				setClinicEmailTo(clinicEmailToWrap.o);
		}
		clinicEmailToWrap.alreadyInitialized(true);
		return (MedicalClinic)this;
	}

	public String solrClinicEmailTo() {
		return clinicEmailTo;
	}

	public String strClinicEmailTo() {
		return clinicEmailTo == null ? "" : clinicEmailTo;
	}

	public String jsonClinicEmailTo() {
		return clinicEmailTo == null ? "" : clinicEmailTo;
	}

	public String nomAffichageClinicEmailTo() {
		return "emails to (1 or more by ,)";
	}

	public String htmTooltipClinicEmailTo() {
		return null;
	}

	public String htmClinicEmailTo() {
		return clinicEmailTo == null ? "" : StringEscapeUtils.escapeHtml4(strClinicEmailTo());
	}

	public void inputClinicEmailTo(String classApiMethodMethod) {
		MedicalClinic s = (MedicalClinic)this;
		{
			e("input")
				.a("type", "text")
				.a("placeholder", "emails to (1 or more by ,)")
				.a("id", classApiMethodMethod, "_clinicEmailTo");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setClinicEmailTo classMedicalClinic inputMedicalClinic", pk, "ClinicEmailTo w3-input w3-border ");
					a("name", "setClinicEmailTo");
				} else {
					a("class", "valueClinicEmailTo w3-input w3-border classMedicalClinic inputMedicalClinic", pk, "ClinicEmailTo w3-input w3-border ");
					a("name", "clinicEmailTo");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patchMedicalClinicVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setClinicEmailTo', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_clinicEmailTo')); }, function() { addError($('#", classApiMethodMethod, "_clinicEmailTo')); }); ");
				}
				a("value", strClinicEmailTo())
			.fg();

		}
	}

	public void htmClinicEmailTo(String classApiMethodMethod) {
		MedicalClinic s = (MedicalClinic)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalClinicClinicEmailTo").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pink ").f();
							e("label").a("for", classApiMethodMethod, "_clinicEmailTo").a("class", "").f().sx("emails to (1 or more by ,)").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputClinicEmailTo(classApiMethodMethod);
							} g("div");
							{
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pink ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_clinicEmailTo')); $('#", classApiMethodMethod, "_clinicEmailTo').val(null); patchMedicalClinicVal([{ name: 'fq', value: 'pk:' + $('#MedicalClinicForm :input[name=pk]').val() }], 'setClinicEmailTo', null, function() { addGlow($('#", classApiMethodMethod, "_clinicEmailTo')); }, function() { addError($('#", classApiMethodMethod, "_clinicEmailTo')); }); ")
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
	// clinicLocation //
	////////////////////

	/**	L'entité « clinicLocation »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String clinicLocation;
	@JsonIgnore
	public Wrap<String> clinicLocationWrap = new Wrap<String>().p(this).c(String.class).var("clinicLocation").o(clinicLocation);

	/**	<br/>L'entité « clinicLocation »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.clinic.MedicalClinic&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinicLocation">Trouver l'entité clinicLocation dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _clinicLocation(Wrap<String> c);

	public String getClinicLocation() {
		return clinicLocation;
	}

	public void setClinicLocation(String clinicLocation) {
		this.clinicLocation = clinicLocation;
		this.clinicLocationWrap.alreadyInitialized = true;
	}
	protected MedicalClinic clinicLocationInit() {
		if(!clinicLocationWrap.alreadyInitialized) {
			_clinicLocation(clinicLocationWrap);
			if(clinicLocation == null)
				setClinicLocation(clinicLocationWrap.o);
		}
		clinicLocationWrap.alreadyInitialized(true);
		return (MedicalClinic)this;
	}

	public String solrClinicLocation() {
		return clinicLocation;
	}

	public String strClinicLocation() {
		return clinicLocation == null ? "" : clinicLocation;
	}

	public String jsonClinicLocation() {
		return clinicLocation == null ? "" : clinicLocation;
	}

	public String nomAffichageClinicLocation() {
		return "location";
	}

	public String htmTooltipClinicLocation() {
		return null;
	}

	public String htmClinicLocation() {
		return clinicLocation == null ? "" : StringEscapeUtils.escapeHtml4(strClinicLocation());
	}

	public void inputClinicLocation(String classApiMethodMethod) {
		MedicalClinic s = (MedicalClinic)this;
		{
			e("input")
				.a("type", "text")
				.a("placeholder", "location")
				.a("id", classApiMethodMethod, "_clinicLocation");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setClinicLocation classMedicalClinic inputMedicalClinic", pk, "ClinicLocation w3-input w3-border ");
					a("name", "setClinicLocation");
				} else {
					a("class", "valueClinicLocation w3-input w3-border classMedicalClinic inputMedicalClinic", pk, "ClinicLocation w3-input w3-border ");
					a("name", "clinicLocation");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patchMedicalClinicVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setClinicLocation', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_clinicLocation')); }, function() { addError($('#", classApiMethodMethod, "_clinicLocation')); }); ");
				}
				a("value", strClinicLocation())
			.fg();

		}
	}

	public void htmClinicLocation(String classApiMethodMethod) {
		MedicalClinic s = (MedicalClinic)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalClinicClinicLocation").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pink ").f();
							e("label").a("for", classApiMethodMethod, "_clinicLocation").a("class", "").f().sx("location").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputClinicLocation(classApiMethodMethod);
							} g("div");
							{
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pink ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_clinicLocation')); $('#", classApiMethodMethod, "_clinicLocation').val(null); patchMedicalClinicVal([{ name: 'fq', value: 'pk:' + $('#MedicalClinicForm :input[name=pk]').val() }], 'setClinicLocation', null, function() { addGlow($('#", classApiMethodMethod, "_clinicLocation')); }, function() { addError($('#", classApiMethodMethod, "_clinicLocation')); }); ")
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
	// clinicAddress //
	///////////////////

	/**	L'entité « clinicAddress »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String clinicAddress;
	@JsonIgnore
	public Wrap<String> clinicAddressWrap = new Wrap<String>().p(this).c(String.class).var("clinicAddress").o(clinicAddress);

	/**	<br/>L'entité « clinicAddress »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.clinic.MedicalClinic&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinicAddress">Trouver l'entité clinicAddress dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _clinicAddress(Wrap<String> c);

	public String getClinicAddress() {
		return clinicAddress;
	}

	public void setClinicAddress(String clinicAddress) {
		this.clinicAddress = clinicAddress;
		this.clinicAddressWrap.alreadyInitialized = true;
	}
	protected MedicalClinic clinicAddressInit() {
		if(!clinicAddressWrap.alreadyInitialized) {
			_clinicAddress(clinicAddressWrap);
			if(clinicAddress == null)
				setClinicAddress(clinicAddressWrap.o);
		}
		clinicAddressWrap.alreadyInitialized(true);
		return (MedicalClinic)this;
	}

	public String solrClinicAddress() {
		return clinicAddress;
	}

	public String strClinicAddress() {
		return clinicAddress == null ? "" : clinicAddress;
	}

	public String jsonClinicAddress() {
		return clinicAddress == null ? "" : clinicAddress;
	}

	public String nomAffichageClinicAddress() {
		return "address";
	}

	public String htmTooltipClinicAddress() {
		return null;
	}

	public String htmClinicAddress() {
		return clinicAddress == null ? "" : StringEscapeUtils.escapeHtml4(strClinicAddress());
	}

	public void inputClinicAddress(String classApiMethodMethod) {
		MedicalClinic s = (MedicalClinic)this;
		{
			e("textarea")
				.a("placeholder", "address")
				.a("id", classApiMethodMethod, "_clinicAddress");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setClinicAddress classMedicalClinic inputMedicalClinic", pk, "ClinicAddress w3-input w3-border ");
					a("name", "setClinicAddress");
				} else {
					a("class", "valueClinicAddress w3-input w3-border classMedicalClinic inputMedicalClinic", pk, "ClinicAddress w3-input w3-border ");
					a("name", "clinicAddress");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patchMedicalClinicVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setClinicAddress', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_clinicAddress')); }, function() { addError($('#", classApiMethodMethod, "_clinicAddress')); }); ");
				}
			f().sx(strClinicAddress()).g("textarea");

		}
	}

	public void htmClinicAddress(String classApiMethodMethod) {
		MedicalClinic s = (MedicalClinic)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalClinicClinicAddress").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pink ").f();
							e("label").a("for", classApiMethodMethod, "_clinicAddress").a("class", "").f().sx("address").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputClinicAddress(classApiMethodMethod);
							} g("div");
							{
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pink ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_clinicAddress')); $('#", classApiMethodMethod, "_clinicAddress').val(null); patchMedicalClinicVal([{ name: 'fq', value: 'pk:' + $('#MedicalClinicForm :input[name=pk]').val() }], 'setClinicAddress', null, function() { addGlow($('#", classApiMethodMethod, "_clinicAddress')); }, function() { addError($('#", classApiMethodMethod, "_clinicAddress')); }); ")
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
	// clinicShortName //
	/////////////////////

	/**	L'entité « clinicShortName »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String clinicShortName;
	@JsonIgnore
	public Wrap<String> clinicShortNameWrap = new Wrap<String>().p(this).c(String.class).var("clinicShortName").o(clinicShortName);

	/**	<br/>L'entité « clinicShortName »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.clinic.MedicalClinic&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinicShortName">Trouver l'entité clinicShortName dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _clinicShortName(Wrap<String> c);

	public String getClinicShortName() {
		return clinicShortName;
	}

	public void setClinicShortName(String clinicShortName) {
		this.clinicShortName = clinicShortName;
		this.clinicShortNameWrap.alreadyInitialized = true;
	}
	protected MedicalClinic clinicShortNameInit() {
		if(!clinicShortNameWrap.alreadyInitialized) {
			_clinicShortName(clinicShortNameWrap);
			if(clinicShortName == null)
				setClinicShortName(clinicShortNameWrap.o);
		}
		clinicShortNameWrap.alreadyInitialized(true);
		return (MedicalClinic)this;
	}

	public String solrClinicShortName() {
		return clinicShortName;
	}

	public String strClinicShortName() {
		return clinicShortName == null ? "" : clinicShortName;
	}

	public String jsonClinicShortName() {
		return clinicShortName == null ? "" : clinicShortName;
	}

	public String nomAffichageClinicShortName() {
		return "r: cliniqueNom";
	}

	public String htmTooltipClinicShortName() {
		return null;
	}

	public String htmClinicShortName() {
		return clinicShortName == null ? "" : StringEscapeUtils.escapeHtml4(strClinicShortName());
	}

	////////////////////////
	// clinicCompleteName //
	////////////////////////

	/**	L'entité « clinicCompleteName »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String clinicCompleteName;
	@JsonIgnore
	public Wrap<String> clinicCompleteNameWrap = new Wrap<String>().p(this).c(String.class).var("clinicCompleteName").o(clinicCompleteName);

	/**	<br/>L'entité « clinicCompleteName »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.clinic.MedicalClinic&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinicCompleteName">Trouver l'entité clinicCompleteName dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _clinicCompleteName(Wrap<String> c);

	public String getClinicCompleteName() {
		return clinicCompleteName;
	}

	public void setClinicCompleteName(String clinicCompleteName) {
		this.clinicCompleteName = clinicCompleteName;
		this.clinicCompleteNameWrap.alreadyInitialized = true;
	}
	protected MedicalClinic clinicCompleteNameInit() {
		if(!clinicCompleteNameWrap.alreadyInitialized) {
			_clinicCompleteName(clinicCompleteNameWrap);
			if(clinicCompleteName == null)
				setClinicCompleteName(clinicCompleteNameWrap.o);
		}
		clinicCompleteNameWrap.alreadyInitialized(true);
		return (MedicalClinic)this;
	}

	public String solrClinicCompleteName() {
		return clinicCompleteName;
	}

	public String strClinicCompleteName() {
		return clinicCompleteName == null ? "" : clinicCompleteName;
	}

	public String jsonClinicCompleteName() {
		return clinicCompleteName == null ? "" : clinicCompleteName;
	}

	public String nomAffichageClinicCompleteName() {
		return "name";
	}

	public String htmTooltipClinicCompleteName() {
		return null;
	}

	public String htmClinicCompleteName() {
		return clinicCompleteName == null ? "" : StringEscapeUtils.escapeHtml4(strClinicCompleteName());
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedMedicalClinic = false;

	public MedicalClinic initDeepMedicalClinic(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedMedicalClinic) {
			alreadyInitializedMedicalClinic = true;
			initDeepMedicalClinic();
		}
		return (MedicalClinic)this;
	}

	public void initDeepMedicalClinic() {
		initMedicalClinic();
		super.initDeepCluster(siteRequest_);
	}

	public void initMedicalClinic() {
		clinicKeyInit();
		yearKeysInit();
		seasonKeysInit();
		sessionKeysInit();
		ageGroupKeysInit();
		blockKeysInit();
		childKeysInit();
		educationSortInit();
		clinicSortInit();
		clinicNameInit();
		clinicPhoneNumberInit();
		clinicAdministratorNameInit();
		clinicEmailFromInit();
		clinicEmailToInit();
		clinicLocationInit();
		clinicAddressInit();
		clinicShortNameInit();
		clinicCompleteNameInit();
	}

	@Override public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepMedicalClinic(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestMedicalClinic(SiteRequestEnUS siteRequest_) {
			super.siteRequestCluster(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestMedicalClinic(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainMedicalClinic(v);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.obtainForClass(v);
			}
		}
		return o;
	}
	public Object obtainMedicalClinic(String var) {
		MedicalClinic oMedicalClinic = (MedicalClinic)this;
		switch(var) {
			case "clinicKey":
				return oMedicalClinic.clinicKey;
			case "yearKeys":
				return oMedicalClinic.yearKeys;
			case "seasonKeys":
				return oMedicalClinic.seasonKeys;
			case "sessionKeys":
				return oMedicalClinic.sessionKeys;
			case "ageGroupKeys":
				return oMedicalClinic.ageGroupKeys;
			case "blockKeys":
				return oMedicalClinic.blockKeys;
			case "childKeys":
				return oMedicalClinic.childKeys;
			case "educationSort":
				return oMedicalClinic.educationSort;
			case "clinicSort":
				return oMedicalClinic.clinicSort;
			case "clinicName":
				return oMedicalClinic.clinicName;
			case "clinicPhoneNumber":
				return oMedicalClinic.clinicPhoneNumber;
			case "clinicAdministratorName":
				return oMedicalClinic.clinicAdministratorName;
			case "clinicEmailFrom":
				return oMedicalClinic.clinicEmailFrom;
			case "clinicEmailTo":
				return oMedicalClinic.clinicEmailTo;
			case "clinicLocation":
				return oMedicalClinic.clinicLocation;
			case "clinicAddress":
				return oMedicalClinic.clinicAddress;
			case "clinicShortName":
				return oMedicalClinic.clinicShortName;
			case "clinicCompleteName":
				return oMedicalClinic.clinicCompleteName;
			default:
				return super.obtainCluster(var);
		}
	}

	///////////////
	// attribute //
	///////////////

	@Override public boolean attributeForClass(String var, Object val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = attributeMedicalClinic(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeMedicalClinic(String var, Object val) {
		MedicalClinic oMedicalClinic = (MedicalClinic)this;
		switch(var) {
			default:
				return super.attributeCluster(var, val);
		}
	}

	/////////////
	// define //
	/////////////

	@Override public boolean defineForClass(String var, String val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		if(val != null) {
			for(String v : vars) {
				if(o == null)
					o = defineMedicalClinic(v, val);
				else if(o instanceof Cluster) {
					Cluster cluster = (Cluster)o;
					o = cluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineMedicalClinic(String var, String val) {
		switch(var) {
			case "clinicName":
				if(val != null)
					setClinicName(val);
				savesMedicalClinic.add(var);
				return val;
			case "clinicPhoneNumber":
				if(val != null)
					setClinicPhoneNumber(val);
				savesMedicalClinic.add(var);
				return val;
			case "clinicAdministratorName":
				if(val != null)
					setClinicAdministratorName(val);
				savesMedicalClinic.add(var);
				return val;
			case "clinicEmailFrom":
				if(val != null)
					setClinicEmailFrom(val);
				savesMedicalClinic.add(var);
				return val;
			case "clinicEmailTo":
				if(val != null)
					setClinicEmailTo(val);
				savesMedicalClinic.add(var);
				return val;
			case "clinicLocation":
				if(val != null)
					setClinicLocation(val);
				savesMedicalClinic.add(var);
				return val;
			case "clinicAddress":
				if(val != null)
					setClinicAddress(val);
				savesMedicalClinic.add(var);
				return val;
			default:
				return super.defineCluster(var, val);
		}
	}

	/////////////////
	// saves //
	/////////////////

	protected List<String> savesMedicalClinic = new ArrayList<String>();

	/////////////
	// populate //
	/////////////

	@Override public void populateForClass(SolrDocument solrDocument) {
		populateMedicalClinic(solrDocument);
	}
	public void populateMedicalClinic(SolrDocument solrDocument) {
		MedicalClinic oMedicalClinic = (MedicalClinic)this;
		savesMedicalClinic = (List<String>)solrDocument.get("savesMedicalClinic_stored_strings");
		if(savesMedicalClinic != null) {

			if(savesMedicalClinic.contains("clinicKey")) {
				Long clinicKey = (Long)solrDocument.get("clinicKey_stored_long");
				if(clinicKey != null)
					oMedicalClinic.setClinicKey(clinicKey);
			}

			if(savesMedicalClinic.contains("yearKeys")) {
				List<Long> yearKeys = (List<Long>)solrDocument.get("yearKeys_stored_longs");
				if(yearKeys != null)
					oMedicalClinic.yearKeys.addAll(yearKeys);
			}

			if(savesMedicalClinic.contains("seasonKeys")) {
				List<Long> seasonKeys = (List<Long>)solrDocument.get("seasonKeys_stored_longs");
				if(seasonKeys != null)
					oMedicalClinic.seasonKeys.addAll(seasonKeys);
			}

			if(savesMedicalClinic.contains("sessionKeys")) {
				List<Long> sessionKeys = (List<Long>)solrDocument.get("sessionKeys_stored_longs");
				if(sessionKeys != null)
					oMedicalClinic.sessionKeys.addAll(sessionKeys);
			}

			if(savesMedicalClinic.contains("ageGroupKeys")) {
				List<Long> ageGroupKeys = (List<Long>)solrDocument.get("ageGroupKeys_stored_longs");
				if(ageGroupKeys != null)
					oMedicalClinic.ageGroupKeys.addAll(ageGroupKeys);
			}

			if(savesMedicalClinic.contains("blockKeys")) {
				List<Long> blockKeys = (List<Long>)solrDocument.get("blockKeys_stored_longs");
				if(blockKeys != null)
					oMedicalClinic.blockKeys.addAll(blockKeys);
			}

			if(savesMedicalClinic.contains("childKeys")) {
				List<Long> childKeys = (List<Long>)solrDocument.get("childKeys_stored_longs");
				if(childKeys != null)
					oMedicalClinic.childKeys.addAll(childKeys);
			}

			if(savesMedicalClinic.contains("educationSort")) {
				Integer educationSort = (Integer)solrDocument.get("educationSort_stored_int");
				if(educationSort != null)
					oMedicalClinic.setEducationSort(educationSort);
			}

			if(savesMedicalClinic.contains("clinicSort")) {
				Integer clinicSort = (Integer)solrDocument.get("clinicSort_stored_int");
				if(clinicSort != null)
					oMedicalClinic.setClinicSort(clinicSort);
			}

			if(savesMedicalClinic.contains("clinicName")) {
				String clinicName = (String)solrDocument.get("clinicName_stored_string");
				if(clinicName != null)
					oMedicalClinic.setClinicName(clinicName);
			}

			if(savesMedicalClinic.contains("clinicPhoneNumber")) {
				String clinicPhoneNumber = (String)solrDocument.get("clinicPhoneNumber_stored_string");
				if(clinicPhoneNumber != null)
					oMedicalClinic.setClinicPhoneNumber(clinicPhoneNumber);
			}

			if(savesMedicalClinic.contains("clinicAdministratorName")) {
				String clinicAdministratorName = (String)solrDocument.get("clinicAdministratorName_stored_string");
				if(clinicAdministratorName != null)
					oMedicalClinic.setClinicAdministratorName(clinicAdministratorName);
			}

			if(savesMedicalClinic.contains("clinicEmailFrom")) {
				String clinicEmailFrom = (String)solrDocument.get("clinicEmailFrom_stored_string");
				if(clinicEmailFrom != null)
					oMedicalClinic.setClinicEmailFrom(clinicEmailFrom);
			}

			if(savesMedicalClinic.contains("clinicEmailTo")) {
				String clinicEmailTo = (String)solrDocument.get("clinicEmailTo_stored_string");
				if(clinicEmailTo != null)
					oMedicalClinic.setClinicEmailTo(clinicEmailTo);
			}

			if(savesMedicalClinic.contains("clinicLocation")) {
				String clinicLocation = (String)solrDocument.get("clinicLocation_stored_string");
				if(clinicLocation != null)
					oMedicalClinic.setClinicLocation(clinicLocation);
			}

			if(savesMedicalClinic.contains("clinicAddress")) {
				String clinicAddress = (String)solrDocument.get("clinicAddress_stored_string");
				if(clinicAddress != null)
					oMedicalClinic.setClinicAddress(clinicAddress);
			}

			if(savesMedicalClinic.contains("clinicShortName")) {
				String clinicShortName = (String)solrDocument.get("clinicShortName_stored_string");
				if(clinicShortName != null)
					oMedicalClinic.setClinicShortName(clinicShortName);
			}

			if(savesMedicalClinic.contains("clinicCompleteName")) {
				String clinicCompleteName = (String)solrDocument.get("clinicCompleteName_stored_string");
				if(clinicCompleteName != null)
					oMedicalClinic.setClinicCompleteName(clinicCompleteName);
			}
		}

		super.populateCluster(solrDocument);
	}

	/////////////
	// index //
	/////////////

	public static void index() {
		try {
			SiteRequestEnUS siteRequest = new SiteRequestEnUS();
			siteRequest.initDeepSiteRequestEnUS();
			SiteContextEnUS siteContext = new SiteContextEnUS();
			siteContext.getSiteConfig().setConfigPath("/usr/local/src/computate-medicale/config/computate-medicale.config");
			siteContext.initDeepSiteContextEnUS();
			siteRequest.setSiteContext_(siteContext);
			siteRequest.setSiteConfig_(siteContext.getSiteConfig());
			SolrQuery solrQuery = new SolrQuery();
			solrQuery.setQuery("*:*");
			solrQuery.setRows(1);
			solrQuery.addFilterQuery("id:" + ClientUtils.escapeQueryChars("org.computate.medicale.enUS.clinic.MedicalClinic"));
			QueryResponse queryResponse = siteRequest.getSiteContext_().getSolrClient().query(solrQuery);
			if(queryResponse.getResults().size() > 0)
				siteRequest.setSolrDocument(queryResponse.getResults().get(0));
			MedicalClinic o = new MedicalClinic();
			o.siteRequestMedicalClinic(siteRequest);
			o.initDeepMedicalClinic(siteRequest);
			o.indexMedicalClinic();
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}


	@Override public void indexForClass() {
		indexMedicalClinic();
	}

	@Override public void indexForClass(SolrInputDocument document) {
		indexMedicalClinic(document);
	}

	public void indexMedicalClinic(SolrClient clientSolr) {
		try {
			SolrInputDocument document = new SolrInputDocument();
			indexMedicalClinic(document);
			clientSolr.add(document);
			clientSolr.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public void indexMedicalClinic() {
		try {
			SolrInputDocument document = new SolrInputDocument();
			indexMedicalClinic(document);
			SolrClient clientSolr = siteRequest_.getSiteContext_().getSolrClient();
			clientSolr.add(document);
			clientSolr.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public void indexMedicalClinic(SolrInputDocument document) {
		if(savesMedicalClinic != null)
			document.addField("savesMedicalClinic_stored_strings", savesMedicalClinic);

		if(clinicKey != null) {
			document.addField("clinicKey_indexed_long", clinicKey);
			document.addField("clinicKey_stored_long", clinicKey);
		}
		if(yearKeys != null) {
			for(java.lang.Long o : yearKeys) {
				document.addField("yearKeys_indexed_longs", o);
			}
			for(java.lang.Long o : yearKeys) {
				document.addField("yearKeys_stored_longs", o);
			}
		}
		if(seasonKeys != null) {
			for(java.lang.Long o : seasonKeys) {
				document.addField("seasonKeys_indexed_longs", o);
			}
			for(java.lang.Long o : seasonKeys) {
				document.addField("seasonKeys_stored_longs", o);
			}
		}
		if(sessionKeys != null) {
			for(java.lang.Long o : sessionKeys) {
				document.addField("sessionKeys_indexed_longs", o);
			}
			for(java.lang.Long o : sessionKeys) {
				document.addField("sessionKeys_stored_longs", o);
			}
		}
		if(ageGroupKeys != null) {
			for(java.lang.Long o : ageGroupKeys) {
				document.addField("ageGroupKeys_indexed_longs", o);
			}
			for(java.lang.Long o : ageGroupKeys) {
				document.addField("ageGroupKeys_stored_longs", o);
			}
		}
		if(blockKeys != null) {
			for(java.lang.Long o : blockKeys) {
				document.addField("blockKeys_indexed_longs", o);
			}
			for(java.lang.Long o : blockKeys) {
				document.addField("blockKeys_stored_longs", o);
			}
		}
		if(childKeys != null) {
			for(java.lang.Long o : childKeys) {
				document.addField("childKeys_indexed_longs", o);
			}
			for(java.lang.Long o : childKeys) {
				document.addField("childKeys_stored_longs", o);
			}
		}
		if(educationSort != null) {
			document.addField("educationSort_indexed_int", educationSort);
			document.addField("educationSort_stored_int", educationSort);
		}
		if(clinicSort != null) {
			document.addField("clinicSort_indexed_int", clinicSort);
			document.addField("clinicSort_stored_int", clinicSort);
		}
		if(clinicName != null) {
			document.addField("clinicName_indexed_string", clinicName);
			document.addField("clinicName_stored_string", clinicName);
		}
		if(clinicPhoneNumber != null) {
			document.addField("clinicPhoneNumber_indexed_string", clinicPhoneNumber);
			document.addField("clinicPhoneNumber_stored_string", clinicPhoneNumber);
		}
		if(clinicAdministratorName != null) {
			document.addField("clinicAdministratorName_indexed_string", clinicAdministratorName);
			document.addField("clinicAdministratorName_stored_string", clinicAdministratorName);
		}
		if(clinicEmailFrom != null) {
			document.addField("clinicEmailFrom_indexed_string", clinicEmailFrom);
			document.addField("clinicEmailFrom_stored_string", clinicEmailFrom);
		}
		if(clinicEmailTo != null) {
			document.addField("clinicEmailTo_indexed_string", clinicEmailTo);
			document.addField("clinicEmailTo_stored_string", clinicEmailTo);
		}
		if(clinicLocation != null) {
			document.addField("clinicLocation_indexed_string", clinicLocation);
			document.addField("clinicLocation_stored_string", clinicLocation);
		}
		if(clinicAddress != null) {
			document.addField("clinicAddress_indexed_string", clinicAddress);
			document.addField("clinicAddress_stored_string", clinicAddress);
		}
		if(clinicShortName != null) {
			document.addField("clinicShortName_indexed_string", clinicShortName);
			document.addField("clinicShortName_stored_string", clinicShortName);
		}
		if(clinicCompleteName != null) {
			document.addField("clinicCompleteName_indexed_string", clinicCompleteName);
			document.addField("clinicCompleteName_stored_string", clinicCompleteName);
		}
		super.indexCluster(document);

	}

	public void unindexMedicalClinic() {
		try {
		SiteRequestEnUS siteRequest = new SiteRequestEnUS();
			siteRequest.initDeepSiteRequestEnUS();
			SiteContextEnUS siteContext = new SiteContextEnUS();
			siteContext.initDeepSiteContextEnUS();
			siteRequest.setSiteContext_(siteContext);
			siteRequest.setSiteConfig_(siteContext.getSiteConfig());
			initDeepMedicalClinic(siteRequest);
			SolrClient solrClient = siteContext.getSolrClient();
			solrClient.deleteById(id.toString());
			solrClient.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public static String varIndexedMedicalClinic(String entityVar) {
		switch(entityVar) {
			case "clinicKey":
				return "clinicKey_indexed_long";
			case "yearKeys":
				return "yearKeys_indexed_longs";
			case "seasonKeys":
				return "seasonKeys_indexed_longs";
			case "sessionKeys":
				return "sessionKeys_indexed_longs";
			case "ageGroupKeys":
				return "ageGroupKeys_indexed_longs";
			case "blockKeys":
				return "blockKeys_indexed_longs";
			case "childKeys":
				return "childKeys_indexed_longs";
			case "educationSort":
				return "educationSort_indexed_int";
			case "clinicSort":
				return "clinicSort_indexed_int";
			case "clinicName":
				return "clinicName_indexed_string";
			case "clinicPhoneNumber":
				return "clinicPhoneNumber_indexed_string";
			case "clinicAdministratorName":
				return "clinicAdministratorName_indexed_string";
			case "clinicEmailFrom":
				return "clinicEmailFrom_indexed_string";
			case "clinicEmailTo":
				return "clinicEmailTo_indexed_string";
			case "clinicLocation":
				return "clinicLocation_indexed_string";
			case "clinicAddress":
				return "clinicAddress_indexed_string";
			case "clinicShortName":
				return "clinicShortName_indexed_string";
			case "clinicCompleteName":
				return "clinicCompleteName_indexed_string";
			default:
				return Cluster.varIndexedCluster(entityVar);
		}
	}

	public static String varSearchMedicalClinic(String entityVar) {
		switch(entityVar) {
			default:
				return Cluster.varSearchCluster(entityVar);
		}
	}

	public static String varSuggestedMedicalClinic(String entityVar) {
		switch(entityVar) {
			default:
				return Cluster.varSuggestedCluster(entityVar);
		}
	}

	/////////////
	// store //
	/////////////

	@Override public void storeForClass(SolrDocument solrDocument) {
		storeMedicalClinic(solrDocument);
	}
	public void storeMedicalClinic(SolrDocument solrDocument) {
		MedicalClinic oMedicalClinic = (MedicalClinic)this;

		Long clinicKey = (Long)solrDocument.get("clinicKey_stored_long");
		if(clinicKey != null)
			oMedicalClinic.setClinicKey(clinicKey);

		List<Long> yearKeys = (List<Long>)solrDocument.get("yearKeys_stored_longs");
		if(yearKeys != null)
			oMedicalClinic.yearKeys.addAll(yearKeys);

		List<Long> seasonKeys = (List<Long>)solrDocument.get("seasonKeys_stored_longs");
		if(seasonKeys != null)
			oMedicalClinic.seasonKeys.addAll(seasonKeys);

		List<Long> sessionKeys = (List<Long>)solrDocument.get("sessionKeys_stored_longs");
		if(sessionKeys != null)
			oMedicalClinic.sessionKeys.addAll(sessionKeys);

		List<Long> ageGroupKeys = (List<Long>)solrDocument.get("ageGroupKeys_stored_longs");
		if(ageGroupKeys != null)
			oMedicalClinic.ageGroupKeys.addAll(ageGroupKeys);

		List<Long> blockKeys = (List<Long>)solrDocument.get("blockKeys_stored_longs");
		if(blockKeys != null)
			oMedicalClinic.blockKeys.addAll(blockKeys);

		List<Long> childKeys = (List<Long>)solrDocument.get("childKeys_stored_longs");
		if(childKeys != null)
			oMedicalClinic.childKeys.addAll(childKeys);

		Integer educationSort = (Integer)solrDocument.get("educationSort_stored_int");
		if(educationSort != null)
			oMedicalClinic.setEducationSort(educationSort);

		Integer clinicSort = (Integer)solrDocument.get("clinicSort_stored_int");
		if(clinicSort != null)
			oMedicalClinic.setClinicSort(clinicSort);

		String clinicName = (String)solrDocument.get("clinicName_stored_string");
		if(clinicName != null)
			oMedicalClinic.setClinicName(clinicName);

		String clinicPhoneNumber = (String)solrDocument.get("clinicPhoneNumber_stored_string");
		if(clinicPhoneNumber != null)
			oMedicalClinic.setClinicPhoneNumber(clinicPhoneNumber);

		String clinicAdministratorName = (String)solrDocument.get("clinicAdministratorName_stored_string");
		if(clinicAdministratorName != null)
			oMedicalClinic.setClinicAdministratorName(clinicAdministratorName);

		String clinicEmailFrom = (String)solrDocument.get("clinicEmailFrom_stored_string");
		if(clinicEmailFrom != null)
			oMedicalClinic.setClinicEmailFrom(clinicEmailFrom);

		String clinicEmailTo = (String)solrDocument.get("clinicEmailTo_stored_string");
		if(clinicEmailTo != null)
			oMedicalClinic.setClinicEmailTo(clinicEmailTo);

		String clinicLocation = (String)solrDocument.get("clinicLocation_stored_string");
		if(clinicLocation != null)
			oMedicalClinic.setClinicLocation(clinicLocation);

		String clinicAddress = (String)solrDocument.get("clinicAddress_stored_string");
		if(clinicAddress != null)
			oMedicalClinic.setClinicAddress(clinicAddress);

		String clinicShortName = (String)solrDocument.get("clinicShortName_stored_string");
		if(clinicShortName != null)
			oMedicalClinic.setClinicShortName(clinicShortName);

		String clinicCompleteName = (String)solrDocument.get("clinicCompleteName_stored_string");
		if(clinicCompleteName != null)
			oMedicalClinic.setClinicCompleteName(clinicCompleteName);

		super.storeCluster(solrDocument);
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestMedicalClinic() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof MedicalClinic) {
			MedicalClinic original = (MedicalClinic)o;
			if(!Objects.equals(clinicName, original.getClinicName()))
				apiRequest.addVars("clinicName");
			if(!Objects.equals(clinicPhoneNumber, original.getClinicPhoneNumber()))
				apiRequest.addVars("clinicPhoneNumber");
			if(!Objects.equals(clinicAdministratorName, original.getClinicAdministratorName()))
				apiRequest.addVars("clinicAdministratorName");
			if(!Objects.equals(clinicEmailFrom, original.getClinicEmailFrom()))
				apiRequest.addVars("clinicEmailFrom");
			if(!Objects.equals(clinicEmailTo, original.getClinicEmailTo()))
				apiRequest.addVars("clinicEmailTo");
			if(!Objects.equals(clinicLocation, original.getClinicLocation()))
				apiRequest.addVars("clinicLocation");
			if(!Objects.equals(clinicAddress, original.getClinicAddress()))
				apiRequest.addVars("clinicAddress");
			super.apiRequestCluster();
		}
	}

	//////////////
	// hashCode //
	//////////////

	@Override public int hashCode() {
		return Objects.hash(super.hashCode(), clinicName, clinicPhoneNumber, clinicAdministratorName, clinicEmailFrom, clinicEmailTo, clinicLocation, clinicAddress);
	}

	////////////
	// equals //
	////////////

	@Override public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof MedicalClinic))
			return false;
		MedicalClinic that = (MedicalClinic)o;
		return super.equals(o)
				&& Objects.equals( clinicName, that.clinicName )
				&& Objects.equals( clinicPhoneNumber, that.clinicPhoneNumber )
				&& Objects.equals( clinicAdministratorName, that.clinicAdministratorName )
				&& Objects.equals( clinicEmailFrom, that.clinicEmailFrom )
				&& Objects.equals( clinicEmailTo, that.clinicEmailTo )
				&& Objects.equals( clinicLocation, that.clinicLocation )
				&& Objects.equals( clinicAddress, that.clinicAddress );
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("MedicalClinic { ");
		sb.append( "clinicName: \"" ).append(clinicName).append( "\"" );
		sb.append( ", clinicPhoneNumber: \"" ).append(clinicPhoneNumber).append( "\"" );
		sb.append( ", clinicAdministratorName: \"" ).append(clinicAdministratorName).append( "\"" );
		sb.append( ", clinicEmailFrom: \"" ).append(clinicEmailFrom).append( "\"" );
		sb.append( ", clinicEmailTo: \"" ).append(clinicEmailTo).append( "\"" );
		sb.append( ", clinicLocation: \"" ).append(clinicLocation).append( "\"" );
		sb.append( ", clinicAddress: \"" ).append(clinicAddress).append( "\"" );
		sb.append(" }");
		return sb.toString();
	}
}
