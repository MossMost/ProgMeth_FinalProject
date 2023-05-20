package Item;

import java.nio.file.Files;
import java.nio.file.Paths;

import Constant.Constant;
import javafx.scene.image.Image;
import sprites.MainCharacter;

public class SpeedUp extends Item{

    private static final String IMAGE_PATH = "assets/Speed_Powerup.png";

    public SpeedUp() {
         super(Constant.BLOCK_SIZE, Constant.BLOCK_SIZE);
         try {
                spriteImage = new Image(Files.newInputStream(Paths.get(IMAGE_PATH)));
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @Override
    public void ItemEffect(MainCharacter Player) {
        Player.setStep(Player.getStep()+1);
    }



}