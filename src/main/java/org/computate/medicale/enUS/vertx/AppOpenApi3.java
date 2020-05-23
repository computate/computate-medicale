package org.computate.medicale.enUS.vertx;

import org.computate.medicale.enUS.context.SiteContextEnUS;
import org.computate.medicale.enUS.wrap.Wrap;
import org.computate.medicale.enUS.request.SiteRequestEnUS;

public class AppOpenApi3 extends AppOpenApi3Gen<AppliSwagger2> {

	@Override()
	protected void  _apiVersion(Wrap<String> c) {
		c.o("3.0.0");
	}

	public static void  main(String[] args) {
		AppOpenApi3 api = new AppOpenApi3();
		SiteContextEnUS siteContext = new SiteContextEnUS();
		siteContext.initDeepSiteContextEnUS(null);
		SiteRequestEnUS siteRequest = new SiteRequestEnUS();
		siteRequest.setSiteContext_(siteContext);
		siteRequest.setSiteConfig_(siteContext.getSiteConfig());
		siteRequest.initDeepSiteRequestEnUS();
		api.initDeepAppOpenApi3(siteRequest);
		api.writeOpenApi();
	}
}
