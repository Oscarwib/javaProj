package states;

import java.io.FileInputStream;

import java.io.FileNotFoundException;

import constants.Constants;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;

public class Player {
	
//	TODO se över funktioner, kanske lättare att slå samman vissa

	private double playerX = (Constants.screenWidth - Constants.playerWidth) / 2;
	private double playerY = 265.00;
	private int score = 0; 
	private int lives = 3;
	private Image image;
	private boolean down = false;
	private boolean up = false;
	private Image slidingImage;
	private Image currImage = null;
	private int passes = 0;
	//	private ImageView playerView;


	public int getPasses() {
		return passes;
	}





	public void updatePasses(int passes) {
		this.passes += passes;
	}





	public Player(String playerImg) {

		try {
			image = new Image(new FileInputStream(playerImg));
			slidingImage = new Image(new FileInputStream(Constants.slidingPlayerImg));
	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		currImage = image;

	}


	


	public Image getImage() {
		return currImage;
	}


	public String getLives() {
		return Integer.toString(this.lives);
	}


	public void decreaseLives() {


		this.lives--;

	}


	public double getPlayerX() {
		return playerX;
	}


	public double getPlayerY() {
		return playerY;
	}






	public void jump() {


		if (!down) {

			playerY -= 10;

			if (playerY <= 110) {

				down = true;

			}

		}

		if (down) {

			playerY += 10;

			if (playerY == 265) {
				down = false;
			}

		}

	}


	public void slide() {

		currImage = slidingImage;
			
	}


	public void setPlayerX(double playerX) {
		this.playerX = playerX;
	}


	public void setPlayerY(double playerY) {
		this.playerY = playerY;
	}


	public void standUp() {
		currImage = image;
		
	}





}
