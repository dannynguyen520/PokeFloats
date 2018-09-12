package controller_view;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
//import javafx.scene.layout.GridPane;
//import javafx.stage.Stage;
import model.BattleObject;
import model.Item;
import model.Pokemon;
import pokemon.Diglett;
import pokemon.Pikachu;

public class BattleView extends Canvas implements Observer{
	
	BattleObject battle;
	MediaPlayer media;
	Image background;
	BorderPane pane;
//	Canvas canvas;
	Boolean canAction;
	String curNode;
	private GraphicsContext gc;
	private Timeline timeline;
	public BattleView(BattleObject theBattle){
//		pane = new BorderPane();
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
	public BattleObject getBattle(){
		return battle;
	}
	public Image getEnemy(){
		return battle.getEnemy().getImg();
	}
	public Image getCur(){
		return battle.getCur().getBackImg();
	}
	public boolean canAction(){
		return canAction;
	}
	public void canAct(){
		canAction = true;
	}
	public void stopAct(){
		canAction = false;
	}
	/*@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

		ArrayList<Pokemon> pokes = new ArrayList<>();
		ArrayList<Item> items = new ArrayList<>();
		pokes.add(new Diglett(null,null));
		battle = new BattleObject(true, pokes, items, new Pikachu(null,null));
		cur =battle.getCur().getBackImg();
		getEnemy() = battle.getgetEnemy()().getImg();
		background = new Image("file:images/pokemons/background.jpg");
		
		pane = new BorderPane();
		defaultDraw();
		gc.drawImage(new Image("file:images/pokemons/poketemplate.jpg"), 282,272);
		new Font(24);
		gc.setFont(Font.font("calibri", FontWeight.BOLD, FontPosture.REGULAR, 24));
		//gc.setStroke(p);
		gc.fillText("What will "+battle.getCur().getName()+ "\ndo?", 30, 310);
		gc.setStroke(Color.BLACK);
		gc.strokeRect(284, 275, 120, 35);
		
	
		showMoves();
		hornAttack();
		pane.setCenter(canvas);
		Scene scene = new Scene(pane, 600, 400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	*/
	public String getCurName(){
		return battle.getCur().getName();
	}
	public String getEnemyName(){
		return battle.getEnemy().getName();
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
	public void defaultDraw(){
		//this = new Canvas();
		this.setWidth(560);
		this.setHeight(380);
		gc = this.getGraphicsContext2D();
		gc.clearRect(0, 0, 560, 380);//reset rect
		gc.setFont(new Font(12));
		
		gc.drawImage(background, 0, 0);
		gc.drawImage(getCur(), 58, 95);
		gc.drawImage(getEnemy(), 335, 0);
		//
		gc.fillText(battle.getEnemy().getName(), 52, 61);
		gc.fillText(battle.getCur().getName(), 342, 194);
		gc.fillText(battle.getEnemy().getLevel()+"", 214, 65);
		gc.fillText(battle.getCur().getLevel()+"", 498,197); 	
		updateHpBars();
		gc.setFill(Color.BLACK);
		gc.setStroke(Color.BLACK);
	}
	public void updateHpBars(){
		double temp = ((battle.getEnemy().getHealth()/battle.getEnemy().getMaxHp())*100); //
		gc.setFill(Color.MEDIUMSEAGREEN);
		if(temp>0){
			temp+=13;
		}
		gc.fillRect(120, 77, temp, 7);
		
		temp = ((battle.getCur().getHealth()/battle.getCur().getMaxHp())*100);
		if(temp>0){
			temp+=13;
		}
		gc.fillRect(403, 209, temp, 7);
	}
	public void showMoves(){
		defaultDraw();
		new Font(24);
		gc.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 24));
		gc.drawImage(new Image("file:images/pokemons/pokeAbilities.jpg"), 282,272);
		gc.setStroke(Color.BLACK);
		gc.fillText("What will "+battle.getCur().getName()+ "\ndo?", 30, 310);
		//gc.strokeRect(284, 275, 120, 35);
		gc.setFont(new Font(12));
		String  temp = battle.getCur().getAbilities().get(0); //1st ability
		gc.fillText(temp, 312, 300);
		temp = battle.getCur().getAbilities().get(1); //2nd ability
		gc.fillText(temp, 441, 300);
		temp = battle.getCur().getAbilities().get(2); //3rd ability
		gc.fillText(temp, 312, 340);
		temp = battle.getCur().getAbilities().get(3); //4th ability
		gc.fillText(temp, 441, 340);
		//firstRect();
	}
	public void drawFirstStage(){
		defaultDraw();
		gc.drawImage(new Image("file:images/pokemons/poketemplate.jpg"), 282,272);
		new Font(24);
		gc.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 24));
		gc.setStroke(Color.BLACK);
		gc.fillText("What will "+battle.getCur().getName()+ "\ndo?", 30, 310);

	}
	public void defaultViewNoEnemy(){
		gc = this.getGraphicsContext2D();
		gc.clearRect(0, 0, 560, 380);//reset rect
		gc.setFont(new Font(12));
		
		gc.drawImage(background, 0, 0);
		gc.drawImage(getCur(), 58, 95);
		//
		gc.fillText(battle.getEnemy().getName(), 52, 61);
		gc.fillText(battle.getCur().getName(), 342, 194);
		gc.fillText(battle.getEnemy().getLevel()+"", 214, 65);
		gc.fillText(battle.getCur().getLevel()+"", 498,197); 	
		updateHpBars();
		gc.setFill(Color.BLACK);
		gc.setStroke(Color.BLACK);
	}
	public void defaultViewNoCur(){
		gc = this.getGraphicsContext2D();
		gc.clearRect(0, 0, 560, 380);//reset rect
		gc.setFont(new Font(12));
		
		gc.drawImage(background, 0, 0);
		
		gc.drawImage(getEnemy(), 335, 0);
		//
		gc.fillText(battle.getEnemy().getName(), 52, 61);
		gc.fillText(battle.getCur().getName(), 342, 194);
		gc.fillText(battle.getEnemy().getLevel()+"", 214, 65);
		gc.fillText(battle.getCur().getLevel()+"", 498,197); 	
		updateHpBars();
		gc.setFill(Color.BLACK);
		gc.setStroke(Color.BLACK);
	}
	public void drawItemView(){
		gc = this.getGraphicsContext2D();
		gc.clearRect(0, 0, 560, 380);//reset rect
		this.setWidth(290);
		this.setHeight(380);
		gc.setFont(new Font(12));
		gc.drawImage(new Image("file:images/pokemons/itemback.png"), 0, 0);
		for (int i =0; i<battle.getItems().size();i++){
			gc.fillText(battle.getItems().get(i).getName(), 39, 58+(i*20));
			gc.drawImage(new Image("file:images/Menu/" + battle.getItems().get(i).getName()+".png"), 12, 46+(i*20));
		}
	}
	public void itemFirst(){
		gc.strokeRect(29, 48, 65, 10);
	}
	public void itemSecond(){
		gc.strokeRect(29, 68, 65, 10);
	}
	public void itemThird(){
		gc.strokeRect(29, 88, 65, 10);
	}
	public void itemFourth(){
		gc.strokeRect(29, 108, 65, 10);
	}
	public void firstRect(){
		gc.strokeRect(284, 275, 120, 35);
	}
	public void secondRect(){
		gc.strokeRect(411, 275, 120, 35);
	}
	public void thirdRect(){
		gc.strokeRect(284, 317, 120, 35);
	}
	public void fourthRect(){
		gc.strokeRect(411, 317, 120, 35);
	}
	public void drawPokemon(){
		gc.clearRect(0, 0, 560, 380);//reset rect
		gc.drawImage(new Image("file:images/menus/pokemenu.png"), 0, 0);
		gc.drawImage(new Image("file:images/menus/1stpoke.png"), 190, 44);//64/44
		double temp = ((battle.getCur().getHealth()/battle.getCur().getMaxHp())*100); //
		gc.setFill(Color.MEDIUMSEAGREEN);
		gc.fillRect(303, 55, getTemp(temp), 3);
		gc.setFont(new Font(10));
		gc.strokeText(getBattle().getCur().getName(), 196, 58);
		int i =1;
		while (i<getBattle().getPokemonList().size() && i<=5){
			temp=getBattle().getPokemonList().get(i).getHealth()/getBattle().getPokemonList().get(i).getMaxHp()*100;
			gc.drawImage(new Image("file:images/menus/restpoke.png"), 190, (i*44)+44);
			gc.fillRect(301, 55+(i*44), getTemp(temp), 3);
			gc.strokeText(getBattle().getPokemonList().get(i).getName(), 196, (i*44)+58);
			i++;
		}
		gc.setFill(Color.BLACK);
		//gc.drawImage(new Image(), x, y);
	}
	public void pokeFirst(){
		gc.strokeRect(190, 44, 178, 27);
	}
	public void pokeSecond(){
		gc.strokeRect(190, 44*2+2, 178, 27);
	}
	public void pokeThird(){
		gc.strokeRect(190, 44*3+2, 178, 27);
	}
	public void pokeFourth(){
		gc.strokeRect(190, 44*4+2, 178, 27);
	}
	public void pokeFifth(){
		gc.strokeRect(190, 44*5+2, 178, 27);
	}
	public void pokeSixth(){
		gc.strokeRect(190, 44*6+2, 178, 27);
	}
	
	public double getTemp(double temp){
		if(temp == 100){
			return temp-=38;
		}
		else if (temp>=70){
			return temp-=30;
		}
		else if(temp<=20){
			return temp;
		}
		else{
			return temp-=14;
		}
	}
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}
}

	
