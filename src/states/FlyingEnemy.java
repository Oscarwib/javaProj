package states;

import java.util.ArrayList;
import constants.Constants;
import java.util.Random;

import javafx.scene.image.Image;

public class FlyingEnemy extends Antagonist{

//	private double enemyX = 800.00;
	private ArrayList<Double> enemyY;
	private double currY;
	private Random rnd = new Random();


	public FlyingEnemy(String enemyImg, double x, double y) {
		super(enemyImg, x, y);	
		this.enemyY = new ArrayList<Double>();
		this.enemyY.add(150.0);
		this.enemyY.add(180.0);
		this.enemyY.add(200.0);
		this.enemyY.add(250.0);
		//		enemyY.add(265.00);
		//		enemyY.add(265.00);
		//		enemyY.add(265.00);
		//		enemyY.add(265.00);


		//		double[] enemyY = {250, 200, 150,100};
		//		enemyY[0] = 100.0;
		//		enemyY[1] = 150.0;
		//		enemyY[2] = 200.0;
		//		enemyY[3] = 250.0;

	}

	//	System.out.println(weather.get(rnd.nextInt(weather.size())));



//	public boolean playerFlyingEnemyCollision(Player player) {
//		boolean hit = false;
//
//		if ((enemyX < (player.getPlayerX() + 80.00)) && ((enemyX) > player.getPlayerX())) { //borde vara +80 på enemyx men funkar ej
//			//			if((player.getPlayerY() + 60.00) >= enemyY) {
//			if((player.getPlayerY() + 60.00) >= currY) {
//				//			if ((enemyY + 80  > (player.getPlayerY())) && ((player.getPlayerY() + 80) > enemyY))  {
//
//				hit = true;
//				player.decreaseLives();
//				//				if (hit && enemyX < player.getPlayerX() + 100) {
//				//					hit = false;
//				//				}
//
//			}
//
//		}
//
//		return hit;
//
//		//	public void setEnemyY() {
//		//		enemyY = 0.0;
//		//		
//	}

	public FlyingEnemy(String enemyImg) {
		super(enemyImg);
		this.enemyY = new ArrayList<Double>();
//		this.enemyY.add(200.00);
		this.enemyY.add(210.00);
//		this.enemyY.add(220.00);
//		this.enemyY.add(250.00);
	}

	@Override
	public boolean playerAntagonistCollision(Player player) {

		boolean collisionX = player.getPlayerX() < (x + 60.00) && (player.getPlayerX() + 60.00) > x;

	    // Check for collision on the y-axis
	    boolean collisionY = player.getRect() < (currY + 60.00) && (player.getRect() + 60.00) > currY;


	    if (collisionX && collisionY && !collisionDetected) {
	        player.decreaseLives();
	        collisionDetected = true;
	        return true;
	    }
	    
	    if (!collisionX) {
            collisionDetected = false;
        }
	    

		return false;
	
	}

	@Override
	public double getY() {

		currY = enemyY.get(rnd.nextInt(enemyY.size()));
		return currY;
	}

}
