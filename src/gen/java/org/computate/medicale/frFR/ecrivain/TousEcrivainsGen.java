package org.computate.medicale.frFR.ecrivain;

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
import java.math.RoundingMode;
import org.computate.medicale.frFR.couverture.Couverture;
import java.math.MathContext;
import org.computate.medicale.frFR.ecrivain.ToutEcrivain;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Objects;
import io.vertx.core.json.JsonArray;
import java.util.List;
import org.computate.medicale.frFR.requete.RequeteSiteFrFR;
import org.apache.commons.lang3.math.NumberUtils;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.lang.Object;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

/**	
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.ecrivain.TousEcrivains&fq=classeEtendGen_indexed_boolean:true">Trouver la classe  dans Solr. </a>
 * <br/>
 **/
public abstract class TousEcrivainsGen<DEV> extends Object {
	protected static final Logger LOGGER = LoggerFactory.getLogger(TousEcrivains.class);

	//////////////////
	// requeteSite_ //
	//////////////////

	/**	 L'entité requeteSite_
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected RequeteSiteFrFR requeteSite_;
	@JsonIgnore
	public Couverture<RequeteSiteFrFR> requeteSite_Couverture = new Couverture<RequeteSiteFrFR>().p(this).c(RequeteSiteFrFR.class).var("requeteSite_").o(requeteSite_);

	/**	<br/> L'entité requeteSite_
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.ecrivain.TousEcrivains&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:requeteSite_">Trouver l'entité requeteSite_ dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _requeteSite_(Couverture<RequeteSiteFrFR> c);

	public RequeteSiteFrFR getRequeteSite_() {
		return requeteSite_;
	}

	public void setRequeteSite_(RequeteSiteFrFR requeteSite_) {
		this.requeteSite_ = requeteSite_;
		this.requeteSite_Couverture.dejaInitialise = true;
	}
	protected TousEcrivains requeteSite_Init() {
		if(!requeteSite_Couverture.dejaInitialise) {
			_requeteSite_(requeteSite_Couverture);
			if(requeteSite_ == null)
				setRequeteSite_(requeteSite_Couverture.o);
		}
		requeteSite_Couverture.dejaInitialise(true);
		return (TousEcrivains)this;
	}

	///////////////
	// ecrivains //
	///////////////

	/**	 L'entité ecrivains
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<ToutEcrivain>(). 
	 */
	@JsonInclude(Include.NON_NULL)
	protected List<ToutEcrivain> ecrivains = new ArrayList<ToutEcrivain>();
	@JsonIgnore
	public Couverture<List<ToutEcrivain>> ecrivainsCouverture = new Couverture<List<ToutEcrivain>>().p(this).c(List.class).var("ecrivains").o(ecrivains);

	/**	<br/> L'entité ecrivains
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<ToutEcrivain>(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.medicale.frFR.ecrivain.TousEcrivains&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:ecrivains">Trouver l'entité ecrivains dans Solr</a>
	 * <br/>
	 * @param ecrivains est l'entité déjà construit. 
	 **/
	protected abstract void _ecrivains(List<ToutEcrivain> c);

	public List<ToutEcrivain> getEcrivains() {
		return ecrivains;
	}

	public void setEcrivains(List<ToutEcrivain> ecrivains) {
		this.ecrivains = ecrivains;
		this.ecrivainsCouverture.dejaInitialise = true;
	}
	public TousEcrivains addEcrivains(ToutEcrivain...objets) {
		for(ToutEcrivain o : objets) {
			addEcrivains(o);
		}
		return (TousEcrivains)this;
	}
	public TousEcrivains addEcrivains(ToutEcrivain o) {
		if(o != null && !ecrivains.contains(o))
			this.ecrivains.add(o);
		return (TousEcrivains)this;
	}
	protected TousEcrivains ecrivainsInit() {
		if(!ecrivainsCouverture.dejaInitialise) {
			_ecrivains(ecrivains);
		}
		ecrivainsCouverture.dejaInitialise(true);
		return (TousEcrivains)this;
	}

	//////////////
	// initLoin //
	//////////////

	protected boolean dejaInitialiseTousEcrivains = false;

	public TousEcrivains initLoinTousEcrivains(RequeteSiteFrFR requeteSite_) {
		setRequeteSite_(requeteSite_);
		if(!dejaInitialiseTousEcrivains) {
			dejaInitialiseTousEcrivains = true;
			initLoinTousEcrivains();
		}
		return (TousEcrivains)this;
	}

	public void initLoinTousEcrivains() {
		initTousEcrivains();
	}

	public void initTousEcrivains() {
		requeteSite_Init();
		ecrivainsInit();
	}

	public void initLoinPourClasse(RequeteSiteFrFR requeteSite_) {
		initLoinTousEcrivains(requeteSite_);
	}

	/////////////////
	// requeteSite //
	/////////////////

	public void requeteSiteTousEcrivains(RequeteSiteFrFR requeteSite_) {
	}

	public void requeteSitePourClasse(RequeteSiteFrFR requeteSite_) {
		requeteSiteTousEcrivains(requeteSite_);
	}

	/////////////
	// obtenir //
	/////////////

	public Object obtenirPourClasse(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtenirTousEcrivains(v);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.obtenirPourClasse(v);
			}
		}
		return o;
	}
	public Object obtenirTousEcrivains(String var) {
		TousEcrivains oTousEcrivains = (TousEcrivains)this;
		switch(var) {
			case "requeteSite_":
				return oTousEcrivains.requeteSite_;
			case "ecrivains":
				return oTousEcrivains.ecrivains;
			default:
				return null;
		}
	}

	///////////////
	// attribuer //
	///////////////

	public boolean attribuerPourClasse(String var, Object val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = attribuerTousEcrivains(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attribuerPourClasse(v, val);
			}
		}
		return o != null;
	}
	public Object attribuerTousEcrivains(String var, Object val) {
		TousEcrivains oTousEcrivains = (TousEcrivains)this;
		switch(var) {
			default:
				return null;
		}
	}

	/////////////
	// definir //
	/////////////

	public boolean definirPourClasse(String var, String val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		if(val != null) {
			for(String v : vars) {
				if(o == null)
					o = definirTousEcrivains(v, val);
				else if(o instanceof Cluster) {
					Cluster cluster = (Cluster)o;
					o = cluster.definirPourClasse(v, val);
				}
			}
		}
		return o != null;
	}
	public Object definirTousEcrivains(String var, String val) {
		switch(var) {
			default:
				return null;
		}
	}

	//////////////////
	// requeteApi //
	//////////////////

	public void requeteApiTousEcrivains() {
		RequeteApi requeteApi = Optional.ofNullable(requeteSite_).map(RequeteSiteFrFR::getRequeteApi_).orElse(null);
		Object o = Optional.ofNullable(requeteApi).map(RequeteApi::getOriginal).orElse(null);
		if(o != null && o instanceof TousEcrivains) {
			TousEcrivains original = (TousEcrivains)o;
		}
	}

	//////////////
	// hashCode //
	//////////////

	@Override public int hashCode() {
		return Objects.hash();
	}

	////////////
	// equals //
	////////////

	@Override public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof TousEcrivains))
			return false;
		TousEcrivains that = (TousEcrivains)o;
		return true;
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("TousEcrivains { ");
		sb.append(" }");
		return sb.toString();
	}
}
