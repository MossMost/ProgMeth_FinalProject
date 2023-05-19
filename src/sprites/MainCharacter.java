package sprites;

import java.nio.file.Files;
import java.nio.file.Paths;

import Constant.Constant;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import scenes.SoloGameScene;

public class MainCharacter extends AnimatedSprite{
	
	private static final String IMAGE_PATH = "assets/Player01.png";
	private static final int STEP = 2;
	private boolean isDead = false;
	public static final byte DIE_FRAME = 90;
	

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
		spriteXCoordinates[DIE] = new int[] {0, 48, 96, 144};
		spriteYCoordinates[DIE] = new int[] {192, 192, 192, 192};
		
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
	
	public void die(int x,int y,GraphicsContext gc,long time) {
		new AnimationTimer() {
			public void handle(long currentNanoTime) {
				if(currentNanoTime - time <= 1e9) {
                    currentSpriteChange++;
                    if(currentSpriteChange >= DIE_FRAME) {
                        currentSpriteChange = 0;
                        currentSprite = (byte)((currentSprite + 1) % (spriteXCoordinates[DIE].length));
                    }
                    updateSpriteCoordinates(gc);
                }
				if(currentNanoTime - time > 2e9) {
					setDead(true);
				}
			}
		}.start();
	}
	
	public boolean checkCollition(int xPlayer, int yPlayer, int xBomb,int yBomb) {
		if(yPlayer>=yBomb-48*2+10 && yPlayer<=yBomb+48*2-10 && xPlayer>=xBomb-48+20 && xPlayer<=xBomb+48)
			return true;
		if(xPlayer>=xBomb-48*2+10 && xPlayer<=xBomb+48*2 && yPlayer>=yBomb-48+20 && yPlayer<=yBomb+48)
			return true;
		return false;
	}
	
	protected void updateSpriteCoordinates(GraphicsContext gc) {

		spriteX = spriteXCoordinates[DIE][currentSprite];
		spriteY = spriteYCoordinates[DIE][currentSprite];
        draw(gc);
        
    }
	public boolean getDead() {
		return isDead;
	}
	
	public void setDead(boolean dead) {
		isDead = dead;
	}
}
