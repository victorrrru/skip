package com.muyuan.platform.skip.common.util;

import com.mchange.lang.ByteUtils;

/**
 * @author 范文武
 * @date 2018/11/21 11:09
 */
public class CRCUtil {

    public static void main(String[] args) {
        String str = "A55A1302008112010000120A0D161833";
        String i = crc16(ByteUtil.byteToUnit(ByteUtil.toBytes(str)));
        System.out.println(i);
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
        String hex = Integer.toHexString(crc16);
        StringBuilder builder = new StringBuilder(Integer.toHexString(crc16));
        for (int a = 0; a < 4 - hex.length(); a++) {
            builder.insert(0, "0");
        }
        return builder.toString();
    }
}
