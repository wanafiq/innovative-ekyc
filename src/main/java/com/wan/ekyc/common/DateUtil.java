package com.wan.ekyc.common;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    public static LocalDate parseDate(String d, String format) {
        if (format.isEmpty()) {
            format = DEFAULT_DATE_FORMAT;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(d, formatter);
    }
}
