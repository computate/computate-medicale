package org.computate.medicale.enUS.design;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.computate.medicale.enUS.clinic.MedicalClinic;
import org.computate.medicale.enUS.context.SiteContextEnUS;
import org.computate.medicale.enUS.enrollment.MedicalEnrollment;
import org.computate.medicale.enUS.html.part.HtmlPart;
import org.computate.medicale.enUS.patient.MedicalPatient;
import org.computate.medicale.enUS.search.SearchList;
import org.computate.medicale.enUS.wrap.Wrap;
import org.computate.medicale.enUS.writer.AllWriter;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.resource.FSEntityResolver;
import org.xml.sax.SAXException;

import com.itextpdf.text.DocumentException;

import io.vertx.core.WorkerExecutor;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.mail.MailAttachment;
import io.vertx.ext.mail.MailClient;
import io.vertx.ext.mail.MailMessage;

/**
 * Translate: false
 **/
public class DesignEmailPage extends DesignEmailPageGen<DesignEmailGenPage> {

	/**
	 * {@inheritDoc}
	 * 
	 **/
	protected void _w1(Wrap<AllWriter> c) {
		c.o(siteRequest_.getW());
	}

	//////////
	// Page //
	//////////

	/**
	 * {@inheritDoc}
	 * 
	 **/
	protected void _wPage(Wrap<AllWriter> c) {
		AllWriter o = AllWriter.create(siteRequest_);
		c.o(o);
	}

	/**
	 * {@inheritDoc}
	 * 
	 **/
	protected void _pageDesign(Wrap<PageDesign> c) {
		if(listPageDesign.size() == 1)
			c.o(listPageDesign.get(0));
	}

	/**
	 * {@inheritDoc}
	 * 
	 **/
	protected void _pageDesignId(Wrap<String> c) {
		if(pageDesign != null)
			c.o(pageDesign.getObjectId());
		else
			throw new RuntimeException("No page design found. ");
	}

	/**
	 * {@inheritDoc}
	 * 
	 **/
	protected void _pageHtmlPartSearch(SearchList<HtmlPart> l) {
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

	protected void _pageHtmlPartList(Wrap<List<HtmlPart>> c) {
		if(pageHtmlPartSearch.size() > 0)
			c.o(pageHtmlPartSearch.getList());
	}

	///////////
	// Email //
	///////////

	/**
	 * {@inheritDoc}
	 * 
	 **/
	protected void _wEmail(Wrap<AllWriter> c) {
		AllWriter o = AllWriter.create(siteRequest_);
		c.o(o);
	}

	protected void _emailContentType(Wrap<String> c) {
		c.o("text/html");
	}

	/**
	 * {@inheritDoc}
	 * 
	 **/
	protected void _emailDesignId(Wrap<String> c) {
		if("enrollment-sent".equals(pageDesignId))
			c.o("email-enrollment");
		else
			c.o(siteRequest_.getRequestVars().get("emailDesignId"));
	}

	protected void _emailDesignSearch(SearchList<PageDesign> l) {
		if(emailDesignId != null) {
			l.setStore(true);
			l.setQuery("*:*");
			l.setC(PageDesign.class);
			l.setRows(1);
			l.addFilterQuery("objectId_indexed_string:" + ClientUtils.escapeQueryChars(emailDesignId));
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 **/
	protected void _emailDesign(Wrap<PageDesign> c) {
		if(emailDesignSearch.size() == 1)
			c.o(emailDesignSearch.get(0));
		else
			throw new RuntimeException("No email design found. ");
	}

	/**
	 * {@inheritDoc}
	 * 
	 **/
	protected void _emailHtmlPartSearch(SearchList<HtmlPart> l) {
		if(emailDesign != null) {
			l.setQuery("*:*");

			StringBuilder fq = new StringBuilder();
			fq.append("pageDesignKeys_indexed_longs:").append(emailDesign.getPk());
			for(Long k : emailDesign.getParentDesignKeys())
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

	protected void _emailHtmlPartList(Wrap<List<HtmlPart>> c) {
		if(emailHtmlPartSearch.size() > 0)
			c.o(emailHtmlPartSearch.getList());
	}

	////////////////
	// Attachment //
	////////////////

	/**
	 * {@inheritDoc}
	 * 
	 **/
	protected void _wAttachment(Wrap<AllWriter> c) {
		AllWriter o = AllWriter.create(siteRequest_);
		c.o(o);
	}

	protected void _attachmentContentType(Wrap<String> c) {
		c.o("application/pdf");
	}

	/**
	 * {@inheritDoc}
	 * 
	 **/
	protected void _attachmentDesignId(Wrap<String> c) {
		if("enrollment-sent".equals(pageDesignId))
			c.o("patient-registration-form");
		else
		c.o(siteRequest_.getRequestVars().get("attachmentDesignId"));
	}

	protected void _attachmentDesignSearch(SearchList<PageDesign> l) {
		if(attachmentDesignId != null) {
			l.setStore(true);
			l.setQuery("*:*");
			l.setC(PageDesign.class);
			l.setRows(1);
			l.addFilterQuery("objectId_indexed_string:" + ClientUtils.escapeQueryChars(attachmentDesignId));
	}
	}

	/**
	 * {@inheritDoc}
	 * 
	 **/
	protected void _attachmentDesign(Wrap<PageDesign> c) {
		if(attachmentDesignSearch.size() == 1)
			c.o(attachmentDesignSearch.get(0));
	}

	/**
	 * {@inheritDoc}
	 * 
	 **/
	protected void _attachmentHtmlPartSearch(SearchList<HtmlPart> l) {
		if(attachmentDesign != null) {
			l.setQuery("*:*");

			StringBuilder fq = new StringBuilder();
			fq.append("pageDesignKeys_indexed_longs:").append(attachmentDesign.getPk());
			for(Long k : attachmentDesign.getParentDesignKeys())
				fq.append(" OR pageDesignKeys_indexed_longs:").append(k);

			l.addFilterQuery(fq.toString());
			l.addFilterQuery("pdfExclude_indexed_boolean:false");
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

	protected void _attachmentHtmlPartList(Wrap<List<HtmlPart>> c) {
		if(attachmentHtmlPartSearch.size() > 0)
			c.o(attachmentHtmlPartSearch.getList());
	}

	/////////////////
	// Enrollments //
	/////////////////

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

		if("name-roster".equals(pageDesignId)) {
			l.addSort("patientCompleteNamePreferred_indexed_string", ORDER.asc);
		}
		else if("birthday-roster".equals(pageDesignId)) {
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
		if("patient-registration-form".equals(pageDesignId) && clinicKey != null) {
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
		if("patient-registration-form".equals(pageDesignId)) {
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
		else if(clinicSearch.size() == 1) {
			c.o(clinicSearch.get(0));
		}
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
	protected void _emailToClinic(Wrap<String> c) {
		if(clinic_ != null)
			c.o(clinic_.getClinicEmailTo());
	}

	/**
	 * {@inheritDoc}
	 * 
	 **/
	protected void _emailToAddress(Wrap<String> c) {
		if("enrollment-sent".equals(pageDesignId)) {
			StringBuilder b = new StringBuilder();
			b.append(clinic_.getClinicEmailTo());
			c.o(b.toString());
		}
		else {
			c.o(siteRequest_.getRequestVars().get("emailToAddress"));
		}
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

	/**
	 * {@inheritDoc}
	 * 
	 **/
	protected void _emailSubject(Wrap<String> c) {
		if("enrollment-sent".equals(pageDesignId)) {
			StringBuilder b = new StringBuilder();
			b.append("Patient registration form for ").append(patientEnrollment.getPatientCompleteNamePreferred());
			c.o(b.toString());
		}
		else {
			String format = siteRequest_.getRequestVars().get("emailSubject");
			if(format == null)
				throw new RuntimeException("The email subject field was blank. ");
			Matcher matcher = Pattern.compile("\\$\\{(\\w+)}", Pattern.MULTILINE).matcher(format);
			boolean found = matcher.find();
			StringBuffer sb = new StringBuffer();
			
			while(found) {
				String var = matcher.group(1);
				Object o = obtainDesignEmailPage(var);
				matcher.appendReplacement(sb, o == null ? "" : o.toString());
				found = matcher.find();
			}
			matcher.appendTail(sb);
			c.o(sb.toString());
		}
	}

	@Override public void htmlPageLayout() {

		SiteContextEnUS siteContext = siteRequest_.getSiteContext_();
		MailClient mailClient = siteContext.getMailClient();
		MailMessage message = new MailMessage();
		message.setFrom(emailFrom);
		if(StringUtils.isBlank(emailToAddress))
			throw new RuntimeException("The email to field was blank. ");
		if(StringUtils.isBlank(emailToClinic))
			throw new RuntimeException("The request was not matched to a clinic. ");
		ArrayList<String> tos = new ArrayList<>();
		tos.addAll(Arrays.asList(emailToClinic.trim().split("\\s*,\\s*")));
		tos.addAll(Arrays.asList(emailToAddress.trim().split("\\s*,\\s*")));
		message.setTo(tos);

		if(pageDesign != null) {
			siteRequest_.setW(wPage);
			setW(wPage);
			if(pageHtmlPartList != null) {
				htmlPageLayout2(pageContentType, pageHtmlPartList, null, 0, pageHtmlPartList.size());
			}
		}

		if(emailDesign != null) {
			siteRequest_.setW(wEmail);
			setW(wEmail);
			if(emailHtmlPartList != null) {
				htmlPageLayout2(emailContentType, emailHtmlPartList, null, 0, emailHtmlPartList.size());
			}
		}

		if(attachmentDesign != null) {
			siteRequest_.setW(wAttachment);
			setW(wAttachment);
			if(attachmentHtmlPartList != null) {
				htmlPageLayout2(attachmentContentType, attachmentHtmlPartList, null, 0, attachmentHtmlPartList.size());
			}
	
			try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
	//			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	//			renderer.setTimeouted(true);
	//			for(File fichier : FileUtils.listFiles(new File(siteConfig.docroot.chaîne() + "/ttf"), new String[] { "ttf" }, false)) {
	//				FontResolver resolver = renderer.getFontResolver();
	//				String chemin = fichier.getAbsolutePath();
	//				renderer.getFontResolver().addFont(chemin, true);
	//			}
		
				DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
				fac.setNamespaceAware(false);
				fac.setValidating(false);
				fac.setFeature("http://xml.org/sax/features/namespaces", false);
				fac.setFeature("http://xml.org/sax/features/validation", false);
				fac.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
				fac.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
	
				DocumentBuilder builder = fac.newDocumentBuilder();
				builder.setEntityResolver(FSEntityResolver.instance());
				Document doc = builder.parse(new ByteArrayInputStream(wAttachment.toString().getBytes()));
	
				if("application/pdf".equals(attachmentContentType)) {
					ITextRenderer renderer = new ITextRenderer();
					renderer.setDocument(doc, null);
					renderer.layout();
					renderer.createPDF(os);
					renderer.finishPDF();
					MailAttachment attachment = new MailAttachment();
					attachment.setContentType("application/pdf");
					attachment.setData(Buffer.buffer(os.toByteArray()));
					attachment.setName(patientEnrollment.getObjectId());
					message.setAttachment(attachment);
				}
			} catch (IOException | ParserConfigurationException | SAXException | DocumentException e) {
				ExceptionUtils.rethrow(e);
			}
		}

		message.setHtml(wEmail.toString());
//		message.setSubject(String.format("Enrollment of %s for the %s", patientEnrollment.getChildCompleteName(), patientEnrollment.getSeasonCompleteName()));
		message.setSubject(emailSubject);

		WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
		workerExecutor.executeBlocking(
			blockingCodeHandler -> {
				mailClient.sendMail(message, result -> {
					if (result.succeeded()) {
						System.out.println(result.result());
					} else {
						result.cause().printStackTrace();
					}
				});
			}, resultHandler -> {
			}
		);

		w1.getBuffer().appendString(wPage.toString());
	}
}
