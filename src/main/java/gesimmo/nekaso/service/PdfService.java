package gesimmo.nekaso.service;



import gesimmo.nekaso.entity.ContratBail;
import gesimmo.nekaso.entity.Paiement;
import gesimmo.nekaso.entity.User;

public interface PdfService {
    byte[] genererContratPdf(ContratBail contrat, User locataireUser, User gestionnaireUser, String typeBien, String libelle);
    byte[] genererQuittancePdf(Paiement paiement  , User locataireUser, User gestionnaireUser, String typeBien, String libelle );
}
