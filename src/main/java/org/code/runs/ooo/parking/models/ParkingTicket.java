package org.code.runs.ooo.parking.models;

import java.time.Instant;
import java.util.Optional;

public class ParkingTicket {
  private final Vehichle vehichle;
  private final ParkingSpot parkingSpot;
  private final Instant parkedTime;
  private Optional<Instant> unparkedTime;

  public ParkingTicket(Vehichle vehichle,
      ParkingSpot parkingSpot, Instant parkedTime) {
    this.vehichle = vehichle;
    this.parkingSpot = parkingSpot;
    this.parkedTime = parkedTime;
    this.unparkedTime = Optional.empty();
  }
}
