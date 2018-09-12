package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import model.Pokemon;
import pokemon.Diglett;
import pokemon.MrMime;
import pokemon.Rhyhorn;

public class PokemonCatchTest {
	
	
	
	@Test
	public void run1000catchTest() {
		
		//Hardcoded Pokemons
		Pokemon mrMime = new MrMime("Mr Mime", "file:images/mrMime.png");
		Pokemon rhyhorn = new Rhyhorn("Rhyhorn", "file:images/rhyhorn.png");
		Pokemon diglett = new Diglett("Diglett", "file:images/diglett.png");
		mrMime.getAbilities();
		
		int rareCount = 0;
		int uncommonCount = 0;
		int commonCount = 0;
		
		boolean caught = false;
		
		for (int i=0; i<1001; i++) {
			caught = mrMime.catchPokemon();
			if (caught)
				rareCount++;
			mrMime.setCaught(false); //reset caught
			
			caught = rhyhorn.catchPokemon();
			if (caught)
				uncommonCount++;
			rhyhorn.setCaught(false); //reset caught
			
			caught = diglett.catchPokemon();
			if (caught)
				commonCount++;
			diglett.setCaught(false); //reset caught
		}
		
		System.out.println("REGULAR CATCHING");
		System.out.println("============================================");
		System.out.println("Rare should have the least caught amount,");
		System.out.println("Uncommon should be more than Rare but less than Common,");
		System.out.println("Common should have the most caught amount.");
		System.out.println("=========================================================");
		System.out.println("Rare win percentage: " + rareCount / 10. + "%");
		System.out.println("Uncommon win percentage: " + uncommonCount / 10. + "%");
		System.out.println("Common win percentage: " + commonCount / 10. + "%\n");

	}
	
	@Test
	public void run1000catchTestWithHealthLowered() {
		
		//Hardcoded Pokemons
		Pokemon mrMime = new MrMime("Mr Mime", "file:images/mrMime.png");
		Pokemon rhyhorn = new Rhyhorn("Rhyhorn", "file:images/rhyhorn.png");
		Pokemon diglett = new Diglett("Diglett", "file:images/diglett.png");
		
		int rareCount = 0;
		int uncommonCount = 0;
		int commonCount = 0;
		
		//change health to be lowered
		mrMime.setHealth(30);
		
		boolean caught = false;
		
		for (int i=0; i<1001; i++) {
			caught = mrMime.catchPokemon();
			if (caught)
				rareCount++;
			mrMime.setCaught(false); //reset caught
			
			caught = rhyhorn.catchPokemon();
			if (caught)
				uncommonCount++;
			rhyhorn.setCaught(false); //reset caught
			
			caught = diglett.catchPokemon();
			caught = diglett.isCaught();
			if (caught)
				commonCount++;
			diglett.setCaught(false); //reset caught
		}
		
		System.out.println("LOW HEALTH CATCHING ON RARE");
		System.out.println("============================================");
		System.out.println("Rare should have more than 5%, Rare pokemon was brought down to 30/100 hp.");
		System.out.println("Others are left at full hp.");
		System.out.println("=========================================================");
		System.out.println("Rare win percentage: " + rareCount / 10. + "%");
		System.out.println("Uncommon win percentage: " + uncommonCount / 10. + "%");
		System.out.println("Common win percentage: " + commonCount / 10. + "%\n");

	}
	
	@Test
	public void getterNameTest() {
		Pokemon mrMime = new MrMime("Mr Mime", "file:images/mrMime.png");

		String name;
		name = mrMime.getName();
		assertEquals("Mr Mime", name);
		mrMime.setName("ChangedName");
		name = mrMime.getName();
		assertEquals("ChangedName", name);
		
	}
	
	@Test
	public void getterImgTest() {
		Pokemon mrMime = new MrMime("Mr Mime", "file:images/mrMime.png");

		String img;
	//	img = mrMime.getImg();
		//assertEquals("file:images/mrMime.png", img);
		//mrMime.setImg("new file path");
		//img = mrMime.getImg();
		//assertEquals("new file path", img);
		
	}
	
	@Test
	public void getterLevelTest() {
		Pokemon mrMime = new MrMime("Mr Mime", "file:images/mrMime.png");
		
		int lvl;
		lvl = mrMime.getLevel();
		assertEquals(1,lvl);
		mrMime.setLevel(5);
		lvl = mrMime.getLevel();
		assertEquals(5,lvl);
		
	}
	
	@Test
	public void getterHealthTest() {
		Pokemon mrMime = new MrMime("Mr Mime", "file:images/mrMime.png");
		
		double hp;
		hp = mrMime.getHealth();
		assertEquals(100, (int) hp);
		mrMime.setHealth(30);
		hp = mrMime.getHealth();
		assertEquals(30, (int) hp);
		
	}
}
