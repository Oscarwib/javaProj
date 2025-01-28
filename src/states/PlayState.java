package states;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.util.Random;
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
	//	private double tempy;
	private Random engen = new Random(); 
	private int movingSpeed = 10;
	private Enemy bomb;
	private boolean isFlyingEnemyActive = false;
	private String currScore;
	private double collisionX = -1.00;
	private SpeedPowerUp speedUp;
	private boolean isPowerUpActive;
	private int num;



	public PlayState(GameModel model, boolean mode) {
		super(model);
		informationText = "Press Escape \nTo Return To The Menu";
		livesleft = "Lives left: ";
		gameOverText = "GAMEOVER\n" + informationText;
		fontColor = Color.BLACK;
		scoreText = "Highscore: " + Integer.toString(score.getHighScore());

		if (mode) {
			mode1();
		} else {
			mode2();
		}

	}

	public void mode1() {
		player = new Player(Constants.playerImg);
		enemy = new Enemy(Constants.enemyImg, -100.00, 270.00, Constants.enemyHeight, Constants.enemyWidth);
		extraLife = new ExtraLifePowerUp(Constants.lifeImg, 900.00, 170, Constants.powerHeight, Constants.powerWidth);
		speedUp = new SpeedPowerUp(Constants.powerImg, 900.00, 265.00, Constants.powerHeight, Constants.powerWidth);
		flyingEnemy = new FlyingEnemy(Constants.flyingEnemyImg, -200.00, 20.00, Constants.enemyHeight, Constants.enemyWidth);
		bgColor = Color.ROYALBLUE;
		lineColor = Color.WHITE;

	}

	public void mode2() {
		player = new Player(Constants.playerImg2);
		enemy = new Enemy(Constants.enemyImg, -100.00, 270.00, Constants.enemyHeight, Constants.enemyWidth);
		extraLife = new ExtraLifePowerUp(Constants.lifeImg, -100.00, 270, Constants.powerHeight, Constants.powerWidth);
		speedUp = new SpeedPowerUp(Constants.powerImg, -100.00, 265.00, Constants.powerHeight, Constants.powerWidth);
		flyingEnemy = new FlyingEnemy(Constants.flyingEnemyImg, -200.00, 20.00, Constants.enemyHeight, Constants.enemyWidth);
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

		if (isPowerUpActive) {

			if (num == 0) {

				if(speedUp.getX() < 0 - Constants.playerWidth) {
					speedUp.setX(Constants.screenWidth);
					isPowerUpActive = false;
				}

				g.drawImage(speedUp.getImage(), speedUp.getX(), speedUp.getY(), Constants.powerWidth, Constants.powerHeight);

			} else if (num == 1){

				if(extraLife.getX() < 0 - Constants.playerWidth) {
					extraLife.setX(Constants.screenWidth);
					isPowerUpActive = false;
				}
				g.drawImage(extraLife.getImage(), extraLife.getX(), extraLife.getY(), Constants.powerWidth, Constants.powerHeight);

			}
		}

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
			isFlyingEnemyActive = false;
			player.updatePasses(1);


			// Reset bomb drop flag to allow the next bomb drop
			flyingEnemy.resetBombDrop();

			speedCheck();


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


			if (getRandom() == 1 && player.getPasses() > 2) {  // 50% chance after player hase passed 
				isFlyingEnemyActive = true;
				flyingEnemy.setX(Constants.screenWidth);  // Reset position
			}
		}		
	}

	//TODO om score är större än visst antal --> lotta mellan flying och vanlig, vi kan göra en random med 2 int, ochberoende på vad den blir kan vi rita en
	//TODO göra en variabel för y positionen som hämtas om vi ritar flygande, för att den ska behålla samma position på hela y axeln

	public void speedCheck() {
		if (player.getPasses() % 5 == 0) {
			movingSpeed += 1;
			System.out.println("speed set to: " + Integer.toString(movingSpeed));
		} else if (player.getPasses() % 2 == 0) {

			isPowerUpActive = true;			
			//			num = 0;
			num = getRandom();
		}
	}

	public int getSpeed() {
		return movingSpeed;
	}

	public void setSpeed(int s) {
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

			Enemy droppedBomb = flyingEnemy.dropBomb(player);

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


