package ua.nure.subscriptionservice.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import ua.nure.subscriptionservice.util.JwtUtil;


import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {
    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        try {
            String token = extractToken(request);
            if (isValidToken(token)) {
                UUID userId = jwtUtil.extractIdFromToken(token);
                request.setAttribute("userId", userId);  // Set user ID to request attribute
                return true;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        return false;  // Stop the execution if token is not valid
    }

    // Extracts the token from the request
    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    // Checks if the token is valid
    private boolean isValidToken(String token) {
        return token != null && !jwtUtil.isTokenExpired(token);
    }
}