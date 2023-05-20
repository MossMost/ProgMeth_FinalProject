package sprites;

import java.nio.file.Files;
import java.nio.file.Paths;

import Constant.Constant;
import javafx.scene.image.Image;

public class MonsterCry extends Sprite{
    private static final String IMAGE_PATH = "assets/MonsterCry.png";
    public static final byte SPRITE_CHANGE = 20;

    protected int[] spriteXCoordinates  = {0,144,288,0,144,288,0,144,288};
    protected int[] spriteYCoordinates  = {0,0,0,144,144,144,288,288,288};

    protected byte currentSprite;
    protected byte currentSpriteChange;
    public MonsterCry() {
        super(Constant.BLOCK_SIZE*3, Constant.BLOCK_SIZE*3);
        currentSprite = 0;
        currentSpriteChange = 0;
        try {
            spriteImage = new Image(Files.newInputStream(Paths.get(IMAGE_PATH)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

public void animate() {
        currentSpriteChange++;
            if(currentSpriteChange >= SPRITE_CHANGE) {
                currentSpriteChange = 0;
                currentSprite = (byte)((currentSprite + 1) % spriteXCoordinates.length);
            }
        updateSpriteCoordinates();
    }

    protected void updateSpriteCoordinates() {
        spriteX = spriteXCoordinates[currentSprite];
        spriteY = spriteYCoordinates[currentSprite];
    }

}