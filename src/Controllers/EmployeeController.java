package Controllers;

import model.Employee;
import service.EmployeeService;

import java.math.BigDecimal;
import java.util.List;

public class EmployeeController {
    private final EmployeeService employeeService = new EmployeeService();

    public void addEmployee(String name, String position, BigDecimal salary) {
        employeeService.addEmployee(name, position, salary);
    }

    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }
}