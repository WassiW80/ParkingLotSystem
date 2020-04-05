package parkinglot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLot {

    private int slotNumber = 01;

    public enum Driver {NORMAL, HANDICAP}

    private int actualCapacity;
    private Map<String, Vehicle> vehicleMap;
    private List<iParkingLotObserver> observersList;
    ParkingLotAvailability availability;
    private int parkingTime;

    public ParkingLot(int capacity) {
        availability = new ParkingLotAvailability();
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

    public void isParked(String slot, Vehicle vehicle, VehicleType vehicleType, Driver driver) {
        if (this.vehicleMap.size() == actualCapacity) {
            availability.parkingFull();
            throw new ParkingLotException("Parking Lot Is Full", ParkingLotException.ExceptionType.PARKING_IS_FULL);
        }
        if (vehicleType.equals(VehicleType.LARGE_VEHICLE)) {
            slot = getLargeParking(slot);
        }
        if (this.isVehicleParked(slot)) {
            if (driver.equals(Driver.HANDICAP)) {
                slot = getAutoParking(slot);
            } else
                throw new ParkingLotException("Slot is Full", ParkingLotException.ExceptionType.SLOT_IS_FULL);
        }
        this.vehicleMap.put(slot, vehicle);
    }

    private String getLargeParking(String slot) {
        for (int i = 1; i < actualCapacity; i++) {
            int j = i + 1;
            if (this.vehicleMap.get("S" + j--) == null && this.vehicleMap.get("S" + j++) == null)
                if (this.vehicleMap.get("S" + j) == null) {
                    slot = "S" + j;
                    return slot;
                }
        }
        throw new ParkingLotException("Slot is Full", ParkingLotException.ExceptionType.SLOT_IS_FULL);

    }

    private String getAutoParking(String slot) {
        if (this.vehicleMap.containsKey(slot))
            slotNumber++;
        return "S" + slotNumber;
    }

    public boolean isVehicleParked(String slot) {
        return this.vehicleMap.containsKey(slot);
    }

    public boolean isUnParked(String slot) {
        if (this.vehicleMap.containsKey(slot)) {
            this.vehicleMap.remove(slot);
            availability.parkingAvailable();
            return true;
        }
        return false;
    }

    public String isVehicleFound(Vehicle vehicle) {
        for (Map.Entry<String, Vehicle> entry : vehicleMap.entrySet()) {
            if (entry.getValue().equals(vehicle)) {
                return entry.getKey();
            }
        }
        throw new ParkingLotException("Vehicle Not Found", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
    }

    public ArrayList<String> findVehicleByColor(String color) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 1; i <= this.vehicleMap.size(); i++) {
            if (this.vehicleMap.get("S" + i) != null) {
                if (this.vehicleMap.get("S" + i).getColor().equals(color)) {
                    arrayList.add("S" + i);
                }
            }
        }
        if (arrayList.size() == 0)
            throw new ParkingLotException("Vehicle Not Found", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
        return arrayList;
    }

    public ArrayList<String> findVehicleByColorAndType(String color, String CarModel) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 1; i <= this.vehicleMap.size(); i++) {
            if (this.vehicleMap.get("S" + i) != null) {
                if (this.vehicleMap.get("S" + i).getColor().equals(color) && this.vehicleMap.get("S" + i).getCarModel().equals(CarModel)) {
                    arrayList.add("S" + i);
                }
            }
        }
        if (arrayList.size() == 0)
            throw new ParkingLotException("Vehicle Not Found", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
        return arrayList;
    }

    public int setParkingTime(int parkingTime) {
        this.parkingTime = parkingTime;
        return parkingTime;
    }
}
