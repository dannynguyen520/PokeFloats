package controller_view;

import java.util.Observer;

import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import model.BattleObject;

public class SafariBattleView extends Canvas implements Observer {
	BattleObject battle;
	MediaPlayer media;
	Image background;
	BorderPane pane;
//	Canvas canvas;
	Boolean canAction;
	String curNode;
	private GraphicsContext gc;
	private Timeline timeline;
	public SafariBattleView(BattleObject theBattle){
	pane = new BorderPane();
	battle = theBattle;
	//cur =battle.getCur().getBackImg();
	//getEnemy() = battle.getgetEnemy()().getImg();
	background = new Image("file:images/pokemons/background.jpg");
	canAction = true;
	drawFirstStage();
//showMoves();
//canAction = true;
//curNode = "Fight";
}
	
public Image getEnemy(){
	return battle.getEnemy().getImg();
}
public Image getCur(){
	return new Image("file:images/brendon/trainer.png");
}
public boolean canAction(){
	return canAction;
}
public BattleObject getBattle(){
	return battle;
}
public void runMsg(){
	defaultDraw();
	if(battle.getEnemyRan()){
		gc.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 24));
		gc.setStroke(Color.BLACK);
		gc.fillText("Successful enemy run.", 30, 310);
	}
	else if(battle.getRan()){
		gc.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 24));
		gc.setStroke(Color.BLACK);
		gc.fillText("Successful trainer run.", 30, 310);
	}
	else{
		gc.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 24));
		gc.setStroke(Color.BLACK);
		gc.fillText("Failed run attempt.", 30, 310);
	}
}
public void canAct(){
	canAction = true;
}
	public void defaultDraw(){
		//this = new Canvas();
		this.setWidth(560);
		this.setHeight(380);
		gc = this.getGraphicsContext2D();
		gc.clearRect(0, 0, 560, 380);//reset rect
		gc.setFont(new Font(12));
		
		gc.drawImage(background, 0, 0);
		gc.drawImage(getCur(), 58, 100);
		gc.drawImage(getEnemy(), 335, 0);
		//
		gc.fillText(battle.getEnemy().getName(), 52, 61);
		gc.fillText("Trainer", 342, 194);
		gc.fillText(battle.getEnemy().getLevel()+"", 214, 65); 	
		updateHpBars();
		gc.setFill(Color.BLACK);
		gc.setStroke(Color.BLACK);
	}
	public void catchMsg(){
		gc.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 24));
		gc.setStroke(Color.BLACK);
		if(battle.getEnemy().isCaught()){
			gc.fillText("The catch attempt was\n a success!", 30, 310);
		}
		else{
			gc.fillText("The catch attempt failed", 30, 310);
		}
	}
	public void updateHpBars(){
		double temp = ((battle.getEnemy().getHealth()/battle.getCur().getMaxHp())*100); //
		gc.setFill(Color.MEDIUMSEAGREEN);
		if(temp>0){
			temp+=13;
		}
		gc.fillRect(120, 77, temp, 7);
		
		temp = ((battle.getCur().getHealth()/battle.getEnemy().getMaxHp())*100);
		if(temp>0){
			temp+=13;
		}
		gc.fillRect(403, 209, temp, 7);
	}
	
	public void drawFirstStage(){
		defaultDraw();
		gc.drawImage(new Image("file:images/pokemons/ballbaitrock.jpg"), 282,272);
		new Font(24);
		gc.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 24));
		gc.setStroke(Color.BLACK);
		gc.fillText("What will Trainer"+ "\ndo?", 30, 310);
	}
	public void firstRect(){
		gc.strokeRect(284, 275, 115, 35);
	}
	public void secondRect(){
		gc.strokeRect(399, 275, 115, 35);
	}
	public void thirdRect(){
		gc.strokeRect(284, 317, 115, 35);
	}
	public void fourthRect(){
		gc.strokeRect(399, 317, 115, 35);
	}



	@Override
	public void update(java.util.Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
