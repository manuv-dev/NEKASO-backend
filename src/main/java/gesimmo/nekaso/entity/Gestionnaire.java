package gesimmo.nekaso.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "gestionnaire")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Gestionnaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
