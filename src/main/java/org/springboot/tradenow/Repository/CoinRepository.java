package org.springboot.tradenow.Repository;

import org.springboot.tradenow.Entity.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepository extends JpaRepository<Coin, String> {
}
