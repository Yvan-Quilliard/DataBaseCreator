package fr.databasebuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TransformValueSQL {

    public static String trasformValue(String type, String value) {
        //trasform boolean
        if(type.equals("boolean"))
            return Boolean.parseBoolean(value) ? "1" : "0";

        //trasform Date
        if(type.equals("Date")) {
            SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            Date date = null;
            try {
                date = inputFormat.parse(value);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return outputFormat.format(date);
        }
        return value;
    }

}
