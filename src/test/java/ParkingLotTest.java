import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ParkingLotTest {
    Object vehicle;
    ParkingLot parkingLot;

    @Before
    public void setUp() {
        vehicle = new Object();
        parkingLot = new ParkingLot();
    }

    @Test
    public void givenAVehicleToPark_WhenParked_ShouldReturnTrue() {
        boolean isParked = parkingLot.isParked(vehicle);
        assertTrue(isParked);
    }

    @Test
    public void givenAVehicleToUnParked_WhenUnParked_ShouldReturnTrue() {
        parkingLot.isParked(vehicle);
        boolean isUnParked = parkingLot.isUnParked(vehicle);
        assertTrue(isUnParked);
    }
}
