package com.backend.projeyonetimi.repository;

import com.backend.projeyonetimi.model.Employee;
import com.backend.projeyonetimi.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Optional<Project> findProjectById(int id);
}
