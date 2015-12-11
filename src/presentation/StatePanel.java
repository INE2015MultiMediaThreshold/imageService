package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class StatePanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextArea stateText;
	
	private BufferedImage stateImg;

	public JTextArea getStateText() {
		return stateText;
	}

	public void setStateText(JTextArea stateText) {
		this.stateText = stateText;
	}

	public BufferedImage getStateImg() {
		return stateImg;
	}

	public void setStateImg(BufferedImage stateImg) {
		this.stateImg = stateImg;
	}

	public StatePanel(){
		Dimension dim = getPreferredSize();
		dim.height = 30;
		setPreferredSize(dim);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		//this.setBackground(Color.WHITE);
		stateText = new JTextArea();
		add(stateText,BorderLayout.CENTER);
	}
	
	public void showInfo(String imageName){
		int imgW = stateImg.getWidth();
		int imgH = stateImg.getHeight();
		String imgInfo = imageName + "  :  "+imgW+" X "+imgH;
		
		stateText.setText(imgInfo);;
		
	}
	
	

}
