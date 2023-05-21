package enemy;

import java.util.Random;

import constant.Constant;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Pair;
import scenes.SoloGameScene;
import sprites.AnimatedSprite;

public abstract class Monster extends AnimatedSprite{

	private boolean isDead;
	
	public Monster() {
		super(Constant.BLOCK_SIZE, Constant.BLOCK_SIZE);
	}
	
	public abstract void move(int movement);
	
	public int randomMovement(int movement, int STEP) {
			int random;
			Random rand = new Random();
			while(true) {
				random = rand.nextInt(4);
				if(checkwall(random, STEP)) {
					break;
				}
			}
			return random;
	}
	
	public boolean checkwall(int movement, int STEP) {
		if (movement == LEFT && x - STEP < 40)
			return false;
		else if (movement == RIGHT && x + STEP > 1008 - 44*2)
			return false;
		else if (movement == UP && y - STEP < Constant.BLOCK_SIZE*3)
			return false;
		else if (movement == DOWN && y + STEP > 720 - Constant.BLOCK_SIZE*2)
			return false;
		
		int newX = x;
		int newY = y;
		if (movement == LEFT && newX - STEP >= 40)
			newX -= STEP;
		else if (movement == RIGHT && newX + STEP <= 1008 - 44*2)
			newX += STEP;
		else if (movement == UP && newY - STEP >= Constant.BLOCK_SIZE*3)
			newY -= STEP;
		else if (movement == DOWN && newY + STEP <= 720 - Constant.BLOCK_SIZE*2)
			newY += STEP;
		
		int sz = SoloGameScene.wallCoordinates.size();
		for(int i = 0; i < sz; i++) {
			int wallX = SoloGameScene.wallCoordinates.get(i).getKey();
			int wallY = SoloGameScene.wallCoordinates.get(i).getValue();
			
			if (newX > wallX - 40 && newX <= wallX + 35 && newY > wallY - 47 && newY <= wallY + 32) {
				return false; 
			}
		}
		
		sz = SoloGameScene.wallBrickCoordinates.size();
		for(int i = 0; i < sz; i++) {
			int wallX = SoloGameScene.wallBrickCoordinates.get(i).getKey();
			int wallY = SoloGameScene.wallBrickCoordinates.get(i).getValue();
			
			if (newX > wallX - 40 && newX <= wallX + 35 && newY > wallY - 47 && newY <= wallY + 32) {
				return false;
			}
		}
		return true;
	}
	
	public void die(int x, int y, GraphicsContext gc, long time) {
		setDead(true);
		new AnimationTimer() {
			public void handle(long currentNanoTime) {
				if(currentNanoTime - time <= 3e9 + 5e8) {
                    currentSpriteChange++;
                    if(currentSpriteChange >= Constant.ENEMY_DIE_FRAME) {
                        currentSpriteChange = 0;
                        currentSprite = (byte)((currentSprite + 1) % (spriteXCoordinates[DIE].length));
                    }
                    updateSpriteCoordinates(gc);
                }
			}
		}.start();
	}
	
	protected void updateSpriteCoordinates(GraphicsContext gc) {
		spriteX = spriteXCoordinates[DIE][currentSprite];
		spriteY = spriteYCoordinates[DIE][currentSprite];
        draw(gc);
    }
	
	/*public boolean checkEnemy(int xPlayer, int yPlayer, int xObj,int yObj) {
		if(xPlayer>=xObj-Constant.BLOCK_SIZE+17 && xPlayer<=xObj+Constant.BLOCK_SIZE-17 && yPlayer>=yObj-Constant.BLOCK_SIZE+17 && yPlayer<=yObj+Constant.BLOCK_SIZE-17)
			return true;
		return false;
	}*/
	
	public boolean checkCollision(int xPlayer, int yPlayer, int xObj, int yObj) {
		if(yPlayer >= yObj - Constant.BLOCK_SIZE * 2 + 10 && yPlayer <= yObj + Constant.BLOCK_SIZE * 2 - 10 
		&& xPlayer >= xObj - Constant.BLOCK_SIZE + 20 && xPlayer <= xObj + Constant.BLOCK_SIZE)
			return true;
		
		if(xPlayer >= xObj - Constant.BLOCK_SIZE * 2 + 10 && xPlayer <= xObj + Constant.BLOCK_SIZE * 2 
		&& yPlayer >= yObj - Constant.BLOCK_SIZE + 20 && yPlayer <= yObj + Constant.BLOCK_SIZE)
			return true;
		
		return false;
	}
	
	public boolean checkBomb(int x, int y, int range) {
		if(checkCollision(this.getX(), this.getY(), x, y)) 
			return true;
		
		for(int i = 1; i <= range; i++) {
			if(SoloGameScene.wallBrickCoordinates.contains(new Pair<>(x, y - Constant.BLOCK_SIZE * i)) 
			|| SoloGameScene.wallCoordinates.contains(new Pair<>(x, y - Constant.BLOCK_SIZE * i))) {
				break;
			}
			if(checkCollision(this.getX(), this.getY(), x, y - Constant.BLOCK_SIZE * i)) {
				return true;
			}
		}
		
		for(int i = 1; i <= range; i++) {
			if(SoloGameScene.wallBrickCoordinates.contains(new Pair<>(x, y + Constant.BLOCK_SIZE * i)) 
			|| SoloGameScene.wallCoordinates.contains(new Pair<>(x, y + Constant.BLOCK_SIZE * i))) {
				break;
			}
			if(checkCollision(this.getX(), this.getY(), x, y+Constant.BLOCK_SIZE*i)) {
				return true;
			}
		}
		
		for(int i = 1; i <= range; i++) {
			if(SoloGameScene.wallBrickCoordinates.contains(new Pair<>(x - Constant.BLOCK_SIZE * i, y)) 
			|| SoloGameScene.wallCoordinates.contains(new Pair<>(x - Constant.BLOCK_SIZE * i, y))) {
				break;
			}
			if(checkCollision(this.getX(), this.getY(), x - Constant.BLOCK_SIZE * i, y)) {
				return true;
			}
		}
		
		for(int i=1;i<=range;i++) {
			if(SoloGameScene.wallBrickCoordinates.contains(new Pair<>(x + Constant.BLOCK_SIZE * i,y)) 
			|| SoloGameScene.wallCoordinates.contains(new Pair<>(x + Constant.BLOCK_SIZE * i,y))) {
				break;
			}
			if(checkCollision(this.getX(), this.getY(), x + Constant.BLOCK_SIZE * i, y)) {
				return true;
			}
		}
		return false;		
	}
	
	//getter&&setter
	public boolean getDead() {
		return isDead;
	}
	
	public void setDead(boolean dead) {
		isDead = dead;
	}
	
}
