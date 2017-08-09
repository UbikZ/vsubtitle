package org.ubikz.vsubtitle.api.core.middleware.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.ubikz.vsubtitle.api.controller.ApiController;
import org.ubikz.vsubtitle.api.core.layer.service.message.BaseMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class TokenAuthentication {

    private static final String HEADER_AUTH = "Authorization";
    private static final String HEADER_CONTENT = "Content-Type";

    private static final String TOKEN_PREFIX = "Bearer";
    private static final String CLAIM_AUTHORITIES_KEY = "authorities";
    private static final String CLAIM_USERNAME_KEY = "username";
    private static final String CLAIM_CREDENTIALS_KEY = "credentials";

    static void addAuthentication(HttpServletResponse res, Authentication auth, JwtProperties props) throws IOException {
        BaseMessage message = new BaseMessage();

        String jwt = Jwts.builder()
                .setClaims(new HashMap<String, Object>() {{
                    put(CLAIM_USERNAME_KEY, auth.getName());
                    put(CLAIM_CREDENTIALS_KEY, auth.getCredentials());
                    put(CLAIM_AUTHORITIES_KEY, auth.getAuthorities());
                }})
                .setExpiration(TokenAuthentication.getExpirationTime(props.getExpire()))
                .signWith(SignatureAlgorithm.HS512, props.getSecret())
                .compact();

        message.setStatus(200);
        message.setSuccess(true);
        message.setData(new HashMap<String, Object>() {{
            put("token", jwt);
            put("roles", auth.getAuthorities());
        }});

        res.addHeader(HEADER_CONTENT, "application/json");
        res.getWriter().print(ApiController.buildMessage(message));
    }

    static Authentication getAuthentication(HttpServletRequest request, JwtProperties props) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Authentication auth = null;
        String token = request.getHeader(HEADER_AUTH);
        if (token != null) {
            Claims claims = Jwts.parser()
                    .setSigningKey(props.getSecret())
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();

            String username = (String) claims.get(CLAIM_USERNAME_KEY);
            ((List<LinkedHashMap>) claims.get(CLAIM_AUTHORITIES_KEY))
                    .forEach(map -> authorities.add(new SimpleGrantedAuthority((String) map.get("authority"))));

            if (username != null) {
                auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
            }
        }

        return auth;
    }

    static Date getExpirationTime(long expireHours) {
        Date now = new Date();
        Long expireInMilis = TimeUnit.HOURS.toMillis(expireHours);
        return new Date(expireInMilis + now.getTime());
    }
}
