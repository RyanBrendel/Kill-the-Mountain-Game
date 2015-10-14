package Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ApplicationUI extends JFrame{
	private static final long serialVersionUID = 1L;
	Controller ctrl;
	public static int windowWidth = 800;
	public static int windowHeight = 600;
	public ApplicationUI(){
		Container pane = getContentPane();
		pane.setLayout(new BorderLayout());
		JPanel drawPanel = new GamePanel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		drawPanel.setBackground(Color.BLACK);
		ctrl = new Controller();
		this.addKeyListener(ctrl);
		ctrl.setGamePanel(drawPanel);
		this.setFocusable(true);
		pane.add(drawPanel);

		Thread gameThread = new Thread(){
			public void run(){
				gameLoop();
			}
		};
		gameThread.start();
	}
	/*
	 * Used to set whether the game should be displayed in full screen or windowed mode
	 * 
	 * @param fullscreen	a boolean used to decide if the game is full screen
	 */
	public void setFullScreen(boolean fullscreen){
		if(fullscreen==true){
			this.setExtendedState(Frame.MAXIMIZED_BOTH);  
			this.setUndecorated(true);  
		}
		else{
			this.setExtendedState(Frame.NORMAL);  
			this.setUndecorated(false);  
		}
	}
	public static void main(String[] args) {
		ApplicationUI f = new ApplicationUI();
		f.setSize(windowWidth,windowHeight);
		f.setVisible(true);
		
	}
	/*
	 * The main loop of the game. This will call all of the update methods and repaint the game at a fixed rate
	 */
	public void gameLoop(){
		while(true){
			//repaint the graphics of the game
			repaint();
		}
	}

}
