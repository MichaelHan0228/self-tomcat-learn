package com.han.httpserver.core;

import com.han.javax.servlet.Servlet;
import com.han.oa.servlet.LoginServlet;

import javax.xml.ws.Response;
import java.io.*;
import java.net.Socket;
import java.util.Map;

/**
 * 使用多线程来处理请求
 * @Author hxd
 * @Date 2020/10/13 17:07
 * @Version 1.0
 * @since 1.0
 */
public class RequestHandler implements Runnable {

    private Socket clientSocket;

    public RequestHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        BufferedReader br = null;
        PrintWriter out = null;
        try {
            br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String requestURI = br.readLine().split(" ")[1];
            out = new PrintWriter(clientSocket.getOutputStream());

            if (requestURI.contains("favicon")) {
                return;
            }

            // 响应浏览器请求
            // 静态页面
            if (requestURI.endsWith("html") || requestURI.endsWith("htm")) {
                responseStaticPage(requestURI, out);
                out.flush();
            }
            // 动态页面
            else {
                // /oa/login
                String servletPath = requestURI;
                if (requestURI.contains("?")) {
                    // 有参数则获取'?'前的内容
                    servletPath = requestURI.split("[?]")[0];
                }

                // 获取uri对应处理类
                String webAppName = servletPath.split("[/]")[1];
                Map<String, String> servletMap = WebParser.servletMaps.get(webAppName);
                String urlPattern = servletPath.substring(1 + webAppName.length());

                String servletClassName = servletMap.get(urlPattern);
                if (servletClassName != null) {
                    ResponseObject responseObject = new ResponseObject();
                    responseObject.setWriter(out);
                    // 利用反射机制
                    Class c = Class.forName(servletClassName);
                    Object obj = c.newInstance();
                    Servlet servlet = (Servlet) obj;
                    servlet.service(responseObject);
                } else {
                    // 找不到资源 404
                    StringBuilder html = new StringBuilder();
                    html.append("HTTP/1.1 404 NotFound\n");
                    html.append("Content-Type:text/html;charset=utf-8\n\n");
                    html.append("<html>");
                    html.append("<head>");
                    html.append("<meta charset='UTF-8'>");
                    html.append("<title>404-Not Found</title>");
                    html.append("</head>");
                    html.append("<body><div style='text-align: center;'><span style='font-size: 35px; color: red'>404-Not Found</span></div></body>");
                    html.append("</html>");
                    out.print(html);
                }
                out.flush();

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        } finally {
            if (clientSocket != null) {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                out.close();
            }
        }

    }

    /**
     * 返回静态页面
     * @param requestURI /oa/index.html
     * @param out
     */
    private void responseStaticPage(String requestURI, PrintWriter out) {
        String htmlPath = requestURI.substring(1);

        try (BufferedReader br = new BufferedReader(new FileReader(htmlPath))){
            StringBuilder html = new StringBuilder();
            String temp = null;
            html.append("HTTP/1.1 200 OK\n");
            html.append("Content-Type:text/html;charset=utf-8\n\n");
            while ((temp = br.readLine()) != null) {
                html.append(temp);
            }
            out.print(html);
        } catch (FileNotFoundException e) {
            StringBuilder html = new StringBuilder();
            html.append("HTTP/1.1 404 NotFound\n");
            html.append("Content-Type:text/html;charset=utf-8\n\n");
            html.append("<html>");
            html.append("<head>");
            html.append("<meta charset='UTF-8'>");
            html.append("<title>404-Not Found</title>");
            html.append("</head>");
            html.append("<body><div style='text-align: center;'><span style='font-size: 35px; color: red'>404-Not Found</span></div></body>");
            html.append("</html>");
            out.print(html);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}