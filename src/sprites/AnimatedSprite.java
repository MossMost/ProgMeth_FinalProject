package sprites;

import java.util.ArrayList;

public class AnimatedSprite extends Sprite{
	public static final int ToTAL_MOVEMENTS = 4;
	public static final int RIGHT = 0;
	public static final int LEFT =1;
	public static final int UP = 2;
	public static final int DOWN = 3;
	public static final byte SPRITE_CHANGE = 15;
	
	public static ArrayList<Integer> wallXCoordinates = new ArrayList<Integer>();
	public static ArrayList<Integer> wallYCoordinates = new ArrayList<Integer>();
	
	protected int currentDirection;
	protected byte currentSprite;
	protected byte currentSpriteChange;
	
	protected int[][] spriteXCoordinates = new int[ToTAL_MOVEMENTS][];
	protected int[][] spriteYCoordinates = new int[ToTAL_MOVEMENTS][];
	
	public AnimatedSprite(int width, int height) {
		super(width, height);
		currentDirection = DOWN;
		currentSprite = 0;
		currentSpriteChange = 0;
	}
	
	public void animate(int movement) {
		
		if(movement != currentDirection) {
			currentDirection = movement;
			currentSprite = 0;
			currentSpriteChange = 0;
		} else {
			currentSpriteChange++;
			if(currentSpriteChange >= SPRITE_CHANGE) {
				currentSpriteChange = 0;
				currentSprite = (byte)((currentSprite + 1) % spriteXCoordinates[currentDirection].length);
			}
			
		}
		updateSpriteCoordinates();
	}
	
	protected void updateSpriteCoordinates() {
		spriteX = spriteXCoordinates[currentDirection][currentSprite];
		spriteY = spriteYCoordinates[currentDirection][currentSprite];
	}
}
