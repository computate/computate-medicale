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
 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.GenPageInscription&fq=classeEtendGen_indexed_boolean:true">Trouver la classe  dans Solr</a>
 * <br/>
 **/
public abstract class GenPageInscriptionGen<DEV> extends ClusterPage {
	protected static final Logger LOGGER = LoggerFactory.getLogger(GenPageInscription.class);

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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.GenPageInscription&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:listeInscriptionMedicale">Trouver l'entité listeInscriptionMedicale dans Solr</a>
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
	protected GenPageInscription listeInscriptionMedicaleInit() {
		if(!listeInscriptionMedicaleCouverture.dejaInitialise) {
			_listeInscriptionMedicale(listeInscriptionMedicaleCouverture);
			if(listeInscriptionMedicale == null)
				setListeInscriptionMedicale(listeInscriptionMedicaleCouverture.o);
		}
		if(listeInscriptionMedicale != null)
			listeInscriptionMedicale.initLoinPourClasse(requeteSite_);
		listeInscriptionMedicaleCouverture.dejaInitialise(true);
		return (GenPageInscription)this;
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.inscription.GenPageInscription&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptionMedicale">Trouver l'entité inscriptionMedicale dans Solr</a>
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
	protected GenPageInscription inscriptionMedicaleInit() {
		if(!inscriptionMedicaleCouverture.dejaInitialise) {
			_inscriptionMedicale(inscriptionMedicaleCouverture);
			if(inscriptionMedicale == null)
				setInscriptionMedicale(inscriptionMedicaleCouverture.o);
		}
		if(inscriptionMedicale != null)
			inscriptionMedicale.initLoinPourClasse(requeteSite_);
		inscriptionMedicaleCouverture.dejaInitialise(true);
		return (GenPageInscription)this;
	}

	//////////////
	// initLoin //
	//////////////

	protected boolean dejaInitialiseGenPageInscription = false;

	public GenPageInscription initLoinGenPageInscription(RequeteSiteFrFR requeteSite_) {
		setRequeteSite_(requeteSite_);
		if(!dejaInitialiseGenPageInscription) {
			dejaInitialiseGenPageInscription = true;
			initLoinGenPageInscription();
		}
		return (GenPageInscription)this;
	}

	public void initLoinGenPageInscription() {
		initGenPageInscription();
		super.initLoinClusterPage(requeteSite_);
	}

	public void initGenPageInscription() {
		listeInscriptionMedicaleInit();
		inscriptionMedicaleInit();
	}

	@Override public void initLoinPourClasse(RequeteSiteFrFR requeteSite_) {
		initLoinGenPageInscription(requeteSite_);
	}

	/////////////////
	// requeteSite //
	/////////////////

	public void requeteSiteGenPageInscription(RequeteSiteFrFR requeteSite_) {
			super.requeteSiteClusterPage(requeteSite_);
		if(listeInscriptionMedicale != null)
			listeInscriptionMedicale.setRequeteSite_(requeteSite_);
		if(inscriptionMedicale != null)
			inscriptionMedicale.setRequeteSite_(requeteSite_);
	}

	public void requeteSitePourClasse(RequeteSiteFrFR requeteSite_) {
		requeteSiteGenPageInscription(requeteSite_);
	}

	/////////////
	// obtenir //
	/////////////

	@Override public Object obtenirPourClasse(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtenirGenPageInscription(v);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.obtenirPourClasse(v);
			}
		}
		return o;
	}
	public Object obtenirGenPageInscription(String var) {
		GenPageInscription oGenPageInscription = (GenPageInscription)this;
		switch(var) {
			case "listeInscriptionMedicale":
				return oGenPageInscription.listeInscriptionMedicale;
			case "inscriptionMedicale":
				return oGenPageInscription.inscriptionMedicale;
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
				o = attribuerGenPageInscription(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attribuerPourClasse(v, val);
			}
		}
		return o != null;
	}
	public Object attribuerGenPageInscription(String var, Object val) {
		GenPageInscription oGenPageInscription = (GenPageInscription)this;
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
					o = definirGenPageInscription(v, val);
				else if(o instanceof Cluster) {
					Cluster cluster = (Cluster)o;
					o = cluster.definirPourClasse(v, val);
				}
			}
		}
		return o != null;
	}
	public Object definirGenPageInscription(String var, String val) {
		switch(var) {
			default:
				return super.definirClusterPage(var, val);
		}
	}

	/////////////////
	// htmlScripts //
	/////////////////

	@Override public void htmlScripts() {
		htmlScriptsGenPageInscription();
		super.htmlScripts();
	}

	public void htmlScriptsGenPageInscription() {
	}

	////////////////
	// htmlScript //
	////////////////

	@Override public void htmlScript() {
		htmlScriptGenPageInscription();
		super.htmlScript();
	}

	public void htmlScriptGenPageInscription() {
	}

	//////////////
	// htmlBody //
	//////////////

	@Override public void htmlBody() {
		htmlBodyGenPageInscription();
		super.htmlBody();
	}

	public void htmlBodyGenPageInscription() {
	}

	//////////
	// html //
	//////////

	@Override public void html() {
		htmlGenPageInscription();
		super.html();
	}

	public void htmlGenPageInscription() {
	}

	//////////////
	// htmlMeta //
	//////////////

	@Override public void htmlMeta() {
		htmlMetaGenPageInscription();
		super.htmlMeta();
	}

	public void htmlMetaGenPageInscription() {
	}

	////////////////
	// htmlStyles //
	////////////////

	@Override public void htmlStyles() {
		htmlStylesGenPageInscription();
		super.htmlStyles();
	}

	public void htmlStylesGenPageInscription() {
	}

	///////////////
	// htmlStyle //
	///////////////

	@Override public void htmlStyle() {
		htmlStyleGenPageInscription();
		super.htmlStyle();
	}

	public void htmlStyleGenPageInscription() {
	}

	//////////////////
	// requeteApi //
	//////////////////

	public void requeteApiGenPageInscription() {
		RequeteApi requeteApi = Optional.ofNullable(requeteSite_).map(RequeteSiteFrFR::getRequeteApi_).orElse(null);
		Object o = Optional.ofNullable(requeteApi).map(RequeteApi::getOriginal).orElse(null);
		if(o != null && o instanceof GenPageInscription) {
			GenPageInscription original = (GenPageInscription)o;
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
		if(!(o instanceof GenPageInscription))
			return false;
		GenPageInscription that = (GenPageInscription)o;
		return super.equals(o);
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("GenPageInscription { ");
		sb.append(" }");
		return sb.toString();
	}
}
