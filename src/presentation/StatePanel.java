package presentation;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class StatePanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StatePanel(){
		Dimension dim = getPreferredSize();
		dim.height = 80;
		setPreferredSize(dim);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		//this.setBackground(Color.WHITE);
	}

}
