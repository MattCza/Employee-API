package com.example.EmployeeAPI.service;

import com.example.EmployeeAPI.entity.Employee;

import java.util.List;

public interface IEmployeeService {

    Employee saveEmployee(Employee employee);

    List<Employee> getAllEmployees();
}
