package controller_view.menuview;


import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Menu;
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
import javafx.scene.text.Text;
/**
 * Creates the menu for the game.
 * The game menu has three sub-menus: <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;{@link controller_view.menuview.PokemonMenu} - the sub menu that shows the pokemon in inventory.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;{@link controller_view.menuview.ItemMenu} - the sub menu that shows the items in inventory.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;{@link controller_view.menuview.TrainerStatsMenu} - the sub menu that shows the trainer stats.<br>
 * The menu also allows a user to save and exit the game at any time.
 * @author Main
 *
 */
public class GameMenu extends GridPane{

  Menu options;
  ImageView selec;
  String optionSelected;
  
  /**
   * Calls setupMainMenu to construct the menu class.
   */
  public GameMenu() {
    setupMainMenu();
  }
  
  /**
   * Constructs a Gridpane that holds the options that can be selected by the user.
   */
  private void setupMainMenu() {
    this.setWidth(150);
    this.setMaxHeight(150);
    this.setPadding(new Insets(10,0,5,0));
    this.setVgap(10);
    this.setHgap(10);
    Image bg = new Image("file:images/Menu/mainmenu.png");
    BackgroundImage gi = new BackgroundImage(bg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    this.setBackground(new Background(gi));
    Image selector = new Image("file:images/Menu/selector.png", 7, 7, true, false);
    selec = new ImageView(selector); 
    Text pkText = new Text("Pokemon"); pkText.setFont(new Font("Verdana", 15)); pkText.setFill(Color.DARKRED);
    Text ItemText = new Text("Items"); ItemText.setFont(new Font("Verdana", 15)); ItemText.setFill(Color.BLACK);
    Text statsText = new Text("Stats"); statsText.setFont(new Font("Verdana", 15)); statsText.setFill(Color.BLACK);
    Text gameguide = new Text("User Guide"); gameguide.setFont(new Font("Verdana", 15)); gameguide.setFill(Color.BLACK);
    Text saveText = new Text("Save"); saveText.setFont(new Font("Verdana", 15)); saveText.setFill(Color.BLACK);
    Text loadText = new Text("Load"); loadText.setFont(new Font("Verdana", 15)); loadText.setFill(Color.BLACK);
    Text quitText = new Text("Exit"); quitText.setFont(new Font("Verdana", 15)); quitText.setFill(Color.BLACK);
    this.add(selec, 0, 0);
    this.addColumn(1, pkText, ItemText, statsText, gameguide, saveText, loadText, quitText);
    optionSelected = "Pokemon";
  }
  
  /**
   * Handles highlighting the correct option due to the user moving a selector.
   * @param direction the direction that the user wants to move the selector.
   */
  public void handleNavigation(int direction) {
    //1,UP 2,Down
    int selectorLocation = GridPane.getRowIndex(selec);
    int oldOption = selectorLocation;
    if(direction == 2 && selectorLocation < 6) {
      this.getChildren().remove(selec);
      this.add(selec, 0, ++selectorLocation);
      updateColor(oldOption);
    }
    if(direction == 1 && selectorLocation > 0) {
      this.getChildren().remove(selec);
      this.add(selec, 0, --selectorLocation);
      updateColor(oldOption);
    } 
  }
  
  /**
   * Changes the color of the option selected to indicate to the user which option is selected.
   * @param oldrow the row of the previous option
   */
  private void updateColor(int oldrow) {
    int row = GridPane.getRowIndex(selec);
    for(Node node : this.getChildren()) {
      if(node == selec) continue;
      if(GridPane.getRowIndex(node) == row) {
        ((Text)node).setFill(Color.DARKRED);
        optionSelected = ((Text)node).getText();
      }
      if(GridPane.getRowIndex(node) == oldrow) {
        ((Text)node).setFill(Color.BLACK);
      }
    }
  }
  
  /**
   * Returns the option that is selected.
   * @return the option that is selected. this controls what sub-menu or action is taken.
   */
  public String getOptionSelected() {
    return optionSelected;
  }
  
}
