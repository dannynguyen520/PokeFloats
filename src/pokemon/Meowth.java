package pokemon;

import java.io.Serializable;

import model.Pokemon;
import model.PokemonType;

public class Meowth extends Pokemon implements Serializable {
	public Meowth (String name, String img) {
		super("Meowth", "meowth.png");
		setBackImg("meowthback.png");
		rarity = PokemonType.COMMON;
		this.addAbility("Pound");
		this.addAbility("Air Slash");
		this.addAbility("Double Slap");
		this.addAbility("Crunch");
	}
}
