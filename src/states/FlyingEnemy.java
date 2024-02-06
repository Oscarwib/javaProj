package states;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.image.Image;

public class FlyingEnemy extends Enemy{
	
//	private double enemyX = -100.00;
	private ArrayList<Double> enemyY;
//	= {250, 200, 150,100};
//	private Image image;
	private Random rnd = new Random();
	

	public FlyingEnemy(String enemyImg) {
		super(enemyImg);
		enemyY = new ArrayList<Double>();
		enemyY.add(100.0);
		enemyY.add(150.0);
		enemyY.add(200.0);
		enemyY.add(250.0);
//		double[] enemyY = {250, 200, 150,100};
//		enemyY[0] = 100.0;
//		enemyY[1] = 150.0;
//		enemyY[2] = 200.0;
//		enemyY[3] = 250.0;
		
	}
	
//	System.out.println(weather.get(rnd.nextInt(weather.size())));

	@Override
	public double getEnemyY() {
		
		return enemyY.get(rnd.nextInt(enemyY.size()));
		
//		Gör en random  generator här som returnerar Y koordinat slumpmässigt inom ett spann(om y koordinaten är 0).
//		När enemy är ute ur frame sätter vi ykoordinaten till 0 så att den kan få en ny vid nästa passering.
//		double enlen = enemyY.length;
//		
//		double temp = 0.0;
//		
//		temp = enemyY[(int) rnd.nextDouble(enlen)];
//		
//		return temp;
		
	}
	
//	public void setEnemyY() {
//		enemyY = 0.0;
//		
//	}

}
