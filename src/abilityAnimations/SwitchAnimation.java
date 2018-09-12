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
import model.Pokemon;

public class SwitchAnimation {
	BattleView bView;
	boolean isEnemyPoke;
	private Timeline timeline;
	private GraphicsContext gc;
	private int count;
	int newPoke;
	EventHandler<? super KeyEvent> t;
	Scene temp;

	public SwitchAnimation(BattleView x,Scene scene, int desired) {
		t = scene.getOnKeyPressed();
		temp = scene;
		scene.setOnKeyPressed(null);
		count = 1;
		bView = x;
		newPoke = desired;
		gc = bView.getGraphicsContext2D();
		timeline = new Timeline(new KeyFrame(Duration.millis(400), new AnimateStarter()));
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
			gc.fillText("Trainer switched pokemon!", 30, 310);
		}

		@Override
		public void handle(ActionEvent event) {
			// TODO Auto-generated method stub
			if (count == 6) {
				bView.getBattle().switchPokemon(newPoke);
				bView.defaultDraw();
				temp.setOnKeyPressed(t);
				timeline.stop();
				return;
			}
			
			if (count == 1) {
				bView.defaultDraw();
				gc.drawImage(new Image("file:images/pokemons/catchball.png"), 7, 225);
			} 
			else if (count==2){
				bView.defaultDraw();
				gc.drawImage(new Image("file:images/pokemons/catchball.png"), 7, 225);
				gc.setFill(Color.RED);
				gc.fillRect(45, 232, 37, 20);
			}
			
			else if (count ==3){
				bView.defaultDraw();
				gc.drawImage(new Image("file:images/pokemons/catchball.png"), 7, 225);
				gc.setFill(Color.RED);
				gc.fillRect(45, 232, 70, 20);
			}
			else if (count==4){
				bView.defaultViewNoCur();
				
			}
			else if (count==5){
				bView.defaultViewNoCur();
				
			}
			gc.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 24));
			gc.setStroke(Color.BLACK);
			gc.fillText("Trainer switched pokemon!", 30, 310);
			count++;
		}
	}
}
