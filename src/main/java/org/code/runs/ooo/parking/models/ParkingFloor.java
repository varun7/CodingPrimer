package org.code.runs.ooo.parking.models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class ParkingFloor {
  private final Map<ParkingType, Set<ParkingSpot>> spotsMap;
  private final String name;

  public ParkingFloor(String name, Set<ParkingSpot> parkingSpots) {
    this.name = name;
    spotsMap = new HashMap<>();
    for (ParkingSpot spot : parkingSpots) {
      Set<ParkingSpot> spotsOfType = spotsMap.getOrDefault(spot.getParkingType(), new HashSet<>());
      spotsOfType.add(spot);
      spotsMap.put(spot.getParkingType(), spotsOfType);
    }
  }

  public Map<ParkingType, Set<ParkingSpot>> getSpotsMap() {
    return spotsMap;
  }

  public Optional<ParkingSpot> findAvailable(ParkingType type) {
    if (!spotsMap.containsKey(type)) {
      return Optional.empty();
    }
    return spotsMap.get(type)
        .stream()
        .filter(p -> p.getAvailabilityStatus() == AvailabilityStatus.AVAILABLE)
        .findFirst();
  }


  public Map<ParkingType, Integer> availableSlotCount() {
    Map<ParkingType, Integer> countMap = new HashMap<>();
    for (Map.Entry<ParkingType, Set<ParkingSpot>> e : spotsMap.entrySet()) {
      countMap.put(e.getKey(), e.getValue().size());
    }
    return countMap;
  }
}
