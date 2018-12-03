package com.muyuan.platform.skip.server;

import com.mchange.lang.ByteUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 范文武
 * @date 2018/11/15 12:03
 * 采用BIO通信模型的服务端，通常由一个独立的Acceptor线程负责监听客户端的连接，
 * 它接收到客户端连接请求之后为每个客户端创建一个新的线程进行链路处理没处理完成后，
 * 通过输出流返回应答给客户端，线程销毁。即典型的一请求一应答通宵模型。
 * io:服务端的线程个数和客户端并发访问数呈1:1的正比关系
 * 伪异步io:使用线程池来代替一个用户一个线程，实现1个或多个线程处理N个客户端的模型
 */
public class IoServer {

    public void start() throws IOException {
        ExecutorService threads = Executors.newFixedThreadPool(10);
        ServerSocket server = new ServerSocket(8000);
        System.out.println("socket服务器启动成功");
        //一直等客户端的消息
        while (true) {
            Socket socket = server.accept();
            System.out.println("客户端连接" + socket.getInetAddress());
            threads.submit(() -> {
                InputStream in = null;
                PrintWriter out = null;
                try {
                    in = socket.getInputStream();
                    out = new PrintWriter(socket.getOutputStream(), true);
                    byte[] bytes = new byte[66];
                    while (in.read(bytes) != -1) {
                        out.print(ByteUtils.toHexAscii(bytes));
                        out.flush();
                        System.out.println(ByteUtils.toHexAscii(bytes));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (out != null) {
                            out.close();
                        }
                        if (in != null) {
                            in.close();
                        }
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static void main(String[] args) throws IOException {
        new IoServer().start();
    }
}
