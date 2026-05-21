package gesimmo.nekaso.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import gesimmo.nekaso.entity.User;
import gesimmo.nekaso.repository.UserRepository;
import gesimmo.nekaso.service.UtilisateurService;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UserRepository userRepository;

    public UtilisateurServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
