package scenes;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import Constant.Constant;
import application.Main;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Pair;
import sprites.Bomb;
import sprites.Enemy1;
import sprites.Enemy5;
import sprites.MainCharacter;
import sprites.Sprite;


public class SoloGameScene extends GeneralScene{
	private static final String BACKGROUND_IMAGE = "assets/background.png";
	private static final String BRICK_IMAGE = "assets/Brick.png";
	private static final String MUSIC_ON_IMAGE = "assets/MusicOn.png";
	private static final String MUSIC_OFF_IMAGE = "assets/MusicOff.png";

	public static int posXBrick[] = new int[110];
	public static int posYBrick[] = new int[110];
	private int posXEnemy[] = new int[10];
	private int posYEnemy[] = new int[10];
	private int posXItem[] = new int[10];
	private int posYItem[] = new int[10];
	
	
	public static Sprite wall[];
	public static ArrayList<Pair<Integer, Integer>> disableWall = new ArrayList<Pair<Integer, Integer>>();
	public static ArrayList<Pair<Integer, Integer>> wallCoordinates = new ArrayList<Pair<Integer, Integer>>();
	public static ArrayList<Pair<Integer, Integer>> wallBrickCoordinates = new ArrayList<Pair<Integer, Integer>>();
	
	public static ArrayList<Pair<Integer, Integer>> BombCoordinates = new ArrayList<Pair<Integer, Integer>>();
	public static ArrayList<Pair<Integer, Integer>> EnemyCoordinates = new ArrayList<Pair<Integer, Integer>>();
	public static ArrayList<Pair<Integer, Integer>> ItemCoordinates = new ArrayList<Pair<Integer, Integer>>();
	public static ArrayList<Bomb> BombArr = new ArrayList<Bomb>();
	
	private Image background,brick,musicOn,musicOff;
	private MainCharacter Player;
	private Enemy1[] enemy1;
	
	public static final String BACKGROUND_SONG = "assets/SoloGameSceneMusic.wav";
	public static final String PLACE_BOMB_EFFECT = "assets/place_bomb.wav";
	public static final String EXPLOSION_EFFECT = "assets/explosion.wav";
	
	private MediaPlayer mediaPlayerEffects;
	private Media effect;
	
	private boolean isMusicEnabled = true;
	
	public SoloGameScene() {
		super();
		addObject();
		//GeneratePosEnemy(5);
		//GeneratePosItem(1, 2, 2);
		wall = new Sprite[75];
		try {
			background = new Image(Files.newInputStream(Paths.get(BACKGROUND_IMAGE)));
			brick = new Image(Files.newInputStream(Paths.get(BRICK_IMAGE)));
			
			musicOn = new Image(Files.newInputStream(Paths.get(MUSIC_ON_IMAGE)));
			musicOff = new Image(Files.newInputStream(Paths.get(MUSIC_OFF_IMAGE)));
			
			Player = new MainCharacter();
			enemy1 = new Enemy1[6];
			for(int i=0;i<5;i++) {
				enemy1[i] = new Enemy1();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void showMessage() {
		
		Font myFont = Font.font("Arial", FontWeight.NORMAL, 16);
		gc.setFont(myFont);
		gc.setFill(Color.BLACK);
		gc.fillText("Press P to turn on/off the music", 50, 30);
		
	}
	
	@Override
	public void draw() {
		
		sound = new Media(new File(BACKGROUND_SONG).toURI().toString());
		mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer.play();
		
		activeKeys.clear();
		Player.moveTo(1*48, 3*48);
		for(int i=0; i<EnemyCoordinates.size(); i++) {
			enemy1[i].moveTo(EnemyCoordinates.get(i).getKey(), EnemyCoordinates.get(i).getValue());
		}
		new AnimationTimer() {
			 int mnX,mnY;
			 long lastP,lastSpace,lastDie;
			 boolean chEnemyCollision = true;
			 ArrayList<Long> delTime = new ArrayList<Long>();
			 public void handle(long currentNanoTime){
				 	gc.setFill(Color.BLACK);
				 	gc.fillRect(0, 0, Constant.SCENE_WIDTH, Constant.SCENE_HEIGHT);
					gc.drawImage(background, 0, 0);
					showMessage();
					drawMusic();
					for(int i=0;i<70;i++) {
						if(disableWall.contains(new Pair<>(posXBrick[i], posYBrick[i])) == false) {
							wall[i] = new Sprite(48,48);
							wall[i].setSpriteImage(brick);
							wall[i].moveTo(posXBrick[i], posYBrick[i]);
							wall[i].draw(gc);
						}
					}
					if(!delTime.isEmpty() && currentNanoTime - delTime.get(0) >= 3e9+1e8) {
						Player.setAmountBomb(Player.getAmountBomb()+1);
						delTime.remove(0);
					}
					
					if(currentNanoTime - lastDie >= 2e8 && currentNanoTime - lastSpace >= 3e9 && currentNanoTime - lastSpace <= 3e9 + 5e8 && Player.checkBomb(mnX,mnY, Player.getFireRange())) {
						lastDie = currentNanoTime;
						Player.die(Player.getX(), Player.getY(), gc, currentNanoTime);
					}
					for(int i=0; i<EnemyCoordinates.size(); i++) {
						enemy1[i].draw(gc);
						enemy1[i].move(enemy1[i].getCurrentDirection());
						if(currentNanoTime - lastSpace >= 3e9 && currentNanoTime - lastSpace <= 3e9 + 1e7 && enemy1[i].checkBomb(mnX, mnY, Player.getFireRange()) && !enemy1[i].getDead()) {
							enemy1[i].die(enemy1[i].getX(), enemy1[i].getY(), gc, currentNanoTime);
						}
						if(chEnemyCollision && enemy1[i].getDead() == false && Player.checkCollision(Player.getX(), Player.getY(), enemy1[i].getX(), enemy1[i].getY())) {
							Player.die(Player.getX(), Player.getY(), gc, currentNanoTime);
							chEnemyCollision = false;
						}
					}
					
					
					if(Player.getDead()) {
						this.stop();
						Main.setScene(Main.CREDITS_SCENE);
					}
					
					
				 	Player.draw(gc);
				 	
			
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
				 	else if(Player.getAmountBomb()>0 && currentNanoTime - lastSpace >= 2e8 * 4 && activeKeys.contains(KeyCode.SPACE)) {
				 		Bomb bomb = new Bomb();
				 		BombArr.add(bomb);
				 		Player.setAmountBomb(Player.getAmountBomb() - 1);
				 		playEffect(PLACE_BOMB_EFFECT);
				 		lastSpace = currentNanoTime;
				 		delTime.add((long) (currentNanoTime));
						mnX = (Player.getX()/48)*48;
						if(Math.abs(mnX - Player.getX()) > Math.abs((Player.getX()/48+1)*48 - Player.getX())) {
							mnX = (Player.getX()/48+1)*48;
						}
						mnY = (Player.getY()/48)*48;
						if(Math.abs(mnY - Player.getY()) > Math.abs((Player.getY()/48+1)*48 - Player.getY())) {
							mnY = (Player.getY()/48+1)*48;
						}
						if(BombCoordinates.contains(new Pair<>(mnX,mnY)) == false) {
	                    	BombCoordinates.add(new Pair<>(mnX,mnY));
	                    	
	                    }
						bomb.animate(mnX, mnY, gc, currentNanoTime, Player.getFireRange());

						
				 	}
				 	else if(currentNanoTime - lastP >= 3e8 && activeKeys.contains(KeyCode.P)) {
				 		lastP = currentNanoTime;
						SwitchMusic();
					}
				 	
				 }
			
		}.start();
	}
	
	private void addObject() {
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
		wallBrickCoordinates.add(new Pair<>(3*48,3*48));
		posXBrick[68] = 3*48;posYBrick[68] = 3*48;
		wallBrickCoordinates.add(new Pair<>(1*48,5*48));
		posXBrick[69] = 1*48;posYBrick[69] = 5*48;
		int posX, posY;
		for(int tmp = 0; tmp < 68; tmp++) {
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
		
		//randomMonster
		for(int tmp = 0; tmp < 5; tmp++) {
			while(true) {
				posX = rand.nextInt(20)*48;
				posY = rand.nextInt(14)*48;
				
				if(posX == 0 || posY < 3*48) 
					continue;
				if(wallBrickCoordinates.contains(new Pair<>(posX,posY)) || wallCoordinates.contains(new Pair<>(posX,posY)) || EnemyCoordinates.contains(new Pair<>(posX,posY)))
					continue;
				if(posX == 48 && posY == 3*48 || posX == 48*2 && posY == 3*48 
				|| posX == 48 && posY == 4*48) 
					continue;
				
				//showImage
				EnemyCoordinates.add(new Pair<>(posX, posY));
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
	
	private void playEffect(String path)
	{
		effect = new Media(new File(path).toURI().toString());
		mediaPlayerEffects = new MediaPlayer(effect);
		mediaPlayerEffects.play();
	}
	
	private void SwitchMusic() {
		if(isMusicEnabled) {
			mediaPlayer.pause();
			isMusicEnabled = false;
			
		}
		else {
			mediaPlayer.play();
			isMusicEnabled = true;
		}
	}
	
	private void drawMusic() {
		if(isMusicEnabled) {
			gc.drawImage(musicOn,0,0);
		}
		else {
			gc.drawImage(musicOff,0,0);
		}
	}

	
	
	
}

