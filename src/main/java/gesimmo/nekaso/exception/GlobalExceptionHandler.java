package gesimmo.nekaso.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import gesimmo.nekaso.shared.Response.RestResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<RestResponse> handleEntityNotFoundException(EntityNotFoundException ex) {

        return ResponseEntity.status(404).
        body(RestResponse.error(ex.getMessage(), HttpStatus.NOT_FOUND));
        
    }
    @ExceptionHandler(BienNonDisponibleException.class)
    public ResponseEntity<RestResponse> handleBienNonDisponibleException(BienNonDisponibleException ex) {

        return ResponseEntity.status(400).
        body(RestResponse.error(ex.getMessage(), HttpStatus.BAD_REQUEST));
        
    }

    @ExceptionHandler(EntityExistException.class)
    public ResponseEntity<RestResponse> handleEntityExistException(EntityExistException ex) {

        return ResponseEntity.status(409).
        body(RestResponse.error(ex.getMessage(), HttpStatus.CONFLICT ));
        
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponse> handleException(Exception ex) {

        return ResponseEntity
        .status(500).
        body(RestResponse.error("une erreur est survenue", HttpStatus.INTERNAL_SERVER_ERROR));
        
    }
    @ExceptionHandler(DemandeLocationException.class)
    public ResponseEntity<RestResponse> handleDemandeLocationException(DemandeLocationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(RestResponse.error(ex.getMessage(), HttpStatus.CONFLICT));
    }

    
}
