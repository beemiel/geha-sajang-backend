package com.incense.gehasajang.security;

import com.incense.gehasajang.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().println("{ \"message\" : \"" + ErrorCode.ACCESS_DENIED.getMessage()
                + "\", \"code\" : \"" +  ErrorCode.ACCESS_DENIED.getCode()
                + "\", \"status\" : " + ErrorCode.ACCESS_DENIED.getStatus()
                + ", \"errors\" : [ ] }");
    }
}
