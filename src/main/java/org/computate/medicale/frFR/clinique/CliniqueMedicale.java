package org.computate.medicale.frFR.clinique;                                   

import java.util.List;

import org.computate.medicale.frFR.cluster.Cluster;
import org.computate.medicale.frFR.couverture.Couverture;



/**                
 * NomCanonique.enUS: org.computate.medicale.enUS.clinic.Clinic
 * Modele: true
 * Api: true
 * Indexe: true
 * Sauvegarde: true
 * 
 * ApiTag.frFR: Clinique
 * ApiUri.frFR: /api/clinique
 * 
 * ApiTag.enUS: Clinic
 * ApiUri.enUS: /api/clinic
 * 
 * ApiMethode: POST
 * ApiMethode: PATCH
 * ApiMethode: GET
 * ApiMethode.frFR: Recherche
 * ApiMethode.enUS: Search
 * 
 * ApiMethode.frFR: PUTImport
 * ApiMethode.frFR: PUTFusion
 * ApiMethode.frFR: PUTCopie
 * ApiMethode.enUS: PUTImport
 * ApiMethode.enUS: PUTMerge
 * ApiMethode.enUS: PUTCopy
 * 
 * ApiMethode.frFR: PageRecherche
 * PagePageRecherche.frFR: CliniquePage
 * PageSuperPageRecherche.frFR: ClusterPage
 * ApiUriPageRecherche.frFR: /clinique
 * 
 * ApiMethode.enUS: SearchPage
 * PageSearchPage.enUS: ClinicPage
 * PageSuperSearchPage.enUS: ClusterPage
 * ApiUriSearchPage.enUS: /clinic
 * 
 * UnNom.frFR: une école
 * UnNom.enUS: a clinic
 * Couleur: pink
 * IconeGroupe: regular
 * IconeNom: clinic
 * 
 * Role.frFR: SiteAdmin
 * Role.enUS: SiteAdmin
 */              
public class CliniqueMedicale extends CliniqueMedicaleGen<Cluster> {   

	/**
	 * {@inheritDoc}
	 * Var.enUS: clinicKey
	 * Indexe: true
	 * Stocke: true
	 * Description.frFR: La clé primaire de l'école dans la base de données. 
	 * Description.enUS: The primary key of the clinic in the database. 
	 * NomAffichage.frFR: clé
	 * NomAffichage.enUS: key
	 */         
	protected void _cliniqueCle(Couverture<Long> c) {
		c.o(pk);
	}
	
	/**
	 * {@inheritDoc}
	 * Var.enUS: yearKeys
	 * Indexe: true
	 * Stocke: true
	 * Attribuer: Anneemedicale.cliniqueCle
	 * HtmlLigne: 6
	 * HtmlCellule: 1
	 * Description.frFR: 
	 * Description.enUS: 
	 * NomAffichage.frFR: années
	 * NomAffichage.enUS: years
	 */   
	protected void _anneeCles(List<Long> o) {}
	
	/**
	 * {@inheritDoc}
	 * Var.enUS: seasonKeys
	 * Indexe: true
	 * Stocke: true
	 * Description.frFR: 
	 * Description.enUS: 
	 * NomAffichage.frFR: 
	 * NomAffichage.enUS: 
	 */        
	protected void _saisonCles(List<Long> o) {}
	
	/**
	 * {@inheritDoc}
	 * Var.enUS: sessionKeys
	 * Indexe: true
	 * Stocke: true
	 * Description.frFR: 
	 * Description.enUS: 
	 * NomAffichage.frFR: 
	 * NomAffichage.enUS: 
	 */
	protected void _sessionCles(List<Long> o) {}
	
	/**
	 * {@inheritDoc}
	 * Var.enUS: ageGroupKeys
	 * Indexe: true
	 * Stocke: true
	 * Description.frFR: 
	 * Description.enUS: 
	 * NomAffichage.frFR: 
	 * NomAffichage.enUS: 
	 */
	protected void _groupeAgeCles(List<Long> o) {}
	
	/**
	 * {@inheritDoc}
	 * Var.enUS: blockKeys
	 * Indexe: true
	 * Stocke: true
	 * Description.frFR: 
	 * Description.enUS: 
	 * NomAffichage.frFR: 
	 * NomAffichage.enUS: 
	 */
	protected void _blocCles(List<Long> o) {}
	
	/**
	 * {@inheritDoc}
	 * Var.enUS: childKeys
	 * Indexe: true
	 * Stocke: true
	 * Description.frFR: 
	 * Description.enUS: 
	 * NomAffichage.frFR: 
	 * NomAffichage.enUS: 
	 */
	protected void _enfantCles(List<Long> o) {}

	/**      
	 * {@inheritDoc}
	 * Var.enUS: educationSort
	 * Indexe: true
	 * Stocke: true
	 * Description.frFR: 
	 * Description.enUS: 
	 * NomAffichage.frFR: 
	 * NomAffichage.enUS: 
	 */ 
	protected void _medicaleTri(Couverture<Integer> c) {
		c.o(1);
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: clinicSort
	 * Indexe: true
	 * Stocke: true
	 * Description.frFR: 
	 * Description.enUS: 
	 * NomAffichage.frFR: 
	 * NomAffichage.enUS: 
	 */            
	protected void _cliniqueTri(Couverture<Integer> c) {
		c.o(1);
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: clinicName
	 * Definir: true
	 * Indexe: true
	 * Stocke: true
	 * HtmlLigne: 3
	 * HtmlCellule: 1
	 * NomAffichage.frFR: nom de l'école
	 * NomAffichage.enUS: name of the clinic
	 * Description.frFR: Nom de l'école. 
	 * Description.enUS: Name of the clinic. 
	 */   
	protected void _cliniqueNom(Couverture<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: clinicPhoneNumber
	 * Definir: true
	 * Indexe: true
	 * Stocke: true
	 * HtmlLigne: 5
	 * HtmlCellule: 1
	 * NomAffichage.frFR: numéro de téléphone
	 * NomAffichage.enUS: phone number
	 * Description.frFR: Numéro de téléphone de l'école. 
	 * Description.enUS: Telephone number of the clinic. 
	 */
	protected void _cliniqueNumeroTelephone(Couverture<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: clinicAdministratorName
	 * Definir: true
	 * Indexe: true
	 * Stocke: true
	 * HtmlLigne: 3
	 * HtmlCellule: 3
	 * NomAffichage.enUS: administrator of the clinic
	 * NomAffichage.frFR: administrateur de l'école
	 * Description.frFR: 
	 * Description.enUS: 
	 */  
	protected void _cliniqueAdministrateurNom(Couverture<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: clinicEmailFrom
	 * Definir: true
	 * Indexe: true
	 * Stocke: true
	 * HtmlLigne: 4
	 * HtmlCellule: 1
	 * NomAffichage.enUS: emails from (1 only)
	 * NomAffichage.frFR: mail de l'école de
	 */  
	protected void _cliniqueMailDe(Couverture<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: clinicEmailTo
	 * Definir: true
	 * Indexe: true
	 * Stocke: true
	 * HtmlLigne: 4
	 * HtmlCellule: 2
	 * NomAffichage.enUS: emails to (1 or more by ,)
	 * NomAffichage.frFR: mail de l'école à
	 */  
	protected void _cliniqueMailA(Couverture<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: clinicLocation
	 * Indexe: true
	 * Stocke: true
	 * Definir: true
	 * HtmlLigne: 3
	 * HtmlCellule: 2
	 * NomAffichage.enUS: location
	 * NomAffichage.frFR: l'emplacement
	 * Description.frFR: 
	 * Description.enUS: 
	 */             
	protected void _cliniqueEmplacement(Couverture<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: clinicAddress
	 * Definir: true
	 * Indexe: true
	 * Stocke: true
	 * HtmlLigne: 5
	 * HtmlCellule: 2
	 * Multiligne: true
	 * NomAffichage.frFR: addresse
	 * NomAffichage.enUS: address
	 * Description.frFR: 
	 * Description.enUS: 
	 */
	protected void _cliniqueAddresse(Couverture<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: clinicShortName
	 * Indexe: true
	 * Stocke: true
	 * Description.frFR: 
	 * Description.enUS: 
	 * NomAffichage.frFR: 
	 * NomAffichage.enUS: 
	 * r: cliniqueNom
	 * r.enUS: clinicName
	 * r: cliniqueEmplacement
	 * r.enUS: clinicLocation
	 */   
	protected void _cliniqueNomCourt(Couverture<String> c) {
		if(cliniqueEmplacement != null)
			c.o(cliniqueEmplacement);
		else 
			c.o(cliniqueNom);
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: clinicCompleteName
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: nom
	 * NomAffichage.enUS: name
	 * r: cliniqueNom
	 * r.enUS: clinicName
	 * VarH2: true
	 * VarTitre: true
	 * r: cliniqueEmplacement
	 * r.enUS: clinicLocation
	 * r: "%s à %s"
	 * r.enUS: "%s in %s"
	 */      
	protected void _cliniqueNomComplet(Couverture<String> c) {
		if(cliniqueEmplacement != null)
			c.o(String.format("%s à %s", cliniqueNom, cliniqueEmplacement));
		else 
			c.o(cliniqueNom);
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: _objectTitle
	 * r: cliniqueNomComplet
	 * r.enUS: clinicCompleteName
	 */
	@Override
	protected void _objetTitre(Couverture<String> c) {
		c.o(cliniqueNomComplet);
	}
}

