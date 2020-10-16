package com.han.javax.servlet;

/**
 * @Author SUN
 * @Date 2020/10/16 14:10
 * @Version 1.0
 * @since 1.0
 *
 * Used to encapsulate info of request
 */
public interface ServletRequest {
    /**
     * Get common parameter value
     * @param key
     * @return String
     */
    String getRequestParameter(String key);

    /**
     * Get checkbox value
     * @param key
     * @return
     */
    String[] getRequestParameters(String key);

}
