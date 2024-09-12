package org.springboot.tradenow.Controller;

import org.springboot.tradenow.Entity.*;
import org.springboot.tradenow.Repository.Implementation.UserImpl;
import org.springboot.tradenow.Service.OrderService;
import org.springboot.tradenow.Service.PaymentOrderService;
import org.springboot.tradenow.Service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {
    @Autowired
    private WalletService walletService;
    @Autowired
    private UserImpl userServiceRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PaymentOrderService paymentOrderService;


    @GetMapping
    public ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userServiceRepository.findUserProfileByJwt(jwt);
        Wallet userWallet = walletService.getUserWallet(user);
        return ResponseEntity.status(HttpStatus.OK).body(userWallet);
    }

    @PutMapping("/{walletId}/transfer")
    public ResponseEntity<Wallet> walletToWalletTransfer(@RequestHeader("Authorization")String jwt, @PathVariable Long walletId, WalletTransaction req) throws Exception {
        User sender = userServiceRepository.findUserProfileByJwt(jwt);
        Wallet receiverWallet = walletService.findWalletById(walletId);
        Wallet senderWallet = walletService.walletToWalletTransfer(sender,receiverWallet,req.getAmount());

        return ResponseEntity.status(HttpStatus.OK).body(senderWallet);
    }

    @PutMapping("/{orderId}/pay")
    public ResponseEntity<Wallet> payOrderPayment(@RequestHeader("Authorization")String jwt, @PathVariable Long orderId) throws Exception {
        User user = userServiceRepository.findUserProfileByJwt(jwt);
        Order order = orderService.getOrderById(orderId);

        Wallet wallet = walletService.payOrderPayment(order, user);

        return ResponseEntity.status(HttpStatus.OK).body(wallet);
    }

    @PutMapping("/deposit")
    public ResponseEntity<Wallet> depositToWallet(@RequestHeader("Authorization")String jwt,
                                                  @RequestParam(name = "order_id") Long orderId,
                                                  @RequestParam(name = "payment_id")Long paymentId) throws Exception {
        User user = userServiceRepository.findUserProfileByJwt(jwt);

        Wallet wallet = walletService.getUserWallet(user);

        PaymentOrder order = paymentOrderService.getPaymentOrderById(orderId);

        Boolean status = paymentOrderService.proceedPaymentOrder(order,paymentId);

        if(status){
            wallet = walletService.addBalance(wallet, order.getAmount());
        }

        return ResponseEntity.status(HttpStatus.OK).body(wallet);
    }
}







