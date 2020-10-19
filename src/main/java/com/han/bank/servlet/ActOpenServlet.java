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
 * @Date 2020/10/19 18:59
 * @Version 1.0
 * @since 1.0
 */
public class ActOpenServlet implements Servlet {

    @Override
    public void service(ServletRequest request, ServletResponse response) {
        String actNo = request.getRequestParameter("actno");
        double balance = Double.parseDouble(request.getRequestParameter("balance"));

        Connection conn = null;
        PreparedStatement ps = null;
        int count = 0;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://cloud.leihuo.netease.com:39627/hxd?serverTimezone=Asia/Shanghai&useSSL=false", "root", "WHsdyTFE0uTqN2g8");
            String sql = "insert into t_act(actno,balance) values(?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, actNo);
            ps.setDouble(2, balance);
            count = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        PrintWriter out = response.getWriter();
        if(count == 1){
            out.print("<html>");
            out.print("<head>");
            out.print("<title>银行帐户-开户结果</title>");
            out.print("<meta content='text/html;charset=utf-8'/>");
            out.print("</head>");
            out.print("<body>");
            out.print("<center><font size='35px' color='green'>恭喜您，开户成功！</font></center>");
            out.print("</body>");
            out.print("</html>");
        }else{
            out.print("<html>");
            out.print("<head>");
            out.print("<title>银行帐户-开户结果</title>");
            out.print("<meta content='text/html;charset=utf-8'/>");
            out.print("</head>");
            out.print("<body>");
            out.print("<center><font size='35px' color='red'>对不起，开户失败！</font></center>");
            out.print("</body>");
            out.print("</html>");
        }

    }
}
