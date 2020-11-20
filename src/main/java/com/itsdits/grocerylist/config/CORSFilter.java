package com.itsdits.grocerylist.config;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * CORSFilter.java - This class implements {@link OncePerRequestFilter} to provide global configuration
 * settings for CORS.
 *
 * @author Tim Dillon
 * @version 1.0
 */
@Component
public class CORSFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                 final FilterChain filterChain) throws ServletException, IOException {
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,
                "Origin, Content-Type, Accept, Authorization, Accept-Language, connection, Cache-Control, Access-Control-Request-Method, Access-Control-Request-Headers");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,
                "Access-Control-Allow-Origin, Access-Control-Allow-Credentials");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://yourdomain.com/");
        filterChain.doFilter(request, response);
    }
}
