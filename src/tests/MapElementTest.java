package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import model.Item;
import model.Map.MapElement;
import pokemon.Diglett;
import pokemon.MrMime;
import pokemon.Rhyhorn;


public class MapElementTest {

	@Test
	public void testEncounterRate() {

		MapElement cave = new MapElement("Cave");
		MapElement grass = new MapElement("Grass");
		MapElement space = new MapElement("Space");
		MapElement wall = new MapElement("Wall");
		MapElement door = new MapElement("Door");

		assertTrue(cave.getEncounterRate() == .115);
		assertTrue(space.getEncounterRate() == 0);
		assertTrue(wall.getEncounterRate() == 0);
		assertTrue(grass.getEncounterRate() == .085);
		assertTrue(door.getEncounterRate() == 0);

	}

	@Test
	public void testCanWalk() {

		MapElement cave = new MapElement("Cave");
		MapElement grass = new MapElement("Grass");
		MapElement space = new MapElement("Space");
		MapElement wall = new MapElement("Wall");
		MapElement door = new MapElement("Door");

		assertTrue(cave.canWalk());
		assertTrue(space.canWalk());
		assertFalse(wall.canWalk());
		assertTrue(grass.canWalk());
		assertTrue(door.canWalk());

	}

	@Test
	public void pokemonYield(){
		MapElement cave = new MapElement("Cave");
	/*	while(cave.giveRandomPokemon().getClass().getName()!=new MrMime(null,null).getClass().getName()){

		}
		while(cave.giveRandomPokemon().getClass().getName()!=new Rhyhorn(null,null).getClass().getName()){

		}
		while(cave.giveRandomPokemon().getClass().getName()!=new Diglett(null,null).getClass().getName()){

		}*/
	}
	@Test
	public void itemsGettersEtcTesting(){
		MapElement def= new MapElement("");
		def.getItem();
		def.setItem(new Item("Razor Claw"));
		def.getElement();


	}


}
