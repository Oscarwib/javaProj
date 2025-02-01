package states;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

import javafx.scene.image.Image;

/**
 * @author oscarwiberg, filipyhde		
 * Powerup bygger vidare på object eftersom vi fortfarande vill komma åt kollisionshanteringen
 * när vi vill kolla kollision med powerup kallar vi däremot på checkcCollision, då kallar den på use om det blir en collision.
 * De spawnar också in på en random y-coordinat inom det spannet som player kan nå med sitt hopp. 
 */
public abstract class PowerUp extends Object{
	
	private int scoreSinceActive = 0;
	private Random random = new Random();
	private int maxH = 110;
	private int minH = 265;

	public PowerUp(String image, double x, double h, double w) {
		super(image, x, h, w);
		this.posY = spawn();
	}
	
	
	
//	lägg in en ny constructor som bara tar in String image
//	lägg till en funktion som returnerar en powerup, likt det som har gjortd för bomben i flying enemy


	public double spawn() {
		int x = random.nextInt(minH - maxH + 1) + maxH;
		return (double) x;
	}
	
	protected int getScoreSinceActive() {
		return scoreSinceActive;
	}
	
	public void checkCollision(Player player, PlayState playState) {
		if (this.playerObjectCollision(player)) {
			use(player, playState);
			 this.setX(-1000);
			scoreSinceActive = player.getPasses();
		
		}	
	}
	
	public void setImage(String s) {
		try {
			this.img = new Image(new FileInputStream(s));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
		
		}
	}
	
	public abstract void use(Player player, PlayState playState);

	}
