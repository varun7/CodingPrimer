package org.code.runs.ooo.parking.models;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ParkingLot {

  /**
   * Find the available slot for parking the vehichle.
   */
  Optional<ParkingTicket> park(ParkingType parkingType);

  /**
   * Pay for the parking.
   */
  void pay(ParkingTicket parkingTicket);

  Map<ParkingType, ParkingSpot> availableSlots();

  DisplayBoard getDisplayBoard();
}

class SimpleParkingLot implements ParkingLot {

  private List<ParkingFloor> floors;

  public SimpleParkingLot(List<ParkingFloor> floors) {

  }

  @Override
  public Optional<ParkingTicket> park(ParkingType parkingType) {
    return Optional.empty();
  }

  @Override
  public void pay(ParkingTicket parkingTicket) {

  }

  @Override
  public Map<ParkingType, ParkingSpot> availableSlots() {
    return null;
  }

  @Override
  public DisplayBoard getDisplayBoard() {
    return null;
  }
}