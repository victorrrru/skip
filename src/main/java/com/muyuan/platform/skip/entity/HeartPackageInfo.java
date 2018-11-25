package com.muyuan.platform.skip.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author 范文武
 * @date 2018/11/19 14:03
 */
@Data
@Accessors(chain = true)
public class HeartPackageInfo {
    //信号（0解锁，1上锁）
    private Integer signal;
    //解锁状态（0解锁，1上锁）
    private Integer lock;
    //启用设防（0禁用， 1启用）
    private Integer fortification;
    //报警
    private String alarm;
    //无效
    private String acc;
    //传感器方向
    private Integer sensorDir;
    //gps方向
    private Integer gpsDir;
    //遥控器版本
    private String controlVer;
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
    //方向传感器数据
    private String dirSensorData;
    //主要电源电压
    private Integer majorMainsInput;
    //内部电源电压
    private Integer innerMainsInput;
    //电机电流
    private Integer motorElectricity;
}
