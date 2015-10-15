package Game;

import java.awt.Graphics2D;

public class Tile {
	int xpos;
	int ypos;
	int artX;
	int artY;
	public Tile(int x, int y, int xID, int yID){
		xpos = x;
		ypos = y;
		artX = xID;
		artY = yID;
	}
	public void Draw(Graphics2D g){
		g.drawImage(GamePanel.tiles[artX][artY],xpos,ypos,32,32,null);
	}
}
