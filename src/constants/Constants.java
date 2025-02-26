package constants;

/**
 * @author oscarwiberg, filipyhde
 * Definerar globala konstanter som vi använder återkommande i andra klasser. 
 */
public interface Constants {

	final int screenWidth = 1000;
	final int screenHeight = 500;
	
	// Player
	String slidingPlayerImg = "src/Images1/player_sliding.png";
//	String playerImg = "src/Images1/runner.png";
	String playerImg = "src/Images1/player.png";
	String slidingPlayerImg2 = "src/Images1/player2_sliding.png";
//	String playerImg2 = "src/Images1/woman-running.png";
	String playerImg2 = "src/Images1/player2.png";
	double playerWidth = 100;
	double playerHeight = 100;
	
	//colours
	String Bluebackground = "-fx-background-color: #34495E;";
	String Blackbackground = "-fx-background-color: #111111;";
	
	//enemies
	String enemyImg = "src/Images1/lorry.png";
	String enemyImg2 = "src/Images1/fire_engine.png";
	String flyingEnemyImg = "src/Images1/saucer.png";
	String flyingEnemyImg2 = "src/Images1/helicopter.png";
	String bombImg = "src/Images1/bomb.png";
	String bombImg2 = "src/Images1/8ball.png";
	double bombWidth = 50;
	double bombHeight = 50;
	double enemyWidth = 80;
	double enemyHeight = 80;
	
//	powerups
	String lifeImg = "src/Images1/apple.png";
	String powerImg = "src/Images1/zap.png";
	double powerWidth = 40;
	double powerHeight = 40;
	
	String backgroundImg = "src/Images1/city.png";
	String backgroundImg2 = "src/Images1/city1.png";
	
}
