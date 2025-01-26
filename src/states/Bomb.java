package states;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import constants.Constants;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class Bomb {

	private double startX;
	private double posY = 50;
	private Image image;
	
	public Bomb(double bombDropX, String bombImg) {
		
		try {
			image = new Image(new FileInputStream(bombImg));
	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.startX = bombDropX;

	
		
	}
	
	public Bounds getBoundingBox() {
	    return new Rectangle(startX, posY, Constants.bombWidth, Constants.bombHeight).getBoundsInParent();
	}

	
	
	public double getX() {
		return startX;
	}




	public double getY() {
		return posY;
	}




	public Image getImage() {
		return image;
	}
	
	public void render(int speed) {
		this.posY += speed;
	}

	
	

}
