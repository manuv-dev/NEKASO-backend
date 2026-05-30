package gesimmo.nekaso.service.impl;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import gesimmo.nekaso.entity.ContratBail;
import gesimmo.nekaso.entity.Quittance;
import gesimmo.nekaso.entity.User;
import gesimmo.nekaso.service.PdfService;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
@Service
public class PdfServiceImpl implements PdfService {

    @Override
    public String genererContratPdf(ContratBail contrat, User locataireUser, User gestionnaireUser) {
        try {
            String chemin = "contrats/contrat_" + contrat.getId() + ".pdf";
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(chemin));
            document.open();

            Font titreFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Paragraph titre = new Paragraph("CONTRAT DE BAIL", titreFont);
            titre.setAlignment(Element.ALIGN_CENTER);
            document.add(titre);
            document.add(new Paragraph("\n"));

            Font normalFont = new Font(Font.HELVETICA, 12);
            document.add(new Paragraph("Date signature : " + contrat.getDateSignature(), normalFont));
            document.add(new Paragraph("Date début : " + contrat.getDateDebut(), normalFont));
            document.add(new Paragraph("Montant du loyer : " + contrat.getMontantLoyer() + " FCFA", normalFont));
            document.add(new Paragraph("Montant de la caution : " + contrat.getMontantCaution() + " FCFA", normalFont));
            document.add(new Paragraph("Conditions : " + contrat.getConditions(), normalFont));
            document.add(new Paragraph("\n"));

            Font boldFont = new Font(Font.HELVETICA, 12, Font.BOLD);
            document.add(new Paragraph("Locataire : " + locataireUser.getNom() + " " + locataireUser.getPrenom()
                    + " (Tel: " + locataireUser.getTelephone() + ")", boldFont));
            document.add(new Paragraph("Gestionnaire : " + gestionnaireUser.getNom() + " " + gestionnaireUser.getPrenom()
                    + " (Tel: " + gestionnaireUser.getTelephone() + ")", boldFont));
            document.add(new Paragraph("\n\n"));

            document.add(new Paragraph("Signature du locataire : ____________________", normalFont));
            document.add(new Paragraph("Signature du gestionnaire : ________________", normalFont));

            document.close();
            return chemin;
        } catch (Exception e) {
            throw new RuntimeException("Erreur génération PDF contrat", e);
        }
    }

    @Override
    public String genererQuittancePdf(Quittance quittance, User locataireUser, User gestionnaireUser) {
        try {
            String chemin = "quittances/quittance_" + quittance.getId() + ".pdf";

            // Définir une page demi-A4 (largeur A4, hauteur moitié)
            Rectangle demiA4 = new Rectangle(PageSize.A4.getWidth(), PageSize.A4.getHeight() / 2);
            Document document = new Document(demiA4);

            PdfWriter.getInstance(document, new FileOutputStream(chemin));
            document.open();

            // Titre
            Font titreFont = new Font(Font.HELVETICA, 16, Font.BOLD);
            Paragraph titre = new Paragraph("QUITTANCE DE LOYER", titreFont);
            titre.setAlignment(Element.ALIGN_CENTER);
            document.add(titre);
            document.add(new Paragraph("\n"));

            // Infos paiement
            Font normalFont = new Font(Font.HELVETICA, 12);
            document.add(new Paragraph("Date émission : " + quittance.getDateEmission(), normalFont));
            document.add(new Paragraph("Montant payé : " + quittance.getPaiement().getMontant() + " FCFA", normalFont));
            document.add(new Paragraph("Mois concerné : " + quittance.getPaiement().getMois(), normalFont));
            document.add(new Paragraph("Méthode de paiement : " + quittance.getPaiement().getMethodePaiement(), normalFont));
            document.add(new Paragraph("Référence : " + quittance.getPaiement().getReference(), normalFont));
            document.add(new Paragraph("\n"));

            // Infos parties
            Font boldFont = new Font(Font.HELVETICA, 12, Font.BOLD);
            document.add(new Paragraph("Locataire : " + locataireUser.getNom() + " " + locataireUser.getPrenom()
                    + " (Tel: " + locataireUser.getTelephone() + ")", boldFont));
            document.add(new Paragraph("Gestionnaire : " + gestionnaireUser.getNom() + " " + gestionnaireUser.getPrenom()
                    + " (Tel: " + gestionnaireUser.getTelephone() + ")", boldFont));
            document.add(new Paragraph("\n\n"));

            // Signature
            document.add(new Paragraph("Signature du gestionnaire : ________________", normalFont));

            document.close();
            return chemin;
        } catch (Exception e) {
            throw new RuntimeException("Erreur génération PDF quittance", e);
        }
    }


}
