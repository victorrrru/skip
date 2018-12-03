package com.muyuan.platform.skip.biz.quartz;

import com.mchange.lang.ByteUtils;
import com.muyuan.platform.skip.biz.ChannelMap;
import com.muyuan.platform.skip.biz.CommonBiz;
import com.muyuan.platform.skip.common.util.HexUtil;
import com.muyuan.platform.skip.entity.CtrlRes;
import com.muyuan.platform.skip.entity.db.MyOperationLog;
import com.muyuan.platform.skip.mapper.MyOperationLogMapper;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.oio.OioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;

/**
 * @author 范文武
 * @date 2018/11/28 10:20
 *
 */
@Component
@Slf4j
public class CtrlQuartz {

    @Autowired
    private MyOperationLogMapper myOperationLogMapper;
    @Autowired
    private CommonBiz commonBiz;

    @Scheduled(fixedRate = 1000 * 60)
    public void execute() {
        log.info("调度开始");
        List<CtrlRes> ctrls = myOperationLogMapper.selectCtrl();
        for (CtrlRes ctrl : ctrls) {
            OioSocketChannel ctx = (OioSocketChannel) ChannelMap.getChannelByName(ctrl.getIp());
            if (ctx == null) {
                MyOperationLog entity = new MyOperationLog();
                entity.setId(ctrl.getId());
                entity.setStatus((byte) 2);
                entity.setOperationNum(ctrl.getOperationNum() + 1);
                entity.setExecuteTime(new Date());
                entity.setOptResult("tcp未连接");
                myOperationLogMapper.updateById(entity);
                log.info(ctrl.getDeviceNo() + "未连接(" + ctrl.getParam() + ")");
                continue;
            }
            String type = Integer.toHexString(ctrl.getOperationCode());
            String len = Integer.toHexString(ctrl.getParam().length() / 2 + 10);
            String answer = commonBiz.getAnswer(HexUtil.reverseHex(ctrl.getDeviceNo()), type.length() == 1 ? "0" + type : type,
                    ctrl.getParam(), len.length() == 1 ? "0" + len : len);
            //当服务端给客户端发消息时，io：堵塞写，nio：不堵塞写，而都不会等待客户端返回
            ctx.writeAndFlush(Unpooled.copiedBuffer(ByteUtils.fromHexAscii(answer)));
            log.info("Server send: " +  answer);
        }
    }
}
