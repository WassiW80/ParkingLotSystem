public class ParkingLot {

    private Object vehicle;

    public boolean isParked(Object vehicle) {
        this.vehicle = vehicle;
        return true;
    }

    public boolean isUnParked(Object vehicle) {
        if (this.vehicle.equals(vehicle)) {
            this.vehicle = null;
            return true;
        }
        return false;
    }
}
