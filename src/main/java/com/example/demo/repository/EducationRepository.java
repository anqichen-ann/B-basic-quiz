package com.example.demo.repository;

import com.example.demo.dto.Education;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class EducationRepository {

    private List<Education> educationList = new ArrayList<>();

    public EducationRepository(){
        Education education = new Education(1, 2005, "Secondary school specializing in artistic",
                "Eos, explicabo, nam, tenetur et ab eius deserunt aspernatur ipsum ducimus quibusdam quis voluptatibus.");
        educationList.add(education);
        education = new Education(1, 2009, "First level graduation in Graphic Design",
                "Aspernatur, mollitia, quos maxime eius suscipit sed beatae ducimus quaerat quibusdam perferendis? Iusto, quibusdam asperiores unde repellat.");
        educationList.add(education);
    }

    public List<Education> findAll(){
        return educationList;
    }

    public void addEducation(Education education){
        educationList.add(education);
    }
}
