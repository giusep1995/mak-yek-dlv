
package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Game;
import model.Player;


public class Window extends JFrame {

	
	
	public static final int DEFAULT_WIDTH = 500;
	
	
	public static final int DEFAULT_HEIGHT = 600;
	
	
	public static final String DEFAULT_TITLE = "Mak-Yek";
	
	
	private BoardView board;
	
	private OptionPanel opts;
	
	
	public Window() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_TITLE);
	}
	
	public Window(Player player1, Player player2) {
		this();
		player1 = Game.getInstance().getP1();
		player2 = Game.getInstance().getP2();
	}
	
	public Window(int width, int height, String title) {
		
		// Setup the window
		super(title);
		super.setSize(width, height);
		super.setLocationByPlatform(true);
		
		// Setup the components
		JPanel layout = new JPanel(new BorderLayout());
		this.board = new BoardView(this);
		this.opts = new OptionPanel(this);
		layout.add(board, BorderLayout.CENTER);
		layout.add(opts, BorderLayout.SOUTH);
		this.add(layout);
		
	
	}
	
	public BoardView getBoard() {
		return board;
	}

	
	
	/**
	 * Resets the game of checkers in the window.
	 */
	public void restart() {
		Game.getInstance().reset();
		try {
			this.board.update();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	

}
