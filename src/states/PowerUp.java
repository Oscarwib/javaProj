package states;

import java.util.Random;

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

	public PowerUp(String image, double x, double y, double h, double w) {
		super(image, x, y, h, w);
		this.posY = spawn();
	}


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
			scoreSinceActive = player.getPasses();
		
		}	
	}
	
	
	public abstract void use(Player player, PlayState playState);

	}
