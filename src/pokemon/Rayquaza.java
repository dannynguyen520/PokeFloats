package pokemon;

import java.io.Serializable;

import model.Pokemon;
import model.PokemonType;

public class Rayquaza extends Pokemon implements Serializable {
	public Rayquaza(String name, String string) {
		super("Rayquaza","rayquaza.png");
		setBackImg("rayquazaback.png");
		rarity = PokemonType.RARE;
		this.addAbility("Pound");
		this.addAbility("Air Slash");
		this.addAbility("Double Slap");
		this.addAbility("Crunch");
	}
}
