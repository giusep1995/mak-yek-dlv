package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private Player P1;
    private Player P2;
    private Board board;
    private boolean whiteTurn;
    private byte status;
    private List<Move> movesPlayed;
    private static Game instance = null;
    
    final public static byte ACTIVE = 0;
    final public static byte WHITE_WINS = 1;
    final public static byte BLACK_WINS = 2;
  
    private Game(Player p1, Player p2)
    {
        P1 = p1;
        P2 = p2;      
    }
    
    public static Game  getInstance() {
    	if(instance == null) {
    		instance = new Game(new HumanPlayer(true), new HumanPlayer(false));
    	}
    	return instance;   		
    }
    
    public void reset() {
    	board = new Board();
        whiteTurn = true;     
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
        Spot endBox = board.getBox(endX, endY);
        Move move = new Move(player, startBox, endBox);
        
        if(checkValidMove(move, player)){
        	makeMove(move, player);
        	return true;
        }
        return false;
        	
        	
    }
    
    public boolean checkValidMove(Move move, Player player) {
    	Piece sourcePiece = move.getStart().getPiece();
        if (sourcePiece == null) {
            return false;
        }
  
        // valid player
        if (player.isWhiteSide() != whiteTurn) {
            return false;
        }
        
        //wrong checker selected
        if (sourcePiece.isWhite() != player.isWhiteSide()) {
            return false;
        }
  
        // valid move?
        if (!sourcePiece.canMove(board, move.getStart(), 
                                            move.getEnd())) {
            return false;
        }
        
        //Occupied direction ?
        ArrayList<Point> points = move.getDirectionSpots();
        for(Point p : points) {
        	try {
				if(board.getBox(p.x, p.y).getPiece() != null)
					return false;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }
        	
        return true;    	
    }
  
    private void makeMove(Move move, Player player)
    {
        
    	Piece sourcePiece = move.getStart().getPiece();
    	
    	//
        //DEFINIRE checkCapture e checkIntervention
    	//
    	
    	 if(checkIntervention(move,player)) {
        	if(sourcePiece.isWhite()) {
        		P2.remainingPieces -=2;
        		if(P2.remainingPieces == 0)
            		status = WHITE_WINS;	
        	}  	
            else {
        		P1.remainingPieces -=2;
        		if(P2.remainingPieces == 0)
            		status = WHITE_WINS;	
        	}
        }
    	
    	 if(checkCapture(move, player)) {
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
        if (whiteTurn) {
            this.whiteTurn = false;
        }
        else {
            this.whiteTurn = true;
        }
    }

	private boolean checkIntervention(Move move, Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean checkCapture(Move move, Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	public Player getP1() {
		return P1;
	}

	public void setP1(Player p1) {
		P1 = p1;
	}

	public Player getP2() {
		return P2;
	}

	public void setP2(Player p2) {
		P2 = p2;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
	
	public boolean isWhiteTurn() {
		return whiteTurn;
	}

	public List<Move> getMovesPlayed() {
		return movesPlayed;
	}

	public void setMovesPlayed(List<Move> movesPlayed) {
		this.movesPlayed = movesPlayed;
	}
    
    
}