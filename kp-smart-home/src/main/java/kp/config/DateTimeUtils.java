package kp.config;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateTimeUtils {

    public static LocalDateTime fromDate(Date date){
        if(date == null)
            return null;
        return LocalDateTime.from(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
    }

    public static Date fromLocalDateTime(LocalDateTime localDateTime){
        if(localDateTime == null)
            return null;
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
