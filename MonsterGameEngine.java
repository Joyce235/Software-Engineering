package river;

import java.awt.*;

public class MonsterGameEngine extends AbstractGameEngine {

    private static final Item MONSTER_1 = Item.ITEM_0;
    private static final Item MUNCHKINS_1 = Item.ITEM_1;
    private static final Item MONSTER_2 = Item.ITEM_2;
    private static final Item MUNCHKINS_2 = Item.ITEM_3;
    private static final Item MONSTER_3 = Item.ITEM_4;
    private static final Item MUNCHKINS_3 = Item.ITEM_5;

    public MonsterGameEngine() {
        super();
        setItemAndGameObject(MONSTER_1, new GameObject("MO1", Location.START, Color.MAGENTA));
        setItemAndGameObject(MUNCHKINS_1, new GameObject("MU1", Location.START, Color.YELLOW));
        setItemAndGameObject(MONSTER_2, new GameObject("MO2", Location.START, Color.MAGENTA));
        setItemAndGameObject(MUNCHKINS_2, new GameObject("MU2", Location.START, Color.YELLOW));
        setItemAndGameObject(MONSTER_3, new GameObject("MO3", Location.START, Color.MAGENTA));
        setItemAndGameObject(MUNCHKINS_3, new GameObject("MU3", Location.START, Color.YELLOW));
    }

    /**
     * Returns the number of items in the game. That is, the number of items
     * you need to take to the other side to win.
     *
     * @return the number of items in the game
     */
    @Override
    public int numberOfItems() {
        return getMap().size();
    }

    private boolean isMonstersGreaterThanMunchins() {
        int munchinsOnTheLeft = 0;
        int munchinsOnTheRight = 0;
        int monstersOnTheLeft = 0;
        int monstersOnTheRight = 0;

        for (Item item : Item.values()) {
            if (!(item.ordinal() < numberOfItems())) break;
            // count monsters and munchins on the left
            if (getMap().get(item).getLocation() == Location.START) {
                if (item == Item.ITEM_0 || item == Item.ITEM_2 || item == Item.ITEM_4) monstersOnTheLeft++;
                else munchinsOnTheLeft++;
            }
            // count munchins and monster on the right
            if (getMap().get(item).getLocation() == Location.FINISH) {
                if (item == Item.ITEM_1 || item == Item.ITEM_3 || item == Item.ITEM_5) munchinsOnTheRight++;
                else monstersOnTheRight++;
            }
            // if muchin or monster is on the boat
            if(getMap().get(item).getLocation() == Location.BOAT) {
                if(getBoatLocation() == Location.START) {
                    if (item == Item.ITEM_0 || item == Item.ITEM_2 || item == Item.ITEM_4) monstersOnTheLeft++;
                    else munchinsOnTheLeft++;
                } else if(getBoatLocation() == Location.FINISH) {
                    if (item == Item.ITEM_1 || item == Item.ITEM_3 || item == Item.ITEM_5) munchinsOnTheRight++;
                    else monstersOnTheRight++;
                }
            }
        }

        if (munchinsOnTheLeft == 0 || munchinsOnTheRight == 0) return false;

        return monstersOnTheLeft > munchinsOnTheLeft || monstersOnTheRight > munchinsOnTheRight;
    }

    @Override
    public boolean gameIsLost() {
        super.gameIsLost();
        // The munchkins must never be outnumbered by the monsters
        return isMonstersGreaterThanMunchins();
    }
}
