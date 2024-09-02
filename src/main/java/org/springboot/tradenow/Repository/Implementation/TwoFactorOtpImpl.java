package org.springboot.tradenow.Repository.Implementation;

import org.springboot.tradenow.Entity.TwoFactorOTP;
import org.springboot.tradenow.Entity.User;

public interface TwoFactorOtpImpl {

    TwoFactorOTP createTwoFactorOtp(User user, String otp, String jwt);

    TwoFactorOTP findByUserId(Long userId);

    TwoFactorOTP findById(String id);

    boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOtp, String otp);

    void deleteTwoFactorOtp(TwoFactorOTP twoFactorOtp);

}
