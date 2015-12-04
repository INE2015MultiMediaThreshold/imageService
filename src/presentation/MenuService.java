package presentation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;

import business.ImageProcessor;
import business.NoiseAdditionFilter;

public class MenuService {
	
	
	
	private static MenuService service = new MenuService();
	
	private JFileChooser fileChooser = new JFileChooser();
	
	private File currentDirectory = null;
	
	private List<File> currentFiles = null;
	
	private File currentFile = null;
	
	//private JFileChooser fileChooser = new JFileChooser();
	
	private MenuService(){
		
	}
	
	public void open(MainFrame frame){
		
		int result = fileChooser.showOpenDialog(null);
		if(result==JFileChooser.APPROVE_OPTION){
               String name = fileChooser.getSelectedFile().getPath();
               //System.out.println(name);
            frame.getPanel().setImage(name);
            frame.getPanel().repaint();
		}
	}
	
	public void toGreyscale(MainFrame frame){
		ImagePanel pane = frame.getPanel();
		ImageProcessor imgP = pane.getImageProcessor();
		BufferedImage colorImg = imgP.getBufferedImage();
		
		pane.setImage(imgP.colorToGrey(colorImg));
		pane.repaint();
		
		
	}
	
	public void addGaussianNoise(MainFrame frame){
		ImagePanel pane = frame.getPanel();
		ImageProcessor imgP = pane.getImageProcessor();
		NoiseAdditionFilter nFilter = new NoiseAdditionFilter();
		imgP.setBufferedImage(nFilter.filter(imgP.getBufferedImage(), null));
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
		
		// background remove by gray scale image thresholding
		if(cmd.equals("Gray thresholding")){
			grayThresholding(frame);
		}
		
	}
}
