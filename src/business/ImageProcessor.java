package business;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;


public class ImageProcessor {
	
	
	private BufferedImage bufferedImage;
	private int imageWidth;
	private int imageHeight;
	private LinkedList<BufferedImage> undoList = new LinkedList<BufferedImage>();
	private LinkedList<BufferedImage> redoList = new LinkedList<BufferedImage>();
	
	// setter and getter

	
	public int getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

	public LinkedList<BufferedImage> getUndoList() {
		return undoList;
	}

	public void setUndoList(LinkedList<BufferedImage> undoList) {
		this.undoList = undoList;
	}

	public LinkedList<BufferedImage> getRedoList() {
		return redoList;
	}

	public void setRedoList(LinkedList<BufferedImage> redoList) {
		this.redoList = redoList;
	}

	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	public void setBufferedImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}

	public ImageProcessor(){
		
	}
	
	// open image file
	public ImageProcessor(String filePath){
		 try {
	            bufferedImage = ImageIO.read(new File(filePath));
	            undoList.add(bufferedImage);
	     } catch (IOException e) {
	            e.printStackTrace();
	     }
	}
	
	// tranfer to greyscale image
	public void imageGreyscale(){
		
		BufferedImage result = new BufferedImage(bufferedImage.getWidth(),bufferedImage.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
		Graphics g = result.getGraphics();
		g.drawImage(bufferedImage, 0, 0, null);
		g.dispose();
		bufferedImage = result;
	}
}
