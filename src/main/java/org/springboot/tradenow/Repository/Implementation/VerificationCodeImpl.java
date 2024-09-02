package org.springboot.tradenow.Repository.Implementation;

import org.springboot.tradenow.Entity.User;
import org.springboot.tradenow.Entity.VerificationCode;
import org.springboot.tradenow.Enum.VerificationType;

public interface VerificationCodeImpl {

    VerificationCode sendVerificationCode(User user, VerificationType verificationType);

    VerificationCode getVerificationCodeById(Long id) throws Exception;

    VerificationCode getVerificationCodeByUserId(Long userId);

    void  deleteVerificationCodeById(VerificationCode verificationCode);
}
