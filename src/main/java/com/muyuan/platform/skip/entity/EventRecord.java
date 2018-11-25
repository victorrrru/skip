package com.muyuan.platform.skip.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author 范文武
 * @date 2018/11/19 17:31
 */
@Data
@Accessors(chain = true)
public class EventRecord {
    //数据序号
    private String dataId;
    //事件
    private String event;
    //传感器方向
    private Integer sensorDir;
    //gps方向
    private Integer gpsDir;
    //报警类型
    private Integer alarmType;
    //时间
    private String dateTime;
    //卫星数量
    private Integer satelliteNum;
    //定位模式
    private Integer positionMode;
    //经度
    private BigDecimal lon;
    //纬度
    private BigDecimal lat;
    //速度
    private BigDecimal speed;
}
