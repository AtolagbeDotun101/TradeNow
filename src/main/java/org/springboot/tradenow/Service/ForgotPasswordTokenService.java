package org.springboot.tradenow.Service;

import org.springboot.tradenow.Entity.ForgotPasswordToken;
import org.springboot.tradenow.Entity.User;
import org.springboot.tradenow.Enum.VerificationType;
import org.springboot.tradenow.Repository.ForgotPasswordTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ForgotPasswordTokenService implements org.springboot.tradenow.Repository.Implementation.ForgotPasswordTokenImpl {
    @Autowired
    private ForgotPasswordTokenRepository forgotPasswordTokenRepository;

    @Override
    public ForgotPasswordToken createForgotPasswordToken(User user, String id, String otp, VerificationType verificationType, String sendTo) {
       ForgotPasswordToken forgotPasswordToken = new ForgotPasswordToken();
       forgotPasswordToken.setUser(user);
       forgotPasswordToken.setOtp(otp);
       forgotPasswordToken.setVerificationType(verificationType);
       forgotPasswordToken.setSendTo(sendTo);
       forgotPasswordToken.setId(id);

        return forgotPasswordTokenRepository.save(forgotPasswordToken);
    }

    @Override
    public ForgotPasswordToken findById(String id) {
        Optional<ForgotPasswordToken> token = forgotPasswordTokenRepository.findById(id);
        return token.orElse(null);
    }

    @Override
    public ForgotPasswordToken findByUser(Long id) {
        return forgotPasswordTokenRepository.findByUserId(id);
    }

    @Override
    public void deleteForgotPasswordToken(ForgotPasswordToken token) {
        forgotPasswordTokenRepository.delete(token);
    }
}
