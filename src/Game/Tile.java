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
	Color flagColor = new Color(200,0,0,100);
	int collisionType = 0;//0 means no collision, 1 means collision
	Rectangle collisionBox;
	public Tile(int x, int y, int xID, int yID){
		xpos = x;
		ypos = y;
		artX = xID;
		artY = yID;
		collisionBox = new Rectangle(x,y,32,32);
		if(xID==6&&yID==0){//grass
			collisionType = 0;
		}
		if(xID==3&&yID==0){//water
			collisionType = 1;
		}
		
	}
	public void Draw(Graphics2D g){
		g.drawImage(GamePanel.tiles[artX][artY],((ApplicationUI.windowWidth/2)-16)+xpos-(int)GamePanel.player.xpos,((ApplicationUI.windowHeight/2)-16)+ypos-(int)GamePanel.player.ypos,32,32,null);
		g.setColor(flagColor);
		if(flagged){
			g.fillRect(((ApplicationUI.windowWidth/2)-16)+xpos-(int)GamePanel.player.xpos,((ApplicationUI.windowHeight/2)-16)+ypos-(int)GamePanel.player.ypos, collisionBox.width, collisionBox.height);
			//flagged = false;
		}
	}
}
