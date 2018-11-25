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
 * 站点区域表
 * 
 * @author fanwenwu
 * @email 
 * @date 2018-11-23 15:43:53
 */
@TableName("my_area")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyArea implements Serializable {
    private static final long serialVersionUID = 1L;

    // 主键
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    // 设备主键
    @TableField(value = "device_id")
    private Integer deviceId;

    // 设备编号
    @TableField(value = "device_no")
    private String deviceNo;

    // 版本信息
    @TableField(value = "ver")
    private String ver;

    // 厂区标识
    @TableField(value = "flag")
    private String flag;

    // 坐标点数
    @TableField(value = "num")
    private String num;

    // 坐标信息
    @TableField(value = "pots")
    private String pots;

    //
    @TableField(value = "time")
    private Date time;

}
