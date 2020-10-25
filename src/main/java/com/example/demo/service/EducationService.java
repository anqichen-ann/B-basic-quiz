package com.example.demo.service;

import com.example.demo.dto.Education;
import com.example.demo.dto.User;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.EducationRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EducationService {


    private final EducationRepository educationRepository;
    private final UserRepository userRepository;

    public EducationService(EducationRepository educationRepository, UserRepository userRepository){
        this.educationRepository = educationRepository;
        this.userRepository = userRepository;
//        Optional<User> user = userRepository.findById(1L);
//        if(user.isPresent()){
//            Education edu1 =  Education.builder().user(user.get()).year(2005).title("Secondary school specializing in artistic")
//                    .description("Eos, explicabo, nam, tenetur et ab eius deserunt aspernatur ipsum ducimus quibusdam quis voluptatibus.")
//                    .build();
//            educationRepository.save(edu1);
//            Education edu2 = Education.builder().user(user.get()).year(2009).title("First level graduation in Graphic Design")
//                    .description("Aspernatur, mollitia, quos maxime eius suscipit sed beatae ducimus quaerat quibusdam perferendis? Iusto, quibusdam asperiores unde repellat.")
//                    .build();
//            educationRepository.save(edu2);
//        }

    }

    public List<Education> getEducationById(long id){
        return educationRepository.findAllByUserId(id);
    }

    public Education createEducation(long id, Education education){
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new UserNotFoundException("用户不存在");
        }
        Education newEducation = Education.builder().user(user.get()).year(education.getYear()).title(education.getTitle())
                .description(education.getDescription()).build();
        educationRepository.save(newEducation);
        return newEducation;
    }

}
