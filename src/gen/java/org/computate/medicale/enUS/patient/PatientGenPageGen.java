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
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.patient.PatientGenPage&fq=classeEtendGen_indexed_boolean:true">Trouver la classe  dans Solr</a>
 * <br/>
 **/
public abstract class PatientGenPageGen<DEV> extends ClusterPage {
	protected static final Logger LOGGER = LoggerFactory.getLogger(PatientGenPage.class);

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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.patient.PatientGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:listMedicalPatient">Trouver l'entité listMedicalPatient dans Solr</a>
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
	protected PatientGenPage listMedicalPatientInit() {
		if(!listMedicalPatientWrap.alreadyInitialized) {
			_listMedicalPatient(listMedicalPatientWrap);
			if(listMedicalPatient == null)
				setListMedicalPatient(listMedicalPatientWrap.o);
		}
		if(listMedicalPatient != null)
			listMedicalPatient.initDeepForClass(siteRequest_);
		listMedicalPatientWrap.alreadyInitialized(true);
		return (PatientGenPage)this;
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.patient.PatientGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:medicalPatient">Trouver l'entité medicalPatient dans Solr</a>
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
	protected PatientGenPage medicalPatientInit() {
		if(!medicalPatientWrap.alreadyInitialized) {
			_medicalPatient(medicalPatientWrap);
			if(medicalPatient == null)
				setMedicalPatient(medicalPatientWrap.o);
		}
		if(medicalPatient != null)
			medicalPatient.initDeepForClass(siteRequest_);
		medicalPatientWrap.alreadyInitialized(true);
		return (PatientGenPage)this;
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedPatientGenPage = false;

	public PatientGenPage initDeepPatientGenPage(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedPatientGenPage) {
			alreadyInitializedPatientGenPage = true;
			initDeepPatientGenPage();
		}
		return (PatientGenPage)this;
	}

	public void initDeepPatientGenPage() {
		initPatientGenPage();
		super.initDeepClusterPage(siteRequest_);
	}

	public void initPatientGenPage() {
		listMedicalPatientInit();
		medicalPatientInit();
	}

	@Override public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepPatientGenPage(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestPatientGenPage(SiteRequestEnUS siteRequest_) {
			super.siteRequestClusterPage(siteRequest_);
		if(listMedicalPatient != null)
			listMedicalPatient.setSiteRequest_(siteRequest_);
		if(medicalPatient != null)
			medicalPatient.setSiteRequest_(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestPatientGenPage(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainPatientGenPage(v);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.obtainForClass(v);
			}
		}
		return o;
	}
	public Object obtainPatientGenPage(String var) {
		PatientGenPage oPatientGenPage = (PatientGenPage)this;
		switch(var) {
			case "listMedicalPatient":
				return oPatientGenPage.listMedicalPatient;
			case "medicalPatient":
				return oPatientGenPage.medicalPatient;
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
				o = attributePatientGenPage(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributePatientGenPage(String var, Object val) {
		PatientGenPage oPatientGenPage = (PatientGenPage)this;
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
					o = definePatientGenPage(v, val);
				else if(o instanceof Cluster) {
					Cluster cluster = (Cluster)o;
					o = cluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object definePatientGenPage(String var, String val) {
		switch(var) {
			default:
				return super.defineClusterPage(var, val);
		}
	}

	/////////////////
	// htmlScripts //
	/////////////////

	@Override public void htmlScripts() {
		htmlScriptsPatientGenPage();
		super.htmlScripts();
	}

	public void htmlScriptsPatientGenPage() {
	}

	////////////////
	// htmlScript //
	////////////////

	@Override public void htmlScript() {
		htmlScriptPatientGenPage();
		super.htmlScript();
	}

	public void htmlScriptPatientGenPage() {
	}

	//////////////
	// htmlBody //
	//////////////

	@Override public void htmlBody() {
		htmlBodyPatientGenPage();
		super.htmlBody();
	}

	public void htmlBodyPatientGenPage() {
	}

	//////////
	// html //
	//////////

	@Override public void html() {
		htmlPatientGenPage();
		super.html();
	}

	public void htmlPatientGenPage() {
	}

	//////////////
	// htmlMeta //
	//////////////

	@Override public void htmlMeta() {
		htmlMetaPatientGenPage();
		super.htmlMeta();
	}

	public void htmlMetaPatientGenPage() {
	}

	////////////////
	// htmlStyles //
	////////////////

	@Override public void htmlStyles() {
		htmlStylesPatientGenPage();
		super.htmlStyles();
	}

	public void htmlStylesPatientGenPage() {
	}

	///////////////
	// htmlStyle //
	///////////////

	@Override public void htmlStyle() {
		htmlStylePatientGenPage();
		super.htmlStyle();
	}

	public void htmlStylePatientGenPage() {
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestPatientGenPage() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof PatientGenPage) {
			PatientGenPage original = (PatientGenPage)o;
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
		if(!(o instanceof PatientGenPage))
			return false;
		PatientGenPage that = (PatientGenPage)o;
		return super.equals(o);
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("PatientGenPage { ");
		sb.append(" }");
		return sb.toString();
	}
}
