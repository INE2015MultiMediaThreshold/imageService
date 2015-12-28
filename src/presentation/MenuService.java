package presentation;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import business.ImageProcessor;
import business.NoiseAdditionFilter;

public class MenuService {
	
	
	
	private static MenuService service = new MenuService();
	
	private JFileChooser fileChooser ;
	
	private double range = 0.1;
	
	private File currentDirectory = null;
	
	private List<File> currentFiles = null;
	
	private File currentFile = null;
	
	//private JFileChooser fileChooser = new JFileChooser();
	
	private MenuService(){
		
	}
	
	public void open(MainFrame frame){
		fileChooser = new JFileChooser();
		
		if(fileChooser.showOpenDialog(frame)==JFileChooser.APPROVE_OPTION){
			this.currentFile = fileChooser.getSelectedFile();
			String imageName = currentFile.getName();
            String imagePath = fileChooser.getSelectedFile().getPath();
            //System.out.println(imageName);
            //frame.getPanel().setImage(name);
            StatePanel stateP = frame.getStatePanel();
            ImagePanel imgP = frame.getPanel();
            imgP.setImage(imagePath);
            stateP.setStateImg(imgP.getImageProcessor().getBufferedImage());
            stateP.showInfo(imageName);
            imgP.repaint();
            stateP.repaint();
		}
	}
	
	public void saveAs(MainFrame frame){
		fileChooser = new JFileChooser();
		int userSelection = fileChooser.showSaveDialog(null);
		 
		if (userSelection == JFileChooser.APPROVE_OPTION) {
		    File fileToSave = fileChooser.getSelectedFile();
		    //System.out.println("Save as file: " + fileToSave.getAbsolutePath());
		    try{
		    ImageIO.write((BufferedImage)frame.getPanel().getImage(),"jpg", fileToSave);
		    }catch(IOException e){
		    	e.printStackTrace();
		    }
		    //System.out.println("Image saved!");
		}
		
	}
	
	public void zoom(MainFrame frame,boolean isEnlarge){
		double enLargeRange = isEnlarge ? 1 + range : 1 - range;
		ImagePanel imgP = frame.getPanel();
		Image sourceImg = imgP.getImage();
		Image scaleImg = sourceImg.getScaledInstance((int)(sourceImg.getWidth(frame)*enLargeRange), (int)(sourceImg.getHeight(frame)*enLargeRange),  Image.SCALE_SMOOTH);
		imgP.resetImage(scaleImg);
		imgP.repaint();
		
	}
	
	public void showSlider(MainFrame frame,String cmd){
		
		 JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 255, 50);
		 
	     slider.setPaintTicks(true);
	     slider.setPaintLabels(true);
	     slider.setMinorTickSpacing(1);
	     slider.setMajorTickSpacing(10);
	     
	     slider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				JSlider jslider = (JSlider)e.getSource();
				//System.out.println(jslider.getValue());
				int sValue = jslider.getValue();
				grayThresholding(frame,sValue);
			}
	     });
	     
	     frame.add(slider,BorderLayout.NORTH);
	     frame.setVisible(true);
		
	}
	
	
	
	public void toGreyscale(MainFrame frame){
		ImagePanel pane = frame.getPanel();
		ImageProcessor imgP = pane.getImageProcessor();
		//BufferedImage colorImg = ;
		BufferedImage greyImg = imgP.colorToGrey(imgP.getBufferedImage());
		imgP.setBufferedImage(greyImg);
		pane.setImage(greyImg);
		pane.repaint();
		
		
	}
	
	public void extraLine(MainFrame frame){
		ImagePanel pane = frame.getPanel();
		ImageProcessor imgP = pane.getImageProcessor();
		BufferedImage sourceImg = imgP.getBufferedImage();
	    BufferedImage extraLineImg = imgP.addExtraLine(sourceImg);
	    imgP.setBufferedImage(extraLineImg);
		pane.setImage(extraLineImg);
		pane.repaint();
		
	}
	
	public void addGaussianNoise(MainFrame frame){
		ImagePanel pane = frame.getPanel();
		ImageProcessor imgP = pane.getImageProcessor();
		NoiseAdditionFilter nFilter = new NoiseAdditionFilter();
		BufferedImage gaussianImg = nFilter.filter(imgP.getBufferedImage(),null);
		imgP.setBufferedImage(gaussianImg);
		pane.setImage(imgP.getBufferedImage());
		pane.repaint();
	}
	
	public void grayThresholding(MainFrame frame,int tValue){
		ImagePanel pane = frame.getPanel();
		ImageProcessor imgP = pane.getImageProcessor();
		BufferedImage orignalImg = imgP.getBufferedImage();
		BufferedImage colorImg = ImageProcessor.deepCopy(orignalImg);
		//frame.showSlider();
		BufferedImage bgrImg = imgP.backgroundRemove(colorImg, tValue);
		pane.setImage(bgrImg);
		pane.repaint();
		
	}
	
	public void bestThresholding(MainFrame frame){
		ImagePanel pane = frame.getPanel();
		ImageProcessor imgP = pane.getImageProcessor();
		BufferedImage orignalImg = imgP.getBufferedImage();
		int w = orignalImg.getWidth();
		int h = orignalImg.getHeight();
		BufferedImage colorImg = ImageProcessor.deepCopy(orignalImg);
		BufferedImage greyImg = imgP.colorToGrey(colorImg);
		int[] greyPixl = new int[w*h];
		greyPixl = imgP.imageToArray(greyImg);
		int bestThre = imgP.bestThresh(greyPixl, w, h);
		System.out.println(bestThre);
		BufferedImage bgrImg = imgP.backgroundRemove(orignalImg, bestThre);
		pane.setImage(bgrImg);
		pane.repaint();
		
	}
	
	public void otsuThresholding(MainFrame frame){
		ImagePanel pane = frame.getPanel();
		ImageProcessor imgP = pane.getImageProcessor();
		BufferedImage orignalImg = imgP.getBufferedImage();
		int w = orignalImg.getWidth();
		int h = orignalImg.getHeight();
		BufferedImage colorImg = ImageProcessor.deepCopy(orignalImg);
		BufferedImage greyImg = imgP.colorToGrey(colorImg);
		int[] greyPixl = new int[w*h];
		greyPixl = imgP.imageToArray(greyImg);
		int otsuThre = imgP.otsuThresh(greyPixl, w, h);
		System.out.println(otsuThre);
		BufferedImage bgrImg = imgP.backgroundRemove(orignalImg, otsuThre);
		pane.setImage(bgrImg);
		pane.repaint();
		
	}
	
	
	public static MenuService getInstance(){
		if(service == null){
			service = new MenuService();
		}
		return service;
	}

	public void menuAction(MainFrame frame, String cmd){
		// open
		if(cmd.equals("Open") ){
			open(frame);
		}
		
		// save as
		if(cmd.equals("Save as")){
			saveAs(frame);
		}
		
		// exit
		if(cmd.equals("Exit")){
			System.exit(0);
		}
		
		// zoom
		if(cmd.equals("Zoom out")){
			zoom(frame,false);
		}
		
		if(cmd.equals("Zoom in")){
			zoom(frame,true);
		}
		
		// image greyscale
		if(cmd.equals("Grayscale")){
			//System.out.println("cmd Greyscale");
			toGreyscale(frame);
		}
		
		// add Gaussion noise
		if(cmd.equals("Gaussian noise")){
			addGaussianNoise(frame);
		}
		
		if(cmd.equals("Extra line")){
			extraLine(frame);
		}
		
		// background remove by gray scale image thresholding
		if(cmd.equals("Manual thresholding")){
			//grayThresholding(frame);
			showSlider(frame,cmd);
		}
		
		// best thresholding
		if(cmd.equals("Best thresholding")){
			bestThresholding(frame);
		}
		
		// Otsu thresholding
		if(cmd.equals("Otsu thresholding")){
			otsuThresholding(frame);
		}
		
	}
}
