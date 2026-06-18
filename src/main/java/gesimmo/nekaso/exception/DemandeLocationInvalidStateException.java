package gesimmo.nekaso.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DemandeLocationInvalidStateException extends DemandeLocationException {
    public DemandeLocationInvalidStateException(String message) {
        super(message);
    }
}
