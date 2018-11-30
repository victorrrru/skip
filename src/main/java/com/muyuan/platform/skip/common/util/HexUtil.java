package com.muyuan.platform.skip.common.util;

/**
 * @author 范文武
 * @date 2018/11/19 17:57
 */
public class HexUtil {

    public static void main(String[] args) {
        System.out.println(reverseHex("01c6dbe9"));
    }

    /**
     * 转换十六进制编码为字符串
     *
     * @author fww
     */
    public static String toStringHex(String s) {
        if ("0x".equals(s.substring(0, 2))) {
            s = s.substring(2);
        }
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "gbk");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    /**
     * 转换字节，低字节在前
     *
     * @author fww
     */
    public static String reverseHex(String str) {
        if (str.length() % 2 != 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = str.length() - 1; i > 0; i -= 2) {
            builder.append(str.charAt(i - 1)).append(str.charAt(i));
        }
        return builder.toString();
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
        return String.valueOf(Integer.parseInt(one, 16)) + "." + Integer.parseInt(two, 16) +
                "." + Integer.parseInt(three, 16) + "." + Integer.parseInt(four, 16);
    }
}
