package parkinglot;

public class Vehicle {
    String color;
    String CarModel;
    String numberPlate;
    public Vehicle() {

    }

    public Vehicle(String color) {
        this.color = color;
    }

    public Vehicle(String color, String CarModel, String numberPlate) {
        this.color = color;
        this.CarModel = CarModel;
        this.numberPlate = numberPlate;
    }

    public String getColor() {
        return this.color;
    }

    public String getCarModel() {
        return this.CarModel;
    }
}
