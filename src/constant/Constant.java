package constant;

public class Constant {
	
	public static final int MAX_SCENES = 5;
	public static final int WELCOME_SCENE = 0;
	public static final int SOLO_GAME_SCENE = 1;
	public static final int CREDITS_SCENE = 2;
	public static final int NEXTSTAGE_SCENE = 3;
	public static final int VICTORY_SCENE = 4;
	
    public static final int SCENE_WIDTH = 1008;
    public static final int SCENE_HEIGHT = 720;

    public static final int BLOCK_SIZE = 48;
    public static final int ENEMY_DIE_FRAME = 31;
    public static final int PLAYER_LIFE = 3;
    
    public static final long SEC = (long) 1e9;
    public static final int[][] STATE_ENEMY = {{0}, {3, 2, 0, 0, 0}, {2, 2, 2, 0, 0}, {2, 2, 1, 2, 1}, {0}};
    
	public static final int GAME_WIDTH = 1008;
	public static final int GAME_HEIGHT = 720;
	
	public static final byte PLAYER_DIE_FRAME = 90;
	public static final byte BOMB_FRAME = 15;
    public static final byte WALL_FRAME = 10;
}
