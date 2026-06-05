package gesimmo.nekaso.validation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class SenegalPhoneNumberValidatorTest {

    private SenegalPhoneNumberValidator validator;

    @BeforeEach
    public void setUp() {
        validator = new SenegalPhoneNumberValidator();
    }

    // Tests Orange (77, 78)
    @Test
    public void testValidOrangeNumber77() {
        assertTrue(validator.isValid("77123456789"));
        assertTrue(validator.isValid("+22177123456789"));
        assertTrue(validator.isValid("0077123456789"));
    }

    @Test
    public void testValidOrangeNumber78() {
        assertTrue(validator.isValid("78123456789"));
        assertTrue(validator.isValid("+22178123456789"));
        assertTrue(validator.isValid("0078123456789"));
    }

    // Tests YAS (76)
    @Test
    public void testValidYasNumber() {
        assertTrue(validator.isValid("76123456789"));
        assertTrue(validator.isValid("+22176123456789"));
        assertTrue(validator.isValid("0076123456789"));
    }

    // Tests Expresso (70)
    @Test
    public void testValidExpressoNumber() {
        assertTrue(validator.isValid("70123456789"));
        assertTrue(validator.isValid("+22170123456789"));
        assertTrue(validator.isValid("0070123456789"));
    }

    // Tests ProMobile (75)
    @Test
    public void testValidProMobileNumber() {
        assertTrue(validator.isValid("75123456789"));
        assertTrue(validator.isValid("+22175123456789"));
        assertTrue(validator.isValid("0075123456789"));
    }

    // Tests formats
    @ParameterizedTest
    @ValueSource(strings = {
            "77123456789", // 9 digits bruts
            "+22177123456789", // Format international
            "00221771234567", // Format 00221
            "0771234567" // Format avec 0
    })
    public void testValidFormats(String phoneNumber) {
        assertTrue(validator.isValid(phoneNumber));
    }

    // Tests invalides
    @ParameterizedTest
    @ValueSource(strings = {
            "71123456789", // Opérateur invalide (71)
            "72123456789", // Opérateur invalide (72)
            "777123456", // Trop court
            "771234567890", // Trop long
            "", // Vide
            "abcdefghijk", // Lettres
            "77 123 456 789" // Avec espaces
    })
    public void testInvalidNumbers(String phoneNumber) {
        assertFalse(validator.isValid(phoneNumber));
    }

    // Tests normalisation
    @Test
    public void testNormalizePhoneNumber() {
        assertEquals("771234567", validator.normalizePhoneNumber("77123456789"));
        assertEquals("771234567", validator.normalizePhoneNumber("+22177123456789"));
        assertEquals("771234567", validator.normalizePhoneNumber("00221771234567"));
        assertEquals("771234567", validator.normalizePhoneNumber("0771234567"));
    }

    // Tests détection opérateur
    @Test
    public void testGetOperator() {
        assertEquals("ORANGE", validator.getOperator("77123456789"));
        assertEquals("ORANGE", validator.getOperator("78123456789"));
        assertEquals("YAS", validator.getOperator("76123456789"));
        assertEquals("EXPRESSO", validator.getOperator("70123456789"));
        assertEquals("PROMOBILE", validator.getOperator("75123456789"));
        assertEquals("INCONNU", validator.getOperator("99123456789"));
    }

    // Tests avec null
    @Test
    public void testNullPhoneNumber() {
        assertFalse(validator.isValid(null));
    }

    // Tests cas limites
    @Test
    public void testPhoneNumberWithPrefix() {
        assertTrue(validator.isValid("+22177123456789"));
        assertTrue(validator.isValid("00221771234567"));
    }
}
