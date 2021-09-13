package model;

import java.awt.Point;

public class Board {
    Spot[][] boxes;
  
    public Board()
    {
    	
        this.resetBoard();
    }
  
    public Spot getBox(int x, int y) throws Exception
    {
  
        if (!isValidPoint(new Point(x,y))) {
            throw new Exception("Index out of bound");
        }
  
        return boxes[x][y];
    }
  
    public void resetBoard()
    {
    	Spot[][] boxes = new Spot[8][8];
    	for(int i = 0; i < 8; i++) {
        	for(int j = 0; j < 8; j++) {
        		
        		//black pieces
        		if(i == 0 || i == 2)
        			boxes[j][i] = new Spot(j, i, new Piece(false));
        		
        		//white pieces
        		else if(i == 7 || i == 5)
        			boxes[j][i] = new Spot(j, i, new Piece(true));
        		
        		//blank
        		else
        			boxes[j][i] = new Spot(j, i, null);
        		
        	}
        }
    	this.boxes = boxes;
    	System.out.println(toString());
        
    }
    
	public static boolean isValidPoint(Point testPoint) {
			
			if (testPoint == null) {
				return false;
			}
			
			// Check that it is on the board
			final int x = testPoint.x, y = testPoint.y;
			if (x < 0 || x > 7 || y < 0 || y > 7) {
				return false;
			}
			
			
			return true;
	}
	
	@Override
	public String toString() {
		String s=null;
		
		for(int i = 0; i < 8; i++) {
        	for(int j = 0; j < 8; j++) {
        		if(boxes[j][i].getPiece() == null)
        			s+="0";
        		else if(boxes[j][i].getPiece().isWhite())
        			s+="1";
        		else
        			s+="2";
        		
        	}
        	s+="\n";
        }
		
		return s;
	}
}