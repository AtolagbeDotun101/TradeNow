package org.springboot.tradenow.Controller;

import org.springboot.tradenow.Entity.User;
import org.springboot.tradenow.Entity.Wallet;
import org.springboot.tradenow.Entity.WalletTransaction;
import org.springboot.tradenow.Repository.Implementation.UserImpl;
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
    public ResponseEntity<Wallet> walletToWalletTransfer(@RequestHeader("Authorization")String jwt, @PathVariable Long orderId) throws Exception {
        User sender = userServiceRepository.findUserProfileByJwt(jwt);


        return ResponseEntity.status(HttpStatus.OK).body();
    }
}







