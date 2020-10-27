package com.example.demo.controller;

import com.example.demo.dto.Education;
import com.example.demo.dto.User;
import com.example.demo.exception.SourceNotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.EducationService;
import com.example.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final EducationService educationService;

    public UserController(UserRepository userRepository, UserService userService, EducationService educationService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.educationService = educationService;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") long id){
        return userRepository.findById(id).orElseThrow(() -> new SourceNotFoundException("User not found"));
    }

    @GetMapping("/{id}/educations")
    public List<Education> getEducationById(@PathVariable("id") int id){
        return educationService.getEducationById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody @Valid User user){
        return userService.createUser(user);
    }

    @PostMapping("/{id}/educations")
    @ResponseStatus(HttpStatus.CREATED)
    public Education addEducation(@PathVariable("id") int id, @RequestBody @Valid Education education){
        return educationService.createEducation(id, education);
    }
}
