package org.code.runs.ooo.amazon.services;

import org.code.runs.ooo.amazon.models.Shipment;

public interface FulfilmentService {
  Shipment getShipment(String shipmentId);
}
