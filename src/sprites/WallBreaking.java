package sprites;

import java.nio.file.Files;
import java.nio.file.Paths;

import Constant.Constant;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class WallBreaking extends AnimatedSprite{

	private static final String IMAGE_PATH = "assets/Brick_break2.png";
    protected int[] spriteXCoordinates = new int[] {0,48,96,144};
    protected int[] spriteYCoordinates = new int[] {0,0,0,0};
    public static final byte WALL_CHANGE = 10;
    	

    protected byte currentSprite;
    protected byte currentSpriteChange;

    public WallBreaking() {
        super(Constant.BLOCK_SIZE, Constant.BLOCK_SIZE);
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
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                if(currentNanoTime - time <= 5e8) {
                    currentSpriteChange++;
                    if(currentSpriteChange >= WALL_CHANGE) {
                        currentSpriteChange = 0;
                        currentSprite = (byte)((currentSprite + 1) % (spriteXCoordinates.length));
                    }
                    updateSpriteCoordinates(gc);
                }
                
            }
        }.start();




    }
    protected void updateSpriteCoordinates(GraphicsContext gc) {

        spriteX = spriteXCoordinates[currentSprite];
        spriteY = spriteYCoordinates[currentSprite];
        draw(gc);
        
    }
	
}
