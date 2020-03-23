import java.util.ArrayList;
import java.util.List;

public class ParkingLot {

    private int actualCapacity;
    private List vehicle;
    private ParkingLotOwner owner;

    public ParkingLot(int capacity) {
        vehicle = new ArrayList();
        this.actualCapacity = capacity;
    }

    public void registerOwner(ParkingLotOwner owner) {
        this.owner = owner;
    }

    public void isParked(Object vehicle) {
        if (this.vehicle.size() == actualCapacity) {
            owner.isCapacityFull();
            throw new ParkingLotException("Parking Lot Is Full", ParkingLotException.ExceptionType.PARKING_IS_FULL);
        }
        if (this.isVehicleParked(vehicle))
            throw new ParkingLotException("Vehicle Is Already Parked", ParkingLotException.ExceptionType.VEHICLE_IS_ALREADY_PARKED);
            this.vehicle.add(vehicle);
    }

    public boolean isVehicleParked(Object vehicle) {
        if (this.vehicle.contains(vehicle))
            return true;
        return false;
    }

    public boolean isUnParked(Object vehicle) {
        if (this.vehicle.contains(vehicle)) {
            this.vehicle.remove(vehicle);
            return true;
        }
        return false;
    }


    public void setCapacity(int actualCapacity) {
        this.actualCapacity=actualCapacity;
    }
}
