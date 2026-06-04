package gesimmo.nekaso.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "locataire")
@PrimaryKeyJoinColumn(name = "id")
@lombok.Data
@EqualsAndHashCode(callSuper = true)
public class Locataire extends User {

	// Locataire specific fields can be added here if needed

}
