package model;

public class ComputerPlayer extends Player {
	  
    public ComputerPlayer(boolean whiteSide)
    {
    	super();
        this.whiteSide = whiteSide;
        this.humanPlayer = false;
    }
}