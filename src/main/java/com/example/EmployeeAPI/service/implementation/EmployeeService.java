package com.example.EmployeeAPI.service.implementation;

import com.example.EmployeeAPI.entity.Employee;
import com.example.EmployeeAPI.exception.ResourceNotFoundException;
import com.example.EmployeeAPI.repository.IEmployeeRepository;
import com.example.EmployeeAPI.service.IEmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService implements IEmployeeService {

    private IEmployeeRepository employeeRepository;

    public EmployeeService(IEmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "ID", id)
        );
    }

    @Override
    public Employee updateEmployee(Employee employee, long id) {
        Employee currentEmployee = getEmployeeById(id);
        currentEmployee.setFirstName(employee.getFirstName());
        currentEmployee.setLastName(employee.getLastName());
        currentEmployee.setEmail(employee.getEmail());
        currentEmployee.setPhoneNumber(employee.getPhoneNumber());
        saveEmployee(currentEmployee);
        return currentEmployee;
    }

    @Override
    public void deleteEmployee(long id) {
        getEmployeeById(id);
        employeeRepository.deleteById(id);
    }
}
