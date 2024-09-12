package org.springboot.tradenow.Repository;

import org.springboot.tradenow.Entity.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {
    PaymentOrder findByUserId(Long userId);
}
