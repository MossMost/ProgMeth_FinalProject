package scenes;

import java.io.File;
import constant.Constant;

import application.Main;
import enemy.MonsterCry;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import music.MusicPlayable;

public class GameOverScene extends GeneralScene implements MusicPlayable, Animateable{
	
	public static final String BACKGROUND_SONG = "assets/CreditSceneMusic.wav";
	private MonsterCry monster;
	
	public GameOverScene()
	{
		super();
		monster = new MonsterCry();
	}
	


	@Override
	public void draw() {
		activeKeys.clear();
		playLoopMusic();
		new AnimationTimer() {
			 public void handle(long currentNanoTime){
				 	showImage();
				 	showMessage();
					showAnimate();
					if(activeKeys.contains(KeyCode.SPACE)){
						this.stop();
						stopMusic();
						Main.setScene(Constant.WELCOME_SCENE);
					}	
			}
		}.start();
	}
	
	@Override
	public void showMessage() {
		Font myFont = Font.font("Arial", FontWeight.NORMAL, 80);
		gc.setFont(myFont);
		gc.setFill(Color.RED);
		gc.fillText("GAME OVER", 250, 200);
		
		myFont = Font.font("Arial", FontWeight.NORMAL, 25);
		gc.setFont(myFont);
		gc.setFill(Color.WHITE);
		gc.fillText("Press Spacebar to go back to Welcome Scene", 250, 545);
	}

	@Override
	public void showImage() {
	 	gc.setFill(Color.BLACK);
	 	gc.fillRect(0, 0, Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
	 	
	}

	@Override
	public void showAnimate() {
		monster.moveTo(430, 300);
	 	monster.draw(gc);
	 	monster.animate();
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