package model;

import java.util.ArrayList;
import java.util.Random;

public class BattleObject {
	boolean isEnemyTurn;
	boolean isFight;
	ArrayList<Pokemon> trainerPokemon;
	Pokemon enemy;
	ArrayList<Item> items;
	Pokemon cur;
	boolean trapped;
	int pokeballsRemaining;
	boolean ran;
	boolean enemyRan;
/**
 * Constructor
 * @param fighting if this battle object will be used for fights or safari catches
 * @param trainersPokes the pokemon the trainer has
 * @param trainersItems the items the trainers use
 * @param enem the enemy pokemon to be used
 */
	public BattleObject(boolean fighting, ArrayList<Pokemon> trainersPokes, ArrayList<Item> trainersItems,
			Pokemon enem) {
		isEnemyTurn = false; // give trainer starting turn
		isFight = fighting; // the battle could be a safari encounter or a
							// cave/town grass one
		trainerPokemon = trainersPokes; // list of pokemon
		enemy = enem; // enemy pokemon
		items = trainersItems; // trainer's current item list
		cur = trainersPokes.get(0); // first pokemon from list is the current
									// one
		trapped = false; // not trapped at start
		pokeballsRemaining = 30; // 30 pokeballs at the beginning (we won't
									// actually use this later)
		ran = false; // trainer successfully ran away
		enemyRan = false; // enemy pokemon successfully ran away
	}
	public ArrayList<Item> getItems(){
		return items;
	}
	/**
	 * getter for list of trainer pokemon
	 * @return
	 */
	public boolean isCaught(){
		return enemy.isCaught();
	}
	public ArrayList<Pokemon> getPokemonList() {
		return trainerPokemon;
	}
	// getter for num pokeballs
/**
 * 
 * @return getter for num pokeballs (will not be used probably)
 */
	public int getNumPokeballs() {
		return pokeballsRemaining;
	}
	/**
	 * 
	 * @return bool to see if trainer's pokemon aren't all dead
	 */
	public boolean havePokemonRemaining() {
		int i;
		for (i = 0; i < trainerPokemon.size(); i++) {
			if (trainerPokemon.get(i).getHealth() > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @return tells user if it is currently enemy's turns
	 */
	public boolean isEnemyTurn() {
		return isEnemyTurn;
	}

	/**
	 * 
	 * @return returns enemy pokemon
	 */
	public Pokemon getEnemy() {
		return enemy;
	}
	/**
	 * 
	 * @return returns the currently in use trainer pokemon
	 */
	public Pokemon getCur() {
		return cur;
	}
	/**
	 * Used to let the user switch pokemon
	 * @param desired the pokemon the user wants to switch to
	 */
	public void switchPokemon(int desired) {
		if (trainerPokemon.get(desired).health > 0) { // if the desired pokemon isn't dead
			if (cur.health > 0) { // if cur pokemon isn't dead
				isEnemyTurn = true; // swap but count it as a turn
			} else { // if pokemon died
				isEnemyTurn = false; // let the trainer's turn come next
			}
			Pokemon temp = cur;
			cur = trainerPokemon.get(desired); // swap to the new/desired pokemon
			trainerPokemon.set(0, cur);
			trainerPokemon.set(desired, temp);
		}
	}
	/** changes one game over condition to default state
	 * 	@return nothing; 
	 */
	public void resetGameState() {
		enemyRan = false;
	}
	/**
	 * 
	 * @return boolean for game is over or not
	 */
	public boolean isOver() { // cur == null is the situation where trainer runs
								// out of pokemon
		return enemy.health <= 0 || enemy.isCaught() || !havePokemonRemaining() || ran || enemyRan;
	}
	/**
	 * 
	 * @return boolean for if game is won or not
	 */
	public boolean won() {
		return enemy.health <= 0 || enemy.isCaught();
	}
	/**
	 * 
	 * @return a boolean that describes if the trainer successfully escaped
	 */
	public boolean getRan() {
		return ran;
	}

	/**
	 * 
	 * @return boolean that describes if enemy pokemon successfully ran/escaped
	 */
	public boolean getEnemyRan() {
		return enemyRan;
	}

	/**
	 * Trainer run method; 70 percent run chance; if failed is enemy turn
	 */
	public void run() {
		if (Math.random() >= 0.3) {// 70 percent chance to run
			ran = true;
		}
		else{
			ran = false;
		}
		isEnemyTurn = true;
	}

	/**
	 * Enemy pokemon run method; 35 percent default run chance; can be modified by rocks and bait
	 */
	public void enemyRun() {
		Random rand = new Random();
		int randomInt = rand.nextInt(101);
		if (randomInt * enemy.runModifier >= 65) { // 35 percent run chance
													// default
			enemyRan = true;
		}
		isEnemyTurn=false;
	}


	/**
	 * 	 Enemy randomly picks between running or their own abilities and makes
	 *   their move
	 */
	public String enemyTurn() {
		Random rand = new Random();
		if (isFight) {// enemy has the option of fighting outside of safari
			int randomInt = rand.nextInt(enemy.abilities.size() + 1);
			if (randomInt == enemy.abilities.size()) { // if enemy tries to run
				System.out.println(randomInt);
				if (trapped) {// if trapped waste their turn
					isEnemyTurn = false;
					return "Run";
				} else {
					enemyRun(); // else run attempt
					isEnemyTurn= false;
					return "Run";
				}
			} else {
				useAbility(enemy.abilities.get(randomInt), enemy, cur);
				isEnemyTurn = false;
				return enemy.abilities.get(randomInt);
			}
		} else { // enemy run attempt in safari
			enemyRun();
			isEnemyTurn = false;
			return "Run";
		}
		//isEnemyTurn = false;
	}

	/**
	 * method for catching pokemon in safari
	 */
	public void safariCatch() {
		Random random = new Random(); // random int generator
		int randomInt = random.nextInt(101); // generates an int from 0-100
		if (enemy.rarity == PokemonType.RARE) { // if rarest type
			if (randomInt <= (5 * enemy.catchModifier)) {// 5 percent default
															// catch rate
				enemy.setCaught(true);
			}
		} else if (enemy.rarity == PokemonType.UNCOMMON) { // if not common, 20%
															// default catch
															// rate
			if (randomInt <= (20 * enemy.catchModifier)) {
				enemy.setCaught(true);
			}
		} else {
			if (randomInt <= (75 * enemy.catchModifier)) { // common is 75%
				enemy.setCaught(true);
			}
		}
		isEnemyTurn = true; // enemy's turn because this method can only be used
							// by trainer
	}

	/**
	 * uses items in battle
	 * @param z item to be used by trainer
	 */
	public void useItem(Item z) {
		if (z.getName().equals("HPPotion")) { // heals trainer's pokemon by 40
			cur.health += 40;
		}
		if (z.getName().equals("RazerClaw")) { // increases trainer's pokemon's
												// dmg by double
			cur.attackModifier += 1.0;
		}
		if (z.getName().equals("Trap")) { // traps enemy pokemon; not allowing
											// them to run
			trapped = true;
		}
		if (z.getName().equals("Safari ball")) { // tries to catch enemy pokemon
			safariCatch();
			pokeballsRemaining -= 1;
		}
		if (z.getName().equals("Rock")) {
			enemy.catchModifier += 0.25; // higher chance to catch
			enemy.runModifier += 0.25; // higher chance for that enemy to run
		}
		if (z.getName().equals("Bait")) {
			enemy.catchModifier -= 0.25; // lower chance to catch
			enemy.runModifier -= 0.25; // lower chance for that enemy to run
		}
		if (isEnemyTurn) {
			isEnemyTurn = false;
		} else {
			isEnemyTurn = true;
		}
	}

	/**
	 * Uses the specified pokemon ability by pokemon; potentially used on themself or target pokemon
	 * @param ab the ability itself
	 * @param thisPok the pokemon who using the ability
	 * @param attackedPok the (potentially) attacked pokemon
	 */
	public void useAbility(String ab, Pokemon thisPok, Pokemon attackedPok) {

		if (ab.equals("Pound")) {
			attackedPok.health -= (20 * thisPok.attackModifier);
		}
		if (ab.equals("Air Slash")) {
			attackedPok.health -= (30 * thisPok.attackModifier);
		}
		if (ab.equals("Psybeam")) {
			attackedPok.health -= (50 * thisPok.attackModifier);
		}
		if (ab.equals("Crunch")) {
			attackedPok.health -= (25 * thisPok.attackModifier);
		}
		if (ab.equals("Hyper Beam")) {
			attackedPok.health -= (50 * thisPok.attackModifier);
		}
		if (ab.equals("Dragon's Dance")) {
			thisPok.attackModifier += 0.25;
		}
		if (ab.equals("Double Slap")) {
			attackedPok.health -= (40 * thisPok.attackModifier);
		}
		if (ab.equals("Tackle")) {
			attackedPok.health -= (10 * thisPok.attackModifier);
		}
		if (ab.equals("Quick Attack")) {
			attackedPok.health -= (20 * thisPok.attackModifier);
		}
		if (ab.equals("Scratch")) {
			attackedPok.health -= (10 * thisPok.attackModifier);
		}
		if (ab.equals("Absorb")) {
			attackedPok.health -= (10 * thisPok.attackModifier);
			thisPok.health += (10 * thisPok.attackModifier);
		}
		if (ab.equals("Water Gun")) {
			attackedPok.health -= (15 * thisPok.attackModifier);
		}
		if (ab.equals("Horn Attack")) {
			attackedPok.health -= (30 * thisPok.attackModifier);
		}
		if (ab.equals("Drill Run")) {
			attackedPok.health -= (50 * thisPok.attackModifier);
		}
		if (ab.equals("Thunderbolt")) {
			attackedPok.health -= (30 * thisPok.attackModifier);
		}
		if (ab.equals("Thunder")) {
			attackedPok.health -= (40 * thisPok.attackModifier);
		}
		if (isEnemyTurn) {
			isEnemyTurn = false;
		} else {
			isEnemyTurn = true;
		}
	}
}
