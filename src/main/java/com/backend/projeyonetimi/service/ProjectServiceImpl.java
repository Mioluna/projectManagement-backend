package com.backend.projeyonetimi.service;

import com.backend.projeyonetimi.model.Employee;
import com.backend.projeyonetimi.model.Project;
import com.backend.projeyonetimi.repository.EmployeeRepository;
import com.backend.projeyonetimi.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService{

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project createProject(Project project){
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(Project project){
        Project existingProject = projectRepository.findById(project.getId())
                .orElseThrow(() -> new RuntimeException("Project not found with id " + project.getId()));

        existingProject.setName(project.getName());
        existingProject.setDescription(project.getDescription());
        existingProject.setStatus(project.getStatus());

        return projectRepository.save(project);
    }

    @Override
    public void deleteProject(Integer id){
        projectRepository.deleteById(id);
    }

    @Override
    public List<Employee> getAllAssignees(Integer projectId){
        return projectRepository.findProjectById(projectId)
                .map(Project::getAssignees)
                .orElse(null);
    }

    @Override
    public Project findProjectById(Integer id){
        return projectRepository.findProjectById(id).orElse(null);
    }

    @Override
    public void assignProject(Integer employeeId, Project project) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + employeeId));

        project.getAssignees().add(employee);
        employee.getAssignments().add(project);

        employeeRepository.save(employee);
        projectRepository.save(project);
    }

    @Override
    public void unassignProject(Integer employeeId, Project project){
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + employeeId));

        project.getAssignees().remove(employee);
        employee.getAssignments().remove(project);

        employeeRepository.save(employee);
        projectRepository.save(project);
    }

}
