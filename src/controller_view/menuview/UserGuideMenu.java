package controller_view.menuview;

import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Creates a menu to show the userguide for a trainer's use
 * using a BorderPane.
 * @author Mohaman
 *
 */
public class UserGuideMenu extends BorderPane{
  private Text guide;
  private ArrayList<String> pages;
  private int i;
  
  public UserGuideMenu() {
    guide = new Text();
    pages = new ArrayList<>();
    guide.setFont(new Font("Verdana", 15));
    guide.setFill(Color.WHITESMOKE);
    this.setPadding(new Insets(30,0,0,60));
    Image bg = new Image("file:images/Menu/backGround.png");
    BackgroundImage gi = new BackgroundImage(bg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    this.setBackground(new Background(gi));
    this.setHeight(200);

    this.setLeft(guide);

    Image selector = new Image("file:images/Menu/selector.png", 50, 50, true, false);
    ImageView imageView = new ImageView(selector);
    Text T = new Text("Continue");
    HBox hBox = new HBox(0, T, imageView);
    this.setRight(hBox);
    BorderPane.setMargin(hBox, new Insets(100 ,  155, 0, 0));
   
    
    setText();
    i = -1;
  }
  
  private void setText() {
    String a = "User Guide: Welcome to pokemon! \n"
        + "Keys: \n\tEnter -- select \n\tleft -- move left \n\tright -- move right \n\tDown -- move down \n\tUp -- move up\n\tm -- Menu key\n\tALT -- use item in menu";
    
    pages.add(a);
    String b = "Maps:\n\nThe Town map is your starting location\n"
                 + "The Safari Zone is located to the north of the starting position.\nThe Cave is located to the north east of the starting location\n";
    pages.add(b);
    String c = "Items:\n\nItems are randomly aquired when walking around the Town or the Cave.\nThese items can be used during the battle by selecting your bag.\n";
    pages.add(c);
    String d = "Battles:\n\nYou cannot catch pokemon outside of the safari zone.\nIf all your pokemon die the game ends.\nIn the Safari Zone pokemon can be caught";
    pages.add(d);
    String e = "Safari Zone:\n\nPokemon are caught in the safari zone.\nTo catch pokemon you can use the 30 pokeballs you are given\n"
        + "The rock and bait affect your chances of catching a pokemon.\nNo other items can be used in the Safari Zone";
    pages.add(e);
    
  }

  public void fillText(String x) {
    Animation animation = new Transition() {
      {
          setCycleDuration(Duration.millis(2000));
      }
  
      @Override
      protected void interpolate(double frac) {
          int length = x.length();
          int n = Math.round(length * (float) frac);
          guide.setText(x.substring(0, n) + "\n");
      }
  
    };
  
    animation.play();

  }
  
  public void continueFill() {
    if(i >= pages.size()-1) return;
    fillText(pages.get(++i));
   
  }
  
  public void reverseFill() {
    if(i <= 0) return;
    fillText(pages.get(--i));
  }
  

}
