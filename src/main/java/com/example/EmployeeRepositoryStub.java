package com.example;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRepositoryStub implements EmployeeRepository {

    private List<Employee> employees = new ArrayList<>();
    @Override
    public List<Employee> findAll() {
        return employees;
    }

    @Override
    public Employee save(Employee employee) {
        employees.add(employee);
        return employee;
    }
}
