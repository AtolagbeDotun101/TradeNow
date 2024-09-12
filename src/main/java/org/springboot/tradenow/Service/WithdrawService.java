package org.springboot.tradenow.Service;

import org.springboot.tradenow.Entity.User;
import org.springboot.tradenow.Entity.Withdraw;
import org.springboot.tradenow.Enum.WithdrawStatus;
import org.springboot.tradenow.Repository.Implementation.WithdrawImpl;
import org.springboot.tradenow.Repository.WithdrawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WithdrawService implements WithdrawImpl {
    @Autowired
    private WithdrawRepository withdrawRepository;

    @Override
    public Withdraw requestWithdraw(Long amount, User user) {
        Withdraw withdraw = new Withdraw();
        withdraw.setAmount(amount);
        withdraw.setUser(user);
        withdraw.setStatus(WithdrawStatus.PENDING);

        return withdrawRepository.save(withdraw);
    }

    @Override
    public Withdraw processWithdraw(Long withdrawId, boolean accept) throws Exception {
        Optional<Withdraw> withdraw = withdrawRepository.findById(withdrawId);
        if(withdraw.isEmpty()){
            throw new Exception("Withdraw request not found!");
        }
        Withdraw withdrawRequest = withdraw.get();
        withdrawRequest.setDate(LocalDateTime.now());
        if(accept){
            withdrawRequest.setStatus(WithdrawStatus.SUCCESS);
        }else withdrawRequest.setStatus(WithdrawStatus.DECLINED);

        return withdrawRepository.save(withdrawRequest);
    }

    @Override
    public List<Withdraw> getUserWithdrawHistory(User user) {
        return withdrawRepository.findByUserId(user.getId());
    }

    @Override
    public List<Withdraw> getAllWithdrawRequest() {
        return withdrawRepository.findAll();
    }
}
