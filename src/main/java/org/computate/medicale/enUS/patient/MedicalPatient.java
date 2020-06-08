package org.computate.medicale.enUS.patient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import org.computate.medicale.enUS.cluster.Cluster;
import org.computate.medicale.enUS.wrap.Wrap;
import org.computate.medicale.enUS.enrollment.MedicalEnrollment;
import org.computate.medicale.enUS.search.SearchList;

/**
 * Model: true
 * Api: true
 * Page: true
 * Saved: true
 * RoleSession: true
 * RoleUser: true
 * Color: orange
 * IconGroup: regular
 * IconName: hospital-user
 * Role.enUS: SiteAdmin
 * ApiUri.enUS: /api/patient
 * ApiTag.enUS: Child
 * AName.enUS: a patient
 * Role.frFR: SiteAdmin
 * ApiUri.frFR: /api/patient
 * ApiTag.frFR: Enfant
 * AName.frFR: un patient
 * CanonicalName: org.computate.medicale.frFR.patient.PatientMedicale
 **/
public class MedicalPatient extends MedicalPatientGen<Cluster> {

	protected void _patientKey(Wrap<Long> c) {
		c.o(pk);
	}

	protected void _enrollmentKeys(List<Long> o) {}

	protected void _schoolSort(Wrap<Integer> c) {
		c.o(1);
	}

	protected void _enrollmentSearch(SearchList<MedicalEnrollment> l) { 
		l.setQuery("*:*");
		l.addFilterQuery("patientKey_indexed_long:" + pk);
		l.setC(MedicalEnrollment.class);
		l.setStore(true);
		l.setFacet(true);
		l.addFacetField("clinicKey_indexed_long");
		l.addFacetField("userKeys_indexed_longs");
	}

	protected void _enrollments(List<MedicalEnrollment> l) {
		l.addAll(enrollmentSearch.getList());
	}

	protected void _userKeys(List<Long> l) {
		l.addAll(enrollmentSearch.getQueryResponse().getFacetField("userKeys_indexed_longs").getValues().stream().map(o -> Long.parseLong(o.getName())).collect(Collectors.toList()));
	}

	protected void _clinicKeys(List<Long> l) {
		l.addAll(enrollmentSearch.getQueryResponse().getFacetField("clinicKey_indexed_long").getValues().stream().map(o -> Long.parseLong(o.getName())).collect(Collectors.toList()));
	}

	protected void _patientFirstName(Wrap<String> c) {
	}

	protected void _patientFirstNamePreferred(Wrap<String> c) {
		c.o(patientFirstName);
	}

	protected void _familyName(Wrap<String> c) {
	}

	protected void _patientCompleteName(Wrap<String> c) {
		if(patientFirstNamePreferred != null && familyName != null)
			c.o(String.format("%s %s", patientFirstNamePreferred, familyName));
		else if(patientFirstNamePreferred != null)
			c.o(patientFirstNamePreferred);
		else if(familyName != null)
			c.o(familyName);
	}

	protected void _patientCompleteNamePreferred(Wrap<String> c) {
		c.o(patientCompleteName);
	}

	protected void _patientFormalName(Wrap<String> c) {
		c.o(String.format("%s %s", patientFirstName, familyName));
	}

	protected void _patientBirthDate(Wrap<LocalDate> c) {
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
		return patientBirthDate == null ? "" : patientBirthDate.format(DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.US));
	}

	@Override()
	protected void  _objectTitle(Wrap<String> c) {
		c.o(patientCompleteName);
	}
}
