package gesimmo.nekaso.service.impl;

import com.lowagie.text.*;

import gesimmo.nekaso.entity.ContratBail;
import gesimmo.nekaso.entity.Quittance;
import gesimmo.nekaso.entity.User;
import gesimmo.nekaso.service.PdfService;
import org.springframework.stereotype.Service;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.io.ByteArrayOutputStream;
import java.net.URL;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
@Service
public class PdfServiceImpl implements PdfService {

//v3

@Override
public byte[] genererContratPdf(ContratBail contrat, User locataireUser, User gestionnaireUser, String typeBien, String libelle) {
    try {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        // Définition du document avec des marges propres
        Document document = new Document(PageSize.A4, 40, 40, 50, 60); // Augmentation de la marge basse pour le pied de page
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        
        // Attribution du pied de page via l'événement de page
        writer.setPageEvent(new PdfPageEventHelper() {
            @Override
            public void onEndPage(PdfWriter writer, Document document) {
                PdfContentByte cb = writer.getDirectContent();
                Font footerFont = new Font(Font.HELVETICA, 9, Font.NORMAL, java.awt.Color.GRAY);
                
                String footerText = "NEKASO — Dakar, Sénégal | +221 77 412 45 19 | nekasoimmo@gmail.com";
                
                Paragraph footer = new Paragraph(footerText, footerFont);
                footer.setAlignment(Element.ALIGN_CENTER);
                
                // Positionnement du pied de page au-dessus du bord inférieur (ex: à 30 points du bas)
                ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, 
                    new Phrase(footerText, footerFont), 
                    (document.right() - document.left()) / 2 + document.left(), 
                    30, 0);
            }
        });

        document.open();

        // ----------------------------------------------------
        // POLICES DE CARACTÈRES (Styles & Couleurs)
        // ----------------------------------------------------
        Font primaryColor = new Font(Font.HELVETICA, 22, Font.BOLD, new java.awt.Color(24, 43, 73)); // Bleu Marine/Nekaso
        Font sectionTitleFont = new Font(Font.HELVETICA, 12, Font.BOLD, new java.awt.Color(24, 43, 73));
        Font bodyBold = new Font(Font.HELVETICA, 10, Font.BOLD, java.awt.Color.BLACK);
        Font bodyNormal = new Font(Font.HELVETICA, 10, Font.NORMAL, java.awt.Color.DARK_GRAY);
        Font bodyNormalItalic = new Font(Font.HELVETICA, 10, Font.ITALIC, java.awt.Color.DARK_GRAY);

        // ----------------------------------------------------
        // EN-TÊTE : LOGO EN HAUT ET TITRE CENTRÉ EN DESSOUS
        // ----------------------------------------------------
        
        // 1. Insertion du Logo Centré
        try {
            String logoUrl = "https://res.cloudinary.com/dx2imkeka/image/upload/v1781548276/nekaso/photos/sb2ashfmvop27p1fd54a.png";
            Image logo = Image.getInstance(new URL(logoUrl));
            logo.scaleToFit(100, 50); 
            logo.setAlignment(Element.ALIGN_CENTER);
            document.add(logo);
        } catch (Exception e) {
            // Repli discret si l'image ne charge pas
            Paragraph altLogo = new Paragraph("NEKASO", sectionTitleFont);
            altLogo.setAlignment(Element.ALIGN_CENTER);
            document.add(altLogo);
        }
        
        document.add(new Paragraph("\n"));

        // 2. Titre Centré "CONTRAT DE BAIL"
        Paragraph titleParagraph = new Paragraph("CONTRAT DE BAIL", primaryColor);
        titleParagraph.setAlignment(Element.ALIGN_CENTER);
        document.add(titleParagraph);
        
        // Ligne de séparation élégante
        Paragraph line = new Paragraph("__________________________________________________________________________________", bodyNormalItalic);
        line.setAlignment(Element.ALIGN_CENTER);
        document.add(line);
        document.add(new Paragraph("\n"));

        // ----------------------------------------------------
        // SECTION 1 : PARTIES CONTRACTANTES
        // ----------------------------------------------------
        document.add(new Paragraph("ENTRE LES SOUSSIGNÉS :", sectionTitleFont));
        document.add(new Paragraph("\n"));

        // Bloc Bailleur / Gestionnaire
        Paragraph pBailleur = new Paragraph();
        pBailleur.add(new Chunk("Le Bailleur / Gestionnaire : ", bodyBold));
        pBailleur.add(new Chunk(gestionnaireUser.getNom().toUpperCase() + " " + gestionnaireUser.getPrenom(), bodyBold));
        pBailleur.add(new Chunk(", bailleur / Gestionnaire.\nTéléphone : " + (gestionnaireUser.getTelephone() != null ? gestionnaireUser.getTelephone() : "N/A"), bodyNormal));
        pBailleur.setSpacingAfter(8);
        document.add(pBailleur);

        // Bloc Preneur / Locataire
        Paragraph pLocataire = new Paragraph();
        pLocataire.add(new Chunk("Le Preneur / Locataire : ", bodyBold));
        pLocataire.add(new Chunk(locataireUser.getNom().toUpperCase() + " " + locataireUser.getPrenom(), bodyBold));
        pLocataire.add(new Chunk(", certifié pour occuper le bien : " + typeBien + " , " + libelle + ".\nTéléphone : " + (locataireUser.getTelephone() != null ? locataireUser.getTelephone() : "N/A"), bodyNormal));
        pLocataire.setSpacingAfter(15);
        document.add(pLocataire);

        document.add(new Paragraph("Il a été convenu et arrêté ce qui suit :", bodyNormalItalic));
        document.add(new Paragraph("\n"));

        // ----------------------------------------------------
        // SECTION 2 : CONDITIONS (Format texte simple, sans tableau)
        // ----------------------------------------------------
        String dateSignature = contrat.getDateSignature() != null ? contrat.getDateSignature().toString() : "En attente";
        String dateDebut = contrat.getDateDebut() != null ? contrat.getDateDebut().toString() : "Non spécifiée";
        
        Paragraph pDetails = new Paragraph();
        pDetails.setLeading(16f); // Espacement de ligne agréable
        
        pDetails.add(new Chunk("• Date de Signature : ", bodyBold));
        pDetails.add(new Chunk(dateSignature + "\n", bodyNormal));
        
        pDetails.add(new Chunk("• Date de Prise d'Effet du Bail : ", bodyBold));
        pDetails.add(new Chunk(dateDebut + "\n", bodyNormal));
        
        pDetails.add(new Chunk("• Montant du Loyer Mensuel : ", bodyBold));
        pDetails.add(new Chunk(contrat.getMontantLoyer() + " FCFA\n", bodyBold)); // Loyer mis en gras
        
        pDetails.add(new Chunk("• Dépôt de Garantie (Caution) : ", bodyBold));
        pDetails.add(new Chunk(contrat.getMontantCaution() + " FCFA\n", bodyNormal));
        
        pDetails.setSpacingAfter(20);
        document.add(pDetails);

        // ----------------------------------------------------
        // SECTION 3 : CHARGES ET CONDITIONS PARTICULIÈRES
        // ----------------------------------------------------
        document.add(new Paragraph("CLAUSES ET CONDITIONS PARTICULIÈRES :", sectionTitleFont));
        document.add(new Paragraph("\n"));
        
        String clausesText = (contrat.getConditions() != null && !contrat.getConditions().trim().isEmpty()) 
                ? contrat.getConditions() 
                : "Le preneur s'engage à maintenir les lieux loués en bon état et à effectuer les réparations locatives courantes. Le paiement s'effectuera en conformité avec les règles de la plateforme NEKASO.";
        
        // Note : Si clausesText contient des '\n' envoyés par le front-end, iText effectuera le saut de ligne automatiquement.
        Paragraph pClauses = new Paragraph(clausesText, bodyNormal);
        pClauses.setAlignment(Element.ALIGN_JUSTIFIED);
        pClauses.setLeading(14f);
        document.add(pClauses);
        
        document.add(new Paragraph("\n\n"));

        // ----------------------------------------------------
        // SECTION 4 : SIGNATURES
        // ----------------------------------------------------
        PdfPTable signatureTable = new PdfPTable(2);
        signatureTable.setWidthPercentage(100);
        signatureTable.setSpacingBefore(20);

        PdfPCell cellBailleur = new PdfPCell(new Paragraph("Signature du Bailleur / Gestionnaire\n\n\n\n_________________________", bodyBold));
        cellBailleur.setBorder(Rectangle.NO_BORDER);
        cellBailleur.setHorizontalAlignment(Element.ALIGN_CENTER);

        PdfPCell cellLocataire = new PdfPCell(new Paragraph("Signature du Preneur / Locataire\n\n\n\n_________________________", bodyBold));
        cellLocataire.setBorder(Rectangle.NO_BORDER);
        cellLocataire.setHorizontalAlignment(Element.ALIGN_CENTER);

        signatureTable.addCell(cellBailleur);
        signatureTable.addCell(cellLocataire);

        document.add(signatureTable);

        document.close();
        return baos.toByteArray();

    } catch (Exception e) {
        throw new RuntimeException("Erreur lors de la génération esthétique du PDF", e);
    }
}
//v2

// @Override
// public byte[] genererContratPdf(ContratBail contrat, User locataireUser, User gestionnaireUser) {
//     try {
//         ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
//         // Définition du document avec des marges propres (36pt = ~1.27cm)
//         Document document = new Document(PageSize.A4, 40, 40, 50, 50);
//         PdfWriter.getInstance(document, baos);
//         document.open();

//         // ----------------------------------------------------
//         // POLICES DE CARACTÈRES (Styles & Couleurs)
//         // ----------------------------------------------------
//         Font primaryColor = new Font(Font.HELVETICA, 20, Font.BOLD, new java.awt.Color(24, 43, 73)); // Bleu Marine/Nekaso
//         Font sectionTitleFont = new Font(Font.HELVETICA, 12, Font.BOLD, new java.awt.Color(24, 43, 73));
//         Font bodyBold = new Font(Font.HELVETICA, 10, Font.BOLD, java.awt.Color.BLACK);
//         Font bodyNormal = new Font(Font.HELVETICA, 10, Font.NORMAL, java.awt.Color.DARK_GRAY);
//         Font bodyNormalItalic = new Font(Font.HELVETICA, 10, Font.ITALIC, java.awt.Color.DARK_GRAY);

//         // ----------------------------------------------------
//         // EN-TÊTE : LOGO NEKASO & TITRE
//         // ----------------------------------------------------
//         PdfPTable headerTable = new PdfPTable(2);
//         headerTable.setWidthPercentage(100);
//         headerTable.setWidths(new float[]{(float) 1.5, (float) 3.5}); // Proportion Logo vs Titre

//         // Insertion du Logo depuis Cloudinary
//         try {
//             String logoUrl = "https://res.cloudinary.com/dx2imkeka/image/upload/v1781548276/nekaso/photos/sb2ashfmvop27p1fd54a.png";
//             Image logo = Image.getInstance(new URL(logoUrl));
//             logo.scaleToFit(100, 50); // Redimensionnement propre
//             PdfPCell logoCell = new PdfPCell(logo);
//             logoCell.setBorder(Rectangle.NO_BORDER);
//             logoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//             headerTable.addCell(logoCell);
//         } catch (Exception e) {
//             // Cellule vide de repli si le logo ne charge pas (pas de plantage de l'API)
//             PdfPCell emptyCell = new PdfPCell(new Paragraph("NEKASO", primaryColor));
//             emptyCell.setBorder(Rectangle.NO_BORDER);
//             headerTable.addCell(emptyCell);
//         }

//         // Titre du Document
//         PdfPCell titleCell = new PdfPCell(new Paragraph("CONTRAT DE BAIL À LOYER", primaryColor));
//         titleCell.setBorder(Rectangle.NO_BORDER);
//         titleCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//         titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//         headerTable.addCell(titleCell);

//         document.add(headerTable);
        
//         // Ligne de séparation élégante
//         document.add(new Paragraph("\n"));
//         Paragraph line = new Paragraph("__________________________________________________________________________________", bodyNormalItalic);
//         line.setAlignment(Element.ALIGN_CENTER);
//         document.add(line);
//         document.add(new Paragraph("\n"));

//         // ----------------------------------------------------
//         // SECTION 1 : PARTIES CONTRACTANTES
//         // ----------------------------------------------------
//         document.add(new Paragraph("ENTRE LES SOUSSIGNÉS :", sectionTitleFont));
//         document.add(new Paragraph("\n"));

//         // Bloc Bailleur / Gestionnaire
//         Paragraph pBailleur = new Paragraph();
//         pBailleur.add(new Chunk("Le Bailleur / Gestionnaire : ", bodyBold));
//         pBailleur.add(new Chunk(gestionnaireUser.getNom().toUpperCase() + " " + gestionnaireUser.getPrenom(), bodyBold));
//         pBailleur.add(new Chunk(", demeurant au profil de l'administration NEKASO. Téléphone : " + (gestionnaireUser.getTelephone() != null ? gestionnaireUser.getTelephone() : "N/A"), bodyNormal));
//         pBailleur.setSpacingAfter(8);
//         document.add(pBailleur);

//         // Bloc Preneur / Locataire
//         Paragraph pLocataire = new Paragraph();
//         pLocataire.add(new Chunk("Le Preneur / Locataire : ", bodyBold));
//         pLocataire.add(new Chunk(locataireUser.getNom().toUpperCase() + " " + locataireUser.getPrenom(), bodyBold));
//         pLocataire.add(new Chunk(", certifié pour occuper les locaux d'habitation/commerciaux enregistrés. Téléphone : " + (locataireUser.getTelephone() != null ? locataireUser.getTelephone() : "N/A"), bodyNormal));
//         pLocataire.setSpacingAfter(15);
//         document.add(pLocataire);

//         document.add(new Paragraph("Il a été convenu et arrêté ce qui suit :", bodyNormalItalic));
//         document.add(new Paragraph("\n"));

//         // ----------------------------------------------------
//         // SECTION 2 : CONDITIONS ET RÈGLEMENTATIONS (Tableau Stylisé)
//         // ----------------------------------------------------
//         PdfPTable detailsTable = new PdfPTable(2);
//         detailsTable.setWidthPercentage(100);
//         detailsTable.setSpacingBefore(10);
//         detailsTable.setSpacingAfter(15);

//         // Style des cellules du tableau
//         java.awt.Color headerBg = new java.awt.Color(240, 244, 248);

//         // Ligne 1 : Date de Signature
//         addTableCell(detailsTable, "Date de Signature", bodyBold, headerBg);
//         addTableCell(detailsTable, contrat.getDateSignature() != null ? contrat.getDateSignature().toString() : "En attente", bodyNormal, java.awt.Color.WHITE);

//         // Ligne 2 : Date d'effet
//         addTableCell(detailsTable, "Date de Prise d'Effet du Bail", bodyBold, headerBg);
//         addTableCell(detailsTable, contrat.getDateDebut() != null ? contrat.getDateDebut().toString() : "Non spécifiée", bodyNormal, java.awt.Color.WHITE);

//         // Ligne 3 : Loyer
//         addTableCell(detailsTable, "Montant du Loyer Mensuel", bodyBold, headerBg);
//         addTableCell(detailsTable, contrat.getMontantLoyer() + " FCFA", bodyBold, java.awt.Color.WHITE);

//         // Ligne 4 : Caution
//         addTableCell(detailsTable, "Dépôt de Garantie (Caution)", bodyBold, headerBg);
//         addTableCell(detailsTable, contrat.getMontantCaution() + " FCFA", bodyNormal, java.awt.Color.WHITE);

//         document.add(detailsTable);

//         // ----------------------------------------------------
//         // SECTION 3 : CHARGES ET CONDITIONS PARTICULIÈRES
//         // ----------------------------------------------------
//         document.add(new Paragraph("CLAUSES ET CONDITIONS PARTICULIÈRES :", sectionTitleFont));
//         document.add(new Paragraph("\n"));
        
//         String clausesText = (contrat.getConditions() != null && !contrat.getConditions().trim().isEmpty()) 
//                 ? contrat.getConditions() 
//                 : "Le preneur s'engage à maintenir les lieux loués en bon état et à effectuer les réparations locatives courantes. Le paiement s'effectuera en conformité avec les règles de la plateforme NEKASO.";
        
//         Paragraph pClauses = new Paragraph(clausesText, bodyNormal);
//         pClauses.setAlignment(Element.ALIGN_JUSTIFIED);
//         pClauses.setLeading(14f); // Espacement des lignes pour le confort de lecture
//         document.add(pClauses);
        
//         document.add(new Paragraph("\n\n"));

//         // ----------------------------------------------------
//         // SECTION 4 : SIGNATURES
//         // ----------------------------------------------------
//         PdfPTable signatureTable = new PdfPTable(2);
//         signatureTable.setWidthPercentage(100);
//         signatureTable.setSpacingBefore(20);

//         PdfPCell cellBailleur = new PdfPCell(new Paragraph("Signature du Bailleur / Gestionnaire\n\n\n\n_________________________", bodyBold));
//         cellBailleur.setBorder(Rectangle.NO_BORDER);
//         cellBailleur.setHorizontalAlignment(Element.ALIGN_CENTER);

//         PdfPCell cellLocataire = new PdfPCell(new Paragraph("Signature du Preneur / Locataire\n\n\n\n_________________________", bodyBold));
//         cellLocataire.setBorder(Rectangle.NO_BORDER);
//         cellLocataire.setHorizontalAlignment(Element.ALIGN_CENTER);

//         signatureTable.addCell(cellBailleur);
//         signatureTable.addCell(cellLocataire);

//         document.add(signatureTable);

//         document.close();
//         return baos.toByteArray();

//     } catch (Exception e) {
//         throw new RuntimeException("Erreur lors de la génération esthétique du PDF", e);
//     }
// }

// // Fonction utilitaire pour générer des cellules de tableau proprement alignées et stylisées
// private void addTableCell(PdfPTable table, String text, Font font, java.awt.Color bgColor) {
//     PdfPCell cell = new PdfPCell(new Paragraph(text, font));
//     cell.setBackgroundColor(bgColor);
//     cell.setPadding(8);
//     cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//     cell.setBorderColor(new java.awt.Color(218, 224, 233));
//     table.addCell(cell);
// }




}
