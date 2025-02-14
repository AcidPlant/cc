package service;
import model.Car;
import repository.CarRepository;

import java.util.List;

import java.util.Optional;


public class CarService {
    private final CarRepository carRepository = new CarRepository();

    public void addCar(Car car) {
        if (car == null || car.getModel() == null || car.getModel().trim().isEmpty() ||
                car.getCategory() == null || car.getCategory().trim().isEmpty() ||
                car.getPrice() <= 0 || car.getYear() < 1886 || car.getYear() > 2100) {
            System.out.println("❌ Ошибка: Некорректные данные для автомобиля!");
            return;
        }
        carRepository.addCar(car);
        System.out.println("✅ Автомобиль добавлен: " + car);
    }

    public List<Car> getAllCars() {
        List<Car> cars = carRepository.getAllCars();
        if (cars.isEmpty()) {
            System.out.println("🚫 В базе данных нет машин.");
        }
        return cars;
    }

    public Car getCarById(int id) {
        Optional<Car> car = carRepository.getCarById(id);
        return car.orElse(null);
    }

    public boolean deleteCar(int id) {
        boolean success = carRepository.deleteCar(id);
        if (!success) {
            System.out.println("❌ Автомобиль с ID " + id + " не найден.");
        }
        return success;
    }
}
