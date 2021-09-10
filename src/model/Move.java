package model;

import java.awt.Point;
import java.util.ArrayList;

public class Move {
    private Player player;
    private Spot start;
    private Spot end;
    private Piece pieceMoved;
    private Piece pieceKilled;   
 
    public Move(Player player, Spot start, Spot end)
    {
        this.player = player;
        this.start = start;
        this.end = end;
        this.pieceMoved = start.getPiece();
    }
  
    public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Spot getStart() {
		return start;
	}

	public void setStart(Spot start) {
		this.start = start;
	}

	public Spot getEnd() {
		return end;
	}

	public void setEnd(Spot end) {
		this.end = end;
	}

	public Piece getPieceMoved() {
		return pieceMoved;
	}

	public void setPieceMoved(Piece pieceMoved) {
		this.pieceMoved = pieceMoved;
	}

	public Piece getPieceKilled() {
		return pieceKilled;
	}

	public void setPieceKilled(Piece pieceKilled) {
		this.pieceKilled = pieceKilled;
	}
	
	public ArrayList<Point> getDirectionSpots() {
		ArrayList<Point> points = new ArrayList<Point>();
		if(start.getX() == end.getX()) {
			int direction = start.getY() - end.getY();
			if(direction < 0) {
				for(int i = start.getY(); i >= end.getY(); i--) {
					points.add(new Point(start.getX(), i));
				}
			}
			
			else if(direction > 0) {
				for(int i = start.getY(); i <= end.getY(); i++) {
					points.add(new Point(start.getX(), i));
				}
			}
		}
		
		else if(start.getY() == end.getY()) {
			int direction = start.getX() - end.getX();
			if(direction < 0) {
				for(int i = start.getX(); i >= end.getX(); i--) {
					points.add(new Point(i, start.getY()));
				}
			}
			
			else if(direction > 0) {
				for(int i = start.getX(); i <= end.getX(); i++) {
					points.add(new Point(i, start.getY()));
				}
			}
		}
		return points;
	}
	
	

}