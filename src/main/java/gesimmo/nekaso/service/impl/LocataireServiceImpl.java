package gesimmo.nekaso.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import gesimmo.nekaso.dto.LocataireDTO;
import gesimmo.nekaso.entity.Locataire;
import gesimmo.nekaso.mapper.LocataireMapper;
import gesimmo.nekaso.repository.LocataireRepository;
import gesimmo.nekaso.service.LocataireService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocataireServiceImpl implements LocataireService {

    private final LocataireRepository locataireRepository;
    private final LocataireMapper locataireMapper;

    @Override
    public LocataireDTO createLocataire(LocataireDTO locataireDTO) {
        Locataire locataire = locataireMapper.toEntity(locataireDTO);
        Locataire savedLocataire = locataireRepository.save(locataire);
        return locataireMapper.toDTO(savedLocataire);
    }

    @Override
    public LocataireDTO getLocataireByUserId(Long userId) {
        Locataire locataire = locataireRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Locataire not found for userId: " + userId));
        return locataireMapper.toDTO(locataire);
    }

    @Override
    public LocataireDTO getLocataireById(Long id) {
        Locataire locataire = locataireRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Locataire not found with id: " + id));
        return locataireMapper.toDTO(locataire);
    }

    @Override
    public List<LocataireDTO> getAllLocataires() {
        return locataireRepository.findAll().stream()
                .map(locataireMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LocataireDTO updateLocataire(Long id, LocataireDTO locataireDTO) {
        Locataire locataire = locataireRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Locataire not found with id: " + id));
        
        // Update can be extended based on requirements
        Locataire updatedLocataire = locataireRepository.save(locataire);
        return locataireMapper.toDTO(updatedLocataire);
    }

    @Override
    public void deleteLocataire(Long id) {
        if (!locataireRepository.existsById(id)) {
            throw new IllegalArgumentException("Locataire not found with id: " + id);
        }
        locataireRepository.deleteById(id);
    }

    @Override
    public boolean existsByUserId(Long userId) {
        return locataireRepository.existsByUserId(userId);
    }
}
