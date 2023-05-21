package scenes;

import java.io.File;


import Music.MusicPlayable;
import application.Main;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import Enemy.MonsterCry;

public class CreditsScene extends GeneralScene implements MusicPlayable{
	
	public static final String BACKGROUND_SONG = "assets/CreditSceneMusic.wav";
	private MonsterCry monster;
	
	protected MediaPlayer mediaPlayer;
	protected Media sound;
	public CreditsScene()
	{
		super();
		monster = new MonsterCry();
	}
	
	private void showCreditsMessage() {
		Font myFont = Font.font("Arial", FontWeight.NORMAL, 80);
		gc.setFont(myFont);
		gc.setFill(Color.RED);
		gc.fillText("GAME OVER", 250, 200);
		myFont = Font.font("Arial", FontWeight.NORMAL, 40);
		gc.setFont(myFont);
		gc.setFill(Color.WHITE);
		gc.fillText("Press Spacebar to go back to Welcome Scene", 100, 585);
	}

	@Override
	public void draw() {
		activeKeys.clear();
		playLoopMusic();
		new AnimationTimer() {
			 public void handle(long currentNanoTime){
				 	gc.setFill(Color.BLACK);
				 	gc.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
				 	monster.moveTo(430, 300);
				 	monster.draw(gc);
				 	monster.animate();
				 	showCreditsMessage();
					
					if(activeKeys.contains(KeyCode.SPACE)){
						this.stop();
						stopMusic();
						Main.setScene(Main.WELCOME_SCENE);
					}	
			}
		}.start();
	}

	@Override
	public void playLoopMusic() {
		sound = new Media(new File(BACKGROUND_SONG).toURI().toString());
		mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer.play();
	}
	
	@Override
	public void stopMusic() {
		mediaPlayer.stop();
	}
}