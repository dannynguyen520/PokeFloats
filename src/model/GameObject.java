package model;

import java.awt.Point;
import java.io.Serializable;
import java.util.Observable;
import java.util.Random;

import model.Map.Direction;
import model.Map.Map;
import model.Map.MapElement;

@SuppressWarnings("serial")
public class GameObject extends Observable implements Serializable {

	private Trainer currPlayer;
	private Map currMap;
	private String currState;
	private Direction curr;
	private Map caveMap;
	private Map townMap;
	private Map safariMap;
	private Map healMap;
	private Point lastPoint;
	private Boolean mapChanged;
	private int safariTown;
	private final Point TOWN_START_POINT = new Point(14,17);
	private final Point CAVE_START_POINT = new Point(2,45);
	private final Point SAFARI_START_POINT = new Point(18,25);
	private final Point HEAL_START_POINT = new Point(9,8);
	
	

	public GameObject() {

		//Start a new game or load
		newGame("Chun");

	    setChanged();
	    notifyObservers();
	    safariTown =0;
	}
	public Map getTown(){
		return townMap;
	}
	private void newGame(String name){

		//Creating...
			//Creating Trainer
			currPlayer = new Trainer("Chun");

			//Creating Maps
			caveMap = new Map("cave.txt", CAVE_START_POINT);
			townMap = new Map("town.txt", TOWN_START_POINT);
			safariMap = new Map("safari.txt", SAFARI_START_POINT);
			healMap = new Map("heal.txt", HEAL_START_POINT);
			//Creating


		//Setting Up...
			//Setting Trainer info
			currPlayer.setCurrentLocation(TOWN_START_POINT);
			curr = Direction.DOWN;
			mapChanged = false;
			lastPoint = currPlayer.getCurrentLocation();

			//Loading currMap
			currMap = townMap;
			mapChanged = false;
			
			//Setting gameStatus
			//TODO: add intro
			currState = "intro";
//			currState = "walking";



	}
	public int getST(){
		return safariTown;
	}
	public void setST(int z){
		safariTown = z;
	}

	public void moveTrainer(Direction to) {

		mapChanged = false;
		
		int target;

		curr = to;
		
		if(currMap == safariMap)
			currPlayer.setSteps(currPlayer.getSteps()+1);
		
		if(currPlayer.getSteps() == 500) {
			System.out.println("Game Over");
			changeMap(townMap);
			currPlayer.setCurrentLocation(currMap.getLastPoint());
			currPlayer.setSteps(0);
			setChanged();
			notifyObservers();
			moveTrainer(Direction.DOWN);
			return;
		}
		
		switch (to) {
		case UP:
			target = currPlayer.getCurrentLocation().y - 1;
			if (target >= 0 && currMap.getMap()[target][currPlayer.getCurrentLocation().x].canWalk()) {
				currPlayer.getCurrentLocation().y = target;
				// System.out.printf("New location: [%s] [%s]\n", currPlayer.getCurrentLocation().y, currPlayer.getCurrentLocation().x);
			}
			break;
		case LEFT:
			target = currPlayer.getCurrentLocation().x - 1;
			if (target >= 0 && currMap.getMap()[currPlayer.getCurrentLocation().y][target].canWalk()) {
				currPlayer.getCurrentLocation().x = target;
				// System.out.printf("New location: [%s] [%s]\n", currPlayer.getCurrentLocation().y, currPlayer.getCurrentLocation().x);
			}
			break;
		case DOWN:
			target = currPlayer.getCurrentLocation().y + 1;
			if (target < (currMap.getMap()).length && currMap.getMap()[target][currPlayer.getCurrentLocation().x].canWalk()) {
				currPlayer.getCurrentLocation().y = target;
				// System.out.printf("New location: [%s] [%s]\n", currPlayer.getCurrentLocation().y, currPlayer.getCurrentLocation().x);
			}
			break;
		case RIGHT:
			target = currPlayer.getCurrentLocation().x + 1;
			if (target < (currMap.getMap()[0]).length && currMap.getMap()[currPlayer.getCurrentLocation().y][target].canWalk()) {
				currPlayer.getCurrentLocation().x = target;
				// System.out.printf("New location: [%s] [%s]\n", currPlayer.getCurrentLocation().y, currPlayer.getCurrentLocation().x);
			}
			break;
		default:

		}

	    updateState();

	}


	public void updateState() {

		int x = currPlayer.getCurrentLocation().x;
		int y = currPlayer.getCurrentLocation().y;
		MapElement currMapElement = currMap.getMap()[y][x];
		lastPoint = new Point(x,y);

		if (currMapElement.getElement().equals("Door")) {
			System.out.println(currMap.getDoors().get(new Point(x,y)));
			switch(currMap.getDoors().get(new Point(x,y))) {

			case ("safari"):
				setST(2);
				changeMap(safariMap);
				currPlayer.setCurrentLocation(currMap.getLastPoint());
				moveTrainer(Direction.UP);
				break;
			case ("town"):
				if(currMap==safariMap){
					setST(1);
				}else{
					setST(0);
				}
				changeMap(townMap);
				currPlayer.setCurrentLocation(currMap.getLastPoint());
				moveTrainer(Direction.DOWN);
				break;
			case ("cave"):
				setST(0);
				changeMap(caveMap);
				currPlayer.setCurrentLocation(currMap.getLastPoint());
				moveTrainer(Direction.UP);
				break;
			case ("house"):

			case ("heal"):
				changeMap(healMap);
				currPlayer.setCurrentLocation(currMap.getLastPoint());

			case ("shop"):

			default:
				break;
				

			}

		}
		

		//See a pokemon
		if (new Random().nextDouble() <= currMapElement.getEncounterRate()){
			System.out.printf("\t!!!!!!!!!!\n\tI see a pokemon!\n\t!!!!!!!!!!\n");
			if(currMap.getMapName().equals("safari.txt")){
				setCurrState("safaricatch");
			}
			else{
				setCurrState("battle");
			}
			currPlayer.getAllPokemons();
			currPlayer.getItems();
		}

		setChanged();
		notifyObservers();
	}


	public void changeMap(Map newMap) {
		currMap = newMap;
		mapChanged = true;
	}

	public Boolean isMapChanged() {
		return mapChanged;
	}


	public Direction getCurrDirection() {
		return curr;
	}


	public Trainer getCurrPlayer() {
		return currPlayer;
	}

	public void setCurrPlayer(Trainer currPlayer) {
		this.currPlayer = currPlayer;
	}
	

	public Map getCurrMap() {
		return currMap;
	}


	public String getCurrState() {
		return currState;
	}
	
	public void setCurrState(String state) {
		currState = state;
	}
	
	public Point getLastPoint() {
		return lastPoint;
	}
	
	public void useItem(String item) {
    for(Item items : currPlayer.getItems()) {
      if(items.getName().equals(item)) {
        if(items.defaultItem) return;
        System.out.println("Item used");
        //TODO: Implement item usage
      }
    }
  }
	


}
