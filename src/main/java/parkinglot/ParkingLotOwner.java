package parkinglot;

public class ParkingLotOwner implements iParkingLotObserver {
    private boolean isFullCapacity;

    @Override
    public void capacityIsFull() {
        isFullCapacity = true;
    }

    @Override
    public boolean isCapacityFull() {
        return isFullCapacity;
    }
}
