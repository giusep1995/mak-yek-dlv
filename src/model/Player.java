package model;

public abstract class Player {
    public boolean whiteSide;
    public boolean humanPlayer;
    public byte remainingPieces;
    
    public Player() {
    	remainingPieces = 16;
    }
  
    public boolean isWhiteSide()
    {
        return this.whiteSide;
    }
    public boolean isHumanPlayer()
    {
        return this.humanPlayer;
    }
    
    
}
  

  
