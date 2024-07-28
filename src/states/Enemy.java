package states;

import java.io.FileInputStream;

import java.io.FileNotFoundException;

import constants.Constants;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Enemy {

	private double enemyX = -100.00;
	private double enemyY = 250.00;
	private Image image;
	private ImageView enemyView;

	public Enemy(String enemyImg) {

		try {
			image = new Image(new FileInputStream(enemyImg));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//	if (collided && enemy.getEnemyX() < player.getPlayerX()) {
	//		collided = false;
	//	}

	public boolean playerEnemyCollision(Player player) {
		boolean hit = false;

		if ((enemyX < (player.getPlayerX() + 80.00)) && ((enemyX) > player.getPlayerX())) { //borde vara +80 på enemyx men funkar ej
//			if((player.getPlayerY() + 60.00) >= enemyY) {
//			if((player.getPlayerY() + 60.00) >= enemyY) {
				//			if ((enemyY + 80  > (player.getPlayerY())) && ((player.getPlayerY() + 80) > enemyY))  {

				hit = true;
				player.decreaseLives();
//				if (hit && enemyX < player.getPlayerX() + 100) {
//					hit = false;
//				}

			}
	

		return hit;
	}



	public void setEnemyX(double pos) {

		this.enemyX = pos;

	}

	public Image getImage() {
		return image;
	}

	public double getEnemyX() {
		return enemyX;
	}

	public ImageView getEnemyView() {
		return enemyView;
	}

	public double getEnemyY() {
		return enemyY;
	}



}
