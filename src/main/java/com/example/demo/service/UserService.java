package com.example.demo.service;

import com.example.demo.dto.Education;
import com.example.demo.dto.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private int USERNAMES = 0;
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
//        User user = User.builder().name("KAMIL").age(24).avatar("https://inews.gtimg.com/newsapp_match/0/3581582328/0")
//                .description("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Repellendus, non, dolorem, cumque distinctio magni quam expedita velit laborum sunt amet facere tempora ut fuga aliquam ad asperiores voluptatem dolorum! Quasi.")
//                .build();
//        userRepository.save(user);
    }

    public User createUser(User user){
        USERNAMES++;
        int id = USERNAMES;
        User newUser = User.builder().age(user.getAge()).id(id).avatar(user.getAvatar())
                .name(user.getName()).description(user.getDescription()).build();
        userRepository.save(newUser);
        return newUser;
    }
}
