package org.springboot.tradenow.Controller;

import org.springboot.tradenow.RequestDTO.ForgotPasswordOtpRequest;
import org.springboot.tradenow.RequestDTO.ResetPasswordRequest;
import org.springboot.tradenow.Entity.User;
import org.springboot.tradenow.Enum.VerificationType;
import org.springboot.tradenow.Response.AuthResponse;
import org.springboot.tradenow.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users/profile")
    public ResponseEntity<User> getProfile(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.getUserProfile(jwt);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }


    @GetMapping("/users/verification/2fa/{verificationType}")
    public ResponseEntity<String> sendOtp(@RequestHeader("Authorization") String jwt, @PathVariable("verificationType")VerificationType verificationType) throws Exception {
       String response = userService.getVerificationOTP(jwt,verificationType );
       return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/users/enable/2fa/{otp}")
    public  ResponseEntity<User> enable2fa(@RequestHeader("Authorization") String jwt, @PathVariable("otp") String otp) throws Exception {
        User response = userService.enableTwoFactorAuth(jwt,otp);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/users/forgot-password-otp")
    public ResponseEntity<AuthResponse> ResetPasswordOtp(@RequestBody ForgotPasswordOtpRequest request) throws Exception {
        AuthResponse response = userService.forgotPasswordOtp(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/users/reset-password/{id}")
    public ResponseEntity<String> resetPassword(@PathVariable("id") String id, @RequestBody ResetPasswordRequest request) throws Exception {
      String  response= userService.resetPassword(id, request);
      return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}
