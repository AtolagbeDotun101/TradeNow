package org.springboot.tradenow.Controller;

import org.springboot.tradenow.Entity.PaymentOrder;
import org.springboot.tradenow.Entity.User;
import org.springboot.tradenow.Enum.PaymentMethod;
import org.springboot.tradenow.Helper.UserServiceImpl;
import org.springboot.tradenow.Response.PaymentResponse;
import org.springboot.tradenow.Service.PaymentOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentOrderController {
    @Autowired
    private PaymentOrderService paymentOrderService;

    @Autowired
    private UserServiceImpl userService;


    @PostMapping("/{paymentMethod}/amount/{amount}")
    public ResponseEntity<?> paymentHandler(@RequestHeader("Authorization")String jwt,
                                            @PathVariable PaymentMethod paymentMethod,
                                            @PathVariable Long amount)throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        PaymentResponse paymentResponse;
        PaymentOrder order = paymentOrderService.createPaymentOrder(user,amount,paymentMethod);

        if (paymentMethod.equals(PaymentMethod.RAZORPAY)){
            paymentResponse = paymentOrderService.createRazorPaymentLink(user,amount);
        }else paymentResponse = paymentOrderService.createStripePaymentLink(user,amount,order.getId());
        return ResponseEntity.ok().body(paymentResponse);
    }


}
