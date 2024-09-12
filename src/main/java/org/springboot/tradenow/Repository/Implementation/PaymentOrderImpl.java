package org.springboot.tradenow.Repository.Implementation;

import com.stripe.exception.StripeException;
import org.springboot.tradenow.Entity.PaymentOrder;
import org.springboot.tradenow.Entity.User;
import org.springboot.tradenow.Enum.PaymentMethod;
import org.springboot.tradenow.Response.PaymentResponse;

public interface PaymentOrderImpl {
    PaymentOrder createPaymentOrder(User user, Long amount, PaymentMethod paymentMethod);
    PaymentOrder getPaymentOrderById(Long id) throws Exception;
    Boolean proceedPaymentOrder(PaymentOrder paymentOrder, Long paymentId);
    PaymentResponse createRazorPaymentLink(User user, Long amount);
    PaymentResponse createStripePaymentLink(User user, Long amount, Long orderId) throws StripeException;
}
