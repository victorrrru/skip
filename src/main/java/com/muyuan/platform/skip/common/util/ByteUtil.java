package com.muyuan.platform.skip.common.util;

/**
 * @author 范文武
 * @date 2018/11/22 19:28
 */
public class ByteUtil {

    public static void main(String[] args) {
        byte[] bytes1 = hexStringToBytes("5AA516010001420135FFD8054D5038332565234301034701383938363034313" +
                "9313631376330353834383038160100017A7257D5411F000000000000320A1EFAFF94");
        System.out.println(byteToHex(bytes1));
    }

    /**
     * Convert hex string to byte[]
     * @author fww
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        if(hexString.length()%2 != 0){
            hexString = "0"+hexString;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * Convert char to byte
     * @author fww
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * byte convert hex
     * @author fww
     */
    public static String byteToHex(byte[] in) {
        int[] x = byteToUnit(in);
        StringBuilder builder = new StringBuilder();
        for (int i : x) {
            builder.append(Integer.toHexString(i));
        }
        return builder.toString();
    }

    /**
     * byte convert hex
     * @author fww
     */
    public static String intToHex(int[] in) {
        StringBuilder builder = new StringBuilder();
        for (int i : in) {
            builder.append(Integer.toHexString(i));
        }
        return builder.toString();
    }

    /**
     * 有符号int转无符号int
     * @author fww
     */
    public static int[] byteToUnit(byte[] v) {
        int[] res = new int[v.length];
        for (int i = 0; i < v.length; i++) {
            res[i] = v[i] & 0xff;
        }
        return res;
    }

    /**
     * 16进制数组变成string，如[65,97]->"Aa"   byte->char
     * @author fww
     */
    public static String byteToHexStr(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            char res = (char) bytes[i];
            builder.append(res);
        }
        return builder.toString();
    }

    /**
     * string变成16进制数组，如"Aa"->[65,97]   char->char
     * @author fww
     */
    public static byte[] hexStrToByte(String hex) {
        byte[] res = new byte[hex.length()];
        for (int i = 0; i < hex.length(); i++) {
            res[i] = (byte) hex.charAt(i);
        }
        return res;
    }

    /**
     * 将16进制字符串转换为byte[]
     *
     * @author fww
     */
    public static byte[] toBytes(String str) {
        if (str == null || "".equals(str.trim())) {
            return new byte[0];
        }
        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }

        return bytes;
    }

}
