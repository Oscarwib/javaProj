package states;

public class SpeedPowerUp extends PowerUp {

	private boolean active = false;
	private int speed;
	private int start;

	public SpeedPowerUp(String image, double x, double y, double h, double w) {
		super(image, x, y, h, w);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void use(Player player, PlayState playState) {

		player.lockLives(true);

		playState.setSpeed(100);

		active = true;


		// TODO Auto-generated method stub

	}

	public void handle(Player player, PlayState playState) {
		if (!active) {

			start = player.getPasses();
			speed = playState.getSpeed();

			this.checkCollision(player, playState);

			

		} else {


			if ((player.getPasses() - start) >= 10) {
				playState.setSpeed(speed);
				player.lockLives(false);
				active = false;

			}


		}



	}

}
