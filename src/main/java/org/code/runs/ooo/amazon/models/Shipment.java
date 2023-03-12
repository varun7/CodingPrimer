package org.code.runs.ooo.amazon.models;

import java.time.LocalDate;

public class Shipment {
  private final ShipmentStatus shipmentStatus;
  private final LocalDate estimatedDelivery;
  private final Address deliveryAddress;

  private Shipment(ShipmentStatus shipmentStatus, LocalDate estimatedDelivery,
      Address deliveryAddress) {
    this.shipmentStatus = shipmentStatus;
    this.estimatedDelivery = estimatedDelivery;
    this.deliveryAddress = deliveryAddress;
  }

  public ShipmentStatus getShipmentStatus() {
    return shipmentStatus;
  }

  public LocalDate getEstimatedDelivery() {
    return estimatedDelivery;
  }

  public Address getDeliveryAddress() {
    return deliveryAddress;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private ShipmentStatus shipmentStatus;
    private LocalDate estimatedDelivery;
    private Address deliveryAddress;

    public Builder setShipmentStatus(ShipmentStatus shipmentStatus) {
      this.shipmentStatus = shipmentStatus;
      return this;
    }

    public Builder setEstimatedDelivery(LocalDate estimatedDelivery) {
      this.estimatedDelivery = estimatedDelivery;
      return this;
    }

    public Builder setDeliveryAddress(Address deliveryAddress) {
      this.deliveryAddress = deliveryAddress;
      return this;
    }

    public Shipment build() {
      return new Shipment(shipmentStatus, estimatedDelivery, deliveryAddress);
    }
  }
}
