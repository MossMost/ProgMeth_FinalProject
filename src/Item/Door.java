package Item;

import java.nio.file.Files;
import java.nio.file.Paths;

import Constant.Constant;
import application.Main;
import javafx.scene.image.Image;
import sprites.MainCharacter;

public class Door extends Item{

	private static final String IMAGE_PATH = "assets/Door.png";
	
	    public Door() {
	         super(Constant.BLOCK_SIZE, Constant.BLOCK_SIZE);
	         try {
	                spriteImage = new Image(Files.newInputStream(Paths.get(IMAGE_PATH)));
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	    }

		@Override
		public void ItemEffect(MainCharacter Player) {
			Main.setScene(Main.CREDITS_SCENE);
		}

}