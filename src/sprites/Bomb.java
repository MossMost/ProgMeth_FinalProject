package sprites;

import java.nio.file.Files;
import java.nio.file.Paths;

import constant.Constant;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Pair;
import scenes.SoloGameScene;

public class Bomb extends AnimatedSprite{
    private static final String IMAGE_PATH = "assets/Bomb.png";

    protected int[] spriteXCoordinates = new int[] {0, Constant.BLOCK_SIZE, 96};
    protected int[] spriteYCoordinates = new int[] {0, 0, 0};

    protected byte currentSprite;
    protected byte currentSpriteChange;
    private boolean hasFlame;

    public Bomb() {
        super(Constant.BLOCK_SIZE, Constant.BLOCK_SIZE);
        setFlame(false);
        currentSprite = 0;
        currentSpriteChange = 0;
        try {
            spriteImage = new Image(Files.newInputStream(Paths.get(IMAGE_PATH)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void animate(int x, int y, GraphicsContext gc, long time, int range) {
    	
        this.x = x;
        this.y = y;
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
            	//bomb
                if(currentNanoTime - time < 3 * Constant.SEC) {
                    currentSpriteChange++;
                    if(currentSpriteChange >= Constant.SPRITE_CHANGE) {
                        currentSpriteChange = 0;
                        currentSprite = (byte)((currentSprite + 1) % (spriteXCoordinates.length));
                    }
                    updateSpriteCoordinates(gc);
                }
                //flame
                if(currentNanoTime - time > 3 * Constant.SEC && currentNanoTime - time < 3 * Constant.SEC + 2e7 + 4e6) {
                	if(!getFlame()) {
                		SoloGameScene.playEffect(Constant.EXPLOSION_EFFECT);
                		setFlame(true);
                	}
                	Fire middle = new Fire("assets/Central_flame.png");
            		middle.animate(x, y, gc, currentNanoTime);
            		
            		//checkeverydirection
                	if(SoloGameScene.wallCoordinates.contains(new Pair<>(x, y - Constant.BLOCK_SIZE)) == false) {
                		int i;
                		for(i = 1; i <= range - 1; i++) {
                			if(SoloGameScene.wallBrickCoordinates.contains(new Pair<>(x, y - Constant.BLOCK_SIZE * i)) 
                			|| SoloGameScene.wallCoordinates.contains(new Pair<>(x, y - Constant.BLOCK_SIZE * (i + 1)))) {
                				break;
                			}
                			Fire tmp = new Fire("assets/Up_flame.png");
                			tmp.animate(x, y - Constant.BLOCK_SIZE * i, gc, currentNanoTime);
                    		checkWallBrick(x, y - Constant.BLOCK_SIZE * i, gc, currentNanoTime);
                		}
                		Fire top = new Fire("assets/Top_Up_flame.png");
                		top.animate(x, y - Constant.BLOCK_SIZE * i, gc, currentNanoTime);
                		checkWallBrick(x, y - Constant.BLOCK_SIZE * i, gc, currentNanoTime);
                	}
                	if(SoloGameScene.wallCoordinates.contains(new Pair<>(x, y + Constant.BLOCK_SIZE)) == false) {
                		int i;
                		for(i = 1; i <= range - 1; i++) {
                			if(SoloGameScene.wallBrickCoordinates.contains(new Pair<>(x, y + Constant.BLOCK_SIZE * i)) 
                			|| SoloGameScene.wallCoordinates.contains(new Pair<>(x, y + Constant.BLOCK_SIZE * (i + 1)))) {
                				break;
                			}
                			Fire tmp = new Fire("assets/Up_flame.png");
                			tmp.animate(x, y + Constant.BLOCK_SIZE * i, gc, currentNanoTime);
                    		checkWallBrick(x, y + Constant.BLOCK_SIZE * i, gc, currentNanoTime);
                		}
                		Fire bottom = new Fire("assets/Top_Down_flame.png");
                		bottom.animate(x, y + Constant.BLOCK_SIZE * i, gc, currentNanoTime);
                		checkWallBrick(x, y + Constant.BLOCK_SIZE * i, gc, currentNanoTime);
                	}
                	if(SoloGameScene.wallCoordinates.contains(new Pair<>(x-Constant.BLOCK_SIZE,y)) == false) {
                		int i;
                		for(i = 1; i <= range - 1; i++) {
                			if(SoloGameScene.wallBrickCoordinates.contains(new Pair<>(x - Constant.BLOCK_SIZE * i, y)) 
                			|| SoloGameScene.wallCoordinates.contains(new Pair<>(x - Constant.BLOCK_SIZE * (i + 1),y))) {
                				break;
                			}
                			Fire tmp = new Fire("assets/Right_flame.png");
                			tmp.animate(x - Constant.BLOCK_SIZE * i, y, gc, currentNanoTime);
                    		checkWallBrick(x - Constant.BLOCK_SIZE * i, y, gc, currentNanoTime);
                		}
                		Fire left = new Fire("assets/Top_Left_flame.png");
                		left.animate(x - Constant.BLOCK_SIZE * i, y, gc, currentNanoTime);
                		checkWallBrick(x - Constant.BLOCK_SIZE * i, y, gc, currentNanoTime);
                	}
                	if(SoloGameScene.wallCoordinates.contains(new Pair<>(x + Constant.BLOCK_SIZE, y)) == false) {
                		int i;
                		for(i = 1; i <= range - 1; i++) {
                			if(SoloGameScene.wallBrickCoordinates.contains(new Pair<>(x + Constant.BLOCK_SIZE * i, y)) 
                			|| SoloGameScene.wallCoordinates.contains(new Pair<>(x + Constant.BLOCK_SIZE * (i + 1), y))) {
                				break;
                			}
                			Fire tmp = new Fire("assets/Right_flame.png");
                			tmp.animate(x + Constant.BLOCK_SIZE * i, y, gc, currentNanoTime);
                    		checkWallBrick(x + Constant.BLOCK_SIZE * i, y, gc, currentNanoTime);
                		}
                		Fire right = new Fire("assets/Top_Right_flame.png");
                		right.animate(x + Constant.BLOCK_SIZE * i, y, gc, currentNanoTime);
                		checkWallBrick(x + Constant.BLOCK_SIZE * i, y, gc, currentNanoTime);
                	}
                }
            }
        }.start();
    }
    
    protected void updateSpriteCoordinates(GraphicsContext gc) {
        spriteX = spriteXCoordinates[currentSprite];
        spriteY = spriteYCoordinates[currentSprite];
        draw(gc);
    }
    
   
    
    private void checkWallBrick(int x, int y, GraphicsContext gc, long currentNanoTime) {
    	int idx;
    	if(SoloGameScene.wallBrickCoordinates.contains(new Pair<>(x,y))) {
			WallBreaking tmp = new WallBreaking();
			tmp.animate(x,y,gc,currentNanoTime);
			idx = SoloGameScene.wallBrickCoordinates.indexOf(new Pair<>(x,y));
			SoloGameScene.wallBrickCoordinates.remove(idx);
			SoloGameScene.disableWall.add(new Pair<>(x,y));
		}
    }
    
    
    
    //getter&setter
    public void setFlame(boolean flame) {
    	hasFlame = flame;
    }
    
    public boolean getFlame() {
    	return hasFlame;
    }

}