package org.ubikz.vsubtitle.api.core.middleware.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.ubikz.vsubtitle.api.core.middleware.CORSMiddleware;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JWTAuthenticationFilter extends CORSMiddleware {
    private JwtProperties jwtProperties;

    public JWTAuthenticationFilter(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        Authentication authentication = TokenAuthentication.getAuthentication(
                (HttpServletRequest) request,
                this.jwtProperties
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        super.doFilter(request, response, filterChain);
    }
}
