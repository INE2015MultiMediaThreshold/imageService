
package presentation;

import javax.swing.SwingUtilities;

/**
 * @author guo
 *
 */
public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new MainFrame();
			}
		});

	}

}
