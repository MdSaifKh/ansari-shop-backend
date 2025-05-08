package com.ansarishop.app.controller;

import com.ansarishop.app.entity.AuthResponse;
import com.ansarishop.app.entity.LoginRequest;
import com.ansarishop.app.service.impl.UserServiceImpl;
import com.ansarishop.app.utils.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private JWTUtils jwtUtils;

    @PostMapping("/authenticate")
    public AuthResponse login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        logger.info(" === inside authenticate method === ");
        AuthResponse response = new AuthResponse();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword())
            );
            response.setToken(jwtUtils.generateToken(request.getUserName()));

        } catch (AuthenticationException e) {
            //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            logger.error(e.getMessage());
            response.setError(e.getMessage());
        }
        return response;
    }
}

