package Game;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel{
	private static final long serialVersionUID = 496501089018037548L;
	public GamePanel(){
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Draw((Graphics2D)g);
	}
	public void Draw(Graphics2D g){
		
	}
}
