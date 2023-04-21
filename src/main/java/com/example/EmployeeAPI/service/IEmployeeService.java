package com.example.EmployeeAPI.service;

import com.example.EmployeeAPI.entity.Employee;

import java.util.List;

public interface IEmployeeService {

    Employee saveEmployee(Employee employee);

    List<Employee> getAllEmployees();
    Employee getEmployeeById(Long id);
    Employee updateEmployee(Employee employee, long id);
    void deleteEmployee(long id);
}
