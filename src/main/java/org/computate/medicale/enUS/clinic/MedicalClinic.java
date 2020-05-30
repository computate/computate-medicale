package org.computate.medicale.enUS.clinic;

import java.util.List;
import org.computate.medicale.enUS.cluster.Cluster;
import org.computate.medicale.enUS.wrap.Wrap;

/**
 * Model: true
 * Api: true
 * Page: true
 * Saved: true
 * Color: pink
 * IconGroup: regular
 * IconName: clinic
 * Role.enUS: SiteAdmin
 * ApiUri.enUS: /api/clinic
 * ApiTag.enUS: Clinic
 * AName.enUS: a clinic
 * Role.frFR: SiteAdmin
 * ApiUri.frFR: /api/clinique
 * ApiTag.frFR: Clinique
 * AName.frFR: une école
 * CanonicalName: org.computate.medicale.frFR.clinique.CliniqueMedicale
 **/
public class MedicalClinic extends MedicalClinicGen<Cluster> {

	protected void _clinicKey(Wrap<Long> c) {
		c.o(pk);
	}

	protected void _yearKeys(List<Long> o) {}

	protected void _seasonKeys(List<Long> o) {}

	protected void _sessionKeys(List<Long> o) {}

	protected void _ageGroupKeys(List<Long> o) {}

	protected void _blockKeys(List<Long> o) {}

	protected void _childKeys(List<Long> o) {}

	protected void _educationSort(Wrap<Integer> c) {
		c.o(1);
	}

	protected void _clinicSort(Wrap<Integer> c) {
		c.o(1);
	}

	protected void _clinicName(Wrap<String> c) {
	}

	protected void _clinicPhoneNumber(Wrap<String> c) {
	}

	protected void _clinicAdministratorName(Wrap<String> c) {
	}

	protected void _clinicEmailFrom(Wrap<String> c) {
	}

	protected void _clinicEmailTo(Wrap<String> c) {
	}

	protected void _clinicLocation(Wrap<String> c) {
	}

	protected void _clinicAddress(Wrap<String> c) {
	}

	protected void _clinicShortName(Wrap<String> c) {
		if(clinicLocation != null)
			c.o(clinicLocation);
		else 
			c.o(clinicName);
	}

	protected void _clinicCompleteName(Wrap<String> c) {
		if(clinicLocation != null)
			c.o(String.format("%s in %s", clinicName, clinicLocation));
		else 
			c.o(clinicName);
	}

	@Override()
	protected void  _objectTitle(Wrap<String> c) {
		c.o(clinicCompleteName);
	}
}
