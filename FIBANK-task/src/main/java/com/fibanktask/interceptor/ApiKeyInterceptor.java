package com.fibanktask.interceptor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.fibanktask.Utils.Constants.API_KEY_HEADER;
import static com.fibanktask.Utils.Constants.API_KEY_VALUE;

@Component
public class ApiKeyInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String apiKey = request.getHeader(API_KEY_HEADER);
        if (apiKey == null || !apiKey.equals(API_KEY_VALUE)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false; // Return false to reject the request
        }
        
        return true;
    }
}