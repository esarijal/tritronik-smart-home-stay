package com.tritronik.hiringtest.smart_home_stay.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public JwtRequestFilter(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if(authHeader!=null && authHeader.startsWith("Bearer ")){
            jwt =authHeader.substring(7);
            try {
                username = Jwts.parser().setSigningKey(jwtSecret).build().parseClaimsJws(jwt).getBody().getSubject();
            } catch (ExpiredJwtException e) {
                logger.error("JWT Token has expired");
            }
            catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e){
                logger.error("Error parsing JWT", e);
            }
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(userDetails != null && validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean validateToken(String jwt, UserDetails userDetails) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(jwtSecret).build().parseClaimsJws(jwt);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            logger.error("JWT Token has expired");
            return false;
        }
        catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e){
            logger.error("Error validating JWT", e);
            return false;
        }
    }
}
