package com.example.demo.controllers;

import com.example.demo.dtos.LoginRequest;
import com.example.demo.dtos.TokenResponse;
import com.example.demo.dtos.UserDto;
import com.example.demo.entities.RefreshToken;
import com.example.demo.repositories.RefreshTokenRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.security.JwtService;
import com.example.demo.services.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.entities.User;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final ModelMapper mapper;
    private final RefreshTokenRepository refreshTokenRepository;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        // authenticate
        Authentication authenticate = authenticate(loginRequest);
        User user = userRepository.findByEmail(loginRequest.email()).orElseThrow(() -> new BadCredentialsException("Invalid Username or Password"));
        if (!user.isEnable()) {
            throw new DisabledException("User is disabled");

        }


        String jti = UUID.randomUUID().toString();
        var refreshTokenOb = RefreshToken.builder()
                .jti(jti)
                .user(user)
                .createdAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(jwtService.getRefreshTtlSeconds()))
                .revoked(false)
                .build();

        //refresh token save--information
        refreshTokenRepository.save(refreshTokenOb);


        //access token--generate
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user, refreshTokenOb.getJti());

        // use cookie service to attach refresh token in cookie

//        cookieService.attachRefreshCookie(response, refreshToken, (int) jwtService.getRefreshTtlSeconds());
//        cookieService.addNoStoreHeaders(response);

        TokenResponse tokenResponse = TokenResponse.of(accessToken, refreshToken, jwtService.getAccessTtlSeconds(), mapper.map(user, UserDto.class));
        return ResponseEntity.ok(tokenResponse);

    }

    private Authentication authenticate(LoginRequest loginRequest) {
        try {

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));

        } catch (Exception e) {
            throw new BadCredentialsException("Invalid Username or Password !!");
        }
    }


    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerUser(userDto));
    }
}
