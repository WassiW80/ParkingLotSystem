import org.junit.Test;

import static org.junit.Assert.*;

public class ParkingLotTest {
    @Test
    public void givenAVehicleToPark_WhenParked_ShouldReturnTrue() {
        Object vehicle = new Object();
        ParkingLot parkingLot = new ParkingLot();
        boolean isParked = parkingLot.isParked(vehicle);
        assertTrue(isParked);
    }
}
