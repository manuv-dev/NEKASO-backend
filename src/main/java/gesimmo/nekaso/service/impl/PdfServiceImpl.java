package gesimmo.nekaso.service.impl;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import gesimmo.nekaso.entity.ContratBail;
import gesimmo.nekaso.entity.Quittance;
import gesimmo.nekaso.entity.User;
import gesimmo.nekaso.service.PdfService;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.File;
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
            document.add(
                    new Paragraph("Gestionnaire : " + gestionnaireUser.getNom() + " " + gestionnaireUser.getPrenom()
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
            String directoryPath = "quittances";
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String chemin = directoryPath + "/quittance_" + quittance.getId() + ".pdf";
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(chemin));
            document.open();

            Color primaryColor = new Color(24, 74, 162);
            Color secondaryColor = new Color(0, 120, 230);
            Color accentColor = new Color(255, 193, 7);
            Color lightGray = new Color(245, 247, 250);
            Color darkGray = new Color(51, 51, 51);

            Font titreFont = new Font(Font.HELVETICA, 18, Font.BOLD, primaryColor);
            Font sectionFont = new Font(Font.HELVETICA, 12, Font.BOLD, secondaryColor);
            Font normalFont = new Font(Font.HELVETICA, 11, Font.NORMAL, darkGray);
            Font boldFont = new Font(Font.HELVETICA, 11, Font.BOLD, darkGray);

            Paragraph titre = new Paragraph("QUITTANCE DE LOYER", titreFont);
            titre.setAlignment(Element.ALIGN_CENTER);
            titre.setSpacingAfter(12f);
            document.add(titre);

            Paragraph intro = new Paragraph(
                    "Document officiel attestant du paiement du loyer pour la période concernée.",
                    normalFont);
            intro.setAlignment(Element.ALIGN_CENTER);
            intro.setSpacingAfter(12f);
            document.add(intro);

            Paragraph divider = new Paragraph("________________________________________________",
                    new Font(Font.HELVETICA, 10, Font.NORMAL, accentColor));
            divider.setAlignment(Element.ALIGN_CENTER);
            divider.setSpacingAfter(14f);
            document.add(divider);

            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            infoTable.setSpacingAfter(16f);
            infoTable.setWidths(new int[] { 2, 3 });

            addCell(infoTable, "Numéro", sectionFont, lightGray, true);
            addCell(infoTable, quittance.getNumero() != null ? quittance.getNumero() : "Non renseigné", normalFont,
                    null, false);
            addCell(infoTable, "Date émission", sectionFont, lightGray, true);
            addCell(infoTable, String.valueOf(quittance.getDateEmission()), normalFont, null, false);
            addCell(infoTable, "Montant payé", sectionFont, lightGray, true);
            addCell(infoTable, quittance.getMontant() + " FCFA", boldFont, null, false);
            addCell(infoTable, "Mois concerné", sectionFont, lightGray, true);
            addCell(infoTable, quittance.getPaiement() != null && quittance.getPaiement().getMois() != null
                    ? quittance.getPaiement().getMois().name()
                    : "Non renseigné", normalFont, null, false);
            addCell(infoTable, "Méthode de paiement", sectionFont, lightGray, true);
            addCell(infoTable, quittance.getPaiement() != null && quittance.getPaiement().getMethodePaiement() != null
                    ? quittance.getPaiement().getMethodePaiement().name()
                    : "Non renseigné", normalFont, null, false);
            addCell(infoTable, "Référence", sectionFont, lightGray, true);
            addCell(infoTable,
                    quittance.getPaiement() != null ? quittance.getPaiement().getReference() : "Non renseignée",
                    normalFont, null, false);
            document.add(infoTable);

            PdfPTable partiesTable = new PdfPTable(2);
            partiesTable.setWidthPercentage(100);
            partiesTable.setSpacingBefore(4f);
            partiesTable.setSpacingAfter(18f);
            partiesTable.setWidths(new int[] { 1, 1 });

            addCell(partiesTable, "Locataire", sectionFont, primaryColor, true);
            addCell(partiesTable, "Gestionnaire", sectionFont, primaryColor, true);
            String locataireLabel = locataireUser != null
                    ? (locataireUser.getNom() + " " + locataireUser.getPrenom() + "\nTel: "
                            + locataireUser.getTelephone())
                    : "Non renseigné";
            String gestionnaireLabel = gestionnaireUser != null
                    ? (gestionnaireUser.getNom() + " " + gestionnaireUser.getPrenom() + "\nTel: "
                            + gestionnaireUser.getTelephone())
                    : "Non renseigné";
            addCell(partiesTable, locataireLabel, boldFont, lightGray, false);
            addCell(partiesTable, gestionnaireLabel, boldFont, lightGray, false);
            document.add(partiesTable);

            Paragraph signature = new Paragraph("Signature du gestionnaire : __________________________", normalFont);
            signature.setSpacingBefore(6f);
            document.add(signature);

            document.close();
            return chemin;
        } catch (Exception e) {
            throw new RuntimeException("Erreur génération PDF quittance", e);
        }
    }

    private void addCell(PdfPTable table, String text, Font font, Color background, boolean header) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(background);
        cell.setPadding(8f);
        cell.setBorder(Rectangle.NO_BORDER);
        if (header) {
            cell.setPaddingBottom(10f);
        }
        table.addCell(cell);
    }

}
