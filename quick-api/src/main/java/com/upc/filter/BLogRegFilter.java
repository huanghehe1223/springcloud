package com.upc.filter;

import com.alibaba.fastjson2.JSONObject;
import com.upc.common.Result;
import com.upc.utils.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.IOException;
@Slf4j
@WebFilter(urlPatterns = "/*")
public class BLogRegFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req=(HttpServletRequest)servletRequest;
        HttpServletResponse resp=(HttpServletResponse)servletResponse;
        //预检请求直接放行
        String url=req.getRequestURI().toString();
        String method=req.getMethod();
        if (url.contains("quickquick"))
        {

            String jwt=req.getHeader("token");
            System.out.println(jwt);
            if(jwt==null||jwt=="")
            {
                log.info("令牌为空");
                Result error= Result.error("notLogin");
                //手动把对象转化为json格式字符串，并响应回去
                String notLogin= JSONObject.toJSONString(error);
                resp.getWriter().write(notLogin);
                return;
            }
            try {
                JwtUtils.parseJWT(jwt);
            } catch (Exception e) {
                e.printStackTrace();
                Result error=Result.error("notLogin");
                String notLogin=JSONObject.toJSONString(error);
                resp.getWriter().write(notLogin);
                return;
            }
            Result success=Result.success("login success");
            String login=JSONObject.toJSONString(success);
            resp.getWriter().write(login);
            return;

        }
        else
        {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }


    }
}
