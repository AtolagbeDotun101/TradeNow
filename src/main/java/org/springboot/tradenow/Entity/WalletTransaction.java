package org.springboot.tradenow.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springboot.tradenow.Enum.WalletTransactionType;

import java.time.LocalDate;

@Entity
@Data
public class WalletTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Wallet wallet;

    private String transferId;

    private LocalDate date;
    private String description;
    private WalletTransactionType type;
    private Long amount;

}
