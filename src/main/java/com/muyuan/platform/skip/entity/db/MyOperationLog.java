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
import javax.persistence.*;


/**
 * 操作指令表
 * 
 * @author fanwenwu
 * @email 
 * @date 2018-11-23 15:43:52
 */
@TableName("my_operation_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyOperationLog implements Serializable {
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

    // 操作码
    @TableField(value = "operation_code")
    private String operationCode;

    // 参数
    @TableField(value = "param")
    private String param;

    // 操作时间
    @TableField(value = "operation_time")
    private Date operationTime;

    // 执行时间
    @TableField(value = "execute_time")
    private Date executeTime;

    // 操作次数
    @TableField(value = "operation_num")
    private Integer operationNum;

    // 结果
    @TableField(value = "opt_result")
    private String optResult;


}
