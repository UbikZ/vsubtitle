package org.ubikz.vsubtitle.api.core.middleware;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CORSMiddleware extends GenericFilterBean {
    public static void handleHeaders(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String ORIGIN = "Origin";

        if (request.getHeader(ORIGIN) != null) {
            String origin = request.getHeader(ORIGIN);

            response.addHeader("Access-Control-Allow-Origin", origin);
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
        }

        if (request.getMethod().equals("OPTIONS")) {
            response.getWriter().print("OK");
            response.getWriter().flush();
        }
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        CORSMiddleware.handleHeaders((HttpServletRequest) req, (HttpServletResponse) res);

        chain.doFilter(req, res);
    }
}