package org.computate.medicale.frFR.vertx;  


import org.computate.medicale.frFR.config.ConfigSite;
import org.computate.medicale.frFR.contexte.SiteContexteFrFR;
import org.computate.medicale.frFR.couverture.Couverture;
import org.computate.medicale.frFR.requete.RequeteSiteFrFR;

/**
 * NomCanonique.enUS: org.computate.medicale.enUS.vertx.AppPopulate
 */
public class AppliPeupler extends AppliPeuplerGen<Object> {   

	/**
	 * Var.enUS: siteRequest_
	 */
	protected void _requeteSite_(Couverture<RequeteSiteFrFR> c) throws Exception {
	}

	/**
	 * Var.enUS: siteContext
	 */
	protected void _siteContexte(SiteContexteFrFR o) throws Exception {
	}

	/**
	 * Var.enUS: siteConfig
	 * r: siteContexte
	 * r.enUS: siteContext
	 * r: ConfigSite
	 * r.enUS: SiteConfig
	 */
	protected void _configSite(Couverture<ConfigSite> c) throws Exception {
		c.o(siteContexte.getConfigSite());
	}

	/**
	 * r: AppliPeupler
	 * r.enUS: AppPopulate
	 * r: initLoin
	 * r.enUS: initDeep
	 * r: peuplerDonnees
	 * r.enUS: populateData
	 */
	public static void main(String[] args) throws Exception {
		AppliPeupler api = new AppliPeupler();
		api.initLoinAppliPeupler();
		api.peuplerDonnees();
	}

	/**
	 * Var.enUS: populateData
	 */
	public void peuplerDonnees() throws Exception {

	}
}
