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
import java.math.BigDecimal;


/**
 * 位置信息
 * 
 * @author fanwenwu
 * @email 
 * @date 2018-11-23 15:43:52
 */
@TableName("my_pos_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyPosLog implements Serializable {
    private static final long serialVersionUID = 1L;

    // 主键
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    // 设备编号
    @TableField(value = "device_no")
    private String deviceNo;

    // 经度
    @TableField(value = "lon")
    private BigDecimal lon;

    // 维度
    @TableField(value = "lat")
    private BigDecimal lat;

    // 速度
    @TableField(value = "speed")
    private BigDecimal speed;

    // 方向
    @TableField(value = "dre")
    private Integer dre;

    // GPS
    @TableField(value = "gps")
    private Integer gps;

    // 记录时间
    @TableField(value = "crt_time")
    private Date crtTime;
}
