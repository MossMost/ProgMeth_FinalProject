package scenes;

import java.nio.file.Files;
import java.nio.file.Paths;

import Constant.Constant;
import application.Main;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class WelcomeScene extends GeneralScene{
	private static final String BACKGROUND_IMAGE = "assets/Welcome_Background.png";
	
	private Image background;
	
	public WelcomeScene() {
		super();
		
		try {
			background = new Image(Files.newInputStream(Paths.get(BACKGROUND_IMAGE)));
		} catch(Exception e) {
			e.printStackTrace();
		}
		showWelcomeMessage();
	}
	
	private void showWelcomeMessage() {
		/*Font myFont = Font.font("THSarabunPSK", FontWeight.NORMAL, 32);
		gc.setFont(myFont);
		gc.setFill(Color.RED);
		gc.fillText("Bomber Boy", 275, 250);*/
		
		Font myFont = Font.font("THSarabunPSK", FontWeight.EXTRA_BOLD, 40);
		gc.setFont(myFont);
		gc.setFill(Color.WHITE);
		gc.fillText("Press Spacebar to play", 500, 500);
	}

	@Override
	public void draw() {
		activeKeys.clear();
		
		new AnimationTimer() {
			 public void handle(long currentNanoTime){
				 	gc.setFill(Color.BLACK);
				 	gc.fillRect(0, 0, Constant.SCENE_WIDTH, Constant.SCENE_HEIGHT);

				 	gc.drawImage(background, 0, 0);
					showWelcomeMessage();
					
					if(activeKeys.contains(KeyCode.SPACE)){
						this.stop();
						Main.setScene(Main.SOLO_GAME_SCENE);
					}
					else if(activeKeys.contains(KeyCode.ESCAPE)) {
						this.stop();
						Main.exit();
					}
			}
		}.start();
		
	}

}
