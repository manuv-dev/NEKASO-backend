package gesimmo.nekaso.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import gesimmo.nekaso.entity.enums.StatutBien;
import gesimmo.nekaso.entity.enums.TypeBien;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bien_immobilier")
@lombok.Data
public class BienImmobilier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeBien typeBien;
    @Column(nullable = false)
    private String libelle;

    @Column(nullable = false)
    private String adresse;

    @Column(nullable = false)
    private Double surface;

    @Column(nullable = false)
    private Integer nombrePieces;

    @Column(nullable = false)
    private Double loyer;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatutBien statutBien;

    @Column(length = 500 )
    private String description;

    @Column(nullable = false)
    private LocalDate dateAjout;

    @OneToMany(mappedBy = "bienImmobilier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhotoBien> photos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "gestionnaire_id")
    private Gestionnaire gestionnaire;

}
