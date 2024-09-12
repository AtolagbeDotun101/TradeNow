package org.springboot.tradenow.Repository.Implementation;

import org.springboot.tradenow.Entity.User;
import org.springboot.tradenow.Entity.Withdraw;

import java.util.List;

public interface WithdrawImpl {

    Withdraw requestWithdraw(Long amount, User user);
    Withdraw processWithdraw(Long withdrawId, boolean accept) throws Exception;
    List<Withdraw> getUserWithdrawHistory(User user);
    List<Withdraw> getAllWithdrawRequest();
}
