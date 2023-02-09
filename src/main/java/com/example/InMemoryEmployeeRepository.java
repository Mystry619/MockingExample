package com.example;

import java.util.ArrayList;
import java.util.List;

public class InMemoryEmployeeRepository implements EmployeeRepository{

    private List<Employee> employees = new ArrayList<>();

    public InMemoryEmployeeRepository(List<Employee> employees) {
        this.employees.addAll(employees);
    }

    @Override
    public List<Employee> findAll() {
        return employees;
    }

    @Override
    public Employee save(Employee employee) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId().equals(employee.getId())){
                employees.set(i,employee);
                return employee;
            }

        }
        employees.add(employee);
        return employee;
    }
}
