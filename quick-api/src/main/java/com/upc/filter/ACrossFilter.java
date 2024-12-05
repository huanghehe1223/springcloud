package com.upc.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class ACrossFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req=(HttpServletRequest)servletRequest;
        HttpServletResponse resp=(HttpServletResponse)servletResponse;
        //1 获取请求路径
        String url=req.getRequestURI().toString();
        String method=req.getMethod();


        System.out.println(url);
        System.out.println(method);
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE,PUT");
        resp.setHeader("Access-Control-Max-Age", "3600");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type,XFILENAME,XFILECATEGORY,XFILESIZE,x-requested-with,Authorization,token,check");
//        resp.setHeader("Access-Control-Allow-Credentials", "true");
        if ("OPTIONS".equals(req.getMethod())) {
            // 进行我们定义的请求前，浏览器自动发起的options请求
            System.out.println("options");
            // 服务器正常接收，返回状态码，不响应其他内容
            resp.setStatus(HttpStatus.ACCEPTED.value());
            return;
        }


        //3 不包含login获取token请求头


        //6 解析成功，放行
        filterChain.doFilter(servletRequest,servletResponse);
        return;
    }
}
