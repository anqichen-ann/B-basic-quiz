package com.example.demo.repository;

import com.example.demo.dto.User;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class UserRepository {
    private List<User> userList = new ArrayList<>();

    public UserRepository(){
        User user = new User(1, "KAMIL", 24, "https://inews.gtimg.com/newsapp_match/0/3581582328/0",
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Repellendus, non, dolorem, cumque distinctio magni quam expedita velit laborum sunt amet facere tempora ut fuga aliquam ad asperiores voluptatem dolorum! Quasi."
);
        userList.add(user);
    }

    public User getUserById(int id){
        return userList.get(id-1);
    }

    public int getLength(){
        return userList.size();
    }

    public void addUser(User user){
        userList.add(user);
    }
}
