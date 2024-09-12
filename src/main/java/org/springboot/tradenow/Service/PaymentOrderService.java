package org.springboot.tradenow.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springboot.tradenow.Entity.PaymentOrder;
import org.springboot.tradenow.Entity.User;
import org.springboot.tradenow.Enum.OrderStatus;
import org.springboot.tradenow.Enum.PaymentMethod;
import org.springboot.tradenow.Repository.Implementation.PaymentOrderImpl;
import org.springboot.tradenow.Repository.PaymentOrderRepository;
import org.springboot.tradenow.Response.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentOrderService implements PaymentOrderImpl {
    @Autowired
    private PaymentOrderRepository paymentOrderRepository;

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Value("${razorpay.api.key}")
    private String razorpayApiKey;

    @Value("${razorpay.api.secret}")
    private String razorpayApiSecret;

    @Override
    public PaymentOrder createPaymentOrder(User user, Long amount, PaymentMethod paymentMethod) {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setUser(user);
        paymentOrder.setAmount(amount);
        paymentOrder.setPaymentMethod(paymentMethod);
        return paymentOrderRepository.save(paymentOrder);

    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) throws Exception {
        return paymentOrderRepository.findById(id).orElseThrow(()-> new Exception("Payment order not found !"));

    }

    @Override
    public Boolean proceedPaymentOrder(PaymentOrder paymentOrder, Long paymentId) {
        if(paymentOrder.getStatus().equals(OrderStatus.PENDING)){
            if (paymentOrder.getPaymentMethod().equals(PaymentMethod.STRIPE)){
                return true;
            }
        }
        return null;
    }

    @Override
    public PaymentResponse createRazorPaymentLink(User user, Long amount) {
        return null;
    }

    @Override
    public PaymentResponse createStripePaymentLink(User user, Long amount, Long orderId) throws StripeException {
        Stripe.apiKey = stripeApiKey;
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:9000/wallet?order_id="+orderId)
                .setCancelUrl("http://localhost/9000/payment/cancel")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setUnitAmount(amount*100)
                                .setProductData(SessionCreateParams
                                        .LineItem
                                        .PriceData
                                        .ProductData
                                        .builder()
                                        .setName("Top up wallet")
                                        .build())
                                .build())
                        .build()
                        ).build();
        Session session = Session.create(params);
        System.out.println("session created" + session);
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setPayment_url(session.getUrl());


        return paymentResponse;
    }
}
