package states;

import constants.Constants;

/**
 * @author oscarwiberg, filipyhde
 * Denhär klassen har en liten längre chain of events vid kollision. Vi kallar på handle i playstate, sparar hur många passes vi gjort samt nuvarande hastighet i spelet
 * därfter kallar vi på kollision, om det blir kollision kallar den på use som låser liven på player och ökar spelhastigheten till 100.
 * När det har gått 10 passeringar sen kollision återgår hastigheten till den som den var innan. 
 */
public class SpeedPowerUp extends PowerUp {

	private boolean active = false;
	private int speed;
	private int start;

	public SpeedPowerUp(String image, double x, double h, double w) {
		super(image, x, h, w);
	}

	@Override
	public void use(Player player, PlayState playState) {


		
		player.lockLives(true);

		playState.setSpeed(100);

		active = true;

	}

	public void handle(Player player, PlayState playState) {
		if (!active) {

			start = player.getPasses();
			speed = playState.getSpeed();

			this.checkCollision(player, playState);

		} else {
			if ((player.getPasses() - start) >= 5) {
				playState.setSpeed(speed);
				player.lockLives(false);
				player.setPlayerY(265);
				active = false;

			}
		}
	}
}