package com.muyuan.platform.skip.common.util;

/**
 * @author 范文武
 * @date 2018/11/19 17:57
 */
public class CharsetUtils {

    public static void main(String[] args) {
        System.out.println(changeByte("01c6dbe9"));
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
    public static String changeByte(String str) {
        if (str.length() % 2 != 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = str.length() - 1; i > 0; i -= 2) {
            builder.append(str.charAt(i - 1)).append(str.charAt(i));
        }
        return builder.toString();
    }
}
