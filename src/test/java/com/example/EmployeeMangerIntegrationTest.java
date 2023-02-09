package com.example;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Arrays;
import java.util.List;

public class EmployeeMangerIntegrationTest {
    InMemoryEmployeeRepository inMemoryEmployeeRepository;
    EmployeeManager employeeManager;

    @Before
    public void initialize() {
        List<Employee> employees = Arrays.asList(new Employee("1", 1000.0), new Employee("2", 2000.0));
        inMemoryEmployeeRepository = new InMemoryEmployeeRepository(employees);
        BankServiceDummy bankServiceDummy = new BankServiceDummy();
        employeeManager = new EmployeeManager(inMemoryEmployeeRepository, bankServiceDummy);
    }

    @Test
    public void testPayEmployeesMethodUsingInMemoryEmployeeRepository() {
        List<Employee> employeesList = inMemoryEmployeeRepository.findAll();
        int payments = employeeManager.payEmployees();
        Assertions.assertEquals(2, payments,"Should be 2 approved payments");
        Assertions.assertEquals(true, employeesList.get(0).isPaid(),"Should the first Employee marked as paid");
        Assertions.assertEquals(true, employeesList.get(1).isPaid(),"Should the second Employee marked as paid");
    }

    @Test
    public void testPayEmployeesMethodUsingInMemoryEmployeeRepositoryWithChangingSalaryBeforePaying() {
        List<Employee> employeesList = inMemoryEmployeeRepository.findAll();
        Assertions.assertEquals(1000.0, employeesList.get(0).getSalary(),"Should the first Employee have 1000.0 as Salary");
        Assertions.assertEquals(2000.0, employeesList.get(1).getSalary(),"Should the Second Employee have 2000.0 as Salary");
        inMemoryEmployeeRepository.save(new Employee("1", 3000.0));
        inMemoryEmployeeRepository.save(new Employee("2", 4000.0));
        int paymentsAfterChangeSalary = employeeManager.payEmployees();
        Assertions.assertEquals(2, paymentsAfterChangeSalary,"Should be 2 approved payments after changing Salary");
        Assertions.assertEquals(3000.0, employeesList.get(0).getSalary(),"Should the first Employee after change have 3000.0 as Salary");
        Assertions.assertEquals(4000.0, employeesList.get(1).getSalary(),"Should the Second Employee after change have 4000.0 as Salary");
    }
}
