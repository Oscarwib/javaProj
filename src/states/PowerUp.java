package states;

import java.util.Random;

public abstract class PowerUp extends Object{
	
	private Boolean active = false;
	private int scoreSinceActive = 0;
	private Random random = new Random();

	public PowerUp(String image, double x, double y, double h, double w) {
		super(image, x, y, h, w);
		// TODO Auto-generated constructor stub
	}

	
	public void checkCollision(Player player) {
		if (this.playerObjectCollision(player)) {
			use(player);
			scoreSinceActive = player.getPasses();
		}	
	}
	
	public abstract void use(Player player);

}
