package gesimmo.nekaso.service;



import gesimmo.nekaso.entity.ContratBail;
import gesimmo.nekaso.entity.Gestionnaire;
import gesimmo.nekaso.entity.Locataire;
import gesimmo.nekaso.entity.Paiement;

public interface PdfService {
    byte[] genererContratPdf(ContratBail contrat, Locataire locataireUser, Gestionnaire gestionnaireUser, String typeBien, String libelle);
    byte[] genererQuittancePdf(Paiement paiement  , Locataire locataireUser, Gestionnaire gestionnaireUser, String typeBien, String libelle );
}
