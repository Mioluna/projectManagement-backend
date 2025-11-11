package com.backend.projeyonetimi.service;

import com.backend.projeyonetimi.model.Employee;
import com.backend.projeyonetimi.model.Project;
import com.backend.projeyonetimi.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService{

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Project createProject(Project project){
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(Project project){
        return projectRepository.save(project);
    }

    @Override
    public void deleteProject(Integer id){
        projectRepository.deleteById(id);
    }

    @Override
    public List<Employee> getAllEmployees(Integer projectId){
        return projectRepository.findProjectById(projectId).get().getAssignees();
    }
}
