package pl.grogowski.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.grogowski.model.User;
import pl.grogowski.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public boolean authenticate(String email, String password) {
        User fromDatabase = userRepository.findByEmail(email);
        if (fromDatabase!=null){
//            if(fromDatabase.getPassword().equals(password)){
//                return true;
//            }
            if (BCrypt.checkpw(password, fromDatabase.getPassword())) {
                return true;
            }
        }
        return false;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean userExists(String email) {
        return userRepository.findByEmail(email)!=null;
    }

    public void registerUser(User user) {
        String plainPassword = user.getPassword();
        user.setPassword(BCrypt.hashpw(plainPassword, BCrypt.gensalt()));
        userRepository.save(user);
    }
}
