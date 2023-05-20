package scenes;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import Music.MusicPlayable;

import Constant.Constant;
import application.Main;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class WelcomeScene extends GeneralScene implements MusicPlayable{
	private static final String BACKGROUND_IMAGE = "assets/Welcome_Background.png";
	public static final String BACKGROUND_SONG = "assets/WelcomeSceneMusic.wav";
	private Image background;
	
	public WelcomeScene() {
		super();
		
		try {
			background = new Image(Files.newInputStream(Paths.get(BACKGROUND_IMAGE)));
			showWelcomeMessage();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void showWelcomeMessage() {
		Font myFont = Font.font("THSarabunPSK", FontWeight.EXTRA_BOLD, 40);
		gc.setFont(myFont);
		gc.setFill(Color.WHITE);
		gc.fillText("Press Spacebar to play", 500, 500);
	}

	@Override
	public void draw() {
		playloopmusic();
		activeKeys.clear();
		
		new AnimationTimer() {
			 public void handle(long currentNanoTime){
				 	gc.setFill(Color.BLACK);
				 	gc.fillRect(0, 0, Constant.SCENE_WIDTH, Constant.SCENE_HEIGHT);

				 	gc.drawImage(background, 0, 0);
					showWelcomeMessage();
					
					if(activeKeys.contains(KeyCode.SPACE)){
						stopmusic();
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
	
	@Override
	public void playloopmusic() {
		sound = new Media(new File(BACKGROUND_SONG).toURI().toString());
		mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer.play();
	}

	@Override
	public void stopmusic() {
		mediaPlayer.stop();
	}
	

}
