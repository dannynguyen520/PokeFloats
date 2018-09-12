package pokemon;

import java.io.Serializable;

import model.Pokemon;
import model.PokemonType;

public class Oddish extends Pokemon implements Serializable {
	public Oddish (String name, String img) {
		super("Oddish", "oddish.png");
		setBackImg("oddishback.png");
		rarity = PokemonType.COMMON;
		this.addAbility("Pound");
		this.addAbility("Air Slash");
		this.addAbility("Double Slap");
		this.addAbility("Crunch");
	}
}