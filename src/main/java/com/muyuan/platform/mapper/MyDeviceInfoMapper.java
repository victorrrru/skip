package com.muyuan.platform.mapper;


import com.muyuan.platform.entity.db.MyDeviceInfo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 设备信息
 * 
 * @author fanwenwu
 * @email 
 * @date 2018-11-23 15:43:52
 */
@Repository
public interface MyDeviceInfoMapper extends Mapper<MyDeviceInfo>, MySqlMapper<MyDeviceInfo> {
	
}
