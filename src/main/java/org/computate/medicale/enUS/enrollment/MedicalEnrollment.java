package org.computate.medicale.enUS.enrollment;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import org.computate.medicale.enUS.clinic.MedicalClinic;
import org.computate.medicale.enUS.cluster.Cluster;
import org.computate.medicale.enUS.wrap.Wrap;
import org.computate.medicale.enUS.page.PageLayout;
import org.computate.medicale.enUS.patient.MedicalPatient;
import org.computate.medicale.enUS.search.SearchList;

/**
 * Model: true
 * Api: true
 * Page: true
 * Saved: true
 * RoleSession: true
 * RoleUser: true
 * Color: blue-gray
 * IconGroup: solid
 * IconName: edit
 * Role.enUS: SiteAdmin
 * ApiUri.enUS: /api/enrollment
 * ApiTag.enUS: Enrollment
 * AName.enUS: an enrollment
 * Role.frFR: SiteAdmin
 * ApiUri.frFR: /api/inscription
 * ApiTag.frFR: Inscription
 * AName.frFR: une inscription
 * CanonicalName: org.computate.medicale.frFR.inscription.InscriptionMedicale
 **/
public class MedicalEnrollment extends MedicalEnrollmentGen<Cluster> {

	protected void _enrollmentKey(Wrap<Long> c) {
		c.o(pk);
	}

	protected void _clinicKey(Wrap<Long> c) {
	}

	protected void _clinicSearch(SearchList<MedicalClinic> l) {
		if(clinicKey != null) {
			l.setQuery("*:*");
			l.addFilterQuery("pk_indexed_long:" + clinicKey);
			l.setC(MedicalClinic.class);
			l.setStore(true);
		}
		else {
			l.setQuery(null);
		}
	}

	protected void _clinic_(Wrap<MedicalClinic> c) {
		if(clinicSearch.size() > 0) {
			c.o(clinicSearch.get(0));
		}
	}

	protected void _patientKey(Wrap<Long> c) {}

	protected void _enrollmentFormKey(Wrap<Long> c) {
	}

	protected void _userKeys(List<Long> l) {
	}

	protected void _medicalSort(Wrap<Integer> c) {
		c.o(6);
	}

	protected void _clinicSort(Wrap<Integer> c) {
		c.o(6);
	}

	protected void _patientSearch(SearchList<MedicalPatient> l) {
		if(patientKey == null) {
			l.setQuery(null);
		}
		else {
			l.setQuery("*:*");
			l.addFilterQuery("pk_indexed_long:" + patientKey);
			l.setC(MedicalPatient.class);
			l.setStore(true);
		}
	}

	protected void _patient_(Wrap<MedicalPatient> c) {
		if(patientSearch.size() > 0) {
			c.o(patientSearch.get(0));
		}
	}

	protected void _patientFirstName(Wrap<String> c) {
		if(patient_ != null)
			c.o(patient_.getPersonFirstName());
	}

	protected void _patientFirstNamePreferred(Wrap<String> c) {
		if(patient_ != null)
			c.o(patient_.getPersonFirstNamePreferred());
	}

	protected void _patientFamilyName(Wrap<String> c) {
		if(patient_ != null)
			c.o(patient_.getFamilyName());
	}

	protected void _patientCompleteName(Wrap<String> c) {
		if(patient_ != null)
			c.o(patient_.getPersonCompleteName());
	}

	protected void _patientCompleteNamePreferred(Wrap<String> c) {
		if(patient_ != null)
			c.o(patient_.getPersonCompleteNamePreferred());
	}

	protected void _patientBirthDate(Wrap<LocalDate> c) {
		if(patient_ != null)
			c.o(patient_.getPersonBirthDate());
	}

	protected void _patientBirthDateYear(Wrap<Integer> c) {
		if(patientBirthDate != null)
			c.o(patientBirthDate.getYear());
	}

	protected void _patientBirthDateMonthOfYear(Wrap<String> c) {
		if(patientBirthDate != null)
			c.o(patientBirthDate.format(DateTimeFormatter.ofPattern("MMMM", Locale.US)));
	}

	protected void _patientBirthDateDayOfWeek(Wrap<String> c) {
		if(patientBirthDate != null)
			c.o(patientBirthDate.format(DateTimeFormatter.ofPattern("EEEE", Locale.US)));
	}

	@Override()
	public String strPatientBirthDate() { 
		return patientBirthDate == null ? "" : patientBirthDate.format(DateTimeFormatter.ofPattern("MMM d yyyy", Locale.US));
	}

	protected void _patientBirthMonth(Wrap<Integer> c) {
		if(patientBirthDate != null)
			c.o(patientBirthDate.getMonthValue());
	}

	protected void _patientBirthDay(Wrap<Integer> c) {
		if(patientBirthDate != null)
			c.o(patientBirthDate.getMonthValue());
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
			c.o(clinic_.getClinicLocation());
	}

	protected void _clinicAddress(Wrap<String> c) {
		if(clinic_ != null)
			c.o(clinic_.getClinicAddress());
	}

	protected void _clinicPhoneNumber(Wrap<String> c) {
		if(clinic_ != null)
			c.o(clinic_.getClinicPhoneNumber());
	}

	protected void _clinicAdministratorName(Wrap<String> c) {
		if(clinic_ != null)
			c.o(clinic_.getClinicAdministratorName());
	}

	protected void _enrollmentApproved(Wrap<Boolean> c) {
		c.o(false);
	}

	protected void _enrollmentImmunizations(Wrap<Boolean> c) {
		c.o(false);
	}

	protected void _familyAddress(Wrap<String> c) {
	}

	protected void _familyHowDoYouKnowTheClinic(Wrap<String> c) {
	}

	protected void _enrollmentSpecialConsiderations(Wrap<String> c) {
	}

	protected void _patientMedicalConditions(Wrap<String> c) {
	}

	protected void _patientPreviousClinicsAttended(Wrap<String> c) {
	}

	protected void _patientDescription(Wrap<String> c) {
	}

	protected void _patientObjectives(Wrap<String> c) {
	}

	protected void _customerProfileId(Wrap<String> c) {
	}

	protected void _createdYear(Wrap<Integer> c) {
		if(created != null)
			c.o(created.getYear());
	}

	protected void _createdDayOfWeek(Wrap<String> c) {
		if(created != null)
			c.o(created.format(DateTimeFormatter.ofPattern("EEEE", Locale.US)));
	}

	protected void _createdMonthOfYear(Wrap<String> c) {
		if(created != null)
			c.o(created.format(DateTimeFormatter.ofPattern("MMMM", Locale.US)));
	}

	protected void _createdHourOfDay(Wrap<String> c) {
		if(created != null) {
			ZonedDateTime created1 = created.truncatedTo(ChronoUnit.HOURS);
			ZonedDateTime created2 = created1.plusHours(1);
			c.o(created1.format(PageLayout.FORMATTimeDisplay) + "-" + created2.format(PageLayout.FORMATTimeDisplay));
		}
	}

	protected void _enrollmentSignature1(Wrap<String> c) {
	}

	protected void _enrollmentSignature2(Wrap<String> c) {
	}

	protected void _enrollmentSignature3(Wrap<String> c) {
	}

	protected void _enrollmentSignature4(Wrap<String> c) {
	}

	protected void _enrollmentSignature5(Wrap<String> c) {
	}

	protected void _enrollmentSignature6(Wrap<String> c) {
	}

	protected void _enrollmentSignature7(Wrap<String> c) {
	}

	protected void _enrollmentSignature8(Wrap<String> c) {
	}

	protected void _enrollmentSignature9(Wrap<String> c) {
	}

	protected void _enrollmentSignature10(Wrap<String> c) {
	}

	protected void _enrollmentDate1(Wrap<LocalDate> c) {
	}

	protected void _enrollmentDate2(Wrap<LocalDate> c) {
	}

	protected void _enrollmentDate3(Wrap<LocalDate> c) {
	}

	protected void _enrollmentDate4(Wrap<LocalDate> c) {
	}

	protected void _enrollmentDate5(Wrap<LocalDate> c) {
	}

	protected void _enrollmentDate6(Wrap<LocalDate> c) {
	}

	protected void _enrollmentDate7(Wrap<LocalDate> c) {
	}

	protected void _enrollmentDate8(Wrap<LocalDate> c) {
	}

	protected void _enrollmentDate9(Wrap<LocalDate> c) {
	}

	protected void _enrollmentDate10(Wrap<LocalDate> c) {
	}

	protected void _enrollmentEnrollments(List<MedicalEnrollment> l) {}

	protected void _enrollmentNumber(Wrap<Integer> c) {}

	protected void _enrollmentCompleteName(Wrap<String> c) {
		String o;
		if(patient_ != null)
			o = String.format("inscription pour le patient %s", patient_.getPersonCompleteNamePreferred());
		else
			o = String.format("enrollment %s", pk);
		c.o(o);
	}

	@Override()
	protected void  _objectTitle(Wrap<String> c) {
		c.o(enrollmentCompleteName);
	}
}
