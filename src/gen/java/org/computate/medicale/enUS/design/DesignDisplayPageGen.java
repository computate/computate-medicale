package org.computate.medicale.enUS.design;

import org.computate.medicale.enUS.writer.AllWriter;
import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.Date;
import org.computate.medicale.enUS.enrollment.MedicalEnrollment;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
import java.text.NumberFormat;
import io.vertx.core.logging.LoggerFactory;
import java.util.ArrayList;
import org.computate.medicale.enUS.request.SiteRequestEnUS;
import org.apache.commons.collections.CollectionUtils;
import java.lang.Long;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.util.Locale;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.lang.String;
import java.time.ZoneOffset;
import org.computate.medicale.enUS.request.api.ApiRequest;
import io.vertx.core.logging.Logger;
import org.computate.medicale.enUS.html.part.HtmlPart;
import org.computate.medicale.enUS.wrap.Wrap;
import java.math.MathContext;
import org.computate.medicale.enUS.clinic.MedicalClinic;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Objects;
import io.vertx.core.json.JsonArray;
import org.computate.medicale.enUS.search.SearchList;
import java.util.List;
import java.time.temporal.ChronoUnit;
import org.computate.medicale.enUS.design.DesignDisplayGenPage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.math.NumberUtils;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.computate.medicale.enUS.design.PageDesign;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.computate.medicale.enUS.cluster.Cluster;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

/**	
 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignDisplayPage&fq=classeEtendGen_indexed_boolean:true">Trouver la classe  dans Solr</a>
 * <br/>
 **/
public abstract class DesignDisplayPageGen<DEV> extends DesignDisplayGenPage {
	protected static final Logger LOGGER = LoggerFactory.getLogger(DesignDisplayPage.class);

	////////////////
	// pageDesign //
	////////////////

	/**	L'entité « pageDesign »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected PageDesign pageDesign;
	@JsonIgnore
	public Wrap<PageDesign> pageDesignWrap = new Wrap<PageDesign>().p(this).c(PageDesign.class).var("pageDesign").o(pageDesign);

	/**	<br/>L'entité « pageDesign »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignDisplayPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageDesign">Trouver l'entité pageDesign dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _pageDesign(Wrap<PageDesign> c);

	public PageDesign getPageDesign() {
		return pageDesign;
	}

	public void setPageDesign(PageDesign pageDesign) {
		this.pageDesign = pageDesign;
		this.pageDesignWrap.alreadyInitialized = true;
	}
	protected DesignDisplayPage pageDesignInit() {
		if(!pageDesignWrap.alreadyInitialized) {
			_pageDesign(pageDesignWrap);
			if(pageDesign == null)
				setPageDesign(pageDesignWrap.o);
		}
		if(pageDesign != null)
			pageDesign.initDeepForClass(siteRequest_);
		pageDesignWrap.alreadyInitialized(true);
		return (DesignDisplayPage)this;
	}

	//////////////
	// designId //
	//////////////

	/**	L'entité « designId »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String designId;
	@JsonIgnore
	public Wrap<String> designIdWrap = new Wrap<String>().p(this).c(String.class).var("designId").o(designId);

	/**	<br/>L'entité « designId »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignDisplayPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:designId">Trouver l'entité designId dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _designId(Wrap<String> c);

	public String getDesignId() {
		return designId;
	}

	public void setDesignId(String designId) {
		this.designId = designId;
		this.designIdWrap.alreadyInitialized = true;
	}
	protected DesignDisplayPage designIdInit() {
		if(!designIdWrap.alreadyInitialized) {
			_designId(designIdWrap);
			if(designId == null)
				setDesignId(designIdWrap.o);
		}
		designIdWrap.alreadyInitialized(true);
		return (DesignDisplayPage)this;
	}

	public String solrDesignId() {
		return designId;
	}

	public String strDesignId() {
		return designId == null ? "" : designId;
	}

	public String jsonDesignId() {
		return designId == null ? "" : designId;
	}

	public String nomAffichageDesignId() {
		return null;
	}

	public String htmTooltipDesignId() {
		return null;
	}

	public String htmDesignId() {
		return designId == null ? "" : StringEscapeUtils.escapeHtml4(strDesignId());
	}

	//////////////////////
	// enrollmentSearch //
	//////////////////////

	/**	L'entité « enrollmentSearch »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut SearchList<MedicalEnrollment>(). 
	 */
	@JsonInclude(Include.NON_NULL)
	protected SearchList<MedicalEnrollment> enrollmentSearch = new SearchList<MedicalEnrollment>();
	@JsonIgnore
	public Wrap<SearchList<MedicalEnrollment>> enrollmentSearchWrap = new Wrap<SearchList<MedicalEnrollment>>().p(this).c(SearchList.class).var("enrollmentSearch").o(enrollmentSearch);

	/**	<br/>L'entité « enrollmentSearch »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut SearchList<MedicalEnrollment>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignDisplayPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentSearch">Trouver l'entité enrollmentSearch dans Solr</a>
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
	protected DesignDisplayPage enrollmentSearchInit() {
		if(!enrollmentSearchWrap.alreadyInitialized) {
			_enrollmentSearch(enrollmentSearch);
		}
		enrollmentSearch.initDeepForClass(siteRequest_);
		enrollmentSearchWrap.alreadyInitialized(true);
		return (DesignDisplayPage)this;
	}

	///////////////////////
	// patientEnrollment //
	///////////////////////

	/**	L'entité « patientEnrollment »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected MedicalEnrollment patientEnrollment;
	@JsonIgnore
	public Wrap<MedicalEnrollment> patientEnrollmentWrap = new Wrap<MedicalEnrollment>().p(this).c(MedicalEnrollment.class).var("patientEnrollment").o(patientEnrollment);

	/**	<br/>L'entité « patientEnrollment »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignDisplayPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:patientEnrollment">Trouver l'entité patientEnrollment dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patientEnrollment(Wrap<MedicalEnrollment> c);

	public MedicalEnrollment getPatientEnrollment() {
		return patientEnrollment;
	}

	public void setPatientEnrollment(MedicalEnrollment patientEnrollment) {
		this.patientEnrollment = patientEnrollment;
		this.patientEnrollmentWrap.alreadyInitialized = true;
	}
	protected DesignDisplayPage patientEnrollmentInit() {
		if(!patientEnrollmentWrap.alreadyInitialized) {
			_patientEnrollment(patientEnrollmentWrap);
			if(patientEnrollment == null)
				setPatientEnrollment(patientEnrollmentWrap.o);
		}
		if(patientEnrollment != null)
			patientEnrollment.initDeepForClass(siteRequest_);
		patientEnrollmentWrap.alreadyInitialized(true);
		return (DesignDisplayPage)this;
	}

	/////////////////
	// enrollments //
	/////////////////

	/**	L'entité « enrollments »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected List<MedicalEnrollment> enrollments;
	@JsonIgnore
	public Wrap<List<MedicalEnrollment>> enrollmentsWrap = new Wrap<List<MedicalEnrollment>>().p(this).c(List.class).var("enrollments").o(enrollments);

	/**	<br/>L'entité « enrollments »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignDisplayPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollments">Trouver l'entité enrollments dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _enrollments(Wrap<List<MedicalEnrollment>> c);

	public List<MedicalEnrollment> getEnrollments() {
		return enrollments;
	}

	public void setEnrollments(List<MedicalEnrollment> enrollments) {
		this.enrollments = enrollments;
		this.enrollmentsWrap.alreadyInitialized = true;
	}
	public DesignDisplayPage addEnrollments(MedicalEnrollment...objets) {
		for(MedicalEnrollment o : objets) {
			addEnrollments(o);
		}
		return (DesignDisplayPage)this;
	}
	public DesignDisplayPage addEnrollments(MedicalEnrollment o) {
		if(o != null && !enrollments.contains(o))
			this.enrollments.add(o);
		return (DesignDisplayPage)this;
	}
	protected DesignDisplayPage enrollmentsInit() {
		if(!enrollmentsWrap.alreadyInitialized) {
			_enrollments(enrollmentsWrap);
			if(enrollments == null)
				setEnrollments(enrollmentsWrap.o);
		}
		enrollmentsWrap.alreadyInitialized(true);
		return (DesignDisplayPage)this;
	}

	//////////////////////
	// enrollmentBlocks //
	//////////////////////

	/**	L'entité « enrollmentBlocks »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<MedicalEnrollment>(). 
	 */
	@JsonInclude(Include.NON_NULL)
	protected List<MedicalEnrollment> enrollmentBlocks = new ArrayList<MedicalEnrollment>();
	@JsonIgnore
	public Wrap<List<MedicalEnrollment>> enrollmentBlocksWrap = new Wrap<List<MedicalEnrollment>>().p(this).c(List.class).var("enrollmentBlocks").o(enrollmentBlocks);

	/**	<br/>L'entité « enrollmentBlocks »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<MedicalEnrollment>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignDisplayPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentBlocks">Trouver l'entité enrollmentBlocks dans Solr</a>
	 * <br/>
	 * @param enrollmentBlocks est l'entité déjà construit. 
	 **/
	protected abstract void _enrollmentBlocks(List<MedicalEnrollment> c);

	public List<MedicalEnrollment> getEnrollmentBlocks() {
		return enrollmentBlocks;
	}

	public void setEnrollmentBlocks(List<MedicalEnrollment> enrollmentBlocks) {
		this.enrollmentBlocks = enrollmentBlocks;
		this.enrollmentBlocksWrap.alreadyInitialized = true;
	}
	public DesignDisplayPage addEnrollmentBlocks(MedicalEnrollment...objets) {
		for(MedicalEnrollment o : objets) {
			addEnrollmentBlocks(o);
		}
		return (DesignDisplayPage)this;
	}
	public DesignDisplayPage addEnrollmentBlocks(MedicalEnrollment o) {
		if(o != null && !enrollmentBlocks.contains(o))
			this.enrollmentBlocks.add(o);
		return (DesignDisplayPage)this;
	}
	protected DesignDisplayPage enrollmentBlocksInit() {
		if(!enrollmentBlocksWrap.alreadyInitialized) {
			_enrollmentBlocks(enrollmentBlocks);
		}
		enrollmentBlocksWrap.alreadyInitialized(true);
		return (DesignDisplayPage)this;
	}

	/////////////////////
	// enrollmentBlock //
	/////////////////////

	/**	L'entité « enrollmentBlock »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected MedicalEnrollment enrollmentBlock;
	@JsonIgnore
	public Wrap<MedicalEnrollment> enrollmentBlockWrap = new Wrap<MedicalEnrollment>().p(this).c(MedicalEnrollment.class).var("enrollmentBlock").o(enrollmentBlock);

	/**	<br/>L'entité « enrollmentBlock »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignDisplayPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentBlock">Trouver l'entité enrollmentBlock dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _enrollmentBlock(Wrap<MedicalEnrollment> c);

	public MedicalEnrollment getEnrollmentBlock() {
		return enrollmentBlock;
	}

	public void setEnrollmentBlock(MedicalEnrollment enrollmentBlock) {
		this.enrollmentBlock = enrollmentBlock;
		this.enrollmentBlockWrap.alreadyInitialized = true;
	}
	protected DesignDisplayPage enrollmentBlockInit() {
		if(!enrollmentBlockWrap.alreadyInitialized) {
			_enrollmentBlock(enrollmentBlockWrap);
			if(enrollmentBlock == null)
				setEnrollmentBlock(enrollmentBlockWrap.o);
		}
		if(enrollmentBlock != null)
			enrollmentBlock.initDeepForClass(siteRequest_);
		enrollmentBlockWrap.alreadyInitialized(true);
		return (DesignDisplayPage)this;
	}

	//////////////////////////
	// enrollmentEnrollment //
	//////////////////////////

	/**	L'entité « enrollmentEnrollment »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected MedicalEnrollment enrollmentEnrollment;
	@JsonIgnore
	public Wrap<MedicalEnrollment> enrollmentEnrollmentWrap = new Wrap<MedicalEnrollment>().p(this).c(MedicalEnrollment.class).var("enrollmentEnrollment").o(enrollmentEnrollment);

	/**	<br/>L'entité « enrollmentEnrollment »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignDisplayPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentEnrollment">Trouver l'entité enrollmentEnrollment dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _enrollmentEnrollment(Wrap<MedicalEnrollment> c);

	public MedicalEnrollment getEnrollmentEnrollment() {
		return enrollmentEnrollment;
	}

	public void setEnrollmentEnrollment(MedicalEnrollment enrollmentEnrollment) {
		this.enrollmentEnrollment = enrollmentEnrollment;
		this.enrollmentEnrollmentWrap.alreadyInitialized = true;
	}
	protected DesignDisplayPage enrollmentEnrollmentInit() {
		if(!enrollmentEnrollmentWrap.alreadyInitialized) {
			_enrollmentEnrollment(enrollmentEnrollmentWrap);
			if(enrollmentEnrollment == null)
				setEnrollmentEnrollment(enrollmentEnrollmentWrap.o);
		}
		if(enrollmentEnrollment != null)
			enrollmentEnrollment.initDeepForClass(siteRequest_);
		enrollmentEnrollmentWrap.alreadyInitialized(true);
		return (DesignDisplayPage)this;
	}

	//////////////////
	// clinicSearch //
	//////////////////

	/**	L'entité « clinicSearch »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut SearchList<MedicalClinic>(). 
	 */
	@JsonInclude(Include.NON_NULL)
	protected SearchList<MedicalClinic> clinicSearch = new SearchList<MedicalClinic>();
	@JsonIgnore
	public Wrap<SearchList<MedicalClinic>> clinicSearchWrap = new Wrap<SearchList<MedicalClinic>>().p(this).c(SearchList.class).var("clinicSearch").o(clinicSearch);

	/**	<br/>L'entité « clinicSearch »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut SearchList<MedicalClinic>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignDisplayPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinicSearch">Trouver l'entité clinicSearch dans Solr</a>
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
	protected DesignDisplayPage clinicSearchInit() {
		if(!clinicSearchWrap.alreadyInitialized) {
			_clinicSearch(clinicSearch);
		}
		clinicSearch.initDeepForClass(siteRequest_);
		clinicSearchWrap.alreadyInitialized(true);
		return (DesignDisplayPage)this;
	}

	/////////////
	// clinic_ //
	/////////////

	/**	L'entité « clinic_ »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected MedicalClinic clinic_;
	@JsonIgnore
	public Wrap<MedicalClinic> clinic_Wrap = new Wrap<MedicalClinic>().p(this).c(MedicalClinic.class).var("clinic_").o(clinic_);

	/**	<br/>L'entité « clinic_ »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignDisplayPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinic_">Trouver l'entité clinic_ dans Solr</a>
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
	protected DesignDisplayPage clinic_Init() {
		if(!clinic_Wrap.alreadyInitialized) {
			_clinic_(clinic_Wrap);
			if(clinic_ == null)
				setClinic_(clinic_Wrap.o);
		}
		clinic_Wrap.alreadyInitialized(true);
		return (DesignDisplayPage)this;
	}

	///////////////
	// emailFrom //
	///////////////

	/**	L'entité « emailFrom »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String emailFrom;
	@JsonIgnore
	public Wrap<String> emailFromWrap = new Wrap<String>().p(this).c(String.class).var("emailFrom").o(emailFrom);

	/**	<br/>L'entité « emailFrom »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignDisplayPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:emailFrom">Trouver l'entité emailFrom dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _emailFrom(Wrap<String> c);

	public String getEmailFrom() {
		return emailFrom;
	}

	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
		this.emailFromWrap.alreadyInitialized = true;
	}
	protected DesignDisplayPage emailFromInit() {
		if(!emailFromWrap.alreadyInitialized) {
			_emailFrom(emailFromWrap);
			if(emailFrom == null)
				setEmailFrom(emailFromWrap.o);
		}
		emailFromWrap.alreadyInitialized(true);
		return (DesignDisplayPage)this;
	}

	public String solrEmailFrom() {
		return emailFrom;
	}

	public String strEmailFrom() {
		return emailFrom == null ? "" : emailFrom;
	}

	public String jsonEmailFrom() {
		return emailFrom == null ? "" : emailFrom;
	}

	public String nomAffichageEmailFrom() {
		return null;
	}

	public String htmTooltipEmailFrom() {
		return null;
	}

	public String htmEmailFrom() {
		return emailFrom == null ? "" : StringEscapeUtils.escapeHtml4(strEmailFrom());
	}

	////////////////////
	// emailToMedical //
	////////////////////

	/**	L'entité « emailToMedical »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String emailToMedical;
	@JsonIgnore
	public Wrap<String> emailToMedicalWrap = new Wrap<String>().p(this).c(String.class).var("emailToMedical").o(emailToMedical);

	/**	<br/>L'entité « emailToMedical »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignDisplayPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:emailToMedical">Trouver l'entité emailToMedical dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _emailToMedical(Wrap<String> c);

	public String getEmailToMedical() {
		return emailToMedical;
	}

	public void setEmailToMedical(String emailToMedical) {
		this.emailToMedical = emailToMedical;
		this.emailToMedicalWrap.alreadyInitialized = true;
	}
	protected DesignDisplayPage emailToMedicalInit() {
		if(!emailToMedicalWrap.alreadyInitialized) {
			_emailToMedical(emailToMedicalWrap);
			if(emailToMedical == null)
				setEmailToMedical(emailToMedicalWrap.o);
		}
		emailToMedicalWrap.alreadyInitialized(true);
		return (DesignDisplayPage)this;
	}

	public String solrEmailToMedical() {
		return emailToMedical;
	}

	public String strEmailToMedical() {
		return emailToMedical == null ? "" : emailToMedical;
	}

	public String jsonEmailToMedical() {
		return emailToMedical == null ? "" : emailToMedical;
	}

	public String nomAffichageEmailToMedical() {
		return null;
	}

	public String htmTooltipEmailToMedical() {
		return null;
	}

	public String htmEmailToMedical() {
		return emailToMedical == null ? "" : StringEscapeUtils.escapeHtml4(strEmailToMedical());
	}

	////////////////////
	// emailToAddress //
	////////////////////

	/**	L'entité « emailToAddress »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String emailToAddress;
	@JsonIgnore
	public Wrap<String> emailToAddressWrap = new Wrap<String>().p(this).c(String.class).var("emailToAddress").o(emailToAddress);

	/**	<br/>L'entité « emailToAddress »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignDisplayPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:emailToAddress">Trouver l'entité emailToAddress dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _emailToAddress(Wrap<String> c);

	public String getEmailToAddress() {
		return emailToAddress;
	}

	public void setEmailToAddress(String emailToAddress) {
		this.emailToAddress = emailToAddress;
		this.emailToAddressWrap.alreadyInitialized = true;
	}
	protected DesignDisplayPage emailToAddressInit() {
		if(!emailToAddressWrap.alreadyInitialized) {
			_emailToAddress(emailToAddressWrap);
			if(emailToAddress == null)
				setEmailToAddress(emailToAddressWrap.o);
		}
		emailToAddressWrap.alreadyInitialized(true);
		return (DesignDisplayPage)this;
	}

	public String solrEmailToAddress() {
		return emailToAddress;
	}

	public String strEmailToAddress() {
		return emailToAddress == null ? "" : emailToAddress;
	}

	public String jsonEmailToAddress() {
		return emailToAddress == null ? "" : emailToAddress;
	}

	public String nomAffichageEmailToAddress() {
		return null;
	}

	public String htmTooltipEmailToAddress() {
		return null;
	}

	public String htmEmailToAddress() {
		return emailToAddress == null ? "" : StringEscapeUtils.escapeHtml4(strEmailToAddress());
	}

	/////////////////
	// emailToName //
	/////////////////

	/**	L'entité « emailToName »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String emailToName;
	@JsonIgnore
	public Wrap<String> emailToNameWrap = new Wrap<String>().p(this).c(String.class).var("emailToName").o(emailToName);

	/**	<br/>L'entité « emailToName »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignDisplayPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:emailToName">Trouver l'entité emailToName dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _emailToName(Wrap<String> c);

	public String getEmailToName() {
		return emailToName;
	}

	public void setEmailToName(String emailToName) {
		this.emailToName = emailToName;
		this.emailToNameWrap.alreadyInitialized = true;
	}
	protected DesignDisplayPage emailToNameInit() {
		if(!emailToNameWrap.alreadyInitialized) {
			_emailToName(emailToNameWrap);
			if(emailToName == null)
				setEmailToName(emailToNameWrap.o);
		}
		emailToNameWrap.alreadyInitialized(true);
		return (DesignDisplayPage)this;
	}

	public String solrEmailToName() {
		return emailToName;
	}

	public String strEmailToName() {
		return emailToName == null ? "" : emailToName;
	}

	public String jsonEmailToName() {
		return emailToName == null ? "" : emailToName;
	}

	public String nomAffichageEmailToName() {
		return null;
	}

	public String htmTooltipEmailToName() {
		return null;
	}

	public String htmEmailToName() {
		return emailToName == null ? "" : StringEscapeUtils.escapeHtml4(strEmailToName());
	}

	//////////////////
	// emailMessage //
	//////////////////

	/**	L'entité « emailMessage »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String emailMessage;
	@JsonIgnore
	public Wrap<String> emailMessageWrap = new Wrap<String>().p(this).c(String.class).var("emailMessage").o(emailMessage);

	/**	<br/>L'entité « emailMessage »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignDisplayPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:emailMessage">Trouver l'entité emailMessage dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _emailMessage(Wrap<String> c);

	public String getEmailMessage() {
		return emailMessage;
	}

	public void setEmailMessage(String emailMessage) {
		this.emailMessage = emailMessage;
		this.emailMessageWrap.alreadyInitialized = true;
	}
	protected DesignDisplayPage emailMessageInit() {
		if(!emailMessageWrap.alreadyInitialized) {
			_emailMessage(emailMessageWrap);
			if(emailMessage == null)
				setEmailMessage(emailMessageWrap.o);
		}
		emailMessageWrap.alreadyInitialized(true);
		return (DesignDisplayPage)this;
	}

	public String solrEmailMessage() {
		return emailMessage;
	}

	public String strEmailMessage() {
		return emailMessage == null ? "" : emailMessage;
	}

	public String jsonEmailMessage() {
		return emailMessage == null ? "" : emailMessage;
	}

	public String nomAffichageEmailMessage() {
		return null;
	}

	public String htmTooltipEmailMessage() {
		return null;
	}

	public String htmEmailMessage() {
		return emailMessage == null ? "" : StringEscapeUtils.escapeHtml4(strEmailMessage());
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignDisplayPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinicKey">Trouver l'entité clinicKey dans Solr</a>
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
	public DesignDisplayPage setClinicKey(String o) {
		if(NumberUtils.isParsable(o))
			this.clinicKey = Long.parseLong(o);
		this.clinicKeyWrap.alreadyInitialized = true;
		return (DesignDisplayPage)this;
	}
	protected DesignDisplayPage clinicKeyInit() {
		if(!clinicKeyWrap.alreadyInitialized) {
			_clinicKey(clinicKeyWrap);
			if(clinicKey == null)
				setClinicKey(clinicKeyWrap.o);
		}
		clinicKeyWrap.alreadyInitialized(true);
		return (DesignDisplayPage)this;
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
		return null;
	}

	public String htmTooltipClinicKey() {
		return null;
	}

	public String htmClinicKey() {
		return clinicKey == null ? "" : StringEscapeUtils.escapeHtml4(strClinicKey());
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignDisplayPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinicName">Trouver l'entité clinicName dans Solr</a>
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
	protected DesignDisplayPage clinicNameInit() {
		if(!clinicNameWrap.alreadyInitialized) {
			_clinicName(clinicNameWrap);
			if(clinicName == null)
				setClinicName(clinicNameWrap.o);
		}
		clinicNameWrap.alreadyInitialized(true);
		return (DesignDisplayPage)this;
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
		return null;
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignDisplayPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinicCompleteName">Trouver l'entité clinicCompleteName dans Solr</a>
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
	protected DesignDisplayPage clinicCompleteNameInit() {
		if(!clinicCompleteNameWrap.alreadyInitialized) {
			_clinicCompleteName(clinicCompleteNameWrap);
			if(clinicCompleteName == null)
				setClinicCompleteName(clinicCompleteNameWrap.o);
		}
		clinicCompleteNameWrap.alreadyInitialized(true);
		return (DesignDisplayPage)this;
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
		return null;
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignDisplayPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinicLocation">Trouver l'entité clinicLocation dans Solr</a>
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
	protected DesignDisplayPage clinicLocationInit() {
		if(!clinicLocationWrap.alreadyInitialized) {
			_clinicLocation(clinicLocationWrap);
			if(clinicLocation == null)
				setClinicLocation(clinicLocationWrap.o);
		}
		clinicLocationWrap.alreadyInitialized(true);
		return (DesignDisplayPage)this;
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
		return null;
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignDisplayPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinicAddress">Trouver l'entité clinicAddress dans Solr</a>
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
	protected DesignDisplayPage clinicAddressInit() {
		if(!clinicAddressWrap.alreadyInitialized) {
			_clinicAddress(clinicAddressWrap);
			if(clinicAddress == null)
				setClinicAddress(clinicAddressWrap.o);
		}
		clinicAddressWrap.alreadyInitialized(true);
		return (DesignDisplayPage)this;
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
		return null;
	}

	public String htmTooltipClinicAddress() {
		return null;
	}

	public String htmClinicAddress() {
		return clinicAddress == null ? "" : StringEscapeUtils.escapeHtml4(strClinicAddress());
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignDisplayPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinicPhoneNumber">Trouver l'entité clinicPhoneNumber dans Solr</a>
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
	protected DesignDisplayPage clinicPhoneNumberInit() {
		if(!clinicPhoneNumberWrap.alreadyInitialized) {
			_clinicPhoneNumber(clinicPhoneNumberWrap);
			if(clinicPhoneNumber == null)
				setClinicPhoneNumber(clinicPhoneNumberWrap.o);
		}
		clinicPhoneNumberWrap.alreadyInitialized(true);
		return (DesignDisplayPage)this;
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
		return null;
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignDisplayPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinicAdministratorName">Trouver l'entité clinicAdministratorName dans Solr</a>
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
	protected DesignDisplayPage clinicAdministratorNameInit() {
		if(!clinicAdministratorNameWrap.alreadyInitialized) {
			_clinicAdministratorName(clinicAdministratorNameWrap);
			if(clinicAdministratorName == null)
				setClinicAdministratorName(clinicAdministratorNameWrap.o);
		}
		clinicAdministratorNameWrap.alreadyInitialized(true);
		return (DesignDisplayPage)this;
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
		return null;
	}

	public String htmTooltipClinicAdministratorName() {
		return null;
	}

	public String htmClinicAdministratorName() {
		return clinicAdministratorName == null ? "" : StringEscapeUtils.escapeHtml4(strClinicAdministratorName());
	}

	/////////////////////
	// seasonStartDate //
	/////////////////////

	/**	L'entité « seasonStartDate »
	 *	 is defined as null before being initialized. 
	 */
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	protected LocalDate seasonStartDate;
	@JsonIgnore
	public Wrap<LocalDate> seasonStartDateWrap = new Wrap<LocalDate>().p(this).c(LocalDate.class).var("seasonStartDate").o(seasonStartDate);

	/**	<br/>L'entité « seasonStartDate »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignDisplayPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:seasonStartDate">Trouver l'entité seasonStartDate dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _seasonStartDate(Wrap<LocalDate> c);

	public LocalDate getSeasonStartDate() {
		return seasonStartDate;
	}

	public void setSeasonStartDate(LocalDate seasonStartDate) {
		this.seasonStartDate = seasonStartDate;
		this.seasonStartDateWrap.alreadyInitialized = true;
	}
	public DesignDisplayPage setSeasonStartDate(Instant o) {
		this.seasonStartDate = o == null ? null : LocalDate.from(o);
		this.seasonStartDateWrap.alreadyInitialized = true;
		return (DesignDisplayPage)this;
	}
	/** Example: 2011-12-03+01:00 **/
	public DesignDisplayPage setSeasonStartDate(String o) {
		this.seasonStartDate = o == null ? null : LocalDate.parse(o, DateTimeFormatter.ISO_DATE);
		this.seasonStartDateWrap.alreadyInitialized = true;
		return (DesignDisplayPage)this;
	}
	public DesignDisplayPage setSeasonStartDate(Date o) {
		this.seasonStartDate = o == null ? null : o.toInstant().atZone(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toLocalDate();
		this.seasonStartDateWrap.alreadyInitialized = true;
		return (DesignDisplayPage)this;
	}
	protected DesignDisplayPage seasonStartDateInit() {
		if(!seasonStartDateWrap.alreadyInitialized) {
			_seasonStartDate(seasonStartDateWrap);
			if(seasonStartDate == null)
				setSeasonStartDate(seasonStartDateWrap.o);
		}
		seasonStartDateWrap.alreadyInitialized(true);
		return (DesignDisplayPage)this;
	}

	public Date solrSeasonStartDate() {
		return seasonStartDate == null ? null : Date.from(seasonStartDate.atStartOfDay(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).toInstant().atZone(ZoneId.of("Z")).toInstant());
	}

	public String strSeasonStartDate() {
		return seasonStartDate == null ? "" : seasonStartDate.format(DateTimeFormatter.ofPattern("EEE MMM d, yyyy", Locale.forLanguageTag("en-US")));
	}

	public String jsonSeasonStartDate() {
		return seasonStartDate == null ? "" : seasonStartDate.format(DateTimeFormatter.ISO_DATE);
	}

	public String nomAffichageSeasonStartDate() {
		return null;
	}

	public String htmTooltipSeasonStartDate() {
		return null;
	}

	public String htmSeasonStartDate() {
		return seasonStartDate == null ? "" : StringEscapeUtils.escapeHtml4(strSeasonStartDate());
	}

	////////////////////
	// htmlPartSearch //
	////////////////////

	/**	L'entité « htmlPartSearch »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut SearchList<HtmlPart>(). 
	 */
	@JsonInclude(Include.NON_NULL)
	protected SearchList<HtmlPart> htmlPartSearch = new SearchList<HtmlPart>();
	@JsonIgnore
	public Wrap<SearchList<HtmlPart>> htmlPartSearchWrap = new Wrap<SearchList<HtmlPart>>().p(this).c(SearchList.class).var("htmlPartSearch").o(htmlPartSearch);

	/**	<br/>L'entité « htmlPartSearch »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut SearchList<HtmlPart>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignDisplayPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:htmlPartSearch">Trouver l'entité htmlPartSearch dans Solr</a>
	 * <br/>
	 * @param htmlPartSearch est l'entité déjà construit. 
	 **/
	protected abstract void _htmlPartSearch(SearchList<HtmlPart> l);

	public SearchList<HtmlPart> getHtmlPartSearch() {
		return htmlPartSearch;
	}

	public void setHtmlPartSearch(SearchList<HtmlPart> htmlPartSearch) {
		this.htmlPartSearch = htmlPartSearch;
		this.htmlPartSearchWrap.alreadyInitialized = true;
	}
	protected DesignDisplayPage htmlPartSearchInit() {
		if(!htmlPartSearchWrap.alreadyInitialized) {
			_htmlPartSearch(htmlPartSearch);
		}
		htmlPartSearch.initDeepForClass(siteRequest_);
		htmlPartSearchWrap.alreadyInitialized(true);
		return (DesignDisplayPage)this;
	}

	//////////////////
	// htmlPartList //
	//////////////////

	/**	L'entité « htmlPartList »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected List<HtmlPart> htmlPartList;
	@JsonIgnore
	public Wrap<List<HtmlPart>> htmlPartListWrap = new Wrap<List<HtmlPart>>().p(this).c(List.class).var("htmlPartList").o(htmlPartList);

	/**	<br/>L'entité « htmlPartList »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignDisplayPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:htmlPartList">Trouver l'entité htmlPartList dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _htmlPartList(Wrap<List<HtmlPart>> c);

	public List<HtmlPart> getHtmlPartList() {
		return htmlPartList;
	}

	public void setHtmlPartList(List<HtmlPart> htmlPartList) {
		this.htmlPartList = htmlPartList;
		this.htmlPartListWrap.alreadyInitialized = true;
	}
	public DesignDisplayPage addHtmlPartList(HtmlPart...objets) {
		for(HtmlPart o : objets) {
			addHtmlPartList(o);
		}
		return (DesignDisplayPage)this;
	}
	public DesignDisplayPage addHtmlPartList(HtmlPart o) {
		if(o != null && !htmlPartList.contains(o))
			this.htmlPartList.add(o);
		return (DesignDisplayPage)this;
	}
	protected DesignDisplayPage htmlPartListInit() {
		if(!htmlPartListWrap.alreadyInitialized) {
			_htmlPartList(htmlPartListWrap);
			if(htmlPartList == null)
				setHtmlPartList(htmlPartListWrap.o);
		}
		htmlPartListWrap.alreadyInitialized(true);
		return (DesignDisplayPage)this;
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedDesignDisplayPage = false;

	public DesignDisplayPage initDeepDesignDisplayPage(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedDesignDisplayPage) {
			alreadyInitializedDesignDisplayPage = true;
			initDeepDesignDisplayPage();
		}
		return (DesignDisplayPage)this;
	}

	public void initDeepDesignDisplayPage() {
		initDesignDisplayPage();
		super.initDeepDesignDisplayGenPage(siteRequest_);
	}

	public void initDesignDisplayPage() {
		pageDesignInit();
		designIdInit();
		enrollmentSearchInit();
		patientEnrollmentInit();
		enrollmentsInit();
		enrollmentBlocksInit();
		enrollmentBlockInit();
		enrollmentEnrollmentInit();
		clinicSearchInit();
		clinic_Init();
		emailFromInit();
		emailToMedicalInit();
		emailToAddressInit();
		emailToNameInit();
		emailMessageInit();
		clinicKeyInit();
		clinicNameInit();
		clinicCompleteNameInit();
		clinicLocationInit();
		clinicAddressInit();
		clinicPhoneNumberInit();
		clinicAdministratorNameInit();
		seasonStartDateInit();
		htmlPartSearchInit();
		htmlPartListInit();
	}

	@Override public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepDesignDisplayPage(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestDesignDisplayPage(SiteRequestEnUS siteRequest_) {
			super.siteRequestDesignDisplayGenPage(siteRequest_);
		if(pageDesign != null)
			pageDesign.setSiteRequest_(siteRequest_);
		if(enrollmentSearch != null)
			enrollmentSearch.setSiteRequest_(siteRequest_);
		if(patientEnrollment != null)
			patientEnrollment.setSiteRequest_(siteRequest_);
		if(enrollmentBlock != null)
			enrollmentBlock.setSiteRequest_(siteRequest_);
		if(enrollmentEnrollment != null)
			enrollmentEnrollment.setSiteRequest_(siteRequest_);
		if(clinicSearch != null)
			clinicSearch.setSiteRequest_(siteRequest_);
		if(htmlPartSearch != null)
			htmlPartSearch.setSiteRequest_(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestDesignDisplayPage(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainDesignDisplayPage(v);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.obtainForClass(v);
			}
		}
		return o;
	}
	public Object obtainDesignDisplayPage(String var) {
		DesignDisplayPage oDesignDisplayPage = (DesignDisplayPage)this;
		switch(var) {
			case "pageDesign":
				return oDesignDisplayPage.pageDesign;
			case "designId":
				return oDesignDisplayPage.designId;
			case "enrollmentSearch":
				return oDesignDisplayPage.enrollmentSearch;
			case "patientEnrollment":
				return oDesignDisplayPage.patientEnrollment;
			case "enrollments":
				return oDesignDisplayPage.enrollments;
			case "enrollmentBlocks":
				return oDesignDisplayPage.enrollmentBlocks;
			case "enrollmentBlock":
				return oDesignDisplayPage.enrollmentBlock;
			case "enrollmentEnrollment":
				return oDesignDisplayPage.enrollmentEnrollment;
			case "clinicSearch":
				return oDesignDisplayPage.clinicSearch;
			case "clinic_":
				return oDesignDisplayPage.clinic_;
			case "emailFrom":
				return oDesignDisplayPage.emailFrom;
			case "emailToMedical":
				return oDesignDisplayPage.emailToMedical;
			case "emailToAddress":
				return oDesignDisplayPage.emailToAddress;
			case "emailToName":
				return oDesignDisplayPage.emailToName;
			case "emailMessage":
				return oDesignDisplayPage.emailMessage;
			case "clinicKey":
				return oDesignDisplayPage.clinicKey;
			case "clinicName":
				return oDesignDisplayPage.clinicName;
			case "clinicCompleteName":
				return oDesignDisplayPage.clinicCompleteName;
			case "clinicLocation":
				return oDesignDisplayPage.clinicLocation;
			case "clinicAddress":
				return oDesignDisplayPage.clinicAddress;
			case "clinicPhoneNumber":
				return oDesignDisplayPage.clinicPhoneNumber;
			case "clinicAdministratorName":
				return oDesignDisplayPage.clinicAdministratorName;
			case "seasonStartDate":
				return oDesignDisplayPage.seasonStartDate;
			case "htmlPartSearch":
				return oDesignDisplayPage.htmlPartSearch;
			case "htmlPartList":
				return oDesignDisplayPage.htmlPartList;
			default:
				return super.obtainDesignDisplayGenPage(var);
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
				o = attributeDesignDisplayPage(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeDesignDisplayPage(String var, Object val) {
		DesignDisplayPage oDesignDisplayPage = (DesignDisplayPage)this;
		switch(var) {
			default:
				return super.attributeDesignDisplayGenPage(var, val);
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
					o = defineDesignDisplayPage(v, val);
				else if(o instanceof Cluster) {
					Cluster cluster = (Cluster)o;
					o = cluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineDesignDisplayPage(String var, String val) {
		switch(var) {
			default:
				return super.defineDesignDisplayGenPage(var, val);
		}
	}

	/////////////////
	// htmlScripts //
	/////////////////

	@Override public void htmlScripts() {
		htmlScriptsDesignDisplayPage();
		super.htmlScripts();
	}

	public void htmlScriptsDesignDisplayPage() {
	}

	////////////////
	// htmlScript //
	////////////////

	@Override public void htmlScript() {
		htmlScriptDesignDisplayPage();
		super.htmlScript();
	}

	public void htmlScriptDesignDisplayPage() {
	}

	//////////////
	// htmlBody //
	//////////////

	@Override public void htmlBody() {
		htmlBodyDesignDisplayPage();
		super.htmlBody();
	}

	public void htmlBodyDesignDisplayPage() {
	}

	//////////
	// html //
	//////////

	@Override public void html() {
		htmlDesignDisplayPage();
		super.html();
	}

	public void htmlDesignDisplayPage() {
	}

	//////////////
	// htmlMeta //
	//////////////

	@Override public void htmlMeta() {
		htmlMetaDesignDisplayPage();
		super.htmlMeta();
	}

	public void htmlMetaDesignDisplayPage() {
	}

	////////////////
	// htmlStyles //
	////////////////

	@Override public void htmlStyles() {
		htmlStylesDesignDisplayPage();
		super.htmlStyles();
	}

	public void htmlStylesDesignDisplayPage() {
	}

	///////////////
	// htmlStyle //
	///////////////

	@Override public void htmlStyle() {
		htmlStyleDesignDisplayPage();
		super.htmlStyle();
	}

	public void htmlStyleDesignDisplayPage() {
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestDesignDisplayPage() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof DesignDisplayPage) {
			DesignDisplayPage original = (DesignDisplayPage)o;
			super.apiRequestDesignDisplayGenPage();
		}
	}

	//////////////
	// hashCode //
	//////////////

	@Override public int hashCode() {
		return Objects.hash(super.hashCode());
	}

	////////////
	// equals //
	////////////

	@Override public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof DesignDisplayPage))
			return false;
		DesignDisplayPage that = (DesignDisplayPage)o;
		return super.equals(o);
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("DesignDisplayPage { ");
		sb.append(" }");
		return sb.toString();
	}
}
