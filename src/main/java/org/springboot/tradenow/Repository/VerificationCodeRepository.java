package org.springboot.tradenow.Repository;

import org.springboot.tradenow.Entity.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository  extends JpaRepository<VerificationCode, Long> {
    VerificationCode findByUserId(Long userId);
}
