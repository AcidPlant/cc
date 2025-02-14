package Controllers;

import model.Car;
import service.CarService;

import java.util.List;

public class CarController {
    private final CarService carService = new CarService();

    public void addCar(String model, String category, double price, int year) {
        carService.addCar(model, category, price, year);
    }

    public void listAllCars() {
        List<Car> cars = carService.getAllCars();
        if (cars.isEmpty()) {
            System.out.println("❌ Нет доступных автомобилей.");
        } else {
            cars.forEach(car -> System.out.println("ID: " + car.getId() + ", Модель: " + car.getModel() + ", Категория: " + car.getCategory() + ", Цена: " + car.getPrice()));
        }
    }

    public void getCarById(int id) {
        Car car = carService.getCarById(id);
        if (car != null) {
            System.out.println("🚗 Найден автомобиль: " + car.getModel());
        } else {
            System.out.println("❌ Автомобиль с таким ID не найден.");
        }
    }

    public void deleteCar(int id) {
        carService.deleteCar(id);
    }
}