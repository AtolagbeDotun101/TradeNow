package org.springboot.tradenow.Repository;

import org.springboot.tradenow.Entity.Withdraw;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WithdrawRepository extends JpaRepository<Withdraw, Long> {
    List<Withdraw> findByUserId(Long userId);
}
