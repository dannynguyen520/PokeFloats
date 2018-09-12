package pokemon;

import java.io.Serializable;

import model.Pokemon;
import model.PokemonType;

public class Caterpie extends Pokemon implements Serializable {
	public Caterpie (String name, String img) {
		super("Caterpie", "caterpie.png");
		setBackImg("caterpieback.png");
		rarity = PokemonType.COMMON;
		this.addAbility("Pound");
		this.addAbility("Air Slash");
		this.addAbility("Double Slap");
		this.addAbility("Crunch");
	}
}
