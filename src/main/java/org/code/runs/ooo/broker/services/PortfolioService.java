package org.code.runs.ooo.broker.services;

import java.util.Set;
import org.code.runs.ooo.broker.models.StockLot;

public interface PortfolioService {

  Set<StockLot> allStocks();

  void addStocks(StockLot stockLot);

  void removeStocks(String lotId);
}
