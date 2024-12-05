package com.upc.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;


import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class CTestFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("我第三");
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("我第三");
    }
}
