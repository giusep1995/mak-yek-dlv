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
    
    public Spot getBoxByComputerId(int id) throws Exception {
    	Piece p;
    	for(int y=0; y<8; y++)
    		for(int x=0; x<8; x++) {
    			p = boxes[x][y].getPiece();
    			if(p instanceof ComputerPiece && ((ComputerPiece)p).getId() == id) {
    				return boxes[x][y];
    			}			
    		}
    	
    	throw new Exception("Nessuna pedina con l'id: " +id);
    }
  
    public void resetBoard()
    {
    	Spot[][] boxes = new Spot[8][8];
    	byte computer_ids = 0;
    	for(int i = 0; i < 8; i++) {
        	for(int j = 0; j < 8; j++) {
        		
        		//black pieces
        		if(i == 0 || i == 2)
        			boxes[j][i] = new Spot(j, i, new ComputerPiece(false, ++computer_ids));
        		
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
		String s="";
		
		for(int y = 0; y < 8; y++) {
        	for(int x = 0; x < 8; x++) {
        		if(boxes[x][y].getPiece() == null)
        			s+="free("+x+","+y+")."; 
        		
        		else if(boxes[x][y].getPiece().isWhite())
        			s+="enemy("+x+","+y+").";
        		
        		else {
        			ComputerPiece p = (ComputerPiece)boxes[x][y].getPiece();
        			s+="computer("+p.getId()+","+x+","+y+").";
        		}   		
        	}
        	s+="\n";
        }		
		return s;
	}
}