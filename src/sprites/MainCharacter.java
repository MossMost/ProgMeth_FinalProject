package sprites;

import java.nio.file.Files;
import java.nio.file.Paths;

import Constant.Constant;
import javafx.scene.image.Image;
import scenes.SoloGameScene;

public class MainCharacter extends AnimatedSprite{
	
	private static final String IMAGE_PATH = "assets/Player01.png";
	private static final int STEP = 2;
	public MainCharacter() {
		super(Constant.BLOCK_SIZE,Constant.BLOCK_SIZE);
		try {
			spriteImage = new Image(Files.newInputStream(Paths.get(IMAGE_PATH)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		spriteXCoordinates[RIGHT] = new int[] {0, 48, 96, 144};
		spriteYCoordinates[RIGHT] = new int[] {0, 0, 0, 0};
		spriteXCoordinates[LEFT] = new int[] {0, 48, 96, 144};
		spriteYCoordinates[LEFT] = new int[] {48, 48, 48, 48};
		spriteXCoordinates[UP] = new int[] {0, 48, 96, 144};
		spriteYCoordinates[UP] = new int[] {96, 96, 96, 96};
		spriteXCoordinates[DOWN] = new int[] {0, 48, 96, 144};
		spriteYCoordinates[DOWN] = new int[] {144, 144, 144, 144};
		
		updateSpriteCoordinates();
	}
	
	public void move(int movement) {
		int newX = x, oldX = x;
		int newY = y, oldY = y;
		if (movement == LEFT && newX - STEP >= 40)
			newX -= STEP;
		else if (movement == RIGHT && newX + STEP <= 1008 - 44*2)
			newX += STEP;
		else if (movement == UP && newY - STEP >= 48*3)
			newY -= STEP;
		else if (movement == DOWN && newY + STEP <= 720 - 48*2)
			newY += STEP;
		
		//check wall
		int sz = SoloGameScene.wallCoordinates.size();
		for(int i=0;i<sz;i++) {
			int wallX = SoloGameScene.wallCoordinates.get(i).getKey();
			int wallY = SoloGameScene.wallCoordinates.get(i).getValue();
			
			if (newX > wallX-40 && newX <= wallX+35 && newY > wallY-47 && newY <= wallY+32) {
				newX = oldX;
				newY = oldY;
			}
		}
		
		sz = SoloGameScene.wallBrickCoordinates.size();
		for(int i=0;i<sz;i++) {
			int wallX = SoloGameScene.wallBrickCoordinates.get(i).getKey();
			int wallY = SoloGameScene.wallBrickCoordinates.get(i).getValue();
			
			if (newX > wallX-40 && newX <= wallX+35 && newY > wallY-47 && newY <= wallY+32) {
				newX = oldX;
				newY = oldY;
			}
		}
		
		
		moveTo(newX, newY);
		animate(movement);
	}

}
