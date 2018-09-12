package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import model.BattleObject;
import model.Item;
import model.Pokemon;
import pokemon.Diglett;
import pokemon.MrMime;
import pokemon.Rhyhorn;

public class BattleObjectTest {
	ArrayList<Pokemon> trainerPokes;
	
	@Test//tests a fight win instance
	public void fightTest1(){
		trainerPokes = new ArrayList<Pokemon>();
		trainerPokes.add(new MrMime(null, null));
		trainerPokes.add(new Diglett(null, null));
		trainerPokes.add(new Rhyhorn(null, null));
		BattleObject z = new BattleObject(true, trainerPokes, null, new Diglett(null, null) ); //enemy diglet, trainer with 3 pokemon
		z.useAbility(z.getCur().getAbilities().get(0), z.getCur(), z.getEnemy());//diglett should now be at 50 hp (Hyper beam used)
		assertTrue(!z.isOver()); //game shouldn't be over yet //skip diglett's turn this time for testing purposes
		z.useAbility(z.getCur().getAbilities().get(1), z.getCur(), z.getEnemy());//diglett should now be dead (Psybeam used)
		
		assertTrue(z.isOver());	
		assertTrue(z.won());
	}	
	@Test //tests a situation where the trainer loses (runs out of pokemon)
	public void battleTrainerLossTest(){
		trainerPokes = new ArrayList<Pokemon>();
		trainerPokes.add(new MrMime(null, null));
		trainerPokes.add(new Diglett(null, null));
		trainerPokes.add(new Rhyhorn(null, null));
		BattleObject z = new BattleObject(true, trainerPokes, null, new Diglett(null, null) ); //enemy diglet, trainer with 3 pokemon
		z.switchPokemon(2);
		assertTrue(z.isEnemyTurn());
		
		z.useItem(new Item("Trap")); //enemy can't run any more; time to test trainer's pokemon death swapping
		int i =0;
		while(z.havePokemonRemaining()){ //make the diglett attack (and skip trainer's turns until trainer has no pokemon left)
			if(z.getCur().getHealth()<=0){
				z.switchPokemon(0);//try this one to test it (shouldn't work because this pokemon died)
				z.switchPokemon(i);//swap in the case where one pokemon dies
				i++;
			}
			z.enemyTurn();
		}
		assertTrue(z.isOver());//game should be over; all trainer's pokemon dead
	
	}
	
	@Test 
	public void BattleEnemyRunTest(){
		trainerPokes = new ArrayList<Pokemon>();
		trainerPokes.add(new MrMime(null, null));
		trainerPokes.add(new Diglett(null, null));
		trainerPokes.add(new Rhyhorn(null, null));
		BattleObject z = new BattleObject(true, trainerPokes, null, new Diglett(null, null) ); //enemy diglet, trainer with 3 pokemon
		z.switchPokemon(2);
		assertTrue(z.isEnemyTurn());
		z.enemyTurn();//two things can happen here because the diglett only has one ability, he can pound or try to run
		assertTrue(!z.isEnemyTurn());
		if(z.isOver()){//mr diglett attempted to escape and was successful
			assertTrue(z.getEnemyRan());
		}
		else{
			assertTrue(!z.isEnemyTurn()); //their turn is over and they either failed an escape or used pound
		}
		while (!z.isOver()){//now really try to make the diglett run
			z.enemyTurn();//attempt run
		}
		assertTrue(z.getEnemyRan());//diglet should have ran off		
	}
	
	@Test 
	public void BattleTrainerRunTest(){
		trainerPokes = new ArrayList<Pokemon>();
		trainerPokes.add(new MrMime(null, null));
		trainerPokes.add(new Diglett(null, null));
		trainerPokes.add(new Rhyhorn(null, null));
		BattleObject z = new BattleObject(true, trainerPokes, null, new Diglett(null, null) ); //enemy diglet, trainer with 3 pokemon
		while (!z.isOver()){//make the trainer continuously attempt runs
			z.run(); //attempt a run/escape
		}
		System.out.println(z.getRan());
		assertTrue(z.getRan());//to get here; trainer should have ran off		
	}
	
	@Test //test safari situations out
	public void catchTest1(){
		trainerPokes = new ArrayList<Pokemon>();
		trainerPokes.add(new MrMime(null, null));
		BattleObject z = new BattleObject(false, trainerPokes, null, new Diglett(null, null) ); //enemy diglet, trainer pokemon irrelevant here
		assertTrue(!z.won());//couldn't have won yet could we :0
		z.useItem(new Item("Rock")); //increase chance of run; also increase chance of catch
		assertEquals(z.getEnemy().catchModifier,1.25,0.001);
		assertEquals(z.getEnemy().runModifier, 1.25, 0.001);
		z.useItem(new Item("Bait")); //decreased run chance, decreased catch chance
		assertEquals(z.getEnemy().catchModifier,1.0, 0.001); //this should just reset the chances back to their defaults
		assertEquals(z.getEnemy().runModifier, 1.0, 0.001);
		z.enemyTurn(); //just for testing purposes
		if( z.isOver()){
			z.resetGameState();
		}
		z.useItem(new Item("Bait"));
		z.useItem(new Item("Bait")); //these two will decrease odds of catches
		while(!z.isOver() || z.getNumPokeballs()<1){//lets attempt some catches on pokemon that cannot escape
			z.useItem(new Item("Safari ball"));
		}
		if(z.getEnemy().isCaught()){//here either diglett was caught or we ran out of pokeballs
			assertTrue(z.won());
			//add testing for win condition which has enemy pokemon with no health (these two will never coincide, but GUnit testing) :(
			z.useAbility("Psybeam", z.getCur(), z.getEnemy());
			z.useAbility("Psybeam", z.getCur(), z.getEnemy());
			assertTrue(z.won());//we wonnnn (the pokemon we caught should also be dead now wooohoooo)
		}
		else{//no win feelsBadMan
			assertTrue(!z.won());
		}
	}
	@Test
	public void catchTest2(){ //rarer pokemon tests
		trainerPokes = new ArrayList<Pokemon>();
		trainerPokes.add(new MrMime(null, null));
		BattleObject z = new BattleObject(false, trainerPokes, null, new Rhyhorn(null, null) ); //enemy rhyhorn, trainer pokemon irrelevant here
		z.useItem(new Item("Rock"));
		z.useItem(new Item("Rock")); //these two will increase odds of catches
		while(!z.isOver() || z.getNumPokeballs()<1){//lets attempt some catches on pokemon that cannot escape
			z.useItem(new Item("Safari ball"));
		}
		if(z.getEnemy().isCaught()){//here either rhyhorn was caught or we ran out of pokeballs
			assertTrue(z.won());
		}
		else{//no win feelsBadMan
			assertTrue(!z.won());
		}
	}
	@Test
	public void catchTest3(){ //even rarer pokemon tests
		trainerPokes = new ArrayList<Pokemon>();
		trainerPokes.add(new MrMime(null, null));
		BattleObject z = new BattleObject(false, trainerPokes, null, new MrMime(null, null) ); //enemy MrMime, trainer pokemon irrelevant here
		z.useItem(new Item("Rock"));
		z.useItem(new Item("Rock"));
		z.useItem(new Item("Rock")); //these three will increase odds of catches
		while(!z.isOver() || z.getNumPokeballs()<1){//lets attempt some catches on pokemon that cannot escape
			z.useItem(new Item("Safari ball"));
		}
		if(z.getEnemy().isCaught()){//here either MrMime was caught or we ran out of pokeballs
			assertTrue(z.won());
		}
		else{//no win feelsBadMan
			assertTrue(!z.won());
		}
	}
	@Test //tests items not yet tested
	public void itemsTest(){
		trainerPokes = new ArrayList<Pokemon>();
		trainerPokes.add(new MrMime(null, null));
		trainerPokes.add(new Diglett(null, null));
		trainerPokes.add(new Rhyhorn(null, null));
		BattleObject z = new BattleObject(true, trainerPokes, null, new Diglett(null, null) ); //enemy diglet, trainer with 3 pokemon
		z.useItem(new Item("Razor Claw")); //doubles pokemon's attack
		assertEquals(z.getCur().attackModifier, 2.0, 0.0);
		z.useItem(new Item("HP Pot")); //heals pokemon for 40 hp
		assertEquals(z.getCur().getHealth(), 140, 0);
	}
	
	@Test //pretty unrealistic use of abilities, but we can use this to make sure values are accurate 
	public void abilitiesTest(){
		trainerPokes = new ArrayList<Pokemon>();
		trainerPokes.add(new MrMime(null, null));
		trainerPokes.add(new Diglett(null, null));
		trainerPokes.add(new Rhyhorn(null, null));
		BattleObject z = new BattleObject(true, trainerPokes, null, new Diglett(null, null) ); //enemy diglet, trainer with 3 pokemon
		z.useAbility("Air Slash", z.getCur(), z.getEnemy());
		assertEquals(z.getEnemy().getHealth(), 70 ,0);
		z.useAbility("Crunch", z.getCur(), z.getEnemy());
		assertEquals(z.getEnemy().getHealth(), 45 ,0);
		z.useAbility("Dragon's Dance", z.getCur(), z.getEnemy());
		assertEquals(z.getCur().attackModifier, 1.25 ,0);
		z.useAbility("Double Slap", z.getCur(), z.getEnemy());
		assertEquals(z.getEnemy().getHealth(), -5 ,0);
		z.getCur().attackModifier=1.0;//let's reset everything because the math is going to get harder to pay attention to
		z.getEnemy().health=100;
		
		z.useAbility("Tackle", z.getCur(), z.getEnemy());
		assertEquals(z.getEnemy().getHealth(), 90 ,0);
		z.useAbility("Quick Attack", z.getCur(), z.getEnemy());
		assertEquals(z.getEnemy().getHealth(), 70 ,0);
		z.useAbility("Scratch", z.getCur(), z.getEnemy());
		assertEquals(z.getEnemy().getHealth(), 60 ,0);
		z.useAbility("Absorb", z.getCur(), z.getEnemy());
		assertEquals(z.getEnemy().getHealth(), 50 ,0);
		assertEquals(z.getCur().getHealth(),110,0);
		z.useAbility("Water Gun", z.getCur(), z.getEnemy());
		assertEquals(z.getEnemy().getHealth(), 35 ,0);
		z.useAbility("Horn Attack", z.getCur(), z.getEnemy());
		assertEquals(z.getEnemy().getHealth(), 5 ,0);
		z.useAbility("Drill Run", z.getCur(), z.getEnemy());
		assertEquals(z.getEnemy().getHealth(), -45 ,0);
		z.useAbility("Thunderbolt", z.getCur(), z.getEnemy());
		assertEquals(z.getEnemy().getHealth(), -75 ,0);
		z.useAbility("Thunder", z.getCur(), z.getEnemy());
		assertEquals(z.getEnemy().getHealth(), -115 ,0);
	}
	

}
