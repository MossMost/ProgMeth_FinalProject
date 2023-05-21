package item;

import sprites.MainCharacter;
import sprites.Sprite;

public abstract class Item extends Sprite{

    public Item(int width, int height) {
        super(width, height);

    }
    
    abstract public void ItemEffect(MainCharacter Player);
}