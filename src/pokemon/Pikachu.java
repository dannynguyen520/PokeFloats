package pokemon;

import java.io.Serializable;

import model.Pokemon;
import model.PokemonType;

public class Pikachu extends Pokemon implements Serializable {
	public Pikachu (String name, String img) {
		super("Pikachu", "pikachu.png");
		setBackImg("pikachuback.png");
		rarity = PokemonType.UNCOMMON;
		this.addAbility("Pound");
		this.addAbility("Air Slash");
		this.addAbility("Double Slap");
		this.addAbility("Crunch");
	}
}