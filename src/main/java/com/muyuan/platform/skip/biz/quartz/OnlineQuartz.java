package com.muyuan.platform.skip.biz.quartz;

import com.muyuan.platform.skip.entity.db.MyDeviceInfo;
import com.muyuan.platform.skip.mapper.MyDeviceInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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

    @Scheduled(fixedRate = 1000)
    public void execute() {
        List<MyDeviceInfo> devices = myDeviceInfoMapper.listDevice();
        for (MyDeviceInfo device : devices) {
            if (Duration.between(Instant.ofEpochMilli(device.getCommunicationTime().getTime()),
                    Instant.now()).get(ChronoUnit.SECONDS) > 3 * 60 && device.getOnline() == 1) {
                device.setOnline(0);
                log.info(device.getDeviceNo() + "离线");
                myDeviceInfoMapper.updateById(device);
            }
        }
    }
}
