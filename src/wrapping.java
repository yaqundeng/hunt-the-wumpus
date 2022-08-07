import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

/**
 * wrapping inherits from the mazeCommon class.
 * It  introduces {@code totalEdges} to represent the removable walls
 * It also introduces {@code newRemain} to represent the remaining walls if perfect
 * A new constrain is the remaining walls should be the fewer than the bew remaining walls
 */
public class wrapping extends mazeCommon {
    private int totalEdges;
    private final int newRemain;

    /**
     * Constructs a perfect object and initializes it to the given
     * rows, cols, remaining walls
     *
     * @param rows           the number of rows of a maze
     * @param cols           the number of cols of a maze
     * @param remainingWalls the remaining walls of a maze
     * @throws IllegalArgumentException {@code remainingWalls} is
     *                                  not fewer the new remaining walls
     */
    public wrapping(int rows, int cols, int remainingWalls) {
        super(rows, cols, remainingWalls);
        this.totalEdges = rows * cols * 2;
        this.newRemain = this.getPerfectRemain() + (rows + cols);
        if (remainingWalls >= this.newRemain) {
            throw new IllegalArgumentException("remaining walls should be less than " +
                    this.newRemain);
        }
        // add borders to the wall list
        for (int i = 0; i < this.getRow(); i++) {
            for (int j = 0; j < this.getCol(); j++) {
                if (i == 0) {
                    // if reaches the first row, add the upper wall to the list
                    room[] roomWall = new room[2];
                    roomWall[0] = this.getRooms()[i][j];
                    roomWall[1] = this.getRooms()[rows - 1][j];
                    this.getWalls().add(roomWall);
                }
                if (j == 0) {
                    // if reaches the first col, add the left wall to the list
                    room[] roomWall = new room[2];
                    roomWall[0] = this.getRooms()[i][j];
                    roomWall[1] = this.getRooms()[i][cols - 1];
                    this.getWalls().add(roomWall);
                }
            }
        }
    }

    @Override
    public void removeWall() {
        //1. choose randomly from walls
        room[] wallChose;
        // if walls remained is greater than the perfect remain
        // do as the perfect does
        if (this.totalEdges > this.newRemain) {
            do {
                Random randomizer = new Random();
                wallChose = this.getWalls().get(randomizer.nextInt(
                        this.getWalls().size()));
            }//2. if the two rooms that the randomly selected wall connected
            // are in the same set
            while (this.inSame(wallChose[0], wallChose[1]));
            //3. if they are, then move on to another wall
            //4. if they are not, then update the roomSet,
            Iterator<HashSet<room>> iter = this.getRoomSet().iterator();
            HashSet<room> set1 = new HashSet<>();
            HashSet<room> set2 = new HashSet<>();
            while (iter.hasNext()) {
                HashSet<room> next = iter.next();
                if (next.contains(wallChose[0])) {
                    set1 = next;
                    iter.remove();
                }
                if (next.contains(wallChose[1])) {
                    set2 = next;
                }
                set2.addAll(set1);
            }
        } else {
            // reaching here, inner edges are smaller than or equal to the perfect remain
            Random randomizer = new Random();
            wallChose = this.getWalls().get(randomizer.nextInt(
                    this.getWalls().size()));
        }
        //5.delete the wall from the walls
        this.getWalls().remove(wallChose);
        //6. set that direction in two rooms to be true;
        if (wallChose[0].getRow() == wallChose[1].getRow() &&
                wallChose[0].getCol() == 0 &&
                wallChose[1].getCol() == this.getCol() - 1) {
            wallChose[0].getDirection()[2] = null;
            wallChose[1].getDirection()[3] = null;
        } else if (wallChose[0].getCol() == wallChose[1].getCol() &&
                wallChose[0].getRow() == 0 &&
                wallChose[1].getRow() == this.getRow() - 1) {
            wallChose[0].getDirection()[0] = null;
            wallChose[1].getDirection()[1] = null;
        } else if (wallChose[0].getCol() == wallChose[1].getCol() - 1) {
            //means wall are horizontally connected
            wallChose[0].getDirection()[3] = null;
            wallChose[1].getDirection()[2] = null;
        } else if (wallChose[0].getRow() == wallChose[1].getRow() - 1) {
            wallChose[0].getDirection()[1] = null;
            wallChose[1].getDirection()[0] = null;
        }
    }

//    @Override
//    public void printChoices(room room1) {
//        System.out.print("Your next choices are: ");
//        if (room1.getRow() != this.getRow() - 1 && room1.getCol()
//        != this.getCol() - 1) {
//            // if the room is not at the last row or the last col
//            if (room1.getDirection()[0]) {
//                System.out.print("UP / ");
//            }
//            if (room1.getDirection()[1]) {
//                System.out.print("DOWN / ");
//            }
//            if (room1.getDirection()[2]) {
//                System.out.print("LEFT / ");
//            }
//            if (room1.getDirection()[3]) {
//                System.out.print("RIGHT / ");
//            }
//            System.out.println();
//        } else {
//            // if the room is at the last row
//            if (room1.getRow() == this.getRow() - 1) {
//                if (room1.getDirection()[0]) {
//                    System.out.print("UP / ");
//                }
//                if (this.getRooms()[0][room1.getCol()].getDirection()[0]) {
//                    // if the adjacent first room's upper wall doesn't exist
//                    System.out.print("DOWN / ");
//                }
//                if (room1.getDirection()[2]) {
//                    System.out.print("LEFT / ");
//                }
//                if (room1.getDirection()[3]) {
//                    System.out.print("RIGHT / ");
//                }
//            }
//            if (room1.getCol() == this.getCol() - 1) {
//                // if the room is at the last col
//                if (room1.getDirection()[0]) {
//                    System.out.print("UP / ");
//                }
//                if (room1.getDirection()[1]) {
//                    System.out.print("DOWN / ");
//                }
//                if (room1.getDirection()[2]) {
//                    System.out.print("LEFT / ");
//                }
//                if (this.getRooms()[room1.getRow()][0].getDirection()[2]) {
//                    // in the first col, if the adjacent room's left wall doesn't exist
//                    System.out.print("RIGHT / ");
//                }
//            }
//        }
//    }
//
//    @Override
//    public void makeMoves() {
//        room curRoom = this.getStart();
//        int collection = 0;
//
//        while (!curRoom.equals(this.getGoal())) {
//            // print choices
//            this.printChoices(curRoom);
//            // print status
//            this.printStatus(curRoom, collection);
//            //scan user input
//            Scanner input = new Scanner(System.in);
//            System.out.println("Enter your choice: ");
//            String choice = input.nextLine();
//            // update the previous location cell
//            if (curRoom.getCoins() == 3) {
//                curRoom.setCoins(0);
//            }
//            //move the current location
//            if (curRoom.getRow() != this.getRow() - 1 && curRoom.getCol() != this.getCol() - 1
//                    && curRoom.getRow() != 0 && curRoom.getCol() != 0) {
//                // if the room is not at the last row or the last col
//                if (choice.equals("UP") && curRoom.getDirection()[0]) {
//                    // if the choice is up and the upper wall doesn't exist
//                    curRoom = this.getRooms()[curRoom.getRow() - 1][curRoom.getCol()];
//                } else if (choice.equals("DOWN") && curRoom.getDirection()[1]) {
//                    // if the choice is down and the lower wall doesn't exist
//                    curRoom = this.getRooms()[curRoom.getRow() + 1][curRoom.getCol()];
//                } else if (choice.equals("LEFT") && curRoom.getDirection()[2]) {
//                    curRoom = this.getRooms()[curRoom.getRow()][curRoom.getCol() - 1];
//                } else if (choice.equals("RIGHT") && curRoom.getDirection()[3]) {
//                    curRoom = this.getRooms()[curRoom.getRow()][curRoom.getCol() + 1];
//                } else {
//                    //illegal input
//                    System.out.println("Please choose again");
//                }
//            } else {
//                // if the room is at the first row
//                if (curRoom.getRow() == 0) {
//                    if (choice.equals("UP") && curRoom.getDirection()[0]) {
//                        // if the choice is up and the upper wall doesn't exist
//                        curRoom = this.getRooms()[this.getRow() - 1][curRoom.getCol()];
//                    } else if (choice.equals("DOWN") && curRoom.getDirection()[1]) {
//                        curRoom = this.getRooms()[curRoom.getRow() + 1][curRoom.getCol()];
//                    } else if (choice.equals("LEFT") && curRoom.getDirection()[2] && curRoom.getCol() != 0) {
//                        curRoom = this.getRooms()[curRoom.getRow()][curRoom.getCol() - 1];
//                    } else if (choice.equals("RIGHT") && curRoom.getDirection()[3]) {
//                        curRoom = this.getRooms()[curRoom.getRow()][curRoom.getCol() + 1];
//                    }
//                }
//                // if the room is at the last row
//                if (curRoom.getRow() == this.getRow() - 1) {
//                    if (choice.equals("UP") && curRoom.getDirection()[0]) {
//                        // if the choice is up and the upper wall doesn't exist
//                        curRoom = this.getRooms()[curRoom.getRow() - 1][curRoom.getCol()];
//                    } else if (choice.equals("DOWN") && this.getRooms()[0][curRoom.getCol()].getDirection()[0]) {
//                        curRoom = this.getRooms()[0][curRoom.getCol()];
//                    } else if (choice.equals("LEFT") && curRoom.getDirection()[2] && curRoom.getRow() != 0) {
//                        curRoom = this.getRooms()[curRoom.getRow()][curRoom.getCol() - 1];
//                    } else if (choice.equals("RIGHT") && curRoom.getDirection()[3]) {
//                        curRoom = this.getRooms()[curRoom.getRow()][curRoom.getCol() + 1];
//                    }
//                }
//                // if the room is at the first col
//                if (curRoom.getCol() == 0) {
//                    if (choice.equals("UP") && curRoom.getDirection()[0] && curRoom.getRow() != 0) {
//                        // if the choice is up and the upper wall doesn't exist
//                        curRoom = this.getRooms()[this.getRow() - 1][curRoom.getCol()];
//                    } else if (choice.equals("DOWN") && curRoom.getDirection()[1]) {
//                        curRoom = this.getRooms()[curRoom.getRow() + 1][curRoom.getCol()];
//                    } else if (choice.equals("LEFT") && curRoom.getDirection()[2]) {
//                        curRoom = this.getRooms()[curRoom.getRow()][curRoom.getCol() - 1];
//                    } else if (choice.equals("RIGHT") && curRoom.getDirection()[3]) {
//                        curRoom = this.getRooms()[curRoom.getRow()][curRoom.getCol() + 1];
//                    }
//                }
//                // if the room is at the last col
//                if (curRoom.getCol() == this.getCol() - 1) {
//                    // if the room is not at the last row or the last col
//                    if (choice.equals("UP") && curRoom.getDirection()[0] && curRoom.getRow() != 0) {
//                        // if the choice is up and the upper wall doesn't exist
//                        curRoom = this.getRooms()[curRoom.getRow() - 1][curRoom.getCol()];
//                    } else if (choice.equals("DOWN") && curRoom.getDirection()[1]) {
//                        // if the choice is down and the lower wall doesn't exist
//                        curRoom = this.getRooms()[curRoom.getRow() + 1][curRoom.getCol()];
//                    } else if (choice.equals("LEFT") && curRoom.getDirection()[2]) {
//                        curRoom = this.getRooms()[curRoom.getRow()][curRoom.getCol() - 1];
//                    } else if (choice.equals("RIGHT") && this.getRooms()[curRoom.getRow()][0].getDirection()[2]) {
//                        curRoom = this.getRooms()[curRoom.getRow()][0];
//                    }
//                } else {
//                    System.out.println("Please choose again");
//                }
//            }
//
//            // collect the coins
//            if (curRoom.getCoins() == 1) {
//                // has a coin
//                collection += curRoom.getAdditionalCoins();
//            } else if (curRoom.getCoins() == 2) {
//                // meets a thief
//                collection *= .90;
//            }
//            //set current room to print
//            if (curRoom.getCoins() == 2) {
//                // if the current location has a thief
//                curRoom.setCoins(3);
//                //print the map
//                this.printMap();
//                curRoom.setCoins(2);
//            } else if (curRoom.getCoins() == 5) {
//                // if the current location is the start point
//                curRoom.setCoins(3);
//                //print the map
//                this.printMap();
//                curRoom.setCoins(5);
//            } else {
//                curRoom.setCoins(3);
//                //print the map
//                this.printMap();
//            }
//        }
//        System.out.println("You've reached the goal!!");
//    }


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

            // if the current room is not at the border
            if (curRoom.getRow() != this.getRow() - 1 && curRoom.getCol() !=
                    this.getCol() - 1
                    && curRoom.getRow() != 0 && curRoom.getCol() != 0) {
                if (choice.equals("UP") && curRoom.getDirection()[0] == null) {
                    // if the choice is up and the upper wall doesn't exist
                    curRoom = this.getRooms()[curRoom.getRow() - 1][curRoom.getCol()];
                } else if (choice.equals("DOWN") && curRoom.getDirection()[1] == null) {
                    // if the choice is down and the lower wall doesn't exist
                    curRoom = this.getRooms()[curRoom.getRow() + 1][curRoom.getCol()];
                } else if (choice.equals("LEFT") && curRoom.getDirection()[2] == null) {
                    curRoom = this.getRooms()[curRoom.getRow()][curRoom.getCol() - 1];
                } else if (choice.equals("RIGHT") && curRoom.getDirection()[3] == null) {
                    curRoom = this.getRooms()[curRoom.getRow()][curRoom.getCol() + 1];
                } else {
                    //illegal input
                    System.out.println("Please choose again");
                }
            } else {
                //if first row
                if (curRoom.getRow() == 0) {
                    if (choice.equals("UP") && curRoom.getDirection()[0] == null) {
                        // if the choice is up and the upper wall doesn't exist
                        curRoom = this.getRooms()[this.getRow() - 1][curRoom.getCol()];
                    } else if (choice.equals("DOWN") && curRoom.getDirection()[1] == null) {
                        // if the choice is down and the lower wall doesn't exist
                        curRoom = this.getRooms()[curRoom.getRow() + 1][curRoom.getCol()];
                    } else if (choice.equals("LEFT") && curRoom.getDirection()[2] == null) {
                        if (curRoom.getCol() == 0) {
                            // upper-left case
                            curRoom = this.getRooms()[curRoom.getRow()][this.getCol() - 1];
                        } else {
                            curRoom = this.getRooms()[curRoom.getRow()][curRoom.getCol() - 1];
                        }
                    } else if (choice.equals("RIGHT") && curRoom.getDirection()[3] == null) {
                        if (curRoom.getCol() == this.getCol() - 1) {
                            //upper-right case
                            curRoom = this.getRooms()[curRoom.getRow()][0];
                        } else {
                            curRoom = this.getRooms()[curRoom.getRow()][curRoom.getCol() + 1];
                        }
                    } else {
                        System.out.println("Please choose again");
                    }
                } else if (curRoom.getRow() == this.getRow() - 1) {
                    // if last row
                    if (choice.equals("UP") && curRoom.getDirection()[0] == null) {
                        // if the choice is up and the upper wall doesn't exist
                        curRoom = this.getRooms()[curRoom.getRow() - 1][curRoom.getCol()];
                    } else if (choice.equals("DOWN") && curRoom.getDirection()[1] == null) {
                        // if the choice is down and the lower wall doesn't exist
                        curRoom = this.getRooms()[0][curRoom.getCol()];
                    } else if (choice.equals("LEFT") && curRoom.getDirection()[2] == null) {
                        if (curRoom.getCol() == 0) {
                            //bottom-left case
                            curRoom = this.getRooms()[curRoom.getRow()][this.getCol() - 1];
                        } else {
                            curRoom = this.getRooms()[curRoom.getRow()][curRoom.getCol() - 1];
                        }
                    } else if (choice.equals("RIGHT") && curRoom.getDirection()[3] == null) {
                        if (curRoom.getCol() == this.getCol() - 1) {
                            //bottom-right case
                            curRoom = this.getRooms()[curRoom.getRow()][0];
                        } else {
                            curRoom = this.getRooms()[curRoom.getRow()][curRoom.getCol() + 1];
                        }
                    } else {
                        //illegal input
                        System.out.println("Please choose again");
                    }
                } else if (curRoom.getCol() == 0) {
                    // if first col
                    if (choice.equals("UP") && curRoom.getDirection()[0] == null) {
                        // if the choice is up and the upper wall doesn't exist
                        curRoom = this.getRooms()[curRoom.getRow() - 1][curRoom.getCol()];
                    } else if (choice.equals("DOWN") && curRoom.getDirection()[1] == null) {
                        // if the choice is down and the lower wall doesn't exist
                        curRoom = this.getRooms()[curRoom.getRow() + 1][curRoom.getCol()];
                    } else if (choice.equals("LEFT") && curRoom.getDirection()[2] == null) {
                        curRoom = this.getRooms()[curRoom.getRow()][this.getCol() - 1];
                    } else if (choice.equals("RIGHT") && curRoom.getDirection()[3] == null) {
                        curRoom = this.getRooms()[curRoom.getRow()][curRoom.getCol() + 1];
                    } else {
                        //illegal input
                        System.out.println("Please choose again");
                    }
                } else if (curRoom.getCol() == this.getCol() - 1) {
                    // if last col
                    if (choice.equals("UP") && curRoom.getDirection()[0] == null) {
                        // if the choice is up and the upper wall doesn't exist
                        curRoom = this.getRooms()[curRoom.getRow() - 1][curRoom.getCol()];
                    } else if (choice.equals("DOWN") && curRoom.getDirection()[1] == null) {
                        // if the choice is down and the lower wall doesn't exist
                        curRoom = this.getRooms()[curRoom.getRow() + 1][curRoom.getCol()];
                    } else if (choice.equals("LEFT") && curRoom.getDirection()[2] == null) {
                        curRoom = this.getRooms()[curRoom.getRow()][curRoom.getCol() - 1];
                    } else if (choice.equals("RIGHT") && curRoom.getDirection()[3] == null) {
                        curRoom = this.getRooms()[curRoom.getRow()][0];
                    } else {
                        //illegal input
                        System.out.println("Please choose again");
                    }
                }
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
    public TypeOfMaze getType() {
        return TypeOfMaze.WRAPPED;
    }

    @Override
    public void bringDown() {
        while (this.totalEdges > this.getRemainingWalls()) {
            this.removeWall();
            this.totalEdges--;
        }
    }

    public static void main(String[] args) {
        wrapping w = new wrapping(4, 6, 15);
        w.assignCoins();
        System.out.println(w.getPerfectRemain());
        System.out.println(w.newRemain);
//        for (room[] wall : w.getWalls()) {
//            System.out.println(wall[0] + " " + wall[1]);
//        }
        w.bringDown();
        w.printMap();
        w.makeMoves();

    }
}
