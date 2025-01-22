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
	
	}  


	

	@Override
	public double getY() {
		return y;
	}



}
