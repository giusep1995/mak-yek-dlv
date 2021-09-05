package model;

public class HumanPlayer extends Player {
	  
    public HumanPlayer(boolean whiteSide)
    {
    	super();
        this.whiteSide = whiteSide;
        this.humanPlayer = true;
    }
}