package Game;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;

public class Controller implements KeyListener,MouseListener,MouseMotionListener,MouseWheelListener{
	private JPanel gamePanel;
	static boolean mousePressed = false;
	static Point mousePosition = new Point(0,0);
	public void setGamePanel(JPanel panelRef) {
		gamePanel = panelRef;
		gamePanel.addKeyListener(this);
		gamePanel.addMouseListener(this);
		gamePanel.addMouseMotionListener(this);
		gamePanel.addMouseWheelListener(this);
	}
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseMoved(MouseEvent e) {
		mousePosition.x = (int) e.getPoint().getX();
		mousePosition.y = (int) e.getPoint().getY();
		// TODO Auto-generated method stub
		
	}

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		mousePressed = true;
		mousePosition.x = (int) e.getPoint().getX();
		mousePosition.y = (int) e.getPoint().getY();
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		mousePressed = false;
	}

	public void keyPressed(KeyEvent e) {

	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	public static void checkKeys(){
		//check if mouse was pressed
		if(mousePressed){
			//Point temp = MouseEvent.getPoint();
			if(mousePressed){
				GamePanel.player.destination.x=(int)GamePanel.player.xpos+mousePosition.x-((ApplicationUI.windowWidth/2)-16);
				GamePanel.player.destination.y=(int)GamePanel.player.ypos+mousePosition.y-((ApplicationUI.windowHeight/2)-16);
			}
			//System.out.println("destination: "+GamePanel.player.destination.x+","+GamePanel.player.destination.y);
		}
	}



}
