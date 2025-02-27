package parkinglot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ParkingLotTest {
    Vehicle vehicle;
    ParkingLot parkingLot;

    @Before
    public void setUp() {
        vehicle = new Vehicle();
        parkingLot = new ParkingLot(2);
    }

    @Test
    public void givenAVehicleToPark_WhenParked_ShouldReturnTrue() {
        parkingLot.isParked("S01", vehicle, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
        boolean isParked = parkingLot.isVehicleParked("S01");
        assertTrue(isParked);
    }

    @Test
    public void givenAVehicleToPark_WhenAlreadyParked_ShouldThrowException() {
        parkingLot.setCapacity(2);
        try {
            parkingLot.isParked("S01", vehicle, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
            parkingLot.isParked("S01", vehicle, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
            boolean isParked1 = parkingLot.isVehicleParked("S01");
            boolean isParked2 = parkingLot.isVehicleParked("S01");
            assertTrue(isParked1 && isParked2);
        } catch (ParkingLotException e) {
            Assert.assertEquals(e.type, ParkingLotException.ExceptionType.SLOT_IS_FULL);
        }
    }

    @Test
    public void givenAVehicleToUnParked_WhenUnParked_ShouldReturnTrue() {
        parkingLot.isParked("S01", vehicle, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
        boolean isUnParked = parkingLot.isUnParked("S01");
        assertTrue(isUnParked);
    }

    @Test
    public void givenWhenParkingLotIsFull_ShouldInformTheOwner() {
        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLot.registerObserver(owner);
        try {
            parkingLot.isParked("S01", vehicle, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
            parkingLot.isParked("S02", new Vehicle(), VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
        } catch (ParkingLotException e) {
            boolean capacityFull = owner.isCapacityFull();
            assertTrue(capacityFull);
        }
    }

    @Test
    public void givenCapacityIs2_ShouldBeAbleToPark2Vehicle() {
        Vehicle vehicle2 = new Vehicle();
        parkingLot.setCapacity(2);
        try {
            parkingLot.isParked("S01", vehicle, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
            parkingLot.isParked("S02", vehicle2, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
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
            parkingLot.isParked("S01", vehicle, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
            parkingLot.isParked("S02", new Vehicle(), VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
        } catch (ParkingLotException e) {
            boolean capacityFull = security.isCapacityFull();
            assertTrue(capacityFull);
        }
    }

    @Test
    public void givenAVehicle_WhenSpaceIsAvailable_ShouldInformTheOwner() {
        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLot.registerObserver(owner);
        parkingLot.isParked("S01", vehicle, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
        parkingLot.isParked("S02", new Vehicle(), VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
        parkingLot.isUnParked("S01");
        boolean capacityFull = owner.isCapacityFull();
        assertFalse(capacityFull);
    }

    @Test
    public void givenAVehicle_WhenSpaceIsAvailable_ShouldInformTheAirportSecurity() {
        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLot.registerObserver(owner);
        parkingLot.isParked("S01", vehicle, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
        parkingLot.isParked("S02", new Vehicle(), VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
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
        parkingLot.isParked("S01", vehicle, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
        boolean isParked = parkingLot.isVehicleParked("S01");
        assertTrue(isParked);
    }

    @Test
    public void givenAVehicle_WhenSlotIsFull_ShouldThrowException() {
        try {
            parkingLot.isParked("S01", vehicle, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
            parkingLot.isParked("S01", new Vehicle(), VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
            boolean isParked1 = parkingLot.isVehicleParked("S01");
            boolean isParked2 = parkingLot.isVehicleParked("S01");
            assertTrue(isParked1 && isParked2);
        } catch (ParkingLotException e) {
            assertEquals(e.type, ParkingLotException.ExceptionType.SLOT_IS_FULL);
        }
    }

    @Test
    public void givenAVehicle_WhenFound_ShouldReturnTrue() {
        parkingLot.isParked("S01", vehicle, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
        parkingLot.isParked("S02", new Vehicle(), VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
        String vehicleFound = parkingLot.isVehicleFound(vehicle);
        assertEquals("S01", vehicleFound);
    }

    @Test
    public void givenAVehicle_WhenNotFound_ShouldThrowException() {
        try {
            parkingLot.isParked("S01", vehicle, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
            parkingLot.isParked("S02", new Vehicle(), VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
            String vehicleFound = parkingLot.isVehicleFound(new Vehicle());
            assertEquals("S01", vehicleFound);
        } catch (ParkingLotException e) {
            assertEquals(e.type, ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
        }
    }

    @Test
    public void givenAVehicle_WhenParkedSetTime_ShouldReturnTrue() {
        parkingLot.setParkingTime(LocalDateTime.now().getMinute());
        parkingLot.isParked("S01", vehicle, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
        boolean vehicleParked = parkingLot.isVehicleParked("S01");
        assertTrue(vehicleParked);
    }

    @Test
    public void givenAVehicle_WhenParkedEvenly_ShouldReturnTrue() {
        parkingLot.setCapacity(10);
        parkingLot.isParked("S01", vehicle, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
        parkingLot.isParked("S03", vehicle, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
        parkingLot.isParked("S05", vehicle, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
        parkingLot.isParked("S07", vehicle, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
        parkingLot.isParked("S09", vehicle, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
        boolean isParked1 = parkingLot.isVehicleParked("S01");
        boolean isParked2 = parkingLot.isVehicleParked("S03");
        boolean isParked3 = parkingLot.isVehicleParked("S05");
        boolean isParked4 = parkingLot.isVehicleParked("S07");
        boolean isParked5 = parkingLot.isVehicleParked("S09");
        assertTrue(isParked1 && isParked2 && isParked3 && isParked4 && isParked5);
    }

    @Test
    public void givenAVehicleWithHandicapDriver_WhenParkedAtNearestSlot_ShouldReturnTrue() {
        parkingLot.isParked("S1", vehicle, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
        parkingLot.isParked("S2", vehicle, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.HANDICAP);
        boolean isParked1 = parkingLot.isVehicleParked("S1");
        boolean isParked2 = parkingLot.isVehicleParked("S2");
        assertTrue(isParked1 && isParked2);
    }

    @Test
    public void givenALargeVehicle_WhenParked_ShouldReturnTrue() {
        parkingLot.setCapacity(5);
        parkingLot.isParked("S1", vehicle, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
        parkingLot.isParked("S3", vehicle, VehicleType.LARGE_VEHICLE, ParkingLot.Driver.NORMAL);
        boolean isParked = parkingLot.isVehicleParked("S1");
        boolean isParked1 = parkingLot.isVehicleParked("S3");
        assertTrue(isParked && isParked1);
    }

    @Test
    public void givenALargeVehicle_WhenNotParked_ShouldThrowException() {
        try {
            parkingLot.setCapacity(5);
            parkingLot.isParked("S1", vehicle, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
            parkingLot.isParked("S3", vehicle, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
            parkingLot.isParked("S4", vehicle, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
            parkingLot.isParked("S5", vehicle, VehicleType.LARGE_VEHICLE, ParkingLot.Driver.NORMAL);
            boolean isParked = parkingLot.isVehicleParked("S1");
            boolean isParked1 = parkingLot.isVehicleParked("S3");
            boolean isParked2 = parkingLot.isVehicleParked("S4");
            boolean isParked3 = parkingLot.isVehicleParked("S5");
            assertTrue(isParked && isParked1 && isParked2 && isParked3);
        } catch (ParkingLotException e) {
            assertEquals(e.type, ParkingLotException.ExceptionType.SLOT_IS_FULL);
        }
    }

    @Test
    public void givenAWhiteColorToFindCarLocation_WhenFound_ShouldReturnTrue() {
        Vehicle vehicle1 = new Vehicle("White");
        Vehicle vehicle2 = new Vehicle("White");
        parkingLot.isParked("S1", vehicle1, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
        parkingLot.isParked("S2", vehicle2, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
        List location = parkingLot.findVehicleByColor("White");
        assertEquals("S1", location.get(0));
    }

    @Test
    public void givenAWhiteColorToFindCarLocation_WhenNotFound_ShouldThrowException() {
        try {
            Vehicle vehicle2 = new Vehicle("Black");
            parkingLot.isParked("S1", vehicle2, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
            List location = parkingLot.findVehicleByColor("White");
            assertEquals("S1", location);
        } catch (ParkingLotException e) {
            assertEquals(e.type, ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
        }
    }

    @Test
    public void givenABlueToyotaToFindCarLocation_WhenFound_ShouldReturnTrue() {
        Vehicle vehicle = new Vehicle("Blue", "Toyota", "MH01-MT6458");
        parkingLot.isParked("S1", vehicle, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
        List location = parkingLot.findVehicleByColorAndType("Blue", "Toyota");
        assertEquals("S1", location.get(0));
    }

    @Test
    public void givenABlueToyotaTOFindCarLocation_WhenNotFound_ShouldThrowException() {
        try {
            Vehicle vehicle = new Vehicle("Black", "Toyota", "MH01-MT6458");
            parkingLot.isParked("S1", vehicle, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
            List location = parkingLot.findVehicleByColorAndType("Blue", "Toyota");
            assertEquals("S1", location);
        } catch (ParkingLotException e) {
            assertEquals(e.type, ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
        }
    }

    @Test
    public void givenABMWToFindCarLocation_WhenFound_ShouldReturnTrue() {
        parkingLot.setCapacity(10);
        Vehicle vehicle1 = new Vehicle("Black", "BMW", "MH02-MD6475");
        Vehicle vehicle2 = new Vehicle("Blue", "BMW", "MH02-MD6875");
        Vehicle vehicle3 = new Vehicle("White", "Toyota", "MH04-MD6475");
        Vehicle vehicle4 = new Vehicle("White", "BMW", "MH02-WA551");
        parkingLot.isParked("S1", vehicle1, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
        parkingLot.isParked("S2", vehicle2, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
        parkingLot.isParked("S3", vehicle3, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
        parkingLot.isParked("S4", vehicle4, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
        List location = parkingLot.findVehicleByModel("BMW");
        List expectedResult = new ArrayList<>();
        expectedResult.add("S4");
        expectedResult.add("S1");
        expectedResult.add("S2");
        assertEquals(expectedResult, location);
    }

    @Test
    public void givenABMWToFindCarLocation_WhenNotFound_ShouldThrowException() {
        try {
            parkingLot.setCapacity(10);
            Vehicle vehicle1 = new Vehicle("Black", "Honda", "MH02-MD6475");
            Vehicle vehicle2 = new Vehicle("Blue", "Innova", "MH02-MD6875");
            Vehicle vehicle3 = new Vehicle("White", "Toyota", "MH04-MD6475");
            Vehicle vehicle4 = new Vehicle("White", "Skoda", "MH02-WA551");
            parkingLot.isParked("S1", vehicle1, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
            parkingLot.isParked("S2", vehicle2, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
            parkingLot.isParked("S3", vehicle3, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
            parkingLot.isParked("S4", vehicle4, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
            List location = parkingLot.findVehicleByModel("BMW");
            ArrayList<String> expectedResult = new ArrayList<>();
            expectedResult.add("S1");
            expectedResult.add("S2");
            expectedResult.add("S4");
            assertEquals(expectedResult, location);
        } catch (ParkingLotException e) {
            assertEquals(e.type, ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
        }
    }

    @Test
    public void givenAVehicleToFindByTime_WhenFound_ShouldReturnTrue() {
        parkingLot.setParkingTime(LocalDateTime.now().getMinute() - 20);
        parkingLot.isParked("S1", vehicle, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
        boolean parked30MinuteBefore = parkingLot.findVehicleWhichParked30MinuteBefore(LocalDateTime.now().getMinute());
        assertTrue(parked30MinuteBefore);
    }

    @Test
    public void givenAVehicleToFindByTime_WhenNotFound_ShouldThrowException() {
        try {
            parkingLot.setParkingTime(LocalDateTime.now().getMinute() + 40);
            parkingLot.isParked("S1", vehicle, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
            boolean parked30MinuteBefore = parkingLot.findVehicleWhichParked30MinuteBefore(LocalDateTime.now().getMinute());
            assertTrue(parked30MinuteBefore);
        } catch (ParkingLotException e) {
            assertEquals(e.type, ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
        }
    }

    @Test
    public void givenAVehicleToFindHandicapDriverAndSmall_WhenFound_ShouldReturnTrue() {
        parkingLot.setCapacity(5);
        Vehicle vehicle1 = new Vehicle("Black", "BMW", "MH02-MD6475");
        Vehicle vehicle2 = new Vehicle("Blue", "BMW", "MH02-MD6875");
        Vehicle vehicle3 = new Vehicle("White", "Toyota", "MH04-MD6475");
        Vehicle vehicle4 = new Vehicle("White", "BMW", "MH02-WA551");
        parkingLot.isParked("S1", vehicle1, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.HANDICAP);
        parkingLot.isParked("S2", vehicle2, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.HANDICAP);
        parkingLot.isParked("S3", vehicle3, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.HANDICAP);
        parkingLot.isParked("S4", vehicle4, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.HANDICAP);
        List location = parkingLot.findVehicleByDriverTypeAndVehicleType(VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.HANDICAP);
        ArrayList<Object> expectedResult = new ArrayList<>();
        expectedResult.add("S1");
        expectedResult.add("S2");
        expectedResult.add("S3");
        expectedResult.add("S4");
        assertEquals(expectedResult, location);
    }

    @Test
    public void givenAVehicleToFindAllVehicle_WhenFound_ShouldReturnTrue() {
        parkingLot.setCapacity(10);
        Vehicle vehicle1 = new Vehicle("Black", "Honda", "MH02-MD6475");
        Vehicle vehicle2 = new Vehicle("Blue", "Innova", "MH02-MD6875");
        Vehicle vehicle3 = new Vehicle("White", "Toyota", "MH04-MD6475");
        Vehicle vehicle4 = new Vehicle("White", "Skoda", "MH02-WA551");
        parkingLot.isParked("S1", vehicle1, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
        parkingLot.isParked("S2", vehicle2, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
        parkingLot.isParked("S3", vehicle3, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.NORMAL);
        parkingLot.isParked("S4", vehicle4, VehicleType.NORMAL_VEHICLE, ParkingLot.Driver.HANDICAP);
        List location = parkingLot.findAllVehicle();
        ArrayList<String> expectedResult = new ArrayList<>();
        expectedResult.add("S3");
        expectedResult.add("S4");
        expectedResult.add("S1");
        expectedResult.add("S2");
        assertEquals(expectedResult, location);
    }

    @Test
    public void givenAVehicleToFindAllVehicle_WhenNotFound_ShouldThrowException() {
        try {
            List location = parkingLot.findAllVehicle();
            ArrayList<String> expectedResult = new ArrayList<>();
            assertEquals(expectedResult, location);
        } catch (ParkingLotException e) {
            assertEquals(e.type, ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
        }
    }
}
