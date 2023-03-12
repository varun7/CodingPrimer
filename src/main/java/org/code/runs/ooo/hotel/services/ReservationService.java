package org.code.runs.ooo.hotel.services;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.code.runs.ooo.hotel.model.Reservation;
import org.code.runs.ooo.hotel.model.RoomType;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public interface ReservationService {
  Set<String> availableRooms(LocalDate from, LocalDate to, RoomType type);

  Optional<Reservation> reserve(LocalDate from, LocalDate to, RoomType type, int numberOfRooms);
}

class SimpleReservationService implements ReservationService {
  private final Table<LocalDate, RoomType, Set<String>> availableRooms;
  private final Table<LocalDate, String, String> bookingTable;

  public SimpleReservationService() {
    this.availableRooms = HashBasedTable.create();
    this.bookingTable = HashBasedTable.create();
  }

  @Override
  public Set<String> availableRooms(LocalDate from, LocalDate to, RoomType type) {
    Set<String> rooms = new HashSet<>(availableRooms.get(from, type));
    for (LocalDate d = from.plusDays(1); d.isBefore(to.plusDays(1)); d = d.plusDays(1)) {
      rooms.retainAll(availableRooms.get(d, type));
    }
    return rooms;
  }

  @Override
  public Optional<Reservation> reserve(LocalDate from, LocalDate to, RoomType type, int numberOfRooms) {
    Set<String> availableRooms = availableRooms(from, to, type);
    if (availableRooms.size() < numberOfRooms) {
      return Optional.empty();
    }
    Reservation reservation = Reservation.newBuilder().build();
    return Optional.of(reservation);
  }
}