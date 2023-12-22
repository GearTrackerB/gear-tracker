package com.bnksystem.trainning1team.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String getCurrentDate() {
        return getCurrentDate("yyyyMMdd");
    }

    public static String getCurrentDate(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format == null || "".equals(format) ? "yyyyMMdd" : format);
        return sdf.format(new Date());
    }
}
