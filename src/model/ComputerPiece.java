package model;

public class ComputerPiece extends Piece {
	
	private byte id;

	public ComputerPiece(boolean white, byte id) {
		super(false);
		this.id = id;
	}

	public byte getId() {
		return id;
	}

	public void setId(byte id) {
		this.id = id;
	}

}
