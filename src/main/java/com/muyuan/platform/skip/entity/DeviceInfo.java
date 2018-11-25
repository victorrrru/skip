package com.muyuan.platform.skip.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 范文武
 * @date 2018/11/19 14:03
 */
@Data
@Accessors(chain = true)
public class DeviceInfo {
    private String deviceId;
    private String hardWare;
    private String softWare;
    private String sim;
    private String deviceNo;
    private String ip;
    private Integer port;
    private String ip2;
    private String port2;
    private String heartBeatInterval;
    private String retryInterval;
    private String serialPortOvertime;
    private String severOvertime;
}
