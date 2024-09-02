package org.springboot.tradenow.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springboot.tradenow.Enum.VerificationType;

@Entity
@Data
public class VerificationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String otp;

    @OneToOne
    private User user;

    private String mobile;
    private  String email;
    private VerificationType verificationType;
}
