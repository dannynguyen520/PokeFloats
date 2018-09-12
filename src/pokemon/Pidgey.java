package pokemon;

import java.io.Serializable;

import model.Pokemon;
import model.PokemonType;

public class Pidgey extends Pokemon implements Serializable {
	public Pidgey (String name, String img) {
		super("Pidgey", "pidgey.png");
		setBackImg("pidgeyback.png");
		rarity = PokemonType.COMMON;
		this.addAbility("Pound");
		this.addAbility("Air Slash");
		this.addAbility("Double Slap");
		this.addAbility("Crunch");
	}
}
