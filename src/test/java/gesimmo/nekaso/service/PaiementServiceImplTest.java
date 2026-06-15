package gesimmo.nekaso.service;

import gesimmo.nekaso.dto.PaiementDTO;
import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.entity.ContratBail;
import gesimmo.nekaso.entity.DemandeLocation;
import gesimmo.nekaso.entity.Locataire;
import gesimmo.nekaso.entity.Paiement;
import gesimmo.nekaso.entity.enums.MethodePaiement;
import gesimmo.nekaso.entity.enums.Mois;
import gesimmo.nekaso.repository.ContratBailRepository;
import gesimmo.nekaso.repository.PaiementRepository;
import gesimmo.nekaso.repository.QuittanceRepository;
import gesimmo.nekaso.service.impl.PaiementServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaiementServiceImplTest {

    @Mock
    private PaiementRepository paiementRepository;

    @Mock
    private QuittanceRepository quittanceRepository;

    @Mock
    private ContratBailRepository contratBailRepository;

    @InjectMocks
    private PaiementServiceImpl paiementService;

    @Test
    void creerPaiement_shouldPersistPaymentAndLinkContract() {
        PaiementDTO dto = new PaiementDTO();
        dto.setMontant(250000.0);
        dto.setDatePaiement(LocalDate.of(2026, 6, 13));
        dto.setMois("Juin");
        dto.setReference("REF-001");
        dto.setMethodePaiement("WAVE");
        dto.setContratId(42L);

        ContratBail contrat = new ContratBail();
        contrat.setId(42L);
        contrat.setMontantLoyer(250000.0);

        when(contratBailRepository.findById(42L)).thenReturn(Optional.of(contrat));
        when(paiementRepository.save(any(Paiement.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Paiement paiement = paiementService.creerPaiement(dto);

        assertNotNull(paiement);
        assertEquals(250000.0, paiement.getMontant());
        assertEquals(MethodePaiement.WAVE, paiement.getMethodePaiement());
        assertEquals(Mois.Juin, paiement.getMois());
        assertEquals(contrat, paiement.getContrat());
    }

    @Test
    void rechercherPaiements_shouldOnlyReturnPaymentsForRequestedTenantAndContract() {
        Locataire locataire = new Locataire();
        locataire.setId(7L);

        BienImmobilier bien = new BienImmobilier();
        bien.setId(11L);

        DemandeLocation demandeLocation = new DemandeLocation();
        demandeLocation.setLocataire(locataire);
        demandeLocation.setBien(bien);

        ContratBail contratExpected = new ContratBail();
        contratExpected.setId(99L);
        contratExpected.setDemandeLocation(demandeLocation);

        Paiement paiementExpected = Paiement.builder()
                .id(1L)
                .montant(100000.0)
                .datePaiement(LocalDate.of(2026, 6, 1))
                .mois(Mois.Juin)
                .methodePaiement(MethodePaiement.OM)
                .contrat(contratExpected)
                .build();

        Paiement paiementOther = Paiement.builder()
                .id(2L)
                .montant(200000.0)
                .datePaiement(LocalDate.of(2026, 6, 2))
                .mois(Mois.Juin)
                .methodePaiement(MethodePaiement.OM)
                .contrat(new ContratBail())
                .build();

        when(paiementRepository.findAll()).thenReturn(List.of(paiementExpected, paiementOther));

        List<Paiement> result = paiementService.rechercherPaiements(null, 11L, 7L, null, null, null, null, null);

        assertEquals(1, result.size());
        assertTrue(result.contains(paiementExpected));
    }
}
