package org.code.runs.ooo.amazon.services;

import org.code.runs.ooo.amazon.models.LineItem;
import org.code.runs.ooo.amazon.models.Product;

public interface Cart {
  String addToCart(Product product, int quantity);

  boolean removeFromCarts(String lineItemId);

  boolean updateItemInCart(LineItem lineItem);
}
