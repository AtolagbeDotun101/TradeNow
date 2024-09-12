package org.springboot.tradenow.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springboot.tradenow.Enum.OrderStatus;
import org.springboot.tradenow.Enum.PaymentMethod;

@Entity
@Data
public class PaymentOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long amount;
    private OrderStatus status;
    private PaymentMethod paymentMethod;

    @ManyToOne
    private User user;
}
