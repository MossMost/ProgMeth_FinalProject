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
	private double Step = 2.0;
	private int life = 3;
	private int fireRange = 1;
	private int amountBomb = 1;
	private boolean isDead = false;
	private boolean canwalk = true;
	private boolean IsturntoDust = false;
	public boolean ch = true;
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
		if (movement == LEFT && newX - Step >= 40)
			newX -= Step;
		else if(movement == LEFT && newX - Step < 40)
			newX = 40;
		if (movement == RIGHT && newX + Step <= 1008 - 44*2)
			newX += Step;
		else if(movement == RIGHT && newX + Step > 1008 - 44*2)
			newX = 1008 - 44*2;
		if (movement == UP && newY - Step >= 48*3)
			newY -= Step;
		else if(movement == UP && newY - Step < 48*3)
			newY = 48*3;
		if (movement == DOWN && newY + Step <= 720 - 48*2)
			newY += Step;
		else if(movement == DOWN && newY + Step > 720 - 48*2)
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
		//checkbomb
		for(int i=0;i<SoloGameScene.BombCoordinates.size();i++) {
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
		if(IsturntoDust == false) {
			SoloGameScene.playEffect(SoloGameScene.DIE_EFFECT);
			IsturntoDust = true;
		}
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
				if(currentNanoTime - time > 1e9 && currentNanoTime - time <= 1e9 + 2e8) {
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
				//System.out.println("true");
				break;
			}
			if(checkCollision(this.getX(),this.getY(),x,y-48*i)) {
				
				return true;
			}
		}
		for(int i=1;i<=range;i++) {
			if(SoloGameScene.wallBrickCoordinates.contains(new Pair<>(x,y+48*i)) || SoloGameScene.wallCoordinates.contains(new Pair<>(x,y+48*i))) {
				//System.out.println("true");
				break;
			}
			if(checkCollision(this.getX(),this.getY(),x,y+48*i)) {
				
				return true;
			}
		}
		for(int i=1;i<=range;i++) {
			if(SoloGameScene.wallBrickCoordinates.contains(new Pair<>(x-48*i,y)) || SoloGameScene.wallCoordinates.contains(new Pair<>(x-48*i,y))) {
				//System.out.println("true");
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
