package com.muyuan.platform.skip.biz;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.muyuan.platform.skip.common.util.HexUtil;
import com.muyuan.platform.skip.entity.db.MyOperationLog;
import com.muyuan.platform.skip.mapper.MyOperationLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author 范文武
 * @date 2018/11/30 10:02
 */
@Slf4j
@Service
public class OperationBiz {

    @Autowired
    private MyOperationLogMapper myOperationLogMapper;

    /**
     * 更改ctrl结果
     * @author fww
     */
    public void changeCtrlRes(String deviceNo, Integer type, String data) {
        deviceNo = HexUtil.reverseHex(deviceNo);
        EntityWrapper<MyOperationLog> wrapper = new EntityWrapper<>();
        wrapper.eq("device_no", deviceNo);
        wrapper.eq("operation_code", type);
        wrapper.orderBy("operation_time", false);
        List<MyOperationLog> myOperationLogs = myOperationLogMapper.selectList(wrapper);
        MyOperationLog myOperationLog = myOperationLogs.get(0);
        MyOperationLog entity = new MyOperationLog();
        entity.setId(myOperationLog.getId());
        entity.setStatus((byte) (Objects.equals(data, "00") ? 2 : 1));
        entity.setExecuteTime(new Date());
        if (entity.getStatus() == 2) {
            entity.setOperationNum(myOperationLog.getOperationNum() + 1);
            if (myOperationLog.getOperationNum() == 9) {
                entity.setOptResult("重试10次无应答");
            } else {
                entity.setOptResult("失败");
            }
        } else {
            entity.setOptResult("成功");
        }
        myOperationLogMapper.updateById(entity);
    }
}
