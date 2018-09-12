package abilityAnimations;

import controller_view.SafariBattleView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class CatchSafari {
	SafariBattleView bView;
	boolean isEnemyPoke;
	private Timeline timeline;
	private GraphicsContext gc;
	private int count;
	Image catchball;
	EventHandler<? super KeyEvent> t;
	Scene temp;

	public CatchSafari(SafariBattleView x, Scene scene) {
		t = scene.getOnKeyPressed();
		temp = scene;
		scene.setOnKeyPressed(null);
		count = 1;
		bView = x;
		catchball = new Image("file:images/pokemons/catchball.png");
		gc = bView.getGraphicsContext2D();
		timeline = new Timeline(new KeyFrame(Duration.millis(300), new AnimateStarter()));
		timeline.setCycleCount(Animation.INDEFINITE);

	}

	public void animate() {
		timeline.play();
	}

	private class AnimateStarter implements EventHandler<ActionEvent> {
		public AnimateStarter() {
			bView.defaultDraw();
			gc.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 24));
			gc.setStroke(Color.BLACK);
			gc.fillText("Trainer used a pokeball!", 30, 310);

		}

		@Override
		public void handle(ActionEvent event) {
			// TODO Auto-generated method stub
			if (count == 4) {
				bView.defaultDraw();
				bView.catchMsg();
				temp.setOnKeyPressed(t);
				timeline.stop();
				return;
			}
			bView.defaultDraw();
			if (count == 1) {
				gc.drawImage(catchball, 208, 127);
			} else if (count == 2) {
				gc.drawImage(catchball, 240, 70);
			} else {
				gc.drawImage(catchball, 280, 50);
			}
			gc.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 24));
			gc.setStroke(Color.BLACK);
			gc.fillText("Trainer used a pokeball!", 30, 310);
			count++;
		}
	}
}
