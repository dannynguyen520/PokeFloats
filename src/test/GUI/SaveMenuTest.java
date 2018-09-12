package test.GUI;

import java.util.Observer;

import controller_view.menuview.SaveMenu;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SaveMenuTest extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	private StackPane root;
	private BorderPane window;

	private Observer currentView;
	private SaveMenu localView;
	private KeyListener test;
	
	@Override
	public void start(Stage stage) throws Exception {
		root = new StackPane();
		window = new BorderPane();
		root.getChildren().add(window);
		Scene scene = new Scene(root, 894, 596);
		test = new KeyListener();
		
		localView = new SaveMenu();
		setViewTo(localView);
		
		scene.setOnKeyPressed(test);
		stage.setScene(scene);
		stage.show();
	}
	
	private void setViewTo(Observer newView) {
		  window.setCenter(null);
		  currentView = newView;
		  window.setCenter((Node) currentView);
	}
	
	private class KeyListener implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent e) {
			switch(e.getCode()) {
				case UP:
					localView.changePointer();
					break;
				case DOWN:
					localView.changePointer();
					break;
				case ENTER:
					//Mode 1: Do you want to save the game?
					if (localView.getMode() == 1) {
						if (localView.getSelection().equals("yes")) {
//							if (localView.checkIfFileExists()) {
							if (true) {
								localView.changeText();
							} else {
								//save the file
							}
						} else {
							//remove pane from the stack
						}
					//Mode 2: Do you want to overwrite saved game?
					} else if (localView.getMode() == 2) {
						if (localView.getSelection().equals("yes")) {
							//save file
						} else {
							localView.changeText();
						}
					}
					break;
				default: break;
			}
		}
	}
}
