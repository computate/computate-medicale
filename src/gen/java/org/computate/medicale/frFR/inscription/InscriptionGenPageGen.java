package org.computate.medicale.frFR.inscription;

import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.computate.medicale.frFR.cluster.Cluster;
import org.computate.medicale.frFR.requete.api.RequeteApi;
import java.util.HashMap;
import org.computate.medicale.frFR.inscription.InscriptionMedicale;
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
import org.computate.medicale.frFR.requete.RequeteSiteFrFR;
import org.apache.commons.lang3.math.NumberUtils;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

/**	
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionGenPage&fq=classeEtendGen_indexed_boolean:true">Trouver la classe  dans Solr</a>
 * <br/>
 **/
public abstract class InscriptionGenPageGen<DEV> extends ClusterPage {
	protected static final Logger LOGGER = LoggerFactory.getLogger(InscriptionGenPage.class);

	//////////////////////////////
	// listeInscriptionMedicale //
	//////////////////////////////

	/**	L'entité « listeInscriptionMedicale »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected ListeRecherche<InscriptionMedicale> listeInscriptionMedicale;
	@JsonIgnore
	public Couverture<ListeRecherche<InscriptionMedicale>> listeInscriptionMedicaleCouverture = new Couverture<ListeRecherche<InscriptionMedicale>>().p(this).c(ListeRecherche.class).var("listeInscriptionMedicale").o(listeInscriptionMedicale);

	/**	<br/>L'entité « listeInscriptionMedicale »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:listeInscriptionMedicale">Trouver l'entité listeInscriptionMedicale dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _listeInscriptionMedicale(Couverture<ListeRecherche<InscriptionMedicale>> c);

	public ListeRecherche<InscriptionMedicale> getListeInscriptionMedicale() {
		return listeInscriptionMedicale;
	}

	public void setListeInscriptionMedicale(ListeRecherche<InscriptionMedicale> listeInscriptionMedicale) {
		this.listeInscriptionMedicale = listeInscriptionMedicale;
		this.listeInscriptionMedicaleCouverture.dejaInitialise = true;
	}
	protected InscriptionGenPage listeInscriptionMedicaleInit() {
		if(!listeInscriptionMedicaleCouverture.dejaInitialise) {
			_listeInscriptionMedicale(listeInscriptionMedicaleCouverture);
			if(listeInscriptionMedicale == null)
				setListeInscriptionMedicale(listeInscriptionMedicaleCouverture.o);
		}
		if(listeInscriptionMedicale != null)
			listeInscriptionMedicale.initLoinPourClasse(requeteSite_);
		listeInscriptionMedicaleCouverture.dejaInitialise(true);
		return (InscriptionGenPage)this;
	}

	/////////////////////////
	// inscriptionMedicale //
	/////////////////////////

	/**	L'entité « inscriptionMedicale »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected InscriptionMedicale inscriptionMedicale;
	@JsonIgnore
	public Couverture<InscriptionMedicale> inscriptionMedicaleCouverture = new Couverture<InscriptionMedicale>().p(this).c(InscriptionMedicale.class).var("inscriptionMedicale").o(inscriptionMedicale);

	/**	<br/>L'entité « inscriptionMedicale »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.InscriptionGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptionMedicale">Trouver l'entité inscriptionMedicale dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _inscriptionMedicale(Couverture<InscriptionMedicale> c);

	public InscriptionMedicale getInscriptionMedicale() {
		return inscriptionMedicale;
	}

	public void setInscriptionMedicale(InscriptionMedicale inscriptionMedicale) {
		this.inscriptionMedicale = inscriptionMedicale;
		this.inscriptionMedicaleCouverture.dejaInitialise = true;
	}
	protected InscriptionGenPage inscriptionMedicaleInit() {
		if(!inscriptionMedicaleCouverture.dejaInitialise) {
			_inscriptionMedicale(inscriptionMedicaleCouverture);
			if(inscriptionMedicale == null)
				setInscriptionMedicale(inscriptionMedicaleCouverture.o);
		}
		if(inscriptionMedicale != null)
			inscriptionMedicale.initLoinPourClasse(requeteSite_);
		inscriptionMedicaleCouverture.dejaInitialise(true);
		return (InscriptionGenPage)this;
	}

	//////////////
	// initLoin //
	//////////////

	protected boolean dejaInitialiseInscriptionGenPage = false;

	public InscriptionGenPage initLoinInscriptionGenPage(RequeteSiteFrFR requeteSite_) {
		setRequeteSite_(requeteSite_);
		if(!dejaInitialiseInscriptionGenPage) {
			dejaInitialiseInscriptionGenPage = true;
			initLoinInscriptionGenPage();
		}
		return (InscriptionGenPage)this;
	}

	public void initLoinInscriptionGenPage() {
		initInscriptionGenPage();
		super.initLoinClusterPage(requeteSite_);
	}

	public void initInscriptionGenPage() {
		listeInscriptionMedicaleInit();
		inscriptionMedicaleInit();
	}

	@Override public void initLoinPourClasse(RequeteSiteFrFR requeteSite_) {
		initLoinInscriptionGenPage(requeteSite_);
	}

	/////////////////
	// requeteSite //
	/////////////////

	public void requeteSiteInscriptionGenPage(RequeteSiteFrFR requeteSite_) {
			super.requeteSiteClusterPage(requeteSite_);
		if(listeInscriptionMedicale != null)
			listeInscriptionMedicale.setRequeteSite_(requeteSite_);
		if(inscriptionMedicale != null)
			inscriptionMedicale.setRequeteSite_(requeteSite_);
	}

	public void requeteSitePourClasse(RequeteSiteFrFR requeteSite_) {
		requeteSiteInscriptionGenPage(requeteSite_);
	}

	/////////////
	// obtenir //
	/////////////

	@Override public Object obtenirPourClasse(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtenirInscriptionGenPage(v);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.obtenirPourClasse(v);
			}
		}
		return o;
	}
	public Object obtenirInscriptionGenPage(String var) {
		InscriptionGenPage oInscriptionGenPage = (InscriptionGenPage)this;
		switch(var) {
			case "listeInscriptionMedicale":
				return oInscriptionGenPage.listeInscriptionMedicale;
			case "inscriptionMedicale":
				return oInscriptionGenPage.inscriptionMedicale;
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
				o = attribuerInscriptionGenPage(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attribuerPourClasse(v, val);
			}
		}
		return o != null;
	}
	public Object attribuerInscriptionGenPage(String var, Object val) {
		InscriptionGenPage oInscriptionGenPage = (InscriptionGenPage)this;
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
					o = definirInscriptionGenPage(v, val);
				else if(o instanceof Cluster) {
					Cluster cluster = (Cluster)o;
					o = cluster.definirPourClasse(v, val);
				}
			}
		}
		return o != null;
	}
	public Object definirInscriptionGenPage(String var, String val) {
		switch(var) {
			default:
				return super.definirClusterPage(var, val);
		}
	}

	/////////////////
	// htmlScripts //
	/////////////////

	@Override public void htmlScripts() {
		htmlScriptsInscriptionGenPage();
		super.htmlScripts();
	}

	public void htmlScriptsInscriptionGenPage() {
	}

	////////////////
	// htmlScript //
	////////////////

	@Override public void htmlScript() {
		htmlScriptInscriptionGenPage();
		super.htmlScript();
	}

	public void htmlScriptInscriptionGenPage() {
	}

	//////////////
	// htmlBody //
	//////////////

	@Override public void htmlBody() {
		htmlBodyInscriptionGenPage();
		super.htmlBody();
	}

	public void htmlBodyInscriptionGenPage() {
	}

	//////////
	// html //
	//////////

	@Override public void html() {
		htmlInscriptionGenPage();
		super.html();
	}

	public void htmlInscriptionGenPage() {
	}

	//////////////
	// htmlMeta //
	//////////////

	@Override public void htmlMeta() {
		htmlMetaInscriptionGenPage();
		super.htmlMeta();
	}

	public void htmlMetaInscriptionGenPage() {
	}

	////////////////
	// htmlStyles //
	////////////////

	@Override public void htmlStyles() {
		htmlStylesInscriptionGenPage();
		super.htmlStyles();
	}

	public void htmlStylesInscriptionGenPage() {
	}

	///////////////
	// htmlStyle //
	///////////////

	@Override public void htmlStyle() {
		htmlStyleInscriptionGenPage();
		super.htmlStyle();
	}

	public void htmlStyleInscriptionGenPage() {
	}

	//////////////////
	// requeteApi //
	//////////////////

	public void requeteApiInscriptionGenPage() {
		RequeteApi requeteApi = Optional.ofNullable(requeteSite_).map(RequeteSiteFrFR::getRequeteApi_).orElse(null);
		Object o = Optional.ofNullable(requeteApi).map(RequeteApi::getOriginal).orElse(null);
		if(o != null && o instanceof InscriptionGenPage) {
			InscriptionGenPage original = (InscriptionGenPage)o;
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
		if(!(o instanceof InscriptionGenPage))
			return false;
		InscriptionGenPage that = (InscriptionGenPage)o;
		return super.equals(o);
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("InscriptionGenPage { ");
		sb.append(" }");
		return sb.toString();
	}
}
