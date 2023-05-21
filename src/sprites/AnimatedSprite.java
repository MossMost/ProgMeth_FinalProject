package sprites;

import constant.Constant;

public class AnimatedSprite extends Sprite{
	protected int currentDirection;
	protected byte currentSprite;
	protected byte currentSpriteChange;
	
	protected int[][] spriteXCoordinates = new int[Constant.TOTAL_MOVEMENTS][];
	protected int[][] spriteYCoordinates = new int[Constant.TOTAL_MOVEMENTS][];
	
	public AnimatedSprite(int width, int height) {
		super(width, height);
		setCurrentDirection(Constant.DOWN);
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
			if(currentSpriteChange >= Constant.SPRITE_CHANGE) {
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
	
	public void setCurrentDirection(int tmp) {
		currentDirection = tmp;
	}
	public int getCurrentDirection() {
        return currentDirection;
    }
}