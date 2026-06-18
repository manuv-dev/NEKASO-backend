package gesimmo.nekaso.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gesimmo.nekaso.auth.dto.LoginRequestDto;
import gesimmo.nekaso.auth.dto.LoginResponseDto;
import gesimmo.nekaso.auth.dto.RegisterRequestDto;
import gesimmo.nekaso.auth.dto.RegisterResponseDto;
import gesimmo.nekaso.shared.Response.RestResponse;
import jakarta.validation.Valid;
import gesimmo.nekaso.auth.service.AuthSevice;



@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserDetailsService authService;
    private final AuthSevice service;

    public AuthController(UserDetailsService authService, gesimmo.nekaso.auth.service.AuthSevice service) {
        this.authService = authService;
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<RestResponse<LoginResponseDto>> login(@Valid @RequestBody LoginRequestDto request) {
        LoginResponseDto response = service.login(request);
        return ResponseEntity.ok(RestResponse.success(response, HttpStatus.OK));
    }
    @PostMapping("/register")
    public ResponseEntity<RestResponse<RegisterResponseDto>> register(@Valid @RequestBody RegisterRequestDto request) {
        var response = service.createUser(request);
        return ResponseEntity.ok(RestResponse.success(response, HttpStatus.OK));
    }
}
