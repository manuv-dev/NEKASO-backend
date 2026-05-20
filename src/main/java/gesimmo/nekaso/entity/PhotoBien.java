package gesimmo.nekaso.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "photo_bien")
public class PhotoBien {
	@Getter
	@Setter
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Setter
	@Getter
    @Column(nullable = false)
	private String urlPhoto;

	@Setter
	@Getter
    @Column(nullable = false)
	private LocalDate dateUpload;
	
	@Setter
	@Getter
	@ManyToOne(optional = false)
	@JoinColumn(name = "bien_id")
	private BienImmobilier bienImmobilier;

	public void setBien(BienImmobilier savedBien) {
	}
}
