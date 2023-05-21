package item;

import java.nio.file.Files;
import java.nio.file.Paths;

import application.Main;
import constant.Constant;
import javafx.scene.image.Image;
import scenes.SoloGameScene;
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
			if(SoloGameScene.stage == Main.VICTORY_SCENE)
				Main.setScene(Main.VICTORY_SCENE);
			else
				Main.setScene(Main.NEXTSTAGE_SCENE);
			Player.setLife(Constant.PLAYER_LIFE);
		}

}