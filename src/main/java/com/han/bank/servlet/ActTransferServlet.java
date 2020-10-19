package com.han.bank.servlet;

import com.han.javax.servlet.Servlet;
import com.han.javax.servlet.ServletRequest;
import com.han.javax.servlet.ServletResponse;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Author hxd
 * @Date 2020/10/19 19:03
 * @Version 1.0
 * @since 1.0
 */
public class ActTransferServlet implements Servlet {

    @Override
    public void service(ServletRequest request, ServletResponse response) {
        String actFrom = request.getRequestParameter("actFrom");
        Double balance = Double.parseDouble(request.getRequestParameter("balance"));
        String actTo = request.getRequestParameter("actTo");
        int count = 0;

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://cloud.leihuo.netease.com:39627/hxd?serverTimezone=Asia/Shanghai&useSSL=false";
            String user = "root";
            String password = "WHsdyTFE0uTqN2g8";
            conn = DriverManager.getConnection(url, user, password);
            String sqlFrom = "update t_act set balance = balance - ? where actno = ?";
            ps = conn.prepareStatement(sqlFrom);
            ps.setDouble(1, balance);
            ps.setString(2, actFrom);
            count = ps.executeUpdate();

            String sqlTo = "update t_act set balance = balance + ? where actno = ?";
            ps = conn.prepareStatement(sqlTo);
            ps.setDouble(1, balance);
            ps.setString(2, actTo);
            count += ps.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

        PrintWriter out = response.getWriter();
        if(count == 2){
            out.print("<html>");
            out.print("<head>");
            out.print("<title>银行帐户-转帐结果</title>");
            out.print("<meta content='text/html;charset=utf-8'/>");
            out.print("</head>");
            out.print("<body>");
            out.print("<center><font size='35px' color='green'>转帐成功！</font></center>");
            out.print("</body>");
            out.print("</html>");
        }else{
            out.print("<html>");
            out.print("<head>");
            out.print("<title>银行帐户-转帐结果</title>");
            out.print("<meta content='text/html;charset=utf-8'/>");
            out.print("</head>");
            out.print("<body>");
            out.print("<center><font size='35px' color='red'>转帐失败！</font></center>");
            out.print("</body>");
            out.print("</html>");
        }
    }
}
