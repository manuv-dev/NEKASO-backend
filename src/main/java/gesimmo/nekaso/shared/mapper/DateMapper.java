package gesimmo.nekaso.shared.mapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class DateMapper {

        public String formatLocalDate(LocalDate date,String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }
    public String formatLocalTime(LocalTime date,String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }
    

}
