package model;

public class Piece {
	  
 
    protected boolean white = false;
  
    public Piece(boolean white)
    {
        this.setWhite(white);
    }
  
    public boolean isWhite()
    {
        return this.white;
    }
  
    public void setWhite(boolean white)
    {
        this.white = white;
    }
  
    public boolean canMove(Board board, Spot start, Spot end) {
    	
    	if (end.getPiece() != null)
    			return false;
        
  
        int x = Math.abs(start.getX() - end.getX());
        int y = Math.abs(start.getY() - end.getY());
        return x * y == 0;
    	
    }
}