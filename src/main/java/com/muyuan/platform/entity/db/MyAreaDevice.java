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
 * 
 * 
 * @author fanwenwu
 * @email 
 * @date 2018-11-23 15:43:53
 */
@Table(name = "my_area_device")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyAreaDevice implements Serializable {
	private static final long serialVersionUID = 1L;
	
	  //主键
    @Id
    private Integer id;
	
	  //设备主键

    @Column(name = "device_id")
    private Integer deviceId;
	
	  //设备编码

    @Column(name = "device_no")
    private String deviceNo;
	
	  //设备厂区

    @Column(name = "name")
    private String name;
	

}
