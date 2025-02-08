package uz.pdp.ecommer.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.pdp.ecommer.entity.User;
import uz.pdp.ecommer.service.JwtService;
import uz.pdp.ecommer.service.UserService;

import java.io.IOException;

@Component
public class MyFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserService userService;

    public MyFilter(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String token = request.getHeader("token");
//        System.out.println(token);
//        if (token != null) {
//            if (jwtService.validate(token)) {
//                String userName = jwtService.getUserName(token);
//                User user = userService.getUserByUserName(userName);
//
//                var auth = new UsernamePasswordAuthenticationToken(
//                        userName,null, user.getRoles()
//                );
//                SecurityContextHolder.getContext().setAuthentication(auth);
//            }
//        }
//        filterChain.doFilter(request, response);
//    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");

        if (token != null && jwtService.validate(token)) {
            String userName = jwtService.getUserName(token);
            User user = userService.getUserByUserName(userName);

            if (user != null) {
                var auth = new UsernamePasswordAuthenticationToken(
                        user.getUsername(), null, user.getRoles()
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }
}
