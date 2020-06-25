package org.computate.medicale.enUS.clinic;

import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.computate.medicale.enUS.cluster.Cluster;
import java.util.Date;
import org.computate.medicale.enUS.request.api.ApiRequest;
import java.util.HashMap;
import org.computate.medicale.enUS.enrollment.MedicalEnrollment;
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
	public static final String MedicalClinic_IconName = "clinic-medical";

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

	/////////////////
	// patientKeys //
	/////////////////

	/**	L'entité « patientKeys »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> patientKeys = new ArrayList<Long>();
	@JsonIgnore
	public Wrap<List<Long>> patientKeysWrap = new Wrap<List<Long>>().p(this).c(List.class).var("patientKeys").o(patientKeys);

	/**	<br/>L'entité « patientKeys »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.clinic.MedicalClinic&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:patientKeys">Trouver l'entité patientKeys dans Solr</a>
	 * <br/>
	 * @param patientKeys est l'entité déjà construit. 
	 **/
	protected abstract void _patientKeys(List<Long> o);

	public List<Long> getPatientKeys() {
		return patientKeys;
	}

	public void setPatientKeys(List<Long> patientKeys) {
		this.patientKeys = patientKeys;
		this.patientKeysWrap.alreadyInitialized = true;
	}
	public MedicalClinic addPatientKeys(Long...objets) {
		for(Long o : objets) {
			addPatientKeys(o);
		}
		return (MedicalClinic)this;
	}
	public MedicalClinic addPatientKeys(Long o) {
		if(o != null && !patientKeys.contains(o))
			this.patientKeys.add(o);
		return (MedicalClinic)this;
	}
	public MedicalClinic setPatientKeys(JsonArray objets) {
		patientKeys.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addPatientKeys(o);
		}
		return (MedicalClinic)this;
	}
	public MedicalClinic addPatientKeys(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addPatientKeys(p);
		}
		return (MedicalClinic)this;
	}
	protected MedicalClinic patientKeysInit() {
		if(!patientKeysWrap.alreadyInitialized) {
			_patientKeys(patientKeys);
		}
		patientKeysWrap.alreadyInitialized(true);
		return (MedicalClinic)this;
	}

	public List<Long> solrPatientKeys() {
		return patientKeys;
	}

	public String strPatientKeys() {
		return patientKeys == null ? "" : patientKeys.toString();
	}

	public String jsonPatientKeys() {
		return patientKeys == null ? "" : patientKeys.toString();
	}

	public String nomAffichagePatientKeys() {
		return "";
	}

	public String htmTooltipPatientKeys() {
		return null;
	}

	public String htmPatientKeys() {
		return patientKeys == null ? "" : StringEscapeUtils.escapeHtml4(strPatientKeys());
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
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setClinicName', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_clinicName')); }, function() { addError($('#", classApiMethodMethod, "_clinicName')); }); ");
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_clinicName')); $('#", classApiMethodMethod, "_clinicName').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#MedicalClinicForm :input[name=pk]').val() }], 'setClinicName', null, function() { addGlow($('#", classApiMethodMethod, "_clinicName')); }, function() { addError($('#", classApiMethodMethod, "_clinicName')); }); ")
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
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setClinicPhoneNumber', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_clinicPhoneNumber')); }, function() { addError($('#", classApiMethodMethod, "_clinicPhoneNumber')); }); ");
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_clinicPhoneNumber')); $('#", classApiMethodMethod, "_clinicPhoneNumber').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#MedicalClinicForm :input[name=pk]').val() }], 'setClinicPhoneNumber', null, function() { addGlow($('#", classApiMethodMethod, "_clinicPhoneNumber')); }, function() { addError($('#", classApiMethodMethod, "_clinicPhoneNumber')); }); ")
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
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setClinicAdministratorName', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_clinicAdministratorName')); }, function() { addError($('#", classApiMethodMethod, "_clinicAdministratorName')); }); ");
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_clinicAdministratorName')); $('#", classApiMethodMethod, "_clinicAdministratorName').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#MedicalClinicForm :input[name=pk]').val() }], 'setClinicAdministratorName', null, function() { addGlow($('#", classApiMethodMethod, "_clinicAdministratorName')); }, function() { addError($('#", classApiMethodMethod, "_clinicAdministratorName')); }); ")
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
	// clinicEmail //
	/////////////////

	/**	L'entité « clinicEmail »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String clinicEmail;
	@JsonIgnore
	public Wrap<String> clinicEmailWrap = new Wrap<String>().p(this).c(String.class).var("clinicEmail").o(clinicEmail);

	/**	<br/>L'entité « clinicEmail »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.clinic.MedicalClinic&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinicEmail">Trouver l'entité clinicEmail dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _clinicEmail(Wrap<String> c);

	public String getClinicEmail() {
		return clinicEmail;
	}

	public void setClinicEmail(String clinicEmail) {
		this.clinicEmail = clinicEmail;
		this.clinicEmailWrap.alreadyInitialized = true;
	}
	protected MedicalClinic clinicEmailInit() {
		if(!clinicEmailWrap.alreadyInitialized) {
			_clinicEmail(clinicEmailWrap);
			if(clinicEmail == null)
				setClinicEmail(clinicEmailWrap.o);
		}
		clinicEmailWrap.alreadyInitialized(true);
		return (MedicalClinic)this;
	}

	public String solrClinicEmail() {
		return clinicEmail;
	}

	public String strClinicEmail() {
		return clinicEmail == null ? "" : clinicEmail;
	}

	public String jsonClinicEmail() {
		return clinicEmail == null ? "" : clinicEmail;
	}

	public String nomAffichageClinicEmail() {
		return "email";
	}

	public String htmTooltipClinicEmail() {
		return null;
	}

	public String htmClinicEmail() {
		return clinicEmail == null ? "" : StringEscapeUtils.escapeHtml4(strClinicEmail());
	}

	public void inputClinicEmail(String classApiMethodMethod) {
		MedicalClinic s = (MedicalClinic)this;
		{
			e("input")
				.a("type", "text")
				.a("placeholder", "email")
				.a("id", classApiMethodMethod, "_clinicEmail");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setClinicEmail classMedicalClinic inputMedicalClinic", pk, "ClinicEmail w3-input w3-border ");
					a("name", "setClinicEmail");
				} else {
					a("class", "valueClinicEmail w3-input w3-border classMedicalClinic inputMedicalClinic", pk, "ClinicEmail w3-input w3-border ");
					a("name", "clinicEmail");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setClinicEmail', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_clinicEmail')); }, function() { addError($('#", classApiMethodMethod, "_clinicEmail')); }); ");
				}
				a("value", strClinicEmail())
			.fg();

		}
	}

	public void htmClinicEmail(String classApiMethodMethod) {
		MedicalClinic s = (MedicalClinic)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalClinicClinicEmail").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pink ").f();
							e("label").a("for", classApiMethodMethod, "_clinicEmail").a("class", "").f().sx("email").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputClinicEmail(classApiMethodMethod);
							} g("div");
							{
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pink ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_clinicEmail')); $('#", classApiMethodMethod, "_clinicEmail').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#MedicalClinicForm :input[name=pk]').val() }], 'setClinicEmail', null, function() { addGlow($('#", classApiMethodMethod, "_clinicEmail')); }, function() { addError($('#", classApiMethodMethod, "_clinicEmail')); }); ")
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
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setClinicEmailFrom', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_clinicEmailFrom')); }, function() { addError($('#", classApiMethodMethod, "_clinicEmailFrom')); }); ");
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_clinicEmailFrom')); $('#", classApiMethodMethod, "_clinicEmailFrom').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#MedicalClinicForm :input[name=pk]').val() }], 'setClinicEmailFrom', null, function() { addGlow($('#", classApiMethodMethod, "_clinicEmailFrom')); }, function() { addError($('#", classApiMethodMethod, "_clinicEmailFrom')); }); ")
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
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setClinicEmailTo', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_clinicEmailTo')); }, function() { addError($('#", classApiMethodMethod, "_clinicEmailTo')); }); ");
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_clinicEmailTo')); $('#", classApiMethodMethod, "_clinicEmailTo').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#MedicalClinicForm :input[name=pk]').val() }], 'setClinicEmailTo', null, function() { addGlow($('#", classApiMethodMethod, "_clinicEmailTo')); }, function() { addError($('#", classApiMethodMethod, "_clinicEmailTo')); }); ")
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
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setClinicLocation', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_clinicLocation')); }, function() { addError($('#", classApiMethodMethod, "_clinicLocation')); }); ");
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_clinicLocation')); $('#", classApiMethodMethod, "_clinicLocation').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#MedicalClinicForm :input[name=pk]').val() }], 'setClinicLocation', null, function() { addGlow($('#", classApiMethodMethod, "_clinicLocation')); }, function() { addError($('#", classApiMethodMethod, "_clinicLocation')); }); ")
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
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setClinicAddress', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_clinicAddress')); }, function() { addError($('#", classApiMethodMethod, "_clinicAddress')); }); ");
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_clinicAddress')); $('#", classApiMethodMethod, "_clinicAddress').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#MedicalClinicForm :input[name=pk]').val() }], 'setClinicAddress', null, function() { addGlow($('#", classApiMethodMethod, "_clinicAddress')); }, function() { addError($('#", classApiMethodMethod, "_clinicAddress')); }); ")
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.clinic.MedicalClinic&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentKeys">Trouver l'entité enrollmentKeys dans Solr</a>
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
	public MedicalClinic addEnrollmentKeys(Long...objets) {
		for(Long o : objets) {
			addEnrollmentKeys(o);
		}
		return (MedicalClinic)this;
	}
	public MedicalClinic addEnrollmentKeys(Long o) {
		if(o != null && !enrollmentKeys.contains(o))
			this.enrollmentKeys.add(o);
		return (MedicalClinic)this;
	}
	public MedicalClinic setEnrollmentKeys(JsonArray objets) {
		enrollmentKeys.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addEnrollmentKeys(o);
		}
		return (MedicalClinic)this;
	}
	public MedicalClinic addEnrollmentKeys(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addEnrollmentKeys(p);
		}
		return (MedicalClinic)this;
	}
	protected MedicalClinic enrollmentKeysInit() {
		if(!enrollmentKeysWrap.alreadyInitialized) {
			_enrollmentKeys(enrollmentKeys);
		}
		enrollmentKeysWrap.alreadyInitialized(true);
		return (MedicalClinic)this;
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
		MedicalClinic s = (MedicalClinic)this;
		{
			e("i").a("class", "far fa-search w3-xxlarge w3-cell w3-cell-middle ").f().g("i");
				e("input")
					.a("type", "text")
					.a("placeholder", "enrollments")
					.a("class", "valueObjectSuggest suggestEnrollmentKeys w3-input w3-border w3-cell w3-cell-middle ")
					.a("name", "setEnrollmentKeys")
					.a("id", classApiMethodMethod, "_enrollmentKeys")
					.a("autocomplete", "off")
					.a("oninput", "suggestMedicalClinicEnrollmentKeys($(this).val() ? searchMedicalEnrollmentFilters($(this.parentElement)) : [", pk == null ? "" : "{'name':'fq','value':'clinicKey:" + pk + "'}", "], $('#listMedicalClinicEnrollmentKeys_", classApiMethodMethod, "'), ", pk, "); ")
				.fg();

		}
	}

	public void htmEnrollmentKeys(String classApiMethodMethod) {
		MedicalClinic s = (MedicalClinic)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "MedicalClinicEnrollmentKeys").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row ").f();
							{ e("a").a("href", "/enrollment?fq=clinicKey:", pk).a("class", "w3-cell w3-btn w3-center h4 w3-block h4 w3-blue-gray w3-hover-blue-gray ").f();
								e("i").a("class", "fas fa-notes-medical ").f().g("i");
								sx("enrollments");
							} g("a");
						} g("div");
						{ e("div").a("class", "w3-cell-row ").f();
							{ e("h5").a("class", "w3-cell ").f();
								sx("relate enrollments to this clinic");
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
								{ e("ul").a("class", "w3-ul w3-hoverable ").a("id", "listMedicalClinicEnrollmentKeys_", classApiMethodMethod).f();
								} g("ul");
								{
									{ e("div").a("class", "w3-cell-row ").f();
										e("button")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-blue-gray ")
											.a("id", classApiMethodMethod, "_enrollmentKeys_add")
											.a("onclick", "$(this).addClass('w3-disabled'); this.disabled = true; this.innerHTML = 'Sending…'; postMedicalEnrollmentVals({ clinicKey: \"", pk, "\" }, function() {}, function() { addError($('#", classApiMethodMethod, "enrollmentKeys')); });")
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
		patientKeysInit();
		educationSortInit();
		clinicSortInit();
		clinicNameInit();
		clinicPhoneNumberInit();
		clinicAdministratorNameInit();
		clinicEmailInit();
		clinicEmailFromInit();
		clinicEmailToInit();
		clinicLocationInit();
		clinicAddressInit();
		enrollmentKeysInit();
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
			case "patientKeys":
				return oMedicalClinic.patientKeys;
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
			case "clinicEmail":
				return oMedicalClinic.clinicEmail;
			case "clinicEmailFrom":
				return oMedicalClinic.clinicEmailFrom;
			case "clinicEmailTo":
				return oMedicalClinic.clinicEmailTo;
			case "clinicLocation":
				return oMedicalClinic.clinicLocation;
			case "clinicAddress":
				return oMedicalClinic.clinicAddress;
			case "enrollmentKeys":
				return oMedicalClinic.enrollmentKeys;
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
			case "enrollmentKeys":
				oMedicalClinic.addEnrollmentKeys((Long)val);
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
				saves.add(var);
				return val;
			case "clinicPhoneNumber":
				if(val != null)
					setClinicPhoneNumber(val);
				saves.add(var);
				return val;
			case "clinicAdministratorName":
				if(val != null)
					setClinicAdministratorName(val);
				saves.add(var);
				return val;
			case "clinicEmail":
				if(val != null)
					setClinicEmail(val);
				saves.add(var);
				return val;
			case "clinicEmailFrom":
				if(val != null)
					setClinicEmailFrom(val);
				saves.add(var);
				return val;
			case "clinicEmailTo":
				if(val != null)
					setClinicEmailTo(val);
				saves.add(var);
				return val;
			case "clinicLocation":
				if(val != null)
					setClinicLocation(val);
				saves.add(var);
				return val;
			case "clinicAddress":
				if(val != null)
					setClinicAddress(val);
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
		populateMedicalClinic(solrDocument);
	}
	public void populateMedicalClinic(SolrDocument solrDocument) {
		MedicalClinic oMedicalClinic = (MedicalClinic)this;
		saves = (List<String>)solrDocument.get("saves_stored_strings");
		if(saves != null) {

			if(saves.contains("clinicKey")) {
				Long clinicKey = (Long)solrDocument.get("clinicKey_stored_long");
				if(clinicKey != null)
					oMedicalClinic.setClinicKey(clinicKey);
			}

			if(saves.contains("patientKeys")) {
				List<Long> patientKeys = (List<Long>)solrDocument.get("patientKeys_stored_longs");
				if(patientKeys != null)
					oMedicalClinic.patientKeys.addAll(patientKeys);
			}

			if(saves.contains("educationSort")) {
				Integer educationSort = (Integer)solrDocument.get("educationSort_stored_int");
				if(educationSort != null)
					oMedicalClinic.setEducationSort(educationSort);
			}

			if(saves.contains("clinicSort")) {
				Integer clinicSort = (Integer)solrDocument.get("clinicSort_stored_int");
				if(clinicSort != null)
					oMedicalClinic.setClinicSort(clinicSort);
			}

			if(saves.contains("clinicName")) {
				String clinicName = (String)solrDocument.get("clinicName_stored_string");
				if(clinicName != null)
					oMedicalClinic.setClinicName(clinicName);
			}

			if(saves.contains("clinicPhoneNumber")) {
				String clinicPhoneNumber = (String)solrDocument.get("clinicPhoneNumber_stored_string");
				if(clinicPhoneNumber != null)
					oMedicalClinic.setClinicPhoneNumber(clinicPhoneNumber);
			}

			if(saves.contains("clinicAdministratorName")) {
				String clinicAdministratorName = (String)solrDocument.get("clinicAdministratorName_stored_string");
				if(clinicAdministratorName != null)
					oMedicalClinic.setClinicAdministratorName(clinicAdministratorName);
			}

			if(saves.contains("clinicEmail")) {
				String clinicEmail = (String)solrDocument.get("clinicEmail_stored_string");
				if(clinicEmail != null)
					oMedicalClinic.setClinicEmail(clinicEmail);
			}

			if(saves.contains("clinicEmailFrom")) {
				String clinicEmailFrom = (String)solrDocument.get("clinicEmailFrom_stored_string");
				if(clinicEmailFrom != null)
					oMedicalClinic.setClinicEmailFrom(clinicEmailFrom);
			}

			if(saves.contains("clinicEmailTo")) {
				String clinicEmailTo = (String)solrDocument.get("clinicEmailTo_stored_string");
				if(clinicEmailTo != null)
					oMedicalClinic.setClinicEmailTo(clinicEmailTo);
			}

			if(saves.contains("clinicLocation")) {
				String clinicLocation = (String)solrDocument.get("clinicLocation_stored_string");
				if(clinicLocation != null)
					oMedicalClinic.setClinicLocation(clinicLocation);
			}

			if(saves.contains("clinicAddress")) {
				String clinicAddress = (String)solrDocument.get("clinicAddress_stored_string");
				if(clinicAddress != null)
					oMedicalClinic.setClinicAddress(clinicAddress);
			}

			List<Long> enrollmentKeys = (List<Long>)solrDocument.get("enrollmentKeys_stored_longs");
			if(enrollmentKeys != null)
				oMedicalClinic.enrollmentKeys.addAll(enrollmentKeys);

			if(saves.contains("clinicShortName")) {
				String clinicShortName = (String)solrDocument.get("clinicShortName_stored_string");
				if(clinicShortName != null)
					oMedicalClinic.setClinicShortName(clinicShortName);
			}

			if(saves.contains("clinicCompleteName")) {
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
		if(clinicKey != null) {
			document.addField("clinicKey_indexed_long", clinicKey);
			document.addField("clinicKey_stored_long", clinicKey);
		}
		if(patientKeys != null) {
			for(java.lang.Long o : patientKeys) {
				document.addField("patientKeys_indexed_longs", o);
			}
			for(java.lang.Long o : patientKeys) {
				document.addField("patientKeys_stored_longs", o);
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
		if(clinicEmail != null) {
			document.addField("clinicEmail_indexed_string", clinicEmail);
			document.addField("clinicEmail_stored_string", clinicEmail);
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
		if(enrollmentKeys != null) {
			for(java.lang.Long o : enrollmentKeys) {
				document.addField("enrollmentKeys_indexed_longs", o);
			}
			for(java.lang.Long o : enrollmentKeys) {
				document.addField("enrollmentKeys_stored_longs", o);
			}
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
			case "patientKeys":
				return "patientKeys_indexed_longs";
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
			case "clinicEmail":
				return "clinicEmail_indexed_string";
			case "clinicEmailFrom":
				return "clinicEmailFrom_indexed_string";
			case "clinicEmailTo":
				return "clinicEmailTo_indexed_string";
			case "clinicLocation":
				return "clinicLocation_indexed_string";
			case "clinicAddress":
				return "clinicAddress_indexed_string";
			case "enrollmentKeys":
				return "enrollmentKeys_indexed_longs";
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

		List<Long> patientKeys = (List<Long>)solrDocument.get("patientKeys_stored_longs");
		if(patientKeys != null)
			oMedicalClinic.patientKeys.addAll(patientKeys);

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

		String clinicEmail = (String)solrDocument.get("clinicEmail_stored_string");
		if(clinicEmail != null)
			oMedicalClinic.setClinicEmail(clinicEmail);

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

		List<Long> enrollmentKeys = (List<Long>)solrDocument.get("enrollmentKeys_stored_longs");
		if(enrollmentKeys != null)
			oMedicalClinic.enrollmentKeys.addAll(enrollmentKeys);

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
			if(!Objects.equals(clinicEmail, original.getClinicEmail()))
				apiRequest.addVars("clinicEmail");
			if(!Objects.equals(clinicEmailFrom, original.getClinicEmailFrom()))
				apiRequest.addVars("clinicEmailFrom");
			if(!Objects.equals(clinicEmailTo, original.getClinicEmailTo()))
				apiRequest.addVars("clinicEmailTo");
			if(!Objects.equals(clinicLocation, original.getClinicLocation()))
				apiRequest.addVars("clinicLocation");
			if(!Objects.equals(clinicAddress, original.getClinicAddress()))
				apiRequest.addVars("clinicAddress");
			if(!Objects.equals(enrollmentKeys, original.getEnrollmentKeys()))
				apiRequest.addVars("enrollmentKeys");
			super.apiRequestCluster();
		}
	}

	//////////////
	// hashCode //
	//////////////

	@Override public int hashCode() {
		return Objects.hash(super.hashCode(), clinicName, clinicPhoneNumber, clinicAdministratorName, clinicEmail, clinicEmailFrom, clinicEmailTo, clinicLocation, clinicAddress, enrollmentKeys);
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
				&& Objects.equals( clinicEmail, that.clinicEmail )
				&& Objects.equals( clinicEmailFrom, that.clinicEmailFrom )
				&& Objects.equals( clinicEmailTo, that.clinicEmailTo )
				&& Objects.equals( clinicLocation, that.clinicLocation )
				&& Objects.equals( clinicAddress, that.clinicAddress )
				&& Objects.equals( enrollmentKeys, that.enrollmentKeys );
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
		sb.append( ", clinicEmail: \"" ).append(clinicEmail).append( "\"" );
		sb.append( ", clinicEmailFrom: \"" ).append(clinicEmailFrom).append( "\"" );
		sb.append( ", clinicEmailTo: \"" ).append(clinicEmailTo).append( "\"" );
		sb.append( ", clinicLocation: \"" ).append(clinicLocation).append( "\"" );
		sb.append( ", clinicAddress: \"" ).append(clinicAddress).append( "\"" );
		sb.append( ", enrollmentKeys: " ).append(enrollmentKeys);
		sb.append(" }");
		return sb.toString();
	}
}
