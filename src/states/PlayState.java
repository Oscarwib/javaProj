package states;

import javafx.scene.canvas.GraphicsContext;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

import constants.Constants;

/**
 * This state represents the Playing State of the Game The main responsibility
 * of this class is to; - create Game Objects - update Game Objects - draw Game
 * Objects Game Objects are for instance; players, enemies, npc's, etc...
 *
 * The PlayState can also be thought off as a blue print where data is loaded
 * into some container from a file or some other type of data storage.
 *
 * It can also be created by some class responsible for object creation and then
 * passed to the PlayState as a parameter. This means all the PlayState has to
 * do is receive a list of objects, store them in some container and then for
 * every object in that container update and render that object.
 *
 * This way you can let the user define different Levels based on what
 * parameters are passed into the PlayState.
 */
public class PlayState extends GameState {
	/*
	 * The following three variables are just used to show that a change of state
	 * can be made. The same variables also exist in the MenuState, can you think of
	 * a way to make this more general and not duplicate variables?
	 */
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
	private boolean down = false;
	private boolean gameOver = false;
	private MenuState menu;
	private HighScore score = new HighScore();
	private int clearedEnemies;
	private String scoreText;
	private ExtraLifePowerUp extraLife;
	private FlyingEnemy flyingEnemy;
	//	private double tempy;
	private Random engen;
	private String slidingPlayer;
	private int movingSpeed = 10;
	private Bomb bomb;
	private boolean isFlyingEnemyActive = false;



	/* Class only used for testing */

	//	TODO nästa steg, lägg in en flygande enemy för att testa glid funktionen, ändra boundsen på den glidande bilden!
	// 	TODO kanske ta bort att skicka med image när man instansierar player

	public PlayState(GameModel model, boolean mode) {
		super(model);
		informationText = "Press Escape \nTo Return To The Menu";
		livesleft = "Lives left: ";
		gameOverText = "GAMEOVER\n" + informationText;
		fontColor = Color.BLACK;
		scoreText = "Highscore: " + Integer.toString(score.getHighScore());
		//		+ Integer.toString(score.getHighScore());


		if (mode) {
			mode1();
		} else {
			mode2();
		}


		//		menu = new MenuState(model);

	}

	public void mode1() {
		player = new Player(Constants.playerImg);
		slidingPlayer = Constants.slidingPlayerImg;
		enemy = new Enemy(Constants.enemyImg, 800.00, 270.00);
		extraLife = new ExtraLifePowerUp(Constants.lifeImg);
		flyingEnemy = new FlyingEnemy(Constants.flyingEnemyImg, -200.00, 20.00);
		//		flyingEnemy.setAntagonistX(800.00);
		//		tempy = flyingEnemy.getY();
		bgColor = Color.ROYALBLUE;
		lineColor = Color.WHITE;

	}

	public void mode2() {
		player = new Player(Constants.playerImg2);
		slidingPlayer = Constants.slidingPlayerImg2;
		enemy = new Enemy(Constants.enemyImg, 800.00, 250.00);
		extraLife = new ExtraLifePowerUp(Constants.lifeImg);
		flyingEnemy = new FlyingEnemy(Constants.flyingEnemyImg, -200.00, 20.00);
		//		flyingEnemy.setAntagonistX(800.00);
		//		tempy = flyingEnemy.getY();
		bgColor = Color.BEIGE;
		lineColor = Color.BLACK;


	}

	/**
	 * Draws information text to the screen.
	 */
	@Override
	public void draw(GraphicsContext g) {
		drawBg(g, bgColor);

		g.setFill(fontColor);
		g.setFont(new Font(30)); // Big letters
		//		ritar liv kvar + nuvarande highscore om det inte är gameover, annars ritar den bara gemaovertexten

		if (!gameOver) {
			g.fillText(livesleft+player.getLives(), 0, 30);
			g.fillText(informationText, Constants.screenWidth - 300, 30);
			g.fillText(scoreText, 0, 60);
		} else {
			g.fillText(gameOverText, Constants.screenWidth/2, 200);
			score.saveScore(player.getPasses());
		}



		g.setStroke(lineColor);
		g.setLineWidth(1);
		g.setLineDashes(2);
		g.strokeLine(Constants.screenWidth, 350, 0, 350);


		g.drawImage(player.getImage(), player.getPlayerX(), player.getPlayerY(), Constants.playerWidth, Constants.playerHeight);



		//		Detta block används för att rita ut rectanglarna runt spelare och enemy
		//		g.setStroke(Color.BLACK); // Set the rectangle's border color
		//		g.setLineWidth(2); // Set the border width
		//		//		g.strokeRect(flyingEnemy.getX(), tempy, Constants.enemyWidth, Constants.enemyHeight);
		//		g.strokeRect(player.getPlayerX(), player.getRect(), Constants.playerWidth, Constants.playerHeight);



		drawEnemy(g);




	}

	public void drawEnemy(GraphicsContext g) {
		engen = new Random(); 

		if (!isFlyingEnemyActive) {

			g.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), Constants.enemyWidth, Constants.enemyHeight);

			if (enemy.getX() < 0 - Constants.enemyWidth) {
				enemy.setAntagonistX(Constants.screenWidth);
				player.updatePasses(1);

				if (engen.nextInt(2) == 1) {  // 50% chance
					isFlyingEnemyActive = true;
					flyingEnemy.setAntagonistX(Constants.screenWidth);  // Reset position
				}
			}

		} else {

			g.drawImage(flyingEnemy.getImage(), flyingEnemy.getX(), flyingEnemy.getY(), Constants.enemyWidth, Constants.enemyHeight);

			if (flyingEnemy.getX() < 0 - Constants.enemyWidth) {
				isFlyingEnemyActive = false;
				//				flyingEnemy.setAntagonistX(Constants.screenWidth);  // Reset the enemy's position
				//				player.updatePasses(1);

				// Reset bomb drop flag to allow the next bomb drop
				flyingEnemy.resetBombDrop();

				//				if (player.getPasses() % 5 == 0) {
				//					movingSpeed += 2;
				//				}
			}
		}

		// Render the bomb if it exists
		if (bomb != null) {
			g.drawImage(bomb.getImage(), bomb.getX(), bomb.getY(), Constants.bombWidth, Constants.bombHeight);
		}
	}




	//TODO om score är större än visst antal --> lotta mellan flying och vanlig, vi kan göra en random med 2 int, ochberoende på vad den blir kan vi rita en
	//TODO göra en variabel för y positionen som hämtas om vi ritar flygande, för att den ska behålla samma position på hela y axeln















	@Override
	public void keyPressed(KeyEvent key) {


		switch (key.getCode()) {

		case ESCAPE:
			model.switchState(new MenuState(model));
			break;

		case UP:
			//		Om spelaren duckar kan den inte hoppa, då ställer sig spelaren upp istället
			if (down) {
				player.standUp();
				down = false;
			} else {
				up = true;
			}
			break;

		case DOWN:
			//		Spelaren kan inte ducka om den är mitt i ett hopp
			if (!up) {
				down = true;

			}
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
		//om enemy position är mindre än player och collide är true kollar den inte collision

		//		Enemy hoppar 10 snäpp till vänster för varje update
		//		enemy.setAntagonistX(enemy.getX()-10);
		//		enemy.setAntagonistX(enemy.getX()-movingSpeed);
		if (isFlyingEnemyActive) {

			flyingEnemy.setAntagonistX(flyingEnemy.getX() -movingSpeed);

			Bomb droppedBomb = flyingEnemy.dropBomb();

			if (droppedBomb != null) {
				bomb = droppedBomb;
			}
		} else {
			enemy.setAntagonistX(enemy.getX() - movingSpeed);
		}

		if (bomb != null) {
			bomb.render(movingSpeed);

			if (bomb.getBoundingBox().intersects(player.getBoundingBox())) {
				System.out.println("Bomb hit the player!");
				player.decreaseLives();  // Assume there's a method to decrement player lives
				bomb = null;  // Destroy the bomb after collision

			}	else if (bomb.getY() > Constants.screenHeight) {
				bomb = null;  // Destroy the bomb if it goes off-screen
				System.out.println("Bomb went off-screen and was destroyed");
			}
		}


		//		Om 
		if (!gameOver) {

			if (!collided) {
				//				collided = enemy.playerAntagonistCollision(player);
				collided = enemy.playerAntagonistCollision(player);


				if (Integer.valueOf(player.getLives()) == 0) {
					gameOver = true;
				} 
			}

			//återställer boolean när enemy passerat
			if (collided && enemy.getX() < player.getPlayerX()) {
				collided = false;
			}





			if (up) {

				player.jump(movingSpeed);
			}

			if (player.getPlayerY() == 265) {
				up = false;
			}

			if (down) {

				player.slide(slidingPlayer);

			}

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


