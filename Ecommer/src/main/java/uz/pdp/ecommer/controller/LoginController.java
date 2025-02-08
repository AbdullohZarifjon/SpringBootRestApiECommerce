package uz.pdp.ecommer.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uz.pdp.ecommer.dto.LoginDto;
import uz.pdp.ecommer.entity.User;
import uz.pdp.ecommer.service.JwtService;

@RestController
@RequestMapping("/login")
public class LoginController {


    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public LoginController(JwtService jwtService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public HttpEntity<?> login(@RequestBody LoginDto loginDto) {
        var auth = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(auth);
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(jwtService.generateToken(user));
    }
}
