package Game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel{
	private static final long serialVersionUID = 496501089018037548L;
	public static BufferedImage[][] tiles = FileIO.loadSpriteSheet("/Textures/Tiles.png", 32, 32);
	public static BufferedImage playerImage = FileIO.loadImage("/Textures/Player.png");
	public static ArrayList<Level> levels = new ArrayList<Level>();
	public static int currentLevel = 0;
	public static Player player = new Player(0,0);
	public GamePanel(){
		levels.add(new Level("Test",30,30));
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Draw((Graphics2D)g);
	}
	public void Draw(Graphics2D g){
		levels.get(currentLevel).Draw(g);
		
	}
}
