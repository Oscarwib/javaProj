package states;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import constants.Constants;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class Bomb extends Object{


	

	public Bomb(String bombImg, double x, double y, double h, double w) {
		super(bombImg, x, y, h, w);
		
		
	}
	
//	public Bounds getBoundingBox() {
//	    return new Rectangle(startX, posY, Constants.bombWidth, Constants.bombHeight).getBoundsInParent();
//	}
//
//	
//	
//	public double getX() {
//		return startX;
//	}
//
//
//
//
//	public double getY() {
//		return posY;
//	}
//
//
//
//
//	public Image getImage() {
//		return image;
//	}
//	
	public void render(int speed) {
		this.posY += speed;
	}

	
	

}
