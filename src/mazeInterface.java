import java.util.HashSet;
import java.util.List;

/**
 * mazeInterface.
 * All birds have similar maze functions
 * to get information from an implementation of a maze interface.
 *
 * <p>
 * Different {@code mazeInterface} implementations should work together, meaning
 * that:
 *
 * <ul>
 * <li>To get the row size of a maze.
 * <li>To get the col size of a maze.
 * <li>To get the start location of a maze
 * <li>To get the goal location of a maze.
 * <li>To get all the rooms in the maze.
 * <li>To get the list of walls exist in the maze.
 * <li>To randomly assign a certain number of rooms with coins in the maze.
 * <li>To check how many rooms are currently connected in the maze.
 * <li>To check if two rooms are currently connected.
 * <li>Print the configurations of the maze.
 * <li>The status of extinction
 * <li>The characteristics of a bird
 * <li>The food list
 * <li>To check if two rooms are currently connected.
 * <li>Print the configurations of the maze
 * <li>Print the choices of available directions at the current location
 * <li>Print the location and coins information so far.
 * <li>To get the walls remained in a perfect-maze situation.
 * <li>To get the type of a maze: perfect, non-wrapping, or wrapping
 * <li>Print the location and coins information so far.
 * <li>Bring down a wall in a maze.
 * </ul>
 */
public interface mazeInterface {
    /**
     * Return the row number of a maze.
     *
     * @return an integer that represents the row number of the maze.
     */
    int getRow();

    /**
     * Return the col number of a maze.
     *
     * @return an integer that represents the col number of the maze.
     */
    int getCol();

    /**
     * Return the start location of a maze.
     *
     * @return a room that represents the start room.
     */
    room getStart();

    /**
     * Return the goal location of a maze.
     *
     * @return a room that represents the goal room.
     */
    room getGoal();

    /**
     * Return all the rooms of a maze.
     *
     * @return a room array that represents the all rooms.
     */
    room[][] getRooms();

    /**
     * Return the all the walls of a maze.
     *
     * @return a list of room array with a size of two. The array represents
     * the two rooms the wall connected to.
     */
    List<room[]> getWalls();

    /**
     * Randomly assign a certain number of rooms with coins in the maze.
     */
    void assignCoins();

    /**
     * Return a set of sets, where two connected rooms are in the same small set.
     *
     * @return a set of room sets.
     */
    HashSet<HashSet<room>> getRoomSet();

    /**
     * Check if two rooms are in the same small room set.
     *
     * @return a boolean, if they are in the same set, return true, vise versa.
     */
    boolean inSame(room room1, room room2);

    /**
     * Randomly remove a wall from the maze.
     */
    void removeWall();

    /**
     * Return all the remaining walls after removing.
     *
     * @return an integer that represents the number of remaining walls.
     */
    int getRemainingWalls();

    /**
     * Print the configurations of a maze.
     */
    void printMap();

    /**
     * Print the available next steps at the current location.
     */
    void printChoices(room room1);

    /**
     * Make a movement.
     */
    void makeMoves();

    /**
     * Print the room information and coins so far.
     */
    void printStatus(room room1, int coins);

    //int getFinalWalls();

    /**
     * Return the number of walls should remain if a perfect case applies.
     *
     * @return an integer of remaining walls.
     */
    int getPerfectRemain();

    /**
     * Return the type of the maze..
     *
     * @return the enum of type of maze.
     */
    TypeOfMaze getType();

    /**
     * Bring down all determined walls of the maze.
     */
    void bringDown();

    void setStart(int row, int col);

    void setGoal(int row, int col);
}
