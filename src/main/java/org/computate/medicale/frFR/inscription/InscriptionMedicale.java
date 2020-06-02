package org.computate.medicale.frFR.inscription;                    

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;

import org.computate.medicale.frFR.clinique.CliniqueMedicale;
import org.computate.medicale.frFR.cluster.Cluster;
import org.computate.medicale.frFR.couverture.Couverture;
import org.computate.medicale.frFR.page.MiseEnPage;
import org.computate.medicale.frFR.patient.PatientMedicale;
import org.computate.medicale.frFR.recherche.ListeRecherche;

/**    
 * NomCanonique.enUS: org.computate.medicale.enUS.enrollment.MedicalEnrollment
 * Modele: true
 * Api: true
 * Indexe: true
 * Sauvegarde: true
 * 
 * ApiTag.frFR: Inscription
 * ApiUri.frFR: /api/inscription
 * 
 * ApiTag.enUS: Enrollment
 * ApiUri.enUS: /api/enrollment
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
 * ApiUriRechercheAdmin.frFR: /api/admin/inscription
 * RoleUtilisateurRechercheAdmin.frFR: true
 * 
 * ApiMethode.enUS: AdminSearch
 * ApiUriAdminSearch.enUS: /api/admin/enrollment
 * RoleUtilisateurAdminSearch.enUS: true
 * 
 * ApiMethode.frFR: PATCHPaiements
 * ApiUriPATCHPayments.frFR: /inscription/paiements
 * 
 * ApiMethode.enUS: PATCHPayments
 * ApiUriPATCHPayments.enUS: /enrollment/payments
 * 
 * ApiMethode.frFR: PageRecherche
 * PagePageRecherche.frFR: InscriptionPage
 * PageSuperPageRecherche.frFR: ClusterPage
 * ApiUriPageRecherche.frFR: /inscription
 * 
 * ApiMethode.enUS: SearchPage
 * PageSearchPage.enUS: EnrollmentPage
 * PageSuperSearchPage.enUS: ClusterPage
 * ApiUriSearchPage.enUS: /enrollment
 * 
 * ApiMethode.frFR: RechargerPageRecherche
 * PageRechargerPageRecherche.frFR: PageInscription
 * PageSuperRechargerPageRecherche.frFR: ClusterPage
 * ApiUriRechargerPageRecherche.frFR: /recharger-inscription
 * RoleUtilisateurRechargerPageRecherche.frFR: true
 * 
 * ApiMethode.enUS: RefreshSearchPage
 * PageRefreshSearchPage.enUS: EnrollmentPage
 * PageSuperRefreshSearchPage.enUS: ClusterPage
 * ApiUriRefreshSearchPage.enUS: /refresh-enrollment
 * RoleUtilisateurRefreshSearchPage.enUS: true
 * 
 * UnNom.frFR: une inscription
 * UnNom.enUS: an enrollment
 * Couleur: blue-gray
 * IconeGroupe: solid
 * IconeNom: edit
 * 
 * Role.frFR: SiteAdmin
 * Role.enUS: SiteAdmin
 * RoleSession: true
 * RoleUtilisateur: true
*/      
public class InscriptionMedicale extends InscriptionMedicaleGen<Cluster> {       

	/**
	 * {@inheritDoc}
	 * Var.enUS: enrollmentKey
	 * Indexe: true
	 * Stocke: true
	 * Description.frFR: La clé primaire de l'inscription dans la base de données. 
	 * Description.enUS: The primary key of the clinic enrollment in the database. 
	 * NomAffichage.frFR: clé
	 * NomAffichage.enUS: key
	 */                              
	protected void _inscriptionCle(Couverture<Long> c) {
		c.o(pk);
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: clinicKey
	 * Indexe: true
	 * Stocke: true
	 * Attribuer: CliniqueMedicale.inscriptionCles
	 * Description.frFR: L'année medicale de la saison medicale. 
	 * Description.enUS: The clinic clinic of the clinic season. 
	 * NomAffichage.frFR: clinique
	 * NomAffichage.enUS: clinic
	*/            
	protected void _cliniqueCle(Couverture<Long> c) {
	}

	/**
	 * Var.enUS: clinicSearch
	 * r: cliniqueCle
	 * r.enUS: clinicKey
	 * r: CliniqueMedicale
	 * r.enUS: MedicalClinic
	 * r: setStocker
	 * r.enUS: setStore
	 * Ignorer: true
	 */ 
	protected void _cliniqueRecherche(ListeRecherche<CliniqueMedicale> l) {
		if(cliniqueCle != null) {
			l.setQuery("*:*");
			l.addFilterQuery("pk_indexed_long:" + cliniqueCle);
			l.setC(CliniqueMedicale.class);
			l.setStocker(true);
		}
		else {
			l.setQuery(null);
		}
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: clinic_
	 * r: cliniqueRecherche
	 * r.enUS: clinicSearch
	 * Ignorer: true
	 */   
	protected void _clinique_(Couverture<CliniqueMedicale> c) {
		if(cliniqueRecherche.size() > 0) {
			c.o(cliniqueRecherche.get(0));
		}
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: patientKey
	 * Indexe: true
	 * Stocke: true
	 * Attribuer: PatientMedicale.inscriptionCles
	 * HtmlLigne: 9
	 * HtmlCellule: 2
	 * Description.frFR: La clé primaire des patients dans la base de données. 
	 * Description.enUS: The primary key of the clinic patientren in the database. 
	 * NomAffichage.frFR: patients
	 * NomAffichage.enUS: patientren
	 */               
	protected void _patientCle(Couverture<Long> c) {}

	/**
	 * {@inheritDoc}
	 * Var.enUS: enrollmentFormKey
	 * Indexe: true
	 * Stocke: true
	 * Attribuer: FormInscription.partFormCles
	 * NomAffichage.frFR: formulaire d'inscription
	 * NomAffichage.enUS: enrollment form
	*/           
	protected void _formInscriptionCle(Couverture<Long> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: userKeys
	 * Indexe: true
	 * Stocke: true
	 * Attribuer: UtilisateurSite.inscriptionCles
	 * HtmlLigne: 12
	 * HtmlCellule: 1
	 * Description.frFR: La clé primaire des utilisateurs dans la base de données. 
	 * Description.enUS: The primary key of the users in the database. 
	 * NomAffichage.frFR: utilisateurs
	 * NomAffichage.enUS: users
	 * r: requeteSite
	 * r.enUS: siteRequest
	 * r: utilisateurCle
	 * r.enUS: userKey
	 */          
	protected void _utilisateurCles(List<Long> l) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: medicalSort
	 * Indexe: true
	 * Stocke: true
	 */
	protected void _medicaleTri(Couverture<Integer> c) {
		c.o(6);
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: clinicSort
	 * Indexe: true
	 * Stocke: true
	 */
	protected void _cliniqueTri(Couverture<Integer> c) {
		c.o(6);
	}

	/**
	 * Var.enUS: patientSearch
	 * r: inscriptionCles
	 * r.enUS: enrollmentKeys
	 * r: PatientMedicale
	 * r.enUS: MedicalPatient
	 * r: setStocker
	 * r.enUS: setStore
	 * r: patientCle
	 * r.enUS: patientKey
	 * Ignorer: true
	 */
	protected void _patientRecherche(ListeRecherche<PatientMedicale> l) {
		if(patientCle == null) {
			l.setQuery(null);
		}
		else {
			l.setQuery("*:*");
			l.addFilterQuery("pk_indexed_long:" + patientCle);
			l.setC(PatientMedicale.class);
			l.setStocker(true);
		}
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: patient_
	 * r: patientRecherche
	 * r.enUS: patientSearch
	 * Ignorer: true
	 */                               
	protected void _patient_(Couverture<PatientMedicale> c) {
		if(patientRecherche.size() > 0) {
			c.o(patientRecherche.get(0));
		}
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: patientFirstName
	 * Indexe: true
	 * Stocke: true
	 * r: patient_
	 * r.enUS: patient_
	 * r: PersonnePrenom
	 * r.enUS: PersonFirstName
	 */   
	protected void _patientPrenom(Couverture<String> c) {
		if(patient_ != null)
			c.o(patient_.getPersonnePrenom());
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: patientFirstNamePreferred
	 * Indexe: true
	 * Stocke: true
	 * r: patient_
	 * r.enUS: patient_
	 * r: PersonnePrenomPrefere
	 * r.enUS: PersonFirstNamePreferred
	 */   
	protected void _patientPrenomPrefere(Couverture<String> c) {
		if(patient_ != null)
			c.o(patient_.getPersonnePrenomPrefere());
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: patientFamilyName
	 * Indexe: true
	 * Stocke: true
	 * r: patient_
	 * r.enUS: patient_
	 * r: FamilleNom
	 * r.enUS: FamilyName
	 */   
	protected void _patientFamilleNom(Couverture<String> c) {
		if(patient_ != null)
			c.o(patient_.getFamilleNom());
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: patientCompleteName
	 * Indexe: true
	 * Stocke: true
	 * Definir: true
	 * Description.frFR: 
	 * Description.enUS: 
	 * NomAffichage.frFR: 
	 * NomAffichage.enUS: 
	 * r: PatientNomComplet
	 * r.enUS: PatientCompleteName
	 * r: patient_
	 * r.enUS: patient_
	 * r: PersonneNomComplet
	 * r.enUS: PersonCompleteName
	 */   
	protected void _patientNomComplet(Couverture<String> c) {
		if(patient_ != null)
			c.o(patient_.getPersonneNomComplet());
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: patientCompleteNamePreferred
	 * Indexe: true
	 * Stocke: true
	 * Definir: true
	 * Description.frFR: 
	 * Description.enUS: 
	 * NomAffichage.frFR: 
	 * NomAffichage.enUS: 
	 * r: patient_
	 * r.enUS: patient_
	 * r: PersonneNomCompletPrefere
	 * r.enUS: PersonCompleteNamePreferred
	 */  
	protected void _patientNomCompletPrefere(Couverture<String> c) {
		if(patient_ != null)
			c.o(patient_.getPersonneNomCompletPrefere());
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: patientBirthDate
	 * Indexe: true
	 * Stocke: true
	 * Definir: true
	 * Description.frFR: 
	 * Description.enUS: 
	 * NomAffichage.frFR: 
	 * NomAffichage.enUS: 
	 * r: patient_
	 * r.enUS: patient_
	 * r: PersonneDateNaissance
	 * r.enUS: PersonBirthDate
	 */     
	protected void _patientDateNaissance(Couverture<LocalDate> c) {
		if(patient_ != null)
			c.o(patient_.getPersonneDateNaissance());
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
	 * r: patientDateNaissance
	 * r.enUS: patientBirthDate
	 * r: Locale.FRANCE
	 * r.enUS: Locale.US
	 * r: "d MMM yyyy"
	 * r.enUS: "MMM d yyyy"
	 */
	@Override public String strPatientDateNaissance() { 
		return patientDateNaissance == null ? "" : patientDateNaissance.format(DateTimeFormatter.ofPattern("d MMM yyyy", Locale.FRANCE));
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: patientBirthMonth
	 * Indexe: true
	 * Stocke: true
	 * r: patientDateNaissance
	 * r.enUS: patientBirthDate
	 */    
	protected void _patientMoisNaissance(Couverture<Integer> c) {
		if(patientDateNaissance != null)
			c.o(patientDateNaissance.getMonthValue());
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: patientBirthDay
	 * Indexe: true
	 * Stocke: true
	 * r: patientDateNaissance
	 * r.enUS: patientBirthDate
	 */    
	protected void _patientJourNaissance(Couverture<Integer> c) {
		if(patientDateNaissance != null)
			c.o(patientDateNaissance.getMonthValue());
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: clinicName
	 * Indexe: true
	 * Stocke: true
	 * Description.frFR: 
	 * Description.enUS: 
	 * NomAffichage.frFR: 
	 * NomAffichage.enUS: 
	 * r: CliniqueNom
	 * r.enUS: ClinicName
	 * r: clinique
	 * r.enUS: clinic
	 */   
	protected void _cliniqueNom(Couverture<String> c) {
		if(clinique_ != null)
			c.o(clinique_.getCliniqueNom());
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: clinicCompleteName
	 * Indexe: true
	 * Stocke: true
	 * Description.frFR: 
	 * Description.enUS: 
	 * NomAffichage.frFR: 
	 * NomAffichage.enUS: 
	 * r: CliniqueNomComplet
	 * r.enUS: ClinicCompleteName
	 * r: clinique
	 * r.enUS: clinic
	 */   
	protected void _cliniqueNomComplet(Couverture<String> c) {
		if(clinique_ != null)
			c.o(clinique_.getCliniqueNomComplet());
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: clinicLocation
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.enUS: location
	 * NomAffichage.frFR: l'emplacement
	 * r: CliniqueEmplacement
	 * r.enUS: ClinicLocation
	 * r: clinique
	 * r.enUS: clinic
	 */           
	protected void _cliniqueEmplacement(Couverture<String> c) {
		if(clinique_ != null)
			c.o(clinique_.getCliniqueEmplacement());
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: clinicAddress
	 * Definir: true
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: addresse
	 * NomAffichage.enUS: address
	 * r: CliniqueAddresse
	 * r.enUS: ClinicAddress
	 * r: clinique
	 * r.enUS: clinic
	 */
	protected void _cliniqueAddresse(Couverture<String> c) {
		if(clinique_ != null)
			c.o(clinique_.getCliniqueAddresse());
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: clinicPhoneNumber
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: numéro de téléphone
	 * NomAffichage.enUS: phone number
	 * r: CliniqueNumeroTelephone
	 * r.enUS: ClinicPhoneNumber
	 * r: clinique
	 * r.enUS: clinic
	 */    
	protected void _cliniqueNumeroTelephone(Couverture<String> c) {
		if(clinique_ != null)
			c.o(clinique_.getCliniqueNumeroTelephone());
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: clinicAdministratorName
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.enUS: administrator of the clinic
	 * NomAffichage.frFR: administrateur de l'école
	 * r: CliniqueAdministrateurNom
	 * r.enUS: ClinicAdministratorName
	 * r: clinique
	 * r.enUS: clinic
	 */             
	protected void _cliniqueAdministrateurNom(Couverture<String> c) {
		if(clinique_ != null)
			c.o(clinique_.getCliniqueAdministrateurNom());
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: enrollmentApproved
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: approuvé
	 * NomAffichage.enUS: approved
	 * Definir: true
	 * HtmlLigne: 3
	 * HtmlCellule: 1
	 */                   
	protected void _inscriptionApprouve(Couverture<Boolean> c) {
		c.o(false);
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: enrollmentImmunizations
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: vacciné
	 * NomAffichage.enUS: immunized
	 * Definir: true
	 * HtmlLigne: 3
	 * HtmlCellule: 2
	 */                   
	protected void _inscriptionImmunisations(Couverture<Boolean> c) {
		c.o(false);
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: familyAddress
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: addresse de la famille
	 * NomAffichage.enUS: family address
	 * Multiligne: true
	 * Definir: true
	 * HtmlLigne: 6
	 * HtmlCellule: 1
	 */                   
	protected void _familleAddresse(Couverture<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: familyHowDoYouKnowTheClinic
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: comment vous connaissez l'école ? 
	 * NomAffichage.enUS: how do you know the clinic? 
	 * Multiligne: true
	 * Definir: true
	 * HtmlLigne: 7
	 * HtmlCellule: 3
	 */                   
	protected void _familleCommentVousConnaissezClinique(Couverture<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: enrollmentSpecialConsiderations
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: considérations spéciale
	 * NomAffichage.enUS: special considerations
	 * Multiligne: true
	 * Definir: true
	 * HtmlLigne: 6
	 * HtmlCellule: 2
	 */                   
	protected void _inscriptionConsiderationsSpeciales(Couverture<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: patientMedicalConditions
	 * Indexe: true
	 * Stocke: true
	 * Texte: true
	 * NomAffichage.frFR: conditions médicales
	 * NomAffichage.enUS: medical conditions
	 * Multiligne: true
	 * Definir: true
	 * HtmlLigne: 7
	 * HtmlCellule: 1
	 */                  
	protected void _patientConditionsMedicales(Couverture<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: patientPreviousClinicsAttended
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: écoles précedemment fréqentées
	 * NomAffichage.enUS: clinics previously attended
	 * Multiligne: true
	 * Definir: true
	 * HtmlLigne: 7
	 * HtmlCellule: 2
	 */                   
	protected void _patientCliniquesPrecedemmentFrequentees(Couverture<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: patientDescription
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: description
	 * NomAffichage.enUS: description
	 * Multiligne: true
	 * Definir: true
	 * HtmlLigne: 8
	 * HtmlCellule: 1
	 */                   
	protected void _patientDescription(Couverture<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: patientObjectives
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: objectifs
	 * NomAffichage.enUS: objectives
	 * Multiligne: true
	 * Definir: true
	 * HtmlLigne: 8
	 * HtmlCellule: 2
	 */                   
	protected void _patientObjectifs(Couverture<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: customerProfileId
	 * Indexe: true
	 * Stocke: true
	 * Definir: true
	 * HtmlLigne: 3
	 * HtmlCellule: 4
	 * NomAffichage.frFR: customer profile ID
	 * NomAffichage.enUS: customer profile ID
	 */               
	protected void _customerProfileId(Couverture<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: createdYear
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: crée l'année
	 * NomAffichage.enUS: created clinic
	 * r: cree
	 * r.enUS: created
	 * r: Locale.FRANCE
	 * r.enUS: Locale.US
	 */                       
	protected void _creeDAnnee(Couverture<Integer> c) {
		if(cree != null)
			c.o(cree.getYear());
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: createdDayOfWeek
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: crée jour de la semaine
	 * NomAffichage.enUS: created day of the week
	 * r: cree
	 * r.enUS: created
	 * r: Locale.FRANCE
	 * r.enUS: Locale.US
	 */                       
	protected void _creeJourDeSemaine(Couverture<String> c) {
		if(cree != null)
			c.o(cree.format(DateTimeFormatter.ofPattern("EEEE", Locale.FRANCE)));
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: createdMonthOfYear
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: crée mois de l'année
	 * NomAffichage.enUS: created month of the clinic
	 * r: cree
	 * r.enUS: created
	 * r: Locale.FRANCE
	 * r.enUS: Locale.US
	 */                       
	protected void _creeMoisDAnnee(Couverture<String> c) {
		if(cree != null)
			c.o(cree.format(DateTimeFormatter.ofPattern("MMMM", Locale.FRANCE)));
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: createdHourOfDay
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: heure du jour
	 * NomAffichage.enUS: hour of day
	 * r: cree
	 * r.enUS: created
	 * r: MiseEnPage
	 * r.enUS: PageLayout
	 * r: FORMATHeureAffichage
	 * r.enUS: FORMATTimeDisplay
	 */                       
	protected void _creeHeureDuJour(Couverture<String> c) {
		if(cree != null) {
			ZonedDateTime cree1 = cree.truncatedTo(ChronoUnit.HOURS);
			ZonedDateTime cree2 = cree1.plusHours(1);
			c.o(cree1.format(MiseEnPage.FORMATHeureAffichage) + "-" + cree2.format(MiseEnPage.FORMATHeureAffichage));
		}
	}

	/**       
	 * {@inheritDoc}
	 * Var.enUS: enrollmentSignature1
	 * Stocke: true
	 * Definir: true
	 * Signature: true
	 */     
	protected void _inscriptionSignature1(Couverture<String> c) {
	}

	/**       
	 * {@inheritDoc}
	 * Var.enUS: enrollmentSignature2
	 * Stocke: true
	 * Definir: true
	 * Signature: true
	 */     
	protected void _inscriptionSignature2(Couverture<String> c) {
	}

	/**       
	 * {@inheritDoc}
	 * Var.enUS: enrollmentSignature3
	 * Stocke: true
	 * Definir: true
	 * Signature: true
	 */     
	protected void _inscriptionSignature3(Couverture<String> c) {
	}

	/**       
	 * {@inheritDoc}
	 * Var.enUS: enrollmentSignature4
	 * Stocke: true
	 * Definir: true
	 * Signature: true
	 */     
	protected void _inscriptionSignature4(Couverture<String> c) {
	}

	/**       
	 * {@inheritDoc}
	 * Var.enUS: enrollmentSignature5
	 * Stocke: true
	 * Definir: true
	 * Signature: true
	 */     
	protected void _inscriptionSignature5(Couverture<String> c) {
	}

	/**       
	 * {@inheritDoc}
	 * Var.enUS: enrollmentSignature6
	 * Stocke: true
	 * Definir: true
	 * Signature: true
	 */     
	protected void _inscriptionSignature6(Couverture<String> c) {
	}

	/**       
	 * {@inheritDoc}
	 * Var.enUS: enrollmentSignature7
	 * Stocke: true
	 * Definir: true
	 * Signature: true
	 */     
	protected void _inscriptionSignature7(Couverture<String> c) {
	}

	/**       
	 * {@inheritDoc}
	 * Var.enUS: enrollmentSignature8
	 * Stocke: true
	 * Definir: true
	 * Signature: true
	 */     
	protected void _inscriptionSignature8(Couverture<String> c) {
	}

	/**       
	 * {@inheritDoc}
	 * Var.enUS: enrollmentSignature9
	 * Stocke: true
	 * Definir: true
	 * Signature: true
	 */     
	protected void _inscriptionSignature9(Couverture<String> c) {
	}

	/**       
	 * {@inheritDoc}
	 * Var.enUS: enrollmentSignature10
	 * Stocke: true
	 * Definir: true
	 * Signature: true
	 */     
	protected void _inscriptionSignature10(Couverture<String> c) {
	}

	/**       
	 * {@inheritDoc}
	 * Var.enUS: enrollmentDate1
	 * Indexe: true
	 * Stocke: true
	 * Definir: true
	 */
	protected void _inscriptionDate1(Couverture<LocalDate> c) {
	}

	/**       
	 * {@inheritDoc}
	 * Var.enUS: enrollmentDate2
	 * Indexe: true
	 * Stocke: true
	 * Definir: true
	 */
	protected void _inscriptionDate2(Couverture<LocalDate> c) {
	}

	/**       
	 * {@inheritDoc}
	 * Var.enUS: enrollmentDate3
	 * Indexe: true
	 * Stocke: true
	 * Definir: true
	 */
	protected void _inscriptionDate3(Couverture<LocalDate> c) {
	}

	/**       
	 * {@inheritDoc}
	 * Var.enUS: enrollmentDate4
	 * Indexe: true
	 * Stocke: true
	 * Definir: true
	 */
	protected void _inscriptionDate4(Couverture<LocalDate> c) {
	}

	/**       
	 * {@inheritDoc}
	 * Var.enUS: enrollmentDate5
	 * Indexe: true
	 * Stocke: true
	 * Definir: true
	 */
	protected void _inscriptionDate5(Couverture<LocalDate> c) {
	}

	/**       
	 * {@inheritDoc}
	 * Var.enUS: enrollmentDate6
	 * Indexe: true
	 * Stocke: true
	 * Definir: true
	 */
	protected void _inscriptionDate6(Couverture<LocalDate> c) {
	}

	/**       
	 * {@inheritDoc}
	 * Var.enUS: enrollmentDate7
	 * Indexe: true
	 * Stocke: true
	 * Definir: true
	 */
	protected void _inscriptionDate7(Couverture<LocalDate> c) {
	}

	/**       
	 * {@inheritDoc}
	 * Var.enUS: enrollmentDate8
	 * Indexe: true
	 * Stocke: true
	 * Definir: true
	 */
	protected void _inscriptionDate8(Couverture<LocalDate> c) {
	}

	/** 
	 * {@inheritDoc}
	 * Var.enUS: enrollmentDate9
	 * Indexe: true
	 * Stocke: true
	 * Definir: true
	 */
	protected void _inscriptionDate9(Couverture<LocalDate> c) {
	}

	/**       
	 * {@inheritDoc}
	 * Var.enUS: enrollmentDate10
	 * Indexe: true
	 * Stocke: true
	 * Definir: true
	 */
	protected void _inscriptionDate10(Couverture<LocalDate> c) {
	}

	/**
	 * Var.enUS: enrollmentEnrollments
	 */
	protected void _inscriptionsInscription(List<InscriptionMedicale> l) {}


	/**
	 * Var.enUS: enrollmentNumber
	 */
	protected void _inscriptionNumero(Couverture<Integer> c) {}

	/**           
	 * {@inheritDoc}
	 * Var.enUS: enrollmentCompleteName
	 * Indexe: true
	 * Stocke: true
	 * VarH2: true
	 * VarTitre: true
	 * NomAffichage.frFR: nom
	 * NomAffichage.enUS: name
	 * r: "inscription pour l'patient %s"
	 * r.enUS: "enrollment for the patient %s"
	 * r: "inscription %s"
	 * r.enUS: "enrollment %s"
	 * r: getPersonneNomCompletPrefere
	 * r.enUS: getPersonCompleteNamePreferred
	 * r: patient_
	 * r.enUS: patient_
	 */  
	protected void _inscriptionNomComplet(Couverture<String> c) {
		String o;
		if(patient_ != null)
			o = String.format("inscription pour le patient %s", patient_.getPersonneNomCompletPrefere());
		else
			o = String.format("inscription %s", pk);
		c.o(o);
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: _objectTitle
	 * r: inscriptionNomComplet
	 * r.enUS: enrollmentCompleteName
	 */
	@Override
	protected void _objetTitre(Couverture<String> c) {
		c.o(inscriptionNomComplet);
	}
}
