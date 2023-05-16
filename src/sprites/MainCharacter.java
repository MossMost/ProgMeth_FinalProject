package sprites;

import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.scene.image.Image;

public class MainCharacter extends AnimatedSprite{
	
	public static final int MAIN_CHARACTER_WIDTH = 48;
	public static final int MAIN_CHARACTER_HEIGHT = 48;
	private static final String IMAGE_PATH = "assets/Player01.png";
	private static final int STEP = 2;
	public MainCharacter() {
		super(MAIN_CHARACTER_WIDTH,MAIN_CHARACTER_HEIGHT);
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
		int newX = x;
		int newY = y;
		if (movement == LEFT)
			newX -= STEP;
		else if (movement == RIGHT)
			newX += STEP;
		else if (movement == UP)
			newY -= STEP;
		else if (movement == DOWN)
			newY += STEP;
		moveTo(newX, newY);
		animate(movement);
	}

}
