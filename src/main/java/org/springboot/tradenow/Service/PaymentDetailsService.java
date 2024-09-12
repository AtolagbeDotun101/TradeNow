package org.springboot.tradenow.Service;

import org.springboot.tradenow.Entity.PaymentDetails;
import org.springboot.tradenow.Entity.User;
import org.springboot.tradenow.Repository.Implementation.PaymentDetailsImpl;
import org.springboot.tradenow.Repository.PaymentDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentDetailsService implements PaymentDetailsImpl {
    @Autowired
    private PaymentDetailsRepository paymentDetailsRepository;

    @Override
    public PaymentDetails addPaymentDetails(User user, String accountNumber, String accountHolderName, String bankName, String ifsc) {
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setAccountNumber(accountNumber);
        paymentDetails.setBankName(bankName);
        paymentDetails.setIfsc(ifsc);
        paymentDetails.setAccountHolderName(accountHolderName);
        paymentDetails.setUser(user);
        return paymentDetailsRepository.save(paymentDetails);
    }

    @Override
    public PaymentDetails getUserPaymentDetails(User user) {
        PaymentDetails paymentDetails = paymentDetailsRepository.findByUserId(user.getId());
        return paymentDetails;
    }
}
