package org.springboot.tradenow.Service;

import org.springboot.tradenow.Entity.Order;
import org.springboot.tradenow.Entity.User;
import org.springboot.tradenow.Entity.Wallet;
import org.springboot.tradenow.Enum.OrderType;
import org.springboot.tradenow.Repository.Implementation.WalletImpl;
import org.springboot.tradenow.Repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletService implements WalletImpl {
    @Autowired
    private WalletRepository walletRepository;

    @Override
    public Wallet getUserWallet(User user) {
        Wallet wallet = walletRepository.findUserById(user.getId());
        if (wallet == null) {
            wallet = new Wallet();
            wallet.setUser(user);
        }
        return wallet;
    }

    @Override
    public Wallet addBalance(Wallet wallet, Long amount) {
        BigDecimal walletBalance = wallet.getBalance();
         walletBalance = walletBalance.add(BigDecimal.valueOf(amount)) ;
        wallet.setBalance(walletBalance);
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findWalletById(Long id) throws Exception {
        Optional<Wallet> wallet = walletRepository.findById(id);
        if (wallet.isPresent()) {
            return wallet.get();
        }
        throw new Exception("Wallet not found !");
    }

    @Override
    public Wallet walletToWalletTransfer(User sender, Wallet receiver, Long amount) throws Exception {
        Wallet senderWallet = getUserWallet(sender);
//        Wallet recipientWallet = findWalletById(receiver.getId());
        if(senderWallet.getBalance().compareTo(BigDecimal.valueOf(amount))<0){
            throw new Exception("Insufficient funds");
        }
        BigDecimal walletBalance = senderWallet.getBalance().subtract(BigDecimal.valueOf(amount));
        senderWallet.setBalance(walletBalance);
        walletRepository.save(senderWallet);

        receiver.setBalance(receiver.getBalance().add(BigDecimal.valueOf(amount)));
        walletRepository.save(receiver);
        return senderWallet;
    }

    @Override
    public Wallet payOrderPayment(Order order, User user) throws Exception {
        Wallet wallet = getUserWallet(user);
        if(order.getOrderType().equals(OrderType.BUY)){
            BigDecimal newBalance = wallet.getBalance().subtract(order.getPrice());
            if(newBalance.compareTo(order.getPrice())<0){
                throw new Exception("Insufficient fund");
            }
            wallet.setBalance(newBalance);
        }else {
            BigDecimal newBalance = wallet.getBalance().add(order.getPrice());
            wallet.setBalance(newBalance);
        }
        walletRepository.save(wallet);
        
        return wallet;
    }
}
