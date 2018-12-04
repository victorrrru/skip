package com.muyuan.platform.skip.biz;

import com.muyuan.platform.skip.common.util.*;
import com.muyuan.platform.skip.entity.HeartPackageInfo;
import com.muyuan.platform.skip.entity.db.MyDeviceInfo;
import com.muyuan.platform.skip.entity.db.MyPosLog;
import com.muyuan.platform.skip.mapper.MyDeviceInfoMapper;
import com.muyuan.platform.skip.mapper.MyPosLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * @author 范文武
 * @date 2018/11/30 09:53
 */
@Slf4j
@Service
public class HeartBeatBiz {

    @Autowired
    private MyDeviceInfoMapper myDeviceInfoMapper;
    @Autowired
    private MyPosLogMapper myPosLogMapper;
    @Autowired
    private CommonBiz commonBiz;

    /**
     * 心跳通知
     *
     * @author fww
     */
    public HeartPackageInfo heartBeat(String head, String data) {
        String deviceNo = HexUtil.reverseHex(head.substring(4, 12));
        String signalStr = HexUtil.hexStringToByte(data.substring(0, 2));
        int signal = Integer.parseInt(signalStr.substring(2, 8), 2);
        //解锁状态
        int lock = Integer.parseInt(signalStr.substring(1, 2));
        //设防状态
        int fortification = Integer.parseInt(signalStr.substring(0, 1));
        String alarm = data.substring(2, 4);
        String acc = data.substring(4, 6);
        String dir = HexUtil.hexStringToByte(data.substring(8, 10));
        Integer sensorDir = Integer.parseInt(dir.substring(4, 8), 2);
        Integer gpsDir = Integer.parseInt(dir.substring(0, 4), 2);
        String controlVer = data.substring(10, 12);
        String gps = HexUtil.hexStringToByte(data.substring(12, 14));
        Integer satelliteNum = Integer.parseInt(gps.substring(4, 8), 2);
        Integer positionMode = Integer.parseInt(gps.substring(0, 4), 2);
        BigDecimal lon = BigDecimal.valueOf(Long.parseLong(HexUtil.reverseHex(data.substring(14, 22)), 16)).divide(BigDecimal.valueOf(1000000), 6, RoundingMode.HALF_UP);
        BigDecimal lat = BigDecimal.valueOf(Long.parseLong(HexUtil.reverseHex(data.substring(22, 30)), 16)).divide(BigDecimal.valueOf(1000000), 6, RoundingMode.HALF_UP);
        BigDecimal speed = BigDecimal.valueOf(Long.parseLong(HexUtil.reverseHex(data.substring(30, 34)), 16)).divide(BigDecimal.valueOf(10), 2, RoundingMode.HALF_UP);
        String dirSensorData = data.substring(34, 46);
        Integer majorMainsInput = Integer.parseInt(data.substring(46, 50), 16);
        Integer innerMainsInput = Integer.parseInt(data.substring(50, 54), 16);
        Integer motorElectricity = Integer.parseInt(data.substring(54, 58), 16);
        HeartPackageInfo heartPackageInfo = new HeartPackageInfo()
                .setSignal(signal)
                .setLock(lock)
                .setFortification(fortification)
                .setAlarm(alarm)
                .setAcc(acc)
                .setSensorDir(sensorDir)
                .setGpsDir(gpsDir)
                .setControlVer(controlVer)
                .setSatelliteNum(satelliteNum)
                .setPositionMode(positionMode)
                .setLon(lon)
                .setLat(lat)
                .setSpeed(speed)
                .setDirSensorData(dirSensorData)
                .setMajorMainsInput(majorMainsInput)
                .setInnerMainsInput(innerMainsInput)
                .setMotorElectricity(motorElectricity);
        MyDeviceInfo entity = BeanMapperUtil.map(heartPackageInfo, MyDeviceInfo.class);
        MyDeviceInfo device = commonBiz.getMyDeviceInfo(deviceNo);
        if (device != null) {
            //更新device
            entity.setId(device.getId());
            entity.setOnline(1);
            entity.setCommunicationTime(new Date());
            myDeviceInfoMapper.updateById(entity);
            if(!deviceNo.startsWith("8")) {
                MyPosLog myPosLog = new MyPosLog()
                        .setDeviceId(device.getId())
                        .setDeviceNo(deviceNo)
                        .setLon(heartPackageInfo.getLon())
                        .setLat(heartPackageInfo.getLat())
                        .setSpeed(heartPackageInfo.getSpeed())
                        .setDirection(Integer.parseInt(data.substring(8, 10), 16))
                        .setGps(Integer.parseInt(data.substring(12, 14), 16));
                //插入实时位置
                myPosLogMapper.insert(myPosLog);
            }
            //检查是否停车
            //todo
        }
        return heartPackageInfo;
    }

}
