package com.muyuan.platform.skip.entity.db;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 日志表
 * 
 * @author fanwenwu
 * @email 
 * @date 2018-11-23 15:43:53
 */
@TableName("my_device_alarm")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyDeviceAlarm implements Serializable {
    private static final long serialVersionUID = 1L;

    // 主键
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    // 设备id
    @TableField(value = "device_id")
    private Integer deviceId;

    // 设备编号
    @TableField(value = "device_no")
    private String deviceNo;

    // 警情
    @TableField(value = "alarm")
    private String alarm;

    // 警情类型
    @TableField(value = "log_type")
    private Integer logType;

    // 接收时间
    @TableField(value = "receive_Time")
    private Date receiveTime;

    // 经度
    @TableField(value = "lon")
    private BigDecimal lon;

    // 纬度
    @TableField(value = "lat")
    private BigDecimal lat;

    // 速度
    @TableField(value = "speed")
    private BigDecimal speed;

    // GPS
    @TableField(value = "gps")
    private Integer gps;

    //
    @TableField(value = "crt_time")
    private Date crtTime;

    //
    @TableField(value = "crt_user")
    private String crtUser;

    //
    @TableField(value = "crt_name")
    private String crtName;

    //
    @TableField(value = "crt_hsot")
    private String crtHsot;

    //
    @TableField(value = "upd_time")
    private Date updTime;

    //
    @TableField(value = "upd_user")
    private String updUser;

    //
    @TableField(value = "upd_name")
    private String updName;

    //
    @TableField(value = "upd_host")
    private String updHost;

}
