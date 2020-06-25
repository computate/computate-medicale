package org.computate.medicale.enUS.design;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.computate.medicale.enUS.clinic.MedicalClinic;
import org.computate.medicale.enUS.enrollment.MedicalEnrollment;
import org.computate.medicale.enUS.html.part.HtmlPart;
import org.computate.medicale.enUS.patient.MedicalPatient;
import org.computate.medicale.enUS.search.SearchList;
import org.computate.medicale.enUS.wrap.Wrap;

/**
 **/
public class DesignDisplayPage extends DesignDisplayPageGen<DesignDisplayGenPage> {

	protected void _pageDesign(Wrap<PageDesign> c) {
		if(listPageDesign.size() == 1)
			c.o(listPageDesign.get(0));
	}

	/**
	 * {@inheritDoc}
	 * 
	 **/
	protected void _designId(Wrap<String> c) {
		if(pageDesign != null)
			c.o(pageDesign.getObjectId());
	}

	protected void _enrollmentSearch(SearchList<MedicalEnrollment> l) {
		l.setStore(true);
		l.setQuery("*:*");
		l.setC(MedicalEnrollment.class);
		l.setRows(1000);

		List<String> roles = Arrays.asList("SiteAdmin");
		if(
				!CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), roles)
				&& !CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), roles)
				) {
			l.addFilterQuery(
				"sessionId_indexed_string:" + ClientUtils.escapeQueryChars(Optional.ofNullable(siteRequest_.getSessionId()).orElse("-----"))
						+ " OR userKeys_indexed_longs:" + Optional.ofNullable(siteRequest_.getUserKey()).orElse(0L)
			);
		}

		if("name-roster".equals(designId)) {
			l.addSort("patientCompleteNamePreferred_indexed_string", ORDER.asc);
		}
		else if("birthday-roster".equals(designId)) {
			l.addSort("patientBirthMonth_indexed_int", ORDER.asc);
			l.addSort("patientBirthDay_indexed_int", ORDER.asc);
		}

		for(String var : siteRequest_.getRequestVars().keySet()) {
			String val = siteRequest_.getRequestVars().get(var);
			if(!"design".equals(var)) {
				String varIndexed = MedicalEnrollment.varIndexedMedicalEnrollment(var);
				if(varIndexed != null)
					l.addFilterQuery(varIndexed + ":" + ClientUtils.escapeQueryChars(val));
			}
		}
	}

	protected void _patientEnrollment(Wrap<MedicalEnrollment> c) {
		if(enrollmentSearch.size() == 1) {
			c.o(enrollmentSearch.get(0));
		}
		else {
			MedicalEnrollment o = new MedicalEnrollment();
			c.o(o);
			o.setPk(0L);
			o.setSiteRequest_(siteRequest_);
			MedicalPatient patient = new MedicalPatient();
			patient.setSiteRequest_(siteRequest_);
			o.setPatient_(patient);
		}
	}

	protected void _enrollments(Wrap<List<MedicalEnrollment>> c) {
		enrollments = enrollmentSearch.getList();
		c.o(enrollments);
	}

	protected void _enrollmentBlocks(List<MedicalEnrollment> c) {
	}

	protected void _enrollmentBlock(Wrap<MedicalEnrollment> c) {
	}

	protected void _enrollmentEnrollment(Wrap<MedicalEnrollment> c) {
	}

	protected void _clinicSearch(SearchList<MedicalClinic> l) {
		l.setStore(true);
		l.setQuery("*:*");
		l.setC(MedicalClinic.class);

		Long clinicKey = Optional.ofNullable(enrollmentSearch.first()).map(MedicalEnrollment::getClinicKey).orElse(null);
		if("patient-registration-form".equals(designId) && clinicKey != null) {
			l.addFilterQuery("pk_indexed_long:" + clinicKey);
		} else {
			for(String var : siteRequest_.getRequestVars().keySet()) {
				String val = siteRequest_.getRequestVars().get(var);
				if(!"design".equals(var)) {
					String varIndexed = MedicalClinic.varIndexedMedicalClinic(var);
					if(varIndexed != null)
						l.addFilterQuery(varIndexed + ":" + ClientUtils.escapeQueryChars(val));
				}
			}
		}
	}

	protected void _clinic_(Wrap<MedicalClinic> c) {
		if("patient-registration-form".equals(designId)) {
			if(clinicSearch.size() == 0) {
				throw new RuntimeException("No clinic was found for the query: " + siteRequest_.getOperationRequest().getParams().getJsonObject("query").encode());
			}
			else if(clinicSearch.size() == 1) {
				c.o(clinicSearch.get(0));
			}
			else  {
				throw new RuntimeException("More than one clinic was found for the query: " + siteRequest_.getOperationRequest().getParams().getJsonObject("query").encode());
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 **/
	protected void _emailFrom(Wrap<String> c) {
		if(clinic_ != null)
			c.o(clinic_.getClinicEmailFrom());
	}

	/**
	 * {@inheritDoc}
	 * 
	 **/
	protected void _emailToMedical(Wrap<String> c) {
		if(clinic_ != null)
			c.o(clinic_.getClinicEmailTo());
	}

	/**
	 * {@inheritDoc}
	 * 
	 **/
	protected void _emailToAddress(Wrap<String> c) {
		c.o(siteRequest_.getRequestVars().get("emailToAddress"));
	}

	/**
	 * {@inheritDoc}
	 * 
	 **/
	protected void _emailToName(Wrap<String> c) {
		c.o(siteRequest_.getRequestVars().get("emailToName"));
	}

	/**
	 * {@inheritDoc}
	 * 
	 **/
	protected void _emailMessage(Wrap<String> c) {
		c.o(siteRequest_.getRequestVars().get("emailMessage"));
	}

	protected void _clinicKey(Wrap<Long> c) {
		if(clinic_ != null)
			c.o(clinic_.getClinicKey());
	}

	protected void _clinicName(Wrap<String> c) {
		if(clinic_ != null)
			c.o(clinic_.getClinicName());
	}

	protected void _clinicCompleteName(Wrap<String> c) {
		if(clinic_ != null)
			c.o(clinic_.getClinicCompleteName());
	}

	protected void _clinicLocation(Wrap<String> c) {
		if(clinic_ != null)
			c.o((String)clinic_.getClinicLocation());
	}

	protected void _clinicAddress(Wrap<String> c) {
		if(clinic_ != null)
			c.o((String)clinic_.getClinicAddress());
	}

	protected void _clinicPhoneNumber(Wrap<String> c) {
		if(clinic_ != null)
			c.o((String)clinic_.getClinicPhoneNumber());
	}

	protected void _clinicAdministratorName(Wrap<String> c) {
		if(clinic_ != null)
			c.o((String)clinic_.getClinicAdministratorName());
	}

	protected void _seasonStartDate(Wrap<LocalDate> c) {}

	/**
	 * {@inheritDoc}
	 * 
	 **/
	protected void _htmlPartSearch(SearchList<HtmlPart> l) {
		if(pageDesign != null) {
			l.setQuery("*:*");

			StringBuilder fq = new StringBuilder();
			fq.append("pageDesignKeys_indexed_longs:").append(pageDesign.getPk());
			for(Long k : pageDesign.getParentDesignKeys())
				fq.append(" OR pageDesignKeys_indexed_longs:").append(k);

			l.addFilterQuery(fq.toString());
			l.setC(HtmlPart.class);
			l.setStore(true);
			l.addSort("sort1_indexed_double", ORDER.asc);
			l.addSort("sort2_indexed_double", ORDER.asc);
			l.addSort("sort3_indexed_double", ORDER.asc);
			l.addSort("sort4_indexed_double", ORDER.asc);
			l.addSort("sort5_indexed_double", ORDER.asc);
			l.addSort("sort6_indexed_double", ORDER.asc);
			l.addSort("sort7_indexed_double", ORDER.asc);
			l.addSort("sort8_indexed_double", ORDER.asc);
			l.addSort("sort9_indexed_double", ORDER.asc);
			l.addSort("sort10_indexed_double", ORDER.asc);
			l.setRows(100000);
		}
	}

	protected void _htmlPartList(Wrap<List<HtmlPart>> c) {
		if(htmlPartSearch.size() > 0)
			c.o(htmlPartSearch.getList());
	}

	@Override public void htmlPageLayout() {
		if(htmlPartList != null) {
			htmlPageLayout2(pageContentType, htmlPartList, null, 0, htmlPartList.size());
		}
	}
}
