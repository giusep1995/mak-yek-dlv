
package view;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;



public class OptionPanel extends JPanel {

	private static final long serialVersionUID = -4763875452164030755L;

	
	private Window window;
	
	
	private JButton restartBtn;
	
	
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
