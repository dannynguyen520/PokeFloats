package pokemon;

import java.io.Serializable;

import model.Pokemon;
import model.PokemonType;

public class Rhyhorn extends Pokemon implements Serializable {
	public Rhyhorn (String name, String img) {
		super("Rhyhorn", "rhyhorn.png");
		setBackImg("rhyhornback.png");
		rarity = PokemonType.UNCOMMON;
		this.addAbility("Pound");
		this.addAbility("Air Slash");
		this.addAbility("Double Slap");
		this.addAbility("Crunch");
	}
}
