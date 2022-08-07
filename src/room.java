/**
 * This class represents a room. It implements the room interface.
 * A room has its row and col, the number of coins, a real coins it has,
 * wall information, and Max direction.
 */
public class room implements roomInterface {

    private final int row;
    private final int col;
    private final DIRECTIONS[] direction;
    private int coin;
    private int additionalCoins;
    private static final int MAXDIRECTION = 4;

    /**
     * Constructs a room object and initializes it to the given
     * row, col, coin
     *
     * @param row  the row number of a room
     * @param col  the col number of a room
     * @param coin coins the room has
     */
    public room(int row, int col, int coin) {
        this.row = row;
        this.col = col;
        this.coin = coin;
        this.additionalCoins = 0;
        // direction = new boolean[MAXDIRECTION];
        // set all directions of this room;
        this.direction = new DIRECTIONS[MAXDIRECTION];
        this.direction[0] = DIRECTIONS.UP;
        this.direction[1] = DIRECTIONS.BOTTOM;
        this.direction[2] = DIRECTIONS.LEFT;
        this.direction[3] = DIRECTIONS.RIGHT;
//        for (int i = 0; i < MAXDIRECTION; i++) {
//            direction[i] = DIR;
//        }
    }

    @Override
    public int getRow() {
        return this.row;
    }

    @Override
    public int getCol() {
        return this.col;
    }

    @Override
    public int getCoins() {
        return this.coin;
    }

    @Override
    public void setAdditionalCoins(int coins) {
        this.additionalCoins = coins;
    }

    @Override
    public int getAdditionalCoins() {
        return this.additionalCoins;
    }

    @Override
    public void setCoins(int coins) {
        this.coin = coins;
    }

    @Override
    public DIRECTIONS[] getDirection() {
        return this.direction;
    }

    @Override
    public String toString() {
        return "room" + getRow() + getCol();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof room)) {
            return false;
        }
        room o = (room) obj;
        return o.getRow() == this.getRow() && o.getCol() == this.getCol();
    }

    public static void main(String[] args) {
        room m = new room(2, 3, 0);
        System.out.print(m.getDirection().length);
    }
}
