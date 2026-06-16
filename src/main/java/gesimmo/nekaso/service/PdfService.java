package gesimmo.nekaso.service;



import gesimmo.nekaso.entity.ContratBail;
import gesimmo.nekaso.entity.Quittance;
import gesimmo.nekaso.entity.User;

public interface PdfService {
    byte[] genererContratPdf(ContratBail contrat, User locataireUser, User gestionnaireUser, String typeBien, String libelle);
    // byte[] genererQuittancePdf(Quittance quittance, User locataireUser, User gestionnaireUser);
}
