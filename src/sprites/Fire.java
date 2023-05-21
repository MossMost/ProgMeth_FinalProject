package sprites;

import java.nio.file.Files;
import java.nio.file.Paths;

import constant.Constant;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Fire extends AnimatedSprite{

    protected int[] spriteXCoordinates = new int[] {0, 48, 96};
    protected int[] spriteYCoordinates = new int[] {0, 0, 0};
    	

    protected byte currentSprite;
    protected byte currentSpriteChange;

    public Fire(String IMAGE_PATH) {
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
                    if(currentSpriteChange >= Constant.BOMB_FRAME) {
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
