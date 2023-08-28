package com.turanbalayev.simpleblogapp.controller;


import com.turanbalayev.simpleblogapp.payload.JWTAuthResponse;
import com.turanbalayev.simpleblogapp.payload.LoginDto;
import com.turanbalayev.simpleblogapp.payload.RegisterDto;
import com.turanbalayev.simpleblogapp.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication operations")
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @Operation(
            summary = "Login to an account",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "You logged in successfully."
            )
    )
    @PostMapping(value = {"/login","/signin"})
    public ResponseEntity<JWTAuthResponse> login (@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);
        JWTAuthResponse response = new JWTAuthResponse();
        response.setAccessToken(token);
        return ResponseEntity.ok(response);
    }


    @Operation(
            summary = "Create a new account",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "A new account has been created successfully."
            )
    )
    @PostMapping(value = {"/register","/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
