package controller_view;

import java.util.ArrayList;

import abilityAnimations.AirSlashAnimation;
import abilityAnimations.Catch;
import abilityAnimations.CrunchAnimation;
import abilityAnimations.DoubleSlapAnimation;
import abilityAnimations.PoundAnimation;
import abilityAnimations.PsybeamAnimation;
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

public class BattleViewTest extends Application{
	BorderPane pane;
	Boolean canAction;
	int curNode;
	BattleObject battle;
	BattleView bView;
	String curPanel;
	Scene scene;
	 public static void main(String[] args) {
		    launch(args);
		  }
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		pane = new BorderPane();
		canAction = true;
		curNode = 1;
		curPanel = "Start";
		ArrayList<Pokemon> pokes = new ArrayList<>();
		ArrayList<Item> items = new ArrayList<>();
		pokes.add(new Diglett(null,null));
		battle = new BattleObject(true, pokes, items, new Pikachu(null,null));
		bView = new BattleView(battle);
		bView.firstRect();
		//new DoubleSlapAnimation(bView, true).animate();
		pane.setCenter(bView);
		scene = new Scene(pane, 600, 400);
		scene.setOnKeyPressed(new BattlePressListener());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	 class BattlePressListener implements EventHandler<KeyEvent>{

		@Override
		public void handle(KeyEvent event) {
			// TODO Auto-generated method stub
			if(bView.canAction()){ //don't allow inputs during animation
				if(event.getCode() == KeyCode.ENTER){
					if(curPanel.equals("Start")){
						if(curNode==1){
							bView.showMoves();
							bView.firstRect();
							curPanel = "Moves";
							return;
						}
						else if(curNode==2){
							//bView.showItems();  will show items later
							bView.drawItemView();
							curPanel = "Items";
							return;
						}
						else if(curNode==3){
							bView.drawPokemon();// will look at pokemon
						}
						else{
							//bView.showRunAttempt();  //will show text w/ run information (success/fail)
						}
					}
					if(curPanel.equals("Moves")){
						if(curNode==1){
							canAction = false;
							bView.getBattle().useAbility(bView.getBattle().getCur().getAbilities().get(0), bView.getBattle().getCur(), bView.getBattle().getEnemy());
							runAnimation(bView.getBattle().getCur().getAbilities().get(0), bView, false);
						}
						else if(curNode==2){
							canAction=false;
							bView.getBattle().useAbility(bView.getBattle().getCur().getAbilities().get(1), bView.getBattle().getCur(), bView.getBattle().getEnemy());
							runAnimation(bView.getBattle().getCur().getAbilities().get(1), bView,false);//second ability
						}
						else if(curNode==3){
							canAction=false;
							bView.getBattle().useAbility(bView.getBattle().getCur().getAbilities().get(2), bView.getBattle().getCur(), bView.getBattle().getEnemy());
							runAnimation(bView.getBattle().getCur().getAbilities().get(2), bView,false);//third ability
						}
						else{
							canAction=false;
							bView.getBattle().useAbility(bView.getBattle().getCur().getAbilities().get(3), bView.getBattle().getCur(), bView.getBattle().getEnemy());
							runAnimation(bView.getBattle().getCur().getAbilities().get(3), bView,false);//fourth ability
						}
						//runAnimation(bView.getBattle().enemyTurn(), bView, true);
						if(bView.getBattle().isOver()){
							//switch view to controller
							
						}
						if(bView.getBattle().getCur().getHealth()==0){
							int i =0;
							while(bView.getBattle().getPokemonList().get(i).getHealth()==0){
								i++;
							}
							bView.getBattle().switchPokemon(i);
						}
						curNode=1;
						curPanel="Start";
						bView.drawFirstStage();
						bView.firstRect();
					
						return;
					}
					if(curPanel.equals("Pokemon")){
						if(curNode==2){
							
						}
					}
					if(curPanel.equals("Items")){
						bView.getBattle().safariCatch();
						new Catch(bView, scene).animate();
						if(bView.getBattle().isOver()){
							//trainer.addPokemon
							//give control back
						}
						
					}
				}
				if(event.getCode()==KeyCode.ESCAPE){
					bView.drawFirstStage();
					curNode=1;
					curPanel = "Start";
				}
				if(event.getCode()==KeyCode.UP){
					if(curNode==3) {
						curNode =1;
						if(curPanel.equals("Start")){
							bView.drawFirstStage();
							bView.firstRect();
						}
						else if(curPanel.equals("Moves")){
							bView.showMoves();
							bView.firstRect();
						}
						return;
					}
					if(curNode==4){
						curNode = 2;
						if(curPanel.equals("Start")){
							bView.drawFirstStage();
							bView.secondRect();
						}
						else if(curPanel.equals("Moves")){
							bView.showMoves();
							bView.secondRect();
						}
					}
					return;
				}
				if (event.getCode()==KeyCode.DOWN){
					if(curNode==1) {
						curNode =3;
						if(curPanel.equals("Start")){
							bView.drawFirstStage();
							bView.thirdRect();
						}
						else if(curPanel.equals("Moves")){
							bView.showMoves();
							bView.thirdRect();
						}
						return;
					}
					if(curNode==2){
						curNode = 4;
						if(curPanel.equals("Start")){
							bView.drawFirstStage();
							bView.fourthRect();
						}
						else if(curPanel.equals("Moves")){
							bView.showMoves();
							bView.fourthRect();
						}
					}
					return;
				}
				if (event.getCode()==KeyCode.RIGHT){ //fx
					if(curNode==1) {
						curNode =2;
						if(curPanel.equals("Start")){
							bView.drawFirstStage();
							bView.secondRect();
						}
						else if(curPanel.equals("Moves")){
							bView.showMoves();
							bView.secondRect();
						}
						return;
					}
					if(curNode==3){
						curNode = 4;
						if(curPanel.equals("Start")){
							bView.drawFirstStage();
							bView.fourthRect();
						}
						else if(curPanel.equals("Moves")){
							bView.showMoves();
							bView.fourthRect();
						}
						return;
					}
				}
				if (event.getCode()==KeyCode.LEFT){ //fx
					if(curNode==2) {
						curNode =1;
						if(curPanel.equals("Start")){
							bView.drawFirstStage();
							bView.firstRect();
						}
						else if(curPanel.equals("Moves")){
							bView.showMoves();
							bView.firstRect();
						}
						return;
					}
					if(curNode==4){
						curNode = 3;
						if(curPanel.equals("Start")){
							bView.drawFirstStage();
							bView.thirdRect();
						}
						else if(curPanel.equals("Moves")){
							bView.showMoves();
							bView.thirdRect();
						}
						return;
					}
				}
			}
		}
		
		
		public void runAnimation(String ab, BattleView bV, boolean isEnemy){
			if (ab.equals("Pound")) {
				new PoundAnimation(bView, isEnemy, scene).animate();
			}
			if (ab.equals("Air Slash")) {
				new AirSlashAnimation(bView, isEnemy, scene).animate();
			}
			if (ab.equals("Psybeam")) {
				new PsybeamAnimation(bView, isEnemy, scene).animate();
			}
			if (ab.equals("Crunch")) {
				new CrunchAnimation(bView, isEnemy, scene).animate();
			}
			if (ab.equals("Hyper Beam")) {
				
			}
			if (ab.equals("Dragon's Dance")) {
				
			}
			if (ab.equals("Double Slap")) {
				new DoubleSlapAnimation(bView, isEnemy, scene).animate();
			}
			if (ab.equals("Tackle")) {
				
			}
			if (ab.equals("Quick Attack")) {
				
			}
			if (ab.equals("Scratch")) {
				
			}
			if (ab.equals("Absorb")) {
				
			}
			if (ab.equals("Water Gun")) {
				
			}
			if (ab.equals("Horn Attack")) {
				
			}
			if (ab.equals("Drill Run")) {
				
			}
			if (ab.equals("Thunderbolt")) {
				
			}
			if (ab.equals("Thunder")) {
				
			}
			if(ab.equals("Run")){
				
			}
		}
	}
	
	
}
