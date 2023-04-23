package com.example.EmployeeAPI.service;

import com.example.EmployeeAPI.entity.Employee;
import com.example.EmployeeAPI.exception.ResourceNotFoundException;
import com.example.EmployeeAPI.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "ID", id)
        );
    }

    public Employee updateEmployee(Employee employee, long id) {
        Employee currentEmployee = getEmployeeById(id);
        currentEmployee.setFirstName(employee.getFirstName());
        currentEmployee.setLastName(employee.getLastName());
        currentEmployee.setEmail(employee.getEmail());
        currentEmployee.setPhoneNumber(employee.getPhoneNumber());
        saveEmployee(currentEmployee);
        return currentEmployee;
    }

    public void deleteEmployee(long id) {
        getEmployeeById(id);
        employeeRepository.deleteById(id);
    }
}
