package states;

/**
 * @author oscarwiberg, filipyhde
 * Usefunktionen är abstract eftersom powerupsen ger olika fördelar, denna återställer spelarens liv, oberoende hur många den har sen innan.
 * Du kan därmed aldrig få mer liv än 3 som du startar med.
 */

public class ExtraLifePowerUp extends PowerUp {

	public ExtraLifePowerUp(String image, double x, double h, double w) {
		super(image, x, h, w);
	}

	@Override
	public void use(Player player, PlayState playState) {
		player.resetLives();
		
		
	}


	
	}


