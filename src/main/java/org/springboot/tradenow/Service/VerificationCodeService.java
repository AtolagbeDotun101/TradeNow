package org.springboot.tradenow.Service;

import org.springboot.tradenow.Entity.User;
import org.springboot.tradenow.Entity.VerificationCode;
import org.springboot.tradenow.Enum.VerificationType;
import org.springboot.tradenow.Repository.VerificationCodeRepository;
import org.springboot.tradenow.Repository.Implementation.VerificationCodeImpl;
import org.springboot.tradenow.Utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VerificationCodeService implements VerificationCodeImpl {
    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Override
    public VerificationCode sendVerificationCode(User user, VerificationType verificationType) {
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(OtpUtils.generateOTP());
        verificationCode.setVerificationType(verificationType);
        verificationCode.setUser(user);
        verificationCodeRepository.save(verificationCode);
        return verificationCode;
    }

    @Override
    public VerificationCode getVerificationCodeById(Long id) throws Exception {
        Optional<VerificationCode> verificationCode = verificationCodeRepository.findById(id);
        if (verificationCode.isPresent()) {
            return verificationCode.get();
        }
        throw new Exception("Verification code not found!!!");
    }

    @Override
    public VerificationCode getVerificationCodeByUserId(Long userId) {
        return verificationCodeRepository.findByUserId(userId);
    }

    @Override
    public void deleteVerificationCodeById(VerificationCode verificationCode) {
            verificationCodeRepository.delete(verificationCode);
    }
}
