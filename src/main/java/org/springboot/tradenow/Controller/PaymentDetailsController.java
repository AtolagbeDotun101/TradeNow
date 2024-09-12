package org.springboot.tradenow.Controller;

import org.springboot.tradenow.Entity.PaymentDetails;
import org.springboot.tradenow.Entity.User;
import org.springboot.tradenow.Helper.UserServiceImpl;
import org.springboot.tradenow.RequestDTO.PaymentDetailsRequest;
import org.springboot.tradenow.Service.PaymentDetailsService;
import org.springboot.tradenow.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paymentDetails")
public class PaymentDetailsController {
    @Autowired
    private PaymentDetailsService paymentDetailsService;
    @Autowired
    private UserServiceImpl userService;


    @PostMapping
    public ResponseEntity<?> addPaymentDetails(@RequestHeader("Authorization")String jwt, @RequestBody PaymentDetailsRequest req)throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        PaymentDetails paymentDetails = paymentDetailsService.addPaymentDetails(user,req.getAccountNumber(), req.getAccountHolderName(),req.getBankName(), req.getIfsc());
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentDetails);

    }

    @GetMapping
    public ResponseEntity<?> getPaymentDetails(@RequestHeader("Authorization")String jwt)throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        PaymentDetails paymentDetails = paymentDetailsService.getUserPaymentDetails(user);
        return ResponseEntity.ok(paymentDetails);
    }
}
