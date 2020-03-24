package parkinglot;

public class ParkingLotException extends RuntimeException {
    public enum ExceptionType {PARKING_IS_FULL, SLOT_IS_FULL, VEHICLE_IS_ALREADY_PARKED}

    public ExceptionType type;

    public ParkingLotException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
