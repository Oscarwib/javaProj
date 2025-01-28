package states;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import constants.Constants;

/**
 * @author oscarwiberg, filipyhde
 * This state represents the menu of the Game The main responsibility of this
 * class is to allow the user to swap state to the PlayState
 */
public class MenuState extends GameState {
	
	private String informationText;
	private Color bgColor;
	private Color fontColor;
	private PlayState playState;
	private Image img1;
	private Image img2;
	private boolean mode1;

	public MenuState(GameModel model) {
		super(model);
		informationText = "Welcome to the Dino game!\nTo play, press:\n1 for mode 1\n2 for mode 2\nEscape to exit game";
		bgColor = Color.GREY;
		fontColor = Color.LIGHTBLUE;

		try {

			img1 = new Image(new FileInputStream(Constants.playerImg));
			img2 = new Image(new FileInputStream(Constants.playerImg2));
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
		g.setFont(new Font(30));
		g.fillText(informationText, Constants.screenWidth/3, Constants.screenHeight / 4);
		drawRotatedImage(g, img1, 45, 0, 100, 450, 450);
		drawRotatedImage(g, img2, -45, Constants.screenWidth - 450, 100, 450, 450);
	}


	private void drawRotatedImage(GraphicsContext g, Image image, double angle, double topLeftX, double topLeftY, double width, double height) {
		g.save(); // Save the current graphics context state
		rotate(g, angle, topLeftX + width / 2, topLeftY + height / 2);
		g.drawImage(image, topLeftX, topLeftY, width, height);
		g.restore(); // Restore the graphics context to its original state
	}

	private void rotate(GraphicsContext gc, double angle, double px, double py) {
		Rotate r = new Rotate(angle, px, py);
		gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
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

