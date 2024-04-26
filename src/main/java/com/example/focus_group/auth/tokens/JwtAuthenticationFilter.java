package com.example.focus_group.auth.tokens;


import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.focus_group.auth.entities.UserEntity;
import com.example.focus_group.auth.enumType.ErrorCode;
import com.example.focus_group.auth.exceptions.UnauthorizedException;
import com.example.focus_group.auth.services.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenService tokenService;
    private final UserService userService;
    private static final List<String> ALLOWED_PATHS = List.of(
            "/api/v1/auth",
            "api/v1/test",
            "/v2/api-docs",
            "/actuator",
            "/error",
            "/v3/api-docs",
            "/swagger-resources",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui",
            "/webjars",
            "/swagger-ui.html",
            "/ws/**",
            "/ws/info",
            "/ws"
    );

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (isRequestAllowedWithoutAuthentication(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String jwt = extractJwtFromRequest(request);
            String email = tokenService.extractUserEmailFromJWT(jwt);
            authenticateUserIfNecessary(email, jwt, request);
        } catch (UnauthorizedException e) {
            log.error("Authentication error: {}", e.getMessage());
            sendErrorResponse(e, response, request);
            return;
        } catch (JWTVerificationException e) {
            log.error("Authentication error: {}", e.getMessage());
            sendErrorResponse(new UnauthorizedException(ErrorCode.INVALID_ACCESS_TOKEN), response, request);
            return;
        }

        filterChain.doFilter(request, response);
    }


    private boolean isRequestAllowedWithoutAuthentication(HttpServletRequest request) {
        String requestPath = request.getServletPath();
        return ALLOWED_PATHS.stream().anyMatch(requestPath::contains);
    }

    private void authenticateUserIfNecessary(String email, String jwt, HttpServletRequest request) {
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserEntity userDetails = userService.findByEmail(email).orElseThrow();
            if (tokenService.validateToken(jwt)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        
        throw new UnsupportedOperationException("Unimplemented method 'extractJwtFromRequest'");
    }


    private void sendErrorResponse(UnauthorizedException e, HttpServletResponse response, HttpServletRequest request) {
        // TODO Auto-generated method stub
        //    ErrorDTO errorDTO = ErrorDTO.builder()
        //         .message(e.getErrorCode().map(ErrorCode::getMessage).orElse(e.getMessage()))
        //         .errorCode(e.getErrorCode().map(ErrorCode::name).orElse(null))
        //         .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
        //         .statusCode(HttpStatus.UNAUTHORIZED.value())
        //         .timestamp(LocalDateTime.now())
        //         .path(request.getRequestURI())
        //         .build();
        // response.setStatus(HttpStatus.UNAUTHORIZED.value());
        // response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // response.getWriter().write(mapper.writeValueAsString(errorDTO));
    }

}
