package gesimmo.nekaso.entity;

import java.util.ArrayList;
import java.util.List;

import gesimmo.nekaso.auth.entity.User;
import jakarta.persistence.Entity;

import jakarta.persistence.OneToMany;

import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "gestionnaire")
@Data

@PrimaryKeyJoinColumn(name = "user_id")
public class Gestionnaire extends User {

	@OneToMany(mappedBy = "gestionnaire")
	private List<BienImmobilier> biens = new ArrayList<>();
}
