package states;

public class ExtraLifePowerUp extends PowerUp {

	public ExtraLifePowerUp(String image, double x, double y, double h, double w) {
		super(image, x, y, h, w);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void use(Player player, PlayState playState) {
		player.resetLives();
		
	}


	
	}


