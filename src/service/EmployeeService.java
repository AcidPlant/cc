package service;
import model.Employee;
import repository.EmployeeRepository;
public class EmployeeService {
    private final EmployeeRepository empRepo = new EmployeeRepository();
    public void addEmployee(Employee emp) { empRepo.addEmployee(emp); }
}