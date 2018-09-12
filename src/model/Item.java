package model;

import java.io.Serializable;
/**
 * The object defines the item's available in the Pokemon game
 * @author Mohaman
 *
 */
@SuppressWarnings("serial")
public class Item implements Serializable {
	private String name;
	private String description;
	protected boolean defaultItem;
	
	/**
	 * Constructs Item based on a {@code String}
	 * @param name parameter that determines type of object
	*/
	public Item(String name) {
	  this.name = name;
		switch (name) {
    case "Bait":
      defaultItem = true;
      description = "Used to make pokemon less likely to run away during a battle.";
      break;
    case "Fragrance":
      defaultItem = false;
      description = "Increases the chance of a pokemon appearing.";
      break;
    case "HPPotion":
      defaultItem = false;
      description = "A Potion used to restore the HP of the pokemon it is given to.";
      break;
    case "RazerClaw":
      defaultItem = false;
      description = "An item that increases the damage your pokemon can do in battle";
      break;
    case "Repel":
      defaultItem = false;
      description = "An item that decreses the chance of you running into a pokemon";
      break;
    case "Rock":
      defaultItem = true;
      description = "A mineral material used in the Safari Zone to make a pokemon easier to catch";
      break;
    case "Pokeball":
    	defaultItem = true;
    	description = "Catches pokemon";
    case "SafariBall":
      defaultItem = true;
      description = "Allows the player to catch wild Pokemon in the Safari Zone.";
      break;
    case "Trap":
      defaultItem = false;
      description = "A object used to prevent a pokemon from running away during a battle outside of the safari zone";
    default:
      break;
    }
	}
	/**
	 * returns the name of the object.
	 * @return the name of the object
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * sets the name of the Item.
	 * @param name new item name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the description of the item.
	 * @return the description of the Item
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * returns if the item is part of the default objects in the game
	 * @return {@code true} if item is default or {@code false} if not default.
	 */
	public boolean isDefault() {
	  return defaultItem;
	}
	
	/**
	 * returns if item can be used.
	 * @return {code true} if can be used when called {@code false} if item cannot be used
	 */
	public boolean canUse() {
	  //TODO: based on trainer, and steps
	  return false;
	}
		
}
