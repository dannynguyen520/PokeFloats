package abilityAnimations;

import controller_view.BattleView;
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

public class PoundAnimation {
	BattleView bView;
	boolean isEnemyPoke;
	private Timeline timeline;
	private GraphicsContext gc;
	private int count;
	Image pound;
	EventHandler<? super KeyEvent> t;
	Scene temp;

	public PoundAnimation(BattleView x, boolean isEnemy, Scene scene) {
		t = scene.getOnKeyPressed();
		temp = scene;
		scene.setOnKeyPressed(null);
		isEnemyPoke = isEnemy;
		count = 1;
		bView = x;
		pound = new Image("file:images/moves/pound.png");
		gc = bView.getGraphicsContext2D();
		timeline = new Timeline(new KeyFrame(Duration.millis(100), new AnimateStarter()));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.setOnFinished(new EndOfAnimation());
	}

	public void animate() {
		timeline.play();
	}

	private class AnimateStarter implements EventHandler<ActionEvent> {
		public AnimateStarter() {
			bView.defaultDraw();
			gc.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 24));
			gc.setStroke(Color.BLACK);
			if (!isEnemyPoke) {
				gc.fillText(bView.getCurName() + " used pound!", 30, 310);
			} else {
				gc.fillText(bView.getEnemyName() + " used pound!", 30, 310);
			}

		}

		@Override
		public void handle(ActionEvent event) {
			// TODO Auto-generated method stub
			if (count == 13) {
				bView.defaultDraw();
				temp.setOnKeyPressed(t);
				timeline.stop();
				return;
			}

			if (count % 3 == 0) {
				if (!isEnemyPoke) {
					bView.defaultViewNoEnemy();
					gc.drawImage(bView.getEnemy(), 335, 0);
				} else {
					gc.drawImage(bView.getCur(), 58, 125);
				}
			} else if (count % 2 == 0) {
				if (!isEnemyPoke) {
					bView.defaultViewNoEnemy();
					gc.drawImage(bView.getEnemy(), 332, 0);
				} else {
					gc.drawImage(bView.getCur(), 55, 125);
				}
			} else {
				if (!isEnemyPoke) {
					bView.defaultViewNoEnemy();
					gc.drawImage(bView.getEnemy(), 338, 0);
				} else {
					gc.drawImage(bView.getCur(), 61, 125);
				}
			}
			gc.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 24));
			gc.setStroke(Color.BLACK);
			if (!isEnemyPoke) {
				gc.drawImage(pound, 350, 50);
				gc.fillText(bView.getCurName() + " used pound!", 30, 310);
			} else {
				gc.drawImage(pound, 64, 125);
				gc.fillText(bView.getEnemyName() + " used pound!", 30, 310);
			}
			count++;
		}
	}

	private class EndOfAnimation implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			// TODO Auto-generated method stub
			bView.defaultDraw();
			if (isEnemyPoke) {
				bView.canAct();
			}
		}
	}
}
