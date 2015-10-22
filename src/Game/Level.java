package Game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Level {
	Tile[][] tileMap;
	int width=30;
	int height=30;
	String name;
	Random levelGenerationRandom;
	int seed;
	LevelMap map;
	ArrayList<Door> doors = new ArrayList<Door>();
	ArrayList<ArrayList<Tile>> Layers = new ArrayList<ArrayList<Tile>>(); 
	public Level(String Name){

		name = Name;
		if(name.equals("Test")){
			seed = 136;
			width = 100;
			height = 100;
		}
		tileMap = new Tile[width][height];

		generateMap();
		//generateDoors();
		//update all the tiles on the map to show their proper artwork
		//updateTileMapArt();

	}
	public void updateTileMapArt(){
		for(int x = 0; x<width; x++){
			for(int y = 0; y<height; y++){
				if(tileMap[x][y]!=null){
					tileMap[x][y].updateArt();
				}
			}
		}
	}
	public void generateMap(){
		levelGenerationRandom = new Random(seed);
		//initialize the tile map
		for(int x = 0; x<width;x++){
			for(int y = 0; y<height; y++){
				//tileMap[x][y]=new Tile(x*32,y*32,0,0);//everything is grass
				tileMap[x][y]=new Tile(x*32,y*32,1,0);//everything is water
			}
		}
		//create islands
		int islandCount = randomNumber(2,15);
		for(int i = 0; i<islandCount; i++){
			generateLakeOrIsland(randomNumber(320,2550), false);
		}
		//create lakes
		int lakeCount = randomNumber(2,3);
		for(int i = 0; i<lakeCount; i++){
			generateLakeOrIsland(randomNumber(120,500), true);
		}
		//create mountains
		for(int k = 1; k<=1;k++){
			//create plateau's
			int plateauCount = randomNumber(2,10);
			for(int i = 0; i<plateauCount;i++){
				generatePlateauOrCanyon(k,randomNumber(200/k,1000/k),true);
			}
			//create the plateau edges
			setElevationEdges(k+1);
			//remove all wierd outcrops that we don't have textures for
			removeAnySectionsOfCliffThatAreConnectedToCliffButTooSmallToWalkOn(k+1);
		}



		//create the map
		map = new LevelMap(tileMap);
	}
	public void generatePlateauOrCanyon(int buildElevation, int size, boolean isPlateau){
		int elevationChange = 0;
		int count = 0;
		if(isPlateau){
			elevationChange = 1;
		}
		else{
			elevationChange = -1;
		}
		ArrayList<Tile> tilesAdded = new ArrayList<Tile>();
		//find a random tile where the tileMap is not water
		Point randomlyChosenTile = new Point(randomNumber(0,width-1),randomNumber(0,height-1));
		while(tileMap[randomlyChosenTile.x][randomlyChosenTile.y].elevation!=buildElevation||tileMap[randomlyChosenTile.x][randomlyChosenTile.y].tileID!=0){//could create an infinite loop if the build elevation does not yet exist
			randomlyChosenTile = new Point(randomNumber(0,width-1),randomNumber(0,height-1));
		}
		tileMap[randomlyChosenTile.x][randomlyChosenTile.y] = new Tile(randomlyChosenTile.x*32,randomlyChosenTile.y*32,0,buildElevation+elevationChange);
		tileMap[randomlyChosenTile.x][randomlyChosenTile.y].oldElevation=buildElevation;
		tilesAdded.add(tileMap[randomlyChosenTile.x][randomlyChosenTile.y]);
		count++;
		while(count<size){//for the desired amount of tiles
			//pick a random tile which has already been created for this terrain feature
			Tile randTile = tilesAdded.get(randomNumber(0,tilesAdded.size()-1));
			randomlyChosenTile = new Point(randTile.xpos/32,randTile.ypos/32);
			
			//get all the adjacent grass tiles that are at the same elevation as buildElevation
			ArrayList<Tile> adjacentTiles = tilesAdjacentToPosition(randomlyChosenTile.x,randomlyChosenTile.y,0,true,buildElevation);
			
			//make sure there are adjacent tiles that can be modified
			if(adjacentTiles.size()>0){
				randTile = adjacentTiles.get(randomNumber(0,adjacentTiles.size()-1));
				//pick a random one of these tiles
				randomlyChosenTile = new Point(randTile.xpos/32,randTile.ypos/32);
				//set the tile at this position to be a placeholder tile
				tileMap[randomlyChosenTile.x][randomlyChosenTile.y]=new Tile(randomlyChosenTile.x*32,randomlyChosenTile.y*32,0,buildElevation+elevationChange);
				tileMap[randomlyChosenTile.x][randomlyChosenTile.y].oldElevation=buildElevation;
				tilesAdded.add(tileMap[randomlyChosenTile.x][randomlyChosenTile.y]);
				count++;
			}
			if(randomNumber(1,100)==1){//failsafe to prevent infinite loops
				count++;
			}
		}
		//		//set the edges of the plateau to be cliff and the inside to be grass
		//		for(int i = 0; i<tilesAdded.size();i++){
		//			ArrayList<Tile> adjacentTiles = tilesAdjacentToPosition(tilesAdded.get(i).xpos/32,tilesAdded.get(i).ypos/32,0,false,buildElevation);
		//			//if some of the adjacent tiles are not the placeholder type
		//			if(adjacentTiles.size()>0){
		//				//make this tile be a cliff tile
		//				tilesAdded.set(i, new Tile(tilesAdded.get(i).xpos,tilesAdded.get(i).ypos,3,buildElevation+elevationChange));
		//			}
		//			else{
		//				//make this tile into a grass tile
		//				tilesAdded.set(i, new Tile(tilesAdded.get(i).xpos,tilesAdded.get(i).ypos,0,buildElevation+elevationChange));
		//			}
		//		}


	}
	public void generateDoors(){
		if(name.equals("Test")){
			ArrayList<Point> acceptableTilesToOverwrite = new ArrayList<Point>();
			acceptableTilesToOverwrite.add(new Point(6,0));
			int w = 3;
			int h = 3;
			ArrayList<Tile[][]> possibleAreas = findAreasFullOfTileTypes(w,h,acceptableTilesToOverwrite);
			Tile[][] randomlyPickedSpot = possibleAreas.get(randomNumber(0,possibleAreas.size()));
		}
	}
	public ArrayList<Tile[][]> findAreasFullOfTileTypes(int w, int h, ArrayList<Point> tileIDs){
		ArrayList<Tile[][]> areasFound = new ArrayList<Tile[][]>();
		//loop through all the tiles in the level
		for(int x = 0; x<width-w; x++){
			for(int y = 0; y<height-h;y++){
				//check an area where this x,y is the top left corner and width and height are what was passed
				boolean isCorrect = true;
				Tile[][] subArea = new Tile[w][h];
				for(int i = x; i<x+w;i++){
					for(int j = y; j<y+h; j++){
						boolean oneWasRight = false;
						//loop through all the desired tile types
						for(int k = 0; k<tileIDs.size();k++){
							if((tileMap[w][j].artX==tileIDs.get(k).x&&tileMap[w][j].artY==tileIDs.get(k).y)){
								oneWasRight = true;
							}
						}
						subArea[i-w][j-h] = tileMap[w][j];
						if(!oneWasRight){
							isCorrect=false;
						}
					}
				}
				if(isCorrect){
					areasFound.add(subArea);
				}
			}
		}
		return areasFound;
	}
	/*
	 * makes cliff tiles on the edges of a change in elevation
	 */
	public void setElevationEdges(int elev){
		//loop through all tiles
		for(int x = 0; x<width; x++){
			for(int y = 0; y<height; y++){
				//if this tile is at the specified elevation
				if(tileMap[x][y].elevation==elev){
					//create a list of the tiles that are adjacent to this
					ArrayList<Tile> adjacentTiles = getTilesAdjacentToPosition(new Point(x,y),true);
					//if this tile is at an edge
					//check for any adjacent tiles that are not at the same elevation as this tile
					boolean isEdge = false;
					for(int i = 0; i<adjacentTiles.size();i++){
						if(adjacentTiles.get(i).elevation!=elev){
							isEdge = true;
						}
					}
					//if the tile is an edge tile make it be cliff
					if(isEdge){
						double oldElev = tileMap[x][y].oldElevation;
						tileMap[x][y]= new Tile(x*32,y*32,3,elev);
						tileMap[x][y].oldElevation=oldElev;
					}
				}
			}
		}
	}
	public void removeAnySectionsOfCliffThatAreConnectedToCliffButTooSmallToWalkOn(int elev){
		//find all the cliff tiles which have cliff on only one side
		for(int x = 0; x<width;x++){
			for(int y = 0; y<height; y++){
				if(tileMap[x][y].tileID==3){
					removeOutcrops(new Point(x,y),elev);
				}
			}
		}
	}
	/*replaces any cliff tile passed (which is connected to only one other cliff tile) with a grass tile
	 * 
	 * @param position - the position of the tile to check
	 * @param elev - the elevation to check for adjacent cliff tiles
	 */
	public void removeOutcrops(Point position, int elev){
		//get the tiles which are adjacent to this one
		ArrayList<Tile> adjacentTiles = getTilesAdjacentToPosition(new Point(position.x,position.y),false);
		//determine how many of the adjacent tiles are cliff
		int count = 0;//the number of adjacent cliff tiles detected
		ArrayList<Tile> cliffTiles = new ArrayList<Tile>();
		for(int i = 0; i<adjacentTiles.size();i++){
			if(adjacentTiles.get(i).tileID==3){
				cliffTiles.add(adjacentTiles.get(i));
				count++;
			}
		}
		Tile thisTile = tileMap[position.x][position.y];
		if(getNorthTile(thisTile)!=null&&getSouthTile(thisTile)!=null&&getWestTile(thisTile)!=null&&getEastTile(thisTile)!=null){
			//check if top and bottom are the same elevation but left and right are not
			if(getNorthTile(thisTile).elevation==thisTile.elevation&&getSouthTile(thisTile).elevation==thisTile.elevation){
				if(getWestTile(thisTile).elevation!=thisTile.elevation&&getEastTile(thisTile).elevation!=thisTile.elevation){
					if(getNorthTile(thisTile).tileID==3&&getSouthTile(thisTile).tileID==3){
						count = 1;
						cliffTiles.add(getNorthTile(thisTile));
						cliffTiles.add(getSouthTile(thisTile));
					}
				}
			}
			//check if left and right are the same elevation but top and bottom are not
			if(getNorthTile(thisTile).elevation!=thisTile.elevation&&getSouthTile(thisTile).elevation!=thisTile.elevation){
				if(getWestTile(thisTile).elevation==thisTile.elevation&&getEastTile(thisTile).elevation==thisTile.elevation){
					if(getWestTile(thisTile).tileID==3&&getEastTile(thisTile).tileID==3){
						count = 1;
						cliffTiles.add(getWestTile(thisTile));
						cliffTiles.add(getEastTile(thisTile));
					}
				}
			}
		}
		if(position.x!=0&&position.y!=0&&position.x!=width-1&&position.y!=height-1){
			if(count==1){

				double oldElev = tileMap[position.x][position.y].oldElevation;
				//set this section to be grass
				tileMap[position.x][position.y]=new Tile(position.x*32,position.y*32,0,oldElev);
				//call this method on the connected cliff sections
				for(int i = 0; i<cliffTiles.size();i++){
					removeOutcrops(new Point(cliffTiles.get(i).xpos/32,cliffTiles.get(i).ypos/32),elev);
				}
			}
		}
	}
	public Tile getNorthTile(Tile tile){
		if((tile.ypos/32)-1>0){
			return tileMap[(tile.xpos/32)][(tile.ypos/32)-1];
		}
		return null;
	}
	public Tile getNorthEastTile(Tile tile){
		if((tile.ypos/32)-1>=0&&(tile.xpos/32)+1<width){
			return tileMap[(tile.xpos/32)+1][(tile.ypos/32)-1];
		}
		return null;
	}
	public Tile getSouthTile(Tile tile){
		if((tile.ypos/32)+1<height){
			return tileMap[(tile.xpos/32)][(tile.ypos/32)+1];
		}
		return null;
	}
	public Tile getSouthEastTile(Tile tile){
		if((tile.ypos/32)+1<height&&(tile.xpos/32)+1<width){
			return tileMap[(tile.xpos/32)+1][(tile.ypos/32)+1];
		}
		return null;
	}
	public Tile getWestTile(Tile tile){
		if((tile.xpos/32)-1>0){
			return tileMap[(tile.xpos/32)-1][(tile.ypos/32)];
		}
		return null;
	}
	public Tile getNorthWestTile(Tile tile){
		if((tile.ypos/32)-1>=0&&(tile.xpos/32)-1>=0){
			return tileMap[(tile.xpos/32)-1][(tile.ypos/32)-1];
		}
		return null;
	}
	public Tile getEastTile(Tile tile){
		if((tile.xpos/32)+1<width){
			return tileMap[(tile.xpos/32)+1][(tile.ypos/32)];
		}
		return null;
	}
	public Tile getSouthWestTile(Tile tile){
		if((tile.ypos/32)+1<height&&(tile.xpos/32)-1>=0){
			return tileMap[(tile.xpos/32)-1][(tile.ypos/32)+1];
		}
		return null;
	}
	public ArrayList<Tile> getTilesAdjacentToPosition(Point position,boolean includeCorners){
		ArrayList<Tile> adjacentTiles = new ArrayList<Tile>();
		for(int x = position.x-1; x<=position.x+1;x++){
			for(int y = position.y-1; y<=position.y+1;y++){
				if(x>=0&&y>=0&&x<width&&y<height){
					if(includeCorners){
						if(!(x==position.x&&y==position.y)){
							adjacentTiles.add(tileMap[x][y]);
						}
					}
					else{
						//if(!(x==position.x-1&&y==position.y-1)&&!(x==position.x+1&&y==position.y-1)&&!(x==position.x-1&&y==position.y+1)&&!(x==position.x+1&&y==position.y+1)){
						if(x==position.x||y==position.y){	
							if(!(x==position.x&&y==position.y)){
								adjacentTiles.add(tileMap[x][y]);
							}
						}
						//}
					}
				}
			}
		}
		//		//flag all adjacent tiles for testing purposes
		//		for(int i = 0; i<adjacentTiles.size();i++){
		//			adjacentTiles.get(i).flagged=true;
		//		}
		return adjacentTiles;
	}
	public void generateLakeOrIsland(int lakeSize, boolean isLake){
		int createdTileID;
		int replacedTileID;
		int elevChange = 0;
		if(isLake){//decrease in elevation
			elevChange = -1;
			createdTileID=1;//water
			replacedTileID =0;//grass
		}
		else{//increase in elevation
			elevChange= 1;
			createdTileID = 0;//grass
			replacedTileID = 1;//water
		}
		//add randomly placed water
		Point lakePosition = new Point(randomNumber(0,width-1),randomNumber(0,height-1));
		ArrayList<Tile> lakeEdges = new ArrayList<Tile>();
		lakeEdges.add(tileMap[lakePosition.x][lakePosition.y]);
		double oldElevation = tileMap[lakePosition.x][lakePosition.y].elevation;
		tileMap[lakePosition.x][lakePosition.y]=new Tile(lakePosition.x*32,lakePosition.y*32,createdTileID,oldElevation+elevChange);
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
			//if the tile chosen is not already a water tile and is at the correct elevation
			if(!(tileMap[lakePosition.x][lakePosition.y].tileID==createdTileID)&&tileMap[lakePosition.x][lakePosition.y].elevation==oldElevation){
				tileMap[lakePosition.x][lakePosition.y]=new Tile(lakePosition.x*32,lakePosition.y*32,createdTileID,oldElevation+elevChange);//random bits of water
				count++;
				//get a list of all the water tiles which are adjacent to this one
				ArrayList<Tile> adjacentTiles = tilesAdjacentToPosition(lakePosition.x,lakePosition.y,createdTileID,false,0);
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
			ArrayList<Tile> adjacentNonWaterTiles = tilesAdjacentToPosition(lakeEdges.get(i).xpos/32,lakeEdges.get(i).ypos/32,replacedTileID,false,0);
			//make all adjacent grass into dirt
			for(int j = 0; j<adjacentNonWaterTiles.size();j++){
				int x = adjacentNonWaterTiles.get(j).xpos;
				int y = adjacentNonWaterTiles.get(j).ypos;
				tileMap[x/32][y/32].flagged=true;
				tileMap[x/32][y/32]= new Tile(x,y,2,oldElevation);
			}
		}

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
	public ArrayList<Tile> tilesAdjacentToPosition(int xpos, int ypos, int id, boolean careAboutElevation, int desiredElevation){
		ArrayList<Tile> adjacentTiles = new ArrayList<Tile>();
		//top
		if(ypos-1>=0){
			if(tileMap[xpos][ypos-1].tileID==id){
				if(careAboutElevation == false||tileMap[xpos][ypos-1].elevation==desiredElevation){
					adjacentTiles.add(tileMap[xpos][ypos-1]);
				}
			}
		}
		//bottom
		if(ypos+1<height){
			if(tileMap[xpos][ypos+1].tileID==id){
				if(careAboutElevation == false||tileMap[xpos][ypos+1].elevation==desiredElevation){
					adjacentTiles.add(tileMap[xpos][ypos+1]);
				}
			}
		}
		//left
		if(xpos-1>=0){
			if(tileMap[xpos-1][ypos].tileID==id){
				if(careAboutElevation == false||tileMap[xpos-1][ypos].elevation==desiredElevation){
					adjacentTiles.add(tileMap[xpos-1][ypos]);
				}
			}
		}
		//right
		if(xpos+1<width){
			if(tileMap[xpos+1][ypos].tileID==id){
				if(careAboutElevation == false||tileMap[xpos+1][ypos].elevation==desiredElevation){
					adjacentTiles.add(tileMap[xpos+1][ypos]);
				}
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
