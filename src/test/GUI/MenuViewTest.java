package test.GUI;


import java.util.ArrayList;
import java.util.List;

import controller_view.menuview.GameMenu;
import controller_view.menuview.ItemMenu;
import controller_view.menuview.PokemonMenu;
import controller_view.menuview.SaveMenu;
import controller_view.menuview.TrainerStatsMenu;
import controller_view.menuview.UserGuideMenu;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.GameObject;
import model.Item;
import model.Trainer;
import viewer.TownView;

public class MenuViewTest extends Application{
  
  private MenuKeyListener menuListener;
  private KeyPressedListener mainListener;
  private Scene scene;
  private StackPane root;
  private GameMenu menu;
  private PokemonMenu pokemonMenu;
  private ItemMenu itemMenu;
  private TrainerStatsMenu tStatsMenu;
  private SaveMenu saveMenu;
  private boolean saveMenuShowing;
  private boolean menuShowing;
  private boolean menuLocked;
  private boolean pokeMenuShowing;
  private boolean itemMenuShowing;
  private boolean statsMenuShowing;
  private boolean guideShowing;
  private UserGuideMenu guideMenu;
  private TownView town;
  private GameObject game;
  private Trainer testTrainer;
  List<String>items;
  List<String>pokemon;
  public static void main(String [] args) {
    launch(args);
  }
  
  @Override
  public void start(Stage primaryStage) throws Exception {
    testTrainer = new Trainer("Test");
    game = new GameObject();
    town = new TownView(game);
    root = new StackPane();
    mainListener = new KeyPressedListener();
    menuListener = new MenuKeyListener();
    
    menu = new GameMenu();
    itemMenu = new ItemMenu(game);
    pokemonMenu = new PokemonMenu(testTrainer);
    tStatsMenu = new TrainerStatsMenu(testTrainer);
    saveMenu = new SaveMenu();
    guideMenu = new UserGuideMenu();
    
    menuLocked = false;
    pokeMenuShowing = false;
    itemMenuShowing = false;
    statsMenuShowing = false;
    saveMenuShowing = false;
    guideShowing = false;
    
    setItems();
    setPokemon();
    
    root.getChildren().add(town);

    scene = new Scene(root, 894, 596);
    scene.setOnKeyPressed(mainListener);
    primaryStage.setScene(scene);
    primaryStage.show();
    
  }
  
  private void setPokemon() {
    // TODO Auto-generated method stub
    
  }

  private void setItems() {
    items = new ArrayList<>();
    items.add("Bait");
    items.add("Fragrance");
    items.add("HPPotion");
    items.add("RazerClaw");
    items.add("Repel");
    items.add("Rock");
    items.add("SafariBall");
    items.add("Trap");
    
    for(String s : items) {
      testTrainer.addItem(new Item(s));
    }
    
    
  }

  private class KeyPressedListener implements EventHandler<KeyEvent> {

    @Override
    public void handle(KeyEvent event) {
      // TODO Auto-generated method stub
      // Match the input key with the arrows

      if(town.isMoving())
        return;

      switch (event.getCode()) {
      case ALT:
        KeyFrame start = null, end = null;
        //if sub menu is open, then menu is locked open
        if(menuLocked) break;
        if(!menuShowing) {
          root.getChildren().add(menu);
          start = new KeyFrame(Duration.ZERO, new KeyValue(menu.translateXProperty(), root.getWidth()));
          end = new KeyFrame(Duration.seconds(0.5), new KeyValue(menu.translateXProperty(), root.getWidth() - 150));
          scene.setOnKeyPressed(menuListener);
          Timeline slide = new Timeline(start,end);
          menuShowing = true;
          slide.play();
        }
        break;
      default:
      }
    }
  }
  
  public class MenuKeyListener implements EventHandler<KeyEvent>{




    @SuppressWarnings("unused")
    @Override
    public void handle(KeyEvent event) {
      switch (event.getCode()) {
      case M:
        if(menuLocked) break;
        KeyFrame start = new KeyFrame(Duration.ZERO, new KeyValue(menu.translateXProperty(), root.getWidth()-150));
        KeyFrame end = new KeyFrame(Duration.seconds(0.5), new KeyValue(menu.translateXProperty(), root.getWidth()));
        scene.setOnKeyPressed(mainListener);
        Timeline slide = new Timeline(start,end);
        slide.setOnFinished(e -> root.getChildren().remove(menu));
        slide.play();
        menuShowing = false;
        break;
      case ALT:
        if(itemMenuShowing) itemMenu.useItem();
        break;
      case RIGHT:
        if(guideShowing) guideMenu.continueFill();
        if(itemMenuShowing) itemMenu.highlightItemIncrease();
        break;
      case LEFT:
        if(guideShowing) guideMenu.reverseFill();
        if(itemMenuShowing) itemMenu.highlightItemDecrese();
        break;
      case DOWN:
        if(saveMenuShowing) {
          saveMenu.changePointer();
          break;
        }
        menu.handleNavigation(2);
        break;
      case UP:
        if(saveMenuShowing) {
          saveMenu.changePointer();
          break;
        }
        menu.handleNavigation(1);
        break;
      case ENTER:
        //handle save menu
        if(saveMenuShowing) {
          //Mode 1: Do you want to save the game?
          if (saveMenu.getMode() == 1) {
            if (saveMenu.getSelection().equals("yes")) {
//              if (localView.checkIfFileExists()) {
              if (true) {
                saveMenu.changeText();
              } else {
                //save the file
              }
            } else {
              start = new KeyFrame(Duration.ZERO, new KeyValue(saveMenu.translateYProperty(), root.getHeight()-root.getHeight()));
              end = new KeyFrame(Duration.seconds(1), new KeyValue(saveMenu.translateYProperty(), root.getHeight()));
              menuLocked = false;
              Timeline t = new Timeline(start, end);
              t.setOnFinished(e -> root.getChildren().remove(saveMenu));
              saveMenuShowing = false;
              t.play();
            }
          //Mode 2: Do you want to overwrite saved game?
          } else if (saveMenu.getMode() == 2) {
            if (saveMenu.getSelection().equals("yes")) {
              //save file
            } else {
              saveMenu.changeText();
            }
          }
          break;
        }
        
        
        //handle menu navigation
        String option = menu.getOptionSelected();
        if(option.equals("Pokemon") || option.equals("Items") || option.equals("Stats")) {
          if(option.equals("Pokemon")) {
            if(!pokeMenuShowing) {
              pokemonMenu.updatePokemon();
              root.getChildren().add(pokemonMenu);
              start = new KeyFrame(Duration.ZERO, new KeyValue(pokemonMenu.translateYProperty(), root.getHeight()));
              end = new KeyFrame(Duration.seconds(0.5), new KeyValue(pokemonMenu.translateYProperty(), root.getHeight()- 200));
              menuLocked = true;
            }else {
              start = new KeyFrame(Duration.ZERO, new KeyValue(pokemonMenu.translateYProperty(), root.getHeight() - 200));
              end = new KeyFrame(Duration.seconds(0.5), new KeyValue(pokemonMenu.translateYProperty(), root.getHeight()));
              menuLocked = false;
            }
            Timeline sl = new Timeline(start, end);
            if(pokeMenuShowing) sl.setOnFinished(e -> root.getChildren().remove(pokemonMenu));
            pokeMenuShowing = !pokeMenuShowing;
            sl.play();
          }else if(option.equals("Items")){
            if(!itemMenuShowing) {
              itemMenu.updateItems();
              root.getChildren().add(itemMenu);
              start = new KeyFrame(Duration.ZERO, new KeyValue(itemMenu.translateYProperty(), root.getHeight()));
              end = new KeyFrame(Duration.seconds(0.5), new KeyValue(itemMenu.translateYProperty(), root.getHeight()- 200));
              menuLocked = true;
            }else {
              start = new KeyFrame(Duration.ZERO, new KeyValue(itemMenu.translateYProperty(), root.getHeight() - 200));
              end = new KeyFrame(Duration.seconds(0.5), new KeyValue(itemMenu.translateYProperty(), root.getHeight()));
              menuLocked = false;
            }
            Timeline sl = new Timeline(start, end);
            if(itemMenuShowing) sl.setOnFinished(e -> root.getChildren().remove(itemMenu));
            itemMenuShowing = !itemMenuShowing;
            sl.play();
          }else {
            if(!statsMenuShowing) {
              tStatsMenu.updateStats();
              root.getChildren().add(tStatsMenu);
              start = new KeyFrame(Duration.ZERO, new KeyValue(tStatsMenu.translateYProperty(), root.getHeight()));
              end = new KeyFrame(Duration.seconds(0.5), new KeyValue(tStatsMenu.translateYProperty(), root.getHeight()- 200));
              menuLocked = true;
            }else {
              start = new KeyFrame(Duration.ZERO, new KeyValue(tStatsMenu.translateYProperty(), root.getHeight() - 200));
              end = new KeyFrame(Duration.seconds(0.5), new KeyValue(tStatsMenu.translateYProperty(), root.getHeight()));
              menuLocked = false;
            }
            Timeline sl = new Timeline(start, end);
            if(statsMenuShowing) sl.setOnFinished(e -> root.getChildren().remove(tStatsMenu));
            statsMenuShowing = !statsMenuShowing;
            sl.play();
          }

        }else {
          if(option.equals("Exit")) {
            Platform.exit();
          }
          if(option.equals("Save")) {
            if(!saveMenuShowing) {
              root.getChildren().add(saveMenu);
              start = new KeyFrame(Duration.ZERO, new KeyValue(saveMenu.translateYProperty(), root.getHeight()));
              end = new KeyFrame(Duration.seconds(1), new KeyValue(saveMenu.translateYProperty(), root.getHeight()-root.getHeight()));
              menuLocked = true;
            }else {
              start = new KeyFrame(Duration.ZERO, new KeyValue(saveMenu.translateYProperty(), root.getHeight()-root.getHeight()));
              end = new KeyFrame(Duration.seconds(1), new KeyValue(saveMenu.translateYProperty(), root.getHeight()));
              menuLocked = false;
            }
            Timeline timeline = new Timeline(start, end);
            if(saveMenuShowing) timeline.setOnFinished(e -> root.getChildren().remove(saveMenu));
            saveMenuShowing = !saveMenuShowing;
            timeline.play();
            break;
          }
          
          if(option.equals("User Guide")) {
            if(!guideShowing) {
              root.getChildren().add(guideMenu);
              start = new KeyFrame(Duration.ZERO, new KeyValue(guideMenu.translateYProperty(), root.getHeight()));
              end = new KeyFrame(Duration.seconds(0.5), new KeyValue(guideMenu.translateYProperty(), root.getHeight()- 200));
              menuLocked = true;
            }else {
              start = new KeyFrame(Duration.ZERO, new KeyValue(guideMenu.translateYProperty(), root.getHeight() - 200));
              end = new KeyFrame(Duration.seconds(0.5), new KeyValue(guideMenu.translateYProperty(), root.getHeight()));
              menuLocked = false;
            }
            Timeline sl = new Timeline(start, end);
            if(guideShowing) {
              sl.setOnFinished(e -> root.getChildren().remove(guideMenu));
            }else {
              sl.setOnFinished(e -> guideMenu.continueFill());
            };
            
            guideShowing = !guideShowing;
            sl.play();
          }
        }

      default:
        break;
      }
    }
  }
}

