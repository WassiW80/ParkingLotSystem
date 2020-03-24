package parkinglot;

import java.util.*;

public class ParkingLot {

    private int actualCapacity;
    private Map<String, Object> vehicleMap;
    private List<iParkingLotObserver> observersList;

    public ParkingLot(int capacity) {
        vehicleMap = new HashMap();
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
        if (this.vehicleMap.size() == actualCapacity) {
            for (iParkingLotObserver observer :
                    observersList) {
                observer.capacityIsFull();
                throw new ParkingLotException("Parking Lot Is Full", ParkingLotException.ExceptionType.PARKING_IS_FULL);
            }
        }
        if (this.isVehicleParked(slot))
            throw new ParkingLotException("Slot is Full", ParkingLotException.ExceptionType.SLOT_IS_FULL);
        this.vehicleMap.put(slot,vehicle);
    }

    public boolean isVehicleParked(String slot) {
        return this.vehicleMap.containsKey(slot);
    }

    public boolean isUnParked(String slot) {
        if (this.vehicleMap.containsKey(slot)) {
            this.vehicleMap.remove(slot);
            return true;
        }
        return false;
    }

    public String isVehicleFound(Object vehicle) {
        for (Map.Entry<String, Object> entry : vehicleMap.entrySet()) {
            if (entry.getValue().equals(vehicle)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
