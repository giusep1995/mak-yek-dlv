package model;

import java.util.List;

public class Game {
    private Player P1;
    private Player P2;
    private Board board;
    private Player currentTurn;
    private byte status;
    private List<Move> movesPlayed;
    private static Game game = null;
    
    final public static byte ACTIVE = 0;
    final public static byte WHITE_WINS = 1;
    final public static byte BLACK_WINS = 2;
  
    private Game(Player p1, Player p2)
    {
        P1 = p1;
        P2 = p2;
  
        board.resetBoard();
  
        if (p1.isWhiteSide()) {
            this.currentTurn = p1;
        }
        else {
            this.currentTurn = p2;
        }
  
        movesPlayed.clear();
    }
    
    public static Game  getIstance() {
    	if(game == null) {
    		game = new Game(new HumanPlayer(true), new HumanPlayer(false));
    		return game;
    	}
    	return game;   		
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
        Spot endBox = board.getBox(endX, endY);
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
  
        // kill
        Piece destPiece = move.getEnd().getPiece();
        if (destPiece != null) {
            destPiece.setKilled(true);
            move.setPieceKilled(destPiece);
            if(sourcePiece.isWhite())
            	if(--P2.remainingPieces == 0)
            		status = WHITE_WINS;
            else   	
            	if(--P1.remainingPieces == 0)
            		status = BLACK_WINS;
        }
  
        // store the move
        movesPlayed.add(move);
  
        // move piece from the start box to end box
        move.getEnd().setPiece(move.getStart().getPiece());
        move.getStart().setPiece(null);

        // set the current turn to the other player
        if (this.currentTurn == P1) {
            this.currentTurn = P1;
        }
        else {
            this.currentTurn = P2;
        }
  
        return true;
    }
}