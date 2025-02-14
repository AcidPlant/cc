import Controllers.AuthController;
import Controllers.CarController;
import Controllers.EmployeeController;
import Controllers.MachineController;
import Controllers.OrderController;
import model.User;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import config.DatabaseConnection;
import config.DatabaseInitializer;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final CarController carController = new CarController();
    private static final EmployeeController employeeController = new EmployeeController();
    private static final MachineController machineController = new MachineController();
    private static final AuthController authController = new AuthController();
    private static final OrderController orderController = new OrderController();
    private static User currentUser = null;

    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            if (connection != null) {
                System.out.println("✅ Database connection is active.");
            } else {
                System.out.println("❌ Failed to establish database connection.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Database connection error: " + e.getMessage());
        }
        DatabaseInitializer.initializeDatabase();

        while (true) {
            if (currentUser == null) {
                System.out.println("\n🚗 Добро пожаловать в Car Factory!");
                System.out.println("1️⃣ - Вход");
                System.out.println("2️⃣ - Регистрация");
                System.out.println("0️⃣ - Выход");
                System.out.print("Выберите действие: ");

                int choice = scanner.nextInt();
                scanner.nextLine();  // Очистка буфера

                switch (choice) {
                    case 1:
                        login();
                        break;
                    case 2:
                        register();
                        break;
                    case 0:
                        System.out.println("👋 Выход из программы...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("❌ Некорректный ввод. Попробуйте снова.");
                }
            } else {
                if ("ADMIN".equals(currentUser.getRole())) {
                    adminMenu();
                } else {
                    userMenu();
                }
            }
        }
    }

    private static void login() {
        System.out.print("Введите имя пользователя: ");
        String username = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        currentUser = authController.login(username, password);
    }

    private static void register() {
        System.out.print("Введите имя пользователя: ");
        String username = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();
        System.out.print("Введите роль (ADMIN/CUSTOMER): ");
        String role = scanner.nextLine();

        authController.register(username, password, role);
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("\n👑 Администратор:");
            System.out.println("1️⃣ - Управление автомобилями");
            System.out.println("2️⃣ - Управление сотрудниками");
            System.out.println("3️⃣ - Управление машинами");
            System.out.println("4️⃣ - Просмотр всех пользователей");
            System.out.println("5️⃣ - Удалить пользователя");
            System.out.println("6️⃣ - Изменить роль пользователя");
            System.out.println("0️⃣ - Выход");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Очистка буфера

            switch (choice) {
                case 1:
                    manageCars();
                    break;
                case 2:
                    manageEmployees();
                    break;
                case 3:
                    manageMachines();
                    break;
                case 4:
                    authController.viewAllUsers();
                    break;
                case 5:
                    System.out.print("Введите имя пользователя для удаления: ");
                    String usernameToDelete = scanner.nextLine();
                    authController.deleteUser(usernameToDelete);
                    break;
                case 6:
                    System.out.print("Введите имя пользователя: ");
                    String usernameToUpdate = scanner.nextLine();
                    System.out.print("Введите новую роль (ADMIN/CUSTOMER): ");
                    String newRole = scanner.nextLine();
                    authController.updateUserRole(usernameToUpdate, newRole);
                    break;
                case 0:
                    currentUser = null;
                    return;
                default:
                    System.out.println("❌ Некорректный ввод.");
            }
        }
    }

    private static void userMenu() {
        while (true) {
            System.out.println("\n👤 Пользователь:");
            System.out.println("1️⃣ - Просмотр автомобилей");
            System.out.println("2️⃣ - Просмотр истории заказов");
            System.out.println("0️⃣ - Выход");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Очистка буфера

            switch (choice) {
                case 1:
                    carController.listAllCars();
                    break;
                case 2:
                    System.out.print("Введите ваш ID пользователя: ");
                    int userId = scanner.nextInt();
                    scanner.nextLine();
                    orderController.viewOrderHistory(userId);
                    break;
                case 0:
                    currentUser = null;
                    return;
                default:
                    System.out.println("❌ Некорректный ввод.");
            }
        }
    }

    private static void manageCars() {
        while (true) {
            System.out.println("\n🚘 Управление автомобилями:");
            System.out.println("1️⃣ - Показать все автомобили");
            System.out.println("2️⃣ - Добавить автомобиль");
            System.out.println("3️⃣ - Удалить автомобиль");
            System.out.println("4️⃣ - Найти автомобиль по ID");
            System.out.println("0️⃣ - Назад");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    carController.listAllCars();
                    break;
                case 2:
                    addCar();
                    break;
                case 3:
                    deleteCar();
                    break;
                case 4:
                    getCarById();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("❌ Некорректный ввод.");
            }
        }
    }

    private static void addCar() {
        System.out.print("Введите модель автомобиля: ");
        String model = scanner.nextLine();
        if (model == null || model.trim().isEmpty()) {
            System.out.println("❌ Модель автомобиля не может быть пустой.");
            return;
        }

        System.out.print("Введите категорию (SUV, Sedan, Truck): ");
        String category = scanner.nextLine();
        if (!"SUV".equals(category) && !"Sedan".equals(category) && !"Truck".equals(category)) {
            System.out.println("❌ Некорректная категория. Допустимые значения: SUV, Sedan, Truck.");
            return;
        }

        System.out.print("Введите цену: ");
        double price = scanner.nextDouble();
        if (price <= 0) {
            System.out.println("❌ Цена должна быть положительным числом.");
            return;
        }

        System.out.print("Введите год выпуска: ");
        int year = scanner.nextInt();
        scanner.nextLine(); // Clear buffer
        if (year < 1900 || year > java.time.Year.now().getValue()) {
            System.out.println("❌ Некорректный год выпуска.");
            return;
        }

        carController.addCar(model, category, price, year);
        System.out.println("✅ Автомобиль успешно добавлен.");
    }

    private static void deleteCar() {
        System.out.print("Введите ID автомобиля для удаления: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        carController.deleteCar(id);
    }

    private static void getCarById() {
        System.out.print("Введите ID автомобиля: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        carController.getCarById(id);
    }

    private static void manageEmployees() {
        while (true) {
            System.out.println("\n👥 Управление сотрудниками:");
            System.out.println("1️⃣ - Добавить сотрудника");
            System.out.println("2️⃣ - Показать всех сотрудников");
            System.out.println("0️⃣ - Назад");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    employeeController.getAllEmployees().forEach(employee ->
                            System.out.println("ID: " + employee.getId() + ", Имя: " + employee.getName() + ", Должность: " + employee.getPosition() + ", Зарплата: " + employee.getSalary())
                    );
                    break;
                case 0:
                    return;
                default:
                    System.out.println("❌ Некорректный ввод.");
            }
        }
    }

    private static void addEmployee() {
        System.out.print("Введите имя сотрудника: ");
        String name = scanner.nextLine();
        if (name == null || name.trim().isEmpty()) {
            System.out.println("❌ Имя сотрудника не может быть пустым.");
            return;
        }

        System.out.print("Введите должность: ");
        String position = scanner.nextLine();
        if (position == null || position.trim().isEmpty()) {
            System.out.println("❌ Должность не может быть пустой.");
            return;
        }

        System.out.print("Введите зарплату: ");
        BigDecimal salary = scanner.nextBigDecimal();
        scanner.nextLine(); // Clear buffer
        if (salary.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("❌ Зарплата должна быть положительным числом.");
            return;
        }

        employeeController.addEmployee(name, position, salary);
        System.out.println("✅ Сотрудник успешно добавлен.");
    }

    private static void manageMachines() {
        while (true) {
            System.out.println("\n⚙️ Управление машинами:");
            System.out.println("1️⃣ - Добавить машину");
            System.out.println("2️⃣ - Показать все машины");
            System.out.println("0️⃣ - Назад");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addMachine();
                    break;
                case 2:
                    machineController.getAllMachines().forEach(machine ->
                            System.out.println("ID: " + machine.getId() + ", Название: " + machine.getName() + ", Производительность: " + machine.getCapacity() + ", Статус: " + machine.getStatus())
                    );
                    break;
                case 0:
                    return;
                default:
                    System.out.println("❌ Некорректный ввод.");
            }
        }
    }

    private static void addMachine() {
        System.out.print("Введите название машины: ");
        String name = scanner.nextLine();
        System.out.print("Введите производительность (авто/день): ");
        int capacity = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Введите статус (ACTIVE/INACTIVE/MAINTENANCE): ");
        String status = scanner.nextLine();

        machineController.addMachine(name, capacity, status);
    }
}