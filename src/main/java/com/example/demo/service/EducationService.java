package com.example.demo.service;

import com.example.demo.dto.Education;
import com.example.demo.repository.EducationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EducationService {


    private final EducationRepository educationRepository;

    public EducationService(EducationRepository educationRepository){
        this.educationRepository = educationRepository;
    }

    public List<Education> getEducationById(int id){
        List<Education> educationList = educationRepository.findAll().stream().filter(edu -> edu.getUserId() == id).collect(Collectors.toList());
        return educationList;
    }

    public Education createEducation(int id, Education education){
        Education newEducation = Education.builder().userId(id).year(education.getYear()).title(education.getTitle())
                .description(education.getDescription()).build();
        educationRepository.addEducation(newEducation);
        return newEducation;
    }

}
