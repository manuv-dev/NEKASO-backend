package gesimmo.nekaso.repository;

import gesimmo.nekaso.entity.PhotoBien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PhotoBienRepository extends JpaRepository<PhotoBien, Long> {

    List<PhotoBien> findByBienId(Long bienId);

    @Transactional
    void deleteByBienId(Long bienId);
}