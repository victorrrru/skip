package com.muyuan.platform.mapper;


import com.muyuan.platform.entity.db.MyAlarmLog;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * 日志表
 * 
 * @author fanwenwu
 * @email 
 * @date 2018-11-23 15:43:53
 */
@Repository
public interface MyAlarmLogMapper extends Mapper<MyAlarmLog> {
	
}
