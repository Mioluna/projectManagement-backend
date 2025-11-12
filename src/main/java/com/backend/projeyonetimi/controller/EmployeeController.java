package com.backend.projeyonetimi.controller;

import com.backend.projeyonetimi.model.Employee;
import com.backend.projeyonetimi.model.Project;
import com.backend.projeyonetimi.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/create")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee){
        Employee newEmployee = employeeService.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEmployee);
    }

    @PutMapping("/update")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee){
        Employee updatedEmployee = employeeService.updateEmployee(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @GetMapping("/{employeeId}/assignments")
    public ResponseEntity<List<Project>> findAllRespAssignments(@PathVariable int employeeId){
        List<Project> assignments = employeeService.getAssignments(employeeId);
        return ResponseEntity.ok(assignments);
    }

    @DeleteMapping("/{employeeId}/delete")
    public void deleteEmployee(@PathVariable int employeeId){
        Employee employee = employeeService.findEmployeeById(employeeId);
        employeeService.deleteEmployee(employee);
    }
}

