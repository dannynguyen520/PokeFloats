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
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import model.GameObject;
import model.Item;
import model.Trainer;
/**
 * Creates a menu to show all the items in a trainer's inventory
 * using a Gridpane.
 * @author Mohaman
 *
 */
public class ItemMenu extends GridPane{
  Trainer trainer;
  ArrayList<Item> allItems;
  ArrayList<StackPane> itemPictures;
  ArrayList<Label> itemLabels;
  String itemSelected;
  int j;
  GameObject game;
  
  /**
   * Constructs the Item Menu using {@link model.Trainer}.
   * @param trainer the {@link model.Trainer} object 
   */
  public ItemMenu(GameObject game) {
    itemSelected = "";
    this.game = game;
    this.trainer = game.getCurrPlayer();
    allItems = trainer.getItems();
    itemPictures = new ArrayList<>();
    itemLabels = new ArrayList<>();
    j = 0;
    init();
  }
  
  /**
   * Initializes the gridpane with a background
   * image and sets padding and gaps
   */
  private void init() {
    this.setVgap(10);
    this.setHgap(12);
    this.setPadding(new Insets(20,0,0,50));
    Image bg = new Image("file:images/Menu/backGround.png");
    BackgroundImage gi = new BackgroundImage(bg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    this.setBackground(new Background(gi));
  }

  /**
   * Adds the items to the gridpane.
   * it gets the item path for the imageview from the item name
   * and adds this to the gridpane
   */
  private void addItemsToMenu() {
    int col = 0, row = 0;
    for(Item item : allItems) {
      StackPane p = new StackPane();
      String path = "file:images/Menu/" + item.getName() + ".png";
      Image itemImage = new Image(path, 40, 40, true, false);
      ImageView itemImageView = new ImageView(itemImage);
      Label itemLabel = new Label(item.getName());
      itemLabel.setFont(new Font("Verdana", 15));
      itemLabel.setTextFill(Color.WHITESMOKE);
      itemPictures.add(p);
      
      p.getChildren().add(itemImageView);
      itemLabels.add(itemLabel);
      this.add(p, col, row);
      this.add(itemLabel, col, row+1);
      col++;
    }
  }
  
  /**
   * Refreshes the information on the menu by removing the items and
   * calling additemsToMenu again
   */
  public void updateItems() {
    this.getChildren().removeAll(itemPictures);
    this.getChildren().removeAll(itemLabels);
    allItems = trainer.getItems();
    addItemsToMenu();
    if(this.getChildren().size() == 0) return;
    StackPane a = (StackPane) this.getChildren().get(0);
    Label b = (Label) this.getChildren().get(1);
    a.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(2.0))));
    itemSelected = b.getText();
    System.out.println(itemSelected);
  }

  public void highlightItemIncrease() {
    if(this.getChildren().size() == 0) return;
    StackPane old;
    if(j < this.getChildren().size()-2) {
      j += 2;
      old = (StackPane) this.getChildren().get(j-2);
      old.setBorder(null);
    }
    
    Label b = (Label) this.getChildren().get(j+1);
    StackPane pane = (StackPane) this.getChildren().get(j);
    pane.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(2.0))));

    itemSelected = b.getText();
  }
  
  public void highlightItemDecrese() {
    if(this.getChildren().size() == 0) return;
    
    if(j >= 2) {
      j -= 2;
      StackPane old = (StackPane) this.getChildren().get(j+2);
      old.setBorder(null);
    }
    
    Label b = (Label) this.getChildren().get(j+1);
    StackPane pane = (StackPane) this.getChildren().get(j);
    pane.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(2.0))));
    
    itemSelected = b.getText();
  }
  
  public void useItem() {
    game.useItem(itemSelected);
  }
}
