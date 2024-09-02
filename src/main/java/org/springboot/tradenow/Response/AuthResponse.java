package org.springboot.tradenow.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class AuthResponse {


    private String token;
    private String session;
    private String message;
    private Boolean status;
    private Boolean isTwoFactorAuthEnabled;

}
