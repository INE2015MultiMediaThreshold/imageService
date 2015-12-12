package business;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

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
	           
	     } catch (IOException e) {
	            e.printStackTrace();
	     }
	}
	
	
	public static int colorToRGB(int alpha, int red, int green, int blue) {

		int newPixel = 0;
		newPixel += alpha;
		newPixel = newPixel << 8;
		newPixel += red;
		newPixel = newPixel << 8;
		newPixel += green;
		newPixel = newPixel << 8;
		newPixel += blue;

		return newPixel;

	}
	
	// tranfer to grayscale image
	public BufferedImage colorToGrey(BufferedImage sourceImg){
		
		BufferedImage grayImage = sourceImg;
				
				
			
			for (int i = 0; i < sourceImg.getWidth(); i++) {
				for (int j = 0; j < sourceImg.getHeight(); j++) {
					final int color = sourceImg.getRGB(i, j);
					final int r = (color >> 16) & 0xff;
					final int g = (color >> 8) & 0xff;
					final int b = color & 0xff;
					int gray = (int) (0.3 * r + 0.59 * g + 0.11 * b);;
					//System.out.println(i + " : " + j + " " + gray);
					int newPixel = colorToRGB(255, gray, gray, gray);
					grayImage.setRGB(i, j, newPixel);
				}
			}
		return grayImage;
		
	}
	
	public BufferedImage addExtraLine(BufferedImage sourceImg){
		int imageW = sourceImg.getWidth();
		int imageH = sourceImg.getHeight();
		BufferedImage extraLineImg = sourceImg;
				//new BufferedImage(imageW, imageH, sourceImg.getType());
		Random random = new Random();
		int extra = random.nextInt(imageW);
		
		
			for (int j = 0; j < imageH; j++) {
				
				int newPixel = colorToRGB(255, 255, 255, 255);
				extraLineImg.setRGB(extra, j, newPixel);
			}
		
		return extraLineImg;
	}
	
	public static BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
		}
	
	public BufferedImage backgroundRemove(BufferedImage sourceImg, int threhold){
		BufferedImage grayImage = this.colorToGrey(sourceImg);
		for (int i = 0; i < sourceImg.getWidth(); i++) {
			for (int j = 0; j < sourceImg.getHeight(); j++) {
				int gray = grayImage.getRGB(i, j);
				int grayB = gray&0xff;
				if(grayB>threhold){
					int newPixel = colorToRGB(255,255,255,255);
					sourceImg.setRGB(i, j, newPixel);
				}
				}
		}
		
		return sourceImg;
		
	}
	
	
	
	
	
}
