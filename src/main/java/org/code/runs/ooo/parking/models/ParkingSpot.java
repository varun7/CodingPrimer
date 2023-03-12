package org.code.runs.ooo.parking.models;

import com.google.common.base.Preconditions;
import java.util.Optional;

public class ParkingSpot {
  private final String id;
  private final ParkingType parkingType;
  private AvailabilityStatus availabilityStatus;
  private Optional<Vehichle> parkedVehichle;

  public ParkingSpot(String id, ParkingType parkingType) {
    this.id = id;
    this.parkingType = parkingType;
    availabilityStatus = AvailabilityStatus.AVAILABLE;
    parkedVehichle = Optional.empty();
  }

  public ParkingType getParkingType() {
    return parkingType;
  }

  public AvailabilityStatus getAvailabilityStatus() {
    return availabilityStatus;
  }

  public Optional<Vehichle> getParkedVehichle() {
    return parkedVehichle;
  }

  public String getId() {
    return id;
  }

  public void parkVehichle(Vehichle vehicle) {
    Preconditions.checkArgument(availabilityStatus != AvailabilityStatus.AVAILABLE, "Parking spot is not available.");
    this.parkedVehichle = Optional.of(vehicle);
    availabilityStatus = AvailabilityStatus.OCCUPIED;
  }

  public void unParkVehichle() {
    Preconditions.checkArgument(availabilityStatus != AvailabilityStatus.OCCUPIED, "Parking spot is not occupied.");
    this.parkedVehichle = Optional.empty();
    availabilityStatus = AvailabilityStatus.AVAILABLE;
  }
}
