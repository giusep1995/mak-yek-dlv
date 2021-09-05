/* Name: CheckersWindow
 * Author: Devon McGrath
 * Description: This class is a window that is used to play a game of checkers.
 * It also contains a component to change the game options.
 */

package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Game;
import model.Player;


public class Window extends JFrame {

	
	/** The default width for the checkers window. */
	public static final int DEFAULT_WIDTH = 500;
	
	/** The default height for the checkers window. */
	public static final int DEFAULT_HEIGHT = 600;
	
	/** The default title for the checkers window. */
	public static final String DEFAULT_TITLE = "Java Checkers";
	
	/** The checker board component playing the updatable game. */
	private BoardView board;
	
	private OptionPanel opts;
	
	
	public Window() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_TITLE);
	}
	
	public Window(Player player1, Player player2) {
		this();
		player1 = Game.getIstance().getP1();
		player2 = Game.getIstance().getP2();
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
		Game.getIstance().reset();
		this.board.update();
	}
	

	

}