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
 * 设备信息
 *
 * @author fanwenwu
 * @email
 * @date 2018-11-23 15:43:52
 */
@Table(name = "my_device_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyDeviceInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    @Id
    private Integer id;

    //设备编号

    @Column(name = "device_no")
    private String deviceNo;

    //车辆编号

    @Column(name = "vehicle_no")
    private String vehicleNo;

    //区域主键

    @Column(name = "area_id")
    private Integer areaId;

    //分公司

    @Column(name = "company")
    private String company;

    //地址

    @Column(name = "address")
    private String address;

    //是否在线(0离线，1在线)

    @Column(name = "online")
    private Integer online;

    //解锁状态（0解锁，1上锁）

    @Column(name = "lock")
    private Integer lock;

    //启用设防（0禁用， 1启用）

    @Column(name = "fortification")
    private Integer fortification;

    //信号

    @Column(name = "signal")
    private Integer signal;

    //注册时间

    @Column(name = "regist_time")
    private Date registTime;

    //通信时间

    @Column(name = "communication_time")
    private Date communicationTime;

    //发料提示

    @Column(name = "note")
    private String note;

    //经度

    @Column(name = "lon")
    private BigDecimal lon;

    //纬度

    @Column(name = "lat")
    private BigDecimal lat;

    //报警

    @Column(name = "alarm")
    private String alarm;

    //mac

    @Column(name = "mac")
    private String mac;

    //ip

    @Column(name = "ip")
    private String ip;

    //port

    @Column(name = "port")
    private Integer port;

    //方向

    @Column(name = "direction")
    private String direction;

    //SIM

    @Column(name = "sim")
    private String sim;

    //硬件版本

    @Column(name = "hardware")
    private String hardware;

    //软件版本

    @Column(name = "software")
    private String software;

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
