package parkinglot;

public class AirportSecurity implements iParkingLotObserver {
    private boolean isFullCapacity;

    @Override
    public void capacityIsFull() {
        isFullCapacity = true;
    }

    @Override
    public boolean isCapacityFull() {
        return isFullCapacity;
    }

    @Override
    public void capacityIsAvailable() {
        isFullCapacity = false;
    }
}
