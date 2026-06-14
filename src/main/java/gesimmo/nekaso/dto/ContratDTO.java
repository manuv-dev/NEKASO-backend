package gesimmo.nekaso.dto;

import lombok.*;
import java.time.LocalDateTime;
import gesimmo.nekaso.entity.Locataire;
import gesimmo.nekaso.entity.Gestionnaire;

@Builder
public record ContratDTO (
        Long id,
        LocalDateTime dateSignature,
        Double montantLoyer,
        Double montantCaution,
        String conditions,
        LocalDateTime dateDebut,
        String cheminPDF,
        Locataire locataire,
        Gestionnaire gestionnaire
) { }