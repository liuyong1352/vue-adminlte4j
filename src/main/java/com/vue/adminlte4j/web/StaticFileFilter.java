package com.vue.adminlte4j.web;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by bjliuyong on 2018/3/29.
 */
public class StaticFileFilter implements Filter {

    @Override public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override public void doFilter(ServletRequest request, ServletResponse response,
        FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request ,new ResponseFilterWrapper((HttpServletResponse)response));
    }

    @Override public void destroy() {

    }
}
