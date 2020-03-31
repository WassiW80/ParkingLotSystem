package parkinglot;

public interface iParkingLotObserver {

    void capacityIsFull();

    boolean isCapacityFull();

    void capacityIsAvailable();
}
