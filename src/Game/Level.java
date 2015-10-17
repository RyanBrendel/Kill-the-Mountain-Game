package Game;

import java.awt.Graphics2D;
import java.util.Random;

public class Level {
	Tile[][] tileMap;
	int width;
	int height;
	String name;
	Random levelGenerationRandom;
	int seed;
	public Level(String Name, int w, int h){
		width = w;
		height = h;
		tileMap = new Tile[width][height];
		name = Name;
		if(name=="Test"){
			seed = 12;
		}
		levelGenerationRandom = new Random(seed);
		generateMap();
	}
	public void generateMap(){
		//initialize the map
		for(int x = 0; x<width;x++){
			for(int y = 0; y<height; y++){
				tileMap[x][y]=new Tile(x*32,y*32,0,0);//everything is grass

			}
		}
		//add randomly placed water
		for(int x = 0; x<width;x++){
			for(int y = 0; y<height; y++){
				if(randomNumber(1,100)<=1||(tileNearPosition(x,y,2,0)&&randomNumber(1,100)<=60)){
					tileMap[x][y]=new Tile(x*32,y*32,2,0);//random bits of water
				}
			}
		}
	}
	/*
	 * Used to determine if there is a tile of the type passed which is near a specified position
	 * 
	 * @param xpos - the x position of the tile to check for adjacent tiles
	 * @param ypos - the y position of the tile to check for adjacent tiles
	 * @param xID - the artX of the tile to be checked for
	 * @param yID - the artY of the tile to be checked for
	 * 
	 * @return true if there is a tile of the type specified adjacent to this
	 */
	public boolean tileNearPosition(int xpos, int ypos, int xID, int yID){
		//top
		if(ypos-1>=0){
			if(tileMap[xpos][ypos-1].artX==xID&&tileMap[xpos][ypos-1].artY==yID){
				return true;
			}
		}
		//bottom
		if(ypos+1<height){
			if(tileMap[xpos][ypos+1].artX==xID&&tileMap[xpos][ypos+1].artY==yID){
				return true;
			}
		}
		//left
		if(xpos-1>=0){
			if(tileMap[xpos-1][ypos].artX==xID&&tileMap[xpos-1][ypos].artY==yID){
				return true;
			}
		}
		//right
		if(xpos+1<width){
			if(tileMap[xpos+1][ypos].artX==xID&&tileMap[xpos+1][ypos].artY==yID){
				return true;
			}
		}

		return false;
	}
	public int randomNumber(int min, int max){
		if(max==min){
			return max;
		}
		int randNum = levelGenerationRandom.nextInt((max-min)+1) + min;
		return randNum;
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
