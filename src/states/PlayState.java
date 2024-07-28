package states;

import javafx.scene.canvas.GraphicsContext;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
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
	private double tempy;
	private Random engen;


	/* Class only used for testing */

	//	TODO nästa steg, lägg in en flygande enemy för att testa glid funktionen, ändra boundsen på den glidande bilden!
	// 	TODO kanske ta bort att skicka med image när man instansierar player

	public PlayState(GameModel model) {
		super(model);
		informationText = "Press Escape \nTo Return To The Menu";
		livesleft = "Lives left: ";
		gameOverText = "GAMEOVER\n" + informationText;
		bgColor = Color.BEIGE;
		fontColor = Color.BLUE;
		scoreText = "Highscore: " + Integer.toString(score.getHighScore());
		//		+ Integer.toString(score.getHighScore());

		player = new Player(Constants.playerImg);
		enemy = new Enemy(Constants.enemyImg);
		extraLife = new ExtraLifePowerUp(Constants.lifeImg);
		flyingEnemy = new FlyingEnemy(Constants.flyingEnemyImg, 800.00, 0.00);

		//		menu = new MenuState(model);

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

		//		ritar ut marklinjen

		g.setStroke(Color.BLACK);
		g.setLineWidth(1);
		g.setLineDashes(2);
		g.strokeLine(Constants.screenWidth, 350, 0, 350);

		//		om enemy är ute ur frame, ställer dem om positionen på den så att den börjar om



		//		Ritar enemy och player
//		g.drawImage(extraLife.getImage(), extraLife.getPowerUpX(), extraLife.getPowerUpY(), 40, 40);
		g.drawImage(player.getImage(), player.getPlayerX(), player.getPlayerY(), Constants.playerWidth, Constants.playerHeight);
//		g.drawImage(enemy.getImage(), enemy.getEnemyX(), enemy.getEnemyY(), Constants.enemyWidth, Constants.enemyHeight);
		drawEnemy(g);

		//		TODO kanske göra en random här också, som väljer om vi ska rita de olika enemies eller powerupsen. Vid en viss score
		//		TODO kommer möjligheten att ta powerups eller möta flyingenemy


	}

	public void drawEnemy(GraphicsContext g) {
		engen = new Random(); 
		
//		
//		if (player.getPasses() > 5) {
//			boolean b = engen.nextBoolean();
//			System.out.println(b);
//			
//			if (b) {
				g.drawImage(flyingEnemy.getImage(), flyingEnemy.getX(), tempy, 80, 80);
				if (flyingEnemy.getX() < 0 - Constants.enemyWidth) {
					flyingEnemy.setAntagonistX(Constants.screenWidth);
					tempy = flyingEnemy.getY();
					player.updatePasses(1);

				}
//			} else if (!b){
//				g.drawImage(enemy.getImage(), enemy.getEnemyX(), enemy.getEnemyY(), Constants.enemyWidth, Constants.enemyHeight);
//				if (enemy.getEnemyX() < 0 - Constants.enemyWidth) {
//					enemy.setEnemyX(Constants.screenWidth);
//					player.updatePasses(1);
//				}

//			}
//		} else {
//			g.drawImage(enemy.getImage(), enemy.getEnemyX(), enemy.getEnemyY(), Constants.enemyWidth, Constants.enemyHeight);
//			if (enemy.getEnemyX() < 0 - Constants.enemyWidth) {
//				enemy.setEnemyX(Constants.screenWidth);
//				player.updatePasses(1);
//			}

//		}
		
		//		enemy = new Enemy(Constants.enemyImg);
	



		//TODO om score är större än visst antal --> lotta mellan flying och vanlig, vi kan göra en random med 2 int, ochberoende på vad den blir kan vi rita en
		//TODO göra en variabel för y positionen som hämtas om vi ritar flygande, för att den ska behålla samma position på hela y axeln




		







	}


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

		default:
			break;

		}



	}

	@Override
	public void update() {
		//om enemy position är mindre än player och collide är true kollar den inte collision

		clearedEnemies ++;

		//		Enemy hoppar 10 snäpp till vänster för varje update
//		enemy.setEnemyX(enemy.getEnemyX()-10);
		flyingEnemy.setAntagonistX(flyingEnemy.getX() -10);

		//		Om 
		if (!gameOver) {

			if (!collided) {
//				collided = enemy.playerEnemyCollision(player);
				collided = flyingEnemy.playerAntagonistCollision(player);
				if (Integer.valueOf(player.getLives()) == 0) {
					gameOver = true;
				}
			}
			if (collided && flyingEnemy.getX() < player.getPlayerX()) {
				collided = false;
			}



			////		Så länge enemy och player har överlappande X koordinat(och inte redan har kolliderat), kollar vi om dem kolliderar på Y axeln
			//			if ((enemy.getEnemyX() <= (player.getPlayerX() + 80)) && (enemy.getEnemyX() > player.getPlayerX())) {
			//				//				if (!collided && enemy.getEnemyX() > player.getPlayerX()) {
			//				if (!collided) {
			//					checkCollision();
			//				}
			//				//				clearedEnemies ++;
			//			}
			//
			//			if (collided && enemy.getEnemyX() < player.getPlayerX()) {
			//				collided = false;
			//			}


			if (up) {

				player.jump();
			}

			if (player.getPlayerY() == 265) {
				up = false;
			}

			if (down) {

				player.slide();
			}

		}

	}

	public void checkCollision() {

		//		Om vi kolliderar på Y axeln

		//		if ((enemy.getEnemyX() <= (player.getPlayerX() + 80)) && (enemy.getEnemyX() > player.getPlayerX()) && ((player.getPlayerY() + 60) >= enemy.getEnemyY() )) {
		//
		//			collided = true;
		//			System.out.println("helo");
		//			//			Sätta någon variabel till något värde. Om denna variabel eller boolean inte är tom nästa update så har en krock skett.
		//
		//		} 
		//
		//		player.decreaseLives(collided);


		//		if ((enemy.getEnemyX() <= (player.getPlayerX() + 80)) && (enemy.getEnemyX() > player.getPlayerX())) {
		//			//			System.out.println("helo");
		if((player.getPlayerY() + 60) >= enemy.getEnemyY() ) {
			collided = true;
			//			System.out.println("fäk");
			if (Integer.valueOf(player.getLives()) == 1) {
				//								menu = new MenuState(model);
				//				model.switchState(menu);
				gameOver = true;
				score.saveScore(clearedEnemies);

			}
			player.decreaseLives();
			//			System.out.println(player.getLives());

		}
		//				System.out.println("slipped by enemy");
		//			}
		//		} 


		//				clearedEnemies ++;


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


