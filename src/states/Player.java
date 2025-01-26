package states;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import constants.Constants;
import javafx.scene.image.Image;


public class Player {

	//	TODO se över funktioner, kanske lättare att slå samman vissa

	private double playerX = (Constants.screenWidth - Constants.playerWidth) / 2;
	private double playerY = 265.00;
	private int lives = 3;
	private Image image;
	private boolean down = false;

	private int passes = 0;

	private boolean livesLocked = false;


	public Player(String playerImg) {


		try {
			image = new Image(new FileInputStream(playerImg));
			//slidingImage = new Image(new FileInputStream(Constants.slidingPlayerImg));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}





	public int getPasses() {
		return passes;
	}


	public void updatePasses(int passes) {
		this.passes += passes;
	}



	public Image getImage() {
		return image;
	}


	public String getLives() {
		return Integer.toString(this.lives);
	}


	public void decreaseLives() {

		if (!livesLocked) {
			this.lives--;
		}

	}

	public void resetLives() {
		this.lives = 3;
	}


	public double getPlayerX() {
		return playerX;
	}


	public double getPlayerY() {
		return playerY;
	}






	public void jump(int movingSpeed) {


		if (!down) {

			playerY -= movingSpeed;

			if (playerY <= 110) {

				down = true;

			}

		}

		if (down) {

			playerY += movingSpeed;

			if (playerY == 265) {
				down = false;
			}

		}

	}


	




	


	public void setPlayerX(double playerX) {
		this.playerX = playerX;
	}


	public void setPlayerY(double playerY) {
		this.playerY = playerY;
	}




	public void moveLeft(int movingSpeed) {
		playerX -= movingSpeed;

	}




	public void moveRight(int movingSpeed) {
		playerX += movingSpeed;

	}





	public void lockLives(boolean b) {
		livesLocked = b;

	}





}
