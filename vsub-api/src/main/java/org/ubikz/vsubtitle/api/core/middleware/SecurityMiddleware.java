package org.ubikz.vsubtitle.api.core.middleware;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.ubikz.vsubtitle.api.core.middleware.security.AuthProvider;
import org.ubikz.vsubtitle.api.core.middleware.security.JWTAuthenticationFilter;
import org.ubikz.vsubtitle.api.core.middleware.security.JWTLoginFilter;
import org.ubikz.vsubtitle.api.core.middleware.security.JwtProperties;

@Configuration
@EnableWebSecurity
public class SecurityMiddleware extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthProvider authProvider;
    @Autowired
    private JwtProperties jwtProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/api/user").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                // We filter the api/login requests
                .addFilterBefore(
                        new JWTLoginFilter("/api/authenticate", authenticationManager(), this.jwtProperties),
                        UsernamePasswordAuthenticationFilter.class
                )
                // And filter other requests to check the presence of JWT in header
                .addFilterBefore(new JWTAuthenticationFilter(this.jwtProperties), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }
}
