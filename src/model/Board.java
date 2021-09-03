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
        //DA FARE
        
    }
}