package com.muyuan.platform.skip.biz;

import com.muyuan.platform.skip.common.util.*;
import com.muyuan.platform.skip.entity.EventRecord;
import com.muyuan.platform.skip.entity.db.MyDeviceAlarm;
import com.muyuan.platform.skip.entity.db.MyDeviceLog;
import com.muyuan.platform.skip.entity.db.MyDeviceInfo;
import com.muyuan.platform.skip.mapper.MyAreaMapper;
import com.muyuan.platform.skip.mapper.MyDeviceAlarmMapper;
import com.muyuan.platform.skip.mapper.MyDeviceInfoMapper;
import com.muyuan.platform.skip.mapper.MyDeviceLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 范文武
 * @date 2018/11/30 09:57
 */
@Slf4j
@Service
public class AlarmBiz {

    @Autowired
    private MyDeviceAlarmMapper myDeviceAlarmMapper;
    @Autowired
    private MyDeviceLogMapper myDeviceLogMapper;
    @Autowired
    private MyDeviceInfoMapper myDeviceInfoMapper;
    @Autowired
    private MyAreaMapper myAreaMapper;
    @Autowired
    private CommonBiz commonBiz;

    /**
     * 事件上报解析
     *
     * @author fww
     */
    public EventRecord eventReport(String head, String data) {
        List<Integer> list = new ArrayList<>();
        list.add(8);
        list.add(16);
        list.add(17);
        list.add(20);
        list.add(32);
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
            MyDeviceLog deviceLog = BeanMapperUtil.map(eventRecord, MyDeviceLog.class);
            deviceLog.setDeviceId(device.getId());
            deviceLog.setDeviceNo(deviceNo);
            deviceLog.setReceiveTime(new Date());
            deviceLog.setLogType(alarmType);
            myDeviceLogMapper.insert(deviceLog);
            //警情
            if (list.contains(alarmType)) {
                MyDeviceAlarm deviceAlarm = BeanMapperUtil.map(eventRecord, MyDeviceAlarm.class);
                deviceAlarm.setDeviceId(device.getId());
                deviceAlarm.setDeviceNo(deviceNo);
                deviceAlarm.setReceiveTime(new Date());
                deviceAlarm.setLogType(alarmType);
                //查询所有场区点位置
                boolean onField = isOnField(lon, lat);
                if (alarmType == 20 && ((!onField && speed.compareTo(BigDecimal.valueOf(30)) == -1) || onField)) {
                    //非法卸料：厂区内不记录，厂外车速在30以下不记录
                } else if (event.contains("罐盖未关") && !onField) {

                } else {
                    myDeviceAlarmMapper.insert(deviceAlarm);
                }

            }
            //更新设备通信时间
            device.setCommunicationTime(new Date());
            device.setOnline(1);
            myDeviceInfoMapper.updateById(device);
        }
        return eventRecord;
    }

    /**
     * 判断此位置是否在站点
     * @author fww
     */
    private boolean isOnField(BigDecimal lon, BigDecimal lat) {
        List<String> pots = myAreaMapper.selectAllPots();
        for (String pot : pots) {
            if (LocationUtil.judge(lon, lat, pot)) {
                return true;
            }
        }
        return false;
    }

}
