package org.computate.medicale.frFR.design;

import org.computate.medicale.frFR.couverture.Couverture;

/**
 * Traduire: false
 **/
public class DesignPageAffichage extends DesignPageAffichageGen<DesignGenPageAffichage> {

	/**
	 * r: listeDesignPage
	 * r.enUS: listPageDesign
	 */
	protected void _pageDesign(Couverture<DesignPage> c) {
		if(listeDesignPage.size() == 1)
			c.o(listeDesignPage.get(0));
	}
}
