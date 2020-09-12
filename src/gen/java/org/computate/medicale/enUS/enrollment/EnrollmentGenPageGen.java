package org.computate.medicale.enUS.enrollment;

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
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.computate.medicale.enUS.request.api.ApiRequest;
import io.vertx.core.logging.Logger;
import org.computate.medicale.enUS.wrap.Wrap;
import org.computate.medicale.enUS.cluster.ClusterPage;
import java.math.MathContext;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Objects;
import io.vertx.core.json.JsonArray;
import org.computate.medicale.enUS.search.SearchList;
import org.apache.commons.lang3.math.NumberUtils;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.computate.medicale.enUS.cluster.Cluster;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

/**	
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.EnrollmentGenPage&fq=classeEtendGen_indexed_boolean:true">Trouver la classe  dans Solr</a>
 * <br/>
 **/
public abstract class EnrollmentGenPageGen<DEV> extends ClusterPage {
	protected static final Logger LOGGER = LoggerFactory.getLogger(EnrollmentGenPage.class);

	///////////////////////////
	// listMedicalEnrollment //
	///////////////////////////

	/**	L'entité « listMedicalEnrollment »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected SearchList<MedicalEnrollment> listMedicalEnrollment;
	@JsonIgnore
	public Wrap<SearchList<MedicalEnrollment>> listMedicalEnrollmentWrap = new Wrap<SearchList<MedicalEnrollment>>().p(this).c(SearchList.class).var("listMedicalEnrollment").o(listMedicalEnrollment);

	/**	<br/>L'entité « listMedicalEnrollment »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.EnrollmentGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:listMedicalEnrollment">Trouver l'entité listMedicalEnrollment dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _listMedicalEnrollment(Wrap<SearchList<MedicalEnrollment>> c);

	public SearchList<MedicalEnrollment> getListMedicalEnrollment() {
		return listMedicalEnrollment;
	}

	public void setListMedicalEnrollment(SearchList<MedicalEnrollment> listMedicalEnrollment) {
		this.listMedicalEnrollment = listMedicalEnrollment;
		this.listMedicalEnrollmentWrap.alreadyInitialized = true;
	}
	protected EnrollmentGenPage listMedicalEnrollmentInit() {
		if(!listMedicalEnrollmentWrap.alreadyInitialized) {
			_listMedicalEnrollment(listMedicalEnrollmentWrap);
			if(listMedicalEnrollment == null)
				setListMedicalEnrollment(listMedicalEnrollmentWrap.o);
		}
		if(listMedicalEnrollment != null)
			listMedicalEnrollment.initDeepForClass(siteRequest_);
		listMedicalEnrollmentWrap.alreadyInitialized(true);
		return (EnrollmentGenPage)this;
	}

	///////////////////////
	// medicalEnrollment //
	///////////////////////

	/**	L'entité « medicalEnrollment »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected MedicalEnrollment medicalEnrollment;
	@JsonIgnore
	public Wrap<MedicalEnrollment> medicalEnrollmentWrap = new Wrap<MedicalEnrollment>().p(this).c(MedicalEnrollment.class).var("medicalEnrollment").o(medicalEnrollment);

	/**	<br/>L'entité « medicalEnrollment »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.enrollment.EnrollmentGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:medicalEnrollment">Trouver l'entité medicalEnrollment dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _medicalEnrollment(Wrap<MedicalEnrollment> c);

	public MedicalEnrollment getMedicalEnrollment() {
		return medicalEnrollment;
	}

	public void setMedicalEnrollment(MedicalEnrollment medicalEnrollment) {
		this.medicalEnrollment = medicalEnrollment;
		this.medicalEnrollmentWrap.alreadyInitialized = true;
	}
	protected EnrollmentGenPage medicalEnrollmentInit() {
		if(!medicalEnrollmentWrap.alreadyInitialized) {
			_medicalEnrollment(medicalEnrollmentWrap);
			if(medicalEnrollment == null)
				setMedicalEnrollment(medicalEnrollmentWrap.o);
		}
		if(medicalEnrollment != null)
			medicalEnrollment.initDeepForClass(siteRequest_);
		medicalEnrollmentWrap.alreadyInitialized(true);
		return (EnrollmentGenPage)this;
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedEnrollmentGenPage = false;

	public EnrollmentGenPage initDeepEnrollmentGenPage(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedEnrollmentGenPage) {
			alreadyInitializedEnrollmentGenPage = true;
			initDeepEnrollmentGenPage();
		}
		return (EnrollmentGenPage)this;
	}

	public void initDeepEnrollmentGenPage() {
		initEnrollmentGenPage();
		super.initDeepClusterPage(siteRequest_);
	}

	public void initEnrollmentGenPage() {
		listMedicalEnrollmentInit();
		medicalEnrollmentInit();
	}

	@Override public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepEnrollmentGenPage(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestEnrollmentGenPage(SiteRequestEnUS siteRequest_) {
			super.siteRequestClusterPage(siteRequest_);
		if(listMedicalEnrollment != null)
			listMedicalEnrollment.setSiteRequest_(siteRequest_);
		if(medicalEnrollment != null)
			medicalEnrollment.setSiteRequest_(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestEnrollmentGenPage(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainEnrollmentGenPage(v);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.obtainForClass(v);
			}
		}
		return o;
	}
	public Object obtainEnrollmentGenPage(String var) {
		EnrollmentGenPage oEnrollmentGenPage = (EnrollmentGenPage)this;
		switch(var) {
			case "listMedicalEnrollment":
				return oEnrollmentGenPage.listMedicalEnrollment;
			case "medicalEnrollment":
				return oEnrollmentGenPage.medicalEnrollment;
			default:
				return super.obtainClusterPage(var);
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
				o = attributeEnrollmentGenPage(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeEnrollmentGenPage(String var, Object val) {
		EnrollmentGenPage oEnrollmentGenPage = (EnrollmentGenPage)this;
		switch(var) {
			default:
				return super.attributeClusterPage(var, val);
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
					o = defineEnrollmentGenPage(v, val);
				else if(o instanceof Cluster) {
					Cluster cluster = (Cluster)o;
					o = cluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineEnrollmentGenPage(String var, String val) {
		switch(var) {
			default:
				return super.defineClusterPage(var, val);
		}
	}

	/////////////////
	// htmlScripts //
	/////////////////

	@Override public void htmlScripts() {
		htmlScriptsEnrollmentGenPage();
		super.htmlScripts();
	}

	public void htmlScriptsEnrollmentGenPage() {
	}

	////////////////
	// htmlScript //
	////////////////

	@Override public void htmlScript() {
		htmlScriptEnrollmentGenPage();
		super.htmlScript();
	}

	public void htmlScriptEnrollmentGenPage() {
	}

	//////////////
	// htmlBody //
	//////////////

	@Override public void htmlBody() {
		htmlBodyEnrollmentGenPage();
		super.htmlBody();
	}

	public void htmlBodyEnrollmentGenPage() {
	}

	//////////
	// html //
	//////////

	@Override public void html() {
		htmlEnrollmentGenPage();
		super.html();
	}

	public void htmlEnrollmentGenPage() {
	}

	//////////////
	// htmlMeta //
	//////////////

	@Override public void htmlMeta() {
		htmlMetaEnrollmentGenPage();
		super.htmlMeta();
	}

	public void htmlMetaEnrollmentGenPage() {
	}

	////////////////
	// htmlStyles //
	////////////////

	@Override public void htmlStyles() {
		htmlStylesEnrollmentGenPage();
		super.htmlStyles();
	}

	public void htmlStylesEnrollmentGenPage() {
	}

	///////////////
	// htmlStyle //
	///////////////

	@Override public void htmlStyle() {
		htmlStyleEnrollmentGenPage();
		super.htmlStyle();
	}

	public void htmlStyleEnrollmentGenPage() {
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestEnrollmentGenPage() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof EnrollmentGenPage) {
			EnrollmentGenPage original = (EnrollmentGenPage)o;
			super.apiRequestClusterPage();
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
		if(!(o instanceof EnrollmentGenPage))
			return false;
		EnrollmentGenPage that = (EnrollmentGenPage)o;
		return super.equals(o);
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("EnrollmentGenPage { ");
		sb.append(" }");
		return sb.toString();
	}
}
