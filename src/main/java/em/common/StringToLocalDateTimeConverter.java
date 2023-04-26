package em.common;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

@Component
public class StringToLocalDateTimeConverter {

    private static final String DATE_PATTERN = "";
    private static final Pattern pattern = Pattern.compile(DATE_PATTERN);

    public void validateDateFormat(String date){
        if(!pattern.matcher(date).matches()){
            throw new IllegalArgumentException("날짜 입력 형식을 확인해주세요. ex) 2021-01-01 00:00:00");
        }
    }

    public LocalDateTime convertStringToLocalDateTime(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
        return localDateTime;
    }
}
