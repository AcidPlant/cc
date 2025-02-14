package Controllers;
import model.Car;
import service.CarService;
import java.util.List;
public class CarController {
    private final CarService carService = new CarService();

    public void addCar(String model, String category, double price, int year) {
        if (model == null || model.trim().isEmpty() || category == null || category.trim().isEmpty() ||
                price <= 0 || year < 1886 || year > 2100) {
            System.out.println("❌ Ошибка: Некорректные данные для автомобиля!");
            return;
        }
        Car car = new Car(0, model, category, year, price);
        carService.addCar(car);
        System.out.println("✅ Автомобиль добавлен: " + car);
    }

    public void listAllCars() {
        List<Car> cars = carService.getAllCars();
        if (cars.isEmpty()) {
            System.out.println("🚫 Машины не найдены!");
        } else {
            System.out.println("📋 Доступные автомобили:");
            cars.forEach(System.out::println);
        }
    }

    public void getCarById(int id) {
        Car car = carService.getCarById(id);
        if (car != null) {
            System.out.println("🚗 Найден автомобиль: " + car);
        } else {
            System.out.println("❌ Автомобиль с ID " + id + " не найден.");
        }
    }

    public void deleteCar(int id) {
        boolean success = carService.deleteCar(id);
        if (success) {
            System.out.println("✅ Автомобиль удален.");
        } else {
            System.out.println("❌ Ошибка при удалении. Автомобиль не найден.");
        }
    }
}
