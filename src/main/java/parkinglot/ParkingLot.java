package parkinglot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ParkingLot {

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
        if (driver.equals(Driver.HANDICAP)) {
            slot = getAutoParking(slot);
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
        for (int i = 1; i <= this.actualCapacity; i++)
            if (this.vehicleMap.get("S" + i) == null) {
                slot = "S" + i;
                return slot;
            }
        throw new ParkingLotException("Slot is Full", ParkingLotException.ExceptionType.SLOT_IS_FULL);
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

    public List findVehicleByColor(String color) {
        List<String> arrayList;
        arrayList = vehicleMap.entrySet().stream()
                .filter(map -> map.getValue().getColor().equals(color))
                .map(map -> map.getKey())
                .collect(Collectors.toList());
        return getArrayListSize(arrayList);
    }

    public List findVehicleByColorAndType(String color, String carModel) {
        List<String> arrayList;
        arrayList = findVehicleByColor(color);
        arrayList = findVehicleByModel(carModel);
        return getArrayListSize(arrayList);
    }

    public List findVehicleByModel(String model) {
        List<String> arrayList;
        arrayList = vehicleMap.entrySet().stream()
                .filter(map -> map.getValue().getCarModel().equals(model))
                .map(map -> map.getKey())
                .collect(Collectors.toList());
        return getArrayListSize(arrayList);
    }

    public List findVehicleByDriverTypeAndVehicleType(VehicleType vehicle, Driver driver) {
        List<String> arrayList = new ArrayList<>();
        for (int i = 1; i <= this.vehicleMap.size(); i++) {
            if (this.vehicleMap.get("S" + i) != null) {
                if (vehicle.equals(VehicleType.NORMAL_VEHICLE) && driver.equals(Driver.HANDICAP)) {
                    arrayList.add("S" + i);
                }
            }
        }
        return getArrayListSize(arrayList);
    }

    public List findAllVehicle() {
        List<String> arrayList;
        arrayList = vehicleMap.entrySet().stream()
                .map(map -> map.getKey())
                .collect(Collectors.toList());
        return getArrayListSize(arrayList);
    }

    private List getArrayListSize(List<String> arrayList) {
        if (arrayList.size() == 0)
            throw new ParkingLotException("Vehicle Not Found", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
        return arrayList;
    }

    public int setParkingTime(int parkingTime) {
        this.parkingTime = parkingTime;
        return parkingTime;
    }

    public boolean findVehicleWhichParked30MinuteBefore(int parkingTime) {
        if (this.parkingTime - parkingTime <= 0)
            return true;
        throw new ParkingLotException("Vehicle Not Found", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
    }
}
