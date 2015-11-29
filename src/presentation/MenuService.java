package presentation;

import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;

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
	
	public static MenuService getInstance(){
		if(service == null){
			service = new MenuService();
		}
		return service;
	}

	public void menuAction(MainFrame frame, String cmd){
		
		if(cmd.equals("Open") ){
			open(frame);
		}
		
		if(cmd.equals("Exit")){
			System.exit(0);
		}
	}
}
