package gesimmo.nekaso.service;



import gesimmo.nekaso.entity.ContratBail;
import gesimmo.nekaso.entity.Quittance;
import gesimmo.nekaso.entity.User;
import gesimmo.nekaso.entity.Locataire;
import gesimmo.nekaso.entity.Gestionnaire;

public interface PdfService {
    String genererContratPdf(ContratBail contrat, Locataire locataire, Gestionnaire gestionnaire);
    // String genererQuittancePdf(Quittance quittance, User locataireUser, User gestionnaireUser);
}
