package com.han.javax.servlet;

/**
 * 接口，用来解耦
 * @Author SUN
 * @Date 2020/10/14 9:21
 * @Version 1.0
 * @since 1.0
 */

public interface Servlet {

    void service(ServletResponse response);
}
