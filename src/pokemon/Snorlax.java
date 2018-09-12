package pokemon;

import java.io.Serializable;

import model.Pokemon;
import model.PokemonType;

public class Snorlax extends Pokemon implements Serializable {
	public Snorlax (String name, String img) {
		super("Snorlax", "snorlax.png");
		setBackImg("snorlaxback.png");
		rarity = PokemonType.UNCOMMON;
		this.addAbility("Pound");
		this.addAbility("Air Slash");
		this.addAbility("Double Slap");
		this.addAbility("Crunch");
	}
}
