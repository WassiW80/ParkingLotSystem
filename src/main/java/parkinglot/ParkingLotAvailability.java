package parkinglot;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotAvailability {

    static List<iParkingLotObserver> observerMap;

    public ParkingLotAvailability() {
        observerMap = new ArrayList<>();
    }

    public void parkingFull() {
        for (iParkingLotObserver observers : observerMap)
            observers.capacityIsFull();
    }

    public void parkingAvailable() {
        for (iParkingLotObserver observers : observerMap)
            observers.capacityIsAvailable();
    }

}
