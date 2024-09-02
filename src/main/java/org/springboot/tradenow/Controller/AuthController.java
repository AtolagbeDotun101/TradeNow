package org.springboot.tradenow.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springboot.tradenow.RequestDTO.RegisterRequest;
import org.springboot.tradenow.Response.AuthResponse;
import org.springboot.tradenow.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Validated
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService service;


    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest user) throws Exception {

        AuthResponse response = service.RegisterService(user);
        return  ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody RegisterRequest user) throws Exception {
       AuthResponse response = service.login(user);
        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/two-factor/verify/{otp}")
    public ResponseEntity<AuthResponse> verifyOTP(@PathVariable("otp") String otp, @RequestParam String id) throws Exception {
        AuthResponse response = service.verifyOtp(otp, id);
        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
