package test.GUI;

import java.awt.Point;

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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Map.Direction;


public class CharAnimationTest extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	
	
	private Canvas drawArea;
	private GraphicsContext gc;
	private BorderPane all;
	private Image bren;
	private Direction curr;
	private boolean up,down,left,right = false;
	private int frame;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		drawArea = new Canvas(894, 596);
		all = new BorderPane();
		all.setTop(drawArea);
		
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
		
	}

	
	private class AnimationStarter implements EventHandler<ActionEvent>{
		
		@Override
		public void handle(ActionEvent arg0) {
			// TODO Auto-generated method stub
			gc.clearRect(0, 0, 894, 596);
			
			frame++;
			
			if (!up && !down && !left && !right)
				frame = 0;

			animate(frame%3);
			
		}
	}
	
	private void animate(int frame) {
		
		switch (curr) {
		case UP:
			gc.drawImage(bren,  15 ,22*frame, 15, 22, 447 - 13, 298 - 21, 26, 42);
			break;
		case LEFT:
			gc.drawImage(bren,  30 ,22*frame, 15, 22, 447 - 13, 298 - 21, 26, 42);
			break;
		case DOWN:
			gc.drawImage(bren,  0 ,22*frame, 15, 22, 447 - 13, 298 - 21, 26, 42);
			break;
		case RIGHT:
			gc.drawImage(bren,  45 ,22*frame, 15, 22, 447 - 13, 298 - 21, 26, 42);
			break;
		default:
		
		}
		
	}

	
	
	private class KeyPressedListener implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent event) {
			// TODO Auto-generated method stub
			// Match the input key with the arrows
			
			
			System.out.println("pressed: "+event.getCode());
			
			
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
			
			
			System.out.println("released: "+event.getCode());
			
			
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
