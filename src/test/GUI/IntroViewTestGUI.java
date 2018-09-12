package test.GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import viewer.IntroView;

public class IntroViewTestGUI extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
//	private Button startAnimationButton;
	private IntroView localView;
	
	@Override
	public void start(Stage stage) throws Exception {
		BorderPane pane = new BorderPane();
		localView = new IntroView();
		pane.setCenter(localView);
		

		Scene scene = new Scene(pane, 894, 596);
		stage.setScene(scene);
		stage.show();
		
		
		localView.animate();
	}
}
