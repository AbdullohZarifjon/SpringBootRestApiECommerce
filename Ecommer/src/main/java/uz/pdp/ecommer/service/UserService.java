package uz.pdp.ecommer.service;

import org.springframework.stereotype.Service;
import uz.pdp.ecommer.entity.User;
import uz.pdp.ecommer.repo.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByUserName(String userName) {
        return userRepository.findByUsername(userName);
    }
}
