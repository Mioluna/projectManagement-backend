package com.backend.projeyonetimi.controller;

import com.backend.projeyonetimi.model.Employee;
import com.backend.projeyonetimi.model.Project;
import com.backend.projeyonetimi.service.EmployeeService;
import com.backend.projeyonetimi.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private EmployeeService employeeService;

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

    @GetMapping("/{projectId}/employees")
    public ResponseEntity<List<String>> getAllAssignees(@PathVariable int projectId){
        List<Employee> employees = projectService.getAllAssignees(projectId);
        List<String> employeeNames = employees.stream()
                .map(Employee::getFirstName)
            .collect(Collectors.toList());

        return ResponseEntity.ok(employeeNames);
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
