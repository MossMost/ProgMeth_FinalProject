package Item;

import java.nio.file.Files;
import java.nio.file.Paths;

import Constant.Constant;
import javafx.scene.image.Image;
import sprites.MainCharacter;

public class RangeUp extends Item{

    private static final String IMAGE_PATH = "assets/Flame_Powerup.png";

    public RangeUp() {
         super(Constant.BLOCK_SIZE, Constant.BLOCK_SIZE);
         try {
                spriteImage = new Image(Files.newInputStream(Paths.get(IMAGE_PATH)));
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @Override
    public void ItemEffect(MainCharacter Player) {
    	Player.setFireRange(Player.getFireRange()+1);
    }

}