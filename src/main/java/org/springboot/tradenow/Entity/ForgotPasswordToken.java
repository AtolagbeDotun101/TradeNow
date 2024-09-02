package org.springboot.tradenow.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springboot.tradenow.Enum.VerificationType;

@Entity
@Data
public class ForgotPasswordToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @OneToOne
    private User user;
    private String otp;
    private VerificationType verificationType;
    private String sendTo;
}
