package states;

import javafx.scene.canvas.GraphicsContext;


import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import constants.Constants;

/**
 * This state represents the menu of the Game The main responsibility of this
 * class is to allow the user to swap state to the PlayState
 */
public class MenuState extends GameState {
	/*
	 * The following three variables are just used to show that a change of state
	 * can be made. The same variables also exist in the PlayState, can you think of
	 * a way to make this more general and not duplicate variables?
	 */
	private String informationText;
	private Color bgColor;
	private Color fontColor;
	// A PlayState, so we can change to the PlayState from the menu.
	private PlayState playState;
	private Image dinosaur;
	private boolean mode1;

	public MenuState(GameModel model) {
		super(model);
		informationText = "Welcome to the Dino game!\nTo play, press 1 for mode 1 or 2 for mode 2\nEscape to exit game";
		bgColor = Color.GREY;
		fontColor = Color.LIGHTBLUE;
		
		try {
			
		dinosaur = new Image(new FileInputStream("src/Images1/dinosaur.png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * Draws information text to the screen
	 */
	@Override
	public void draw(GraphicsContext g) {
		drawBg(g, bgColor);
		g.setFill(fontColor);
		g.setFont(new Font(30)); // Big letters
		// Print the information text, centered on the canvas
		g.fillText(informationText, Constants.screenWidth/4, Constants.screenHeight / 4);
		g.drawImage(dinosaur, Constants.screenWidth/2, Constants.screenHeight/2, 200, 200);
		// Can also use:
		// g.setStroke(fontColor);
		// g.strokeText(informationText, SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
	}

	
	@Override
	public void keyPressed(KeyEvent key) {
		System.out.println("Trycker på " + key.getText() + " i MenuState");

		if (key.getCode() == KeyCode.DIGIT1) {
			mode1 = true;
			playState = new PlayState(model, mode1);
			model.switchState(playState);
		} else if (key.getCode() == KeyCode.DIGIT2) {
			mode1 = false;
			playState = new PlayState(model, mode1);
			model.switchState(playState);
		} else if (key.getCode() == KeyCode.ESCAPE) {
			System.exit(0);
		}
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void activate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		
	}

	

}

