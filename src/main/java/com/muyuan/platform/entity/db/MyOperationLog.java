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
 * 操作指令表
 * 
 * @author fanwenwu
 * @email 
 * @date 2018-11-23 15:43:52
 */
@Table(name = "my_operation_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyOperationLog implements Serializable {
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
	
	  //操作码

    @Column(name = "operation_code")
    private String operationCode;
	
	  //参数

    @Column(name = "param")
    private String param;
	
	  //操作时间

    @Column(name = "operation_time")
    private Date operationTime;
	
	  //执行时间

    @Column(name = "execute_time")
    private Date executeTime;
	
	  //操作次数

    @Column(name = "operation_num")
    private Integer operationNum;
	
	  //结果

    @Column(name = "opt_result")
    private String optResult;
	

}
