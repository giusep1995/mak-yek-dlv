package model;

import java.util.List;

public class Game {
    private Player[] players;
    private Board board;
    private Player currentTurn;
    private byte status;
    private List<Move> movesPlayed;
    
    final public static byte ACTIVE = 0;
    final public static byte WHITE_WINS = 1;
    final public static byte BLACK_WINS = 2;
  
    private void initialize(Player p1, Player p2)
    {
        players[0] = p1;
        players[1] = p2;
  
        board.resetBoard();
  
        if (p1.isWhiteSide()) {
            this.currentTurn = p1;
        }
        else {
            this.currentTurn = p2;
        }
  
        movesPlayed.clear();
    }
  
    public boolean isEnd()
    {
        return this.getStatus() != ACTIVE;
    }
  
    public byte getStatus()
    {
        return this.status;
    }
  
    public void setStatus(byte status)
    {
        this.status = status;
    }
  
    public boolean playerMove(Player player, int startX, 
                                int startY, int endX, int endY) throws Exception
    {
        Spot startBox = board.getBox(startX, startY);
        Spot endBox = board.getBox(startY, endY);
        Move move = new Move(player, startBox, endBox);
        return this.makeMove(move, player);
    }
  
    private boolean makeMove(Move move, Player player)
    {
        Piece sourcePiece = move.getStart().getPiece();
        if (sourcePiece == null) {
            return false;
        }
  
        // valid player
        if (player != currentTurn) {
            return false;
        }
  
        if (sourcePiece.isWhite() != player.isWhiteSide()) {
            return false;
        }
  
        // valid move?
        if (!sourcePiece.canMove(board, move.getStart(), 
                                            move.getEnd())) {
            return false;
        }
  
        // kill?
        Piece destPiece = move.getStart().getPiece();
        if (destPiece != null) {
            destPiece.setKilled(true);
            move.setPieceKilled(destPiece);
        }
  
        
  
        // store the move
        movesPlayed.add(move);
  
        // move piece from the start box to end box
        move.getEnd().setPiece(move.getStart().getPiece());
        move.getStart().setPiece(null);
  
        if (destPiece != null) {
            if (player.isWhiteSide()) {
                this.setStatus(WHITE_WINS);
            }
            else {
                this.setStatus(BLACK_WINS);
            }
        }
  
        // set the current turn to the other player
        if (this.currentTurn == players[0]) {
            this.currentTurn = players[1];
        }
        else {
            this.currentTurn = players[0];
        }
  
        return true;
    }
}