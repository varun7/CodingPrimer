package org.code.runs.ooo.amazon.services;

import org.code.runs.ooo.amazon.models.Product;

public interface CatalogueService {
  void add(Product product);

  void update(Product product);

  void delete(Product product);
}
