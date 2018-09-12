package pokemon;

import java.io.Serializable;

import model.Pokemon;
import model.PokemonType;

public class Slowpoke extends Pokemon implements Serializable {
	public Slowpoke (String name, String img) {
		super("Slowpoke", "slowpoke.png");
		setBackImg("slowback.png");
		rarity = PokemonType.COMMON;
		this.addAbility("Pound");
		this.addAbility("Air Slash");
		this.addAbility("Double Slap");
		this.addAbility("Crunch");
	}
}