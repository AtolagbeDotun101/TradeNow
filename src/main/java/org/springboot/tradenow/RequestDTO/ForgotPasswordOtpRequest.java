package org.springboot.tradenow.RequestDTO;

import lombok.Data;
import org.springboot.tradenow.Enum.VerificationType;

@Data
public class ForgotPasswordOtpRequest {
    private String email;
    private VerificationType verificationType;
//    private String mobile;
}
