package org.computate.medicale.enUS.patient;

import org.computate.medicale.enUS.writer.AllWriter;
import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
import java.text.NumberFormat;
import io.vertx.core.logging.LoggerFactory;
import java.util.ArrayList;
import org.computate.medicale.enUS.request.SiteRequestEnUS;
import org.apache.commons.collections.CollectionUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.computate.medicale.enUS.request.api.ApiRequest;
import io.vertx.core.logging.Logger;
import org.computate.medicale.enUS.wrap.Wrap;
import org.computate.medicale.enUS.patient.PatientGenPage;
import java.math.MathContext;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Objects;
import io.vertx.core.json.JsonArray;
import org.apache.commons.lang3.math.NumberUtils;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.computate.medicale.enUS.cluster.Cluster;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

/**	
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.patient.PatientPage&fq=classeEtendGen_indexed_boolean:true">Trouver la classe  dans Solr</a>
 * <br/>
 **/
public abstract class PatientPageGen<DEV> extends PatientGenPage {
	protected static final Logger LOGGER = LoggerFactory.getLogger(PatientPage.class);

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedPatientPage = false;

	public PatientPage initDeepPatientPage(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedPatientPage) {
			alreadyInitializedPatientPage = true;
			initDeepPatientPage();
		}
		return (PatientPage)this;
	}

	public void initDeepPatientPage() {
		initPatientPage();
		super.initDeepPatientGenPage(siteRequest_);
	}

	public void initPatientPage() {
	}

	@Override public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepPatientPage(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestPatientPage(SiteRequestEnUS siteRequest_) {
			super.siteRequestPatientGenPage(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestPatientPage(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainPatientPage(v);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.obtainForClass(v);
			}
		}
		return o;
	}
	public Object obtainPatientPage(String var) {
		PatientPage oPatientPage = (PatientPage)this;
		switch(var) {
			default:
				return super.obtainPatientGenPage(var);
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
				o = attributePatientPage(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributePatientPage(String var, Object val) {
		PatientPage oPatientPage = (PatientPage)this;
		switch(var) {
			default:
				return super.attributePatientGenPage(var, val);
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
					o = definePatientPage(v, val);
				else if(o instanceof Cluster) {
					Cluster cluster = (Cluster)o;
					o = cluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object definePatientPage(String var, String val) {
		switch(var) {
			default:
				return super.definePatientGenPage(var, val);
		}
	}

	/////////////////
	// htmlScripts //
	/////////////////

	@Override public void htmlScripts() {
		htmlScriptsPatientPage();
		super.htmlScripts();
	}

	public void htmlScriptsPatientPage() {
	}

	////////////////
	// htmlScript //
	////////////////

	@Override public void htmlScript() {
		htmlScriptPatientPage();
		super.htmlScript();
	}

	public void htmlScriptPatientPage() {
	}

	//////////////
	// htmlBody //
	//////////////

	@Override public void htmlBody() {
		htmlBodyPatientPage();
		super.htmlBody();
	}

	public void htmlBodyPatientPage() {
	}

	//////////
	// html //
	//////////

	@Override public void html() {
		htmlPatientPage();
		super.html();
	}

	public void htmlPatientPage() {
	}

	//////////////
	// htmlMeta //
	//////////////

	@Override public void htmlMeta() {
		htmlMetaPatientPage();
		super.htmlMeta();
	}

	public void htmlMetaPatientPage() {
	}

	////////////////
	// htmlStyles //
	////////////////

	@Override public void htmlStyles() {
		htmlStylesPatientPage();
		super.htmlStyles();
	}

	public void htmlStylesPatientPage() {
	}

	///////////////
	// htmlStyle //
	///////////////

	@Override public void htmlStyle() {
		htmlStylePatientPage();
		super.htmlStyle();
	}

	public void htmlStylePatientPage() {
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestPatientPage() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof PatientPage) {
			PatientPage original = (PatientPage)o;
			super.apiRequestPatientGenPage();
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
		if(!(o instanceof PatientPage))
			return false;
		PatientPage that = (PatientPage)o;
		return super.equals(o);
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("PatientPage { ");
		sb.append(" }");
		return sb.toString();
	}
}
