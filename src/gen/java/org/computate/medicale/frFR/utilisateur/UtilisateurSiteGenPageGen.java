package org.computate.medicale.frFR.utilisateur;

import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.computate.medicale.frFR.cluster.Cluster;
import org.computate.medicale.frFR.requete.api.RequeteApi;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
import java.text.NumberFormat;
import io.vertx.core.logging.LoggerFactory;
import java.util.ArrayList;
import org.computate.medicale.frFR.utilisateur.UtilisateurSite;
import org.apache.commons.collections.CollectionUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.vertx.core.logging.Logger;
import java.math.RoundingMode;
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
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.utilisateur.UtilisateurSiteGenPage&fq=classeEtendGen_indexed_boolean:true">Trouver la classe  dans Solr. </a>
 * <br/>
 **/
public abstract class UtilisateurSiteGenPageGen<DEV> extends ClusterPage {
	protected static final Logger LOGGER = LoggerFactory.getLogger(UtilisateurSiteGenPage.class);

	//////////////////////////
	// listeUtilisateurSite //
	//////////////////////////

	/**	 L'entité listeUtilisateurSite
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected ListeRecherche<UtilisateurSite> listeUtilisateurSite;
	@JsonIgnore
	public Couverture<ListeRecherche<UtilisateurSite>> listeUtilisateurSiteCouverture = new Couverture<ListeRecherche<UtilisateurSite>>().p(this).c(ListeRecherche.class).var("listeUtilisateurSite").o(listeUtilisateurSite);

	/**	<br/> L'entité listeUtilisateurSite
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.utilisateur.UtilisateurSiteGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:listeUtilisateurSite">Trouver l'entité listeUtilisateurSite dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _listeUtilisateurSite(Couverture<ListeRecherche<UtilisateurSite>> c);

	public ListeRecherche<UtilisateurSite> getListeUtilisateurSite() {
		return listeUtilisateurSite;
	}

	public void setListeUtilisateurSite(ListeRecherche<UtilisateurSite> listeUtilisateurSite) {
		this.listeUtilisateurSite = listeUtilisateurSite;
		this.listeUtilisateurSiteCouverture.dejaInitialise = true;
	}
	protected UtilisateurSiteGenPage listeUtilisateurSiteInit() {
		if(!listeUtilisateurSiteCouverture.dejaInitialise) {
			_listeUtilisateurSite(listeUtilisateurSiteCouverture);
			if(listeUtilisateurSite == null)
				setListeUtilisateurSite(listeUtilisateurSiteCouverture.o);
		}
		if(listeUtilisateurSite != null)
			listeUtilisateurSite.initLoinPourClasse(requeteSite_);
		listeUtilisateurSiteCouverture.dejaInitialise(true);
		return (UtilisateurSiteGenPage)this;
	}

	//////////////////////
	// utilisateurSite_ //
	//////////////////////

	/**	 L'entité utilisateurSite_
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected UtilisateurSite utilisateurSite_;
	@JsonIgnore
	public Couverture<UtilisateurSite> utilisateurSite_Couverture = new Couverture<UtilisateurSite>().p(this).c(UtilisateurSite.class).var("utilisateurSite_").o(utilisateurSite_);

	/**	<br/> L'entité utilisateurSite_
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.utilisateur.UtilisateurSiteGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:utilisateurSite_">Trouver l'entité utilisateurSite_ dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _utilisateurSite_(Couverture<UtilisateurSite> c);

	public UtilisateurSite getUtilisateurSite_() {
		return utilisateurSite_;
	}

	public void setUtilisateurSite_(UtilisateurSite utilisateurSite_) {
		this.utilisateurSite_ = utilisateurSite_;
		this.utilisateurSite_Couverture.dejaInitialise = true;
	}
	protected UtilisateurSiteGenPage utilisateurSite_Init() {
		if(!utilisateurSite_Couverture.dejaInitialise) {
			_utilisateurSite_(utilisateurSite_Couverture);
			if(utilisateurSite_ == null)
				setUtilisateurSite_(utilisateurSite_Couverture.o);
		}
		utilisateurSite_Couverture.dejaInitialise(true);
		return (UtilisateurSiteGenPage)this;
	}

	//////////////
	// initLoin //
	//////////////

	protected boolean dejaInitialiseUtilisateurSiteGenPage = false;

	public UtilisateurSiteGenPage initLoinUtilisateurSiteGenPage(RequeteSiteFrFR requeteSite_) {
		setRequeteSite_(requeteSite_);
		if(!dejaInitialiseUtilisateurSiteGenPage) {
			dejaInitialiseUtilisateurSiteGenPage = true;
			initLoinUtilisateurSiteGenPage();
		}
		return (UtilisateurSiteGenPage)this;
	}

	public void initLoinUtilisateurSiteGenPage() {
		initUtilisateurSiteGenPage();
		super.initLoinClusterPage(requeteSite_);
	}

	public void initUtilisateurSiteGenPage() {
		listeUtilisateurSiteInit();
		utilisateurSite_Init();
	}

	@Override public void initLoinPourClasse(RequeteSiteFrFR requeteSite_) {
		initLoinUtilisateurSiteGenPage(requeteSite_);
	}

	/////////////////
	// requeteSite //
	/////////////////

	public void requeteSiteUtilisateurSiteGenPage(RequeteSiteFrFR requeteSite_) {
			super.requeteSiteClusterPage(requeteSite_);
		if(listeUtilisateurSite != null)
			listeUtilisateurSite.setRequeteSite_(requeteSite_);
	}

	public void requeteSitePourClasse(RequeteSiteFrFR requeteSite_) {
		requeteSiteUtilisateurSiteGenPage(requeteSite_);
	}

	/////////////
	// obtenir //
	/////////////

	@Override public Object obtenirPourClasse(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtenirUtilisateurSiteGenPage(v);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.obtenirPourClasse(v);
			}
		}
		return o;
	}
	public Object obtenirUtilisateurSiteGenPage(String var) {
		UtilisateurSiteGenPage oUtilisateurSiteGenPage = (UtilisateurSiteGenPage)this;
		switch(var) {
			case "listeUtilisateurSite":
				return oUtilisateurSiteGenPage.listeUtilisateurSite;
			case "utilisateurSite_":
				return oUtilisateurSiteGenPage.utilisateurSite_;
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
				o = attribuerUtilisateurSiteGenPage(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attribuerPourClasse(v, val);
			}
		}
		return o != null;
	}
	public Object attribuerUtilisateurSiteGenPage(String var, Object val) {
		UtilisateurSiteGenPage oUtilisateurSiteGenPage = (UtilisateurSiteGenPage)this;
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
					o = definirUtilisateurSiteGenPage(v, val);
				else if(o instanceof Cluster) {
					Cluster cluster = (Cluster)o;
					o = cluster.definirPourClasse(v, val);
				}
			}
		}
		return o != null;
	}
	public Object definirUtilisateurSiteGenPage(String var, String val) {
		switch(var) {
			default:
				return super.definirClusterPage(var, val);
		}
	}

	/////////////////
	// htmlScripts //
	/////////////////

	@Override public void htmlScripts() {
		htmlScriptsUtilisateurSiteGenPage();
		super.htmlScripts();
	}

	public void htmlScriptsUtilisateurSiteGenPage() {
	}

	////////////////
	// htmlScript //
	////////////////

	@Override public void htmlScript() {
		htmlScriptUtilisateurSiteGenPage();
		super.htmlScript();
	}

	public void htmlScriptUtilisateurSiteGenPage() {
	}

	//////////////
	// htmlBody //
	//////////////

	@Override public void htmlBody() {
		htmlBodyUtilisateurSiteGenPage();
		super.htmlBody();
	}

	public void htmlBodyUtilisateurSiteGenPage() {
	}

	//////////
	// html //
	//////////

	@Override public void html() {
		htmlUtilisateurSiteGenPage();
		super.html();
	}

	public void htmlUtilisateurSiteGenPage() {
	}

	//////////////
	// htmlMeta //
	//////////////

	@Override public void htmlMeta() {
		htmlMetaUtilisateurSiteGenPage();
		super.htmlMeta();
	}

	public void htmlMetaUtilisateurSiteGenPage() {
	}

	////////////////
	// htmlStyles //
	////////////////

	@Override public void htmlStyles() {
		htmlStylesUtilisateurSiteGenPage();
		super.htmlStyles();
	}

	public void htmlStylesUtilisateurSiteGenPage() {
	}

	///////////////
	// htmlStyle //
	///////////////

	@Override public void htmlStyle() {
		htmlStyleUtilisateurSiteGenPage();
		super.htmlStyle();
	}

	public void htmlStyleUtilisateurSiteGenPage() {
	}

	//////////////////
	// requeteApi //
	//////////////////

	public void requeteApiUtilisateurSiteGenPage() {
		RequeteApi requeteApi = Optional.ofNullable(requeteSite_).map(RequeteSiteFrFR::getRequeteApi_).orElse(null);
		Object o = Optional.ofNullable(requeteApi).map(RequeteApi::getOriginal).orElse(null);
		if(o != null && o instanceof UtilisateurSiteGenPage) {
			UtilisateurSiteGenPage original = (UtilisateurSiteGenPage)o;
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
		if(!(o instanceof UtilisateurSiteGenPage))
			return false;
		UtilisateurSiteGenPage that = (UtilisateurSiteGenPage)o;
		return super.equals(o);
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("UtilisateurSiteGenPage { ");
		sb.append(" }");
		return sb.toString();
	}
}
