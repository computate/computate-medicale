package org.computate.medicale.enUS.patient;

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
 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.patient.MedicalPatient&fq=classeEtendGen_indexed_boolean:true">Trouver la classe  dans Solr</a>
 * <br/>
 **/
public abstract class MedicalPatientGen<DEV> extends Cluster {
	protected static final Logger LOGGER = LoggerFactory.getLogger(MedicalPatient.class);

	public static final List<String> ROLES = Arrays.asList("SiteAdmin");
	public static final List<String> ROLE_READS = Arrays.asList("");

	public static final String MedicalPatient_AName = "a patient";
	public static final String MedicalPatient_This = "this ";
	public static final String MedicalPatient_ThisName = "this patient";
	public static final String MedicalPatient_A = "a ";
	public static final String MedicalPatient_TheName = "the patient";
	public static final String MedicalPatient_NameSingular = "patient";
	public static final String MedicalPatient_NamePlural = "patients";
	public static final String MedicalPatient_NameActual = "current patient";
	public static final String MedicalPatient_AllName = "all the patients";
	public static final String MedicalPatient_SearchAllNameBy = "search patients by ";
	public static final String MedicalPatient_ThePluralName = "the patients";
	public static final String MedicalPatient_NoNameFound = "no patient found";
	public static final String MedicalPatient_NameVar = "patient";
	public static final String MedicalPatient_OfName = "of patient";
	public static final String MedicalPatient_ANameAdjective = "a patient";
	public static final String MedicalPatient_NameAdjectiveSingular = "patient";
	public static final String MedicalPatient_NameAdjectivePlural = "patients";
	public static final String MedicalPatient_Color = "orange";
	public static final String MedicalPatient_IconGroup = "regular";
	public static final String MedicalPatient_IconName = "patient";

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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.patient.MedicalPatient&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:patientKey">Trouver l'entité patientKey dans Solr</a>
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
	public MedicalPatient setPatientKey(String o) {
		if(NumberUtils.isParsable(o))
			this.patientKey = Long.parseLong(o);
		this.patientKeyWrap.alreadyInitialized = true;
		return (MedicalPatient)this;
	}
	protected MedicalPatient patientKeyInit() {
		if(!patientKeyWrap.alreadyInitialized) {
			_patientKey(patientKeyWrap);
			if(patientKey == null)
				setPatientKey(patientKeyWrap.o);
		}
		patientKeyWrap.alreadyInitialized(true);
		return (MedicalPatient)this;
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
		return "key";
	}

	public String htmTooltipPatientKey() {
		return null;
	}

	public String htmPatientKey() {
		return patientKey == null ? "" : StringEscapeUtils.escapeHtml4(strPatientKey());
	}

	////////////////////
	// enrollmentKeys //
	////////////////////

	/**	L'entité « enrollmentKeys »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> enrollmentKeys = new java.util.ArrayList<java.lang.Long>();
	@JsonIgnore
	public Wrap<List<Long>> enrollmentKeysWrap = new Wrap<List<Long>>().p(this).c(List.class).var("enrollmentKeys").o(enrollmentKeys);

	/**	<br/>L'entité « enrollmentKeys »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.patient.MedicalPatient&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentKeys">Trouver l'entité enrollmentKeys dans Solr</a>
	 * <br/>
	 * @param enrollmentKeys est l'entité déjà construit. 
	 **/
	protected abstract void _enrollmentKeys(List<Long> o);

	public List<Long> getEnrollmentKeys() {
		return enrollmentKeys;
	}

	public void setEnrollmentKeys(List<Long> enrollmentKeys) {
		this.enrollmentKeys = enrollmentKeys;
		this.enrollmentKeysWrap.alreadyInitialized = true;
	}
	public MedicalPatient addEnrollmentKeys(Long...objets) {
		for(Long o : objets) {
			addEnrollmentKeys(o);
		}
		return (MedicalPatient)this;
	}
	public MedicalPatient addEnrollmentKeys(Long o) {
		if(o != null && !enrollmentKeys.contains(o))
			this.enrollmentKeys.add(o);
		return (MedicalPatient)this;
	}
	public MedicalPatient setEnrollmentKeys(JsonArray objets) {
		enrollmentKeys.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addEnrollmentKeys(o);
		}
		return (MedicalPatient)this;
	}
	public MedicalPatient addEnrollmentKeys(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addEnrollmentKeys(p);
		}
		return (MedicalPatient)this;
	}
	protected MedicalPatient enrollmentKeysInit() {
		if(!enrollmentKeysWrap.alreadyInitialized) {
			_enrollmentKeys(enrollmentKeys);
		}
		enrollmentKeysWrap.alreadyInitialized(true);
		return (MedicalPatient)this;
	}

	public List<Long> solrEnrollmentKeys() {
		return enrollmentKeys;
	}

	public String strEnrollmentKeys() {
		return enrollmentKeys == null ? "" : enrollmentKeys.toString();
	}

	public String jsonEnrollmentKeys() {
		return enrollmentKeys == null ? "" : enrollmentKeys.toString();
	}

	public String nomAffichageEnrollmentKeys() {
		return "enrollments";
	}

	public String htmTooltipEnrollmentKeys() {
		return null;
	}

	public String htmEnrollmentKeys() {
		return enrollmentKeys == null ? "" : StringEscapeUtils.escapeHtml4(strEnrollmentKeys());
	}

	public void inputEnrollmentKeys(String classApiMethodMethod) {
		MedicalPatient s = (MedicalPatient)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("i").a("class", "far fa-search w3-xxlarge w3-cell w3-cell-middle ").f().g("i");
				e("input")
					.a("type", "text")
					.a("placeholder", "enrollments")
					.a("class", "valueObjectSuggest suggestEnrollmentKeys w3-input w3-border w3-cell w3-cell-middle ")
					.a("name", "setEnrollmentKeys")
					.a("id", classApiMethodMethod, "_enrollmentKeys")
					.a("autocomplete", "off")
					.a("oninput", "suggestMedicalPatientEnrollmentKeys($(this).val() ? searchMedicalEnrollmentFilters($(this.parentElement)) : [", pk == null ? "" : "{'name':'fq','value':'patientKey:" + pk + "'}", "], $('#listMedicalPatientEnrollmentKeys_", classApiMethodMethod, "'), ", pk, "); ")
				.fg();

		} else {
			sx(htmEnrollmentKeys());
		}
	}

	public void htmEnrollmentKeys(String classApiMethodMethod) {
		MedicalPatient s = (MedicalPatient)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalPatientEnrollmentKeys").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row ").f();
							{ e("a").a("href", "/enrollment?fq=patientKey:", pk).a("class", "w3-cell w3-btn w3-center h4 w3-block h4 w3-blue-gray w3-hover-blue-gray ").f();
								e("i").a("class", "fas fa-edit ").f().g("i");
								sx("enrollments");
							} g("a");
						} g("div");
						{ e("div").a("class", "w3-cell-row ").f();
							{ e("h5").a("class", "w3-cell ").f();
								sx("relate enrollments to this patient");
							} g("h5");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();
								{ e("div").a("class", "w3-cell-row ").f();

								inputEnrollmentKeys(classApiMethodMethod);
								} g("div");
							} g("div");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
								{ e("ul").a("class", "w3-ul w3-hoverable ").a("id", "listMedicalPatientEnrollmentKeys_", classApiMethodMethod).f();
								} g("ul");
								if(
										userKeys.contains(siteRequest_.getUserKey())
										|| Objects.equals(sessionId, siteRequest_.getSessionId())
										|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
										|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
								) {
									{ e("div").a("class", "w3-cell-row ").f();
										e("button")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-blue-gray ")
											.a("id", classApiMethodMethod, "_enrollmentKeys_add")
											.a("onclick", "$(this).addClass('w3-disabled'); this.disabled = true; this.innerHTML = 'Sending…'; postMedicalEnrollmentVals({ patientKey: \"", pk, "\" }, function() {}, function() { addError($('#", classApiMethodMethod, "enrollmentKeys')); });")
											.f().sx("add an enrollment")
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
	// schoolSort //
	////////////////

	/**	L'entité « schoolSort »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer schoolSort;
	@JsonIgnore
	public Wrap<Integer> schoolSortWrap = new Wrap<Integer>().p(this).c(Integer.class).var("schoolSort").o(schoolSort);

	/**	<br/>L'entité « schoolSort »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.patient.MedicalPatient&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:schoolSort">Trouver l'entité schoolSort dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _schoolSort(Wrap<Integer> c);

	public Integer getSchoolSort() {
		return schoolSort;
	}

	public void setSchoolSort(Integer schoolSort) {
		this.schoolSort = schoolSort;
		this.schoolSortWrap.alreadyInitialized = true;
	}
	public MedicalPatient setSchoolSort(String o) {
		if(NumberUtils.isParsable(o))
			this.schoolSort = Integer.parseInt(o);
		this.schoolSortWrap.alreadyInitialized = true;
		return (MedicalPatient)this;
	}
	protected MedicalPatient schoolSortInit() {
		if(!schoolSortWrap.alreadyInitialized) {
			_schoolSort(schoolSortWrap);
			if(schoolSort == null)
				setSchoolSort(schoolSortWrap.o);
		}
		schoolSortWrap.alreadyInitialized(true);
		return (MedicalPatient)this;
	}

	public Integer solrSchoolSort() {
		return schoolSort;
	}

	public String strSchoolSort() {
		return schoolSort == null ? "" : schoolSort.toString();
	}

	public String jsonSchoolSort() {
		return schoolSort == null ? "" : schoolSort.toString();
	}

	public String nomAffichageSchoolSort() {
		return null;
	}

	public String htmTooltipSchoolSort() {
		return null;
	}

	public String htmSchoolSort() {
		return schoolSort == null ? "" : StringEscapeUtils.escapeHtml4(strSchoolSort());
	}

	//////////////////////
	// enrollmentSearch //
	//////////////////////

	/**	L'entité « enrollmentSearch »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut SearchList<MedicalEnrollment>(). 
	 */
	@JsonIgnore
	@JsonInclude(Include.NON_NULL)
	protected SearchList<MedicalEnrollment> enrollmentSearch = new SearchList<MedicalEnrollment>();
	@JsonIgnore
	public Wrap<SearchList<MedicalEnrollment>> enrollmentSearchWrap = new Wrap<SearchList<MedicalEnrollment>>().p(this).c(SearchList.class).var("enrollmentSearch").o(enrollmentSearch);

	/**	<br/>L'entité « enrollmentSearch »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut SearchList<MedicalEnrollment>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.patient.MedicalPatient&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentSearch">Trouver l'entité enrollmentSearch dans Solr</a>
	 * <br/>
	 * @param enrollmentSearch est l'entité déjà construit. 
	 **/
	protected abstract void _enrollmentSearch(SearchList<MedicalEnrollment> l);

	public SearchList<MedicalEnrollment> getEnrollmentSearch() {
		return enrollmentSearch;
	}

	public void setEnrollmentSearch(SearchList<MedicalEnrollment> enrollmentSearch) {
		this.enrollmentSearch = enrollmentSearch;
		this.enrollmentSearchWrap.alreadyInitialized = true;
	}
	protected MedicalPatient enrollmentSearchInit() {
		if(!enrollmentSearchWrap.alreadyInitialized) {
			_enrollmentSearch(enrollmentSearch);
		}
		enrollmentSearch.initDeepForClass(siteRequest_);
		enrollmentSearchWrap.alreadyInitialized(true);
		return (MedicalPatient)this;
	}

	/////////////////
	// enrollments //
	/////////////////

	/**	L'entité « enrollments »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<MedicalEnrollment>(). 
	 */
	@JsonIgnore
	@JsonInclude(Include.NON_NULL)
	protected List<MedicalEnrollment> enrollments = new java.util.ArrayList<org.computate.medicale.enUS.enrollment.MedicalEnrollment>();
	@JsonIgnore
	public Wrap<List<MedicalEnrollment>> enrollmentsWrap = new Wrap<List<MedicalEnrollment>>().p(this).c(List.class).var("enrollments").o(enrollments);

	/**	<br/>L'entité « enrollments »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<MedicalEnrollment>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.patient.MedicalPatient&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollments">Trouver l'entité enrollments dans Solr</a>
	 * <br/>
	 * @param enrollments est l'entité déjà construit. 
	 **/
	protected abstract void _enrollments(List<MedicalEnrollment> l);

	public List<MedicalEnrollment> getEnrollments() {
		return enrollments;
	}

	public void setEnrollments(List<MedicalEnrollment> enrollments) {
		this.enrollments = enrollments;
		this.enrollmentsWrap.alreadyInitialized = true;
	}
	public MedicalPatient addEnrollments(MedicalEnrollment...objets) {
		for(MedicalEnrollment o : objets) {
			addEnrollments(o);
		}
		return (MedicalPatient)this;
	}
	public MedicalPatient addEnrollments(MedicalEnrollment o) {
		if(o != null && !enrollments.contains(o))
			this.enrollments.add(o);
		return (MedicalPatient)this;
	}
	protected MedicalPatient enrollmentsInit() {
		if(!enrollmentsWrap.alreadyInitialized) {
			_enrollments(enrollments);
		}
		enrollmentsWrap.alreadyInitialized(true);
		return (MedicalPatient)this;
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.patient.MedicalPatient&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:userKeys">Trouver l'entité userKeys dans Solr</a>
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
	public MedicalPatient addUserKeys(Long...objets) {
		for(Long o : objets) {
			addUserKeys(o);
		}
		return (MedicalPatient)this;
	}
	public MedicalPatient addUserKeys(Long o) {
		if(o != null && !userKeys.contains(o))
			this.userKeys.add(o);
		return (MedicalPatient)this;
	}
	public MedicalPatient setUserKeys(JsonArray objets) {
		userKeys.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addUserKeys(o);
		}
		return (MedicalPatient)this;
	}
	public MedicalPatient addUserKeys(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addUserKeys(p);
		}
		return (MedicalPatient)this;
	}
	protected MedicalPatient userKeysInit() {
		if(!userKeysWrap.alreadyInitialized) {
			_userKeys(userKeys);
		}
		userKeysWrap.alreadyInitialized(true);
		return (MedicalPatient)this;
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
		return null;
	}

	public String htmTooltipUserKeys() {
		return null;
	}

	public String htmUserKeys() {
		return userKeys == null ? "" : StringEscapeUtils.escapeHtml4(strUserKeys());
	}

	////////////////
	// clinicKeys //
	////////////////

	/**	L'entité « clinicKeys »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> clinicKeys = new java.util.ArrayList<java.lang.Long>();
	@JsonIgnore
	public Wrap<List<Long>> clinicKeysWrap = new Wrap<List<Long>>().p(this).c(List.class).var("clinicKeys").o(clinicKeys);

	/**	<br/>L'entité « clinicKeys »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.patient.MedicalPatient&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinicKeys">Trouver l'entité clinicKeys dans Solr</a>
	 * <br/>
	 * @param clinicKeys est l'entité déjà construit. 
	 **/
	protected abstract void _clinicKeys(List<Long> l);

	public List<Long> getClinicKeys() {
		return clinicKeys;
	}

	public void setClinicKeys(List<Long> clinicKeys) {
		this.clinicKeys = clinicKeys;
		this.clinicKeysWrap.alreadyInitialized = true;
	}
	public MedicalPatient addClinicKeys(Long...objets) {
		for(Long o : objets) {
			addClinicKeys(o);
		}
		return (MedicalPatient)this;
	}
	public MedicalPatient addClinicKeys(Long o) {
		if(o != null && !clinicKeys.contains(o))
			this.clinicKeys.add(o);
		return (MedicalPatient)this;
	}
	public MedicalPatient setClinicKeys(JsonArray objets) {
		clinicKeys.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addClinicKeys(o);
		}
		return (MedicalPatient)this;
	}
	public MedicalPatient addClinicKeys(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addClinicKeys(p);
		}
		return (MedicalPatient)this;
	}
	protected MedicalPatient clinicKeysInit() {
		if(!clinicKeysWrap.alreadyInitialized) {
			_clinicKeys(clinicKeys);
		}
		clinicKeysWrap.alreadyInitialized(true);
		return (MedicalPatient)this;
	}

	public List<Long> solrClinicKeys() {
		return clinicKeys;
	}

	public String strClinicKeys() {
		return clinicKeys == null ? "" : clinicKeys.toString();
	}

	public String jsonClinicKeys() {
		return clinicKeys == null ? "" : clinicKeys.toString();
	}

	public String nomAffichageClinicKeys() {
		return "schools";
	}

	public String htmTooltipClinicKeys() {
		return null;
	}

	public String htmClinicKeys() {
		return clinicKeys == null ? "" : StringEscapeUtils.escapeHtml4(strClinicKeys());
	}

	/////////////////////
	// personFirstName //
	/////////////////////

	/**	L'entité « personFirstName »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String personFirstName;
	@JsonIgnore
	public Wrap<String> personFirstNameWrap = new Wrap<String>().p(this).c(String.class).var("personFirstName").o(personFirstName);

	/**	<br/>L'entité « personFirstName »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.patient.MedicalPatient&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personFirstName">Trouver l'entité personFirstName dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _personFirstName(Wrap<String> c);

	public String getPersonFirstName() {
		return personFirstName;
	}

	public void setPersonFirstName(String personFirstName) {
		this.personFirstName = personFirstName;
		this.personFirstNameWrap.alreadyInitialized = true;
	}
	protected MedicalPatient personFirstNameInit() {
		if(!personFirstNameWrap.alreadyInitialized) {
			_personFirstName(personFirstNameWrap);
			if(personFirstName == null)
				setPersonFirstName(personFirstNameWrap.o);
		}
		personFirstNameWrap.alreadyInitialized(true);
		return (MedicalPatient)this;
	}

	public String solrPersonFirstName() {
		return personFirstName;
	}

	public String strPersonFirstName() {
		return personFirstName == null ? "" : personFirstName;
	}

	public String jsonPersonFirstName() {
		return personFirstName == null ? "" : personFirstName;
	}

	public String nomAffichagePersonFirstName() {
		return "first name";
	}

	public String htmTooltipPersonFirstName() {
		return null;
	}

	public String htmPersonFirstName() {
		return personFirstName == null ? "" : StringEscapeUtils.escapeHtml4(strPersonFirstName());
	}

	public void inputPersonFirstName(String classApiMethodMethod) {
		MedicalPatient s = (MedicalPatient)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("placeholder", "first name")
				.a("id", classApiMethodMethod, "_personFirstName");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setPersonFirstName classMedicalPatient inputMedicalPatient", pk, "PersonFirstName w3-input w3-border ");
					a("name", "setPersonFirstName");
				} else {
					a("class", "valuePersonFirstName w3-input w3-border classMedicalPatient inputMedicalPatient", pk, "PersonFirstName w3-input w3-border ");
					a("name", "personFirstName");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patchMedicalPatientVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setPersonFirstName', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_personFirstName')); }, function() { addError($('#", classApiMethodMethod, "_personFirstName')); }); ");
				}
				a("value", strPersonFirstName())
			.fg();

		} else {
			sx(htmPersonFirstName());
		}
	}

	public void htmPersonFirstName(String classApiMethodMethod) {
		MedicalPatient s = (MedicalPatient)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalPatientPersonFirstName").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-orange ").f();
							e("label").a("for", classApiMethodMethod, "_personFirstName").a("class", "").f().sx("first name").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputPersonFirstName(classApiMethodMethod);
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
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-orange ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_personFirstName')); $('#", classApiMethodMethod, "_personFirstName').val(null); patchMedicalPatientVal([{ name: 'fq', value: 'pk:' + $('#MedicalPatientForm :input[name=pk]').val() }], 'setPersonFirstName', null, function() { addGlow($('#", classApiMethodMethod, "_personFirstName')); }, function() { addError($('#", classApiMethodMethod, "_personFirstName')); }); ")
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
	// personFirstNamePreferred //
	//////////////////////////////

	/**	L'entité « personFirstNamePreferred »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String personFirstNamePreferred;
	@JsonIgnore
	public Wrap<String> personFirstNamePreferredWrap = new Wrap<String>().p(this).c(String.class).var("personFirstNamePreferred").o(personFirstNamePreferred);

	/**	<br/>L'entité « personFirstNamePreferred »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.patient.MedicalPatient&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personFirstNamePreferred">Trouver l'entité personFirstNamePreferred dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _personFirstNamePreferred(Wrap<String> c);

	public String getPersonFirstNamePreferred() {
		return personFirstNamePreferred;
	}

	public void setPersonFirstNamePreferred(String personFirstNamePreferred) {
		this.personFirstNamePreferred = personFirstNamePreferred;
		this.personFirstNamePreferredWrap.alreadyInitialized = true;
	}
	protected MedicalPatient personFirstNamePreferredInit() {
		if(!personFirstNamePreferredWrap.alreadyInitialized) {
			_personFirstNamePreferred(personFirstNamePreferredWrap);
			if(personFirstNamePreferred == null)
				setPersonFirstNamePreferred(personFirstNamePreferredWrap.o);
		}
		personFirstNamePreferredWrap.alreadyInitialized(true);
		return (MedicalPatient)this;
	}

	public String solrPersonFirstNamePreferred() {
		return personFirstNamePreferred;
	}

	public String strPersonFirstNamePreferred() {
		return personFirstNamePreferred == null ? "" : personFirstNamePreferred;
	}

	public String jsonPersonFirstNamePreferred() {
		return personFirstNamePreferred == null ? "" : personFirstNamePreferred;
	}

	public String nomAffichagePersonFirstNamePreferred() {
		return "preferred first name";
	}

	public String htmTooltipPersonFirstNamePreferred() {
		return null;
	}

	public String htmPersonFirstNamePreferred() {
		return personFirstNamePreferred == null ? "" : StringEscapeUtils.escapeHtml4(strPersonFirstNamePreferred());
	}

	public void inputPersonFirstNamePreferred(String classApiMethodMethod) {
		MedicalPatient s = (MedicalPatient)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("placeholder", "preferred first name")
				.a("id", classApiMethodMethod, "_personFirstNamePreferred");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setPersonFirstNamePreferred classMedicalPatient inputMedicalPatient", pk, "PersonFirstNamePreferred w3-input w3-border ");
					a("name", "setPersonFirstNamePreferred");
				} else {
					a("class", "valuePersonFirstNamePreferred w3-input w3-border classMedicalPatient inputMedicalPatient", pk, "PersonFirstNamePreferred w3-input w3-border ");
					a("name", "personFirstNamePreferred");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patchMedicalPatientVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setPersonFirstNamePreferred', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_personFirstNamePreferred')); }, function() { addError($('#", classApiMethodMethod, "_personFirstNamePreferred')); }); ");
				}
				a("value", strPersonFirstNamePreferred())
			.fg();

		} else {
			sx(htmPersonFirstNamePreferred());
		}
	}

	public void htmPersonFirstNamePreferred(String classApiMethodMethod) {
		MedicalPatient s = (MedicalPatient)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalPatientPersonFirstNamePreferred").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-orange ").f();
							e("label").a("for", classApiMethodMethod, "_personFirstNamePreferred").a("class", "").f().sx("preferred first name").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputPersonFirstNamePreferred(classApiMethodMethod);
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
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-orange ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_personFirstNamePreferred')); $('#", classApiMethodMethod, "_personFirstNamePreferred').val(null); patchMedicalPatientVal([{ name: 'fq', value: 'pk:' + $('#MedicalPatientForm :input[name=pk]').val() }], 'setPersonFirstNamePreferred', null, function() { addGlow($('#", classApiMethodMethod, "_personFirstNamePreferred')); }, function() { addError($('#", classApiMethodMethod, "_personFirstNamePreferred')); }); ")
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
	// familyName //
	////////////////

	/**	L'entité « familyName »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String familyName;
	@JsonIgnore
	public Wrap<String> familyNameWrap = new Wrap<String>().p(this).c(String.class).var("familyName").o(familyName);

	/**	<br/>L'entité « familyName »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.patient.MedicalPatient&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:familyName">Trouver l'entité familyName dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _familyName(Wrap<String> c);

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
		this.familyNameWrap.alreadyInitialized = true;
	}
	protected MedicalPatient familyNameInit() {
		if(!familyNameWrap.alreadyInitialized) {
			_familyName(familyNameWrap);
			if(familyName == null)
				setFamilyName(familyNameWrap.o);
		}
		familyNameWrap.alreadyInitialized(true);
		return (MedicalPatient)this;
	}

	public String solrFamilyName() {
		return familyName;
	}

	public String strFamilyName() {
		return familyName == null ? "" : familyName;
	}

	public String jsonFamilyName() {
		return familyName == null ? "" : familyName;
	}

	public String nomAffichageFamilyName() {
		return "last name";
	}

	public String htmTooltipFamilyName() {
		return null;
	}

	public String htmFamilyName() {
		return familyName == null ? "" : StringEscapeUtils.escapeHtml4(strFamilyName());
	}

	public void inputFamilyName(String classApiMethodMethod) {
		MedicalPatient s = (MedicalPatient)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("placeholder", "last name")
				.a("id", classApiMethodMethod, "_familyName");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setFamilyName classMedicalPatient inputMedicalPatient", pk, "FamilyName w3-input w3-border ");
					a("name", "setFamilyName");
				} else {
					a("class", "valueFamilyName w3-input w3-border classMedicalPatient inputMedicalPatient", pk, "FamilyName w3-input w3-border ");
					a("name", "familyName");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patchMedicalPatientVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setFamilyName', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_familyName')); }, function() { addError($('#", classApiMethodMethod, "_familyName')); }); ");
				}
				a("value", strFamilyName())
			.fg();

		} else {
			sx(htmFamilyName());
		}
	}

	public void htmFamilyName(String classApiMethodMethod) {
		MedicalPatient s = (MedicalPatient)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalPatientFamilyName").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-orange ").f();
							e("label").a("for", classApiMethodMethod, "_familyName").a("class", "").f().sx("last name").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputFamilyName(classApiMethodMethod);
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
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-orange ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_familyName')); $('#", classApiMethodMethod, "_familyName').val(null); patchMedicalPatientVal([{ name: 'fq', value: 'pk:' + $('#MedicalPatientForm :input[name=pk]').val() }], 'setFamilyName', null, function() { addGlow($('#", classApiMethodMethod, "_familyName')); }, function() { addError($('#", classApiMethodMethod, "_familyName')); }); ")
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
	// personCompleteName //
	////////////////////////

	/**	L'entité « personCompleteName »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String personCompleteName;
	@JsonIgnore
	public Wrap<String> personCompleteNameWrap = new Wrap<String>().p(this).c(String.class).var("personCompleteName").o(personCompleteName);

	/**	<br/>L'entité « personCompleteName »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.patient.MedicalPatient&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personCompleteName">Trouver l'entité personCompleteName dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _personCompleteName(Wrap<String> c);

	public String getPersonCompleteName() {
		return personCompleteName;
	}

	public void setPersonCompleteName(String personCompleteName) {
		this.personCompleteName = personCompleteName;
		this.personCompleteNameWrap.alreadyInitialized = true;
	}
	protected MedicalPatient personCompleteNameInit() {
		if(!personCompleteNameWrap.alreadyInitialized) {
			_personCompleteName(personCompleteNameWrap);
			if(personCompleteName == null)
				setPersonCompleteName(personCompleteNameWrap.o);
		}
		personCompleteNameWrap.alreadyInitialized(true);
		return (MedicalPatient)this;
	}

	public String solrPersonCompleteName() {
		return personCompleteName;
	}

	public String strPersonCompleteName() {
		return personCompleteName == null ? "" : personCompleteName;
	}

	public String jsonPersonCompleteName() {
		return personCompleteName == null ? "" : personCompleteName;
	}

	public String nomAffichagePersonCompleteName() {
		return "complete name";
	}

	public String htmTooltipPersonCompleteName() {
		return null;
	}

	public String htmPersonCompleteName() {
		return personCompleteName == null ? "" : StringEscapeUtils.escapeHtml4(strPersonCompleteName());
	}

	/////////////////////////////////
	// personCompleteNamePreferred //
	/////////////////////////////////

	/**	L'entité « personCompleteNamePreferred »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String personCompleteNamePreferred;
	@JsonIgnore
	public Wrap<String> personCompleteNamePreferredWrap = new Wrap<String>().p(this).c(String.class).var("personCompleteNamePreferred").o(personCompleteNamePreferred);

	/**	<br/>L'entité « personCompleteNamePreferred »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.patient.MedicalPatient&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personCompleteNamePreferred">Trouver l'entité personCompleteNamePreferred dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _personCompleteNamePreferred(Wrap<String> c);

	public String getPersonCompleteNamePreferred() {
		return personCompleteNamePreferred;
	}

	public void setPersonCompleteNamePreferred(String personCompleteNamePreferred) {
		this.personCompleteNamePreferred = personCompleteNamePreferred;
		this.personCompleteNamePreferredWrap.alreadyInitialized = true;
	}
	protected MedicalPatient personCompleteNamePreferredInit() {
		if(!personCompleteNamePreferredWrap.alreadyInitialized) {
			_personCompleteNamePreferred(personCompleteNamePreferredWrap);
			if(personCompleteNamePreferred == null)
				setPersonCompleteNamePreferred(personCompleteNamePreferredWrap.o);
		}
		personCompleteNamePreferredWrap.alreadyInitialized(true);
		return (MedicalPatient)this;
	}

	public String solrPersonCompleteNamePreferred() {
		return personCompleteNamePreferred;
	}

	public String strPersonCompleteNamePreferred() {
		return personCompleteNamePreferred == null ? "" : personCompleteNamePreferred;
	}

	public String jsonPersonCompleteNamePreferred() {
		return personCompleteNamePreferred == null ? "" : personCompleteNamePreferred;
	}

	public String nomAffichagePersonCompleteNamePreferred() {
		return "complete name preferred";
	}

	public String htmTooltipPersonCompleteNamePreferred() {
		return null;
	}

	public String htmPersonCompleteNamePreferred() {
		return personCompleteNamePreferred == null ? "" : StringEscapeUtils.escapeHtml4(strPersonCompleteNamePreferred());
	}

	//////////////////////
	// personFormalName //
	//////////////////////

	/**	L'entité « personFormalName »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String personFormalName;
	@JsonIgnore
	public Wrap<String> personFormalNameWrap = new Wrap<String>().p(this).c(String.class).var("personFormalName").o(personFormalName);

	/**	<br/>L'entité « personFormalName »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.patient.MedicalPatient&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personFormalName">Trouver l'entité personFormalName dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _personFormalName(Wrap<String> c);

	public String getPersonFormalName() {
		return personFormalName;
	}

	public void setPersonFormalName(String personFormalName) {
		this.personFormalName = personFormalName;
		this.personFormalNameWrap.alreadyInitialized = true;
	}
	protected MedicalPatient personFormalNameInit() {
		if(!personFormalNameWrap.alreadyInitialized) {
			_personFormalName(personFormalNameWrap);
			if(personFormalName == null)
				setPersonFormalName(personFormalNameWrap.o);
		}
		personFormalNameWrap.alreadyInitialized(true);
		return (MedicalPatient)this;
	}

	public String solrPersonFormalName() {
		return personFormalName;
	}

	public String strPersonFormalName() {
		return personFormalName == null ? "" : personFormalName;
	}

	public String jsonPersonFormalName() {
		return personFormalName == null ? "" : personFormalName;
	}

	public String nomAffichagePersonFormalName() {
		return "formal name";
	}

	public String htmTooltipPersonFormalName() {
		return null;
	}

	public String htmPersonFormalName() {
		return personFormalName == null ? "" : StringEscapeUtils.escapeHtml4(strPersonFormalName());
	}

	/////////////////////
	// personBirthDate //
	/////////////////////

	/**	L'entité « personBirthDate »
	 *	 is defined as null before being initialized. 
	 */
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	protected LocalDate personBirthDate;
	@JsonIgnore
	public Wrap<LocalDate> personBirthDateWrap = new Wrap<LocalDate>().p(this).c(LocalDate.class).var("personBirthDate").o(personBirthDate);

	/**	<br/>L'entité « personBirthDate »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.patient.MedicalPatient&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personBirthDate">Trouver l'entité personBirthDate dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _personBirthDate(Wrap<LocalDate> c);

	public LocalDate getPersonBirthDate() {
		return personBirthDate;
	}

	public void setPersonBirthDate(LocalDate personBirthDate) {
		this.personBirthDate = personBirthDate;
		this.personBirthDateWrap.alreadyInitialized = true;
	}
	public MedicalPatient setPersonBirthDate(Instant o) {
		this.personBirthDate = o == null ? null : LocalDate.from(o);
		this.personBirthDateWrap.alreadyInitialized = true;
		return (MedicalPatient)this;
	}
	/** Example: 2011-12-03+01:00 **/
	public MedicalPatient setPersonBirthDate(String o) {
		this.personBirthDate = o == null ? null : LocalDate.parse(o, DateTimeFormatter.ISO_DATE);
		this.personBirthDateWrap.alreadyInitialized = true;
		return (MedicalPatient)this;
	}
	public MedicalPatient setPersonBirthDate(Date o) {
		this.personBirthDate = o == null ? null : o.toInstant().atZone(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toLocalDate();
		this.personBirthDateWrap.alreadyInitialized = true;
		return (MedicalPatient)this;
	}
	protected MedicalPatient personBirthDateInit() {
		if(!personBirthDateWrap.alreadyInitialized) {
			_personBirthDate(personBirthDateWrap);
			if(personBirthDate == null)
				setPersonBirthDate(personBirthDateWrap.o);
		}
		personBirthDateWrap.alreadyInitialized(true);
		return (MedicalPatient)this;
	}

	public Date solrPersonBirthDate() {
		return personBirthDate == null ? null : Date.from(personBirthDate.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z")).toInstant());
	}

	public String strPersonBirthDate() {
		return personBirthDate == null ? "" : personBirthDate.format(DateTimeFormatter.ofPattern("EEE MMM d, yyyy", Locale.forLanguageTag("en-US")));
	}

	public String jsonPersonBirthDate() {
		return personBirthDate == null ? "" : personBirthDate.format(DateTimeFormatter.ISO_DATE);
	}

	public String nomAffichagePersonBirthDate() {
		return "birth date";
	}

	public String htmTooltipPersonBirthDate() {
		return null;
	}

	public String htmPersonBirthDate() {
		return personBirthDate == null ? "" : StringEscapeUtils.escapeHtml4(strPersonBirthDate());
	}

	public void inputPersonBirthDate(String classApiMethodMethod) {
		MedicalPatient s = (MedicalPatient)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("class", "w3-input w3-border datepicker setPersonBirthDate classMedicalPatient inputMedicalPatient", pk, "PersonBirthDate w3-input w3-border ")
				.a("placeholder", "MM/DD/YYYY")
				.a("data-timeformat", "MM/dd/yyyy")
				.a("id", classApiMethodMethod, "_personBirthDate")
				.a("onclick", "removeGlow($(this)); ")
				.a("value", personBirthDate == null ? "" : DateTimeFormatter.ofPattern("MM/dd/yyyy").format(personBirthDate))
				.a("onchange", "var t = moment(this.value, 'MM/DD/YYYY'); if(t) { var s = t.format('YYYY-MM-DD'); patchMedicalPatientVal([{ name: 'fq', value: 'pk:", pk, "' }], 'setPersonBirthDate', s, function() { addGlow($('#", classApiMethodMethod, "_personBirthDate')); }, function() { addError($('#", classApiMethodMethod, "_personBirthDate')); }); } ")
				.fg();
		} else {
			sx(htmPersonBirthDate());
		}
	}

	public void htmPersonBirthDate(String classApiMethodMethod) {
		MedicalPatient s = (MedicalPatient)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalPatientPersonBirthDate").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-orange ").f();
							e("label").a("for", classApiMethodMethod, "_personBirthDate").a("class", "").f().sx("birth date").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row  ").f();
							{ e("div").a("class", "w3-cell ").f();
								inputPersonBirthDate(classApiMethodMethod);
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
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-orange ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_personBirthDate')); $('#", classApiMethodMethod, "_personBirthDate').val(null); patchMedicalPatientVal([{ name: 'fq', value: 'pk:' + $('#MedicalPatientForm :input[name=pk]').val() }], 'setPersonBirthDate', null, function() { addGlow($('#", classApiMethodMethod, "_personBirthDate')); }, function() { addError($('#", classApiMethodMethod, "_personBirthDate')); }); ")
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
	// personBirthDateYear //
	/////////////////////////

	/**	L'entité « personBirthDateYear »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer personBirthDateYear;
	@JsonIgnore
	public Wrap<Integer> personBirthDateYearWrap = new Wrap<Integer>().p(this).c(Integer.class).var("personBirthDateYear").o(personBirthDateYear);

	/**	<br/>L'entité « personBirthDateYear »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.patient.MedicalPatient&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personBirthDateYear">Trouver l'entité personBirthDateYear dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _personBirthDateYear(Wrap<Integer> c);

	public Integer getPersonBirthDateYear() {
		return personBirthDateYear;
	}

	public void setPersonBirthDateYear(Integer personBirthDateYear) {
		this.personBirthDateYear = personBirthDateYear;
		this.personBirthDateYearWrap.alreadyInitialized = true;
	}
	public MedicalPatient setPersonBirthDateYear(String o) {
		if(NumberUtils.isParsable(o))
			this.personBirthDateYear = Integer.parseInt(o);
		this.personBirthDateYearWrap.alreadyInitialized = true;
		return (MedicalPatient)this;
	}
	protected MedicalPatient personBirthDateYearInit() {
		if(!personBirthDateYearWrap.alreadyInitialized) {
			_personBirthDateYear(personBirthDateYearWrap);
			if(personBirthDateYear == null)
				setPersonBirthDateYear(personBirthDateYearWrap.o);
		}
		personBirthDateYearWrap.alreadyInitialized(true);
		return (MedicalPatient)this;
	}

	public Integer solrPersonBirthDateYear() {
		return personBirthDateYear;
	}

	public String strPersonBirthDateYear() {
		return personBirthDateYear == null ? "" : personBirthDateYear.toString();
	}

	public String jsonPersonBirthDateYear() {
		return personBirthDateYear == null ? "" : personBirthDateYear.toString();
	}

	public String nomAffichagePersonBirthDateYear() {
		return null;
	}

	public String htmTooltipPersonBirthDateYear() {
		return null;
	}

	public String htmPersonBirthDateYear() {
		return personBirthDateYear == null ? "" : StringEscapeUtils.escapeHtml4(strPersonBirthDateYear());
	}

	////////////////////////////////
	// personBirthDateMonthOfYear //
	////////////////////////////////

	/**	L'entité « personBirthDateMonthOfYear »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String personBirthDateMonthOfYear;
	@JsonIgnore
	public Wrap<String> personBirthDateMonthOfYearWrap = new Wrap<String>().p(this).c(String.class).var("personBirthDateMonthOfYear").o(personBirthDateMonthOfYear);

	/**	<br/>L'entité « personBirthDateMonthOfYear »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.patient.MedicalPatient&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personBirthDateMonthOfYear">Trouver l'entité personBirthDateMonthOfYear dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _personBirthDateMonthOfYear(Wrap<String> c);

	public String getPersonBirthDateMonthOfYear() {
		return personBirthDateMonthOfYear;
	}

	public void setPersonBirthDateMonthOfYear(String personBirthDateMonthOfYear) {
		this.personBirthDateMonthOfYear = personBirthDateMonthOfYear;
		this.personBirthDateMonthOfYearWrap.alreadyInitialized = true;
	}
	protected MedicalPatient personBirthDateMonthOfYearInit() {
		if(!personBirthDateMonthOfYearWrap.alreadyInitialized) {
			_personBirthDateMonthOfYear(personBirthDateMonthOfYearWrap);
			if(personBirthDateMonthOfYear == null)
				setPersonBirthDateMonthOfYear(personBirthDateMonthOfYearWrap.o);
		}
		personBirthDateMonthOfYearWrap.alreadyInitialized(true);
		return (MedicalPatient)this;
	}

	public String solrPersonBirthDateMonthOfYear() {
		return personBirthDateMonthOfYear;
	}

	public String strPersonBirthDateMonthOfYear() {
		return personBirthDateMonthOfYear == null ? "" : personBirthDateMonthOfYear;
	}

	public String jsonPersonBirthDateMonthOfYear() {
		return personBirthDateMonthOfYear == null ? "" : personBirthDateMonthOfYear;
	}

	public String nomAffichagePersonBirthDateMonthOfYear() {
		return null;
	}

	public String htmTooltipPersonBirthDateMonthOfYear() {
		return null;
	}

	public String htmPersonBirthDateMonthOfYear() {
		return personBirthDateMonthOfYear == null ? "" : StringEscapeUtils.escapeHtml4(strPersonBirthDateMonthOfYear());
	}

	//////////////////////////////
	// personBirthDateDayOfWeek //
	//////////////////////////////

	/**	L'entité « personBirthDateDayOfWeek »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String personBirthDateDayOfWeek;
	@JsonIgnore
	public Wrap<String> personBirthDateDayOfWeekWrap = new Wrap<String>().p(this).c(String.class).var("personBirthDateDayOfWeek").o(personBirthDateDayOfWeek);

	/**	<br/>L'entité « personBirthDateDayOfWeek »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.patient.MedicalPatient&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personBirthDateDayOfWeek">Trouver l'entité personBirthDateDayOfWeek dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _personBirthDateDayOfWeek(Wrap<String> c);

	public String getPersonBirthDateDayOfWeek() {
		return personBirthDateDayOfWeek;
	}

	public void setPersonBirthDateDayOfWeek(String personBirthDateDayOfWeek) {
		this.personBirthDateDayOfWeek = personBirthDateDayOfWeek;
		this.personBirthDateDayOfWeekWrap.alreadyInitialized = true;
	}
	protected MedicalPatient personBirthDateDayOfWeekInit() {
		if(!personBirthDateDayOfWeekWrap.alreadyInitialized) {
			_personBirthDateDayOfWeek(personBirthDateDayOfWeekWrap);
			if(personBirthDateDayOfWeek == null)
				setPersonBirthDateDayOfWeek(personBirthDateDayOfWeekWrap.o);
		}
		personBirthDateDayOfWeekWrap.alreadyInitialized(true);
		return (MedicalPatient)this;
	}

	public String solrPersonBirthDateDayOfWeek() {
		return personBirthDateDayOfWeek;
	}

	public String strPersonBirthDateDayOfWeek() {
		return personBirthDateDayOfWeek == null ? "" : personBirthDateDayOfWeek;
	}

	public String jsonPersonBirthDateDayOfWeek() {
		return personBirthDateDayOfWeek == null ? "" : personBirthDateDayOfWeek;
	}

	public String nomAffichagePersonBirthDateDayOfWeek() {
		return null;
	}

	public String htmTooltipPersonBirthDateDayOfWeek() {
		return null;
	}

	public String htmPersonBirthDateDayOfWeek() {
		return personBirthDateDayOfWeek == null ? "" : StringEscapeUtils.escapeHtml4(strPersonBirthDateDayOfWeek());
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.patient.MedicalPatient&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:patientCompleteName">Trouver l'entité patientCompleteName dans Solr</a>
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
	protected MedicalPatient patientCompleteNameInit() {
		if(!patientCompleteNameWrap.alreadyInitialized) {
			_patientCompleteName(patientCompleteNameWrap);
			if(patientCompleteName == null)
				setPatientCompleteName(patientCompleteNameWrap.o);
		}
		patientCompleteNameWrap.alreadyInitialized(true);
		return (MedicalPatient)this;
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
		return "name";
	}

	public String htmTooltipPatientCompleteName() {
		return null;
	}

	public String htmPatientCompleteName() {
		return patientCompleteName == null ? "" : StringEscapeUtils.escapeHtml4(strPatientCompleteName());
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedMedicalPatient = false;

	public MedicalPatient initDeepMedicalPatient(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedMedicalPatient) {
			alreadyInitializedMedicalPatient = true;
			initDeepMedicalPatient();
		}
		return (MedicalPatient)this;
	}

	public void initDeepMedicalPatient() {
		initMedicalPatient();
		super.initDeepCluster(siteRequest_);
	}

	public void initMedicalPatient() {
		patientKeyInit();
		enrollmentKeysInit();
		schoolSortInit();
		enrollmentSearchInit();
		enrollmentsInit();
		userKeysInit();
		clinicKeysInit();
		personFirstNameInit();
		personFirstNamePreferredInit();
		familyNameInit();
		personCompleteNameInit();
		personCompleteNamePreferredInit();
		personFormalNameInit();
		personBirthDateInit();
		personBirthDateYearInit();
		personBirthDateMonthOfYearInit();
		personBirthDateDayOfWeekInit();
		patientCompleteNameInit();
	}

	@Override public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepMedicalPatient(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestMedicalPatient(SiteRequestEnUS siteRequest_) {
			super.siteRequestCluster(siteRequest_);
		if(enrollmentSearch != null)
			enrollmentSearch.setSiteRequest_(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestMedicalPatient(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainMedicalPatient(v);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.obtainForClass(v);
			}
		}
		return o;
	}
	public Object obtainMedicalPatient(String var) {
		MedicalPatient oMedicalPatient = (MedicalPatient)this;
		switch(var) {
			case "patientKey":
				return oMedicalPatient.patientKey;
			case "enrollmentKeys":
				return oMedicalPatient.enrollmentKeys;
			case "schoolSort":
				return oMedicalPatient.schoolSort;
			case "enrollmentSearch":
				return oMedicalPatient.enrollmentSearch;
			case "enrollments":
				return oMedicalPatient.enrollments;
			case "userKeys":
				return oMedicalPatient.userKeys;
			case "clinicKeys":
				return oMedicalPatient.clinicKeys;
			case "personFirstName":
				return oMedicalPatient.personFirstName;
			case "personFirstNamePreferred":
				return oMedicalPatient.personFirstNamePreferred;
			case "familyName":
				return oMedicalPatient.familyName;
			case "personCompleteName":
				return oMedicalPatient.personCompleteName;
			case "personCompleteNamePreferred":
				return oMedicalPatient.personCompleteNamePreferred;
			case "personFormalName":
				return oMedicalPatient.personFormalName;
			case "personBirthDate":
				return oMedicalPatient.personBirthDate;
			case "personBirthDateYear":
				return oMedicalPatient.personBirthDateYear;
			case "personBirthDateMonthOfYear":
				return oMedicalPatient.personBirthDateMonthOfYear;
			case "personBirthDateDayOfWeek":
				return oMedicalPatient.personBirthDateDayOfWeek;
			case "patientCompleteName":
				return oMedicalPatient.patientCompleteName;
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
				o = attributeMedicalPatient(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeMedicalPatient(String var, Object val) {
		MedicalPatient oMedicalPatient = (MedicalPatient)this;
		switch(var) {
			case "enrollmentKeys":
				oMedicalPatient.addEnrollmentKeys((Long)val);
				if(!savesMedicalPatient.contains(var))
					savesMedicalPatient.add(var);
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
					o = defineMedicalPatient(v, val);
				else if(o instanceof Cluster) {
					Cluster cluster = (Cluster)o;
					o = cluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineMedicalPatient(String var, String val) {
		switch(var) {
			case "personFirstName":
				if(val != null)
					setPersonFirstName(val);
				savesMedicalPatient.add(var);
				return val;
			case "personFirstNamePreferred":
				if(val != null)
					setPersonFirstNamePreferred(val);
				savesMedicalPatient.add(var);
				return val;
			case "familyName":
				if(val != null)
					setFamilyName(val);
				savesMedicalPatient.add(var);
				return val;
			case "personBirthDate":
				if(val != null)
					setPersonBirthDate(val);
				savesMedicalPatient.add(var);
				return val;
			default:
				return super.defineCluster(var, val);
		}
	}

	/////////////////
	// saves //
	/////////////////

	protected List<String> savesMedicalPatient = new ArrayList<String>();

	/////////////
	// populate //
	/////////////

	@Override public void populateForClass(SolrDocument solrDocument) {
		populateMedicalPatient(solrDocument);
	}
	public void populateMedicalPatient(SolrDocument solrDocument) {
		MedicalPatient oMedicalPatient = (MedicalPatient)this;
		savesMedicalPatient = (List<String>)solrDocument.get("savesMedicalPatient_stored_strings");
		if(savesMedicalPatient != null) {

			if(savesMedicalPatient.contains("patientKey")) {
				Long patientKey = (Long)solrDocument.get("patientKey_stored_long");
				if(patientKey != null)
					oMedicalPatient.setPatientKey(patientKey);
			}

			List<Long> enrollmentKeys = (List<Long>)solrDocument.get("enrollmentKeys_stored_longs");
			if(enrollmentKeys != null)
				oMedicalPatient.enrollmentKeys.addAll(enrollmentKeys);

			if(savesMedicalPatient.contains("schoolSort")) {
				Integer schoolSort = (Integer)solrDocument.get("schoolSort_stored_int");
				if(schoolSort != null)
					oMedicalPatient.setSchoolSort(schoolSort);
			}

			if(savesMedicalPatient.contains("userKeys")) {
				List<Long> userKeys = (List<Long>)solrDocument.get("userKeys_stored_longs");
				if(userKeys != null)
					oMedicalPatient.userKeys.addAll(userKeys);
			}

			if(savesMedicalPatient.contains("clinicKeys")) {
				List<Long> clinicKeys = (List<Long>)solrDocument.get("clinicKeys_stored_longs");
				if(clinicKeys != null)
					oMedicalPatient.clinicKeys.addAll(clinicKeys);
			}

			if(savesMedicalPatient.contains("personFirstName")) {
				String personFirstName = (String)solrDocument.get("personFirstName_stored_string");
				if(personFirstName != null)
					oMedicalPatient.setPersonFirstName(personFirstName);
			}

			if(savesMedicalPatient.contains("personFirstNamePreferred")) {
				String personFirstNamePreferred = (String)solrDocument.get("personFirstNamePreferred_stored_string");
				if(personFirstNamePreferred != null)
					oMedicalPatient.setPersonFirstNamePreferred(personFirstNamePreferred);
			}

			if(savesMedicalPatient.contains("familyName")) {
				String familyName = (String)solrDocument.get("familyName_stored_string");
				if(familyName != null)
					oMedicalPatient.setFamilyName(familyName);
			}

			if(savesMedicalPatient.contains("personCompleteName")) {
				String personCompleteName = (String)solrDocument.get("personCompleteName_stored_string");
				if(personCompleteName != null)
					oMedicalPatient.setPersonCompleteName(personCompleteName);
			}

			if(savesMedicalPatient.contains("personCompleteNamePreferred")) {
				String personCompleteNamePreferred = (String)solrDocument.get("personCompleteNamePreferred_stored_string");
				if(personCompleteNamePreferred != null)
					oMedicalPatient.setPersonCompleteNamePreferred(personCompleteNamePreferred);
			}

			if(savesMedicalPatient.contains("personFormalName")) {
				String personFormalName = (String)solrDocument.get("personFormalName_stored_string");
				if(personFormalName != null)
					oMedicalPatient.setPersonFormalName(personFormalName);
			}

			if(savesMedicalPatient.contains("personBirthDate")) {
				Date personBirthDate = (Date)solrDocument.get("personBirthDate_stored_date");
				if(personBirthDate != null)
					oMedicalPatient.setPersonBirthDate(personBirthDate);
			}

			if(savesMedicalPatient.contains("personBirthDateYear")) {
				Integer personBirthDateYear = (Integer)solrDocument.get("personBirthDateYear_stored_int");
				if(personBirthDateYear != null)
					oMedicalPatient.setPersonBirthDateYear(personBirthDateYear);
			}

			if(savesMedicalPatient.contains("personBirthDateMonthOfYear")) {
				String personBirthDateMonthOfYear = (String)solrDocument.get("personBirthDateMonthOfYear_stored_string");
				if(personBirthDateMonthOfYear != null)
					oMedicalPatient.setPersonBirthDateMonthOfYear(personBirthDateMonthOfYear);
			}

			if(savesMedicalPatient.contains("personBirthDateDayOfWeek")) {
				String personBirthDateDayOfWeek = (String)solrDocument.get("personBirthDateDayOfWeek_stored_string");
				if(personBirthDateDayOfWeek != null)
					oMedicalPatient.setPersonBirthDateDayOfWeek(personBirthDateDayOfWeek);
			}

			if(savesMedicalPatient.contains("patientCompleteName")) {
				String patientCompleteName = (String)solrDocument.get("patientCompleteName_stored_string");
				if(patientCompleteName != null)
					oMedicalPatient.setPatientCompleteName(patientCompleteName);
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
			solrQuery.addFilterQuery("id:" + ClientUtils.escapeQueryChars("org.computate.medicale.enUS.patient.MedicalPatient"));
			QueryResponse queryResponse = siteRequest.getSiteContext_().getSolrClient().query(solrQuery);
			if(queryResponse.getResults().size() > 0)
				siteRequest.setSolrDocument(queryResponse.getResults().get(0));
			MedicalPatient o = new MedicalPatient();
			o.siteRequestMedicalPatient(siteRequest);
			o.initDeepMedicalPatient(siteRequest);
			o.indexMedicalPatient();
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}


	@Override public void indexForClass() {
		indexMedicalPatient();
	}

	@Override public void indexForClass(SolrInputDocument document) {
		indexMedicalPatient(document);
	}

	public void indexMedicalPatient(SolrClient clientSolr) {
		try {
			SolrInputDocument document = new SolrInputDocument();
			indexMedicalPatient(document);
			clientSolr.add(document);
			clientSolr.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public void indexMedicalPatient() {
		try {
			SolrInputDocument document = new SolrInputDocument();
			indexMedicalPatient(document);
			SolrClient clientSolr = siteRequest_.getSiteContext_().getSolrClient();
			clientSolr.add(document);
			clientSolr.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public void indexMedicalPatient(SolrInputDocument document) {
		if(savesMedicalPatient != null)
			document.addField("savesMedicalPatient_stored_strings", savesMedicalPatient);

		if(patientKey != null) {
			document.addField("patientKey_indexed_long", patientKey);
			document.addField("patientKey_stored_long", patientKey);
		}
		if(enrollmentKeys != null) {
			for(java.lang.Long o : enrollmentKeys) {
				document.addField("enrollmentKeys_indexed_longs", o);
			}
			for(java.lang.Long o : enrollmentKeys) {
				document.addField("enrollmentKeys_stored_longs", o);
			}
		}
		if(schoolSort != null) {
			document.addField("schoolSort_indexed_int", schoolSort);
			document.addField("schoolSort_stored_int", schoolSort);
		}
		if(userKeys != null) {
			for(java.lang.Long o : userKeys) {
				document.addField("userKeys_indexed_longs", o);
			}
			for(java.lang.Long o : userKeys) {
				document.addField("userKeys_stored_longs", o);
			}
		}
		if(clinicKeys != null) {
			for(java.lang.Long o : clinicKeys) {
				document.addField("clinicKeys_indexed_longs", o);
			}
			for(java.lang.Long o : clinicKeys) {
				document.addField("clinicKeys_stored_longs", o);
			}
		}
		if(personFirstName != null) {
			document.addField("personFirstName_indexed_string", personFirstName);
			document.addField("personFirstName_stored_string", personFirstName);
		}
		if(personFirstNamePreferred != null) {
			document.addField("personFirstNamePreferred_indexed_string", personFirstNamePreferred);
			document.addField("personFirstNamePreferred_stored_string", personFirstNamePreferred);
		}
		if(familyName != null) {
			document.addField("familyName_indexed_string", familyName);
			document.addField("familyName_stored_string", familyName);
		}
		if(personCompleteName != null) {
			document.addField("personCompleteName_indexed_string", personCompleteName);
			document.addField("personCompleteName_stored_string", personCompleteName);
		}
		if(personCompleteNamePreferred != null) {
			document.addField("personCompleteNamePreferred_indexed_string", personCompleteNamePreferred);
			document.addField("personCompleteNamePreferred_stored_string", personCompleteNamePreferred);
		}
		if(personFormalName != null) {
			document.addField("personFormalName_indexed_string", personFormalName);
			document.addField("personFormalName_stored_string", personFormalName);
		}
		if(personBirthDate != null) {
			document.addField("personBirthDate_indexed_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(personBirthDate.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
			document.addField("personBirthDate_stored_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(personBirthDate.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
		}
		if(personBirthDateYear != null) {
			document.addField("personBirthDateYear_indexed_int", personBirthDateYear);
			document.addField("personBirthDateYear_stored_int", personBirthDateYear);
		}
		if(personBirthDateMonthOfYear != null) {
			document.addField("personBirthDateMonthOfYear_indexed_string", personBirthDateMonthOfYear);
			document.addField("personBirthDateMonthOfYear_stored_string", personBirthDateMonthOfYear);
		}
		if(personBirthDateDayOfWeek != null) {
			document.addField("personBirthDateDayOfWeek_indexed_string", personBirthDateDayOfWeek);
			document.addField("personBirthDateDayOfWeek_stored_string", personBirthDateDayOfWeek);
		}
		if(patientCompleteName != null) {
			document.addField("patientCompleteName_indexed_string", patientCompleteName);
			document.addField("patientCompleteName_stored_string", patientCompleteName);
		}
		super.indexCluster(document);

	}

	public void unindexMedicalPatient() {
		try {
		SiteRequestEnUS siteRequest = new SiteRequestEnUS();
			siteRequest.initDeepSiteRequestEnUS();
			SiteContextEnUS siteContext = new SiteContextEnUS();
			siteContext.initDeepSiteContextEnUS();
			siteRequest.setSiteContext_(siteContext);
			siteRequest.setSiteConfig_(siteContext.getSiteConfig());
			initDeepMedicalPatient(siteRequest);
			SolrClient solrClient = siteContext.getSolrClient();
			solrClient.deleteById(id.toString());
			solrClient.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public static String varIndexedMedicalPatient(String entityVar) {
		switch(entityVar) {
			case "patientKey":
				return "patientKey_indexed_long";
			case "enrollmentKeys":
				return "enrollmentKeys_indexed_longs";
			case "schoolSort":
				return "schoolSort_indexed_int";
			case "userKeys":
				return "userKeys_indexed_longs";
			case "clinicKeys":
				return "clinicKeys_indexed_longs";
			case "personFirstName":
				return "personFirstName_indexed_string";
			case "personFirstNamePreferred":
				return "personFirstNamePreferred_indexed_string";
			case "familyName":
				return "familyName_indexed_string";
			case "personCompleteName":
				return "personCompleteName_indexed_string";
			case "personCompleteNamePreferred":
				return "personCompleteNamePreferred_indexed_string";
			case "personFormalName":
				return "personFormalName_indexed_string";
			case "personBirthDate":
				return "personBirthDate_indexed_date";
			case "personBirthDateYear":
				return "personBirthDateYear_indexed_int";
			case "personBirthDateMonthOfYear":
				return "personBirthDateMonthOfYear_indexed_string";
			case "personBirthDateDayOfWeek":
				return "personBirthDateDayOfWeek_indexed_string";
			case "patientCompleteName":
				return "patientCompleteName_indexed_string";
			default:
				return Cluster.varIndexedCluster(entityVar);
		}
	}

	public static String varSearchMedicalPatient(String entityVar) {
		switch(entityVar) {
			default:
				return Cluster.varSearchCluster(entityVar);
		}
	}

	public static String varSuggestedMedicalPatient(String entityVar) {
		switch(entityVar) {
			default:
				return Cluster.varSuggestedCluster(entityVar);
		}
	}

	/////////////
	// store //
	/////////////

	@Override public void storeForClass(SolrDocument solrDocument) {
		storeMedicalPatient(solrDocument);
	}
	public void storeMedicalPatient(SolrDocument solrDocument) {
		MedicalPatient oMedicalPatient = (MedicalPatient)this;

		Long patientKey = (Long)solrDocument.get("patientKey_stored_long");
		if(patientKey != null)
			oMedicalPatient.setPatientKey(patientKey);

		List<Long> enrollmentKeys = (List<Long>)solrDocument.get("enrollmentKeys_stored_longs");
		if(enrollmentKeys != null)
			oMedicalPatient.enrollmentKeys.addAll(enrollmentKeys);

		Integer schoolSort = (Integer)solrDocument.get("schoolSort_stored_int");
		if(schoolSort != null)
			oMedicalPatient.setSchoolSort(schoolSort);

		List<Long> userKeys = (List<Long>)solrDocument.get("userKeys_stored_longs");
		if(userKeys != null)
			oMedicalPatient.userKeys.addAll(userKeys);

		List<Long> clinicKeys = (List<Long>)solrDocument.get("clinicKeys_stored_longs");
		if(clinicKeys != null)
			oMedicalPatient.clinicKeys.addAll(clinicKeys);

		String personFirstName = (String)solrDocument.get("personFirstName_stored_string");
		if(personFirstName != null)
			oMedicalPatient.setPersonFirstName(personFirstName);

		String personFirstNamePreferred = (String)solrDocument.get("personFirstNamePreferred_stored_string");
		if(personFirstNamePreferred != null)
			oMedicalPatient.setPersonFirstNamePreferred(personFirstNamePreferred);

		String familyName = (String)solrDocument.get("familyName_stored_string");
		if(familyName != null)
			oMedicalPatient.setFamilyName(familyName);

		String personCompleteName = (String)solrDocument.get("personCompleteName_stored_string");
		if(personCompleteName != null)
			oMedicalPatient.setPersonCompleteName(personCompleteName);

		String personCompleteNamePreferred = (String)solrDocument.get("personCompleteNamePreferred_stored_string");
		if(personCompleteNamePreferred != null)
			oMedicalPatient.setPersonCompleteNamePreferred(personCompleteNamePreferred);

		String personFormalName = (String)solrDocument.get("personFormalName_stored_string");
		if(personFormalName != null)
			oMedicalPatient.setPersonFormalName(personFormalName);

		Date personBirthDate = (Date)solrDocument.get("personBirthDate_stored_date");
		if(personBirthDate != null)
			oMedicalPatient.setPersonBirthDate(personBirthDate);

		Integer personBirthDateYear = (Integer)solrDocument.get("personBirthDateYear_stored_int");
		if(personBirthDateYear != null)
			oMedicalPatient.setPersonBirthDateYear(personBirthDateYear);

		String personBirthDateMonthOfYear = (String)solrDocument.get("personBirthDateMonthOfYear_stored_string");
		if(personBirthDateMonthOfYear != null)
			oMedicalPatient.setPersonBirthDateMonthOfYear(personBirthDateMonthOfYear);

		String personBirthDateDayOfWeek = (String)solrDocument.get("personBirthDateDayOfWeek_stored_string");
		if(personBirthDateDayOfWeek != null)
			oMedicalPatient.setPersonBirthDateDayOfWeek(personBirthDateDayOfWeek);

		String patientCompleteName = (String)solrDocument.get("patientCompleteName_stored_string");
		if(patientCompleteName != null)
			oMedicalPatient.setPatientCompleteName(patientCompleteName);

		super.storeCluster(solrDocument);
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestMedicalPatient() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof MedicalPatient) {
			MedicalPatient original = (MedicalPatient)o;
			if(!Objects.equals(enrollmentKeys, original.getEnrollmentKeys()))
				apiRequest.addVars("enrollmentKeys");
			if(!Objects.equals(personFirstName, original.getPersonFirstName()))
				apiRequest.addVars("personFirstName");
			if(!Objects.equals(personFirstNamePreferred, original.getPersonFirstNamePreferred()))
				apiRequest.addVars("personFirstNamePreferred");
			if(!Objects.equals(familyName, original.getFamilyName()))
				apiRequest.addVars("familyName");
			if(!Objects.equals(personBirthDate, original.getPersonBirthDate()))
				apiRequest.addVars("personBirthDate");
			super.apiRequestCluster();
		}
	}

	//////////////
	// hashCode //
	//////////////

	@Override public int hashCode() {
		return Objects.hash(super.hashCode(), enrollmentKeys, personFirstName, personFirstNamePreferred, familyName, personBirthDate);
	}

	////////////
	// equals //
	////////////

	@Override public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof MedicalPatient))
			return false;
		MedicalPatient that = (MedicalPatient)o;
		return super.equals(o)
				&& Objects.equals( enrollmentKeys, that.enrollmentKeys )
				&& Objects.equals( personFirstName, that.personFirstName )
				&& Objects.equals( personFirstNamePreferred, that.personFirstNamePreferred )
				&& Objects.equals( familyName, that.familyName )
				&& Objects.equals( personBirthDate, that.personBirthDate );
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("MedicalPatient { ");
		sb.append( "enrollmentKeys: " ).append(enrollmentKeys);
		sb.append( ", personFirstName: \"" ).append(personFirstName).append( "\"" );
		sb.append( ", personFirstNamePreferred: \"" ).append(personFirstNamePreferred).append( "\"" );
		sb.append( ", familyName: \"" ).append(familyName).append( "\"" );
		sb.append( ", personBirthDate: " ).append(personBirthDate);
		sb.append(" }");
		return sb.toString();
	}
}
