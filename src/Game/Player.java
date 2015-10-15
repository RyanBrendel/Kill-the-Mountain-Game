package Game;

import java.awt.Graphics2D;
import java.awt.Point;

public class Player extends Entity{
	Inventory inventory = new Inventory();
	Point destination = new Point(0,0);
	double angleInRadians;
	double angleInDegrees;
	double movementSpeed = 1;
	public Player(int x, int y){
		this.xpos = x;
		this.ypos = y;
	}
	public void moveTowardsDestination(){
		
		if(!atDestination()){
			//find angle between current position and destination
			angleInDegrees = getAngleToDestination();
			angleInRadians = Math.toRadians(angleInDegrees);
			//move towards destination
			xpos+=movementSpeed*Math.cos(angleInRadians);
			ypos+=movementSpeed*Math.sin(angleInRadians);
		}
	}
	/*
	 * check if the player is already at their destination
	 * 
	 * @return true if player is at their destination, false if not
	 */
	public boolean atDestination(){
		if(Math.abs(xpos-destination.x)<=movementSpeed&&Math.abs(ypos-destination.y)<=movementSpeed){
			xpos = destination.x;
			ypos = destination.y;
			return true;
		}
		return false;
	}
	/*
	 * get angle between the player and the destination
	 * 
	 * @return the angle to move in
	 */
	public double getAngleToDestination() {
		double angle = (float) Math.toDegrees(Math.atan2((double)destination.y - ypos, (double)destination.x - xpos));
		if(angle < 0){
			angle += 360;
		}
		return angle;
	}
	public void update(){
		moveTowardsDestination();
	}
	public void Draw(Graphics2D g){
		g.drawImage(GamePanel.playerImage,(int)xpos,(int)ypos,32,32,null);
	}
}
