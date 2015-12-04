package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	static final int WIDTH = 800;
	static final int HEIGHT = 600;
	
	private ImagePanel panel;
	//private JScrollPane imageSp;
	private StatePanel statePanel;
	
	
	
	public ImagePanel getPanel() {
		return panel;
	}

	public void setPanel(ImagePanel panel) {
		this.panel = panel;
	}

	public StatePanel getStatePanel() {
		return statePanel;
	}

	public void setStatePanel(StatePanel statePanel) {
		this.statePanel = statePanel;
	}

	MenuService service = MenuService.getInstance();
	
	ActionListener menuListener = new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			service.menuAction(MainFrame.this, e.getActionCommand());
		}
		
	};
	
	public MainFrame(){
		
		super("Image Process");
		this.setSize(800, 600);
		this.setLocation(300, 50);
		
		this.createMenuBar();
		
		panel = new ImagePanel();
		//panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		statePanel = new StatePanel();
		
		
		this.add(panel,BorderLayout.CENTER);
		this.add(statePanel,BorderLayout.SOUTH);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void createMenuBar(){
		
		JMenuBar menuBar = new JMenuBar();
		// string for menu
		String[] menuArr = {"File","View","Image","BGRemove","Neuro","Help"};
		// string for menu item
		String[][] menuItemArr = {
				{"Open","Save","Save as","-","Exit"},
				{"Zoom out","Zoom in"},
				{"Undo","Redo","-","Grayscale","Resize","Gaussian noise","Extra line"},
				{"Gray thresholding","Color thresholding"},
				{"KNN setting","KNN training","KNN testing"},
				{"Manual","About"}
		};
		// create menu
		for(int i=0;i< menuArr.length;i++){
			
			JMenu menu = new JMenu(menuArr[i]);
			for(int j=0;j< menuItemArr[i].length;j++){
				// seperator
				if(menuItemArr[i][j].equals("-")){
					menu.addSeparator();
				}
				else{
					JMenuItem menuItem = new JMenuItem(menuItemArr[i][j]);
					menuItem.addActionListener(menuListener);
					menu.add(menuItem);
				}
			}
			menuBar.add(menu);
		}
		
		this.setJMenuBar(menuBar);
		
	}
}

	
	
	
	                    

	
	

