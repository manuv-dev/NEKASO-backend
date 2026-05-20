package gesimmo.nekaso.dto;

import gesimmo.nekaso.entity.StatutBien;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatutUpdateDTO {

    @NotNull(message = "Le statut est obligatoire")
    private StatutBien statut;
}