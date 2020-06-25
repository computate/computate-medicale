package org.computate.medicale.enUS.design;

import org.computate.medicale.enUS.writer.AllWriter;
import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.computate.medicale.enUS.design.DesignEmailGenPage;
import java.lang.String;
import org.computate.medicale.enUS.request.api.ApiRequest;
import io.vertx.core.logging.Logger;
import org.computate.medicale.enUS.html.part.HtmlPart;
import org.computate.medicale.enUS.wrap.Wrap;
import java.math.MathContext;
import org.computate.medicale.enUS.clinic.MedicalClinic;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Objects;
import io.vertx.core.json.JsonArray;
import org.computate.medicale.enUS.search.SearchList;
import java.util.List;
import org.apache.commons.lang3.math.NumberUtils;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.computate.medicale.enUS.design.PageDesign;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.computate.medicale.enUS.cluster.Cluster;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

/**	
 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true">Trouver la classe  dans Solr</a>
 * <br/>
 **/
public abstract class DesignEmailPageGen<DEV> extends DesignEmailGenPage {
	protected static final Logger LOGGER = LoggerFactory.getLogger(DesignEmailPage.class);

	////////
	// w1 //
	////////

	/**	L'entité « w1 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected AllWriter w1;
	@JsonIgnore
	public Wrap<AllWriter> w1Wrap = new Wrap<AllWriter>().p(this).c(AllWriter.class).var("w1").o(w1);

	/**	<br/>L'entité « w1 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:w1">Trouver l'entité w1 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _w1(Wrap<AllWriter> c);

	public AllWriter getW1() {
		return w1;
	}

	public void setW1(AllWriter w1) {
		this.w1 = w1;
		this.w1Wrap.alreadyInitialized = true;
	}
	protected DesignEmailPage w1Init() {
		if(!w1Wrap.alreadyInitialized) {
			_w1(w1Wrap);
			if(w1 == null)
				setW1(w1Wrap.o);
		}
		if(w1 != null)
			w1.initDeepForClass(siteRequest_);
		w1Wrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
	}

	///////////
	// wPage //
	///////////

	/**	L'entité « wPage »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected AllWriter wPage;
	@JsonIgnore
	public Wrap<AllWriter> wPageWrap = new Wrap<AllWriter>().p(this).c(AllWriter.class).var("wPage").o(wPage);

	/**	<br/>L'entité « wPage »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:wPage">Trouver l'entité wPage dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _wPage(Wrap<AllWriter> c);

	public AllWriter getWPage() {
		return wPage;
	}

	public void setWPage(AllWriter wPage) {
		this.wPage = wPage;
		this.wPageWrap.alreadyInitialized = true;
	}
	protected DesignEmailPage wPageInit() {
		if(!wPageWrap.alreadyInitialized) {
			_wPage(wPageWrap);
			if(wPage == null)
				setWPage(wPageWrap.o);
		}
		if(wPage != null)
			wPage.initDeepForClass(siteRequest_);
		wPageWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
	}

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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageDesign">Trouver l'entité pageDesign dans Solr</a>
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
	protected DesignEmailPage pageDesignInit() {
		if(!pageDesignWrap.alreadyInitialized) {
			_pageDesign(pageDesignWrap);
			if(pageDesign == null)
				setPageDesign(pageDesignWrap.o);
		}
		if(pageDesign != null)
			pageDesign.initDeepForClass(siteRequest_);
		pageDesignWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
	}

	//////////////////
	// pageDesignId //
	//////////////////

	/**	L'entité « pageDesignId »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String pageDesignId;
	@JsonIgnore
	public Wrap<String> pageDesignIdWrap = new Wrap<String>().p(this).c(String.class).var("pageDesignId").o(pageDesignId);

	/**	<br/>L'entité « pageDesignId »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageDesignId">Trouver l'entité pageDesignId dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _pageDesignId(Wrap<String> c);

	public String getPageDesignId() {
		return pageDesignId;
	}

	public void setPageDesignId(String pageDesignId) {
		this.pageDesignId = pageDesignId;
		this.pageDesignIdWrap.alreadyInitialized = true;
	}
	protected DesignEmailPage pageDesignIdInit() {
		if(!pageDesignIdWrap.alreadyInitialized) {
			_pageDesignId(pageDesignIdWrap);
			if(pageDesignId == null)
				setPageDesignId(pageDesignIdWrap.o);
		}
		pageDesignIdWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
	}

	public String solrPageDesignId() {
		return pageDesignId;
	}

	public String strPageDesignId() {
		return pageDesignId == null ? "" : pageDesignId;
	}

	public String jsonPageDesignId() {
		return pageDesignId == null ? "" : pageDesignId;
	}

	public String nomAffichagePageDesignId() {
		return null;
	}

	public String htmTooltipPageDesignId() {
		return null;
	}

	public String htmPageDesignId() {
		return pageDesignId == null ? "" : StringEscapeUtils.escapeHtml4(strPageDesignId());
	}

	////////////////////////
	// pageHtmlPartSearch //
	////////////////////////

	/**	L'entité « pageHtmlPartSearch »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut SearchList<HtmlPart>(). 
	 */
	@JsonInclude(Include.NON_NULL)
	protected SearchList<HtmlPart> pageHtmlPartSearch = new SearchList<HtmlPart>();
	@JsonIgnore
	public Wrap<SearchList<HtmlPart>> pageHtmlPartSearchWrap = new Wrap<SearchList<HtmlPart>>().p(this).c(SearchList.class).var("pageHtmlPartSearch").o(pageHtmlPartSearch);

	/**	<br/>L'entité « pageHtmlPartSearch »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut SearchList<HtmlPart>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageHtmlPartSearch">Trouver l'entité pageHtmlPartSearch dans Solr</a>
	 * <br/>
	 * @param pageHtmlPartSearch est l'entité déjà construit. 
	 **/
	protected abstract void _pageHtmlPartSearch(SearchList<HtmlPart> l);

	public SearchList<HtmlPart> getPageHtmlPartSearch() {
		return pageHtmlPartSearch;
	}

	public void setPageHtmlPartSearch(SearchList<HtmlPart> pageHtmlPartSearch) {
		this.pageHtmlPartSearch = pageHtmlPartSearch;
		this.pageHtmlPartSearchWrap.alreadyInitialized = true;
	}
	protected DesignEmailPage pageHtmlPartSearchInit() {
		if(!pageHtmlPartSearchWrap.alreadyInitialized) {
			_pageHtmlPartSearch(pageHtmlPartSearch);
		}
		pageHtmlPartSearch.initDeepForClass(siteRequest_);
		pageHtmlPartSearchWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
	}

	//////////////////////
	// pageHtmlPartList //
	//////////////////////

	/**	L'entité « pageHtmlPartList »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected List<HtmlPart> pageHtmlPartList;
	@JsonIgnore
	public Wrap<List<HtmlPart>> pageHtmlPartListWrap = new Wrap<List<HtmlPart>>().p(this).c(List.class).var("pageHtmlPartList").o(pageHtmlPartList);

	/**	<br/>L'entité « pageHtmlPartList »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageHtmlPartList">Trouver l'entité pageHtmlPartList dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _pageHtmlPartList(Wrap<List<HtmlPart>> c);

	public List<HtmlPart> getPageHtmlPartList() {
		return pageHtmlPartList;
	}

	public void setPageHtmlPartList(List<HtmlPart> pageHtmlPartList) {
		this.pageHtmlPartList = pageHtmlPartList;
		this.pageHtmlPartListWrap.alreadyInitialized = true;
	}
	public DesignEmailPage addPageHtmlPartList(HtmlPart...objets) {
		for(HtmlPart o : objets) {
			addPageHtmlPartList(o);
		}
		return (DesignEmailPage)this;
	}
	public DesignEmailPage addPageHtmlPartList(HtmlPart o) {
		if(o != null && !pageHtmlPartList.contains(o))
			this.pageHtmlPartList.add(o);
		return (DesignEmailPage)this;
	}
	protected DesignEmailPage pageHtmlPartListInit() {
		if(!pageHtmlPartListWrap.alreadyInitialized) {
			_pageHtmlPartList(pageHtmlPartListWrap);
			if(pageHtmlPartList == null)
				setPageHtmlPartList(pageHtmlPartListWrap.o);
		}
		pageHtmlPartListWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
	}

	////////////
	// wEmail //
	////////////

	/**	L'entité « wEmail »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected AllWriter wEmail;
	@JsonIgnore
	public Wrap<AllWriter> wEmailWrap = new Wrap<AllWriter>().p(this).c(AllWriter.class).var("wEmail").o(wEmail);

	/**	<br/>L'entité « wEmail »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:wEmail">Trouver l'entité wEmail dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _wEmail(Wrap<AllWriter> c);

	public AllWriter getWEmail() {
		return wEmail;
	}

	public void setWEmail(AllWriter wEmail) {
		this.wEmail = wEmail;
		this.wEmailWrap.alreadyInitialized = true;
	}
	protected DesignEmailPage wEmailInit() {
		if(!wEmailWrap.alreadyInitialized) {
			_wEmail(wEmailWrap);
			if(wEmail == null)
				setWEmail(wEmailWrap.o);
		}
		if(wEmail != null)
			wEmail.initDeepForClass(siteRequest_);
		wEmailWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
	}

	//////////////////////
	// emailContentType //
	//////////////////////

	/**	L'entité « emailContentType »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String emailContentType;
	@JsonIgnore
	public Wrap<String> emailContentTypeWrap = new Wrap<String>().p(this).c(String.class).var("emailContentType").o(emailContentType);

	/**	<br/>L'entité « emailContentType »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:emailContentType">Trouver l'entité emailContentType dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _emailContentType(Wrap<String> c);

	public String getEmailContentType() {
		return emailContentType;
	}

	public void setEmailContentType(String emailContentType) {
		this.emailContentType = emailContentType;
		this.emailContentTypeWrap.alreadyInitialized = true;
	}
	protected DesignEmailPage emailContentTypeInit() {
		if(!emailContentTypeWrap.alreadyInitialized) {
			_emailContentType(emailContentTypeWrap);
			if(emailContentType == null)
				setEmailContentType(emailContentTypeWrap.o);
		}
		emailContentTypeWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
	}

	public String solrEmailContentType() {
		return emailContentType;
	}

	public String strEmailContentType() {
		return emailContentType == null ? "" : emailContentType;
	}

	public String jsonEmailContentType() {
		return emailContentType == null ? "" : emailContentType;
	}

	public String nomAffichageEmailContentType() {
		return null;
	}

	public String htmTooltipEmailContentType() {
		return null;
	}

	public String htmEmailContentType() {
		return emailContentType == null ? "" : StringEscapeUtils.escapeHtml4(strEmailContentType());
	}

	///////////////////
	// emailDesignId //
	///////////////////

	/**	L'entité « emailDesignId »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String emailDesignId;
	@JsonIgnore
	public Wrap<String> emailDesignIdWrap = new Wrap<String>().p(this).c(String.class).var("emailDesignId").o(emailDesignId);

	/**	<br/>L'entité « emailDesignId »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:emailDesignId">Trouver l'entité emailDesignId dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _emailDesignId(Wrap<String> c);

	public String getEmailDesignId() {
		return emailDesignId;
	}

	public void setEmailDesignId(String emailDesignId) {
		this.emailDesignId = emailDesignId;
		this.emailDesignIdWrap.alreadyInitialized = true;
	}
	protected DesignEmailPage emailDesignIdInit() {
		if(!emailDesignIdWrap.alreadyInitialized) {
			_emailDesignId(emailDesignIdWrap);
			if(emailDesignId == null)
				setEmailDesignId(emailDesignIdWrap.o);
		}
		emailDesignIdWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
	}

	public String solrEmailDesignId() {
		return emailDesignId;
	}

	public String strEmailDesignId() {
		return emailDesignId == null ? "" : emailDesignId;
	}

	public String jsonEmailDesignId() {
		return emailDesignId == null ? "" : emailDesignId;
	}

	public String nomAffichageEmailDesignId() {
		return null;
	}

	public String htmTooltipEmailDesignId() {
		return null;
	}

	public String htmEmailDesignId() {
		return emailDesignId == null ? "" : StringEscapeUtils.escapeHtml4(strEmailDesignId());
	}

	///////////////////////
	// emailDesignSearch //
	///////////////////////

	/**	L'entité « emailDesignSearch »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut SearchList<PageDesign>(). 
	 */
	@JsonInclude(Include.NON_NULL)
	protected SearchList<PageDesign> emailDesignSearch = new SearchList<PageDesign>();
	@JsonIgnore
	public Wrap<SearchList<PageDesign>> emailDesignSearchWrap = new Wrap<SearchList<PageDesign>>().p(this).c(SearchList.class).var("emailDesignSearch").o(emailDesignSearch);

	/**	<br/>L'entité « emailDesignSearch »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut SearchList<PageDesign>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:emailDesignSearch">Trouver l'entité emailDesignSearch dans Solr</a>
	 * <br/>
	 * @param emailDesignSearch est l'entité déjà construit. 
	 **/
	protected abstract void _emailDesignSearch(SearchList<PageDesign> l);

	public SearchList<PageDesign> getEmailDesignSearch() {
		return emailDesignSearch;
	}

	public void setEmailDesignSearch(SearchList<PageDesign> emailDesignSearch) {
		this.emailDesignSearch = emailDesignSearch;
		this.emailDesignSearchWrap.alreadyInitialized = true;
	}
	protected DesignEmailPage emailDesignSearchInit() {
		if(!emailDesignSearchWrap.alreadyInitialized) {
			_emailDesignSearch(emailDesignSearch);
		}
		emailDesignSearch.initDeepForClass(siteRequest_);
		emailDesignSearchWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
	}

	/////////////////
	// emailDesign //
	/////////////////

	/**	L'entité « emailDesign »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected PageDesign emailDesign;
	@JsonIgnore
	public Wrap<PageDesign> emailDesignWrap = new Wrap<PageDesign>().p(this).c(PageDesign.class).var("emailDesign").o(emailDesign);

	/**	<br/>L'entité « emailDesign »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:emailDesign">Trouver l'entité emailDesign dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _emailDesign(Wrap<PageDesign> c);

	public PageDesign getEmailDesign() {
		return emailDesign;
	}

	public void setEmailDesign(PageDesign emailDesign) {
		this.emailDesign = emailDesign;
		this.emailDesignWrap.alreadyInitialized = true;
	}
	protected DesignEmailPage emailDesignInit() {
		if(!emailDesignWrap.alreadyInitialized) {
			_emailDesign(emailDesignWrap);
			if(emailDesign == null)
				setEmailDesign(emailDesignWrap.o);
		}
		if(emailDesign != null)
			emailDesign.initDeepForClass(siteRequest_);
		emailDesignWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
	}

	/////////////////////////
	// emailHtmlPartSearch //
	/////////////////////////

	/**	L'entité « emailHtmlPartSearch »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut SearchList<HtmlPart>(). 
	 */
	@JsonInclude(Include.NON_NULL)
	protected SearchList<HtmlPart> emailHtmlPartSearch = new SearchList<HtmlPart>();
	@JsonIgnore
	public Wrap<SearchList<HtmlPart>> emailHtmlPartSearchWrap = new Wrap<SearchList<HtmlPart>>().p(this).c(SearchList.class).var("emailHtmlPartSearch").o(emailHtmlPartSearch);

	/**	<br/>L'entité « emailHtmlPartSearch »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut SearchList<HtmlPart>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:emailHtmlPartSearch">Trouver l'entité emailHtmlPartSearch dans Solr</a>
	 * <br/>
	 * @param emailHtmlPartSearch est l'entité déjà construit. 
	 **/
	protected abstract void _emailHtmlPartSearch(SearchList<HtmlPart> l);

	public SearchList<HtmlPart> getEmailHtmlPartSearch() {
		return emailHtmlPartSearch;
	}

	public void setEmailHtmlPartSearch(SearchList<HtmlPart> emailHtmlPartSearch) {
		this.emailHtmlPartSearch = emailHtmlPartSearch;
		this.emailHtmlPartSearchWrap.alreadyInitialized = true;
	}
	protected DesignEmailPage emailHtmlPartSearchInit() {
		if(!emailHtmlPartSearchWrap.alreadyInitialized) {
			_emailHtmlPartSearch(emailHtmlPartSearch);
		}
		emailHtmlPartSearch.initDeepForClass(siteRequest_);
		emailHtmlPartSearchWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
	}

	///////////////////////
	// emailHtmlPartList //
	///////////////////////

	/**	L'entité « emailHtmlPartList »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected List<HtmlPart> emailHtmlPartList;
	@JsonIgnore
	public Wrap<List<HtmlPart>> emailHtmlPartListWrap = new Wrap<List<HtmlPart>>().p(this).c(List.class).var("emailHtmlPartList").o(emailHtmlPartList);

	/**	<br/>L'entité « emailHtmlPartList »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:emailHtmlPartList">Trouver l'entité emailHtmlPartList dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _emailHtmlPartList(Wrap<List<HtmlPart>> c);

	public List<HtmlPart> getEmailHtmlPartList() {
		return emailHtmlPartList;
	}

	public void setEmailHtmlPartList(List<HtmlPart> emailHtmlPartList) {
		this.emailHtmlPartList = emailHtmlPartList;
		this.emailHtmlPartListWrap.alreadyInitialized = true;
	}
	public DesignEmailPage addEmailHtmlPartList(HtmlPart...objets) {
		for(HtmlPart o : objets) {
			addEmailHtmlPartList(o);
		}
		return (DesignEmailPage)this;
	}
	public DesignEmailPage addEmailHtmlPartList(HtmlPart o) {
		if(o != null && !emailHtmlPartList.contains(o))
			this.emailHtmlPartList.add(o);
		return (DesignEmailPage)this;
	}
	protected DesignEmailPage emailHtmlPartListInit() {
		if(!emailHtmlPartListWrap.alreadyInitialized) {
			_emailHtmlPartList(emailHtmlPartListWrap);
			if(emailHtmlPartList == null)
				setEmailHtmlPartList(emailHtmlPartListWrap.o);
		}
		emailHtmlPartListWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
	}

	/////////////////
	// wAttachment //
	/////////////////

	/**	L'entité « wAttachment »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected AllWriter wAttachment;
	@JsonIgnore
	public Wrap<AllWriter> wAttachmentWrap = new Wrap<AllWriter>().p(this).c(AllWriter.class).var("wAttachment").o(wAttachment);

	/**	<br/>L'entité « wAttachment »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:wAttachment">Trouver l'entité wAttachment dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _wAttachment(Wrap<AllWriter> c);

	public AllWriter getWAttachment() {
		return wAttachment;
	}

	public void setWAttachment(AllWriter wAttachment) {
		this.wAttachment = wAttachment;
		this.wAttachmentWrap.alreadyInitialized = true;
	}
	protected DesignEmailPage wAttachmentInit() {
		if(!wAttachmentWrap.alreadyInitialized) {
			_wAttachment(wAttachmentWrap);
			if(wAttachment == null)
				setWAttachment(wAttachmentWrap.o);
		}
		if(wAttachment != null)
			wAttachment.initDeepForClass(siteRequest_);
		wAttachmentWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
	}

	///////////////////////////
	// attachmentContentType //
	///////////////////////////

	/**	L'entité « attachmentContentType »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String attachmentContentType;
	@JsonIgnore
	public Wrap<String> attachmentContentTypeWrap = new Wrap<String>().p(this).c(String.class).var("attachmentContentType").o(attachmentContentType);

	/**	<br/>L'entité « attachmentContentType »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:attachmentContentType">Trouver l'entité attachmentContentType dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _attachmentContentType(Wrap<String> c);

	public String getAttachmentContentType() {
		return attachmentContentType;
	}

	public void setAttachmentContentType(String attachmentContentType) {
		this.attachmentContentType = attachmentContentType;
		this.attachmentContentTypeWrap.alreadyInitialized = true;
	}
	protected DesignEmailPage attachmentContentTypeInit() {
		if(!attachmentContentTypeWrap.alreadyInitialized) {
			_attachmentContentType(attachmentContentTypeWrap);
			if(attachmentContentType == null)
				setAttachmentContentType(attachmentContentTypeWrap.o);
		}
		attachmentContentTypeWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
	}

	public String solrAttachmentContentType() {
		return attachmentContentType;
	}

	public String strAttachmentContentType() {
		return attachmentContentType == null ? "" : attachmentContentType;
	}

	public String jsonAttachmentContentType() {
		return attachmentContentType == null ? "" : attachmentContentType;
	}

	public String nomAffichageAttachmentContentType() {
		return null;
	}

	public String htmTooltipAttachmentContentType() {
		return null;
	}

	public String htmAttachmentContentType() {
		return attachmentContentType == null ? "" : StringEscapeUtils.escapeHtml4(strAttachmentContentType());
	}

	////////////////////////
	// attachmentDesignId //
	////////////////////////

	/**	L'entité « attachmentDesignId »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String attachmentDesignId;
	@JsonIgnore
	public Wrap<String> attachmentDesignIdWrap = new Wrap<String>().p(this).c(String.class).var("attachmentDesignId").o(attachmentDesignId);

	/**	<br/>L'entité « attachmentDesignId »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:attachmentDesignId">Trouver l'entité attachmentDesignId dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _attachmentDesignId(Wrap<String> c);

	public String getAttachmentDesignId() {
		return attachmentDesignId;
	}

	public void setAttachmentDesignId(String attachmentDesignId) {
		this.attachmentDesignId = attachmentDesignId;
		this.attachmentDesignIdWrap.alreadyInitialized = true;
	}
	protected DesignEmailPage attachmentDesignIdInit() {
		if(!attachmentDesignIdWrap.alreadyInitialized) {
			_attachmentDesignId(attachmentDesignIdWrap);
			if(attachmentDesignId == null)
				setAttachmentDesignId(attachmentDesignIdWrap.o);
		}
		attachmentDesignIdWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
	}

	public String solrAttachmentDesignId() {
		return attachmentDesignId;
	}

	public String strAttachmentDesignId() {
		return attachmentDesignId == null ? "" : attachmentDesignId;
	}

	public String jsonAttachmentDesignId() {
		return attachmentDesignId == null ? "" : attachmentDesignId;
	}

	public String nomAffichageAttachmentDesignId() {
		return null;
	}

	public String htmTooltipAttachmentDesignId() {
		return null;
	}

	public String htmAttachmentDesignId() {
		return attachmentDesignId == null ? "" : StringEscapeUtils.escapeHtml4(strAttachmentDesignId());
	}

	////////////////////////////
	// attachmentDesignSearch //
	////////////////////////////

	/**	L'entité « attachmentDesignSearch »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut SearchList<PageDesign>(). 
	 */
	@JsonInclude(Include.NON_NULL)
	protected SearchList<PageDesign> attachmentDesignSearch = new SearchList<PageDesign>();
	@JsonIgnore
	public Wrap<SearchList<PageDesign>> attachmentDesignSearchWrap = new Wrap<SearchList<PageDesign>>().p(this).c(SearchList.class).var("attachmentDesignSearch").o(attachmentDesignSearch);

	/**	<br/>L'entité « attachmentDesignSearch »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut SearchList<PageDesign>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:attachmentDesignSearch">Trouver l'entité attachmentDesignSearch dans Solr</a>
	 * <br/>
	 * @param attachmentDesignSearch est l'entité déjà construit. 
	 **/
	protected abstract void _attachmentDesignSearch(SearchList<PageDesign> l);

	public SearchList<PageDesign> getAttachmentDesignSearch() {
		return attachmentDesignSearch;
	}

	public void setAttachmentDesignSearch(SearchList<PageDesign> attachmentDesignSearch) {
		this.attachmentDesignSearch = attachmentDesignSearch;
		this.attachmentDesignSearchWrap.alreadyInitialized = true;
	}
	protected DesignEmailPage attachmentDesignSearchInit() {
		if(!attachmentDesignSearchWrap.alreadyInitialized) {
			_attachmentDesignSearch(attachmentDesignSearch);
		}
		attachmentDesignSearch.initDeepForClass(siteRequest_);
		attachmentDesignSearchWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
	}

	//////////////////////
	// attachmentDesign //
	//////////////////////

	/**	L'entité « attachmentDesign »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected PageDesign attachmentDesign;
	@JsonIgnore
	public Wrap<PageDesign> attachmentDesignWrap = new Wrap<PageDesign>().p(this).c(PageDesign.class).var("attachmentDesign").o(attachmentDesign);

	/**	<br/>L'entité « attachmentDesign »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:attachmentDesign">Trouver l'entité attachmentDesign dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _attachmentDesign(Wrap<PageDesign> c);

	public PageDesign getAttachmentDesign() {
		return attachmentDesign;
	}

	public void setAttachmentDesign(PageDesign attachmentDesign) {
		this.attachmentDesign = attachmentDesign;
		this.attachmentDesignWrap.alreadyInitialized = true;
	}
	protected DesignEmailPage attachmentDesignInit() {
		if(!attachmentDesignWrap.alreadyInitialized) {
			_attachmentDesign(attachmentDesignWrap);
			if(attachmentDesign == null)
				setAttachmentDesign(attachmentDesignWrap.o);
		}
		if(attachmentDesign != null)
			attachmentDesign.initDeepForClass(siteRequest_);
		attachmentDesignWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
	}

	//////////////////////////////
	// attachmentHtmlPartSearch //
	//////////////////////////////

	/**	L'entité « attachmentHtmlPartSearch »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut SearchList<HtmlPart>(). 
	 */
	@JsonInclude(Include.NON_NULL)
	protected SearchList<HtmlPart> attachmentHtmlPartSearch = new SearchList<HtmlPart>();
	@JsonIgnore
	public Wrap<SearchList<HtmlPart>> attachmentHtmlPartSearchWrap = new Wrap<SearchList<HtmlPart>>().p(this).c(SearchList.class).var("attachmentHtmlPartSearch").o(attachmentHtmlPartSearch);

	/**	<br/>L'entité « attachmentHtmlPartSearch »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut SearchList<HtmlPart>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:attachmentHtmlPartSearch">Trouver l'entité attachmentHtmlPartSearch dans Solr</a>
	 * <br/>
	 * @param attachmentHtmlPartSearch est l'entité déjà construit. 
	 **/
	protected abstract void _attachmentHtmlPartSearch(SearchList<HtmlPart> l);

	public SearchList<HtmlPart> getAttachmentHtmlPartSearch() {
		return attachmentHtmlPartSearch;
	}

	public void setAttachmentHtmlPartSearch(SearchList<HtmlPart> attachmentHtmlPartSearch) {
		this.attachmentHtmlPartSearch = attachmentHtmlPartSearch;
		this.attachmentHtmlPartSearchWrap.alreadyInitialized = true;
	}
	protected DesignEmailPage attachmentHtmlPartSearchInit() {
		if(!attachmentHtmlPartSearchWrap.alreadyInitialized) {
			_attachmentHtmlPartSearch(attachmentHtmlPartSearch);
		}
		attachmentHtmlPartSearch.initDeepForClass(siteRequest_);
		attachmentHtmlPartSearchWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
	}

	////////////////////////////
	// attachmentHtmlPartList //
	////////////////////////////

	/**	L'entité « attachmentHtmlPartList »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected List<HtmlPart> attachmentHtmlPartList;
	@JsonIgnore
	public Wrap<List<HtmlPart>> attachmentHtmlPartListWrap = new Wrap<List<HtmlPart>>().p(this).c(List.class).var("attachmentHtmlPartList").o(attachmentHtmlPartList);

	/**	<br/>L'entité « attachmentHtmlPartList »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:attachmentHtmlPartList">Trouver l'entité attachmentHtmlPartList dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _attachmentHtmlPartList(Wrap<List<HtmlPart>> c);

	public List<HtmlPart> getAttachmentHtmlPartList() {
		return attachmentHtmlPartList;
	}

	public void setAttachmentHtmlPartList(List<HtmlPart> attachmentHtmlPartList) {
		this.attachmentHtmlPartList = attachmentHtmlPartList;
		this.attachmentHtmlPartListWrap.alreadyInitialized = true;
	}
	public DesignEmailPage addAttachmentHtmlPartList(HtmlPart...objets) {
		for(HtmlPart o : objets) {
			addAttachmentHtmlPartList(o);
		}
		return (DesignEmailPage)this;
	}
	public DesignEmailPage addAttachmentHtmlPartList(HtmlPart o) {
		if(o != null && !attachmentHtmlPartList.contains(o))
			this.attachmentHtmlPartList.add(o);
		return (DesignEmailPage)this;
	}
	protected DesignEmailPage attachmentHtmlPartListInit() {
		if(!attachmentHtmlPartListWrap.alreadyInitialized) {
			_attachmentHtmlPartList(attachmentHtmlPartListWrap);
			if(attachmentHtmlPartList == null)
				setAttachmentHtmlPartList(attachmentHtmlPartListWrap.o);
		}
		attachmentHtmlPartListWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentSearch">Trouver l'entité enrollmentSearch dans Solr</a>
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
	protected DesignEmailPage enrollmentSearchInit() {
		if(!enrollmentSearchWrap.alreadyInitialized) {
			_enrollmentSearch(enrollmentSearch);
		}
		enrollmentSearch.initDeepForClass(siteRequest_);
		enrollmentSearchWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:patientEnrollment">Trouver l'entité patientEnrollment dans Solr</a>
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
	protected DesignEmailPage patientEnrollmentInit() {
		if(!patientEnrollmentWrap.alreadyInitialized) {
			_patientEnrollment(patientEnrollmentWrap);
			if(patientEnrollment == null)
				setPatientEnrollment(patientEnrollmentWrap.o);
		}
		if(patientEnrollment != null)
			patientEnrollment.initDeepForClass(siteRequest_);
		patientEnrollmentWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollments">Trouver l'entité enrollments dans Solr</a>
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
	public DesignEmailPage addEnrollments(MedicalEnrollment...objets) {
		for(MedicalEnrollment o : objets) {
			addEnrollments(o);
		}
		return (DesignEmailPage)this;
	}
	public DesignEmailPage addEnrollments(MedicalEnrollment o) {
		if(o != null && !enrollments.contains(o))
			this.enrollments.add(o);
		return (DesignEmailPage)this;
	}
	protected DesignEmailPage enrollmentsInit() {
		if(!enrollmentsWrap.alreadyInitialized) {
			_enrollments(enrollmentsWrap);
			if(enrollments == null)
				setEnrollments(enrollmentsWrap.o);
		}
		enrollmentsWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentBlocks">Trouver l'entité enrollmentBlocks dans Solr</a>
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
	public DesignEmailPage addEnrollmentBlocks(MedicalEnrollment...objets) {
		for(MedicalEnrollment o : objets) {
			addEnrollmentBlocks(o);
		}
		return (DesignEmailPage)this;
	}
	public DesignEmailPage addEnrollmentBlocks(MedicalEnrollment o) {
		if(o != null && !enrollmentBlocks.contains(o))
			this.enrollmentBlocks.add(o);
		return (DesignEmailPage)this;
	}
	protected DesignEmailPage enrollmentBlocksInit() {
		if(!enrollmentBlocksWrap.alreadyInitialized) {
			_enrollmentBlocks(enrollmentBlocks);
		}
		enrollmentBlocksWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentBlock">Trouver l'entité enrollmentBlock dans Solr</a>
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
	protected DesignEmailPage enrollmentBlockInit() {
		if(!enrollmentBlockWrap.alreadyInitialized) {
			_enrollmentBlock(enrollmentBlockWrap);
			if(enrollmentBlock == null)
				setEnrollmentBlock(enrollmentBlockWrap.o);
		}
		if(enrollmentBlock != null)
			enrollmentBlock.initDeepForClass(siteRequest_);
		enrollmentBlockWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:enrollmentEnrollment">Trouver l'entité enrollmentEnrollment dans Solr</a>
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
	protected DesignEmailPage enrollmentEnrollmentInit() {
		if(!enrollmentEnrollmentWrap.alreadyInitialized) {
			_enrollmentEnrollment(enrollmentEnrollmentWrap);
			if(enrollmentEnrollment == null)
				setEnrollmentEnrollment(enrollmentEnrollmentWrap.o);
		}
		if(enrollmentEnrollment != null)
			enrollmentEnrollment.initDeepForClass(siteRequest_);
		enrollmentEnrollmentWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinicSearch">Trouver l'entité clinicSearch dans Solr</a>
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
	protected DesignEmailPage clinicSearchInit() {
		if(!clinicSearchWrap.alreadyInitialized) {
			_clinicSearch(clinicSearch);
		}
		clinicSearch.initDeepForClass(siteRequest_);
		clinicSearchWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinic_">Trouver l'entité clinic_ dans Solr</a>
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
	protected DesignEmailPage clinic_Init() {
		if(!clinic_Wrap.alreadyInitialized) {
			_clinic_(clinic_Wrap);
			if(clinic_ == null)
				setClinic_(clinic_Wrap.o);
		}
		clinic_Wrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinicKey">Trouver l'entité clinicKey dans Solr</a>
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
	public DesignEmailPage setClinicKey(String o) {
		if(NumberUtils.isParsable(o))
			this.clinicKey = Long.parseLong(o);
		this.clinicKeyWrap.alreadyInitialized = true;
		return (DesignEmailPage)this;
	}
	protected DesignEmailPage clinicKeyInit() {
		if(!clinicKeyWrap.alreadyInitialized) {
			_clinicKey(clinicKeyWrap);
			if(clinicKey == null)
				setClinicKey(clinicKeyWrap.o);
		}
		clinicKeyWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinicName">Trouver l'entité clinicName dans Solr</a>
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
	protected DesignEmailPage clinicNameInit() {
		if(!clinicNameWrap.alreadyInitialized) {
			_clinicName(clinicNameWrap);
			if(clinicName == null)
				setClinicName(clinicNameWrap.o);
		}
		clinicNameWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinicCompleteName">Trouver l'entité clinicCompleteName dans Solr</a>
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
	protected DesignEmailPage clinicCompleteNameInit() {
		if(!clinicCompleteNameWrap.alreadyInitialized) {
			_clinicCompleteName(clinicCompleteNameWrap);
			if(clinicCompleteName == null)
				setClinicCompleteName(clinicCompleteNameWrap.o);
		}
		clinicCompleteNameWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinicLocation">Trouver l'entité clinicLocation dans Solr</a>
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
	protected DesignEmailPage clinicLocationInit() {
		if(!clinicLocationWrap.alreadyInitialized) {
			_clinicLocation(clinicLocationWrap);
			if(clinicLocation == null)
				setClinicLocation(clinicLocationWrap.o);
		}
		clinicLocationWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinicAddress">Trouver l'entité clinicAddress dans Solr</a>
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
	protected DesignEmailPage clinicAddressInit() {
		if(!clinicAddressWrap.alreadyInitialized) {
			_clinicAddress(clinicAddressWrap);
			if(clinicAddress == null)
				setClinicAddress(clinicAddressWrap.o);
		}
		clinicAddressWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinicPhoneNumber">Trouver l'entité clinicPhoneNumber dans Solr</a>
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
	protected DesignEmailPage clinicPhoneNumberInit() {
		if(!clinicPhoneNumberWrap.alreadyInitialized) {
			_clinicPhoneNumber(clinicPhoneNumberWrap);
			if(clinicPhoneNumber == null)
				setClinicPhoneNumber(clinicPhoneNumberWrap.o);
		}
		clinicPhoneNumberWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:clinicAdministratorName">Trouver l'entité clinicAdministratorName dans Solr</a>
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
	protected DesignEmailPage clinicAdministratorNameInit() {
		if(!clinicAdministratorNameWrap.alreadyInitialized) {
			_clinicAdministratorName(clinicAdministratorNameWrap);
			if(clinicAdministratorName == null)
				setClinicAdministratorName(clinicAdministratorNameWrap.o);
		}
		clinicAdministratorNameWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:emailFrom">Trouver l'entité emailFrom dans Solr</a>
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
	protected DesignEmailPage emailFromInit() {
		if(!emailFromWrap.alreadyInitialized) {
			_emailFrom(emailFromWrap);
			if(emailFrom == null)
				setEmailFrom(emailFromWrap.o);
		}
		emailFromWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
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

	///////////////////
	// emailToClinic //
	///////////////////

	/**	L'entité « emailToClinic »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String emailToClinic;
	@JsonIgnore
	public Wrap<String> emailToClinicWrap = new Wrap<String>().p(this).c(String.class).var("emailToClinic").o(emailToClinic);

	/**	<br/>L'entité « emailToClinic »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:emailToClinic">Trouver l'entité emailToClinic dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _emailToClinic(Wrap<String> c);

	public String getEmailToClinic() {
		return emailToClinic;
	}

	public void setEmailToClinic(String emailToClinic) {
		this.emailToClinic = emailToClinic;
		this.emailToClinicWrap.alreadyInitialized = true;
	}
	protected DesignEmailPage emailToClinicInit() {
		if(!emailToClinicWrap.alreadyInitialized) {
			_emailToClinic(emailToClinicWrap);
			if(emailToClinic == null)
				setEmailToClinic(emailToClinicWrap.o);
		}
		emailToClinicWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
	}

	public String solrEmailToClinic() {
		return emailToClinic;
	}

	public String strEmailToClinic() {
		return emailToClinic == null ? "" : emailToClinic;
	}

	public String jsonEmailToClinic() {
		return emailToClinic == null ? "" : emailToClinic;
	}

	public String nomAffichageEmailToClinic() {
		return null;
	}

	public String htmTooltipEmailToClinic() {
		return null;
	}

	public String htmEmailToClinic() {
		return emailToClinic == null ? "" : StringEscapeUtils.escapeHtml4(strEmailToClinic());
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:emailToAddress">Trouver l'entité emailToAddress dans Solr</a>
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
	protected DesignEmailPage emailToAddressInit() {
		if(!emailToAddressWrap.alreadyInitialized) {
			_emailToAddress(emailToAddressWrap);
			if(emailToAddress == null)
				setEmailToAddress(emailToAddressWrap.o);
		}
		emailToAddressWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:emailToName">Trouver l'entité emailToName dans Solr</a>
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
	protected DesignEmailPage emailToNameInit() {
		if(!emailToNameWrap.alreadyInitialized) {
			_emailToName(emailToNameWrap);
			if(emailToName == null)
				setEmailToName(emailToNameWrap.o);
		}
		emailToNameWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:emailMessage">Trouver l'entité emailMessage dans Solr</a>
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
	protected DesignEmailPage emailMessageInit() {
		if(!emailMessageWrap.alreadyInitialized) {
			_emailMessage(emailMessageWrap);
			if(emailMessage == null)
				setEmailMessage(emailMessageWrap.o);
		}
		emailMessageWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
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

	//////////////////
	// emailSubject //
	//////////////////

	/**	L'entité « emailSubject »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String emailSubject;
	@JsonIgnore
	public Wrap<String> emailSubjectWrap = new Wrap<String>().p(this).c(String.class).var("emailSubject").o(emailSubject);

	/**	<br/>L'entité « emailSubject »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.DesignEmailPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:emailSubject">Trouver l'entité emailSubject dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _emailSubject(Wrap<String> c);

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
		this.emailSubjectWrap.alreadyInitialized = true;
	}
	protected DesignEmailPage emailSubjectInit() {
		if(!emailSubjectWrap.alreadyInitialized) {
			_emailSubject(emailSubjectWrap);
			if(emailSubject == null)
				setEmailSubject(emailSubjectWrap.o);
		}
		emailSubjectWrap.alreadyInitialized(true);
		return (DesignEmailPage)this;
	}

	public String solrEmailSubject() {
		return emailSubject;
	}

	public String strEmailSubject() {
		return emailSubject == null ? "" : emailSubject;
	}

	public String jsonEmailSubject() {
		return emailSubject == null ? "" : emailSubject;
	}

	public String nomAffichageEmailSubject() {
		return null;
	}

	public String htmTooltipEmailSubject() {
		return null;
	}

	public String htmEmailSubject() {
		return emailSubject == null ? "" : StringEscapeUtils.escapeHtml4(strEmailSubject());
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedDesignEmailPage = false;

	public DesignEmailPage initDeepDesignEmailPage(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedDesignEmailPage) {
			alreadyInitializedDesignEmailPage = true;
			initDeepDesignEmailPage();
		}
		return (DesignEmailPage)this;
	}

	public void initDeepDesignEmailPage() {
		initDesignEmailPage();
		super.initDeepDesignEmailGenPage(siteRequest_);
	}

	public void initDesignEmailPage() {
		w1Init();
		wPageInit();
		pageDesignInit();
		pageDesignIdInit();
		pageHtmlPartSearchInit();
		pageHtmlPartListInit();
		wEmailInit();
		emailContentTypeInit();
		emailDesignIdInit();
		emailDesignSearchInit();
		emailDesignInit();
		emailHtmlPartSearchInit();
		emailHtmlPartListInit();
		wAttachmentInit();
		attachmentContentTypeInit();
		attachmentDesignIdInit();
		attachmentDesignSearchInit();
		attachmentDesignInit();
		attachmentHtmlPartSearchInit();
		attachmentHtmlPartListInit();
		enrollmentSearchInit();
		patientEnrollmentInit();
		enrollmentsInit();
		enrollmentBlocksInit();
		enrollmentBlockInit();
		enrollmentEnrollmentInit();
		clinicSearchInit();
		clinic_Init();
		clinicKeyInit();
		clinicNameInit();
		clinicCompleteNameInit();
		clinicLocationInit();
		clinicAddressInit();
		clinicPhoneNumberInit();
		clinicAdministratorNameInit();
		emailFromInit();
		emailToClinicInit();
		emailToAddressInit();
		emailToNameInit();
		emailMessageInit();
		emailSubjectInit();
	}

	@Override public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepDesignEmailPage(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestDesignEmailPage(SiteRequestEnUS siteRequest_) {
			super.siteRequestDesignEmailGenPage(siteRequest_);
		if(w1 != null)
			w1.setSiteRequest_(siteRequest_);
		if(wPage != null)
			wPage.setSiteRequest_(siteRequest_);
		if(pageDesign != null)
			pageDesign.setSiteRequest_(siteRequest_);
		if(pageHtmlPartSearch != null)
			pageHtmlPartSearch.setSiteRequest_(siteRequest_);
		if(wEmail != null)
			wEmail.setSiteRequest_(siteRequest_);
		if(emailDesignSearch != null)
			emailDesignSearch.setSiteRequest_(siteRequest_);
		if(emailDesign != null)
			emailDesign.setSiteRequest_(siteRequest_);
		if(emailHtmlPartSearch != null)
			emailHtmlPartSearch.setSiteRequest_(siteRequest_);
		if(wAttachment != null)
			wAttachment.setSiteRequest_(siteRequest_);
		if(attachmentDesignSearch != null)
			attachmentDesignSearch.setSiteRequest_(siteRequest_);
		if(attachmentDesign != null)
			attachmentDesign.setSiteRequest_(siteRequest_);
		if(attachmentHtmlPartSearch != null)
			attachmentHtmlPartSearch.setSiteRequest_(siteRequest_);
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
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestDesignEmailPage(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainDesignEmailPage(v);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.obtainForClass(v);
			}
		}
		return o;
	}
	public Object obtainDesignEmailPage(String var) {
		DesignEmailPage oDesignEmailPage = (DesignEmailPage)this;
		switch(var) {
			case "w1":
				return oDesignEmailPage.w1;
			case "wPage":
				return oDesignEmailPage.wPage;
			case "pageDesign":
				return oDesignEmailPage.pageDesign;
			case "pageDesignId":
				return oDesignEmailPage.pageDesignId;
			case "pageHtmlPartSearch":
				return oDesignEmailPage.pageHtmlPartSearch;
			case "pageHtmlPartList":
				return oDesignEmailPage.pageHtmlPartList;
			case "wEmail":
				return oDesignEmailPage.wEmail;
			case "emailContentType":
				return oDesignEmailPage.emailContentType;
			case "emailDesignId":
				return oDesignEmailPage.emailDesignId;
			case "emailDesignSearch":
				return oDesignEmailPage.emailDesignSearch;
			case "emailDesign":
				return oDesignEmailPage.emailDesign;
			case "emailHtmlPartSearch":
				return oDesignEmailPage.emailHtmlPartSearch;
			case "emailHtmlPartList":
				return oDesignEmailPage.emailHtmlPartList;
			case "wAttachment":
				return oDesignEmailPage.wAttachment;
			case "attachmentContentType":
				return oDesignEmailPage.attachmentContentType;
			case "attachmentDesignId":
				return oDesignEmailPage.attachmentDesignId;
			case "attachmentDesignSearch":
				return oDesignEmailPage.attachmentDesignSearch;
			case "attachmentDesign":
				return oDesignEmailPage.attachmentDesign;
			case "attachmentHtmlPartSearch":
				return oDesignEmailPage.attachmentHtmlPartSearch;
			case "attachmentHtmlPartList":
				return oDesignEmailPage.attachmentHtmlPartList;
			case "enrollmentSearch":
				return oDesignEmailPage.enrollmentSearch;
			case "patientEnrollment":
				return oDesignEmailPage.patientEnrollment;
			case "enrollments":
				return oDesignEmailPage.enrollments;
			case "enrollmentBlocks":
				return oDesignEmailPage.enrollmentBlocks;
			case "enrollmentBlock":
				return oDesignEmailPage.enrollmentBlock;
			case "enrollmentEnrollment":
				return oDesignEmailPage.enrollmentEnrollment;
			case "clinicSearch":
				return oDesignEmailPage.clinicSearch;
			case "clinic_":
				return oDesignEmailPage.clinic_;
			case "clinicKey":
				return oDesignEmailPage.clinicKey;
			case "clinicName":
				return oDesignEmailPage.clinicName;
			case "clinicCompleteName":
				return oDesignEmailPage.clinicCompleteName;
			case "clinicLocation":
				return oDesignEmailPage.clinicLocation;
			case "clinicAddress":
				return oDesignEmailPage.clinicAddress;
			case "clinicPhoneNumber":
				return oDesignEmailPage.clinicPhoneNumber;
			case "clinicAdministratorName":
				return oDesignEmailPage.clinicAdministratorName;
			case "emailFrom":
				return oDesignEmailPage.emailFrom;
			case "emailToClinic":
				return oDesignEmailPage.emailToClinic;
			case "emailToAddress":
				return oDesignEmailPage.emailToAddress;
			case "emailToName":
				return oDesignEmailPage.emailToName;
			case "emailMessage":
				return oDesignEmailPage.emailMessage;
			case "emailSubject":
				return oDesignEmailPage.emailSubject;
			default:
				return super.obtainDesignEmailGenPage(var);
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
				o = attributeDesignEmailPage(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeDesignEmailPage(String var, Object val) {
		DesignEmailPage oDesignEmailPage = (DesignEmailPage)this;
		switch(var) {
			default:
				return super.attributeDesignEmailGenPage(var, val);
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
					o = defineDesignEmailPage(v, val);
				else if(o instanceof Cluster) {
					Cluster cluster = (Cluster)o;
					o = cluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineDesignEmailPage(String var, String val) {
		switch(var) {
			default:
				return super.defineDesignEmailGenPage(var, val);
		}
	}

	/////////////////
	// htmlScripts //
	/////////////////

	@Override public void htmlScripts() {
		htmlScriptsDesignEmailPage();
		super.htmlScripts();
	}

	public void htmlScriptsDesignEmailPage() {
	}

	////////////////
	// htmlScript //
	////////////////

	@Override public void htmlScript() {
		htmlScriptDesignEmailPage();
		super.htmlScript();
	}

	public void htmlScriptDesignEmailPage() {
	}

	//////////////
	// htmlBody //
	//////////////

	@Override public void htmlBody() {
		htmlBodyDesignEmailPage();
		super.htmlBody();
	}

	public void htmlBodyDesignEmailPage() {
	}

	//////////
	// html //
	//////////

	@Override public void html() {
		htmlDesignEmailPage();
		super.html();
	}

	public void htmlDesignEmailPage() {
	}

	//////////////
	// htmlMeta //
	//////////////

	@Override public void htmlMeta() {
		htmlMetaDesignEmailPage();
		super.htmlMeta();
	}

	public void htmlMetaDesignEmailPage() {
	}

	////////////////
	// htmlStyles //
	////////////////

	@Override public void htmlStyles() {
		htmlStylesDesignEmailPage();
		super.htmlStyles();
	}

	public void htmlStylesDesignEmailPage() {
	}

	///////////////
	// htmlStyle //
	///////////////

	@Override public void htmlStyle() {
		htmlStyleDesignEmailPage();
		super.htmlStyle();
	}

	public void htmlStyleDesignEmailPage() {
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestDesignEmailPage() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof DesignEmailPage) {
			DesignEmailPage original = (DesignEmailPage)o;
			super.apiRequestDesignEmailGenPage();
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
		if(!(o instanceof DesignEmailPage))
			return false;
		DesignEmailPage that = (DesignEmailPage)o;
		return super.equals(o);
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("DesignEmailPage { ");
		sb.append(" }");
		return sb.toString();
	}
}
