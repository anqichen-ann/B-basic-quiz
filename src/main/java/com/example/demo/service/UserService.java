package com.example.demo.service;

import com.example.demo.dto.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user){
        int id = userRepository.getLength() + 1;
        User newUser = User.builder().age(user.getAge()).id(id).avatar(user.getAvatar())
                .name(user.getName()).build();
        userRepository.addUser(newUser);
        return newUser;
    }
}
