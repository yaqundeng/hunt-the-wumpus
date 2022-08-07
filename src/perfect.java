import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

/**
 * perfect inherits from the mazeCommon class.
 * It also introduce {@code innerEdges} to represent the removable walls
 * A new constrain is the remaining walls should be the same as perfect remaining walls
 */
public class perfect extends mazeCommon {

    private int innerEdges;

    /**
     * Constructs a perfect object and initializes it to the given
     * rows, cols, remaining walls
     *
     * @param rows           the number of rows of a maze
     * @param cols           the number of cols of a maze
     * @param remainingWalls the remaining walls of a maze
     * @throws IllegalArgumentException {@code remainingWalls} is
     *                                  not the same as the perfect remaining walls
     */
    public perfect(int rows, int cols, int remainingWalls) {
        super(rows, cols, remainingWalls);
//        int totalEdges = rows * cols * 2 + rows + cols;
//        // remainingwalls + savedwalls = totoal edges - rows - cols;
        this.innerEdges = rows * cols * 2 - (rows + cols);
//        this.remainingWalls = innerEdges - rows * cols + 1;
        if (remainingWalls != this.getPerfectRemain()) {
            throw new IllegalArgumentException("remaining walls " +
                    "for perfect maze should be " + this.getPerfectRemain());
        }
    }


    @Override
    public void removeWall() {
        //1. choose randomly from walls
        room[] wallChose;
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
        //5.delete the wall from the walls
        this.getWalls().remove(wallChose);
        //6. set that direction in two rooms to be true;
        if (wallChose[0].getCol() == wallChose[1].getCol() - 1) {
            //means walls are horizontally connected
            wallChose[0].getDirection()[3] = null;
            wallChose[1].getDirection()[2] = null;
        } else if (wallChose[0].getRow() == wallChose[1].getRow() - 1) {
            wallChose[0].getDirection()[1] = null;
            wallChose[1].getDirection()[0] = null;
        }

    }

    @Override
    public TypeOfMaze getType() {
        return TypeOfMaze.PERFECT;
    }

    @Override
    public void bringDown() {
        while (this.innerEdges > this.getPerfectRemain()) {
            this.removeWall();
            this.innerEdges--;
        }
    }

    public static void main(String[] args) {
        perfect p = new perfect(4, 6, 15);
        p.setGoal(0, 1);
        p.setStart(3, 4);
        p.assignCoins();
//        for (room[] wall : p.getWalls()) {
//            System.out.println(wall[0] + " " + wall[1]);
//        }

        p.bringDown();

//        for (room[] wall : p.getWalls()) {
//            System.out.println(wall[0] + " " + wall[1]);
//        }
        //print out the grid
        p.printMap();

        //make moves
        p.makeMoves();
        System.out.println(p.getRemainingWalls());

    }
}
