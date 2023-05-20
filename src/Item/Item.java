package Item;

import sprites.MainCharacter;
import sprites.Sprite;

public abstract class Item extends Sprite{

    public Item(int width, int height) {
        super(width, height);

    }

    public boolean checkcollision(int PlayerX, int PlayerY) {
        return true;

    }
    abstract public void ItemEffect(MainCharacter Player);


}