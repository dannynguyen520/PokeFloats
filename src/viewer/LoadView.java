package viewer;

/**
 * This class holds the view for the load screen, detailing the option to either 
 * have the game loads from a previous state that was saved or start a new game.
 * If yes was chosen, there should be a list of saved files to choose from. No would
 * just start the game fresh changing the screen straight to the beginning
 * of the game.
 * 
 * @author Danny Nguyen
 */

import java.util.Observable;
import java.util.Observer;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class LoadView extends BorderPane implements Observer {
	
	private ImageCanvas ic;
	private GraphicsContext gc;
	private String selection;
	private int pointerX;
	private int pointerY;
	
	
	public LoadView() {
		//Selecting pointers
		pointerX = 520;
		pointerY = 315;
		selection = "no";
		
		//Innitialize Canvas and set it
		ic = new ImageCanvas();
		this.setCenter(ic);

		updateImage();
	}
	/**
	 * updateImage() clears the path for the GraphicsContext and redraws
	 * the image. This is usually called when the user has moved the selection
	 * from yes or no.
	 */
	public void updateImage() {
		gc.beginPath();
		ic.draw();
	}
	public String getSelection() {
		return selection;
	}
	
	public void changePointer() {
		//mode is just whether its up or down, i got lazy
		if (selection.equals("yes")) {
			pointerY = 315;
			selection = "no";
		} else if (selection.equals("no")) {
			pointerY = 215;
			selection = "yes";
		}
		updateImage();
	}
	
	private class KeyListener implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent e) {
			//Check and see if the game is still running
			if (e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.UP) {
				if (pointerY == 215) {
					System.out.println("Pressed down");
					selection = "no";
					pointerY = 315;
				} else {
					System.out.println("Pressed up");
					selection = "yes";
					pointerY = 215;
				}
				updateImage();
			}
			
		}
		
	}
	
	class ImageCanvas extends Canvas {
		private Image  bg, pointer;
		private String question, yes, no;
		
		public ImageCanvas() {
			this.setWidth(894);  //+194
			this.setHeight(596); //-104
			gc = this.getGraphicsContext2D();
			
			//texts
			question = "Do you want to load game?";
			yes = "Yes";
			no = "No";
			
			//Create images
			bg = new Image("file:images/load/bg.png"); //image is 680 x 1216
			pointer = new Image("file:images/load/pointer.png"); //image is 400 x 400
			
		}
		
		public void draw() {
			gc.drawImage(bg, 514, 305, 156, 117, 0, 0, 894, 596);
			gc.setFont(new Font("Courier new", 40));
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setEffect(new DropShadow(6, 2, 2, Color.BLACK));
			gc.setFill(Color.BLACK);
			gc.fillText(question, 450, 100);
			colorSelection();
			gc.drawImage(pointer, 0, 197, 70, 123, pointerX, pointerY, 25, 50);
		}
		
		public void colorSelection() {
			//pointing at yes
			if (pointerY == 215)
				gc.setFill(Color.LIGHTBLUE);
			else
				gc.setFill(Color.BLACK);
			gc.fillText(yes, 450, 250);
			
			//pointing at no
			if (pointerY == 315)
				gc.setFill(Color.LIGHTBLUE);
			else
				gc.setFill(Color.BLACK);
			gc.fillText(no, 450, 350);
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		gc.clearRect(0, 0, 894, 596);
		updateImage();
		
	}
	
	
	
	
}
