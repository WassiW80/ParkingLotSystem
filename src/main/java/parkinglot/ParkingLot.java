package parkinglot;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {

    private int actualCapacity;
    private List vehicle;
    private List<iParkingLotObserver> observersList;

    public ParkingLot(int capacity) {
        vehicle = new ArrayList();
        this.actualCapacity = capacity;
        this.observersList = new ArrayList<>();
    }

    public void registerObserver(iParkingLotObserver observer) {
        observersList.add(observer);
    }

    public void setCapacity(int actualCapacity) {
        this.actualCapacity = actualCapacity;
    }

    public void isParked(Object vehicle) {
        if (this.vehicle.size() == actualCapacity) {
            for (iParkingLotObserver observer :
                    observersList) {
                observer.capacityIsFull();
                throw new ParkingLotException("Parking Lot Is Full", ParkingLotException.ExceptionType.PARKING_IS_FULL);
            }
        }
        if (this.isVehicleParked(vehicle))
            throw new ParkingLotException("Vehicle Is Already Parked", ParkingLotException.ExceptionType.VEHICLE_IS_ALREADY_PARKED);
        this.vehicle.add(vehicle);
    }

    public boolean isVehicleParked(Object vehicle) {
        return this.vehicle.contains(vehicle);
    }

    public boolean isUnParked(Object vehicle) {
        if (this.vehicle.contains(vehicle)) {
            this.vehicle.remove(vehicle);
            return true;
        }
        return false;
    }
}
