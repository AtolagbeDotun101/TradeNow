package org.springboot.tradenow.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springboot.tradenow.Enum.WithdrawStatus;

import java.time.LocalDateTime;

@Entity
@Data
public class Withdraw {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private WithdrawStatus status;
    private Long amount;

    @ManyToOne
    private User user;
    private LocalDateTime date= LocalDateTime.now();
}
