package org.computate.medicale.enUS.design;

import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.PageDesign&fq=classeEtendGen_indexed_boolean:true">Trouver la classe  dans Solr</a>
 * <br/>
 **/
public abstract class PageDesignGen<DEV> extends Cluster {
	protected static final Logger LOGGER = LoggerFactory.getLogger(PageDesign.class);

	public static final List<String> ROLES = Arrays.asList("SiteAdmin");
	public static final List<String> ROLE_READS = Arrays.asList("");

	public static final String PageDesign_AName = "a page design";
	public static final String PageDesign_This = "this ";
	public static final String PageDesign_ThisName = "this page design";
	public static final String PageDesign_A = "a ";
	public static final String PageDesign_TheName = "the page design";
	public static final String PageDesign_NameSingular = "page design";
	public static final String PageDesign_NamePlural = "page designs";
	public static final String PageDesign_NameActual = "current page design";
	public static final String PageDesign_AllName = "all the page designs";
	public static final String PageDesign_SearchAllNameBy = "search page designs by ";
	public static final String PageDesign_ThePluralName = "the page designs";
	public static final String PageDesign_NoNameFound = "no page design found";
	public static final String PageDesign_NameVar = "page-design";
	public static final String PageDesign_OfName = "of page design";
	public static final String PageDesign_ANameAdjective = "a page design";
	public static final String PageDesign_NameAdjectiveSingular = "page design";
	public static final String PageDesign_NameAdjectivePlural = "page designs";
	public static final String PageDesign_Color = "khaki";
	public static final String PageDesign_IconGroup = "regular";
	public static final String PageDesign_IconName = "drafting-compass";
	public static final Integer PageDesign_Rows = 100;

	///////////////////
	// pageDesignKey //
	///////////////////

	/**	L'entité « pageDesignKey »
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long pageDesignKey;
	@JsonIgnore
	public Wrap<Long> pageDesignKeyWrap = new Wrap<Long>().p(this).c(Long.class).var("pageDesignKey").o(pageDesignKey);

	/**	<br/>L'entité « pageDesignKey »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.PageDesign&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageDesignKey">Trouver l'entité pageDesignKey dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _pageDesignKey(Wrap<Long> c);

	public Long getPageDesignKey() {
		return pageDesignKey;
	}

	public void setPageDesignKey(Long pageDesignKey) {
		this.pageDesignKey = pageDesignKey;
		this.pageDesignKeyWrap.alreadyInitialized = true;
	}
	public PageDesign setPageDesignKey(String o) {
		if(NumberUtils.isParsable(o))
			this.pageDesignKey = Long.parseLong(o);
		this.pageDesignKeyWrap.alreadyInitialized = true;
		return (PageDesign)this;
	}
	protected PageDesign pageDesignKeyInit() {
		if(!pageDesignKeyWrap.alreadyInitialized) {
			_pageDesignKey(pageDesignKeyWrap);
			if(pageDesignKey == null)
				setPageDesignKey(pageDesignKeyWrap.o);
		}
		pageDesignKeyWrap.alreadyInitialized(true);
		return (PageDesign)this;
	}

	public Long solrPageDesignKey() {
		return pageDesignKey;
	}

	public String strPageDesignKey() {
		return pageDesignKey == null ? "" : pageDesignKey.toString();
	}

	public String jsonPageDesignKey() {
		return pageDesignKey == null ? "" : pageDesignKey.toString();
	}

	public String nomAffichagePageDesignKey() {
		return "key";
	}

	public String htmTooltipPageDesignKey() {
		return null;
	}

	public String htmPageDesignKey() {
		return pageDesignKey == null ? "" : StringEscapeUtils.escapeHtml4(strPageDesignKey());
	}

	/////////////////////
	// childDesignKeys //
	/////////////////////

	/**	L'entité « childDesignKeys »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> childDesignKeys = new java.util.ArrayList<java.lang.Long>();
	@JsonIgnore
	public Wrap<List<Long>> childDesignKeysWrap = new Wrap<List<Long>>().p(this).c(List.class).var("childDesignKeys").o(childDesignKeys);

	/**	<br/>L'entité « childDesignKeys »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.PageDesign&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:childDesignKeys">Trouver l'entité childDesignKeys dans Solr</a>
	 * <br/>
	 * @param childDesignKeys est l'entité déjà construit. 
	 **/
	protected abstract void _childDesignKeys(List<Long> c);

	public List<Long> getChildDesignKeys() {
		return childDesignKeys;
	}

	public void setChildDesignKeys(List<Long> childDesignKeys) {
		this.childDesignKeys = childDesignKeys;
		this.childDesignKeysWrap.alreadyInitialized = true;
	}
	public PageDesign addChildDesignKeys(Long...objets) {
		for(Long o : objets) {
			addChildDesignKeys(o);
		}
		return (PageDesign)this;
	}
	public PageDesign addChildDesignKeys(Long o) {
		if(o != null && !childDesignKeys.contains(o))
			this.childDesignKeys.add(o);
		return (PageDesign)this;
	}
	public PageDesign setChildDesignKeys(JsonArray objets) {
		childDesignKeys.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addChildDesignKeys(o);
		}
		return (PageDesign)this;
	}
	public PageDesign addChildDesignKeys(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addChildDesignKeys(p);
		}
		return (PageDesign)this;
	}
	protected PageDesign childDesignKeysInit() {
		if(!childDesignKeysWrap.alreadyInitialized) {
			_childDesignKeys(childDesignKeys);
		}
		childDesignKeysWrap.alreadyInitialized(true);
		return (PageDesign)this;
	}

	public List<Long> solrChildDesignKeys() {
		return childDesignKeys;
	}

	public String strChildDesignKeys() {
		return childDesignKeys == null ? "" : childDesignKeys.toString();
	}

	public String jsonChildDesignKeys() {
		return childDesignKeys == null ? "" : childDesignKeys.toString();
	}

	public String nomAffichageChildDesignKeys() {
		return "child designs";
	}

	public String htmTooltipChildDesignKeys() {
		return null;
	}

	public String htmChildDesignKeys() {
		return childDesignKeys == null ? "" : StringEscapeUtils.escapeHtml4(strChildDesignKeys());
	}

	//////////////////////
	// parentDesignKeys //
	//////////////////////

	/**	L'entité « parentDesignKeys »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> parentDesignKeys = new java.util.ArrayList<java.lang.Long>();
	@JsonIgnore
	public Wrap<List<Long>> parentDesignKeysWrap = new Wrap<List<Long>>().p(this).c(List.class).var("parentDesignKeys").o(parentDesignKeys);

	/**	<br/>L'entité « parentDesignKeys »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.PageDesign&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:parentDesignKeys">Trouver l'entité parentDesignKeys dans Solr</a>
	 * <br/>
	 * @param parentDesignKeys est l'entité déjà construit. 
	 **/
	protected abstract void _parentDesignKeys(List<Long> c);

	public List<Long> getParentDesignKeys() {
		return parentDesignKeys;
	}

	public void setParentDesignKeys(List<Long> parentDesignKeys) {
		this.parentDesignKeys = parentDesignKeys;
		this.parentDesignKeysWrap.alreadyInitialized = true;
	}
	public PageDesign addParentDesignKeys(Long...objets) {
		for(Long o : objets) {
			addParentDesignKeys(o);
		}
		return (PageDesign)this;
	}
	public PageDesign addParentDesignKeys(Long o) {
		if(o != null && !parentDesignKeys.contains(o))
			this.parentDesignKeys.add(o);
		return (PageDesign)this;
	}
	public PageDesign setParentDesignKeys(JsonArray objets) {
		parentDesignKeys.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addParentDesignKeys(o);
		}
		return (PageDesign)this;
	}
	public PageDesign addParentDesignKeys(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addParentDesignKeys(p);
		}
		return (PageDesign)this;
	}
	protected PageDesign parentDesignKeysInit() {
		if(!parentDesignKeysWrap.alreadyInitialized) {
			_parentDesignKeys(parentDesignKeys);
		}
		parentDesignKeysWrap.alreadyInitialized(true);
		return (PageDesign)this;
	}

	public List<Long> solrParentDesignKeys() {
		return parentDesignKeys;
	}

	public String strParentDesignKeys() {
		return parentDesignKeys == null ? "" : parentDesignKeys.toString();
	}

	public String jsonParentDesignKeys() {
		return parentDesignKeys == null ? "" : parentDesignKeys.toString();
	}

	public String nomAffichageParentDesignKeys() {
		return "parent designs";
	}

	public String htmTooltipParentDesignKeys() {
		return null;
	}

	public String htmParentDesignKeys() {
		return parentDesignKeys == null ? "" : StringEscapeUtils.escapeHtml4(strParentDesignKeys());
	}

	//////////////////
	// htmlPartKeys //
	//////////////////

	/**	L'entité « htmlPartKeys »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> htmlPartKeys = new java.util.ArrayList<java.lang.Long>();
	@JsonIgnore
	public Wrap<List<Long>> htmlPartKeysWrap = new Wrap<List<Long>>().p(this).c(List.class).var("htmlPartKeys").o(htmlPartKeys);

	/**	<br/>L'entité « htmlPartKeys »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.PageDesign&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:htmlPartKeys">Trouver l'entité htmlPartKeys dans Solr</a>
	 * <br/>
	 * @param htmlPartKeys est l'entité déjà construit. 
	 **/
	protected abstract void _htmlPartKeys(List<Long> o);

	public List<Long> getHtmlPartKeys() {
		return htmlPartKeys;
	}

	public void setHtmlPartKeys(List<Long> htmlPartKeys) {
		this.htmlPartKeys = htmlPartKeys;
		this.htmlPartKeysWrap.alreadyInitialized = true;
	}
	public PageDesign addHtmlPartKeys(Long...objets) {
		for(Long o : objets) {
			addHtmlPartKeys(o);
		}
		return (PageDesign)this;
	}
	public PageDesign addHtmlPartKeys(Long o) {
		if(o != null && !htmlPartKeys.contains(o))
			this.htmlPartKeys.add(o);
		return (PageDesign)this;
	}
	public PageDesign setHtmlPartKeys(JsonArray objets) {
		htmlPartKeys.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addHtmlPartKeys(o);
		}
		return (PageDesign)this;
	}
	public PageDesign addHtmlPartKeys(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addHtmlPartKeys(p);
		}
		return (PageDesign)this;
	}
	protected PageDesign htmlPartKeysInit() {
		if(!htmlPartKeysWrap.alreadyInitialized) {
			_htmlPartKeys(htmlPartKeys);
		}
		htmlPartKeysWrap.alreadyInitialized(true);
		return (PageDesign)this;
	}

	public List<Long> solrHtmlPartKeys() {
		return htmlPartKeys;
	}

	public String strHtmlPartKeys() {
		return htmlPartKeys == null ? "" : htmlPartKeys.toString();
	}

	public String jsonHtmlPartKeys() {
		return htmlPartKeys == null ? "" : htmlPartKeys.toString();
	}

	public String nomAffichageHtmlPartKeys() {
		return "parts";
	}

	public String htmTooltipHtmlPartKeys() {
		return null;
	}

	public String htmHtmlPartKeys() {
		return htmlPartKeys == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlPartKeys());
	}

	////////////////////////////
	// pageDesignCompleteName //
	////////////////////////////

	/**	L'entité « pageDesignCompleteName »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String pageDesignCompleteName;
	@JsonIgnore
	public Wrap<String> pageDesignCompleteNameWrap = new Wrap<String>().p(this).c(String.class).var("pageDesignCompleteName").o(pageDesignCompleteName);

	/**	<br/>L'entité « pageDesignCompleteName »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.design.PageDesign&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageDesignCompleteName">Trouver l'entité pageDesignCompleteName dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _pageDesignCompleteName(Wrap<String> c);

	public String getPageDesignCompleteName() {
		return pageDesignCompleteName;
	}

	public void setPageDesignCompleteName(String pageDesignCompleteName) {
		this.pageDesignCompleteName = pageDesignCompleteName;
		this.pageDesignCompleteNameWrap.alreadyInitialized = true;
	}
	protected PageDesign pageDesignCompleteNameInit() {
		if(!pageDesignCompleteNameWrap.alreadyInitialized) {
			_pageDesignCompleteName(pageDesignCompleteNameWrap);
			if(pageDesignCompleteName == null)
				setPageDesignCompleteName(pageDesignCompleteNameWrap.o);
		}
		pageDesignCompleteNameWrap.alreadyInitialized(true);
		return (PageDesign)this;
	}

	public String solrPageDesignCompleteName() {
		return pageDesignCompleteName;
	}

	public String strPageDe