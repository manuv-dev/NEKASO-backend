package gesimmo.nekaso.entity;

import lombok.*;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;



@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false, unique = true)
    private String telephone;

    @Column(nullable = false)
    private String motDePasse;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private LocalDateTime dateCreation = LocalDateTime.now();

    @Column(nullable = false)
    private String statut;
}