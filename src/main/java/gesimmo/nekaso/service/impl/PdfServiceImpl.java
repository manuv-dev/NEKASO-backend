package gesimmo.nekaso.service.impl;

import com.lowagie.text.*;

import gesimmo.nekaso.entity.ContratBail;
import gesimmo.nekaso.entity.Gestionnaire;
import gesimmo.nekaso.entity.Locataire;
import gesimmo.nekaso.entity.Paiement;

import gesimmo.nekaso.service.PdfService;
import org.springframework.stereotype.Service;
import com.lowagie.text.pdf.*;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.time.LocalDate;


@Service
public class PdfServiceImpl implements PdfService {

    @Override
    public byte[] genererContratPdf(ContratBail contrat, Locataire locataireUser, Gestionnaire gestionnaireUser, String typeBien, String libelle) {
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
            pDetails.add(new Chunk(contrat.getMontantLoyer() + " FCFA\n", bodyBold)); 
            
            pDetails.add(new Chunk("• Dépôt de Garantie (Caution) : ", bodyBold));
            pDetails.add(new Chunk(contrat.getMontantCaution() + " FCFA\n", bodyNormal));

            // 👇 AJOUT DEMANDÉ : Intégration propre de l'échéance mensuelle
            pDetails.add(new Chunk("• Échéance de Paiement : ", bodyBold));
            pDetails.add(new Chunk("Chaque " + contrat.getJourEcheanceLoyer() + " du mois\n", bodyNormal));
            
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
            // SECTION 4 : SIGNATURES DYNAMIQUES
            // ----------------------------------------------------
            PdfPTable signatureTable = new PdfPTable(2);
            signatureTable.setWidthPercentage(100);
            signatureTable.setSpacingBefore(30); // Un peu plus d'espace pour respirer

            // Préparation des chaînes de texte avec les noms dynamiques
            String nomGestionnaire = gestionnaireUser.getPrenom() + " " + gestionnaireUser.getNom().toUpperCase();
            String nomLocataire = locataireUser.getPrenom() + " " + locataireUser.getNom().toUpperCase();

            // Bloc de gauche : Le Bailleur / Gestionnaire
            Paragraph pSignBailleur = new Paragraph();
            pSignBailleur.add(new Chunk("Signature du Bailleur / Gestionnaire\n", bodyBold));
            pSignBailleur.add(new Chunk("Lu et approuvé\n\n\n\n\n", bodyNormalItalic)); // Espace pour signer
            pSignBailleur.add(new Chunk(nomGestionnaire, bodyBold)); // Nom affiché sous la signature
            
            PdfPCell cellBailleur = new PdfPCell(pSignBailleur);
            cellBailleur.setBorder(Rectangle.NO_BORDER);
            cellBailleur.setHorizontalAlignment(Element.ALIGN_CENTER);

            // Bloc de droite : Le Preneur / Locataire
            Paragraph pSignLocataire = new Paragraph();
            pSignLocataire.add(new Chunk("Signature du Preneur / Locataire\n", bodyBold));
            pSignLocataire.add(new Chunk("Lu et approuvé\n\n\n\n\n", bodyNormalItalic)); // Espace pour signer
            pSignLocataire.add(new Chunk(nomLocataire, bodyBold)); // Nom affiché sous la signature

            PdfPCell cellLocataire = new PdfPCell(pSignLocataire);
            cellLocataire.setBorder(Rectangle.NO_BORDER);
            cellLocataire.setHorizontalAlignment(Element.ALIGN_CENTER);

            // Ajout des cellules au tableau
            signatureTable.addCell(cellBailleur);
            signatureTable.addCell(cellLocataire);

            document.add(signatureTable);

            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération esthétique du PDF", e);
        }
    }

    @Override
    public byte[] genererQuittancePdf(Paiement paiement, Locataire locataireUser, Gestionnaire gestionnaireUser, String typeBien, String libelle) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            
            // 1. Passage au format A5 en Paysage (A5 Rotated)
            Document document = new Document(PageSize.A5.rotate(), 30, 30, 30, 35); 
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            
            // Attribution du pied de page NEKASO adapté au format A5 Paysage
            writer.setPageEvent(new PdfPageEventHelper() {
                @Override
                public void onEndPage(PdfWriter writer, Document document) {
                    PdfContentByte cb = writer.getDirectContent();
                    Font footerFont = new Font(Font.HELVETICA, 8, Font.NORMAL, java.awt.Color.GRAY);
                    String footerText = "NEKASO — Dakar, Sénégal | +221 77 412 45 19 | nekasoimmo@gmail.com";
                    
                    ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, 
                        new Phrase(footerText, footerFont), 
                        (document.right() - document.left()) / 2 + document.left(), 
                        15, 0);
                }
            });

            document.open();

            // ----------------------------------------------------
            // POLICES DE CARACTÈRES
            // ----------------------------------------------------
            java.awt.Color nekasoBlue = new java.awt.Color(24, 43, 73);
            Font primaryColor = new Font(Font.HELVETICA, 18, Font.BOLD, nekasoBlue);
            Font sectionTitleFont = new Font(Font.HELVETICA, 11, Font.BOLD, nekasoBlue);
            Font labelFont = new Font(Font.HELVETICA, 9, Font.BOLD, java.awt.Color.GRAY);
            Font bodyBold = new Font(Font.HELVETICA, 10, Font.BOLD, java.awt.Color.BLACK);
            Font bodyNormal = new Font(Font.HELVETICA, 10, Font.NORMAL, java.awt.Color.DARK_GRAY);
            Font amountFont = new Font(Font.HELVETICA, 14, Font.BOLD, nekasoBlue);

            // ----------------------------------------------------
            // EN-TÊTE : LOGO CENTRÉ ET TITRE
            // ----------------------------------------------------
            try {
                String logoUrl = "https://res.cloudinary.com/dx2imkeka/image/upload/v1781548276/nekaso/photos/sb2ashfmvop27p1fd54a.png";
                Image logo = Image.getInstance(new URL(logoUrl));
                logo.scaleToFit(90, 45); 
                logo.setAlignment(Element.ALIGN_CENTER); //  Centrage du logo au milieu
                document.add(logo);
            } catch (Exception e) {
                Paragraph altLogo = new Paragraph("NEKASO", sectionTitleFont);
                altLogo.setAlignment(Element.ALIGN_CENTER); //  Centrage du texte alternatif
                document.add(altLogo);
            }
            
            Paragraph titleParagraph = new Paragraph("Reçu de Paiement", primaryColor);
            titleParagraph.setAlignment(Element.ALIGN_CENTER);
            titleParagraph.setSpacingBefore(8);
            titleParagraph.setSpacingAfter(15);
            document.add(titleParagraph);

            // ----------------------------------------------------
            // SECTIONS EN COLONNES : "DE" & "DÉTAILS"
            // ----------------------------------------------------
            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            infoTable.setWidths(new float[]{50f, 50f});

            // Colonne Gauche : Émetteur (Gestionnaire)
            PdfPCell leftCell = new PdfPCell();
            leftCell.setBorder(Rectangle.NO_BORDER);
            leftCell.addElement(new Paragraph("De", labelFont));
            leftCell.addElement(new Paragraph(gestionnaireUser.getNom().toUpperCase() + " " + gestionnaireUser.getPrenom(), bodyBold));
            leftCell.addElement(new Paragraph("Téléphone : " + (gestionnaireUser.getTelephone() != null ? gestionnaireUser.getTelephone() : "N/A"), bodyNormal));
            
            // Colonne Droite : Informations du paiement
            PdfPCell rightCell = new PdfPCell();
            rightCell.setBorder(Rectangle.NO_BORDER);
            rightCell.addElement(new Paragraph("Détails", labelFont));
            
            String datePaiementStr = paiement.getDatePaiement() != null ? paiement.getDatePaiement().toString() : "N/A";
            
            // Calcul de l'année en cours pour le mois
            int anneeEnCours = LocalDate.now().getYear();
            String moisEtAnnee = paiement.getMois() + " " + anneeEnCours;

            Paragraph detailsContent = new Paragraph();
            detailsContent.setLeading(13f);
            detailsContent.add(new Chunk("Référence : ", bodyBold));
            detailsContent.add(new Chunk(paiement.getReference() + "\n", bodyNormal));
            detailsContent.add(new Chunk("Date émission : ", bodyBold));
            detailsContent.add(new Chunk(datePaiementStr + "\n", bodyNormal));
            detailsContent.add(new Chunk("Mois associé : ", bodyBold));
            detailsContent.add(new Chunk(moisEtAnnee, bodyNormal)); //  Ajout de l'année en cours
            rightCell.addElement(detailsContent);

            infoTable.addCell(leftCell);
            infoTable.addCell(rightCell);
            infoTable.setSpacingAfter(15);
            document.add(infoTable);

            // ----------------------------------------------------
            // SECTION : "POUR" (Locataire & Bien)
            // ----------------------------------------------------
            Paragraph pForLabel = new Paragraph("Pour", labelFont);
            pForLabel.setSpacingAfter(2);
            document.add(pForLabel);

            Paragraph pForContent = new Paragraph();
            pForContent.setLeading(13f);
            //  Nom du locataire en MAJUSCULES et en GRAS
            pForContent.add(new Chunk(locataireUser.getNom().toUpperCase() + " " + locataireUser.getPrenom() + "\n", bodyBold));
            pForContent.add(new Chunk("Téléphone : " + (locataireUser.getTelephone() != null ? locataireUser.getTelephone() : "N/A") + "\n", bodyNormal));
            pForContent.add(new Chunk("Désignation : " + typeBien + " — " + libelle, bodyNormal));
            pForContent.setSpacingAfter(20);
            document.add(pForContent);

            // ----------------------------------------------------
            // LIGNE DE DÉMARCATION ET TRANSACTION
            // ----------------------------------------------------
            Paragraph separator = new Paragraph("_________________________________________________________________________________", 
                new Font(Font.HELVETICA, 10, Font.NORMAL, java.awt.Color.LIGHT_GRAY));
            separator.setSpacingAfter(12);
            document.add(separator);

            PdfPTable transactionTable = new PdfPTable(2);
            transactionTable.setWidthPercentage(100);
            transactionTable.setWidths(new float[]{65f, 35f});

            PdfPCell transactionLabelCell = new PdfPCell(new Paragraph("Transaction (" + paiement.getMethodePaiement() + ") :", bodyBold));
            transactionLabelCell.setBorder(Rectangle.NO_BORDER);
            transactionLabelCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

            String montantFormate = String.format("%,.0f", paiement.getMontant()) + " FCFA";
            PdfPCell transactionAmountCell = new PdfPCell(new Paragraph(montantFormate, amountFont));
            transactionAmountCell.setBorder(Rectangle.NO_BORDER);
            transactionAmountCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            transactionAmountCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

            transactionTable.addCell(transactionLabelCell);
            transactionTable.addCell(transactionAmountCell);
            document.add(transactionTable);

            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération de la quittance PDF", e);
        }
    }


}
