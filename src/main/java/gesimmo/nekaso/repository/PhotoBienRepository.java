package gesimmo.nekaso.repository;

import gesimmo.nekaso.entity.PhotoBien;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface PhotoBienRepository extends JpaRepository<PhotoBien, Long> {
    List<PhotoBien> findByBienImmobilierId(Long bienId);
    @Transactional
    void deleteById(Long photoId);
}