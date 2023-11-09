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
	private Image playerImage;
	private Double posX = Constants.screenWidth/2 - (Constants.playerWidth/2);
	private Double posY = 265.00;
	
	public Player(String playerImg) {

		try {
			Image playerImage = new Image(new FileInputStream(playerImg));
			playerImageView = new ImageView(playerImage);
			playerImageView.setX(posX);
			playerImageView.setY(posY);
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
	
	public Image getImage() {
		return playerImage;
	}


	public void move(KeyCode keyCode) {
		if (keyCode == KeyCode.UP) {
			this.moveUp();
		} else if (keyCode == KeyCode.DOWN) {
			this.moveDown();		
		}
	}

	private void moveDown() {
		
	}

	private void moveUp() {
		
			playerImageView.setY(playerY + 5);
		
	}

	
}
