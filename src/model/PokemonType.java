package model;

import java.io.Serializable;

public enum PokemonType implements Serializable {
	
	//Create types
	RARE(3), UNCOMMON(2), COMMON(1);
	
	private int rarity;
	
	PokemonType (int val) {
		this.rarity = val;
	}
	
	public int rarity() {
		return rarity;
	}

}
