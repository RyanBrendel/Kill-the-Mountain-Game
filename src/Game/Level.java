package Game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Level {
	Tile[][] tileMap;
	Tile[][] hardMap;
	Tile[] baseTiles;	//for initalizing maps
	int width=30;
	int height=30;
	String name;
	Random levelGenerationRandom;
	int seed;
	LevelMap map;
	ArrayList<ArrayList<Tile>> lakes = new ArrayList<ArrayList<Tile>>();

	/* Get/Set methods*/
	public int getSeed()
	{
		return this.seed;
	}
	public void setSeed(int s)
	{
		this.seed = s;
	}
	public String getLevelName()
	{
		return this.name;
	}
	public void setLevelName(String str)
	{
		this.name = str;
	}



	public Level(String Name){

		//Pre-determined map generation for specified zones or debugging purposes.
		name = Name;
		name = "Test";
		if(name.equals("Test")){
			seed = 135;
			width = 100;
			height = 100;
		}
		else if(name.equals("Overworld"))	//player hub; Links to each of the dungeons' starting locations and serves as safe point.
		{
			seed = -1;
			width = 50;
			height = 50;
		}
		else if(name.equals("Boss Room A"))	//zone where player encounters the first boss
		{
			seed = -2;
			width = 20;
			height = 10;
		}
		else if(name.equals("Boss Room B"))	//zone where player encounters the second boss
		{
			seed = -3;
			width = 20;
			height = 10;
		}
		else if(name.equals("Boss Room C")) //zone where player encounters the third boss
		{
			seed = -4;
			width = 15;
			height = 40;
		}
		else if(name.equals("Safe Zone")) 	//randomly occuring safe zone within dungeons where the player character can rest.
		{
			seed = -5;
			width = 40;
			height = 25;
		}
		
		generateMap();
	}
	public void generateMap(){
		tileMap = new Tile[width][height];
		levelGenerationRandom = new Random(seed);
		//initialize the tile map
		for(int x = 0; x<width;x++){
			for(int y = 0; y<height; y++){
				//tileMap[x][y]=new Tile(x,y,6,0);//everything is grass
				tileMap[x][y]=new Tile(x,y,3,0);//everything is water
			}
		}		
		
		if(seed < 0)
		{
			buildRoom(-1 - seed);	//translate seed to a key for room ID's, build them
			tileMap = hardMap;
		}
		
		//randomly generates terrain for positive seeds
		else
		{
			//create islands
			int islandCount = randomNumber(2,15);
			for(int i = 0; i<islandCount; i++){
				generateLakeOrIsland(randomNumber(220,2550), false);
			}
			addTallGrass(tileMap);
			//create lakes
			int lakeCount = randomNumber(2,3);
			for(int i = 0; i<lakeCount; i++){
				generateLakeOrIsland(randomNumber(120,500), true);
			}
		}

		//create the map
		map = new LevelMap(tileMap);
	}
	public void generateLakeOrIsland(int lakeSize, boolean isLake){
		Point createdTileID;
		Point replacedTileID;
		if(isLake){
			createdTileID=new Point(3,0);
			replacedTileID = new Point(6,0);
		}
		else{
			createdTileID = new Point(6,0);
			replacedTileID = new Point(3,0);
		}
		//add randomly placed water
		Point lakePosition = new Point(randomNumber(0,width-1),randomNumber(0,height-1));
		ArrayList<Tile> lakeEdges = new ArrayList<Tile>();
		lakeEdges.add(tileMap[lakePosition.x][lakePosition.y]);
		tileMap[lakePosition.x][lakePosition.y]=new Tile(lakePosition.x,lakePosition.y,createdTileID.x,createdTileID.y);
		int count = 0;
		//generate water
		while(count<lakeSize){
			//pick a random side of this tile
			boolean changed = false;
			while(!changed){
				Tile randTile = lakeEdges.get(randomNumber(0,lakeEdges.size()-1));
				Point randEdge = new Point(randTile.xpos/32,randTile.ypos/32);
				lakePosition = new Point(randEdge.x,randEdge.y);
				int side = randomNumber(1,4);
				//top
				if(side==1&&lakePosition.y-1>=0){
					lakePosition.y-=1;
					changed = true;
				}
				//bottom
				if(side==2&&lakePosition.y+1<height){
					lakePosition.y+=1;
					changed = true;
				}
				//left
				if(side==3&&lakePosition.x-1>=0){
					lakePosition.x-=1;
					changed = true;
				}
				//right
				if(side==4&&lakePosition.x+1<width){
					lakePosition.x+=1;
					changed = true;
				}
			}
			//if the tile chosen is not already a water tile
			if(!(tileMap[lakePosition.x][lakePosition.y].artX==createdTileID.x&&tileMap[lakePosition.x][lakePosition.y].artY==createdTileID.y)){
				tileMap[lakePosition.x][lakePosition.y]=new Tile(lakePosition.x,lakePosition.y,createdTileID.x,createdTileID.y);//random bits of water
				count++;
				//get a list of all the water tiles which are adjacent to this one
				ArrayList<Tile> adjacentTiles = tilesAdjacentToPosition(lakePosition.x,lakePosition.y,createdTileID.x,createdTileID.y);
				//if there is a non water tile adjacent to this
				if(adjacentTiles.size()!=4){
					lakeEdges.add(tileMap[lakePosition.x][lakePosition.y]);
				}
				//remove any water tiles from the list of edge tiles which are no longer on an edge
				for(int i = 0; i<adjacentTiles.size();i++){
					//if(tilesAdjacentToPosition(adjacentTiles.get(i).xpos/32,adjacentTiles.get(i).ypos/32,3,0).size()==4){
					if(adjacentTiles.size()==4){
						lakeEdges.remove(adjacentTiles.get(i));
					}
				}
			}
			else{
				if(randomNumber(1,10)==1){
					count++;
				}
			}
		}
		//generate shore
		for(int i = 0; i<lakeEdges.size();i++){
			//tileMap[lakeEdges.get(i).x][lakeEdges.get(i).y].flagged=true;
			//tileMap[lakeEdges.get(i).x][lakeEdges.get(i).y].flagColor=new Color(200,0,0,100);
			//list of all the adjacent grass tiles to this water tile
			ArrayList<Tile> adjacentNonWaterTiles = tilesAdjacentToPosition(lakeEdges.get(i).xpos/32,lakeEdges.get(i).ypos/32,replacedTileID.x,replacedTileID.y);
			//make all adjacent grass into dirt
			for(int j = 0; j<adjacentNonWaterTiles.size();j++){
				int x = adjacentNonWaterTiles.get(j).xpos;
				int y = adjacentNonWaterTiles.get(j).ypos;
				tileMap[x/32][y/32].flagged=true;
				tileMap[x/32][y/32]= new Tile(x/32,y/32,7,0);
			}
		}
	}

	private void buildRoom(int ID)
	{
		hardMap = new Tile[width][height];
		switch(ID)
		{
		//Overworld
		case 0: 
			//overworld = new Tile[width][height];
			for(int x = 0; x<width;x++){
				for(int y = 0; y<height; y++){
					hardMap[x][y]=new Tile(x,y,7,2); //base tile is heavy dirt
				}
			}
			for(int x = 0; x < width; x++)			//wall in with stone wall
			{
				hardMap[x][0] = new Tile(x,0,1,0);
				hardMap[x][height - 1] = new Tile(x, height - 1, 1, 0);
			}
			for(int y = 0; y < height - 1; y++)
			{
				hardMap[0][y] = new Tile(0,y,1,1);
				hardMap[width - 1][y] = new Tile(width - 1,y,1,1);
			}
			break;

			//boss room a
		case 1:
			//bossA = new Tile[width][height];
			for(int x = 0; x<width;x++){
				for(int y = 0; y<height; y++){
					hardMap[x][y]=new Tile(x,y,6,0); //base tile grass
				}
			}
			addTallGrass(hardMap);
			for(int x = 0; x < width; x++)			//wall in with tree cover
			{
				hardMap[x][0] = new Tile(x,0,8,1);
				hardMap[x][height - 1] = new Tile(x, height - 1, 8, 1);
			}
			for(int y = 0; y < height - 1; y++)
			{
				hardMap[0][y] = new Tile(0,y,8,2);
				hardMap[width - 1][y] = new Tile(width - 1,y,8,2);
			}
			
			break;

			//boss room b
		case 2:
			//bossB = new Tile[width][height];
			for(int x = 0; x<width;x++){
				for(int y = 0; y<height; y++){
					hardMap[x][y]=new Tile(x,y,0,1); //base tile is heavy dirt
				}
			}
			for(int x = 0; x < width; x++)			//wall in with stone wall
			{
				hardMap[x][0] = new Tile(x,0,1,0);
				hardMap[x][height - 1] = new Tile(x, height - 1, 1, 0);
			}
			for(int y = 0; y < height - 1; y++)
			{
				hardMap[0][y] = new Tile(0,y,1,1);
				hardMap[width - 1][y] = new Tile(width - 1,y,1,1);
			}
			break;

			//boss room c
		case 3:
			//bossC = new Tile[width][height];
			for(int x = 0; x<width;x++){
				for(int y = 0; y<height; y++){
					hardMap[x][y]=new Tile(x,y,0,1); //base tile is heavy dirt
				}
			}
			for(int x = 0; x < width; x++)
			{
				hardMap[x][0] = new Tile(x,0,1,0);
				hardMap[x][height - 1] = new Tile(x, height - 1, 1, 0);
			}
			for(int y = 0; y < height - 1; y++)
			{
				hardMap[0][y] = new Tile(0,y,1,1);
				hardMap[width - 1][y] = new Tile(width - 1,y,1,1);
			}
			break;

			//safe zone
		case 4:
			//safeRoom = new Tile[width][height];
			for(int x = 0; x<width;x++){
				for(int y = 0; y<height; y++){
					hardMap[x][y]=new Tile(x,y,0,1); //base tile is heavy dirt
				}
			}
			for(int x = 0; x < width; x++)
			{
				hardMap[x][0] = new Tile(x,0,1,0);
				hardMap[x][height - 1] = new Tile(x, height - 1, 1, 0);
			}
			for(int y = 0; y < height - 1; y++)
			{
				hardMap[0][y] = new Tile(0,y,1,1);
				hardMap[width - 1][y] = new Tile(width - 1,y,1,1);
			}
			break;		
		}
	}
	
	private Tile[][] addTallGrass(Tile[][] map)
	{
		for(int x = 0; x < width; x++)
			for(int y = 0; y < height; y++)
				if(map[x][y].getArtX() == 6 && map[x][y].getArtY() == 0)
					if(levelGenerationRandom.nextFloat() < 0.1)
						map[x][y].setArtY(1);
		return map;
	}
	


	/*
	 * Used to determine how many of the specified tile type is adjacent to a position
	 * 
	 * @param xpos - the x position of the tile to check for adjacent tiles
	 * @param ypos - the y position of the tile to check for adjacent tiles
	 * @param xID - the artX of the tile to be checked for
	 * @param yID - the artY of the tile to be checked for
	 * 
	 * @return The number of tiles of the type specified that are adjacent to this
	 */
	public ArrayList<Tile> tilesAdjacentToPosition(int xpos, int ypos, int xID, int yID){
		ArrayList<Tile> adjacentTiles = new ArrayList<Tile>();
		//top
		if(ypos-1>=0){
			if(tileMap[xpos][ypos-1].artX==xID&&tileMap[xpos][ypos-1].artY==yID){
				adjacentTiles.add(tileMap[xpos][ypos-1]);
			}
		}
		//bottom
		if(ypos+1<height){
			if(tileMap[xpos][ypos+1].artX==xID&&tileMap[xpos][ypos+1].artY==yID){
				adjacentTiles.add(tileMap[xpos][ypos+1]);
			}
		}
		//left
		if(xpos-1>=0){
			if(tileMap[xpos-1][ypos].artX==xID&&tileMap[xpos-1][ypos].artY==yID){
				adjacentTiles.add(tileMap[xpos-1][ypos]);
			}
		}
		//right
		if(xpos+1<width){
			if(tileMap[xpos+1][ypos].artX==xID&&tileMap[xpos+1][ypos].artY==yID){
				adjacentTiles.add(tileMap[xpos+1][ypos]);
			}
		}

		return adjacentTiles;
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
		int viewDistanceX = ((ApplicationUI.windowWidth/32)/2)+2;
		int viewDistanceY = ((ApplicationUI.windowHeight/32)/2)+2;
		for(int x = (int)(GamePanel.player.xpos/32)-(viewDistanceX); x<(int)(GamePanel.player.xpos/32)+(viewDistanceX);x++){
			for(int y = (int)(GamePanel.player.ypos/32)-(viewDistanceY); y<(int)(GamePanel.player.ypos/32)+(viewDistanceY);y++){
				if(x>=0&&y>=0&&x<width&&y<height)
					tileMap[x][y].Draw(g);
			}
		}
		GamePanel.player.Draw(g);
	}
}
