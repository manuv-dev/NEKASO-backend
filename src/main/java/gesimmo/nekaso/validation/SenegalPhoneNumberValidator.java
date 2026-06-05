package gesimmo.nekaso.validation;

import org.springframework.stereotype.Component;

/**
 * Validateur pour les numéros de téléphone sénégalais.
 * 
 * Opérateurs supportés :
 * - Orange : 77, 78
 * - YAS : 76
 * - Expresso : 70
 * - ProMobile : 75
 * 
 * Format attendu : +221xxxxxxxxx ou 0xxxxxxxxx ou 77xxxxxxxx
 */
@Component
public class SenegalPhoneNumberValidator {

    private static final String[] ORANGE_PREFIXES = { "77", "78" };
    private static final String[] YAS_PREFIXES = { "76" };
    private static final String[] EXPRESSO_PREFIXES = { "70" };
    private static final String[] PROMOBILE_PREFIXES = { "75" };

    /**
     * Valide un numéro de téléphone sénégalais.
     * 
     * @param phoneNumber le numéro de téléphone à valider
     * @return true si le numéro est valide, false sinon
     */
    public boolean isValid(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            return false;
        }

        // Nettoyer le numéro : supprimer les espaces et caractères spéciaux
        String cleaned = phoneNumber.replaceAll("[\\s\\-()]+", "");

        // Normaliser le numéro
        String normalized = normalizePhoneNumber(cleaned);

        if (normalized == null) {
            return false;
        }

        // Vérifier la longueur (doit être 9 chiffres après les préfixes régionaux)
        if (normalized.length() != 9) {
            return false;
        }

        // Vérifier le préfixe opérateur
        String prefix = normalized.substring(0, 2);
        return isValidOperatorPrefix(prefix);
    }

    /**
     * Normalise un numéro de téléphone sénégalais.
     * Convertit tous les formats en format sans le +221 et le 0.
     * 
     * @param phoneNumber le numéro à normaliser
     * @return le numéro normalisé (9 chiffres) ou null si invalide
     */
    public String normalizePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            return null;
        }

        String cleaned = phoneNumber.replaceAll("[\\s\\-()]+", "");

        // Format +221xxxxxxxxx -> xxxxxxxxx
        if (cleaned.startsWith("+221")) {
            return cleaned.substring(4);
        }

        // Format 00221xxxxxxxxx -> xxxxxxxxx
        if (cleaned.startsWith("00221")) {
            return cleaned.substring(5);
        }

        // Format 0xxxxxxxxx -> xxxxxxxxx
        if (cleaned.startsWith("0") && cleaned.length() == 10) {
            return cleaned.substring(1);
        }

        // Format xxxxxxxxx -> xxxxxxxxx
        if (cleaned.length() == 9 && cleaned.matches("\\d+")) {
            return cleaned;
        }

        // Format 77/76/70/75xxxxxxxxx (11 chiffres) -> prendre les 9 premiers
        if (cleaned.length() >= 9 && cleaned.matches("\\d+")) {
            String prefix = cleaned.substring(0, 2);
            if (isValidOperatorPrefix(prefix)) {
                return cleaned.substring(0, 9);
            }
        }

        return null;
    }

    /**
     * Récupère l'opérateur du numéro de téléphone.
     * 
     * @param phoneNumber le numéro de téléphone
     * @return le nom de l'opérateur ou "INCONNU" si non reconnu
     */
    public String getOperator(String phoneNumber) {
        String normalized = normalizePhoneNumber(phoneNumber);
        if (normalized == null) {
            return "INCONNU";
        }

        String prefix = normalized.substring(0, 2);
        if (isInArray(prefix, ORANGE_PREFIXES)) {
            return "ORANGE";
        } else if (isInArray(prefix, YAS_PREFIXES)) {
            return "YAS";
        } else if (isInArray(prefix, EXPRESSO_PREFIXES)) {
            return "EXPRESSO";
        } else if (isInArray(prefix, PROMOBILE_PREFIXES)) {
            return "PROMOBILE";
        }

        return "INCONNU";
    }

    private boolean isValidOperatorPrefix(String prefix) {
        return isInArray(prefix, ORANGE_PREFIXES)
                || isInArray(prefix, YAS_PREFIXES)
                || isInArray(prefix, EXPRESSO_PREFIXES)
                || isInArray(prefix, PROMOBILE_PREFIXES);
    }

    private boolean isInArray(String value, String[] array) {
        for (String item : array) {
            if (item.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
