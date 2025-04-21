package cn.edu.xjtlu.readingnotes.user.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.xjtlu.readingnotes.user.User;
import cn.edu.xjtlu.readingnotes.user.UserRepo;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private VerificationTokenRepo tokenRepo;

    public void createUser(User user) {
        userRepo.save(user);
    }

    public void enableUser(User user) {
        user.setIsEnabled(true);
        userRepo.save(user);
    }

    public void createVerificationToken(User user, String uuid) {
        VerificationToken token = new VerificationToken(uuid, user);
        tokenRepo.save(token);
    }

    public VerificationToken getVerificationToken(String uuid) {
        return tokenRepo.findByToken(uuid).orElseThrow();
    }

    public void cleanToken(VerificationToken token) {
        tokenRepo.delete(token);
    }
}
