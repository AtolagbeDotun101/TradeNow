package org.springboot.tradenow.Controller;

import org.springboot.tradenow.Entity.User;
import org.springboot.tradenow.Entity.Wallet;
import org.springboot.tradenow.Entity.Withdraw;
import org.springboot.tradenow.Helper.UserServiceImpl;
import org.springboot.tradenow.Service.UserService;
import org.springboot.tradenow.Service.WalletService;
import org.springboot.tradenow.Service.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/withdraw")
public class WithdrawController {
    @Autowired
    private WithdrawService withdrawService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private WalletService walletService;

//    @Autowired
//    private WalletTransactionService walletTransactionService;


    @PostMapping("/{amount}")
    public ResponseEntity<?> withdrawRequest(@RequestHeader("Authorization") String jwt, @PathVariable Long amount)throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Wallet wallet = walletService.getUserWallet(user);

        Withdraw withdraw = withdrawService.requestWithdraw(amount,user);
        walletService.addBalance(wallet, -amount);

        return ResponseEntity.ok(withdraw);
    }

    @PatchMapping("/admin/{id}/proceed/{accept}")
    public ResponseEntity<?> processWithdrawRequest(@RequestHeader("Authorization")String jwt, @PathVariable Long id, @PathVariable boolean accept)throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Wallet userWallet= walletService.getUserWallet(user);

        Withdraw processWithdraw =  withdrawService.processWithdraw(id,accept);
        if(!accept){
            walletService.addBalance(userWallet, processWithdraw.getAmount());
        }
        return ResponseEntity.ok(processWithdraw);
    }

    @GetMapping("/withdraw/history")
    public ResponseEntity<List<Withdraw>> getUserWithdrawHistory(@RequestHeader("Authorization")String jwt) throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
       List<Withdraw> history = withdrawService.getUserWithdrawHistory(user);
       return ResponseEntity.ok(history);

    }

    @GetMapping("/admin/withdraw/history")
    public  ResponseEntity<List<Withdraw>> getWithdrawHistory() throws Exception{
        List<Withdraw> history = withdrawService.getAllWithdrawRequest();
        return ResponseEntity.ok(history);
    }
}
