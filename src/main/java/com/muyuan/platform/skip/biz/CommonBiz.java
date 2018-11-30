package com.muyuan.platform.skip.biz;

import com.muyuan.platform.skip.common.util.ByteUtil;
import com.muyuan.platform.skip.common.util.CRCUtil;
import com.muyuan.platform.skip.common.util.HexUtil;
import com.muyuan.platform.skip.entity.db.MyDeviceInfo;
import com.muyuan.platform.skip.mapper.MyDeviceInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 范文武
 * @date 2018/11/30 10:00
 */
@Slf4j
@Service
public class CommonBiz {

    @Autowired
    private MyDeviceInfoMapper myDeviceInfoMapper;

    /**
     * 查询是否存在此设备号
     * @author fww
     */
    public MyDeviceInfo getMyDeviceInfo(String deviceNo) {
        MyDeviceInfo myDeviceInfo = new MyDeviceInfo();
        myDeviceInfo.setDeviceNo(deviceNo);
        return myDeviceInfoMapper.selectOne(myDeviceInfo);
    }

    /**
     * 服务器拼接向终端发送的数据
     * @author fww
     */
    public String getAnswer(String devideNo, String type, String data, String len) {
        String answer = "A55A" + devideNo + len + type + data;
        answer += HexUtil.reverseHex(CRCUtil.crc16(ByteUtil.byteToUnit(ByteUtil.toBytes(answer))));
        answer = answer.toUpperCase();
        return answer;
    }
}
