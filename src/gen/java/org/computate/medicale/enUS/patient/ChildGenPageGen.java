package org.computate.medicale.enUS.patient;

import org.computate.medicale.enUS.writer.AllWriter;
import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.apache.commons.lang3.StringUtils;
import java.text.NumberFormat;
import io.vertx.core.logging.LoggerFactory;
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
import org.computate.medicale.enUS.patient.MedicalPatient;
import org.apache.commons.lang3.math.NumberUtils;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.computate.medicale.enUS.cluster.Cluster;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

/**	
 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.patient.ChildGenPage&fq=classeEtendGen_indexed_boolean:true">Trouver la classe  dans Solr</a>
 * <br/>
 **/
public abstract class ChildGenPageGen<DEV> extends ClusterPage {
	protected static final Logger LOGGER = LoggerFactory.getLogger(ChildGenPage.class);

	////////////////////////
	// listMedicalPatient //
	////////////////////////

	/**	L'entité « listMedicalPatient »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected SearchList<MedicalPatient> listMedicalPatient;
	@JsonIgnore
	public Wrap<SearchList<MedicalPatient>> listMedicalPatientWrap = new Wrap<SearchList<MedicalPatient>>().p(this).c(SearchList.class).var("listMedicalPatient").o(listMedicalPatient);

	/**	<br/>L'entité « listMedicalPatient »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.patient.ChildGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:listMedicalPatient">Trouver l'entité listMedicalPatient dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _listMedicalPatient(Wrap<SearchList<MedicalPatient>> c);

	public SearchList<MedicalPatient> getListMedicalPatient() {
		return listMedicalPatient;
	}

	public void setListMedicalPatient(SearchList<MedicalPatient> listMedicalPatient) {
		this.listMedicalPatient = listMedicalPatient;
		this.listMedicalPatientWrap.alreadyInitialized = true;
	}
	protected ChildGenPage listMedicalPatientInit() {
		if(!listMedicalPatientWrap.alreadyInitialized) {
			_listMedicalPatient(listMedicalPatientWrap);
			if(listMedicalPatient == null)
				setListMedicalPatient(listMedicalPatientWrap.o);
		}
		if(listMedicalPatient != null)
			listMedicalPatient.initDeepForClass(siteRequest_);
		listMedicalPatientWrap.alreadyInitialized(true);
		return (ChildGenPage)this;
	}

	////////////////////
	// medicalPatient //
	////////////////////

	/**	L'entité « medicalPatient »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected MedicalPatient medicalPatient;
	@JsonIgnore
	public Wrap<MedicalPatient> medicalPatientWrap = new Wrap<MedicalPatient>().p(this).c(MedicalPatient.class).var("medicalPatient").o(medicalPatient);

	/**	<br/>L'entité « medicalPatient »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.patient.ChildGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:medicalPatient">Trouver l'entité medicalPatient dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _medicalPatient(Wrap<MedicalPatient> c);

	public MedicalPatient getMedicalPatient() {
		return medicalPatient;
	}

	public void setMedicalPatient(MedicalPatient medicalPatient) {
		this.medicalPatient = medicalPatient;
		this.medicalPatientWrap.alreadyInitialized = true;
	}
	protected ChildGenPage medicalPatientInit() {
		if(!medicalPatientWrap.alreadyInitialized) {
			_medicalPatient(medicalPatientWrap);
			if(medicalPatient == null)
				setMedicalPatient(medicalPatientWrap.o);
		}
		if(medicalPatient != null)
			medicalPatient.initDeepForClass(siteRequest_);
		medicalPatientWrap.alreadyInitialized(true);
		return (ChildGenPage)this;
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedChildGenPage = false;

	public ChildGenPage initDeepChildGenPage(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedChildGenPage) {
			alreadyInitializedChildGenPage = true;
			initDeepChildGenPage();
		}
		return (ChildGenPage)this;
	}

	public void initDeepChildGenPage() {
		initChildGenPage();
		super.initDeepClusterPage(siteRequest_);
	}

	public void initChildGenPage() {
		listMedicalPatientInit();
		medicalPatientInit();
	}

	@Override public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepChildGenPage(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestChildGenPage(SiteRequestEnUS siteRequest_) {
			super.siteRequestClusterPage(siteRequest_);
		if(listMedicalPatient != null)
			listMedicalPatient.setSiteRequest_(siteRequest_);
		if(medicalPatient != null)
			medicalPatient.setSiteRequest_(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestChildGenPage(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainChildGenPage(v);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.obtainForClass(v);
			}
		}
		return o;
	}
	public Object obtainChildGenPage(String var) {
		ChildGenPage oChildGenPage = (ChildGenPage)this;
		switch(var) {
			case "listMedicalPatient":
				return oChildGenPage.listMedicalPatient;
			case "medicalPatient":
				return oChildGenPage.medicalPatient;
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
				o = attributeChildGenPage(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeChildGenPage(String var, Object val) {
		ChildGenPage oChildGenPage = (ChildGenPage)this;
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
					o = defineChildGenPage(v, val);
				else if(o instanceof Cluster) {
					Cluster cluster = (Cluster)o;
					o = cluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineChildGenPage(String var, String val) {
		switch(var) {
			default:
				return super.defineClusterPage(var, val);
		}
	}

	/////////////////
	// htmlScripts //
	/////////////////

	@Override public void htmlScripts() {
		htmlScriptsChildGenPage();
		super.htmlScripts();
	}

	public void htmlScriptsChildGenPage() {
	}

	////////////////
	// htmlScript //
	////////////////

	@Override public void htmlScript() {
		htmlScriptChildGenPage();
		super.htmlScript();
	}

	public void htmlScriptChildGenPage() {
	}

	//////////////
	// htmlBody //
	//////////////

	@Override public void htmlBody() {
		htmlBodyChildGenPage();
		super.htmlBody();
	}

	public void htmlBodyChildGenPage() {
	}

	//////////
	// html //
	//////////

	@Override public void html() {
		htmlChildGenPage();
		super.html();
	}

	public void htmlChildGenPage() {
	}

	//////////////
	// htmlMeta //
	//////////////

	@Override public void htmlMeta() {
		htmlMetaChildGenPage();
		super.htmlMeta();
	}

	public void htmlMetaChildGenPage() {
	}

	////////////////
	// htmlStyles //
	////////////////

	@Override public void htmlStyles() {
		htmlStylesChildGenPage();
		super.htmlStyles();
	}

	public void htmlStylesChildGenPage() {
	}

	///////////////
	// htmlStyle //
	///////////////

	@Override public void htmlStyle() {
		htmlStyleChildGenPage();
		super.htmlStyle();
	}

	public void htmlStyleChildGenPage() {
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestChildGenPage() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof ChildGenPage) {
			ChildGenPage original = (ChildGenPage)o;
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
		if(!(o instanceof ChildGenPage))
			return false;
		ChildGenPage that = (ChildGenPage)o;
		return super.equals(o);
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("ChildGenPage { ");
		sb.append(" }");
		return sb.toString();
	}
}
