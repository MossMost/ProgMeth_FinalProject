package scenes;

import java.util.HashSet;
import java.util.Set;

import Constant.Constant;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

abstract public class GeneralScene extends Scene{
	public static final int GAME_WIDTH = 1008;
	public static final int GAME_HEIGHT = 720;

	
	private StackPane root = new StackPane();
	protected GraphicsContext gc;
	protected Set<KeyCode> activeKeys;
	protected Set<KeyCode> releasedKeys;
	
	protected MediaPlayer mediaPlayer;
	protected Media sound;
	
	public GeneralScene() {
		
		super(new StackPane(), Constant.SCENE_WIDTH, Constant.SCENE_HEIGHT);
		
		root = new StackPane();
		this.setRoot(root);
		
		Canvas canvas = new Canvas(Constant.SCENE_WIDTH, Constant.SCENE_HEIGHT);
		root.getChildren().add(canvas);
		gc = canvas.getGraphicsContext2D();
		
		activeKeys = new HashSet<>();
		releasedKeys = new HashSet<>();
		this.setOnKeyPressed(e -> {
			activeKeys.add(e.getCode());
		});
		this.setOnKeyReleased(e->{
			activeKeys.remove(e.getCode());
			releasedKeys.add(e.getCode());
		});
	}
	
	public abstract void draw();
}
