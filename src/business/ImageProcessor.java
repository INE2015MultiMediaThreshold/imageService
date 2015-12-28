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
		BufferedImage tempImg = deepCopy(sourceImg);
		BufferedImage greyImage = this.colorToGrey(tempImg);
		for (int i = 0; i < sourceImg.getWidth(); i++) {
			for (int j = 0; j < sourceImg.getHeight(); j++) {
				int grey = greyImage.getRGB(i, j);
				int greyB = grey&0xff;
				if(greyB>threhold){
					int newPixel = colorToRGB(255,255,255,255);
					sourceImg.setRGB(i, j, newPixel);
				}
				}
		}
		
		return sourceImg;
		
	}
	
	
	public int[] imageToArray(BufferedImage greyImg){
		
		int h = greyImg.getHeight();
		int w = greyImg.getWidth();
		int length = h*w;
		int[] greyArr = new int[length];
		bufferedImage.getRGB(0, 0, w, h, greyArr, 0, w);
		return greyArr;
		
	}
	
	public int bestThresh(int[] pix, int w, int h)  
	{  
	    int i, j, 
	        thresh,   
	        newthresh,  
	        gmax, gmin;        
	       double[] p = new double[256];  
	  
	    int[][] im = new int[w][h];  
	      
	    for(j = 0; j < h; j++)  
	        for(i = 0; i < w; i++)             
	            im[i][j] = pix[i+j*w]&0xff;  
	              
	       for (i = 0; i < 256; i++)  
	           p[i] = 0;  
	         
	       gmax = 0;  
	       gmin =255;  
	       for (j = 0; j < h; j++)  
	       {  
	           for (i = 0; i < w; i++)  
	           {  
	            int g = im[i][j];  
	               p[g]++;  
	               if(g > gmax) gmax = g;  
	               if(g < gmin) gmin = g;  
	           }  
	       }  
	         
	       thresh = 0;  
	       newthresh = (gmax+gmin)/2;  
	         
	       int meangray1,meangray2;  
	       long p1, p2, s1, s2;  
	       for(i = 0; (thresh!=newthresh)&&(i<100);i++)  
	       {  
	        thresh = newthresh;  
	        p1 = 0; p2 = 0; s1 = 0; s2 = 0;  
	          
	        for(j = gmin; j < thresh;j++)  
	        {  
	            p1 += p[j]*j;  
	            s1 += p[j];               
	        }  
	        meangray1 = (int)(p1/s1);  
	          
	        for(j = thresh+1; j < gmax; j++)  
	        {  
	            p2 += p[j]*j;  
	            s2 += p[j];               
	        }  
	        meangray2 = (int)(p2/s2);  
	        newthresh = (meangray1+meangray2)/2;      
	       }  
	       return newthresh;  
	} 
	
	
	public int otsuThresh(int[] pix, int iw, int ih)  
	{  
	    //ColorModel cm = ColorModel.getRGBdefault();  
	       int wh = iw * ih;  
	       int[][] inIm = new int[iw][ih];   
	  
	       int i, j, t;  
	       int L = 256;  
	       double[] p = new double[L];  
	                         
	       for (j = 0; j < ih; j++)  
	           for (i = 0; i < iw; i++)  
	               inIm[i][j] = pix[i+j*iw]&0xff;                 
	  
	       for (i = 0; i < L; i++)  
	           p[i] = 0;  
	  
	       for (j = 0; j < ih; j++)  
	           for (i = 0; i < iw; i++)  
	               p[inIm[i][j]]++;  
	  
	       for (int m = 0; m < L; m++)  
	           p[m] = p[m] / wh;  
	  
	       double[] sigma = new double[L];  
	       for (t = 0; t < L; t++)  
	       {  
	           double w0 = 0;  
	           for (int m = 0; m < t+1; m++)  
	               w0 += p[m];  
	           double w1 = 1 - w0;  
	  
	           double u0 = 0;  
	           for (int m = 0; m < t + 1; m++)  
	               u0 += m * p[m] / w0;  
	  
	           double u1 = 0;  
	           for (int m = t; m < L; m++)  
	               u1 += m * p[m] / w1;  
	  
	           sigma[t] = w0*w1*(u0-u1)*(u0-u1);  
	       }  
	       double max = 0.0;  
	       int T = 0;  
	       for (i = 0; i < L-1; i++)  
	       {  
	           if (max < sigma[i])  
	           {  
	               max = sigma[i];  
	               T = i;  
	           }  
	       }          
	       return T;                  
	}  
	
	
}
