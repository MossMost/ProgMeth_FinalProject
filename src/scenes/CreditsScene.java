package scenes;

import application.Main;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CreditsScene extends GeneralScene{
	
	public CreditsScene()
	{
		super();
	}
	
	private void showCreditsMessage() {
		Font myFont = Font.font("Arial", FontWeight.NORMAL, 32);
		gc.setFont(myFont);
		gc.setFill(Color.GREEN);
		gc.fillText("Game Over", 325, 200);
		
		myFont = Font.font("Arial", FontWeight.NORMAL, 20);
		gc.setFont(myFont);
		gc.setFill(Color.WHITE);
		gc.fillText("Press Spacebar to go back to welcome scene", 200, 275);
	}

	@Override
	public void draw() {
		activeKeys.clear();
		
		new AnimationTimer() {
			 public void handle(long currentNanoTime){
				 	gc.setFill(Color.BLACK);
				 	gc.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

				 	showCreditsMessage();
					
					if(activeKeys.contains(KeyCode.SPACE)){
						this.stop();
						Main.setScene(Main.WELCOME_SCENE);
					}	
			}
		}.start();
	}
}
