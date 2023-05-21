package Enemy;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import Constant.Constant;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Pair;
import scenes.SoloGameScene;
import sprites.AnimatedSprite;


public class Enemy2 extends AnimatedSprite{
	private static final String IMAGE_PATH = "assets/Enemy2.png";
	private static final int STEP = 1;
	private boolean isDead = false;
	public static final byte DIE_FRAME = 31;
	
	public Enemy2() {
		super(Constant.BLOCK_SIZE, Constant.BLOCK_SIZE);
		try {
			spriteImage = new Image(Files.newInputStream(Paths.get(IMAGE_PATH)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		spriteXCoordinates[LEFT] = new int[] {0, 48, 96};
		spriteYCoordinates[LEFT] = new int[] {0, 0, 0};
		spriteXCoordinates[RIGHT] = new int[] {0, 48, 96};
		spriteYCoordinates[RIGHT] = new int[] {48, 48, 48};
		spriteXCoordinates[DOWN] = new int[] {0, 48, 96};
		spriteYCoordinates[DOWN] = new int[] {0, 0, 0};
		spriteXCoordinates[UP] = new int[] {0, 48, 96}; 
		spriteYCoordinates[UP] = new int[] {48, 48, 48};
		spriteXCoordinates[DIE] = new int[] {0, 48, 96, 0, 48, 96, 0, 48, 96, 0, 48, 96, 0, 48, 96, 0, 48, 96};
		spriteYCoordinates[DIE] = new int[] {96, 96, 96, 144, 144, 144, 192, 192, 192, 240, 240, 240, 288, 288, 288, 336, 336, 336};
		
		updateSpriteCoordinates();
	}
	
	public void move(int movement) {
		if(getDead())
			return;
		int newX = x;
		int newY = y;
		int oldX = x;
		int oldY = y;
		
		if (movement == LEFT && newX - STEP >= 40)
			newX -= STEP;
		else if (movement == RIGHT && newX + STEP <= 1008 - 44*2)
			newX += STEP;
		else if (movement == UP && newY - STEP >= 48*3)
			newY -= STEP;
		else if (movement == DOWN && newY + STEP <= 720 - 48*2)
			newY += STEP;
		
		if (movement == LEFT && newX - STEP < 40)
			movement = randomMovement(movement);
		else if (movement == RIGHT && newX + STEP > 1008 - 44*2)
			movement = randomMovement(movement);
		else if (movement == UP && newY - STEP < 48*3)
			movement = randomMovement(movement);
		else if (movement == DOWN && newY + STEP > 720 - 48*2)
			movement = randomMovement(movement);
		
		int sz = SoloGameScene.wallCoordinates.size();
		for(int i=0;i<sz;i++) {
			int wallX = SoloGameScene.wallCoordinates.get(i).getKey();
			int wallY = SoloGameScene.wallCoordinates.get(i).getValue();
			
			if (newX > wallX-40 && newX <= wallX+35 && newY > wallY-47 && newY <= wallY+35) {
				movement = randomMovement(movement);
				break;
			}
		}
		
		sz = SoloGameScene.wallBrickCoordinates.size();
		for(int i=0;i<sz;i++) {
			int wallX = SoloGameScene.wallBrickCoordinates.get(i).getKey();
			int wallY = SoloGameScene.wallBrickCoordinates.get(i).getValue();
			
			if (newX > wallX-40 && newX <= wallX+35 && newY > wallY-47 && newY <= wallY+35) {
				movement = randomMovement(movement);
				break;
			}
		}
		
		sz = SoloGameScene.BombCoordinates.size();
		for(int i=0;i<sz;i++) {
			int wallX = SoloGameScene.BombCoordinates.get(i).getKey();
			int wallY = SoloGameScene.BombCoordinates.get(i).getValue();
			
			if (newX > wallX-40 && newX <= wallX+35 && newY > wallY-47 && newY <= wallY+35) {
				movement = randomMovement(movement);
				break;
			}
		}
		
		if (movement == LEFT && newX - STEP >= 40)
			oldX -= STEP;
		else if (movement == RIGHT && newX + STEP <= 1008 - 44*2)
			oldX += STEP;
		else if (movement == UP && newY - STEP >= 48*3)
			oldY -= STEP;
		else if (movement == DOWN && newY + STEP <= 720 - 48*2)
			oldY += STEP;

		moveTo(oldX, oldY);
		animate(movement);
	}
	
	public int randomMovement(int movement) {
		int random;
		Random rand = new Random();
		while(true) {
			random = rand.nextInt(4);
			if(checkwall(random)) {
				break;
			}
		}
		return random;
	}
	
	public boolean checkwall(int movement) {
		if (movement == LEFT && x - STEP < 40)
			return false;
		else if (movement == RIGHT && x + STEP > 1008 - 44*2)
			return false;
		else if (movement == UP && y - STEP < 48*3)
			return false;
		else if (movement == DOWN && y + STEP > 720 - 48*2)
			return false;
		int newX = x;
		int newY = y;
		if (movement == LEFT && newX - STEP >= 40)
			newX -= STEP;
		else if (movement == RIGHT && newX + STEP <= 1008 - 44*2)
			newX += STEP;
		else if (movement == UP && newY - STEP >= 48*3)
			newY -= STEP;
		else if (movement == DOWN && newY + STEP <= 720 - 48*2)
			newY += STEP;
		
		int sz = SoloGameScene.wallCoordinates.size();
		for(int i=0;i<sz;i++) {
			int wallX = SoloGameScene.wallCoordinates.get(i).getKey();
			int wallY = SoloGameScene.wallCoordinates.get(i).getValue();
			
			if (newX > wallX-40 && newX <= wallX+35 && newY > wallY-47 && newY <= wallY+32) {
				return false;
			}
		}
		
		sz = SoloGameScene.wallBrickCoordinates.size();
		for(int i=0;i<sz;i++) {
			int wallX = SoloGameScene.wallBrickCoordinates.get(i).getKey();
			int wallY = SoloGameScene.wallBrickCoordinates.get(i).getValue();
			
			if (newX > wallX-40 && newX <= wallX+35 && newY > wallY-47 && newY <= wallY+32) {
				return false;
			}
		}
		
		return true;
	}
	
	public void die(int x,int y,GraphicsContext gc,long time) {
		setDead(true);
		new AnimationTimer() {
			public void handle(long currentNanoTime) {
				if(currentNanoTime - time <= 3e9 + 5e8) {
                    currentSpriteChange++;
                    if(currentSpriteChange >= DIE_FRAME) {
                        currentSpriteChange = 0;
                        currentSprite = (byte)((currentSprite + 1) % (spriteXCoordinates[DIE].length));
                    }
                    updateSpriteCoordinates(gc);
                }
			}
		}.start();
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
	
	public boolean checkEnemy(int xPlayer, int yPlayer, int xObj,int yObj) {
		if(xPlayer>=xObj-48+17 && xPlayer<=xObj+48-17 && yPlayer>=yObj-48+17 && yPlayer<=yObj+48-17)
			return true;
		return false;
	}
	
	public boolean checkCollision(int xPlayer, int yPlayer, int xBomb,int yBomb) {
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