package com.han.javax.servlet;

import java.io.PrintWriter;

/**
 * @Author SUN
 * @Date 2020/10/14 19:17
 * @Version 1.0
 * @since 1.0
 *
 * Used to encapsulate info of response
 */
public interface ServletResponse {

    void setWriter(PrintWriter out);

    PrintWriter getWriter();

}
