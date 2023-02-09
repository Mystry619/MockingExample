package com.example;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;


public class EmployeeManagerTest {


    private EmployeeRepository employeeRepository;
    private BankService bankService;
    private EmployeeManager employeeManager;
    private List<Employee> employees = new ArrayList<>();

    @Before
    public void initializeForMockitoTests(){
        employeeRepository = mock(EmployeeRepository.class);
        bankService = mock(BankService.class);
        employeeManager = new EmployeeManager(employeeRepository, bankService);
        employees.add(new Employee("1", 2000.0));
        employees.add(new Employee("2", 3000.0));
        employees.add(new Employee("3", 4000.0));
    }



    @Test
    public void testPayEmployeesMethodUsingMockito() {
        when(employeeRepository.findAll()).thenReturn(employees);
        int payments = employeeManager.payEmployees();
        verify(bankService,Mockito.times(1)).pay("1", 2000.0);
        verify(bankService,Mockito.times(1)).pay("2", 3000.0);
        verify(bankService,Mockito.times(1)).pay("3", 4000.0);
        Assertions.assertEquals(3, payments,"Should be 3 approved payments");
    }

    @Test
    public void testPayEmployeesMethodWithThrowingExceptionUsingMockito() {
        when(employeeRepository.findAll()).thenReturn(employees);
        doThrow(new RuntimeException()).when(bankService).pay("3", 4000.0);
        int payments = employeeManager.payEmployees();
        verify(bankService, Mockito.times(1)).pay("1",2000.0);
        verify(bankService, Mockito.times(1)).pay("2",3000.0);
        verify(bankService, Mockito.times(1)).pay("3",4000.0);
        Assertions.assertEquals(true,employees.get(0).isPaid(),"Should the first Employee marked as paid");
        Assertions.assertEquals(true,employees.get(1).isPaid(),"Should the second Employee marked as paid");
        Assertions.assertEquals(false,employees.get(2).isPaid(),"Should not the third Employee marked as paid");
        Assertions.assertEquals(2, payments,"Should be 2 approved payments");
    }


    @Test
    public void testPayEmployeeMethodWithStub() {
        com.example.EmployeeRepositoryStub employeeRepositoryStub = new com.example.EmployeeRepositoryStub();
        BankServiceDummy bankServiceDummy = new BankServiceDummy();
        EmployeeManager employeeManager = new EmployeeManager(employeeRepositoryStub, bankServiceDummy);
        employeeRepositoryStub.save(new Employee("1",2000.0));
        employeeRepositoryStub.save(new Employee("2",3000.0));
        employeeRepositoryStub.save(new Employee("3",4000.0));
        List<Employee> employeeList = employeeRepositoryStub.findAll();
        int payments = employeeManager.payEmployees();
        Assertions.assertEquals(true, employeeList.get(0).isPaid(),"Should the first Employee marked as paid");
        Assertions.assertEquals(true, employeeList.get(1).isPaid(),"Should the second Employee marked as paid");
        Assertions.assertEquals(true, employeeList.get(2).isPaid(),"Should the third Employee marked as paid");
        Assertions.assertEquals(3, payments,"Should be 3 approved payments");

    }

}
