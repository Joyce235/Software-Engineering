package river;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractGameEngine implements GameEngine {

    private final Map<Item, GameObject> map;
    private Location boatLocation;

    public AbstractGameEngine() {
        this.map = new HashMap<>();
        this.boatLocation = Location .START;
    }

    public Map<Item, GameObject> getMap() {
        return this.map;
    }

    public void setItemAndGameObject(Item item, GameObject gameObject) {
        this.map.put(item, gameObject);
    }

    /**
     * Returns the label of the specified item. This method may be used by a GUI
     * (for example) to put the label string inside of a rectangle. A label is
     * typically one or two characters long.
     *
     * @param item the item with the desired label
     * @return the label of the specified item
     */
    @Override
    public String getItemLabel(Item item) {
        return this.map.get(item).getLabel();
    }

    /**
     * Returns the color of the specified item. This method may be used by a GUI
     * (for example) to color a rectangle that represents the item.
     *
     * @param item the item with the desired color
     * @return the color of the specified item
     */
    @Override
    public Color getItemColor(Item item) {
        return this.map.get(item).getColor();
    }

    /**
     * Returns the location of the specified item. The location may be START,
     * FINISH, or BOAT.
     *
     * @param item the item with the desired location
     * @return the location of the specified item
     */
    @Override
    public Location getItemLocation(Item item) {
        return this.map.get(item).getLocation();
    }

    /**
     * Changes the location of the specified item to the specified location.
     *
     * @param item     whose location will be changed
     * @param location the new location of the specified item
     */
    @Override
    public void setItemLocation(Item item, Location location) {
        map.get(item).setLocation(location);
    }

    /**
     * Returns the location of the boat.
     *
     * @return the location of the boat
     */
    @Override
    public Location getBoatLocation() {
        return this.boatLocation;
    }

    public void setBoatLocation(Location location) {
        this.boatLocation = location;
    }

    /**
     * Loads the specified item onto the boat. Assuming that all the
     * required conditions are met, this method will change the location
     * of the specified item to BOAT. Typically, the following conditions
     * must be met: (1) the item's location and the boat's location
     * must be the same, and (2) there must be room on the boat for the
     * item. If any condition is not met, this method does nothing.
     *
     * @param item the item to load onto the boat
     */
    @Override
    public void loadBoat(Item item) {
        if (getItemLocation(item) == getBoatLocation() && getNumberOfElementsInBoard() < 2)
            setItemLocation(item, Location.BOAT);
    }

    private int getNumberOfElementsInBoard() {
        int counter = 0;

        for (Item item : Item.values()) {
            if (!(item.ordinal() < numberOfItems())) break;
            if (map.get(item).getLocation() == Location.BOAT) counter++;
        }
        return counter;
    }

    /**
     * Unloads the specified item from the boat. If the item is on the boat
     * (the item's location is BOAT), then the item's location is changed to
     * the boat's location. If the item is not on the boat, then this method
     * does nothing.
     *
     * @param item the item to be unloaded
     */
    @Override
    public void unloadBoat(Item item) {
        if (getItemLocation(item) == Location.BOAT) {
            map.get(item).setLocation(getBoatLocation());
        }
    }

    /**
     * Rows the boat to the other shore. This method will only change the
     * location of the boat if the boat has a passenger that can drive the boat.
     */
    @Override
    public void rowBoat() {
        assert (getBoatLocation() != Location.BOAT);
        if (getBoatLocation() == Location.START) {
            setBoatLocation(Location.FINISH);
        } else {
            setBoatLocation(Location.START);
        }
    }

    /**
     * True when the location of all the game items is FINISH.
     *
     * @return true if all game items of a location of FINISH, false otherwise
     */
    @Override
    public boolean gameIsWon() {
        for (Item item : Item.values()) {
            if (!(item.ordinal() < numberOfItems())) break;
            if(getItemLocation(item) != Location.FINISH) return false;
        }
        return true;
    }

    /**
     * True when one or more implementation-specific conditions are met.
     * The conditions have to do with which items are on which side of the
     * river. If an item is in the boat, it is typically still considered
     * to be on the same side of the river as the boat.
     *
     * @return true when one or more game-specific conditions are met, false
     * otherwise
     */
    @Override
    public boolean gameIsLost() {
        return false;
    }

    /**
     * Resets the game.
     */
    @Override
    public void resetGame() {
        for (Item item : Item.values()) {
            if (!(item.ordinal() < numberOfItems())) break;
            getMap().get(item).setLocation(Location.START);
        }
        setBoatLocation(Location.START);
    }
}
