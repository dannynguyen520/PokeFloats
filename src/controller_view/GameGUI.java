package controller_view;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observer;
import java.util.Random;

import abilityAnimations.AirSlashAnimation;
import abilityAnimations.Bait;
import abilityAnimations.Catch;
import abilityAnimations.CatchSafari;
import abilityAnimations.CrunchAnimation;
import abilityAnimations.DoubleSlapAnimation;
import abilityAnimations.PoundAnimation;
import abilityAnimations.PsybeamAnimation;
import abilityAnimations.Rock;
import abilityAnimations.SwitchAnimation;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.BattleObject;
import model.GameObject;
import model.Item;
import model.Pokemon;
import model.Map.Direction;
import model.Map.Map;
import pokemon.Caterpie;
import pokemon.Diglett;
import pokemon.Meowth;
import pokemon.MrMime;
import pokemon.Oddish;
import pokemon.Pidgey;
import pokemon.Pikachu;
import pokemon.Rayquaza;
import pokemon.Rhyhorn;
import pokemon.Slowpoke;
import controller_view.BattleView;
import controller_view.SafariBattleView;
import viewer.CaveView;
import viewer.HealView;
import viewer.IntroView;
import viewer.LoadView;
import viewer.MenuView;
import viewer.SafariView;
import viewer.TownView;
import controller_view.menuview.GameMenu;
import controller_view.menuview.ItemMenu;
import controller_view.menuview.PokemonMenu;
import controller_view.menuview.SaveMenu;
import controller_view.menuview.TrainerStatsMenu;
import controller_view.menuview.UserGuideMenu;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


@SuppressWarnings("restriction")


public class GameGUI extends Application {

	private GameMenu menu;
	private PokemonMenu pokemonMenu;
	private ItemMenu itemMenu;
	private SaveMenu saveMenu;
	private UserGuideMenu guideMenu;
	private TrainerStatsMenu tStatsMenu;
	private boolean menuShowing;
	private boolean menuLocked;
	private boolean pokeMenuShowing;
	private boolean itemMenuShowing;
	private boolean statsMenuShowing;
	private boolean saveMenuShowing;
	private boolean guideShowing;
	private int numPokeballs; 


	private Pokemon toBeCaught;
	String curPanel;
	int curNode;
	String curPanelS;
	int curNodeS;
	private GameObject game;
	private BattleView battleView;
	private SafariBattleView safariView;
	private TownView town;
	private CaveView cave;
	private HealView heal;
	private SafariView safari;
	private IntroView intro;
	private LoadView load;

	private Observer currentView;

	public static final int width = 700;
	public static final int height = 700;


	private MenuKeyListener menuListener;
	private IntroListener introListener;
	private LoadListener loadListener;
	private KeyPressedListener mainListener;

	private Scene scene;
	private StackPane root;
	private BorderPane all;

	private Rectangle fade;
	private FadeTransition trans;

	private MediaTrack mt;


	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		//Music Player
		mt = new MediaTrack();

		//Transition
		fade = new Rectangle(0,0,894,596);
		fade.setFill(Color.BLACK);

		//Listeners
		mainListener = new KeyPressedListener();
		menuListener = new MenuKeyListener();
		introListener = new IntroListener();
		loadListener = new LoadListener();

		//Creating..
		game = new GameObject();
		town = new TownView(game);
		cave = new CaveView(game);
		safari = new SafariView(game);
		heal = new HealView(game);
		curNode = 1;
		curPanel = "Start";
		curNodeS=1;
		curPanelS = "Start";
		//setup intro
		intro = new IntroView();
		load = new LoadView();

		//setup menu
		menu = new GameMenu();
		itemMenu = new ItemMenu(game);
		pokemonMenu = new PokemonMenu(game.getCurrPlayer());
		tStatsMenu = new TrainerStatsMenu(game.getCurrPlayer());
		saveMenu = new SaveMenu();
		guideMenu = new UserGuideMenu();
		menuShowing = false;
		menuLocked = false;
		pokeMenuShowing = false;
		itemMenuShowing = false;
		statsMenuShowing = false;
		saveMenuShowing = false;
		guideShowing = false;
		//-----------------------

		//Setting view
		game.addObserver(town);
		game.addObserver(cave);
		game.addObserver(safari);
		game.addObserver(heal);

		all = new BorderPane();

		root = new StackPane();
		root.getChildren().add(all);

		scene = new Scene(root, 894, 596);
		scene.setOnKeyPressed(introListener);
		//		scene.setOnKeyPressed(mainListener);
		primaryStage.setScene(scene);
		primaryStage.show();

		updateView();
	}

	/**
	 * setView() first checks to see if the current view and the set
	 * view are different. This is meant to avoid replay of music and
	 * replay of transition as the updateView() is used primarily for
	 * trainer movement.
	 *
	 * The Views are:
	 * intro, load, town, cave, safari, and battle
	 *
	 * @param view - the observer that the method switches to
	 */
	public void setView(Observer view) {
		//When a new view is entered, applies transition
		if (currentView != view && currentView != null) {
			if (!game.getCurrState().equals("load")) {
				mt.stop();
			}
			
			
			//music change
			switch(game.getCurrState()) {
			case "walking":
				switch(game.getCurrMap().getMapName()) {
				case "cave.txt":
					mt.playSound("enter");
					mt.playSong("Cave");
					break;
				case "town.txt":
					if (currentView != load)
						mt.playSound("enter");
					mt.playSong("Town");
					break;
				case "safari.txt":
					mt.playSound("enter");
					mt.playSong("SafariZone");
					break;
				default:
					break;
				}
				break;
			case "battle":
				mt.playSong("BattleEncounter");
				break;
			case "safaricatch":
				mt.playSong("BattleEncounter");
				break;
			default:
				break;
			}

			//Added the black square on top of the stack
			if(!root.getChildren().contains(fade)) root.getChildren().add(fade);

			//Make it fade to black
			trans = new FadeTransition(Duration.millis(1000), fade);
			trans.setFromValue(0);
			trans.setToValue(1);
			trans.play();
			
			if (currentView == safari && view == town) 
				System.out.println("GAMEOVER");

			//upon finishing, change the view behind the black square
			trans.setOnFinished(event -> {
				currentView = view;
				all.setCenter((Node) view);

				//Fade into the new view
				trans.setFromValue(1);
				trans.setToValue(0);
				trans.play();

				//Remove fade just in case someone needs to use the stack pane
				trans.setOnFinished(event2 -> {
					root.getChildren().remove(fade);
				});
			});


			//Repeated view
		} else {
			currentView = view;
			all.setCenter((Node) view);
		}

	}

	/**
	 * updateView() reads the current state of the game and then
	 * applies any changes that is necessary to the views.
	 */
	private void updateView() {

		if (game.getCurrState().equals("walking")) {
			if (game.getCurrMap().getMapName().equals("cave.txt")) {
				setView(cave);
			} else if (game.getCurrMap().getMapName().equals("town.txt")) {
				setView(town);
			} else if (game.getCurrMap().getMapName().equals("safari.txt")) {
				setView(safari);
			} else if (game.getCurrMap().getMapName().equals("heal.txt")) {
				setView(heal);
			}
		} else if (game.getCurrState().equals("battle")) {
			toBeCaught = giveRandomPokemon();
			battleView = new BattleView(new BattleObject(true, game.getCurrPlayer().getAllPokemons(), game.getCurrPlayer().getItems(),
					toBeCaught));
			battleView.firstRect();
			setView( battleView);
			scene.setOnKeyPressed(new BattlePressListener());
			//setView()

		} else if(game.getCurrState().equals("safaricatch")){
			toBeCaught = giveRandomSafariPokemon();
			safariView = new SafariBattleView(new BattleObject(false, game.getCurrPlayer().getAllPokemons(),
					game.getCurrPlayer().getItems(), toBeCaught));
			safariView.firstRect();
			setView(safariView);
			scene.setOnKeyPressed(new SafariPressListener());
		}
		else if (game.getCurrState().equals("menu")) {


		} else if (game.getCurrState().equals("intro")) {
			setView(intro);
			mt.playSong("TitleScreen");
			intro.animate();

		}

	}

	private class KeyPressedListener implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent event) {
			// TODO Auto-generated method stub
			// Match the input key with the arrows
			if(town.isMoving())
				return;

			switch (event.getCode()) {
			case UP:
				game.moveTrainer(Direction.UP);
				break;
			case LEFT:
				game.moveTrainer(Direction.LEFT);
				break;
			case DOWN:
				game.moveTrainer(Direction.DOWN);
				break;
			case RIGHT:
				game.moveTrainer(Direction.RIGHT);
				break;
			case M:
				KeyFrame start = null, end = null;
				//if sub menu is open, then menu is locked open
				if(menuLocked) break;
				if(!menuShowing) {
					mt.playSound("menu_open");
					root.getChildren().add(menu);
					start = new KeyFrame(Duration.ZERO, new KeyValue(menu.translateXProperty(), root.getWidth()));
					end = new KeyFrame(Duration.seconds(0.5), new KeyValue(menu.translateXProperty(), root.getWidth() - 150));
					scene.setOnKeyPressed(menuListener);
					Timeline slide = new Timeline(start,end);
					menuShowing = true;
					slide.play();
				}
				break;
			default:
			}

			updateView();
		}
	}

	private class IntroListener implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent e) {
			boolean temp = intro.getPressEnter();
			if (temp) {
				if (e.getCode() == KeyCode.ENTER) {
					mt.playSound("select");
					scene.setOnKeyPressed(loadListener);
					game.setCurrState("load");
					setView(load);
				}
			}
		}

	}

	public void saveData() {
		try {
			FileOutputStream fileOutput = new FileOutputStream("PokemonSavedData");
			ObjectOutputStream out = new ObjectOutputStream(fileOutput);
			out.writeObject(game);
			out.close();

		}catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private class LoadListener implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent e) {
			switch (e.getCode()) {
			case DOWN:
				mt.playSound("select");
				load.changePointer();
				load.updateImage();
				break;
			case UP:
				mt.playSound("select");
				load.changePointer();
				load.updateImage();
				break;
			case ENTER:
				String selection = load.getSelection();
				if (selection.equals("yes")) {
					try {
						FileInputStream fileInput = new FileInputStream("PokemonSavedData");
						ObjectInputStream in = new ObjectInputStream(fileInput);
						game = (GameObject) in.readObject();

						itemMenu = new ItemMenu(game);
						pokemonMenu = new PokemonMenu(game.getCurrPlayer());
						tStatsMenu = new TrainerStatsMenu(game.getCurrPlayer());
						//read them to observer
						game.addObserver(town);
						game.addObserver(safari);
						game.addObserver(cave);

						scene.setOnKeyPressed(mainListener);
						updateView();

						game.updateState();
						System.out.println("Successfully loaded existing data");
						in.close();
					} catch (IOException | ClassNotFoundException e1) {
						System.out.println("No saved file to load!");
					}

				} else if (selection.equals("no")) {
					//Start game
					mt.playSound("select");
					scene.setOnKeyPressed(mainListener);
					game.setCurrState("walking");
					setView(town);
				}
			default:
				break;
			}
		}

	}


	public class MenuKeyListener implements EventHandler<KeyEvent>{
		@Override
		public void handle(KeyEvent event) {
			switch (event.getCode()) {
			case M:
				if(menuLocked) break;
				mt.playSound("menu_open");
				KeyFrame start = new KeyFrame(Duration.ZERO, new KeyValue(menu.translateXProperty(), root.getWidth()-150));
				KeyFrame end = new KeyFrame(Duration.seconds(0.5), new KeyValue(menu.translateXProperty(), root.getWidth()));
				scene.setOnKeyPressed(mainListener);
				Timeline slide = new Timeline(start,end);
				slide.setOnFinished(e -> root.getChildren().remove(menu));
				slide.play();
				menuShowing = false;
				break;
			case ALT:
				if(itemMenuShowing) itemMenu.useItem();
				break;
			case DOWN:
				mt.playSound("select");

				if(saveMenuShowing) {
					saveMenu.changePointer();
					break;
				}

				menu.handleNavigation(2);
				break;
			case UP:
				mt.playSound("select");

				if(saveMenuShowing) {
					saveMenu.changePointer();
					break;
				}

				menu.handleNavigation(1);
				break;
			case RIGHT:
				if(guideShowing) guideMenu.continueFill();
				if(itemMenuShowing) itemMenu.highlightItemIncrease();
				break;
			case LEFT:
				if(guideShowing) guideMenu.reverseFill();
				if(itemMenuShowing) itemMenu.highlightItemDecrese();
				break;
			case ENTER:
				mt.playSound("select");

				if(saveMenuShowing) {
					if (saveMenu.getMode() == 1) {
						if (saveMenu.getSelection().equals("yes")) {
							//See if file already exist or not
							if (saveMenu.checkIfFileExists()) {
								saveMenu.changeText();
							} else {
								//save the file
								saveData();
								root.getChildren().remove(saveMenu);
							}
						} else {
							//remove pane from the stack
							root.getChildren().remove(saveMenu);
						}
						//Mode 2: Do you want to overwrite saved game?
					} else if (saveMenu.getMode() == 2) {
						if (saveMenu.getSelection().equals("yes")) {
							//save file
							saveData();
							root.getChildren().remove(saveMenu);
						} else {
							saveMenu.changeText();
						}
					}
					saveMenuShowing = !saveMenuShowing;
					menuLocked = !menuLocked;
					break;
				}
				String option = menu.getOptionSelected();
				//handle menu options
				if(option.equals("Pokemon") || option.equals("Items") || option.equals("Stats")) {
					if(option.equals("Pokemon")) {
						if(!pokeMenuShowing) {
							pokemonMenu.updatePokemon();
							root.getChildren().add(pokemonMenu);
							start = new KeyFrame(Duration.ZERO, new KeyValue(pokemonMenu.translateYProperty(), root.getHeight()));
							end = new KeyFrame(Duration.seconds(0.5), new KeyValue(pokemonMenu.translateYProperty(), root.getHeight()- 200));
							menuLocked = true;
						}else {
							start = new KeyFrame(Duration.ZERO, new KeyValue(pokemonMenu.translateYProperty(), root.getHeight() - 200));
							end = new KeyFrame(Duration.seconds(0.5), new KeyValue(pokemonMenu.translateYProperty(), root.getHeight()));
							menuLocked = false;
						}
						Timeline sl = new Timeline(start, end);
						if(pokeMenuShowing) sl.setOnFinished(e -> root.getChildren().remove(pokemonMenu));
						pokeMenuShowing = !pokeMenuShowing;
						sl.play();
					}else if(option.equals("Items")){
						if(!itemMenuShowing) {
							itemMenu.updateItems();
							root.getChildren().add(itemMenu);
							start = new KeyFrame(Duration.ZERO, new KeyValue(itemMenu.translateYProperty(), root.getHeight()));
							end = new KeyFrame(Duration.seconds(0.5), new KeyValue(itemMenu.translateYProperty(), root.getHeight()- 200));
							menuLocked = true;
						}else {
							start = new KeyFrame(Duration.ZERO, new KeyValue(itemMenu.translateYProperty(), root.getHeight() - 200));
							end = new KeyFrame(Duration.seconds(0.5), new KeyValue(itemMenu.translateYProperty(), root.getHeight()));
							menuLocked = false;
						}
						Timeline sl = new Timeline(start, end);
						if(itemMenuShowing) sl.setOnFinished(e -> root.getChildren().remove(itemMenu));
						itemMenuShowing = !itemMenuShowing;
						sl.play();
					} else if (option.equals("Stats")){
						if(!statsMenuShowing) {
							tStatsMenu.updateStats();
							root.getChildren().add(tStatsMenu);
							start = new KeyFrame(Duration.ZERO, new KeyValue(tStatsMenu.translateYProperty(), root.getHeight()));
							end = new KeyFrame(Duration.seconds(0.5), new KeyValue(tStatsMenu.translateYProperty(), root.getHeight()- 200));
							menuLocked = true;
						}else {
							start = new KeyFrame(Duration.ZERO, new KeyValue(tStatsMenu.translateYProperty(), root.getHeight() - 200));
							end = new KeyFrame(Duration.seconds(0.5), new KeyValue(tStatsMenu.translateYProperty(), root.getHeight()));
							menuLocked = false;
						}
						Timeline sl = new Timeline(start, end);
						if(statsMenuShowing) sl.setOnFinished(e -> root.getChildren().remove(tStatsMenu));
						statsMenuShowing = !statsMenuShowing;
						sl.play();
					}
					//Menu is not selecting pokemon, item, or stats
				} else {
					if(option.equals("Exit")) {
						Platform.exit();
					}

					if(option.equals("Save")) {
						if(!saveMenuShowing) {
							root.getChildren().add(saveMenu);
							start = new KeyFrame(Duration.ZERO, new KeyValue(saveMenu.translateYProperty(), root.getHeight()));
							end = new KeyFrame(Duration.seconds(1), new KeyValue(saveMenu.translateYProperty(), root.getHeight()-root.getHeight()));
							menuLocked = true;
						}else {
							start = new KeyFrame(Duration.ZERO, new KeyValue(saveMenu.translateYProperty(), root.getHeight()-root.getHeight()));
							end = new KeyFrame(Duration.seconds(1), new KeyValue(saveMenu.translateYProperty(), root.getHeight()));
							menuLocked = false;
						}
						Timeline timeline = new Timeline(start, end);
						if(saveMenuShowing) timeline.setOnFinished(e -> root.getChildren().remove(saveMenu));
						saveMenuShowing = !saveMenuShowing;
						timeline.play();
						break;
					}

					if(option.equals("User Guide")) {
						if(!guideShowing) {
							root.getChildren().add(guideMenu);
							start = new KeyFrame(Duration.ZERO, new KeyValue(guideMenu.translateYProperty(), root.getHeight()));
							end = new KeyFrame(Duration.seconds(0.5), new KeyValue(guideMenu.translateYProperty(), root.getHeight()- 200));
							menuLocked = true;
						}else {
							start = new KeyFrame(Duration.ZERO, new KeyValue(guideMenu.translateYProperty(), root.getHeight() - 200));
							end = new KeyFrame(Duration.seconds(0.5), new KeyValue(guideMenu.translateYProperty(), root.getHeight()));
							menuLocked = false;
						}
						Timeline sl = new Timeline(start, end);
						if(guideShowing) {
							sl.setOnFinished(e -> root.getChildren().remove(guideMenu));
						}else {
							sl.setOnFinished(e -> guideMenu.continueFill());
						};
						guideShowing = !guideShowing;
						sl.play();
					}
					if(option.equals("Load")) {
						break;
					}
				}
			default:
				break;
			}
		}
	}

	private class writeFileHandler implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent event) {
			try {
				FileOutputStream fileOutput = new FileOutputStream("PokemonSavedData");
				ObjectOutputStream out = new ObjectOutputStream(fileOutput);
				out.writeObject(game);
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	/**
	 * This randomly generate a pokemon
	 * @return a pokemon
	 */
	//Used when an encounter is found to yield a pokemon for said encounter
	public Pokemon giveRandomSafariPokemon(){
		Random random = new Random();        //random int generator
		int randomInt = random.nextInt(101); //generates an int from 0-100
		if (randomInt <= (5)) { //5% chance for rares
			randomInt= random.nextInt(101);
			if(randomInt<=50){
				return new Rayquaza(null, null);
			}
			return new MrMime(null,null);
		}
		if (randomInt <= (20)) { //20% for uncommons
			randomInt= random.nextInt(101);
			if(randomInt<=50){
				return new Pikachu(null,null);
			}
			return new Rhyhorn(null, null);
		}  //75% for commons
		randomInt = random.nextInt(121); //20% chance each 
		if(randomInt<=20){
			return new Caterpie(null,null);
		}
		else if(randomInt<=40){
			return new Meowth(null,null);
		}
		else if(randomInt<=60){
			return new Pidgey(null,null);
		}
		else if(randomInt<=80){
			return new Slowpoke(null,null);
		}
		else if(randomInt<=100){
			return new Oddish(null,null);
		}
		return new Diglett(null,null); //else return a diglett
	}


	public Pokemon giveRandomPokemon(){


		Random random = new Random();        //random int generator
		int randomInt = random.nextInt(101); //generates an int from 0-100
		if (randomInt <= (5)) { //5% chance for rares
			return new Rayquaza(null,null);
		}
		if (randomInt <= (20)) { //20% for uncommons
			return new Pikachu(null, null);
		}  //75% for commons
		return new Oddish(null,null); //else return an oddish
	}
	class BattlePressListener implements EventHandler<KeyEvent>{

		@Override
		public void handle(KeyEvent event) {
			// TODO Auto-generated method stub
			mt.playSound("select");
			if(battleView.getBattle().isOver() ){
				if(event.getCode()!=KeyCode.ENTER){
					return;
				}
				if(battleView.getBattle().isCaught()){
					battleView.getBattle().getPokemonList().add(battleView.getBattle().getEnemy());
					game.getCurrPlayer().setAllPokemons(battleView.getBattle().getPokemonList());
				}
				else if(battleView.getBattle().won() || battleView.getBattle().getEnemyRan() ||
						battleView.getBattle().getRan()){ //if won update exp and pokemon list again
					game.getCurrPlayer().setAllPokemons(battleView.getBattle().getPokemonList());
				}
				else{ //if lost update location of trainer to original zone
					int i;
					for (i=0; i<game.getCurrPlayer().getAllPokemons().size(); i++){ //heal all pokemon
						game.getCurrPlayer().getAllPokemons().get(i).setHealth(game.getCurrPlayer().getAllPokemons().get(i).getMaxHp());
					}
					game.getCurrPlayer().setCurrentLocation( new Point(14,17)); //reset locations
					game.changeMap(game.getTown());
					//game.setCurrState("walking");
					//	setView(town);
					game.moveTrainer(Direction.UP);
				}
				game.setCurrState("walking");
				updateView();
				curNode=1;
				curPanel="Start";
				scene.setOnKeyPressed(new KeyPressedListener());
				return;
			}
			if(curNode==0){
				if(event.getCode()==KeyCode.ENTER){
					if(battleView.getBattle().isEnemyTurn()){
						String temp = battleView.getBattle().enemyTurn();
						if(temp.equals("Run")){
							battleView.runMsg();
						}
						else{
							runAnimation(temp, battleView , true);
						}
						curPanel = "Start";
						curNode=0;
						return;
					}
					else if(battleView.getBattle().getCur().getHealth()<=0){ //check if our pokemon died
						int i =0;
						while(battleView.getBattle().getPokemonList().get(i).getHealth()<=0 && i<6){
							i++;
						}
						if(i>=battleView.getBattle().getPokemonList().size() || i>=6){
							return;
						}
						new SwitchAnimation(battleView, scene, i).animate();
						curPanel = "Start";
						curNode =0;
						return;
					}
					else{
						curNode=1;
						battleView.drawFirstStage();
						battleView.firstRect();
						return;
					}
				}
				else{
					return;
				}
			}
			if(event.getCode() == KeyCode.ENTER){
				if(curPanel.equals("Start")){
					if(curNode==1){
						battleView.showMoves();
						battleView.firstRect();
						curPanel = "Moves";
						return;
					}
					else if(curNode==2){
						//battleView.showItems();  will show items later
						battleView.drawItemView();
						battleView.itemFirst();
						curPanel = "Items";
						return;
					}
					else if(curNode==3){
						battleView.drawPokemon(); //will look at pokemon
						battleView.pokeFirst();
						curPanel = "Pokemon";
						curNode = 1;
						return;
					}
					else{
						//battleView.showRunAttempt();  //will show text w/ run information (success/fail)
						battleView.getBattle().run();
						battleView.runMsg();
						curNode=0;
						return;
					}
				}
				if(curPanel.equals("Moves")){
					if(curNode==1){
						battleView.stopAct();
						battleView.getBattle().useAbility(battleView.getBattle().getCur().getAbilities().get(0), battleView.getBattle().getCur(), battleView.getBattle().getEnemy());
						runAnimation(battleView.getBattle().getCur().getAbilities().get(0), battleView, false);
					}
					else if(curNode==2){
						battleView.stopAct();
						battleView.getBattle().useAbility(battleView.getBattle().getCur().getAbilities().get(1), battleView.getBattle().getCur(), battleView.getBattle().getEnemy());
						runAnimation(battleView.getBattle().getCur().getAbilities().get(1), battleView,false);//second ability
					}
					else if(curNode==3){
						battleView.stopAct();
						battleView.getBattle().useAbility(battleView.getBattle().getCur().getAbilities().get(2), battleView.getBattle().getCur(), battleView.getBattle().getEnemy());
						runAnimation(battleView.getBattle().getCur().getAbilities().get(2), battleView,false);//third ability
					}
					else{
						battleView.stopAct();
						battleView.getBattle().useAbility(battleView.getBattle().getCur().getAbilities().get(3), battleView.getBattle().getCur(), battleView.getBattle().getEnemy());
						runAnimation(battleView.getBattle().getCur().getAbilities().get(3), battleView,false);//fourth ability
					}
					//runAnimation(battleView.getBattle().enemyTurn(), battleView, true);
					curNode=0;
					curPanel="Start";
					//battleView.drawFirstStage();
					//	battleView.firstRect();

					return;
				}
				if(curPanel.equals("Pokemon")){
					if(battleView.getBattle().getPokemonList().get(curNode-1).getHealth()<1){
						return;
					}
					new SwitchAnimation(battleView, scene, curNode-1).animate();
					curPanel = "Start";
					curNode =0;
					return;
				}
				if(curPanel.equals("Items")){
					if(curNode==1){
						return;
					}
					//battleView.getBattle().safariCatch();
					//new Catch(battleView, scene).animate(); catching not allowed outside of safari
					battleView.getBattle().useItem(battleView.getBattle().getItems().get(curNode-1));
					curPanel="Start";
					curNode=0;
					return;
					//	if(battleView.getBattle().isOver()){
					//trainer.addPokemon
					//give control back
					//}

				}
				//	curNode=;
				//curPanel="Start";
				//battleView.drawFirstStage();
				//	battleView.firstRect();

				return;

			}

			if(event.getCode()==KeyCode.ESCAPE){
				battleView.drawFirstStage();
				battleView.firstRect();
				curNode=1;
				curPanel = "Start";
			}
			if(event.getCode()==KeyCode.UP){
				if(curPanel.equals("Items")){
					if (curNode ==1){
						return;
					}
					else if(curNode==2){
						battleView.drawItemView();
						battleView.itemFirst();
					}
					else if(curNode==3){
						battleView.drawItemView();
						battleView.itemSecond();
					}
					else{
						battleView.drawItemView();
						battleView.itemThird();
					}
					curNode--;
					return;
				}
				if(curPanel.equals("Pokemon")){
					if (curNode ==1){
						return;
					}
					else if(curNode==2){
						battleView.drawPokemon();
						battleView.pokeFirst();
					}
					else if(curNode==3){
						battleView.drawPokemon();
						battleView.pokeSecond();
					}
					else if(curNode==4){
						battleView.drawPokemon();
						battleView.pokeThird();
					}
					else if(curNode==5){
						battleView.drawPokemon();
						battleView.pokeFourth();
					}
					else{
						battleView.drawPokemon();
						battleView.pokeFifth();
					}
					curNode--;
					return;
				}
				if(curNode==3) {
					curNode =1;
					if(curPanel.equals("Start")){
						battleView.drawFirstStage();
						battleView.firstRect();
					}
					else if(curPanel.equals("Moves")){
						battleView.showMoves();
						battleView.firstRect();
					}
					return;
				}
				if(curNode==4){
					curNode = 2;
					if(curPanel.equals("Start")){
						battleView.drawFirstStage();
						battleView.secondRect();
					}
					else if(curPanel.equals("Moves")){
						battleView.showMoves();
						battleView.secondRect();
					}
				}
				return;
			}
			if (event.getCode()==KeyCode.DOWN){
				if(curPanel.equals("Items")){
					if (curNode ==4){
						return;
					}
					else if(curNode==2 && battleView.getBattle().getItems().size()>2){
						battleView.drawItemView();
						battleView.itemThird();
						curNode++;
					}
					else if(curNode==3 && battleView.getBattle().getItems().size()>3){
						battleView.drawItemView();
						battleView.itemFourth();
						curNode++;
					}
					else if (curNode ==1 && battleView.getBattle().getItems().size()>1){
						battleView.drawItemView();
						battleView.itemSecond();
						curNode++;
					}
					return;
				}
				if(curPanel.equals("Pokemon")){
					if (curNode ==6){
						return;
					}
					else if(curNode==4 && battleView.getBattle().getPokemonList().size()>4){
						battleView.drawPokemon();
						battleView.pokeFifth();
						curNode++;
					}
					else if(curNode==5 && battleView.getBattle().getPokemonList().size()>5){
						battleView.drawPokemon();
						battleView.pokeSixth();
						curNode++;
					}
					else if(curNode==2 && battleView.getBattle().getPokemonList().size()>2){
						battleView.drawPokemon();
						battleView.pokeThird();
						curNode++;
					}
					else if(curNode==3 && battleView.getBattle().getPokemonList().size()>3){
						battleView.drawPokemon();
						battleView.pokeFourth();
						curNode++;
					}
					else if (curNode ==1 && battleView.getBattle().getPokemonList().size()>1){
						battleView.drawPokemon();
						battleView.pokeSecond();
						curNode++;
					}
					return;
				}

				if(curNode==1) {
					curNode =3;
					if(curPanel.equals("Start")){
						battleView.drawFirstStage();
						battleView.thirdRect();
					}
					else if(curPanel.equals("Moves")){
						battleView.showMoves();
						battleView.thirdRect();
					}
					return;
				}
				if(curNode==2){
					curNode = 4;
					if(curPanel.equals("Start")){
						battleView.drawFirstStage();
						battleView.fourthRect();
					}
					else if(curPanel.equals("Moves")){
						battleView.showMoves();
						battleView.fourthRect();
					}
				}
				return;
			}
			if (event.getCode()==KeyCode.RIGHT){ //fx
				if(curNode==1 && (curPanel.equals("Start") ||
						curPanel.equals("Moves"))) {
					curNode =2;
					if(curPanel.equals("Start")){
						battleView.drawFirstStage();
						battleView.secondRect();
					}
					else if(curPanel.equals("Moves")){
						battleView.showMoves();
						battleView.secondRect();
					}
					return;
				}
				if(curNode==3 && (curPanel.equals("Start") ||
						curPanel.equals("Moves"))){
					curNode = 4;
					if(curPanel.equals("Start")){
						battleView.drawFirstStage();
						battleView.fourthRect();
					}
					else if(curPanel.equals("Moves")){
						battleView.showMoves();
						battleView.fourthRect();
					}
					return;
				}
			}
			if (event.getCode()==KeyCode.LEFT){ //fx
				if(curNode==2&& (curPanel.equals("Start") ||
						curPanel.equals("Moves"))) {
					curNode =1;
					if(curPanel.equals("Start")){
						battleView.drawFirstStage();
						battleView.firstRect();
					}
					else if(curPanel.equals("Moves")){
						battleView.showMoves();
						battleView.firstRect();
					}
					return;
				}
				if(curNode==4 && (curPanel.equals("Start") ||
						curPanel.equals("Moves"))){
					curNode = 3;
					if(curPanel.equals("Start")){
						battleView.drawFirstStage();
						battleView.thirdRect();
					}
					else if(curPanel.equals("Moves")){
						battleView.showMoves();
						battleView.thirdRect();
					}
					return;
				}
			}

		}
	}
	private class SafariPressListener implements EventHandler<KeyEvent>{
		@Override
		public void handle(KeyEvent event) {
			// TODO Auto-generated method stub
			mt.playSound("select");
			if(safariView.getBattle().isOver()){
				if(event.getCode()!=KeyCode.ENTER){
					return;
				}
				//return control
				if(safariView.getBattle().isCaught()){
					safariView.getBattle().getPokemonList().add(safariView.getBattle().getEnemy());
					game.getCurrPlayer().setAllPokemons(safariView.getBattle().getPokemonList());
				}
				game.setCurrState("walking");
				updateView();
				curNodeS=1;
				scene.setOnKeyPressed(new KeyPressedListener());
				return;
			}
			if(curNodeS==0){
				if(event.getCode()==KeyCode.ENTER){
					if(safariView.getBattle().isEnemyTurn()){
						safariView.getBattle().enemyRun();
						safariView.runMsg();
						curNodeS=0;
						return;
					}
					else{
						curNodeS=1;
						safariView.drawFirstStage();
						safariView.firstRect();
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



	public void runAnimation(String ab, BattleView bV, boolean isEnemy){
		if (ab.equals("Pound")) {
			new PoundAnimation(battleView, isEnemy, scene).animate();
		}
		if (ab.equals("Air Slash")) {
			new AirSlashAnimation(battleView, isEnemy, scene).animate();
		}
		if (ab.equals("Psybeam")) {
			new PsybeamAnimation(battleView, isEnemy, scene).animate();
		}
		if (ab.equals("Crunch")) {
			new CrunchAnimation(battleView, isEnemy, scene).animate();
		}
		if (ab.equals("Hyper Beam")) {

		}
		if (ab.equals("Dragon's Dance")) {

		}
		if (ab.equals("Double Slap")) {
			new DoubleSlapAnimation(battleView, isEnemy, scene).animate();
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
