package pokemon;

import java.io.Serializable;

import model.Pokemon;
import model.PokemonType;

public class MrMime extends Pokemon implements Serializable {
	public MrMime(String name, String string) {
		super("MrMime","mrmime.png");
		setBackImg("mrmimeback.png");
		rarity = PokemonType.RARE;
		this.addAbility("Pound");
		this.addAbility("Air Slash");
		this.addAbility("Double Slap");
		this.addAbility("Crunch");
	}
}
