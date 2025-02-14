package Controllers;
import model.Employee;
import model.Car;
import model.CarProducingMachine;
import service.CarService;
import service.EmployeeService;
import service.MachineService;

import java.math.BigDecimal;


public class FactoryController {
    private final EmployeeService empService = new EmployeeService();
    private final MachineService machineService = new MachineService();
    private final CarService carService = new CarService();

    public void addEmployee(String name, String position, BigDecimal salary) {
        if (name == null || name.trim().isEmpty() || position == null || position.trim().isEmpty() ||
                salary == null || salary.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("❌ Ошибка: Некорректные данные для сотрудника!");
            return;
        }
        empService.addEmployee(new Employee(0, name, position, salary));
        System.out.println("✅ Сотрудник добавлен: " + name);
    }

    public void addMachine(String name, int capacity, String status) {
        if (name == null || name.trim().isEmpty() || capacity <= 0 ||
                (!status.equalsIgnoreCase("ACTIVE") &&
                        !status.equalsIgnoreCase("INACTIVE") &&
                        !status.equalsIgnoreCase("MAINTENANCE"))) {
            System.out.println("❌ Ошибка: Некорректные данные для машины!");
            return;
        }
        machineService.addMachine(new CarProducingMachine(0, name, "Generic Model", capacity, status));
        System.out.println("✅ Машина добавлена: " + name);
    }

    public void addCar(String name, String category, double price, int productionYear) {
        if (name == null || name.trim().isEmpty() || category == null || category.trim().isEmpty() ||
                price <= 0 || productionYear < 1886 || productionYear > 2100) {
            System.out.println("❌ Ошибка: Некорректные данные для автомобиля!");
            return;
        }
        carService.addCar(new Car(0, name, category, productionYear, price));
        System.out.println("✅ Автомобиль добавлен: " + name);
    }
}
