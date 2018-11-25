package com.muyuan.platform.skip.entity;

import lombok.Data;

/**
 * @author 范文武
 * @date 2018/11/19 11:28
 */
@Data
public class CommonInfo {
    //数据帧标识（0上行， 1下行）
    private Integer dataType;
    //设备编号
    private String deviceNo;
    //数据长度
    private Integer dataLength;
    //指令类型
    private String type;
    //剩余数据
    private String data;
}
