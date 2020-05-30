package org.computate.medicale.enUS.enrollment;

import java.util.Arrays;
import java.util.Date;
import org.computate.medicale.enUS.request.api.ApiRequest;
import org.apache.commons.lang3.StringUtils;
import java.lang.Integer;
import java.lang.Long;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.util.Locale;
import io.vertx.core.json.JsonObject;
import java.time.ZoneOffset;
import io.vertx.core.logging.Logger;
import org.computate.medicale.enUS.wrap.Wrap;
import java.math.MathContext;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Objects;
import java.util.List;
import org.computate.medicale.enUS.search.SearchList;
import org.computate.medicale.enUS.patient.MedicalPatient;
import org.computate.medicale.enUS.request.SiteRequestEnUS;
import java.time.LocalDate;
import org.apache.solr.client.solrj.SolrQuery;
import java.util.Optional;
import org.apache.solr.client.solrj.util.ClientUtils;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.solr.common.SolrInputDocument;
import org.apache.commons.lang3.exception.ExceptionUtils;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.computate.medicale.enUS.cluster.Cluster;
import org.computate.medicale.enUS.enrollment.MedicalEnrollment;
import org.computate.medicale.enUS.context.SiteContextEnUS;
import io.vertx.core.logging.LoggerFactory;
import java.text.NumberFormat;
import java.util.ArrayList;
import org.apache.commons.collections.CollectionUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.lang.Boolean;
import org.computate.medicale.enUS.clinic.MedicalClinic;
import java.lang.String;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.computate.medicale.enUS.writer.AllWriter;
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
 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true">Trouver la classe  dans Solr</a>
 * <br/>
 **/
public abstract class MedicalEnrollmentGen<DEV> extends Cluster {
	protected static final Logger LOGGER = LoggerFactory.getLogger(MedicalEnrollment.class);

	public static final List<String> ROLES = Arrays.asList("SiteAdmin");
	public static final List<String> ROLE_READS = Arrays.asList("");

	public static final String MedicalEnrollment_AName = "an enrollment";
	public static final String MedicalEnrollment_This = "this ";
	public static final String MedicalEnrollment_ThisName = "this enrollment";
	public static final String MedicalEnrollment_A = "a ";
	public static final String MedicalEnrollment_TheName = "theenrollment";
	public static final String MedicalEnrollment_NameSingular = "enrollment";
	public static final String MedicalEnrollment_NamePlural = "enrollments";
	public static final String MedicalEnrollment_NameActual = "current enrollment";
	public static final String MedicalEnrollment_AllName = "all the enrollments";
	public static final String MedicalEnrollment_SearchAllNameBy = "search enrollments by ";
	public static final String MedicalEnrollment_ThePluralName = "the enrollments";
	public static final String MedicalEnrollment_NoNameFound = "no enrollment found";
	public static final String MedicalEnrollment_NameVar = "enrollment";
	public static final String MedicalEnrollment_OfName = "of enrollment";
	public static final String MedicalEnrollment_ANameAdjective = "an enrollment";
	public static final String MedicalEnrollment_NameAdjectiveSingular = "enrollment";
	public static final String MedicalEnrollment_NameAdjectivePlural = "enrollments";
	public static final String MedicalEnrollment_Color = "blue-gray";
	public static final String MedicalEnrollment_IconGroup = "solid";
	public static final String MedicalEnrollment_IconName = "edit";

	///////////////////
	// enrollmentKey //
	///////////////////

	/**	L'entité « enrollmentKey »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long enrollmentKey;
	@JsonIgnore
	public Wrap<Long> enrollmentKeyWrap = new Wrap<Long>().p(this).c(Long.class).var("enrollmentKey").o(enrollmentKey);

	/**	<br/>L'entité « enrollmentKey »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentKey">Trouver l'entité enrollmentKey dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _enrollmentKey(Wrap<Long> c);

	public Long getEnrollmentKey() {
		return enrollmentKey;
	}

	public void setEnrollmentKey(Long enrollmentKey) {
		this.enrollmentKey = enrollmentKey;
		this.enrollmentKeyWrap.alreadyInitialized = true;
	}
	public MedicalEnrollment setEnrollmentKey(String o) {
		if(NumberUtils.isParsable(o))
			this.enrollmentKey = Long.parseLong(o);
		this.enrollmentKeyWrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	protected MedicalEnrollment enrollmentKeyInit() {
		if(!enrollmentKeyWrap.alreadyInitialized) {
			_enrollmentKey(enrollmentKeyWrap);
			if(enrollmentKey == null)
				setEnrollmentKey(enrollmentKeyWrap.o);
		}
		enrollmentKeyWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public Long solrEnrollmentKey() {
		return enrollmentKey;
	}

	public String strEnrollmentKey() {
		return enrollmentKey == null ? "" : enrollmentKey.toString();
	}

	public String jsonEnrollmentKey() {
		return enrollmentKey == null ? "" : enrollmentKey.toString();
	}

	public String nomAffichageEnrollmentKey() {
		return "key";
	}

	public String htmTooltipEnrollmentKey() {
		return null;
	}

	public String htmEnrollmentKey() {
		return enrollmentKey == null ? "" : StringEscapeUtils.escapeHtml4(strEnrollmentKey());
	}

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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinicKey">Trouver l'entité clinicKey dans Solr</a>
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
	public MedicalEnrollment setClinicKey(String o) {
		if(NumberUtils.isParsable(o))
			this.clinicKey = Long.parseLong(o);
		this.clinicKeyWrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	protected MedicalEnrollment clinicKeyInit() {
		if(!clinicKeyWrap.alreadyInitialized) {
			_clinicKey(clinicKeyWrap);
			if(clinicKey == null)
				setClinicKey(clinicKeyWrap.o);
		}
		clinicKeyWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
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
		return "clinic";
	}

	public String htmTooltipClinicKey() {
		return null;
	}

	public String htmClinicKey() {
		return clinicKey == null ? "" : StringEscapeUtils.escapeHtml4(strClinicKey());
	}

	//////////////////
	// clinicSearch //
	//////////////////

	/**	L'entité « clinicSearch »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut SearchList<MedicalClinic>(). 
	 */
	@JsonIgnore
	@JsonInclude(Include.NON_NULL)
	protected SearchList<MedicalClinic> clinicSearch = new SearchList<MedicalClinic>();
	@JsonIgnore
	public Wrap<SearchList<MedicalClinic>> clinicSearchWrap = new Wrap<SearchList<MedicalClinic>>().p(this).c(SearchList.class).var("clinicSearch").o(clinicSearch);

	/**	<br/>L'entité « clinicSearch »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut SearchList<MedicalClinic>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinicSearch">Trouver l'entité clinicSearch dans Solr</a>
	 * <br/>
	 * @param clinicSearch est l'entité déjà construit. 
	 **/
	protected abstract void _clinicSearch(SearchList<MedicalClinic> l);

	public SearchList<MedicalClinic> getClinicSearch() {
		return clinicSearch;
	}

	public void setClinicSearch(SearchList<MedicalClinic> clinicSearch) {
		this.clinicSearch = clinicSearch;
		this.clinicSearchWrap.alreadyInitialized = true;
	}
	protected MedicalEnrollment clinicSearchInit() {
		if(!clinicSearchWrap.alreadyInitialized) {
			_clinicSearch(clinicSearch);
		}
		clinicSearch.initDeepForClass(siteRequest_);
		clinicSearchWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	/////////////
	// clinic_ //
	/////////////

	/**	L'entité « clinic_ »
	 *	 is defined as null before being initialized. 
	 */
	@JsonIgnore
	@JsonInclude(Include.NON_NULL)
	protected MedicalClinic clinic_;
	@JsonIgnore
	public Wrap<MedicalClinic> clinic_Wrap = new Wrap<MedicalClinic>().p(this).c(MedicalClinic.class).var("clinic_").o(clinic_);

	/**	<br/>L'entité « clinic_ »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinic_">Trouver l'entité clinic_ dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _clinic_(Wrap<MedicalClinic> c);

	public MedicalClinic getClinic_() {
		return clinic_;
	}

	public void setClinic_(MedicalClinic clinic_) {
		this.clinic_ = clinic_;
		this.clinic_Wrap.alreadyInitialized = true;
	}
	protected MedicalEnrollment clinic_Init() {
		if(!clinic_Wrap.alreadyInitialized) {
			_clinic_(clinic_Wrap);
			if(clinic_ == null)
				setClinic_(clinic_Wrap.o);
		}
		clinic_Wrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	////////////////
	// patientKey //
	////////////////

	/**	L'entité « patientKey »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long patientKey;
	@JsonIgnore
	public Wrap<Long> patientKeyWrap = new Wrap<Long>().p(this).c(Long.class).var("patientKey").o(patientKey);

	/**	<br/>L'entité « patientKey »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:patientKey">Trouver l'entité patientKey dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patientKey(Wrap<Long> c);

	public Long getPatientKey() {
		return patientKey;
	}

	public void setPatientKey(Long patientKey) {
		this.patientKey = patientKey;
		this.patientKeyWrap.alreadyInitialized = true;
	}
	public MedicalEnrollment setPatientKey(String o) {
		if(NumberUtils.isParsable(o))
			this.patientKey = Long.parseLong(o);
		this.patientKeyWrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	protected MedicalEnrollment patientKeyInit() {
		if(!patientKeyWrap.alreadyInitialized) {
			_patientKey(patientKeyWrap);
			if(patientKey == null)
				setPatientKey(patientKeyWrap.o);
		}
		patientKeyWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public Long solrPatientKey() {
		return patientKey;
	}

	public String strPatientKey() {
		return patientKey == null ? "" : patientKey.toString();
	}

	public String jsonPatientKey() {
		return patientKey == null ? "" : patientKey.toString();
	}

	public String nomAffichagePatientKey() {
		return "patientren";
	}

	public String htmTooltipPatientKey() {
		return null;
	}

	public String htmPatientKey() {
		return patientKey == null ? "" : StringEscapeUtils.escapeHtml4(strPatientKey());
	}

	public void inputPatientKey(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("i").a("class", "far fa-search w3-xxlarge w3-cell w3-cell-middle ").f().g("i");
				e("input")
					.a("type", "text")
					.a("placeholder", "patientren")
					.a("class", "valueObjectSuggest suggestPatientKey w3-input w3-border w3-cell w3-cell-middle ")
					.a("name", "setPatientKey")
					.a("id", classApiMethodMethod, "_patientKey")
					.a("autocomplete", "off")
					.a("oninput", "suggestMedicalEnrollmentPatientKey($(this).val() ? searchMedicalPatientFilters($(this.parentElement)) : [", pk == null ? "" : "{'name':'fq','value':'enrollmentKeys:" + pk + "'}", "], $('#listMedicalEnrollmentPatientKey_", classApiMethodMethod, "'), ", pk, "); ")
				.fg();

		} else {
			sx(htmPatientKey());
		}
	}

	public void htmPatientKey(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalEnrollmentPatientKey").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row ").f();
							{ e("a").a("href", "/child?fq=enrollmentKeys:", pk).a("class", "w3-cell w3-btn w3-center h4 w3-block h4 w3-orange w3-hover-orange ").f();
								e("i").a("class", "far fa-child ").f().g("i");
								sx("patientren");
							} g("a");
						} g("div");
						{ e("div").a("class", "w3-cell-row ").f();
							{ e("h5").a("class", "w3-cell ").f();
								sx("relate a child to this enrollment");
							} g("h5");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();
								{ e("div").a("class", "w3-cell-row ").f();

								inputPatientKey(classApiMethodMethod);
								} g("div");
							} g("div");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
								{ e("ul").a("class", "w3-ul w3-hoverable ").a("id", "listMedicalEnrollmentPatientKey_", classApiMethodMethod).f();
								} g("ul");
								if(
										userKeys.contains(siteRequest_.getUserKey())
										|| Objects.equals(sessionId, siteRequest_.getSessionId())
										|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
										|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
								) {
									{ e("div").a("class", "w3-cell-row ").f();
										e("button")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-orange ")
											.a("id", classApiMethodMethod, "_patientKey_add")
											.a("onclick", "$(this).addClass('w3-disabled'); this.disabled = true; this.innerHTML = 'Sending…'; postMedicalPatientVals({ enrollmentKeys: [ \"", pk, "\" ] }, function() {}, function() { addError($('#", classApiMethodMethod, "patientKey')); });")
											.f().sx("add a child")
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

	///////////////////////
	// enrollmentFormKey //
	///////////////////////

	/**	L'entité « enrollmentFormKey »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long enrollmentFormKey;
	@JsonIgnore
	public Wrap<Long> enrollmentFormKeyWrap = new Wrap<Long>().p(this).c(Long.class).var("enrollmentFormKey").o(enrollmentFormKey);

	/**	<br/>L'entité « enrollmentFormKey »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentFormKey">Trouver l'entité enrollmentFormKey dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _enrollmentFormKey(Wrap<Long> c);

	public Long getEnrollmentFormKey() {
		return enrollmentFormKey;
	}

	public void setEnrollmentFormKey(Long enrollmentFormKey) {
		this.enrollmentFormKey = enrollmentFormKey;
		this.enrollmentFormKeyWrap.alreadyInitialized = true;
	}
	public MedicalEnrollment setEnrollmentFormKey(String o) {
		if(NumberUtils.isParsable(o))
			this.enrollmentFormKey = Long.parseLong(o);
		this.enrollmentFormKeyWrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	protected MedicalEnrollment enrollmentFormKeyInit() {
		if(!enrollmentFormKeyWrap.alreadyInitialized) {
			_enrollmentFormKey(enrollmentFormKeyWrap);
			if(enrollmentFormKey == null)
				setEnrollmentFormKey(enrollmentFormKeyWrap.o);
		}
		enrollmentFormKeyWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public Long solrEnrollmentFormKey() {
		return enrollmentFormKey;
	}

	public String strEnrollmentFormKey() {
		return enrollmentFormKey == null ? "" : enrollmentFormKey.toString();
	}

	public String jsonEnrollmentFormKey() {
		return enrollmentFormKey == null ? "" : enrollmentFormKey.toString();
	}

	public String nomAffichageEnrollmentFormKey() {
		return "enrollment form";
	}

	public String htmTooltipEnrollmentFormKey() {
		return null;
	}

	public String htmEnrollmentFormKey() {
		return enrollmentFormKey == null ? "" : StringEscapeUtils.escapeHtml4(strEnrollmentFormKey());
	}

	//////////////
	// userKeys //
	//////////////

	/**	L'entité « userKeys »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> userKeys = new java.util.ArrayList<java.lang.Long>();
	@JsonIgnore
	public Wrap<List<Long>> userKeysWrap = new Wrap<List<Long>>().p(this).c(List.class).var("userKeys").o(userKeys);

	/**	<br/>L'entité « userKeys »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:userKeys">Trouver l'entité userKeys dans Solr</a>
	 * <br/>
	 * @param userKeys est l'entité déjà construit. 
	 **/
	protected abstract void _userKeys(List<Long> l);

	public List<Long> getUserKeys() {
		return userKeys;
	}

	public void setUserKeys(List<Long> userKeys) {
		this.userKeys = userKeys;
		this.userKeysWrap.alreadyInitialized = true;
	}
	public MedicalEnrollment addUserKeys(Long...objets) {
		for(Long o : objets) {
			addUserKeys(o);
		}
		return (MedicalEnrollment)this;
	}
	public MedicalEnrollment addUserKeys(Long o) {
		if(o != null && !userKeys.contains(o))
			this.userKeys.add(o);
		return (MedicalEnrollment)this;
	}
	public MedicalEnrollment setUserKeys(JsonArray objets) {
		userKeys.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addUserKeys(o);
		}
		return (MedicalEnrollment)this;
	}
	public MedicalEnrollment addUserKeys(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addUserKeys(p);
		}
		return (MedicalEnrollment)this;
	}
	protected MedicalEnrollment userKeysInit() {
		if(!userKeysWrap.alreadyInitialized) {
			_userKeys(userKeys);
		}
		userKeysWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public List<Long> solrUserKeys() {
		return userKeys;
	}

	public String strUserKeys() {
		return userKeys == null ? "" : userKeys.toString();
	}

	public String jsonUserKeys() {
		return userKeys == null ? "" : userKeys.toString();
	}

	public String nomAffichageUserKeys() {
		return "users";
	}

	public String htmTooltipUserKeys() {
		return null;
	}

	public String htmUserKeys() {
		return userKeys == null ? "" : StringEscapeUtils.escapeHtml4(strUserKeys());
	}

	public void inputUserKeys(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("i").a("class", "far fa-search w3-xxlarge w3-cell w3-cell-middle ").f().g("i");
				e("input")
					.a("type", "text")
					.a("placeholder", "users")
					.a("class", "valueObjectSuggest suggestUserKeys w3-input w3-border w3-cell w3-cell-middle ")
					.a("name", "setUserKeys")
					.a("id", classApiMethodMethod, "_userKeys")
					.a("autocomplete", "off")
					.a("oninput", "suggestMedicalEnrollmentUserKeys($(this).val() ? searchSiteUserFilters($(this.parentElement)) : [", pk == null ? "" : "{'name':'fq','value':'enrollmentKeys:" + pk + "'}", "], $('#listMedicalEnrollmentUserKeys_", classApiMethodMethod, "'), ", pk, "); ")
				.fg();

		} else {
			sx(htmUserKeys());
		}
	}

	public void htmUserKeys(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalEnrollmentUserKeys").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row ").f();
							{ e("a").a("href", "/user?fq=enrollmentKeys:", pk).a("class", "w3-cell w3-btn w3-center h4 w3-block h4 w3-gray w3-hover-gray ").f();
								e("i").a("class", "far fa-user-cog ").f().g("i");
								sx("users");
							} g("a");
						} g("div");
						{ e("div").a("class", "w3-cell-row ").f();
							{ e("h5").a("class", "w3-cell ").f();
								sx("relate site users to this enrollment");
							} g("h5");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();
								{ e("div").a("class", "w3-cell-row ").f();

								inputUserKeys(classApiMethodMethod);
								} g("div");
							} g("div");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
								{ e("ul").a("class", "w3-ul w3-hoverable ").a("id", "listMedicalEnrollmentUserKeys_", classApiMethodMethod).f();
								} g("ul");
								if(
										userKeys.contains(siteRequest_.getUserKey())
										|| Objects.equals(sessionId, siteRequest_.getSessionId())
										|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
										|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
								) {
									{ e("div").a("class", "w3-cell-row ").f();
										e("button")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-gray ")
											.a("id", classApiMethodMethod, "_userKeys_add")
											.a("onclick", "$(this).addClass('w3-disabled'); this.disabled = true; this.innerHTML = 'Sending…'; postSiteUserVals({ enrollmentKeys: [ \"", pk, "\" ] }, function() {}, function() { addError($('#", classApiMethodMethod, "userKeys')); });")
											.f().sx("add a site user")
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
	// medicalSort //
	/////////////////

	/**	L'entité « medicalSort »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer medicalSort;
	@JsonIgnore
	public Wrap<Integer> medicalSortWrap = new Wrap<Integer>().p(this).c(Integer.class).var("medicalSort").o(medicalSort);

	/**	<br/>L'entité « medicalSort »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:medicalSort">Trouver l'entité medicalSort dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _medicalSort(Wrap<Integer> c);

	public Integer getMedicalSort() {
		return medicalSort;
	}

	public void setMedicalSort(Integer medicalSort) {
		this.medicalSort = medicalSort;
		this.medicalSortWrap.alreadyInitialized = true;
	}
	public MedicalEnrollment setMedicalSort(String o) {
		if(NumberUtils.isParsable(o))
			this.medicalSort = Integer.parseInt(o);
		this.medicalSortWrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	protected MedicalEnrollment medicalSortInit() {
		if(!medicalSortWrap.alreadyInitialized) {
			_medicalSort(medicalSortWrap);
			if(medicalSort == null)
				setMedicalSort(medicalSortWrap.o);
		}
		medicalSortWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public Integer solrMedicalSort() {
		return medicalSort;
	}

	public String strMedicalSort() {
		return medicalSort == null ? "" : medicalSort.toString();
	}

	public String jsonMedicalSort() {
		return medicalSort == null ? "" : medicalSort.toString();
	}

	public String nomAffichageMedicalSort() {
		return null;
	}

	public String htmTooltipMedicalSort() {
		return null;
	}

	public String htmMedicalSort() {
		return medicalSort == null ? "" : StringEscapeUtils.escapeHtml4(strMedicalSort());
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinicSort">Trouver l'entité clinicSort dans Solr</a>
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
	public MedicalEnrollment setClinicSort(String o) {
		if(NumberUtils.isParsable(o))
			this.clinicSort = Integer.parseInt(o);
		this.clinicSortWrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	protected MedicalEnrollment clinicSortInit() {
		if(!clinicSortWrap.alreadyInitialized) {
			_clinicSort(clinicSortWrap);
			if(clinicSort == null)
				setClinicSort(clinicSortWrap.o);
		}
		clinicSortWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
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
		return null;
	}

	public String htmTooltipClinicSort() {
		return null;
	}

	public String htmClinicSort() {
		return clinicSort == null ? "" : StringEscapeUtils.escapeHtml4(strClinicSort());
	}

	///////////////////
	// patientSearch //
	///////////////////

	/**	L'entité « patientSearch »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut SearchList<MedicalPatient>(). 
	 */
	@JsonIgnore
	@JsonInclude(Include.NON_NULL)
	protected SearchList<MedicalPatient> patientSearch = new SearchList<MedicalPatient>();
	@JsonIgnore
	public Wrap<SearchList<MedicalPatient>> patientSearchWrap = new Wrap<SearchList<MedicalPatient>>().p(this).c(SearchList.class).var("patientSearch").o(patientSearch);

	/**	<br/>L'entité « patientSearch »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut SearchList<MedicalPatient>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:patientSearch">Trouver l'entité patientSearch dans Solr</a>
	 * <br/>
	 * @param patientSearch est l'entité déjà construit. 
	 **/
	protected abstract void _patientSearch(SearchList<MedicalPatient> l);

	public SearchList<MedicalPatient> getPatientSearch() {
		return patientSearch;
	}

	public void setPatientSearch(SearchList<MedicalPatient> patientSearch) {
		this.patientSearch = patientSearch;
		this.patientSearchWrap.alreadyInitialized = true;
	}
	protected MedicalEnrollment patientSearchInit() {
		if(!patientSearchWrap.alreadyInitialized) {
			_patientSearch(patientSearch);
		}
		patientSearch.initDeepForClass(siteRequest_);
		patientSearchWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	//////////////
	// patient_ //
	//////////////

	/**	L'entité « patient_ »
	 *	 is defined as null before being initialized. 
	 */
	@JsonIgnore
	@JsonInclude(Include.NON_NULL)
	protected MedicalPatient patient_;
	@JsonIgnore
	public Wrap<MedicalPatient> patient_Wrap = new Wrap<MedicalPatient>().p(this).c(MedicalPatient.class).var("patient_").o(patient_);

	/**	<br/>L'entité « patient_ »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:patient_">Trouver l'entité patient_ dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patient_(Wrap<MedicalPatient> c);

	public MedicalPatient getPatient_() {
		return patient_;
	}

	public void setPatient_(MedicalPatient patient_) {
		this.patient_ = patient_;
		this.patient_Wrap.alreadyInitialized = true;
	}
	protected MedicalEnrollment patient_Init() {
		if(!patient_Wrap.alreadyInitialized) {
			_patient_(patient_Wrap);
			if(patient_ == null)
				setPatient_(patient_Wrap.o);
		}
		patient_Wrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	//////////////////////
	// patientFirstName //
	//////////////////////

	/**	L'entité « patientFirstName »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String patientFirstName;
	@JsonIgnore
	public Wrap<String> patientFirstNameWrap = new Wrap<String>().p(this).c(String.class).var("patientFirstName").o(patientFirstName);

	/**	<br/>L'entité « patientFirstName »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:patientFirstName">Trouver l'entité patientFirstName dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patientFirstName(Wrap<String> c);

	public String getPatientFirstName() {
		return patientFirstName;
	}

	public void setPatientFirstName(String patientFirstName) {
		this.patientFirstName = patientFirstName;
		this.patientFirstNameWrap.alreadyInitialized = true;
	}
	protected MedicalEnrollment patientFirstNameInit() {
		if(!patientFirstNameWrap.alreadyInitialized) {
			_patientFirstName(patientFirstNameWrap);
			if(patientFirstName == null)
				setPatientFirstName(patientFirstNameWrap.o);
		}
		patientFirstNameWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public String solrPatientFirstName() {
		return patientFirstName;
	}

	public String strPatientFirstName() {
		return patientFirstName == null ? "" : patientFirstName;
	}

	public String jsonPatientFirstName() {
		return patientFirstName == null ? "" : patientFirstName;
	}

	public String nomAffichagePatientFirstName() {
		return null;
	}

	public String htmTooltipPatientFirstName() {
		return null;
	}

	public String htmPatientFirstName() {
		return patientFirstName == null ? "" : StringEscapeUtils.escapeHtml4(strPatientFirstName());
	}

	///////////////////////////////
	// patientFirstNamePreferred //
	///////////////////////////////

	/**	L'entité « patientFirstNamePreferred »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String patientFirstNamePreferred;
	@JsonIgnore
	public Wrap<String> patientFirstNamePreferredWrap = new Wrap<String>().p(this).c(String.class).var("patientFirstNamePreferred").o(patientFirstNamePreferred);

	/**	<br/>L'entité « patientFirstNamePreferred »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:patientFirstNamePreferred">Trouver l'entité patientFirstNamePreferred dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patientFirstNamePreferred(Wrap<String> c);

	public String getPatientFirstNamePreferred() {
		return patientFirstNamePreferred;
	}

	public void setPatientFirstNamePreferred(String patientFirstNamePreferred) {
		this.patientFirstNamePreferred = patientFirstNamePreferred;
		this.patientFirstNamePreferredWrap.alreadyInitialized = true;
	}
	protected MedicalEnrollment patientFirstNamePreferredInit() {
		if(!patientFirstNamePreferredWrap.alreadyInitialized) {
			_patientFirstNamePreferred(patientFirstNamePreferredWrap);
			if(patientFirstNamePreferred == null)
				setPatientFirstNamePreferred(patientFirstNamePreferredWrap.o);
		}
		patientFirstNamePreferredWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public String solrPatientFirstNamePreferred() {
		return patientFirstNamePreferred;
	}

	public String strPatientFirstNamePreferred() {
		return patientFirstNamePreferred == null ? "" : patientFirstNamePreferred;
	}

	public String jsonPatientFirstNamePreferred() {
		return patientFirstNamePreferred == null ? "" : patientFirstNamePreferred;
	}

	public String nomAffichagePatientFirstNamePreferred() {
		return null;
	}

	public String htmTooltipPatientFirstNamePreferred() {
		return null;
	}

	public String htmPatientFirstNamePreferred() {
		return patientFirstNamePreferred == null ? "" : StringEscapeUtils.escapeHtml4(strPatientFirstNamePreferred());
	}

	///////////////////////
	// patientFamilyName //
	///////////////////////

	/**	L'entité « patientFamilyName »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String patientFamilyName;
	@JsonIgnore
	public Wrap<String> patientFamilyNameWrap = new Wrap<String>().p(this).c(String.class).var("patientFamilyName").o(patientFamilyName);

	/**	<br/>L'entité « patientFamilyName »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:patientFamilyName">Trouver l'entité patientFamilyName dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patientFamilyName(Wrap<String> c);

	public String getPatientFamilyName() {
		return patientFamilyName;
	}

	public void setPatientFamilyName(String patientFamilyName) {
		this.patientFamilyName = patientFamilyName;
		this.patientFamilyNameWrap.alreadyInitialized = true;
	}
	protected MedicalEnrollment patientFamilyNameInit() {
		if(!patientFamilyNameWrap.alreadyInitialized) {
			_patientFamilyName(patientFamilyNameWrap);
			if(patientFamilyName == null)
				setPatientFamilyName(patientFamilyNameWrap.o);
		}
		patientFamilyNameWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public String solrPatientFamilyName() {
		return patientFamilyName;
	}

	public String strPatientFamilyName() {
		return patientFamilyName == null ? "" : patientFamilyName;
	}

	public String jsonPatientFamilyName() {
		return patientFamilyName == null ? "" : patientFamilyName;
	}

	public String nomAffichagePatientFamilyName() {
		return null;
	}

	public String htmTooltipPatientFamilyName() {
		return null;
	}

	public String htmPatientFamilyName() {
		return patientFamilyName == null ? "" : StringEscapeUtils.escapeHtml4(strPatientFamilyName());
	}

	/////////////////////////
	// patientCompleteName //
	/////////////////////////

	/**	L'entité « patientCompleteName »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String patientCompleteName;
	@JsonIgnore
	public Wrap<String> patientCompleteNameWrap = new Wrap<String>().p(this).c(String.class).var("patientCompleteName").o(patientCompleteName);

	/**	<br/>L'entité « patientCompleteName »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:patientCompleteName">Trouver l'entité patientCompleteName dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patientCompleteName(Wrap<String> c);

	public String getPatientCompleteName() {
		return patientCompleteName;
	}

	public void setPatientCompleteName(String patientCompleteName) {
		this.patientCompleteName = patientCompleteName;
		this.patientCompleteNameWrap.alreadyInitialized = true;
	}
	protected MedicalEnrollment patientCompleteNameInit() {
		if(!patientCompleteNameWrap.alreadyInitialized) {
			_patientCompleteName(patientCompleteNameWrap);
			if(patientCompleteName == null)
				setPatientCompleteName(patientCompleteNameWrap.o);
		}
		patientCompleteNameWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public String solrPatientCompleteName() {
		return patientCompleteName;
	}

	public String strPatientCompleteName() {
		return patientCompleteName == null ? "" : patientCompleteName;
	}

	public String jsonPatientCompleteName() {
		return patientCompleteName == null ? "" : patientCompleteName;
	}

	public String nomAffichagePatientCompleteName() {
		return "r: PatientNomComplet";
	}

	public String htmTooltipPatientCompleteName() {
		return null;
	}

	public String htmPatientCompleteName() {
		return patientCompleteName == null ? "" : StringEscapeUtils.escapeHtml4(strPatientCompleteName());
	}

	public void inputPatientCompleteName(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("placeholder", "r: PatientNomComplet")
				.a("id", classApiMethodMethod, "_patientCompleteName");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setPatientCompleteName classMedicalEnrollment inputMedicalEnrollment", pk, "PatientCompleteName w3-input w3-border ");
					a("name", "setPatientCompleteName");
				} else {
					a("class", "valuePatientCompleteName w3-input w3-border classMedicalEnrollment inputMedicalEnrollment", pk, "PatientCompleteName w3-input w3-border ");
					a("name", "patientCompleteName");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setPatientCompleteName', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_patientCompleteName')); }, function() { addError($('#", classApiMethodMethod, "_patientCompleteName')); }); ");
				}
				a("value", strPatientCompleteName())
			.fg();

		} else {
			sx(htmPatientCompleteName());
		}
	}

	public void htmPatientCompleteName(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalEnrollmentPatientCompleteName").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-blue-gray ").f();
							e("label").a("for", classApiMethodMethod, "_patientCompleteName").a("class", "").f().sx("r: PatientNomComplet").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputPatientCompleteName(classApiMethodMethod);
							} g("div");
							if(
									userKeys.contains(siteRequest_.getUserKey())
									|| Objects.equals(sessionId, siteRequest_.getSessionId())
									|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
							) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_patientCompleteName')); $('#", classApiMethodMethod, "_patientCompleteName').val(null); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + $('#MedicalEnrollmentForm :input[name=pk]').val() }], 'setPatientCompleteName', null, function() { addGlow($('#", classApiMethodMethod, "_patientCompleteName')); }, function() { addError($('#", classApiMethodMethod, "_patientCompleteName')); }); ")
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

	//////////////////////////////////
	// patientCompleteNamePreferred //
	//////////////////////////////////

	/**	L'entité « patientCompleteNamePreferred »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String patientCompleteNamePreferred;
	@JsonIgnore
	public Wrap<String> patientCompleteNamePreferredWrap = new Wrap<String>().p(this).c(String.class).var("patientCompleteNamePreferred").o(patientCompleteNamePreferred);

	/**	<br/>L'entité « patientCompleteNamePreferred »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:patientCompleteNamePreferred">Trouver l'entité patientCompleteNamePreferred dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patientCompleteNamePreferred(Wrap<String> c);

	public String getPatientCompleteNamePreferred() {
		return patientCompleteNamePreferred;
	}

	public void setPatientCompleteNamePreferred(String patientCompleteNamePreferred) {
		this.patientCompleteNamePreferred = patientCompleteNamePreferred;
		this.patientCompleteNamePreferredWrap.alreadyInitialized = true;
	}
	protected MedicalEnrollment patientCompleteNamePreferredInit() {
		if(!patientCompleteNamePreferredWrap.alreadyInitialized) {
			_patientCompleteNamePreferred(patientCompleteNamePreferredWrap);
			if(patientCompleteNamePreferred == null)
				setPatientCompleteNamePreferred(patientCompleteNamePreferredWrap.o);
		}
		patientCompleteNamePreferredWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public String solrPatientCompleteNamePreferred() {
		return patientCompleteNamePreferred;
	}

	public String strPatientCompleteNamePreferred() {
		return patientCompleteNamePreferred == null ? "" : patientCompleteNamePreferred;
	}

	public String jsonPatientCompleteNamePreferred() {
		return patientCompleteNamePreferred == null ? "" : patientCompleteNamePreferred;
	}

	public String nomAffichagePatientCompleteNamePreferred() {
		return "r: patient_";
	}

	public String htmTooltipPatientCompleteNamePreferred() {
		return null;
	}

	public String htmPatientCompleteNamePreferred() {
		return patientCompleteNamePreferred == null ? "" : StringEscapeUtils.escapeHtml4(strPatientCompleteNamePreferred());
	}

	public void inputPatientCompleteNamePreferred(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("placeholder", "r: patient_")
				.a("id", classApiMethodMethod, "_patientCompleteNamePreferred");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setPatientCompleteNamePreferred classMedicalEnrollment inputMedicalEnrollment", pk, "PatientCompleteNamePreferred w3-input w3-border ");
					a("name", "setPatientCompleteNamePreferred");
				} else {
					a("class", "valuePatientCompleteNamePreferred w3-input w3-border classMedicalEnrollment inputMedicalEnrollment", pk, "PatientCompleteNamePreferred w3-input w3-border ");
					a("name", "patientCompleteNamePreferred");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setPatientCompleteNamePreferred', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_patientCompleteNamePreferred')); }, function() { addError($('#", classApiMethodMethod, "_patientCompleteNamePreferred')); }); ");
				}
				a("value", strPatientCompleteNamePreferred())
			.fg();

		} else {
			sx(htmPatientCompleteNamePreferred());
		}
	}

	public void htmPatientCompleteNamePreferred(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalEnrollmentPatientCompleteNamePreferred").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-blue-gray ").f();
							e("label").a("for", classApiMethodMethod, "_patientCompleteNamePreferred").a("class", "").f().sx("r: patient_").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputPatientCompleteNamePreferred(classApiMethodMethod);
							} g("div");
							if(
									userKeys.contains(siteRequest_.getUserKey())
									|| Objects.equals(sessionId, siteRequest_.getSessionId())
									|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
							) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_patientCompleteNamePreferred')); $('#", classApiMethodMethod, "_patientCompleteNamePreferred').val(null); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + $('#MedicalEnrollmentForm :input[name=pk]').val() }], 'setPatientCompleteNamePreferred', null, function() { addGlow($('#", classApiMethodMethod, "_patientCompleteNamePreferred')); }, function() { addError($('#", classApiMethodMethod, "_patientCompleteNamePreferred')); }); ")
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
	// patientBirthDate //
	//////////////////////

	/**	L'entité « patientBirthDate »
	 *	 is defined as null before being initialized. 
	 */
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	protected LocalDate patientBirthDate;
	@JsonIgnore
	public Wrap<LocalDate> patientBirthDateWrap = new Wrap<LocalDate>().p(this).c(LocalDate.class).var("patientBirthDate").o(patientBirthDate);

	/**	<br/>L'entité « patientBirthDate »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:patientBirthDate">Trouver l'entité patientBirthDate dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patientBirthDate(Wrap<LocalDate> c);

	public LocalDate getPatientBirthDate() {
		return patientBirthDate;
	}

	public void setPatientBirthDate(LocalDate patientBirthDate) {
		this.patientBirthDate = patientBirthDate;
		this.patientBirthDateWrap.alreadyInitialized = true;
	}
	public MedicalEnrollment setPatientBirthDate(Instant o) {
		this.patientBirthDate = o == null ? null : LocalDate.from(o);
		this.patientBirthDateWrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	/** Example: 2011-12-03+01:00 **/
	public MedicalEnrollment setPatientBirthDate(String o) {
		this.patientBirthDate = o == null ? null : LocalDate.parse(o, DateTimeFormatter.ISO_DATE);
		this.patientBirthDateWrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	public MedicalEnrollment setPatientBirthDate(Date o) {
		this.patientBirthDate = o == null ? null : o.toInstant().atZone(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toLocalDate();
		this.patientBirthDateWrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	protected MedicalEnrollment patientBirthDateInit() {
		if(!patientBirthDateWrap.alreadyInitialized) {
			_patientBirthDate(patientBirthDateWrap);
			if(patientBirthDate == null)
				setPatientBirthDate(patientBirthDateWrap.o);
		}
		patientBirthDateWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public Date solrPatientBirthDate() {
		return patientBirthDate == null ? null : Date.from(patientBirthDate.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z")).toInstant());
	}

	public String strPatientBirthDate() {
		return patientBirthDate == null ? "" : patientBirthDate.format(DateTimeFormatter.ofPattern("EEE MMM d, yyyy", Locale.forLanguageTag("en-US")));
	}

	public String jsonPatientBirthDate() {
		return patientBirthDate == null ? "" : patientBirthDate.format(DateTimeFormatter.ISO_DATE);
	}

	public String nomAffichagePatientBirthDate() {
		return "r: patient_";
	}

	public String htmTooltipPatientBirthDate() {
		return null;
	}

	public String htmPatientBirthDate() {
		return patientBirthDate == null ? "" : StringEscapeUtils.escapeHtml4(strPatientBirthDate());
	}

	public void inputPatientBirthDate(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("class", "w3-input w3-border datepicker setPatientBirthDate classMedicalEnrollment inputMedicalEnrollment", pk, "PatientBirthDate w3-input w3-border ")
				.a("placeholder", "MM/DD/YYYY")
				.a("data-timeformat", "MM/dd/yyyy")
				.a("id", classApiMethodMethod, "_patientBirthDate")
				.a("onclick", "removeGlow($(this)); ")
				.a("value", patientBirthDate == null ? "" : DateTimeFormatter.ofPattern("MM/dd/yyyy").format(patientBirthDate))
				.a("onchange", "var t = moment(this.value, 'MM/DD/YYYY'); if(t) { var s = t.format('YYYY-MM-DD'); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setPatientBirthDate', s, function() { addGlow($('#", classApiMethodMethod, "_patientBirthDate')); }, function() { addError($('#", classApiMethodMethod, "_patientBirthDate')); }); } ")
				.fg();
		} else {
			sx(htmPatientBirthDate());
		}
	}

	public void htmPatientBirthDate(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalEnrollmentPatientBirthDate").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-blue-gray ").f();
							e("label").a("for", classApiMethodMethod, "_patientBirthDate").a("class", "").f().sx("r: patient_").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row  ").f();
							{ e("div").a("class", "w3-cell ").f();
								inputPatientBirthDate(classApiMethodMethod);
							} g("div");
							if(
									userKeys.contains(siteRequest_.getUserKey())
									|| Objects.equals(sessionId, siteRequest_.getSessionId())
									|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
							) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_patientBirthDate')); $('#", classApiMethodMethod, "_patientBirthDate').val(null); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + $('#MedicalEnrollmentForm :input[name=pk]').val() }], 'setPatientBirthDate', null, function() { addGlow($('#", classApiMethodMethod, "_patientBirthDate')); }, function() { addError($('#", classApiMethodMethod, "_patientBirthDate')); }); ")
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
	// patientBirthMonth //
	///////////////////////

	/**	L'entité « patientBirthMonth »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer patientBirthMonth;
	@JsonIgnore
	public Wrap<Integer> patientBirthMonthWrap = new Wrap<Integer>().p(this).c(Integer.class).var("patientBirthMonth").o(patientBirthMonth);

	/**	<br/>L'entité « patientBirthMonth »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:patientBirthMonth">Trouver l'entité patientBirthMonth dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patientBirthMonth(Wrap<Integer> c);

	public Integer getPatientBirthMonth() {
		return patientBirthMonth;
	}

	public void setPatientBirthMonth(Integer patientBirthMonth) {
		this.patientBirthMonth = patientBirthMonth;
		this.patientBirthMonthWrap.alreadyInitialized = true;
	}
	public MedicalEnrollment setPatientBirthMonth(String o) {
		if(NumberUtils.isParsable(o))
			this.patientBirthMonth = Integer.parseInt(o);
		this.patientBirthMonthWrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	protected MedicalEnrollment patientBirthMonthInit() {
		if(!patientBirthMonthWrap.alreadyInitialized) {
			_patientBirthMonth(patientBirthMonthWrap);
			if(patientBirthMonth == null)
				setPatientBirthMonth(patientBirthMonthWrap.o);
		}
		patientBirthMonthWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public Integer solrPatientBirthMonth() {
		return patientBirthMonth;
	}

	public String strPatientBirthMonth() {
		return patientBirthMonth == null ? "" : patientBirthMonth.toString();
	}

	public String jsonPatientBirthMonth() {
		return patientBirthMonth == null ? "" : patientBirthMonth.toString();
	}

	public String nomAffichagePatientBirthMonth() {
		return null;
	}

	public String htmTooltipPatientBirthMonth() {
		return null;
	}

	public String htmPatientBirthMonth() {
		return patientBirthMonth == null ? "" : StringEscapeUtils.escapeHtml4(strPatientBirthMonth());
	}

	/////////////////////
	// patientBirthDay //
	/////////////////////

	/**	L'entité « patientBirthDay »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer patientBirthDay;
	@JsonIgnore
	public Wrap<Integer> patientBirthDayWrap = new Wrap<Integer>().p(this).c(Integer.class).var("patientBirthDay").o(patientBirthDay);

	/**	<br/>L'entité « patientBirthDay »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:patientBirthDay">Trouver l'entité patientBirthDay dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patientBirthDay(Wrap<Integer> c);

	public Integer getPatientBirthDay() {
		return patientBirthDay;
	}

	public void setPatientBirthDay(Integer patientBirthDay) {
		this.patientBirthDay = patientBirthDay;
		this.patientBirthDayWrap.alreadyInitialized = true;
	}
	public MedicalEnrollment setPatientBirthDay(String o) {
		if(NumberUtils.isParsable(o))
			this.patientBirthDay = Integer.parseInt(o);
		this.patientBirthDayWrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	protected MedicalEnrollment patientBirthDayInit() {
		if(!patientBirthDayWrap.alreadyInitialized) {
			_patientBirthDay(patientBirthDayWrap);
			if(patientBirthDay == null)
				setPatientBirthDay(patientBirthDayWrap.o);
		}
		patientBirthDayWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public Integer solrPatientBirthDay() {
		return patientBirthDay;
	}

	public String strPatientBirthDay() {
		return patientBirthDay == null ? "" : patientBirthDay.toString();
	}

	public String jsonPatientBirthDay() {
		return patientBirthDay == null ? "" : patientBirthDay.toString();
	}

	public String nomAffichagePatientBirthDay() {
		return null;
	}

	public String htmTooltipPatientBirthDay() {
		return null;
	}

	public String htmPatientBirthDay() {
		return patientBirthDay == null ? "" : StringEscapeUtils.escapeHtml4(strPatientBirthDay());
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinicName">Trouver l'entité clinicName dans Solr</a>
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
	protected MedicalEnrollment clinicNameInit() {
		if(!clinicNameWrap.alreadyInitialized) {
			_clinicName(clinicNameWrap);
			if(clinicName == null)
				setClinicName(clinicNameWrap.o);
		}
		clinicNameWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
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
		return "r: CliniqueNom";
	}

	public String htmTooltipClinicName() {
		return null;
	}

	public String htmClinicName() {
		return clinicName == null ? "" : StringEscapeUtils.escapeHtml4(strClinicName());
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinicCompleteName">Trouver l'entité clinicCompleteName dans Solr</a>
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
	protected MedicalEnrollment clinicCompleteNameInit() {
		if(!clinicCompleteNameWrap.alreadyInitialized) {
			_clinicCompleteName(clinicCompleteNameWrap);
			if(clinicCompleteName == null)
				setClinicCompleteName(clinicCompleteNameWrap.o);
		}
		clinicCompleteNameWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
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
		return "r: CliniqueNomComplet";
	}

	public String htmTooltipClinicCompleteName() {
		return null;
	}

	public String htmClinicCompleteName() {
		return clinicCompleteName == null ? "" : StringEscapeUtils.escapeHtml4(strClinicCompleteName());
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinicLocation">Trouver l'entité clinicLocation dans Solr</a>
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
	protected MedicalEnrollment clinicLocationInit() {
		if(!clinicLocationWrap.alreadyInitialized) {
			_clinicLocation(clinicLocationWrap);
			if(clinicLocation == null)
				setClinicLocation(clinicLocationWrap.o);
		}
		clinicLocationWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinicAddress">Trouver l'entité clinicAddress dans Solr</a>
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
	protected MedicalEnrollment clinicAddressInit() {
		if(!clinicAddressWrap.alreadyInitialized) {
			_clinicAddress(clinicAddressWrap);
			if(clinicAddress == null)
				setClinicAddress(clinicAddressWrap.o);
		}
		clinicAddressWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
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
		MedicalEnrollment s = (MedicalEnrollment)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("placeholder", "address")
				.a("id", classApiMethodMethod, "_clinicAddress");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setClinicAddress classMedicalEnrollment inputMedicalEnrollment", pk, "ClinicAddress w3-input w3-border ");
					a("name", "setClinicAddress");
				} else {
					a("class", "valueClinicAddress w3-input w3-border classMedicalEnrollment inputMedicalEnrollment", pk, "ClinicAddress w3-input w3-border ");
					a("name", "clinicAddress");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setClinicAddress', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_clinicAddress')); }, function() { addError($('#", classApiMethodMethod, "_clinicAddress')); }); ");
				}
				a("value", strClinicAddress())
			.fg();

		} else {
			sx(htmClinicAddress());
		}
	}

	public void htmClinicAddress(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalEnrollmentClinicAddress").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-blue-gray ").f();
							e("label").a("for", classApiMethodMethod, "_clinicAddress").a("class", "").f().sx("address").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputClinicAddress(classApiMethodMethod);
							} g("div");
							if(
									userKeys.contains(siteRequest_.getUserKey())
									|| Objects.equals(sessionId, siteRequest_.getSessionId())
									|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
							) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_clinicAddress')); $('#", classApiMethodMethod, "_clinicAddress').val(null); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + $('#MedicalEnrollmentForm :input[name=pk]').val() }], 'setClinicAddress', null, function() { addGlow($('#", classApiMethodMethod, "_clinicAddress')); }, function() { addError($('#", classApiMethodMethod, "_clinicAddress')); }); ")
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinicPhoneNumber">Trouver l'entité clinicPhoneNumber dans Solr</a>
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
	protected MedicalEnrollment clinicPhoneNumberInit() {
		if(!clinicPhoneNumberWrap.alreadyInitialized) {
			_clinicPhoneNumber(clinicPhoneNumberWrap);
			if(clinicPhoneNumber == null)
				setClinicPhoneNumber(clinicPhoneNumberWrap.o);
		}
		clinicPhoneNumberWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinicAdministratorName">Trouver l'entité clinicAdministratorName dans Solr</a>
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
	protected MedicalEnrollment clinicAdministratorNameInit() {
		if(!clinicAdministratorNameWrap.alreadyInitialized) {
			_clinicAdministratorName(clinicAdministratorNameWrap);
			if(clinicAdministratorName == null)
				setClinicAdministratorName(clinicAdministratorNameWrap.o);
		}
		clinicAdministratorNameWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
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

	////////////////////////
	// enrollmentApproved //
	////////////////////////

	/**	L'entité « enrollmentApproved »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean enrollmentApproved;
	@JsonIgnore
	public Wrap<Boolean> enrollmentApprovedWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("enrollmentApproved").o(enrollmentApproved);

	/**	<br/>L'entité « enrollmentApproved »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentApproved">Trouver l'entité enrollmentApproved dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _enrollmentApproved(Wrap<Boolean> c);

	public Boolean getEnrollmentApproved() {
		return enrollmentApproved;
	}

	public void setEnrollmentApproved(Boolean enrollmentApproved) {
		this.enrollmentApproved = enrollmentApproved;
		this.enrollmentApprovedWrap.alreadyInitialized = true;
	}
	public MedicalEnrollment setEnrollmentApproved(String o) {
		this.enrollmentApproved = Boolean.parseBoolean(o);
		this.enrollmentApprovedWrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	protected MedicalEnrollment enrollmentApprovedInit() {
		if(!enrollmentApprovedWrap.alreadyInitialized) {
			_enrollmentApproved(enrollmentApprovedWrap);
			if(enrollmentApproved == null)
				setEnrollmentApproved(enrollmentApprovedWrap.o);
		}
		enrollmentApprovedWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public Boolean solrEnrollmentApproved() {
		return enrollmentApproved;
	}

	public String strEnrollmentApproved() {
		return enrollmentApproved == null ? "" : enrollmentApproved.toString();
	}

	public String jsonEnrollmentApproved() {
		return enrollmentApproved == null ? "" : enrollmentApproved.toString();
	}

	public String nomAffichageEnrollmentApproved() {
		return "approved";
	}

	public String htmTooltipEnrollmentApproved() {
		return null;
	}

	public String htmEnrollmentApproved() {
		return enrollmentApproved == null ? "" : StringEscapeUtils.escapeHtml4(strEnrollmentApproved());
	}

	public void inputEnrollmentApproved(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			if("Page".equals(classApiMethodMethod)) {
				e("input")
					.a("type", "checkbox")
					.a("id", classApiMethodMethod, "_enrollmentApproved")
					.a("value", "true");
			} else {
				e("select")
					.a("id", classApiMethodMethod, "_enrollmentApproved");
			}
			if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
				a("class", "setEnrollmentApproved classMedicalEnrollment inputMedicalEnrollment", pk, "EnrollmentApproved w3-input w3-border ");
				a("name", "setEnrollmentApproved");
			} else {
				a("class", "valueEnrollmentApproved classMedicalEnrollment inputMedicalEnrollment", pk, "EnrollmentApproved w3-input w3-border ");
				a("name", "enrollmentApproved");
			}
			if("Page".equals(classApiMethodMethod)) {
				a("onchange", "patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setEnrollmentApproved', $(this).prop('checked'), function() { addGlow($('#", classApiMethodMethod, "_enrollmentApproved')); }, function() { addError($('#", classApiMethodMethod, "_enrollmentApproved')); }); ");
			}
			if("Page".equals(classApiMethodMethod)) {
				if(getEnrollmentApproved() != null && getEnrollmentApproved())
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
			sx(htmEnrollmentApproved());
		}
	}

	public void htmEnrollmentApproved(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalEnrollmentEnrollmentApproved").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-blue-gray ").f();
							e("label").a("for", classApiMethodMethod, "_enrollmentApproved").a("class", "").f().sx("approved").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputEnrollmentApproved(classApiMethodMethod);
							} g("div");
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	/////////////////////////////
	// enrollmentImmunizations //
	/////////////////////////////

	/**	L'entité « enrollmentImmunizations »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean enrollmentImmunizations;
	@JsonIgnore
	public Wrap<Boolean> enrollmentImmunizationsWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("enrollmentImmunizations").o(enrollmentImmunizations);

	/**	<br/>L'entité « enrollmentImmunizations »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentImmunizations">Trouver l'entité enrollmentImmunizations dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _enrollmentImmunizations(Wrap<Boolean> c);

	public Boolean getEnrollmentImmunizations() {
		return enrollmentImmunizations;
	}

	public void setEnrollmentImmunizations(Boolean enrollmentImmunizations) {
		this.enrollmentImmunizations = enrollmentImmunizations;
		this.enrollmentImmunizationsWrap.alreadyInitialized = true;
	}
	public MedicalEnrollment setEnrollmentImmunizations(String o) {
		this.enrollmentImmunizations = Boolean.parseBoolean(o);
		this.enrollmentImmunizationsWrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	protected MedicalEnrollment enrollmentImmunizationsInit() {
		if(!enrollmentImmunizationsWrap.alreadyInitialized) {
			_enrollmentImmunizations(enrollmentImmunizationsWrap);
			if(enrollmentImmunizations == null)
				setEnrollmentImmunizations(enrollmentImmunizationsWrap.o);
		}
		enrollmentImmunizationsWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public Boolean solrEnrollmentImmunizations() {
		return enrollmentImmunizations;
	}

	public String strEnrollmentImmunizations() {
		return enrollmentImmunizations == null ? "" : enrollmentImmunizations.toString();
	}

	public String jsonEnrollmentImmunizations() {
		return enrollmentImmunizations == null ? "" : enrollmentImmunizations.toString();
	}

	public String nomAffichageEnrollmentImmunizations() {
		return "immunized";
	}

	public String htmTooltipEnrollmentImmunizations() {
		return null;
	}

	public String htmEnrollmentImmunizations() {
		return enrollmentImmunizations == null ? "" : StringEscapeUtils.escapeHtml4(strEnrollmentImmunizations());
	}

	public void inputEnrollmentImmunizations(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			if("Page".equals(classApiMethodMethod)) {
				e("input")
					.a("type", "checkbox")
					.a("id", classApiMethodMethod, "_enrollmentImmunizations")
					.a("value", "true");
			} else {
				e("select")
					.a("id", classApiMethodMethod, "_enrollmentImmunizations");
			}
			if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
				a("class", "setEnrollmentImmunizations classMedicalEnrollment inputMedicalEnrollment", pk, "EnrollmentImmunizations w3-input w3-border ");
				a("name", "setEnrollmentImmunizations");
			} else {
				a("class", "valueEnrollmentImmunizations classMedicalEnrollment inputMedicalEnrollment", pk, "EnrollmentImmunizations w3-input w3-border ");
				a("name", "enrollmentImmunizations");
			}
			if("Page".equals(classApiMethodMethod)) {
				a("onchange", "patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setEnrollmentImmunizations', $(this).prop('checked'), function() { addGlow($('#", classApiMethodMethod, "_enrollmentImmunizations')); }, function() { addError($('#", classApiMethodMethod, "_enrollmentImmunizations')); }); ");
			}
			if("Page".equals(classApiMethodMethod)) {
				if(getEnrollmentImmunizations() != null && getEnrollmentImmunizations())
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
			sx(htmEnrollmentImmunizations());
		}
	}

	public void htmEnrollmentImmunizations(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalEnrollmentEnrollmentImmunizations").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-blue-gray ").f();
							e("label").a("for", classApiMethodMethod, "_enrollmentImmunizations").a("class", "").f().sx("immunized").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputEnrollmentImmunizations(classApiMethodMethod);
							} g("div");
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	///////////////////
	// familyAddress //
	///////////////////

	/**	L'entité « familyAddress »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String familyAddress;
	@JsonIgnore
	public Wrap<String> familyAddressWrap = new Wrap<String>().p(this).c(String.class).var("familyAddress").o(familyAddress);

	/**	<br/>L'entité « familyAddress »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:familyAddress">Trouver l'entité familyAddress dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _familyAddress(Wrap<String> c);

	public String getFamilyAddress() {
		return familyAddress;
	}

	public void setFamilyAddress(String familyAddress) {
		this.familyAddress = familyAddress;
		this.familyAddressWrap.alreadyInitialized = true;
	}
	protected MedicalEnrollment familyAddressInit() {
		if(!familyAddressWrap.alreadyInitialized) {
			_familyAddress(familyAddressWrap);
			if(familyAddress == null)
				setFamilyAddress(familyAddressWrap.o);
		}
		familyAddressWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public String solrFamilyAddress() {
		return familyAddress;
	}

	public String strFamilyAddress() {
		return familyAddress == null ? "" : familyAddress;
	}

	public String jsonFamilyAddress() {
		return familyAddress == null ? "" : familyAddress;
	}

	public String nomAffichageFamilyAddress() {
		return "family address";
	}

	public String htmTooltipFamilyAddress() {
		return null;
	}

	public String htmFamilyAddress() {
		return familyAddress == null ? "" : StringEscapeUtils.escapeHtml4(strFamilyAddress());
	}

	public void inputFamilyAddress(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("textarea")
				.a("placeholder", "family address")
				.a("id", classApiMethodMethod, "_familyAddress");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setFamilyAddress classMedicalEnrollment inputMedicalEnrollment", pk, "FamilyAddress w3-input w3-border ");
					a("name", "setFamilyAddress");
				} else {
					a("class", "valueFamilyAddress w3-input w3-border classMedicalEnrollment inputMedicalEnrollment", pk, "FamilyAddress w3-input w3-border ");
					a("name", "familyAddress");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setFamilyAddress', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_familyAddress')); }, function() { addError($('#", classApiMethodMethod, "_familyAddress')); }); ");
				}
			f().sx(strFamilyAddress()).g("textarea");

		} else {
			sx(htmFamilyAddress());
		}
	}

	public void htmFamilyAddress(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalEnrollmentFamilyAddress").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-blue-gray ").f();
							e("label").a("for", classApiMethodMethod, "_familyAddress").a("class", "").f().sx("family address").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputFamilyAddress(classApiMethodMethod);
							} g("div");
							if(
									userKeys.contains(siteRequest_.getUserKey())
									|| Objects.equals(sessionId, siteRequest_.getSessionId())
									|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
							) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_familyAddress')); $('#", classApiMethodMethod, "_familyAddress').val(null); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + $('#MedicalEnrollmentForm :input[name=pk]').val() }], 'setFamilyAddress', null, function() { addGlow($('#", classApiMethodMethod, "_familyAddress')); }, function() { addError($('#", classApiMethodMethod, "_familyAddress')); }); ")
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
	// familyHowDoYouKnowTheClinic //
	/////////////////////////////////

	/**	L'entité « familyHowDoYouKnowTheClinic »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String familyHowDoYouKnowTheClinic;
	@JsonIgnore
	public Wrap<String> familyHowDoYouKnowTheClinicWrap = new Wrap<String>().p(this).c(String.class).var("familyHowDoYouKnowTheClinic").o(familyHowDoYouKnowTheClinic);

	/**	<br/>L'entité « familyHowDoYouKnowTheClinic »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:familyHowDoYouKnowTheClinic">Trouver l'entité familyHowDoYouKnowTheClinic dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _familyHowDoYouKnowTheClinic(Wrap<String> c);

	public String getFamilyHowDoYouKnowTheClinic() {
		return familyHowDoYouKnowTheClinic;
	}

	public void setFamilyHowDoYouKnowTheClinic(String familyHowDoYouKnowTheClinic) {
		this.familyHowDoYouKnowTheClinic = familyHowDoYouKnowTheClinic;
		this.familyHowDoYouKnowTheClinicWrap.alreadyInitialized = true;
	}
	protected MedicalEnrollment familyHowDoYouKnowTheClinicInit() {
		if(!familyHowDoYouKnowTheClinicWrap.alreadyInitialized) {
			_familyHowDoYouKnowTheClinic(familyHowDoYouKnowTheClinicWrap);
			if(familyHowDoYouKnowTheClinic == null)
				setFamilyHowDoYouKnowTheClinic(familyHowDoYouKnowTheClinicWrap.o);
		}
		familyHowDoYouKnowTheClinicWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public String solrFamilyHowDoYouKnowTheClinic() {
		return familyHowDoYouKnowTheClinic;
	}

	public String strFamilyHowDoYouKnowTheClinic() {
		return familyHowDoYouKnowTheClinic == null ? "" : familyHowDoYouKnowTheClinic;
	}

	public String jsonFamilyHowDoYouKnowTheClinic() {
		return familyHowDoYouKnowTheClinic == null ? "" : familyHowDoYouKnowTheClinic;
	}

	public String nomAffichageFamilyHowDoYouKnowTheClinic() {
		return "how do you know the clinic? ";
	}

	public String htmTooltipFamilyHowDoYouKnowTheClinic() {
		return null;
	}

	public String htmFamilyHowDoYouKnowTheClinic() {
		return familyHowDoYouKnowTheClinic == null ? "" : StringEscapeUtils.escapeHtml4(strFamilyHowDoYouKnowTheClinic());
	}

	public void inputFamilyHowDoYouKnowTheClinic(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("textarea")
				.a("placeholder", "how do you know the clinic? ")
				.a("id", classApiMethodMethod, "_familyHowDoYouKnowTheClinic");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setFamilyHowDoYouKnowTheClinic classMedicalEnrollment inputMedicalEnrollment", pk, "FamilyHowDoYouKnowTheClinic w3-input w3-border ");
					a("name", "setFamilyHowDoYouKnowTheClinic");
				} else {
					a("class", "valueFamilyHowDoYouKnowTheClinic w3-input w3-border classMedicalEnrollment inputMedicalEnrollment", pk, "FamilyHowDoYouKnowTheClinic w3-input w3-border ");
					a("name", "familyHowDoYouKnowTheClinic");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setFamilyHowDoYouKnowTheClinic', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_familyHowDoYouKnowTheClinic')); }, function() { addError($('#", classApiMethodMethod, "_familyHowDoYouKnowTheClinic')); }); ");
				}
			f().sx(strFamilyHowDoYouKnowTheClinic()).g("textarea");

		} else {
			sx(htmFamilyHowDoYouKnowTheClinic());
		}
	}

	public void htmFamilyHowDoYouKnowTheClinic(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalEnrollmentFamilyHowDoYouKnowTheClinic").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-blue-gray ").f();
							e("label").a("for", classApiMethodMethod, "_familyHowDoYouKnowTheClinic").a("class", "").f().sx("how do you know the clinic? ").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputFamilyHowDoYouKnowTheClinic(classApiMethodMethod);
							} g("div");
							if(
									userKeys.contains(siteRequest_.getUserKey())
									|| Objects.equals(sessionId, siteRequest_.getSessionId())
									|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
							) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_familyHowDoYouKnowTheClinic')); $('#", classApiMethodMethod, "_familyHowDoYouKnowTheClinic').val(null); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + $('#MedicalEnrollmentForm :input[name=pk]').val() }], 'setFamilyHowDoYouKnowTheClinic', null, function() { addGlow($('#", classApiMethodMethod, "_familyHowDoYouKnowTheClinic')); }, function() { addError($('#", classApiMethodMethod, "_familyHowDoYouKnowTheClinic')); }); ")
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

	/////////////////////////////////////
	// enrollmentSpecialConsiderations //
	/////////////////////////////////////

	/**	L'entité « enrollmentSpecialConsiderations »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String enrollmentSpecialConsiderations;
	@JsonIgnore
	public Wrap<String> enrollmentSpecialConsiderationsWrap = new Wrap<String>().p(this).c(String.class).var("enrollmentSpecialConsiderations").o(enrollmentSpecialConsiderations);

	/**	<br/>L'entité « enrollmentSpecialConsiderations »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentSpecialConsiderations">Trouver l'entité enrollmentSpecialConsiderations dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _enrollmentSpecialConsiderations(Wrap<String> c);

	public String getEnrollmentSpecialConsiderations() {
		return enrollmentSpecialConsiderations;
	}

	public void setEnrollmentSpecialConsiderations(String enrollmentSpecialConsiderations) {
		this.enrollmentSpecialConsiderations = enrollmentSpecialConsiderations;
		this.enrollmentSpecialConsiderationsWrap.alreadyInitialized = true;
	}
	protected MedicalEnrollment enrollmentSpecialConsiderationsInit() {
		if(!enrollmentSpecialConsiderationsWrap.alreadyInitialized) {
			_enrollmentSpecialConsiderations(enrollmentSpecialConsiderationsWrap);
			if(enrollmentSpecialConsiderations == null)
				setEnrollmentSpecialConsiderations(enrollmentSpecialConsiderationsWrap.o);
		}
		enrollmentSpecialConsiderationsWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public String solrEnrollmentSpecialConsiderations() {
		return enrollmentSpecialConsiderations;
	}

	public String strEnrollmentSpecialConsiderations() {
		return enrollmentSpecialConsiderations == null ? "" : enrollmentSpecialConsiderations;
	}

	public String jsonEnrollmentSpecialConsiderations() {
		return enrollmentSpecialConsiderations == null ? "" : enrollmentSpecialConsiderations;
	}

	public String nomAffichageEnrollmentSpecialConsiderations() {
		return "special considerations";
	}

	public String htmTooltipEnrollmentSpecialConsiderations() {
		return null;
	}

	public String htmEnrollmentSpecialConsiderations() {
		return enrollmentSpecialConsiderations == null ? "" : StringEscapeUtils.escapeHtml4(strEnrollmentSpecialConsiderations());
	}

	public void inputEnrollmentSpecialConsiderations(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("textarea")
				.a("placeholder", "special considerations")
				.a("id", classApiMethodMethod, "_enrollmentSpecialConsiderations");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setEnrollmentSpecialConsiderations classMedicalEnrollment inputMedicalEnrollment", pk, "EnrollmentSpecialConsiderations w3-input w3-border ");
					a("name", "setEnrollmentSpecialConsiderations");
				} else {
					a("class", "valueEnrollmentSpecialConsiderations w3-input w3-border classMedicalEnrollment inputMedicalEnrollment", pk, "EnrollmentSpecialConsiderations w3-input w3-border ");
					a("name", "enrollmentSpecialConsiderations");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setEnrollmentSpecialConsiderations', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_enrollmentSpecialConsiderations')); }, function() { addError($('#", classApiMethodMethod, "_enrollmentSpecialConsiderations')); }); ");
				}
			f().sx(strEnrollmentSpecialConsiderations()).g("textarea");

		} else {
			sx(htmEnrollmentSpecialConsiderations());
		}
	}

	public void htmEnrollmentSpecialConsiderations(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalEnrollmentEnrollmentSpecialConsiderations").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-blue-gray ").f();
							e("label").a("for", classApiMethodMethod, "_enrollmentSpecialConsiderations").a("class", "").f().sx("special considerations").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputEnrollmentSpecialConsiderations(classApiMethodMethod);
							} g("div");
							if(
									userKeys.contains(siteRequest_.getUserKey())
									|| Objects.equals(sessionId, siteRequest_.getSessionId())
									|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
							) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_enrollmentSpecialConsiderations')); $('#", classApiMethodMethod, "_enrollmentSpecialConsiderations').val(null); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + $('#MedicalEnrollmentForm :input[name=pk]').val() }], 'setEnrollmentSpecialConsiderations', null, function() { addGlow($('#", classApiMethodMethod, "_enrollmentSpecialConsiderations')); }, function() { addError($('#", classApiMethodMethod, "_enrollmentSpecialConsiderations')); }); ")
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
	// patientMedicalConditions //
	//////////////////////////////

	/**	L'entité « patientMedicalConditions »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String patientMedicalConditions;
	@JsonIgnore
	public Wrap<String> patientMedicalConditionsWrap = new Wrap<String>().p(this).c(String.class).var("patientMedicalConditions").o(patientMedicalConditions);

	/**	<br/>L'entité « patientMedicalConditions »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:patientMedicalConditions">Trouver l'entité patientMedicalConditions dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patientMedicalConditions(Wrap<String> c);

	public String getPatientMedicalConditions() {
		return patientMedicalConditions;
	}

	public void setPatientMedicalConditions(String patientMedicalConditions) {
		this.patientMedicalConditions = patientMedicalConditions;
		this.patientMedicalConditionsWrap.alreadyInitialized = true;
	}
	protected MedicalEnrollment patientMedicalConditionsInit() {
		if(!patientMedicalConditionsWrap.alreadyInitialized) {
			_patientMedicalConditions(patientMedicalConditionsWrap);
			if(patientMedicalConditions == null)
				setPatientMedicalConditions(patientMedicalConditionsWrap.o);
		}
		patientMedicalConditionsWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public String solrPatientMedicalConditions() {
		return patientMedicalConditions;
	}

	public String strPatientMedicalConditions() {
		return patientMedicalConditions == null ? "" : patientMedicalConditions;
	}

	public String jsonPatientMedicalConditions() {
		return patientMedicalConditions == null ? "" : patientMedicalConditions;
	}

	public String nomAffichagePatientMedicalConditions() {
		return "medical conditions";
	}

	public String htmTooltipPatientMedicalConditions() {
		return null;
	}

	public String htmPatientMedicalConditions() {
		return patientMedicalConditions == null ? "" : StringEscapeUtils.escapeHtml4(strPatientMedicalConditions());
	}

	public void inputPatientMedicalConditions(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("textarea")
				.a("placeholder", "medical conditions")
				.a("id", classApiMethodMethod, "_patientMedicalConditions");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setPatientMedicalConditions classMedicalEnrollment inputMedicalEnrollment", pk, "PatientMedicalConditions w3-input w3-border ");
					a("name", "setPatientMedicalConditions");
				} else {
					a("class", "valuePatientMedicalConditions w3-input w3-border classMedicalEnrollment inputMedicalEnrollment", pk, "PatientMedicalConditions w3-input w3-border ");
					a("name", "patientMedicalConditions");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setPatientMedicalConditions', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_patientMedicalConditions')); }, function() { addError($('#", classApiMethodMethod, "_patientMedicalConditions')); }); ");
				}
			f().sx(strPatientMedicalConditions()).g("textarea");

		} else {
			sx(htmPatientMedicalConditions());
		}
	}

	public void htmPatientMedicalConditions(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalEnrollmentPatientMedicalConditions").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-blue-gray ").f();
							e("label").a("for", classApiMethodMethod, "_patientMedicalConditions").a("class", "").f().sx("medical conditions").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputPatientMedicalConditions(classApiMethodMethod);
							} g("div");
							if(
									userKeys.contains(siteRequest_.getUserKey())
									|| Objects.equals(sessionId, siteRequest_.getSessionId())
									|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
							) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_patientMedicalConditions')); $('#", classApiMethodMethod, "_patientMedicalConditions').val(null); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + $('#MedicalEnrollmentForm :input[name=pk]').val() }], 'setPatientMedicalConditions', null, function() { addGlow($('#", classApiMethodMethod, "_patientMedicalConditions')); }, function() { addError($('#", classApiMethodMethod, "_patientMedicalConditions')); }); ")
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

	////////////////////////////////////
	// patientPreviousClinicsAttended //
	////////////////////////////////////

	/**	L'entité « patientPreviousClinicsAttended »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String patientPreviousClinicsAttended;
	@JsonIgnore
	public Wrap<String> patientPreviousClinicsAttendedWrap = new Wrap<String>().p(this).c(String.class).var("patientPreviousClinicsAttended").o(patientPreviousClinicsAttended);

	/**	<br/>L'entité « patientPreviousClinicsAttended »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:patientPreviousClinicsAttended">Trouver l'entité patientPreviousClinicsAttended dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patientPreviousClinicsAttended(Wrap<String> c);

	public String getPatientPreviousClinicsAttended() {
		return patientPreviousClinicsAttended;
	}

	public void setPatientPreviousClinicsAttended(String patientPreviousClinicsAttended) {
		this.patientPreviousClinicsAttended = patientPreviousClinicsAttended;
		this.patientPreviousClinicsAttendedWrap.alreadyInitialized = true;
	}
	protected MedicalEnrollment patientPreviousClinicsAttendedInit() {
		if(!patientPreviousClinicsAttendedWrap.alreadyInitialized) {
			_patientPreviousClinicsAttended(patientPreviousClinicsAttendedWrap);
			if(patientPreviousClinicsAttended == null)
				setPatientPreviousClinicsAttended(patientPreviousClinicsAttendedWrap.o);
		}
		patientPreviousClinicsAttendedWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public String solrPatientPreviousClinicsAttended() {
		return patientPreviousClinicsAttended;
	}

	public String strPatientPreviousClinicsAttended() {
		return patientPreviousClinicsAttended == null ? "" : patientPreviousClinicsAttended;
	}

	public String jsonPatientPreviousClinicsAttended() {
		return patientPreviousClinicsAttended == null ? "" : patientPreviousClinicsAttended;
	}

	public String nomAffichagePatientPreviousClinicsAttended() {
		return "clinics previously attended";
	}

	public String htmTooltipPatientPreviousClinicsAttended() {
		return null;
	}

	public String htmPatientPreviousClinicsAttended() {
		return patientPreviousClinicsAttended == null ? "" : StringEscapeUtils.escapeHtml4(strPatientPreviousClinicsAttended());
	}

	public void inputPatientPreviousClinicsAttended(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("textarea")
				.a("placeholder", "clinics previously attended")
				.a("id", classApiMethodMethod, "_patientPreviousClinicsAttended");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setPatientPreviousClinicsAttended classMedicalEnrollment inputMedicalEnrollment", pk, "PatientPreviousClinicsAttended w3-input w3-border ");
					a("name", "setPatientPreviousClinicsAttended");
				} else {
					a("class", "valuePatientPreviousClinicsAttended w3-input w3-border classMedicalEnrollment inputMedicalEnrollment", pk, "PatientPreviousClinicsAttended w3-input w3-border ");
					a("name", "patientPreviousClinicsAttended");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setPatientPreviousClinicsAttended', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_patientPreviousClinicsAttended')); }, function() { addError($('#", classApiMethodMethod, "_patientPreviousClinicsAttended')); }); ");
				}
			f().sx(strPatientPreviousClinicsAttended()).g("textarea");

		} else {
			sx(htmPatientPreviousClinicsAttended());
		}
	}

	public void htmPatientPreviousClinicsAttended(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalEnrollmentPatientPreviousClinicsAttended").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-blue-gray ").f();
							e("label").a("for", classApiMethodMethod, "_patientPreviousClinicsAttended").a("class", "").f().sx("clinics previously attended").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputPatientPreviousClinicsAttended(classApiMethodMethod);
							} g("div");
							if(
									userKeys.contains(siteRequest_.getUserKey())
									|| Objects.equals(sessionId, siteRequest_.getSessionId())
									|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
							) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_patientPreviousClinicsAttended')); $('#", classApiMethodMethod, "_patientPreviousClinicsAttended').val(null); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + $('#MedicalEnrollmentForm :input[name=pk]').val() }], 'setPatientPreviousClinicsAttended', null, function() { addGlow($('#", classApiMethodMethod, "_patientPreviousClinicsAttended')); }, function() { addError($('#", classApiMethodMethod, "_patientPreviousClinicsAttended')); }); ")
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
	public Wrap<String> patientDescriptionWrap = new Wrap<String>().p(this).c(String.class).var("patientDescription").o(patientDescription);

	/**	<br/>L'entité « patientDescription »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:patientDescription">Trouver l'entité patientDescription dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patientDescription(Wrap<String> c);

	public String getPatientDescription() {
		return patientDescription;
	}

	public void setPatientDescription(String patientDescription) {
		this.patientDescription = patientDescription;
		this.patientDescriptionWrap.alreadyInitialized = true;
	}
	protected MedicalEnrollment patientDescriptionInit() {
		if(!patientDescriptionWrap.alreadyInitialized) {
			_patientDescription(patientDescriptionWrap);
			if(patientDescription == null)
				setPatientDescription(patientDescriptionWrap.o);
		}
		patientDescriptionWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
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

	public void inputPatientDescription(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("textarea")
				.a("placeholder", "description")
				.a("id", classApiMethodMethod, "_patientDescription");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setPatientDescription classMedicalEnrollment inputMedicalEnrollment", pk, "PatientDescription w3-input w3-border ");
					a("name", "setPatientDescription");
				} else {
					a("class", "valuePatientDescription w3-input w3-border classMedicalEnrollment inputMedicalEnrollment", pk, "PatientDescription w3-input w3-border ");
					a("name", "patientDescription");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setPatientDescription', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_patientDescription')); }, function() { addError($('#", classApiMethodMethod, "_patientDescription')); }); ");
				}
			f().sx(strPatientDescription()).g("textarea");

		} else {
			sx(htmPatientDescription());
		}
	}

	public void htmPatientDescription(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalEnrollmentPatientDescription").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-blue-gray ").f();
							e("label").a("for", classApiMethodMethod, "_patientDescription").a("class", "").f().sx("description").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputPatientDescription(classApiMethodMethod);
							} g("div");
							if(
									userKeys.contains(siteRequest_.getUserKey())
									|| Objects.equals(sessionId, siteRequest_.getSessionId())
									|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
							) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_patientDescription')); $('#", classApiMethodMethod, "_patientDescription').val(null); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + $('#MedicalEnrollmentForm :input[name=pk]').val() }], 'setPatientDescription', null, function() { addGlow($('#", classApiMethodMethod, "_patientDescription')); }, function() { addError($('#", classApiMethodMethod, "_patientDescription')); }); ")
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
	// patientObjectives //
	///////////////////////

	/**	L'entité « patientObjectives »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String patientObjectives;
	@JsonIgnore
	public Wrap<String> patientObjectivesWrap = new Wrap<String>().p(this).c(String.class).var("patientObjectives").o(patientObjectives);

	/**	<br/>L'entité « patientObjectives »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:patientObjectives">Trouver l'entité patientObjectives dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patientObjectives(Wrap<String> c);

	public String getPatientObjectives() {
		return patientObjectives;
	}

	public void setPatientObjectives(String patientObjectives) {
		this.patientObjectives = patientObjectives;
		this.patientObjectivesWrap.alreadyInitialized = true;
	}
	protected MedicalEnrollment patientObjectivesInit() {
		if(!patientObjectivesWrap.alreadyInitialized) {
			_patientObjectives(patientObjectivesWrap);
			if(patientObjectives == null)
				setPatientObjectives(patientObjectivesWrap.o);
		}
		patientObjectivesWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public String solrPatientObjectives() {
		return patientObjectives;
	}

	public String strPatientObjectives() {
		return patientObjectives == null ? "" : patientObjectives;
	}

	public String jsonPatientObjectives() {
		return patientObjectives == null ? "" : patientObjectives;
	}

	public String nomAffichagePatientObjectives() {
		return "objectives";
	}

	public String htmTooltipPatientObjectives() {
		return null;
	}

	public String htmPatientObjectives() {
		return patientObjectives == null ? "" : StringEscapeUtils.escapeHtml4(strPatientObjectives());
	}

	public void inputPatientObjectives(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("textarea")
				.a("placeholder", "objectives")
				.a("id", classApiMethodMethod, "_patientObjectives");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setPatientObjectives classMedicalEnrollment inputMedicalEnrollment", pk, "PatientObjectives w3-input w3-border ");
					a("name", "setPatientObjectives");
				} else {
					a("class", "valuePatientObjectives w3-input w3-border classMedicalEnrollment inputMedicalEnrollment", pk, "PatientObjectives w3-input w3-border ");
					a("name", "patientObjectives");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setPatientObjectives', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_patientObjectives')); }, function() { addError($('#", classApiMethodMethod, "_patientObjectives')); }); ");
				}
			f().sx(strPatientObjectives()).g("textarea");

		} else {
			sx(htmPatientObjectives());
		}
	}

	public void htmPatientObjectives(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalEnrollmentPatientObjectives").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-blue-gray ").f();
							e("label").a("for", classApiMethodMethod, "_patientObjectives").a("class", "").f().sx("objectives").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputPatientObjectives(classApiMethodMethod);
							} g("div");
							if(
									userKeys.contains(siteRequest_.getUserKey())
									|| Objects.equals(sessionId, siteRequest_.getSessionId())
									|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
							) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_patientObjectives')); $('#", classApiMethodMethod, "_patientObjectives').val(null); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + $('#MedicalEnrollmentForm :input[name=pk]').val() }], 'setPatientObjectives', null, function() { addGlow($('#", classApiMethodMethod, "_patientObjectives')); }, function() { addError($('#", classApiMethodMethod, "_patientObjectives')); }); ")
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
	public Wrap<String> customerProfileIdWrap = new Wrap<String>().p(this).c(String.class).var("customerProfileId").o(customerProfileId);

	/**	<br/>L'entité « customerProfileId »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:customerProfileId">Trouver l'entité customerProfileId dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _customerProfileId(Wrap<String> c);

	public String getCustomerProfileId() {
		return customerProfileId;
	}

	public void setCustomerProfileId(String customerProfileId) {
		this.customerProfileId = customerProfileId;
		this.customerProfileIdWrap.alreadyInitialized = true;
	}
	protected MedicalEnrollment customerProfileIdInit() {
		if(!customerProfileIdWrap.alreadyInitialized) {
			_customerProfileId(customerProfileIdWrap);
			if(customerProfileId == null)
				setCustomerProfileId(customerProfileIdWrap.o);
		}
		customerProfileIdWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
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

	public void inputCustomerProfileId(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("placeholder", "customer profile ID")
				.a("id", classApiMethodMethod, "_customerProfileId");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setCustomerProfileId classMedicalEnrollment inputMedicalEnrollment", pk, "CustomerProfileId w3-input w3-border ");
					a("name", "setCustomerProfileId");
				} else {
					a("class", "valueCustomerProfileId w3-input w3-border classMedicalEnrollment inputMedicalEnrollment", pk, "CustomerProfileId w3-input w3-border ");
					a("name", "customerProfileId");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setCustomerProfileId', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_customerProfileId')); }, function() { addError($('#", classApiMethodMethod, "_customerProfileId')); }); ");
				}
				a("value", strCustomerProfileId())
			.fg();

		} else {
			sx(htmCustomerProfileId());
		}
	}

	public void htmCustomerProfileId(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalEnrollmentCustomerProfileId").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-blue-gray ").f();
							e("label").a("for", classApiMethodMethod, "_customerProfileId").a("class", "").f().sx("customer profile ID").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputCustomerProfileId(classApiMethodMethod);
							} g("div");
							if(
									userKeys.contains(siteRequest_.getUserKey())
									|| Objects.equals(sessionId, siteRequest_.getSessionId())
									|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
							) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_customerProfileId')); $('#", classApiMethodMethod, "_customerProfileId').val(null); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + $('#MedicalEnrollmentForm :input[name=pk]').val() }], 'setCustomerProfileId', null, function() { addGlow($('#", classApiMethodMethod, "_customerProfileId')); }, function() { addError($('#", classApiMethodMethod, "_customerProfileId')); }); ")
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

	/////////////////
	// createdYear //
	/////////////////

	/**	L'entité « createdYear »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer createdYear;
	@JsonIgnore
	public Wrap<Integer> createdYearWrap = new Wrap<Integer>().p(this).c(Integer.class).var("createdYear").o(createdYear);

	/**	<br/>L'entité « createdYear »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:createdYear">Trouver l'entité createdYear dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _createdYear(Wrap<Integer> c);

	public Integer getCreatedYear() {
		return createdYear;
	}

	public void setCreatedYear(Integer createdYear) {
		this.createdYear = createdYear;
		this.createdYearWrap.alreadyInitialized = true;
	}
	public MedicalEnrollment setCreatedYear(String o) {
		if(NumberUtils.isParsable(o))
			this.createdYear = Integer.parseInt(o);
		this.createdYearWrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	protected MedicalEnrollment createdYearInit() {
		if(!createdYearWrap.alreadyInitialized) {
			_createdYear(createdYearWrap);
			if(createdYear == null)
				setCreatedYear(createdYearWrap.o);
		}
		createdYearWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public Integer solrCreatedYear() {
		return createdYear;
	}

	public String strCreatedYear() {
		return createdYear == null ? "" : createdYear.toString();
	}

	public String jsonCreatedYear() {
		return createdYear == null ? "" : createdYear.toString();
	}

	public String nomAffichageCreatedYear() {
		return "created clinic";
	}

	public String htmTooltipCreatedYear() {
		return null;
	}

	public String htmCreatedYear() {
		return createdYear == null ? "" : StringEscapeUtils.escapeHtml4(strCreatedYear());
	}

	//////////////////////
	// createdDayOfWeek //
	//////////////////////

	/**	L'entité « createdDayOfWeek »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String createdDayOfWeek;
	@JsonIgnore
	public Wrap<String> createdDayOfWeekWrap = new Wrap<String>().p(this).c(String.class).var("createdDayOfWeek").o(createdDayOfWeek);

	/**	<br/>L'entité « createdDayOfWeek »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:createdDayOfWeek">Trouver l'entité createdDayOfWeek dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _createdDayOfWeek(Wrap<String> c);

	public String getCreatedDayOfWeek() {
		return createdDayOfWeek;
	}

	public void setCreatedDayOfWeek(String createdDayOfWeek) {
		this.createdDayOfWeek = createdDayOfWeek;
		this.createdDayOfWeekWrap.alreadyInitialized = true;
	}
	protected MedicalEnrollment createdDayOfWeekInit() {
		if(!createdDayOfWeekWrap.alreadyInitialized) {
			_createdDayOfWeek(createdDayOfWeekWrap);
			if(createdDayOfWeek == null)
				setCreatedDayOfWeek(createdDayOfWeekWrap.o);
		}
		createdDayOfWeekWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public String solrCreatedDayOfWeek() {
		return createdDayOfWeek;
	}

	public String strCreatedDayOfWeek() {
		return createdDayOfWeek == null ? "" : createdDayOfWeek;
	}

	public String jsonCreatedDayOfWeek() {
		return createdDayOfWeek == null ? "" : createdDayOfWeek;
	}

	public String nomAffichageCreatedDayOfWeek() {
		return "created day of the week";
	}

	public String htmTooltipCreatedDayOfWeek() {
		return null;
	}

	public String htmCreatedDayOfWeek() {
		return createdDayOfWeek == null ? "" : StringEscapeUtils.escapeHtml4(strCreatedDayOfWeek());
	}

	////////////////////////
	// createdMonthOfYear //
	////////////////////////

	/**	L'entité « createdMonthOfYear »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String createdMonthOfYear;
	@JsonIgnore
	public Wrap<String> createdMonthOfYearWrap = new Wrap<String>().p(this).c(String.class).var("createdMonthOfYear").o(createdMonthOfYear);

	/**	<br/>L'entité « createdMonthOfYear »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:createdMonthOfYear">Trouver l'entité createdMonthOfYear dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _createdMonthOfYear(Wrap<String> c);

	public String getCreatedMonthOfYear() {
		return createdMonthOfYear;
	}

	public void setCreatedMonthOfYear(String createdMonthOfYear) {
		this.createdMonthOfYear = createdMonthOfYear;
		this.createdMonthOfYearWrap.alreadyInitialized = true;
	}
	protected MedicalEnrollment createdMonthOfYearInit() {
		if(!createdMonthOfYearWrap.alreadyInitialized) {
			_createdMonthOfYear(createdMonthOfYearWrap);
			if(createdMonthOfYear == null)
				setCreatedMonthOfYear(createdMonthOfYearWrap.o);
		}
		createdMonthOfYearWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public String solrCreatedMonthOfYear() {
		return createdMonthOfYear;
	}

	public String strCreatedMonthOfYear() {
		return createdMonthOfYear == null ? "" : createdMonthOfYear;
	}

	public String jsonCreatedMonthOfYear() {
		return createdMonthOfYear == null ? "" : createdMonthOfYear;
	}

	public String nomAffichageCreatedMonthOfYear() {
		return "created month of the clinic";
	}

	public String htmTooltipCreatedMonthOfYear() {
		return null;
	}

	public String htmCreatedMonthOfYear() {
		return createdMonthOfYear == null ? "" : StringEscapeUtils.escapeHtml4(strCreatedMonthOfYear());
	}

	//////////////////////
	// createdHourOfDay //
	//////////////////////

	/**	L'entité « createdHourOfDay »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String createdHourOfDay;
	@JsonIgnore
	public Wrap<String> createdHourOfDayWrap = new Wrap<String>().p(this).c(String.class).var("createdHourOfDay").o(createdHourOfDay);

	/**	<br/>L'entité « createdHourOfDay »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:createdHourOfDay">Trouver l'entité createdHourOfDay dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _createdHourOfDay(Wrap<String> c);

	public String getCreatedHourOfDay() {
		return createdHourOfDay;
	}

	public void setCreatedHourOfDay(String createdHourOfDay) {
		this.createdHourOfDay = createdHourOfDay;
		this.createdHourOfDayWrap.alreadyInitialized = true;
	}
	protected MedicalEnrollment createdHourOfDayInit() {
		if(!createdHourOfDayWrap.alreadyInitialized) {
			_createdHourOfDay(createdHourOfDayWrap);
			if(createdHourOfDay == null)
				setCreatedHourOfDay(createdHourOfDayWrap.o);
		}
		createdHourOfDayWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public String solrCreatedHourOfDay() {
		return createdHourOfDay;
	}

	public String strCreatedHourOfDay() {
		return createdHourOfDay == null ? "" : createdHourOfDay;
	}

	public String jsonCreatedHourOfDay() {
		return createdHourOfDay == null ? "" : createdHourOfDay;
	}

	public String nomAffichageCreatedHourOfDay() {
		return "hour of day";
	}

	public String htmTooltipCreatedHourOfDay() {
		return null;
	}

	public String htmCreatedHourOfDay() {
		return createdHourOfDay == null ? "" : StringEscapeUtils.escapeHtml4(strCreatedHourOfDay());
	}

	//////////////////////////
	// enrollmentSignature1 //
	//////////////////////////

	/**	L'entité « enrollmentSignature1 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String enrollmentSignature1;
	@JsonIgnore
	public Wrap<String> enrollmentSignature1Wrap = new Wrap<String>().p(this).c(String.class).var("enrollmentSignature1").o(enrollmentSignature1);

	/**	<br/>L'entité « enrollmentSignature1 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentSignature1">Trouver l'entité enrollmentSignature1 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _enrollmentSignature1(Wrap<String> c);

	public String getEnrollmentSignature1() {
		return enrollmentSignature1;
	}

	public void setEnrollmentSignature1(String enrollmentSignature1) {
		this.enrollmentSignature1 = enrollmentSignature1;
		this.enrollmentSignature1Wrap.alreadyInitialized = true;
	}
	protected MedicalEnrollment enrollmentSignature1Init() {
		if(!enrollmentSignature1Wrap.alreadyInitialized) {
			_enrollmentSignature1(enrollmentSignature1Wrap);
			if(enrollmentSignature1 == null)
				setEnrollmentSignature1(enrollmentSignature1Wrap.o);
		}
		enrollmentSignature1Wrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public String solrEnrollmentSignature1() {
		return enrollmentSignature1;
	}

	public String strEnrollmentSignature1() {
		return enrollmentSignature1 == null ? "" : enrollmentSignature1;
	}

	public String jsonEnrollmentSignature1() {
		return enrollmentSignature1 == null ? "" : enrollmentSignature1;
	}

	public String nomAffichageEnrollmentSignature1() {
		return null;
	}

	public String htmTooltipEnrollmentSignature1() {
		return null;
	}

	public String htmEnrollmentSignature1() {
		return enrollmentSignature1 == null ? "" : StringEscapeUtils.escapeHtml4(strEnrollmentSignature1());
	}

	public void inputEnrollmentSignature1(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("div").a("class", "signatureDiv1MedicalEnrollment_enrollmentSignature1").a("id", "signatureDiv1MedicalEnrollment", pk, "enrollmentSignature1").f();
				e("div").a("id", "signatureInputMedicalEnrollment", pk, "enrollmentSignature1");
					a("style", "display: ", StringUtils.isBlank(enrollmentSignature1) ? "block" : "none", "; ");
				f().g("div");
				e("img").a("id", "signatureImgMedicalEnrollment", pk, "enrollmentSignature1");
					a("src", StringUtils.isBlank(enrollmentSignature1) ? "data:image/png;base64," : enrollmentSignature1).a("alt", "");
					a("style", "padding: 10px; display: ", StringUtils.isBlank(enrollmentSignature1) ? "none" : "block", "; ");
				fg();
			g("div");
			e("div").a("id", "signatureDiv2MedicalEnrollment", pk, "enrollmentSignature1").f();
				e("button").a("id", "signatureButtonClearMedicalEnrollment", pk, "enrollmentSignature1");
					a("class", "w3-btn w3-round w3-border w3-border-black w3-section w3-ripple w3-padding w3-margin ");
					s(" onclick=", "\"");
						s("$('#signatureInputMedicalEnrollment", pk, "enrollmentSignature1').show(); ");
						s("$('#signatureImgMedicalEnrollment", pk, "enrollmentSignature1').hide(); ");
						s("removeGlow($('#signatureInputMedicalEnrollment", pk, "enrollmentSignature1')); ");
						s("patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setEnrollmentSignature1', null); ");
						s("if($('#signatureInputMedicalEnrollment", pk, "enrollmentSignature1')) { ");
						s("$('#signatureInputMedicalEnrollment", pk, "enrollmentSignature1').jSignature('reset'); ");
						s(" } else { ");
						s("$('#signatureInputMedicalEnrollment", pk, "enrollmentSignature1').jSignature({'height':200}); ");
						s(" } ");
					s("\"");
					f().sx("Clear");
				g("button");
			g("div");
		} else {
			sx(htmEnrollmentSignature1());
		}
	}

	public void htmEnrollmentSignature1(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalEnrollmentEnrollmentSignature1").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputEnrollmentSignature1(classApiMethodMethod);
							} g("div");
							if(
									userKeys.contains(siteRequest_.getUserKey())
									|| Objects.equals(sessionId, siteRequest_.getSessionId())
									|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
							) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_enrollmentSignature1')); $('#", classApiMethodMethod, "_enrollmentSignature1').val(null); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + $('#MedicalEnrollmentForm :input[name=pk]').val() }], 'setEnrollmentSignature1', null, function() { addGlow($('#", classApiMethodMethod, "_enrollmentSignature1')); }, function() { addError($('#", classApiMethodMethod, "_enrollmentSignature1')); }); ")
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
	// enrollmentSignature2 //
	//////////////////////////

	/**	L'entité « enrollmentSignature2 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String enrollmentSignature2;
	@JsonIgnore
	public Wrap<String> enrollmentSignature2Wrap = new Wrap<String>().p(this).c(String.class).var("enrollmentSignature2").o(enrollmentSignature2);

	/**	<br/>L'entité « enrollmentSignature2 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentSignature2">Trouver l'entité enrollmentSignature2 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _enrollmentSignature2(Wrap<String> c);

	public String getEnrollmentSignature2() {
		return enrollmentSignature2;
	}

	public void setEnrollmentSignature2(String enrollmentSignature2) {
		this.enrollmentSignature2 = enrollmentSignature2;
		this.enrollmentSignature2Wrap.alreadyInitialized = true;
	}
	protected MedicalEnrollment enrollmentSignature2Init() {
		if(!enrollmentSignature2Wrap.alreadyInitialized) {
			_enrollmentSignature2(enrollmentSignature2Wrap);
			if(enrollmentSignature2 == null)
				setEnrollmentSignature2(enrollmentSignature2Wrap.o);
		}
		enrollmentSignature2Wrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public String solrEnrollmentSignature2() {
		return enrollmentSignature2;
	}

	public String strEnrollmentSignature2() {
		return enrollmentSignature2 == null ? "" : enrollmentSignature2;
	}

	public String jsonEnrollmentSignature2() {
		return enrollmentSignature2 == null ? "" : enrollmentSignature2;
	}

	public String nomAffichageEnrollmentSignature2() {
		return null;
	}

	public String htmTooltipEnrollmentSignature2() {
		return null;
	}

	public String htmEnrollmentSignature2() {
		return enrollmentSignature2 == null ? "" : StringEscapeUtils.escapeHtml4(strEnrollmentSignature2());
	}

	public void inputEnrollmentSignature2(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("div").a("class", "signatureDiv1MedicalEnrollment_enrollmentSignature2").a("id", "signatureDiv1MedicalEnrollment", pk, "enrollmentSignature2").f();
				e("div").a("id", "signatureInputMedicalEnrollment", pk, "enrollmentSignature2");
					a("style", "display: ", StringUtils.isBlank(enrollmentSignature2) ? "block" : "none", "; ");
				f().g("div");
				e("img").a("id", "signatureImgMedicalEnrollment", pk, "enrollmentSignature2");
					a("src", StringUtils.isBlank(enrollmentSignature2) ? "data:image/png;base64," : enrollmentSignature2).a("alt", "");
					a("style", "padding: 10px; display: ", StringUtils.isBlank(enrollmentSignature2) ? "none" : "block", "; ");
				fg();
			g("div");
			e("div").a("id", "signatureDiv2MedicalEnrollment", pk, "enrollmentSignature2").f();
				e("button").a("id", "signatureButtonClearMedicalEnrollment", pk, "enrollmentSignature2");
					a("class", "w3-btn w3-round w3-border w3-border-black w3-section w3-ripple w3-padding w3-margin ");
					s(" onclick=", "\"");
						s("$('#signatureInputMedicalEnrollment", pk, "enrollmentSignature2').show(); ");
						s("$('#signatureImgMedicalEnrollment", pk, "enrollmentSignature2').hide(); ");
						s("removeGlow($('#signatureInputMedicalEnrollment", pk, "enrollmentSignature2')); ");
						s("patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setEnrollmentSignature2', null); ");
						s("if($('#signatureInputMedicalEnrollment", pk, "enrollmentSignature2')) { ");
						s("$('#signatureInputMedicalEnrollment", pk, "enrollmentSignature2').jSignature('reset'); ");
						s(" } else { ");
						s("$('#signatureInputMedicalEnrollment", pk, "enrollmentSignature2').jSignature({'height':200}); ");
						s(" } ");
					s("\"");
					f().sx("Clear");
				g("button");
			g("div");
		} else {
			sx(htmEnrollmentSignature2());
		}
	}

	public void htmEnrollmentSignature2(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalEnrollmentEnrollmentSignature2").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputEnrollmentSignature2(classApiMethodMethod);
							} g("div");
							if(
									userKeys.contains(siteRequest_.getUserKey())
									|| Objects.equals(sessionId, siteRequest_.getSessionId())
									|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
							) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_enrollmentSignature2')); $('#", classApiMethodMethod, "_enrollmentSignature2').val(null); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + $('#MedicalEnrollmentForm :input[name=pk]').val() }], 'setEnrollmentSignature2', null, function() { addGlow($('#", classApiMethodMethod, "_enrollmentSignature2')); }, function() { addError($('#", classApiMethodMethod, "_enrollmentSignature2')); }); ")
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
	// enrollmentSignature3 //
	//////////////////////////

	/**	L'entité « enrollmentSignature3 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String enrollmentSignature3;
	@JsonIgnore
	public Wrap<String> enrollmentSignature3Wrap = new Wrap<String>().p(this).c(String.class).var("enrollmentSignature3").o(enrollmentSignature3);

	/**	<br/>L'entité « enrollmentSignature3 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentSignature3">Trouver l'entité enrollmentSignature3 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _enrollmentSignature3(Wrap<String> c);

	public String getEnrollmentSignature3() {
		return enrollmentSignature3;
	}

	public void setEnrollmentSignature3(String enrollmentSignature3) {
		this.enrollmentSignature3 = enrollmentSignature3;
		this.enrollmentSignature3Wrap.alreadyInitialized = true;
	}
	protected MedicalEnrollment enrollmentSignature3Init() {
		if(!enrollmentSignature3Wrap.alreadyInitialized) {
			_enrollmentSignature3(enrollmentSignature3Wrap);
			if(enrollmentSignature3 == null)
				setEnrollmentSignature3(enrollmentSignature3Wrap.o);
		}
		enrollmentSignature3Wrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public String solrEnrollmentSignature3() {
		return enrollmentSignature3;
	}

	public String strEnrollmentSignature3() {
		return enrollmentSignature3 == null ? "" : enrollmentSignature3;
	}

	public String jsonEnrollmentSignature3() {
		return enrollmentSignature3 == null ? "" : enrollmentSignature3;
	}

	public String nomAffichageEnrollmentSignature3() {
		return null;
	}

	public String htmTooltipEnrollmentSignature3() {
		return null;
	}

	public String htmEnrollmentSignature3() {
		return enrollmentSignature3 == null ? "" : StringEscapeUtils.escapeHtml4(strEnrollmentSignature3());
	}

	public void inputEnrollmentSignature3(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("div").a("class", "signatureDiv1MedicalEnrollment_enrollmentSignature3").a("id", "signatureDiv1MedicalEnrollment", pk, "enrollmentSignature3").f();
				e("div").a("id", "signatureInputMedicalEnrollment", pk, "enrollmentSignature3");
					a("style", "display: ", StringUtils.isBlank(enrollmentSignature3) ? "block" : "none", "; ");
				f().g("div");
				e("img").a("id", "signatureImgMedicalEnrollment", pk, "enrollmentSignature3");
					a("src", StringUtils.isBlank(enrollmentSignature3) ? "data:image/png;base64," : enrollmentSignature3).a("alt", "");
					a("style", "padding: 10px; display: ", StringUtils.isBlank(enrollmentSignature3) ? "none" : "block", "; ");
				fg();
			g("div");
			e("div").a("id", "signatureDiv2MedicalEnrollment", pk, "enrollmentSignature3").f();
				e("button").a("id", "signatureButtonClearMedicalEnrollment", pk, "enrollmentSignature3");
					a("class", "w3-btn w3-round w3-border w3-border-black w3-section w3-ripple w3-padding w3-margin ");
					s(" onclick=", "\"");
						s("$('#signatureInputMedicalEnrollment", pk, "enrollmentSignature3').show(); ");
						s("$('#signatureImgMedicalEnrollment", pk, "enrollmentSignature3').hide(); ");
						s("removeGlow($('#signatureInputMedicalEnrollment", pk, "enrollmentSignature3')); ");
						s("patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setEnrollmentSignature3', null); ");
						s("if($('#signatureInputMedicalEnrollment", pk, "enrollmentSignature3')) { ");
						s("$('#signatureInputMedicalEnrollment", pk, "enrollmentSignature3').jSignature('reset'); ");
						s(" } else { ");
						s("$('#signatureInputMedicalEnrollment", pk, "enrollmentSignature3').jSignature({'height':200}); ");
						s(" } ");
					s("\"");
					f().sx("Clear");
				g("button");
			g("div");
		} else {
			sx(htmEnrollmentSignature3());
		}
	}

	public void htmEnrollmentSignature3(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalEnrollmentEnrollmentSignature3").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputEnrollmentSignature3(classApiMethodMethod);
							} g("div");
							if(
									userKeys.contains(siteRequest_.getUserKey())
									|| Objects.equals(sessionId, siteRequest_.getSessionId())
									|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
							) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_enrollmentSignature3')); $('#", classApiMethodMethod, "_enrollmentSignature3').val(null); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + $('#MedicalEnrollmentForm :input[name=pk]').val() }], 'setEnrollmentSignature3', null, function() { addGlow($('#", classApiMethodMethod, "_enrollmentSignature3')); }, function() { addError($('#", classApiMethodMethod, "_enrollmentSignature3')); }); ")
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
	// enrollmentSignature4 //
	//////////////////////////

	/**	L'entité « enrollmentSignature4 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String enrollmentSignature4;
	@JsonIgnore
	public Wrap<String> enrollmentSignature4Wrap = new Wrap<String>().p(this).c(String.class).var("enrollmentSignature4").o(enrollmentSignature4);

	/**	<br/>L'entité « enrollmentSignature4 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentSignature4">Trouver l'entité enrollmentSignature4 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _enrollmentSignature4(Wrap<String> c);

	public String getEnrollmentSignature4() {
		return enrollmentSignature4;
	}

	public void setEnrollmentSignature4(String enrollmentSignature4) {
		this.enrollmentSignature4 = enrollmentSignature4;
		this.enrollmentSignature4Wrap.alreadyInitialized = true;
	}
	protected MedicalEnrollment enrollmentSignature4Init() {
		if(!enrollmentSignature4Wrap.alreadyInitialized) {
			_enrollmentSignature4(enrollmentSignature4Wrap);
			if(enrollmentSignature4 == null)
				setEnrollmentSignature4(enrollmentSignature4Wrap.o);
		}
		enrollmentSignature4Wrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public String solrEnrollmentSignature4() {
		return enrollmentSignature4;
	}

	public String strEnrollmentSignature4() {
		return enrollmentSignature4 == null ? "" : enrollmentSignature4;
	}

	public String jsonEnrollmentSignature4() {
		return enrollmentSignature4 == null ? "" : enrollmentSignature4;
	}

	public String nomAffichageEnrollmentSignature4() {
		return null;
	}

	public String htmTooltipEnrollmentSignature4() {
		return null;
	}

	public String htmEnrollmentSignature4() {
		return enrollmentSignature4 == null ? "" : StringEscapeUtils.escapeHtml4(strEnrollmentSignature4());
	}

	public void inputEnrollmentSignature4(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("div").a("class", "signatureDiv1MedicalEnrollment_enrollmentSignature4").a("id", "signatureDiv1MedicalEnrollment", pk, "enrollmentSignature4").f();
				e("div").a("id", "signatureInputMedicalEnrollment", pk, "enrollmentSignature4");
					a("style", "display: ", StringUtils.isBlank(enrollmentSignature4) ? "block" : "none", "; ");
				f().g("div");
				e("img").a("id", "signatureImgMedicalEnrollment", pk, "enrollmentSignature4");
					a("src", StringUtils.isBlank(enrollmentSignature4) ? "data:image/png;base64," : enrollmentSignature4).a("alt", "");
					a("style", "padding: 10px; display: ", StringUtils.isBlank(enrollmentSignature4) ? "none" : "block", "; ");
				fg();
			g("div");
			e("div").a("id", "signatureDiv2MedicalEnrollment", pk, "enrollmentSignature4").f();
				e("button").a("id", "signatureButtonClearMedicalEnrollment", pk, "enrollmentSignature4");
					a("class", "w3-btn w3-round w3-border w3-border-black w3-section w3-ripple w3-padding w3-margin ");
					s(" onclick=", "\"");
						s("$('#signatureInputMedicalEnrollment", pk, "enrollmentSignature4').show(); ");
						s("$('#signatureImgMedicalEnrollment", pk, "enrollmentSignature4').hide(); ");
						s("removeGlow($('#signatureInputMedicalEnrollment", pk, "enrollmentSignature4')); ");
						s("patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setEnrollmentSignature4', null); ");
						s("if($('#signatureInputMedicalEnrollment", pk, "enrollmentSignature4')) { ");
						s("$('#signatureInputMedicalEnrollment", pk, "enrollmentSignature4').jSignature('reset'); ");
						s(" } else { ");
						s("$('#signatureInputMedicalEnrollment", pk, "enrollmentSignature4').jSignature({'height':200}); ");
						s(" } ");
					s("\"");
					f().sx("Clear");
				g("button");
			g("div");
		} else {
			sx(htmEnrollmentSignature4());
		}
	}

	public void htmEnrollmentSignature4(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalEnrollmentEnrollmentSignature4").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputEnrollmentSignature4(classApiMethodMethod);
							} g("div");
							if(
									userKeys.contains(siteRequest_.getUserKey())
									|| Objects.equals(sessionId, siteRequest_.getSessionId())
									|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
							) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_enrollmentSignature4')); $('#", classApiMethodMethod, "_enrollmentSignature4').val(null); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + $('#MedicalEnrollmentForm :input[name=pk]').val() }], 'setEnrollmentSignature4', null, function() { addGlow($('#", classApiMethodMethod, "_enrollmentSignature4')); }, function() { addError($('#", classApiMethodMethod, "_enrollmentSignature4')); }); ")
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
	// enrollmentSignature5 //
	//////////////////////////

	/**	L'entité « enrollmentSignature5 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String enrollmentSignature5;
	@JsonIgnore
	public Wrap<String> enrollmentSignature5Wrap = new Wrap<String>().p(this).c(String.class).var("enrollmentSignature5").o(enrollmentSignature5);

	/**	<br/>L'entité « enrollmentSignature5 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentSignature5">Trouver l'entité enrollmentSignature5 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _enrollmentSignature5(Wrap<String> c);

	public String getEnrollmentSignature5() {
		return enrollmentSignature5;
	}

	public void setEnrollmentSignature5(String enrollmentSignature5) {
		this.enrollmentSignature5 = enrollmentSignature5;
		this.enrollmentSignature5Wrap.alreadyInitialized = true;
	}
	protected MedicalEnrollment enrollmentSignature5Init() {
		if(!enrollmentSignature5Wrap.alreadyInitialized) {
			_enrollmentSignature5(enrollmentSignature5Wrap);
			if(enrollmentSignature5 == null)
				setEnrollmentSignature5(enrollmentSignature5Wrap.o);
		}
		enrollmentSignature5Wrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public String solrEnrollmentSignature5() {
		return enrollmentSignature5;
	}

	public String strEnrollmentSignature5() {
		return enrollmentSignature5 == null ? "" : enrollmentSignature5;
	}

	public String jsonEnrollmentSignature5() {
		return enrollmentSignature5 == null ? "" : enrollmentSignature5;
	}

	public String nomAffichageEnrollmentSignature5() {
		return null;
	}

	public String htmTooltipEnrollmentSignature5() {
		return null;
	}

	public String htmEnrollmentSignature5() {
		return enrollmentSignature5 == null ? "" : StringEscapeUtils.escapeHtml4(strEnrollmentSignature5());
	}

	public void inputEnrollmentSignature5(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("div").a("class", "signatureDiv1MedicalEnrollment_enrollmentSignature5").a("id", "signatureDiv1MedicalEnrollment", pk, "enrollmentSignature5").f();
				e("div").a("id", "signatureInputMedicalEnrollment", pk, "enrollmentSignature5");
					a("style", "display: ", StringUtils.isBlank(enrollmentSignature5) ? "block" : "none", "; ");
				f().g("div");
				e("img").a("id", "signatureImgMedicalEnrollment", pk, "enrollmentSignature5");
					a("src", StringUtils.isBlank(enrollmentSignature5) ? "data:image/png;base64," : enrollmentSignature5).a("alt", "");
					a("style", "padding: 10px; display: ", StringUtils.isBlank(enrollmentSignature5) ? "none" : "block", "; ");
				fg();
			g("div");
			e("div").a("id", "signatureDiv2MedicalEnrollment", pk, "enrollmentSignature5").f();
				e("button").a("id", "signatureButtonClearMedicalEnrollment", pk, "enrollmentSignature5");
					a("class", "w3-btn w3-round w3-border w3-border-black w3-section w3-ripple w3-padding w3-margin ");
					s(" onclick=", "\"");
						s("$('#signatureInputMedicalEnrollment", pk, "enrollmentSignature5').show(); ");
						s("$('#signatureImgMedicalEnrollment", pk, "enrollmentSignature5').hide(); ");
						s("removeGlow($('#signatureInputMedicalEnrollment", pk, "enrollmentSignature5')); ");
						s("patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setEnrollmentSignature5', null); ");
						s("if($('#signatureInputMedicalEnrollment", pk, "enrollmentSignature5')) { ");
						s("$('#signatureInputMedicalEnrollment", pk, "enrollmentSignature5').jSignature('reset'); ");
						s(" } else { ");
						s("$('#signatureInputMedicalEnrollment", pk, "enrollmentSignature5').jSignature({'height':200}); ");
						s(" } ");
					s("\"");
					f().sx("Clear");
				g("button");
			g("div");
		} else {
			sx(htmEnrollmentSignature5());
		}
	}

	public void htmEnrollmentSignature5(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalEnrollmentEnrollmentSignature5").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputEnrollmentSignature5(classApiMethodMethod);
							} g("div");
							if(
									userKeys.contains(siteRequest_.getUserKey())
									|| Objects.equals(sessionId, siteRequest_.getSessionId())
									|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
							) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_enrollmentSignature5')); $('#", classApiMethodMethod, "_enrollmentSignature5').val(null); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + $('#MedicalEnrollmentForm :input[name=pk]').val() }], 'setEnrollmentSignature5', null, function() { addGlow($('#", classApiMethodMethod, "_enrollmentSignature5')); }, function() { addError($('#", classApiMethodMethod, "_enrollmentSignature5')); }); ")
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
	// enrollmentSignature6 //
	//////////////////////////

	/**	L'entité « enrollmentSignature6 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String enrollmentSignature6;
	@JsonIgnore
	public Wrap<String> enrollmentSignature6Wrap = new Wrap<String>().p(this).c(String.class).var("enrollmentSignature6").o(enrollmentSignature6);

	/**	<br/>L'entité « enrollmentSignature6 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentSignature6">Trouver l'entité enrollmentSignature6 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _enrollmentSignature6(Wrap<String> c);

	public String getEnrollmentSignature6() {
		return enrollmentSignature6;
	}

	public void setEnrollmentSignature6(String enrollmentSignature6) {
		this.enrollmentSignature6 = enrollmentSignature6;
		this.enrollmentSignature6Wrap.alreadyInitialized = true;
	}
	protected MedicalEnrollment enrollmentSignature6Init() {
		if(!enrollmentSignature6Wrap.alreadyInitialized) {
			_enrollmentSignature6(enrollmentSignature6Wrap);
			if(enrollmentSignature6 == null)
				setEnrollmentSignature6(enrollmentSignature6Wrap.o);
		}
		enrollmentSignature6Wrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public String solrEnrollmentSignature6() {
		return enrollmentSignature6;
	}

	public String strEnrollmentSignature6() {
		return enrollmentSignature6 == null ? "" : enrollmentSignature6;
	}

	public String jsonEnrollmentSignature6() {
		return enrollmentSignature6 == null ? "" : enrollmentSignature6;
	}

	public String nomAffichageEnrollmentSignature6() {
		return null;
	}

	public String htmTooltipEnrollmentSignature6() {
		return null;
	}

	public String htmEnrollmentSignature6() {
		return enrollmentSignature6 == null ? "" : StringEscapeUtils.escapeHtml4(strEnrollmentSignature6());
	}

	public void inputEnrollmentSignature6(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("div").a("class", "signatureDiv1MedicalEnrollment_enrollmentSignature6").a("id", "signatureDiv1MedicalEnrollment", pk, "enrollmentSignature6").f();
				e("div").a("id", "signatureInputMedicalEnrollment", pk, "enrollmentSignature6");
					a("style", "display: ", StringUtils.isBlank(enrollmentSignature6) ? "block" : "none", "; ");
				f().g("div");
				e("img").a("id", "signatureImgMedicalEnrollment", pk, "enrollmentSignature6");
					a("src", StringUtils.isBlank(enrollmentSignature6) ? "data:image/png;base64," : enrollmentSignature6).a("alt", "");
					a("style", "padding: 10px; display: ", StringUtils.isBlank(enrollmentSignature6) ? "none" : "block", "; ");
				fg();
			g("div");
			e("div").a("id", "signatureDiv2MedicalEnrollment", pk, "enrollmentSignature6").f();
				e("button").a("id", "signatureButtonClearMedicalEnrollment", pk, "enrollmentSignature6");
					a("class", "w3-btn w3-round w3-border w3-border-black w3-section w3-ripple w3-padding w3-margin ");
					s(" onclick=", "\"");
						s("$('#signatureInputMedicalEnrollment", pk, "enrollmentSignature6').show(); ");
						s("$('#signatureImgMedicalEnrollment", pk, "enrollmentSignature6').hide(); ");
						s("removeGlow($('#signatureInputMedicalEnrollment", pk, "enrollmentSignature6')); ");
						s("patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setEnrollmentSignature6', null); ");
						s("if($('#signatureInputMedicalEnrollment", pk, "enrollmentSignature6')) { ");
						s("$('#signatureInputMedicalEnrollment", pk, "enrollmentSignature6').jSignature('reset'); ");
						s(" } else { ");
						s("$('#signatureInputMedicalEnrollment", pk, "enrollmentSignature6').jSignature({'height':200}); ");
						s(" } ");
					s("\"");
					f().sx("Clear");
				g("button");
			g("div");
		} else {
			sx(htmEnrollmentSignature6());
		}
	}

	public void htmEnrollmentSignature6(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalEnrollmentEnrollmentSignature6").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputEnrollmentSignature6(classApiMethodMethod);
							} g("div");
							if(
									userKeys.contains(siteRequest_.getUserKey())
									|| Objects.equals(sessionId, siteRequest_.getSessionId())
									|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
							) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_enrollmentSignature6')); $('#", classApiMethodMethod, "_enrollmentSignature6').val(null); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + $('#MedicalEnrollmentForm :input[name=pk]').val() }], 'setEnrollmentSignature6', null, function() { addGlow($('#", classApiMethodMethod, "_enrollmentSignature6')); }, function() { addError($('#", classApiMethodMethod, "_enrollmentSignature6')); }); ")
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
	// enrollmentSignature7 //
	//////////////////////////

	/**	L'entité « enrollmentSignature7 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String enrollmentSignature7;
	@JsonIgnore
	public Wrap<String> enrollmentSignature7Wrap = new Wrap<String>().p(this).c(String.class).var("enrollmentSignature7").o(enrollmentSignature7);

	/**	<br/>L'entité « enrollmentSignature7 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentSignature7">Trouver l'entité enrollmentSignature7 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _enrollmentSignature7(Wrap<String> c);

	public String getEnrollmentSignature7() {
		return enrollmentSignature7;
	}

	public void setEnrollmentSignature7(String enrollmentSignature7) {
		this.enrollmentSignature7 = enrollmentSignature7;
		this.enrollmentSignature7Wrap.alreadyInitialized = true;
	}
	protected MedicalEnrollment enrollmentSignature7Init() {
		if(!enrollmentSignature7Wrap.alreadyInitialized) {
			_enrollmentSignature7(enrollmentSignature7Wrap);
			if(enrollmentSignature7 == null)
				setEnrollmentSignature7(enrollmentSignature7Wrap.o);
		}
		enrollmentSignature7Wrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public String solrEnrollmentSignature7() {
		return enrollmentSignature7;
	}

	public String strEnrollmentSignature7() {
		return enrollmentSignature7 == null ? "" : enrollmentSignature7;
	}

	public String jsonEnrollmentSignature7() {
		return enrollmentSignature7 == null ? "" : enrollmentSignature7;
	}

	public String nomAffichageEnrollmentSignature7() {
		return null;
	}

	public String htmTooltipEnrollmentSignature7() {
		return null;
	}

	public String htmEnrollmentSignature7() {
		return enrollmentSignature7 == null ? "" : StringEscapeUtils.escapeHtml4(strEnrollmentSignature7());
	}

	public void inputEnrollmentSignature7(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("div").a("class", "signatureDiv1MedicalEnrollment_enrollmentSignature7").a("id", "signatureDiv1MedicalEnrollment", pk, "enrollmentSignature7").f();
				e("div").a("id", "signatureInputMedicalEnrollment", pk, "enrollmentSignature7");
					a("style", "display: ", StringUtils.isBlank(enrollmentSignature7) ? "block" : "none", "; ");
				f().g("div");
				e("img").a("id", "signatureImgMedicalEnrollment", pk, "enrollmentSignature7");
					a("src", StringUtils.isBlank(enrollmentSignature7) ? "data:image/png;base64," : enrollmentSignature7).a("alt", "");
					a("style", "padding: 10px; display: ", StringUtils.isBlank(enrollmentSignature7) ? "none" : "block", "; ");
				fg();
			g("div");
			e("div").a("id", "signatureDiv2MedicalEnrollment", pk, "enrollmentSignature7").f();
				e("button").a("id", "signatureButtonClearMedicalEnrollment", pk, "enrollmentSignature7");
					a("class", "w3-btn w3-round w3-border w3-border-black w3-section w3-ripple w3-padding w3-margin ");
					s(" onclick=", "\"");
						s("$('#signatureInputMedicalEnrollment", pk, "enrollmentSignature7').show(); ");
						s("$('#signatureImgMedicalEnrollment", pk, "enrollmentSignature7').hide(); ");
						s("removeGlow($('#signatureInputMedicalEnrollment", pk, "enrollmentSignature7')); ");
						s("patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setEnrollmentSignature7', null); ");
						s("if($('#signatureInputMedicalEnrollment", pk, "enrollmentSignature7')) { ");
						s("$('#signatureInputMedicalEnrollment", pk, "enrollmentSignature7').jSignature('reset'); ");
						s(" } else { ");
						s("$('#signatureInputMedicalEnrollment", pk, "enrollmentSignature7').jSignature({'height':200}); ");
						s(" } ");
					s("\"");
					f().sx("Clear");
				g("button");
			g("div");
		} else {
			sx(htmEnrollmentSignature7());
		}
	}

	public void htmEnrollmentSignature7(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalEnrollmentEnrollmentSignature7").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputEnrollmentSignature7(classApiMethodMethod);
							} g("div");
							if(
									userKeys.contains(siteRequest_.getUserKey())
									|| Objects.equals(sessionId, siteRequest_.getSessionId())
									|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
							) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_enrollmentSignature7')); $('#", classApiMethodMethod, "_enrollmentSignature7').val(null); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + $('#MedicalEnrollmentForm :input[name=pk]').val() }], 'setEnrollmentSignature7', null, function() { addGlow($('#", classApiMethodMethod, "_enrollmentSignature7')); }, function() { addError($('#", classApiMethodMethod, "_enrollmentSignature7')); }); ")
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
	// enrollmentSignature8 //
	//////////////////////////

	/**	L'entité « enrollmentSignature8 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String enrollmentSignature8;
	@JsonIgnore
	public Wrap<String> enrollmentSignature8Wrap = new Wrap<String>().p(this).c(String.class).var("enrollmentSignature8").o(enrollmentSignature8);

	/**	<br/>L'entité « enrollmentSignature8 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentSignature8">Trouver l'entité enrollmentSignature8 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _enrollmentSignature8(Wrap<String> c);

	public String getEnrollmentSignature8() {
		return enrollmentSignature8;
	}

	public void setEnrollmentSignature8(String enrollmentSignature8) {
		this.enrollmentSignature8 = enrollmentSignature8;
		this.enrollmentSignature8Wrap.alreadyInitialized = true;
	}
	protected MedicalEnrollment enrollmentSignature8Init() {
		if(!enrollmentSignature8Wrap.alreadyInitialized) {
			_enrollmentSignature8(enrollmentSignature8Wrap);
			if(enrollmentSignature8 == null)
				setEnrollmentSignature8(enrollmentSignature8Wrap.o);
		}
		enrollmentSignature8Wrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public String solrEnrollmentSignature8() {
		return enrollmentSignature8;
	}

	public String strEnrollmentSignature8() {
		return enrollmentSignature8 == null ? "" : enrollmentSignature8;
	}

	public String jsonEnrollmentSignature8() {
		return enrollmentSignature8 == null ? "" : enrollmentSignature8;
	}

	public String nomAffichageEnrollmentSignature8() {
		return null;
	}

	public String htmTooltipEnrollmentSignature8() {
		return null;
	}

	public String htmEnrollmentSignature8() {
		return enrollmentSignature8 == null ? "" : StringEscapeUtils.escapeHtml4(strEnrollmentSignature8());
	}

	public void inputEnrollmentSignature8(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("div").a("class", "signatureDiv1MedicalEnrollment_enrollmentSignature8").a("id", "signatureDiv1MedicalEnrollment", pk, "enrollmentSignature8").f();
				e("div").a("id", "signatureInputMedicalEnrollment", pk, "enrollmentSignature8");
					a("style", "display: ", StringUtils.isBlank(enrollmentSignature8) ? "block" : "none", "; ");
				f().g("div");
				e("img").a("id", "signatureImgMedicalEnrollment", pk, "enrollmentSignature8");
					a("src", StringUtils.isBlank(enrollmentSignature8) ? "data:image/png;base64," : enrollmentSignature8).a("alt", "");
					a("style", "padding: 10px; display: ", StringUtils.isBlank(enrollmentSignature8) ? "none" : "block", "; ");
				fg();
			g("div");
			e("div").a("id", "signatureDiv2MedicalEnrollment", pk, "enrollmentSignature8").f();
				e("button").a("id", "signatureButtonClearMedicalEnrollment", pk, "enrollmentSignature8");
					a("class", "w3-btn w3-round w3-border w3-border-black w3-section w3-ripple w3-padding w3-margin ");
					s(" onclick=", "\"");
						s("$('#signatureInputMedicalEnrollment", pk, "enrollmentSignature8').show(); ");
						s("$('#signatureImgMedicalEnrollment", pk, "enrollmentSignature8').hide(); ");
						s("removeGlow($('#signatureInputMedicalEnrollment", pk, "enrollmentSignature8')); ");
						s("patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setEnrollmentSignature8', null); ");
						s("if($('#signatureInputMedicalEnrollment", pk, "enrollmentSignature8')) { ");
						s("$('#signatureInputMedicalEnrollment", pk, "enrollmentSignature8').jSignature('reset'); ");
						s(" } else { ");
						s("$('#signatureInputMedicalEnrollment", pk, "enrollmentSignature8').jSignature({'height':200}); ");
						s(" } ");
					s("\"");
					f().sx("Clear");
				g("button");
			g("div");
		} else {
			sx(htmEnrollmentSignature8());
		}
	}

	public void htmEnrollmentSignature8(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalEnrollmentEnrollmentSignature8").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputEnrollmentSignature8(classApiMethodMethod);
							} g("div");
							if(
									userKeys.contains(siteRequest_.getUserKey())
									|| Objects.equals(sessionId, siteRequest_.getSessionId())
									|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
							) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_enrollmentSignature8')); $('#", classApiMethodMethod, "_enrollmentSignature8').val(null); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + $('#MedicalEnrollmentForm :input[name=pk]').val() }], 'setEnrollmentSignature8', null, function() { addGlow($('#", classApiMethodMethod, "_enrollmentSignature8')); }, function() { addError($('#", classApiMethodMethod, "_enrollmentSignature8')); }); ")
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
	// enrollmentSignature9 //
	//////////////////////////

	/**	L'entité « enrollmentSignature9 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String enrollmentSignature9;
	@JsonIgnore
	public Wrap<String> enrollmentSignature9Wrap = new Wrap<String>().p(this).c(String.class).var("enrollmentSignature9").o(enrollmentSignature9);

	/**	<br/>L'entité « enrollmentSignature9 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentSignature9">Trouver l'entité enrollmentSignature9 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _enrollmentSignature9(Wrap<String> c);

	public String getEnrollmentSignature9() {
		return enrollmentSignature9;
	}

	public void setEnrollmentSignature9(String enrollmentSignature9) {
		this.enrollmentSignature9 = enrollmentSignature9;
		this.enrollmentSignature9Wrap.alreadyInitialized = true;
	}
	protected MedicalEnrollment enrollmentSignature9Init() {
		if(!enrollmentSignature9Wrap.alreadyInitialized) {
			_enrollmentSignature9(enrollmentSignature9Wrap);
			if(enrollmentSignature9 == null)
				setEnrollmentSignature9(enrollmentSignature9Wrap.o);
		}
		enrollmentSignature9Wrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public String solrEnrollmentSignature9() {
		return enrollmentSignature9;
	}

	public String strEnrollmentSignature9() {
		return enrollmentSignature9 == null ? "" : enrollmentSignature9;
	}

	public String jsonEnrollmentSignature9() {
		return enrollmentSignature9 == null ? "" : enrollmentSignature9;
	}

	public String nomAffichageEnrollmentSignature9() {
		return null;
	}

	public String htmTooltipEnrollmentSignature9() {
		return null;
	}

	public String htmEnrollmentSignature9() {
		return enrollmentSignature9 == null ? "" : StringEscapeUtils.escapeHtml4(strEnrollmentSignature9());
	}

	public void inputEnrollmentSignature9(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("div").a("class", "signatureDiv1MedicalEnrollment_enrollmentSignature9").a("id", "signatureDiv1MedicalEnrollment", pk, "enrollmentSignature9").f();
				e("div").a("id", "signatureInputMedicalEnrollment", pk, "enrollmentSignature9");
					a("style", "display: ", StringUtils.isBlank(enrollmentSignature9) ? "block" : "none", "; ");
				f().g("div");
				e("img").a("id", "signatureImgMedicalEnrollment", pk, "enrollmentSignature9");
					a("src", StringUtils.isBlank(enrollmentSignature9) ? "data:image/png;base64," : enrollmentSignature9).a("alt", "");
					a("style", "padding: 10px; display: ", StringUtils.isBlank(enrollmentSignature9) ? "none" : "block", "; ");
				fg();
			g("div");
			e("div").a("id", "signatureDiv2MedicalEnrollment", pk, "enrollmentSignature9").f();
				e("button").a("id", "signatureButtonClearMedicalEnrollment", pk, "enrollmentSignature9");
					a("class", "w3-btn w3-round w3-border w3-border-black w3-section w3-ripple w3-padding w3-margin ");
					s(" onclick=", "\"");
						s("$('#signatureInputMedicalEnrollment", pk, "enrollmentSignature9').show(); ");
						s("$('#signatureImgMedicalEnrollment", pk, "enrollmentSignature9').hide(); ");
						s("removeGlow($('#signatureInputMedicalEnrollment", pk, "enrollmentSignature9')); ");
						s("patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setEnrollmentSignature9', null); ");
						s("if($('#signatureInputMedicalEnrollment", pk, "enrollmentSignature9')) { ");
						s("$('#signatureInputMedicalEnrollment", pk, "enrollmentSignature9').jSignature('reset'); ");
						s(" } else { ");
						s("$('#signatureInputMedicalEnrollment", pk, "enrollmentSignature9').jSignature({'height':200}); ");
						s(" } ");
					s("\"");
					f().sx("Clear");
				g("button");
			g("div");
		} else {
			sx(htmEnrollmentSignature9());
		}
	}

	public void htmEnrollmentSignature9(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalEnrollmentEnrollmentSignature9").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputEnrollmentSignature9(classApiMethodMethod);
							} g("div");
							if(
									userKeys.contains(siteRequest_.getUserKey())
									|| Objects.equals(sessionId, siteRequest_.getSessionId())
									|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
							) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_enrollmentSignature9')); $('#", classApiMethodMethod, "_enrollmentSignature9').val(null); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + $('#MedicalEnrollmentForm :input[name=pk]').val() }], 'setEnrollmentSignature9', null, function() { addGlow($('#", classApiMethodMethod, "_enrollmentSignature9')); }, function() { addError($('#", classApiMethodMethod, "_enrollmentSignature9')); }); ")
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
	// enrollmentSignature10 //
	///////////////////////////

	/**	L'entité « enrollmentSignature10 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String enrollmentSignature10;
	@JsonIgnore
	public Wrap<String> enrollmentSignature10Wrap = new Wrap<String>().p(this).c(String.class).var("enrollmentSignature10").o(enrollmentSignature10);

	/**	<br/>L'entité « enrollmentSignature10 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentSignature10">Trouver l'entité enrollmentSignature10 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _enrollmentSignature10(Wrap<String> c);

	public String getEnrollmentSignature10() {
		return enrollmentSignature10;
	}

	public void setEnrollmentSignature10(String enrollmentSignature10) {
		this.enrollmentSignature10 = enrollmentSignature10;
		this.enrollmentSignature10Wrap.alreadyInitialized = true;
	}
	protected MedicalEnrollment enrollmentSignature10Init() {
		if(!enrollmentSignature10Wrap.alreadyInitialized) {
			_enrollmentSignature10(enrollmentSignature10Wrap);
			if(enrollmentSignature10 == null)
				setEnrollmentSignature10(enrollmentSignature10Wrap.o);
		}
		enrollmentSignature10Wrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public String solrEnrollmentSignature10() {
		return enrollmentSignature10;
	}

	public String strEnrollmentSignature10() {
		return enrollmentSignature10 == null ? "" : enrollmentSignature10;
	}

	public String jsonEnrollmentSignature10() {
		return enrollmentSignature10 == null ? "" : enrollmentSignature10;
	}

	public String nomAffichageEnrollmentSignature10() {
		return null;
	}

	public String htmTooltipEnrollmentSignature10() {
		return null;
	}

	public String htmEnrollmentSignature10() {
		return enrollmentSignature10 == null ? "" : StringEscapeUtils.escapeHtml4(strEnrollmentSignature10());
	}

	public void inputEnrollmentSignature10(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("div").a("class", "signatureDiv1MedicalEnrollment_enrollmentSignature10").a("id", "signatureDiv1MedicalEnrollment", pk, "enrollmentSignature10").f();
				e("div").a("id", "signatureInputMedicalEnrollment", pk, "enrollmentSignature10");
					a("style", "display: ", StringUtils.isBlank(enrollmentSignature10) ? "block" : "none", "; ");
				f().g("div");
				e("img").a("id", "signatureImgMedicalEnrollment", pk, "enrollmentSignature10");
					a("src", StringUtils.isBlank(enrollmentSignature10) ? "data:image/png;base64," : enrollmentSignature10).a("alt", "");
					a("style", "padding: 10px; display: ", StringUtils.isBlank(enrollmentSignature10) ? "none" : "block", "; ");
				fg();
			g("div");
			e("div").a("id", "signatureDiv2MedicalEnrollment", pk, "enrollmentSignature10").f();
				e("button").a("id", "signatureButtonClearMedicalEnrollment", pk, "enrollmentSignature10");
					a("class", "w3-btn w3-round w3-border w3-border-black w3-section w3-ripple w3-padding w3-margin ");
					s(" onclick=", "\"");
						s("$('#signatureInputMedicalEnrollment", pk, "enrollmentSignature10').show(); ");
						s("$('#signatureImgMedicalEnrollment", pk, "enrollmentSignature10').hide(); ");
						s("removeGlow($('#signatureInputMedicalEnrollment", pk, "enrollmentSignature10')); ");
						s("patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setEnrollmentSignature10', null); ");
						s("if($('#signatureInputMedicalEnrollment", pk, "enrollmentSignature10')) { ");
						s("$('#signatureInputMedicalEnrollment", pk, "enrollmentSignature10').jSignature('reset'); ");
						s(" } else { ");
						s("$('#signatureInputMedicalEnrollment", pk, "enrollmentSignature10').jSignature({'height':200}); ");
						s(" } ");
					s("\"");
					f().sx("Clear");
				g("button");
			g("div");
		} else {
			sx(htmEnrollmentSignature10());
		}
	}

	public void htmEnrollmentSignature10(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalEnrollmentEnrollmentSignature10").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputEnrollmentSignature10(classApiMethodMethod);
							} g("div");
							if(
									userKeys.contains(siteRequest_.getUserKey())
									|| Objects.equals(sessionId, siteRequest_.getSessionId())
									|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
							) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_enrollmentSignature10')); $('#", classApiMethodMethod, "_enrollmentSignature10').val(null); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + $('#MedicalEnrollmentForm :input[name=pk]').val() }], 'setEnrollmentSignature10', null, function() { addGlow($('#", classApiMethodMethod, "_enrollmentSignature10')); }, function() { addError($('#", classApiMethodMethod, "_enrollmentSignature10')); }); ")
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
	// enrollmentDate1 //
	/////////////////////

	/**	L'entité « enrollmentDate1 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	protected LocalDate enrollmentDate1;
	@JsonIgnore
	public Wrap<LocalDate> enrollmentDate1Wrap = new Wrap<LocalDate>().p(this).c(LocalDate.class).var("enrollmentDate1").o(enrollmentDate1);

	/**	<br/>L'entité « enrollmentDate1 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentDate1">Trouver l'entité enrollmentDate1 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _enrollmentDate1(Wrap<LocalDate> c);

	public LocalDate getEnrollmentDate1() {
		return enrollmentDate1;
	}

	public void setEnrollmentDate1(LocalDate enrollmentDate1) {
		this.enrollmentDate1 = enrollmentDate1;
		this.enrollmentDate1Wrap.alreadyInitialized = true;
	}
	public MedicalEnrollment setEnrollmentDate1(Instant o) {
		this.enrollmentDate1 = o == null ? null : LocalDate.from(o);
		this.enrollmentDate1Wrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	/** Example: 2011-12-03+01:00 **/
	public MedicalEnrollment setEnrollmentDate1(String o) {
		this.enrollmentDate1 = o == null ? null : LocalDate.parse(o, DateTimeFormatter.ISO_DATE);
		this.enrollmentDate1Wrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	public MedicalEnrollment setEnrollmentDate1(Date o) {
		this.enrollmentDate1 = o == null ? null : o.toInstant().atZone(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toLocalDate();
		this.enrollmentDate1Wrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	protected MedicalEnrollment enrollmentDate1Init() {
		if(!enrollmentDate1Wrap.alreadyInitialized) {
			_enrollmentDate1(enrollmentDate1Wrap);
			if(enrollmentDate1 == null)
				setEnrollmentDate1(enrollmentDate1Wrap.o);
		}
		enrollmentDate1Wrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public Date solrEnrollmentDate1() {
		return enrollmentDate1 == null ? null : Date.from(enrollmentDate1.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z")).toInstant());
	}

	public String strEnrollmentDate1() {
		return enrollmentDate1 == null ? "" : enrollmentDate1.format(DateTimeFormatter.ofPattern("EEE MMM d, yyyy", Locale.forLanguageTag("en-US")));
	}

	public String jsonEnrollmentDate1() {
		return enrollmentDate1 == null ? "" : enrollmentDate1.format(DateTimeFormatter.ISO_DATE);
	}

	public String nomAffichageEnrollmentDate1() {
		return null;
	}

	public String htmTooltipEnrollmentDate1() {
		return null;
	}

	public String htmEnrollmentDate1() {
		return enrollmentDate1 == null ? "" : StringEscapeUtils.escapeHtml4(strEnrollmentDate1());
	}

	public void inputEnrollmentDate1(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("class", "w3-input w3-border datepicker setEnrollmentDate1 classMedicalEnrollment inputMedicalEnrollment", pk, "EnrollmentDate1 w3-input w3-border ")
				.a("placeholder", "MM/DD/YYYY")
				.a("data-timeformat", "MM/dd/yyyy")
				.a("id", classApiMethodMethod, "_enrollmentDate1")
				.a("onclick", "removeGlow($(this)); ")
				.a("value", enrollmentDate1 == null ? "" : DateTimeFormatter.ofPattern("MM/dd/yyyy").format(enrollmentDate1))
				.a("onchange", "var t = moment(this.value, 'MM/DD/YYYY'); if(t) { var s = t.format('YYYY-MM-DD'); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setEnrollmentDate1', s, function() { addGlow($('#", classApiMethodMethod, "_enrollmentDate1')); }, function() { addError($('#", classApiMethodMethod, "_enrollmentDate1')); }); } ")
				.fg();
		} else {
			sx(htmEnrollmentDate1());
		}
	}

	public void htmEnrollmentDate1(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalEnrollmentEnrollmentDate1").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row  ").f();
							{ e("div").a("class", "w3-cell ").f();
								inputEnrollmentDate1(classApiMethodMethod);
							} g("div");
							if(
									userKeys.contains(siteRequest_.getUserKey())
									|| Objects.equals(sessionId, siteRequest_.getSessionId())
									|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
							) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_enrollmentDate1')); $('#", classApiMethodMethod, "_enrollmentDate1').val(null); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + $('#MedicalEnrollmentForm :input[name=pk]').val() }], 'setEnrollmentDate1', null, function() { addGlow($('#", classApiMethodMethod, "_enrollmentDate1')); }, function() { addError($('#", classApiMethodMethod, "_enrollmentDate1')); }); ")
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
	// enrollmentDate2 //
	/////////////////////

	/**	L'entité « enrollmentDate2 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	protected LocalDate enrollmentDate2;
	@JsonIgnore
	public Wrap<LocalDate> enrollmentDate2Wrap = new Wrap<LocalDate>().p(this).c(LocalDate.class).var("enrollmentDate2").o(enrollmentDate2);

	/**	<br/>L'entité « enrollmentDate2 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentDate2">Trouver l'entité enrollmentDate2 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _enrollmentDate2(Wrap<LocalDate> c);

	public LocalDate getEnrollmentDate2() {
		return enrollmentDate2;
	}

	public void setEnrollmentDate2(LocalDate enrollmentDate2) {
		this.enrollmentDate2 = enrollmentDate2;
		this.enrollmentDate2Wrap.alreadyInitialized = true;
	}
	public MedicalEnrollment setEnrollmentDate2(Instant o) {
		this.enrollmentDate2 = o == null ? null : LocalDate.from(o);
		this.enrollmentDate2Wrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	/** Example: 2011-12-03+01:00 **/
	public MedicalEnrollment setEnrollmentDate2(String o) {
		this.enrollmentDate2 = o == null ? null : LocalDate.parse(o, DateTimeFormatter.ISO_DATE);
		this.enrollmentDate2Wrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	public MedicalEnrollment setEnrollmentDate2(Date o) {
		this.enrollmentDate2 = o == null ? null : o.toInstant().atZone(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toLocalDate();
		this.enrollmentDate2Wrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	protected MedicalEnrollment enrollmentDate2Init() {
		if(!enrollmentDate2Wrap.alreadyInitialized) {
			_enrollmentDate2(enrollmentDate2Wrap);
			if(enrollmentDate2 == null)
				setEnrollmentDate2(enrollmentDate2Wrap.o);
		}
		enrollmentDate2Wrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public Date solrEnrollmentDate2() {
		return enrollmentDate2 == null ? null : Date.from(enrollmentDate2.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z")).toInstant());
	}

	public String strEnrollmentDate2() {
		return enrollmentDate2 == null ? "" : enrollmentDate2.format(DateTimeFormatter.ofPattern("EEE MMM d, yyyy", Locale.forLanguageTag("en-US")));
	}

	public String jsonEnrollmentDate2() {
		return enrollmentDate2 == null ? "" : enrollmentDate2.format(DateTimeFormatter.ISO_DATE);
	}

	public String nomAffichageEnrollmentDate2() {
		return null;
	}

	public String htmTooltipEnrollmentDate2() {
		return null;
	}

	public String htmEnrollmentDate2() {
		return enrollmentDate2 == null ? "" : StringEscapeUtils.escapeHtml4(strEnrollmentDate2());
	}

	public void inputEnrollmentDate2(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("class", "w3-input w3-border datepicker setEnrollmentDate2 classMedicalEnrollment inputMedicalEnrollment", pk, "EnrollmentDate2 w3-input w3-border ")
				.a("placeholder", "MM/DD/YYYY")
				.a("data-timeformat", "MM/dd/yyyy")
				.a("id", classApiMethodMethod, "_enrollmentDate2")
				.a("onclick", "removeGlow($(this)); ")
				.a("value", enrollmentDate2 == null ? "" : DateTimeFormatter.ofPattern("MM/dd/yyyy").format(enrollmentDate2))
				.a("onchange", "var t = moment(this.value, 'MM/DD/YYYY'); if(t) { var s = t.format('YYYY-MM-DD'); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setEnrollmentDate2', s, function() { addGlow($('#", classApiMethodMethod, "_enrollmentDate2')); }, function() { addError($('#", classApiMethodMethod, "_enrollmentDate2')); }); } ")
				.fg();
		} else {
			sx(htmEnrollmentDate2());
		}
	}

	public void htmEnrollmentDate2(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalEnrollmentEnrollmentDate2").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row  ").f();
							{ e("div").a("class", "w3-cell ").f();
								inputEnrollmentDate2(classApiMethodMethod);
							} g("div");
							if(
									userKeys.contains(siteRequest_.getUserKey())
									|| Objects.equals(sessionId, siteRequest_.getSessionId())
									|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
							) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_enrollmentDate2')); $('#", classApiMethodMethod, "_enrollmentDate2').val(null); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + $('#MedicalEnrollmentForm :input[name=pk]').val() }], 'setEnrollmentDate2', null, function() { addGlow($('#", classApiMethodMethod, "_enrollmentDate2')); }, function() { addError($('#", classApiMethodMethod, "_enrollmentDate2')); }); ")
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
	// enrollmentDate3 //
	/////////////////////

	/**	L'entité « enrollmentDate3 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	protected LocalDate enrollmentDate3;
	@JsonIgnore
	public Wrap<LocalDate> enrollmentDate3Wrap = new Wrap<LocalDate>().p(this).c(LocalDate.class).var("enrollmentDate3").o(enrollmentDate3);

	/**	<br/>L'entité « enrollmentDate3 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentDate3">Trouver l'entité enrollmentDate3 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _enrollmentDate3(Wrap<LocalDate> c);

	public LocalDate getEnrollmentDate3() {
		return enrollmentDate3;
	}

	public void setEnrollmentDate3(LocalDate enrollmentDate3) {
		this.enrollmentDate3 = enrollmentDate3;
		this.enrollmentDate3Wrap.alreadyInitialized = true;
	}
	public MedicalEnrollment setEnrollmentDate3(Instant o) {
		this.enrollmentDate3 = o == null ? null : LocalDate.from(o);
		this.enrollmentDate3Wrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	/** Example: 2011-12-03+01:00 **/
	public MedicalEnrollment setEnrollmentDate3(String o) {
		this.enrollmentDate3 = o == null ? null : LocalDate.parse(o, DateTimeFormatter.ISO_DATE);
		this.enrollmentDate3Wrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	public MedicalEnrollment setEnrollmentDate3(Date o) {
		this.enrollmentDate3 = o == null ? null : o.toInstant().atZone(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toLocalDate();
		this.enrollmentDate3Wrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	protected MedicalEnrollment enrollmentDate3Init() {
		if(!enrollmentDate3Wrap.alreadyInitialized) {
			_enrollmentDate3(enrollmentDate3Wrap);
			if(enrollmentDate3 == null)
				setEnrollmentDate3(enrollmentDate3Wrap.o);
		}
		enrollmentDate3Wrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public Date solrEnrollmentDate3() {
		return enrollmentDate3 == null ? null : Date.from(enrollmentDate3.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z")).toInstant());
	}

	public String strEnrollmentDate3() {
		return enrollmentDate3 == null ? "" : enrollmentDate3.format(DateTimeFormatter.ofPattern("EEE MMM d, yyyy", Locale.forLanguageTag("en-US")));
	}

	public String jsonEnrollmentDate3() {
		return enrollmentDate3 == null ? "" : enrollmentDate3.format(DateTimeFormatter.ISO_DATE);
	}

	public String nomAffichageEnrollmentDate3() {
		return null;
	}

	public String htmTooltipEnrollmentDate3() {
		return null;
	}

	public String htmEnrollmentDate3() {
		return enrollmentDate3 == null ? "" : StringEscapeUtils.escapeHtml4(strEnrollmentDate3());
	}

	public void inputEnrollmentDate3(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("class", "w3-input w3-border datepicker setEnrollmentDate3 classMedicalEnrollment inputMedicalEnrollment", pk, "EnrollmentDate3 w3-input w3-border ")
				.a("placeholder", "MM/DD/YYYY")
				.a("data-timeformat", "MM/dd/yyyy")
				.a("id", classApiMethodMethod, "_enrollmentDate3")
				.a("onclick", "removeGlow($(this)); ")
				.a("value", enrollmentDate3 == null ? "" : DateTimeFormatter.ofPattern("MM/dd/yyyy").format(enrollmentDate3))
				.a("onchange", "var t = moment(this.value, 'MM/DD/YYYY'); if(t) { var s = t.format('YYYY-MM-DD'); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setEnrollmentDate3', s, function() { addGlow($('#", classApiMethodMethod, "_enrollmentDate3')); }, function() { addError($('#", classApiMethodMethod, "_enrollmentDate3')); }); } ")
				.fg();
		} else {
			sx(htmEnrollmentDate3());
		}
	}

	public void htmEnrollmentDate3(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalEnrollmentEnrollmentDate3").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row  ").f();
							{ e("div").a("class", "w3-cell ").f();
								inputEnrollmentDate3(classApiMethodMethod);
							} g("div");
							if(
									userKeys.contains(siteRequest_.getUserKey())
									|| Objects.equals(sessionId, siteRequest_.getSessionId())
									|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
							) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_enrollmentDate3')); $('#", classApiMethodMethod, "_enrollmentDate3').val(null); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + $('#MedicalEnrollmentForm :input[name=pk]').val() }], 'setEnrollmentDate3', null, function() { addGlow($('#", classApiMethodMethod, "_enrollmentDate3')); }, function() { addError($('#", classApiMethodMethod, "_enrollmentDate3')); }); ")
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
	// enrollmentDate4 //
	/////////////////////

	/**	L'entité « enrollmentDate4 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	protected LocalDate enrollmentDate4;
	@JsonIgnore
	public Wrap<LocalDate> enrollmentDate4Wrap = new Wrap<LocalDate>().p(this).c(LocalDate.class).var("enrollmentDate4").o(enrollmentDate4);

	/**	<br/>L'entité « enrollmentDate4 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentDate4">Trouver l'entité enrollmentDate4 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _enrollmentDate4(Wrap<LocalDate> c);

	public LocalDate getEnrollmentDate4() {
		return enrollmentDate4;
	}

	public void setEnrollmentDate4(LocalDate enrollmentDate4) {
		this.enrollmentDate4 = enrollmentDate4;
		this.enrollmentDate4Wrap.alreadyInitialized = true;
	}
	public MedicalEnrollment setEnrollmentDate4(Instant o) {
		this.enrollmentDate4 = o == null ? null : LocalDate.from(o);
		this.enrollmentDate4Wrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	/** Example: 2011-12-03+01:00 **/
	public MedicalEnrollment setEnrollmentDate4(String o) {
		this.enrollmentDate4 = o == null ? null : LocalDate.parse(o, DateTimeFormatter.ISO_DATE);
		this.enrollmentDate4Wrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	public MedicalEnrollment setEnrollmentDate4(Date o) {
		this.enrollmentDate4 = o == null ? null : o.toInstant().atZone(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toLocalDate();
		this.enrollmentDate4Wrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	protected MedicalEnrollment enrollmentDate4Init() {
		if(!enrollmentDate4Wrap.alreadyInitialized) {
			_enrollmentDate4(enrollmentDate4Wrap);
			if(enrollmentDate4 == null)
				setEnrollmentDate4(enrollmentDate4Wrap.o);
		}
		enrollmentDate4Wrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public Date solrEnrollmentDate4() {
		return enrollmentDate4 == null ? null : Date.from(enrollmentDate4.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z")).toInstant());
	}

	public String strEnrollmentDate4() {
		return enrollmentDate4 == null ? "" : enrollmentDate4.format(DateTimeFormatter.ofPattern("EEE MMM d, yyyy", Locale.forLanguageTag("en-US")));
	}

	public String jsonEnrollmentDate4() {
		return enrollmentDate4 == null ? "" : enrollmentDate4.format(DateTimeFormatter.ISO_DATE);
	}

	public String nomAffichageEnrollmentDate4() {
		return null;
	}

	public String htmTooltipEnrollmentDate4() {
		return null;
	}

	public String htmEnrollmentDate4() {
		return enrollmentDate4 == null ? "" : StringEscapeUtils.escapeHtml4(strEnrollmentDate4());
	}

	public void inputEnrollmentDate4(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("class", "w3-input w3-border datepicker setEnrollmentDate4 classMedicalEnrollment inputMedicalEnrollment", pk, "EnrollmentDate4 w3-input w3-border ")
				.a("placeholder", "MM/DD/YYYY")
				.a("data-timeformat", "MM/dd/yyyy")
				.a("id", classApiMethodMethod, "_enrollmentDate4")
				.a("onclick", "removeGlow($(this)); ")
				.a("value", enrollmentDate4 == null ? "" : DateTimeFormatter.ofPattern("MM/dd/yyyy").format(enrollmentDate4))
				.a("onchange", "var t = moment(this.value, 'MM/DD/YYYY'); if(t) { var s = t.format('YYYY-MM-DD'); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setEnrollmentDate4', s, function() { addGlow($('#", classApiMethodMethod, "_enrollmentDate4')); }, function() { addError($('#", classApiMethodMethod, "_enrollmentDate4')); }); } ")
				.fg();
		} else {
			sx(htmEnrollmentDate4());
		}
	}

	public void htmEnrollmentDate4(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalEnrollmentEnrollmentDate4").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row  ").f();
							{ e("div").a("class", "w3-cell ").f();
								inputEnrollmentDate4(classApiMethodMethod);
							} g("div");
							if(
									userKeys.contains(siteRequest_.getUserKey())
									|| Objects.equals(sessionId, siteRequest_.getSessionId())
									|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
							) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_enrollmentDate4')); $('#", classApiMethodMethod, "_enrollmentDate4').val(null); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + $('#MedicalEnrollmentForm :input[name=pk]').val() }], 'setEnrollmentDate4', null, function() { addGlow($('#", classApiMethodMethod, "_enrollmentDate4')); }, function() { addError($('#", classApiMethodMethod, "_enrollmentDate4')); }); ")
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
	// enrollmentDate5 //
	/////////////////////

	/**	L'entité « enrollmentDate5 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	protected LocalDate enrollmentDate5;
	@JsonIgnore
	public Wrap<LocalDate> enrollmentDate5Wrap = new Wrap<LocalDate>().p(this).c(LocalDate.class).var("enrollmentDate5").o(enrollmentDate5);

	/**	<br/>L'entité « enrollmentDate5 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentDate5">Trouver l'entité enrollmentDate5 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _enrollmentDate5(Wrap<LocalDate> c);

	public LocalDate getEnrollmentDate5() {
		return enrollmentDate5;
	}

	public void setEnrollmentDate5(LocalDate enrollmentDate5) {
		this.enrollmentDate5 = enrollmentDate5;
		this.enrollmentDate5Wrap.alreadyInitialized = true;
	}
	public MedicalEnrollment setEnrollmentDate5(Instant o) {
		this.enrollmentDate5 = o == null ? null : LocalDate.from(o);
		this.enrollmentDate5Wrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	/** Example: 2011-12-03+01:00 **/
	public MedicalEnrollment setEnrollmentDate5(String o) {
		this.enrollmentDate5 = o == null ? null : LocalDate.parse(o, DateTimeFormatter.ISO_DATE);
		this.enrollmentDate5Wrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	public MedicalEnrollment setEnrollmentDate5(Date o) {
		this.enrollmentDate5 = o == null ? null : o.toInstant().atZone(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toLocalDate();
		this.enrollmentDate5Wrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	protected MedicalEnrollment enrollmentDate5Init() {
		if(!enrollmentDate5Wrap.alreadyInitialized) {
			_enrollmentDate5(enrollmentDate5Wrap);
			if(enrollmentDate5 == null)
				setEnrollmentDate5(enrollmentDate5Wrap.o);
		}
		enrollmentDate5Wrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public Date solrEnrollmentDate5() {
		return enrollmentDate5 == null ? null : Date.from(enrollmentDate5.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z")).toInstant());
	}

	public String strEnrollmentDate5() {
		return enrollmentDate5 == null ? "" : enrollmentDate5.format(DateTimeFormatter.ofPattern("EEE MMM d, yyyy", Locale.forLanguageTag("en-US")));
	}

	public String jsonEnrollmentDate5() {
		return enrollmentDate5 == null ? "" : enrollmentDate5.format(DateTimeFormatter.ISO_DATE);
	}

	public String nomAffichageEnrollmentDate5() {
		return null;
	}

	public String htmTooltipEnrollmentDate5() {
		return null;
	}

	public String htmEnrollmentDate5() {
		return enrollmentDate5 == null ? "" : StringEscapeUtils.escapeHtml4(strEnrollmentDate5());
	}

	public void inputEnrollmentDate5(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("class", "w3-input w3-border datepicker setEnrollmentDate5 classMedicalEnrollment inputMedicalEnrollment", pk, "EnrollmentDate5 w3-input w3-border ")
				.a("placeholder", "MM/DD/YYYY")
				.a("data-timeformat", "MM/dd/yyyy")
				.a("id", classApiMethodMethod, "_enrollmentDate5")
				.a("onclick", "removeGlow($(this)); ")
				.a("value", enrollmentDate5 == null ? "" : DateTimeFormatter.ofPattern("MM/dd/yyyy").format(enrollmentDate5))
				.a("onchange", "var t = moment(this.value, 'MM/DD/YYYY'); if(t) { var s = t.format('YYYY-MM-DD'); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setEnrollmentDate5', s, function() { addGlow($('#", classApiMethodMethod, "_enrollmentDate5')); }, function() { addError($('#", classApiMethodMethod, "_enrollmentDate5')); }); } ")
				.fg();
		} else {
			sx(htmEnrollmentDate5());
		}
	}

	public void htmEnrollmentDate5(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalEnrollmentEnrollmentDate5").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row  ").f();
							{ e("div").a("class", "w3-cell ").f();
								inputEnrollmentDate5(classApiMethodMethod);
							} g("div");
							if(
									userKeys.contains(siteRequest_.getUserKey())
									|| Objects.equals(sessionId, siteRequest_.getSessionId())
									|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
							) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_enrollmentDate5')); $('#", classApiMethodMethod, "_enrollmentDate5').val(null); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + $('#MedicalEnrollmentForm :input[name=pk]').val() }], 'setEnrollmentDate5', null, function() { addGlow($('#", classApiMethodMethod, "_enrollmentDate5')); }, function() { addError($('#", classApiMethodMethod, "_enrollmentDate5')); }); ")
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
	// enrollmentDate6 //
	/////////////////////

	/**	L'entité « enrollmentDate6 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	protected LocalDate enrollmentDate6;
	@JsonIgnore
	public Wrap<LocalDate> enrollmentDate6Wrap = new Wrap<LocalDate>().p(this).c(LocalDate.class).var("enrollmentDate6").o(enrollmentDate6);

	/**	<br/>L'entité « enrollmentDate6 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentDate6">Trouver l'entité enrollmentDate6 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _enrollmentDate6(Wrap<LocalDate> c);

	public LocalDate getEnrollmentDate6() {
		return enrollmentDate6;
	}

	public void setEnrollmentDate6(LocalDate enrollmentDate6) {
		this.enrollmentDate6 = enrollmentDate6;
		this.enrollmentDate6Wrap.alreadyInitialized = true;
	}
	public MedicalEnrollment setEnrollmentDate6(Instant o) {
		this.enrollmentDate6 = o == null ? null : LocalDate.from(o);
		this.enrollmentDate6Wrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	/** Example: 2011-12-03+01:00 **/
	public MedicalEnrollment setEnrollmentDate6(String o) {
		this.enrollmentDate6 = o == null ? null : LocalDate.parse(o, DateTimeFormatter.ISO_DATE);
		this.enrollmentDate6Wrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	public MedicalEnrollment setEnrollmentDate6(Date o) {
		this.enrollmentDate6 = o == null ? null : o.toInstant().atZone(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toLocalDate();
		this.enrollmentDate6Wrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	protected MedicalEnrollment enrollmentDate6Init() {
		if(!enrollmentDate6Wrap.alreadyInitialized) {
			_enrollmentDate6(enrollmentDate6Wrap);
			if(enrollmentDate6 == null)
				setEnrollmentDate6(enrollmentDate6Wrap.o);
		}
		enrollmentDate6Wrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public Date solrEnrollmentDate6() {
		return enrollmentDate6 == null ? null : Date.from(enrollmentDate6.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z")).toInstant());
	}

	public String strEnrollmentDate6() {
		return enrollmentDate6 == null ? "" : enrollmentDate6.format(DateTimeFormatter.ofPattern("EEE MMM d, yyyy", Locale.forLanguageTag("en-US")));
	}

	public String jsonEnrollmentDate6() {
		return enrollmentDate6 == null ? "" : enrollmentDate6.format(DateTimeFormatter.ISO_DATE);
	}

	public String nomAffichageEnrollmentDate6() {
		return null;
	}

	public String htmTooltipEnrollmentDate6() {
		return null;
	}

	public String htmEnrollmentDate6() {
		return enrollmentDate6 == null ? "" : StringEscapeUtils.escapeHtml4(strEnrollmentDate6());
	}

	public void inputEnrollmentDate6(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("class", "w3-input w3-border datepicker setEnrollmentDate6 classMedicalEnrollment inputMedicalEnrollment", pk, "EnrollmentDate6 w3-input w3-border ")
				.a("placeholder", "MM/DD/YYYY")
				.a("data-timeformat", "MM/dd/yyyy")
				.a("id", classApiMethodMethod, "_enrollmentDate6")
				.a("onclick", "removeGlow($(this)); ")
				.a("value", enrollmentDate6 == null ? "" : DateTimeFormatter.ofPattern("MM/dd/yyyy").format(enrollmentDate6))
				.a("onchange", "var t = moment(this.value, 'MM/DD/YYYY'); if(t) { var s = t.format('YYYY-MM-DD'); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setEnrollmentDate6', s, function() { addGlow($('#", classApiMethodMethod, "_enrollmentDate6')); }, function() { addError($('#", classApiMethodMethod, "_enrollmentDate6')); }); } ")
				.fg();
		} else {
			sx(htmEnrollmentDate6());
		}
	}

	public void htmEnrollmentDate6(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalEnrollmentEnrollmentDate6").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row  ").f();
							{ e("div").a("class", "w3-cell ").f();
								inputEnrollmentDate6(classApiMethodMethod);
							} g("div");
							if(
									userKeys.contains(siteRequest_.getUserKey())
									|| Objects.equals(sessionId, siteRequest_.getSessionId())
									|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
							) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_enrollmentDate6')); $('#", classApiMethodMethod, "_enrollmentDate6').val(null); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + $('#MedicalEnrollmentForm :input[name=pk]').val() }], 'setEnrollmentDate6', null, function() { addGlow($('#", classApiMethodMethod, "_enrollmentDate6')); }, function() { addError($('#", classApiMethodMethod, "_enrollmentDate6')); }); ")
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
	// enrollmentDate7 //
	/////////////////////

	/**	L'entité « enrollmentDate7 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	protected LocalDate enrollmentDate7;
	@JsonIgnore
	public Wrap<LocalDate> enrollmentDate7Wrap = new Wrap<LocalDate>().p(this).c(LocalDate.class).var("enrollmentDate7").o(enrollmentDate7);

	/**	<br/>L'entité « enrollmentDate7 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentDate7">Trouver l'entité enrollmentDate7 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _enrollmentDate7(Wrap<LocalDate> c);

	public LocalDate getEnrollmentDate7() {
		return enrollmentDate7;
	}

	public void setEnrollmentDate7(LocalDate enrollmentDate7) {
		this.enrollmentDate7 = enrollmentDate7;
		this.enrollmentDate7Wrap.alreadyInitialized = true;
	}
	public MedicalEnrollment setEnrollmentDate7(Instant o) {
		this.enrollmentDate7 = o == null ? null : LocalDate.from(o);
		this.enrollmentDate7Wrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	/** Example: 2011-12-03+01:00 **/
	public MedicalEnrollment setEnrollmentDate7(String o) {
		this.enrollmentDate7 = o == null ? null : LocalDate.parse(o, DateTimeFormatter.ISO_DATE);
		this.enrollmentDate7Wrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	public MedicalEnrollment setEnrollmentDate7(Date o) {
		this.enrollmentDate7 = o == null ? null : o.toInstant().atZone(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toLocalDate();
		this.enrollmentDate7Wrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	protected MedicalEnrollment enrollmentDate7Init() {
		if(!enrollmentDate7Wrap.alreadyInitialized) {
			_enrollmentDate7(enrollmentDate7Wrap);
			if(enrollmentDate7 == null)
				setEnrollmentDate7(enrollmentDate7Wrap.o);
		}
		enrollmentDate7Wrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public Date solrEnrollmentDate7() {
		return enrollmentDate7 == null ? null : Date.from(enrollmentDate7.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z")).toInstant());
	}

	public String strEnrollmentDate7() {
		return enrollmentDate7 == null ? "" : enrollmentDate7.format(DateTimeFormatter.ofPattern("EEE MMM d, yyyy", Locale.forLanguageTag("en-US")));
	}

	public String jsonEnrollmentDate7() {
		return enrollmentDate7 == null ? "" : enrollmentDate7.format(DateTimeFormatter.ISO_DATE);
	}

	public String nomAffichageEnrollmentDate7() {
		return null;
	}

	public String htmTooltipEnrollmentDate7() {
		return null;
	}

	public String htmEnrollmentDate7() {
		return enrollmentDate7 == null ? "" : StringEscapeUtils.escapeHtml4(strEnrollmentDate7());
	}

	public void inputEnrollmentDate7(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("class", "w3-input w3-border datepicker setEnrollmentDate7 classMedicalEnrollment inputMedicalEnrollment", pk, "EnrollmentDate7 w3-input w3-border ")
				.a("placeholder", "MM/DD/YYYY")
				.a("data-timeformat", "MM/dd/yyyy")
				.a("id", classApiMethodMethod, "_enrollmentDate7")
				.a("onclick", "removeGlow($(this)); ")
				.a("value", enrollmentDate7 == null ? "" : DateTimeFormatter.ofPattern("MM/dd/yyyy").format(enrollmentDate7))
				.a("onchange", "var t = moment(this.value, 'MM/DD/YYYY'); if(t) { var s = t.format('YYYY-MM-DD'); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setEnrollmentDate7', s, function() { addGlow($('#", classApiMethodMethod, "_enrollmentDate7')); }, function() { addError($('#", classApiMethodMethod, "_enrollmentDate7')); }); } ")
				.fg();
		} else {
			sx(htmEnrollmentDate7());
		}
	}

	public void htmEnrollmentDate7(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalEnrollmentEnrollmentDate7").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row  ").f();
							{ e("div").a("class", "w3-cell ").f();
								inputEnrollmentDate7(classApiMethodMethod);
							} g("div");
							if(
									userKeys.contains(siteRequest_.getUserKey())
									|| Objects.equals(sessionId, siteRequest_.getSessionId())
									|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
							) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_enrollmentDate7')); $('#", classApiMethodMethod, "_enrollmentDate7').val(null); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + $('#MedicalEnrollmentForm :input[name=pk]').val() }], 'setEnrollmentDate7', null, function() { addGlow($('#", classApiMethodMethod, "_enrollmentDate7')); }, function() { addError($('#", classApiMethodMethod, "_enrollmentDate7')); }); ")
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
	// enrollmentDate8 //
	/////////////////////

	/**	L'entité « enrollmentDate8 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	protected LocalDate enrollmentDate8;
	@JsonIgnore
	public Wrap<LocalDate> enrollmentDate8Wrap = new Wrap<LocalDate>().p(this).c(LocalDate.class).var("enrollmentDate8").o(enrollmentDate8);

	/**	<br/>L'entité « enrollmentDate8 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentDate8">Trouver l'entité enrollmentDate8 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _enrollmentDate8(Wrap<LocalDate> c);

	public LocalDate getEnrollmentDate8() {
		return enrollmentDate8;
	}

	public void setEnrollmentDate8(LocalDate enrollmentDate8) {
		this.enrollmentDate8 = enrollmentDate8;
		this.enrollmentDate8Wrap.alreadyInitialized = true;
	}
	public MedicalEnrollment setEnrollmentDate8(Instant o) {
		this.enrollmentDate8 = o == null ? null : LocalDate.from(o);
		this.enrollmentDate8Wrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	/** Example: 2011-12-03+01:00 **/
	public MedicalEnrollment setEnrollmentDate8(String o) {
		this.enrollmentDate8 = o == null ? null : LocalDate.parse(o, DateTimeFormatter.ISO_DATE);
		this.enrollmentDate8Wrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	public MedicalEnrollment setEnrollmentDate8(Date o) {
		this.enrollmentDate8 = o == null ? null : o.toInstant().atZone(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toLocalDate();
		this.enrollmentDate8Wrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	protected MedicalEnrollment enrollmentDate8Init() {
		if(!enrollmentDate8Wrap.alreadyInitialized) {
			_enrollmentDate8(enrollmentDate8Wrap);
			if(enrollmentDate8 == null)
				setEnrollmentDate8(enrollmentDate8Wrap.o);
		}
		enrollmentDate8Wrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public Date solrEnrollmentDate8() {
		return enrollmentDate8 == null ? null : Date.from(enrollmentDate8.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z")).toInstant());
	}

	public String strEnrollmentDate8() {
		return enrollmentDate8 == null ? "" : enrollmentDate8.format(DateTimeFormatter.ofPattern("EEE MMM d, yyyy", Locale.forLanguageTag("en-US")));
	}

	public String jsonEnrollmentDate8() {
		return enrollmentDate8 == null ? "" : enrollmentDate8.format(DateTimeFormatter.ISO_DATE);
	}

	public String nomAffichageEnrollmentDate8() {
		return null;
	}

	public String htmTooltipEnrollmentDate8() {
		return null;
	}

	public String htmEnrollmentDate8() {
		return enrollmentDate8 == null ? "" : StringEscapeUtils.escapeHtml4(strEnrollmentDate8());
	}

	public void inputEnrollmentDate8(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("class", "w3-input w3-border datepicker setEnrollmentDate8 classMedicalEnrollment inputMedicalEnrollment", pk, "EnrollmentDate8 w3-input w3-border ")
				.a("placeholder", "MM/DD/YYYY")
				.a("data-timeformat", "MM/dd/yyyy")
				.a("id", classApiMethodMethod, "_enrollmentDate8")
				.a("onclick", "removeGlow($(this)); ")
				.a("value", enrollmentDate8 == null ? "" : DateTimeFormatter.ofPattern("MM/dd/yyyy").format(enrollmentDate8))
				.a("onchange", "var t = moment(this.value, 'MM/DD/YYYY'); if(t) { var s = t.format('YYYY-MM-DD'); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setEnrollmentDate8', s, function() { addGlow($('#", classApiMethodMethod, "_enrollmentDate8')); }, function() { addError($('#", classApiMethodMethod, "_enrollmentDate8')); }); } ")
				.fg();
		} else {
			sx(htmEnrollmentDate8());
		}
	}

	public void htmEnrollmentDate8(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalEnrollmentEnrollmentDate8").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row  ").f();
							{ e("div").a("class", "w3-cell ").f();
								inputEnrollmentDate8(classApiMethodMethod);
							} g("div");
							if(
									userKeys.contains(siteRequest_.getUserKey())
									|| Objects.equals(sessionId, siteRequest_.getSessionId())
									|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
							) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_enrollmentDate8')); $('#", classApiMethodMethod, "_enrollmentDate8').val(null); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + $('#MedicalEnrollmentForm :input[name=pk]').val() }], 'setEnrollmentDate8', null, function() { addGlow($('#", classApiMethodMethod, "_enrollmentDate8')); }, function() { addError($('#", classApiMethodMethod, "_enrollmentDate8')); }); ")
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
	// enrollmentDate9 //
	/////////////////////

	/**	L'entité « enrollmentDate9 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	protected LocalDate enrollmentDate9;
	@JsonIgnore
	public Wrap<LocalDate> enrollmentDate9Wrap = new Wrap<LocalDate>().p(this).c(LocalDate.class).var("enrollmentDate9").o(enrollmentDate9);

	/**	<br/>L'entité « enrollmentDate9 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentDate9">Trouver l'entité enrollmentDate9 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _enrollmentDate9(Wrap<LocalDate> c);

	public LocalDate getEnrollmentDate9() {
		return enrollmentDate9;
	}

	public void setEnrollmentDate9(LocalDate enrollmentDate9) {
		this.enrollmentDate9 = enrollmentDate9;
		this.enrollmentDate9Wrap.alreadyInitialized = true;
	}
	public MedicalEnrollment setEnrollmentDate9(Instant o) {
		this.enrollmentDate9 = o == null ? null : LocalDate.from(o);
		this.enrollmentDate9Wrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	/** Example: 2011-12-03+01:00 **/
	public MedicalEnrollment setEnrollmentDate9(String o) {
		this.enrollmentDate9 = o == null ? null : LocalDate.parse(o, DateTimeFormatter.ISO_DATE);
		this.enrollmentDate9Wrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	public MedicalEnrollment setEnrollmentDate9(Date o) {
		this.enrollmentDate9 = o == null ? null : o.toInstant().atZone(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toLocalDate();
		this.enrollmentDate9Wrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	protected MedicalEnrollment enrollmentDate9Init() {
		if(!enrollmentDate9Wrap.alreadyInitialized) {
			_enrollmentDate9(enrollmentDate9Wrap);
			if(enrollmentDate9 == null)
				setEnrollmentDate9(enrollmentDate9Wrap.o);
		}
		enrollmentDate9Wrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public Date solrEnrollmentDate9() {
		return enrollmentDate9 == null ? null : Date.from(enrollmentDate9.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z")).toInstant());
	}

	public String strEnrollmentDate9() {
		return enrollmentDate9 == null ? "" : enrollmentDate9.format(DateTimeFormatter.ofPattern("EEE MMM d, yyyy", Locale.forLanguageTag("en-US")));
	}

	public String jsonEnrollmentDate9() {
		return enrollmentDate9 == null ? "" : enrollmentDate9.format(DateTimeFormatter.ISO_DATE);
	}

	public String nomAffichageEnrollmentDate9() {
		return null;
	}

	public String htmTooltipEnrollmentDate9() {
		return null;
	}

	public String htmEnrollmentDate9() {
		return enrollmentDate9 == null ? "" : StringEscapeUtils.escapeHtml4(strEnrollmentDate9());
	}

	public void inputEnrollmentDate9(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("class", "w3-input w3-border datepicker setEnrollmentDate9 classMedicalEnrollment inputMedicalEnrollment", pk, "EnrollmentDate9 w3-input w3-border ")
				.a("placeholder", "MM/DD/YYYY")
				.a("data-timeformat", "MM/dd/yyyy")
				.a("id", classApiMethodMethod, "_enrollmentDate9")
				.a("onclick", "removeGlow($(this)); ")
				.a("value", enrollmentDate9 == null ? "" : DateTimeFormatter.ofPattern("MM/dd/yyyy").format(enrollmentDate9))
				.a("onchange", "var t = moment(this.value, 'MM/DD/YYYY'); if(t) { var s = t.format('YYYY-MM-DD'); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setEnrollmentDate9', s, function() { addGlow($('#", classApiMethodMethod, "_enrollmentDate9')); }, function() { addError($('#", classApiMethodMethod, "_enrollmentDate9')); }); } ")
				.fg();
		} else {
			sx(htmEnrollmentDate9());
		}
	}

	public void htmEnrollmentDate9(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalEnrollmentEnrollmentDate9").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row  ").f();
							{ e("div").a("class", "w3-cell ").f();
								inputEnrollmentDate9(classApiMethodMethod);
							} g("div");
							if(
									userKeys.contains(siteRequest_.getUserKey())
									|| Objects.equals(sessionId, siteRequest_.getSessionId())
									|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
							) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_enrollmentDate9')); $('#", classApiMethodMethod, "_enrollmentDate9').val(null); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + $('#MedicalEnrollmentForm :input[name=pk]').val() }], 'setEnrollmentDate9', null, function() { addGlow($('#", classApiMethodMethod, "_enrollmentDate9')); }, function() { addError($('#", classApiMethodMethod, "_enrollmentDate9')); }); ")
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
	// enrollmentDate10 //
	//////////////////////

	/**	L'entité « enrollmentDate10 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	protected LocalDate enrollmentDate10;
	@JsonIgnore
	public Wrap<LocalDate> enrollmentDate10Wrap = new Wrap<LocalDate>().p(this).c(LocalDate.class).var("enrollmentDate10").o(enrollmentDate10);

	/**	<br/>L'entité « enrollmentDate10 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentDate10">Trouver l'entité enrollmentDate10 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _enrollmentDate10(Wrap<LocalDate> c);

	public LocalDate getEnrollmentDate10() {
		return enrollmentDate10;
	}

	public void setEnrollmentDate10(LocalDate enrollmentDate10) {
		this.enrollmentDate10 = enrollmentDate10;
		this.enrollmentDate10Wrap.alreadyInitialized = true;
	}
	public MedicalEnrollment setEnrollmentDate10(Instant o) {
		this.enrollmentDate10 = o == null ? null : LocalDate.from(o);
		this.enrollmentDate10Wrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	/** Example: 2011-12-03+01:00 **/
	public MedicalEnrollment setEnrollmentDate10(String o) {
		this.enrollmentDate10 = o == null ? null : LocalDate.parse(o, DateTimeFormatter.ISO_DATE);
		this.enrollmentDate10Wrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	public MedicalEnrollment setEnrollmentDate10(Date o) {
		this.enrollmentDate10 = o == null ? null : o.toInstant().atZone(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toLocalDate();
		this.enrollmentDate10Wrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	protected MedicalEnrollment enrollmentDate10Init() {
		if(!enrollmentDate10Wrap.alreadyInitialized) {
			_enrollmentDate10(enrollmentDate10Wrap);
			if(enrollmentDate10 == null)
				setEnrollmentDate10(enrollmentDate10Wrap.o);
		}
		enrollmentDate10Wrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public Date solrEnrollmentDate10() {
		return enrollmentDate10 == null ? null : Date.from(enrollmentDate10.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z")).toInstant());
	}

	public String strEnrollmentDate10() {
		return enrollmentDate10 == null ? "" : enrollmentDate10.format(DateTimeFormatter.ofPattern("EEE MMM d, yyyy", Locale.forLanguageTag("en-US")));
	}

	public String jsonEnrollmentDate10() {
		return enrollmentDate10 == null ? "" : enrollmentDate10.format(DateTimeFormatter.ISO_DATE);
	}

	public String nomAffichageEnrollmentDate10() {
		return null;
	}

	public String htmTooltipEnrollmentDate10() {
		return null;
	}

	public String htmEnrollmentDate10() {
		return enrollmentDate10 == null ? "" : StringEscapeUtils.escapeHtml4(strEnrollmentDate10());
	}

	public void inputEnrollmentDate10(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("class", "w3-input w3-border datepicker setEnrollmentDate10 classMedicalEnrollment inputMedicalEnrollment", pk, "EnrollmentDate10 w3-input w3-border ")
				.a("placeholder", "MM/DD/YYYY")
				.a("data-timeformat", "MM/dd/yyyy")
				.a("id", classApiMethodMethod, "_enrollmentDate10")
				.a("onclick", "removeGlow($(this)); ")
				.a("value", enrollmentDate10 == null ? "" : DateTimeFormatter.ofPattern("MM/dd/yyyy").format(enrollmentDate10))
				.a("onchange", "var t = moment(this.value, 'MM/DD/YYYY'); if(t) { var s = t.format('YYYY-MM-DD'); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setEnrollmentDate10', s, function() { addGlow($('#", classApiMethodMethod, "_enrollmentDate10')); }, function() { addError($('#", classApiMethodMethod, "_enrollmentDate10')); }); } ")
				.fg();
		} else {
			sx(htmEnrollmentDate10());
		}
	}

	public void htmEnrollmentDate10(String classApiMethodMethod) {
		MedicalEnrollment s = (MedicalEnrollment)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalEnrollmentEnrollmentDate10").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row  ").f();
							{ e("div").a("class", "w3-cell ").f();
								inputEnrollmentDate10(classApiMethodMethod);
							} g("div");
							if(
									userKeys.contains(siteRequest_.getUserKey())
									|| Objects.equals(sessionId, siteRequest_.getSessionId())
									|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
							) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_enrollmentDate10')); $('#", classApiMethodMethod, "_enrollmentDate10').val(null); patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + $('#MedicalEnrollmentForm :input[name=pk]').val() }], 'setEnrollmentDate10', null, function() { addGlow($('#", classApiMethodMethod, "_enrollmentDate10')); }, function() { addError($('#", classApiMethodMethod, "_enrollmentDate10')); }); ")
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
	// enrollmentEnrollments //
	///////////////////////////

	/**	L'entité « enrollmentEnrollments »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<MedicalEnrollment>(). 
	 */
	@JsonInclude(Include.NON_NULL)
	protected List<MedicalEnrollment> enrollmentEnrollments = new java.util.ArrayList<org.computate.medicale.enUS.enrollment.MedicalEnrollment>();
	@JsonIgnore
	public Wrap<List<MedicalEnrollment>> enrollmentEnrollmentsWrap = new Wrap<List<MedicalEnrollment>>().p(this).c(List.class).var("enrollmentEnrollments").o(enrollmentEnrollments);

	/**	<br/>L'entité « enrollmentEnrollments »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<MedicalEnrollment>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentEnrollments">Trouver l'entité enrollmentEnrollments dans Solr</a>
	 * <br/>
	 * @param enrollmentEnrollments est l'entité déjà construit. 
	 **/
	protected abstract void _enrollmentEnrollments(List<MedicalEnrollment> l);

	public List<MedicalEnrollment> getEnrollmentEnrollments() {
		return enrollmentEnrollments;
	}

	public void setEnrollmentEnrollments(List<MedicalEnrollment> enrollmentEnrollments) {
		this.enrollmentEnrollments = enrollmentEnrollments;
		this.enrollmentEnrollmentsWrap.alreadyInitialized = true;
	}
	public MedicalEnrollment addEnrollmentEnrollments(MedicalEnrollment...objets) {
		for(MedicalEnrollment o : objets) {
			addEnrollmentEnrollments(o);
		}
		return (MedicalEnrollment)this;
	}
	public MedicalEnrollment addEnrollmentEnrollments(MedicalEnrollment o) {
		if(o != null && !enrollmentEnrollments.contains(o))
			this.enrollmentEnrollments.add(o);
		return (MedicalEnrollment)this;
	}
	protected MedicalEnrollment enrollmentEnrollmentsInit() {
		if(!enrollmentEnrollmentsWrap.alreadyInitialized) {
			_enrollmentEnrollments(enrollmentEnrollments);
		}
		enrollmentEnrollmentsWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	//////////////////////
	// enrollmentNumber //
	//////////////////////

	/**	L'entité « enrollmentNumber »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer enrollmentNumber;
	@JsonIgnore
	public Wrap<Integer> enrollmentNumberWrap = new Wrap<Integer>().p(this).c(Integer.class).var("enrollmentNumber").o(enrollmentNumber);

	/**	<br/>L'entité « enrollmentNumber »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentNumber">Trouver l'entité enrollmentNumber dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _enrollmentNumber(Wrap<Integer> c);

	public Integer getEnrollmentNumber() {
		return enrollmentNumber;
	}

	public void setEnrollmentNumber(Integer enrollmentNumber) {
		this.enrollmentNumber = enrollmentNumber;
		this.enrollmentNumberWrap.alreadyInitialized = true;
	}
	public MedicalEnrollment setEnrollmentNumber(String o) {
		if(NumberUtils.isParsable(o))
			this.enrollmentNumber = Integer.parseInt(o);
		this.enrollmentNumberWrap.alreadyInitialized = true;
		return (MedicalEnrollment)this;
	}
	protected MedicalEnrollment enrollmentNumberInit() {
		if(!enrollmentNumberWrap.alreadyInitialized) {
			_enrollmentNumber(enrollmentNumberWrap);
			if(enrollmentNumber == null)
				setEnrollmentNumber(enrollmentNumberWrap.o);
		}
		enrollmentNumberWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public Integer solrEnrollmentNumber() {
		return enrollmentNumber;
	}

	public String strEnrollmentNumber() {
		return enrollmentNumber == null ? "" : enrollmentNumber.toString();
	}

	public String jsonEnrollmentNumber() {
		return enrollmentNumber == null ? "" : enrollmentNumber.toString();
	}

	public String nomAffichageEnrollmentNumber() {
		return null;
	}

	public String htmTooltipEnrollmentNumber() {
		return null;
	}

	public String htmEnrollmentNumber() {
		return enrollmentNumber == null ? "" : StringEscapeUtils.escapeHtml4(strEnrollmentNumber());
	}

	////////////////////////////
	// enrollmentCompleteName //
	////////////////////////////

	/**	L'entité « enrollmentCompleteName »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String enrollmentCompleteName;
	@JsonIgnore
	public Wrap<String> enrollmentCompleteNameWrap = new Wrap<String>().p(this).c(String.class).var("enrollmentCompleteName").o(enrollmentCompleteName);

	/**	<br/>L'entité « enrollmentCompleteName »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.MedicalEnrollment&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentCompleteName">Trouver l'entité enrollmentCompleteName dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _enrollmentCompleteName(Wrap<String> c);

	public String getEnrollmentCompleteName() {
		return enrollmentCompleteName;
	}

	public void setEnrollmentCompleteName(String enrollmentCompleteName) {
		this.enrollmentCompleteName = enrollmentCompleteName;
		this.enrollmentCompleteNameWrap.alreadyInitialized = true;
	}
	protected MedicalEnrollment enrollmentCompleteNameInit() {
		if(!enrollmentCompleteNameWrap.alreadyInitialized) {
			_enrollmentCompleteName(enrollmentCompleteNameWrap);
			if(enrollmentCompleteName == null)
				setEnrollmentCompleteName(enrollmentCompleteNameWrap.o);
		}
		enrollmentCompleteNameWrap.alreadyInitialized(true);
		return (MedicalEnrollment)this;
	}

	public String solrEnrollmentCompleteName() {
		return enrollmentCompleteName;
	}

	public String strEnrollmentCompleteName() {
		return enrollmentCompleteName == null ? "" : enrollmentCompleteName;
	}

	public String jsonEnrollmentCompleteName() {
		return enrollmentCompleteName == null ? "" : enrollmentCompleteName;
	}

	public String nomAffichageEnrollmentCompleteName() {
		return "name";
	}

	public String htmTooltipEnrollmentCompleteName() {
		return null;
	}

	public String htmEnrollmentCompleteName() {
		return enrollmentCompleteName == null ? "" : StringEscapeUtils.escapeHtml4(strEnrollmentCompleteName());
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedMedicalEnrollment = false;

	public MedicalEnrollment initDeepMedicalEnrollment(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedMedicalEnrollment) {
			alreadyInitializedMedicalEnrollment = true;
			initDeepMedicalEnrollment();
		}
		return (MedicalEnrollment)this;
	}

	public void initDeepMedicalEnrollment() {
		initMedicalEnrollment();
		super.initDeepCluster(siteRequest_);
	}

	public void initMedicalEnrollment() {
		enrollmentKeyInit();
		clinicKeyInit();
		clinicSearchInit();
		clinic_Init();
		patientKeyInit();
		enrollmentFormKeyInit();
		userKeysInit();
		medicalSortInit();
		clinicSortInit();
		patientSearchInit();
		patient_Init();
		patientFirstNameInit();
		patientFirstNamePreferredInit();
		patientFamilyNameInit();
		patientCompleteNameInit();
		patientCompleteNamePreferredInit();
		patientBirthDateInit();
		patientBirthMonthInit();
		patientBirthDayInit();
		clinicNameInit();
		clinicCompleteNameInit();
		clinicLocationInit();
		clinicAddressInit();
		clinicPhoneNumberInit();
		clinicAdministratorNameInit();
		enrollmentApprovedInit();
		enrollmentImmunizationsInit();
		familyAddressInit();
		familyHowDoYouKnowTheClinicInit();
		enrollmentSpecialConsiderationsInit();
		patientMedicalConditionsInit();
		patientPreviousClinicsAttendedInit();
		patientDescriptionInit();
		patientObjectivesInit();
		customerProfileIdInit();
		createdYearInit();
		createdDayOfWeekInit();
		createdMonthOfYearInit();
		createdHourOfDayInit();
		enrollmentSignature1Init();
		enrollmentSignature2Init();
		enrollmentSignature3Init();
		enrollmentSignature4Init();
		enrollmentSignature5Init();
		enrollmentSignature6Init();
		enrollmentSignature7Init();
		enrollmentSignature8Init();
		enrollmentSignature9Init();
		enrollmentSignature10Init();
		enrollmentDate1Init();
		enrollmentDate2Init();
		enrollmentDate3Init();
		enrollmentDate4Init();
		enrollmentDate5Init();
		enrollmentDate6Init();
		enrollmentDate7Init();
		enrollmentDate8Init();
		enrollmentDate9Init();
		enrollmentDate10Init();
		enrollmentEnrollmentsInit();
		enrollmentNumberInit();
		enrollmentCompleteNameInit();
	}

	@Override public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepMedicalEnrollment(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestMedicalEnrollment(SiteRequestEnUS siteRequest_) {
			super.siteRequestCluster(siteRequest_);
		if(clinicSearch != null)
			clinicSearch.setSiteRequest_(siteRequest_);
		if(patientSearch != null)
			patientSearch.setSiteRequest_(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestMedicalEnrollment(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainMedicalEnrollment(v);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.obtainForClass(v);
			}
		}
		return o;
	}
	public Object obtainMedicalEnrollment(String var) {
		MedicalEnrollment oMedicalEnrollment = (MedicalEnrollment)this;
		switch(var) {
			case "enrollmentKey":
				return oMedicalEnrollment.enrollmentKey;
			case "clinicKey":
				return oMedicalEnrollment.clinicKey;
			case "clinicSearch":
				return oMedicalEnrollment.clinicSearch;
			case "clinic_":
				return oMedicalEnrollment.clinic_;
			case "patientKey":
				return oMedicalEnrollment.patientKey;
			case "enrollmentFormKey":
				return oMedicalEnrollment.enrollmentFormKey;
			case "userKeys":
				return oMedicalEnrollment.userKeys;
			case "medicalSort":
				return oMedicalEnrollment.medicalSort;
			case "clinicSort":
				return oMedicalEnrollment.clinicSort;
			case "patientSearch":
				return oMedicalEnrollment.patientSearch;
			case "patient_":
				return oMedicalEnrollment.patient_;
			case "patientFirstName":
				return oMedicalEnrollment.patientFirstName;
			case "patientFirstNamePreferred":
				return oMedicalEnrollment.patientFirstNamePreferred;
			case "patientFamilyName":
				return oMedicalEnrollment.patientFamilyName;
			case "patientCompleteName":
				return oMedicalEnrollment.patientCompleteName;
			case "patientCompleteNamePreferred":
				return oMedicalEnrollment.patientCompleteNamePreferred;
			case "patientBirthDate":
				return oMedicalEnrollment.patientBirthDate;
			case "patientBirthMonth":
				return oMedicalEnrollment.patientBirthMonth;
			case "patientBirthDay":
				return oMedicalEnrollment.patientBirthDay;
			case "clinicName":
				return oMedicalEnrollment.clinicName;
			case "clinicCompleteName":
				return oMedicalEnrollment.clinicCompleteName;
			case "clinicLocation":
				return oMedicalEnrollment.clinicLocation;
			case "clinicAddress":
				return oMedicalEnrollment.clinicAddress;
			case "clinicPhoneNumber":
				return oMedicalEnrollment.clinicPhoneNumber;
			case "clinicAdministratorName":
				return oMedicalEnrollment.clinicAdministratorName;
			case "enrollmentApproved":
				return oMedicalEnrollment.enrollmentApproved;
			case "enrollmentImmunizations":
				return oMedicalEnrollment.enrollmentImmunizations;
			case "familyAddress":
				return oMedicalEnrollment.familyAddress;
			case "familyHowDoYouKnowTheClinic":
				return oMedicalEnrollment.familyHowDoYouKnowTheClinic;
			case "enrollmentSpecialConsiderations":
				return oMedicalEnrollment.enrollmentSpecialConsiderations;
			case "patientMedicalConditions":
				return oMedicalEnrollment.patientMedicalConditions;
			case "patientPreviousClinicsAttended":
				return oMedicalEnrollment.patientPreviousClinicsAttended;
			case "patientDescription":
				return oMedicalEnrollment.patientDescription;
			case "patientObjectives":
				return oMedicalEnrollment.patientObjectives;
			case "customerProfileId":
				return oMedicalEnrollment.customerProfileId;
			case "createdYear":
				return oMedicalEnrollment.createdYear;
			case "createdDayOfWeek":
				return oMedicalEnrollment.createdDayOfWeek;
			case "createdMonthOfYear":
				return oMedicalEnrollment.createdMonthOfYear;
			case "createdHourOfDay":
				return oMedicalEnrollment.createdHourOfDay;
			case "enrollmentSignature1":
				return oMedicalEnrollment.enrollmentSignature1;
			case "enrollmentSignature2":
				return oMedicalEnrollment.enrollmentSignature2;
			case "enrollmentSignature3":
				return oMedicalEnrollment.enrollmentSignature3;
			case "enrollmentSignature4":
				return oMedicalEnrollment.enrollmentSignature4;
			case "enrollmentSignature5":
				return oMedicalEnrollment.enrollmentSignature5;
			case "enrollmentSignature6":
				return oMedicalEnrollment.enrollmentSignature6;
			case "enrollmentSignature7":
				return oMedicalEnrollment.enrollmentSignature7;
			case "enrollmentSignature8":
				return oMedicalEnrollment.enrollmentSignature8;
			case "enrollmentSignature9":
				return oMedicalEnrollment.enrollmentSignature9;
			case "enrollmentSignature10":
				return oMedicalEnrollment.enrollmentSignature10;
			case "enrollmentDate1":
				return oMedicalEnrollment.enrollmentDate1;
			case "enrollmentDate2":
				return oMedicalEnrollment.enrollmentDate2;
			case "enrollmentDate3":
				return oMedicalEnrollment.enrollmentDate3;
			case "enrollmentDate4":
				return oMedicalEnrollment.enrollmentDate4;
			case "enrollmentDate5":
				return oMedicalEnrollment.enrollmentDate5;
			case "enrollmentDate6":
				return oMedicalEnrollment.enrollmentDate6;
			case "enrollmentDate7":
				return oMedicalEnrollment.enrollmentDate7;
			case "enrollmentDate8":
				return oMedicalEnrollment.enrollmentDate8;
			case "enrollmentDate9":
				return oMedicalEnrollment.enrollmentDate9;
			case "enrollmentDate10":
				return oMedicalEnrollment.enrollmentDate10;
			case "enrollmentEnrollments":
				return oMedicalEnrollment.enrollmentEnrollments;
			case "enrollmentNumber":
				return oMedicalEnrollment.enrollmentNumber;
			case "enrollmentCompleteName":
				return oMedicalEnrollment.enrollmentCompleteName;
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
				o = attributeMedicalEnrollment(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeMedicalEnrollment(String var, Object val) {
		MedicalEnrollment oMedicalEnrollment = (MedicalEnrollment)this;
		switch(var) {
			case "patientKey":
				oMedicalEnrollment.setPatientKey((Long)val);
				if(!savesMedicalEnrollment.contains(var))
					savesMedicalEnrollment.add(var);
				return val;
			case "userKeys":
				oMedicalEnrollment.addUserKeys((Long)val);
				if(!savesMedicalEnrollment.contains(var))
					savesMedicalEnrollment.add(var);
				return val;
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
					o = defineMedicalEnrollment(v, val);
				else if(o instanceof Cluster) {
					Cluster cluster = (Cluster)o;
					o = cluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineMedicalEnrollment(String var, String val) {
		switch(var) {
			case "patientCompleteName":
				if(val != null)
					setPatientCompleteName(val);
				savesMedicalEnrollment.add(var);
				return val;
			case "patientCompleteNamePreferred":
				if(val != null)
					setPatientCompleteNamePreferred(val);
				savesMedicalEnrollment.add(var);
				return val;
			case "patientBirthDate":
				if(val != null)
					setPatientBirthDate(val);
				savesMedicalEnrollment.add(var);
				return val;
			case "clinicAddress":
				if(val != null)
					setClinicAddress(val);
				savesMedicalEnrollment.add(var);
				return val;
			case "enrollmentApproved":
				if(val != null)
					setEnrollmentApproved(val);
				savesMedicalEnrollment.add(var);
				return val;
			case "enrollmentImmunizations":
				if(val != null)
					setEnrollmentImmunizations(val);
				savesMedicalEnrollment.add(var);
				return val;
			case "familyAddress":
				if(val != null)
					setFamilyAddress(val);
				savesMedicalEnrollment.add(var);
				return val;
			case "familyHowDoYouKnowTheClinic":
				if(val != null)
					setFamilyHowDoYouKnowTheClinic(val);
				savesMedicalEnrollment.add(var);
				return val;
			case "enrollmentSpecialConsiderations":
				if(val != null)
					setEnrollmentSpecialConsiderations(val);
				savesMedicalEnrollment.add(var);
				return val;
			case "patientMedicalConditions":
				if(val != null)
					setPatientMedicalConditions(val);
				savesMedicalEnrollment.add(var);
				return val;
			case "patientPreviousClinicsAttended":
				if(val != null)
					setPatientPreviousClinicsAttended(val);
				savesMedicalEnrollment.add(var);
				return val;
			case "patientDescription":
				if(val != null)
					setPatientDescription(val);
				savesMedicalEnrollment.add(var);
				return val;
			case "patientObjectives":
				if(val != null)
					setPatientObjectives(val);
				savesMedicalEnrollment.add(var);
				return val;
			case "customerProfileId":
				if(val != null)
					setCustomerProfileId(val);
				savesMedicalEnrollment.add(var);
				return val;
			case "enrollmentSignature1":
				if(val != null)
					setEnrollmentSignature1(val);
				savesMedicalEnrollment.add(var);
				return val;
			case "enrollmentSignature2":
				if(val != null)
					setEnrollmentSignature2(val);
				savesMedicalEnrollment.add(var);
				return val;
			case "enrollmentSignature3":
				if(val != null)
					setEnrollmentSignature3(val);
				savesMedicalEnrollment.add(var);
				return val;
			case "enrollmentSignature4":
				if(val != null)
					setEnrollmentSignature4(val);
				savesMedicalEnrollment.add(var);
				return val;
			case "enrollmentSignature5":
				if(val != null)
					setEnrollmentSignature5(val);
				savesMedicalEnrollment.add(var);
				return val;
			case "enrollmentSignature6":
				if(val != null)
					setEnrollmentSignature6(val);
				savesMedicalEnrollment.add(var);
				return val;
			case "enrollmentSignature7":
				if(val != null)
					setEnrollmentSignature7(val);
				savesMedicalEnrollment.add(var);
				return val;
			case "enrollmentSignature8":
				if(val != null)
					setEnrollmentSignature8(val);
				savesMedicalEnrollment.add(var);
				return val;
			case "enrollmentSignature9":
				if(val != null)
					setEnrollmentSignature9(val);
				savesMedicalEnrollment.add(var);
				return val;
			case "enrollmentSignature10":
				if(val != null)
					setEnrollmentSignature10(val);
				savesMedicalEnrollment.add(var);
				return val;
			case "enrollmentDate1":
				if(val != null)
					setEnrollmentDate1(val);
				savesMedicalEnrollment.add(var);
				return val;
			case "enrollmentDate2":
				if(val != null)
					setEnrollmentDate2(val);
				savesMedicalEnrollment.add(var);
				return val;
			case "enrollmentDate3":
				if(val != null)
					setEnrollmentDate3(val);
				savesMedicalEnrollment.add(var);
				return val;
			case "enrollmentDate4":
				if(val != null)
					setEnrollmentDate4(val);
				savesMedicalEnrollment.add(var);
				return val;
			case "enrollmentDate5":
				if(val != null)
					setEnrollmentDate5(val);
				savesMedicalEnrollment.add(var);
				return val;
			case "enrollmentDate6":
				if(val != null)
					setEnrollmentDate6(val);
				savesMedicalEnrollment.add(var);
				return val;
			case "enrollmentDate7":
				if(val != null)
					setEnrollmentDate7(val);
				savesMedicalEnrollment.add(var);
				return val;
			case "enrollmentDate8":
				if(val != null)
					setEnrollmentDate8(val);
				savesMedicalEnrollment.add(var);
				return val;
			case "enrollmentDate9":
				if(val != null)
					setEnrollmentDate9(val);
				savesMedicalEnrollment.add(var);
				return val;
			case "enrollmentDate10":
				if(val != null)
					setEnrollmentDate10(val);
				savesMedicalEnrollment.add(var);
				return val;
			default:
				return super.defineCluster(var, val);
		}
	}

	/////////////////
	// saves //
	/////////////////

	protected List<String> savesMedicalEnrollment = new ArrayList<String>();

	/////////////
	// populate //
	/////////////

	@Override public void populateForClass(SolrDocument solrDocument) {
		populateMedicalEnrollment(solrDocument);
	}
	public void populateMedicalEnrollment(SolrDocument solrDocument) {
		MedicalEnrollment oMedicalEnrollment = (MedicalEnrollment)this;
		savesMedicalEnrollment = (List<String>)solrDocument.get("savesMedicalEnrollment_stored_strings");
		if(savesMedicalEnrollment != null) {

			if(savesMedicalEnrollment.contains("enrollmentKey")) {
				Long enrollmentKey = (Long)solrDocument.get("enrollmentKey_stored_long");
				if(enrollmentKey != null)
					oMedicalEnrollment.setEnrollmentKey(enrollmentKey);
			}

			if(savesMedicalEnrollment.contains("clinicKey")) {
				Long clinicKey = (Long)solrDocument.get("clinicKey_stored_long");
				if(clinicKey != null)
					oMedicalEnrollment.setClinicKey(clinicKey);
			}

			Long patientKey = (Long)solrDocument.get("patientKey_stored_long");
			if(patientKey != null)
				oMedicalEnrollment.setPatientKey(patientKey);

			if(savesMedicalEnrollment.contains("enrollmentFormKey")) {
				Long enrollmentFormKey = (Long)solrDocument.get("enrollmentFormKey_stored_long");
				if(enrollmentFormKey != null)
					oMedicalEnrollment.setEnrollmentFormKey(enrollmentFormKey);
			}

			List<Long> userKeys = (List<Long>)solrDocument.get("userKeys_stored_longs");
			if(userKeys != null)
				oMedicalEnrollment.userKeys.addAll(userKeys);

			if(savesMedicalEnrollment.contains("medicalSort")) {
				Integer medicalSort = (Integer)solrDocument.get("medicalSort_stored_int");
				if(medicalSort != null)
					oMedicalEnrollment.setMedicalSort(medicalSort);
			}

			if(savesMedicalEnrollment.contains("clinicSort")) {
				Integer clinicSort = (Integer)solrDocument.get("clinicSort_stored_int");
				if(clinicSort != null)
					oMedicalEnrollment.setClinicSort(clinicSort);
			}

			if(savesMedicalEnrollment.contains("patientFirstName")) {
				String patientFirstName = (String)solrDocument.get("patientFirstName_stored_string");
				if(patientFirstName != null)
					oMedicalEnrollment.setPatientFirstName(patientFirstName);
			}

			if(savesMedicalEnrollment.contains("patientFirstNamePreferred")) {
				String patientFirstNamePreferred = (String)solrDocument.get("patientFirstNamePreferred_stored_string");
				if(patientFirstNamePreferred != null)
					oMedicalEnrollment.setPatientFirstNamePreferred(patientFirstNamePreferred);
			}

			if(savesMedicalEnrollment.contains("patientFamilyName")) {
				String patientFamilyName = (String)solrDocument.get("patientFamilyName_stored_string");
				if(patientFamilyName != null)
					oMedicalEnrollment.setPatientFamilyName(patientFamilyName);
			}

			if(savesMedicalEnrollment.contains("patientCompleteName")) {
				String patientCompleteName = (String)solrDocument.get("patientCompleteName_stored_string");
				if(patientCompleteName != null)
					oMedicalEnrollment.setPatientCompleteName(patientCompleteName);
			}

			if(savesMedicalEnrollment.contains("patientCompleteNamePreferred")) {
				String patientCompleteNamePreferred = (String)solrDocument.get("patientCompleteNamePreferred_stored_string");
				if(patientCompleteNamePreferred != null)
					oMedicalEnrollment.setPatientCompleteNamePreferred(patientCompleteNamePreferred);
			}

			if(savesMedicalEnrollment.contains("patientBirthDate")) {
				Date patientBirthDate = (Date)solrDocument.get("patientBirthDate_stored_date");
				if(patientBirthDate != null)
					oMedicalEnrollment.setPatientBirthDate(patientBirthDate);
			}

			if(savesMedicalEnrollment.contains("patientBirthMonth")) {
				Integer patientBirthMonth = (Integer)solrDocument.get("patientBirthMonth_stored_int");
				if(patientBirthMonth != null)
					oMedicalEnrollment.setPatientBirthMonth(patientBirthMonth);
			}

			if(savesMedicalEnrollment.contains("patientBirthDay")) {
				Integer patientBirthDay = (Integer)solrDocument.get("patientBirthDay_stored_int");
				if(patientBirthDay != null)
					oMedicalEnrollment.setPatientBirthDay(patientBirthDay);
			}

			if(savesMedicalEnrollment.contains("clinicName")) {
				String clinicName = (String)solrDocument.get("clinicName_stored_string");
				if(clinicName != null)
					oMedicalEnrollment.setClinicName(clinicName);
			}

			if(savesMedicalEnrollment.contains("clinicCompleteName")) {
				String clinicCompleteName = (String)solrDocument.get("clinicCompleteName_stored_string");
				if(clinicCompleteName != null)
					oMedicalEnrollment.setClinicCompleteName(clinicCompleteName);
			}

			if(savesMedicalEnrollment.contains("clinicLocation")) {
				String clinicLocation = (String)solrDocument.get("clinicLocation_stored_string");
				if(clinicLocation != null)
					oMedicalEnrollment.setClinicLocation(clinicLocation);
			}

			if(savesMedicalEnrollment.contains("clinicAddress")) {
				String clinicAddress = (String)solrDocument.get("clinicAddress_stored_string");
				if(clinicAddress != null)
					oMedicalEnrollment.setClinicAddress(clinicAddress);
			}

			if(savesMedicalEnrollment.contains("clinicPhoneNumber")) {
				String clinicPhoneNumber = (String)solrDocument.get("clinicPhoneNumber_stored_string");
				if(clinicPhoneNumber != null)
					oMedicalEnrollment.setClinicPhoneNumber(clinicPhoneNumber);
			}

			if(savesMedicalEnrollment.contains("clinicAdministratorName")) {
				String clinicAdministratorName = (String)solrDocument.get("clinicAdministratorName_stored_string");
				if(clinicAdministratorName != null)
					oMedicalEnrollment.setClinicAdministratorName(clinicAdministratorName);
			}

			if(savesMedicalEnrollment.contains("enrollmentApproved")) {
				Boolean enrollmentApproved = (Boolean)solrDocument.get("enrollmentApproved_stored_boolean");
				if(enrollmentApproved != null)
					oMedicalEnrollment.setEnrollmentApproved(enrollmentApproved);
			}

			if(savesMedicalEnrollment.contains("enrollmentImmunizations")) {
				Boolean enrollmentImmunizations = (Boolean)solrDocument.get("enrollmentImmunizations_stored_boolean");
				if(enrollmentImmunizations != null)
					oMedicalEnrollment.setEnrollmentImmunizations(enrollmentImmunizations);
			}

			if(savesMedicalEnrollment.contains("familyAddress")) {
				String familyAddress = (String)solrDocument.get("familyAddress_stored_string");
				if(familyAddress != null)
					oMedicalEnrollment.setFamilyAddress(familyAddress);
			}

			if(savesMedicalEnrollment.contains("familyHowDoYouKnowTheClinic")) {
				String familyHowDoYouKnowTheClinic = (String)solrDocument.get("familyHowDoYouKnowTheClinic_stored_string");
				if(familyHowDoYouKnowTheClinic != null)
					oMedicalEnrollment.setFamilyHowDoYouKnowTheClinic(familyHowDoYouKnowTheClinic);
			}

			if(savesMedicalEnrollment.contains("enrollmentSpecialConsiderations")) {
				String enrollmentSpecialConsiderations = (String)solrDocument.get("enrollmentSpecialConsiderations_stored_string");
				if(enrollmentSpecialConsiderations != null)
					oMedicalEnrollment.setEnrollmentSpecialConsiderations(enrollmentSpecialConsiderations);
			}

			if(savesMedicalEnrollment.contains("patientMedicalConditions")) {
				String patientMedicalConditions = (String)solrDocument.get("patientMedicalConditions_stored_string");
				if(patientMedicalConditions != null)
					oMedicalEnrollment.setPatientMedicalConditions(patientMedicalConditions);
			}

			if(savesMedicalEnrollment.contains("patientPreviousClinicsAttended")) {
				String patientPreviousClinicsAttended = (String)solrDocument.get("patientPreviousClinicsAttended_stored_string");
				if(patientPreviousClinicsAttended != null)
					oMedicalEnrollment.setPatientPreviousClinicsAttended(patientPreviousClinicsAttended);
			}

			if(savesMedicalEnrollment.contains("patientDescription")) {
				String patientDescription = (String)solrDocument.get("patientDescription_stored_string");
				if(patientDescription != null)
					oMedicalEnrollment.setPatientDescription(patientDescription);
			}

			if(savesMedicalEnrollment.contains("patientObjectives")) {
				String patientObjectives = (String)solrDocument.get("patientObjectives_stored_string");
				if(patientObjectives != null)
					oMedicalEnrollment.setPatientObjectives(patientObjectives);
			}

			if(savesMedicalEnrollment.contains("customerProfileId")) {
				String customerProfileId = (String)solrDocument.get("customerProfileId_stored_string");
				if(customerProfileId != null)
					oMedicalEnrollment.setCustomerProfileId(customerProfileId);
			}

			if(savesMedicalEnrollment.contains("createdYear")) {
				Integer createdYear = (Integer)solrDocument.get("createdYear_stored_int");
				if(createdYear != null)
					oMedicalEnrollment.setCreatedYear(createdYear);
			}

			if(savesMedicalEnrollment.contains("createdDayOfWeek")) {
				String createdDayOfWeek = (String)solrDocument.get("createdDayOfWeek_stored_string");
				if(createdDayOfWeek != null)
					oMedicalEnrollment.setCreatedDayOfWeek(createdDayOfWeek);
			}

			if(savesMedicalEnrollment.contains("createdMonthOfYear")) {
				String createdMonthOfYear = (String)solrDocument.get("createdMonthOfYear_stored_string");
				if(createdMonthOfYear != null)
					oMedicalEnrollment.setCreatedMonthOfYear(createdMonthOfYear);
			}

			if(savesMedicalEnrollment.contains("createdHourOfDay")) {
				String createdHourOfDay = (String)solrDocument.get("createdHourOfDay_stored_string");
				if(createdHourOfDay != null)
					oMedicalEnrollment.setCreatedHourOfDay(createdHourOfDay);
			}

			if(savesMedicalEnrollment.contains("enrollmentSignature1")) {
				String enrollmentSignature1 = (String)solrDocument.get("enrollmentSignature1_stored_string");
				if(enrollmentSignature1 != null)
					oMedicalEnrollment.setEnrollmentSignature1(enrollmentSignature1);
			}

			if(savesMedicalEnrollment.contains("enrollmentSignature2")) {
				String enrollmentSignature2 = (String)solrDocument.get("enrollmentSignature2_stored_string");
				if(enrollmentSignature2 != null)
					oMedicalEnrollment.setEnrollmentSignature2(enrollmentSignature2);
			}

			if(savesMedicalEnrollment.contains("enrollmentSignature3")) {
				String enrollmentSignature3 = (String)solrDocument.get("enrollmentSignature3_stored_string");
				if(enrollmentSignature3 != null)
					oMedicalEnrollment.setEnrollmentSignature3(enrollmentSignature3);
			}

			if(savesMedicalEnrollment.contains("enrollmentSignature4")) {
				String enrollmentSignature4 = (String)solrDocument.get("enrollmentSignature4_stored_string");
				if(enrollmentSignature4 != null)
					oMedicalEnrollment.setEnrollmentSignature4(enrollmentSignature4);
			}

			if(savesMedicalEnrollment.contains("enrollmentSignature5")) {
				String enrollmentSignature5 = (String)solrDocument.get("enrollmentSignature5_stored_string");
				if(enrollmentSignature5 != null)
					oMedicalEnrollment.setEnrollmentSignature5(enrollmentSignature5);
			}

			if(savesMedicalEnrollment.contains("enrollmentSignature6")) {
				String enrollmentSignature6 = (String)solrDocument.get("enrollmentSignature6_stored_string");
				if(enrollmentSignature6 != null)
					oMedicalEnrollment.setEnrollmentSignature6(enrollmentSignature6);
			}

			if(savesMedicalEnrollment.contains("enrollmentSignature7")) {
				String enrollmentSignature7 = (String)solrDocument.get("enrollmentSignature7_stored_string");
				if(enrollmentSignature7 != null)
					oMedicalEnrollment.setEnrollmentSignature7(enrollmentSignature7);
			}

			if(savesMedicalEnrollment.contains("enrollmentSignature8")) {
				String enrollmentSignature8 = (String)solrDocument.get("enrollmentSignature8_stored_string");
				if(enrollmentSignature8 != null)
					oMedicalEnrollment.setEnrollmentSignature8(enrollmentSignature8);
			}

			if(savesMedicalEnrollment.contains("enrollmentSignature9")) {
				String enrollmentSignature9 = (String)solrDocument.get("enrollmentSignature9_stored_string");
				if(enrollmentSignature9 != null)
					oMedicalEnrollment.setEnrollmentSignature9(enrollmentSignature9);
			}

			if(savesMedicalEnrollment.contains("enrollmentSignature10")) {
				String enrollmentSignature10 = (String)solrDocument.get("enrollmentSignature10_stored_string");
				if(enrollmentSignature10 != null)
					oMedicalEnrollment.setEnrollmentSignature10(enrollmentSignature10);
			}

			if(savesMedicalEnrollment.contains("enrollmentDate1")) {
				Date enrollmentDate1 = (Date)solrDocument.get("enrollmentDate1_stored_date");
				if(enrollmentDate1 != null)
					oMedicalEnrollment.setEnrollmentDate1(enrollmentDate1);
			}

			if(savesMedicalEnrollment.contains("enrollmentDate2")) {
				Date enrollmentDate2 = (Date)solrDocument.get("enrollmentDate2_stored_date");
				if(enrollmentDate2 != null)
					oMedicalEnrollment.setEnrollmentDate2(enrollmentDate2);
			}

			if(savesMedicalEnrollment.contains("enrollmentDate3")) {
				Date enrollmentDate3 = (Date)solrDocument.get("enrollmentDate3_stored_date");
				if(enrollmentDate3 != null)
					oMedicalEnrollment.setEnrollmentDate3(enrollmentDate3);
			}

			if(savesMedicalEnrollment.contains("enrollmentDate4")) {
				Date enrollmentDate4 = (Date)solrDocument.get("enrollmentDate4_stored_date");
				if(enrollmentDate4 != null)
					oMedicalEnrollment.setEnrollmentDate4(enrollmentDate4);
			}

			if(savesMedicalEnrollment.contains("enrollmentDate5")) {
				Date enrollmentDate5 = (Date)solrDocument.get("enrollmentDate5_stored_date");
				if(enrollmentDate5 != null)
					oMedicalEnrollment.setEnrollmentDate5(enrollmentDate5);
			}

			if(savesMedicalEnrollment.contains("enrollmentDate6")) {
				Date enrollmentDate6 = (Date)solrDocument.get("enrollmentDate6_stored_date");
				if(enrollmentDate6 != null)
					oMedicalEnrollment.setEnrollmentDate6(enrollmentDate6);
			}

			if(savesMedicalEnrollment.contains("enrollmentDate7")) {
				Date enrollmentDate7 = (Date)solrDocument.get("enrollmentDate7_stored_date");
				if(enrollmentDate7 != null)
					oMedicalEnrollment.setEnrollmentDate7(enrollmentDate7);
			}

			if(savesMedicalEnrollment.contains("enrollmentDate8")) {
				Date enrollmentDate8 = (Date)solrDocument.get("enrollmentDate8_stored_date");
				if(enrollmentDate8 != null)
					oMedicalEnrollment.setEnrollmentDate8(enrollmentDate8);
			}

			if(savesMedicalEnrollment.contains("enrollmentDate9")) {
				Date enrollmentDate9 = (Date)solrDocument.get("enrollmentDate9_stored_date");
				if(enrollmentDate9 != null)
					oMedicalEnrollment.setEnrollmentDate9(enrollmentDate9);
			}

			if(savesMedicalEnrollment.contains("enrollmentDate10")) {
				Date enrollmentDate10 = (Date)solrDocument.get("enrollmentDate10_stored_date");
				if(enrollmentDate10 != null)
					oMedicalEnrollment.setEnrollmentDate10(enrollmentDate10);
			}

			if(savesMedicalEnrollment.contains("enrollmentCompleteName")) {
				String enrollmentCompleteName = (String)solrDocument.get("enrollmentCompleteName_stored_string");
				if(enrollmentCompleteName != null)
					oMedicalEnrollment.setEnrollmentCompleteName(enrollmentCompleteName);
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
			solrQuery.addFilterQuery("id:" + ClientUtils.escapeQueryChars("org.computate.medicale.enUS.enrollment.MedicalEnrollment"));
			QueryResponse queryResponse = siteRequest.getSiteContext_().getSolrClient().query(solrQuery);
			if(queryResponse.getResults().size() > 0)
				siteRequest.setSolrDocument(queryResponse.getResults().get(0));
			MedicalEnrollment o = new MedicalEnrollment();
			o.siteRequestMedicalEnrollment(siteRequest);
			o.initDeepMedicalEnrollment(siteRequest);
			o.indexMedicalEnrollment();
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}


	@Override public void indexForClass() {
		indexMedicalEnrollment();
	}

	@Override public void indexForClass(SolrInputDocument document) {
		indexMedicalEnrollment(document);
	}

	public void indexMedicalEnrollment(SolrClient clientSolr) {
		try {
			SolrInputDocument document = new SolrInputDocument();
			indexMedicalEnrollment(document);
			clientSolr.add(document);
			clientSolr.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public void indexMedicalEnrollment() {
		try {
			SolrInputDocument document = new SolrInputDocument();
			indexMedicalEnrollment(document);
			SolrClient clientSolr = siteRequest_.getSiteContext_().getSolrClient();
			clientSolr.add(document);
			clientSolr.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public void indexMedicalEnrollment(SolrInputDocument document) {
		if(savesMedicalEnrollment != null)
			document.addField("savesMedicalEnrollment_stored_strings", savesMedicalEnrollment);

		if(enrollmentKey != null) {
			document.addField("enrollmentKey_indexed_long", enrollmentKey);
			document.addField("enrollmentKey_stored_long", enrollmentKey);
		}
		if(clinicKey != null) {
			document.addField("clinicKey_indexed_long", clinicKey);
			document.addField("clinicKey_stored_long", clinicKey);
		}
		if(patientKey != null) {
			document.addField("patientKey_indexed_long", patientKey);
			document.addField("patientKey_stored_long", patientKey);
		}
		if(enrollmentFormKey != null) {
			document.addField("enrollmentFormKey_indexed_long", enrollmentFormKey);
			document.addField("enrollmentFormKey_stored_long", enrollmentFormKey);
		}
		if(userKeys != null) {
			for(java.lang.Long o : userKeys) {
				document.addField("userKeys_indexed_longs", o);
			}
			for(java.lang.Long o : userKeys) {
				document.addField("userKeys_stored_longs", o);
			}
		}
		if(medicalSort != null) {
			document.addField("medicalSort_indexed_int", medicalSort);
			document.addField("medicalSort_stored_int", medicalSort);
		}
		if(clinicSort != null) {
			document.addField("clinicSort_indexed_int", clinicSort);
			document.addField("clinicSort_stored_int", clinicSort);
		}
		if(patientFirstName != null) {
			document.addField("patientFirstName_indexed_string", patientFirstName);
			document.addField("patientFirstName_stored_string", patientFirstName);
		}
		if(patientFirstNamePreferred != null) {
			document.addField("patientFirstNamePreferred_indexed_string", patientFirstNamePreferred);
			document.addField("patientFirstNamePreferred_stored_string", patientFirstNamePreferred);
		}
		if(patientFamilyName != null) {
			document.addField("patientFamilyName_indexed_string", patientFamilyName);
			document.addField("patientFamilyName_stored_string", patientFamilyName);
		}
		if(patientCompleteName != null) {
			document.addField("patientCompleteName_indexed_string", patientCompleteName);
			document.addField("patientCompleteName_stored_string", patientCompleteName);
		}
		if(patientCompleteNamePreferred != null) {
			document.addField("patientCompleteNamePreferred_indexed_string", patientCompleteNamePreferred);
			document.addField("patientCompleteNamePreferred_stored_string", patientCompleteNamePreferred);
		}
		if(patientBirthDate != null) {
			document.addField("patientBirthDate_indexed_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(patientBirthDate.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
			document.addField("patientBirthDate_stored_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(patientBirthDate.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
		}
		if(patientBirthMonth != null) {
			document.addField("patientBirthMonth_indexed_int", patientBirthMonth);
			document.addField("patientBirthMonth_stored_int", patientBirthMonth);
		}
		if(patientBirthDay != null) {
			document.addField("patientBirthDay_indexed_int", patientBirthDay);
			document.addField("patientBirthDay_stored_int", patientBirthDay);
		}
		if(clinicName != null) {
			document.addField("clinicName_indexed_string", clinicName);
			document.addField("clinicName_stored_string", clinicName);
		}
		if(clinicCompleteName != null) {
			document.addField("clinicCompleteName_indexed_string", clinicCompleteName);
			document.addField("clinicCompleteName_stored_string", clinicCompleteName);
		}
		if(clinicLocation != null) {
			document.addField("clinicLocation_indexed_string", clinicLocation);
			document.addField("clinicLocation_stored_string", clinicLocation);
		}
		if(clinicAddress != null) {
			document.addField("clinicAddress_indexed_string", clinicAddress);
			document.addField("clinicAddress_stored_string", clinicAddress);
		}
		if(clinicPhoneNumber != null) {
			document.addField("clinicPhoneNumber_indexed_string", clinicPhoneNumber);
			document.addField("clinicPhoneNumber_stored_string", clinicPhoneNumber);
		}
		if(clinicAdministratorName != null) {
			document.addField("clinicAdministratorName_indexed_string", clinicAdministratorName);
			document.addField("clinicAdministratorName_stored_string", clinicAdministratorName);
		}
		if(enrollmentApproved != null) {
			document.addField("enrollmentApproved_indexed_boolean", enrollmentApproved);
			document.addField("enrollmentApproved_stored_boolean", enrollmentApproved);
		}
		if(enrollmentImmunizations != null) {
			document.addField("enrollmentImmunizations_indexed_boolean", enrollmentImmunizations);
			document.addField("enrollmentImmunizations_stored_boolean", enrollmentImmunizations);
		}
		if(familyAddress != null) {
			document.addField("familyAddress_indexed_string", familyAddress);
			document.addField("familyAddress_stored_string", familyAddress);
		}
		if(familyHowDoYouKnowTheClinic != null) {
			document.addField("familyHowDoYouKnowTheClinic_indexed_string", familyHowDoYouKnowTheClinic);
			document.addField("familyHowDoYouKnowTheClinic_stored_string", familyHowDoYouKnowTheClinic);
		}
		if(enrollmentSpecialConsiderations != null) {
			document.addField("enrollmentSpecialConsiderations_indexed_string", enrollmentSpecialConsiderations);
			document.addField("enrollmentSpecialConsiderations_stored_string", enrollmentSpecialConsiderations);
		}
		if(patientMedicalConditions != null) {
			document.addField("patientMedicalConditions_indexed_string", patientMedicalConditions);
			document.addField("patientMedicalConditions_stored_string", patientMedicalConditions);
		}
		if(patientPreviousClinicsAttended != null) {
			document.addField("patientPreviousClinicsAttended_indexed_string", patientPreviousClinicsAttended);
			document.addField("patientPreviousClinicsAttended_stored_string", patientPreviousClinicsAttended);
		}
		if(patientDescription != null) {
			document.addField("patientDescription_indexed_string", patientDescription);
			document.addField("patientDescription_stored_string", patientDescription);
		}
		if(patientObjectives != null) {
			document.addField("patientObjectives_indexed_string", patientObjectives);
			document.addField("patientObjectives_stored_string", patientObjectives);
		}
		if(customerProfileId != null) {
			document.addField("customerProfileId_indexed_string", customerProfileId);
			document.addField("customerProfileId_stored_string", customerProfileId);
		}
		if(createdYear != null) {
			document.addField("createdYear_indexed_int", createdYear);
			document.addField("createdYear_stored_int", createdYear);
		}
		if(createdDayOfWeek != null) {
			document.addField("createdDayOfWeek_indexed_string", createdDayOfWeek);
			document.addField("createdDayOfWeek_stored_string", createdDayOfWeek);
		}
		if(createdMonthOfYear != null) {
			document.addField("createdMonthOfYear_indexed_string", createdMonthOfYear);
			document.addField("createdMonthOfYear_stored_string", createdMonthOfYear);
		}
		if(createdHourOfDay != null) {
			document.addField("createdHourOfDay_indexed_string", createdHourOfDay);
			document.addField("createdHourOfDay_stored_string", createdHourOfDay);
		}
		if(enrollmentSignature1 != null) {
			document.addField("enrollmentSignature1_stored_string", enrollmentSignature1);
		}
		if(enrollmentSignature2 != null) {
			document.addField("enrollmentSignature2_stored_string", enrollmentSignature2);
		}
		if(enrollmentSignature3 != null) {
			document.addField("enrollmentSignature3_stored_string", enrollmentSignature3);
		}
		if(enrollmentSignature4 != null) {
			document.addField("enrollmentSignature4_stored_string", enrollmentSignature4);
		}
		if(enrollmentSignature5 != null) {
			document.addField("enrollmentSignature5_stored_string", enrollmentSignature5);
		}
		if(enrollmentSignature6 != null) {
			document.addField("enrollmentSignature6_stored_string", enrollmentSignature6);
		}
		if(enrollmentSignature7 != null) {
			document.addField("enrollmentSignature7_stored_string", enrollmentSignature7);
		}
		if(enrollmentSignature8 != null) {
			document.addField("enrollmentSignature8_stored_string", enrollmentSignature8);
		}
		if(enrollmentSignature9 != null) {
			document.addField("enrollmentSignature9_stored_string", enrollmentSignature9);
		}
		if(enrollmentSignature10 != null) {
			document.addField("enrollmentSignature10_stored_string", enrollmentSignature10);
		}
		if(enrollmentDate1 != null) {
			document.addField("enrollmentDate1_indexed_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(enrollmentDate1.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
			document.addField("enrollmentDate1_stored_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(enrollmentDate1.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
		}
		if(enrollmentDate2 != null) {
			document.addField("enrollmentDate2_indexed_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(enrollmentDate2.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
			document.addField("enrollmentDate2_stored_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(enrollmentDate2.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
		}
		if(enrollmentDate3 != null) {
			document.addField("enrollmentDate3_indexed_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(enrollmentDate3.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
			document.addField("enrollmentDate3_stored_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(enrollmentDate3.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
		}
		if(enrollmentDate4 != null) {
			document.addField("enrollmentDate4_indexed_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(enrollmentDate4.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
			document.addField("enrollmentDate4_stored_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(enrollmentDate4.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
		}
		if(enrollmentDate5 != null) {
			document.addField("enrollmentDate5_indexed_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(enrollmentDate5.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
			document.addField("enrollmentDate5_stored_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(enrollmentDate5.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
		}
		if(enrollmentDate6 != null) {
			document.addField("enrollmentDate6_indexed_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(enrollmentDate6.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
			document.addField("enrollmentDate6_stored_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(enrollmentDate6.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
		}
		if(enrollmentDate7 != null) {
			document.addField("enrollmentDate7_indexed_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(enrollmentDate7.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
			document.addField("enrollmentDate7_stored_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(enrollmentDate7.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
		}
		if(enrollmentDate8 != null) {
			document.addField("enrollmentDate8_indexed_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(enrollmentDate8.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
			document.addField("enrollmentDate8_stored_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(enrollmentDate8.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
		}
		if(enrollmentDate9 != null) {
			document.addField("enrollmentDate9_indexed_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(enrollmentDate9.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
			document.addField("enrollmentDate9_stored_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(enrollmentDate9.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
		}
		if(enrollmentDate10 != null) {
			document.addField("enrollmentDate10_indexed_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(enrollmentDate10.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
			document.addField("enrollmentDate10_stored_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(enrollmentDate10.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
		}
		if(enrollmentCompleteName != null) {
			document.addField("enrollmentCompleteName_indexed_string", enrollmentCompleteName);
			document.addField("enrollmentCompleteName_stored_string", enrollmentCompleteName);
		}
		super.indexCluster(document);

	}

	public void unindexMedicalEnrollment() {
		try {
		SiteRequestEnUS siteRequest = new SiteRequestEnUS();
			siteRequest.initDeepSiteRequestEnUS();
			SiteContextEnUS siteContext = new SiteContextEnUS();
			siteContext.initDeepSiteContextEnUS();
			siteRequest.setSiteContext_(siteContext);
			siteRequest.setSiteConfig_(siteContext.getSiteConfig());
			initDeepMedicalEnrollment(siteRequest);
			SolrClient solrClient = siteContext.getSolrClient();
			solrClient.deleteById(id.toString());
			solrClient.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public static String varIndexedMedicalEnrollment(String entityVar) {
		switch(entityVar) {
			case "enrollmentKey":
				return "enrollmentKey_indexed_long";
			case "clinicKey":
				return "clinicKey_indexed_long";
			case "patientKey":
				return "patientKey_indexed_long";
			case "enrollmentFormKey":
				return "enrollmentFormKey_indexed_long";
			case "userKeys":
				return "userKeys_indexed_longs";
			case "medicalSort":
				return "medicalSort_indexed_int";
			case "clinicSort":
				return "clinicSort_indexed_int";
			case "patientFirstName":
				return "patientFirstName_indexed_string";
			case "patientFirstNamePreferred":
				return "patientFirstNamePreferred_indexed_string";
			case "patientFamilyName":
				return "patientFamilyName_indexed_string";
			case "patientCompleteName":
				return "patientCompleteName_indexed_string";
			case "patientCompleteNamePreferred":
				return "patientCompleteNamePreferred_indexed_string";
			case "patientBirthDate":
				return "patientBirthDate_indexed_date";
			case "patientBirthMonth":
				return "patientBirthMonth_indexed_int";
			case "patientBirthDay":
				return "patientBirthDay_indexed_int";
			case "clinicName":
				return "clinicName_indexed_string";
			case "clinicCompleteName":
				return "clinicCompleteName_indexed_string";
			case "clinicLocation":
				return "clinicLocation_indexed_string";
			case "clinicAddress":
				return "clinicAddress_indexed_string";
			case "clinicPhoneNumber":
				return "clinicPhoneNumber_indexed_string";
			case "clinicAdministratorName":
				return "clinicAdministratorName_indexed_string";
			case "enrollmentApproved":
				return "enrollmentApproved_indexed_boolean";
			case "enrollmentImmunizations":
				return "enrollmentImmunizations_indexed_boolean";
			case "familyAddress":
				return "familyAddress_indexed_string";
			case "familyHowDoYouKnowTheClinic":
				return "familyHowDoYouKnowTheClinic_indexed_string";
			case "enrollmentSpecialConsiderations":
				return "enrollmentSpecialConsiderations_indexed_string";
			case "patientMedicalConditions":
				return "patientMedicalConditions_indexed_string";
			case "patientPreviousClinicsAttended":
				return "patientPreviousClinicsAttended_indexed_string";
			case "patientDescription":
				return "patientDescription_indexed_string";
			case "patientObjectives":
				return "patientObjectives_indexed_string";
			case "customerProfileId":
				return "customerProfileId_indexed_string";
			case "createdYear":
				return "createdYear_indexed_int";
			case "createdDayOfWeek":
				return "createdDayOfWeek_indexed_string";
			case "createdMonthOfYear":
				return "createdMonthOfYear_indexed_string";
			case "createdHourOfDay":
				return "createdHourOfDay_indexed_string";
			case "enrollmentDate1":
				return "enrollmentDate1_indexed_date";
			case "enrollmentDate2":
				return "enrollmentDate2_indexed_date";
			case "enrollmentDate3":
				return "enrollmentDate3_indexed_date";
			case "enrollmentDate4":
				return "enrollmentDate4_indexed_date";
			case "enrollmentDate5":
				return "enrollmentDate5_indexed_date";
			case "enrollmentDate6":
				return "enrollmentDate6_indexed_date";
			case "enrollmentDate7":
				return "enrollmentDate7_indexed_date";
			case "enrollmentDate8":
				return "enrollmentDate8_indexed_date";
			case "enrollmentDate9":
				return "enrollmentDate9_indexed_date";
			case "enrollmentDate10":
				return "enrollmentDate10_indexed_date";
			case "enrollmentCompleteName":
				return "enrollmentCompleteName_indexed_string";
			default:
				return Cluster.varIndexedCluster(entityVar);
		}
	}

	public static String varSearchMedicalEnrollment(String entityVar) {
		switch(entityVar) {
			default:
				return Cluster.varSearchCluster(entityVar);
		}
	}

	public static String varSuggestedMedicalEnrollment(String entityVar) {
		switch(entityVar) {
			default:
				return Cluster.varSuggestedCluster(entityVar);
		}
	}

	/////////////
	// store //
	/////////////

	@Override public void storeForClass(SolrDocument solrDocument) {
		storeMedicalEnrollment(solrDocument);
	}
	public void storeMedicalEnrollment(SolrDocument solrDocument) {
		MedicalEnrollment oMedicalEnrollment = (MedicalEnrollment)this;

		Long enrollmentKey = (Long)solrDocument.get("enrollmentKey_stored_long");
		if(enrollmentKey != null)
			oMedicalEnrollment.setEnrollmentKey(enrollmentKey);

		Long clinicKey = (Long)solrDocument.get("clinicKey_stored_long");
		if(clinicKey != null)
			oMedicalEnrollment.setClinicKey(clinicKey);

		Long patientKey = (Long)solrDocument.get("patientKey_stored_long");
		if(patientKey != null)
			oMedicalEnrollment.setPatientKey(patientKey);

		Long enrollmentFormKey = (Long)solrDocument.get("enrollmentFormKey_stored_long");
		if(enrollmentFormKey != null)
			oMedicalEnrollment.setEnrollmentFormKey(enrollmentFormKey);

		List<Long> userKeys = (List<Long>)solrDocument.get("userKeys_stored_longs");
		if(userKeys != null)
			oMedicalEnrollment.userKeys.addAll(userKeys);

		Integer medicalSort = (Integer)solrDocument.get("medicalSort_stored_int");
		if(medicalSort != null)
			oMedicalEnrollment.setMedicalSort(medicalSort);

		Integer clinicSort = (Integer)solrDocument.get("clinicSort_stored_int");
		if(clinicSort != null)
			oMedicalEnrollment.setClinicSort(clinicSort);

		String patientFirstName = (String)solrDocument.get("patientFirstName_stored_string");
		if(patientFirstName != null)
			oMedicalEnrollment.setPatientFirstName(patientFirstName);

		String patientFirstNamePreferred = (String)solrDocument.get("patientFirstNamePreferred_stored_string");
		if(patientFirstNamePreferred != null)
			oMedicalEnrollment.setPatientFirstNamePreferred(patientFirstNamePreferred);

		String patientFamilyName = (String)solrDocument.get("patientFamilyName_stored_string");
		if(patientFamilyName != null)
			oMedicalEnrollment.setPatientFamilyName(patientFamilyName);

		String patientCompleteName = (String)solrDocument.get("patientCompleteName_stored_string");
		if(patientCompleteName != null)
			oMedicalEnrollment.setPatientCompleteName(patientCompleteName);

		String patientCompleteNamePreferred = (String)solrDocument.get("patientCompleteNamePreferred_stored_string");
		if(patientCompleteNamePreferred != null)
			oMedicalEnrollment.setPatientCompleteNamePreferred(patientCompleteNamePreferred);

		Date patientBirthDate = (Date)solrDocument.get("patientBirthDate_stored_date");
		if(patientBirthDate != null)
			oMedicalEnrollment.setPatientBirthDate(patientBirthDate);

		Integer patientBirthMonth = (Integer)solrDocument.get("patientBirthMonth_stored_int");
		if(patientBirthMonth != null)
			oMedicalEnrollment.setPatientBirthMonth(patientBirthMonth);

		Integer patientBirthDay = (Integer)solrDocument.get("patientBirthDay_stored_int");
		if(patientBirthDay != null)
			oMedicalEnrollment.setPatientBirthDay(patientBirthDay);

		String clinicName = (String)solrDocument.get("clinicName_stored_string");
		if(clinicName != null)
			oMedicalEnrollment.setClinicName(clinicName);

		String clinicCompleteName = (String)solrDocument.get("clinicCompleteName_stored_string");
		if(clinicCompleteName != null)
			oMedicalEnrollment.setClinicCompleteName(clinicCompleteName);

		String clinicLocation = (String)solrDocument.get("clinicLocation_stored_string");
		if(clinicLocation != null)
			oMedicalEnrollment.setClinicLocation(clinicLocation);

		String clinicAddress = (String)solrDocument.get("clinicAddress_stored_string");
		if(clinicAddress != null)
			oMedicalEnrollment.setClinicAddress(clinicAddress);

		String clinicPhoneNumber = (String)solrDocument.get("clinicPhoneNumber_stored_string");
		if(clinicPhoneNumber != null)
			oMedicalEnrollment.setClinicPhoneNumber(clinicPhoneNumber);

		String clinicAdministratorName = (String)solrDocument.get("clinicAdministratorName_stored_string");
		if(clinicAdministratorName != null)
			oMedicalEnrollment.setClinicAdministratorName(clinicAdministratorName);

		Boolean enrollmentApproved = (Boolean)solrDocument.get("enrollmentApproved_stored_boolean");
		if(enrollmentApproved != null)
			oMedicalEnrollment.setEnrollmentApproved(enrollmentApproved);

		Boolean enrollmentImmunizations = (Boolean)solrDocument.get("enrollmentImmunizations_stored_boolean");
		if(enrollmentImmunizations != null)
			oMedicalEnrollment.setEnrollmentImmunizations(enrollmentImmunizations);

		String familyAddress = (String)solrDocument.get("familyAddress_stored_string");
		if(familyAddress != null)
			oMedicalEnrollment.setFamilyAddress(familyAddress);

		String familyHowDoYouKnowTheClinic = (String)solrDocument.get("familyHowDoYouKnowTheClinic_stored_string");
		if(familyHowDoYouKnowTheClinic != null)
			oMedicalEnrollment.setFamilyHowDoYouKnowTheClinic(familyHowDoYouKnowTheClinic);

		String enrollmentSpecialConsiderations = (String)solrDocument.get("enrollmentSpecialConsiderations_stored_string");
		if(enrollmentSpecialConsiderations != null)
			oMedicalEnrollment.setEnrollmentSpecialConsiderations(enrollmentSpecialConsiderations);

		String patientMedicalConditions = (String)solrDocument.get("patientMedicalConditions_stored_string");
		if(patientMedicalConditions != null)
			oMedicalEnrollment.setPatientMedicalConditions(patientMedicalConditions);

		String patientPreviousClinicsAttended = (String)solrDocument.get("patientPreviousClinicsAttended_stored_string");
		if(patientPreviousClinicsAttended != null)
			oMedicalEnrollment.setPatientPreviousClinicsAttended(patientPreviousClinicsAttended);

		String patientDescription = (String)solrDocument.get("patientDescription_stored_string");
		if(patientDescription != null)
			oMedicalEnrollment.setPatientDescription(patientDescription);

		String patientObjectives = (String)solrDocument.get("patientObjectives_stored_string");
		if(patientObjectives != null)
			oMedicalEnrollment.setPatientObjectives(patientObjectives);

		String customerProfileId = (String)solrDocument.get("customerProfileId_stored_string");
		if(customerProfileId != null)
			oMedicalEnrollment.setCustomerProfileId(customerProfileId);

		Integer createdYear = (Integer)solrDocument.get("createdYear_stored_int");
		if(createdYear != null)
			oMedicalEnrollment.setCreatedYear(createdYear);

		String createdDayOfWeek = (String)solrDocument.get("createdDayOfWeek_stored_string");
		if(createdDayOfWeek != null)
			oMedicalEnrollment.setCreatedDayOfWeek(createdDayOfWeek);

		String createdMonthOfYear = (String)solrDocument.get("createdMonthOfYear_stored_string");
		if(createdMonthOfYear != null)
			oMedicalEnrollment.setCreatedMonthOfYear(createdMonthOfYear);

		String createdHourOfDay = (String)solrDocument.get("createdHourOfDay_stored_string");
		if(createdHourOfDay != null)
			oMedicalEnrollment.setCreatedHourOfDay(createdHourOfDay);

		String enrollmentSignature1 = (String)solrDocument.get("enrollmentSignature1_stored_string");
		if(enrollmentSignature1 != null)
			oMedicalEnrollment.setEnrollmentSignature1(enrollmentSignature1);

		String enrollmentSignature2 = (String)solrDocument.get("enrollmentSignature2_stored_string");
		if(enrollmentSignature2 != null)
			oMedicalEnrollment.setEnrollmentSignature2(enrollmentSignature2);

		String enrollmentSignature3 = (String)solrDocument.get("enrollmentSignature3_stored_string");
		if(enrollmentSignature3 != null)
			oMedicalEnrollment.setEnrollmentSignature3(enrollmentSignature3);

		String enrollmentSignature4 = (String)solrDocument.get("enrollmentSignature4_stored_string");
		if(enrollmentSignature4 != null)
			oMedicalEnrollment.setEnrollmentSignature4(enrollmentSignature4);

		String enrollmentSignature5 = (String)solrDocument.get("enrollmentSignature5_stored_string");
		if(enrollmentSignature5 != null)
			oMedicalEnrollment.setEnrollmentSignature5(enrollmentSignature5);

		String enrollmentSignature6 = (String)solrDocument.get("enrollmentSignature6_stored_string");
		if(enrollmentSignature6 != null)
			oMedicalEnrollment.setEnrollmentSignature6(enrollmentSignature6);

		String enrollmentSignature7 = (String)solrDocument.get("enrollmentSignature7_stored_string");
		if(enrollmentSignature7 != null)
			oMedicalEnrollment.setEnrollmentSignature7(enrollmentSignature7);

		String enrollmentSignature8 = (String)solrDocument.get("enrollmentSignature8_stored_string");
		if(enrollmentSignature8 != null)
			oMedicalEnrollment.setEnrollmentSignature8(enrollmentSignature8);

		String enrollmentSignature9 = (String)solrDocument.get("enrollmentSignature9_stored_string");
		if(enrollmentSignature9 != null)
			oMedicalEnrollment.setEnrollmentSignature9(enrollmentSignature9);

		String enrollmentSignature10 = (String)solrDocument.get("enrollmentSignature10_stored_string");
		if(enrollmentSignature10 != null)
			oMedicalEnrollment.setEnrollmentSignature10(enrollmentSignature10);

		Date enrollmentDate1 = (Date)solrDocument.get("enrollmentDate1_stored_date");
		if(enrollmentDate1 != null)
			oMedicalEnrollment.setEnrollmentDate1(enrollmentDate1);

		Date enrollmentDate2 = (Date)solrDocument.get("enrollmentDate2_stored_date");
		if(enrollmentDate2 != null)
			oMedicalEnrollment.setEnrollmentDate2(enrollmentDate2);

		Date enrollmentDate3 = (Date)solrDocument.get("enrollmentDate3_stored_date");
		if(enrollmentDate3 != null)
			oMedicalEnrollment.setEnrollmentDate3(enrollmentDate3);

		Date enrollmentDate4 = (Date)solrDocument.get("enrollmentDate4_stored_date");
		if(enrollmentDate4 != null)
			oMedicalEnrollment.setEnrollmentDate4(enrollmentDate4);

		Date enrollmentDate5 = (Date)solrDocument.get("enrollmentDate5_stored_date");
		if(enrollmentDate5 != null)
			oMedicalEnrollment.setEnrollmentDate5(enrollmentDate5);

		Date enrollmentDate6 = (Date)solrDocument.get("enrollmentDate6_stored_date");
		if(enrollmentDate6 != null)
			oMedicalEnrollment.setEnrollmentDate6(enrollmentDate6);

		Date enrollmentDate7 = (Date)solrDocument.get("enrollmentDate7_stored_date");
		if(enrollmentDate7 != null)
			oMedicalEnrollment.setEnrollmentDate7(enrollmentDate7);

		Date enrollmentDate8 = (Date)solrDocument.get("enrollmentDate8_stored_date");
		if(enrollmentDate8 != null)
			oMedicalEnrollment.setEnrollmentDate8(enrollmentDate8);

		Date enrollmentDate9 = (Date)solrDocument.get("enrollmentDate9_stored_date");
		if(enrollmentDate9 != null)
			oMedicalEnrollment.setEnrollmentDate9(enrollmentDate9);

		Date enrollmentDate10 = (Date)solrDocument.get("enrollmentDate10_stored_date");
		if(enrollmentDate10 != null)
			oMedicalEnrollment.setEnrollmentDate10(enrollmentDate10);

		String enrollmentCompleteName = (String)solrDocument.get("enrollmentCompleteName_stored_string");
		if(enrollmentCompleteName != null)
			oMedicalEnrollment.setEnrollmentCompleteName(enrollmentCompleteName);

		super.storeCluster(solrDocument);
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestMedicalEnrollment() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof MedicalEnrollment) {
			MedicalEnrollment original = (MedicalEnrollment)o;
			if(!Objects.equals(patientKey, original.getPatientKey()))
				apiRequest.addVars("patientKey");
			if(!Objects.equals(userKeys, original.getUserKeys()))
				apiRequest.addVars("userKeys");
			if(!Objects.equals(patientCompleteName, original.getPatientCompleteName()))
				apiRequest.addVars("patientCompleteName");
			if(!Objects.equals(patientCompleteNamePreferred, original.getPatientCompleteNamePreferred()))
				apiRequest.addVars("patientCompleteNamePreferred");
			if(!Objects.equals(patientBirthDate, original.getPatientBirthDate()))
				apiRequest.addVars("patientBirthDate");
			if(!Objects.equals(clinicAddress, original.getClinicAddress()))
				apiRequest.addVars("clinicAddress");
			if(!Objects.equals(enrollmentApproved, original.getEnrollmentApproved()))
				apiRequest.addVars("enrollmentApproved");
			if(!Objects.equals(enrollmentImmunizations, original.getEnrollmentImmunizations()))
				apiRequest.addVars("enrollmentImmunizations");
			if(!Objects.equals(familyAddress, original.getFamilyAddress()))
				apiRequest.addVars("familyAddress");
			if(!Objects.equals(familyHowDoYouKnowTheClinic, original.getFamilyHowDoYouKnowTheClinic()))
				apiRequest.addVars("familyHowDoYouKnowTheClinic");
			if(!Objects.equals(enrollmentSpecialConsiderations, original.getEnrollmentSpecialConsiderations()))
				apiRequest.addVars("enrollmentSpecialConsiderations");
			if(!Objects.equals(patientMedicalConditions, original.getPatientMedicalConditions()))
				apiRequest.addVars("patientMedicalConditions");
			if(!Objects.equals(patientPreviousClinicsAttended, original.getPatientPreviousClinicsAttended()))
				apiRequest.addVars("patientPreviousClinicsAttended");
			if(!Objects.equals(patientDescription, original.getPatientDescription()))
				apiRequest.addVars("patientDescription");
			if(!Objects.equals(patientObjectives, original.getPatientObjectives()))
				apiRequest.addVars("patientObjectives");
			if(!Objects.equals(customerProfileId, original.getCustomerProfileId()))
				apiRequest.addVars("customerProfileId");
			if(!Objects.equals(enrollmentSignature1, original.getEnrollmentSignature1()))
				apiRequest.addVars("enrollmentSignature1");
			if(!Objects.equals(enrollmentSignature2, original.getEnrollmentSignature2()))
				apiRequest.addVars("enrollmentSignature2");
			if(!Objects.equals(enrollmentSignature3, original.getEnrollmentSignature3()))
				apiRequest.addVars("enrollmentSignature3");
			if(!Objects.equals(enrollmentSignature4, original.getEnrollmentSignature4()))
				apiRequest.addVars("enrollmentSignature4");
			if(!Objects.equals(enrollmentSignature5, original.getEnrollmentSignature5()))
				apiRequest.addVars("enrollmentSignature5");
			if(!Objects.equals(enrollmentSignature6, original.getEnrollmentSignature6()))
				apiRequest.addVars("enrollmentSignature6");
			if(!Objects.equals(enrollmentSignature7, original.getEnrollmentSignature7()))
				apiRequest.addVars("enrollmentSignature7");
			if(!Objects.equals(enrollmentSignature8, original.getEnrollmentSignature8()))
				apiRequest.addVars("enrollmentSignature8");
			if(!Objects.equals(enrollmentSignature9, original.getEnrollmentSignature9()))
				apiRequest.addVars("enrollmentSignature9");
			if(!Objects.equals(enrollmentSignature10, original.getEnrollmentSignature10()))
				apiRequest.addVars("enrollmentSignature10");
			if(!Objects.equals(enrollmentDate1, original.getEnrollmentDate1()))
				apiRequest.addVars("enrollmentDate1");
			if(!Objects.equals(enrollmentDate2, original.getEnrollmentDate2()))
				apiRequest.addVars("enrollmentDate2");
			if(!Objects.equals(enrollmentDate3, original.getEnrollmentDate3()))
				apiRequest.addVars("enrollmentDate3");
			if(!Objects.equals(enrollmentDate4, original.getEnrollmentDate4()))
				apiRequest.addVars("enrollmentDate4");
			if(!Objects.equals(enrollmentDate5, original.getEnrollmentDate5()))
				apiRequest.addVars("enrollmentDate5");
			if(!Objects.equals(enrollmentDate6, original.getEnrollmentDate6()))
				apiRequest.addVars("enrollmentDate6");
			if(!Objects.equals(enrollmentDate7, original.getEnrollmentDate7()))
				apiRequest.addVars("enrollmentDate7");
			if(!Objects.equals(enrollmentDate8, original.getEnrollmentDate8()))
				apiRequest.addVars("enrollmentDate8");
			if(!Objects.equals(enrollmentDate9, original.getEnrollmentDate9()))
				apiRequest.addVars("enrollmentDate9");
			if(!Objects.equals(enrollmentDate10, original.getEnrollmentDate10()))
				apiRequest.addVars("enrollmentDate10");
			super.apiRequestCluster();
		}
	}

	//////////////
	// hashCode //
	//////////////

	@Override public int hashCode() {
		return Objects.hash(super.hashCode(), patientKey, userKeys, patientCompleteName, patientCompleteNamePreferred, patientBirthDate, clinicAddress, enrollmentApproved, enrollmentImmunizations, familyAddress, familyHowDoYouKnowTheClinic, enrollmentSpecialConsiderations, patientMedicalConditions, patientPreviousClinicsAttended, patientDescription, patientObjectives, customerProfileId, enrollmentSignature1, enrollmentSignature2, enrollmentSignature3, enrollmentSignature4, enrollmentSignature5, enrollmentSignature6, enrollmentSignature7, enrollmentSignature8, enrollmentSignature9, enrollmentSignature10, enrollmentDate1, enrollmentDate2, enrollmentDate3, enrollmentDate4, enrollmentDate5, enrollmentDate6, enrollmentDate7, enrollmentDate8, enrollmentDate9, enrollmentDate10);
	}

	////////////
	// equals //
	////////////

	@Override public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof MedicalEnrollment))
			return false;
		MedicalEnrollment that = (MedicalEnrollment)o;
		return super.equals(o)
				&& Objects.equals( patientKey, that.patientKey )
				&& Objects.equals( userKeys, that.userKeys )
				&& Objects.equals( patientCompleteName, that.patientCompleteName )
				&& Objects.equals( patientCompleteNamePreferred, that.patientCompleteNamePreferred )
				&& Objects.equals( patientBirthDate, that.patientBirthDate )
				&& Objects.equals( clinicAddress, that.clinicAddress )
				&& Objects.equals( enrollmentApproved, that.enrollmentApproved )
				&& Objects.equals( enrollmentImmunizations, that.enrollmentImmunizations )
				&& Objects.equals( familyAddress, that.familyAddress )
				&& Objects.equals( familyHowDoYouKnowTheClinic, that.familyHowDoYouKnowTheClinic )
				&& Objects.equals( enrollmentSpecialConsiderations, that.enrollmentSpecialConsiderations )
				&& Objects.equals( patientMedicalConditions, that.patientMedicalConditions )
				&& Objects.equals( patientPreviousClinicsAttended, that.patientPreviousClinicsAttended )
				&& Objects.equals( patientDescription, that.patientDescription )
				&& Objects.equals( patientObjectives, that.patientObjectives )
				&& Objects.equals( customerProfileId, that.customerProfileId )
				&& Objects.equals( enrollmentSignature1, that.enrollmentSignature1 )
				&& Objects.equals( enrollmentSignature2, that.enrollmentSignature2 )
				&& Objects.equals( enrollmentSignature3, that.enrollmentSignature3 )
				&& Objects.equals( enrollmentSignature4, that.enrollmentSignature4 )
				&& Objects.equals( enrollmentSignature5, that.enrollmentSignature5 )
				&& Objects.equals( enrollmentSignature6, that.enrollmentSignature6 )
				&& Objects.equals( enrollmentSignature7, that.enrollmentSignature7 )
				&& Objects.equals( enrollmentSignature8, that.enrollmentSignature8 )
				&& Objects.equals( enrollmentSignature9, that.enrollmentSignature9 )
				&& Objects.equals( enrollmentSignature10, that.enrollmentSignature10 )
				&& Objects.equals( enrollmentDate1, that.enrollmentDate1 )
				&& Objects.equals( enrollmentDate2, that.enrollmentDate2 )
				&& Objects.equals( enrollmentDate3, that.enrollmentDate3 )
				&& Objects.equals( enrollmentDate4, that.enrollmentDate4 )
				&& Objects.equals( enrollmentDate5, that.enrollmentDate5 )
				&& Objects.equals( enrollmentDate6, that.enrollmentDate6 )
				&& Objects.equals( enrollmentDate7, that.enrollmentDate7 )
				&& Objects.equals( enrollmentDate8, that.enrollmentDate8 )
				&& Objects.equals( enrollmentDate9, that.enrollmentDate9 )
				&& Objects.equals( enrollmentDate10, that.enrollmentDate10 );
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("MedicalEnrollment { ");
		sb.append( "patientKey: " ).append(patientKey);
		sb.append( ", userKeys: " ).append(userKeys);
		sb.append( ", patientCompleteName: \"" ).append(patientCompleteName).append( "\"" );
		sb.append( ", patientCompleteNamePreferred: \"" ).append(patientCompleteNamePreferred).append( "\"" );
		sb.append( ", patientBirthDate: " ).append(patientBirthDate);
		sb.append( ", clinicAddress: \"" ).append(clinicAddress).append( "\"" );
		sb.append( ", enrollmentApproved: " ).append(enrollmentApproved);
		sb.append( ", enrollmentImmunizations: " ).append(enrollmentImmunizations);
		sb.append( ", familyAddress: \"" ).append(familyAddress).append( "\"" );
		sb.append( ", familyHowDoYouKnowTheClinic: \"" ).append(familyHowDoYouKnowTheClinic).append( "\"" );
		sb.append( ", enrollmentSpecialConsiderations: \"" ).append(enrollmentSpecialConsiderations).append( "\"" );
		sb.append( ", patientMedicalConditions: \"" ).append(patientMedicalConditions).append( "\"" );
		sb.append( ", patientPreviousClinicsAttended: \"" ).append(patientPreviousClinicsAttended).append( "\"" );
		sb.append( ", patientDescription: \"" ).append(patientDescription).append( "\"" );
		sb.append( ", patientObjectives: \"" ).append(patientObjectives).append( "\"" );
		sb.append( ", customerProfileId: \"" ).append(customerProfileId).append( "\"" );
		sb.append( ", enrollmentSignature1: \"" ).append(enrollmentSignature1).append( "\"" );
		sb.append( ", enrollmentSignature2: \"" ).append(enrollmentSignature2).append( "\"" );
		sb.append( ", enrollmentSignature3: \"" ).append(enrollmentSignature3).append( "\"" );
		sb.append( ", enrollmentSignature4: \"" ).append(enrollmentSignature4).append( "\"" );
		sb.append( ", enrollmentSignature5: \"" ).append(enrollmentSignature5).append( "\"" );
		sb.append( ", enrollmentSignature6: \"" ).append(enrollmentSignature6).append( "\"" );
		sb.append( ", enrollmentSignature7: \"" ).append(enrollmentSignature7).append( "\"" );
		sb.append( ", enrollmentSignature8: \"" ).append(enrollmentSignature8).append( "\"" );
		sb.append( ", enrollmentSignature9: \"" ).append(enrollmentSignature9).append( "\"" );
		sb.append( ", enrollmentSignature10: \"" ).append(enrollmentSignature10).append( "\"" );
		sb.append( ", enrollmentDate1: " ).append(enrollmentDate1);
		sb.append( ", enrollmentDate2: " ).append(enrollmentDate2);
		sb.append( ", enrollmentDate3: " ).append(enrollmentDate3);
		sb.append( ", enrollmentDate4: " ).append(enrollmentDate4);
		sb.append( ", enrollmentDate5: " ).append(enrollmentDate5);
		sb.append( ", enrollmentDate6: " ).append(enrollmentDate6);
		sb.append( ", enrollmentDate7: " ).append(enrollmentDate7);
		sb.append( ", enrollmentDate8: " ).append(enrollmentDate8);
		sb.append( ", enrollmentDate9: " ).append(enrollmentDate9);
		sb.append( ", enrollmentDate10: " ).append(enrollmentDate10);
		sb.append(" }");
		return sb.toString();
	}
}
