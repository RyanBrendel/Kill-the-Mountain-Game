package Game;

import java.awt.Graphics2D;

public class Level {
	Tile[][] tileMap;
	int width;
	int height;
	String name;
	public Level(String Name, int w, int h){
		width = w;
		height = h;
		tileMap = new Tile[width][height];
		name = Name;
		generateMap();
	}
	public void generateMap(){
		for(int x = 0; x<width;x++){
			for(int y = 0; y<height; y++){
				tileMap[x][y]=new Tile(x*32,y*32,0,0);//everything is grass
			}
		}
	}
	public void update(){
		GamePanel.player.update();
	}
	public void Draw(Graphics2D g){
		for(int x = 0; x<width;x++){
			for(int y = 0; y<height; y++){
				tileMap[x][y].Draw(g);
			}
		}
		GamePanel.player.Draw(g);
	}
}
