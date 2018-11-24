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
 * 站点区域表
 * 
 * @author fanwenwu
 * @email 
 * @date 2018-11-23 15:43:53
 */
@Table(name = "my_area")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyArea implements Serializable {
	private static final long serialVersionUID = 1L;
	
	  //主键
    @Id
    private Integer id;
	
	  //设备主键

    @Column(name = "device_id")
    private Integer deviceId;
	
	  //设备编号

    @Column(name = "device_no")
    private String deviceNo;
	
	  //版本信息

    @Column(name = "ver")
    private String ver;
	
	  //厂区标识

    @Column(name = "flag")
    private String flag;
	
	  //坐标点数

    @Column(name = "num")
    private String num;
	
	  //坐标信息

    @Column(name = "pots")
    private String pots;
	
	  //

    @Column(name = "time")
    private Date time;
	

}
