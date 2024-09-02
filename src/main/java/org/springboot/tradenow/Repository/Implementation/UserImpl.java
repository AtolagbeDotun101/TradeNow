package org.springboot.tradenow.Repository.Implementation;

import org.springboot.tradenow.Entity.User;
import org.springboot.tradenow.Enum.VerificationType;

public interface UserImpl {

    public User findUserByEmail(String email)throws Exception;
    public  User findUserProfileByJwt(String jwt) throws Exception;
    public User findUserById(Long userId)throws Exception;

    public User enableTwoFactorAuthentication(User user, VerificationType verificationType, String sendTo)throws Exception;

    public User updatePassword(User user, String password)throws Exception;


}
