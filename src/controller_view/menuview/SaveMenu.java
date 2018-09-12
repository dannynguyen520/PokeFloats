package controller_view.menuview;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class SaveMenu extends BorderPane implements Observer {
	private ImageCanvas ic;
	private GraphicsContext gc;
	private String selection;
	private String question;
	private int pointerX, pointerY;
	private int mode;
	
	public SaveMenu() {
		selection = "yes";
		question = "Do you want to save the game?";
		ic = new ImageCanvas();
		this.setCenter(ic);
		pointerX = 125;
		pointerY = 75;
		mode = 1;
		
		updateImage();
	}
	
	public boolean checkIfFileExists() {
		try {
			FileInputStream fileInput = new FileInputStream("PokemonSavedData");
			ObjectInputStream in = new ObjectInputStream(fileInput);
			in.readObject();
			in.close();
			return true;
		} catch (IOException | ClassNotFoundException e) {
			return false;
		} 
	}
	
	public int getMode() {
		return mode;
	}
	
	public void changeText() {
		switch(mode) {
			case 2:
				question = "Do you want to save the game?";
				mode = 1;
				break;
			case 1:
				question = "Game already exists. Overwrite?";
				mode = 2;
				break;
			default: break;
		}
		updateImage();
	}
	
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
			pointerY = 115;
			selection = "no";
		} else if (selection.equals("no")) {
			pointerY = 75;
			selection = "yes";
		}
		updateImage();
	}
	
	class ImageCanvas extends Canvas {
		private Image  bg, pointer;
		private String yes, no;
		
		public ImageCanvas() {
			this.setWidth(550); 
			this.setHeight(200);
			gc = this.getGraphicsContext2D();
			
			//texts
			yes = "Yes";
			no = "No";
			
			//Create images
			bg = new Image("file:images/Menu/mainmenu.png", 550, 200, false, false); //image is 680 x 1216
			pointer = new Image("file:images/Menu/selector.png", 25, 25, false, false); //image is 400 x 400
			
		}
		
		public void draw() {
			gc.drawImage(bg, 0, 0);
			gc.setFont(new Font("Courier new", 25));
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setEffect(new DropShadow(6, 2, 2, Color.BLACK));
			gc.setFill(Color.BLACK);
			gc.fillText(question, 275, 50);
			colorSelection();
			gc.drawImage(pointer, pointerX, pointerY);
		}
		
		public void colorSelection() {
			//pointing at yes
			if (pointerY == 75)
				gc.setFill(Color.PURPLE);
			else
				gc.setFill(Color.BLACK);
			gc.fillText(yes, 175, 95);
			
			//pointing at no
			if (pointerY == 115)
				gc.setFill(Color.PURPLE);
			else
				gc.setFill(Color.BLACK);
			gc.fillText(no, 170, 135);
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		gc.clearRect(0, 0, 894, 596);
		updateImage();
		
	}
}
