package parkinglot;

public class Vehicle {
    String color;
    String type;
    String numberPlate;
    public Vehicle() {

    }

    public Vehicle(String color) {
        this.color = color;
    }

    public Vehicle(String color, String type, String numberPlate) {
        this.color = color;
        this.type = type;
        this.numberPlate = numberPlate;
    }

    public String getColor() {
        return this.color;
    }

    public String getType() {
        return this.type;
    }
}
