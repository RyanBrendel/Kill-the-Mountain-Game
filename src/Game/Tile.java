package Game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Tile {
	int xpos;
	int ypos;
	int artX;
	int artY;
	boolean flagged = false;
	int collisionType = 0;//0 means no collision, 1 means collision
	Rectangle collisionBox;
	public Tile(int x, int y, int xID, int yID){
		xpos = x;
		ypos = y;
		artX = xID;
		artY = yID;
		collisionBox = new Rectangle(x,y,32,32);
		if(xID==0&&yID==0){//grass
			collisionType = 0;
		}
		if(xID==2&&yID==0){//water
			collisionType = 1;
		}
		
	}
	public void Draw(Graphics2D g){
		g.drawImage(GamePanel.tiles[artX][artY],xpos,ypos,32,32,null);
		g.setColor(new Color(200,0,0));
		if(flagged){
			g.fillRect(collisionBox.x, collisionBox.y, collisionBox.width, collisionBox.height);
			flagged = false;
		}
	}
}
