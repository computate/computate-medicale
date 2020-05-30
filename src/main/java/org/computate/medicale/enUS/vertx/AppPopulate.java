package org.computate.medicale.enUS.vertx;

import org.computate.medicale.enUS.config.SiteConfig;
import org.computate.medicale.enUS.context.SiteContextEnUS;
import org.computate.medicale.enUS.wrap.Wrap;
import org.computate.medicale.enUS.request.SiteRequestEnUS;

public class AppPopulate extends AppPopulateGen<Object> {

	protected void _siteRequest_(Wrap<SiteRequestEnUS> c) {
	}

	protected void _siteContext(SiteContextEnUS o) {
	}

	protected void _siteConfig(Wrap<SiteConfig> c) {
		c.o(siteContext.getSiteConfig());
	}

	public static void  main(String[] args) throws Exception, Exception {
		AppPopulate api = new AppPopulate();
		api.initDeepAppPopulate();
		api.populateData();
	}

	public void  populateData() throws Exception, Exception {

	}
}
