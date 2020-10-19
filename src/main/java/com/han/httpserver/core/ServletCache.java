package com.han.httpserver.core;

import com.han.javax.servlet.Servlet;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author hxd
 * @Date 2020/10/19 9:51
 * @Version 1.0
 * @since 1.0
 */
public class ServletCache {

    private static Map<String, Servlet> servletCache = new HashMap<String, Servlet>();

    public static void setServlet (String urlPattern, Servlet servlet) {
        servletCache.put(urlPattern, servlet);
    }

    public static Servlet getServlet(String urlPattern) {
        return servletCache.get(urlPattern);
    }

}
