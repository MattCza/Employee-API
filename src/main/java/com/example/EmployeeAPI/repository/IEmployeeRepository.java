package com.example.EmployeeAPI.repository;

import com.example.EmployeeAPI.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmployeeRepository extends JpaRepository<Employee, Long>{
}
