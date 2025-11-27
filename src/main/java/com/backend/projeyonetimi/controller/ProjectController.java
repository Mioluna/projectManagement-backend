package com.backend.projeyonetimi.controller;

import com.backend.projeyonetimi.model.Employee;
import com.backend.projeyonetimi.model.Project;
import com.backend.projeyonetimi.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/all")
    public ResponseEntity<List<Project>> getAllProjects(){
        List<Project> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProject(@PathVariable int projectId) {
        Project existingProject = projectService.findProjectById(projectId);
    return ResponseEntity.ok(existingProject);}

    @PostMapping("/create")
    public ResponseEntity<Project> createProject(@RequestBody Project project){
        Project newProject = projectService.createProject(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProject);
    }

    @PutMapping("/{projectId}/update")
    public ResponseEntity<Project> updateProject(@PathVariable int projectId, @RequestBody Project project) {

        Project existingProject = projectService.findProjectById(projectId);
        existingProject.setName(project.getName());
        existingProject.setDescription(project.getDescription());
        existingProject.setStatus(project.getStatus());

        Project updatedProject = projectService.updateProject(existingProject);
        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping("/{projectId}/delete")
    public void deleteProject(@PathVariable int projectId){
        projectService.deleteProject(projectId);
    }

    @GetMapping("/{projectId}/assignees")
    public ResponseEntity<List<Employee>> getAllAssignees(@PathVariable int projectId){
        List<Employee> employees = projectService.getAllAssignees(projectId);
        return ResponseEntity.ok(employees);
    }

    @PostMapping("/{projectId}/assign")
    public ResponseEntity<String> assignProject(@PathVariable int projectId, @RequestBody List<Integer> employeeIds) {

        Project project = projectService.findProjectById(projectId);

        for (Integer employeeId : employeeIds) {
            projectService.assignProject(employeeId, project);
        }

        return ResponseEntity.ok("Employees ASSIGNED to the project '" + project.getName() + "' successfully.");
    }

    @PostMapping("/{projectId}/unassign")
    public ResponseEntity<String> unassignProject(@PathVariable int projectId, @RequestBody List<Integer> employeeIds){
        Project project = projectService.findProjectById(projectId);
        for (Integer employeeId : employeeIds) {
            projectService.unassignProject(employeeId, project);
        }
        return ResponseEntity.ok("Employees UNASSIGNED to the project " + project.getName() + " successfully.");
    }
}
