package org.code.runs.ooo.chess;

public final class Chess {
  ChessBoard board;
  Player whitePlayer;
  Player blackPlayer;

  public Chess() {
    this.board = new ChessBoard();
  }

  public void join(Player player) {
    throw new IllegalArgumentException("Tried to join a game with already 2 players");
  }

  public void move(Player player, Piece piece, int x, int y) {

  }
}
