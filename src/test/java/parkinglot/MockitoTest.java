package parkinglot;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class MockitoTest {
    Object vehicle;
    ParkingLot parkingLot;

    @Mock
    iParkingLotObserver observer;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        vehicle = new Object();
        parkingLot = new ParkingLot(2);
        observer = mock(iParkingLotObserver.class);
    }

    @Test
    public void testIsCapacityFull() {
        parkingLot.registerObserver(observer);
        when(observer.isCapacityFull()).thenReturn(true);
        try {
            parkingLot.isParked("S01", vehicle, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
            parkingLot.isParked("S02", new Object(), VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
        } catch (ParkingLotException e) {
            boolean capacityFull = observer.isCapacityFull();
            assertTrue(capacityFull);
            verify(observer).isCapacityFull();
        }
    }
}

