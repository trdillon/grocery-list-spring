package com.itsdits.grocerylist.service;

import com.itsdits.grocerylist.model.User;
import com.itsdits.grocerylist.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUser(String userId) {
        return userRepository.findById(userId);
    }
}
