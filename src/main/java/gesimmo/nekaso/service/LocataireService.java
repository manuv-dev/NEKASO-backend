package gesimmo.nekaso.service;

import java.util.List;

import gesimmo.nekaso.dto.LocataireDTO;

public interface LocataireService {
    LocataireDTO createLocataire(LocataireDTO locataireDTO);

    LocataireDTO getLocataireByUserId(Long userId);

    LocataireDTO getLocataireById(Long id);

    List<LocataireDTO> getAllLocataires();

    LocataireDTO updateLocataire(Long id, LocataireDTO locataireDTO);

    void deleteLocataire(Long id);

    boolean existsByUserId(Long userId);
}
