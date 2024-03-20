package river;

import java.awt.*;

public class FarmerGameEngine extends AbstractGameEngine {
    private static final Item WOLF = Item.ITEM_2;
    private static final Item GOOSE = Item.ITEM_1;
    private static final Item BEANS = Item.ITEM_0;
    private static final Item FARMER = Item.ITEM_3;

    public FarmerGameEngine() {
        super();
        setItemAndGameObject(WOLF, new GameObject("W", Location.START, Color.CYAN));
        setItemAndGameObject(GOOSE, new GameObject("G", Location.START, Color.CYAN));
        setItemAndGameObject(BEANS, new GameObject("B", Location.START, Color.CYAN));
        setItemAndGameObject(FARMER, new GameObject("", Location.START, Color.MAGENTA));
    }

    @Override
    public int numberOfItems() {
        return getMap().size();
    }

    @Override
    public boolean gameIsLost() {
        if (getItemLocation(GOOSE) == Location.BOAT) {
            return false;
        }
        if (getItemLocation(GOOSE) == getItemLocation(FARMER)) {
            return false;
        }
        if (getItemLocation(GOOSE) == getBoatLocation()) {
            return false;
        }
        if (getItemLocation(GOOSE) == getItemLocation(WOLF)) {
            return true;
        }
        if (getItemLocation(GOOSE) == getItemLocation(BEANS)) {
            return true;
        }
        return super.gameIsLost();
    }
}
