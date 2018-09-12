package test.GUI;

import java.awt.Point;
import java.util.Random;

import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Map.Direction;
import model.Map.Map;

public class MapTestTownGUI extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private Map town;
	private Point player;
	private Canvas drawArea;
	private GraphicsContext gc;
	private Image background, bren;
	private BorderPane all;
	private Point camera;
	private boolean up, down, left, right = false;
	private int frame;
	private Direction curr;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		town = new Map("town.txt", new Point(14, 17));
		player = new Point(14, 17);
		camera = new Point(14*16, 17*16);
		drawArea = new Canvas(894, 596);
		all = new BorderPane();
		all.setTop(drawArea);
		background = new Image("file:images/map/town.png");
		bren = new Image("file:images/brendon/bren.png");
		curr = Direction.DOWN;
		frame = 0;
		
		gc = drawArea.getGraphicsContext2D();

		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20), new AnimationStarter()));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
		
		Scene scene = new Scene(all, 894, 596);
		scene.setOnKeyPressed(new KeyPressedListener());
		scene.setOnKeyReleased(new KeyReleasedListener());
		primaryStage.setScene(scene);
	    primaryStage.show();
		
	    update();
	    
	}
	
	private class AnimationStarter implements EventHandler<ActionEvent>{
		
		@Override
		public void handle(ActionEvent arg0) {
			// TODO Auto-generated method stub
			gc.setFill(Color.BLACK);
			gc.fillRect(0, 0, 894, 596);
			gc.drawImage(background,  camera.x - 152, camera.y - 80, 447, 298, 0, 0, 894, 596);

			frame++;

			if (!up && !down && !left && !right)
				frame = 0;
			else if (frame % 17 == 0)
				move(curr);

			moveCamera();

			animate(frame);

		}

	}

	private void animate(int frame) {

		int index = 0;
		frame = frame % 51;
		if (frame == 0)
			index = 0;
		else if (frame > 0 && frame <= 17)
			index = 1;
		else if (frame > 17 && frame <= 34)
			index = 2;

		switch (curr) {
		case UP:
			gc.drawImage(bren, 15, 22 * index, 15, 22, 447 - 13, 298 - 21, 26, 42);
			break;
		case LEFT:
			gc.drawImage(bren, 30, 22 * index, 15, 22, 447 - 13, 298 - 21, 26, 42);
			break;
		case DOWN:
			gc.drawImage(bren, 0, 22 * index, 15, 22, 447 - 13, 298 - 21, 26, 42);
			break;
		case RIGHT:
			gc.drawImage(bren, 45, 22 * index, 15, 22, 447 - 13, 298 - 21, 26, 42);
			break;
		default:

		}

	}

	public void moveCamera() {

		if (camera.x == player.x * 16 && camera.y == player.y * 16)
			return;

		if (camera.x > player.x * 16)
			camera.x -= 1;

		if (camera.y > player.y * 16)
			camera.y -= 1;

		if (camera.x < player.x * 16)
			camera.x += 1;

		if (camera.y < player.y * 16)
			camera.y += 1;

	}

	public void update() {

		if (town.getMap()[player.y][player.x].getElement().equals("Door"))
			System.out.println("GOING IN A DOOR");

		if (new Random().nextDouble() <= town.getMap()[player.y][player.x].getEncounterRate())
			System.out.printf("\t!!!!!!!!!!\n\tI see a pokemon!\n\t!!!!!!!!!!\n");

	}

	private void move(Direction to) {
		int target;

		switch (to) {
		case UP:
			target = player.y - 1;
			if (target >= 0 && town.getMap()[target][player.x].canWalk()) {
				player.y = target;
				// System.out.printf("New location: [%s] [%s]\n", player.y, player.x);
				update();
			}
			break;
		case LEFT:
			target = player.x - 1;
			if (target >= 0 && town.getMap()[player.y][target].canWalk()) {
				player.x = target;
				// System.out.printf("New location: [%s] [%s]\n", player.y, player.x);
				update();
			}
			break;
		case DOWN:
			target = player.y + 1;
			if (target < (town.getMap()).length && town.getMap()[target][player.x].canWalk()) {
				player.y = target;
				// System.out.printf("New location: [%s] [%s]\n", player.y, player.x);
				update();
			}
			break;
		case RIGHT:
			target = player.x + 1;
			if (target < (town.getMap()[0]).length && town.getMap()[player.y][target].canWalk()) {
				player.x = target;
				// System.out.printf("New location: [%s] [%s]\n", player.y, player.x);
				update();
			}
			break;
		default:

		}

	}

	/*
	 * Listener for the arrow keys
	 */
	private class KeyPressedListener implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent event) {
			// TODO Auto-generated method stub
			// Match the input key with the arrows

			switch (event.getCode()) {
			case UP:
				curr = Direction.UP;
				up = true;
				break;
			case LEFT:
				curr = Direction.LEFT;
				left = true;
				break;
			case DOWN:
				curr = Direction.DOWN;
				down = true;
				break;
			case RIGHT:
				curr = Direction.RIGHT;
				right = true;
				break;
			default:

			}

		}
	}

	private class KeyReleasedListener implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent event) {
			// TODO Auto-generated method stub
			// Match the input key with the arrows

			switch (event.getCode()) {
			case UP:
				up = false;
				break;
			case LEFT:
				left = false;
				break;
			case DOWN:
				down = false;
				break;
			case RIGHT:
				right = false;
				break;
			default:

			}
		}
	}

}
