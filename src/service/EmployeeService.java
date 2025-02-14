package service;

import model.Employee;
import repository.EmployeeRepository;

import java.math.BigDecimal;
import java.util.List;

public class EmployeeService {
    private final EmployeeRepository employeeRepository = new EmployeeRepository();

    public void addEmployee(String name, String position, BigDecimal salary) {
        Employee employee = new Employee(0, name, position, salary);
        employeeRepository.addEmployee(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.getAllEmployees();
    }
}