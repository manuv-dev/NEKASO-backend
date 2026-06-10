package gesimmo.nekaso.shared.Response;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public record RestResponse<T>(
    boolean success,
    String message,
    T data,
    HttpStatus status,
    LocalDateTime timestamp
) {

    public RestResponse(boolean success,HttpStatus status, String message, T data) {
        this(success, message, data, status, LocalDateTime.now());
    }
    public static <T> RestResponse<T> success(T data, HttpStatus status ) {
        return new RestResponse<>(true, "Données récupérés avec succès", data,status, LocalDateTime.now());
    }
    public static <T> RestResponse<T> error(T data, HttpStatus status ) {
        return new RestResponse<>(false, "Erreur lors de la récupération des données", data,status, LocalDateTime.now());
    }
    public static <T> RestResponse<T> error(T data,String message ) {
        return new RestResponse<>(false, message, data, HttpStatus.BAD_REQUEST, LocalDateTime.now());
    }
}  