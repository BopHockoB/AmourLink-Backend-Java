package ua.nure.userservice.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import ua.nure.userservice.util.JwtUtil;

import java.util.UUID;

@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String token = request.getHeader("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                String jwt = token.substring(7);
                UUID userId = jwtUtil.extractIdFromToken(jwt);
                request.setAttribute("userId", userId);  // Set user ID to request attribute
                return true;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;  // Stop the execution if token is not valid
    }
}
