package enemy;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import constant.Constant;
import javafx.scene.image.Image;

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
		if (movement == Constant.LEFT && newX - STEP >= 40)
			newX -= STEP;
		else if (movement == Constant.RIGHT && newX + STEP <= 1008 - 44*2)
			newX += STEP;
		else if (movement == Constant.UP && newY - STEP >= 48*3)
			newY -= STEP;
		else if (movement == Constant.DOWN && newY + STEP <= 720 - 48*2)
			newY += STEP;

		flutter(getCurrentDirection(), newX, newY);
		
	}
	
	public void flutter(int movement, int newX, int newY) {
		

		if (movement == Constant.LEFT && newX - STEP < 40)
			movement = randomMovement(movement, STEP);
		else if (movement == Constant.RIGHT && newX + STEP > 1008 - 44*2)
			movement = randomMovement(movement, STEP);
		else if (movement == Constant.UP && newY - STEP < 48*3)
			movement = randomMovement(movement, STEP);
		else if (movement == Constant.DOWN && newY + STEP > 720 - 48*2)
			movement = randomMovement(movement, STEP);
		
		if(newX%96  > 40 && newX%96 < 64 && newY%96 > 46 && newY%96 < 64) {
			
			if(movement == Constant.LEFT || movement == Constant.RIGHT) {
				int random;
				Random rand = new Random();
				random = rand.nextInt(500);
				if(random<=5) {
					moveTo(newX, newY);
					if(random<=2)
						animate(Constant.UP);
					else 
						animate(Constant.DOWN);
				}
				else {
					moveTo(newX, newY);
					animate(movement);
				}
			}
			else {

				int random;
				Random rand = new Random();
				random = rand.nextInt(500);
				if(random<=5) {
					moveTo(newX, newY);
					if(random<=2)
						animate(Constant.LEFT);
					else 
						animate(Constant.RIGHT);
				}
				else {
					moveTo(newX, newY);
					animate(movement);
				}
			}
		}
		else {
			moveTo(newX, newY);
			animate(movement);
		}
	}
	
}