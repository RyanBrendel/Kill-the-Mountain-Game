package Game;

public class Player extends Entity{
	Inventory inventory = new Inventory();
	public Player(int x, int y){
		this.xpos = x;
		this.ypos = y;
	}
}
