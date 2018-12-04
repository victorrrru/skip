package com.muyuan.platform.skip.biz;

import com.muyuan.platform.skip.common.util.*;
import com.muyuan.platform.skip.entity.EventRecord;
import com.muyuan.platform.skip.entity.db.MyAlarmLog;
import com.muyuan.platform.skip.entity.db.MyDeviceInfo;
import com.muyuan.platform.skip.mapper.MyAlarmLogMapper;
import com.muyuan.platform.skip.mapper.MyDeviceInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * @author 范文武
 * @date 2018/11/30 09:57
 */
@Slf4j
@Service
public class AlarmBiz {

    @Autowired
    private MyAlarmLogMapper myAlarmLogMapper;
    @Autowired
    private MyDeviceInfoMapper myDeviceInfoMapper;
    @Autowired
    private CommonBiz commonBiz;

    /**
     * 事件上报解析
     *
     * @author fww
     */
    public EventRecord eventReport(String head, String data) {
        String deviceNo = HexUtil.reverseHex(head.substring(4, 12));
        String dataId = data.substring(0, 2);
        String event = HexUtil.toStringHex(data.substring(2, 50)).trim();
        Integer alarmType = Integer.parseInt(data.substring(52, 54), 16);
        String datetime = DateUtil.getDate(data.substring(54, 66));
        String gps = HexUtil.hexStringToByte(data.substring(66, 68));
        Integer satelliteNum = Integer.parseInt(gps.substring(0, 4), 2);
        Integer positionMode = Integer.parseInt(gps.substring(4, 8), 2);
        BigDecimal lon = BigDecimal.valueOf(Long.parseLong(HexUtil.reverseHex(data.substring(68, 76)), 16)).divide(BigDecimal.valueOf(1000000), 6, RoundingMode.HALF_UP);
        BigDecimal lat = BigDecimal.valueOf(Long.parseLong(HexUtil.reverseHex(data.substring(76, 84)), 16)).divide(BigDecimal.valueOf(1000000), 6, RoundingMode.HALF_UP);
        BigDecimal speed = BigDecimal.valueOf(Long.parseLong(HexUtil.reverseHex(data.substring(84, 88)), 16)).divide(BigDecimal.valueOf(10), 2, RoundingMode.HALF_UP);
        EventRecord eventRecord = new EventRecord()
                .setDataId(dataId)
                .setAlarm(event)
                .setAlarmType(alarmType)
                .setDateTime(datetime)
                .setSatelliteNum(satelliteNum)
                .setPositionMode(positionMode)
                .setLon(lon)
                .setLat(lat)
                .setSpeed(speed);
        MyDeviceInfo device = commonBiz.getMyDeviceInfo(deviceNo);
        if (device != null) {
            MyAlarmLog myAlarmLog = BeanMapperUtil.map(eventRecord, MyAlarmLog.class);
            myAlarmLog.setDeviceId(device.getId());
            myAlarmLog.setDeviceNo(deviceNo);
            myAlarmLog.setReceiveTime(new Date());
            myAlarmLog.setLogType(alarmType);
            myAlarmLogMapper.insert(myAlarmLog);
            //更新设备通信时间
            device.setCommunicationTime(new Date());
            device.setOnline(1);
            myDeviceInfoMapper.updateById(device);
        }
        return eventRecord;
    }

}
