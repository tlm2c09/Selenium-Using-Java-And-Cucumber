package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private static final Logger logger = LogManager.getLogger(DateUtil.class.getSimpleName());
    public static String formatDate(String dateString, String dateFormat) {
        String value;
        if (dateString.equalsIgnoreCase("today")) {
            value = getCurrentDate(dateFormat);
        } else {
            Date date = addCustomDurationToCurrentDate(dateString);
            value = formatDate(date, dateFormat);
        }
        return value;
    }

    public static String formatDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String getCurrentDate(String dateFormat) {
        return formatDate(String.valueOf(new Date()), dateFormat);
    }

    public static Date addCustomDurationToCurrentDate(String testData) {
        int days = 0;
        int months = 0;
        int years = 0;
        String[] parts = testData.split("[a-zA-Z]+");
        logger.info(Arrays.stream(parts).toList());

        for (String part : parts) {
            if (part.isEmpty()) {
                continue;
            }
            int value = Integer.parseInt(part.substring(0, part.length() - 1));
            char unit = part.charAt(part.length() - 1);
            switch (unit) {
                case 'D' -> days = value;
                case 'M' -> months = value;
                case 'Y' -> years = value;
                default -> throw new IllegalArgumentException("Invalid unit: " + unit);
            }
        }
        return addDurationToCurrentDate(days, months, years);
    }

    private static Date addDurationToCurrentDate(int days, int months, int years) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if (days != 0) {
            calendar.add(Calendar.DAY_OF_YEAR, days);
        }
        if (months != 0) {
            calendar.add(Calendar.MONTH, months);
        }
        if (years != 0) {
            calendar.add(Calendar.YEAR, years);
        }
        return calendar.getTime();
    }

}
