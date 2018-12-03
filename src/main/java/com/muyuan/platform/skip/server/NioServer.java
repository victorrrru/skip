package com.muyuan.platform.skip.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author 范文武
 * @date 2018/12/03 18:03
 * nio
 */
public class NioServer {
    public void start() throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.bind(new InetSocketAddress(8100));
        System.out.println("----------服务器已连接----------");
        //选择器
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            if (!selector.isOpen()) {
                System.out.println("selector is closed");
                break;
            }
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                try {
                    if (key.isAcceptable()) {
                        ServerSocketChannel serverSocket = (ServerSocketChannel) key.channel();
                        SocketChannel socket = serverSocket.accept();
                        socket.configureBlocking(false);
                        socket.register(selector, SelectionKey.OP_READ);
                    }
                    if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        //创建ByteBuffer，并开辟一个1M的缓冲区
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        //读取请求码流，返回读取到的字节数
                        int readBytes = client.read(buffer);
                        //读取到字节，对字节进行编解码
                        if (readBytes > 0) {
                            //将缓冲区当前的limit设置为position=0，用于后续对缓冲区的读取操作
                            buffer.flip();
                            //根据缓冲区可读字节数创建字节数组
                            byte[] bytes = new byte[buffer.remaining()];
                            //将缓冲区可读字节数组复制到新建的数组中
                            buffer.get(bytes);
                            String expression = new String(bytes, "UTF-8");
                            System.out.println("服务器收到消息：" + expression);
                            //发送应答消息
                            doWrite(client, expression);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void doWrite(SocketChannel channel,String response) {
        //将消息编码为字节数组
        byte[] bytes = response.getBytes();
        //根据数组容量创建ByteBuffer
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        //将字节数组复制到缓冲区
        writeBuffer.put(bytes);
        //flip操作
        writeBuffer.flip();
        //发送缓冲区的字节数组
        try {
            channel.write(writeBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        new NioServer().start();
    }
}
