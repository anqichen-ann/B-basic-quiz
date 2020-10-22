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
        //TODO GTB-完成度: - UserService.java:17 id 的生成逻辑最好不要跟当前 users 数量有这么强的耦合
        int id = userRepository.getLength() + 1;
        User newUser = User.builder().age(user.getAge()).id(id).avatar(user.getAvatar())
                .name(user.getName()).description(user.getDescription()).build();
        userRepository.addUser(newUser);
        return newUser;
    }
}
