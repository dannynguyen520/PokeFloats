package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javafx.scene.image.Image;


public abstract class Pokemon implements Serializable {

	private String name;
	private String img;
	private int level;
	public double health;
	private boolean caught;
	public double attackModifier;
	public double catchModifier;
	public double runModifier;
	private double maxHp;
	private String backImg;
	ArrayList<String> abilities;
	protected PokemonType rarity;

	public Pokemon(String name, String imgPath) {
		this.name = name;            //Pokemon name
		img = imgPath;               //Image path to the jpg for the pokemon
		level = 1;                   //Every pokemon starts at lvl 1
		health = 100;                //Every pokemon starts off at 100 hp
		maxHp = 100;
		caught = false;
		abilities = new ArrayList<String>();
		attackModifier = 1.0;
		catchModifier = 1.0;
		runModifier = 1.0;
	}
	
	public double getMaxHp(){
		return maxHp;
	}

	public void setBackImg(String backImgFile){
		backImg = backImgFile;
	}
	public Image getBackImg(){
		return new Image("file:images/pokemons/"+backImg);
	}
	
	public Image getImg(){
		return new Image("file:images/pokemons/"+img);
	}

	public ArrayList <String> getAbilities(){
		return abilities;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public void setImg(String img) {
		this.img = img;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setHealth(double health) {
		this.health = health;
	}

	public double getHealth() {
		return health;
	}

	public boolean isCaught() {
		return caught;
	}

	public void addAbility(String ability){
		abilities.add(ability);
	}

	public void setCaught(boolean caught) {
		this.caught = caught;
	}
	public void resetModifier(){
		attackModifier = 1.0;
		catchModifier = 1.0;
		runModifier = 1.0;
	}

	/**
	 * catchPokemon() performs the action of catching the current Pokemon of this class. If the catch was
	 * successful, the boolean caught would be true. When catching pokemon, there are many different types
	 * of rarity of the pokemon, depending how rare it is will decide how hard it is to catch it.
	 * Rarity is as goes:
	 * 		Rare     =  5%
	 * 		Uncommon = 20%
	 * 		Common   = 75%
	 * @return boolean of whether the pokemon was caught or not
	 */
	public boolean catchPokemon() {

		//Health increases catch rate
		// (100 - current hp) / 2 = % amount increased on catching

		double rateIncrease = 0;
		rateIncrease = (100.00 - health) / 2;

		Random random = new Random();        //random int generator
		int randomInt = random.nextInt(101); //generates a int from 0-100

		if (rarity == PokemonType.RARE) {
			if (randomInt <= (5 + rateIncrease)) {
				caught = true;
			}
		} else if (rarity == PokemonType.UNCOMMON) {
			if (randomInt <= (20 + rateIncrease)) {
				caught = true;
			}
		} else if (rarity == PokemonType.COMMON) {
			if (randomInt <= (75 + rateIncrease)) {
				caught = true;
			}
		}
		return caught;
	}
}
