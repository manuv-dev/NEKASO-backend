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

@Entity
@Table(name = "photo_bien")
public class PhotoBien {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String urlPhoto;

	@Column(nullable = false)
	private LocalDate dateUpload;

	@ManyToOne(optional = false)
	@JoinColumn(name = "bien_id")
	private BienImmobilier bienImmobilier;
}
