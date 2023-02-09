package com.example;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class InMemoryEmployeeRepositoryTest {
    InMemoryEmployeeRepository inMemoryEmployeeRepository;
    List<Employee> employees = new ArrayList<>();

    @Test
    public void testFindAllMethodInMemoryEmployeeRepository() {
        employees.add(new Employee("1", 1000.0));
        employees.add(new Employee("2", 2000.0));
        inMemoryEmployeeRepository = new InMemoryEmployeeRepository(employees);
        List<Employee> employeesList = inMemoryEmployeeRepository.findAll();
        Assertions.assertEquals(2, employeesList.size(),"Should list have 2 Employees");
        Assertions.assertEquals("1", employeesList.get(0).getId(),"Should the first Employee have number 1 as Id");
        Assertions.assertEquals("2", employeesList.get(1).getId(),"Should the second Employee have number 2 as Id");
        Assertions.assertEquals(1000.0, employeesList.get(0).getSalary(),"Should the first Employee have 1000.0 as Salary");
        Assertions.assertEquals(2000.0, employeesList.get(1).getSalary(),"Should the Second Employee have 2000.0 as Salary");
    }


    @Test
    public void testSaveMethodInMemoryEmployeeRepository() {
        inMemoryEmployeeRepository = new InMemoryEmployeeRepository(employees);
        inMemoryEmployeeRepository.save(new Employee("1", 1000.0));
        List<Employee> employeesList = inMemoryEmployeeRepository.findAll();
        Assertions.assertEquals(1, employeesList.size(),"Should list have 1 Employee");
        Assertions.assertEquals(1000.0, employeesList.get(0).getSalary(),"Should the Employee have 1000.0 as Salary");
        inMemoryEmployeeRepository.save(new Employee("1", 2000.0));
        Assertions.assertEquals(1, employeesList.size(),"Should list have 1 Employee after change the Salary");
        Assertions.assertEquals(2000.0, employeesList.get(0).getSalary(),"Should the Employee after change have 2000.0 as Salary");
    }

}
