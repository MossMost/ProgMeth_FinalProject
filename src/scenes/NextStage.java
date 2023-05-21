package scenes;

import java.io.File;

import Constant.Constant;
import Enemy.Enemy1;
import Enemy.Enemy2;
import Enemy.Enemy3;
import Enemy.Enemy4;
import Enemy.Enemy5;
import application.Main;
import javafx.animation.AnimationTimer;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import sprites.AnimatedSprite;

public class NextStage extends GeneralScene{
	public static final String NEXTSTAGE_EFFECT = "assets/NextStage.wav";
	
	private static MediaPlayer mediaPlayerEffects;
	private static Media effect;
	
	private Enemy1 enemy1;
	private Enemy2 enemy2;
	private Enemy3 enemy3;
	private Enemy4 enemy4;
	private Enemy5 enemy5;
	
	public NextStage() {
		super();
		enemy1 = new Enemy1();
		enemy2 = new Enemy2();
		enemy3 = new Enemy3();
		enemy4 = new Enemy4();
		enemy5 = new Enemy5();
	}
	
	private void showStageMessage() {
		
		if(SoloGameScene.stage == 1) {
			Font myFont = Font.font("THSarabunPSK", FontWeight.EXTRA_BOLD, 120);
			gc.setFont(myFont);
			gc.setFill(Color.WHITE);
			gc.fillText("Stage " + SoloGameScene.stage, 300, 360);
		
			myFont = Font.font("verdana", FontWeight.BOLD, 50);
			gc.setFont(myFont);
			gc.setFill(Color.WHITE);
			gc.fillText("X 3", 380, 570);
		
			myFont = Font.font("verdana", FontWeight.BOLD, 50);
			gc.setFont(myFont);
			gc.setFill(Color.WHITE);
			gc.fillText("X 2", 640, 570);
		
		}
		
		if(SoloGameScene.stage == 2) {
			Font myFont = Font.font("THSarabunPSK", FontWeight.EXTRA_BOLD, 120);
			gc.setFont(myFont);
			gc.setFill(Color.WHITE);
			gc.fillText("Stage " + SoloGameScene.stage, 300, 360);
			
			myFont = Font.font("verdana", FontWeight.BOLD, 50);
			gc.setFont(myFont);
			gc.setFill(Color.WHITE);
			gc.fillText("X 2", 255, 570);
		
			myFont = Font.font("verdana", FontWeight.BOLD, 50);
			gc.setFont(myFont);
			gc.setFill(Color.WHITE);
			gc.fillText("X 2", 515, 570);
		
			myFont = Font.font("verdana", FontWeight.BOLD, 50);
			gc.setFont(myFont);
			gc.setFill(Color.WHITE);
			gc.fillText("X 2", 775, 570);
		}
		
		if(SoloGameScene.stage == 3) {
			Font myFont = Font.font("THSarabunPSK", FontWeight.EXTRA_BOLD, 120);
			gc.setFont(myFont);
			gc.setFill(Color.WHITE);
			gc.fillText("Stage " + SoloGameScene.stage, 300, 260);
			
			myFont = Font.font("verdana", FontWeight.BOLD, 50);
			gc.setFont(myFont);
			gc.setFill(Color.WHITE);
			gc.fillText("X 2", 380, 430);
		
			myFont = Font.font("verdana", FontWeight.BOLD, 50);
			gc.setFont(myFont);
			gc.setFill(Color.WHITE);
			gc.fillText("X 2", 640, 430);
		
			myFont = Font.font("verdana", FontWeight.BOLD, 50);
			gc.setFont(myFont);
			gc.setFill(Color.WHITE);
			gc.fillText("X 1", 255, 570);
		
			myFont = Font.font("verdana", FontWeight.BOLD, 50);
			gc.setFont(myFont);
			gc.setFill(Color.WHITE);
			gc.fillText("X 2", 515, 570);
		
			myFont = Font.font("verdana", FontWeight.BOLD, 50);
			gc.setFont(myFont);
			gc.setFill(Color.WHITE);
			gc.fillText("X 1", 775, 570);
		}
		
		
	}
	
	@Override
	public void draw() {
		activeKeys.clear();
		playEffect(NEXTSTAGE_EFFECT);
		
		new AnimationTimer() {
			boolean ch = true;
			long time;
			public void handle(long currentNanoTime){
				 	if(ch) {
				 		ch = false;
				 		time = currentNanoTime;
				 	}
				 	
				 	if(currentNanoTime - time >= 3e9) {
				 		this.stop();
				 		Main.setScene(Main.SOLO_GAME_SCENE);
				 	}
				 	gc.setFill(Color.BLACK);
				 	gc.fillRect(0, 0, Constant.SCENE_WIDTH, Constant.SCENE_HEIGHT);
				 	
				 	if(SoloGameScene.stage == 1) {
				 		enemy1.moveTo(275, 500);
				 		enemy1.draw(gc,2,2);
				 		enemy1.animate(AnimatedSprite.LEFT);
				 		enemy2.moveTo(535, 500);
				 		enemy2.draw(gc,2,2);
				 		enemy2.animate(AnimatedSprite.LEFT);
				 	}
				 	
				 	if(SoloGameScene.stage == 2) {
				 		enemy1.moveTo(145, 500);
				 		enemy1.draw(gc,2,2);
				 		enemy1.animate(AnimatedSprite.LEFT);
				 		enemy2.moveTo(405, 500);
				 		enemy2.draw(gc,2,2);
				 		enemy2.animate(AnimatedSprite.LEFT);
				 		enemy3.moveTo(665, 500);
				 		enemy3.draw(gc,2,2);
				 		enemy3.animate(AnimatedSprite.LEFT);
				 	}
				 	
				 	if(SoloGameScene.stage == 3) {
				 		enemy1.moveTo(270, 350);
				 		enemy1.draw(gc,2,2);
				 		enemy1.animate(AnimatedSprite.LEFT);
				 		enemy2.moveTo(530, 350);
				 		enemy2.draw(gc,2,2);
				 		enemy2.animate(AnimatedSprite.LEFT);
				 		enemy3.moveTo(145, 500);
				 		enemy3.draw(gc,2,2);
				 		enemy3.animate(AnimatedSprite.LEFT);
				 		enemy4.moveTo(405, 500);
				 		enemy4.draw(gc,2,2);
				 		enemy4.animate(AnimatedSprite.LEFT);
				 		enemy5.moveTo(665, 500);
				 		enemy5.draw(gc,2,2);
				 		enemy5.animate(AnimatedSprite.LEFT);
				 	}
				 	
				 	
				 	showStageMessage();
					
			}
		}.start();
		
	}
	
	public void playEffect(String path)
	{
		effect = new Media(new File(path).toURI().toString());
		mediaPlayerEffects = new MediaPlayer(effect);
		mediaPlayerEffects.play();
	}

}