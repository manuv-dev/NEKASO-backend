package gesimmo.nekaso.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import gesimmo.nekaso.auth.entity.User;
import gesimmo.nekaso.repository.UserRepository;
import gesimmo.nekaso.service.UtilisateurService;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UserRepository userRepository;

    public UtilisateurServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByTelephone(String telephone) {
        return userRepository.findByTelephone(telephone);
    }

    @Override
    public boolean existsByUserTelephone(String telephone) {
        return false;
    }

    @Override
    public boolean existsByTelephone(String telephone) {
        return userRepository.existsByTelephone(telephone);
    }
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
