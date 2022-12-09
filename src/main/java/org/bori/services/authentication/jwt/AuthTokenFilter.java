package org.bori.services.authentication.jwt;

import org.bori.services.authentication.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * AuthTokenFilter filters each incoming request based on the authentication status.
 */
@Component
public class AuthTokenFilter implements Filter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }

    /**
     * Filters a request based on authentication status, relying on the jwt token sent with the
     * request. If the jwt token is valid, then the UserDetails of the current user are set in
     * the SecurityContext.
     *
     * @param request     is the request to be filtered.
     * @param response    is the response to the request.
     * @param filterChain is the chain of filters through which the request is going.
     * @throws ServletException if the execution of filterChain throws a ServletException.
     * @throws IOException      if the execution of filterChain throws an IOException.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         javax.servlet.FilterChain filterChain) throws IOException, javax.servlet.ServletException {
        try {
            String jwt = parseJwt((HttpServletRequest) request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getUserNameFromJwtToken(jwt);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails((javax.servlet.http.HttpServletRequest) request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
            throw (e);
        }
        filterChain.doFilter(request, response);
    }
}
