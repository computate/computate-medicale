package org.computate.medicale.enUS.html.part;

import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.lang.Double;
import java.util.Date;
import org.computate.medicale.frFR.cluster.Cluster;
import org.computate.medicale.enUS.request.api.ApiRequest;
import org.computate.medicale.enUS.context.SiteContextEnUS;
import org.apache.commons.lang3.StringUtils;
import io.vertx.core.logging.LoggerFactory;
import java.text.NumberFormat;
import java.util.ArrayList;
import org.apache.commons.collections.CollectionUtils;
import java.lang.Long;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.lang.Boolean;
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
 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true">Trouver la classe  dans Solr</a>
 * <br/>
 **/
public abstract class HtmlPartGen<DEV> extends Cluster {
	protected static final Logger LOGGER = LoggerFactory.getLogger(HtmlPart.class);

	public static final List<String> ROLES = Arrays.asList("SiteAdmin");
	public static final List<String> ROLE_READS = Arrays.asList("");

	public static final String HtmlPart_AName = "an HTML part";
	public static final String HtmlPart_This = "this ";
	public static final String HtmlPart_ThisName = "this HTML part";
	public static final String HtmlPart_A = "a ";
	public static final String HtmlPart_TheName = "theHTML part";
	public static final String HtmlPart_NameSingular = "HTML part";
	public static final String HtmlPart_NamePlural = "HTML parts";
	public static final String HtmlPart_NameActual = "current HTML part";
	public static final String HtmlPart_AllName = "all the HTML parts";
	public static final String HtmlPart_SearchAllNameBy = "search HTML parts by ";
	public static final String HtmlPart_ThePluralName = "the HTML parts";
	public static final String HtmlPart_NoNameFound = "no HTML part found";
	public static final String HtmlPart_NameVar = "html-part";
	public static final String HtmlPart_OfName = "of HTML part";
	public static final String HtmlPart_ANameAdjective = "an HTML part";
	public static final String HtmlPart_NameAdjectiveSingular = "HTML part";
	public static final String HtmlPart_NameAdjectivePlural = "HTML parts";
	public static final String HtmlPart_Color = "yellow";
	public static final String HtmlPart_IconGroup = "regular";
	public static final String HtmlPart_IconName = "sun";
	public static final Integer HtmlPart_Rows = 300;

	/////////////////
	// htmlPartKey //
	/////////////////

	/**	L'entité « htmlPartKey »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long htmlPartKey;
	@JsonIgnore
	public Wrap<Long> htmlPartKeyWrap = new Wrap<Long>().p(this).c(Long.class).var("htmlPartKey").o(htmlPartKey);

	/**	<br/>L'entité « htmlPartKey »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:htmlPartKey">Trouver l'entité htmlPartKey dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _htmlPartKey(Wrap<Long> c);

	public Long getHtmlPartKey() {
		return htmlPartKey;
	}

	public void setHtmlPartKey(Long htmlPartKey) {
		this.htmlPartKey = htmlPartKey;
		this.htmlPartKeyWrap.alreadyInitialized = true;
	}
	public HtmlPart setHtmlPartKey(String o) {
		if(NumberUtils.isParsable(o))
			this.htmlPartKey = Long.parseLong(o);
		this.htmlPartKeyWrap.alreadyInitialized = true;
		return (HtmlPart)this;
	}
	protected HtmlPart htmlPartKeyInit() {
		if(!htmlPartKeyWrap.alreadyInitialized) {
			_htmlPartKey(htmlPartKeyWrap);
			if(htmlPartKey == null)
				setHtmlPartKey(htmlPartKeyWrap.o);
		}
		htmlPartKeyWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public Long solrHtmlPartKey() {
		return htmlPartKey;
	}

	public String strHtmlPartKey() {
		return htmlPartKey == null ? "" : htmlPartKey.toString();
	}

	public String jsonHtmlPartKey() {
		return htmlPartKey == null ? "" : htmlPartKey.toString();
	}

	public String nomAffichageHtmlPartKey() {
		return "key";
	}

	public String htmTooltipHtmlPartKey() {
		return null;
	}

	public String htmHtmlPartKey() {
		return htmlPartKey == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlPartKey());
	}

	////////////////////
	// pageDesignKeys //
	////////////////////

	/**	L'entité « pageDesignKeys »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> pageDesignKeys = new java.util.ArrayList<java.lang.Long>();
	@JsonIgnore
	public Wrap<List<Long>> pageDesignKeysWrap = new Wrap<List<Long>>().p(this).c(List.class).var("pageDesignKeys").o(pageDesignKeys);

	/**	<br/>L'entité « pageDesignKeys »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageDesignKeys">Trouver l'entité pageDesignKeys dans Solr</a>
	 * <br/>
	 * @param pageDesignKeys est l'entité déjà construit. 
	 **/
	protected abstract void _pageDesignKeys(List<Long> l);

	public List<Long> getPageDesignKeys() {
		return pageDesignKeys;
	}

	public void setPageDesignKeys(List<Long> pageDesignKeys) {
		this.pageDesignKeys = pageDesignKeys;
		this.pageDesignKeysWrap.alreadyInitialized = true;
	}
	public HtmlPart addPageDesignKeys(Long...objets) {
		for(Long o : objets) {
			addPageDesignKeys(o);
		}
		return (HtmlPart)this;
	}
	public HtmlPart addPageDesignKeys(Long o) {
		if(o != null && !pageDesignKeys.contains(o))
			this.pageDesignKeys.add(o);
		return (HtmlPart)this;
	}
	public HtmlPart setPageDesignKeys(JsonArray objets) {
		pageDesignKeys.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addPageDesignKeys(o);
		}
		return (HtmlPart)this;
	}
	public HtmlPart addPageDesignKeys(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addPageDesignKeys(p);
		}
		return (HtmlPart)this;
	}
	protected HtmlPart pageDesignKeysInit() {
		if(!pageDesignKeysWrap.alreadyInitialized) {
			_pageDesignKeys(pageDesignKeys);
		}
		pageDesignKeysWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public List<Long> solrPageDesignKeys() {
		return pageDesignKeys;
	}

	public String strPageDesignKeys() {
		return pageDesignKeys == null ? "" : pageDesignKeys.toString();
	}

	public String jsonPageDesignKeys() {
		return pageDesignKeys == null ? "" : pageDesignKeys.toString();
	}

	public String nomAffichagePageDesignKeys() {
		return "page designs";
	}

	public String htmTooltipPageDesignKeys() {
		return null;
	}

	public String htmPageDesignKeys() {
		return pageDesignKeys == null ? "" : StringEscapeUtils.escapeHtml4(strPageDesignKeys());
	}

	//////////////
	// htmlLink //
	//////////////

	/**	L'entité « htmlLink »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String htmlLink;
	@JsonIgnore
	public Wrap<String> htmlLinkWrap = new Wrap<String>().p(this).c(String.class).var("htmlLink").o(htmlLink);

	/**	<br/>L'entité « htmlLink »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:htmlLink">Trouver l'entité htmlLink dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _htmlLink(Wrap<String> c);

	public String getHtmlLink() {
		return htmlLink;
	}

	public void setHtmlLink(String htmlLink) {
		this.htmlLink = htmlLink;
		this.htmlLinkWrap.alreadyInitialized = true;
	}
	protected HtmlPart htmlLinkInit() {
		if(!htmlLinkWrap.alreadyInitialized) {
			_htmlLink(htmlLinkWrap);
			if(htmlLink == null)
				setHtmlLink(htmlLinkWrap.o);
		}
		htmlLinkWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public String solrHtmlLink() {
		return htmlLink;
	}

	public String strHtmlLink() {
		return htmlLink == null ? "" : htmlLink;
	}

	public String jsonHtmlLink() {
		return htmlLink == null ? "" : htmlLink;
	}

	public String nomAffichageHtmlLink() {
		return "link";
	}

	public String htmTooltipHtmlLink() {
		return null;
	}

	public String htmHtmlLink() {
		return htmlLink == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlLink());
	}

	/////////////////
	// htmlElement //
	/////////////////

	/**	L'entité « htmlElement »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String htmlElement;
	@JsonIgnore
	public Wrap<String> htmlElementWrap = new Wrap<String>().p(this).c(String.class).var("htmlElement").o(htmlElement);

	/**	<br/>L'entité « htmlElement »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:htmlElement">Trouver l'entité htmlElement dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _htmlElement(Wrap<String> c);

	public String getHtmlElement() {
		return htmlElement;
	}

	public void setHtmlElement(String htmlElement) {
		this.htmlElement = htmlElement;
		this.htmlElementWrap.alreadyInitialized = true;
	}
	protected HtmlPart htmlElementInit() {
		if(!htmlElementWrap.alreadyInitialized) {
			_htmlElement(htmlElementWrap);
			if(htmlElement == null)
				setHtmlElement(htmlElementWrap.o);
		}
		htmlElementWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public String solrHtmlElement() {
		return htmlElement;
	}

	public String strHtmlElement() {
		return htmlElement == null ? "" : htmlElement;
	}

	public String jsonHtmlElement() {
		return htmlElement == null ? "" : htmlElement;
	}

	public String nomAffichageHtmlElement() {
		return "HTML element";
	}

	public String htmTooltipHtmlElement() {
		return null;
	}

	public String htmHtmlElement() {
		return htmlElement == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlElement());
	}

	////////////
	// htmlId //
	////////////

	/**	L'entité « htmlId »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String htmlId;
	@JsonIgnore
	public Wrap<String> htmlIdWrap = new Wrap<String>().p(this).c(String.class).var("htmlId").o(htmlId);

	/**	<br/>L'entité « htmlId »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:htmlId">Trouver l'entité htmlId dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _htmlId(Wrap<String> c);

	public String getHtmlId() {
		return htmlId;
	}

	public void setHtmlId(String htmlId) {
		this.htmlId = htmlId;
		this.htmlIdWrap.alreadyInitialized = true;
	}
	protected HtmlPart htmlIdInit() {
		if(!htmlIdWrap.alreadyInitialized) {
			_htmlId(htmlIdWrap);
			if(htmlId == null)
				setHtmlId(htmlIdWrap.o);
		}
		htmlIdWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public String solrHtmlId() {
		return htmlId;
	}

	public String strHtmlId() {
		return htmlId == null ? "" : htmlId;
	}

	public String jsonHtmlId() {
		return htmlId == null ? "" : htmlId;
	}

	public String nomAffichageHtmlId() {
		return "HTML ID";
	}

	public String htmTooltipHtmlId() {
		return null;
	}

	public String htmHtmlId() {
		return htmlId == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlId());
	}

	/////////////////
	// htmlClasses //
	/////////////////

	/**	L'entité « htmlClasses »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String htmlClasses;
	@JsonIgnore
	public Wrap<String> htmlClassesWrap = new Wrap<String>().p(this).c(String.class).var("htmlClasses").o(htmlClasses);

	/**	<br/>L'entité « htmlClasses »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:htmlClasses">Trouver l'entité htmlClasses dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _htmlClasses(Wrap<String> c);

	public String getHtmlClasses() {
		return htmlClasses;
	}

	public void setHtmlClasses(String htmlClasses) {
		this.htmlClasses = htmlClasses;
		this.htmlClassesWrap.alreadyInitialized = true;
	}
	protected HtmlPart htmlClassesInit() {
		if(!htmlClassesWrap.alreadyInitialized) {
			_htmlClasses(htmlClassesWrap);
			if(htmlClasses == null)
				setHtmlClasses(htmlClassesWrap.o);
		}
		htmlClassesWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public String solrHtmlClasses() {
		return htmlClasses;
	}

	public String strHtmlClasses() {
		return htmlClasses == null ? "" : htmlClasses;
	}

	public String jsonHtmlClasses() {
		return htmlClasses == null ? "" : htmlClasses;
	}

	public String nomAffichageHtmlClasses() {
		return "HTML classes";
	}

	public String htmTooltipHtmlClasses() {
		return null;
	}

	public String htmHtmlClasses() {
		return htmlClasses == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlClasses());
	}

	///////////////
	// htmlStyle //
	///////////////

	/**	L'entité « htmlStyle »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String htmlStyle;
	@JsonIgnore
	public Wrap<String> htmlStyleWrap = new Wrap<String>().p(this).c(String.class).var("htmlStyle").o(htmlStyle);

	/**	<br/>L'entité « htmlStyle »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:htmlStyle">Trouver l'entité htmlStyle dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _htmlStyle(Wrap<String> c);

	public String getHtmlStyle() {
		return htmlStyle;
	}

	public void setHtmlStyle(String htmlStyle) {
		this.htmlStyle = htmlStyle;
		this.htmlStyleWrap.alreadyInitialized = true;
	}
	protected HtmlPart htmlStyleInit() {
		if(!htmlStyleWrap.alreadyInitialized) {
			_htmlStyle(htmlStyleWrap);
			if(htmlStyle == null)
				setHtmlStyle(htmlStyleWrap.o);
		}
		htmlStyleWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public String solrHtmlStyle() {
		return htmlStyle;
	}

	public String strHtmlStyle() {
		return htmlStyle == null ? "" : htmlStyle;
	}

	public String jsonHtmlStyle() {
		return htmlStyle == null ? "" : htmlStyle;
	}

	public String nomAffichageHtmlStyle() {
		return "HTML style";
	}

	public String htmTooltipHtmlStyle() {
		return null;
	}

	public String htmHtmlStyle() {
		return htmlStyle == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlStyle());
	}

	////////////////
	// htmlBefore //
	////////////////

	/**	L'entité « htmlBefore »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String htmlBefore;
	@JsonIgnore
	public Wrap<String> htmlBeforeWrap = new Wrap<String>().p(this).c(String.class).var("htmlBefore").o(htmlBefore);

	/**	<br/>L'entité « htmlBefore »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:htmlBefore">Trouver l'entité htmlBefore dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _htmlBefore(Wrap<String> c);

	public String getHtmlBefore() {
		return htmlBefore;
	}

	public void setHtmlBefore(String htmlBefore) {
		this.htmlBefore = htmlBefore;
		this.htmlBeforeWrap.alreadyInitialized = true;
	}
	protected HtmlPart htmlBeforeInit() {
		if(!htmlBeforeWrap.alreadyInitialized) {
			_htmlBefore(htmlBeforeWrap);
			if(htmlBefore == null)
				setHtmlBefore(htmlBeforeWrap.o);
		}
		htmlBeforeWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public String solrHtmlBefore() {
		return htmlBefore;
	}

	public String strHtmlBefore() {
		return htmlBefore == null ? "" : htmlBefore;
	}

	public String jsonHtmlBefore() {
		return htmlBefore == null ? "" : htmlBefore;
	}

	public String nomAffichageHtmlBefore() {
		return "HTML before";
	}

	public String htmTooltipHtmlBefore() {
		return null;
	}

	public String htmHtmlBefore() {
		return htmlBefore == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlBefore());
	}

	///////////////
	// htmlAfter //
	///////////////

	/**	L'entité « htmlAfter »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String htmlAfter;
	@JsonIgnore
	public Wrap<String> htmlAfterWrap = new Wrap<String>().p(this).c(String.class).var("htmlAfter").o(htmlAfter);

	/**	<br/>L'entité « htmlAfter »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:htmlAfter">Trouver l'entité htmlAfter dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _htmlAfter(Wrap<String> c);

	public String getHtmlAfter() {
		return htmlAfter;
	}

	public void setHtmlAfter(String htmlAfter) {
		this.htmlAfter = htmlAfter;
		this.htmlAfterWrap.alreadyInitialized = true;
	}
	protected HtmlPart htmlAfterInit() {
		if(!htmlAfterWrap.alreadyInitialized) {
			_htmlAfter(htmlAfterWrap);
			if(htmlAfter == null)
				setHtmlAfter(htmlAfterWrap.o);
		}
		htmlAfterWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public String solrHtmlAfter() {
		return htmlAfter;
	}

	public String strHtmlAfter() {
		return htmlAfter == null ? "" : htmlAfter;
	}

	public String jsonHtmlAfter() {
		return htmlAfter == null ? "" : htmlAfter;
	}

	public String nomAffichageHtmlAfter() {
		return "HTML after";
	}

	public String htmTooltipHtmlAfter() {
		return null;
	}

	public String htmHtmlAfter() {
		return htmlAfter == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlAfter());
	}

	//////////////
	// htmlText //
	//////////////

	/**	L'entité « htmlText »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String htmlText;
	@JsonIgnore
	public Wrap<String> htmlTextWrap = new Wrap<String>().p(this).c(String.class).var("htmlText").o(htmlText);

	/**	<br/>L'entité « htmlText »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:htmlText">Trouver l'entité htmlText dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _htmlText(Wrap<String> c);

	public String getHtmlText() {
		return htmlText;
	}

	public void setHtmlText(String htmlText) {
		this.htmlText = htmlText;
		this.htmlTextWrap.alreadyInitialized = true;
	}
	protected HtmlPart htmlTextInit() {
		if(!htmlTextWrap.alreadyInitialized) {
			_htmlText(htmlTextWrap);
			if(htmlText == null)
				setHtmlText(htmlTextWrap.o);
		}
		htmlTextWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public String solrHtmlText() {
		return htmlText;
	}

	public String strHtmlText() {
		return htmlText == null ? "" : htmlText;
	}

	public String jsonHtmlText() {
		return htmlText == null ? "" : htmlText;
	}

	public String nomAffichageHtmlText() {
		return "text";
	}

	public String htmTooltipHtmlText() {
		return null;
	}

	public String htmHtmlText() {
		return htmlText == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlText());
	}

	/////////////
	// htmlVar //
	/////////////

	/**	L'entité « htmlVar »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String htmlVar;
	@JsonIgnore
	public Wrap<String> htmlVarWrap = new Wrap<String>().p(this).c(String.class).var("htmlVar").o(htmlVar);

	/**	<br/>L'entité « htmlVar »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:htmlVar">Trouver l'entité htmlVar dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _htmlVar(Wrap<String> c);

	public String getHtmlVar() {
		return htmlVar;
	}

	public void setHtmlVar(String htmlVar) {
		this.htmlVar = htmlVar;
		this.htmlVarWrap.alreadyInitialized = true;
	}
	protected HtmlPart htmlVarInit() {
		if(!htmlVarWrap.alreadyInitialized) {
			_htmlVar(htmlVarWrap);
			if(htmlVar == null)
				setHtmlVar(htmlVarWrap.o);
		}
		htmlVarWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public String solrHtmlVar() {
		return htmlVar;
	}

	public String strHtmlVar() {
		return htmlVar == null ? "" : htmlVar;
	}

	public String jsonHtmlVar() {
		return htmlVar == null ? "" : htmlVar;
	}

	public String nomAffichageHtmlVar() {
		return "var";
	}

	public String htmTooltipHtmlVar() {
		return null;
	}

	public String htmHtmlVar() {
		return htmlVar == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlVar());
	}

	/////////////////
	// htmlVarSpan //
	/////////////////

	/**	L'entité « htmlVarSpan »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String htmlVarSpan;
	@JsonIgnore
	public Wrap<String> htmlVarSpanWrap = new Wrap<String>().p(this).c(String.class).var("htmlVarSpan").o(htmlVarSpan);

	/**	<br/>L'entité « htmlVarSpan »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:htmlVarSpan">Trouver l'entité htmlVarSpan dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _htmlVarSpan(Wrap<String> c);

	public String getHtmlVarSpan() {
		return htmlVarSpan;
	}

	public void setHtmlVarSpan(String htmlVarSpan) {
		this.htmlVarSpan = htmlVarSpan;
		this.htmlVarSpanWrap.alreadyInitialized = true;
	}
	protected HtmlPart htmlVarSpanInit() {
		if(!htmlVarSpanWrap.alreadyInitialized) {
			_htmlVarSpan(htmlVarSpanWrap);
			if(htmlVarSpan == null)
				setHtmlVarSpan(htmlVarSpanWrap.o);
		}
		htmlVarSpanWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public String solrHtmlVarSpan() {
		return htmlVarSpan;
	}

	public String strHtmlVarSpan() {
		return htmlVarSpan == null ? "" : htmlVarSpan;
	}

	public String jsonHtmlVarSpan() {
		return htmlVarSpan == null ? "" : htmlVarSpan;
	}

	public String nomAffichageHtmlVarSpan() {
		return "var span";
	}

	public String htmTooltipHtmlVarSpan() {
		return null;
	}

	public String htmHtmlVarSpan() {
		return htmlVarSpan == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlVarSpan());
	}

	/////////////////
	// htmlVarForm //
	/////////////////

	/**	L'entité « htmlVarForm »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String htmlVarForm;
	@JsonIgnore
	public Wrap<String> htmlVarFormWrap = new Wrap<String>().p(this).c(String.class).var("htmlVarForm").o(htmlVarForm);

	/**	<br/>L'entité « htmlVarForm »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:htmlVarForm">Trouver l'entité htmlVarForm dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _htmlVarForm(Wrap<String> c);

	public String getHtmlVarForm() {
		return htmlVarForm;
	}

	public void setHtmlVarForm(String htmlVarForm) {
		this.htmlVarForm = htmlVarForm;
		this.htmlVarFormWrap.alreadyInitialized = true;
	}
	protected HtmlPart htmlVarFormInit() {
		if(!htmlVarFormWrap.alreadyInitialized) {
			_htmlVarForm(htmlVarFormWrap);
			if(htmlVarForm == null)
				setHtmlVarForm(htmlVarFormWrap.o);
		}
		htmlVarFormWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public String solrHtmlVarForm() {
		return htmlVarForm;
	}

	public String strHtmlVarForm() {
		return htmlVarForm == null ? "" : htmlVarForm;
	}

	public String jsonHtmlVarForm() {
		return htmlVarForm == null ? "" : htmlVarForm;
	}

	public String nomAffichageHtmlVarForm() {
		return "var form";
	}

	public String htmTooltipHtmlVarForm() {
		return null;
	}

	public String htmHtmlVarForm() {
		return htmlVarForm == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlVarForm());
	}

	//////////////////
	// htmlVarInput //
	//////////////////

	/**	L'entité « htmlVarInput »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String htmlVarInput;
	@JsonIgnore
	public Wrap<String> htmlVarInputWrap = new Wrap<String>().p(this).c(String.class).var("htmlVarInput").o(htmlVarInput);

	/**	<br/>L'entité « htmlVarInput »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:htmlVarInput">Trouver l'entité htmlVarInput dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _htmlVarInput(Wrap<String> c);

	public String getHtmlVarInput() {
		return htmlVarInput;
	}

	public void setHtmlVarInput(String htmlVarInput) {
		this.htmlVarInput = htmlVarInput;
		this.htmlVarInputWrap.alreadyInitialized = true;
	}
	protected HtmlPart htmlVarInputInit() {
		if(!htmlVarInputWrap.alreadyInitialized) {
			_htmlVarInput(htmlVarInputWrap);
			if(htmlVarInput == null)
				setHtmlVarInput(htmlVarInputWrap.o);
		}
		htmlVarInputWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public String solrHtmlVarInput() {
		return htmlVarInput;
	}

	public String strHtmlVarInput() {
		return htmlVarInput == null ? "" : htmlVarInput;
	}

	public String jsonHtmlVarInput() {
		return htmlVarInput == null ? "" : htmlVarInput;
	}

	public String nomAffichageHtmlVarInput() {
		return "var input";
	}

	public String htmTooltipHtmlVarInput() {
		return null;
	}

	public String htmHtmlVarInput() {
		return htmlVarInput == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlVarInput());
	}

	////////////////////
	// htmlVarForEach //
	////////////////////

	/**	L'entité « htmlVarForEach »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String htmlVarForEach;
	@JsonIgnore
	public Wrap<String> htmlVarForEachWrap = new Wrap<String>().p(this).c(String.class).var("htmlVarForEach").o(htmlVarForEach);

	/**	<br/>L'entité « htmlVarForEach »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:htmlVarForEach">Trouver l'entité htmlVarForEach dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _htmlVarForEach(Wrap<String> c);

	public String getHtmlVarForEach() {
		return htmlVarForEach;
	}

	public void setHtmlVarForEach(String htmlVarForEach) {
		this.htmlVarForEach = htmlVarForEach;
		this.htmlVarForEachWrap.alreadyInitialized = true;
	}
	protected HtmlPart htmlVarForEachInit() {
		if(!htmlVarForEachWrap.alreadyInitialized) {
			_htmlVarForEach(htmlVarForEachWrap);
			if(htmlVarForEach == null)
				setHtmlVarForEach(htmlVarForEachWrap.o);
		}
		htmlVarForEachWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public String solrHtmlVarForEach() {
		return htmlVarForEach;
	}

	public String strHtmlVarForEach() {
		return htmlVarForEach == null ? "" : htmlVarForEach;
	}

	public String jsonHtmlVarForEach() {
		return htmlVarForEach == null ? "" : htmlVarForEach;
	}

	public String nomAffichageHtmlVarForEach() {
		return "var for each";
	}

	public String htmTooltipHtmlVarForEach() {
		return null;
	}

	public String htmHtmlVarForEach() {
		return htmlVarForEach == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlVarForEach());
	}

	/////////////////
	// htmlExclude //
	/////////////////

	/**	L'entité « htmlExclude »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean htmlExclude;
	@JsonIgnore
	public Wrap<Boolean> htmlExcludeWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("htmlExclude").o(htmlExclude);

	/**	<br/>L'entité « htmlExclude »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:htmlExclude">Trouver l'entité htmlExclude dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _htmlExclude(Wrap<Boolean> c);

	public Boolean getHtmlExclude() {
		return htmlExclude;
	}

	public void setHtmlExclude(Boolean htmlExclude) {
		this.htmlExclude = htmlExclude;
		this.htmlExcludeWrap.alreadyInitialized = true;
	}
	public HtmlPart setHtmlExclude(String o) {
		this.htmlExclude = Boolean.parseBoolean(o);
		this.htmlExcludeWrap.alreadyInitialized = true;
		return (HtmlPart)this;
	}
	protected HtmlPart htmlExcludeInit() {
		if(!htmlExcludeWrap.alreadyInitialized) {
			_htmlExclude(htmlExcludeWrap);
			if(htmlExclude == null)
				setHtmlExclude(htmlExcludeWrap.o);
		}
		htmlExcludeWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public Boolean solrHtmlExclude() {
		return htmlExclude;
	}

	public String strHtmlExclude() {
		return htmlExclude == null ? "" : htmlExclude.toString();
	}

	public String jsonHtmlExclude() {
		return htmlExclude == null ? "" : htmlExclude.toString();
	}

	public String nomAffichageHtmlExclude() {
		return "HTML exclude";
	}

	public String htmTooltipHtmlExclude() {
		return null;
	}

	public String htmHtmlExclude() {
		return htmlExclude == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlExclude());
	}

	////////////////
	// pdfExclude //
	////////////////

	/**	L'entité « pdfExclude »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean pdfExclude;
	@JsonIgnore
	public Wrap<Boolean> pdfExcludeWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("pdfExclude").o(pdfExclude);

	/**	<br/>L'entité « pdfExclude »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pdfExclude">Trouver l'entité pdfExclude dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _pdfExclude(Wrap<Boolean> c);

	public Boolean getPdfExclude() {
		return pdfExclude;
	}

	public void setPdfExclude(Boolean pdfExclude) {
		this.pdfExclude = pdfExclude;
		this.pdfExcludeWrap.alreadyInitialized = true;
	}
	public HtmlPart setPdfExclude(String o) {
		this.pdfExclude = Boolean.parseBoolean(o);
		this.pdfExcludeWrap.alreadyInitialized = true;
		return (HtmlPart)this;
	}
	protected HtmlPart pdfExcludeInit() {
		if(!pdfExcludeWrap.alreadyInitialized) {
			_pdfExclude(pdfExcludeWrap);
			if(pdfExclude == null)
				setPdfExclude(pdfExcludeWrap.o);
		}
		pdfExcludeWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public Boolean solrPdfExclude() {
		return pdfExclude;
	}

	public String strPdfExclude() {
		return pdfExclude == null ? "" : pdfExclude.toString();
	}

	public String jsonPdfExclude() {
		return pdfExclude == null ? "" : pdfExclude.toString();
	}

	public String nomAffichagePdfExclude() {
		return "PDF exclude";
	}

	public String htmTooltipPdfExclude() {
		return null;
	}

	public String htmPdfExclude() {
		return pdfExclude == null ? "" : StringEscapeUtils.escapeHtml4(strPdfExclude());
	}

	/////////////////
	// loginLogout //
	/////////////////

	/**	L'entité « loginLogout »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean loginLogout;
	@JsonIgnore
	public Wrap<Boolean> loginLogoutWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("loginLogout").o(loginLogout);

	/**	<br/>L'entité « loginLogout »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:loginLogout">Trouver l'entité loginLogout dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _loginLogout(Wrap<Boolean> c);

	public Boolean getLoginLogout() {
		return loginLogout;
	}

	public void setLoginLogout(Boolean loginLogout) {
		this.loginLogout = loginLogout;
		this.loginLogoutWrap.alreadyInitialized = true;
	}
	public HtmlPart setLoginLogout(String o) {
		this.loginLogout = Boolean.parseBoolean(o);
		this.loginLogoutWrap.alreadyInitialized = true;
		return (HtmlPart)this;
	}
	protected HtmlPart loginLogoutInit() {
		if(!loginLogoutWrap.alreadyInitialized) {
			_loginLogout(loginLogoutWrap);
			if(loginLogout == null)
				setLoginLogout(loginLogoutWrap.o);
		}
		loginLogoutWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public Boolean solrLoginLogout() {
		return loginLogout;
	}

	public String strLoginLogout() {
		return loginLogout == null ? "" : loginLogout.toString();
	}

	public String jsonLoginLogout() {
		return loginLogout == null ? "" : loginLogout.toString();
	}

	public String nomAffichageLoginLogout() {
		return "login/logout";
	}

	public String htmTooltipLoginLogout() {
		return null;
	}

	public String htmLoginLogout() {
		return loginLogout == null ? "" : StringEscapeUtils.escapeHtml4(strLoginLogout());
	}

	///////////
	// sort1 //
	///////////

	/**	L'entité « sort1 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Double sort1;
	@JsonIgnore
	public Wrap<Double> sort1Wrap = new Wrap<Double>().p(this).c(Double.class).var("sort1").o(sort1);

	/**	<br/>L'entité « sort1 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:sort1">Trouver l'entité sort1 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _sort1(Wrap<Double> c);

	public Double getSort1() {
		return sort1;
	}

	public void setSort1(Double sort1) {
		this.sort1 = sort1;
		this.sort1Wrap.alreadyInitialized = true;
	}
	public HtmlPart setSort1(String o) {
		if(NumberUtils.isParsable(o))
			this.sort1 = Double.parseDouble(o);
		this.sort1Wrap.alreadyInitialized = true;
		return (HtmlPart)this;
	}
	protected HtmlPart sort1Init() {
		if(!sort1Wrap.alreadyInitialized) {
			_sort1(sort1Wrap);
			if(sort1 == null)
				setSort1(sort1Wrap.o);
		}
		sort1Wrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public Double solrSort1() {
		return sort1;
	}

	public String strSort1() {
		return sort1 == null ? "" : sort1.toString();
	}

	public String jsonSort1() {
		return sort1 == null ? "" : sort1.toString();
	}

	public String nomAffichageSort1() {
		return "sort1";
	}

	public String htmTooltipSort1() {
		return null;
	}

	public String htmSort1() {
		return sort1 == null ? "" : StringEscapeUtils.escapeHtml4(strSort1());
	}

	///////////
	// sort2 //
	///////////

	/**	L'entité « sort2 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Double sort2;
	@JsonIgnore
	public Wrap<Double> sort2Wrap = new Wrap<Double>().p(this).c(Double.class).var("sort2").o(sort2);

	/**	<br/>L'entité « sort2 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:sort2">Trouver l'entité sort2 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _sort2(Wrap<Double> c);

	public Double getSort2() {
		return sort2;
	}

	public void setSort2(Double sort2) {
		this.sort2 = sort2;
		this.sort2Wrap.alreadyInitialized = true;
	}
	public HtmlPart setSort2(String o) {
		if(NumberUtils.isParsable(o))
			this.sort2 = Double.parseDouble(o);
		this.sort2Wrap.alreadyInitialized = true;
		return (HtmlPart)this;
	}
	protected HtmlPart sort2Init() {
		if(!sort2Wrap.alreadyInitialized) {
			_sort2(sort2Wrap);
			if(sort2 == null)
				setSort2(sort2Wrap.o);
		}
		sort2Wrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public Double solrSort2() {
		return sort2;
	}

	public String strSort2() {
		return sort2 == null ? "" : sort2.toString();
	}

	public String jsonSort2() {
		return sort2 == null ? "" : sort2.toString();
	}

	public String nomAffichageSort2() {
		return "sort2";
	}

	public String htmTooltipSort2() {
		return null;
	}

	public String htmSort2() {
		return sort2 == null ? "" : StringEscapeUtils.escapeHtml4(strSort2());
	}

	///////////
	// sort3 //
	///////////

	/**	L'entité « sort3 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Double sort3;
	@JsonIgnore
	public Wrap<Double> sort3Wrap = new Wrap<Double>().p(this).c(Double.class).var("sort3").o(sort3);

	/**	<br/>L'entité « sort3 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:sort3">Trouver l'entité sort3 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _sort3(Wrap<Double> c);

	public Double getSort3() {
		return sort3;
	}

	public void setSort3(Double sort3) {
		this.sort3 = sort3;
		this.sort3Wrap.alreadyInitialized = true;
	}
	public HtmlPart setSort3(String o) {
		if(NumberUtils.isParsable(o))
			this.sort3 = Double.parseDouble(o);
		this.sort3Wrap.alreadyInitialized = true;
		return (HtmlPart)this;
	}
	protected HtmlPart sort3Init() {
		if(!sort3Wrap.alreadyInitialized) {
			_sort3(sort3Wrap);
			if(sort3 == null)
				setSort3(sort3Wrap.o);
		}
		sort3Wrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public Double solrSort3() {
		return sort3;
	}

	public String strSort3() {
		return sort3 == null ? "" : sort3.toString();
	}

	public String jsonSort3() {
		return sort3 == null ? "" : sort3.toString();
	}

	public String nomAffichageSort3() {
		return "sort3";
	}

	public String htmTooltipSort3() {
		return null;
	}

	public String htmSort3() {
		return sort3 == null ? "" : StringEscapeUtils.escapeHtml4(strSort3());
	}

	///////////
	// sort4 //
	///////////

	/**	L'entité « sort4 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Double sort4;
	@JsonIgnore
	public Wrap<Double> sort4Wrap = new Wrap<Double>().p(this).c(Double.class).var("sort4").o(sort4);

	/**	<br/>L'entité « sort4 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:sort4">Trouver l'entité sort4 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _sort4(Wrap<Double> c);

	public Double getSort4() {
		return sort4;
	}

	public void setSort4(Double sort4) {
		this.sort4 = sort4;
		this.sort4Wrap.alreadyInitialized = true;
	}
	public HtmlPart setSort4(String o) {
		if(NumberUtils.isParsable(o))
			this.sort4 = Double.parseDouble(o);
		this.sort4Wrap.alreadyInitialized = true;
		return (HtmlPart)this;
	}
	protected HtmlPart sort4Init() {
		if(!sort4Wrap.alreadyInitialized) {
			_sort4(sort4Wrap);
			if(sort4 == null)
				setSort4(sort4Wrap.o);
		}
		sort4Wrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public Double solrSort4() {
		return sort4;
	}

	public String strSort4() {
		return sort4 == null ? "" : sort4.toString();
	}

	public String jsonSort4() {
		return sort4 == null ? "" : sort4.toString();
	}

	public String nomAffichageSort4() {
		return "sort4";
	}

	public String htmTooltipSort4() {
		return null;
	}

	public String htmSort4() {
		return sort4 == null ? "" : StringEscapeUtils.escapeHtml4(strSort4());
	}

	///////////
	// sort5 //
	///////////

	/**	L'entité « sort5 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Double sort5;
	@JsonIgnore
	public Wrap<Double> sort5Wrap = new Wrap<Double>().p(this).c(Double.class).var("sort5").o(sort5);

	/**	<br/>L'entité « sort5 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:sort5">Trouver l'entité sort5 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _sort5(Wrap<Double> c);

	public Double getSort5() {
		return sort5;
	}

	public void setSort5(Double sort5) {
		this.sort5 = sort5;
		this.sort5Wrap.alreadyInitialized = true;
	}
	public HtmlPart setSort5(String o) {
		if(NumberUtils.isParsable(o))
			this.sort5 = Double.parseDouble(o);
		this.sort5Wrap.alreadyInitialized = true;
		return (HtmlPart)this;
	}
	protected HtmlPart sort5Init() {
		if(!sort5Wrap.alreadyInitialized) {
			_sort5(sort5Wrap);
			if(sort5 == null)
				setSort5(sort5Wrap.o);
		}
		sort5Wrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public Double solrSort5() {
		return sort5;
	}

	public String strSort5() {
		return sort5 == null ? "" : sort5.toString();
	}

	public String jsonSort5() {
		return sort5 == null ? "" : sort5.toString();
	}

	public String nomAffichageSort5() {
		return "sort5";
	}

	public String htmTooltipSort5() {
		return null;
	}

	public String htmSort5() {
		return sort5 == null ? "" : StringEscapeUtils.escapeHtml4(strSort5());
	}

	///////////
	// sort6 //
	///////////

	/**	L'entité « sort6 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Double sort6;
	@JsonIgnore
	public Wrap<Double> sort6Wrap = new Wrap<Double>().p(this).c(Double.class).var("sort6").o(sort6);

	/**	<br/>L'entité « sort6 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:sort6">Trouver l'entité sort6 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _sort6(Wrap<Double> c);

	public Double getSort6() {
		return sort6;
	}

	public void setSort6(Double sort6) {
		this.sort6 = sort6;
		this.sort6Wrap.alreadyInitialized = true;
	}
	public HtmlPart setSort6(String o) {
		if(NumberUtils.isParsable(o))
			this.sort6 = Double.parseDouble(o);
		this.sort6Wrap.alreadyInitialized = true;
		return (HtmlPart)this;
	}
	protected HtmlPart sort6Init() {
		if(!sort6Wrap.alreadyInitialized) {
			_sort6(sort6Wrap);
			if(sort6 == null)
				setSort6(sort6Wrap.o);
		}
		sort6Wrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public Double solrSort6() {
		return sort6;
	}

	public String strSort6() {
		return sort6 == null ? "" : sort6.toString();
	}

	public String jsonSort6() {
		return sort6 == null ? "" : sort6.toString();
	}

	public String nomAffichageSort6() {
		return "sort6";
	}

	public String htmTooltipSort6() {
		return null;
	}

	public String htmSort6() {
		return sort6 == null ? "" : StringEscapeUtils.escapeHtml4(strSort6());
	}

	///////////
	// sort7 //
	///////////

	/**	L'entité « sort7 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Double sort7;
	@JsonIgnore
	public Wrap<Double> sort7Wrap = new Wrap<Double>().p(this).c(Double.class).var("sort7").o(sort7);

	/**	<br/>L'entité « sort7 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:sort7">Trouver l'entité sort7 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _sort7(Wrap<Double> c);

	public Double getSort7() {
		return sort7;
	}

	public void setSort7(Double sort7) {
		this.sort7 = sort7;
		this.sort7Wrap.alreadyInitialized = true;
	}
	public HtmlPart setSort7(String o) {
		if(NumberUtils.isParsable(o))
			this.sort7 = Double.parseDouble(o);
		this.sort7Wrap.alreadyInitialized = true;
		return (HtmlPart)this;
	}
	protected HtmlPart sort7Init() {
		if(!sort7Wrap.alreadyInitialized) {
			_sort7(sort7Wrap);
			if(sort7 == null)
				setSort7(sort7Wrap.o);
		}
		sort7Wrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public Double solrSort7() {
		return sort7;
	}

	public String strSort7() {
		return sort7 == null ? "" : sort7.toString();
	}

	public String jsonSort7() {
		return sort7 == null ? "" : sort7.toString();
	}

	public String nomAffichageSort7() {
		return "sort7";
	}

	public String htmTooltipSort7() {
		return null;
	}

	public String htmSort7() {
		return sort7 == null ? "" : StringEscapeUtils.escapeHtml4(strSort7());
	}

	///////////
	// sort8 //
	///////////

	/**	L'entité « sort8 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Double sort8;
	@JsonIgnore
	public Wrap<Double> sort8Wrap = new Wrap<Double>().p(this).c(Double.class).var("sort8").o(sort8);

	/**	<br/>L'entité « sort8 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:sort8">Trouver l'entité sort8 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _sort8(Wrap<Double> c);

	public Double getSort8() {
		return sort8;
	}

	public void setSort8(Double sort8) {
		this.sort8 = sort8;
		this.sort8Wrap.alreadyInitialized = true;
	}
	public HtmlPart setSort8(String o) {
		if(NumberUtils.isParsable(o))
			this.sort8 = Double.parseDouble(o);
		this.sort8Wrap.alreadyInitialized = true;
		return (HtmlPart)this;
	}
	protected HtmlPart sort8Init() {
		if(!sort8Wrap.alreadyInitialized) {
			_sort8(sort8Wrap);
			if(sort8 == null)
				setSort8(sort8Wrap.o);
		}
		sort8Wrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public Double solrSort8() {
		return sort8;
	}

	public String strSort8() {
		return sort8 == null ? "" : sort8.toString();
	}

	public String jsonSort8() {
		return sort8 == null ? "" : sort8.toString();
	}

	public String nomAffichageSort8() {
		return "sort8";
	}

	public String htmTooltipSort8() {
		return null;
	}

	public String htmSort8() {
		return sort8 == null ? "" : StringEscapeUtils.escapeHtml4(strSort8());
	}

	///////////
	// sort9 //
	///////////

	/**	L'entité « sort9 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Double sort9;
	@JsonIgnore
	public Wrap<Double> sort9Wrap = new Wrap<Double>().p(this).c(Double.class).var("sort9").o(sort9);

	/**	<br/>L'entité « sort9 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:sort9">Trouver l'entité sort9 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _sort9(Wrap<Double> c);

	public Double getSort9() {
		return sort9;
	}

	public void setSort9(Double sort9) {
		this.sort9 = sort9;
		this.sort9Wrap.alreadyInitialized = true;
	}
	public HtmlPart setSort9(String o) {
		if(NumberUtils.isParsable(o))
			this.sort9 = Double.parseDouble(o);
		this.sort9Wrap.alreadyInitialized = true;
		return (HtmlPart)this;
	}
	protected HtmlPart sort9Init() {
		if(!sort9Wrap.alreadyInitialized) {
			_sort9(sort9Wrap);
			if(sort9 == null)
				setSort9(sort9Wrap.o);
		}
		sort9Wrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public Double solrSort9() {
		return sort9;
	}

	public String strSort9() {
		return sort9 == null ? "" : sort9.toString();
	}

	public String jsonSort9() {
		return sort9 == null ? "" : sort9.toString();
	}

	public String nomAffichageSort9() {
		return "sort9";
	}

	public String htmTooltipSort9() {
		return null;
	}

	public String htmSort9() {
		return sort9 == null ? "" : StringEscapeUtils.escapeHtml4(strSort9());
	}

	////////////
	// sort10 //
	////////////

	/**	L'entité « sort10 »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Double sort10;
	@JsonIgnore
	public Wrap<Double> sort10Wrap = new Wrap<Double>().p(this).c(Double.class).var("sort10").o(sort10);

	/**	<br/>L'entité « sort10 »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:sort10">Trouver l'entité sort10 dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _sort10(Wrap<Double> c);

	public Double getSort10() {
		return sort10;
	}

	public void setSort10(Double sort10) {
		this.sort10 = sort10;
		this.sort10Wrap.alreadyInitialized = true;
	}
	public HtmlPart setSort10(String o) {
		if(NumberUtils.isParsable(o))
			this.sort10 = Double.parseDouble(o);
		this.sort10Wrap.alreadyInitialized = true;
		return (HtmlPart)this;
	}
	protected HtmlPart sort10Init() {
		if(!sort10Wrap.alreadyInitialized) {
			_sort10(sort10Wrap);
			if(sort10 == null)
				setSort10(sort10Wrap.o);
		}
		sort10Wrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public Double solrSort10() {
		return sort10;
	}

	public String strSort10() {
		return sort10 == null ? "" : sort10.toString();
	}

	public String jsonSort10() {
		return sort10 == null ? "" : sort10.toString();
	}

	public String nomAffichageSort10() {
		return "sort10";
	}

	public String htmTooltipSort10() {
		return null;
	}

	public String htmSort10() {
		return sort10 == null ? "" : StringEscapeUtils.escapeHtml4(strSort10());
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedHtmlPart = false;

	public HtmlPart initDeepHtmlPart(SiteRequestEnUS siteRequest_) {
		if(!alreadyInitializedHtmlPart) {
			alreadyInitializedHtmlPart = true;
			initDeepHtmlPart();
		}
		return (HtmlPart)this;
	}

	public void initDeepHtmlPart() {
		initHtmlPart();
	}

	public void initHtmlPart() {
		htmlPartKeyInit();
		pageDesignKeysInit();
		htmlLinkInit();
		htmlElementInit();
		htmlIdInit();
		htmlClassesInit();
		htmlStyleInit();
		htmlBeforeInit();
		htmlAfterInit();
		htmlTextInit();
		htmlVarInit();
		htmlVarSpanInit();
		htmlVarFormInit();
		htmlVarInputInit();
		htmlVarForEachInit();
		htmlExcludeInit();
		pdfExcludeInit();
		loginLogoutInit();
		sort1Init();
		sort2Init();
		sort3Init();
		sort4Init();
		sort5Init();
		sort6Init();
		sort7Init();
		sort8Init();
		sort9Init();
		sort10Init();
	}

	public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepHtmlPart(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainHtmlPart(v);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.obtainForClass(v);
			}
		}
		return o;
	}
	public Object obtainHtmlPart(String var) {
		HtmlPart oHtmlPart = (HtmlPart)this;
		switch(var) {
			case "htmlPartKey":
				return oHtmlPart.htmlPartKey;
			case "pageDesignKeys":
				return oHtmlPart.pageDesignKeys;
			case "htmlLink":
				return oHtmlPart.htmlLink;
			case "htmlElement":
				return oHtmlPart.htmlElement;
			case "htmlId":
				return oHtmlPart.htmlId;
			case "htmlClasses":
				return oHtmlPart.htmlClasses;
			case "htmlStyle":
				return oHtmlPart.htmlStyle;
			case "htmlBefore":
				return oHtmlPart.htmlBefore;
			case "htmlAfter":
				return oHtmlPart.htmlAfter;
			case "htmlText":
				return oHtmlPart.htmlText;
			case "htmlVar":
				return oHtmlPart.htmlVar;
			case "htmlVarSpan":
				return oHtmlPart.htmlVarSpan;
			case "htmlVarForm":
				return oHtmlPart.htmlVarForm;
			case "htmlVarInput":
				return oHtmlPart.htmlVarInput;
			case "htmlVarForEach":
				return oHtmlPart.htmlVarForEach;
			case "htmlExclude":
				return oHtmlPart.htmlExclude;
			case "pdfExclude":
				return oHtmlPart.pdfExclude;
			case "loginLogout":
				return oHtmlPart.loginLogout;
			case "sort1":
				return oHtmlPart.sort1;
			case "sort2":
				return oHtmlPart.sort2;
			case "sort3":
				return oHtmlPart.sort3;
			case "sort4":
				return oHtmlPart.sort4;
			case "sort5":
				return oHtmlPart.sort5;
			case "sort6":
				return oHtmlPart.sort6;
			case "sort7":
				return oHtmlPart.sort7;
			case "sort8":
				return oHtmlPart.sort8;
			case "sort9":
				return oHtmlPart.sort9;
			case "sort10":
				return oHtmlPart.sort10;
			default:
				return null;
		}
	}

	///////////////
	// attribute //
	///////////////

	public boolean attributeForClass(String var, Object val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = attributeHtmlPart(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeHtmlPart(String var, Object val) {
		HtmlPart oHtmlPart = (HtmlPart)this;
		switch(var) {
			default:
				return null;
		}
	}

	/////////////
	// define //
	/////////////

	public boolean defineForClass(String var, String val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		if(val != null) {
			for(String v : vars) {
				if(o == null)
					o = defineHtmlPart(v, val);
				else if(o instanceof Cluster) {
					Cluster cluster = (Cluster)o;
					o = cluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineHtmlPart(String var, String val) {
		switch(var) {
			case "htmlLink":
				if(val != null)
					setHtmlLink(val);
				savesHtmlPart.add(var);
				return val;
			case "htmlElement":
				if(val != null)
					setHtmlElement(val);
				savesHtmlPart.add(var);
				return val;
			case "htmlId":
				if(val != null)
					setHtmlId(val);
				savesHtmlPart.add(var);
				return val;
			case "htmlClasses":
				if(val != null)
					setHtmlClasses(val);
				savesHtmlPart.add(var);
				return val;
			case "htmlStyle":
				if(val != null)
					setHtmlStyle(val);
				savesHtmlPart.add(var);
				return val;
			case "htmlBefore":
				if(val != null)
					setHtmlBefore(val);
				savesHtmlPart.add(var);
				return val;
			case "htmlAfter":
				if(val != null)
					setHtmlAfter(val);
				savesHtmlPart.add(var);
				return val;
			case "htmlText":
				if(val != null)
					setHtmlText(val);
				savesHtmlPart.add(var);
				return val;
			case "htmlVar":
				if(val != null)
					setHtmlVar(val);
				savesHtmlPart.add(var);
				return val;
			case "htmlVarSpan":
				if(val != null)
					setHtmlVarSpan(val);
				savesHtmlPart.add(var);
				return val;
			case "htmlVarForm":
				if(val != null)
					setHtmlVarForm(val);
				savesHtmlPart.add(var);
				return val;
			case "htmlVarInput":
				if(val != null)
					setHtmlVarInput(val);
				savesHtmlPart.add(var);
				return val;
			case "htmlVarForEach":
				if(val != null)
					setHtmlVarForEach(val);
				savesHtmlPart.add(var);
				return val;
			case "htmlExclude":
				if(val != null)
					setHtmlExclude(val);
				savesHtmlPart.add(var);
				return val;
			case "pdfExclude":
				if(val != null)
					setPdfExclude(val);
				savesHtmlPart.add(var);
				return val;
			case "loginLogout":
				if(val != null)
					setLoginLogout(val);
				savesHtmlPart.add(var);
				return val;
			case "sort1":
				if(val != null)
					setSort1(val);
				savesHtmlPart.add(var);
				return val;
			case "sort2":
				if(val != null)
					setSort2(val);
				savesHtmlPart.add(var);
				return val;
			case "sort3":
				if(val != null)
					setSort3(val);
				savesHtmlPart.add(var);
				return val;
			case "sort4":
				if(val != null)
					setSort4(val);
				savesHtmlPart.add(var);
				return val;
			case "sort5":
				if(val != null)
					setSort5(val);
				savesHtmlPart.add(var);
				return val;
			case "sort6":
				if(val != null)
					setSort6(val);
				savesHtmlPart.add(var);
				return val;
			case "sort7":
				if(val != null)
					setSort7(val);
				savesHtmlPart.add(var);
				return val;
			case "sort8":
				if(val != null)
					setSort8(val);
				savesHtmlPart.add(var);
				return val;
			case "sort9":
				if(val != null)
					setSort9(val);
				savesHtmlPart.add(var);
				return val;
			case "sort10":
				if(val != null)
					setSort10(val);
				savesHtmlPart.add(var);
				return val;
			default:
				return null;
		}
	}

	/////////////////
	// saves //
	/////////////////

	protected List<String> savesHtmlPart = new ArrayList<String>();

	/////////////
	// populate //
	/////////////

	public void populateForClass(SolrDocument solrDocument) {
		populateHtmlPart(solrDocument);
	}
	public void populateHtmlPart(SolrDocument solrDocument) {
		HtmlPart oHtmlPart = (HtmlPart)this;
		savesHtmlPart = (List<String>)solrDocument.get("savesHtmlPart_stored_strings");
		if(savesHtmlPart != null) {

			if(savesHtmlPart.contains("htmlPartKey")) {
				Long htmlPartKey = (Long)solrDocument.get("htmlPartKey_stored_long");
				if(htmlPartKey != null)
					oHtmlPart.setHtmlPartKey(htmlPartKey);
			}

			if(savesHtmlPart.contains("pageDesignKeys")) {
				List<Long> pageDesignKeys = (List<Long>)solrDocument.get("pageDesignKeys_stored_longs");
				if(pageDesignKeys != null)
					oHtmlPart.pageDesignKeys.addAll(pageDesignKeys);
			}

			if(savesHtmlPart.contains("htmlLink")) {
				String htmlLink = (String)solrDocument.get("htmlLink_stored_string");
				if(htmlLink != null)
					oHtmlPart.setHtm