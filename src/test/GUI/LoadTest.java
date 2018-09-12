package test.GUI;

import java.util.Observer;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import viewer.LoadView;

public class LoadTest extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
//	private Button startAnimationButton;
	private BorderPane window;

	private Observer currentView;
	private Observer localView;
	
	@Override
	public void start(Stage stage) throws Exception {
		window = new BorderPane();
		Scene scene = new Scene(window, 894, 596);
		
		localView = new LoadView();
		setViewTo(localView);
		
		stage.setScene(scene);
		stage.show();
	}
	
	private void setViewTo(Observer newView) {
		  window.setCenter(null);
		  currentView = newView;
		  window.setCenter((Node) currentView);
	  }
}
