package com.muyuan.platform.skip.common.util;

/**
 * @author 范文武
 * @date 2018/11/19 16:48
 */
public class HexTwoUtils {

    public static void main(String[] args) {
        System.out.println(hexStringToByte("ED4903"));
    }

    /**
     * 16进制-》2进制  字符串
     * @author fww
     */
    public static String hexStringToByte(String hex) {
        String cover = Integer.toBinaryString(1 << 4).substring(1);
        StringBuilder res = new StringBuilder();
        int len = (hex.length() / 2);
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            String str1 = Integer.toBinaryString(toByte(achar[pos]));
            String str2 = Integer.toBinaryString(toByte(achar[pos + 1]));
            res.append(str1.length() < 4 ? cover.substring(str1.length()) + str1 : str1)
                    .append(str2.length() < 4 ? cover.substring(str2.length()) + str2 : str2);
        }
        return res.toString();
    }

    private static byte toByte(char c) {
        byte b = (byte) "0123456789abcdef".indexOf(c);
        if (b == -1) {
            b = (byte) "0123456789ABCDEF".indexOf(c);
        }
        return b;
    }

    /**
     * 16进制转10进制(ip)
     * @author fww
     */
    public static String hexStringToTen(String hex) {
        String one = hex.substring(0, 2);
        String two = hex.substring(2, 4);
        String three = hex.substring(4, 6);
        String four = hex.substring(6, 8);
        StringBuilder builder = new StringBuilder();
        return builder.append(Integer.parseInt(one, 16)).append(".").append(Integer.parseInt(two, 16))
                .append(".").append(Integer.parseInt(three, 16)).append(".").append(Integer.parseInt(four, 16))
                .toString();
    }

}
