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
			enemyView = new ImageView(image);
			enemyView.setX(enemyX);
			enemyView.setY(enemyY);
			enemyView.setFitHeight(Constants.enemyHeight);
			enemyView.setFitWidth(Constants.enemyWidth);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
