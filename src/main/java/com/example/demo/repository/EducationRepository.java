package com.example.demo.repository;

import com.example.demo.dto.Education;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {

    @Override
    List<Education> findAll();

    List<Education> findAllByUserId(long userId);

}
