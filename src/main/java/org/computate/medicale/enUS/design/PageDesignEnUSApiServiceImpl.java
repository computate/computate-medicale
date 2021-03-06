package org.computate.medicale.enUS.design;

import org.apache.solr.client.solrj.util.ClientUtils;
import org.computate.medicale.enUS.context.SiteContextEnUS;
import org.computate.medicale.enUS.search.SearchList;

/**
 * Translate: false
 * CanonicalName.frFR: org.computate.medicale.frFR.design.DesignPageFrFRApiServiceImpl
 **/
public class PageDesignEnUSApiServiceImpl extends PageDesignEnUSGenApiServiceImpl {

	public PageDesignEnUSApiServiceImpl(SiteContextEnUS siteContext) {
		super(siteContext);
	} 

	@Override public void aSearchPageDesignVar(String uri, String apiMethod, SearchList<PageDesign> searchList, String var, String value) {
		if ("/page".equals(uri) || "/pdf".equals(uri) || "/email".equals(uri)) {
			if("design".equals(var))
				searchList.addFilterQuery("pageDesignCompleteName_indexed_string:" + ClientUtils.escapeQueryChars(value));
		}
		super.aSearchPageDesignVar(uri, apiMethod, searchList, var, value);
	}
	@Override public void aSearchPageDesignFq(String uri, String apiMethod, SearchList<PageDesign> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if ("/page".equals(uri) || "/pdf".equals(uri) || "/email".equals(uri)) {
			// skip
		}
		else {
			super.aSearchPageDesignFq(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
		}
	}
	@Override
	public void aSearchPageDesignUri(String uri, String apiMethod, SearchList<PageDesign> searchList) {
		if ("/".equals(uri)) {
			searchList.addFilterQuery("pageDesignCompleteName_indexed_string:" + ClientUtils.escapeQueryChars("home page"));
		}
	}
}
