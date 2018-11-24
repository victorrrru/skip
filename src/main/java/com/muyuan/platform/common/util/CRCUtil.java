package com.muyuan.platform.common.util;

/**
 * @author 范文武
 * @date 2018/11/21 11:09
 */
public class CRCUtil {

    public static void main(String[] args) {
        String str = "A55A1302008112010000120A0D161833";
        String i = crc16(ByteUtils.byteToUnint(toBytes(str)));
        System.out.println(i);
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


    /**
     * crc16算法
     *
     * @author fww
     */
    public static String crc16(int[] dat) {
        int i, j;
        int crc16 = 0;
        for (i = 0; i < dat.length; i++) {
            // CRC = BYTE xor CRC
            crc16 ^= dat[i];
            for (j = 0; j < 8; j++) {
                //如果CRC最后一位为1右移一位后carry=1
                if ((crc16 & 0x01) == 1) {
                    crc16 = ((crc16 >> 1) ^ 0xA001);
                } else {
                    //如果CRC最后一位为0则只将CRC右移一位
                    crc16 = (crc16 >> 1);
                }
            }
        }
        return Integer.toHexString(crc16);
    }
}
