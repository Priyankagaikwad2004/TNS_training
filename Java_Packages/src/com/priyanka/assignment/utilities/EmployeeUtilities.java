package com.priyanka.assignment.utilities;

import com.priyanka.assignment.employee.Employee;

public class EmployeeUtilities {
	
	public void printEmployeeDetails(Employee employee) {
        System.out.println("--------------------");
        System.out.println("ID: " + employee.getEmployeeId());
        System.out.println("Name: " + employee.getName());
        System.out.println("Salary: " + employee.getSalary());
    }

}
