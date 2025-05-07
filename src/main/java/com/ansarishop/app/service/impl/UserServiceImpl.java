package com.ansarishop.app.service.impl;

import com.ansarishop.app.constants.Constants;
import com.ansarishop.app.entity.User;
import com.ansarishop.app.repository.UserRepository;
import com.ansarishop.app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @Override
    public User saveUser(User user) {
        logger.info("{} {}", Constants.USER_LOG_PREFIX, "User details to be saved "+ user);
        String pass = user.getPassword();
        user.setPassword(passwordEncoder.encode(pass));
        userRepository.save(user);
        logger.info("{} {}", Constants.USER_LOG_PREFIX, "Data saved successfully ...");
        return user;
    }

    @Override
    public List<User> getAll() {
        logger.info("{} {}", Constants.USER_LOG_PREFIX, "Fetching all user from DB ");
        return userRepository.findAll();
    }

    @Override
    public User findByUserName(String username) {
        return userRepository.findByUserName(username).orElseThrow(()->
                new RuntimeException("User not found")
        );
    }
}
