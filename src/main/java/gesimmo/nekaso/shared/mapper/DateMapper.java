package gesimmo.nekaso.shared.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class DateMapper {

        public String formatLocalDate(LocalDate date,String pattern) {
                if (date == null) {
        return null;
    }


        return date.format(DateTimeFormatter.ofPattern(pattern));
    }
    public String formatLocalDateTime(LocalDateTime date,String pattern) {
        if (date == null) {
            return null;
        }
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }
    

}
