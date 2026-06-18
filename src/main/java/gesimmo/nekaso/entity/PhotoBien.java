package gesimmo.nekaso.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "photo_bien")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhotoBien {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "urlphoto", nullable = false)
	private String urlPhoto;

	@Column(nullable = false)
	private LocalDate dateUpload = LocalDate.now();

	@ManyToOne(optional = false)
	@JoinColumn(name = "bien_id", nullable = false)
	private BienImmobilier bienImmobilier;
}
