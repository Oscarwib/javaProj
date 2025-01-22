package states;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;

public abstract class Antagonist {
	
	protected double x;
	protected double y;
	protected Image img;
	protected boolean collisionDetected;
	
	
	public Antagonist(String image, double x, double y) {
		this.x = x;
		this.y = y;
		this.collisionDetected = false;
		
		try {
			this.img = new Image(new FileInputStream(image));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Antagonist(String image) {
		this.collisionDetected = false;

		try {
			this.img = new Image(new FileInputStream(image));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public abstract boolean playerAntagonistCollision(Player player);
	
	public void setAntagonistX(double pos) {
		this.x = pos;
	}

	public double getX() {
		return x;
	}

	public abstract double getY();

	
	public Image getImage() {
		return img;
	}
	
}
