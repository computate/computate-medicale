package org.computate.medicale.enUS.patient;

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
public class PatientGenPage extends PatientGenPageGen<ClusterPage> {

	public static final List<String> ROLES = Arrays.asList("SiteAdmin");
	public static final List<String> ROLE_READS = Arrays.asList("");

	/**
	 * {@inheritDoc}
	 * 
	 **/
	protected void _listMedicalPatient(Wrap<SearchList<MedicalPatient>> c) {
	}

	protected void _medicalPatient(Wrap<MedicalPatient> c) {
		if(listMedicalPatient != null && listMedicalPatient.size() == 1)
			c.o(listMedicalPatient.get(0));
	}

	@Override protected void _pageH1(Wrap<String> c) {
			c.o("patients");
	}

	@Override protected void _pageH2(Wrap<String> c) {
		if(medicalPatient != null && medicalPatient.getPatientCompleteName() != null)
			c.o(medicalPatient.getPatientCompleteName());
	}

	@Override protected void _pageH3(Wrap<String> c) {
		c.o("");
	}

	@Override protected void _pageTitle(Wrap<String> c) {
		if(medicalPatient != null && medicalPatient.getPatientCompleteName() != null)
			c.o(medicalPatient.getPatientCompleteName());
		else if(medicalPatient != null)
			c.o("");
		else if(listMedicalPatient == null || listMedicalPatient.size() == 0)
			c.o("no patient found");
	}

	@Override protected void _pageUri(Wrap<String> c) {
		c.o("/patient");
	}

	@Override protected void _pageImageUri(Wrap<String> c) {
			c.o("/png/patient-999.png");
	}

	@Override protected void _contextIconGroup(Wrap<String> c) {
			c.o("regular");
	}

	@Override protected void _contextIconName(Wrap<String> c) {
			c.o("hospital-user");
	}

	@Override public void initDeepPatientGenPage() {
		initPatientGenPage();
		super.initDeepPageLayout();
	}

	@Override public void htmlScriptsPatientGenPage() {
		e("script").a("src", staticBaseUrl, "/js/enUS/PatientPage.js").f().g("script");
		e("script").a("src", staticBaseUrl, "/js/enUS/EnrollmentPage.js").f().g("script");
	}

	@Override public void htmlScriptPatientGenPage() {
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
			tl(2, "suggestMedicalPatientEnrollmentKeys([{'name':'fq','value':'patientKey:' + pk}], $('#listMedicalPatientEnrollmentKeys_Page'), pk, true); ");
		} else {
			tl(2, "suggestMedicalPatientEnrollmentKeys([{'name':'fq','value':'patientKey:' + pk}], $('#listMedicalPatientEnrollmentKeys_Page'), pk, false); ");
		}
		tl(1, "}");
		tl(1, "websocketMedicalPatient(websocketMedicalPatientInner);");
		l("});");
	}

	public void htmlFormPageMedicalPatient(MedicalPatient o) {
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
			o.htmPatientFirstName("Page");
			o.htmFamilyName("Page");
			o.htmPatientFirstNamePreferred("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientBirthDate("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmEnrollmentKeys("Page");
		} g("div");
	}

	public void htmlFormPOSTMedicalPatient(MedicalPatient o) {
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
			o.htmPatientFirstName("POST");
			o.htmFamilyName("POST");
			o.htmPatientFirstNamePreferred("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientBirthDate("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmEnrollmentKeys("POST");
		} g("div");
	}

	public void htmlFormPUTImportMedicalPatient(MedicalPatient o) {
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

	public void htmlFormPUTMergeMedicalPatient(MedicalPatient o) {
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

	public void htmlFormPUTCopyMedicalPatient(MedicalPatient o) {
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmCreated("PUTCopy");
			o.htmModified("PUTCopy");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmArchived("PUTCopy");
			o.htmDeleted("PUTCopy");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientFirstName("PUTCopy");
			o.htmFamilyName("PUTCopy");
			o.htmPatientFirstNamePreferred("PUTCopy");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientBirthDate("PUTCopy");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmEnrollmentKeys("PUTCopy");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmInheritPk("PUTCopy");
			o.htmSessionId("PUTCopy");
			o.htmUserId("PUTCopy");
			o.htmUserKey("PUTCopy");
		} g("div");
	}

	public void htmlFormPATCHMedicalPatient(MedicalPatient o) {
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmCreated("PATCH");
			o.htmModified("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmArchived("PATCH");
			o.htmDeleted("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientFirstName("PATCH");
			o.htmFamilyName("PATCH");
			o.htmPatientFirstNamePreferred("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientBirthDate("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmEnrollmentKeys("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmInheritPk("PATCH");
			o.htmSessionId("PATCH");
			o.htmUserId("PATCH");
			o.htmUserKey("PATCH");
		} g("div");
	}

	public void htmlFormSearchMedicalPatient(MedicalPatient o) {
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
			o.htmPatientFirstName("Search");
			o.htmFamilyName("Search");
			o.htmPatientFirstNamePreferred("Search");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPatientBirthDate("Search");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmEnrollmentKeys("Search");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmInheritPk("Search");
			o.htmSessionId("Search");
			o.htmUserId("Search");
			o.htmUserKey("Search");
			o.htmObjectTitle("Search");
		} g("div");
	}

	@Override public void htmlBodyPatientGenPage() {

		OperationRequest operationRequest = siteRequest_.getOperationRequest();
		JsonObject params = operationRequest.getParams();
		if(listMedicalPatient == null || listMedicalPatient.size() == 0) {

			{ e("h1").f();
				{ e("a").a("href", "/patient").a("class", "w3-bar-item w3-btn w3-center w3-block w3-orange w3-hover-orange ").f();
					if(contextIconCssClasses != null)
						e("i").a("class", contextIconCssClasses + " site-menu-icon ").f().g("i");
					e("span").a("class", " ").f().sx("patients").g("span");
				} g("a");
			} g("h1");
			e("div").a("class", "w3-padding-16 w3-card-4 w3-light-grey ").f();
			{ e("h2").f();
				{ e("span").a("class", "w3-bar-item w3-padding w3-center w3-block w3-orange ").f();
					if(contextIconCssClasses != null)
						e("i").a("class", contextIconCssClasses + " site-menu-icon ").f().g("i");
					e("span").a("class", " ").f().sx("no patient found").g("span");
				} g("span");
			} g("h2");
		} else if(listMedicalPatient != null && listMedicalPatient.size() == 1 && params.getJsonObject("query").getString("q").equals("*:*")) {
			MedicalPatient o = listMedicalPatient.get(0);
			siteRequest_.setRequestPk(o.getPk());
			if(StringUtils.isNotEmpty(pageH1)) {
				{ e("h1").f();
					{ e("a").a("href", "/patient").a("class", "w3-bar-item w3-btn w3-center w3-block w3-orange w3-hover-orange ").f();
						if(contextIconCssClasses != null)
							e("i").a("class", contextIconCssClasses + " site-menu-icon ").f().g("i");
						e("span").a("class", " ").f().sx(pageH1).g("span");
					} g("a");
				} g("h1");
			}
			e("div").a("class", "w3-padding-16 w3-card-4 w3-light-grey ").f();
			if(StringUtils.isNotEmpty(pageH2)) {
				{ e("h2").f();
					{ e("span").a("class", "w3-bar-item w3-padding w3-center w3-block w3-orange ").f();
						e("span").a("class", " ").f().sx(pageH2).g("span");
					} g("span");
				} g("h2");
			}
			if(StringUtils.isNotEmpty(pageH3)) {
				{ e("h3").f();
					{ e("span").a("class", "w3-bar-item w3-padding w3-center w3-block w3-orange ").f();
						e("span").a("class", " ").f().sx(pageH3).g("span");
					} g("span");
				} g("h3");
			}
		} else {

			{ e("h1").f();
				{ e("a").a("href", "/patient").a("class", "w3-bar-item w3-btn w3-center w3-block w3-orange w3-hover-orange ").f();
					if(contextIconCssClasses != null)
						e("i").a("class", contextIconCssClasses + " site-menu-icon ").f().g("i");
					e("span").a("class", " ").f().sx(pageH1).g("span");
				} g("a");
			} g("h1");
			e("div").a("class", "").f();
				{ e("div").f();
					JsonObject queryParams = Optional.ofNullable(operationRequest).map(OperationRequest::getParams).map(or -> or.getJsonObject("query")).orElse(new JsonObject());
					Long num = listMedicalPatient.getQueryResponse().getResults().getNumFound();
					String q = "*:*";
					String query1 = "objectText";
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

					Integer rows1 = Optional.ofNullable(listMedicalPatient).map(l -> l.getRows()).orElse(10);
					Integer start1 = Optional.ofNullable(listMedicalPatient).map(l -> l.getStart()).orElse(1);
					Integer start2 = start1 - rows1;
					Integer start3 = start1 + rows1;
					Integer rows2 = rows1 / 2;
					Integer rows3 = rows1 * 2;
					start2 = start2 < 0 ? 0 : start2;
					StringBuilder fqs = new StringBuilder();
					for(String fq : Optional.ofNullable(listMedicalPatient).map(l -> l.getFilterQueries()).orElse(new String[0])) {
						if(!StringUtils.contains(fq, "(")) {
							String fq1 = StringUtils.substringBefore(fq, "_");
							String fq2 = StringUtils.substringAfter(fq, ":");
							if(!StringUtils.startsWithAny(fq, "classCanonicalNames_", "archived_", "deleted_", "sessionId", "userKeys"))
								fqs.append("&fq=").append(fq1).append(":").append(fq2);
						}
					}
					StringBuilder sorts = new StringBuilder();
					for(SortClause sort : Optional.ofNullable(listMedicalPatient).map(l -> l.getSorts()).orElse(Arrays.asList())) {
						sorts.append("&sort=").append(StringUtils.substringBefore(sort.getItem(), "_")).append(" ").append(sort.getOrder().name());
					}

					if(start1 == 0) {
						e("i").a("class", "fas fa-arrow-square-left w3-opacity ").f().g("i");
					} else {
						{ e("a").a("href", "/patient?q=", query, fqs, sorts, "&start=", start2, "&rows=", rows1).f();
							e("i").a("class", "fas fa-arrow-square-left ").f().g("i");
						} g("a");
					}

					if(rows1 <= 1) {
						e("i").a("class", "fas fa-minus-square w3-opacity ").f().g("i");
					} else {
						{ e("a").a("href", "/patient?q=", query, fqs, sorts, "&start=", start1, "&rows=", rows2).f();
							e("i").a("class", "fas fa-minus-square ").f().g("i");
						} g("a");
					}

					{ e("a").a("href", "/patient?q=", query, fqs, sorts, "&start=", start1, "&rows=", rows3).f();
						e("i").a("class", "fas fa-plus-square ").f().g("i");
					} g("a");

					if(start3 >= num) {
						e("i").a("class", "fas fa-arrow-square-right w3-opacity ").f().g("i");
					} else {
						{ e("a").a("href", "/patient?q=", query, fqs, sorts, "&start=", start3, "&rows=", rows1).f();
							e("i").a("class", "fas fa-arrow-square-right ").f().g("i");
						} g("a");
					}
						e("span").f().sx((start1 + 1), " - ", (start1 + rows1), " of ", num).g("span");
				} g("div");
				table1PatientGenPage();
		}

		if(listMedicalPatient != null && listMedicalPatient.size() == 1 && params.getJsonObject("query").getString("q").equals("*:*")) {
			MedicalPatient o = listMedicalPatient.first();

			{ e("div").a("class", "").f();

				if(o.getPk() != null) {
					{ e("form").a("action", "").a("id", "MedicalPatientForm").a("style", "display: inline-block; width: 100%; ").a("onsubmit", "event.preventDefault(); return false; ").f();
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
					htmlFormPageMedicalPatient(o);
				}

			} g("div");

		}
		htmlBodyFormsPatientGenPage();
		g("div");
	}

	public void table1PatientGenPage() {
		{ e("table").a("class", "w3-table w3-bordered w3-striped w3-border w3-hoverable ").f();
			table2PatientGenPage();
		} g("table");
	}

	public void table2PatientGenPage() {
		thead1PatientGenPage();
		tbody1PatientGenPage();
		tfoot1PatientGenPage();
	}

	public void thead1PatientGenPage() {
		{ e("thead").a("class", "w3-orange w3-hover-orange ").f();
			thead2PatientGenPage();
		} g("thead");
	}

	public void thead2PatientGenPage() {
			{ e("tr").f();
			if(getColumnCreated()) {
				e("th").f().sx("created").g("th");
			}
			if(getColumnObjectTitle()) {
				e("th").f().sx("").g("th");
			}
			} g("tr");
	}

	public void tbody1PatientGenPage() {
		{ e("tbody").f();
			tbody2PatientGenPage();
		} g("tbody");
	}

	public void tbody2PatientGenPage() {
		Map<String, Map<String, List<String>>> highlighting = listMedicalPatient.getQueryResponse().getHighlighting();
		for(int i = 0; i < listMedicalPatient.size(); i++) {
			MedicalPatient o = listMedicalPatient.getList().get(i);
			Map<String, List<String>> highlights = highlighting == null ? null : highlighting.get(o.getId());
			List<String> highlightList = highlights == null ? null : highlights.get(highlights.keySet().stream().findFirst().orElse(null));
			String uri = "/patient/" + o.getPk();
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
							e("i").a("class", "far fa-hospital-user ").f().g("i");
							{ e("span").f();
								sx(o.strObjectTitle());
							} g("span");
						} g("a");
					} g("td");
				}
			} g("tr");
		}
	}

	public void tfoot1PatientGenPage() {
		{ e("tfoot").a("class", "w3-orange w3-hover-orange ").f();
			tfoot2PatientGenPage();
		} g("tfoot");
	}

	public void tfoot2PatientGenPage() {
		{ e("tr").f();
			SimpleOrderedMap facets = (SimpleOrderedMap)Optional.ofNullable(listMedicalPatient.getQueryResponse()).map(QueryResponse::getResponse).map(r -> r.get("facets")).orElse(new SimpleOrderedMap());
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

	public void htmlBodyFormsPatientGenPage() {
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("div").a("class", "w3-margin-top ").f();

			if(listMedicalPatient != null && listMedicalPatient.size() == 1) {
				{ e("button")
					.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-orange ")
						.a("id", "refreshThisPatientGenPage")
						.a("onclick", "patchMedicalPatientVals( [ {name: 'fq', value: 'pk:' + " + siteRequest_.getRequestPk() + " } ], {}, function() { addGlow($('#refreshThisPatientGenPage')); }, function() { addError($('#refreshThisPatientGenPage')); }); return false; ").f();
						e("i").a("class", "fas fa-sync-alt ").f().g("i");
					sx("refresh this patient");
				} g("button");
			}

			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-orange ")
				.a("onclick", "$('#postMedicalPatientModal').show(); ")
				.f();
				e("i").a("class", "fas fa-file-plus ").f().g("i");
				sx("Create a patient");
			} g("button");
			{ e("div").a("id", "postMedicalPatientModal").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-orange ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#postMedicalPatientModal').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Create a patient").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").f();
							MedicalPatient o = new MedicalPatient();
							o.setSiteRequest_(siteRequest_);

							// Form POST
							{ e("div").a("id", "postMedicalPatientForm").f();
								htmlFormPOSTMedicalPatient(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-orange ")
								.a("onclick", "postMedicalPatient($('#postMedicalPatientForm')); ")
								.f().sx("Create a patient")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-orange ")
				.a("onclick", "$('#putimportMedicalPatientModal').show(); ")
				.f();
				e("i").a("class", "fas fa-file-import ").f().g("i");
				sx("Import patients");
			} g("button");
			{ e("div").a("id", "putimportMedicalPatientModal").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-orange ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#putimportMedicalPatientModal').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Import patients").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").f();
							MedicalPatient o = new MedicalPatient();
							o.setSiteRequest_(siteRequest_);

							// Form PUT
							{ e("div").a("id", "putimportMedicalPatientForm").f();
								htmlFormPUTImportMedicalPatient(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-orange ")
								.a("onclick", "putimportMedicalPatient($('#putimportMedicalPatientForm')); ")
								.f().sx("Import patients")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-orange ")
				.a("onclick", "$('#putmergeMedicalPatientModal').show(); ")
				.f();
				e("i").a("class", "fas fa-code-merge ").f().g("i");
				sx("Merge patients");
			} g("button");
			{ e("div").a("id", "putmergeMedicalPatientModal").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-orange ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#putmergeMedicalPatientModal').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Merge patients").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").f();
							MedicalPatient o = new MedicalPatient();
							o.setSiteRequest_(siteRequest_);

							// Form PUT
							{ e("div").a("id", "putmergeMedicalPatientForm").f();
								htmlFormPUTMergeMedicalPatient(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-orange ")
								.a("onclick", "putmergeMedicalPatient($('#putmergeMedicalPatientForm')); ")
								.f().sx("Merge patients")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-orange ")
				.a("onclick", "$('#putcopyMedicalPatientModal').show(); ")
				.f();
				e("i").a("class", "fas fa-copy ").f().g("i");
				sx("Duplicate patients");
			} g("button");
			{ e("div").a("id", "putcopyMedicalPatientModal").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-orange ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#putcopyMedicalPatientModal').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Duplicate patients").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").f();
							MedicalPatient o = new MedicalPatient();
							o.setSiteRequest_(siteRequest_);

							// Form PUT
							{ e("div").a("id", "putcopyMedicalPatientForm").f();
								htmlFormPUTCopyMedicalPatient(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-orange ")
								.a("onclick", "putcopyMedicalPatient($('#putcopyMedicalPatientForm'), ", medicalPatient == null ? "null" : medicalPatient.getPk(), "); ")
								.f().sx("Duplicate patients")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-orange ")
				.a("onclick", "$('#patchMedicalPatientModal').show(); ")
				.f();
				e("i").a("class", "fas fa-edit ").f().g("i");
				sx("Modify patients");
			} g("button");
			{ e("div").a("id", "patchMedicalPatientModal").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-orange ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#patchMedicalPatientModal').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Modify patients").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").f();
							MedicalPatient o = new MedicalPatient();
							o.setSiteRequest_(siteRequest_);

							// FormValues PATCH
							{ e("form").a("action", "").a("id", "patchMedicalPatientFormValues").a("onsubmit", "event.preventDefault(); return false; ").f();
								htmlFormPATCHMedicalPatient(o);
							} g("form");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-orange ")
								.a("onclick", "patchMedicalPatient(null, $('#patchMedicalPatientFormValues'), ", Optional.ofNullable(medicalPatient).map(MedicalPatient::getPk).map(a -> a.toString()).orElse("null"), ", function() {}, function() {}); ")
								.f().sx("Modify patients")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");

			g("div");
		}
		htmlSuggestedPatientGenPage(this, null, listMedicalPatient);
	}

	/**
	**/
	public static void htmlSuggestedPatientGenPage(PageLayout p, String id, SearchList<MedicalPatient> listMedicalPatient) {
		SiteRequestEnUS siteRequest_ = p.getSiteRequest_();
		try {
			OperationRequest operationRequest = siteRequest_.getOperationRequest();
			JsonObject queryParams = Optional.ofNullable(operationRequest).map(OperationRequest::getParams).map(or -> or.getJsonObject("query")).orElse(new JsonObject());
			String q = "*:*";
			String query1 = "objectText";
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

			Integer rows1 = Optional.ofNullable(listMedicalPatient).map(l -> l.getRows()).orElse(10);
			Integer start1 = Optional.ofNullable(listMedicalPatient).map(l -> l.getStart()).orElse(1);
			Integer start2 = start1 - rows1;
			Integer start3 = start1 + rows1;
			Integer rows2 = rows1 / 2;
			Integer rows3 = rows1 * 2;
			start2 = start2 < 0 ? 0 : start2;
			StringBuilder fqs = new StringBuilder();
			for(String fq : Optional.ofNullable(listMedicalPatient).map(l -> l.getFilterQueries()).orElse(new String[0])) {
				if(!StringUtils.contains(fq, "(")) {
					String fq1 = StringUtils.substringBefore(fq, "_");
					String fq2 = StringUtils.substringAfter(fq, ":");
					if(!StringUtils.startsWithAny(fq, "classCanonicalNames_", "archived_", "deleted_", "sessionId", "userKeys"))
						fqs.append("&fq=").append(fq1).append(":").append(fq2);
				}
			}
			StringBuilder sorts = new StringBuilder();
			for(SortClause sort : Optional.ofNullable(listMedicalPatient).map(l -> l.getSorts()).orElse(Arrays.asList())) {
				sorts.append("&sort=").append(StringUtils.substringBefore(sort.getItem(), "_")).append(" ").append(sort.getOrder().name());
			}

			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), PatientGenPage.ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), PatientGenPage.ROLES)
					) {
					{ p.e("div").a("class", "").f();
						{ p.e("button").a("id", "refreshAllPatientGenPage", id).a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-orange ").a("onclick", "patchMedicalPatientVals([], {}, function() { addGlow($('#refreshAllPatientGenPage", id, "')); }, function() { addError($('#refreshAllPatientGenPage", id, "')); }); ").f();
							p.e("i").a("class", "fas fa-sync-alt ").f().g("i");
							p.sx("refresh all the patients");
						} p.g("button");
					} p.g("div");
			}
			{ p.e("div").a("class", "w3-cell-row ").f();
				{ p.e("div").a("class", "w3-cell ").f();
					{ p.e("span").f();
						p.sx("search patients: ");
					} p.g("span");
				} p.g("div");
			} p.g("div");
			{ p.e("div").a("class", "w3-bar ").f();

				p.e("input")
					.a("type", "text")
					.a("class", "suggestMedicalPatient w3-input w3-border w3-bar-item ")
					.a("name", "suggestMedicalPatient")
					.a("id", "suggestMedicalPatient", id)
					.a("autocomplete", "off")
					.a("oninput", "suggestMedicalPatientObjectSuggest( [ { 'name': 'q', 'value': 'objectSuggest:' + $(this).val() } ], $('#suggestListMedicalPatient", id, "'), ", p.getSiteRequest_().getRequestPk(), "); ")
					.a("onkeyup", "if (event.keyCode === 13) { event.preventDefault(); window.location.href = '/patient?q=", query1, ":' + encodeURIComponent(this.value) + '", fqs, sorts, "&start=", start2, "&rows=", rows1, "'; }"); 
				if(listMedicalPatient != null)
					p.a("value", query2);
				p.fg();
				{ p.e("button")
					.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-orange ")
					.a("onclick", "window.location.href = '/patient?q=", query1, ":' + encodeURIComponent(this.previousElementSibling.value) + '", fqs, sorts, "&start=", start2, "&rows=", rows1, "'; ") 
					.f();
					p.e("i").a("class", "fas fa-search ").f().g("i");
				} p.g("button");

			} p.g("div");
			{ p.e("div").a("class", "w3-cell-row ").f();
				{ p.e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
					{ p.e("ul").a("class", "w3-ul w3-hoverable ").a("id", "suggestListMedicalPatient", id).f();
					} p.g("ul");
				} p.g("div");
			} p.g("div");
			{ p.e("div").a("class", "").f();
				{ p.e("a").a("href", "/patient").a("class", "").f();
					p.e("i").a("class", "far fa-hospital-user ").f().g("i");
					p.sx("see all the patients");
				} p.g("a");
			} p.g("div");
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

}
