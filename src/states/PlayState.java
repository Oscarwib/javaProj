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
	private boolean speedActive;


// Sätter upp playstate och tar en in en boolean som avgör om mode 1 eller 2
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
// sätter upp mode1 med relevanta variabler
	public void mode1() {
		player = new Player(Constants.playerImg);
		enemy = new Enemy(Constants.enemyImg, 900.00, 270.00, Constants.enemyHeight, Constants.enemyWidth);	
		flyingEnemy = new FlyingEnemy(Constants.flyingEnemyImg, 900.00, 20.00, Constants.enemyHeight, Constants.enemyWidth);
		bombImg = Constants.bombImg;
		bgColor = Color.ROYALBLUE;
		lineColor = Color.WHITE;

	}
//samma som ovan fast mode 2
	public void mode2() {
		player = new Player(Constants.playerImg2);
		enemy = new Enemy(Constants.enemyImg2, 900.00, 270.00, Constants.enemyHeight, Constants.enemyWidth);
		flyingEnemy = new FlyingEnemy(Constants.flyingEnemyImg2, 900.00, 20.00, Constants.enemyHeight, Constants.enemyWidth);
		bombImg = Constants.bombImg2;
		bgColor = Color.BEIGE;
		lineColor = Color.BLACK;


	}

	
//	ritar ut allt relevant på skärmen, gärs från main och sker med 50 ggr per sekund
	public void draw(GraphicsContext g) {
		drawBackground(g);
		drawGameStats(g);
		drawPlayer(g);
		drawEnemies(g);
		drawPowerUps(g);
	}
//	ritar player
	private void drawPlayer(GraphicsContext g) {
		g.drawImage(player.getImage(), player.getPlayerX(), player.getPlayerY(), Constants.playerWidth, Constants.playerHeight);		
	}
//	ritar texten på skärmen samt 
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
//	ritar bakgrunden samt linjen som spelaren står på
	private void drawBackground(GraphicsContext g) {
		drawBg(g, bgColor);
		g.setStroke(lineColor);
		g.setLineWidth(1);
		g.setLineDashes(2);
		g.strokeLine(Constants.screenWidth, 350, 0, 350);

	}
//	om powerups har blivit ativerade och speedup powerupen inte är aktiov så ritas en ny powerup ut, beroende på om den tar num är 0 eller 1, som genereras random i speedcheck()
	private void drawPowerUps(GraphicsContext g) {

		if (isPowerUpActive && !speedActive) {


			if (num == 0) {
				drawSpeedUp(g);

			} else {
				drawExtraLife(g);
			}
		}
	}

//	ritar speedup, fungerar likadant som ritningen under för den andra powerupen
	public void drawSpeedUp(GraphicsContext g) {
		if(speedUp.getX() < 0 - Constants.playerWidth) {
			speedUp.setX(Constants.screenWidth);
			isPowerUpActive = false;
		}

		g.drawImage(speedUp.getImage(), speedUp.getX(), speedUp.getY(), Constants.powerWidth, Constants.powerHeight);
	}

	public void drawExtraLife(GraphicsContext g) {
		if(extraLife.getX() < 0 - Constants.playerWidth) {
			extraLife.setX(Constants.screenWidth);
			isPowerUpActive = false;
		}
		g.drawImage(extraLife.getImage(), extraLife.getX(), extraLife.getY(), Constants.powerWidth, Constants.powerHeight);

	}

//	ritar enemy, beroende på om den flygande har blivit aktiverad eller ej, som intitielt sker i ground enemy, därefter i flygande enemy
//	om bomb inte är null så ritas den också.
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



//	om den befinner sig utanför skärmen så pterställer vi en vriabel för att bomben har blivit droppad i flyingenemy klassen
//	Den tar ett rando 50/50 beslut om det ska ritas uten en flygande enemy härnäst, om false så blir det flygande igen
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

//	fungerar mer eller mindre likadant som för flying enemy. 
//	Sparar en variabel för om det skett en kollision eller ej, sätter den til null samt kordinaten för kollisonen varje gång enemy hamnar utanför skärmen.
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
				System.out.println("flyingenemy");
			}
		}		
	}


//	Vi baserar nivåperna och hastigheten på spelet baserat på hur mpånga enemy vi har passerat.
//	dessa returnerar bara samma på nummer som är delbara med 30, därmed gör vi båda sakerna om det är en siffra jämt delbar med 30
//	om vi har passerat ett antal jämt delbart med 6, ökjar vi farten på spelet.
//	om vi har passerat fler än tio samt att det är en siffra jämt delbart med 10, aktiverar vi powerupsm, och genrar vilken som ska ritas, 50/50 chans.
	public void speedCheck() {
		if (player.getPasses() % 30 == 0) {
			movingSpeed += 1;
			System.out.println("speed set to: " + Integer.toString(movingSpeed));
			isPowerUpActive = true;			
			num = getRandom();
		} else if (player.getPasses() % 6 == 0) {
			movingSpeed += 1;
			System.out.println("speed set to: " + Integer.toString(movingSpeed));
		} else if (player.getPasses() >= 10 && player.getPasses() % 10 == 0) {
			isPowerUpActive = true;			
			num = getRandom();

		}
	}

	public int getSpeed() {
		return movingSpeed;
	}
	
//	används genom speedup powerup, man måste veta i denhär klassen om den är aktiv.
// Använde inte för något annat än speedpowerup
	public void setSpeed(int s) {

		if (s == 100) {
			speedActive = true;
		} else {
			speedActive = false;
		}

		movingSpeed = s;

	}

//	funktion för att returnera random, denna kan hantera att den kallas på på flera ställen samtidigt.
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

//	om powerups är aktiva updaterar den poweruppen som har ritats basserat på vilken siffra numm är, samt kollar efter kollisioner.
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

//	om vi hoppar med player aktiveras up boolean, detta för att hoppet blir som en animation snarar än när vi flyttar oss högfer och vänster.
//	spelar objectet hanterar hoppet själv. den minskar sin y position tills den når toppen, sen ökar den y positionen tills den är tillbaks på marken.
//	då återställer vi till ursprungspositionen och ställer up variabeln till false för att sluta animationen. 
	private void movePlayer() {
		if (up) {

			player.jump(movingSpeed);
		}

		if (player.getPlayerY() == 265) {
			up = false;
		}		
	}

//	Eftersom ground enemy inte försvinner när vi krockar behöver vi en global boolean här som gör att vi bara kollar efter kollision tills den är true.
//	annars tappar vi alla liv på en gång eftersom den kallas 50 ggr per sekund. 
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

//	fungerar lite annorlunda jämfört med kollisionen på marken. Här behöver vi ingen global variabel. Vi ställer istället bomben till null om det sker en kollisone eller om den åker utanför skärmen.
	
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

//	om det är en bil enemy så minskar vi bara dens x position.
//	Om flygande enemy så skapar vi en ny bomb som får x koordinaten som player hade sist vi kallade på den funktionen. Sen släpps bomben inom +-50px av den.
//	om den bomben vi skapat inte är null så tilldelar vi globala variabeln bomb den nya bomben. Anars ritas bomben bara vid ursprungspositionen.
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


