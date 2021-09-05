package model;

public class Board {
    Spot[][] boxes;
  
    public Board()
    {
        this.resetBoard();
    }
  
    public Spot getBox(int x, int y) throws Exception
    {
  
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            throw new Exception("Index out of bound");
        }
  
        return boxes[x][y];
    }
  
    public void resetBoard()
    {
        for(int i = 0; i < 8; i++) {
        	for(int j = 0; j < 8; j++) {
        		
        		//black pieces
        		if(i == 0 || i == 2)
        			boxes[i][j] = new Spot(i, j, new Piece(false));
        		
        		//white pieces
        		else if(i == 7 || i == 5)
        			boxes[i][j] = new Spot(i, j, new Piece(true));
        		
        		//blank
        		else
        			boxes[i][j] = new Spot(i, j, null);
        		
        	}
        }
        
    }
}