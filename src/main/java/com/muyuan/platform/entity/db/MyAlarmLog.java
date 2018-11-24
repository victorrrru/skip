package com.muyuan.platform.entity.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


/**
 * 日志表
 * 
 * @author fanwenwu
 * @email 
 * @date 2018-11-23 15:43:53
 */
@Table(name = "my_alarm_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyAlarmLog implements Serializable {
	private static final long serialVersionUID = 1L;
	
	  //主键
    @Id
    private Integer id;
	
	  //设备id

    @Column(name = "device_id")
    private Integer deviceId;
	
	  //设备编号

    @Column(name = "device_no")
    private String deviceNo;
	
	  //警情

    @Column(name = "alarm")
    private String alarm;
	
	  //警情类型

    @Column(name = "log_type")
    private Integer logType;
	
	  //接收时间

    @Column(name = "receive_Time")
    private Date receiveTime;
	
	  //经度

    @Column(name = "lon")
    private BigDecimal lon;
	
	  //纬度

    @Column(name = "lat")
    private BigDecimal lat;
	
	  //速度

    @Column(name = "speed")
    private BigDecimal speed;
	
	  //GPS

    @Column(name = "gps")
    private Integer gps;
	
	  //

    @Column(name = "crt_time")
    private Date crtTime;
	
	  //

    @Column(name = "crt_user")
    private String crtUser;
	
	  //

    @Column(name = "crt_name")
    private String crtName;
	
	  //

    @Column(name = "crt_hsot")
    private String crtHsot;
	
	  //

    @Column(name = "upd_time")
    private Date updTime;
	
	  //

    @Column(name = "upd_user")
    private String updUser;
	
	  //

    @Column(name = "upd_name")
    private String updName;
	
	  //

    @Column(name = "upd_host")
    private String updHost;
	

}
