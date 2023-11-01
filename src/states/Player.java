package states;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import constants.Constants;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class Player {
	
	private ImageView playerImageView;
	private double playerX = (Constants.screenWidth - Constants.playerWidth) / 2;
	private double playerY = Constants.screenHeight - 50;
	private int score = 0;
	private int lives = 3;
	private int scoreBonus = 1;
	
	public Player(String playerImg) {

		try {
			Image playerImage = new Image(new FileInputStream(playerImg));
			playerImageView = new ImageView(playerImage);
			playerImageView.setX(playerX);
			playerImageView.setY(playerY);
			playerImageView.setFitWidth(Constants.playerWidth);
			playerImageView.setFitHeight(Constants.playerHeight);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ImageView getPlayerImageView() {
		return playerImageView;
	}


	public void move(KeyCode keyCode) {
		if (keyCode == KeyCode.UP) {
			this.moveUp();
		} else if (keyCode == KeyCode.DOWN) {
			this.moveDown();		
		}
	}

	private void moveDown() {
		// TODO Auto-generated method stub
		
	}

	private void moveUp() {
		// TODO Auto-generated method stub
		
	}

	
}
