import java.util.*;

/**
 * This class represents a common maze. It implements the maze interface.
 * A maze has its number of rows and cols, the number of perfect remaining walls,
 * the number of real remaining walls, the start and goal location, all the roomsl
 * wall information, and a room set.
 */
public abstract class mazeCommon implements mazeInterface {
    private final int rows;
    private final int cols;
    private final int perfectRemain;
    private final int remainingWalls;
    private room startRoom;
    private room goalRoom;
    private final room[][] rooms;
    private final List<room[]> walls;
    private final HashSet<HashSet<room>> roomSet;
    private static final double chanceMeetingCoins = .2;
    private static final double chanceMeetingThief = .1;

    /**
     * Constructs a maze object and initializes it to the given
     * rows, cols, remaining walls
     *
     * @param rows           the number of rows of a maze
     * @param cols           the number of cols of a maze
     * @param remainingWalls the remaining walls of a maze
     * @throws IllegalArgumentException {@code rows} is not greater than 0
     * @throws IllegalArgumentException {@code cols} is not greater than 0
     */
    public mazeCommon(int rows, int cols, int remainingWalls) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("rows and columns should be positive");
        }
        this.rows = rows;
        this.cols = cols;
        this.remainingWalls = remainingWalls;

        //int totalEdges = rows * cols * 2 - (cols + rows);
        this.perfectRemain = rows * cols - rows - cols + 1;
        this.rooms = new room[rows][cols];
        walls = new ArrayList<>();
        roomSet = new HashSet<>();

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                // create a new room
                rooms[i][j] = new room(i, j, 0);
                // update the roomSet
                HashSet<room> newSet = new HashSet<>();
                newSet.add(rooms[i][j]);
                this.roomSet.add(newSet);
            }
        }

        // update the walls list
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                if (i != this.rows - 1) {
                    room[] roomWall = new room[2];
                    roomWall[0] = rooms[i][j];
                    roomWall[1] = rooms[i + 1][j];
                    this.walls.add(roomWall);
                }
                if (j != this.cols - 1) {
                    room[] roomWall = new room[2];
                    roomWall[0] = rooms[i][j];
                    roomWall[1] = rooms[i][j + 1];
                    this.walls.add(roomWall);
                }
            }
        }

        // set the start and the goal location
        int startCor, goalCor;
        startCor = new Random().nextInt((this.rows - 1) * 10 + this.cols);
        int startRow = Math.max(startCor / 10, 0);
        int startCol = startCor % this.cols;
        this.startRoom = rooms[startRow][startCol];
        startRoom.setCoins(5);


        do {
            goalCor = new Random().nextInt((this.rows - 1) * 10 + this.cols);
            int endRow = Math.max(goalCor / 10, 0);
            int endCol = goalCor % this.cols;
            this.goalRoom = rooms[endRow][endCol];
        } while (this.goalRoom.equals(this.startRoom));

        goalRoom.setCoins(6);
    }

    @Override
    public int getRemainingWalls() {
        return this.remainingWalls;
    }

    public abstract void removeWall();

    @Override
    public room getStart() {
        return this.startRoom;
    }

    @Override
    public room getGoal() {
        return this.goalRoom;
    }

    @Override
    public void assignCoins() {
        // set the coins for each room
        int roomHasCoins = (int) (this.rows * this.cols * chanceMeetingCoins);
        int roomHasThief = (int) (this.rows * this.cols * chanceMeetingThief);
        while (roomHasCoins > 0) {
            int corRow, corCol;
            int cor = new Random().nextInt((this.rows - 1) * 10 + this.cols);
            corRow = Math.max(cor / 10, 0);
            corCol = cor % this.cols;
            if (rooms[corRow][corCol].getCoins() == 0) {
                rooms[corRow][corCol].setCoins(1);
                rooms[corRow][corCol].setAdditionalCoins(new Random().nextInt(9) + 1);
                roomHasCoins--;
            }
        }
        while (roomHasThief > 0) {
            int corRow, corCol;
            int cor = new Random().nextInt((this.rows - 1) * 10 + this.cols);
            corRow = Math.max(cor / 10, 0);
            corCol = cor % this.cols;
            if (rooms[corRow][corCol].getCoins() == 0) {
                rooms[corRow][corCol].setCoins(2);
                roomHasThief--;
            }
        }
    }

    @Override
    public int getRow() {
        return this.rows;
    }

    @Override
    public int getCol() {
        return this.cols;
    }

    @Override
    public room[][] getRooms() {
        return this.rooms;
    }

    @Override
    public boolean inSame(room room1, room room2) {
        for (HashSet<room> set : getRoomSet()) {
            if (set.contains(room1) && set.contains(room2)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<room[]> getWalls() {
        return this.walls;
    }

    @Override
    public HashSet<HashSet<room>> getRoomSet() {
        return this.roomSet;
    }

    @Override
    public void printMap() {
        System.out.println("^ indicates the start location");
        System.out.println("# indicates the goal location");
        System.out.println("* indicates the current location");
        System.out.println("$ indicates a room contains coins");
        System.out.println("& indicates a room with a thief");
        String sign;
        for (int i = 0; i < this.getRow() * 2 + 1; i++) {
            for (int j = 0; j < this.getCol(); j++) {

                if (i % 2 == 0) { //even row, print the horizontal edges

                    System.out.print("+");
                    // if not the last line
                    if (i != this.getRow() * 2) {
                        // wall exists and is not the last col
                        if (j != this.getCol() - 1 && this.getRooms()[i / 2][j].getDirection()[0] == DIRECTIONS.UP) {
                            System.out.print("----");
                        } else if (j != this.getCol() - 1 && this.getRooms()[i / 2][j].getDirection()[0] != DIRECTIONS.UP) {
                            // wall doesn't exist and is not the last col
                            System.out.print("    ");
                        } else if (j == this.getCol() - 1 && this.getRooms()[i / 2][j].getDirection()[0] == DIRECTIONS.UP) {
                            System.out.print("----+");
                        } else if (j == this.getCol() - 1 && this.getRooms()[i / 2][j].getDirection()[0] != DIRECTIONS.UP) {
                            System.out.print("    +");
                        }
                    } else {
                        // reaching the last line
                        if (j != this.getCol() - 1 && this.getRooms()[(i - 1) / 2][j].getDirection()[1] == DIRECTIONS.BOTTOM) {
                            // wall exists and not the last col
                            System.out.print("----");
                        } else if (j != this.getCol() - 1 && this.getRooms()[(i - 1) / 2][j].getDirection()[1] != DIRECTIONS.BOTTOM) {
                            // wall doesn't exist and not the last col
                            System.out.print("    ");
                        } else if (j == this.getCol() - 1 && this.getRooms()[(i - 1) / 2][j].getDirection()[1] == DIRECTIONS.BOTTOM) {
                            //wall exists and is the last col
                            System.out.print("----+");
                        } else if (j == this.getCol() - 1 && this.getRooms()[(i - 1) / 2][j].getDirection()[1] != DIRECTIONS.BOTTOM) {
                            // wall doesn't exist and is the last col
                            System.out.print("    +");
                        }
                    }
                } else {

                    // odd rows, print the vertical edges
                    if (this.getRooms()[i / 2][j].getDirection()[2] == DIRECTIONS.LEFT) {
                        // wall exists
                        if (this.getRooms()[i / 2][j].getCoins() == 1) {
                            sign = " $" + this.getRooms()[i / 2][j].getAdditionalCoins() + " ";
                        } else if (this.getRooms()[i / 2][j].getCoins() == 2) {
                            sign = " &  ";
                        } else if (this.getRooms()[i / 2][j].getCoins() == 3) {
                            sign = " *  ";
                        } else if (this.getRooms()[i / 2][j].getCoins() == 5) {
                            sign = " ^  ";
                        } else if (this.getRooms()[i / 2][j].getCoins() == 6) {
                            sign = " #  ";
                        } else {
                            sign = "    ";
                        }
                        System.out.print("|" + sign);
                    } else {
                        // wall doesn't exist
                        if (this.getRooms()[i / 2][j].getCoins() == 1) {
                            sign = " $" + this.getRooms()[i / 2][j].getAdditionalCoins() + " ";
                        } else if (this.getRooms()[i / 2][j].getCoins() == 2) {
                            sign = " &  ";
                        } else if (this.getRooms()[i / 2][j].getCoins() == 3) {
                            sign = " *  ";
                        } else if (this.getRooms()[i / 2][j].getCoins() == 5) {
                            sign = " ^  ";
                        } else if (this.getRooms()[i / 2][j].getCoins() == 6) {
                            sign = " #  ";
                        } else {
                            sign = "    ";
                        }
                        System.out.print(" " + sign);
                    }
                    // reaching the last col
                    if (j == this.getCol() - 1) {
                        if (this.getRooms()[i / 2][j].getDirection()[3] == DIRECTIONS.RIGHT) {
                            // right wall exists
                            System.out.print("|");
                        } else {
                            // right wall doesn't exist
                            System.out.print(" ");
                        }
                    }
                }
            }
            System.out.println();
        }
    }

    @Override
    public void printChoices(room room1) {
        System.out.print("Your next choices are: ");
        if (room1.getDirection()[0] != DIRECTIONS.UP) {
            System.out.print("UP / ");
        }
        if (room1.getDirection()[1] != DIRECTIONS.BOTTOM) {
            System.out.print("DOWN / ");
        }
        if (room1.getDirection()[2] != DIRECTIONS.LEFT) {
            System.out.print("LEFT / ");
        }
        if (room1.getDirection()[3] != DIRECTIONS.RIGHT) {
            System.out.print("RIGHT / ");
        }
        System.out.println();
    }

    @Override
    public void makeMoves() {
        room curRoom = this.getStart();
        int collection = 0;

        while (!curRoom.equals(this.getGoal())) {
            // print choices
            this.printChoices(curRoom);
            // print status
            this.printStatus(curRoom, collection);
            //scan user input
            Scanner input = new Scanner(System.in);
            System.out.println("Enter your choice: ");
            String choice = input.nextLine();
            // update the previous location cell
            if (curRoom.getCoins() == 3) {
                curRoom.setCoins(0);
            }
            //move the current location
            if (choice.equals("UP") && curRoom.getDirection()[0] != DIRECTIONS.UP) {
                // if the choice is up and the upper wall doesn't exist
                curRoom = this.getRooms()[curRoom.getRow() - 1][curRoom.getCol()];
            } else if (choice.equals("DOWN") && curRoom.getDirection()[1] != DIRECTIONS.BOTTOM) {
                // if the choice is down and the lower wall doesn't exist
                curRoom = this.getRooms()[curRoom.getRow() + 1][curRoom.getCol()];
            } else if (choice.equals("LEFT") && curRoom.getDirection()[2] != DIRECTIONS.LEFT) {
                curRoom = this.getRooms()[curRoom.getRow()][curRoom.getCol() - 1];
            } else if (choice.equals("RIGHT") && curRoom.getDirection()[3] != DIRECTIONS.RIGHT) {
                curRoom = this.getRooms()[curRoom.getRow()][curRoom.getCol() + 1];
            } else {
                //illegal input
                System.out.println("Please choose again");
            }

            // collect the coins
            if (curRoom.getCoins() == 1) {
                // has a coin
                collection += curRoom.getAdditionalCoins();
            } else if (curRoom.getCoins() == 2) {
                // meets a thief
                collection *= .90;
            }
            //set current room to print
            if (curRoom.getCoins() == 2) {
                // if the current location has a thief
                curRoom.setCoins(3);
                //print the map
                this.printMap();
                curRoom.setCoins(2);
            } else if (curRoom.getCoins() == 5) {
                // if the current location is the start point
                curRoom.setCoins(3);
                //print the map
                this.printMap();
                curRoom.setCoins(5);
            } else {
                curRoom.setCoins(3);
                //print the map
                this.printMap();
            }
        }
        this.printStatus(curRoom, collection);
        System.out.println("You've reached the goal!!");
    }

    @Override
    public void printStatus(room room1, int coins) {
        System.out.println("Your position: " + room1 + "\nYour gold collection: " + coins);
    }


    @Override
    public int getPerfectRemain() {
        return this.perfectRemain;
    }

    @Override
    public void setStart(int row, int col) {
        if (row >= this.getRow() || col >= this.getCol()) {
            throw new IllegalArgumentException("index out of bound.");
        }
        this.startRoom = this.getRooms()[row][col];
        this.startRoom.setCoins(5);
    }

    @Override
    public void setGoal(int row, int col) {
        if (row >= this.getRow() || col >= this.getCol()) {
            throw new IllegalArgumentException("index out of bound.");
        }
        if (rooms[row][col].equals(this.getStart())) {
            throw new IllegalArgumentException("goal cannot be the same as the start");
        }
        this.goalRoom = this.getRooms()[row][col];
        this.goalRoom.setCoins(6);
    }

    public abstract TypeOfMaze getType();

    public abstract void bringDown();
}
