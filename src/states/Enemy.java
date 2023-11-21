package states;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import constants.Constants;
import javafx.scene.image.Image;

public class Enemy {
	
	private double enemyX = -100.00;
	private double enemyY = 250.00;
	private Image image;

	public Enemy(String enemyImg) {

		try {
			image = new Image(new FileInputStream(enemyImg));
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

	public double getEnemyY() {
		return enemyY;
	}
	
	
	
}
