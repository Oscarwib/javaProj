package states;

import java.io.FileInputStream;

import java.io.FileNotFoundException;

import constants.Constants;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Enemy extends Antagonist{

	private double enemyX = -100.00;
	private double enemyY = 250.00;
	private Image image;
	private ImageView enemyView;

	public Enemy(String enemyImg, double x, double y) {
		super(enemyImg, x, y);

	}

	//	if (collided && enemy.getEnemyX() < player.getPlayerX()) {
	//		collided = false;
	//	}

	@Override
	public boolean playerAntagonistCollision(Player player) {
//		boolean hit = false;

		boolean collisionX = player.getPlayerX() < (x + 60.00) && (player.getPlayerX() + 60.00) > x;

		// Check for collision on the y-axis
		boolean collisionY = player.getPlayerY() < (y + 45.00) && (player.getPlayerY() + 60.00) > y;

		if (collisionY && collisionX && !collisionDetected) {
			player.decreaseLives();

			 collisionDetected = true;
		        return true;
		    }
		    
		    if (!collisionX) {
	            collisionDetected = false;
	        }
		
		return false;
		//		if ((enemyX < (player.getPlayerX() + 80.00)) && ((enemyX) > player.getPlayerX())) { //borde vara +80 på enemyx men funkar ej
		////			if((player.getPlayerY() + 60.00) >= enemyY) {
		////			if((player.getPlayerY() + 60.00) >= enemyY) {
		//				//			if ((enemyY + 80  > (player.getPlayerY())) && ((player.getPlayerY() + 80) > enemyY))  {
		//				hit = true;
		//				player.decreaseLives();
		////				if (hit && enemyX < player.getPlayerX() + 100) {
		////					hit = false;
		////				}
		//
		//			}
		//	
		//		System.out.println("helooooooooo");
		//
		//		return hit;
	}  


	

	@Override
	public double getY() {
		return y;
	}



}
