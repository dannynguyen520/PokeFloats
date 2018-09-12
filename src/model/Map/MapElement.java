package model.Map;

import java.io.Serializable;
import java.util.Random;

import model.Item;
import model.Pokemon;
import pokemon.Diglett;
import pokemon.MrMime;
import pokemon.Oddish;
import pokemon.Pikachu;
import pokemon.Rayquaza;
import pokemon.Rhyhorn;

@SuppressWarnings("serial")
public class MapElement implements Serializable {

	private Item item;
	private boolean walkable;
	private Double encounterRate;
	private String element;
	
	/**
	 * Constructor
	 * This creates a single space of a map that contains a specific element 
	 * @param ele
	 */

	public MapElement(String ele) {

		switch (ele) {

			case "Cave":
				walkable = true;
				encounterRate = (11.5/100.0);
				break;
			case "Grass":
				walkable = true;
				encounterRate = (8.5/100.0);
				break;
			case "Space":
				walkable = true;
				encounterRate = (0/100.0);
				break;
			case "Wall":
				walkable = false;
				encounterRate = (0/100.0);
				break;
			case "Door":
				walkable = true;
				encounterRate = (0/100.0);
				break;
			default:
				walkable = false;
				encounterRate = (0/100.0);
				break;
		}

		element = ele;
		item = null;
	}

	/**
	 * 
	 * @return The chance of encountering
	 */
	public Double getEncounterRate() {
		return encounterRate;
	}

	/**
	 * 
	 * @return an item of this space is holding
	 */
	public Item getItem() {
		return item;
	}
	
	/**
	 * Set the current item of this space
	 * @param item
	 */

	public void setItem(Item item) {
		this.item = item;
	}

	/**
	 * Returns the type of this space
	 * @return String of space type
	 */
	public String getElement() {
		return element;
	}

	
	/**
	 * See if this space is walkable
	 * @return Boolean that show of this space can be walked on
	 */

	public boolean canWalk() {
		return walkable;
	}


}
