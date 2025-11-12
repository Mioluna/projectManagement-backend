package com.backend.projeyonetimi.service;

import com.backend.projeyonetimi.model.Employee;
import com.backend.projeyonetimi.model.Project;

import java.util.List;

public interface ProjectService {
    public Project createProject(Project project);
    public Project updateProject(Project project);
    public Project findProjectById(Integer id);
    public List<Employee> getAllAssignees(Integer id);
    public void assignProject(Integer employeeId, Project project);
    public void unassignProject(Integer employeeId, Project project);
    public void deleteProject(Integer id);

}
