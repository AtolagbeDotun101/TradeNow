package org.springboot.tradenow.Repository.Implementation;

import org.springboot.tradenow.Entity.PaymentDetails;
import org.springboot.tradenow.Entity.User;

public interface PaymentDetailsImpl {
   public PaymentDetails addPaymentDetails(User user,
                                           String accountNumber,
                                           String accountHolder,
                                           String bankName,String ifsc);

   public PaymentDetails getUserPaymentDetails(User user);
}
