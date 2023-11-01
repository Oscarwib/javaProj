package states;

import testing.Tester;
import testing.Tester.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;



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
	private Line dottedline;
	private Image banana;
	private Image car;
	private Player player;
	private Point position;


	/* Class only used for testing */
	private Tester tester;

	public class Point {
		double x;
		double y;

		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}

	public PlayState(GameModel model) {
		super(model);
		informationText = "Press Escape To Return To The Menu";
		bgColor = Color.WHITE;
		fontColor = Color.BLUE;
		position = new Point(0, 0);

		try {
			tester = new Tester();
			banana = new Image(new FileInputStream("src/Images1/h-banana.png"));
			car = new Image(new FileInputStream("src/Images1/car.png"));
			player = new Player(Constants.playerImg);
		} catch (FileNotFoundException e) {
			System.out.println("Unable to find image-files!");
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
		g.strokeLine(Constants.screenWidth, 650, 0, 650);
		// Can also use:
		// g.setStroke(fontColor);
		// g.strokeText(informationText, SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);

		// This could be a call to all our objects that we want to draw.
		// Using the tester simply to illustrate how it could work.
		//		tester.delegate(g);
		g.drawImage(car, position.x, position.y, 100, 100);
		g.drawImage(player.getPlayerImageView(), Constants.screenWidth/2-50, 600, 100, 100);
		g.drawImage(banana, position.x, position.y, 100, 100);
	}

	@Override
	public void keyPressed(KeyEvent key) {
		System.out.println("Trycker på " + key.getCode() + " i PlayState");

		if (key.getCode() == KeyCode.ESCAPE) {

			model.switchState(new MenuState(model));
		}

		else {
			player.move(key.getCode());
		}


	}

	@Override
	public void update() {
		// Here one would probably instead move the player and any
		// enemies / moving obstacles currently active.
		tester.update();
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


