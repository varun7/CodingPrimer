package org.code.runs.ooo.chess;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class ChessBoard {

  private final Box[][] board;

  public ChessBoard() {
    board = new Box[8][8];
  }

  private void initializeChessBoard() {
    createChessBoard();
    addWhitePieces();
    addBlackPieces();
  }

  private void createChessBoard() {
    for (int i=0; i<board.length; i++) {
      for (int j=0; j<board[0].length; j++) {
        Color boxColor = (i+j)%2 == 0 ? Color.WHITE : Color.BLACK;
        board[i][j] = new Box(boxColor, i, j);
      }
    }
  }

  private void addWhitePieces() {
    Rook wRook1 = new Rook("WR1");
    Rook wRook2 = new Rook("WR2");
    Bishop wBishop1 = new Bishop("WB1");
    Bishop wBishop2 = new Bishop("WB1");
    Knight wKnight1 = new Knight("WKN1");
    Knight wKnight2 = new Knight("WKN2");
    King wKing = new King("WK");
    Queen wQueen = new Queen("WQ");
    Pawn wPawn1 = new Pawn("WP1");
    Pawn wPawn2 = new Pawn("WP2");
    Pawn wPawn3 = new Pawn("WP3");
    Pawn wPawn4 = new Pawn("WP4");
    Pawn wPawn5 = new Pawn("WP5");
    Pawn wPawn6 = new Pawn("WP6");
    Pawn wPawn7 = new Pawn("WP7");
    Pawn wPawn8 = new Pawn("WP8");
    board[0][0].addPiece(wRook1);
    board[0][1].addPiece(wKnight1);
    board[0][2].addPiece(wBishop1);
    board[0][3].addPiece(wKing);
    board[0][4].addPiece(wQueen);
    board[0][5].addPiece(wBishop2);
    board[0][6].addPiece(wKnight2);
    board[0][7].addPiece(wRook2);
    board[1][0].addPiece(wPawn1);
    board[1][1].addPiece(wPawn2);
    board[1][2].addPiece(wPawn3);
    board[1][3].addPiece(wPawn4);
    board[1][4].addPiece(wPawn5);
    board[1][5].addPiece(wPawn6);
    board[1][6].addPiece(wPawn7);
    board[1][7].addPiece(wPawn8);
  }

  private void addBlackPieces() {
    Rook bRook1 = new Rook("BR1");
    Rook bRook2 = new Rook("BR2");
    Bishop bBishop1 = new Bishop("BB1");
    Bishop bBishop2 = new Bishop("BB1");
    Knight bKnight1 = new Knight("BKN1");
    Knight bKnight2 = new Knight("BKN2");
    King bKing = new King("BK");
    Queen bQueen = new Queen("BQ");
    Pawn bPawn1 = new Pawn("BP1");
    Pawn bPawn2 = new Pawn("BP2");
    Pawn bPawn3 = new Pawn("BP3");
    Pawn bPawn4 = new Pawn("BP4");
    Pawn bPawn5 = new Pawn("BP5");
    Pawn bPawn6 = new Pawn("BP6");
    Pawn bPawn7 = new Pawn("BP7");
    Pawn bPawn8 = new Pawn("BP8");
    board[7][0].addPiece(bRook1);
    board[7][1].addPiece(bKnight1);
    board[7][2].addPiece(bBishop1);
    board[7][3].addPiece(bKing);
    board[7][4].addPiece(bQueen);
    board[7][5].addPiece(bBishop2);
    board[7][6].addPiece(bKnight2);
    board[7][7].addPiece(bRook2);
    board[6][0].addPiece(bPawn1);
    board[6][1].addPiece(bPawn2);
    board[6][2].addPiece(bPawn3);
    board[6][3].addPiece(bPawn4);
    board[6][4].addPiece(bPawn5);
    board[6][5].addPiece(bPawn6);
    board[6][6].addPiece(bPawn7);
    board[6][7].addPiece(bPawn8);
  }
}
