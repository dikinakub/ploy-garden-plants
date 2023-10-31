package com.example.ploygardenplants.util;

import com.example.ploygardenplants.constant.CommonConstant;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;
import org.springframework.stereotype.Component;
import java.time.DayOfWeek;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.TimeZone;

@Component
public class DateUtil {

    public static final ZoneId defaultZoneId = ZoneId.systemDefault();

    public static final Locale ENG_LOCALE = new Locale("en", "US");
    public static final Locale THAI_LOCALE = new Locale("th", "TH");

    public static final String STANDARD_DATE_PATTERN = "dd/MM/yyyy";
    public static final String STANDARD_TIME_PATTERN = "HH:mm:ss";
    public static final String TIME_TRANSACTION_PATTERN = "HH:mm:ss.SSS";
    public static final String ISO_8601_LOCAL_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String ISO_8601_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    public static final String ISO_8601_DATE_TIME_ZONE = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String STATEMENT_DATE_FORMAT = "dd/MM/yy";
    public static final String STANDARD_DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm:ss";
    public static final String DEFAULT_CONVERT_DATE_PATTERN = "ddMMyyyy";
    public static final String YYYYMMDD_DATE_PATTERN = "yyyyMMdd";
    public static final String YYMMDD_DATE_PATTERN = "yyMMdd";
    public static final String HHMMSS_TIME_PATTERN = "HHmmss";
    public static final String ISO_8601_DATE = "yyyy-MM-dd";
    public static final String DD_MM_YYYY_DATE = "dd-MM-yyyy";
    public static final String YYYY_MM_DD_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DD_MM_YYYY_DATE_TIME_PATTERN = "dd-MM-yyyy HH:mm:ss";
    public static final String HHMM_TIME_PATTERN = "HH:mm";
    public static final String YYYYMMDDHH24MMSS_SSSSSS_PATTERN = "yyyyMMddHHmmssSSSSSS";
    public static final String YYYYMMDDHH24MMSS_PATTERN = "yyyyMMddHHmmss";
    public static final String YYMMDDHH24MMSS_PATTERN = "yyMMddHHmmss";

    public static final DateTimeFormatter ISO_LOCAL_DATE = DateTimeFormatter.ISO_LOCAL_DATE;

    public boolean isValidDate(String date) {
        DateFormat sdf = new SimpleDateFormat(YYYYMMDD_DATE_PATTERN);
        sdf.setLenient(false);
        try {
            sdf.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public boolean isValidDateTimeFormatter(String dateStr, DateTimeFormatter dateFormatter) {

        try {
            LocalDate.parse(dateStr, dateFormatter);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    public boolean isValidDateFormat(String date, String fomat) {
        DateFormat sdf = new SimpleDateFormat(fomat);
        sdf.setLenient(false);
        try {
            sdf.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public String parseDateFormat(Date dt, String fomat) {
        if (dt == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(fomat);
        return sdf.format(dt);
    }

    public String parseDateFormat(LocalDateTime dt, String fomat) {
        if (dt == null) {
            return "";
        }
        DateTimeFormatter format = DateTimeFormatter.ofPattern(fomat);
        return dt.format(format);
    }

    public String parseDateDefaultFormat(LocalDate dt) {
        if (dt == null) {
            return "";
        }
        DateTimeFormatter format = DateTimeFormatter.ofPattern(ISO_8601_DATE);
        return dt.format(format);
    }

    public String parseDatePatternFormat(LocalDate dt, String pattern) {
        if (dt == null) {
            return "";
        }
        DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern);
        return dt.format(format);
    }

    public String parseLocalDateThaiFormat(LocalDate dt) {
        if (dt == null) {
            return "";
        }
        DateTimeFormatter format = DateTimeFormatter.ofPattern(DD_MM_YYYY_DATE);
        return dt.format(format);
    }

    public String parseDateThaiFormat(LocalDateTime dt) {
        if (dt == null) {
            return "";
        }
        DateTimeFormatter format = DateTimeFormatter.ofPattern(DD_MM_YYYY_DATE_TIME_PATTERN);
        return dt.format(format);
    }

    /**
     *
     * @param dt yyyy-MM-dd
     * @return dd-MM-yyyy
     */
    public String parseStringDateThaiFormat(String dt) {
        if (dt == null) {
            return "";
        }
        LocalDate ld = parseStringToLocalDateFormat(dt, ISO_8601_DATE);
        DateTimeFormatter format = DateTimeFormatter.ofPattern(DateUtil.DD_MM_YYYY_DATE);
        return ld.format(format);
    }

    public String parseDateDefaultFormat(LocalDateTime dt) {
        if (dt == null) {
            return "";
        }
        DateTimeFormatter format = DateTimeFormatter.ofPattern(YYYY_MM_DD_DATE_TIME_PATTERN);
        return dt.format(format);
    }

    public LocalDateTime parseLocalDateTimeDefaultFormat(LocalDateTime dt) {
        if (dt == null) {
            return null;
        }
        DateTimeFormatter format = DateTimeFormatter.ofPattern(YYYY_MM_DD_DATE_TIME_PATTERN);
        return LocalDateTime.parse(dt.format(format), format);
    }

    public LocalDateTime convertDateToLocalDateTime(Date dateToConvert) {
        if (dateToConvert == null) {
            return null;
        }
        return LocalDateTime.ofInstant(dateToConvert.toInstant(), defaultZoneId);
    }

    public LocalDate convertDateToLocalDate(Date dateToConvert) {
        if (dateToConvert == null) {
            return null;
        }
        return dateToConvert.toInstant().atZone(defaultZoneId).toLocalDate();
    }

    public Date convertLocalDateTimeToDate(LocalDateTime dateToConvert) {
        return java.sql.Timestamp.valueOf(dateToConvert);
    }

    public Date convertLocalDateToDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }

    public Date convertLocalDateToDateZone(LocalDate dateToConvert) {
        if (dateToConvert == null) {
            return null;
        }
        return Date.from(dateToConvert.atStartOfDay(defaultZoneId).toInstant());
    }

    public Instant parseStringDefaultFormatToInstant(String dateStr) {
        if (dateStr == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD_DATE_TIME_PATTERN);
        return LocalDateTime.parse(dateStr, formatter).atZone(DateUtil.defaultZoneId).toInstant();
    }

    public Instant parseStringThaiFormatToInstant(String dateStr) {
        if (dateStr == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DD_MM_YYYY_DATE_TIME_PATTERN);
        return LocalDateTime.parse(dateStr, formatter).atZone(DateUtil.defaultZoneId).toInstant();
    }

    public Instant parseLocalDateTimeToInstant(LocalDateTime localDateTimeToConvert) {
        if (localDateTimeToConvert == null) {
            return null;
        }
        return localDateTimeToConvert.atZone(DateUtil.defaultZoneId).toInstant();
    }

    public LocalDateTime parseStringToLocalDateTimeFormat(String dateStr, String pattern) {
        if (dateStr == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(dateStr, formatter);
    }

    public LocalDate parseStringToLocalDateFormat(String dateStr, String pattern) {
        if (dateStr == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(dateStr, formatter);
    }

    public String parseStringToLocalDateFormat(String dateStr, String srcPattern, String destPattern) {
        if (dateStr == null || dateStr.equals("null")) // wait chain fix string null
        {
            return null;
        }
        LocalDate tmp = parseStringToLocalDateFormat(dateStr, srcPattern);
        return parseDatePatternFormat(tmp, destPattern);
    }

    public Date parseStringToDateFormat(String dateStr, String pattern) {
        if (dateStr == null || dateStr.equals("null")) {
            return null; // wait chain fix string null
        }
        Date dateResult = null;
        try {
            SimpleDateFormat sdf = getSimpleDateFormat(pattern, ENG_LOCALE);
            dateResult = sdf.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
        return dateResult;
    }

    public SimpleDateFormat getSimpleDateFormat(String pattern, Locale locale) {
        return new SimpleDateFormat(pattern, locale);
    }

    public LocalDateTime getLocalDateTime(Instant instant) {
        return LocalDateTime.ofInstant(instant, defaultZoneId);
    }

    public String convertMillis(long ms) {
        StringBuilder sb = new StringBuilder();
        long totalSecs = ms / 1000;
        long hours = (totalSecs / 3600);
        long mins = (totalSecs / 60) % 60;
        long secs = totalSecs % 60;
        if (hours > 0) {
            sb.append(hours);
            sb.append(" " + CommonConstant.TIME_UNIT_HOUR + " ");
            sb.append(mins);
            sb.append(" " + CommonConstant.TIME_UNIT_MINUTE + " ");
            sb.append(secs);
            sb.append(" " + CommonConstant.TIME_UNIT_SEC);
        } else if (mins > 0) {
            sb.append(mins);
            sb.append(" " + CommonConstant.TIME_UNIT_MINUTE + " ");
            sb.append(secs);
            sb.append(" " + CommonConstant.TIME_UNIT_SEC);
        } else if (secs > 0) {
            sb.append(secs);
            sb.append(" " + CommonConstant.TIME_UNIT_SEC);
        } else {
            sb.append(ms);
            sb.append(" " + CommonConstant.TIME_UNIT_MILLI_SEC);
        }
        return sb.toString();
    }

    public Calendar parseCutOffTimeStringToCalendar(Date currentDateTime, String cutOffTimeStr) {
        String[] cutOffTime = cutOffTimeStr.split("\\."); // e.g. 11.00
        String hour = cutOffTime[0];
        String minute = cutOffTime[1];

        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDateTime);
        cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hour));
        cal.set(Calendar.MINUTE, Integer.valueOf(minute));
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal;
    }

    public Calendar convertDateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(defaultZoneId));
        cal.setTime(date);
        return cal;
    }

    public LocalDateTime getNextLocalDateTime(LocalDateTime currentdate, Long unitDate) {
        return currentdate.plusDays(unitDate);
    }

    public LocalDate getNextLocalDate(LocalDateTime currentdate, int unitDate) {
        return currentdate.plusDays(unitDate).toLocalDate();
    }

    public Date getNextDate(Date currentdate, int unitDate) {
        LocalDate localDate = convertDateToLocalDate(currentdate);
        localDate = localDate.plusDays(unitDate);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public Date getPreviousDate(Date currentdate, int unitDate) {
        LocalDate localDate = convertDateToLocalDate(currentdate);
        localDate = localDate.minusDays(unitDate);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public boolean isWeekend(Date currentdate) {
        LocalDate localdate = convertDateToLocalDate(currentdate);
        DayOfWeek day = DayOfWeek.of(localdate.get(ChronoField.DAY_OF_WEEK));
        switch (day) {
            case SATURDAY:
            case SUNDAY:
                return true;
            default:
                return false;
        }
    }

    public boolean isSameDay(Date date1, Date date2) {
        LocalDate localDate1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDate2 = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate1.isEqual(localDate2);
    }

    public Boolean isSameDay(LocalDateTime date1, LocalDateTime date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        return date1.toLocalDate().isEqual(date2.toLocalDate());
    }

}
