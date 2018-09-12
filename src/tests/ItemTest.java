package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.Item;
/**
 * Test for the Items
 */
public class ItemTest {
  private Item rock;
  private Item sball;
  private Item bait;
  private Item frag;
  private Item potion;
  private Item razer;
  private Item repel;
  private Item trap;
  private Item fake;
  
  @Before
  public void before() {
    rock = new Item("Rock");
    sball = new Item("SafariBall");
    bait = new Item("Bait");
    frag = new Item("Fragrance");
    potion = new Item("HPPotion");
    razer = new Item("RazerClaw");
    repel = new Item("Repel");
    trap = new Item("Trap");
    fake = new Item("Fake");
  }

  @Test
  public void testDescriptions() {
    String rockDescription = "A mineral material used in the Safari Zone to make a pokemon easier to catch";
    assertEquals(rock.getDescription(), rockDescription);
    assertEquals(fake.getDescription(), null);
    assertNotEquals(bait.getDescription(), rockDescription);
  }
  
  @Test
  public void nameTests() {
    String rString = sball.getName();
    assertEquals("SafariBall", rString);
    sball.setName("Test");
    assertNotEquals("SafariBall", sball.getName());
  }
  
  @Test
  public void testDefaultItem() {
    assertFalse(frag.isDefault());
    assertTrue(rock.isDefault());
    assertFalse(potion.isDefault());
  }
  
  @Test
  public void TestCanUse() {
    assertFalse(razer.canUse());
    assertFalse(repel.canUse());
    assertFalse(trap.canUse());
  }
}
