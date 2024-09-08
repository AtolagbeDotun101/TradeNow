package org.springboot.tradenow.Repository;

import org.springboot.tradenow.Entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Wallet findUserById(Long id);
}
