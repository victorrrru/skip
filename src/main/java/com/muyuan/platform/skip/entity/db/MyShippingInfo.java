package com.muyuan.platform.skip.entity.db;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;


/**
 * 发料信息

 * 
 * @author fanwenwu
 * @email 
 * @date 2018-11-23 15:43:52
 */
@TableName("my_shipping_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyShippingInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    // 主键
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    // 订单号
    @TableField(value = "order_no")
    private String orderNo;

    // 设备主键
    @TableField(value = "device_id")
    private Integer deviceId;

    // 设备编号
    @TableField(value = "device_no")
    private String deviceNo;

    // 运输车号
    @TableField(value = "truck_num")
    private String truckNum;

    // 目的地
    @TableField(value = "destination")
    private String destination;

    // 发货地
    @TableField(value = "source")
    private String source;

    // 发货时间
    @TableField(value = "delivery_time")
    private Date deliveryTime;

    // 签收时间
    @TableField(value = "sign_time")
    private Date signTime;

    // 已处理状态
    @TableField(value = "checked")
    private String checked;

    // 发货地编码
    @TableField(value = "source_no")
    private String sourceNo;

}
