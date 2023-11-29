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

	private double playerX = (Constants.screenWidth - Constants.playerWidth) / 2;
	private double playerY = 265.00;
	private int score = 0; 
	private int lives = 3;
	private Image image;
	private boolean down = false;
	private boolean up = false;
	private Rectangle rekt;
	private ImageView playerView;


	public Player(String playerImg) {

		try {
			image = new Image(new FileInputStream(playerImg));
			playerView = new ImageView(image);
			playerView.setX(playerX);
			playerView.setY(playerY);
			playerView.setFitHeight(Constants.playerHeight);
			playerView.setFitWidth(Constants.playerWidth);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}


	public ImageView getPlayerView() {
		return playerView;
	}


	public Image getImage() {
		return image;
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


	public void setPlayerX(double playerX) {
		this.playerX = playerX;
	}


	public void setPlayerY(double playerY) {
		this.playerY = playerY;
	}


}
