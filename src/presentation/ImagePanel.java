package presentation;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import business.ImageProcessor;

public class ImagePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
    private Image image;
    private ImageProcessor imageProcesser;
    
    
    public void setImage(String filePath) {
        // read image file
    	
    	imageProcesser = new ImageProcessor(filePath);
    	this.image = imageProcesser.getImage();
    }
    
    public void resetImage(Image newImage){
    	
    	this.image = newImage;
    }
    
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image == null)
            return;
        
        Graphics2D g2d = (Graphics2D) g;
        int x = (this.getWidth() - image.getWidth(null)) / 2;
        int y = (this.getHeight() - image.getHeight(null)) / 2;
        g2d.drawImage(image, x, y, null);
    }
}
