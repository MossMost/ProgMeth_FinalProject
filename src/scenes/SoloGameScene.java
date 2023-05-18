package scenes;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import application.Main;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import sprites.AnimatedSprite;
import sprites.MainCharacter;


public class SoloGameScene extends GeneralScene{
	private static final String BACKGROUND_IMAGE = "assets/background.png";
	private static final String BRICK_IMAGE = "assets/Brick.png";
	private int posXBrick[] = new int[110];
	private int posYBrick[] = new int[110];
	
	private Image background,brick;
	private MainCharacter Player;
	public SoloGameScene() {
		super();
		addWall();
		
		try {
			background = new Image(Files.newInputStream(Paths.get(BACKGROUND_IMAGE)));
			brick = new Image(Files.newInputStream(Paths.get(BRICK_IMAGE)));
			Player = new MainCharacter();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void draw() {
		activeKeys.clear();
		Player.moveTo(1*48, 3*48);
		new AnimationTimer() {
			 public void handle(long currentNanoTime){
				 	gc.setFill(Color.BLACK);
				 	gc.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
				 	
					gc.drawImage(background, 0, 0);
					
					for(int i=0;i<70 ;i++) {
						gc.drawImage(brick, posXBrick[i], posYBrick[i]);
					}
					
				 	Player.draw(gc);
				 	
					if(activeKeys.contains(KeyCode.ESCAPE)){
						this.stop();
						Main.setScene(Main.WELCOME_SCENE);
					}
					else if(activeKeys.contains(KeyCode.ENTER)) {
						this.stop();
						Main.setScene(Main.CREDITS_SCENE);
					}
					else if(activeKeys.contains(KeyCode.LEFT)) {
						Player.move(MainCharacter.LEFT);
					}
					else if(activeKeys.contains(KeyCode.RIGHT)) {
						Player.move(MainCharacter.RIGHT);
					}
					else if(activeKeys.contains(KeyCode.UP)) {
						Player.move(MainCharacter.UP);
					}
					else if(activeKeys.contains(KeyCode.DOWN)) {
						Player.move(MainCharacter.DOWN);
					}
					else if(activeKeys.contains(KeyCode.SPACE)) {
						//Player.bomb();
					}
			}
		}.start();
	}
	
	private void addWall() {
		for(int i=2; i<=18; i+=2) {
			for(int j=4; j<=12; j+=2) {
				AnimatedSprite.wallXCoordinates.add(i*48);
				AnimatedSprite.wallYCoordinates.add(j*48);
			}
		}
		
		//randomWall
		Random rand = new Random();
		int posX,posY;
		for(int tmp = 0; tmp < 70; tmp++) {
			while(true) {
				posX = rand.nextInt(20)*48;
				posY = rand.nextInt(14)*48;
				
				if(posX == 0 || posY < 3*48) 
					continue;
				if(AnimatedSprite.wallXCoordinates.contains(posX) 
				&& AnimatedSprite.wallYCoordinates.contains(posY))
					continue;
				if(posX == 48 && posY == 3*48 || posX == 48*2 && posY == 3*48 
				|| posX == 48 && posY == 4*48) 
					continue;
				
				//showImage
				posXBrick[tmp] = posX;
				posYBrick[tmp] = posY;
				break;
			}
			
		}
		
		//addWall
		for(int i=0;i<100;i++) {
			AnimatedSprite.wallXCoordinates.add(posXBrick[i]);
			AnimatedSprite.wallYCoordinates.add(posYBrick[i]);
		}

	}
}
