package com.han.javax.servlet;

/**
 * @Author SUN
 * @Date 2020/10/14 9:21
 * @Version 1.0
 * @since 1.0
 *
 * This is used as a unite interface for web server dev to call
 * Any Servlet Class developed by web app dev should implement this interface
 */

public interface Servlet {

    void service(ServletRequest request, ServletResponse response);
}
