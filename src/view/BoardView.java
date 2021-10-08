

package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.Timer;

import model.Board;
import model.Game;
import model.HumanPlayer;
import model.Player;
import model.Spot;



public class BoardView extends JButton {

	private static final long serialVersionUID = -6014690893709316364L;
	
	
	private static final int TIMER_DELAY = 1000;
	
	
	
	private static final int PADDING = 16;

	
	private Game game;
	
	
	private Window window;
	
	

	private Point selected;
	

	private boolean selectionValid;
	

	private Color lightTile;

	
	private Color darkTile;
	
	
	private boolean isGameOver;
	
	
	private Timer timer;
	
	
	public BoardView(Window window) {
		
		// Setup the component
		super.setBorderPainted(false);
		super.setFocusPainted(false);
		super.setContentAreaFilled(false);
		super.setBackground(Color.LIGHT_GRAY);
		this.addActionListener(new ClickListener());
		
		// Setup the game
		this.lightTile = Color.WHITE;
		this.darkTile = Color.BLACK;
		this.window = window;
		this.game = Game.getInstance();
	}
	

	public void update() throws Exception {
		this.isGameOver = Game.getInstance().getStatus() != Game.ACTIVE? true:false;
		repaint();
		game.computerMove();
		this.isGameOver = Game.getInstance().getStatus() != Game.ACTIVE? true:false;
		repaint();
	}
	

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		// Perform calculations
		final int BOX_PADDING = 4;
		final int W = getWidth(), H = getHeight();
		final int DIM = W < H? W : H, BOX_SIZE = (DIM - 2 * PADDING) / 8;
		final int OFFSET_X = (W - BOX_SIZE * 8) / 2;
		final int OFFSET_Y = (H - BOX_SIZE * 8) / 2;
		final int CHECKER_SIZE = Math.max(0, BOX_SIZE - 2 * BOX_PADDING);
		
		// Draw checker board
		g.setColor(Color.BLACK);
		g.drawRect(OFFSET_X - 1, OFFSET_Y - 1, BOX_SIZE * 8 + 1, BOX_SIZE * 8 + 1);
		g.setColor(lightTile);
		g.fillRect(OFFSET_X, OFFSET_Y, BOX_SIZE * 8, BOX_SIZE * 8);
		g.setColor(darkTile);
		for (int y = 0; y < 8; y ++) {
			for (int x = (y + 1) % 2; x < 8; x += 2) {
				g.fillRect(OFFSET_X + x * BOX_SIZE, OFFSET_Y + y * BOX_SIZE,
						BOX_SIZE, BOX_SIZE);
			}
		}
		
		// Highlight the selected tile if valid
		if (Board.isValidPoint(selected)) {
			g.setColor(selectionValid? Color.GREEN : Color.RED);
			g.fillRect(OFFSET_X + selected.x * BOX_SIZE,
					OFFSET_Y + selected.y * BOX_SIZE,
					BOX_SIZE, BOX_SIZE);
		}
		
		// Draw the checkers
		Board b = game.getBoard();
		for (int y = 0; y < 8; y ++) {
			int cy = OFFSET_Y + y * BOX_SIZE + BOX_PADDING;
			for (int x = 0; x < 8; x ++) {
				Spot s;
				try {
					s = b.getBox(x, y);
					// Empty, just skip
					if (s.isEmpty())
						continue;
					
					
					int cx = OFFSET_X + x * BOX_SIZE + BOX_PADDING;
					
					// Black checker
					if (!s.getPiece().isWhite()) {
						g.setColor(Color.DARK_GRAY);
						g.fillOval(cx + 1, cy + 2, CHECKER_SIZE, CHECKER_SIZE);
						g.setColor(Color.LIGHT_GRAY);
						g.drawOval(cx + 1, cy + 2, CHECKER_SIZE, CHECKER_SIZE);
						g.setColor(Color.BLACK);
						g.fillOval(cx, cy, CHECKER_SIZE, CHECKER_SIZE);
						g.setColor(Color.LIGHT_GRAY);
						g.drawOval(cx, cy, CHECKER_SIZE, CHECKER_SIZE);
					}
					
					
					// White checker
					else {
						g.setColor(Color.LIGHT_GRAY);
						g.fillOval(cx + 1, cy + 2, CHECKER_SIZE, CHECKER_SIZE);
						g.setColor(Color.DARK_GRAY);
						g.drawOval(cx + 1, cy + 2, CHECKER_SIZE, CHECKER_SIZE);
						g.setColor(Color.WHITE);
						g.fillOval(cx, cy, CHECKER_SIZE, CHECKER_SIZE);
						g.setColor(Color.DARK_GRAY);
						g.drawOval(cx, cy, CHECKER_SIZE, CHECKER_SIZE);
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}	
				
		}
		
		// Draw the player turn sign
		String msg = game.isWhiteTurn()? "Player 1's turn" : "Player 2's turn";
		int width = g.getFontMetrics().stringWidth(msg);
		Color back = game.isWhiteTurn()? Color.WHITE : Color.BLACK;
		Color front = game.isWhiteTurn()? Color.BLACK : Color.WHITE;
		g.setColor(back);
		g.fillRect(W / 2 - width / 2 - 5, OFFSET_Y + 8 * BOX_SIZE + 2,
				width + 10, 15);
		g.setColor(front);
		g.drawString(msg, W / 2 - width / 2, OFFSET_Y + 8 * BOX_SIZE + 2 + 11);
		
		// Draw a game over sign
		if (isGameOver) {
			g.setFont(new Font("Arial", Font.BOLD, 20));
			msg = "Game Over!";
			width = g.getFontMetrics().stringWidth(msg);
			g.setColor(new Color(240, 240, 255));
			g.fillRoundRect(W / 2 - width / 2 - 5,
					OFFSET_Y + BOX_SIZE * 4 - 16,
					width + 10, 30, 10, 10);
			g.setColor(Color.RED);
			g.drawString(msg, W / 2 - width / 2, OFFSET_Y + BOX_SIZE * 4 + 7);
		}
	}
	

	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}

	public Color getLightTile() {
		return lightTile;
	}

	public void setLightTile(Color lightTile) {
		this.lightTile = (lightTile == null)? Color.WHITE : lightTile;
	}

	public Color getDarkTile() {
		return darkTile;
	}

	public void setDarkTile(Color darkTile) {
		this.darkTile = (darkTile == null)? Color.BLACK : darkTile;
	}

	
	private void handleClick(int x, int y) throws Exception {
		
		if (game.isWhiteTurn()) {
		
			if (isGameOver) {
				return;
			}
			// Determine what square (if any) was selected
			final int W = getWidth(), H = getHeight();
			final int DIM = W < H ? W : H, BOX_SIZE = (DIM - 2 * PADDING) / 8;
			final int OFFSET_X = (W - BOX_SIZE * 8) / 2;
			final int OFFSET_Y = (H - BOX_SIZE * 8) / 2;
			x = (x - OFFSET_X) / BOX_SIZE;
			y = (y - OFFSET_Y) / BOX_SIZE;
			Point sel = new Point(x, y);
		
			// Determine if a move should be attempted
			try {
				if (Board.isValidPoint(sel) && Board.isValidPoint(selected)) {
					boolean change = game.isWhiteTurn();
					game.playerMove(change ? game.getP1() : game.getP2(), selected.x, selected.y, sel.x, sel.y);

					change = (game.isWhiteTurn() != change);
					this.selected = change ? null : sel;
					// Check if the selection is valid
					if (!change)
						this.selectionValid = isValidSelection(game.getBoard(), game.isWhiteTurn(), selected);
				} else {
					this.selected = sel;
					this.selectionValid = isValidSelection(game.getBoard(), game.isWhiteTurn(), selected);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			update();
		}
	}
	

	private boolean isValidSelection(Board b, boolean isP1Turn, Point selected) throws Exception {

		// Trivial cases
		Spot s = b.getBox(selected.x, selected.y);
		if (s.isEmpty()) 
			// no checker here
			return false;
		
		 else if(game.isWhiteTurn() != s.getPiece().isWhite())
			// wrong checker
			return false;
	
		return true;
	}

	private class ClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			// Get the new mouse coordinates and handle the click
			Point m = BoardView.this.getMousePosition();
			if (m != null) {
				try {
					handleClick(m.x, m.y);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
			}
		}
		
}

	
