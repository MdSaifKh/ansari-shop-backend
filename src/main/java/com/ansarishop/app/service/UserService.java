package com.ansarishop.app.service;

import com.ansarishop.app.entity.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    List<User> getAll();
    User findByUserName(String username);
    default void print(){
        System.out.print("Interface is implemented");
    }
}
