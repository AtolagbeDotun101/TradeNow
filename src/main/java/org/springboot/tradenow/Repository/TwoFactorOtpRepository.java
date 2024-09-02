package org.springboot.tradenow.Repository;

import org.springboot.tradenow.Entity.TwoFactorOTP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwoFactorOtpRepository extends JpaRepository<TwoFactorOTP , String> {
     TwoFactorOTP findByUserId(Long userId);
}
