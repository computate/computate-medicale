package org.computate.medicale.frFR.page.part;    


import org.computate.medicale.frFR.cluster.Cluster;
import org.computate.medicale.frFR.couverture.Couverture;
import org.computate.medicale.frFR.page.MiseEnPage;

/**
 * NomCanonique.enUS: org.computate.medicale.enUS.page.part.PagePart
 * MotCle: classeNomSimplePagePart
 */ 
public abstract class PagePart extends PagePartGen<Cluster> {  

	protected void _page_(Couverture<MiseEnPage> c) {}

	public void  htmlBody() {
		
	}

	/**
	 * Var.enUS: htmlBodyShort
	 */
	public void  htmlBodyCourt() {
		
	}

	protected void _partVar(Couverture<String> c) {}

	/**
	 * Var.enUS: htmlBeforePagePart
	 */
	public void  htmlAvantPagePart() {}

	/**
	 * Var.enUS: htmlAfterPagePart
	 */
	public void  htmlApresPagePart() {}
}
