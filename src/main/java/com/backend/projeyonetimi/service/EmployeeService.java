package com.backend.projeyonetimi.service;

import com.backend.projeyonetimi.model.Employee;
import com.backend.projeyonetimi.model.Project;


import java.util.List;

public interface EmployeeService {
    public Employee createEmployee(Employee employee);
    public Employee updateEmployee(int employeeId, Employee employee);
    public Employee findEmployeeById(Integer id);
    public List<Project> getAssignments(Integer id);
    public void deleteEmployee(Employee employee);
    public List<Employee> getAllEmployees();
}
