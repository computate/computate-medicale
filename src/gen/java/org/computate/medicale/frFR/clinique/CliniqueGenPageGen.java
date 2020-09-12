package org.computate.medicale.frFR.clinique;

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
import org.computate.medicale.frFR.clinique.CliniqueMedicale;
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
import org.computate.medicale.frFR.requete.RequeteSiteFrFR;
import org.apache.commons.lang3.math.NumberUtils;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

/**	
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.clinique.CliniqueGenPage&fq=classeEtendGen_indexed_boolean:true">Trouver la classe  dans Solr</a>
 * <br/>
 **/
public abstract class CliniqueGenPageGen<DEV> extends ClusterPage {
	protected static final Logger LOGGER = LoggerFactory.getLogger(CliniqueGenPage.class);

	///////////////////////////
	// listeCliniqueMedicale //
	///////////////////////////

	/**	L'entité « listeCliniqueMedicale »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected ListeRecherche<CliniqueMedicale> listeCliniqueMedicale;
	@JsonIgnore
	public Couverture<ListeRecherche<CliniqueMedicale>> listeCliniqueMedicaleCouverture = new Couverture<ListeRecherche<CliniqueMedicale>>().p(this).c(ListeRecherche.class).var("listeCliniqueMedicale").o(listeCliniqueMedicale);

	/**	<br/>L'entité « listeCliniqueMedicale »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.clinique.CliniqueGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:listeCliniqueMedicale">Trouver l'entité listeCliniqueMedicale dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _listeCliniqueMedicale(Couverture<ListeRecherche<CliniqueMedicale>> c);

	public ListeRecherche<CliniqueMedicale> getListeCliniqueMedicale() {
		return listeCliniqueMedicale;
	}

	public void setListeCliniqueMedicale(ListeRecherche<CliniqueMedicale> listeCliniqueMedicale) {
		this.listeCliniqueMedicale = listeCliniqueMedicale;
		this.listeCliniqueMedicaleCouverture.dejaInitialise = true;
	}
	protected CliniqueGenPage listeCliniqueMedicaleInit() {
		if(!listeCliniqueMedicaleCouverture.dejaInitialise) {
			_listeCliniqueMedicale(listeCliniqueMedicaleCouverture);
			if(listeCliniqueMedicale == null)
				setListeCliniqueMedicale(listeCliniqueMedicaleCouverture.o);
		}
		if(listeCliniqueMedicale != null)
			listeCliniqueMedicale.initLoinPourClasse(requeteSite_);
		listeCliniqueMedicaleCouverture.dejaInitialise(true);
		return (CliniqueGenPage)this;
	}

	//////////////////////
	// cliniqueMedicale //
	//////////////////////

	/**	L'entité « cliniqueMedicale »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected CliniqueMedicale cliniqueMedicale;
	@JsonIgnore
	public Couverture<CliniqueMedicale> cliniqueMedicaleCouverture = new Couverture<CliniqueMedicale>().p(this).c(CliniqueMedicale.class).var("cliniqueMedicale").o(cliniqueMedicale);

	/**	<br/>L'entité « cliniqueMedicale »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.clinique.CliniqueGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:cliniqueMedicale">Trouver l'entité cliniqueMedicale dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _cliniqueMedicale(Couverture<CliniqueMedicale> c);

	public CliniqueMedicale getCliniqueMedicale() {
		return cliniqueMedicale;
	}

	public void setCliniqueMedicale(CliniqueMedicale cliniqueMedicale) {
		this.cliniqueMedicale = cliniqueMedicale;
		this.cliniqueMedicaleCouverture.dejaInitialise = true;
	}
	protected CliniqueGenPage cliniqueMedicaleInit() {
		if(!cliniqueMedicaleCouverture.dejaInitialise) {
			_cliniqueMedicale(cliniqueMedicaleCouverture);
			if(cliniqueMedicale == null)
				setCliniqueMedicale(cliniqueMedicaleCouverture.o);
		}
		if(cliniqueMedicale != null)
			cliniqueMedicale.initLoinPourClasse(requeteSite_);
		cliniqueMedicaleCouverture.dejaInitialise(true);
		return (CliniqueGenPage)this;
	}

	//////////////
	// initLoin //
	//////////////

	protected boolean dejaInitialiseCliniqueGenPage = false;

	public CliniqueGenPage initLoinCliniqueGenPage(RequeteSiteFrFR requeteSite_) {
		setRequeteSite_(requeteSite_);
		if(!dejaInitialiseCliniqueGenPage) {
			dejaInitialiseCliniqueGenPage = true;
			initLoinCliniqueGenPage();
		}
		return (CliniqueGenPage)this;
	}

	public void initLoinCliniqueGenPage() {
		initCliniqueGenPage();
		super.initLoinClusterPage(requeteSite_);
	}

	public void initCliniqueGenPage() {
		listeCliniqueMedicaleInit();
		cliniqueMedicaleInit();
	}

	@Override public void initLoinPourClasse(RequeteSiteFrFR requeteSite_) {
		initLoinCliniqueGenPage(requeteSite_);
	}

	/////////////////
	// requeteSite //
	/////////////////

	public void requeteSiteCliniqueGenPage(RequeteSiteFrFR requeteSite_) {
			super.requeteSiteClusterPage(requeteSite_);
		if(listeCliniqueMedicale != null)
			listeCliniqueMedicale.setRequeteSite_(requeteSite_);
		if(cliniqueMedicale != null)
			cliniqueMedicale.setRequeteSite_(requeteSite_);
	}

	public void requeteSitePourClasse(RequeteSiteFrFR requeteSite_) {
		requeteSiteCliniqueGenPage(requeteSite_);
	}

	/////////////
	// obtenir //
	/////////////

	@Override public Object obtenirPourClasse(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtenirCliniqueGenPage(v);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.obtenirPourClasse(v);
			}
		}
		return o;
	}
	public Object obtenirCliniqueGenPage(String var) {
		CliniqueGenPage oCliniqueGenPage = (CliniqueGenPage)this;
		switch(var) {
			case "listeCliniqueMedicale":
				return oCliniqueGenPage.listeCliniqueMedicale;
			case "cliniqueMedicale":
				return oCliniqueGenPage.cliniqueMedicale;
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
				o = attribuerCliniqueGenPage(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attribuerPourClasse(v, val);
			}
		}
		return o != null;
	}
	public Object attribuerCliniqueGenPage(String var, Object val) {
		CliniqueGenPage oCliniqueGenPage = (CliniqueGenPage)this;
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
					o = definirCliniqueGenPage(v, val);
				else if(o instanceof Cluster) {
					Cluster cluster = (Cluster)o;
					o = cluster.definirPourClasse(v, val);
				}
			}
		}
		return o != null;
	}
	public Object definirCliniqueGenPage(String var, String val) {
		switch(var) {
			default:
				return super.definirClusterPage(var, val);
		}
	}

	/////////////////
	// htmlScripts //
	/////////////////

	@Override public void htmlScripts() {
		htmlScriptsCliniqueGenPage();
		super.htmlScripts();
	}

	public void htmlScriptsCliniqueGenPage() {
	}

	////////////////
	// htmlScript //
	////////////////

	@Override public void htmlScript() {
		htmlScriptCliniqueGenPage();
		super.htmlScript();
	}

	public void htmlScriptCliniqueGenPage() {
	}

	//////////////
	// htmlBody //
	//////////////

	@Override public void htmlBody() {
		htmlBodyCliniqueGenPage();
		super.htmlBody();
	}

	public void htmlBodyCliniqueGenPage() {
	}

	//////////
	// html //
	//////////

	@Override public void html() {
		htmlCliniqueGenPage();
		super.html();
	}

	public void htmlCliniqueGenPage() {
	}

	//////////////
	// htmlMeta //
	//////////////

	@Override public void htmlMeta() {
		htmlMetaCliniqueGenPage();
		super.htmlMeta();
	}

	public void htmlMetaCliniqueGenPage() {
	}

	////////////////
	// htmlStyles //
	////////////////

	@Override public void htmlStyles() {
		htmlStylesCliniqueGenPage();
		super.htmlStyles();
	}

	public void htmlStylesCliniqueGenPage() {
	}

	///////////////
	// htmlStyle //
	///////////////

	@Override public void htmlStyle() {
		htmlStyleCliniqueGenPage();
		super.htmlStyle();
	}

	public void htmlStyleCliniqueGenPage() {
	}

	//////////////////
	// requeteApi //
	//////////////////

	public void requeteApiCliniqueGenPage() {
		RequeteApi requeteApi = Optional.ofNullable(requeteSite_).map(RequeteSiteFrFR::getRequeteApi_).orElse(null);
		Object o = Optional.ofNullable(requeteApi).map(RequeteApi::getOriginal).orElse(null);
		if(o != null && o instanceof CliniqueGenPage) {
			CliniqueGenPage original = (CliniqueGenPage)o;
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
		if(!(o instanceof CliniqueGenPage))
			return false;
		CliniqueGenPage that = (CliniqueGenPage)o;
		return super.equals(o);
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("CliniqueGenPage { ");
		sb.append(" }");
		return sb.toString();
	}
}
