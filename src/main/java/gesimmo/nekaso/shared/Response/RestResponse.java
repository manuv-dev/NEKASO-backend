package gesimmo.nekaso.shared.Response;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public record RestResponse<T>(
    boolean success,

    T message,
    HttpStatus status,
    LocalDateTime timestamp
) {

    public RestResponse(boolean success,HttpStatus status, T message) {
        this(success, message, status, LocalDateTime.now());
    }
    public static <T> RestResponse<T> success(T message, HttpStatus status ) {
        return new RestResponse<>(true,message,status, LocalDateTime.now());
    }

    public static <T> RestResponse<T> error(T message, HttpStatus status ) {
        return new RestResponse<>(false, message,status, LocalDateTime.now());
    }
    public static <T> RestResponse<T> errors(T message,HttpStatus status ) {
        return new RestResponse<>(false, message, HttpStatus.BAD_REQUEST, LocalDateTime.now());
    }
}  