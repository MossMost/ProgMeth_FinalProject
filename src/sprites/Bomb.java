package sprites;

import java.nio.file.Files;
import java.nio.file.Paths;

import Constant.Constant;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Pair;
import scenes.SoloGameScene;

public class Bomb extends AnimatedSprite{
    private static final String IMAGE_PATH = "assets/Bomb.png";
    public static final byte SPRITE_CHANGE = 25;
    public static final byte BOMB_CHANGE = 15;

    protected int[] spriteXCoordinates = new int[] {0,48,96};
    protected int[] spriteYCoordinates = new int[] {0,0,0};

    protected byte currentSprite;
    protected byte currentSpriteChange;
    protected boolean IsInBomb;
    private boolean ch;

    public Bomb() {
        super(Constant.BLOCK_SIZE, Constant.BLOCK_SIZE);
        setIsInBomb(true);
        currentSprite = 0;
        currentSpriteChange = 0;
        try {
            spriteImage = new Image(Files.newInputStream(Paths.get(IMAGE_PATH)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void animate(int x, int y, GraphicsContext gc, long time) {
    	
        this.x = x;
        this.y = y;
        ch = true;
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                if(currentNanoTime - time < 3e9) {
                    currentSpriteChange++;
                    if(currentSpriteChange >= SPRITE_CHANGE) {
                        currentSpriteChange = 0;
                        currentSprite = (byte)((currentSprite + 1) % (spriteXCoordinates.length));
                    }
                    updateSpriteCoordinates(gc);
                }
                else if(currentNanoTime - time > 3e9 && currentNanoTime - time < 3e9 + 1e8) {
                	
                	int idx;
                	if(!SoloGameScene.BombCoordinates.isEmpty() && ch) {
                		System.out.println(SoloGameScene.BombCoordinates);
                		idx = SoloGameScene.BombCoordinates.indexOf(new Pair<>(x,y));
                		SoloGameScene.BombCoordinates.remove(idx);
                		ch=false;
                	}
                	if(!SoloGameScene.BombArr.isEmpty()) {
                		SoloGameScene.BombArr.remove(0);
                		
                	}
                	Fire middle = new Fire("assets/Central_flame.png");
            		middle.animate(x, y, gc, currentNanoTime);
                	if(SoloGameScene.wallCoordinates.contains(new Pair<>(x,y-48)) == false) {
                		Fire top = new Fire("assets/Top_Up_flame.png");
                		top.animate(x, y-48, gc, currentNanoTime);
                		if(SoloGameScene.wallBrickCoordinates.contains(new Pair<>(x,y-48))) {
                			WallBreaking tmp = new WallBreaking();
                			tmp.animate(x,y-48,gc,currentNanoTime);
                			idx = SoloGameScene.wallBrickCoordinates.indexOf(new Pair<>(x,y-48));
                			SoloGameScene.wallBrickCoordinates.remove(idx);
                			SoloGameScene.disableWall.add(new Pair<>(x,y-48));
                		}
                	}
                	if(SoloGameScene.wallCoordinates.contains(new Pair<>(x,y+48)) == false) {
                		Fire bottom = new Fire("assets/Top_Down_flame.png");
                		bottom.animate(x, y+48, gc, currentNanoTime);
                		if(SoloGameScene.wallBrickCoordinates.contains(new Pair<>(x,y+48))) {
                			WallBreaking tmp = new WallBreaking();
                			tmp.animate(x,y+48,gc,currentNanoTime);
                			idx = SoloGameScene.wallBrickCoordinates.indexOf(new Pair<>(x,y+48));
                			SoloGameScene.wallBrickCoordinates.remove(idx);
                			SoloGameScene.disableWall.add(new Pair<>(x,y+48));
 
                		}
                	}
                	if(SoloGameScene.wallCoordinates.contains(new Pair<>(x-48,y)) == false) {
                		Fire left = new Fire("assets/Top_Left_flame.png");
                		left.animate(x-48, y, gc, currentNanoTime);
                		if(SoloGameScene.wallBrickCoordinates.contains(new Pair<>(x-48,y))) {
                			WallBreaking tmp = new WallBreaking();
                			tmp.animate(x-48,y,gc,currentNanoTime);
                			idx = SoloGameScene.wallBrickCoordinates.indexOf(new Pair<>(x-48,y));
                			SoloGameScene.wallBrickCoordinates.remove(idx);
                			SoloGameScene.disableWall.add(new Pair<>(x-48,y));
 
                		}
                	}
                	if(SoloGameScene.wallCoordinates.contains(new Pair<>(x+48,y)) == false) {
                		Fire right = new Fire("assets/Top_Right_flame.png");
                		right.animate(x+48, y, gc, currentNanoTime);
                		if(SoloGameScene.wallBrickCoordinates.contains(new Pair<>(x+48,y))) {
                			WallBreaking tmp = new WallBreaking();
                			tmp.animate(x+48,y,gc,currentNanoTime);
                			idx = SoloGameScene.wallBrickCoordinates.indexOf(new Pair<>(x+48,y));
                			SoloGameScene.wallBrickCoordinates.remove(idx);
                			SoloGameScene.disableWall.add(new Pair<>(x+48,y));
 
                		}
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
    
    public void checkIsInBomb() {
    	
    }
    
    public void setIsInBomb(boolean IsInBomb) {
		this.IsInBomb = IsInBomb;
	}
	
	public boolean getIsInBomb() {
		return IsInBomb;
	}


}