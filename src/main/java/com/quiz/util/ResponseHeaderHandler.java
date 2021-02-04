package com.quiz.util;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ResponseHeaderHandler extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse,
            final FilterChain filterChain) throws ServletException, IOException {
        httpServletResponse.addHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        httpServletResponse.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        httpServletResponse.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}