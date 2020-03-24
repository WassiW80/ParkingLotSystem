package parkinglot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLot {

    private int actualCapacity;
    private Map vehicle;
    private List<iParkingLotObserver> observersList;

    public ParkingLot(int capacity) {
        vehicle = new HashMap();
        this.actualCapacity = capacity;
        this.observersList = new ArrayList<>();
    }

    public void registerObserver(iParkingLotObserver observer) {
        observersList.add(observer);
    }

    public void setCapacity(int actualCapacity) {
        this.actualCapacity = actualCapacity;
        getCapacity();
    }

    public int getCapacity() {
        return this.actualCapacity;
    }

    public void isParked(String slot, Object vehicle) {
        if (this.vehicle.size() == actualCapacity) {
            for (iParkingLotObserver observer :
                    observersList) {
                observer.capacityIsFull();
                throw new ParkingLotException("Parking Lot Is Full", ParkingLotException.ExceptionType.PARKING_IS_FULL);
            }
        }
        if (this.isVehicleParked(slot))
            throw new ParkingLotException("Slot is Full", ParkingLotException.ExceptionType.SLOT_IS_FULL);
        this.vehicle.put(slot,vehicle);
    }

    public boolean isVehicleParked(String slot) {
        return this.vehicle.containsKey(slot);
    }

    public boolean isUnParked(String slot) {
        if (this.vehicle.containsKey(slot)) {
            this.vehicle.remove(slot);
            return true;
        }
        return false;
    }
}
