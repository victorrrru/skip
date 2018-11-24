package com.muyuan.platform.mapper;

import com.muyuan.platform.entity.db.MyOperationLog;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * 操作指令表
 * 
 * @author fanwenwu
 * @email 
 * @date 2018-11-23 15:43:52
 */
@Repository
public interface MyOperationLogMapper extends Mapper<MyOperationLog> {
	
}
