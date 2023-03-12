package org.code.runs.ooo.chess;

public abstract class Piece {
  private final String name;
  boolean active;

  public Piece(String name) {
    this.name = name;
    active = true;
  }

  abstract boolean canMove(Box[][] board, int startX, int end);
}

class Rook extends Piece {
  public Rook(String name) {
    super(name);
  }

  @Override
  boolean canMove(Box[][] board, int startX, int end) {
    return false;
  }
}

class Pawn extends Piece {

  public Pawn(String name) {
    super(name);
  }

  @Override
  boolean canMove(Box[][] board, int startX, int end) {
    return false;
  }
}

class  King extends Piece {

  public King(String name) {
    super(name);
  }

  @Override
  boolean canMove(Box[][] board, int startX, int end) {
    return false;
  }
}

class Queen extends Piece {

  public Queen(String name) {
    super(name);
  }

  @Override
  boolean canMove(Box[][] board, int startX, int end) {
    return false;
  }
}

class Knight extends Piece {

  public Knight(String name) {
    super(name);
  }

  @Override
  boolean canMove(Box[][] board, int startX, int end) {
    return false;
  }
}

class Bishop extends Piece {

  public Bishop(String name) {
    super(name);
  }

  @Override
  boolean canMove(Box[][] board, int startX, int end) {
    return false;
  }
}