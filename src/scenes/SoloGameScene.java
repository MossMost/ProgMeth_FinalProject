package scenes;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import constant.Constant;
import application.Main;
import enemy.Enemy1;
import enemy.Enemy2;
import enemy.Enemy3;
import enemy.Enemy4;
import enemy.Enemy5;
import item.AmountUp;
import item.Door;
import item.RangeUp;
import item.SpeedUp;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Pair;
import music.MusicPlayable;
import sprites.AnimatedSprite;
import sprites.Bomb;
import sprites.MainCharacter;
import sprites.Sprite;


public class SoloGameScene extends GeneralScene implements MusicPlayable{
	private static final String BACKGROUND_IMAGE = "assets/background.png";
	private static final String BRICK_IMAGE = "assets/Brick.png";
	private static final String MUSIC_ON_IMAGE = "assets/MusicGoldOn.png";
	private static final String MUSIC_OFF_IMAGE = "assets/MusicGoldOff.png";

	public static int posXBrick[] = new int[110];
	public static int posYBrick[] = new int[110];
	
	public static Sprite wall[];
	public static ArrayList<Pair<Integer, Integer>> disableWall;
	public static ArrayList<Pair<Integer, Integer>> wallCoordinates;
	public static ArrayList<Pair<Integer, Integer>> wallBrickCoordinates;
	
	public static ArrayList<Pair<Integer, Integer>> BombCoordinates;
	public static ArrayList<Pair<Integer, Integer>> EnemyCoordinates;
	
	public static ArrayList<Pair<Integer, Integer>> ItemCoordinates;
	public static ArrayList<Pair<Integer, Integer>> DoorCoordinates;
	public static ArrayList<Pair<Integer, Integer>> RangeCoordinates;
	public static ArrayList<Pair<Integer, Integer>> SpeedCoordinates;
	public static ArrayList<Pair<Integer, Integer>> AmountCoordinates;
	
	public static ArrayList<Bomb> BombArr = new ArrayList<Bomb>();
	
	private Image background,brick,musicOn,musicOff;
	private MainCharacter Player;
	private Enemy1[] enemy1;
	private Enemy2[] enemy2;
	private Enemy3[] enemy3;
	private Enemy4[] enemy4;
	private Enemy5[] enemy5;
	private Door[] door;
	private RangeUp[] rangeUp;
	private SpeedUp[] speedUp;
	private AmountUp[] amountUp;
	
	public static final String BACKGROUND_SONG = "assets/SoloGameSceneMusic.wav";
	public static final String PLACE_BOMB_EFFECT = "assets/place_bomb.wav";
	public static final String EXPLOSION_EFFECT = "assets/explosion.wav";	
	public static final String DIE_EFFECT = "assets/PlayerDie.wav";
	public static final String ITEM_EFFECT = "assets/Collectitem.wav";
	
	public static int stage;
	
	private static MediaPlayer mediaPlayerEffects;
	private static Media effect;
	
	private int dividedDead;
	private boolean isMusicEnabled = true;
	
	public SoloGameScene() {
		super();
		try {
			Player = new MainCharacter();
			background = new Image(Files.newInputStream(Paths.get(BACKGROUND_IMAGE)));
			brick = new Image(Files.newInputStream(Paths.get(BRICK_IMAGE)));
			
			musicOn = new Image(Files.newInputStream(Paths.get(MUSIC_ON_IMAGE)));
			musicOff = new Image(Files.newInputStream(Paths.get(MUSIC_OFF_IMAGE)));
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void draw() {
		reset();
		setDividedDead(0);
		if(isMusicEnabled == true)
			playLoopMusic();
		
		activeKeys.clear();
		Player.moveTo(1*48, 3*48);
		Player.setDead(false);
		for(int i=0; i<DoorCoordinates.size(); i++) {
			door[i].moveTo(DoorCoordinates.get(i).getKey(), DoorCoordinates.get(i).getValue());
		}
		
		for(int i=0; i<RangeCoordinates.size(); i++) {
			rangeUp[i].moveTo(RangeCoordinates.get(i).getKey(), RangeCoordinates.get(i).getValue());
		}
		
		for(int i=0; i<SpeedCoordinates.size(); i++) {
			speedUp[i].moveTo(SpeedCoordinates.get(i).getKey(), SpeedCoordinates.get(i).getValue());
		}
		
		for(int i=0; i<AmountCoordinates.size(); i++) {
			amountUp[i].moveTo(AmountCoordinates.get(i).getKey(), AmountCoordinates.get(i).getValue());
		}
		new AnimationTimer() {
			 int mnX,mnY;
			 long lastP,lastSpace,lastDie;
			 ArrayList<Long> delTime = new ArrayList<Long>();
			 public void handle(long currentNanoTime){
					
				 	showImage();
				 	showMessage();
					for(int i=0; i<DoorCoordinates.size();i++) {
						boolean ch = true;
						for(int j=0;j<Constant.STATE_ENEMY[stage][0]+2*getDividedDead();j++) {
							if(enemy1[j].getDead() == false)
								ch = false;
						}
						for(int j=0;j<Constant.STATE_ENEMY[stage][1];j++) {
							if(enemy2[j].getDead() == false)
								ch = false;
						}
						for(int j=0;j<Constant.STATE_ENEMY[stage][2];j++) {
							if(enemy3[j].getDead() == false)
								ch = false;
						}
						for(int j=0;j<Constant.STATE_ENEMY[stage][3];j++) {
							if(enemy4[j].getDead() == false)
								ch = false;
						}
						for(int j=0;j<Constant.STATE_ENEMY[stage][4];j++) {
							if(enemy5[j].getDead() == false)
								ch = false;
						}
						if(door[i]!=null && ch && Player.checkCollision(Player.getX(), Player.getY(), DoorCoordinates.get(i).getKey(), DoorCoordinates.get(i).getValue())) {
							this.stop();
							stopMusic();
							setStage(stage + 1);
							door[i].ItemEffect(Player);
						}
					}
					
					for(int i=0; i<RangeCoordinates.size();i++) {
						if(rangeUp[i]!=null&&Player.checkCollision(Player.getX(), Player.getY(), RangeCoordinates.get(i).getKey(), RangeCoordinates.get(i).getValue())) {
							playEffect(ITEM_EFFECT);
							rangeUp[i].ItemEffect(Player);
							rangeUp[i] = null;
						}
					}
					
					for(int i=0; i<SpeedCoordinates.size();i++) {
						if(speedUp[i]!=null&&Player.checkCollision(Player.getX(), Player.getY(), SpeedCoordinates.get(i).getKey(), SpeedCoordinates.get(i).getValue())) {
							playEffect(ITEM_EFFECT);
							speedUp[i].ItemEffect(Player);
							speedUp[i] = null;
						}
					}
					
					for(int i=0; i<AmountCoordinates.size();i++) {
						if(amountUp[i]!=null&&Player.checkCollision(Player.getX(), Player.getY(), AmountCoordinates.get(i).getKey(), AmountCoordinates.get(i).getValue())) {
							playEffect(ITEM_EFFECT);
							amountUp[i].ItemEffect(Player);
							amountUp[i] = null;
						}
					}
					
					for(int i=0;i<DoorCoordinates.size();i++) {///
						door[i].draw(gc);
					}
					
					for(int i=0;i<RangeCoordinates.size();i++) {
						if(rangeUp[i]!=null)
							rangeUp[i].draw(gc);
					}
					
					for(int i=0;i<SpeedCoordinates.size();i++) {
						if(speedUp[i]!=null)
							speedUp[i].draw(gc);
					}
					
					for(int i=0;i<AmountCoordinates.size();i++) {
						if(amountUp[i]!=null)
							amountUp[i].draw(gc);
					}
					
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
						BombCoordinates.remove(0);
						BombArr.remove(0);
						delTime.remove(0);
					}
					
					if(!delTime.isEmpty() && !BombCoordinates.isEmpty() && currentNanoTime - lastDie >= 2e8 && currentNanoTime - delTime.get(0) > 3e9 && currentNanoTime - delTime.get(0) <= 3e9 + 1e7 && Player.checkBomb(BombCoordinates.get(0).getKey(), BombCoordinates.get(0).getValue(), Player.getFireRange())) {
						lastDie = currentNanoTime;
						Player.die(Player.getX(), Player.getY(), gc, currentNanoTime);
					}
					
					
					//enemy
					for(int i=0; i<Constant.STATE_ENEMY[stage][0]+2*getDividedDead(); i++) {
						enemy1[i].draw(gc);
						enemy1[i].move(enemy1[i].getCurrentDirection());
						for(int j=0; j<delTime.size();j++) {
							if(currentNanoTime - delTime.get(j) >= 3e9 && currentNanoTime - delTime.get(j) <= 3e9 + 1e7 && enemy1[i].checkBomb(mnX, mnY, Player.getFireRange()) && !enemy1[i].getDead()) {
								enemy1[i].die(enemy1[i].getX(), enemy1[i].getY(), gc, currentNanoTime);
							}
						}
						
						if(currentNanoTime - lastDie >= 2e8 && enemy1[i].getDead() == false && Player.checkEnemy(Player.getX(), Player.getY(), enemy1[i].getX(), enemy1[i].getY())) {
							Player.die(Player.getX(), Player.getY(), gc, currentNanoTime);
							lastDie = currentNanoTime;
						}
					}
					for(int i=0; i<Constant.STATE_ENEMY[stage][1]; i++) {
						enemy2[i].draw(gc);
						enemy2[i].move(enemy2[i].getCurrentDirection());
						for(int j=0; j<delTime.size();j++) {
							if(currentNanoTime - delTime.get(j) >= 3e9 && currentNanoTime - delTime.get(j) <= 3e9 + 1e7 && enemy2[i].checkBomb(mnX, mnY, Player.getFireRange()) && !enemy2[i].getDead()) {
								enemy2[i].die(enemy2[i].getX(), enemy2[i].getY(), gc, currentNanoTime);
							}
						}
						
						if(currentNanoTime - lastDie >= 2e8 && enemy2[i].getDead() == false && Player.checkEnemy(Player.getX(), Player.getY(), enemy2[i].getX(), enemy2[i].getY())) {
							Player.die(Player.getX(), Player.getY(), gc, currentNanoTime);
							lastDie = currentNanoTime;
						}
					}
					for(int i=0; i<Constant.STATE_ENEMY[stage][2]; i++) {
						enemy3[i].draw(gc);
						enemy3[i].move(enemy3[i].getCurrentDirection());
						for(int j=0; j<delTime.size();j++) {
							if(currentNanoTime - delTime.get(j) >= 3e9 && currentNanoTime - delTime.get(j) <= 3e9 + 1e7 && enemy3[i].checkBomb(mnX, mnY, Player.getFireRange()) && !enemy3[i].getDead()) {
								int newMonster1 = Constant.STATE_ENEMY[stage][1]+2*getDividedDead();
								int posX = enemy3[i].getX();
								int posY = enemy3[i].getY();
								enemy1[newMonster1].moveTo(posX, posY);
								enemy1[newMonster1].setCurrentDirection(AnimatedSprite.LEFT);
								enemy1[newMonster1+1].moveTo(posX, posY);
								enemy1[newMonster1+1].setCurrentDirection(AnimatedSprite.RIGHT);
								setDividedDead(getDividedDead()+1);
								enemy3[i].die(enemy3[i].getX(), enemy3[i].getY(), gc, currentNanoTime);
							}
						}
						
						if(currentNanoTime - lastDie >= 2e8 && enemy3[i].getDead() == false && Player.checkEnemy(Player.getX(), Player.getY(), enemy3[i].getX(), enemy3[i].getY())) {
							Player.die(Player.getX(), Player.getY(), gc, currentNanoTime);
							lastDie = currentNanoTime;
						}
					}
					for(int i=0; i<Constant.STATE_ENEMY[stage][3]; i++) {
						enemy4[i].draw(gc);
						enemy4[i].move(enemy4[i].getCurrentDirection());
						for(int j=0; j<delTime.size();j++) {
							if(currentNanoTime - delTime.get(j) >= 3e9 && currentNanoTime - delTime.get(j) <= 3e9 + 1e7 && enemy4[i].checkBomb(mnX, mnY, Player.getFireRange()) && !enemy4[i].getDead()) {
								enemy4[i].die(enemy4[i].getX(), enemy4[i].getY(), gc, currentNanoTime);
							}
						}
						
						if(currentNanoTime - lastDie >= 2e8 && enemy4[i].getDead() == false && Player.checkEnemy(Player.getX(), Player.getY(), enemy4[i].getX(), enemy4[i].getY())) {
							Player.die(Player.getX(), Player.getY(), gc, currentNanoTime);
							lastDie = currentNanoTime;
						}
					}
					for(int i=0; i<Constant.STATE_ENEMY[stage][4]; i++) {
						enemy5[i].draw(gc);
						if(!enemy5[i].getDead())
							enemy5[i].move(enemy5[i].getCurrentDirection());
						for(int j=0; j<delTime.size();j++) {
							if(currentNanoTime - delTime.get(j) >= 3e9 && currentNanoTime - delTime.get(j) <= 3e9 + 1e7 && enemy5[i].checkBomb(mnX, mnY, Player.getFireRange()) && !enemy5[i].getDead()) {
								enemy5[i].die(enemy5[i].getX(), enemy5[i].getY(), gc, currentNanoTime);
							}
						}
						
						if(currentNanoTime - lastDie >= 2e8 && enemy5[i].getDead() == false && Player.checkEnemy(Player.getX(), Player.getY(), enemy5[i].getX(), enemy5[i].getY())) {
							Player.die(Player.getX(), Player.getY(), gc, currentNanoTime);
							lastDie = currentNanoTime;
						}
					}
					
					//CheckDead
					if(currentNanoTime - lastDie >= 2e9 && Player.getDead()) {
						this.stop();
						stopMusic();
						Player.setLife(Player.getLife()-1);
						if(Player.getLife() == 0) {
							Main.setScene(Constant.CREDITS_SCENE);
							Player.setLife(3);
						}
						else {
							Main.setScene(Constant.SOLO_GAME_SCENE);
						}
					}
					
					
				 	Player.draw(gc);
				 	
			
				 	if(activeKeys.contains(KeyCode.ESCAPE)){         
				 		Player.die(Player.getX(), Player.getY(), gc, currentNanoTime);
				 		this.stop();
				 		Main.setScene(Constant.WELCOME_SCENE);
				 	}
				 	else if(activeKeys.contains(KeyCode.ENTER)) {
				 		this.stop();
				 		Main.setScene(Constant.CREDITS_SCENE);
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
		
		
		//randomItem
		
		
	}
	
	private void GeneratePosItem(int numberof_door, int numberof_rangeUp, int numberof_speedUp, int numberof_amountUp) {
		GenerateDoorItem(numberof_door);
		GeneratePowerItem(numberof_rangeUp);
		GenerateSpeedItem(numberof_speedUp);
		GenerateAmountItem(numberof_amountUp);
		
	}
	
	private void GenerateDoorItem(int n) {
		Random rand = new Random();
		int posX, posY;
		for(int tmp = 0; tmp < n; tmp++) {
			while(true) {
				posX = rand.nextInt(20)*48;
				posY = rand.nextInt(14)*48;
				
				if(ItemCoordinates.contains(new Pair<>(posX,posY)))
					continue;
				
				if(wallBrickCoordinates.contains(new Pair<>(posX,posY))) {
					ItemCoordinates.add(new Pair<>(posX,posY));
					DoorCoordinates.add(new Pair<>(posX,posY));
					break;
				}
			}
		}
	}
	
	private void GenerateSpeedItem(int n) {
		Random rand = new Random();
		int posX, posY;
		for(int tmp = 0; tmp < n; tmp++) {
			while(true) {
				posX = rand.nextInt(20)*48;
				posY = rand.nextInt(14)*48;
				
				if(ItemCoordinates.contains(new Pair<>(posX,posY)))
					continue;
				
				if(wallBrickCoordinates.contains(new Pair<>(posX,posY))) {
					ItemCoordinates.add(new Pair<>(posX,posY));
					SpeedCoordinates.add(new Pair<>(posX,posY));
					break;
				}
			}
		}
	}
	
	private void GeneratePowerItem(int n) {
		Random rand = new Random();
		int posX, posY;
		for(int tmp = 0; tmp < n; tmp++) {
			while(true) {
				posX = rand.nextInt(20)*48;
				posY = rand.nextInt(14)*48;
				
				if(ItemCoordinates.contains(new Pair<>(posX,posY)))
					continue;
				
				if(wallBrickCoordinates.contains(new Pair<>(posX,posY))) {
					ItemCoordinates.add(new Pair<>(posX,posY));
					RangeCoordinates.add(new Pair<>(posX,posY));
					break;
				}
			}
		}
	}
	
	private void GenerateAmountItem(int n) {
		Random rand = new Random();
		int posX, posY;
		for(int tmp = 0; tmp < n; tmp++) {
			while(true) {
				posX = rand.nextInt(20)*48;
				posY = rand.nextInt(14)*48;
				
				if(ItemCoordinates.contains(new Pair<>(posX,posY)))
					continue;
				
				if(wallBrickCoordinates.contains(new Pair<>(posX,posY))) {
					ItemCoordinates.add(new Pair<>(posX,posY));
					AmountCoordinates.add(new Pair<>(posX,posY));
					break;
				}
			}
		}
	}
	
	private void GenerateEnemy(int type1,int type2, int type3, int type4, int type5) {
		Random rand = new Random();
		int posX, posY;
		for(int tmp = 0; tmp < type1; tmp++) {
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
				
				enemy1[tmp].moveTo(posX, posY);
				//showImage
				EnemyCoordinates.add(new Pair<>(posX, posY));
				break;
			}
		}
		
		for(int tmp = 0; tmp < type2; tmp++) {
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
				
				enemy2[tmp].moveTo(posX, posY);
				//showImage
				EnemyCoordinates.add(new Pair<>(posX, posY));
				break;
			}
		}
		
		for(int tmp = 0; tmp < type3; tmp++) {
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
				
				enemy3[tmp].moveTo(posX, posY);
				//showImage
				EnemyCoordinates.add(new Pair<>(posX, posY));
				break;
			}
		}
		
		for(int tmp = 0; tmp < type4; tmp++) {
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
				
				enemy4[tmp].moveTo(posX, posY);
				//showImage
				EnemyCoordinates.add(new Pair<>(posX, posY));
				break;
			}
		}
		
		for(int tmp = 0; tmp < type5; tmp++) {
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
				
				enemy5[tmp].moveTo(posX, posY);
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
	
	public static void playEffect(String path)
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
	
	private void reset() {
		int playerLife = Player.getLife();
		Player = new MainCharacter();
		Player.setLife(playerLife);
		disableWall = new ArrayList<Pair<Integer, Integer>>();
		wallCoordinates = new ArrayList<Pair<Integer, Integer>>();
		wallBrickCoordinates = new ArrayList<Pair<Integer, Integer>>();
		
		BombCoordinates = new ArrayList<Pair<Integer, Integer>>();
		EnemyCoordinates = new ArrayList<Pair<Integer, Integer>>();
		
		ItemCoordinates = new ArrayList<Pair<Integer, Integer>>();
		DoorCoordinates = new ArrayList<Pair<Integer, Integer>>();
		RangeCoordinates = new ArrayList<Pair<Integer, Integer>>();
		SpeedCoordinates = new ArrayList<Pair<Integer, Integer>>();
		AmountCoordinates = new ArrayList<Pair<Integer, Integer>>();
		
		wall = new Sprite[75];
		
		enemy1 = new Enemy1[7];
		enemy2 = new Enemy2[7];
		enemy3 = new Enemy3[7];
		enemy4 = new Enemy4[7];
		enemy5 = new Enemy5[7];
		door = new Door[2];
		rangeUp = new RangeUp[10];
		speedUp = new SpeedUp[10];
		amountUp = new AmountUp[10];
		
		for(int i=0;i<3+4;i++) {
			enemy1[i] = new Enemy1();
		}
		for(int i=0;i<3;i++) {
			enemy2[i] = new Enemy2();
		}
		for(int i=0;i<3;i++) {
			enemy3[i] = new Enemy3();
		}
		for(int i=0;i<3;i++) {
			enemy4[i] = new Enemy4();
		}
		for(int i=0;i<3;i++) {
			enemy5[i] = new Enemy5();
		}
		
		for(int i=0;i<1;i++) {
			door[i] = new Door();
		}
		for(int i=0;i<8;i++) {
			rangeUp[i] = new RangeUp();
		}
		for(int i=0;i<8;i++) {
			speedUp[i] = new SpeedUp();
		}
		for(int i=0;i<8;i++) {
			amountUp[i] = new AmountUp();
		}
		
		addObject();
		GeneratePosItem(1, 5, 5, 5);
		GenerateEnemy(Constant.STATE_ENEMY[stage][0],Constant.STATE_ENEMY[stage][1],Constant.STATE_ENEMY[stage][2],Constant.STATE_ENEMY[stage][3],Constant.STATE_ENEMY[stage][4]);
	}
	
	@Override
	public void playLoopMusic() {
		sound = new Media(new File(BACKGROUND_SONG).toURI().toString());
		mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer.play();
	}
	
	@Override
	public void stopMusic() {
		mediaPlayer.stop();
	}
	
	public static void setStage(int n) {
		stage = n;
	}

	@Override
	public void showMessage() {
		
		Font State = Font.font("verdana", FontWeight.BOLD, 40);
		gc.setFont(State);
		gc.setFill(Color.WHITE);
		gc.fillText(Integer.toString(stage), 49*2+10 , 53);
		
		Font Life = Font.font("verdana", FontWeight.BOLD, 40);
		gc.setFont(Life);
		gc.setFill(Color.WHITE);
		gc.fillText(Integer.toString(Player.getLife()), 49*5+10 , 53);
		
		Font Monster = Font.font("verdana", FontWeight.BOLD, 40);
		gc.setFont(Monster);
		gc.setFill(Color.WHITE);
		int cnt = 0;
		for(int i=0;i<Constant.STATE_ENEMY[stage][0]+2*getDividedDead();i++) {
			if(enemy1[i].getDead() == false)
				cnt++;
		}
		for(int i=0;i<Constant.STATE_ENEMY[stage][1];i++) {
			if(enemy2[i].getDead() == false)
				cnt++;
		}
		for(int i=0;i<Constant.STATE_ENEMY[stage][2];i++) {
			if(enemy3[i].getDead() == false)
				cnt++;
		}
		for(int i=0;i<Constant.STATE_ENEMY[stage][3];i++) {
			if(enemy4[i].getDead() == false)
				cnt++;
		}
		for(int i=0;i<Constant.STATE_ENEMY[stage][4];i++) {
			if(enemy5[i].getDead() == false)
				cnt++;
		}
		gc.fillText(Integer.toString(cnt), 49*8+10 , 53);
		
		Font Bomb = Font.font("verdana", FontWeight.BOLD, 40);
		gc.setFont(Bomb);
		gc.setFill(Color.WHITE);
		gc.fillText(Integer.toString(Player.getAmountBomb()), 49*11+10 , 53);
		
		Font Range = Font.font("verdana", FontWeight.BOLD, 40);
		gc.setFont(Range);
		gc.setFill(Color.WHITE);
		gc.fillText(Integer.toString(Player.getFireRange()), 49*14+10 , 53);
		
		Font Speed = Font.font("verdana", FontWeight.BOLD, 40);
		gc.setFont(Speed);
		gc.setFill(Color.WHITE);
		gc.fillText(Integer.toString( (int)((Player.getStep()-2)*2) + 1 ), 49*17+10 , 53);
		
	}

	@Override
	public void showImage() {
		gc.setFill(Color.BLACK);
	 	gc.fillRect(0, 0, Constant.SCENE_WIDTH, Constant.SCENE_HEIGHT);
		gc.drawImage(background, 0, 0);
		
		if(isMusicEnabled) {
			gc.drawImage(musicOn,912,10);
		}
		else {
			gc.drawImage(musicOff,912,10);
		}
	}
	
	//getter&&setter
	public int getDividedDead() {
		return dividedDead;
	}
	
	public void setDividedDead(int n) {
		dividedDead = n;
	}
	
}

