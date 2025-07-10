package uz.app.lesson2.conf;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.app.lesson2.entity.User;

import java.io.IOException;
import java.util.Base64;

@Component
public class MyFilter extends OncePerRequestFilter {
    @Autowired
    @Lazy
    UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("authorization");
        if (authorization==null){
            filterChain.doFilter(request, response);
            return;
        }
        byte[] byteDecode = Base64.getDecoder().decode(authorization);
        String email = new String(byteDecode);
        User user = (User) userDetailsService.loadUserByUsername(email);
        setUserToContextHolder(user);
        filterChain.doFilter(request,response);
    }

    private void setUserToContextHolder(User user) {
        var principal = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(principal);
    }
}
