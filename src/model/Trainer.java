package model;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import pokemon.Diglett;

public class Trainer implements Serializable {

	private String name;
	private int steps;
	private ArrayList<Pokemon> allPokemons;
	private ArrayList<Item> allItems;
	private Point currentLocation;
	private Point lastMapPoint;

	public Trainer(String name) {
		//Trainer should start off with one Pokemon
		allPokemons = new ArrayList<Pokemon>();
		allItems = new ArrayList<>();
		allPokemons.add(new Diglett("Diglett", "file:images/pokemons/diglett.png"));
		allItems.add(new Item("SafariBall"));
		this.name = name;
		steps = 0;

	}

  public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}
	
	public void incrementSteps() {
		this.steps++;
	}

	public ArrayList<Pokemon> getAllPokemons() {
		return allPokemons;
	}

	public void setAllPokemons(ArrayList<Pokemon> allPokemons) {
		this.allPokemons = allPokemons;
	}

	public Point getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Point currentLocation) {
		this.currentLocation = currentLocation;
	}

	public Point getLastMapPoint() {
		return lastMapPoint;
	}

	public void setLastMapPoint(Point newPoint) {
		this.lastMapPoint = newPoint;

	}

	public ArrayList<Item> getItems() {
	  return allItems;
	}

	public void addItem(Item item) {
	  allItems.add(item);
  }
	
}
