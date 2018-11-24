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
 * 发料信息

 * 
 * @author fanwenwu
 * @email 
 * @date 2018-11-23 15:43:52
 */
@Table(name = "my_shipping_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyShippingInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	  //主键
    @Id
    private String id;
	
	  //订单号

    @Column(name = "order_no")
    private String orderNo;
	
	  //设备主键

    @Column(name = "device_id")
    private Integer deviceId;
	
	  //设备编号

    @Column(name = "device_no")
    private String deviceNo;
	
	  //运输车号

    @Column(name = "truck_num")
    private String truckNum;
	
	  //目的地

    @Column(name = "destination")
    private String destination;
	
	  //发货地

    @Column(name = "source")
    private String source;
	
	  //发货时间

    @Column(name = "delivery_time")
    private Date deliveryTime;
	
	  //签收时间

    @Column(name = "sign_time")
    private Date signTime;
	
	  //已处理状态

    @Column(name = "checked")
    private String checked;
	
	  //发货地编码

    @Column(name = "source_no")
    private String sourceNo;
	

}
