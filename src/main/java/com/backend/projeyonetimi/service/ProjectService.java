package com.backend.projeyonetimi.service;

import com.backend.projeyonetimi.model.Employee;
import com.backend.projeyonetimi.model.Project;

import java.util.List;

public interface ProjectService {
    public Project createProject(Project project);
    public Project updateProject(Project project);
    public List<Employee> getAllEmployees(Integer id);
    public void deleteProject(Integer id);
}
