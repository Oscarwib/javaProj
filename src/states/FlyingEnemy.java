package states;

import java.util.Random;

import javafx.scene.image.Image;

public class FlyingEnemy extends Enemy{
	
	private double enemyX = -100.00;
	private double enemyY = 0;
	private Image image;
	private Random rnd;
	

	public FlyingEnemy(String enemyImg) {
		super(enemyImg);
		
		
	}
	
	
	public double getEnemyY() {
//		Gör en random  generator här som returnerar Y koordinat slumpmässigt inom ett spann(om y koordinaten är 0).
//		När enemy är ute ur frame sätter vi ykoordinaten till 0 så att den kan få en ny vid nästa passering.
		
		return enemyY;
		
	}
	
	public void setEnemyY() {
		enemyY = 0;
		
	}

}
