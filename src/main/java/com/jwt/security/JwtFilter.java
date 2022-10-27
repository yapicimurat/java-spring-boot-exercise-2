package com.jwt.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailService jwtUserDetailService;

    @Autowired
    private TokenManager tokenManager;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(request.getServletPath().equals("/login")){
            doFilter(request, response, filterChain);
        }
        else{

            final String tokenHeader = request.getHeader("Authorization");

            String username = null;
            String token = null;

            if(tokenHeader != null && tokenHeader.startsWith("Bearer ")){
                token = tokenHeader.substring(7);

                try{
                    username = tokenManager.getUsernameFromToken(token);
                }catch(IllegalArgumentException e){

                    System.out.println("Unable to get JWT");

                }catch(ExpiredJwtException e){
                    System.out.println("JWT has expired");
                }

            }else{
                System.out.println("Bearer string not found in the Authorization header...");
            }

            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = jwtUserDetailService.loadUserByUsername(username);

                if(tokenManager.validateJwt(token, userDetails)){
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());


                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext()
                            .setAuthentication(authenticationToken);


                }
            }

            filterChain.doFilter(request, response);
        }



    }

}
