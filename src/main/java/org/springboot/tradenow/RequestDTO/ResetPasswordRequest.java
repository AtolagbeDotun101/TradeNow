package org.springboot.tradenow.RequestDTO;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String password;
    private String otp;
}
