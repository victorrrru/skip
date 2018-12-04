package com.muyuan.platform.skip.biz.quartz;

import com.muyuan.platform.skip.biz.ChannelMap;
import com.muyuan.platform.skip.entity.db.MyDeviceInfo;
import com.muyuan.platform.skip.mapper.MyDeviceInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

/**
 * @author 范文武
 * @date 2018/11/30 11:12
 */
@Slf4j
@Component
public class OnlineQuartz {

    @Autowired
    private MyDeviceInfoMapper myDeviceInfoMapper;

    @Scheduled(fixedRate = 1000 * 5)
    public void execute() {
        List<MyDeviceInfo> devices = myDeviceInfoMapper.listDevice();
        for (MyDeviceInfo device : devices) {
            if (Duration.between(Instant.ofEpochMilli(device.getCommunicationTime().getTime()),
                    Instant.now()).get(ChronoUnit.SECONDS) > 3 * 60) {
                device.setOnline(0);
                log.info(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "  "
                        + device.getDeviceNo() + "离线");
                myDeviceInfoMapper.updateById(device);
                ChannelMap.removeChannel(device.getIp());
            }
        }
    }
}
