package com.backend.projeyonetimi.service;

import com.backend.projeyonetimi.model.Employee;
import com.backend.projeyonetimi.model.Project;
import com.backend.projeyonetimi.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee createEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    @Override
    public Employee findEmployeeById(Integer id){
        return employeeRepository.findEmployeeById(id).orElse(null);
    }

    @Override
    public void deleteEmployee(Employee employee){
        employeeRepository.delete(employee);
    }

    @Override
    public List<Project> getAssignments(Integer id) {
        return employeeRepository.findEmployeeById(id).get().getAssignments();
    }
}
