package controller_view;

import java.util.ArrayList;

import abilityAnimations.Bait;
import abilityAnimations.Catch;
import abilityAnimations.CatchSafari;
import abilityAnimations.Rock;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.BattleObject;
import model.Item;
import model.Pokemon;
import pokemon.Diglett;
import pokemon.Pikachu;

public class SafariViewTest extends Application {
	BorderPane pane;
	int curNodeS;
	BattleObject battle;
	SafariBattleView safariView;
	String curPanelS;
	Scene scene;
	 public static void main(String[] args) {
		    launch(args);
		  }
		@Override
		public void start(Stage primaryStage) throws Exception {
			// TODO Auto-generated method stub
			pane = new BorderPane();
			curNodeS = 1;
			curPanelS = "Start";
			ArrayList<Pokemon> pokes = new ArrayList<>();
			ArrayList<Item> items = new ArrayList<>();
			pokes.add(new Diglett(null,null));
			battle = new BattleObject(true, pokes, items, new Pikachu(null,null));
			safariView = new SafariBattleView(battle);
			
			safariView.firstRect();
			//new DoubleSlapAnimation(safariView, true).animate();
			pane.setCenter(safariView);
			scene = new Scene(pane, 600, 400);
			scene.setOnKeyPressed(new SafariPressListener());
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		

		class SafariPressListener implements EventHandler<KeyEvent>{

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if(battle.isOver()){
					//return control
					return;
				}
				if(curNodeS==0){
					if(event.getCode()==KeyCode.ENTER){
						if(safariView.getBattle().isEnemyTurn()){
							battle.enemyRun();
							safariView.runMsg();
							curNodeS=0;
							return;
						}
						else{
							curNodeS=1;
							safariView.drawFirstStage();
							return;
						}
					}
					else{
						return;
					}
				}
				
					if(event.getCode() == KeyCode.ENTER){
						if(curNodeS==1){
							safariView.getBattle().safariCatch();
							new CatchSafari(safariView, scene).animate();
							curNodeS=0;
							return;
						}
						else if(curNodeS==2){
							safariView.getBattle().useItem(new Item("Bait"));
							new Bait(safariView, scene).animate();
							curNodeS=0;
							return;
						}
						else if(curNodeS==3){
							safariView.getBattle().useItem(new Item("Rock"));
							new Rock(safariView, scene).animate();
							curNodeS=0;
							return;
						}
						else{
							safariView.getBattle().run();
							safariView.runMsg();
							curNodeS =0;
								//safariView.defaultDraw();
								
								return;
								//return control
							
						}
					}
					safariView.drawFirstStage();
					if (event.getCode()==KeyCode.RIGHT){ //fx
						if(curNodeS==1) {
							curNodeS =2;
							safariView.secondRect();
							return;
						}
						if(curNodeS==3){
							curNodeS = 4;
							safariView.fourthRect();
							return;
						}
					}
					if (event.getCode()==KeyCode.LEFT){ //fx
						if(curNodeS==2) {
							curNodeS =1;
							safariView.firstRect();
							return;
						}
						if(curNodeS==4){
							curNodeS = 3;
							safariView.thirdRect();
							return;
						}
					}
					if (event.getCode()==KeyCode.DOWN){
						if(curNodeS==1) {
							curNodeS =3;
							safariView.thirdRect();
							return;
						}
						if(curNodeS==2){
							curNodeS = 4;
							safariView.fourthRect();
						}
						return;
					}
					if(event.getCode()==KeyCode.UP){
						if(curNodeS==3) {
							curNodeS =1;
							safariView.firstRect();
							return;
						}
						if(curNodeS==4){
							curNodeS = 2;
							safariView.secondRect();
							return;
						}
				}
				if(curNodeS==1){
					safariView.firstRect();
				}
				else if(curNodeS==2){
					safariView.secondRect();
				}
				else if(curNodeS==3){
					safariView.thirdRect();
				}
				else{
					safariView.fourthRect();
				}
			}
		}
}
