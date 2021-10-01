package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private HumanPlayer P1;
    private ComputerPlayer P2;
    private Board board;
    private boolean whiteTurn;
    private byte status;
    private List<Move> movesPlayed;
    private static Game instance = null;
    
    final public static byte ACTIVE = 0;
    final public static byte WHITE_WINS = 1;
    final public static byte BLACK_WINS = 2;
  
    private Game()
    {
        P1 = new HumanPlayer(true);
        P2 = new ComputerPlayer(false);
        board = new Board();
        movesPlayed = new ArrayList<Move>();
        reset();
    }
    
    public static Game  getInstance() {
    	if(instance == null) {
    		instance = new Game();
    	}
    	return instance;   		
    }
    
    public void reset() {
    	board.resetBoard();
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
    
    public boolean computerMove() throws Exception {
    	
    	if(!whiteTurn) {
    		
    		System.out.println(board.toString());
    		MyPair<Integer, Point> pair = P2.evaluateNextMove(board.toString());
	    	Spot startBox = board.getBoxByComputerId(pair.getElement0());
	    	Spot endBox = board.getBox(pair.getElement1().x, pair.getElement1().y);
	    	Move move = new Move(P2, startBox, endBox);
	    	
	    	if(checkValidMove(move, P2)){
	        	makeMove(move, P2);
	        	return true;
	        }
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
		try {
			int x = move.getEnd().getX();
			int y = move.getEnd().getY();
			
			//BORDO SUPERIORE E INFERIROE
			if((x == 0 || x == 7) && (y>=1 && y<=6) ) {
				if(isOccupied(board.getBox(x, y-1)) && isOccupied(board.getBox(x, y+1)) && isOpponent(board.getBox(x, y-1), player) && isOpponent(board.getBox(x, y+1), player)) {
					capturePiece(board.getBox(x, y-1));
					capturePiece(board.getBox(x, y+1));
					return true;
				}
			}
			else
			//LATO DX E SX
			if((y == 0 || y == 7) && (x>=1 && x<=6) ) {
				if(isOccupied(board.getBox(x-1, y)) && isOccupied(board.getBox(x+1, y)) && isOpponent(board.getBox(x-1, y), player) && isOpponent(board.getBox(x+1, y), player)) {
					capturePiece(board.getBox(x-1, y));
					capturePiece(board.getBox(x+1, y));
					return true;
				}
			}
			else
			//CENTRO
			if ((x>0 || x<7) && (y>0 || y<7)) {
				if(isOccupied(board.getBox(x, y-1)) && isOccupied(board.getBox(x, y+1)) && isOpponent(board.getBox(x, y-1), player) && isOpponent(board.getBox(x, y+1), player)) {
					capturePiece(board.getBox(x, y-1));
					capturePiece(board.getBox(x, y+1));
					return true;
				}
				if(isOccupied(board.getBox(x-1, y)) && isOccupied(board.getBox(x+1, y)) && isOpponent(board.getBox(x-1, y), player) && isOpponent(board.getBox(x+1, y), player)) {
					capturePiece(board.getBox(x-1, y));
					capturePiece(board.getBox(x+1, y));
					return true;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	private boolean checkCapture(Move move, Player player) {
		try {
			int x = move.getEnd().getX();
			int y = move.getEnd().getY();
			// CASI SPECIALI
			
			//X=0, Y=0; X=1, Y=0; X=1, Y=1; X=0, Y=1
			if((x == 0 || x == 1) && (y == 0 || y == 1)) {
				if(isOccupied(board.getBox(x, y+1)) && isOccupied(board.getBox(x, y+2)) && isOpponent(board.getBox(x, y+1), player) && !isOpponent(board.getBox(x, y+2), player)) {
					capturePiece(board.getBox(x, y+1));
					return true;
				}
				if(isOccupied(board.getBox(x+1, y)) && isOccupied(board.getBox(x+2, y)) && isOpponent(board.getBox(x, y+1), player) && !isOpponent(board.getBox(x, y+2), player)) {
					capturePiece(board.getBox(x+1, y));
					return true;
				}
			}
			else
			//X=7 Y=7 etc ...
			if((x == 7 || x == 6) && (y == 7 || y == 6)) {
				if(isOccupied(board.getBox(x, y-1)) && isOccupied(board.getBox(x, y-2)) && isOpponent(board.getBox(x, y-1), player) && !isOpponent(board.getBox(x, y-2), player)) {
					capturePiece(board.getBox(x, y-1));
					return true;
				}
				if(isOccupied(board.getBox(x-1, y)) && isOccupied(board.getBox(x-2, y)) && isOpponent(board.getBox(x-1, y), player) && !isOpponent(board.getBox(x-2, y), player)) {
					capturePiece(board.getBox(x-1, y));
					return true;
				}
			}
			else
			//X=7 Y=0 etc ...
			if((x == 7 || x == 6) && (y == 0 || y == 1)) {
				if(isOccupied(board.getBox(x, y+1)) && isOccupied(board.getBox(x, y+2)) && isOpponent(board.getBox(x, y+1), player) && !isOpponent(board.getBox(x, y+2), player)) {
					capturePiece(board.getBox(x, y+1));
					return true;
				}
				if(isOccupied(board.getBox(x-1, y)) && isOccupied(board.getBox(x-2, y)) && isOpponent(board.getBox(x-1, y), player) && !isOpponent(board.getBox(x-2, y), player)) {
					capturePiece(board.getBox(x-1, y));
					return true;
				}
			}
			else
			//X=0 Y=7 etc ...
			if((x == 0 || x == 1) && (y == 6 || y == 7)) {
				if(isOccupied(board.getBox(x, y-1)) && isOccupied(board.getBox(x, y-2)) && isOpponent(board.getBox(x, y-1), player) && !isOpponent(board.getBox(x, y-2), player)) {
					capturePiece(board.getBox(x, y-1));
					return true;
				}
				if(isOccupied(board.getBox(x+1, y)) && isOccupied(board.getBox(x+2, y)) && isOpponent(board.getBox(x+1, y), player) && !isOpponent(board.getBox(x+2, y), player)) {
					capturePiece(board.getBox(x+1, y));
					return true;
				}
			}
			else
			//BORDO SUPERIORE
			if((x == 0 || x == 1) && (y>=2 && y<=5) ) {
				if(isOccupied(board.getBox(x, y-1)) && isOccupied(board.getBox(x, y-2)) && isOpponent(board.getBox(x, y-1), player) && !isOpponent(board.getBox(x, y-2), player)) {
					capturePiece(board.getBox(x, y-1));
					return true;
				}
				if(isOccupied(board.getBox(x+1, y)) && isOccupied(board.getBox(x+2, y)) && isOpponent(board.getBox(x+1, y), player) && !isOpponent(board.getBox(x+2, y), player)) {
					capturePiece(board.getBox(x+1, y));
					return true;
				}
				if(isOccupied(board.getBox(x, y+1)) && isOccupied(board.getBox(x, y+2)) && isOpponent(board.getBox(x, y+1), player) && !isOpponent(board.getBox(x, y+2), player)) {
					capturePiece(board.getBox(x, y+1));
					return true;
				}
			}
			else
			//BORDO INFERIORE
			if((x == 7 || x == 6) && (y>=2 && y<=5) ) {
				if(isOccupied(board.getBox(x, y-1)) && isOccupied(board.getBox(x, y-2)) && isOpponent(board.getBox(x, y-1), player) && !isOpponent(board.getBox(x, y-2), player)) {
					capturePiece(board.getBox(x, y-1));
					return true;
				}
				if(isOccupied(board.getBox(x-1, y)) && isOccupied(board.getBox(x-2, y)) && isOpponent(board.getBox(x-1, y), player) && !isOpponent(board.getBox(x-2, y), player)) {
					capturePiece(board.getBox(x-1, y));
					return true;
				}
				if(isOccupied(board.getBox(x, y+1)) && isOccupied(board.getBox(x, y+2)) && isOpponent(board.getBox(x, y+1), player) && !isOpponent(board.getBox(x, y+2), player)) {
					capturePiece(board.getBox(x, y+1));
					return true;
				}
			}
			else
			//LATO DX
			if((y == 7 || y == 6) && (x>=2 && x<=5) ) {
				if(isOccupied(board.getBox(x, y-1)) && isOccupied(board.getBox(x, y-2)) && isOpponent(board.getBox(x, y-1), player) && !isOpponent(board.getBox(x, y-2), player)) {
					capturePiece(board.getBox(x, y-1));
					return true;
				}
				if(isOccupied(board.getBox(x-1, y)) && isOccupied(board.getBox(x-2, y)) && isOpponent(board.getBox(x-1, y), player) && !isOpponent(board.getBox(x-2, y), player)) {
					capturePiece(board.getBox(x-1, y));
					return true;
				}
				if(isOccupied(board.getBox(x+1, y)) && isOccupied(board.getBox(x+2, y)) && isOpponent(board.getBox(x+1, y), player) && !isOpponent(board.getBox(x+2, y), player)) {
					capturePiece(board.getBox(x+1, y));
					return true;
				}
			}
			else
			//LATO SX
			if((y == 0 || y == 1) && (x>=2 && x<=5) ) {
				if(isOccupied(board.getBox(x, y+1)) && isOccupied(board.getBox(x, y+2)) && isOpponent(board.getBox(x, y+1), player) && !isOpponent(board.getBox(x, y+2), player)) {
					capturePiece(board.getBox(x, y+1));
					return true;
				}
				if(isOccupied(board.getBox(x-1, y)) && isOccupied(board.getBox(x-2, y)) && isOpponent(board.getBox(x-1, y), player) && !isOpponent(board.getBox(x-2, y), player)) {
					capturePiece(board.getBox(x-1, y));
					return true;
				}
				if(isOccupied(board.getBox(x+1, y)) && isOccupied(board.getBox(x+2, y)) && isOpponent(board.getBox(x+1, y), player) && !isOpponent(board.getBox(x+2, y), player)) {
					capturePiece(board.getBox(x+1, y));
					return true;
				}
			}
			//CENTRO
			else{
				if(isOccupied(board.getBox(x, y+1)) && isOccupied(board.getBox(x, y+2)) && isOpponent(board.getBox(x, y+1), player) && !isOpponent(board.getBox(x, y+2), player)) {
					capturePiece(board.getBox(x, y+1));
					return true;
				}
				if(isOccupied(board.getBox(x-1, y)) && isOccupied(board.getBox(x-2, y)) && isOpponent(board.getBox(x-1, y), player) && !isOpponent(board.getBox(x-2, y), player)) {
					capturePiece(board.getBox(x-1, y));
					return true;
				}
				if(isOccupied(board.getBox(x+1, y)) && isOccupied(board.getBox(x+2, y)) && isOpponent(board.getBox(x+1, y), player) && !isOpponent(board.getBox(x+2, y), player)) {
					capturePiece(board.getBox(x+1, y));
					return true;
				}
				if(isOccupied(board.getBox(x, y-1)) && isOccupied(board.getBox(x, y-2)) && isOpponent(board.getBox(x, y-1), player) && !isOpponent(board.getBox(x, y-2), player)) {
					capturePiece(board.getBox(x, y-1));
					return true;
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return false;
	}
	
	private boolean isOpponent(Spot s, Player player) {
		
		return s.getPiece().isWhite() != player.isWhiteSide();
		
	}
	
	private boolean isOccupied(Spot s) {
		return s.getPiece() != null;
	}
	
	private void capturePiece(Spot s) {
		s.setPiece(null);
	}
	

	public Player getP1() {
		return P1;
	}



	public Player getP2() {
		return P2;
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