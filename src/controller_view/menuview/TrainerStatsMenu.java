package controller_view.menuview;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Trainer;

/**
 * Creates an {@code GridPane} that shows the stats from the trainer during the game.
 * it shows the trainer's name and steps and number of pokemon caught.
 * @author Mohaman
 */
public class TrainerStatsMenu extends GridPane{
  
  private Trainer trainer;
  private Text nameText;
  private Text stepsText;
  private Text pokeCaught;
  
  /**
   * Constructs TrainerStatsMenu menu for the {@link controller_view.menuview.GameMenu}
   * @param trainer Trainer object for the game.
   */
  public TrainerStatsMenu(Trainer trainer) {
    this.trainer = trainer;
    init();
  }
  
  /**
   * Initializes the gridpane with a background
   * image and sets padding and gaps
   */
  private void init() {
    this.setVgap(10);
    this.setHgap(10);
    this.setPadding(new Insets(50,0,0,50));
    
    Image bg = new Image("file:images/Menu/backGround.png");
    BackgroundImage gi = new BackgroundImage(bg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    this.setBackground(new Background(gi));
  }
  
  /**
   * Adds the three stats fields on the gridpane
   */
  private void addStats() {
    nameText = new Text("Name: " + trainer.getName()); nameText.setFont(new Font("Verdana", 20)); nameText.setFill(Color.WHITE);
    stepsText = new Text("Steps: " + trainer.getSteps()); stepsText.setFont(new Font("Verdana", 20)); stepsText.setFill(Color.WHITE);
    pokeCaught = new Text("Pokemon Caught: " + (trainer.getAllPokemons().size()-1)); pokeCaught.setFont(new Font("Verdana", 20)); pokeCaught.setFill(Color.WHITE);
    
    this.add(nameText, 0, 0);
    this.add(stepsText, 0, 1);
    this.add(pokeCaught, 0, 2);
  }
  
  /**
   * Removes stats on the stats menu
   * and re-adds the updated information.
   */
  public void updateStats() {
    this.getChildren().remove(nameText);
    this.getChildren().remove(stepsText);
    this.getChildren().remove(pokeCaught);
    addStats();
  }

}
