package states;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import constants.Constants;
import javafx.scene.image.Image;

public abstract class PowerUp {
	
	private double powerUpX = (Constants.screenWidth - Constants.playerWidth) / 2;
	private double powerUpY = 265.00;
	private Image powerUpImage;

	PowerUp(String image) {

		try {
			powerUpImage = new Image(new FileInputStream(image));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public double getPowerUpX() {
		return powerUpX;
	}

	public void setPowerUpX(double powerUpX) {
		this.powerUpX = powerUpX;
	}

	public double getPowerUpY() {
		return powerUpY;
	}

	public Image getImage() {
		return powerUpImage;
	}
	
	public boolean powerUpPlayerCollision(Player player) {
		boolean hit = false;

//		här ska det finnas kod för att kolla kollisioner mellan spelare och powerup

		return hit;
	}
	
	public abstract void use(Player player);
	
//	här kommer de två olika powerupsen avgöra vad som händer när ma ta den, denna klass kallar på kollisionen
	
}
