package viewer;

/**
 * This class holds the starting animation for pokemon legalize pokefloats edition.
 * It uses javaFX animation's timeline to do a sequence of animations. The trick 
 * to making one animation after another is the use of a tic counters. The view will
 * be paired up with a keyhandler on the GUI to change views when enter is pressed.
 * Because the animation will be played and the key handler doesn't know when to hit enter,
 * a boolean was used to keep track of when enter should be available and is turned
 * true when the right time happens within the sequence. 
 * 
 * @author Danny Nguyen
 */

import java.util.Observable;
import java.util.Observer;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class IntroView extends StackPane implements Observer {
	
	private ImageCanvas ic;
	private GraphicsContext g2;
	private Timeline timeline;
	private final int width = 894;
	private final int height = 596;
	
	private int ticLimit = 30;
	
	private boolean pressEnterPart;
	Rectangle square;
	FadeTransition ft;
	BorderPane all;
	
	
	public IntroView() {
		
		square = new Rectangle(894,596);
		square.setFill(Color.BLACK);
		
		all = new BorderPane();
		
		this.getChildren().add(all);
		
		this.getChildren().add(square);
		
		//Innitialize Canvas and set it
		ic = new ImageCanvas();
		
		all.setCenter(ic);
		
		ft = new FadeTransition(Duration.millis(1000), square);
		ft.setFromValue(1);
		ft.setToValue(0);
				
	}
	
	public boolean getPressEnter() {
		return pressEnterPart;
	}
	
	//Starts the animation
	public void animate() {
		ft.play();
		timeline.play();

	}
	
	
	
	private class ImageCanvas extends Canvas {
		private Image may;
		private Image bg, introSpriteSheet;
		private Image title, version;
		
		private String text;
		
		public ImageCanvas() {
			//Temporarily, will remove once we have a observer class going
			pressEnterPart = false;
			
			this.setWidth(894);  //+194
			this.setHeight(596); //-104
			g2 = this.getGraphicsContext2D();
			
			may       = new Image("file:images/intro/may2.png");
			
			bg        = new Image("file:images/intro/introBG.png", 3600, 625, false, false);
			introSpriteSheet = new Image("file:images/intro/intro.gif");
			
			title = new Image("file:images/intro/PokemonTitle.gif", 347, 175, false, true);
			version = new Image("file:images/intro/Version2.png", 300, 125, false, true);
			
			text = "Press Enter";
			
			//Create animation handler
			timeline = new Timeline(new KeyFrame(Duration.millis(50), new AnimateStarter()));
			timeline.setCycleCount(Animation.INDEFINITE);
		}
		
		private class AnimateStarter implements EventHandler<ActionEvent> {
			private int sx, sy, sw, sh, dx, dy, dw, dh;
			private int tic = 0;
			private int flygonX, flygonY, flygonPhase;
			private int dogSx, dogSy, dogDx, dogDy, dogPhase;
			private int birdSx, birdSy, birdDx, birdDy, birdPhase;
			private int bugSx, bugSy, bugDx, bugDy, bugPhase;
			private int maySx, maySy, mayDx, mayDy, mayPhase;
						
			private double opacity, opacity2;
			private int delay, delay2, delay3, delay4, delay5;
			
			public AnimateStarter() {		
				dx = dy = 0;
				dw = width;
				dh = height;
				sx = 2700;
				sy = 0;
				sw = width;
				sh = height;
				g2.drawImage(bg, sx, sy, sw, sh, dx, dy, dw, dh);
				
				delay = delay2 = delay3 = delay4 = delay5 = 0;
				opacity = opacity2 = 0;
				
				//Flygon
				flygonX = 900;
				flygonY = 250;
				flygonPhase = 0;
				
				//Dog
				dogSx = dogSy = 0;
				dogDx = 900;
				dogDy = 450;
				dogPhase = 0;
				
				//Bird
				birdSx = 301;
				birdSy = 0;
				birdDx = 900;
				birdDy = 550;
				birdPhase = 0;
				
				//Bug
				bugSx = 235;
				bugSy = 0;
				bugDx = 900;
				bugDy = 150;
				bugPhase = 0;
				
				//May
				maySx = 617;
				maySy = 83;
				mayDx = 900;
				mayDy = 400;
				mayPhase = 0;
				
			}
			
			@Override
			public void handle(ActionEvent e) {
				tic++;
				sx -= 25;
				if (sx < 0)
					sx = 2500;
				g2.drawImage(bg, sx, sy, sw, sh, dx, dy, dw, dh);
				
				if (tic > 20) {
					switch(flygonPhase) {
						case 0: 
							if (flygonX < 450)
								flygonPhase = 1;
							flygonX -= 5; 
							break;
						case 1:
							if (flygonX > 600)
								flygonPhase = 2;
							flygonX += 2;
							break;
						case 2:
							if (flygonY < 125)
								break;
							flygonY -= 1;
							break;
						default: break;
					}
					g2.drawImage(introSpriteSheet, 451, 0, 125, 43, flygonX, flygonY, 250, 86);
				}
				
				if (tic > 60) {
					switch(dogPhase) {
						case 0: 
							if (dogDx < 250)
								dogPhase = 1;
							dogDx -= 8; 
							break;
						case 1:
							if (dogDx > 400)
								break;
							dogDx += 2;
							break;
						default: break;
					}
					g2.drawImage(introSpriteSheet, dogSx, dogSy, 57, 50, dogDx, dogDy, 114, 100);
					if (delay == 3) {
						dogSx += 57;
						if (dogSx > 171)
							dogSx = 0;
						delay = 0;
					}
					delay++;
				}
				
				if (tic > 90) {
					switch(birdPhase) {
						case 0: 
							if (birdDx < 350)
								birdPhase = 1;
							birdDx -= 9; 
							break;
						case 1:
							if (birdDx > 900)
								break;
							birdDx += 12;
							break;
						default: break;
					}
					switch(bugPhase) {
						case 0:
							if (bugDx < 250)
								bugPhase = 1;
							bugDx -= 15;
							break;
						case 1:
							if (bugDx > 400)
								bugPhase = 2;
							bugDx += 15;
							break;
						case 2:
							if (bugDx < 250 && bugDy < 100)
								bugPhase = 3;
							bugDx -= 15;
							bugDy -= 10;
							break;
						case 3:
							if (bugDx > 400) 
								break;
							bugDx += 15;
							break;
						default: break;		
					}
					switch(mayPhase) {
					case 0:
						if (mayDx < 550)
							mayPhase = 1;
						mayDx -= 8;
						break;
					case 1:
						if (mayDx > 700)
							break;
						mayDx += 2;
						break;
					default: break;		
				}
					g2.drawImage(introSpriteSheet, birdSx, birdSy, 21, 25, birdDx, birdDy, 42, 50);
					g2.drawImage(introSpriteSheet, bugSx, bugSy, 30, 30, bugDx, bugDy, 60, 60);
					g2.drawImage(may, maySx, maySy, 46, 51, mayDx, mayDy, 92, 102);
					
					if (delay2 == 3) {
						if (birdPhase == 0) {
							birdSx += 21;
							if (birdSx > 364)
								birdSx = 301;
							delay2 = 0;
						} else if (birdPhase == 1) {
							if (birdSx < 364)
								birdSx += 21;
							else if (birdSx == 364) {
								birdSx = 388;
							} else if (birdSx == 388) {
								birdSx += 30;
							}
							delay2 = 0;
						}
					}
					if (delay3 == 2) {
						bugSx += 30;
						if (bugSx > 265)
							bugSx = 235;
						delay3 = 0;
					}
					if (delay4 == 4) {
						maySy += 51;
						if (maySy > 185)
							maySy = 83;
						delay4 = 0;
					}
					delay4++;
					delay3++;
					delay2++;
				}
				
				if (tic > 120) {
					if (opacity != 1.0) 
						opacity += 0.05;
					g2.setGlobalAlpha(opacity);
					g2.drawImage(title, 260, 100);
					g2.drawImage(version, 285, 210);
					g2.setGlobalAlpha(1);
				}
				
				if (tic > 160) {
					if (opacity2 != 1.0) 
						opacity2 += 0.05;
					g2.setFill(Color.WHITE);
					g2.setFont(Font.font("Courier", FontWeight.BOLD, 30));
					g2.setGlobalAlpha(opacity2);
					if (delay5 == 40) {
						if (text.equals("Press Enter"))
							text = "";
						else
							text = "Press Enter";
						delay5 = 0;
					}
					g2.fillText(text, 360, 400);
					g2.setGlobalAlpha(1);
					pressEnterPart = true;
					delay5++;
				}
				
				if (tic > ticLimit) {
					timeline.pause();
					ticLimit +=40;
					timeline.play();
				}
			}
			
		}
	}
	
//	private class ImageCanvas extends Canvas {
//		private Image  raquazaBG, title, leftCloud, rightCloud, version;
//		
//		public ImageCanvas() {
//			//Temporarily, will remove once we have a observer class going
//			pressEnterPart = false;
//			
//			this.setWidth(894);  //+194
//			this.setHeight(596); //-104
//			g2 = this.getGraphicsContext2D();
//			
//			//Create images
//			title = new Image("file:images/intro/PokemonTitle.gif", 694, 350, false, true);
//			leftCloud = new Image("file:images/intro/LeftCloud.jpg", 477, 596, false, true);
//			rightCloud = new Image("file:images/intro/RightCloud.jpg", 477, 596, false, true);
//			raquazaBG = new Image("file:images/intro/RaquazaBG.jpg", 894, 596, false, true);
//			version = new Image("file:images/intro/Version.png", 300, 150, false, true);
//			
//			
//			
//			//Create animation handler
//			timeline = new Timeline(new KeyFrame(Duration.millis(50), new AnimateStarter()));
//			timeline.setCycleCount(Animation.INDEFINITE);
//		}
//		
//		private class AnimateStarter implements EventHandler<ActionEvent> {
//			private int tic = 0;
//			private int x1 = 0;
//			private int x2 = 477;
//			private double opacity;
//			private String text;
//
//			
//			public AnimateStarter() {
//				
//				g2.setFill(Color.BLACK);
//				g2.fillRect(0, 0, 894, 596);
//				g2.drawImage(leftCloud, 0, 0);
//				g2.drawImage(rightCloud, 477, 0);
//				text = "Press Enter";
//
//			}
//			
//			@Override
//			public void handle(ActionEvent e) {
//				x1 -= 4;
//				x2 += 4;
//				
//				//Clouds moving apart
//				if (tic < 20) {
//					tic++;
//					g2.setFill(Color.BLACK);
//					g2.fillRect(0, 0, 894, 596);
//					g2.drawImage(leftCloud, x1, 0);
//					g2.drawImage(rightCloud, x2, 0);
//				//opacity of cloud fades
//				} else if (tic < 100) {
//					tic++;
//					opacity = ((double) tic) / 100;
//					g2.setGlobalAlpha(opacity);
//					g2.drawImage(leftCloud, x1, 0);
//					g2.drawImage(rightCloud, x2, 0);
//					g2.setFill(Color.WHITE.deriveColor(0, 1, 1, opacity));
//					g2.fillRect(0, 0, 894, 596);
//				//slowly transition into Black
//				} else if (tic < 125) {
//					tic += 1;
//					opacity = ((double) tic) / 125;
//
//					g2.setGlobalAlpha(opacity);
//					g2.setFill(Color.BLACK.deriveColor(0, 1, 1, opacity));
//					g2.fillRect(0, 0, 894, 596);
//				//fades to black becomes transparent
//				} else if (tic < 130) {
//					tic += 2;
//					opacity = 1 - ((double) tic) / 130;
//					g2.setGlobalAlpha(opacity);
//					g2.setFill(Color.TRANSPARENT.deriveColor(0, 1, 1, opacity));
//					g2.fillRect(0, 0, 894, 596);
//				//raquaza picture appears and the pokemon 
//				} else if (tic < 140) {
//					tic++;
//					opacity = 1 - ((double) tic) / 140;
//					g2.setGlobalAlpha(opacity);
//					g2.drawImage(raquazaBG, 0, 0);
//				} else if (tic < 145) {
//					tic++;
//					opacity = 1 - ((double) tic) / 145;
//					g2.setGlobalAlpha(opacity);
//					g2.drawImage(title, 100, 25);
//					g2.drawImage(version, 310, 220);
//				} else if (tic < 155){
//					pressEnterPart = true;
//					tic++;
//					g2.setGlobalAlpha(1);
//					g2.drawImage(raquazaBG, 0, 0);
//					g2.drawImage(title, 100, 25);
//					g2.drawImage(version, 310, 220);
//					g2.setFont(Font.font("Verdana", FontWeight.BOLD, 35));
//					g2.setTextAlign(TextAlignment.CENTER);
//					g2.setFill(Color.WHITE);
//					g2.fillText(text, 477, 475);
//					
//				}
//				//Stop animation
//				if (tic >= 160) {
//					timeline.stop();
//				} 
//			}
//			
//		}
//	}

	@Override
	public void update(Observable arg0, Object arg1) {
		
	}
	
	
	
	
}
