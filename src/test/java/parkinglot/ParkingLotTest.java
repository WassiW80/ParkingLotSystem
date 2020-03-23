package parkinglot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ParkingLotTest {
    Object vehicle;
    ParkingLot parkingLot;

    @Before
    public void setUp() {
        vehicle = new Object();
        parkingLot = new ParkingLot(2);
    }

    @Test
    public void givenAVehicleToPark_WhenParked_ShouldReturnTrue() {
        parkingLot.isParked(vehicle);
        boolean isParked = parkingLot.isVehicleParked(vehicle);
        assertTrue(isParked);
    }

    @Test
    public void givenAVehicleToPark_WhenAlreadyParked_ShouldThrowException() {
        parkingLot.setCapacity(2);
        try {
            parkingLot.isParked(vehicle);
            parkingLot.isParked(vehicle);
            boolean isParked1 = parkingLot.isVehicleParked(vehicle);
            boolean isParked2 = parkingLot.isVehicleParked(vehicle);
            assertTrue(isParked1 && isParked2);
        } catch (ParkingLotException e) {
            Assert.assertEquals(e.type, ParkingLotException.ExceptionType.VEHICLE_IS_ALREADY_PARKED);
        }
    }

    @Test
    public void givenAVehicleToUnParked_WhenUnParked_ShouldReturnTrue() {
        parkingLot.isParked(vehicle);
        boolean isUnParked = parkingLot.isUnParked(vehicle);
        assertTrue(isUnParked);
    }

    @Test
    public void givenWhenParkingLotIsFull_ShouldInformTheOwner() {
        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLot.registerObserver(owner);
        try {
            parkingLot.isParked(vehicle);
            parkingLot.isParked(new Object());
        } catch (ParkingLotException e) {
            boolean capacityFull = owner.isCapacityFull();
            assertTrue(capacityFull);
        }
    }

    @Test
    public void givenCapacityIs2_ShouldBeAbleToPark2Vehicle() {
        Object vehicle2 = new Object();
        parkingLot.setCapacity(2);
        try {
            parkingLot.isParked(vehicle);
            parkingLot.isParked(vehicle2);
            boolean isParked1 = parkingLot.isVehicleParked(vehicle);
            boolean isParked2 = parkingLot.isVehicleParked(vehicle2);
            assertTrue(isParked1 && isParked2);
        } catch (ParkingLotException e) {
            Assert.assertEquals(e.type, ParkingLotException.ExceptionType.VEHICLE_IS_ALREADY_PARKED);
        }
    }

    @Test
    public void givenWhenParkingLotIsFull_ShouldInformTheAirportSecurity() {
        AirportSecurity security = new AirportSecurity();
        parkingLot.registerObserver(security);
        try {
            parkingLot.isParked(vehicle);
            parkingLot.isParked(new Object());
        } catch (ParkingLotException e) {
            boolean capacityFull = security.isCapacityFull();
            assertTrue(capacityFull);
        }
    }

    @Test
    public void givenAVehicle_WhenSpaceIsAvailable_ShouldInformTheOwner() {
        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLot.registerObserver(owner);
        parkingLot.isParked(vehicle);
        parkingLot.isParked(new Object());
        parkingLot.isUnParked(vehicle);
        boolean capacityFull = owner.isCapacityFull();
        assertFalse(capacityFull);
    }

    @Test
    public void givenAVehicle_WhenSpaceIsAvailable_ShouldInformTheAirportSecurity() {
        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLot.registerObserver(owner);
        parkingLot.isParked(vehicle);
        parkingLot.isParked(new Object());
        parkingLot.isUnParked(vehicle);
        boolean capacityFull = owner.isCapacityFull();
        assertFalse(capacityFull);
    }
}
