package com.muyuan.platform.skip.common.util;

import java.time.LocalDateTime;

/**
 * @author 范文武
 * @date 2018/11/20 16:51
 */
public class DateUtil {

    public static void main(String[] args) {
        String date = getDate("090A0D142B03");
        System.out.println(date);
    }

    public static String getDate(String str) {
        String year = String.format("%02d", Integer.parseInt(str.substring(0, 2), 16));
        String month = String.format("%02d", Integer.parseInt(str.substring(2, 4), 16));
        String day = String.format("%02d", Integer.parseInt(str.substring(4, 6), 16));
        String hour = String.format("%02d", Integer.parseInt(str.substring(6, 8), 16));
        String minute = String.format("%02d", Integer.parseInt(str.substring(8, 10), 16));
        String second = String.format("%02d", Integer.parseInt(str.substring(10, 12), 16));
        return "20" + String.format("%s-%s-%s %s:%s:%s", year, month, day, hour, minute, second);
    }

    /**
     * 得到时间的十六进制
     * @author fww
     */
    public static String getDateHex() {
        LocalDateTime now = LocalDateTime.now();
        String year = Integer.toHexString(now.getYear() - 2000);
        year = year.length() == 1 ? "0" + year : year;
        String month = Integer.toHexString(now.getMonth().getValue());
        month = month.length() == 1 ? "0" + month : month;
        String day = Integer.toHexString(now.getDayOfMonth());
        day = day.length() == 1 ? "0" + day : day;
        String hour = Integer.toHexString(now.getHour());
        hour = hour.length() == 1 ? "0" + hour : hour;
        String minute = Integer.toHexString(now.getMinute());
        minute = minute.length() == 1 ? "0" + minute : minute;
        String second = Integer.toHexString(now.getSecond());
        second = second.length() == 1 ? "0" + second : second;
        return year + month + day + hour + minute + second;
    }
}
