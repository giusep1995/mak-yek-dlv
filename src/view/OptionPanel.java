/* Name: OptionPanel
 * Author: Devon McGrath
 * Description: This class is a user interface to interact with a checkers
 * game window.
 */

package view;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;




/**
 * The {@code OptionPanel} class provides a user interface component to control
 * options for the game of checkers being played in the window.
 */
public class OptionPanel extends JPanel {

	private static final long serialVersionUID = -4763875452164030755L;

	/** The checkers window to update when an option is changed. */
	private Window window;
	
	/** The button that when clicked, restarts the game. */
	private JButton restartBtn;
	
	/**
	 * Creates a new option panel for the specified checkers window.
	 * 
	 * @param window	the window with the game of checkers to update.
	 */
	public OptionPanel(Window window) {
		super(new GridLayout(0, 1));
		
		this.window = window;
		
		// Initialize the components
		OptionListener ol = new OptionListener();
		this.restartBtn = new JButton("Restart");
		this.restartBtn.addActionListener(ol);
		JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		// Add components to the layout
		top.add(restartBtn);
		this.add(top);
	}

	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}
	
	
	/**
	 * The {@code OptionListener} class responds to the components within the
	 * option panel when they are clicked/updated.
	 */
	private class OptionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			// No window to update
			if (window == null) {
				return;
			}
			
			Object src = e.getSource();
			
			if (src == restartBtn) {
				window.restart();
	
			} 					
			
		}
	}
}
