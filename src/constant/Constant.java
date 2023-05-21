package constant;

public class Constant {
    public static final int SCENE_WIDTH = 1008;
    public static final int SCENE_HEIGHT = 720;

    public static final int BLOCK_SIZE = 48;
    public static final int ENEMY_DIE_FRAME = 31;
    public static final int PLAYER_LIFE = 3;
    
    public static final int SEC = (int) 1e9;
    public static final int[][] STATE_ENEMY = {{0}, {3, 2, 0, 0, 0}, {2, 2, 2, 0, 0}, {2, 2, 1, 2, 1}, {0}};
    
	public static final int GAME_WIDTH = 1008;
	public static final int GAME_HEIGHT = 720;
	
	public static final byte PLAYER_DIE_FRAME = 90;
	public static final byte BOMB_FRAME = 15;
}
