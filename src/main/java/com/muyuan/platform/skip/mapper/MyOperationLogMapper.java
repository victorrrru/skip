package com.muyuan.platform.skip.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.muyuan.platform.skip.entity.CtrlRes;
import com.muyuan.platform.skip.entity.db.MyOperationLog;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 操作指令表
 * 
 * @author fanwenwu
 * @email 
 * @date 2018-11-23 15:43:52
 */
@Repository
public interface MyOperationLogMapper extends BaseMapper<MyOperationLog> {

    List<CtrlRes> selectCtrl();
	
}
