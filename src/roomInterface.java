/**
 * roomInterface.
 * All rooms have similar basic functions
 * to get information from an implementation of a room interface.
 *
 * <p>
 * Different {@code roomInterface} implementations should work together, meaning
 * that:
 *
 * <ul>
 * <li>To get the row number of a room.
 * <li>To get the col number of a room.
 * <li>To set the coins, which represent the property of a room.
 * <li>To get the coins, which represent the property of a room.
 * <li>To set the real coins of the room
 * <li>To get the real coins contained in the room.
 * <li>To get the wall information of the room.
 * </ul>
 */
public interface roomInterface {

    /**
     * Return the row number of a room.
     *
     * @return an integer that represents the row number of the room.
     */
    int getRow();

    /**
     * Return the col number of a room.
     *
     * @return an integer that represents the col number of the room.
     */
    int getCol();

    /**
     * Return the coins that room has, which represent the property of the room.
     *
     * @return an integer that represents the property of the room.
     */
    int getCoins();

    /**
     * Takes an integer and assign it to the real coins a room has.
     * <p>
     * Set an integer that represents the real coins the room has.
     */
    void setAdditionalCoins(int coins);

    /**
     * Return the real coins that room has.
     *
     * @return an integer that represents the real coins the room has.
     */
    int getAdditionalCoins();

    /**
     * Takes an integer and assign it to the property of the room.
     * <p>
     * Set an integer that represents the property of the room.
     */
    void setCoins(int coins);

    /**
     * Return the boolean array that indicates the wall information the room has.
     *
     * @return a boolean array that represents whether a wall exists in certain direction.
     */
    DIRECTIONS[] getDirection();
}
