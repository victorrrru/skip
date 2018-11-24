package com.muyuan.platform.biz;

import com.muyuan.platform.entity.CommonInfo;
import com.muyuan.platform.entity.DeviceInfo;
import com.muyuan.platform.entity.EventRecord;
import com.muyuan.platform.entity.HeartPackageInfo;
import com.muyuan.platform.entity.db.MyDeviceInfo;
import com.muyuan.platform.mapper.MyAlarmLogMapper;
import com.muyuan.platform.mapper.MyDeviceInfoMapper;
import com.muyuan.platform.mapper.MyOperationLogMapper;
import com.muyuan.platform.common.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Objects;

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
    private MyOperationLogMapper myOperationLogMapper;
    @Autowired
    private MyAlarmLogMapper myAlarmLogMapper;


    /**
     * 解析通用指令
     * @author fww
     */
    public byte[] commonAnalysis(String data) {
        byte[] answer = new byte[]{};
        CommonInfo commonInfo = new CommonInfo();
        //数据帧标识
        if (Objects.equals("5aa5", data.substring(0, 4))) {
            commonInfo.setDataType(0);
        }
        if (Objects.equals("a55a", data.substring(0, 4))) {
            commonInfo.setDataType(1);
        }
        commonInfo.setDeviceNo(data.substring(4, 12));
        commonInfo.setDataLength(Integer.valueOf(data.substring(12, 14)));
        commonInfo.setType(data.substring(14, 16));
        data = data.substring(16);
        switch (commonInfo.getType()) {
            case "01":
                log.info("注册");
                DeviceInfo regist = regist(data);
                log.info(regist.toString());
                answer = answer(data);
                log.info(Arrays.toString(answer));
                break;
            case "04":
                log.info("心跳");
                heartBeat(data);
                break;
            case "05":
                areaCoordinate(data);
                log.info("请求区域坐标");
                break;
            case "10":
                log.info("事件上报");
                eventReport(data);
                break;
            case "80":
                log.info("设备参数应答");
                break;
            case "81":
                manualUnlock(data);
                log.info("手动解锁应答");
                break;
            case "87":
                destination(data);
                log.info("下发目的地");
                break;
            case "83":
                log.info("设备维护应答");
                break;
            case "84":
                log.info("校准方向应答");
                break;
            case "86":
                checkControlRes(data);
                log.info("透传2.4G");
            case "90":
                open(data);
                log.info("开门");
                break;
            default:
        }
        commonInfo.setData(data.substring(16));
        return answer;
    }

    /**
     * 注册解析入库
     * @author fww
     */
    private DeviceInfo regist(String data) {
        DeviceInfo deviceInfo = new DeviceInfo().setDeviceId(data.substring(0, 24))
                .setHardWare(data.substring(24, 28))
                .setSoftWare(data.substring(28, 32))
                .setSim(data.substring(32, 72))
                .setDeviceNo(data.substring(72, 80))
                .setIp(data.substring(80, 88))
                .setPort(Integer.parseInt(data.substring(88, 92), 16))
                .setIp2(data.substring(92, 100))
                .setPort2(data.substring(100, 104))
                .setHeartBeatInterval(data.substring(104, 106))
                .setRetryInterval(data.substring(106, 108))
                .setSerialPortOvertime(data.substring(108, 110))
                .setSeverOvertime(data.substring(110, 112));
        MyDeviceInfo entity = BeanMapperUtil.map(deviceInfo, MyDeviceInfo.class);
        myDeviceInfoMapper.insertSelective(entity);
        return deviceInfo;
    }

    /**
     * 心跳通知
     * @author fww
     */
    private static void heartBeat(String data) {
        String signalStr = HexTwoUtils.hexStringToByte(data.substring(0, 2));
        int signal = Integer.parseInt(signalStr.substring(0, 6), 2);
        int lock = Integer.parseInt(signalStr.substring(6, 7));
        int fortification = Integer.parseInt(signalStr.substring(7, 8));
        String alarm = data.substring(2, 4);
        String acc = data.substring(4, 6);
        String dir = HexTwoUtils.hexStringToByte(data.substring(8, 10));
        Integer sensorDir = Integer.parseInt(dir.substring(0, 4), 2);
        Integer gpsDir = Integer.parseInt(dir.substring(4, 8), 2);
        String controlVer = data.substring(10, 12);
        String gps = HexTwoUtils.hexStringToByte(data.substring(12, 14));
        Integer satelliteNum = Integer.parseInt(gps.substring(0, 4), 2);
        Integer positionMode = Integer.parseInt(gps.substring(4, 8), 2);
        BigDecimal lon = BigDecimal.valueOf(Long.parseLong(CharsetUtils.changeByte(data.substring(14, 22)), 16)).divide(BigDecimal.valueOf(1000000), 6, RoundingMode.HALF_UP);
        BigDecimal lat = BigDecimal.valueOf(Long.parseLong(CharsetUtils.changeByte(data.substring(22, 30)), 16)).divide(BigDecimal.valueOf(1000000), 6, RoundingMode.HALF_UP);
        BigDecimal speed = BigDecimal.valueOf(Long.parseLong(CharsetUtils.changeByte(data.substring(30, 34)), 16)).divide(BigDecimal.valueOf(10), 2, RoundingMode.HALF_UP);
        String dirSensorData = data.substring(34, 46);
        Integer majorMainsInput = Integer.parseInt(data.substring(46, 50), 16);
        Integer innerMainsInput = Integer.parseInt(data.substring(50, 54), 16);
        Integer motorElectricity = Integer.parseInt(data.substring(54, 58), 16);
        HeartPackageInfo heartPackageInfo = new HeartPackageInfo().setSignal(signal)
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
        System.out.println(heartPackageInfo.toString());
    }

    /**
     * 请求区域坐标
     * @author fww
     */
    private static void areaCoordinate(String data) {
    }

    /**
     * 事件上报解析
     * @author fww
     */
    private static void eventReport(String data) {
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
                .setEvent(event)
                .setSensorDir(sensorDir)
                .setGpsDir(gpsDir)
                .setAlarmType(alarmType)
                .setDateTime(datetime)
                .setSatelliteNum(satelliteNum)
                .setPositionMode(positionMode)
                .setLon(lon)
                .setLat(lat)
                .setSpeed(speed);
        System.out.println(eventRecord.toString());
    }

    /**
     * 应答拼装消息
     * @author fww
     */
    private byte[] answer(String str) {
        String hex = "A55A" + str.substring(4, 12) + str.substring(12, 14) + str.substring(14, 16) +
                CRCUtil.crc16(ByteUtils.byteToUnint(CRCUtil.toBytes(str)));
        return ByteUtils.hexStrToByte(hex);
    }

    /**
     * 设备参数应答
     * @author fww
     */
    private static void deviceParameterRes(String data) {

    }
    /**
     * 手动解锁应答
     * @author fww
     */
    private static void manualUnlock(String data) {

    }
    /**
     * 下发目的地
     * @author fww
     */
    private static void destination(String data) {
        String flag = data.substring(0, 2);
        String content = data.substring(2, 50);
        String direction = data.substring(50, 52);
        String type = data.substring(52, 54);
        String datetime = data.substring(54, 66);
        String gps = data.substring(66, 68);
        String lon = data.substring(68, 76);
        String lat = data.substring(76, 84);
        String speed = data.substring(84, 88);
    }
    /**
     * 设备维护应答
     * @author fww
     */
    private static void deviceMaintainRes(String data) {

    }
    /**
     * 校准方向应答
     * @author fww
     */
    private void calibrationDreRes(String data) {

    }
    /**
     * 透传2.4G
     * @author fww
     */
    private static void checkControlRes(String data) {

    }
    /**
     * 开门
     * @author fww
     */
    private static void open(String data) {

    }
}
