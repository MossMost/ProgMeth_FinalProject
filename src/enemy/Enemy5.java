package enemy;

import java.nio.file.Files;
import java.nio.file.Paths;

import constant.Constant;
import javafx.scene.image.Image;
import scenes.SoloGameScene;

public class Enemy5 extends Monster{
	private static final String IMAGE_PATH = "assets/Enemy5.png";
	private static final int STEP = 1;
	
	public Enemy5() {
		super();
		try {
			spriteImage = new Image(Files.newInputStream(Paths.get(IMAGE_PATH)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		spriteXCoordinates[Constant.LEFT] = new int[] {0, 48, 96};
		spriteYCoordinates[Constant.LEFT] = new int[] {0, 0, 0};
		spriteXCoordinates[Constant.RIGHT] = new int[] {0, 48, 96};
		spriteYCoordinates[Constant.RIGHT] = new int[] {48, 48, 48};
		spriteXCoordinates[Constant.DOWN] = new int[] {0, 48, 96};
		spriteYCoordinates[Constant.DOWN] = new int[] {0, 0, 0};
		spriteXCoordinates[Constant.UP] = new int[] {0, 48, 96}; 
		spriteYCoordinates[Constant.UP] = new int[] {48, 48, 48};
		spriteXCoordinates[Constant.DIE] = new int[] {0, 48, 96, 0, 48, 96, 0, 48, 96, 0, 48, 96, 0, 48, 96, 0, 48, 96};
		spriteYCoordinates[Constant.DIE] = new int[] {96, 96, 96, 144, 144, 144, 192, 192, 192, 240, 240, 240, 288, 288, 288, 336, 336, 336};
		
		updateSpriteCoordinates();
	}
	
	public void move(int movement) {
		int newX = x;
		int newY = y;
		int oldX = x;
		int oldY = y;
		
		if (movement == Constant.LEFT && newX - STEP >= 40)
			newX -= STEP;
		else if (movement == Constant.RIGHT && newX + STEP <= 1008 - 44*2)
			newX += STEP;
		else if (movement == Constant.UP && newY - STEP >= 48*3)
			newY -= STEP;
		else if (movement == Constant.DOWN && newY + STEP <= 720 - 48*2)
			newY += STEP;
		
		if (movement == Constant.LEFT && newX - STEP < 40)
			movement = randomMovement(movement, STEP);
		else if (movement == Constant.RIGHT && newX + STEP > 1008 - 44*2)
			movement = randomMovement(movement, STEP);
		else if (movement == Constant.UP && newY - STEP < 48*3)
			movement = randomMovement(movement, STEP);
		else if (movement == Constant.DOWN && newY + STEP > 720 - 48*2)
			movement = randomMovement(movement, STEP);
		
		int sz = SoloGameScene.wallCoordinates.size();
		for(int i = 0; i < sz; i++) {
			int wallX = SoloGameScene.wallCoordinates.get(i).getKey();
			int wallY = SoloGameScene.wallCoordinates.get(i).getValue();
			
			if (newX > wallX - 40 && newX <= wallX + 35 && newY > wallY - 47 && newY <= wallY + 35) {
				movement = randomMovement(movement, STEP);
				break;
			}
		}
		
		sz = SoloGameScene.wallBrickCoordinates.size();
		for(int i = 0; i < sz; i++) {
			int wallX = SoloGameScene.wallBrickCoordinates.get(i).getKey();
			int wallY = SoloGameScene.wallBrickCoordinates.get(i).getValue();
			
			if (newX > wallX - 40 && newX <= wallX + 35 && newY > wallY - 47 && newY <= wallY + 35) {
				movement = randomMovement(movement, STEP);
				break;
			}
		}
		
		/*sz = SoloGameScene.BombCoordinates.size();
		for(int i = 0; i < sz; i++) {
			int wallX = SoloGameScene.BombCoordinates.get(i).getKey();
			int wallY = SoloGameScene.BombCoordinates.get(i).getValue();
			
			if (newX > wallX - 40 && newX <= wallX + 35 && newY > wallY - 47 && newY <= wallY + 35) {
				movement = randomMovement(movement, STEP);
				break;
			}
		}*/
		
		if (movement == Constant.LEFT && newX - STEP >= 40)
			oldX -= STEP;
		else if (movement == Constant.RIGHT && newX + STEP <= 1008 - 44*2)
			oldX += STEP;
		else if (movement == Constant.UP && newY - STEP >= 48*3)
			oldY -= STEP;
		else if (movement == Constant.DOWN && newY + STEP <= 720 - 48*2)
			oldY += STEP;

		moveTo(oldX, oldY);
		animate(movement);
		
	}
	
}