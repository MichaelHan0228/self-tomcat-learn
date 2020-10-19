package com.han.oa.servlet;

import com.han.httpserver.core.RequestObject;
import com.han.javax.servlet.Servlet;
import com.han.javax.servlet.ServletRequest;
import com.han.javax.servlet.ServletResponse;

import java.io.PrintWriter;

/**
 * 提供处理逻辑，由 webAPP开发提供 -> web server 开发
 * @Author hxd
 * @Date 2020/10/14 9:21
 * @Version 1.0
 * @since 1.0
 */
public class LoginServlet implements Servlet {

    @Override
    public void service(ServletRequest request, ServletResponse response) {
        PrintWriter out = response.getWriter();
        out.print("HTTP/1.1 200 OK\n");
        out.print("Content-Type:text/html;charset=utf-8\n\n");
        out.print("<html>");
        out.print("<head>");
        out.print("<meta charset='UTF-8'>");
        out.print("<title>response</title>");
        out.print("</head>");
        out.print("<body><div style='text-align: center;'><span style='font-size: 35px; color: blue'>正在验证中，请稍等...</span></div></body>");
        out.print("</html>");
    }
}
