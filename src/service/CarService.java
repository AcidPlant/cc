package service;

import model.Car;
import repository.CarRepository;

import java.util.List;

public class CarService {
    private final CarRepository carRepository = new CarRepository();

    public void addCar(String model, String category, double price, int year) {
        Car car = new Car(0, model, category, price, year);
        carRepository.addCar(car);
    }

    public List<Car> getAllCars() {
        return carRepository.getAllCars();
    }

    public Car getCarById(int id) {
        return carRepository.getCarById(id);
    }

    public void deleteCar(int id) {
        carRepository.deleteCar(id);
    }
}