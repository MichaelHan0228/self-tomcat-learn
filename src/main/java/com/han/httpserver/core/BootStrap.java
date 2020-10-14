package com.han.httpserver.core;

import org.dom4j.DocumentException;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * httpserver 程序入口
 * @Author hxd
 * @Date 2020/10/13 15:01
 * @Version 1.0
 * @since 1.0
 */
public class BootStrap {
    public static void main(String[] args) {
        start();
    }

    public static void start() {
        Logger logger = Logger.getGlobal();
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        BufferedReader br = null;

        try {
            logger.info("httpserver starting");
            long start = System.currentTimeMillis();
            // webAppNames, 这里先固定，服务器启动便解析web.xml
            String[] webAppNames = {"oa"};
            WebParser.parseWebXML(webAppNames);
            // 获取配置信息的端口
            int port = ServerParser.getPort();
            serverSocket = new ServerSocket(port);
            long end = System.currentTimeMillis();
            logger.info("httpserver uses " + (end-start) + "ms to start");

            // 循环监听
            while (true) {
                clientSocket = serverSocket.accept();
                new Thread(new RequestHandler(clientSocket)).start();
            }
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
