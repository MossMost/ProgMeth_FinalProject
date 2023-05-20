package sprites;

import java.nio.file.Files;
import java.nio.file.Paths;

import Constant.Constant;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Pair;
import scenes.SoloGameScene;

public class MainCharacter extends AnimatedSprite{
	
	private static final String IMAGE_PATH = "assets/Player01.png";
	private int STEP = 2;
	private int fireRange = 4;
	private int amountBomb = 2;
	private boolean isDead = false;
	private boolean canwalk = true;
	public static final byte DIE_FRAME = 90;
	protected boolean IsInBomb;

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
		if(!getCanWalk())
			return;
		
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
		//checkbomb
		sz = SoloGameScene.BombCoordinates.size();
		for(int i=0;i<sz;i++) {
			int wallX = SoloGameScene.BombCoordinates.get(i).getKey();
			int wallY = SoloGameScene.BombCoordinates.get(i).getValue();
			if(SoloGameScene.BombArr.get(i).getIsInBomb() && newX > wallX - 45 && newX < wallX + 49 && newY  < wallY + 49 && newY  > wallY-47) {
				continue;
			}
			
			SoloGameScene.BombArr.get(i).setIsInBomb(false);
			
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
		setCanWalk(false);
	}
	
	public boolean checkBomb(int x,int y, int range) {
		if(checkCollision(this.getX(), this.getY(), x, y)) 
			return true;
		for(int i=1;i<=range;i++) {
			if(SoloGameScene.wallBrickCoordinates.contains(new Pair<>(x,y-48*i)) || SoloGameScene.wallCoordinates.contains(new Pair<>(x,y-48*i))) {
				break;
			}
			if(checkCollision(this.getX(),this.getY(),x,y-48*i)) {
				return true;
			}
		}
		for(int i=1;i<=range;i++) {
			if(SoloGameScene.wallBrickCoordinates.contains(new Pair<>(x,y+48*i)) || SoloGameScene.wallCoordinates.contains(new Pair<>(x,y+48*i))) {
				break;
			}
			if(checkCollision(this.getX(),this.getY(),x,y+48*i)) {
				return true;
			}
		}
		for(int i=1;i<=range;i++) {
			if(SoloGameScene.wallBrickCoordinates.contains(new Pair<>(x-48*i,y)) || SoloGameScene.wallCoordinates.contains(new Pair<>(x-48*i,y))) {
				break;
			}
			if(checkCollision(this.getX(),this.getY(),x-48*i,y)) {
				return true;
			}
		}
		for(int i=1;i<=range;i++) {
			if(SoloGameScene.wallBrickCoordinates.contains(new Pair<>(x+48*i,y)) || SoloGameScene.wallCoordinates.contains(new Pair<>(x+48*i,y))) {
				break;
			}
			if(checkCollision(this.getX(),this.getY(),x+48*i,y)) {
				return true;
			}
		}
		return false;		
	}
	
	public boolean checkCollision(int xPlayer, int yPlayer, int xObj,int yObj) {
		if(xPlayer>=xObj-48+15 && xPlayer<=xObj+48-15 && yPlayer>=yObj-48+7 && yPlayer<=yObj+48-15)
			return true;
		return false;
	}
	
	protected void updateSpriteCoordinates(GraphicsContext gc) {

		spriteX = spriteXCoordinates[DIE][currentSprite];
		spriteY = spriteYCoordinates[DIE][currentSprite];
        draw(gc);
        
    }
	
	public int getFireRange() {
		return fireRange;
	}
	
	public void setFireRange(int num) {
		fireRange = num;
	}
	
	public int getAmountBomb() {
		return amountBomb;
	}
	
	public void setAmountBomb(int num) {
		amountBomb = num;
	}
	
	public boolean getDead() {
		return isDead;
	}
	
	public void setDead(boolean dead) {
		isDead = dead;
	}
	public boolean getCanWalk() {
		return canwalk;
	}
	
	public void setCanWalk(boolean canwalk) {
		this.canwalk = canwalk;
	}
}
