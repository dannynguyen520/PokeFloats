package controller_view.menuview;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Pokemon;
import model.Trainer;

/**
 * Creates the Pokemon menu.
 * this menu shows all the pokemon that the trainer has in
 * his inventory
 * @author Mohaman
 */

public class PokemonMenu extends GridPane{
  private Trainer trainer;
  private ArrayList<Pokemon> allPokemon;
  private ArrayList<ImageView> pokemonPictures;
  private ArrayList<Label> pokemonLabels;
  
  /**
   * Constructs the Pokemon Menu for the {@link controller_view.menuview.GameMenu}.
   * @param trainer the {@link model.Trainer} object to get the pokemon from.
   */
  public PokemonMenu(Trainer trainer) {
    this.trainer = trainer;
    allPokemon = trainer.getAllPokemons();
    pokemonPictures = new ArrayList<>();
    pokemonLabels = new ArrayList<>();
    init();
  }
  
  /**
   * Initializes the gridpane with a background
   * image and sets padding and gaps
   */
  private void init() {
    this.setVgap(10);
    this.setHgap(10);
    this.setPadding(new Insets(30,0,0,50));
    Image bg = new Image("file:images/Menu/backGround.png");
    BackgroundImage gi = new BackgroundImage(bg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    this.setBackground(new Background(gi));
  }
  
  /**
   * Adds the pokemon to the gridpane.
   * it gets the image path for the imageview from the pokemon object
   * and adds this to the gridpane
   */
  private void addPokemonToMenu() {
    int col = 0, row = 0;
    for(Pokemon pokemon : allPokemon) {
      //String path = pokemon.getImg();
      //Image pok = new Image(path, 40, 40, true, false);
      Image pok = pokemon.getImg();
      ImageView pokeImageView = new ImageView(pok);
      pokeImageView.setFitHeight(40);
      pokeImageView.setFitWidth(40);
      Label pokeLabel = new Label(pokemon.getName());
      pokeLabel.setFont(new Font("Verdana", 15));
      pokeLabel.setTextFill(Color.WHITESMOKE);
      pokemonPictures.add(pokeImageView);
      pokemonLabels.add(pokeLabel);
      this.add(pokeImageView, col, row);
      this.add(pokeLabel, col, row+1);
      col++;
    }
  }
  
  /**
   * Updates the arraylist of pokemon for the menu.
   * this is called on every menu open.
   */
  public void updatePokemon() {
    this.getChildren().removeAll(pokemonPictures);
    this.getChildren().removeAll(pokemonLabels);
    allPokemon = trainer.getAllPokemons();
    addPokemonToMenu();
  }
}
