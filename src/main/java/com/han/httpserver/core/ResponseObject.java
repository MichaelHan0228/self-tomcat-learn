package com.han.httpserver.core;

import com.han.javax.servlet.ServletResponse;

import java.io.PrintWriter;

/**
 * 封装响应参数
 * @Author Web server开发
 * @Date 2020/10/14 19:11
 * @Version 1.0
 * @since 1.0
 */
public class ResponseObject implements ServletResponse {

    private PrintWriter out;

    public PrintWriter getWriter() {
        return out;
    }

    public void setWriter(PrintWriter out) {
        this.out = out;
    }
}
