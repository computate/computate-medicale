package org.computate.medicale.frFR.patient;

import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.computate.medicale.frFR.cluster.Cluster;
import org.computate.medicale.frFR.requete.api.RequeteApi;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
import java.text.NumberFormat;
import io.vertx.core.logging.LoggerFactory;
import java.util.ArrayList;
import org.apache.commons.collections.CollectionUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.vertx.core.logging.Logger;
import org.computate.medicale.frFR.couverture.Couverture;
import java.math.MathContext;
import org.computate.medicale.frFR.ecrivain.ToutEcrivain;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.computate.medicale.frFR.cluster.ClusterPage;
import java.util.Objects;
import io.vertx.core.json.JsonArray;
import org.computate.medicale.frFR.recherche.ListeRecherche;
import org.computate.medicale.frFR.patient.PatientMedicale;
import org.computate.medicale.frFR.requete.RequeteSiteFrFR;
import org.apache.commons.lang3.math.NumberUtils;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

/**	
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.patient.PatientGenPage&fq=classeEtendGen_indexed_boolean:true">Trouver la classe  dans Solr</a>
 * <br/>
 **/
public abstract class PatientGenPageGen<DEV> extends ClusterPage {
	protected static final Logger LOGGER = LoggerFactory.getLogger(PatientGenPage.class);

	//////////////////////////
	// listePatientMedicale //
	//////////////////////////

	/**	L'entité « listePatientMedicale »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected ListeRecherche<PatientMedicale> listePatientMedicale;
	@JsonIgnore
	public Couverture<ListeRecherche<PatientMedicale>> listePatientMedicaleCouverture = new Couverture<ListeRecherche<PatientMedicale>>().p(this).c(ListeRecherche.class).var("listePatientMedicale").o(listePatientMedicale);

	/**	<br/>L'entité « listePatientMedicale »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.patient.PatientGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:listePatientMedicale">Trouver l'entité listePatientMedicale dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _listePatientMedicale(Couverture<ListeRecherche<PatientMedicale>> c);

	public ListeRecherche<PatientMedicale> getListePatientMedicale() {
		return listePatientMedicale;
	}

	public void setListePatientMedicale(ListeRecherche<PatientMedicale> listePatientMedicale) {
		this.listePatientMedicale = listePatientMedicale;
		this.listePatientMedicaleCouverture.dejaInitialise = true;
	}
	protected PatientGenPage listePatientMedicaleInit() {
		if(!listePatientMedicaleCouverture.dejaInitialise) {
			_listePatientMedicale(listePatientMedicaleCouverture);
			if(listePatientMedicale == null)
				setListePatientMedicale(listePatientMedicaleCouverture.o);
		}
		if(listePatientMedicale != null)
			listePatientMedicale.initLoinPourClasse(requeteSite_);
		listePatientMedicaleCouverture.dejaInitialise(true);
		return (PatientGenPage)this;
	}

	/////////////////////
	// patientMedicale //
	/////////////////////

	/**	L'entité « patientMedicale »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected PatientMedicale patientMedicale;
	@JsonIgnore
	public Couverture<PatientMedicale> patientMedicaleCouverture = new Couverture<PatientMedicale>().p(this).c(PatientMedicale.class).var("patientMedicale").o(patientMedicale);

	/**	<br/>L'entité « patientMedicale »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.patient.PatientGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:patientMedicale">Trouver l'entité patientMedicale dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _patientMedicale(Couverture<PatientMedicale> c);

	public PatientMedicale getPatientMedicale() {
		return patientMedicale;
	}

	public void setPatientMedicale(PatientMedicale patientMedicale) {
		this.patientMedicale = patientMedicale;
		this.patientMedicaleCouverture.dejaInitialise = true;
	}
	protected PatientGenPage patientMedicaleInit() {
		if(!patientMedicaleCouverture.dejaInitialise) {
			_patientMedicale(patientMedicaleCouverture);
			if(patientMedicale == null)
				setPatientMedicale(patientMedicaleCouverture.o);
		}
		if(patientMedicale != null)
			patientMedicale.initLoinPourClasse(requeteSite_);
		patientMedicaleCouverture.dejaInitialise(true);
		return (PatientGenPage)this;
	}

	//////////////
	// initLoin //
	//////////////

	protected boolean dejaInitialisePatientGenPage = false;

	public PatientGenPage initLoinPatientGenPage(RequeteSiteFrFR requeteSite_) {
		setRequeteSite_(requeteSite_);
		if(!dejaInitialisePatientGenPage) {
			dejaInitialisePatientGenPage = true;
			initLoinPatientGenPage();
		}
		return (PatientGenPage)this;
	}

	public void initLoinPatientGenPage() {
		initPatientGenPage();
		super.initLoinClusterPage(requeteSite_);
	}

	public void initPatientGenPage() {
		listePatientMedicaleInit();
		patientMedicaleInit();
	}

	@Override public void initLoinPourClasse(RequeteSiteFrFR requeteSite_) {
		initLoinPatientGenPage(requeteSite_);
	}

	/////////////////
	// requeteSite //
	/////////////////

	public void requeteSitePatientGenPage(RequeteSiteFrFR requeteSite_) {
			super.requeteSiteClusterPage(requeteSite_);
		if(listePatientMedicale != null)
			listePatientMedicale.setRequeteSite_(requeteSite_);
		if(patientMedicale != null)
			patientMedicale.setRequeteSite_(requeteSite_);
	}

	public void requeteSitePourClasse(RequeteSiteFrFR requeteSite_) {
		requeteSitePatientGenPage(requeteSite_);
	}

	/////////////
	// obtenir //
	/////////////

	@Override public Object obtenirPourClasse(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtenirPatientGenPage(v);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.obtenirPourClasse(v);
			}
		}
		return o;
	}
	public Object obtenirPatientGenPage(String var) {
		PatientGenPage oPatientGenPage = (PatientGenPage)this;
		switch(var) {
			case "listePatientMedicale":
				return oPatientGenPage.listePatientMedicale;
			case "patientMedicale":
				return oPatientGenPage.patientMedicale;
			default:
				return super.obtenirClusterPage(var);
		}
	}

	///////////////
	// attribuer //
	///////////////

	@Override public boolean attribuerPourClasse(String var, Object val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = attribuerPatientGenPage(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attribuerPourClasse(v, val);
			}
		}
		return o != null;
	}
	public Object attribuerPatientGenPage(String var, Object val) {
		PatientGenPage oPatientGenPage = (PatientGenPage)this;
		switch(var) {
			default:
				return super.attribuerClusterPage(var, val);
		}
	}

	/////////////
	// definir //
	/////////////

	@Override public boolean definirPourClasse(String var, String val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		if(val != null) {
			for(String v : vars) {
				if(o == null)
					o = definirPatientGenPage(v, val);
				else if(o instanceof Cluster) {
					Cluster cluster = (Cluster)o;
					o = cluster.definirPourClasse(v, val);
				}
			}
		}
		return o != null;
	}
	public Object definirPatientGenPage(String var, String val) {
		switch(var) {
			default:
				return super.definirClusterPage(var, val);
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
	// requeteApi //
	//////////////////

	public void requeteApiPatientGenPage() {
		RequeteApi requeteApi = Optional.ofNullable(requeteSite_).map(RequeteSiteFrFR::getRequeteApi_).orElse(null);
		Object o = Optional.ofNullable(requeteApi).map(RequeteApi::getOriginal).orElse(null);
		if(o != null && o instanceof PatientGenPage) {
			PatientGenPage original = (PatientGenPage)o;
			super.requeteApiClusterPage();
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
