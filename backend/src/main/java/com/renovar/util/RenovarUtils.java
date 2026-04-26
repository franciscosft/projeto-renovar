package com.renovar.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RenovarUtils {

    private final static Logger log = LoggerFactory.getLogger(RenovarUtils.class);

    public static Date formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            return sdf.parse(date + "");
        } catch (ParseException e) {
            return date;
        }
    }

    public static Date toStartDate(String start) {
        try {
            String startOfDay = start + " 00:00:00";
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            return formatter.parse(startOfDay);
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
            return new Date();
        }
    }

    public static Date toEndDate(String end) {
        try {
            String endOfDay = end + " 23:59:59";
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            return formatter.parse(endOfDay);
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
            return new Date();
        }
    }

}