package org.springboot.tradenow.Repository.Implementation;

import org.springboot.tradenow.Entity.Order;
import org.springboot.tradenow.Entity.User;
import org.springboot.tradenow.Entity.Wallet;

public interface WalletImpl {

    Wallet getUserWallet(User user);
    Wallet addBalance(Wallet wallet, Long amount);
    Wallet findWalletById(Long id) throws Exception;
    Wallet walletToWalletTransfer(User sender,Wallet receiverWallet, Long amount) throws Exception;
    Wallet payOrderPayment(Order order, User user) throws Exception;
}
