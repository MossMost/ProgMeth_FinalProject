package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import scenes.CreditsScene;
import scenes.GeneralScene;
import scenes.SoloGameScene;
import scenes.WelcomeScene;


public class Main extends Application {
	
	public static final int MAX_SCENES = 3;
	public static final int WELCOME_SCENE = 0;
	public static final int SOLO_GAME_SCENE = 1;
	public static final int CREDITS_SCENE = 2;
	
	public static final GeneralScene[] scenes = new GeneralScene[MAX_SCENES];
	
	private static Stage stage;
	
	@Override
	public void start(Stage stage) {
		Main.stage = stage;
		
		scenes[0] = new WelcomeScene();
		scenes[1] = new SoloGameScene();
		scenes[2] = new CreditsScene();
		
		stage.setTitle("Bomber Boy");
		setScene(WELCOME_SCENE);
		stage.setResizable(false);
		stage.show();
	}
	
	public static void setScene(int numScene) {
		stage.setScene(scenes[numScene]);
		scenes[numScene].draw();
	}
	
	public static void exit() {
		stage.hide();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
