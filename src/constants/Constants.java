package constants;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;

public interface Constants {

	/**
	 * In this file we define some global constants.
	 *
	 * An interface is one way to store variables that are truly constants and not
	 * subject to change during run time.
	 *
	 * Please note that the problem with global variables is that anyone can change
	 * them whenever. This makes it extremely hard to reason about your code. But
	 * for constants, this is not a problem since we cannot change them, and
	 * therefore they stay the same during the entire execution of the program.
	 */
	/*
	 * Define the size of the window
	 */
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
	String flyingEnemyImg = "src/Images1/saucer.png";
	double enemyWidth = 80;
	double enemyHeight = 80;
	
//	powerups
	String lifeImg = "src/Images1/apple.png";
	String powerImg = "src/Images1/zap.png";
	
	String bombImg = "src/Images1/bomb.png";
	double bombWidth = 50;
	double bombHeight = 50;
}
