package com.projeto.dsc.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthozizationFilter extends OncePerRequestFilter {

    @Autowired//gambi para nao dar erro
    private  JwtUserDatailsService jwtUserDatailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String token = request.getHeader(JwtUtls.JWT_AUTHORIZATION);

        if (token == null || !token.startsWith(JwtUtls.JWT_BEARER)) {
            log.info("Token esta nulo ou sem Bearer");
            filterChain.doFilter(request, response);
            return;
        }

        if (!JwtUtls.isTokenIsValid(token)) {
            log.warn("Token invalido");
            filterChain.doFilter(request, response);
            return;
        }

        String username = JwtUtls.getUsernameFromToken(token);
        toAuthentication(request,username);

        filterChain.doFilter(request, response);
        log.info("Usuario Logado");
    }

    private void toAuthentication(HttpServletRequest request, String username) {
        UserDetails user = jwtUserDatailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authentication =  UsernamePasswordAuthenticationToken
                .authenticated(user,null , user.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


}
