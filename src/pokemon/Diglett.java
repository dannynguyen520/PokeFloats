package pokemon;

import java.io.Serializable;

import model.Pokemon;
import model.PokemonType;

public class Diglett extends Pokemon implements Serializable {
	public Diglett (String name, String img) {
		super("Diglett", "diglett.png");
		setBackImg("diglettback.png");
		rarity = PokemonType.COMMON;
		this.addAbility("Pound");
		this.addAbility("Air Slash");
		this.addAbility("Double Slap");
		this.addAbility("Crunch");
	}
}
