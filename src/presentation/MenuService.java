package presentation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import business.ImageProcessor;
import business.NoiseAdditionFilter;

public class MenuService {
	
	
	
	private static MenuService service = new MenuService();
	
	private JFileChooser fileChooser ;
	
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
	
	public void grayThresholding(MainFrame frame){
		ImagePanel pane = frame.getPanel();
		ImageProcessor imgP = pane.getImageProcessor();
		BufferedImage colorImg = imgP.getBufferedImage();
		BufferedImage bgrImg = imgP.backgroundRemove(colorImg, 50);
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
		if(cmd.equals("Gray thresholding")){
			grayThresholding(frame);
		}
		
	}
}
