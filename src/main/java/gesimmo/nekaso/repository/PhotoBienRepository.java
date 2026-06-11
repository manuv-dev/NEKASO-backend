package gesimmo.nekaso.repository;

import gesimmo.nekaso.entity.PhotoBien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface PhotoBienRepository extends JpaRepository<PhotoBien, Long> {
    PhotoBien save(PhotoBien photoBien);

    @Transactional
    void deleteById(Long photoId);
}