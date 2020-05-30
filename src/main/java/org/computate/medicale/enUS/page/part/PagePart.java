package org.computate.medicale.enUS.page.part;

import org.computate.medicale.enUS.cluster.Cluster;
import org.computate.medicale.enUS.wrap.Wrap;
import org.computate.medicale.enUS.page.PageLayout;

/**
 * CanonicalName: org.computate.medicale.frFR.page.part.PagePart
 **/
public abstract class PagePart extends PagePartGen<Cluster> {

	protected void _page_(Wrap<PageLayout> c) {}

	public void  htmlBody() {
		
	}

	public void  htmlBodyShort() {
		
	}

	protected void _partVar(Wrap<String> c) {}

	public void  htmlBeforePagePart() {}

	public void  htmlAfterPagePart() {}
}
