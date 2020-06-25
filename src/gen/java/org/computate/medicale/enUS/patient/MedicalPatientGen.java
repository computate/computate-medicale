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
import java.util.HashMap;
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
	public static final String MedicalPatient_IconName = "hospital-user";

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
	protected List<Long> enrollmentKeys = new ArrayList<Long>();
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
								e("i").a("class", "fas fa-notes-medical ").f().g("i");
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
								{
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
	protected List<MedicalEnrollment> enrollments = new ArrayList<MedicalEnrollment>();
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
	protected List<Long> userKeys = new ArrayList<Long>();
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
	protected List<Long> clinicKeys = new ArrayList<Long>();
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.patient.MedicalPatient&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:patientFirstName">Trouver l'entité patientFirstName dans Solr</a>
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
	protected MedicalPatient patientFirstNameInit() {
		if(!patientFirstNameWrap.alreadyInitialized) {
			_patientFirstName(patientFirstNameWrap);
			if(patientFirstName == null)
				setPatientFirstName(patientFirstNameWrap.o);
		}
		patientFirstNameWrap.alreadyInitialized(true);
		return (MedicalPatient)this;
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
		return "first name";
	}

	public String htmTooltipPatientFirstName() {
		return null;
	}

	public String htmPatientFirstName() {
		return patientFirstName == null ? "" : StringEscapeUtils.escapeHtml4(strPatientFirstName());
	}

	public void inputPatientFirstName(String classApiMethodMethod) {
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
				.a("id", classApiMethodMethod, "_patientFirstName");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setPatientFirstName classMedicalPatient inputMedicalPatient", pk, "PatientFirstName w3-input w3-border ");
					a("name", "setPatientFirstName");
				} else {
					a("class", "valuePatientFirstName w3-input w3-border classMedicalPatient inputMedicalPatient", pk, "PatientFirstName w3-input w3-border ");
					a("name", "patientFirstName");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setPatientFirstName', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_patientFirstName')); }, function() { addError($('#", classApiMethodMethod, "_patientFirstName')); }); ");
				}
				a("value", strPatientFirstName())
			.fg();

		} else {
			sx(htmPatientFirstName());
		}
	}

	public void htmPatientFirstName(String classApiMethodMethod) {
		MedicalPatient s = (MedicalPatient)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalPatientPatientFirstName").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-orange ").f();
							e("label").a("for", classApiMethodMethod, "_patientFirstName").a("class", "").f().sx("first name").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputPatientFirstName(classApiMethodMethod);
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_patientFirstName')); $('#", classApiMethodMethod, "_patientFirstName').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#MedicalPatientForm :input[name=pk]').val() }], 'setPatientFirstName', null, function() { addGlow($('#", classApiMethodMethod, "_patientFirstName')); }, function() { addError($('#", classApiMethodMethod, "_patientFirstName')); }); ")
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.patient.MedicalPatient&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:patientFirstNamePreferred">Trouver l'entité patientFirstNamePreferred dans Solr</a>
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
	protected MedicalPatient patientFirstNamePreferredInit() {
		if(!patientFirstNamePreferredWrap.alreadyInitialized) {
			_patientFirstNamePreferred(patientFirstNamePreferredWrap);
			if(patientFirstNamePreferred == null)
				setPatientFirstNamePreferred(patientFirstNamePreferredWrap.o);
		}
		patientFirstNamePreferredWrap.alreadyInitialized(true);
		return (MedicalPatient)this;
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
		return "preferred first name";
	}

	public String htmTooltipPatientFirstNamePreferred() {
		return null;
	}

	public String htmPatientFirstNamePreferred() {
		return patientFirstNamePreferred == null ? "" : StringEscapeUtils.escapeHtml4(strPatientFirstNamePreferred());
	}

	public void inputPatientFirstNamePreferred(String classApiMethodMethod) {
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
				.a("id", classApiMethodMethod, "_patientFirstNamePreferred");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setPatientFirstNamePreferred classMedicalPatient inputMedicalPatient", pk, "PatientFirstNamePreferred w3-input w3-border ");
					a("name", "setPatientFirstNamePreferred");
				} else {
					a("class", "valuePatientFirstNamePreferred w3-input w3-border classMedicalPatient inputMedicalPatient", pk, "PatientFirstNamePreferred w3-input w3-border ");
					a("name", "patientFirstNamePreferred");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setPatientFirstNamePreferred', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_patientFirstNamePreferred')); }, function() { addError($('#", classApiMethodMethod, "_patientFirstNamePreferred')); }); ");
				}
				a("value", strPatientFirstNamePreferred())
			.fg();

		} else {
			sx(htmPatientFirstNamePreferred());
		}
	}

	public void htmPatientFirstNamePreferred(String classApiMethodMethod) {
		MedicalPatient s = (MedicalPatient)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalPatientPatientFirstNamePreferred").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-orange ").f();
							e("label").a("for", classApiMethodMethod, "_patientFirstNamePreferred").a("class", "").f().sx("preferred first name").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputPatientFirstNamePreferred(classApiMethodMethod);
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_patientFirstNamePreferred')); $('#", classApiMethodMethod, "_patientFirstNamePreferred').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#MedicalPatientForm :input[name=pk]').val() }], 'setPatientFirstNamePreferred', null, function() { addGlow($('#", classApiMethodMethod, "_patientFirstNamePreferred')); }, function() { addError($('#", classApiMethodMethod, "_patientFirstNamePreferred')); }); ")
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
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setFamilyName', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_familyName')); }, function() { addError($('#", classApiMethodMethod, "_familyName')); }); ");
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_familyName')); $('#", classApiMethodMethod, "_familyName').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#MedicalPatientForm :input[name=pk]').val() }], 'setFamilyName', null, function() { addGlow($('#", classApiMethodMethod, "_familyName')); }, function() { addError($('#", classApiMethodMethod, "_familyName')); }); ")
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
		return "complete name";
	}

	public String htmTooltipPatientCompleteName() {
		return null;
	}

	public String htmPatientCompleteName() {
		return patientCompleteName == null ? "" : StringEscapeUtils.escapeHtml4(strPatientCompleteName());
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.patient.MedicalPatient&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:patientCompleteNamePreferred">Trouver l'entité patientCompleteNamePreferred dans Solr</a>
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
	protected MedicalPatient patientCompleteNamePreferredInit() {
		if(!patientCompleteNamePreferredWrap.alreadyInitialized) {
			_patientCompleteNamePreferred(patientCompleteNamePreferredWrap);
			if(patientCompleteNamePreferred == null)
				setPatientCompleteNamePreferred(patientCompleteNamePreferredWrap.o);
		}
		patientCompleteNamePreferredWrap.alreadyInitialized(true);
		return (MedicalPatient)this;
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
		return "complete name preferred";
	}

	public String htmTooltipPatientCompleteNamePreferred() {
		return null;
	}

	public String htmPatientCompleteNamePreferred() {
		return patientCompleteNamePreferred == null ? "" : StringEscapeUtils.escapeHtml4(strPatientCompleteNamePreferred());
	}

	///////////////////////
	// patientFormalName //
	///////////////////////

	/**	L'entité « patientFormalName »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String patientFormalName;
	@JsonIgnore
	public Wrap<String> patientFormalNameWrap = new Wrap<String>().p(this).c(String.class).var("patientFormalName").o(patientFormalName);

	/**	<br/>L'entité « patientFormalName »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.patient.MedicalPatient&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:patientFormalName">Trouver l'entité patientFormalName dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patientFormalName(Wrap<String> c);

	public String getPatientFormalName() {
		return patientFormalName;
	}

	public void setPatientFormalName(String patientFormalName) {
		this.patientFormalName = patientFormalName;
		this.patientFormalNameWrap.alreadyInitialized = true;
	}
	protected MedicalPatient patientFormalNameInit() {
		if(!patientFormalNameWrap.alreadyInitialized) {
			_patientFormalName(patientFormalNameWrap);
			if(patientFormalName == null)
				setPatientFormalName(patientFormalNameWrap.o);
		}
		patientFormalNameWrap.alreadyInitialized(true);
		return (MedicalPatient)this;
	}

	public String solrPatientFormalName() {
		return patientFormalName;
	}

	public String strPatientFormalName() {
		return patientFormalName == null ? "" : patientFormalName;
	}

	public String jsonPatientFormalName() {
		return patientFormalName == null ? "" : patientFormalName;
	}

	public String nomAffichagePatientFormalName() {
		return "formal name";
	}

	public String htmTooltipPatientFormalName() {
		return null;
	}

	public String htmPatientFormalName() {
		return patientFormalName == null ? "" : StringEscapeUtils.escapeHtml4(strPatientFormalName());
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.patient.MedicalPatient&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:patientBirthDate">Trouver l'entité patientBirthDate dans Solr</a>
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
	public MedicalPatient setPatientBirthDate(Instant o) {
		this.patientBirthDate = o == null ? null : LocalDate.from(o);
		this.patientBirthDateWrap.alreadyInitialized = true;
		return (MedicalPatient)this;
	}
	/** Example: 2011-12-03+01:00 **/
	public MedicalPatient setPatientBirthDate(String o) {
		this.patientBirthDate = o == null ? null : LocalDate.parse(o, DateTimeFormatter.ISO_DATE);
		this.patientBirthDateWrap.alreadyInitialized = true;
		return (MedicalPatient)this;
	}
	public MedicalPatient setPatientBirthDate(Date o) {
		this.patientBirthDate = o == null ? null : o.toInstant().atZone(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toLocalDate();
		this.patientBirthDateWrap.alreadyInitialized = true;
		return (MedicalPatient)this;
	}
	protected MedicalPatient patientBirthDateInit() {
		if(!patientBirthDateWrap.alreadyInitialized) {
			_patientBirthDate(patientBirthDateWrap);
			if(patientBirthDate == null)
				setPatientBirthDate(patientBirthDateWrap.o);
		}
		patientBirthDateWrap.alreadyInitialized(true);
		return (MedicalPatient)this;
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
		return "birth date";
	}

	public String htmTooltipPatientBirthDate() {
		return null;
	}

	public String htmPatientBirthDate() {
		return patientBirthDate == null ? "" : StringEscapeUtils.escapeHtml4(strPatientBirthDate());
	}

	public void inputPatientBirthDate(String classApiMethodMethod) {
		MedicalPatient s = (MedicalPatient)this;
		if(
				userKeys.contains(siteRequest_.getUserKey())
				|| Objects.equals(sessionId, siteRequest_.getSessionId())
				|| CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
		) {
			e("input")
				.a("type", "text")
				.a("class", "w3-input w3-border datepicker setPatientBirthDate classMedicalPatient inputMedicalPatient", pk, "PatientBirthDate w3-input w3-border ")
				.a("placeholder", "MM/DD/YYYY")
				.a("data-timeformat", "MM/dd/yyyy")
				.a("id", classApiMethodMethod, "_patientBirthDate")
				.a("onclick", "removeGlow($(this)); ")
				.a("value", patientBirthDate == null ? "" : DateTimeFormatter.ofPattern("MM/dd/yyyy").format(patientBirthDate))
				.a("onchange", "var t = moment(this.value, 'MM/DD/YYYY'); if(t) { var s = t.format('YYYY-MM-DD'); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setPatientBirthDate', s, function() { addGlow($('#", classApiMethodMethod, "_patientBirthDate')); }, function() { addError($('#", classApiMethodMethod, "_patientBirthDate')); }); } ")
				.fg();
		} else {
			sx(htmPatientBirthDate());
		}
	}

	public void htmPatientBirthDate(String classApiMethodMethod) {
		MedicalPatient s = (MedicalPatient)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalPatientPatientBirthDate").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-orange ").f();
							e("label").a("for", classApiMethodMethod, "_patientBirthDate").a("class", "").f().sx("birth date").g("label");
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
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-orange ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_patientBirthDate')); $('#", classApiMethodMethod, "_patientBirthDate').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#MedicalPatientForm :input[name=pk]').val() }], 'setPatientBirthDate', null, function() { addGlow($('#", classApiMethodMethod, "_patientBirthDate')); }, function() { addError($('#", classApiMethodMethod, "_patientBirthDate')); }); ")
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
	// patientBirthDateYear //
	//////////////////////////

	/**	L'entité « patientBirthDateYear »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer patientBirthDateYear;
	@JsonIgnore
	public Wrap<Integer> patientBirthDateYearWrap = new Wrap<Integer>().p(this).c(Integer.class).var("patientBirthDateYear").o(patientBirthDateYear);

	/**	<br/>L'entité « patientBirthDateYear »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.patient.MedicalPatient&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:patientBirthDateYear">Trouver l'entité patientBirthDateYear dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patientBirthDateYear(Wrap<Integer> c);

	public Integer getPatientBirthDateYear() {
		return patientBirthDateYear;
	}

	public void setPatientBirthDateYear(Integer patientBirthDateYear) {
		this.patientBirthDateYear = patientBirthDateYear;
		this.patientBirthDateYearWrap.alreadyInitialized = true;
	}
	public MedicalPatient setPatientBirthDateYear(String o) {
		if(NumberUtils.isParsable(o))
			this.patientBirthDateYear = Integer.parseInt(o);
		this.patientBirthDateYearWrap.alreadyInitialized = true;
		return (MedicalPatient)this;
	}
	protected MedicalPatient patientBirthDateYearInit() {
		if(!patientBirthDateYearWrap.alreadyInitialized) {
			_patientBirthDateYear(patientBirthDateYearWrap);
			if(patientBirthDateYear == null)
				setPatientBirthDateYear(patientBirthDateYearWrap.o);
		}
		patientBirthDateYearWrap.alreadyInitialized(true);
		return (MedicalPatient)this;
	}

	public Integer solrPatientBirthDateYear() {
		return patientBirthDateYear;
	}

	public String strPatientBirthDateYear() {
		return patientBirthDateYear == null ? "" : patientBirthDateYear.toString();
	}

	public String jsonPatientBirthDateYear() {
		return patientBirthDateYear == null ? "" : patientBirthDateYear.toString();
	}

	public String nomAffichagePatientBirthDateYear() {
		return null;
	}

	public String htmTooltipPatientBirthDateYear() {
		return null;
	}

	public String htmPatientBirthDateYear() {
		return patientBirthDateYear == null ? "" : StringEscapeUtils.escapeHtml4(strPatientBirthDateYear());
	}

	/////////////////////////////////
	// patientBirthDateMonthOfYear //
	/////////////////////////////////

	/**	L'entité « patientBirthDateMonthOfYear »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String patientBirthDateMonthOfYear;
	@JsonIgnore
	public Wrap<String> patientBirthDateMonthOfYearWrap = new Wrap<String>().p(this).c(String.class).var("patientBirthDateMonthOfYear").o(patientBirthDateMonthOfYear);

	/**	<br/>L'entité « patientBirthDateMonthOfYear »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.patient.MedicalPatient&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:patientBirthDateMonthOfYear">Trouver l'entité patientBirthDateMonthOfYear dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patientBirthDateMonthOfYear(Wrap<String> c);

	public String getPatientBirthDateMonthOfYear() {
		return patientBirthDateMonthOfYear;
	}

	public void setPatientBirthDateMonthOfYear(String patientBirthDateMonthOfYear) {
		this.patientBirthDateMonthOfYear = patientBirthDateMonthOfYear;
		this.patientBirthDateMonthOfYearWrap.alreadyInitialized = true;
	}
	protected MedicalPatient patientBirthDateMonthOfYearInit() {
		if(!patientBirthDateMonthOfYearWrap.alreadyInitialized) {
			_patientBirthDateMonthOfYear(patientBirthDateMonthOfYearWrap);
			if(patientBirthDateMonthOfYear == null)
				setPatientBirthDateMonthOfYear(patientBirthDateMonthOfYearWrap.o);
		}
		patientBirthDateMonthOfYearWrap.alreadyInitialized(true);
		return (MedicalPatient)this;
	}

	public String solrPatientBirthDateMonthOfYear() {
		return patientBirthDateMonthOfYear;
	}

	public String strPatientBirthDateMonthOfYear() {
		return patientBirthDateMonthOfYear == null ? "" : patientBirthDateMonthOfYear;
	}

	public String jsonPatientBirthDateMonthOfYear() {
		return patientBirthDateMonthOfYear == null ? "" : patientBirthDateMonthOfYear;
	}

	public String nomAffichagePatientBirthDateMonthOfYear() {
		return null;
	}

	public String htmTooltipPatientBirthDateMonthOfYear() {
		return null;
	}

	public String htmPatientBirthDateMonthOfYear() {
		return patientBirthDateMonthOfYear == null ? "" : StringEscapeUtils.escapeHtml4(strPatientBirthDateMonthOfYear());
	}

	///////////////////////////////
	// patientBirthDateDayOfWeek //
	///////////////////////////////

	/**	L'entité « patientBirthDateDayOfWeek »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String patientBirthDateDayOfWeek;
	@JsonIgnore
	public Wrap<String> patientBirthDateDayOfWeekWrap = new Wrap<String>().p(this).c(String.class).var("patientBirthDateDayOfWeek").o(patientBirthDateDayOfWeek);

	/**	<br/>L'entité « patientBirthDateDayOfWeek »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.patient.MedicalPatient&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:patientBirthDateDayOfWeek">Trouver l'entité patientBirthDateDayOfWeek dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patientBirthDateDayOfWeek(Wrap<String> c);

	public String getPatientBirthDateDayOfWeek() {
		return patientBirthDateDayOfWeek;
	}

	public void setPatientBirthDateDayOfWeek(String patientBirthDateDayOfWeek) {
		this.patientBirthDateDayOfWeek = patientBirthDateDayOfWeek;
		this.patientBirthDateDayOfWeekWrap.alreadyInitialized = true;
	}
	protected MedicalPatient patientBirthDateDayOfWeekInit() {
		if(!patientBirthDateDayOfWeekWrap.alreadyInitialized) {
			_patientBirthDateDayOfWeek(patientBirthDateDayOfWeekWrap);
			if(patientBirthDateDayOfWeek == null)
				setPatientBirthDateDayOfWeek(patientBirthDateDayOfWeekWrap.o);
		}
		patientBirthDateDayOfWeekWrap.alreadyInitialized(true);
		return (MedicalPatient)this;
	}

	public String solrPatientBirthDateDayOfWeek() {
		return patientBirthDateDayOfWeek;
	}

	public String strPatientBirthDateDayOfWeek() {
		return patientBirthDateDayOfWeek == null ? "" : patientBirthDateDayOfWeek;
	}

	public String jsonPatientBirthDateDayOfWeek() {
		return patientBirthDateDayOfWeek == null ? "" : patientBirthDateDayOfWeek;
	}

	public String nomAffichagePatientBirthDateDayOfWeek() {
		return null;
	}

	public String htmTooltipPatientBirthDateDayOfWeek() {
		return null;
	}

	public String htmPatientBirthDateDayOfWeek() {
		return patientBirthDateDayOfWeek == null ? "" : StringEscapeUtils.escapeHtml4(strPatientBirthDateDayOfWeek());
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
		patientFirstNameInit();
		patientFirstNamePreferredInit();
		familyNameInit();
		patientCompleteNameInit();
		patientCompleteNamePreferredInit();
		patientFormalNameInit();
		patientBirthDateInit();
		patientBirthDateYearInit();
		patientBirthDateMonthOfYearInit();
		patientBirthDateDayOfWeekInit();
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
			case "patientFirstName":
				return oMedicalPatient.patientFirstName;
			case "patientFirstNamePreferred":
				return oMedicalPatient.patientFirstNamePreferred;
			case "familyName":
				return oMedicalPatient.familyName;
			case "patientCompleteName":
				return oMedicalPatient.patientCompleteName;
			case "patientCompleteNamePreferred":
				return oMedicalPatient.patientCompleteNamePreferred;
			case "patientFormalName":
				return oMedicalPatient.patientFormalName;
			case "patientBirthDate":
				return oMedicalPatient.patientBirthDate;
			case "patientBirthDateYear":
				return oMedicalPatient.patientBirthDateYear;
			case "patientBirthDateMonthOfYear":
				return oMedicalPatient.patientBirthDateMonthOfYear;
			case "patientBirthDateDayOfWeek":
				return oMedicalPatient.patientBirthDateDayOfWeek;
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
				if(!saves.contains(var))
					saves.add(var);
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
			case "patientFirstName":
				if(val != null)
					setPatientFirstName(val);
				saves.add(var);
				return val;
			case "patientFirstNamePreferred":
				if(val != null)
					setPatientFirstNamePreferred(val);
				saves.add(var);
				return val;
			case "familyName":
				if(val != null)
					setFamilyName(val);
				saves.add(var);
				return val;
			case "patientBirthDate":
				if(val != null)
					setPatientBirthDate(val);
				saves.add(var);
				return val;
			default:
				return super.defineCluster(var, val);
		}
	}

	/////////////
	// populate //
	/////////////

	@Override public void populateForClass(SolrDocument solrDocument) {
		populateMedicalPatient(solrDocument);
	}
	public void populateMedicalPatient(SolrDocument solrDocument) {
		MedicalPatient oMedicalPatient = (MedicalPatient)this;
		saves = (List<String>)solrDocument.get("saves_stored_strings");
		if(saves != null) {

			if(saves.contains("patientKey")) {
				Long patientKey = (Long)solrDocument.get("patientKey_stored_long");
				if(patientKey != null)
					oMedicalPatient.setPatientKey(patientKey);
			}

			List<Long> enrollmentKeys = (List<Long>)solrDocument.get("enrollmentKeys_stored_longs");
			if(enrollmentKeys != null)
				oMedicalPatient.enrollmentKeys.addAll(enrollmentKeys);

			if(saves.contains("schoolSort")) {
				Integer schoolSort = (Integer)solrDocument.get("schoolSort_stored_int");
				if(schoolSort != null)
					oMedicalPatient.setSchoolSort(schoolSort);
			}

			if(saves.contains("userKeys")) {
				List<Long> userKeys = (List<Long>)solrDocument.get("userKeys_stored_longs");
				if(userKeys != null)
					oMedicalPatient.userKeys.addAll(userKeys);
			}

			if(saves.contains("clinicKeys")) {
				List<Long> clinicKeys = (List<Long>)solrDocument.get("clinicKeys_stored_longs");
				if(clinicKeys != null)
					oMedicalPatient.clinicKeys.addAll(clinicKeys);
			}

			if(saves.contains("patientFirstName")) {
				String patientFirstName = (String)solrDocument.get("patientFirstName_stored_string");
				if(patientFirstName != null)
					oMedicalPatient.setPatientFirstName(patientFirstName);
			}

			if(saves.contains("patientFirstNamePreferred")) {
				String patientFirstNamePreferred = (String)solrDocument.get("patientFirstNamePreferred_stored_string");
				if(patientFirstNamePreferred != null)
					oMedicalPatient.setPatientFirstNamePreferred(patientFirstNamePreferred);
			}

			if(saves.contains("familyName")) {
				String familyName = (String)solrDocument.get("familyName_stored_string");
				if(familyName != null)
					oMedicalPatient.setFamilyName(familyName);
			}

			if(saves.contains("patientCompleteName")) {
				String patientCompleteName = (String)solrDocument.get("patientCompleteName_stored_string");
				if(patientCompleteName != null)
					oMedicalPatient.setPatientCompleteName(patientCompleteName);
			}

			if(saves.contains("patientCompleteNamePreferred")) {
				String patientCompleteNamePreferred = (String)solrDocument.get("patientCompleteNamePreferred_stored_string");
				if(patientCompleteNamePreferred != null)
					oMedicalPatient.setPatientCompleteNamePreferred(patientCompleteNamePreferred);
			}

			if(saves.contains("patientFormalName")) {
				String patientFormalName = (String)solrDocument.get("patientFormalName_stored_string");
				if(patientFormalName != null)
					oMedicalPatient.setPatientFormalName(patientFormalName);
			}

			if(saves.contains("patientBirthDate")) {
				Date patientBirthDate = (Date)solrDocument.get("patientBirthDate_stored_date");
				if(patientBirthDate != null)
					oMedicalPatient.setPatientBirthDate(patientBirthDate);
			}

			if(saves.contains("patientBirthDateYear")) {
				Integer patientBirthDateYear = (Integer)solrDocument.get("patientBirthDateYear_stored_int");
				if(patientBirthDateYear != null)
					oMedicalPatient.setPatientBirthDateYear(patientBirthDateYear);
			}

			if(saves.contains("patientBirthDateMonthOfYear")) {
				String patientBirthDateMonthOfYear = (String)solrDocument.get("patientBirthDateMonthOfYear_stored_string");
				if(patientBirthDateMonthOfYear != null)
					oMedicalPatient.setPatientBirthDateMonthOfYear(patientBirthDateMonthOfYear);
			}

			if(saves.contains("patientBirthDateDayOfWeek")) {
				String patientBirthDateDayOfWeek = (String)solrDocument.get("patientBirthDateDayOfWeek_stored_string");
				if(patientBirthDateDayOfWeek != null)
					oMedicalPatient.setPatientBirthDateDayOfWeek(patientBirthDateDayOfWeek);
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
		if(patientFirstName != null) {
			document.addField("patientFirstName_indexed_string", patientFirstName);
			document.addField("patientFirstName_stored_string", patientFirstName);
		}
		if(patientFirstNamePreferred != null) {
			document.addField("patientFirstNamePreferred_indexed_string", patientFirstNamePreferred);
			document.addField("patientFirstNamePreferred_stored_string", patientFirstNamePreferred);
		}
		if(familyName != null) {
			document.addField("familyName_indexed_string", familyName);
			document.addField("familyName_stored_string", familyName);
		}
		if(patientCompleteName != null) {
			document.addField("patientCompleteName_indexed_string", patientCompleteName);
			document.addField("patientCompleteName_stored_string", patientCompleteName);
		}
		if(patientCompleteNamePreferred != null) {
			document.addField("patientCompleteNamePreferred_indexed_string", patientCompleteNamePreferred);
			document.addField("patientCompleteNamePreferred_stored_string", patientCompleteNamePreferred);
		}
		if(patientFormalName != null) {
			document.addField("patientFormalName_indexed_string", patientFormalName);
			document.addField("patientFormalName_stored_string", patientFormalName);
		}
		if(patientBirthDate != null) {
			document.addField("patientBirthDate_indexed_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(patientBirthDate.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
			document.addField("patientBirthDate_stored_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(patientBirthDate.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z"))));
		}
		if(patientBirthDateYear != null) {
			document.addField("patientBirthDateYear_indexed_int", patientBirthDateYear);
			document.addField("patientBirthDateYear_stored_int", patientBirthDateYear);
		}
		if(patientBirthDateMonthOfYear != null) {
			document.addField("patientBirthDateMonthOfYear_indexed_string", patientBirthDateMonthOfYear);
			document.addField("patientBirthDateMonthOfYear_stored_string", patientBirthDateMonthOfYear);
		}
		if(patientBirthDateDayOfWeek != null) {
			document.addField("patientBirthDateDayOfWeek_indexed_string", patientBirthDateDayOfWeek);
			document.addField("patientBirthDateDayOfWeek_stored_string", patientBirthDateDayOfWeek);
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
			case "patientFirstName":
				return "patientFirstName_indexed_string";
			case "patientFirstNamePreferred":
				return "patientFirstNamePreferred_indexed_string";
			case "familyName":
				return "familyName_indexed_string";
			case "patientCompleteName":
				return "patientCompleteName_indexed_string";
			case "patientCompleteNamePreferred":
				return "patientCompleteNamePreferred_indexed_string";
			case "patientFormalName":
				return "patientFormalName_indexed_string";
			case "patientBirthDate":
				return "patientBirthDate_indexed_date";
			case "patientBirthDateYear":
				return "patientBirthDateYear_indexed_int";
			case "patientBirthDateMonthOfYear":
				return "patientBirthDateMonthOfYear_indexed_string";
			case "patientBirthDateDayOfWeek":
				return "patientBirthDateDayOfWeek_indexed_string";
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

		String patientFirstName = (String)solrDocument.get("patientFirstName_stored_string");
		if(patientFirstName != null)
			oMedicalPatient.setPatientFirstName(patientFirstName);

		String patientFirstNamePreferred = (String)solrDocument.get("patientFirstNamePreferred_stored_string");
		if(patientFirstNamePreferred != null)
			oMedicalPatient.setPatientFirstNamePreferred(patientFirstNamePreferred);

		String familyName = (String)solrDocument.get("familyName_stored_string");
		if(familyName != null)
			oMedicalPatient.setFamilyName(familyName);

		String patientCompleteName = (String)solrDocument.get("patientCompleteName_stored_string");
		if(patientCompleteName != null)
			oMedicalPatient.setPatientCompleteName(patientCompleteName);

		String patientCompleteNamePreferred = (String)solrDocument.get("patientCompleteNamePreferred_stored_string");
		if(patientCompleteNamePreferred != null)
			oMedicalPatient.setPatientCompleteNamePreferred(patientCompleteNamePreferred);

		String patientFormalName = (String)solrDocument.get("patientFormalName_stored_string");
		if(patientFormalName != null)
			oMedicalPatient.setPatientFormalName(patientFormalName);

		Date patientBirthDate = (Date)solrDocument.get("patientBirthDate_stored_date");
		if(patientBirthDate != null)
			oMedicalPatient.setPatientBirthDate(patientBirthDate);

		Integer patientBirthDateYear = (Integer)solrDocument.get("patientBirthDateYear_stored_int");
		if(patientBirthDateYear != null)
			oMedicalPatient.setPatientBirthDateYear(patientBirthDateYear);

		String patientBirthDateMonthOfYear = (String)solrDocument.get("patientBirthDateMonthOfYear_stored_string");
		if(patientBirthDateMonthOfYear != null)
			oMedicalPatient.setPatientBirthDateMonthOfYear(patientBirthDateMonthOfYear);

		String patientBirthDateDayOfWeek = (String)solrDocument.get("patientBirthDateDayOfWeek_stored_string");
		if(patientBirthDateDayOfWeek != null)
			oMedicalPatient.setPatientBirthDateDayOfWeek(patientBirthDateDayOfWeek);

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
			if(!Objects.equals(patientFirstName, original.getPatientFirstName()))
				apiRequest.addVars("patientFirstName");
			if(!Objects.equals(patientFirstNamePreferred, original.getPatientFirstNamePreferred()))
				apiRequest.addVars("patientFirstNamePreferred");
			if(!Objects.equals(familyName, original.getFamilyName()))
				apiRequest.addVars("familyName");
			if(!Objects.equals(patientBirthDate, original.getPatientBirthDate()))
				apiRequest.addVars("patientBirthDate");
			super.apiRequestCluster();
		}
	}

	//////////////
	// hashCode //
	//////////////

	@Override public int hashCode() {
		return Objects.hash(super.hashCode(), enrollmentKeys, patientFirstName, patientFirstNamePreferred, familyName, patientBirthDate);
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
				&& Objects.equals( patientFirstName, that.patientFirstName )
				&& Objects.equals( patientFirstNamePreferred, that.patientFirstNamePreferred )
				&& Objects.equals( familyName, that.familyName )
				&& Objects.equals( patientBirthDate, that.patientBirthDate );
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("MedicalPatient { ");
		sb.append( "enrollmentKeys: " ).append(enrollmentKeys);
		sb.append( ", patientFirstName: \"" ).append(patientFirstName).append( "\"" );
		sb.append( ", patientFirstNamePreferred: \"" ).append(patientFirstNamePreferred).append( "\"" );
		sb.append( ", familyName: \"" ).append(familyName).append( "\"" );
		sb.append( ", patientBirthDate: " ).append(patientBirthDate);
		sb.append(" }");
		return sb.toString();
	}
}
