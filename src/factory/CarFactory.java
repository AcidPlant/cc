package factory;

import model.Car;

public class CarFactory {
    public static Car createCar(int id, String model, String category, int year, double price) {
        return new Car(id, model, category, year, price);
    }
}
