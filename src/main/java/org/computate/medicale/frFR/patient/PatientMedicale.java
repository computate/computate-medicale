package org.computate.medicale.frFR.patient;                                  

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.computate.medicale.frFR.cluster.Cluster;
import org.computate.medicale.frFR.couverture.Couverture;
import org.computate.medicale.frFR.inscription.InscriptionMedicale;
import org.computate.medicale.frFR.recherche.ListeRecherche;

/**    
 * NomCanonique.enUS: org.computate.medicale.enUS.patient.MedicalPatient
 * Modele: true
 * Api: true
 * Indexe: true
 * Sauvegarde: true
 * 
 * ApiTag.frFR: Enfant
 * ApiUri.frFR: /api/patient
 * 
 * ApiTag.enUS: Child
 * ApiUri.enUS: /api/patient
 * 
 * ApiMethode: POST
 * 
 * ApiMethode.frFR: PUTImport
 * RoleUtilisateurPUTImport.frFR: true
 * ApiMethode.frFR: PUTFusion
 * ApiMethode.frFR: PUTCopie
 * ApiMethode.enUS: PUTImport
 * RoleUtilisateurPUTImport.enUS: true
 * ApiMethode.enUS: PUTMerge
 * ApiMethode.enUS: PUTCopy

 * ApiMethode: PATCH
 * ApiMethode: GET
 * ApiMethode.frFR: Recherche
 * ApiMethode.enUS: Search
 * 
 * ApiMethode.frFR: RechercheAdmin
 * ApiUriRechercheAdmin.frFR: /api/admin/patient
 * RoleUtilisateurRechercheAdmin.frFR: true
 * 
 * ApiMethode.enUS: AdminSearch
 * ApiUriAdminSearch.enUS: /api/admin/patient
 * RoleUtilisateurAdminSearch.enUS: true
 * 
 * ApiMethode.frFR: PageRecherche
 * PagePageRecherche.frFR: EnfantPage
 * PageSuperPageRecherche.frFR: ClusterPage
 * ApiUriPageRecherche.frFR: /patient
 * 
 * ApiMethode.enUS: SearchPage
 * PageSearchPage.enUS: ChildPage
 * PageSuperSearchPage.enUS: ClusterPage
 * ApiUriSearchPage.enUS: /patient
 * 
 * UnNom.frFR: un patient
 * UnNom.enUS: a patient
 * Couleur: orange
 * IconeGroupe: regular
 * IconeNom: hospital-user
 * 
 * Role.frFR: SiteAdmin
 * Role.enUS: SiteAdmin
 * RoleSession: true
 * RoleUtilisateur: true
*/   
public class PatientMedicale extends PatientMedicaleGen<Cluster> {

	/**
	 * {@inheritDoc}
	 * Var.enUS: patientKey
	 * Indexe: true
	 * Stocke: true
	 * Description.frFR: La clé primaire du inscription dans la base de données. 
	 * Description.enUS: The primary key of the school enrollment in the database. 
	 * NomAffichage.frFR: clé
	 * NomAffichage.enUS: key
	 */               
	protected void _patientCle(Couverture<Long> c) {
		c.o(pk);
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: enrollmentKeys
	 * Indexe: true
	 * Stocke: true
	 * Attribuer: InscriptionMedicale.patientCle
	 * HtmlLigne: 8
	 * HtmlCellule: 1
	 * NomAffichage.frFR: inscriptions
	 * NomAffichage.enUS: enrollments
	 */              
	protected void _inscriptionCles(List<Long> o) {}

	/**
	 * {@inheritDoc}
	 * Var.enUS: schoolSort
	 * Indexe: true
	 * Stocke: true
	 */
	protected void _patientTri(Couverture<Integer> c) {
		c.o(1);
	}

	/**
	 * Var.enUS: enrollmentSearch
	 * r: patientCle
	 * r.enUS: patientKey
	 * r: InscriptionMedicale
	 * r.enUS: MedicalEnrollment
	 * r: setStocker
	 * r.enUS: setStore
	 * Ignorer: true
	 * r: cliniqueCle
	 * r.enUS: clinicKey
	 * r: utilisateurCles
	 * r.enUS: userKeys
	 */
	protected void _inscriptionRecherche(ListeRecherche<InscriptionMedicale> l) { 
		l.setQuery("*:*");
		l.addFilterQuery("patientCle_indexed_long:" + pk);
		l.setC(InscriptionMedicale.class);
		l.setStocker(true);
		l.setFacet(true);
		l.addFacetField("cliniqueCle_indexed_long");
		l.addFacetField("utilisateurCles_indexed_longs");
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: enrollments
	 * r: inscriptionRecherche
	 * r.enUS: enrollmentSearch
	 * r: inscriptions
	 * r.enUS: enrollments
	 * Ignorer: true
	 */ 
	protected void _inscriptions(List<InscriptionMedicale> l) {
		l.addAll(inscriptionRecherche.getList());
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: userKeys
	 * Indexe: true
	 * Stocke: true
	 * Description.frFR: La clé primaire des utlisateurs dans la base de données. 
	 * Description.enUS: The primary key of the users in the database. 
	 * r: utilisateurCles
	 * r.enUS: userKeys
	 * r: inscriptionRecherche
	 * r.enUS: enrollmentSearch
	 */                  
	protected void _utilisateurCles(List<Long> l) {
		l.addAll(inscriptionRecherche.getQueryResponse().getFacetField("utilisateurCles_indexed_longs").getValues().stream().map(o -> Long.parseLong(o.getName())).collect(Collectors.toList()));
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: clinicKeys
	 * Indexe: true
	 * Stocke: true
	 * Description.frFR: La clé primaire de l'école dans la base de données. 
	 * Description.enUS: The primary key of the school in the database. 
	 * NomAffichage.frFR: écoles
	 * NomAffichage.enUS: schools
	 * r: cliniqueCle
	 * r.enUS: clinicKey
	 * r: inscriptionRecherche
	 * r.enUS: enrollmentSearch
	 */                  
	protected void _cliniqueCles(List<Long> l) {
		l.addAll(inscriptionRecherche.getQueryResponse().getFacetField("cliniqueCle_indexed_long").getValues().stream().map(o -> Long.parseLong(o.getName())).collect(Collectors.toList()));
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: patientFirstName
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: prénom
	 * NomAffichage.enUS: first name
	 * Definir: true
	 * HtmlLigne: 3
	 * HtmlCellule: 1
	 */                   
	protected void _patientPrenom(Couverture<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: patientFirstNamePreferred
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: prénom préferé
	 * NomAffichage.enUS: preferred first name
	 * Definir: true
	 * HtmlLigne: 3
	 * HtmlCellule: 3
	 * r: patientPrenom
	 * r.enUS: patientFirstName
	 */                   
	protected void _patientPrenomPrefere(Couverture<String> c) {
		c.o(patientPrenom);
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: familyName
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: nom de famille
	 * NomAffichage.enUS: last name
	 * Definir: true
	 * HtmlLigne: 3
	 * HtmlCellule: 2
	 */                   
	protected void _familleNom(Couverture<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: patientCompleteName
	 * Indexe: true
	 * Stocke: true
	 * VarH2: true
	 * VarTitre: true
	 * NomAffichage.frFR: nom complèt
	 * NomAffichage.enUS: complete name
	 * r: patientPrenomPrefere
	 * r.enUS: patientFirstNamePreferred
	 * r: familleNom
	 * r.enUS: familyName
	 */                   
	protected void _patientNomComplet(Couverture<String> c) {
		if(patientPrenomPrefere != null && familleNom != null)
			c.o(String.format("%s %s", patientPrenomPrefere, familleNom));
		else if(patientPrenomPrefere != null)
			c.o(patientPrenomPrefere);
		else if(familleNom != null)
			c.o(familleNom);
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: patientCompleteNamePreferred
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: nom complèt préferé
	 * NomAffichage.enUS: complete name preferred
	 * r: patientNomComplet
	 * r.enUS: patientCompleteName
	 */                   
	protected void _patientNomCompletPrefere(Couverture<String> c) {
		c.o(patientNomComplet);
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: patientFormalName
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: nom formel
	 * NomAffichage.enUS: formal name
	 * r: patientPrenom
	 * r.enUS: patientFirstName
	 * r: familleNom
	 * r.enUS: familyName
	 */                   
	protected void _patientNomFormel(Couverture<String> c) {
		c.o(String.format("%s %s", patientPrenom, familleNom));
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: patientBirthDate
	 * Indexe: true
	 * Stocke: true
	 * Definir: true
	 * HtmlLigne: 4
	 * HtmlCellule: 1
	 * NomAffichage.frFR: date de naissance
	 * NomAffichage.enUS: birth date
	 */                   
	protected void _patientDateNaissance(Couverture<LocalDate> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: patientBirthDateYear
	 * Indexe: true
	 * Stocke: true
	 * r: patientDateNaissance
	 * r.enUS: patientBirthDate
	 * r: Locale.FRANCE
	 * r.enUS: Locale.US
	 */                       
	protected void _patientDateNaissanceDAnnee(Couverture<Integer> c) {
		if(patientDateNaissance != null)
			c.o(patientDateNaissance.getYear());
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: patientBirthDateMonthOfYear
	 * Indexe: true
	 * Stocke: true
	 * r: patientDateNaissance
	 * r.enUS: patientBirthDate
	 * r: Locale.FRANCE
	 * r.enUS: Locale.US
	 */                       
	protected void _patientDateNaissanceMoisDAnnee(Couverture<String> c) {
		if(patientDateNaissance != null)
			c.o(patientDateNaissance.format(DateTimeFormatter.ofPattern("MMMM", Locale.FRANCE)));
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: patientBirthDateDayOfWeek
	 * Indexe: true
	 * Stocke: true
	 * r: patientDateNaissance
	 * r.enUS: patientBirthDate
	 * r: Locale.FRANCE
	 * r.enUS: Locale.US
	 */                       
	protected void _patientDateNaissanceJourDeSemaine(Couverture<String> c) {
		if(patientDateNaissance != null)
			c.o(patientDateNaissance.format(DateTimeFormatter.ofPattern("EEEE", Locale.FRANCE)));
	}

	/**
	 * Var.enUS: strPatientBirthDate
	 * r: "d MMMM yyyy"
	 * r.enUS: "MMMM d, yyyy"
	 * r: FRANCE
	 * r.enUS: US
	 * r: patientDateNaissance
	 * r.enUS: patientBirthDate
	 */
	@Override public String strPatientDateNaissance() {
		return patientDateNaissance == null ? "" : patientDateNaissance.format(DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.FRANCE));
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: _objectTitle
	 * r: patientNomComplet
	 * r.enUS: patientCompleteName
	 */ 
	@Override
	protected void _objetTitre(Couverture<String> c) {
		c.o(patientNomComplet);
	}
}
