package com.muyuan.platform.skip.biz;

import com.muyuan.platform.skip.common.util.*;
import com.muyuan.platform.skip.entity.DeviceInfo;
import com.muyuan.platform.skip.entity.db.MyDeviceInfo;
import com.muyuan.platform.skip.mapper.MyDeviceInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

/**
 * @author 范文武
 * @date 2018/11/30 09:50
 */
@Service
public class RegisterBiz {

    @Autowired
    private CommonBiz commonBiz;
    @Autowired
    private MyDeviceInfoMapper myDeviceInfoMapper;

    /**
     * 注册解析入库
     *
     * @author fww
     */
    public DeviceInfo regist(String ip, String head, String data) {
        String deviceNo = HexUtil.reverseHex(head.substring(4, 12));
        DeviceInfo deviceInfo = new DeviceInfo()
                .setMac(data.substring(0, 24))
                .setHardware(data.substring(24, 28))
                .setSoftware(data.substring(28, 32))
                .setSim(data.substring(32, 72))
                .setIp(ip)
                .setIp2(data.substring(92, 100))
                .setPort2(data.substring(100, 104))
                .setHeartBeatInterval(data.substring(104, 106))
                .setRetryInterval(data.substring(106, 108))
                .setSerialPortOvertime(data.substring(108, 110))
                .setSeverOvertime(data.substring(110, 112));
        MyDeviceInfo entity = BeanMapperUtil.map(deviceInfo, MyDeviceInfo.class);
        entity.setDeviceNo(deviceNo);
        entity.setOnline(1);
        entity.setCommunicationTime(new Date());
        entity.setRegistTime(new Date());

        MyDeviceInfo device = commonBiz.getMyDeviceInfo(deviceNo);
        if (device != null) {
            //更新
            entity.setId(device.getId());
            myDeviceInfoMapper.updateById(entity);
        } else {
            myDeviceInfoMapper.insert(entity);
        }
        return deviceInfo;
    }

}
