package scenes;

import java.io.File;

import application.Main;
import constant.Constant;
import enemy.MonsterLove;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class VictoryScene extends GeneralScene implements Animateable{
	public static final String VICTORY_EFFECT = "assets/Victory.wav";
	
	private MonsterLove monster;
	
	private static MediaPlayer mediaPlayerEffects;
	private static Media effect;
	
	public VictoryScene() {
		super();
		monster = new MonsterLove();
	}
	@Override
	public void draw() {
		activeKeys.clear();
		playEffect(VICTORY_EFFECT);
		new AnimationTimer() {
			 public void handle(long currentNanoTime){
				 
					showImage();
				 	showMessage();
				 	showAnimate();
				 	
					if(activeKeys.contains(KeyCode.SPACE)){
						this.stop();
						Main.setScene(Main.WELCOME_SCENE);
					}
			}
		}.start();
	}

	@Override
	public void showMessage() {
		Font myFont = Font.font("Arial", FontWeight.NORMAL, 80);
		gc.setFont(myFont);
		gc.setFill(Color.YELLOW);
		gc.fillText("VICTORY", 330, 200);
		
		myFont = Font.font("Arial", FontWeight.NORMAL, 25);
		gc.setFont(myFont);
		gc.setFill(Color.WHITE);
		gc.fillText("Press Spacebar to go back to Welcome Scene", 250, 545);
	}

	@Override
	public void showImage() {
		gc.setFill(Color.BLACK);
	 	gc.fillRect(0, 0, Constant.SCENE_WIDTH, Constant.SCENE_HEIGHT);
	 	
	}
	@Override
	public void showAnimate() {
		monster.moveTo(430, 300);
	 	monster.draw(gc);
	 	monster.animate();
	}
	
	public void playEffect(String path)
	{
		effect = new Media(new File(path).toURI().toString());
		mediaPlayerEffects = new MediaPlayer(effect);
		mediaPlayerEffects.play();
	}

}