package org.springboot.tradenow.Entity;

import jakarta.persistence.Column;
import lombok.Data;
import org.springboot.tradenow.Enum.VerificationType;

@Data
public class TwoFactorAuth {
  @Column(nullable = false, columnDefinition = "BOOLEAN")
    private boolean isEnabled =false;
    private VerificationType sendTo;

}
