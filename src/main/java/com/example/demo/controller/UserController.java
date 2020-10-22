package com.example.demo.controller;

import com.example.demo.dto.Education;
import com.example.demo.dto.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.EducationService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
    //TODO GTB-工程实践: - 推荐使用构造函数注入
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    EducationService educationService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") int id){
        User user = userRepository.getUserById(id);
        //TODO GTB-知识点: - 这种情况下不需要用 ResponseEntity 再包一层。其它处同理。
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}/educations")
    public ResponseEntity<List<Education>> getEducationById(@PathVariable("id") int id){
        List<Education> educationList = educationService.getEducationById(id);
        return ResponseEntity.ok(educationList);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid User user){
        User newUser = userService.createUser(user);
        //TODO GTB-知识点: - 可以使用 @ResponseStatus 来简化。
        return ResponseEntity.created(null).body(newUser);
    }

    @PostMapping("/{id}/educations")
    public ResponseEntity<Education> addEducation(@PathVariable("id") int id, @RequestBody @Valid Education education){
        Education newEducation = educationService.createEducation(id, education);
        return ResponseEntity.created(null).body(newEducation);
    }
}
