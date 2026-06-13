package gesimmo.nekaso.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO {
    private Long id;
    private String message;
    private String type;
    private LocalDateTime dateEnvoi;
    private Long locataireId;
}
