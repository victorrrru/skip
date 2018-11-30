package com.muyuan.platform.skip.biz;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author 范文武
 * @date 2018/11/27 15:45
 */
public class ChannelMap {
    private static Map<String, Channel> channels = null;
    private static Integer channelNum = 0;

    public static Map<String, Channel> getChannelMap() {
        if (channels == null) {
            channels = new ConcurrentHashMap<>(500);
        }
        return channels;
    }

    public static Channel getChannelByName(String name) {
        if (channels == null) {
            channels = new ConcurrentHashMap<>(500);
        }
        return channels.get(name);
    }

    public static void addChannel(String name, Channel channel) {
        if (channels == null) {
            channels = new ConcurrentHashMap<>(500);
        }
        channels.put(name, channel);
        channelNum++;
    }

    public static void removeChannel(String name) {
        if (channels == null) {
            return;
        }
        if (channels.containsKey(name)) {
            channels.remove(name);
        }
    }

    public void schedule(Channel channel) {
        channel.eventLoop().scheduleAtFixedRate(() -> {
            System.out.println("222");
            channel.write("111");
        }, 60, 60, TimeUnit.SECONDS);
    }
}
