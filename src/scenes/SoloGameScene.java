package scenes;

import java.nio.file.Files;
import java.nio.file.Paths;

import application.Main;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import sprites.MainCharacter;


public class SoloGameScene extends GeneralScene{
	private static final String BACKGROUND_IMAGE = "assets/background.png";
	
	
	private Image background;
	private MainCharacter bear;
	public SoloGameScene() {
		super();
		try {
			background = new Image(Files.newInputStream(Paths.get(BACKGROUND_IMAGE)));
			bear = new MainCharacter();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void draw() {
		activeKeys.clear();
		bear.moveTo(48, 144);
		new AnimationTimer() {
			 public void handle(long currentNanoTime){
				 	gc.setFill(Color.BLACK);
				 	gc.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
				 	
					gc.drawImage(background, 0, 0);
				 	bear.draw(gc);
				 	
					if(activeKeys.contains(KeyCode.ESCAPE)){
						this.stop();
						Main.setScene(Main.WELCOME_SCENE);
					}
					else if(activeKeys.contains(KeyCode.ENTER)) {
						this.stop();
						Main.setScene(Main.CREDITS_SCENE);
					}
					else if(activeKeys.contains(KeyCode.LEFT)) {
						bear.move(MainCharacter.LEFT);
					}
					else if(activeKeys.contains(KeyCode.RIGHT)) {
						bear.move(MainCharacter.RIGHT);
					}
					else if(activeKeys.contains(KeyCode.UP)) {
						bear.move(MainCharacter.UP);
					}
					else if(activeKeys.contains(KeyCode.DOWN)) {
						bear.move(MainCharacter.DOWN);
					}
			}
		}.start();
		
		
		
	}
}
