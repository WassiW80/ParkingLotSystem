package parkinglot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

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
        parkingLot.isParked("S01", vehicle, ParkingLot.Driver.NORMAL);
        boolean isParked = parkingLot.isVehicleParked("S01");
        assertTrue(isParked);
    }

    @Test
    public void givenAVehicleToPark_WhenAlreadyParked_ShouldThrowException() {
        parkingLot.setCapacity(2);
        try {
            parkingLot.isParked("S01", vehicle, ParkingLot.Driver.NORMAL);
            parkingLot.isParked("S01", vehicle, ParkingLot.Driver.NORMAL);
            boolean isParked1 = parkingLot.isVehicleParked("S01");
            boolean isParked2 = parkingLot.isVehicleParked("S01");
            assertTrue(isParked1 && isParked2);
        } catch (ParkingLotException e) {
            Assert.assertEquals(e.type, ParkingLotException.ExceptionType.SLOT_IS_FULL);
        }
    }

    @Test
    public void givenAVehicleToUnParked_WhenUnParked_ShouldReturnTrue() {
        parkingLot.isParked("S01", vehicle, ParkingLot.Driver.NORMAL);
        boolean isUnParked = parkingLot.isUnParked("S01");
        assertTrue(isUnParked);
    }

    @Test
    public void givenWhenParkingLotIsFull_ShouldInformTheOwner() {
        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLot.registerObserver(owner);
        try {
            parkingLot.isParked("S01", vehicle, ParkingLot.Driver.NORMAL);
            parkingLot.isParked("S02", new Object(), ParkingLot.Driver.NORMAL);
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
            parkingLot.isParked("S01", vehicle, ParkingLot.Driver.NORMAL);
            parkingLot.isParked("S02", vehicle2, ParkingLot.Driver.NORMAL);
            boolean isParked1 = parkingLot.isVehicleParked("S01");
            boolean isParked2 = parkingLot.isVehicleParked("S02");
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
            parkingLot.isParked("S01", vehicle, ParkingLot.Driver.NORMAL);
            parkingLot.isParked("S02", new Object(), ParkingLot.Driver.NORMAL);
        } catch (ParkingLotException e) {
            boolean capacityFull = security.isCapacityFull();
            assertTrue(capacityFull);
        }
    }

    @Test
    public void givenAVehicle_WhenSpaceIsAvailable_ShouldInformTheOwner() {
        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLot.registerObserver(owner);
        parkingLot.isParked("S01", vehicle, ParkingLot.Driver.NORMAL);
        parkingLot.isParked("S02", new Object(), ParkingLot.Driver.NORMAL);
        parkingLot.isUnParked("S01");
        boolean capacityFull = owner.isCapacityFull();
        assertFalse(capacityFull);
    }

    @Test
    public void givenAVehicle_WhenSpaceIsAvailable_ShouldInformTheAirportSecurity() {
        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLot.registerObserver(owner);
        parkingLot.isParked("S01", vehicle, ParkingLot.Driver.NORMAL);
        parkingLot.isParked("S02", new Object(), ParkingLot.Driver.NORMAL);
        parkingLot.isUnParked("S01");
        boolean capacityFull = owner.isCapacityFull();
        assertFalse(capacityFull);
    }

    @Test
    public void givenCapacity_WhenFullSlotAvailable_ShouldReturnTrue() {
        parkingLot.setCapacity(5);
        int capacity = parkingLot.getCapacity();
        assertEquals(5, capacity);
    }

    @Test
    public void givenAVehicle_WhenParkedOnAvailableSlot_ShouldReturnTrue() {
        parkingLot.setCapacity(3);
        parkingLot.isParked("S01", vehicle, ParkingLot.Driver.NORMAL);
        boolean isParked = parkingLot.isVehicleParked("S01");
        assertTrue(isParked);
    }

    @Test
    public void givenAVehicle_WhenSlotIsFull_ShouldThrowException() {
        try {
            parkingLot.isParked("S01", vehicle, ParkingLot.Driver.NORMAL);
            parkingLot.isParked("S01", new Object(), ParkingLot.Driver.NORMAL);
            boolean isParked1 = parkingLot.isVehicleParked("S01");
            boolean isParked2 = parkingLot.isVehicleParked("S01");
            assertTrue(isParked1 && isParked2);
        } catch (ParkingLotException e) {
            assertEquals(e.type, ParkingLotException.ExceptionType.SLOT_IS_FULL);
        }
    }

    @Test
    public void givenAVehicle_WhenFound_ShouldReturnTrue() {
        parkingLot.isParked("S01", vehicle, ParkingLot.Driver.NORMAL);
        parkingLot.isParked("S02", new Object(), ParkingLot.Driver.NORMAL);
        String vehicleFound = parkingLot.isVehicleFound(vehicle);
        assertEquals("S01", vehicleFound);
    }

    @Test
    public void givenAVehicle_WhenNotFound_ShouldThrowException() {
        try {
            parkingLot.isParked("S01", vehicle, ParkingLot.Driver.NORMAL);
            parkingLot.isParked("S02", new Object(), ParkingLot.Driver.NORMAL);
            String vehicleFound = parkingLot.isVehicleFound(new Object());
            assertEquals("S01", vehicleFound);
        } catch (ParkingLotException e) {
            assertEquals(e.type, ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
        }
    }

    @Test
    public void givenAVehicle_WhenParkedSetTime_ShouldReturnTrue() {
        parkingLot.setParkingTime(LocalDateTime.now().getHour());
        parkingLot.isParked("S01", vehicle, ParkingLot.Driver.NORMAL);
        boolean vehicleParked = parkingLot.isVehicleParked("S01");
        assertTrue(vehicleParked);
    }

    @Test
    public void givenAVehicle_WhenParkedEvenly_ShouldReturnTrue() {
        parkingLot.setCapacity(10);
        parkingLot.isParked("S01", vehicle, ParkingLot.Driver.NORMAL);
        parkingLot.isParked("S03", vehicle, ParkingLot.Driver.NORMAL);
        parkingLot.isParked("S05", vehicle, ParkingLot.Driver.NORMAL);
        parkingLot.isParked("S07", vehicle, ParkingLot.Driver.NORMAL);
        parkingLot.isParked("S09", vehicle, ParkingLot.Driver.NORMAL);
        boolean isParked1 = parkingLot.isVehicleParked("S01");
        boolean isParked2 = parkingLot.isVehicleParked("S03");
        boolean isParked3 = parkingLot.isVehicleParked("S05");
        boolean isParked4 = parkingLot.isVehicleParked("S07");
        boolean isParked5 = parkingLot.isVehicleParked("S09");
        assertTrue(isParked1 && isParked2 && isParked3 && isParked4 && isParked5);
    }

    @Test
    public void givenAVehicleWithHandicapDriver_WhenParkedAtNearestSlot_ShouldReturnTrue() {
        parkingLot.isParked("S01", vehicle, ParkingLot.Driver.NORMAL);
        parkingLot.isParked("S01", vehicle, ParkingLot.Driver.HANDICAP);
        boolean isParked1 = parkingLot.isVehicleParked("S01");
        boolean isParked2 = parkingLot.isVehicleParked("S2");
        assertTrue(isParked1 && isParked2);
    }
}
