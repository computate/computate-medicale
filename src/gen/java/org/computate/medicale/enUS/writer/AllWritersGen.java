package org.computate.medicale.enUS.writer;

import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.computate.medicale.enUS.cluster.Cluster;
import org.computate.medicale.enUS.request.api.ApiRequest;
import org.apache.commons.lang3.StringUtils;
import java.text.NumberFormat;
import io.vertx.core.logging.LoggerFactory;
import java.util.ArrayList;
import org.apache.commons.collections.CollectionUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.vertx.core.logging.Logger;
import org.computate.medicale.enUS.wrap.Wrap;
import java.math.MathContext;
import org.computate.medicale.enUS.writer.AllWriter;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Objects;
import io.vertx.core.json.JsonArray;
import java.util.List;
import org.computate.medicale.enUS.request.SiteRequestEnUS;
import org.apache.commons.lang3.math.NumberUtils;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.lang.Object;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

/**	
 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.writer.AllWriters&fq=classeEtendGen_indexed_boolean:true">Trouver la classe  dans Solr</a>
 * <br/>
 **/
public abstract class AllWritersGen<DEV> extends Object {
	protected static final Logger LOGGER = LoggerFactory.getLogger(AllWriters.class);

	//////////////////
	// siteRequest_ //
	//////////////////

	/**	L'entité « siteRequest_ »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected SiteRequestEnUS siteRequest_;
	@JsonIgnore
	public Wrap<SiteRequestEnUS> siteRequest_Wrap = new Wrap<SiteRequestEnUS>().p(this).c(SiteRequestEnUS.class).var("siteRequest_").o(siteRequest_);

	/**	<br/>L'entité « siteRequest_ »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.writer.AllWriters&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:siteRequest_">Trouver l'entité siteRequest_ dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _siteRequest_(Wrap<SiteRequestEnUS> c);

	public SiteRequestEnUS getSiteRequest_() {
		return siteRequest_;
	}

	public void setSiteRequest_(SiteRequestEnUS siteRequest_) {
		this.siteRequest_ = siteRequest_;
		this.siteRequest_Wrap.alreadyInitialized = true;
	}
	protected AllWriters siteRequest_Init() {
		if(!siteRequest_Wrap.alreadyInitialized) {
			_siteRequest_(siteRequest_Wrap);
			if(siteRequest_ == null)
				setSiteRequest_(siteRequest_Wrap.o);
		}
		siteRequest_Wrap.alreadyInitialized(true);
		return (AllWriters)this;
	}

	/////////////
	// writers //
	/////////////

	/**	L'entité « writers »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<AllWriter>(). 
	 */
	@JsonInclude(Include.NON_NULL)
	protected List<AllWriter> writers = new ArrayList<AllWriter>();
	@JsonIgnore
	public Wrap<List<AllWriter>> writersWrap = new Wrap<List<AllWriter>>().p(this).c(List.class).var("writers").o(writers);

	/**	<br/>L'entité « writers »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<AllWriter>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.writer.AllWriters&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:writers">Trouver l'entité writers dans Solr</a>
	 * <br/>
	 * @param writers est l'entité déjà construit. 
	 **/
	protected abstract void _writers(List<AllWriter> c);

	public List<AllWriter> getWriters() {
		return writers;
	}

	public void setWriters(List<AllWriter> writers) {
		this.writers = writers;
		this.writersWrap.alreadyInitialized = true;
	}
	public AllWriters addWriters(AllWriter...objets) {
		for(AllWriter o : objets) {
			addWriters(o);
		}
		return (AllWriters)this;
	}
	public AllWriters addWriters(AllWriter o) {
		if(o != null && !writers.contains(o))
			this.writers.add(o);
		return (AllWriters)this;
	}
	protected AllWriters writersInit() {
		if(!writersWrap.alreadyInitialized) {
			_writers(writers);
		}
		writersWrap.alreadyInitialized(true);
		return (AllWriters)this;
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedAllWriters = false;

	public AllWriters initDeepAllWriters(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedAllWriters) {
			alreadyInitializedAllWriters = true;
			initDeepAllWriters();
		}
		return (AllWriters)this;
	}

	public void initDeepAllWriters() {
		initAllWriters();
	}

	public void initAllWriters() {
		siteRequest_Init();
		writersInit();
	}

	public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepAllWriters(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestAllWriters(SiteRequestEnUS siteRequest_) {
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestAllWriters(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainAllWriters(v);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.obtainForClass(v);
			}
		}
		return o;
	}
	public Object obtainAllWriters(String var) {
		AllWriters oAllWriters = (AllWriters)this;
		switch(var) {
			case "siteRequest_":
				return oAllWriters.siteRequest_;
			case "writers":
				return oAllWriters.writers;
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
				o = attributeAllWriters(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeAllWriters(String var, Object val) {
		AllWriters oAllWriters = (AllWriters)this;
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
					o = defineAllWriters(v, val);
				else if(o instanceof Cluster) {
					Cluster cluster = (Cluster)o;
					o = cluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineAllWriters(String var, String val) {
		switch(var) {
			default:
				return null;
		}
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestAllWriters() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof AllWriters) {
			AllWriters original = (AllWriters)o;
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
		if(!(o instanceof AllWriters))
			return false;
		AllWriters that = (AllWriters)o;
		return true;
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("AllWriters { ");
		sb.append(" }");
		return sb.toString();
	}
}
