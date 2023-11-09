package states;

import testing.Tester;
import testing.Tester.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
	private Color bgColor;
	private Color fontColor;
	private Double playerPosX = Constants.screenWidth/2 - (Constants.playerWidth/2);
	//	private Double posX = Constants.screenWidth/2 - (Constants.playerWidth/2);
	//	private Double posY = Constants.screenHeight - 200;
	//	private Double posY = Constants.screenHeight;
	private Double playerPosY = 265.00;
	private Image player;
	private Image obstacle;
	private Double enemyPosX = -100.00;
	private Double enemyPosY = 250.00;


	/* Class only used for testing */


	public PlayState(GameModel model) {
		super(model);
		informationText = "Press Escape To Return To The Menu";
		bgColor = Color.WHITE;
		fontColor = Color.BLUE;
		try {
			player = new Image(new FileInputStream(Constants.playerImg));
			obstacle = new Image(new FileInputStream(Constants.enemyImg));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		

	}

	/**
	 * Draws information text to the screen.
	 */
	@Override
	public void draw(GraphicsContext g) {
		drawBg(g, bgColor);

		g.setFill(fontColor);
		g.setFont(new Font(30)); // Big letters
		g.fillText(informationText, Constants.screenWidth / 3, Constants.screenHeight / 3);
		//		dottedline = new Line(20, 200, 120, 270);
		//		dottedline.getStrokeDashArray().addAll(2d);
		g.setStroke(Color.BLACK);
		g.setLineWidth(1);
		g.setLineDashes(2);
		g.strokeLine(Constants.screenWidth, 350, 0, 350);
		// Can also use:
		// g.setStroke(fontColor);
		// g.strokeText(informationText, SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);

		// This could be a call to all our objects that we want to draw.
		// Using the tester simply to illustrate how it could work.
		//		tester.delegate(g);
		//		g.drawImage(car, posX, posY, 100, 100);
		
		if (enemyPosX < 0 - Constants.enemyWidth) {
			enemyPosX = Constants.screenWidth;
		}
		
		g.drawImage(player, playerPosX, playerPosY, Constants.playerWidth, Constants.playerHeight);
		g.drawImage(obstacle, enemyPosX, enemyPosY, Constants.enemyWidth, Constants.enemyHeight);
		//		g.drawImage(banana, posX, posY, 100, 100);
	}

	@Override
	public void keyPressed(KeyEvent key) {
		System.out.println("Trycker på " + key.getCode() + " i PlayState");

		if (key.getCode() == KeyCode.ESCAPE) {

			model.switchState(new MenuState(model));
		} else if (key.getCode() == KeyCode.UP) {
			
			if (playerPosY < 265) {
				return;
			}

			playerPosY -= 175;
			


		}


	}

	@Override
	public void update() {
		
		enemyPosX -= 10;
		
		if (playerPosY < 265) {
			
			playerPosY += 5;
			
		}
		
		
		// Here one would probably instead move the player and any
		// enemies / moving obstacles currently active.
	}

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


