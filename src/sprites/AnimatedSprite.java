package sprites;


public class AnimatedSprite extends Sprite{
	public static final int TOTAL_MOVEMENTS = 5;
	public static final int RIGHT = 0;
	public static final int LEFT =1;
	public static final int UP = 2;
	public static final int DOWN = 3;
	public static final int DIE = 4;
	public static final byte SPRITE_CHANGE = 15;
	
	protected int currentDirection;
	protected byte currentSprite;
	protected byte currentSpriteChange;
	
	protected int[][] spriteXCoordinates = new int[TOTAL_MOVEMENTS][];
	protected int[][] spriteYCoordinates = new int[TOTAL_MOVEMENTS][];
	
	public AnimatedSprite(int width, int height) {
		super(width, height);
		setCurrentDirection(DOWN);
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
	
	public void setCurrentDirection(int tmp) {
		currentDirection = tmp;
	}
	public int getCurrentDirection() {
        return currentDirection;
    }
}