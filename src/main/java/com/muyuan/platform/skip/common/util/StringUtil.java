package com.muyuan.platform.skip.common.util;

import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * @author 范文武
 * @date 2018/12/04 09:14
 */
public class StringUtil {
    /**
     * 得到公共返回
     * @author fww
     */
    public static String getReturn(ChannelHandlerContext ctx) {
        return DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "  " + ctx.channel().remoteAddress();
    }
}
