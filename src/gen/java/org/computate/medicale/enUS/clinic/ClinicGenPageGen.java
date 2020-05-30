package org.computate.medicale.enUS.clinic;

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
import org.computate.medicale.enUS.clinic.MedicalClinic;
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
 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.clinic.ClinicGenPage&fq=classeEtendGen_indexed_boolean:true">Trouver la classe  dans Solr</a>
 * <br/>
 **/
public abstract class ClinicGenPageGen<DEV> extends ClusterPage {
	protected static final Logger LOGGER = LoggerFactory.getLogger(ClinicGenPage.class);

	///////////////////////
	// listMedicalClinic //
	///////////////////////

	/**	L'entité « listMedicalClinic »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected SearchList<MedicalClinic> listMedicalClinic;
	@JsonIgnore
	public Wrap<SearchList<MedicalClinic>> listMedicalClinicWrap = new Wrap<SearchList<MedicalClinic>>().p(this).c(SearchList.class).var("listMedicalClinic").o(listMedicalClinic);

	/**	<br/>L'entité « listMedicalClinic »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.clinic.ClinicGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:listMedicalClinic">Trouver l'entité listMedicalClinic dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _listMedicalClinic(Wrap<SearchList<MedicalClinic>> c);

	public SearchList<MedicalClinic> getListMedicalClinic() {
		return listMedicalClinic;
	}

	public void setListMedicalClinic(SearchList<MedicalClinic> listMedicalClinic) {
		this.listMedicalClinic = listMedicalClinic;
		this.listMedicalClinicWrap.alreadyInitialized = true;
	}
	protected ClinicGenPage listMedicalClinicInit() {
		if(!listMedicalClinicWrap.alreadyInitialized) {
			_listMedicalClinic(listMedicalClinicWrap);
			if(listMedicalClinic == null)
				setListMedicalClinic(listMedicalClinicWrap.o);
		}
		if(listMedicalClinic != null)
			listMedicalClinic.initDeepForClass(siteRequest_);
		listMedicalClinicWrap.alreadyInitialized(true);
		return (ClinicGenPage)this;
	}

	///////////////////
	// medicalClinic //
	///////////////////

	/**	L'entité « medicalClinic »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected MedicalClinic medicalClinic;
	@JsonIgnore
	public Wrap<MedicalClinic> medicalClinicWrap = new Wrap<MedicalClinic>().p(this).c(MedicalClinic.class).var("medicalClinic").o(medicalClinic);

	/**	<br/>L'entité « medicalClinic »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.medicale.enUS.clinic.ClinicGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:medicalClinic">Trouver l'entité medicalClinic dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _medicalClinic(Wrap<MedicalClinic> c);

	public MedicalClinic getMedicalClinic() {
		return medicalClinic;
	}

	public void setMedicalClinic(MedicalClinic medicalClinic) {
		this.medicalClinic = medicalClinic;
		this.medicalClinicWrap.alreadyInitialized = true;
	}
	protected ClinicGenPage medicalClinicInit() {
		if(!medicalClinicWrap.alreadyInitialized) {
			_medicalClinic(medicalClinicWrap);
			if(medicalClinic == null)
				setMedicalClinic(medicalClinicWrap.o);
		}
		if(medicalClinic != null)
			medicalClinic.initDeepForClass(siteRequest_);
		medicalClinicWrap.alreadyInitialized(true);
		return (ClinicGenPage)this;
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedClinicGenPage = false;

	public ClinicGenPage initDeepClinicGenPage(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedClinicGenPage) {
			alreadyInitializedClinicGenPage = true;
			initDeepClinicGenPage();
		}
		return (ClinicGenPage)this;
	}

	public void initDeepClinicGenPage() {
		initClinicGenPage();
		super.initDeepClusterPage(siteRequest_);
	}

	public void initClinicGenPage() {
		listMedicalClinicInit();
		medicalClinicInit();
	}

	@Override public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepClinicGenPage(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestClinicGenPage(SiteRequestEnUS siteRequest_) {
			super.siteRequestClusterPage(siteRequest_);
		if(medicalClinic != null)
			medicalClinic.setSiteRequest_(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestClinicGenPage(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainClinicGenPage(v);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.obtainForClass(v);
			}
		}
		return o;
	}
	public Object obtainClinicGenPage(String var) {
		ClinicGenPage oClinicGenPage = (ClinicGenPage)this;
		switch(var) {
			case "listMedicalClinic":
				return oClinicGenPage.listMedicalClinic;
			case "medicalClinic":
				return oClinicGenPage.medicalClinic;
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
				o = attributeClinicGenPage(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeClinicGenPage(String var, Object val) {
		ClinicGenPage oClinicGenPage = (ClinicGenPage)this;
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
					o = defineClinicGenPage(v, val);
				else if(o instanceof Cluster) {
					Cluster cluster = (Cluster)o;
					o = cluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineClinicGenPage(String var, String val) {
		switch(var) {
			default:
				return super.defineClusterPage(var, val);
		}
	}

	/////////////////
	// htmlScripts //
	/////////////////

	public void htmlScripts() {
		htmlScriptsClinicGenPage();
	}

	public void htmlScriptsClinicGenPage() {
	}

	////////////////
	// htmlScript //
	////////////////

	public void htmlScript() {
		htmlScriptClinicGenPage();
	}

	public void htmlScriptClinicGenPage() {
	}

	//////////////
	// htmlBody //
	//////////////

	public void htmlBody() {
		htmlBodyClinicGenPage();
	}

	public void htmlBodyClinicGenPage() {
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestClinicGenPage() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof ClinicGenPage) {
			ClinicGenPage original = (ClinicGenPage)o;
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
		if(!(o instanceof ClinicGenPage))
			return false;
		ClinicGenPage that = (ClinicGenPage)o;
		return super.equals(o);
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("ClinicGenPage { ");
		sb.append(" }");
		return sb.toString();
	}
}
