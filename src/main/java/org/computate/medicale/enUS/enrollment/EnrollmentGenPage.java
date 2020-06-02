package org.computate.medicale.enUS.enrollment;

import org.computate.medicale.enUS.cluster.ClusterPage;
import org.computate.medicale.enUS.page.PageLayout;
import org.computate.medicale.enUS.config.SiteConfig;
import org.computate.medicale.enUS.request.SiteRequestEnUS;
import org.computate.medicale.enUS.context.SiteContextEnUS;
import org.computate.medicale.enUS.user.SiteUser;
import java.io.IOException;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import org.computate.medicale.enUS.search.SearchList;
import org.computate.medicale.enUS.wrap.Wrap;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.api.OperationRequest;
import io.vertx.core.json.JsonArray;
import java.net.URLDecoder;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import java.util.Map;
import java.util.List;
import java.util.Optional;
import org.apache.solr.common.util.SimpleOrderedMap;
import java.util.stream.Collectors;
import java.util.Arrays;
import org.apache.solr.client.solrj.response.QueryResponse;
import java.math.BigDecimal;
import java.math.MathContext;
import org.apache.commons.collections.CollectionUtils;
import java.util.Objects;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.computate.medicale.enUS.cluster.ClusterPage;
import org.computate.medicale.enUS.page.PageLayout;
import org.computate.medicale.enUS.config.SiteConfig;
import org.computate.medicale.enUS.request.SiteRequestEnUS;
import org.computate.medicale.enUS.context.SiteContextEnUS;
import org.computate.medicale.enUS.user.SiteUser;
import java.io.IOException;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import org.computate.medicale.enUS.search.SearchList;
import org.computate.medicale.enUS.wrap.Wrap;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.api.OperationRequest;
import io.vertx.core.json.JsonArray;
import java.net.URLDecoder;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import java.util.Map;
import java.util.List;
import java.util.Optional;
import org.apache.solr.common.util.SimpleOrderedMap;
import java.util.stream.Collectors;
import java.util.Arrays;
import org.apache.solr.client.solrj.response.QueryResponse;
import java.math.BigDecimal;
import java.math.MathContext;
import org.apache.commons.collections.CollectionUtils;
import java.util.Objects;
import org.apache.solr.client.solrj.SolrQuery.SortClause;


/**
 * Translate: false
 **/
public class EnrollmentGenPage extends EnrollmentGenPageGen<ClusterPage> {

	public static final List<String> ROLES = Arrays.asList("SiteAdmin");
	public static final List<String> ROLE_READS = Arrays.asList("");

	/**
	 * {@inheritDoc}
	 * 
	 **/
	protected void _listMedicalEnrollment(Wrap<SearchList<MedicalEnrollment>> c) {
	}

	protected void _medicalEnrollment(Wrap<MedicalEnrollment> c) {
		if(listMedicalEnrollment != null && listMedicalEnrollment.size() == 1)
			c.o(listMedicalEnrollment.get(0));
	}

	@Override protected void _pageH1(Wrap<String> c) {
			c.o("enrollments");
	}

	@Override protected void _pageH2(Wrap<String> c) {
		if(medicalEnrollment != null && medicalEnrollment.getEnrollmentCompleteName() != null)
			c.o(medicalEnrollment.getEnrollmentCompleteName());
	}

	@Override protected void _pageH3(Wrap<String> c) {
		c.o("");
	}

	@Override protected void _pageTitle(Wrap<String> c) {
		if(medicalEnrollment != null && medicalEnrollment.getEnrollmentCompleteName() != null)
			c.o(medicalEnrollment.getEnrollmentCompleteName());
		else if(medicalEnrollment != null)
			c.o("");
		else if(listMedicalEnrollment == null || listMedicalEnrollment.size() == 0)
			c.o("no enrollment found");
	}

	@Override protected void _pageUri(Wrap<String> c) {
		c.o("/enrollment");
	}

	@Override protected void _pageImageUri(Wrap<String> c) {
			c.o("/png/enrollment-999.png");
	}

	@Override protected void _contextIconGroup(Wrap<String> c) {
			c.o("solid");
	}

	@Override protected void _contextIconName(Wrap<String> c) {
			c.o("edit");
	}

	@Override public void initDeepEnrollmentGenPage() {
		initEnrollmentGenPage();
		super.initDeepPageLayout();
	}

	@Override public void htmlScriptsEnrollmentGenPage() {
		e("script").a("src", staticBaseUrl, "/js/enUS/EnrollmentPage.js").f().g("script");
		e("script").a("src", staticBaseUrl, "/js/enUS/ChildPage.js").f().g("script");
		e("script").a("src", staticBaseUrl, "/js/enUS/SiteUserPage.js").f().g("script");
	}

	@Override public void htmlScriptEnrollmentGenPage() {
		l("$(document).ready(function() {");
		tl(1, "document.onkeydown = function(evt) {");
		tl(2, "evt = evt || window.event;");
		tl(2, "var isEscape = false;");
		tl(2, "if ('key' in evt) {");
		tl(3, "isEscape = (evt.key === 'Escape' || evt.key === 'Esc');");
		tl(2, "} else {");
		tl(3, "isEscape = (evt.keyCode === 27);");
		tl(2, "}");
		tl(2, "if (isEscape) {");
		tl(3, "$('.w3-modal:visible').hide();");
		tl(2, "}");
		tl(1, "};");
		tl(1, "window.eventBus = new EventBus('/eventbus');");
		tl(1, "var pk = ", Optional.ofNullable(siteRequest_.getRequestPk()).map(l -> l.toString()).orElse("null"), ";");
		tl(1, "if(pk != null) {");
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			tl(2, "suggestMedicalEnrollmentPatientKey([{'name':'fq','value':'enrollmentKeys:' + pk}], $('#listMedicalEnrollmentPatientKey_Page'), pk, true); ");
		} else {
			tl(2, "suggestMedicalEnrollmentPatientKey([{'name':'fq','value':'enrollmentKeys:' + pk}], $('#listMedicalEnrollmentPatientKey_Page'), pk, false); ");
		}
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			tl(2, "suggestMedicalEnrollmentUserKeys([{'name':'fq','value':'enrollmentKeys:' + pk}], $('#listMedicalEnrollmentUserKeys_Page'), pk, true); ");
		} else {
			tl(2, "suggestMedicalEnrollmentUserKeys([{'name':'fq','value':'enrollmentKeys:' + pk}], $('#listMedicalEnrollmentUserKeys_Page'), pk, false); ");
		}
		tl(2, "$('#signatureInputMedicalEnrollment' + pk + 'enrollmentSignature1').jSignature({'height':200}).bind('change', function(e){ patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + pk }], 'setEnrollmentSignature1', $('#signatureInputMedicalEnrollment' + pk + 'enrollmentSignature1').jSignature('getData', 'default')); }); ");
		tl(2, "$('#signatureInputMedicalEnrollment' + pk + 'enrollmentSignature2').jSignature({'height':200}).bind('change', function(e){ patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + pk }], 'setEnrollmentSignature2', $('#signatureInputMedicalEnrollment' + pk + 'enrollmentSignature2').jSignature('getData', 'default')); }); ");
		tl(2, "$('#signatureInputMedicalEnrollment' + pk + 'enrollmentSignature3').jSignature({'height':200}).bind('change', function(e){ patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + pk }], 'setEnrollmentSignature3', $('#signatureInputMedicalEnrollment' + pk + 'enrollmentSignature3').jSignature('getData', 'default')); }); ");
		tl(2, "$('#signatureInputMedicalEnrollment' + pk + 'enrollmentSignature4').jSignature({'height':200}).bind('change', function(e){ patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + pk }], 'setEnrollmentSignature4', $('#signatureInputMedicalEnrollment' + pk + 'enrollmentSignature4').jSignature('getData', 'default')); }); ");
		tl(2, "$('#signatureInputMedicalEnrollment' + pk + 'enrollmentSignature5').jSignature({'height':200}).bind('change', function(e){ patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + pk }], 'setEnrollmentSignature5', $('#signatureInputMedicalEnrollment' + pk + 'enrollmentSignature5').jSignature('getData', 'default')); }); ");
		tl(2, "$('#signatureInputMedicalEnrollment' + pk + 'enrollmentSignature6').jSignature({'height':200}).bind('change', function(e){ patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + pk }], 'setEnrollmentSignature6', $('#signatureInputMedicalEnrollment' + pk + 'enrollmentSignature6').jSignature('getData', 'default')); }); ");
		tl(2, "$('#signatureInputMedicalEnrollment' + pk + 'enrollmentSignature7').jSignature({'height':200}).bind('change', function(e){ patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + pk }], 'setEnrollmentSignature7', $('#signatureInputMedicalEnrollment' + pk + 'enrollmentSignature7').jSignature('getData', 'default')); }); ");
		tl(2, "$('#signatureInputMedicalEnrollment' + pk + 'enrollmentSignature8').jSignature({'height':200}).bind('change', function(e){ patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + pk }], 'setEnrollmentSignature8', $('#signatureInputMedicalEnrollment' + pk + 'enrollmentSignature8').jSignature('getData', 'default')); }); ");
		tl(2, "$('#signatureInputMedicalEnrollment' + pk + 'enrollmentSignature9').jSignature({'height':200}).bind('change', function(e){ patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + pk }], 'setEnrollmentSignature9', $('#signatureInputMedicalEnrollment' + pk + 'enrollmentSignature9').jSignature('getData', 'default')); }); ");
		tl(2, "$('#signatureInputMedicalEnrollment' + pk + 'enrollmentSignature10').jSignature({'height':200}).bind('change', function(e){ patchMedicalEnrollmentVal([{ name: 'fq', value: 'pk:' + pk }], 'setEnrollmentSignature10', $('#signatureInputMedicalEnrollment' + pk + 'enrollmentSignature10').jSignature('getData', 'default')); }); ");
		tl(1, "}");
		tl(1, "websocketMedicalEnrollment(websocketMedicalEnrollmentInner);");
		l("});");
	}

	public void htmlFormPageMedicalEnrollment(MedicalEnrollment o) {
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPk("Page");
			o.htmCreated("Page");
			o.htmModified("Page");
			o.htmObjectId("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmArchived("Page");
			o.htmDeleted("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmEnrollmentApproved("Page");
			o.htmEnrollmentImmunizations("Page");
			o.htmCustomerProfileId("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmFamilyAddress("Page");
			o.htmEnrollmentSpecialConsiderations("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientMedicalConditions("Page");
			o.htmPatientPreviousClinicsAttended("Page");
			o.htmFamilyHowDoYouKnowTheClinic("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientDescription("Page");
			o.htmPatientObjectives("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientKey("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmUserKeys("Page");
		} g("div");
	}

	public void htmlFormPOSTMedicalEnrollment(MedicalEnrollment o) {
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPk("POST");
			o.htmCreated("POST");
			o.htmModified("POST");
			o.htmObjectId("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmArchived("POST");
			o.htmDeleted("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmEnrollmentApproved("POST");
			o.htmEnrollmentImmunizations("POST");
			o.htmCustomerProfileId("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmFamilyAddress("POST");
			o.htmEnrollmentSpecialConsiderations("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientMedicalConditions("POST");
			o.htmPatientPreviousClinicsAttended("POST");
			o.htmFamilyHowDoYouKnowTheClinic("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientDescription("POST");
			o.htmPatientObjectives("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientKey("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmUserKeys("POST");
		} g("div");
	}

	public void htmlFormPUTImportMedicalEnrollment(MedicalEnrollment o) {
		{ e("div").a("class", "w3-cell-row ").f();
			e("textarea")
				.a("class", "PUTImport_list w3-input w3-border ")
				.a("style", "height: 400px; ")
				.a("placeholder", "{ \"list\": [ { \"pk\": ... , \"saves\": [ ... ] }, ... ] }")
				;
				f();
			g("textarea");
		} g("div");
	}

	public void htmlFormPUTMergeMedicalEnrollment(MedicalEnrollment o) {
		{ e("div").a("class", "w3-cell-row ").f();
			e("textarea")
				.a("class", "PUTMerge_list w3-input w3-border ")
				.a("style", "height: 400px; ")
				.a("placeholder", "{ \"list\": [ { \"pk\": ... , \"saves\": [ ... ] }, ... ] }")
				;
				f();
			g("textarea");
		} g("div");
	}

	public void htmlFormPUTCopyMedicalEnrollment(MedicalEnrollment o) {
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmCreated("PUTCopy");
			o.htmModified("PUTCopy");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmArchived("PUTCopy");
			o.htmDeleted("PUTCopy");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmEnrollmentApproved("PUTCopy");
			o.htmEnrollmentImmunizations("PUTCopy");
			o.htmCustomerProfileId("PUTCopy");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmFamilyAddress("PUTCopy");
			o.htmEnrollmentSpecialConsiderations("PUTCopy");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientMedicalConditions("PUTCopy");
			o.htmPatientPreviousClinicsAttended("PUTCopy");
			o.htmFamilyHowDoYouKnowTheClinic("PUTCopy");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientDescription("PUTCopy");
			o.htmPatientObjectives("PUTCopy");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientKey("PUTCopy");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmUserKeys("PUTCopy");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmInheritPk("PUTCopy");
			o.htmSessionId("PUTCopy");
			o.htmUserId("PUTCopy");
			o.htmUserKey("PUTCopy");
			o.htmPatientCompleteName("PUTCopy");
			o.htmPatientCompleteNamePreferred("PUTCopy");
			o.htmPatientBirthDate("PUTCopy");
			o.htmClinicAddress("PUTCopy");
			o.htmEnrollmentSignature1("PUTCopy");
			o.htmEnrollmentSignature2("PUTCopy");
			o.htmEnrollmentSignature3("PUTCopy");
			o.htmEnrollmentSignature4("PUTCopy");
			o.htmEnrollmentSignature5("PUTCopy");
			o.htmEnrollmentSignature6("PUTCopy");
			o.htmEnrollmentSignature7("PUTCopy");
			o.htmEnrollmentSignature8("PUTCopy");
			o.htmEnrollmentSignature9("PUTCopy");
			o.htmEnrollmentSignature10("PUTCopy");
			o.htmEnrollmentDate1("PUTCopy");
			o.htmEnrollmentDate2("PUTCopy");
			o.htmEnrollmentDate3("PUTCopy");
			o.htmEnrollmentDate4("PUTCopy");
			o.htmEnrollmentDate5("PUTCopy");
			o.htmEnrollmentDate6("PUTCopy");
			o.htmEnrollmentDate7("PUTCopy");
			o.htmEnrollmentDate8("PUTCopy");
			o.htmEnrollmentDate9("PUTCopy");
			o.htmEnrollmentDate10("PUTCopy");
		} g("div");
	}

	public void htmlFormPATCHMedicalEnrollment(MedicalEnrollment o) {
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmCreated("PATCH");
			o.htmModified("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmArchived("PATCH");
			o.htmDeleted("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmEnrollmentApproved("PATCH");
			o.htmEnrollmentImmunizations("PATCH");
			o.htmCustomerProfileId("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmFamilyAddress("PATCH");
			o.htmEnrollmentSpecialConsiderations("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientMedicalConditions("PATCH");
			o.htmPatientPreviousClinicsAttended("PATCH");
			o.htmFamilyHowDoYouKnowTheClinic("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientDescription("PATCH");
			o.htmPatientObjectives("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientKey("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmUserKeys("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmInheritPk("PATCH");
			o.htmSessionId("PATCH");
			o.htmUserId("PATCH");
			o.htmUserKey("PATCH");
			o.htmPatientCompleteName("PATCH");
			o.htmPatientCompleteNamePreferred("PATCH");
			o.htmPatientBirthDate("PATCH");
			o.htmClinicAddress("PATCH");
			o.htmEnrollmentSignature1("PATCH");
			o.htmEnrollmentSignature2("PATCH");
			o.htmEnrollmentSignature3("PATCH");
			o.htmEnrollmentSignature4("PATCH");
			o.htmEnrollmentSignature5("PATCH");
			o.htmEnrollmentSignature6("PATCH");
			o.htmEnrollmentSignature7("PATCH");
			o.htmEnrollmentSignature8("PATCH");
			o.htmEnrollmentSignature9("PATCH");
			o.htmEnrollmentSignature10("PATCH");
			o.htmEnrollmentDate1("PATCH");
			o.htmEnrollmentDate2("PATCH");
			o.htmEnrollmentDate3("PATCH");
			o.htmEnrollmentDate4("PATCH");
			o.htmEnrollmentDate5("PATCH");
			o.htmEnrollmentDate6("PATCH");
			o.htmEnrollmentDate7("PATCH");
			o.htmEnrollmentDate8("PATCH");
			o.htmEnrollmentDate9("PATCH");
			o.htmEnrollmentDate10("PATCH");
		} g("div");
	}

	public void htmlFormSearchMedicalEnrollment(MedicalEnrollment o) {
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPk("Search");
			o.htmCreated("Search");
			o.htmModified("Search");
			o.htmObjectId("Search");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmArchived("Search");
			o.htmDeleted("Search");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmEnrollmentApproved("Search");
			o.htmEnrollmentImmunizations("Search");
			o.htmCustomerProfileId("Search");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmFamilyAddress("Search");
			o.htmEnrollmentSpecialConsiderations("Search");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientMedicalConditions("Search");
			o.htmPatientPreviousClinicsAttended("Search");
			o.htmFamilyHowDoYouKnowTheClinic("Search");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientDescription("Search");
			o.htmPatientObjectives("Search");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientKey("Search");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmUserKeys("Search");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmInheritPk("Search");
			o.htmSessionId("Search");
			o.htmUserId("Search");
			o.htmUserKey("Search");
			o.htmObjectTitle("Search");
			o.htmPatientCompleteName("Search");
			o.htmPatientCompleteNamePreferred("Search");
			o.htmPatientBirthDate("Search");
			o.htmClinicAddress("Search");
			o.htmEnrollmentDate1("Search");
			o.htmEnrollmentDate2("Search");
			o.htmEnrollmentDate3("Search");
			o.htmEnrollmentDate4("Search");
			o.htmEnrollmentDate5("Search");
			o.htmEnrollmentDate6("Search");
			o.htmEnrollmentDate7("Search");
			o.htmEnrollmentDate8("Search");
			o.htmEnrollmentDate9("Search");
			o.htmEnrollmentDate10("Search");
		} g("div");
	}

	@Override public void htmlBodyEnrollmentGenPage() {

		OperationRequest operationRequest = siteRequest_.getOperationRequest();
		JsonObject params = operationRequest.getParams();
		if(listMedicalEnrollment == null || listMedicalEnrollment.size() == 0) {

			{ e("h1").f();
				{ e("a").a("href", "/enrollment").a("class", "w3-bar-item w3-btn w3-center w3-block w3-blue-gray w3-hover-blue-gray ").f();
					if(contextIconCssClasses != null)
						e("i").a("class", contextIconCssClasses + " site-menu-icon ").f().g("i");
					e("span").a("class", " ").f().sx("enrollments").g("span");
				} g("a");
			} g("h1");
			e("div").a("class", "w3-padding-16 w3-card-4 w3-light-grey ").f();
			{ e("h2").f();
				{ e("span").a("class", "w3-bar-item w3-padding w3-center w3-block w3-blue-gray ").f();
					if(contextIconCssClasses != null)
						e("i").a("class", contextIconCssClasses + " site-menu-icon ").f().g("i");
					e("span").a("class", " ").f().sx("no enrollment found").g("span");
				} g("span");
			} g("h2");
		} else if(listMedicalEnrollment != null && listMedicalEnrollment.size() == 1 && params.getJsonObject("query").getString("q").equals("*:*")) {
			MedicalEnrollment o = listMedicalEnrollment.get(0);
			siteRequest_.setRequestPk(o.getPk());
			if(StringUtils.isNotEmpty(pageH1)) {
				{ e("h1").f();
					{ e("a").a("href", "/enrollment").a("class", "w3-bar-item w3-btn w3-center w3-block w3-blue-gray w3-hover-blue-gray ").f();
						if(contextIconCssClasses != null)
							e("i").a("class", contextIconCssClasses + " site-menu-icon ").f().g("i");
						e("span").a("class", " ").f().sx(pageH1).g("span");
					} g("a");
				} g("h1");
			}
			e("div").a("class", "w3-padding-16 w3-card-4 w3-light-grey ").f();
			if(StringUtils.isNotEmpty(pageH2)) {
				{ e("h2").f();
					{ e("span").a("class", "w3-bar-item w3-padding w3-center w3-block w3-blue-gray ").f();
						e("span").a("class", " ").f().sx(pageH2).g("span");
					} g("span");
				} g("h2");
			}
			if(StringUtils.isNotEmpty(pageH3)) {
				{ e("h3").f();
					{ e("span").a("class", "w3-bar-item w3-padding w3-center w3-block w3-blue-gray ").f();
						e("span").a("class", " ").f().sx(pageH3).g("span");
					} g("span");
				} g("h3");
			}
		} else {

			{ e("h1").f();
				{ e("a").a("href", "/enrollment").a("class", "w3-bar-item w3-btn w3-center w3-block w3-blue-gray w3-hover-blue-gray ").f();
					if(contextIconCssClasses != null)
						e("i").a("class", contextIconCssClasses + " site-menu-icon ").f().g("i");
					e("span").a("class", " ").f().sx(pageH1).g("span");
				} g("a");
			} g("h1");
			e("div").a("class", "").f();
				{ e("div").f();
					JsonObject queryParams = Optional.ofNullable(operationRequest).map(OperationRequest::getParams).map(or -> or.getJsonObject("query")).orElse(new JsonObject());
					Long num = listMedicalEnrollment.getQueryResponse().getResults().getNumFound();
					String q = "*:*";
					String query1 = "patientMedicalConditions";
					String query2 = "";
					String query = "*:*";
					for(String paramName : queryParams.fieldNames()) {
						String entityVar = null;
						String valueIndexed = null;
						Object paramObjectValues = queryParams.getValue(paramName);
						JsonArray paramObjects = paramObjectValues instanceof JsonArray ? (JsonArray)paramObjectValues : new JsonArray().add(paramObjectValues);

						try {
							for(Object paramObject : paramObjects) {
								switch(paramName) {
									case "q":
										q = (String)paramObject;
										entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
										valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
										query1 = entityVar.equals("*") ? query1 : entityVar;
										query2 = valueIndexed;
										query = query1 + ":" + query2;
								}
							}
						} catch(Exception e) {
							ExceptionUtils.rethrow(e);
						}
					}

					Integer rows1 = Optional.ofNullable(listMedicalEnrollment).map(l -> l.getRows()).orElse(10);
					Integer start1 = Optional.ofNullable(listMedicalEnrollment).map(l -> l.getStart()).orElse(1);
					Integer start2 = start1 - rows1;
					Integer start3 = start1 + rows1;
					Integer rows2 = rows1 / 2;
					Integer rows3 = rows1 * 2;
					start2 = start2 < 0 ? 0 : start2;
					StringBuilder fqs = new StringBuilder();
					for(String fq : Optional.ofNullable(listMedicalEnrollment).map(l -> l.getFilterQueries()).orElse(new String[0])) {
						if(!StringUtils.contains(fq, "(")) {
							String fq1 = StringUtils.substringBefore(fq, "_");
							String fq2 = StringUtils.substringAfter(fq, ":");
							if(!StringUtils.startsWithAny(fq, "classCanonicalNames_", "archived_", "deleted_", "sessionId", "userKeys"))
								fqs.append("&fq=").append(fq1).append(":").append(fq2);
						}
					}
					StringBuilder sorts = new StringBuilder();
					for(SortClause sort : Optional.ofNullable(listMedicalEnrollment).map(l -> l.getSorts()).orElse(Arrays.asList())) {
						sorts.append("&sort=").append(StringUtils.substringBefore(sort.getItem(), "_")).append(" ").append(sort.getOrder().name());
					}

					if(start1 == 0) {
						e("i").a("class", "fas fa-arrow-square-left w3-opacity ").f().g("i");
					} else {
						{ e("a").a("href", "/enrollment?q=", query, fqs, sorts, "&start=", start2, "&rows=", rows1).f();
							e("i").a("class", "fas fa-arrow-square-left ").f().g("i");
						} g("a");
					}

					if(rows1 <= 1) {
						e("i").a("class", "fas fa-minus-square w3-opacity ").f().g("i");
					} else {
						{ e("a").a("href", "/enrollment?q=", query, fqs, sorts, "&start=", start1, "&rows=", rows2).f();
							e("i").a("class", "fas fa-minus-square ").f().g("i");
						} g("a");
					}

					{ e("a").a("href", "/enrollment?q=", query, fqs, sorts, "&start=", start1, "&rows=", rows3).f();
						e("i").a("class", "fas fa-plus-square ").f().g("i");
					} g("a");

					if(start3 >= num) {
						e("i").a("class", "fas fa-arrow-square-right w3-opacity ").f().g("i");
					} else {
						{ e("a").a("href", "/enrollment?q=", query, fqs, sorts, "&start=", start3, "&rows=", rows1).f();
							e("i").a("class", "fas fa-arrow-square-right ").f().g("i");
						} g("a");
					}
						e("span").f().sx((start1 + 1), " - ", (start1 + rows1), " of ", num).g("span");
				} g("div");
				table1EnrollmentGenPage();
		}

		if(listMedicalEnrollment != null && listMedicalEnrollment.size() == 1 && params.getJsonObject("query").getString("q").equals("*:*")) {
			MedicalEnrollment o = listMedicalEnrollment.first();

			{ e("div").a("class", "").f();

				if(o.getPk() != null) {
					{ e("form").a("action", "").a("id", "MedicalEnrollmentForm").a("style", "display: inline-block; width: 100%; ").a("onsubmit", "event.preventDefault(); return false; ").f();
						e("input")
						.a("name", "pk")
						.a("class", "valuePk")
						.a("type", "hidden")
						.a("value", o.getPk())
						.fg();
						e("input")
						.a("name", "focusId")
						.a("type", "hidden")
						.fg();
					} g("form");
					htmlFormPageMedicalEnrollment(o);
				}

			} g("div");

		}
		htmlBodyFormsEnrollmentGenPage();
		g("div");
	}

	public void table1EnrollmentGenPage() {
		{ e("table").a("class", "w3-table w3-bordered w3-striped w3-border w3-hoverable ").f();
			table2EnrollmentGenPage();
		} g("table");
	}

	public void table2EnrollmentGenPage() {
		thead1EnrollmentGenPage();
		tbody1EnrollmentGenPage();
		tfoot1EnrollmentGenPage();
	}

	public void thead1EnrollmentGenPage() {
		{ e("thead").a("class", "w3-blue-gray w3-hover-blue-gray ").f();
			thead2EnrollmentGenPage();
		} g("thead");
	}

	public void thead2EnrollmentGenPage() {
			{ e("tr").f();
			if(getColumnCreated()) {
				e("th").f().sx("created").g("th");
			}
			if(getColumnObjectTitle()) {
				e("th").f().sx("").g("th");
			}
			} g("tr");
	}

	public void tbody1EnrollmentGenPage() {
		{ e("tbody").f();
			tbody2EnrollmentGenPage();
		} g("tbody");
	}

	public void tbody2EnrollmentGenPage() {
		Map<String, Map<String, List<String>>> highlighting = listMedicalEnrollment.getQueryResponse().getHighlighting();
		for(int i = 0; i < listMedicalEnrollment.size(); i++) {
			MedicalEnrollment o = listMedicalEnrollment.getList().get(i);
			Map<String, List<String>> highlights = highlighting == null ? null : highlighting.get(o.getId());
			List<String> highlightList = highlights == null ? null : highlights.get(highlights.keySet().stream().findFirst().orElse(null));
			String uri = "/enrollment/" + o.getPk();
			{ e("tr").f();
				if(getColumnCreated()) {
					{ e("td").f();
						{ e("a").a("href", uri).f();
							{ e("span").f();
								sx(o.strCreated());
							} g("span");
						} g("a");
					} g("td");
				}
				if(getColumnObjectTitle()) {
					{ e("td").f();
						{ e("a").a("href", uri).f();
							e("i").a("class", "fas fa-edit ").f().g("i");
							{ e("span").f();
								sx(o.strObjectTitle());
							} g("span");
						} g("a");
					} g("td");
				}
			} g("tr");
		}
	}

	public void tfoot1EnrollmentGenPage() {
		{ e("tfoot").a("class", "w3-blue-gray w3-hover-blue-gray ").f();
			tfoot2EnrollmentGenPage();
		} g("tfoot");
	}

	public void tfoot2EnrollmentGenPage() {
		{ e("tr").f();
			SimpleOrderedMap facets = (SimpleOrderedMap)Optional.ofNullable(listMedicalEnrollment.getQueryResponse()).map(QueryResponse::getResponse).map(r -> r.get("facets")).orElse(new SimpleOrderedMap());
			if(getColumnCreated()) {
				e("td").f();
				g("td");
			}
			if(getColumnObjectTitle()) {
				e("td").f();
				g("td");
			}
		} g("tr");
	}

	public Boolean getColumnCreated() {
		return true;
	}

	public Boolean getColumnObjectTitle() {
		return true;
	}

	public void htmlBodyFormsEnrollmentGenPage() {
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("div").a("class", "w3-margin-top ").f();

			if(listMedicalEnrollment != null && listMedicalEnrollment.size() == 1) {
				{ e("button")
					.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-blue-gray ")
						.a("id", "refreshThisEnrollmentGenPage")
						.a("onclick", "patchMedicalEnrollmentVals( [ {name: 'fq', value: 'pk:' + " + siteRequest_.getRequestPk() + " } ], {}, function() { addGlow($('#refreshThisEnrollmentGenPage')); }, function() { addError($('#refreshThisEnrollmentGenPage')); }); return false; ").f();
						e("i").a("class", "fas fa-sync-alt ").f().g("i");
					sx("refresh this enrollment");
				} g("button");
			}

			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-blue-gray ")
				.a("onclick", "$('#postMedicalEnrollmentModal').show(); ")
				.f();
				e("i").a("class", "fas fa-file-plus ").f().g("i");
				sx("Create an enrollment");
			} g("button");
			{ e("div").a("id", "postMedicalEnrollmentModal").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-blue-gray ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#postMedicalEnrollmentModal').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Create an enrollment").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").f();
							MedicalEnrollment o = new MedicalEnrollment();
							o.setSiteRequest_(siteRequest_);

							// Form POST
							{ e("div").a("id", "postMedicalEnrollmentForm").f();
								htmlFormPOSTMedicalEnrollment(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-blue-gray ")
								.a("onclick", "postMedicalEnrollment($('#postMedicalEnrollmentForm')); ")
								.f().sx("Create an enrollment")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-blue-gray ")
				.a("onclick", "$('#putimportMedicalEnrollmentModal').show(); ")
				.f();
				e("i").a("class", "fas fa-file-import ").f().g("i");
				sx("Import enrollments");
			} g("button");
			{ e("div").a("id", "putimportMedicalEnrollmentModal").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-blue-gray ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#putimportMedicalEnrollmentModal').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Import enrollments").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").f();
							MedicalEnrollment o = new MedicalEnrollment();
							o.setSiteRequest_(siteRequest_);

							// Form PUT
							{ e("div").a("id", "putimportMedicalEnrollmentForm").f();
								htmlFormPUTImportMedicalEnrollment(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-blue-gray ")
								.a("onclick", "putimportMedicalEnrollment($('#putimportMedicalEnrollmentForm')); ")
								.f().sx("Import enrollments")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-blue-gray ")
				.a("onclick", "$('#putmergeMedicalEnrollmentModal').show(); ")
				.f();
				e("i").a("class", "fas fa-code-merge ").f().g("i");
				sx("Merge enrollments");
			} g("button");
			{ e("div").a("id", "putmergeMedicalEnrollmentModal").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-blue-gray ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#putmergeMedicalEnrollmentModal').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Merge enrollments").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").f();
							MedicalEnrollment o = new MedicalEnrollment();
							o.setSiteRequest_(siteRequest_);

							// Form PUT
							{ e("div").a("id", "putmergeMedicalEnrollmentForm").f();
								htmlFormPUTMergeMedicalEnrollment(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-blue-gray ")
								.a("onclick", "putmergeMedicalEnrollment($('#putmergeMedicalEnrollmentForm')); ")
								.f().sx("Merge enrollments")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-blue-gray ")
				.a("onclick", "$('#putcopyMedicalEnrollmentModal').show(); ")
				.f();
				e("i").a("class", "fas fa-copy ").f().g("i");
				sx("Duplicate enrollments");
			} g("button");
			{ e("div").a("id", "putcopyMedicalEnrollmentModal").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-blue-gray ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#putcopyMedicalEnrollmentModal').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Duplicate enrollments").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").f();
							MedicalEnrollment o = new MedicalEnrollment();
							o.setSiteRequest_(siteRequest_);

							// Form PUT
							{ e("div").a("id", "putcopyMedicalEnrollmentForm").f();
								htmlFormPUTCopyMedicalEnrollment(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-blue-gray ")
								.a("onclick", "putcopyMedicalEnrollment($('#putcopyMedicalEnrollmentForm'), ", medicalEnrollment == null ? "null" : medicalEnrollment.getPk(), "); ")
								.f().sx("Duplicate enrollments")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-blue-gray ")
				.a("onclick", "$('#patchMedicalEnrollmentModal').show(); ")
				.f();
				e("i").a("class", "fas fa-edit ").f().g("i");
				sx("Modify enrollments");
			} g("button");
			{ e("div").a("id", "patchMedicalEnrollmentModal").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-blue-gray ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#patchMedicalEnrollmentModal').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Modify enrollments").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").f();
							MedicalEnrollment o = new MedicalEnrollment();
							o.setSiteRequest_(siteRequest_);

							// FormValues PATCH
							{ e("form").a("action", "").a("id", "patchMedicalEnrollmentFormValues").a("onsubmit", "event.preventDefault(); return false; ").f();
								htmlFormPATCHMedicalEnrollment(o);
							} g("form");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-blue-gray ")
								.a("onclick", "patchMedicalEnrollment(null, $('#patchMedicalEnrollmentFormValues'), ", Optional.ofNullable(medicalEnrollment).map(MedicalEnrollment::getPk).map(a -> a.toString()).orElse("null"), ", function() {}, function() {}); ")
								.f().sx("Modify enrollments")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");

			g("div");
		}
		htmlSuggestedEnrollmentGenPage(this, null, listMedicalEnrollment);
	}

	/**
	**/
	public static void htmlSuggestedEnrollmentGenPage(PageLayout p, String id, SearchList<MedicalEnrollment> listMedicalEnrollment) {
		SiteRequestEnUS siteRequest_ = p.getSiteRequest_();
		try {
			OperationRequest operationRequest = siteRequest_.getOperationRequest();
			JsonObject queryParams = Optional.ofNullable(operationRequest).map(OperationRequest::getParams).map(or -> or.getJsonObject("query")).orElse(new JsonObject());
			String q = "*:*";
			String query1 = "patientMedicalConditions";
			String query2 = "";
			for(String paramName : queryParams.fieldNames()) {
				String entityVar = null;
				String valueIndexed = null;
				Object paramObjectValues = queryParams.getValue(paramName);
				JsonArray paramObjects = paramObjectValues instanceof JsonArray ? (JsonArray)paramObjectValues : new JsonArray().add(paramObjectValues);

				try {
					for(Object paramObject : paramObjects) {
						switch(paramName) {
							case "q":
								q = (String)paramObject;
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								query1 = entityVar.equals("*") ? query1 : entityVar;
								query2 = valueIndexed.equals("*") ? "" : valueIndexed;
						}
					}
				} catch(Exception e) {
					ExceptionUtils.rethrow(e);
				}
			}

			Integer rows1 = Optional.ofNullable(listMedicalEnrollment).map(l -> l.getRows()).orElse(10);
			Integer start1 = Optional.ofNullable(listMedicalEnrollment).map(l -> l.getStart()).orElse(1);
			Integer start2 = start1 - rows1;
			Integer start3 = start1 + rows1;
			Integer rows2 = rows1 / 2;
			Integer rows3 = rows1 * 2;
			start2 = start2 < 0 ? 0 : start2;
			StringBuilder fqs = new StringBuilder();
			for(String fq : Optional.ofNullable(listMedicalEnrollment).map(l -> l.getFilterQueries()).orElse(new String[0])) {
				if(!StringUtils.contains(fq, "(")) {
					String fq1 = StringUtils.substringBefore(fq, "_");
					String fq2 = StringUtils.substringAfter(fq, ":");
					if(!StringUtils.startsWithAny(fq, "classCanonicalNames_", "archived_", "deleted_", "sessionId", "userKeys"))
						fqs.append("&fq=").append(fq1).append(":").append(fq2);
				}
			}
			StringBuilder sorts = new StringBuilder();
			for(SortClause sort : Optional.ofNullable(listMedicalEnrollment).map(l -> l.getSorts()).orElse(Arrays.asList())) {
				sorts.append("&sort=").append(StringUtils.substringBefore(sort.getItem(), "_")).append(" ").append(sort.getOrder().name());
			}

			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), EnrollmentGenPage.ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), EnrollmentGenPage.ROLES)
					) {
					{ p.e("div").a("class", "").f();
						{ p.e("button").a("id", "refreshAllEnrollmentGenPage", id).a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-blue-gray ").a("onclick", "patchMedicalEnrollmentVals([], {}, function() { addGlow($('#refreshAllEnrollmentGenPage", id, "')); }, function() { addError($('#refreshAllEnrollmentGenPage", id, "')); }); ").f();
							p.e("i").a("class", "fas fa-sync-alt ").f().g("i");
							p.sx("refresh all the enrollments");
						} p.g("button");
					} p.g("div");
			}
			{ p.e("div").a("class", "w3-cell-row ").f();
				{ p.e("div").a("class", "w3-cell ").f();
					{ p.e("span").f();
						p.sx("search enrollments: ");
					} p.g("span");
				} p.g("div");
			} p.g("div");
			{ p.e("div").a("class", "w3-bar ").f();

				p.e("input")
					.a("type", "text")
					.a("class", "suggestMedicalEnrollment w3-input w3-border w3-bar-item ")
					.a("name", "suggestMedicalEnrollment")
					.a("id", "suggestMedicalEnrollment", id)
					.a("autocomplete", "off")
					.a("oninput", "suggestMedicalEnrollmentObjectSuggest( [ { 'name': 'q', 'value': 'objectSuggest:' + $(this).val() } ], $('#suggestListMedicalEnrollment", id, "'), ", p.getSiteRequest_().getRequestPk(), "); ")
					.a("onkeyup", "if (event.keyCode === 13) { event.preventDefault(); window.location.href = '/enrollment?q=", query1, ":' + encodeURIComponent(this.value) + '", fqs, sorts, "&start=", start2, "&rows=", rows1, "'; }"); 
				if(listMedicalEnrollment != null)
					p.a("value", query2);
				p.fg();
				{ p.e("button")
					.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-blue-gray ")
					.a("onclick", "window.location.href = '/enrollment?q=", query1, ":' + encodeURIComponent(this.previousElementSibling.value) + '", fqs, sorts, "&start=", start2, "&rows=", rows1, "'; ") 
					.f();
					p.e("i").a("class", "fas fa-search ").f().g("i");
				} p.g("button");

			} p.g("div");
			{ p.e("div").a("class", "w3-cell-row ").f();
				{ p.e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
					{ p.e("ul").a("class", "w3-ul w3-hoverable ").a("id", "suggestListMedicalEnrollment", id).f();
					} p.g("ul");
				} p.g("div");
			} p.g("div");
			{ p.e("div").a("class", "").f();
				{ p.e("a").a("href", "/enrollment").a("class", "").f();
					p.e("i").a("class", "fas fa-edit ").f().g("i");
					p.sx("see all the enrollments");
				} p.g("a");
			} p.g("div");
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

}
