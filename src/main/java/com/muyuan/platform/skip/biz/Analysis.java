package com.muyuan.platform.skip.biz;

import com.muyuan.platform.skip.common.util.DateUtil;
import com.muyuan.platform.skip.entity.CommonInfo;
import com.muyuan.platform.skip.entity.DeviceInfo;
import com.muyuan.platform.skip.entity.EventRecord;
import com.muyuan.platform.skip.entity.HeartPackageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;

/**
 * @author 范文武
 * @date 2018/11/19 11:24
 */
@Slf4j
@Component
public class Analysis {

    @Autowired
    private RegisterBiz registerBiz;
    @Autowired
    private HeartBeatBiz heartBeatBiz;
    @Autowired
    private AlarmBiz alarmBiz;
    @Autowired
    private OperationBiz operationBiz;
    @Autowired
    private CommonBiz commonBiz;


    /**
     * 解析通用指令
     *
     * @author fww
     */
    public String commonAnalysis(String ip, String str) {
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
        commonInfo.setDataLength(Integer.valueOf(str.substring(12, 14), 16));
        commonInfo.setType(str.substring(14, 16));
        String data = str.substring(16);
        switch (commonInfo.getType()) {
            case "01":
                log.info("注册  " + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "  " + ip +
                        "\nServer Received:" + str);
                DeviceInfo regist = registerBiz.regist(ip, str.substring(0, 16), data);
                //log.info(regist.toString());
                answer = commonBiz.getAnswer(str.substring(4, 12), "01", str.substring(44, 48) + DateUtil.getDateHex(), "12");
                if (StringUtils.isNotBlank(answer)) {
                    log.info("注册应答  " + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "  " + ip + "\nServer answer:" + answer);
                }
                break;
            case "04":
                log.info("心跳  " + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "  " + ip +
                        "\nServer Received:" + str);
                HeartPackageInfo heartPackageInfo = heartBeatBiz.heartBeat(str.substring(0, 16), data);
                //log.info(heartPackageInfo.toString());
                answer = commonBiz.getAnswer(str.substring(4, 12),"04", DateUtil.getDateHex(), "10");
                if (StringUtils.isNotBlank(answer)) {
                    log.info("心跳应答  " + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "  " + ip + "\nServer answer:" + answer);
                }
                break;
            case "05":
                log.info("请求区域坐标");
                break;
            case "10":
                log.info("事件上报  " + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "  " + ip +
                        "\nServer Received:" + str);
                EventRecord eventRecord = alarmBiz.eventReport(str.substring(0, 16), data);
                //log.info(eventRecord.toString());
                answer = commonBiz.getAnswer(str.substring(4, 12), "10", str.substring(16, 18), "0B");
                if (StringUtils.isNotBlank(answer)) {
                    log.info("事件上报应答  " + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "  " + ip + "\nServer answer:" + answer);
                }
                break;
            case "80":
                log.info("设备参数应答");
                break;
            case "81":
                log.info("手动解锁应答  " + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "  " + ip +
                        "\nServer Received:" + str);
                operationBiz.changeCtrlRes(commonInfo.getDeviceNo(), 129, data);
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
        commonInfo.setData(str.substring(16));
        return answer;
    }

}
