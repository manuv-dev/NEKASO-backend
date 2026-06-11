package gesimmo.nekaso.service;

import org.springframework.stereotype.Service;

import gesimmo.nekaso.entity.ContratBail;
import gesimmo.nekaso.entity.Quittance;
import gesimmo.nekaso.entity.User;

public interface PdfService {
    String genererContratPdf(ContratBail contrat, User locataireUser, User gestionnaireUser);
    // String genererQuittancePdf(Quittance quittance, User locataireUser, User gestionnaireUser);
}
