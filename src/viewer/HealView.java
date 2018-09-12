package viewer;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Duration;
import model.GameObject;
import model.Map.Direction;

public class HealView extends BorderPane implements Observer {

	private Canvas drawArea;
	private Point player;
	private GraphicsContext gc;
	private Image background, bren;
	private Point camera;
	public boolean moving;
	private int frame;
	private Direction curr;
	private GameObject game;
	private Boolean changingMap;
	
	public HealView(GameObject x){
		// TODO Auto-generated method stub
		game = x;
		player = game.getCurrPlayer().getCurrentLocation();
		camera = new Point(player.x*16, player.y*16);
		drawArea = new Canvas(894, 596);
		this.setCenter(drawArea);
		background = new Image("file:images/map/heal.png");
		bren = new Image("file:images/brendon/bren.png");
		curr = Direction.DOWN;
		frame = 0;
		moving = false;
		
		changingMap = false;
		
		gc = drawArea.getGraphicsContext2D();

		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20), new AnimationStarter()));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	    
	}
	
	private class AnimationStarter implements EventHandler<ActionEvent>{
		
		@Override
		public void handle(ActionEvent arg0) {
			// TODO Auto-generated method stub
			gc.setFill(Color.BLACK);
			gc.fillRect(0, 0, 894, 596);
			
			if(!changingMap)
				gc.drawImage(background,  camera.x - 240, camera.y - 110, 447, 298, 0, 0, 894, 596);

			frame++;
			
			if(!moving)
				frame = 0;

			if(frame > 15)
				frame = 1;
			
			
			moveCamera();

			animate(frame);

		}

	}

	private void animate(int frame) {

		
		int index = 0;
		frame = frame % 51;
		if (frame == 0)
			index = 0;
		else if (frame > 0 && frame <= 7)
			index = 1;
		else if (frame > 7 && frame <= 15)
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

		moving = true;
		
		
		if (camera.x == player.x * 16 && camera.y == player.y * 16) {
			moving = false;
			return;
		}
		
		if (camera.x > player.x * 16) {
			
			if(camera.x - (player.x *16) > 32)
				camera.x = player.x *16;
			else
				camera.x -= 1;
		
		}

		if (camera.y > player.y * 16) {
			
			if(camera.y - (player.y *16) > 32)
				camera.y = player.y *16;
			else
				camera.y -= 1;
			
		}
			
		if (camera.x < player.x * 16) {
			
			if(player.x*16 - camera.x > 32)
				camera.x = player.x*16;
			else
				camera.x += 1;

		}
			
		if (camera.y < player.y * 16) {
			
			if(player.y*16 - camera.y > 32)
				camera.y = player.y*16;
			else
				camera.y += 1;
		}
		
	}
	
	public boolean isMoving() {
		return moving;
	}
	

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		game = (GameObject) o;
		curr = game.getCurrDirection();
		player = game.getCurrPlayer().getCurrentLocation();
		changingMap = game.isMapChanged();

	}

}
