package gesimmo.nekaso.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import gesimmo.nekaso.entity.enums.Statut;
import gesimmo.nekaso.entity.enums.TypeBien;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "bien_immobilier")
@lombok.Data
public class BienImmobilier {
    @Id
    @Setter
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeBien typeBien;

    @Setter
    @Getter
    @Column(nullable = false)
    private String adresse;

    @Setter
    @Getter
    @Column(nullable = false)
    private Double surface;

    @Setter
    @Getter
    @Column(nullable = false)
    private Integer nombrePieces;

    @Setter
    @Getter
    @Column(nullable = false)
    private Double loyer;

    @Setter
    @Getter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Statut statutBien;

    @Setter
    @Getter
    @Column(length = 500 )
    private String description;

    @Setter
    @Getter
    @Column(nullable = false)
    private LocalDate dateAjout;

    @OneToMany(mappedBy = "bienImmobilier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhotoBien> photos = new ArrayList<>();
}
