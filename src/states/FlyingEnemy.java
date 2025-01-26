package states;

import java.util.ArrayList;
import constants.Constants;
import java.util.Random;

import javafx.scene.image.Image;

public class FlyingEnemy extends Object{


	private Random drop;
	private int margin = 50;
	private Bomb bomb;
	private double bombDropX;
	private boolean bombDropped = false;


	public FlyingEnemy(String enemyImg, double x, double y, double h, double w) {
		super(enemyImg, x, y, h, w);	

		this.drop = new Random();
		this.bombDropX = dropNextBombX();

	}



	private double dropNextBombX() {
		int minX = margin;
		int maxX = Constants.screenWidth - margin;
		return drop.nextInt(maxX - minX + 1) + minX;
	}



	public Bomb dropBomb() {

		if (!bombDropped && this.getX() >= bombDropX - 50 && this.getX() <= bombDropX + 50) {
			Bomb bomb = new Bomb(bombDropX, Constants.bombImg);  // Drop the bomb at bombDropX
			System.out.println("Bomb dropped at: " + bombDropX);
			bombDropped = true;  // Mark that the bomb has been dropped

			// Optionally, reset bombDropX to a new random location after dropping the bomb
			bombDropX = dropNextBombX();
			return bomb;
		}

		return null;
	}

	public void resetBombDrop() {
		bombDropped = false;
	}



	@Override
	public void use(Player player) {
		// TODO Auto-generated method stub
		
	}

	










}
