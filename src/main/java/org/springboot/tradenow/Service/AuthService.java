package org.springboot.tradenow.Service;

import lombok.extern.slf4j.Slf4j;
import org.springboot.tradenow.Config.JwtProvider;
import org.springboot.tradenow.RequestDTO.RegisterRequest;
import org.springboot.tradenow.Entity.TwoFactorOTP;
import org.springboot.tradenow.Entity.User;
import org.springboot.tradenow.Repository.Implementation.TwoFactorOtpImpl;
import org.springboot.tradenow.Repository.UserRepository;
import org.springboot.tradenow.Response.AuthResponse;
import org.springboot.tradenow.Utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TwoFactorOtpImpl twoFactorOtpService;

    @Autowired
    private EmailService emailService;

    public AuthResponse RegisterService(RegisterRequest user) throws Exception {
        User isEmailExist = userRepository.findByEmail(user.getEmail());
        if (isEmailExist != null) {
            throw new Exception("Email already exists ......Try another email");
        }
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setFullName(user.getFullName());
        userRepository.save(newUser);


        Authentication auth = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword()
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = JwtProvider.generateToken(auth);

        AuthResponse response = new AuthResponse();
        response.setToken(jwt);
        response.setMessage("User registered successfully");
        response.setStatus(true);

        return response;

    }


    public  AuthResponse login(RegisterRequest user) throws Exception {

        String username = user.getEmail();
        String password = user.getPassword();

        Authentication auth = authenticate(username, password);

        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = JwtProvider.generateToken(auth);

        User authUser = userRepository.findByEmail(username);

        if(authUser.getTwoFactorAuth().isEnabled()){
            AuthResponse response = new AuthResponse();
            response.setToken(jwt);
            response.setIsTwoFactorAuthEnabled(true);
            response.setMessage("Two-factor auth is enabled");
            String otp = OtpUtils.generateOTP();

            TwoFactorOTP oldTwoFactorOtp = twoFactorOtpService.findByUserId(authUser.getId());
            if(oldTwoFactorOtp != null){
                twoFactorOtpService.deleteTwoFactorOtp(oldTwoFactorOtp);
            }
            TwoFactorOTP newTwoFactorOtp = twoFactorOtpService.createTwoFactorOtp(authUser, otp, jwt);
            emailService.sendOtpVerificationEmail(username, otp);
            response.setSession(newTwoFactorOtp.getId());
           return  response;
        }

        AuthResponse response = new AuthResponse();
        response.setToken(jwt);
        response.setMessage("User registered successfully");
        response.setStatus(true);

        return response;
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if(userDetails == null) {
            throw new BadCredentialsException("invalid username");
        }
        if(!password.equals(userDetails.getPassword())) {
            throw new BadCredentialsException("invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

    }

    public AuthResponse verifyOtp( String otp,String id)throws Exception{

        TwoFactorOTP twoFactorOTP = twoFactorOtpService.findById(id);

        if(twoFactorOtpService.verifyTwoFactorOtp(twoFactorOTP, otp)){
        AuthResponse response =  new AuthResponse();
        response.setIsTwoFactorAuthEnabled(true);
        response.setMessage("Two-factor authentication verifies successfully !!!");
        response.setToken(twoFactorOTP.getJwt());
        return response;
        }
        throw new Exception("Invalid  OTP");
    }

}
