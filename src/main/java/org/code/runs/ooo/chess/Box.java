package org.code.runs.ooo.chess;

import java.util.Optional;

public final class Box {
  private final Color color;
  private final int x;
  private final int y;
  private Optional<Piece> piece;

  public Box(Color color, int x, int y) {
    this.color = color;
    this.x = x;
    this.y = y;
  }

  public Color getColor() {
    return color;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public Optional<Piece> getPiece() {
    return piece;
  }

  public void addPiece(Piece piece) {
    this.piece = Optional.of(piece);
  }

  public void removePiece() {
    this.piece = Optional.empty();
  }
}
