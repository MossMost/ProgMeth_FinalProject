package application;

import javax.print.DocFlavor.URL;

import constant.Constant;
import javafx.application.Application;
import javafx.stage.Stage;
import scenes.GameOverScene;
import scenes.GeneralScene;
import scenes.NextStage;
import scenes.SoloGameScene;
import scenes.VictoryScene;
import scenes.WelcomeScene;


public class Main extends Application {
	
	public static final GeneralScene[] scenes = new GeneralScene[Constant.MAX_SCENES];
	private static Stage stage;
	
	
	@Override
	public void start(Stage stage) {
		Main.stage = stage;
		
		scenes[0] = new WelcomeScene();
		scenes[1] = new SoloGameScene();
		scenes[2] = new GameOverScene();
		scenes[3] = new NextStage();
		scenes[4] = new VictoryScene();
		
		
		stage.setTitle("Trinity Boy");
		setScene(Constant.WELCOME_SCENE);
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
