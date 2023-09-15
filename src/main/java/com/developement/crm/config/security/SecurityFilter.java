package com.developement.crm.config.security;

import com.developement.crm.service.AuthenticationService;
import com.developement.crm.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.UserDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;

    @Autowired
    AuthenticationService authenticationService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoversToken(request);
        if(token != null){
            var login = tokenService.validateToken(token);
//            .getSubject(): Após a verificação bem-sucedida do token,
//                você está extraindo o "subject" (assunto) do token.
//                O assunto é geralmente um identificador único associado ao usuário
//            ou à entidade que emitiu o token.
            UserDetails user = authenticationService.loadUserByUsername(login);
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);

    }

    private String recoversToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null || authHeader.isEmpty() || !authHeader.startsWith("Bearer ")){
            return null;
        }
        return authHeader.replace("Bearer ", "");
    }
}
