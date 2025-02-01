package states;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import constants.Constants;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/**
 * @author oscarwiberg, filipyhde
 * Allt som inte är player går egentligen genom denna class för att ärva funktionalitet och variabler. 
 * 
 */
public abstract class Object {

	protected double posX;
	protected double posY; 
	private double height;
	private double width;
	protected Image img;

	public Object(String image, double x, double y, double h, double w) {

		posY = y;
		posX = x;
		width = w;
		height = h;

		try {
			img = new Image(new FileInputStream(image));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Object(String image, double x, double h, double w) {
		posX = x;
		width = w;
		height = h;

		try {
			img = new Image(new FileInputStream(image));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public double getX() {
		return posX;
	}

	public double getY() {
		return posY;
	}

	public Image getImage() {
		return img;
	}


	public void setX(double posX) {
		this.posX = posX;
	}

	public void setY(double posY) {
		this.posY = posY;
	}

	public double getH() {
		return height;
	}

	public double getW() {
		return width;
	}

	public boolean playerObjectCollision(Player player) {
		boolean hit = false;

		double playerX = player.getPlayerX();
		double playerY = player.getPlayerY();

		// Define new smaller width and height
		double smallerWidth = Constants.playerWidth - 50;  // Example: reduced by 20 pixels
		double smallerHeight = Constants.playerHeight - 50; // Example: reduced by 20 pixels

		// Calculate new X and Y to center the smaller rectangle around the player's position
		double centeredX = playerX + (Constants.playerWidth - smallerWidth) / 2;
		double centeredY = playerY + (Constants.playerHeight - smallerHeight) / 2;


		Bounds playerRect = new Rectangle(centeredX, centeredY, smallerWidth, smallerHeight).getBoundsInParent();


		Bounds objectRect = new Rectangle(this.getX(), this.getY(), this.getW(), this.getH()).getBoundsInParent();

		if (playerRect.intersects(objectRect)) {
			hit = true;
		}

		return hit;
	}



	//	här kommer de två olika powerupsen avgöra vad som händer när ma ta den, denna klass kallar på kollisionen

}
