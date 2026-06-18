package gesimmo.nekaso.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import gesimmo.nekaso.exception.EntityNotFoundException;
import gesimmo.nekaso.repository.UserRepository;
@Service
public class AuthDetailsService implements UserDetailsService  {

    private final UserRepository userRepository;

    public AuthDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String numeroString) throws UsernameNotFoundException {
        return userRepository.findByTelephone(numeroString)
                .orElseThrow(() -> new EntityNotFoundException("User not found with telephone: " + numeroString));
    }


    
}