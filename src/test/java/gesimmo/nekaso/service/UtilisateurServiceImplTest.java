package gesimmo.nekaso.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import gesimmo.nekaso.entity.Gestionnaire;
import gesimmo.nekaso.entity.Locataire;
import gesimmo.nekaso.entity.User;
import gesimmo.nekaso.entity.enums.Role;
import gesimmo.nekaso.repository.GestionnaireRepository;
import gesimmo.nekaso.repository.LocataireRepository;
import gesimmo.nekaso.repository.UserRepository;
import gesimmo.nekaso.service.impl.UtilisateurServiceImpl;

@ExtendWith(MockitoExtension.class)
class UtilisateurServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private LocataireRepository locataireRepository;

    @Mock
    private GestionnaireRepository gestionnaireRepository;

    private UtilisateurServiceImpl utilisateurService;

    @BeforeEach
    void setUp() {
        utilisateurService = new UtilisateurServiceImpl(userRepository, locataireRepository, gestionnaireRepository);
    }

    @Test
    void createLocataireShouldCreateUserAndLocataire() {
        when(userRepository.existsByTelephone("+221771234567")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(locataireRepository.save(any(Locataire.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Locataire created = utilisateurService.createLocataire("Diop", "Awa", "+221771234567", "secret123");

        assertNotNull(created);
        assertNotNull(created.getUser());
        assertEquals(Role.LOCATAIRE, created.getUser().getRole());
        assertEquals("Diop", created.getUser().getNom());
        assertEquals("Awa", created.getUser().getPrenom());
    }

    @Test
    void createGestionnaireShouldUseDefaultValuesAndIncrementNumber() {
        when(userRepository.existsByTelephone(any(String.class))).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(gestionnaireRepository.count()).thenReturn(0L);
        when(gestionnaireRepository.save(any(Gestionnaire.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Gestionnaire created = utilisateurService.createGestionnaire("   ", " ", " ", " ");

        assertNotNull(created);
        assertEquals(1L, created.getNumeroGestionnaire());
        assertEquals("Gestionnaire", created.getUser().getNom());
        assertEquals("Principal", created.getUser().getPrenom());
        assertEquals(Role.GESTIONNAIRE, created.getUser().getRole());
        assertFalse(created.getUser().getTelephone().isBlank());
    }

    @Test
    void createGestionnaireShouldAssignIncrementedNumber() {
        when(userRepository.existsByTelephone("+221771000111")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(gestionnaireRepository.count()).thenReturn(2L);
        when(gestionnaireRepository.save(any(Gestionnaire.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Gestionnaire created = utilisateurService.createGestionnaire("Sow", "Moussa", "+221771000111", "secret123");

        assertNotNull(created);
        assertEquals(3L, created.getNumeroGestionnaire());
        assertEquals(Role.GESTIONNAIRE, created.getUser().getRole());
    }
}
