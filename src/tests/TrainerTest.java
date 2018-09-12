package tests;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.util.ArrayList;

import org.junit.Test;

import model.Pokemon;
import model.Trainer;


public class TrainerTest {

	@Test
	public void test1(){
		
		Trainer test = new Trainer("Chun");
		assertEquals(test.getName(),"Chun");
		test.setSteps(5);
		assertEquals(test.getSteps(), 5);
		test.setName("ChunNew");
		assertEquals("ChunNew", test.getName());

		test.setAllPokemons(new ArrayList<Pokemon>());
		test.setCurrentLocation(new Point(1,1));
		assertEquals(test.getCurrentLocation().getX(), 1.0, 0);
		test.getAllPokemons();
		
	}
	
	
}
