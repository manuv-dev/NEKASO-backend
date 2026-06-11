package gesimmo.nekaso.dto;

import gesimmo.nekaso.entity.enums.StatutBien;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatutBienUpdateDTO {

    @NotNull(message = "Le statut est obligatoire")
    private StatutBien statut;
}