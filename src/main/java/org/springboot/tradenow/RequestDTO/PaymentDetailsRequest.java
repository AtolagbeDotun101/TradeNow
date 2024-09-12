package org.springboot.tradenow.RequestDTO;

import lombok.Data;

@Data
public class PaymentDetailsRequest {
    private String accountHolderName;
    private String bankName;
    private String ifsc;
    private String accountNumber;
}
