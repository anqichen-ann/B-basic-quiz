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

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    EducationService educationService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") int id){
        User user = userRepository.getUserById(id);
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
        return ResponseEntity.created(null).body(newUser);
    }

    @PostMapping("/{id}/educations")
    public ResponseEntity<Education> addEducation(@PathVariable("id") int id, @RequestBody @Valid Education education){
        Education newEducation = educationService.createEducation(id, education);
        return ResponseEntity.created(null).body(newEducation);
    }
}
