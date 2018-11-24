package com.muyuan.platform.common.util;

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
}
