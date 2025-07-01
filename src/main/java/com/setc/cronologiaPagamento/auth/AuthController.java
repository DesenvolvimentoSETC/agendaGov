package com.setc.cronologiaPagamento.auth;

import com.setc.cronologiaPagamento.agendas.dto.AuthRequest;
import com.setc.cronologiaPagamento.agendas.dto.AuthResponse;
import com.setc.cronologiaPagamento.agendas.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        var authentication = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );

        authManager.authenticate(authentication);

        String token = jwtService.generateToken(request.getUsername());

        return ResponseEntity.ok(new AuthResponse(token));
    }
}
