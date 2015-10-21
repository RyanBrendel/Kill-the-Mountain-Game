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
	
	public int getXpos()
	{
		return this.xpos;
	}
	public int getYpos()
	{
		return this.ypos;
	}
	public void setXpos(int x)
	{
		this.xpos = x;
	}
	public void setYpos(int y)
	{
		this.ypos = y;
	}
	public int getArtX()
	{
		return this.artX;
	}
	public int getArtY()
	{
		return this.artY;
	}
	public void setArtX(int x)
	{
		this.artX = x;
	}
	public void setArtY(int y)
	{
		this.artY = y;
	}
	public boolean isFlagged()
	{
		return this.flagged;
	}
	public void setFlagged(boolean bool)
	{
		this.flagged = bool;
	}
	
	
	public Tile(int x, int y, int xID, int yID){
		xpos = x*32;
		ypos = y*32;
		artX = xID;
		artY = yID;
		collisionBox = new Rectangle(xpos,ypos,32,32);
		if(xID==6&&yID==0){//grass
			collisionType = 0;
		}
		if(xID==3&&yID==0){//water
			collisionType = 1;
		}
		else if(xID == 1 && yID == 0)//stone wall edge
		{
			collisionType = 2;
		}
		else if(xID == 1 && yID == 1)//stone wall fill
		{
			collisionType = 2;
		}
		else if(xID == 8 && yID == 2)//tree canopy
		{
			collisionType = 2;
		}
		else if(xID == 8 && yID == 1)//tree flush top
		{
			collisionType = 2;
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
