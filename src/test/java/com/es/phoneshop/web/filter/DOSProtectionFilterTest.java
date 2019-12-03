package com.es.phoneshop.web.filter;

import com.es.phoneshop.model.DOSProtection.DOSProtectionService;
import com.es.phoneshop.model.DOSProtection.DOSProtectionServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DOSProtectionFilterTest {

    DOSProtectionFilter filter;

    @Mock
    private FilterConfig filterConfig;

    @Mock
    private ServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    private DOSProtectionService protectionService;

    @Before
    public void setup() {
        filter = new DOSProtectionFilter();
        protectionService = DOSProtectionServiceImpl.getInstance();
    }

    @Test
    public void testDoFilterWithPositiveResult() throws IOException, ServletException {
        String ip = "3";
        when(request.getRemoteAddr()).thenReturn(ip);
        filter.init(filterConfig);
        filter.doFilter(request, response, chain);
        verify(chain).doFilter(request, response);
    }

    @Test
    public void testDoFilterWithNegativeResult() throws IOException, ServletException {
        String ip = "4";
        when(request.getRemoteAddr()).thenReturn(ip);
        for (int i = 0; i < 20; i++) {
            protectionService.isAllowed(ip);
        }
        filter.init(filterConfig);
        filter.doFilter(request, response, chain);
        verify(chain, never()).doFilter(request, response);
    }
}
