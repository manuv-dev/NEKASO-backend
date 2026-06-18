package gesimmo.nekaso.entity;

import java.util.List;

import gesimmo.nekaso.auth.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "locataire")
@Data
@NoArgsConstructor
@AllArgsConstructor

@PrimaryKeyJoinColumn(name = "user_id")
public class Locataire extends User {


    @OneToMany(mappedBy = "locataire")
    private List<DemandeVisite> demandevistes;
    @OneToMany(mappedBy = "locataire")
    private List<ContratBail> contratLocations;
  
}
