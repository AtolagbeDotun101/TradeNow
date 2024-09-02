package org.springboot.tradenow.Helper;

import org.springboot.tradenow.Config.JwtProvider;
import org.springboot.tradenow.Entity.TwoFactorAuth;
import org.springboot.tradenow.Entity.User;
import org.springboot.tradenow.Enum.VerificationType;
import org.springboot.tradenow.Repository.UserRepository;
import org.springboot.tradenow.Repository.Implementation.UserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserImpl {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {
        String email = JwtProvider.getEmailFromToken(jwt);
        User user = findUserByEmail(email);
        if (user == null) {
            throw new Exception("user not found !!!");
        }
        return user;
    }

    @Override
    public User findUserById(Long userId) throws Exception {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            return user.get();
        }
        throw new Exception("User not found !!!");
    }

    @Override
    public User enableTwoFactorAuthentication(User user, VerificationType verificationType, String sendTo) {
        TwoFactorAuth twoFactorAuth = new TwoFactorAuth();
        twoFactorAuth.setEnabled(true);
        twoFactorAuth.setSendTo(verificationType);
        user.setTwoFactorAuth(twoFactorAuth);

        return userRepository.save(user);
    }

    @Override
    public User updatePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        return userRepository.save(user);
    }
}
