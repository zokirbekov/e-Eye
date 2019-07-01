package uz.zokirbekov.e_eye.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {
    private static DateFormatter formatter;

    public static DateFormatter getFormatter() {
        if (formatter == null)
            formatter = new DateFormatter();
        return formatter;
    }

    public String dateToString(Date date)
    {
        SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy");
        return f.format(date);
    }
}
