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

public class AirSlashAnimation {
	BattleView bView;
	boolean isEnemyPoke;
	private Timeline timeline;
	private GraphicsContext gc;
	private int count;
	Image airslash1, airslash2;
	EventHandler<? super KeyEvent> t;
	Scene temp;
	public AirSlashAnimation(BattleView x, boolean isEnemy, Scene scene){
		isEnemyPoke = isEnemy;
		 t = scene.getOnKeyPressed();
		temp = scene;
		scene.setOnKeyPressed(null);
		count =1;
		bView = x;
		airslash1 = new Image("file:images/moves/airslash1.png");
		airslash2 = new Image("file:images/moves/airslash2.png");
		gc = bView.getGraphicsContext2D();
		timeline = new Timeline(new KeyFrame(Duration.millis(500), new AnimateStarter()));
		timeline.setCycleCount(Animation.INDEFINITE);

	}
	public void animate(){
		timeline.play();
	}
	
	 private class AnimateStarter implements EventHandler<ActionEvent> {
		public AnimateStarter(){
			bView.defaultDraw();
			gc.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 24));
			gc.setStroke(Color.BLACK);
			if(!isEnemyPoke){
				gc.fillText(bView.getCurName()+ " used airslash!", 30, 310);
			}
			else{
				gc.fillText(bView.getEnemyName()+ " used airslash!", 30, 310);
			}
			
		}
		@Override
		public void handle(ActionEvent event) {
			// TODO Auto-generated method stub
			if(count==3){
				bView.defaultDraw();
				bView.canAct();
				temp.setOnKeyPressed(t);
				timeline.stop();
				return;
			}
			bView.defaultDraw();
			if(count==1){
				if(!isEnemyPoke){
					
					gc.drawImage(airslash1, 300, 120);
				}
				else{
					gc.drawImage(airslash1, 208, 127);
				}
			}
			else{
				if(!isEnemyPoke){
					gc.drawImage(airslash2, 240, 182);
				}
				else{
					gc.drawImage(airslash2, 240, 182);
				}
			}
	
			gc.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 24));
			gc.setStroke(Color.BLACK);
			if(!isEnemyPoke){
				gc.fillText(bView.getCurName()+ " used airslash!", 30, 310);
			}
			else{
				gc.fillText(bView.getEnemyName()+ " used airslash!", 30, 310);
			}
			count++;
		}
	 }
}
