package com.muyuan.platform.skip.biz;

import com.muyuan.platform.skip.entity.CommonInfo;
import com.muyuan.platform.skip.entity.DeviceInfo;
import com.muyuan.platform.skip.entity.EventRecord;
import com.muyuan.platform.skip.entity.HeartPackageInfo;
import com.muyuan.platform.skip.entity.db.MyAlarmLog;
import com.muyuan.platform.skip.entity.db.MyDeviceInfo;
import com.muyuan.platform.skip.entity.db.MyPosLog;
import com.muyuan.platform.skip.mapper.MyAlarmLogMapper;
import com.muyuan.platform.skip.mapper.MyDeviceInfoMapper;
import com.muyuan.platform.skip.mapper.MyOperationLogMapper;
import com.muyuan.platform.skip.common.util.*;
import com.muyuan.platform.skip.mapper.MyPosLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * @author 范文武
 * @date 2018/11/19 11:24
 */
@Slf4j
@Component
public class Analysis {

    @Autowired
    private MyDeviceInfoMapper myDeviceInfoMapper;
    @Autowired
    private MyPosLogMapper myPosLogMapper;
    @Autowired
    private MyAlarmLogMapper myAlarmLogMapper;
    @Autowired
    private MyOperationLogMapper myOperationLogMapper;


    /**
     * 解析通用指令
     *
     * @author fww
     */
    public String commonAnalysis(String str) {
        String answer = "";
        CommonInfo commonInfo = new CommonInfo();
        //数据帧标识
        if (Objects.equals("5aa5", str.substring(0, 4))) {
            commonInfo.setDataType(0);
        }
        if (Objects.equals("a55a", str.substring(0, 4))) {
            commonInfo.setDataType(1);
        }
        commonInfo.setDeviceNo(str.substring(4, 12));
        commonInfo.setDataLength(Integer.valueOf(str.substring(12, 14)));
        commonInfo.setType(str.substring(14, 16));
        String data = str.substring(16);
        switch (commonInfo.getType()) {
            case "01":
                log.info("注册");
                DeviceInfo regist = regist(str.substring(0, 16), data);
                log.info(regist.toString());
                answer = registerAnswer(str);
                break;
            case "04":
                log.info("心跳");
                HeartPackageInfo heartPackageInfo = heartBeat(str.substring(0, 16), data);
                log.info(heartPackageInfo.toString());
                answer = alarmAnswer(str);
                break;
            case "05":
                log.info("请求区域坐标");
                break;
            case "10":
                log.info("事件上报");
                EventRecord eventRecord = eventReport(str.substring(0, 16), data);
                log.info(eventRecord.toString());
                answer = alarmAnswer(str);
                break;
            case "80":
                log.info("设备参数应答");
                break;
            case "81":
                log.info("手动解锁应答");
                break;
            case "87":
                log.info("下发目的地");
                break;
            case "83":
                log.info("设备维护应答");
                break;
            case "84":
                log.info("校准方向应答");
                break;
            case "86":
                log.info("透传2.4G");
            case "90":
                log.info("开门");
                break;
            default:
        }
        commonInfo.setData(data.substring(16));
        return answer;
    }

    /**
     * 注册解析入库
     *
     * @author fww
     */
    private DeviceInfo regist(String head, String data) {
        String deviceNo = CharsetUtils.changeByte(head.substring(4, 12));
        DeviceInfo deviceInfo = new DeviceInfo()
                .setMac(data.substring(0, 24))
                .setHardware(data.substring(24, 28))
                .setSoftware(data.substring(28, 32))
                .setSim(data.substring(32, 72))
                .setIp(HexTwoUtils.hexStringToTen(data.substring(80, 88)))
                .setPort(Integer.parseInt(data.substring(88, 92), 16))
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

        MyDeviceInfo device = getMyDeviceInfo(deviceNo);
        if (device != null) {
            //更新
            entity.setId(device.getId());
            myDeviceInfoMapper.updateById(entity);
        } else {
            myDeviceInfoMapper.insert(entity);
        }
        return deviceInfo;
    }

    /**
     * 查询是否存在此设备号
     * @author fww
     */
    private MyDeviceInfo getMyDeviceInfo(String deviceNo) {
        MyDeviceInfo myDeviceInfo = new MyDeviceInfo();
        myDeviceInfo.setDeviceNo(deviceNo);
        return myDeviceInfoMapper.selectOne(myDeviceInfo);
    }

    /**
     * 心跳通知
     *
     * @author fww
     */
    private HeartPackageInfo heartBeat(String head, String data) {
        String deviceNo = CharsetUtils.changeByte(head.substring(4, 12));
        String signalStr = HexTwoUtils.hexStringToByte(data.substring(0, 2));
        int signal = Integer.parseInt(signalStr.substring(2, 8), 2);
        //解锁状态
        int lock = Integer.parseInt(signalStr.substring(1, 2));
        //设防状态
        int fortification = Integer.parseInt(signalStr.substring(0, 1));
        String alarm = data.substring(2, 4);
        String acc = data.substring(4, 6);
        String dir = HexTwoUtils.hexStringToByte(data.substring(8, 10));
        Integer sensorDir = Integer.parseInt(dir.substring(4, 8), 2);
        Integer gpsDir = Integer.parseInt(dir.substring(0, 4), 2);
        String controlVer = data.substring(10, 12);
        String gps = HexTwoUtils.hexStringToByte(data.substring(12, 14));
        Integer satelliteNum = Integer.parseInt(gps.substring(4, 8), 2);
        Integer positionMode = Integer.parseInt(gps.substring(0, 4), 2);
        BigDecimal lon = BigDecimal.valueOf(Long.parseLong(CharsetUtils.changeByte(data.substring(14, 22)), 16)).divide(BigDecimal.valueOf(1000000), 6, RoundingMode.HALF_UP);
        BigDecimal lat = BigDecimal.valueOf(Long.parseLong(CharsetUtils.changeByte(data.substring(22, 30)), 16)).divide(BigDecimal.valueOf(1000000), 6, RoundingMode.HALF_UP);
        BigDecimal speed = BigDecimal.valueOf(Long.parseLong(CharsetUtils.changeByte(data.substring(30, 34)), 16)).divide(BigDecimal.valueOf(10), 2, RoundingMode.HALF_UP);
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
        log.info(heartPackageInfo.toString());
        MyDeviceInfo entity = BeanMapperUtil.map(heartPackageInfo, MyDeviceInfo.class);
        MyDeviceInfo device = getMyDeviceInfo(deviceNo);
        if (device != null) {
            //更新device
            entity.setId(device.getId());
            myDeviceInfoMapper.updateById(entity);
            if(!deviceNo.startsWith("8") && heartPackageInfo.getSatelliteNum() > 0) {
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


    /**
     * 事件上报解析
     *
     * @author fww
     */
    private EventRecord eventReport(String head, String data) {
        String deviceNo = CharsetUtils.changeByte(head.substring(4, 12));
        String dataId = data.substring(0, 2);
        String event = CharsetUtils.toStringHex(data.substring(2, 50)).trim();
        String dir = HexTwoUtils.hexStringToByte(data.substring(50, 52));
        Integer sensorDir = Integer.parseInt(dir.substring(0, 4), 2);
        Integer gpsDir = Integer.parseInt(dir.substring(4, 8), 2);
        Integer alarmType = Integer.valueOf(data.substring(52, 54));
        String datetime = DateUtil.getDate(data.substring(54, 66));
        String gps = HexTwoUtils.hexStringToByte(data.substring(66, 68));
        Integer satelliteNum = Integer.parseInt(gps.substring(0, 4), 2);
        Integer positionMode = Integer.parseInt(gps.substring(4, 8), 2);
        BigDecimal lon = BigDecimal.valueOf(Long.parseLong(CharsetUtils.changeByte(data.substring(68, 76)), 16)).divide(BigDecimal.valueOf(1000000), 6, RoundingMode.HALF_UP);
        BigDecimal lat = BigDecimal.valueOf(Long.parseLong(CharsetUtils.changeByte(data.substring(76, 84)), 16)).divide(BigDecimal.valueOf(1000000), 6, RoundingMode.HALF_UP);
        BigDecimal speed = BigDecimal.valueOf(Long.parseLong(CharsetUtils.changeByte(data.substring(84, 88)), 16)).divide(BigDecimal.valueOf(10), 2, RoundingMode.HALF_UP);
        EventRecord eventRecord = new EventRecord()
                .setDataId(dataId)
                .setAlarm(event)
                .setSensorDir(sensorDir)
                .setGpsDir(gpsDir)
                .setAlarmType(alarmType)
                .setDateTime(datetime)
                .setSatelliteNum(satelliteNum)
                .setPositionMode(positionMode)
                .setLon(lon)
                .setLat(lat)
                .setSpeed(speed);
        log.info(eventRecord.toString());
        MyDeviceInfo device = getMyDeviceInfo(deviceNo);
        if (device != null) {
            MyAlarmLog myAlarmLog = BeanMapperUtil.map(eventRecord, MyAlarmLog.class);
            myAlarmLog.setDeviceId(device.getId());
            myAlarmLog.setDeviceNo(deviceNo);
            myAlarmLog.setReceiveTime(new Date());
            myAlarmLogMapper.insert(myAlarmLog);
        }
        return eventRecord;
    }

    /**
     * 注册应答拼装消息
     *
     * @author fww
     */
    private String registerAnswer(String str) {
        String answer = "A55A" + str.substring(4, 12) + "12" + "01" + str.substring(44, 48) + DateUtil.getDateHex();
        answer += CharsetUtils.changeByte(CRCUtil.crc16(ByteUtils.byteToUnint(CRCUtil.toBytes(answer))));
        answer = answer.toUpperCase();
        return answer;
    }

    /**
     * 心跳包应答拼装消息
     *
     * @author fww
     */
    private String heartAnswer(String str) {
        String answer = "A55A" + str.substring(4, 12) + "10" + "04" + DateUtil.getDateHex();
        answer += CharsetUtils.changeByte(CRCUtil.crc16(ByteUtils.byteToUnint(CRCUtil.toBytes(answer))));
        answer = answer.toUpperCase();
        return answer;
    }

    /**
     * 事件应答拼装消息
     *
     * @author fww
     */
    private String alarmAnswer(String str) {
        String answer = "A55A" + str.substring(4, 12) + "0B" + "10" + str.substring(16, 18);
        answer += CharsetUtils.changeByte(CRCUtil.crc16(ByteUtils.byteToUnint(CRCUtil.toBytes(answer))));
        answer = answer.toUpperCase();
        return answer;
    }

}
