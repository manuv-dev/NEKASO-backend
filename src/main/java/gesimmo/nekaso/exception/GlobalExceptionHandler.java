
package gesimmo.nekaso.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import gesimmo.nekaso.shared.Response.RestResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
        EntityNotFoundException.class, 
        jakarta.persistence.EntityNotFoundException.class
    })
    public ResponseEntity<RestResponse> handleEntityNotFoundException(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(RestResponse.error(ex.getMessage(), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(BienNonDisponibleException.class)
    public ResponseEntity<RestResponse> handleBienNonDisponibleException(BienNonDisponibleException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(RestResponse.error(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(EntityExistException.class)
    public ResponseEntity<RestResponse> handleEntityExistException(EntityExistException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(RestResponse.error(ex.getMessage(), HttpStatus.CONFLICT));
    }

    @ExceptionHandler(DemandeLocationException.class)
    public ResponseEntity<RestResponse> handleDemandeLocationException(DemandeLocationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(RestResponse.error(ex.getMessage(), HttpStatus.CONFLICT));
    }

    @ExceptionHandler(PhotoCountExceededException.class)
    public ResponseEntity<Map<String, String>> handlePhotoCountExceededException(PhotoCountExceededException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RestResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(RestResponse.error(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponse> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(RestResponse.error("une erreur est survenue", HttpStatus.INTERNAL_SERVER_ERROR));
    }
}