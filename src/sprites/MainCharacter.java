package sprites;

import java.nio.file.Files;
import java.nio.file.Paths;

import constant.Constant;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Pair;
import scenes.SoloGameScene;

public class MainCharacter extends AnimatedSprite{
	
	private static final String IMAGE_PATH = "assets/Player01.png";
	private double Step;
	private int life;
	private int fireRange;
	private int amountBomb;
	private boolean isDead;
	private boolean isInstantDead;

	public MainCharacter() {
		super(Constant.BLOCK_SIZE,Constant.BLOCK_SIZE);
		try {
			spriteImage = new Image(Files.newInputStream(Paths.get(IMAGE_PATH)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		setStep(2.0);
		setLife(3);
		setFireRange(1);
		setAmountBomb(1);
		setDead(false);
		setInstantDead(false);
		
		spriteXCoordinates[Constant.RIGHT] = new int[] {0, 48, 96, 144};
		spriteYCoordinates[Constant.RIGHT] = new int[] {0, 0, 0, 0};
		spriteXCoordinates[Constant.LEFT] = new int[] {0, 48, 96, 144};
		spriteYCoordinates[Constant.LEFT] = new int[] {48, 48, 48, 48};
		spriteXCoordinates[Constant.UP] = new int[] {0, 48, 96, 144};
		spriteYCoordinates[Constant.UP] = new int[] {96, 96, 96, 96};
		spriteXCoordinates[Constant.DOWN] = new int[] {0, 48, 96, 144};
		spriteYCoordinates[Constant.DOWN] = new int[] {144, 144, 144, 144};
		spriteXCoordinates[Constant.DIE] = new int[] {0, 48, 96, 144};
		spriteYCoordinates[Constant.DIE] = new int[] {192, 192, 192, 192};
		
		updateSpriteCoordinates();
	}
	
	public void move(int movement) {
		if(getInstantDead())
			return;
		
		int newX = x, oldX = x;
		int newY = y, oldY = y;
		if (movement == Constant.LEFT && newX - Step >= 40)
			newX -= Step;
		else if(movement == Constant.LEFT && newX - Step < 40)
			newX = 40;
		if (movement == Constant.RIGHT && newX + Step <= 1008 - 44*2)
			newX += Step;
		else if(movement == Constant.RIGHT && newX + Step > 1008 - 44*2)
			newX = 1008 - 44*2;
		if (movement == Constant.UP && newY - Step >= 48*3)
			newY -= Step;
		else if(movement == Constant.UP && newY - Step < 48*3)
			newY = 48*3;
		if (movement == Constant.DOWN && newY + Step <= 720 - 48*2)
			newY += Step;
		else if(movement == Constant.DOWN && newY + Step > 720 - 48*2)
			newY = 720 - 48*2;
		
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
		
		for(int i=0;i<SoloGameScene.BombCoordinates.size();i++) {
			int wallX = SoloGameScene.BombCoordinates.get(i).getKey();
			int wallY = SoloGameScene.BombCoordinates.get(i).getValue();
			if(newX > wallX - 45 && newX < wallX + 49 && newY  < wallY + 49 && newY  > wallY-47) {
				continue;
			}
			
			if (newX > wallX-40 && newX <= wallX+35 && newY > wallY-47 && newY <= wallY+32) {
				newX = oldX;
				newY = oldY;
				
			}
		}
		
		
		moveTo(newX, newY);
		animate(movement);
	}

	public void die(int x,int y,GraphicsContext gc,long time) {
		if(!getInstantDead()) {
			SoloGameScene.playEffect(Constant.DIE_EFFECT);
			setInstantDead(true);
		}
		new AnimationTimer() {
			public void handle(long currentNanoTime) {
				if(currentNanoTime - time <= Constant.SEC) {
                    currentSpriteChange++;
                    if(currentSpriteChange >= Constant.PLAYER_DIE_FRAME) {
                        currentSpriteChange = 0;
                        currentSprite = (byte)((currentSprite + 1) % (spriteXCoordinates[Constant.DIE].length));
                    }
                    updateSpriteCoordinates(gc);
                }
				if(currentNanoTime - time > Constant.SEC && currentNanoTime - time <= Constant.SEC + 0.2 * Constant.SEC) {
					setDead(true);
				}
			}
		}.start();
	}
	
	public boolean checkBomb(int x,int y, int range) {
		if(checkCollision(this.getX(), this.getY(), x, y)) 
			return true;
		//1 = minus, 2 = plus
		return (checkCollisionBrick(range,x,y,0,1)||checkCollisionBrick(range,x,y,0,2)
			  ||checkCollisionBrick(range,x,y,1,0)||checkCollisionBrick(range,x,y,2,0));
	}
	
	private boolean checkCollisionBrick(int range, int x, int y, int oprX, int oprY) {
		int newX, newY;
		for(int i = 1; i <= range; i++) {
			newX = x + Constant.BLOCK_SIZE * i * (int)(Math.pow(-1, oprX));
			newY = y + Constant.BLOCK_SIZE * i * (int)(Math.pow(-1, oprY));
			if(oprX == 0) newX = x;
			if(oprY == 0) newY = y;
			
			if(SoloGameScene.wallBrickCoordinates.contains(new Pair<>(newX, newY)) 
			|| SoloGameScene.wallCoordinates.contains(new Pair<>(newX, newY))) {
				return false;
			}
			if(checkCollision(this.getX(), this.getY(), newX, newY)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkEnemy(int xPlayer, int yPlayer, int xObj,int yObj) {
		if(xPlayer >= xObj - Constant.BLOCK_SIZE + 17 && xPlayer <= xObj + Constant.BLOCK_SIZE - 17 
		&& yPlayer >= yObj - Constant.BLOCK_SIZE + 17 && yPlayer <= yObj + Constant.BLOCK_SIZE - 17)
			return true;
		return false;
	}
	
	public boolean checkCollision(int xPlayer, int yPlayer, int xObj,int yObj) {
		if(xPlayer >= xObj - Constant.BLOCK_SIZE + 15 && xPlayer <= xObj + Constant.BLOCK_SIZE - 15 
		&& yPlayer >= yObj - Constant.BLOCK_SIZE + 7  && yPlayer <= yObj + Constant.BLOCK_SIZE - 15)
			return true;
		return false;
	}
	
	protected void updateSpriteCoordinates(GraphicsContext gc) {
		spriteX = spriteXCoordinates[Constant.DIE][currentSprite];
		spriteY = spriteYCoordinates[Constant.DIE][currentSprite];
        draw(gc);
    }
	
	//getter&&setter
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
	public boolean getInstantDead() {
		return isInstantDead;
	}
	
	public void setInstantDead(boolean dead) {
		this.isInstantDead = dead;
	}
	
	public int getLife() {
		return life;
	}
	
	public void setLife(int num) {
		life = num;
	}
	
	public double getStep() {
		return Step;
	}

	public void setStep(double step) {
		Step = step;
	}
}