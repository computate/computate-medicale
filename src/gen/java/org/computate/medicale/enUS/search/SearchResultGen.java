package org.computate.medicale.enUS.search;

import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.computate.medicale.enUS.cluster.Cluster;
import org.computate.medicale.enUS.request.api.ApiRequest;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
import java.text.NumberFormat;
import io.vertx.core.logging.LoggerFactory;
import java.util.ArrayList;
import org.apache.commons.collections.CollectionUtils;
import java.lang.Long;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.lang.String;
import io.vertx.core.logging.Logger;
import java.math.RoundingMode;
import org.computate.medicale.enUS.wrap.Wrap;
import java.math.MathContext;
import org.computate.medicale.enUS.writer.AllWriter;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.lang.Exception;
import java.util.Objects;
import io.vertx.core.json.JsonArray;
import org.apache.solr.common.SolrDocument;
import org.computate.medicale.enUS.request.SiteRequestEnUS;
import org.apache.commons.lang3.math.NumberUtils;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.lang.Object;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

/**	
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.search.SearchResult&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class SearchResultGen<DEV> extends Object {
	protected static final Logger LOGGER = LoggerFactory.getLogger(SearchResult.class);

	//////////////////
	// siteRequest_ //
	//////////////////

	/**	 The entity siteRequest_
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected SiteRequestEnUS siteRequest_;
	@JsonIgnore
	public Wrap<SiteRequestEnUS> siteRequest_Wrap = new Wrap<SiteRequestEnUS>().p(this).c(SiteRequestEnUS.class).var("siteRequest_").o(siteRequest_);

	/**	<br/> The entity siteRequest_
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.search.SearchResult&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:siteRequest_">Find the entity siteRequest_ in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _siteRequest_(Wrap<SiteRequestEnUS> c) throws Exception, Exception;

	public SiteRequestEnUS getSiteRequest_() {
		return siteRequest_;
	}

	public void setSiteRequest_(SiteRequestEnUS siteRequest_) {
		this.siteRequest_ = siteRequest_;
		this.siteRequest_Wrap.alreadyInitialized = true;
	}
	protected SearchResult siteRequest_Init() throws Exception {
		if(!siteRequest_Wrap.alreadyInitialized) {
			_siteRequest_(siteRequest_Wrap);
			if(siteRequest_ == null)
				setSiteRequest_(siteRequest_Wrap.o);
		}
		siteRequest_Wrap.alreadyInitialized(true);
		return (SearchResult)this;
	}

	//////////////////
	// solrDocument //
	//////////////////

	/**	 The entity solrDocument
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected SolrDocument solrDocument;
	@JsonIgnore
	public Wrap<SolrDocument> solrDocumentWrap = new Wrap<SolrDocument>().p(this).c(SolrDocument.class).var("solrDocument").o(solrDocument);

	/**	<br/> The entity solrDocument
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.search.SearchResult&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:solrDocument">Find the entity solrDocument in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _solrDocument(Wrap<SolrDocument> c) throws Exception, Exception;

	public SolrDocument getSolrDocument() {
		return solrDocument;
	}

	public void setSolrDocument(SolrDocument solrDocument) {
		this.solrDocument = solrDocument;
		this.solrDocumentWrap.alreadyInitialized = true;
	}
	protected SearchResult solrDocumentInit() throws Exception {
		if(!solrDocumentWrap.alreadyInitialized) {
			_solrDocument(solrDocumentWrap);
			if(solrDocument == null)
				setSolrDocument(solrDocumentWrap.o);
		}
		solrDocumentWrap.alreadyInitialized(true);
		return (SearchResult)this;
	}

	/////////////////
	// resultIndex //
	/////////////////

	/**	 The entity resultIndex
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long resultIndex;
	@JsonIgnore
	public Wrap<Long> resultIndexWrap = new Wrap<Long>().p(this).c(Long.class).var("resultIndex").o(resultIndex);

	/**	<br/> The entity resultIndex
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.search.SearchResult&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:resultIndex">Find the entity resultIndex in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _resultIndex(Wrap<Long> c) throws Exception, Exception;

	public Long getResultIndex() {
		return resultIndex;
	}

	public void setResultIndex(Long resultIndex) {
		this.resultIndex = resultIndex;
		this.resultIndexWrap.alreadyInitialized = true;
	}
	public SearchResult setResultIndex(String o) {
		if(NumberUtils.isParsable(o))
			this.resultIndex = Long.parseLong(o);
		this.resultIndexWrap.alreadyInitialized = true;
		return (SearchResult)this;
	}
	protected SearchResult resultIndexInit() throws Exception {
		if(!resultIndexWrap.alreadyInitialized) {
			_resultIndex(resultIndexWrap);
			if(resultIndex == null)
				setResultIndex(resultIndexWrap.o);
		}
		resultIndexWrap.alreadyInitialized(true);
		return (SearchResult)this;
	}

	public Long solrResultIndex() {
		return resultIndex;
	}

	public String strResultIndex() {
		return resultIndex == null ? "" : resultIndex.toString();
	}

	public String jsonResultIndex() {
		return resultIndex == null ? "" : resultIndex.toString();
	}

	public String nomAffichageResultIndex() {
		return null;
	}

	public String htmTooltipResultIndex() {
		return null;
	}

	public String htmResultIndex() {
		return resultIndex == null ? "" : StringEscapeUtils.escapeHtml4(strResultIndex());
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedSearchResult = false;

	public SearchResult initDeepSearchResult(SiteRequestEnUS siteRequest_) throws Exception {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedSearchResult) {
			alreadyInitializedSearchResult = true;
			initDeepSearchResult();
		}
		return (SearchResult)this;
	}

	public void initDeepSearchResult() throws Exception {
		initSearchResult();
	}

	public void initSearchResult() throws Exception {
		siteRequest_Init();
		solrDocumentInit();
		resultIndexInit();
	}

	public void initDeepForClass(SiteRequestEnUS siteRequest_) throws Exception {
		initDeepSearchResult(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestSearchResult(SiteRequestEnUS siteRequest_) {
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestSearchResult(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainSearchResult(v);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.obtainForClass(v);
			}
		}
		return o;
	}
	public Object obtainSearchResult(String var) {
		SearchResult oSearchResult = (SearchResult)this;
		switch(var) {
			case "siteRequest_":
				return oSearchResult.siteRequest_;
			case "solrDocument":
				return oSearchResult.solrDocument;
			case "resultIndex":
				return oSearchResult.resultIndex;
			default:
				return null;
		}
	}

	///////////////
	// attribute //
	///////////////

	public boolean attributeForClass(String var, Object val) throws Exception {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = attributeSearchResult(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeSearchResult(String var, Object val) {
		SearchResult oSearchResult = (SearchResult)this;
		switch(var) {
			default:
				return null;
		}
	}

	/////////////
	// define //
	/////////////

	public boolean defineForClass(String var, String val) throws Exception {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		if(val != null) {
			for(String v : vars) {
				if(o == null)
					o = defineSearchResult(v, val);
				else if(o instanceof Cluster) {
					Cluster cluster = (Cluster)o;
					o = cluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineSearchResult(String var, String val) {
		switch(var) {
			default:
				return null;
		}
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestSearchResult() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof SearchResult) {
			SearchResult original = (SearchResult)o;
		}
	}

	//////////////
	// hashCode //
	//////////////

	@Override public int hashCode() {
		return Objects.hash();
	}

	////////////
	// equals //
	////////////

	@Override public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof SearchResult))
			return false;
		SearchResult that = (SearchResult)o;
		return true;
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("SearchResult { ");
		sb.append(" }");
		return sb.toString();
	}
}
