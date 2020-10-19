package com.han.oa.servlet;
import com.han.javax.servlet.Servlet;
import com.han.javax.servlet.ServletRequest;
import com.han.javax.servlet.ServletResponse;

import java.io.PrintWriter;

/**
 *
 * @Author hx
 * @Date 2020/10/16 10:22
 * @Version 1.0
 * @since 1.0
 *
 * Save info of user
 */
public class UserSaveServlet implements Servlet {

    @Override
    public void service(ServletRequest request, ServletResponse response) {
        String username = request.getRequestParameter("username");
        String gender = request.getRequestParameter("gender");
        String[] interest = request.getRequestParameters("interest");
        StringBuilder interests = new StringBuilder();
        for (String interestValue: interest) {
            interests.append(interestValue).append(" ");
        }

        PrintWriter out = response.getWriter();
        out.print("<html>");
        out.print("<head>");
        out.print("<title>用户信息</title>");
        out.print("<meta content='text/html;charset=utf-8'/>");
        out.print("</head>");
        out.print("<body>");
        out.print("用户名：" + username + "<br>");
        out.print("性别：" + gender + "<br>");
        out.print("兴趣：" + interests);
        out.print("</body>");
        out.print("</html>");
    }

}
