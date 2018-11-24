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
 * 位置信息
 * 
 * @author fanwenwu
 * @email 
 * @date 2018-11-23 15:43:52
 */
@Table(name = "my_pos_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyPosLog implements Serializable {
	private static final long serialVersionUID = 1L;
	
	  //主键
    @Id
    private Integer id;
	
	  //设备编号

    @Column(name = "device_no")
    private String deviceNo;
	
	  //经度

    @Column(name = "lon")
    private BigDecimal lon;
	
	  //维度

    @Column(name = "lat")
    private BigDecimal lat;
	
	  //速度

    @Column(name = "speed")
    private BigDecimal speed;
	
	  //方向

    @Column(name = "dre")
    private Integer dre;
	
	  //GPS

    @Column(name = "gps")
    private Integer gps;
	
	  //记录时间

    @Column(name = "crt_time")
    private Date crtTime;
	

}
