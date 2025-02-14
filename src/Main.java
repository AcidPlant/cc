
import Controllers.CarController;
import Controllers.FactoryController;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import config.DatabaseConnection;
import config.DatabaseInitializer;
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final CarController carController = new CarController();
    private static final FactoryController factoryController = new FactoryController();

    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
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


            System.out.println("\n🚗 Добро пожаловать в Car Factory!");
            System.out.println("1️⃣ - Управление автомобилями");
            System.out.println("2️⃣ - Управление сотрудниками");
            System.out.println("3️⃣ - Управление машинами");
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
                case 0:
                    System.out.println("👋 Выход из программы...");
                    scanner.close();
                    return;
                default:
                    System.out.println("❌ Некорректный ввод. Попробуйте снова.");
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
        System.out.print("Введите категорию: ");
        String category = scanner.nextLine();
        System.out.print("Введите цену: ");
        double price = scanner.nextDouble();
        System.out.print("Введите год выпуска: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        carController.addCar(model, category, price, year);
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
            System.out.println("0️⃣ - Назад");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addEmployee();
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
        System.out.print("Введите должность: ");
        String position = scanner.nextLine();
        System.out.print("Введите зарплату: ");
        BigDecimal salary = scanner.nextBigDecimal();
        scanner.nextLine();

        factoryController.addEmployee(name, position, salary);
    }

    private static void manageMachines() {
        while (true) {
            System.out.println("\n⚙️ Управление машинами:");
            System.out.println("1️⃣ - Добавить машину");
            System.out.println("0️⃣ - Назад");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addMachine();
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

        factoryController.addMachine(name, capacity, status);
    }
}


