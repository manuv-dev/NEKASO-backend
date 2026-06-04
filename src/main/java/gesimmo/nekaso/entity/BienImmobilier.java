package gesimmo.nekaso.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.BatchSize;

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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bien_immobilier")
@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BienImmobilier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeBien typeBien;

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
    private Statut statutBien;

    @Column(length = 500 )
    private String description;

    @Column(nullable = false)
    private LocalDate dateAjout;

    @ManyToOne
    @JoinColumn(name = "gestionnaire_id")
    private Gestionnaire gestionnaire;

    @OneToMany(mappedBy = "bienImmobilier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhotoBien> photos = new ArrayList<>();
}
