package com.es.phoneshop.web.filter;

import com.es.phoneshop.model.DOSProtection.DOSProtectionService;
import com.es.phoneshop.model.DOSProtection.DOSProtectionServiceImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DOSProtectionFilter implements Filter {
    public static final int TOO_MANY_REQUESTS_CODE = 429;
    private DOSProtectionService protectionService;

    @Override
    public void init(FilterConfig filterConfig) {
        protectionService = DOSProtectionServiceImpl.getInstance();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String ip = request.getRemoteAddr();
        if (protectionService.isAllowed(ip)) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse) response).setStatus(TOO_MANY_REQUESTS_CODE);
        }
    }

    @Override
    public void destroy() {
    }
}
