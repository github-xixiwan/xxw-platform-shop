package com.xxw.shop.module.security.filter;

import com.xxw.shop.module.security.xss.XssWrapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 一些简单的安全过滤： xss
 */
@WebFilter(filterName = "xssFilter")
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class XssFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(XssFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // replaceAll("[\r\n]" =》 Potential CRLF Injection for logs
        logger.info("AuthFilter RequestURI :{}", req.getRequestURI().replaceAll("[\r\n]", ""));
        // xss 过滤
        chain.doFilter(new XssWrapper(req), resp);
    }
}
