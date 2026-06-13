package gesimmo.nekaso.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "locataire")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Locataire extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
