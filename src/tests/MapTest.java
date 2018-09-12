package tests;

import static org.junit.Assert.assertTrue;

import java.awt.Point;

import org.junit.Test;

import model.Map.Map;

public class MapTest {

	@Test
	public void readFileTest() {
		
		Map test1 = new Map("town.txt", new Point(0,0));
		System.out.print(test1.getMap().length);
		System.out.print(test1.getMap()[0].length);
		//test1.printMap();
		test1.getMap();
		assertTrue(true);
		
	}
	
	
}
