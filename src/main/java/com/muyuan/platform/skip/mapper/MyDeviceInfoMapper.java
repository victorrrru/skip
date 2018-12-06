package com.muyuan.platform.skip.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.muyuan.platform.skip.entity.db.MyDeviceInfo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 设备信息
 * 
 * @author fanwenwu
 * @email 
 * @date 2018-11-23 15:43:52
 */
@Repository
public interface MyDeviceInfoMapper extends BaseMapper<MyDeviceInfo> {
    @Select("SELECT id, ip, device_no AS deviceNo, communication_time AS communicationTime, online FROM my_device_info where online = 1")
    List<MyDeviceInfo> listDevice();
}
