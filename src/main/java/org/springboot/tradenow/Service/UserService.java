package org.springboot.tradenow.Service;

import org.springboot.tradenow.RequestDTO.ForgotPasswordOtpRequest;
import org.springboot.tradenow.RequestDTO.ResetPasswordRequest;
import org.springboot.tradenow.Entity.ForgotPasswordToken;
import org.springboot.tradenow.Entity.User;
import org.springboot.tradenow.Entity.VerificationCode;
import org.springboot.tradenow.Enum.VerificationType;
import org.springboot.tradenow.Repository.Implementation.ForgotPasswordTokenImpl;
import org.springboot.tradenow.Repository.Implementation.UserImpl;
import org.springboot.tradenow.Repository.Implementation.VerificationCodeImpl;
import org.springboot.tradenow.Response.AuthResponse;
import org.springboot.tradenow.Utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserImpl userServiceRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private VerificationCodeImpl verificationCodeService;

    @Autowired
    private ForgotPasswordTokenImpl forgotPasswordTokenService;

    public User getUserProfile(String jwt) throws Exception {
        return userServiceRepository.findUserProfileByJwt(jwt);
    }

    public String getVerificationOTP (String jwt, VerificationType verificationType) throws Exception {
        User user = getUserProfile(jwt);

        VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUserId(user.getId());
        if (verificationCode == null) {
            verificationCode = verificationCodeService.sendVerificationCode(user,verificationType);

        }
        if(verificationType.equals(VerificationType.EMAIL)) {
            emailService.sendOtpVerificationEmail(user.getEmail(), verificationCode.getOtp());
        }
        return "OTP sent successfully";
    }


    public User enableTwoFactorAuth (String jwt, String otp) throws Exception {
        User user = getUserProfile(jwt);
        VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUserId(user.getId());

        String sendTo = verificationCode.getVerificationType().equals(VerificationType.EMAIL)?
                verificationCode.getEmail() : verificationCode.getMobile();
        boolean isVerified = verificationCode.getOtp().equals(otp);
        if(isVerified) {
            User enabledUserOtp = userServiceRepository.enableTwoFactorAuthentication(user,verificationCode.getVerificationType(), sendTo);
            verificationCodeService.deleteVerificationCodeById(verificationCode);
            return enabledUserOtp;
        }
        throw  new Exception("Invalid OTP..... Verification failed !!!");
    }

    public AuthResponse forgotPasswordOtp (ForgotPasswordOtpRequest request) throws Exception {
        User user = userServiceRepository.findUserByEmail(request.getEmail());
        ForgotPasswordToken token = forgotPasswordTokenService.findByUser(user.getId());
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();

        if (token == null) {
        token = forgotPasswordTokenService.createForgotPasswordToken(user,id, OtpUtils.generateOTP(),request.getVerificationType(), request.getEmail() );
        }
        if (request.getVerificationType().equals(VerificationType.EMAIL)) {
            emailService.sendOtpVerificationEmail(user.getEmail(), token.getOtp());
        }
        AuthResponse response = new AuthResponse();
        response.setSession(token.getId());
        response.setMessage("Forgot password token sent successfully");
        return response;
    }

    public String resetPassword(String id, ResetPasswordRequest request) throws Exception {
        ForgotPasswordToken forgotPasswordToken = forgotPasswordTokenService.findById(id);

        boolean isVerified = forgotPasswordToken.getOtp().equals(request.getOtp());
        if(isVerified) {
            userServiceRepository.updatePassword(forgotPasswordToken.getUser(), request.getPassword());
            return "Password reset successfully";
        }
        throw  new Exception("Invalid OTP..... Verification failed !!!");
    }

}
