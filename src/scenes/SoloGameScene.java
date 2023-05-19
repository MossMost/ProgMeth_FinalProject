package scenes;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;

import Constant.Constant;
import application.Main;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import sprites.Bomb;
import sprites.Enemy1;
import sprites.Enemy5;
import sprites.MainCharacter;
import sprites.Sprite;
import sprites.WallBreaking;


public class SoloGameScene extends GeneralScene{
	private static final String BACKGROUND_IMAGE = "assets/background.png";
	private static final String BRICK_IMAGE = "assets/Brick.png";

	public static int posXBrick[] = new int[110];
	public static int posYBrick[] = new int[110];
	
	public static Sprite wall[];
	public static ArrayList<Pair<Integer, Integer>> disableWall = new ArrayList<Pair<Integer, Integer>>();
	public static ArrayList<Pair<Integer, Integer>> wallCoordinates = new ArrayList<Pair<Integer, Integer>>();
	public static ArrayList<Pair<Integer, Integer>> wallBrickCoordinates = new ArrayList<Pair<Integer, Integer>>();
	
	private Image background,brick;
	private MainCharacter Player;
	private long lastSpace;
	private Enemy1 enemy1;
	private Enemy5 enemy5;
	
	public SoloGameScene() {
		super();
		addWall();
		wall = new Sprite[75];
		try {
			background = new Image(Files.newInputStream(Paths.get(BACKGROUND_IMAGE)));
			brick = new Image(Files.newInputStream(Paths.get(BRICK_IMAGE)));
			Player = new MainCharacter();
			enemy1 = new Enemy1();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void draw() {
		activeKeys.clear();
		Player.moveTo(1*48, 3*48);
		enemy1.moveTo(1*48, 3*48);
		new AnimationTimer() {
			 int mnX,mnY;
			 ArrayList<Long> delTime = new ArrayList<Long>();
			 public void handle(long currentNanoTime){
				 	gc.setFill(Color.BLACK);
				 	gc.fillRect(0, 0, Constant.SCENE_WIDTH, Constant.SCENE_HEIGHT);
				 	
					gc.drawImage(background, 0, 0);
					
					for(int i=0;i<70;i++) {
						if(disableWall.contains(new Pair<>(posXBrick[i], posYBrick[i])) == false) {
							wall[i] = new Sprite(48,48);
							wall[i].setSpriteImage(brick);
							wall[i].moveTo(posXBrick[i], posYBrick[i]);
							wall[i].draw(gc);
						}
					}
					/*if(lastSpace != 0 && currentNanoTime - delTime.get(0) > 3e9 + 1e8 && currentNanoTime - delTime.get(0) < 3e9+1e8+3e8) {
						delTime.remove(0);
						Bomb.amountBomb+=4;
					}*/
					if(currentNanoTime - lastSpace >= 3e9 && currentNanoTime - lastSpace <= 3e9 + 1e7 && Player.checkCollition(Player.getX(), Player.getY(), mnX, mnY)) {
						Player.die(Player.getX(), Player.getY(), gc, currentNanoTime);
					}
					if(Player.getDead()) {
						this.stop();
						Main.setScene(Main.CREDITS_SCENE);
					}
					
				 	Player.draw(gc);
				 	enemy1.draw(gc);
				 	enemy1.move(enemy1.getCurrentDirection());
					if(activeKeys.contains(KeyCode.ESCAPE)){         
						Player.die(Player.getX(), Player.getY(), gc, currentNanoTime);
						this.stop();
						Main.setScene(Main.WELCOME_SCENE);
					}
					else if(activeKeys.contains(KeyCode.ENTER)) {
						this.stop();
						Main.setScene(Main.CREDITS_SCENE);
					}
					else if(activeKeys.contains(KeyCode.LEFT)) {
						Player.move(MainCharacter.LEFT);
					}
					else if(activeKeys.contains(KeyCode.RIGHT)) {
						Player.move(MainCharacter.RIGHT);
					}
					else if(activeKeys.contains(KeyCode.UP)) {
						Player.move(MainCharacter.UP);
					}
					else if(activeKeys.contains(KeyCode.DOWN)) {
						Player.move(MainCharacter.DOWN);
					}
					else if(currentNanoTime - lastSpace > 3e9 + 1e8 && activeKeys.contains(KeyCode.SPACE)) {
						Bomb bomb = new Bomb();
						Bomb.amountBomb--;
						lastSpace = currentNanoTime;
						delTime.add((long) (currentNanoTime+3e9+1e8));
						System.out.println(Bomb.amountBomb);
						mnX = (Player.getX()/48)*48;
						if(Math.abs(mnX - Player.getX()) > Math.abs((Player.getX()/48+1)*48 - Player.getX())) {
							mnX = (Player.getX()/48+1)*48;
						}
						mnY = (Player.getY()/48)*48;
						if(Math.abs(mnY - Player.getY()) > Math.abs((Player.getY()/48+1)*48 - Player.getY())) {
							mnY = (Player.getY()/48+1)*48;
						}
 						bomb.animate(mnX, mnY, gc, currentNanoTime);
					}
			}
		}.start();
	}
	
	private void addWall() {
		for(int i=1; i<=19; i++) {
			wallCoordinates.add(new Pair<>(i*48,2*48));
			wallCoordinates.add(new Pair<>(i*48,14*48));
		}
		
		for(int j=3; j<=13; j++) {
			wallCoordinates.add(new Pair<>(0,j*48));
			wallCoordinates.add(new Pair<>(20*48,j*48));
		}
		
		for(int i=2; i<=18; i+=2) {
			for(int j=4; j<=12; j+=2) {
				wallCoordinates.add(new Pair<>(i*48,j*48));
			}
		}
		
		//randomWall
		Random rand = new Random();
		int posX, posY;
		for(int tmp = 0; tmp < 70; tmp++) {
			while(true) {
				posX = rand.nextInt(20)*48;
				posY = rand.nextInt(14)*48;
				
				if(posX == 0 || posY < 3*48) 
					continue;
				if(wallBrickCoordinates.contains(new Pair<>(posX,posY)) || wallCoordinates.contains(new Pair<>(posX,posY)))
					continue;
				if(posX == 48 && posY == 3*48 || posX == 48*2 && posY == 3*48 
				|| posX == 48 && posY == 4*48) 
					continue;
				
				//showImage
				wallBrickCoordinates.add(new Pair<>(posX,posY));
				posXBrick[tmp] = posX;
				posYBrick[tmp] = posY;
				break;
			}
			
		}
		
		

	}
	
	public static Boolean checkWall(int x, int y) { 
		if(wallCoordinates.contains(new Pair<>(x,y)) || wallBrickCoordinates.contains(new Pair<>(x,y))) {
			return true;
		}
		return false;
		
	}
}
