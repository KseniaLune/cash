package com.example.cash.web.security;

import com.example.cash.domain.exception.ResourceMappingEx;
import com.example.cash.domain.exception.ResourceNotFoundEx;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@AllArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvider tokenProvider;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String bearerToken =((HttpServletRequest) request).getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")){
        bearerToken = bearerToken.substring(7);
        }
        if (bearerToken != null && tokenProvider.validateToken(bearerToken)){
            try {
                Authentication authentication = tokenProvider.getAuthentication(bearerToken);
                if (authentication!=null){
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (ResourceNotFoundEx ignored){}
        }
        chain.doFilter(request, response);
    }
}
