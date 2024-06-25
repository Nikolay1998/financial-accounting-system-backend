package kraynov.n.financialaccountingsystembackend.security.impl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

public class FASBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
            throws IOException {
        // toDo: change headers
        response.addHeader("WWW-Authenticate", "Basic realm=\"\" + getRealmName() + \"\"");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        writer.print("{\"message\":\"" + authEx.getMessage() + "\"}");
        writer.flush();
    }

    @Override
    public void afterPropertiesSet() {
        setRealmName("FAS");
        super.afterPropertiesSet();
    }
}
