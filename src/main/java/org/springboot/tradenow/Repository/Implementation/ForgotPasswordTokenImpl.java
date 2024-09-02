package org.springboot.tradenow.Repository.Implementation;

import org.springboot.tradenow.Entity.ForgotPasswordToken;
import org.springboot.tradenow.Entity.User;
import org.springboot.tradenow.Enum.VerificationType;

public interface ForgotPasswordTokenImpl {

    public ForgotPasswordToken createForgotPasswordToken(User user, String id,
                                                         String otp, VerificationType verificationType
                                                        , String sendTo);

    public ForgotPasswordToken findById(String id);
    public ForgotPasswordToken findByUser(Long id);
    void deleteForgotPasswordToken(ForgotPasswordToken token);
}
