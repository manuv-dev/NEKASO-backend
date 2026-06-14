package gesimmo.nekaso.service;

import gesimmo.nekaso.dto.PaiementDTO;
import gesimmo.nekaso.entity.ContratBail;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
}
