package states;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.util.concurrent.ThreadLocalRandom;
import constants.Constants;

/**
 * @author oscarwiberg, filipyhde
 * hanterar självaste spelflödet. renderar alla karaktärer och objekt under spelets gång och uppdaterar alla positioner.
 */
public class PlayState extends GameState {

	private String informationText;
	private String livesleft;
	private String gameOverText;
	private Color bgColor;
	private Color lineColor;
	private Color fontColor;
	private Player player;
	private Enemy enemy;
	private boolean collided = false;
	private boolean up = false;
	private boolean gameOver = false;
	private HighScore score = new HighScore();
	private String scoreText;
	private ExtraLifePowerUp extraLife;
	private FlyingEnemy flyingEnemy;
	private int movingSpeed = 10;
	private Enemy bomb;
	private boolean isFlyingEnemyActive = false;
	private String currScore;
	private double collisionX = -1.00;
	private SpeedPowerUp speedUp;
	private boolean isPowerUpActive;
	private int num;
	private String bombImg;
	private PowerUp last = null;
	private PowerUp pw;
	private boolean speedActive;



	public PlayState(GameModel model, boolean mode) {
		super(model);
		informationText = "Press Escape \nTo Return To The Menu";
		livesleft = "Lives left: ";
		gameOverText = "GAMEOVER\n" + informationText;
		fontColor = Color.BLACK;
		scoreText = "Highscore: " + Integer.toString(score.getHighScore());
		extraLife = new ExtraLifePowerUp(Constants.lifeImg, 1200.00, Constants.powerHeight, Constants.powerWidth);
		speedUp = new SpeedPowerUp(Constants.powerImg, 1200.00, Constants.powerHeight, Constants.powerWidth);

		if (mode) {
			mode1();
		} else {
			mode2();
		}

	}

	public void mode1() {
		player = new Player(Constants.playerImg);
		enemy = new Enemy(Constants.enemyImg, 900.00, 270.00, Constants.enemyHeight, Constants.enemyWidth);	
		flyingEnemy = new FlyingEnemy(Constants.flyingEnemyImg, 900.00, 20.00, Constants.enemyHeight, Constants.enemyWidth);
		bombImg = Constants.bombImg;
		bgColor = Color.ROYALBLUE;
		lineColor = Color.WHITE;

	}

	public void mode2() {
		player = new Player(Constants.playerImg2);
		enemy = new Enemy(Constants.enemyImg2, 900.00, 270.00, Constants.enemyHeight, Constants.enemyWidth);
		flyingEnemy = new FlyingEnemy(Constants.flyingEnemyImg2, 900.00, 20.00, Constants.enemyHeight, Constants.enemyWidth);
		bombImg = Constants.bombImg2;
		bgColor = Color.BEIGE;
		lineColor = Color.BLACK;


	}

	/**
	 * Draws information text to the screen.
	 */
	@Override
	public void draw(GraphicsContext g) {
		drawBackground(g);
		drawGameStats(g);
		drawPlayer(g);
		drawEnemies(g);
		drawPowerUps(g);
	}

	private void drawPlayer(GraphicsContext g) {
		g.drawImage(player.getImage(), player.getPlayerX(), player.getPlayerY(), Constants.playerWidth, Constants.playerHeight);		
	}

	private void drawGameStats(GraphicsContext g) {
		currScore = "Currently: " + Integer.toString(player.getPasses());


		g.setFill(fontColor);
		g.setFont(new Font(30)); // Big letters
		//		ritar liv kvar + nuvarande highscore om det inte är gameover, annars ritar den bara gemaovertexten

		if (!gameOver) {
			g.fillText(livesleft+player.getLives(), 0, 30);
			g.fillText(informationText, Constants.screenWidth - 300, 30);
			g.fillText(scoreText, 0, 60);
			g.fillText(currScore, 0, 90);
		} else {
			g.fillText(gameOverText, Constants.screenWidth/2, 200);
			score.saveScore(player.getPasses());
		}

	}

	private void drawBackground(GraphicsContext g) {
		drawBg(g, bgColor);
		g.setStroke(lineColor);
		g.setLineWidth(1);
		g.setLineDashes(2);
		g.strokeLine(Constants.screenWidth, 350, 0, 350);

	}

	private void drawPowerUps(GraphicsContext g) {

		if (isPowerUpActive && !speedActive) {


			if (num == 0) {
				drawSpeedUp(g);
				last = speedUp;

			} else {
				drawExtraLife(g);
				last = extraLife;
			}
		}
	}

	public void drawSpeedUp(GraphicsContext g) {
		if(speedUp.getX() < 0 - Constants.playerWidth) {
			speedUp.setX(Constants.screenWidth);
			isPowerUpActive = false;
		}

		g.drawImage(speedUp.getImage(), speedUp.getX(), speedUp.getY(), Constants.powerWidth, Constants.powerHeight);
	}

	public void drawExtraLife(GraphicsContext g) {
		if(extraLife.getX() < 0 - Constants.playerWidth) {
//			extraLife.setImage(Constants.lifeImg);
			extraLife.setX(Constants.screenWidth);
			isPowerUpActive = false;
		}
		g.drawImage(extraLife.getImage(), extraLife.getX(), extraLife.getY(), Constants.powerWidth, Constants.powerHeight);

	}

	public void drawEnemies(GraphicsContext g) {

		if (!isFlyingEnemyActive) {	
			drawGroundEnemy(g);
		} else {
			drawFlyingEnemy(g);	
		}

		// Render the bomb if it exists
		if (bomb != null) {
			g.drawImage(bomb.getImage(), bomb.getX(), bomb.getY(), Constants.bombWidth, Constants.bombHeight);
		}
	}




	private void drawFlyingEnemy(GraphicsContext g) {
		g.drawImage(flyingEnemy.getImage(), flyingEnemy.getX(), flyingEnemy.getY(), Constants.enemyWidth, Constants.enemyHeight);

		if (flyingEnemy.getX() < 0 - Constants.enemyWidth) {
			flyingEnemy.setX(Constants.screenWidth);
			player.updatePasses(1);
			// Reset bomb drop flag to allow the next bomb drop
			flyingEnemy.resetBombDrop();
			speedCheck();

		

			if(getRandom() == 1) {
				isFlyingEnemyActive = false;
			}


		}		
	}

	private void drawGroundEnemy(GraphicsContext g) {


		g.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), Constants.enemyWidth, Constants.enemyHeight);

		if (enemy.getX() < 0 - Constants.enemyWidth) {
			enemy.setX(Constants.screenWidth);
			collided = false;
			collisionX = -1.00;
			player.updatePasses(1);

			speedCheck();


			if (player.getPasses() > 4 && getRandom() == 0) {  // 50% chance after player hase passed 
				isFlyingEnemyActive = true;
				// Reset position
				System.out.println("Stage one activated, flyingenemy added to the mix");
			}
		}		
	}

	//TODO om score är större än visst antal --> lotta mellan flying och vanlig, vi kan göra en random med 2 int, ochberoende på vad den blir kan vi rita en
	//TODO göra en variabel för y positionen som hämtas om vi ritar flygande, för att den ska behålla samma position på hela y axeln


	//	kör modulo 6, 10, 15
	//	dessa returnerar bara samma på nummer som är delbara med 30
	public void speedCheck() {
		if (player.getPasses() % 6 == 0) {
			movingSpeed += 1;
			System.out.println("speed set to: " + Integer.toString(movingSpeed));
		} else if (player.getPasses() > 1 && player.getPasses() % 2 == 0) { //denna bör ändras till något som aldrig klaffar med den ovan
			//			if (movingSpeed < 100) {
			isPowerUpActive = true;			
//			num = getRandom();
			num = 0;
			System.out.println(num);

			//			}
		}
	}

	public int getSpeed() {
		return movingSpeed;
	}

	public void setSpeed(int s) {

		if (s == 100) {
			speedActive = true;
		} else {
			speedActive = false;
		}

		movingSpeed = s;

	}


	public int getRandom() {
		return ThreadLocalRandom.current().nextInt(2);
	}








	@Override
	public void keyPressed(KeyEvent key) {


		switch (key.getCode()) {

		case ESCAPE:
			model.switchState(new MenuState(model));
			break;

		case UP:
			//		Om spelaren duckar kan den inte hoppa, då ställer sig spelaren upp istället
			up = true;
			break;

		case LEFT:
			player.moveLeft(movingSpeed);

			break;

		case RIGHT:
			player.moveRight(movingSpeed);

			break;

		default:
			break;
		}
	}


	@Override
	public void update() {

		if (!gameOver) {
			moveEnemies();
			movePowerUps();
			checkGroundCollision();
			checkBombCollision();
			movePlayer();
		}
	}


	private void movePowerUps() {
		if (isPowerUpActive) {

			if (num == 0) {

				speedUp.setX(speedUp.getX() - movingSpeed);
				speedUp.handle(player, this);

			} else if (num == 1) {

				extraLife.setX(extraLife.getX() - movingSpeed);
				extraLife.checkCollision(player, this);

			}
		}

	}


	private void movePlayer() {
		if (up) {

			player.jump(movingSpeed);
		}

		if (player.getPlayerY() == 265) {
			up = false;
		}		
	}


	private void checkGroundCollision() {
		if (!collided && enemy.playerObjectCollision(player)) {

			collided = true;

			if (collisionX == -1.00) {
				collisionX = enemy.getX();
				System.out.println("Player-Enemy Collision X Coordinate: " + collisionX);
			}

			player.decreaseLives();

		}

		if (Integer.valueOf(player.getLives()) == 0) {
			gameOver = true;
		} 		
	}


	private void checkBombCollision() {
		if (bomb != null) {
			bomb.setY(bomb.getY() + movingSpeed);

			if (bomb.playerObjectCollision(player)) {
				System.out.println("Bomb hit the player!");
				player.decreaseLives();  // Assume there's a method to decrement player lives
				bomb = null;  // Destroy the bomb after collision

			}	else if (bomb.getY() > Constants.screenHeight) {
				bomb = null;  // Destroy the bomb if it goes off-screen
				System.out.println("Bomb went off-screen and was destroyed");
			}
		}
	}


	private void moveEnemies() {
		if (isFlyingEnemyActive) {

			flyingEnemy.setX(flyingEnemy.getX() -movingSpeed);

			Enemy droppedBomb = flyingEnemy.dropBomb(player, bombImg);

			if (droppedBomb != null) {
				bomb = droppedBomb;
			}
		} else {
			enemy.setX(enemy.getX() - movingSpeed);
		}
	}

	// Here one would probably instead move the player and any
	// enemies / moving obstacles currently active.

	/**
	 * We currently don't have anything to activate in the PlayState so we leave
	 * this method empty in this case.
	 */
	@Override
	public void activate() {

	}

	/**
	 * We currently don't have anything to deactivate in the PlayState so we leave
	 * this method empty in this case.
	 */
	@Override
	public void deactivate() {

	}

}


