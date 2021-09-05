package model;

public class Piece {
	  
    private boolean killed = false;
    private boolean white = false;
  
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
  
    public boolean isKilled()
    {
        return this.killed;
    }
  
    public void setKilled(boolean killed)
    {
        this.killed = killed;
    }
  
    public boolean canMove(Board board, Spot start, Spot end) {
    	
    	if (end.getPiece().isWhite() == this.isWhite()) {
            return false;
        }
  
        int x = Math.abs(start.getX() - end.getX());
        int y = Math.abs(start.getY() - end.getY());
        return x * y == 0;
    	
    }
}