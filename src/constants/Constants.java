package constants;

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
	int SCREEN_WIDTH = 1000;
	int SCREEN_HEIGHT = 800;
	
	// Player
	
	String playerImg = "src/Images1/player.png";
	
	//colours
	String Bluebackground = "-fx-background-color: #34495E;";
	String Blackbackground = "-fx-background-color: #111111;";
	
	
}
