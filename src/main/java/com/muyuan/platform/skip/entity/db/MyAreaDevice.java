package com.muyuan.platform.skip.entity.db;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * 
 * 
 * @author fanwenwu
 * @email 
 * @date 2018-11-23 15:43:53
 */
@TableName("my_area_device")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyAreaDevice implements Serializable {
    private static final long serialVersionUID = 1L;

    // 主键
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    // 设备主键
    @TableField(value = "device_id")
    private Integer deviceId;

    // 设备编码
    @TableField(value = "device_no")
    private String deviceNo;

    // 设备厂区
    @TableField(value = "name")
    private String name;

}
